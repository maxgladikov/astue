$(document).ready(function () {
// *** BEGIN ***

    // *** GLOBAL VARS ***
    let ip="http://192.168.56.1";
    let port="8080";
    let address=ip+":"+port+"/api/v1";
    let urlDevices=address+'/devices';
    let urlDevicesId=address+'/devices/';
    let urlSS=address+'/substations';
    let urlSwgrName=address+'/switchgears/name/';
    let editing=false;
    let adding=false;
    let divisionMode=false;
    let substationMode=false;
    let curId;
    let swgr;
    // Drop down menu management
    $("body").on("click", ".dropdown > a", function(){
        $(this).parent().siblings().find('ul').fadeOut(500);
        $(this).next().stop(true,false,true).fadeToggle(500);
        return false;
    });
    
    //Drop down hide once pressed outside
    $(document).mouseup(function(e){
	var container = $(".dropdown > a");
	if (!container.is(e.target) && container.has(e.target).length === 0)
	{
	container.parent().siblings().find('ul').fadeOut(300);
	}

})
    
    // drop down menu fill in  substations
    $.ajax({url: urlSS,
            method: 'GET',
            dataType: 'json',
            success:function(data) {
                $.each(data, function (key, value) {
                    let str='<li class="dropdown"><a class="ss" href="#">'+value.name+'</a>';
                    str=str+"<ul>";
                    value.switchgears.forEach(element => str=str+"<li><a class='switchgear' href='#'>"+element.name+"</a></li>");
                    str=str+"</ul></li>";
                    $('#substations').append(str);
                })
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log(jqXHR.status);
            }
        })


   
    // *** POPULATE THE CHART ***
    $("body").on("click", ".switchgear", function(){
		 let table=$("#table");
        swgr=$(this).text();
        updateTable();
    });
    // *** UPDATE THE TABLE ***
        function updateTable(){
            $("#tableName").remove();
          if( $.fn.dataTable.isDataTable( table )){
                table.destroy() ;}
            let dynUrl=urlSwgrName+swgr;
            $.ajax(
                {
                    url:dynUrl,
                    method: 'GET',
                    dataType: 'json',
                    success: function (data) {
                        $('<div id="tableName" ><h5>'+data.name+'</h5><a class="add popup-toggle fa-thin fa-plus" href="#"></a></div>').insertBefore( $("#table"));
                        drawTable(data.devices);
                       table=$("#table").DataTable({
                            data:data.devices,
                            
                            "paging":false,
                            "searching":false,
                            "iDisplayLength": 10,
                            "bPaginate": false,
                            "bInfo" : false,
                            columns:[
                                {   "data": "name",
                                	"width": "10%"
                                    // "searchable":true,
                                    // "sortable":true,
                                },
                                { "data": "line",
                                	"width": "5%"
                                },
                                { "data": "drawerColumn"},
                                { "data": "drawerRow"},
                                { "data": "ied"},
                                { "data": "power"},
                                { "data": "voltage"},
                                {   "data": "hostAddress"},
                                {   "data": "incomer",
                                	"className": "text-center",
                                    // //visible: (aDurchgang==1 ? true : false),
                                    // className: "text-center",
                                    render: function ( data, type, row ) {
                                        return (data === true) ? '<span class="fa-regular fa-circle-check"></span>'
                                            : '<i class="fa-regular fa-circle"></i>';}
                                },
                                {   "data": "active",
                                	"className": "text-center",
                                    render: function ( data, type, row ) {
                                        return (data === true) ? '<span class="fa-regular fa-circle-check"></span>'
                                            : '<i class="fa-regular fa-circle"></i>';}
                                },
                                {   "data": "description",},
                                {
                                    "data": null,
                                    "render": function (data, type, full, meta) {
                                        return '<div> <button id=e' + data.id + ' class="edit transparent far fa-pen-to-square popup-toggle" href="#"></button>' +
                                            '<button id=d' + data.id + ' class="delete  transparent far fa-trash-can" href="#"></button> </div>';
                                    }
                                }

                            ]
                        });
                    }
                }
            )
        };

        // *** POP UP ***
    $("body").on("click", ".popup-toggle", function(e){
        e.preventDefault();
        if($('.popup').css('display')=="none"){
            if($(this).hasClass('edit')){
                editing=true;
                prepareEdit(this);
            }else if ($(this).hasClass('add')){
                adding=true;
                prepareAdd();
            }else {
                editing=false;
                adding=false;
            }
        }
        $('.popup').fadeToggle( "slow", "linear" );
        $('.popup').draggable();
    });

    // *** SAVE ***
    $("body").on("click", ".submit", function(e){
        e.preventDefault();
        if(adding){
            // *** POST ***
            let url=urlDevices;
            let device=new Device();
            device.createFromForm("#form");
            device.switchgear=swgr;
            device.post(url);
            console.log(device);
            updateTable();
            // console.log(device);
            // $.ajax({url: url,type: 'POST',data:data,
            //     success: function() {
            //         console.log("Successfully added");
            //         updateTable();
            //     },
            //     error: function (){
            //         console.log("not added")
            //     }
            // });
        } else if(editing){
            // *** PUT ***
            let url=urlDevicesId+curId;
            let data=$('#form').serialize();
            $.ajax({method: "PUT",url: url,data: data,
                success: function(data) {
                    console.log('everything was OK');
                    updateTable();
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log("jqXHR.status:"+jqXHR.status);
                }
            })
        }
    })
    // *** DELETE ***
    $("body").on("click", ".delete", function(e){
        if (confirm('sure?')) {
            let id=$(this).attr('id');
            id=id.substring(1,id.length);
            let url=urlDevicesId+id;
            $.ajax({
                url: url,
                type: 'DELETE',
                success: function(result) {
                    console.log("Successfully deleted");
                    updateTable();
                },
                error: function (){
                    console.log("not deleted")
                }
            });
        };
    });
    // Fill in modal when edit
    function prepareEdit(obj){
        let id=$(obj).attr('id');
        id=id.substring(1,id.length);
        let url=urlDevicesId+id;
        $.ajax(
            {
                url:url,
                method: 'GET',
                // dataType: 'json',
                success: function (data) {
                    let form=$('.popup');
                    curId=data.id;
                    form.find("#id").val(curId);
                    form.find("#name").val(data.name);
                    form.find("#line").val(data.line);
                    form.find("#drawerNum").val(data.drawerNum);
                    form.find("#drawerLetter").val(data.drawerLetter);
                    form.find("#power").val(data.power);
                    form.find("#hostAddress").val(data.hostAddress);
                    form.find("#description").val(data.description);
                    form.find("#ied").val(data.ied);
                    form.find("#voltage").val(data.voltage);
                    form.find("#incomer").prop('checked', data.incomer);
                    form.find("#active").prop('checked', data.active);
                    form.find("#incomer").val(data.incomer==true?'true':'false');
                    form.find("#active").val(data.active==true?'true':'false');
                }}
        );
    };
    // Fill in modal when add
    function prepareAdd(){
        let form=$('.popup');
        form.find("#id").val("");
        form.find("#switchgear").val(swgr);
        form.find("#name").val("");
        form.find("#line").val("");
        form.find("#drawerNum").val("");
        form.find("#drawerLetter").val("");
        form.find("#power").val("");
        form.find("#hostAddress").val("");
        form.find("#description").val("");
        form.find("#ied").val("");
        form.find("#voltage").val("");
        form.find("#incomer").prop('checked', false);
        form.find("#active").prop('checked', false);
        form.find("#incomer").val(false);
        form.find("#active").val(false);
    }

    // Checkbox management
    $(".checkbox").on('change', function() {
        if ($(this).is(':checked')) {
            $(this).attr('value', 'true');
        } else {
            $(this).attr('value', 'false');
        }
    });
	// *** AUX *** \\
    function drawTable(tableData) {
        const rowsId = "#rows";
        $(rowsId).children().remove();
        $(rowsId).append('<th>name</th>');
        $(rowsId).append("<th>line</th>");
        $(rowsId).append("<th>column</th>");
        $(rowsId).append("<th>row</th>");
        $(rowsId).append("<th>ied</th>");
        $(rowsId).append("<th>P,kW</th>");
        $(rowsId).append("<th>V</th>");
        $(rowsId).append("<th>host</th>");
        $(rowsId).append("<th>incomer</th>");
        $(rowsId).append("<th>active</th>");
        $(rowsId).append("<th>description</th>");
        $(rowsId).append("<th>Edit</th>");
    }

    // *** CLASSES *** \\
    class Device{
        constructor(name,hostAddress,line) {
            this.name=name;
            this.hostAddress=hostAddress;
            this.line=line;
        }
        id;
        name;
        hostAddress;
        line;
        drawerNum;
        drawerLetter;
        incomer;
        active;
        power;
        ied;
        description;
        switchgear;
        division;
        fromArray(arr){
            let map=new Map(arr.map((obj) => [obj.name, obj.value]));
            this.name=map.get("name");
            this.hostAddress=map.get("hostAddress");
            this.line=map.get("line");
            this.drawerNum=map.get("drawerNum");
            this.drawerLetter=map.get("drawerLetter");
            this.power=map.get("power");
            this.description=map.get("description");
            this.ied=map.get("ied");
            this.incomer=map.get("incomer");
            this.active=map.get("active");
            return map;
        }
        post(url){
            $.ajax(
                {
                    type:"POST",
                    url:url,
                    contentType: "application/json; charset=utf-8",
                    traditional: true,
                    data:this.serialize(),
                    success: function() {
                        console.log("Successfully added");
                    },
                    error: function (){
                        console.log("not added")
                    }
                });
        }
        serialize(){
          return  JSON.stringify(this);
        }
        createFromForm(form){
            let arr=$(form).serializeArray();
            $("#form input:checkbox").each(function () {
                arr.push({ name: this.name, value: this.checked });})
            this.fromArray(arr);
        }
    }


    // *** END ***
});


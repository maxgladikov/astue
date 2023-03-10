$(document).ready(function () {
// *** BEGIN ***

    // *** GLOBAL VARS ***
    let urlBase='http://localhost:8080/api/v1/devices';
    let editing;
    let adding;
    let curId;
    let swgr;
    // Drop down menu management
    $("body").on("click", ".dropdown > a", function(){
        $(this).parent().siblings().find('ul').fadeOut(500);
        $(this).next().stop(true,false,true).fadeToggle(500);
        return false;
    });
    // drop down menu fill in  substations
    createMenuHierarchy();
    // *** POPULATE THE CHART ***
    let table;
    $("body").on("click", ".switchgear", function(){
        swgr=$(this).text();
        updateTable();
    });
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
        // *** FORM VALIDATION *** \\
    $("#form").validate({
        rules:{
            name:"required"
        }, messages:{
            name:"ENTER THE NAME PIDOR"
        },
        submitHandler: function(form) {
            form.submit();
        }
    });
    // *** PUT AND POST ***
    $("body").on("click", ".submit", function(e){
        e.preventDefault();
        let device=new Device();
        device.createFromForm("#form");
        if(adding){
            // *** POST ***
            let url='http://localhost:8080/api/v1/devices/';
            device.switchgear=swgr;
            device.post(url);
            adding=false;
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
            let url='http://localhost:8080/api/v1/devices/'+curId;
            device.put(url);
            editing=false;
            // let data=$('#form').serialize();
            // $.ajax({method: "PUT",url: url,data: data,
            //     success: function(data) {
            //         console.log('everything was OK');
            //         updateTable();
            //     },
            //     error: function(jqXHR, textStatus, errorThrown) {
            //         console.log("jqXHR.status:"+jqXHR.status);
            //     }
            // })
        }
        delay(1000).then(() =>  updateTable());

    })
    // *** DELETE ***
    $("body").on("click", ".delete", function(e){
        if (confirm('sure?')) {
            let id=$(this).attr('id');
            id=id.substring(1,id.length);
            let url='http://localhost:8080/api/v1/devices/'+id;
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
    // Checkbox management
    $(".checkbox").on('change', function() {
        if ($(this).is(':checked')) {
            $(this).attr('value', 'true');
        } else {
            $(this).attr('value', 'false');
        }
    });
    // *** AUX FUNC *** \\
    function drawTable(tableData) {
        const rowsId = "#rows";
        $(rowsId).children().remove();
        $(rowsId).append("<th>name</th>>");
        $(rowsId).append("<th>line</th>>");
        $(rowsId).append("<th>drawer</th>>");
        $(rowsId).append("<th>letter</th>>");
        $(rowsId).append("<th>ied</th>>");
        $(rowsId).append("<th>P,kW</th>>");
        $(rowsId).append("<th>V</th>>");
        $(rowsId).append("<th>ip</th>>");
        $(rowsId).append("<th>incomer</th>>");
        $(rowsId).append("<th>active</th>>");
        $(rowsId).append("<th>description</th>>");
        $(rowsId).append("<th>Edit</th>>");
    }
    function updateTable(){
        $("#tableName").remove();
        if( $.fn.dataTable.isDataTable( table )){
            table.destroy() ;
        }
        let dynUrl='http://localhost:8080/api/v1/switchgears/name/'+swgr;
        $.ajax(
            {   url:dynUrl,
                method: 'GET',
                dataType: 'json',
                success: function (data) {
                    $('<div id="tableName" ><h5>'+data.name+'</h5><a class="add popup-toggle fa-thin fa-plus" href="#"></a></div>').insertBefore( '#table1');
                    drawTable(data.devices);
                    table=$('#table1').DataTable({
                        // retrieve: true,
                        data:data.devices,
                        searching: false,
                        // "paging":false,
                        // "sort":false,
                        "searching":false,
                        "iDisplayLength": 10,
                        "bPaginate": false,
                        "bInfo" : false,
                        columns:[
                            {   "data": "name",
                                // "searchable":true,
                                // "sortable":true,
                            },
                            { "data": "line",
                                // "searchable":true,
                                // "sortable":true,
                            },
                            { "data": "drawerNum",
                                // "searchable":true,
                                // "sortable":true,
                            },
                            { "data": "drawerLetter",
                                // "searchable":true,
                                // "sortable":true,
                            },
                            { "data": "ied",
                                // "searchable":true,
                                // "sortable":true,
                            },
                            { "data": "power",
                                // "searchable":true,
                                // "sortable":true,
                            },
                            { "data": "voltage",
                                // "searchable":true,
                                // "sortable":true,
                            },
                            {   "data": "hostAddress",
                                // "searchable":true,
                                // "sortable":true,
                            },
                            {   "data": "incomer",
                                // searchable: false,
                                // sortable: false,
                                // //visible: (aDurchgang==1 ? true : false),
                                // className: "text-center",
                                render: function ( data, type, row ) {
                                    return (data === true) ? '<span class="far fa-check-circle"> </span>'
                                        : '<span class="far fa-circle"></span>';}
                            },
                            {   "data": "active",
                                render: function ( data, type, row ) {
                                    return (data === true) ? '<span class="far fa-check-circle"> </span>'
                                        : '<span class="far fa-circle"></span>';}
                                // "searchable":true,
                                // "sortable":true,
                            },
                            {   "data": "description",
                            },
                            {
                                "data": null,
                                "render": function (data, type, full, meta) {
                                    return '<button id=e' + data.id + ' class="edit fas fa-edit popup-toggle"></button>' +
                                        '<span></span>'+
                                        '<button id=d' + data.id + ' class="delete transparent far fa-trash-alt"></button>'
                                        ;
                                }
                            }

                        ]
                    });
                }
            }
        )
    };
    // Fill in modal when edit
    function prepareEdit(obj){
        let id=$(obj).attr('id');
        id=id.substring(1,id.length);
        let url="http://localhost:8080/api/v1/devices/"+id;
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
    function createMenuHierarchy(){
        $.ajax({url: 'http://localhost:8080/api/v1/substations/',
            method: 'GET',
            //dataType: 'json',
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
            //dataType: "jsonp"
        })
    }
    function delay(time) {
        return new Promise(resolve => setTimeout(resolve, time));
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
                    error: function (jqXHR, textStatus, errorThrown){
                        console.log("not added");
                        console.log("jqXHR.status:"+jqXHR.status);
                        console.log("jqXHR.responseText:"+jqXHR.responseText);
                        alert(jqXHR.responseText);
                        // console.log("textStatus:"+textStatus);
                        // let err = eval("(" + jqXHR.responseText + ")");
                        // alert(err.Message);
                    }
                });
        }
        put(url){
            let jqxhr= $.ajax(
                {
                    type:"PUT",
                    url:url,
                    contentType: "application/json; charset=utf-8",
                    traditional: true,
                    data:this.serialize(),
                    success: function() {
                        console.log("Successfully put");
                    },
                    error: function (jqXHR, textStatus, errorThrown){
                        console.log("haven't been put")
                        console.log("jqXHR.status:"+jqXHR.status);
                        console.log("jqXHR.text:"+jqXHR.responseText);
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
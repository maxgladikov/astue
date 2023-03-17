$(document).ready(function () {
// *** BEGIN ***

    // *** GLOBAL VARS ***
    let pre="http://";
    let ip="localhost";
    //let ip=ip+"192.168.56.1";
    ip=pre+ip;
    let port="8080";
    let address=ip+":"+port+"/api/v1";
    let urlDevices=address+'/devices';
    let urlDevicesId=address+'/devices/';
    let urlSS=address+'/substations';
    let urlPlants=address+'/plants';
    let urlSwgrName=address+'/switchgears/name/';
    let urlDivisionName=address+'/divisions/name/';
    let urlIed=urlDevices+'/ied';
    let modalMode;
    let divisionMode=false;
    let substationMode=false;
    let curId;
    let swgr;
    let ssActive;
    let plantActive;
    let jwt;
    token=localStorage.getItem("token");
    
    // *** AJAX SET UP ***\\
    $.ajaxSetup({
					  dataType: 'json',
					  contentType: "application/json",
					  headers: {"Authorization":'Bearer '+token}
					});
    // *** DROPDOWN ***\\
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
	});
    // drop down menu fill in  substations
    $.ajax({url: urlSS, method: 'GET'})
    	.done( (data)=> {
			                $.each(data, function (key, value) {
			                    let str='<li class="dropdown"><a class="ss" href="#">'+value.name+'</a>';
			                    str=str+"<ul>";
			                    value.switchgears.forEach(element => str=str+"<li><a class='switchgear' href='#'>"+element.name+"</a></li>");
			                    str=str+"</ul></li>";
			                    $('#substations').append(str);
           						})
			}).fail((jqXHR, textStatus, errorThrown) =>{print("fail")});
    // drop down menu fill in  plants
    $.ajax({url: urlPlants,method: 'GET',
            success:function(data) {
                $.each(data, function (key, value) {
                    let str='<li class="dropdown"><a class="plant" href="#">'+value.name+'</a>';
                    str=str+"<ul>";
                    value.divisions.forEach(element => str=str+"<li><a class='division' href='#'>"+element.name+"</a></li>");
                    str=str+"</ul></li>";
                    $('#plants').append(str);
                })
            },
            error: function(jqXHR, textStatus, errorThrown) {
               print(jqXHR.status);
            }
        })
	// *** AUTH ***\\
	  $.ajax({url: address+"/auth/username", method: 'GET',headers: {}
	  }).done( (data)=> {	$("#user").text(data.name);					
			}).fail((jqXHR, textStatus, errorThrown) =>{print("fail")});
	
	// *** MAIN ***\\
	
    	// *** populate the table ***
    $("body").on("click", ".switchgear", function(){
		ssActive=true;
		plantActive=false;
		let table=$("#table");
        swgr=$(this).text();
        updateTable();
    });
    $("body").on("click", ".division", function(){
		ssActive=false;
		plantActive=true;
		let table=$("#table");
        division=$(this).text();
        updateTable();
    });
       
  
    	// Checkbox management
    $(".checkbox").on('change', function() {
        if ($(this).is(':checked')) {
            $(this).attr('value', 'true');
        } else {
            $(this).attr('value', 'false');
        }
    });
    
    	// *** delete ***
    $("body").on("click", ".delete", function(e){
        if (confirm('sure?')) {
            let id=$(this).attr('id');
            id=id.substring(1,id.length);
            let url=urlDevicesId+id;
            $.ajax({url: url, type: 'DELETE'}).then((data, textStatus, jqXHR)=>{print(jqXHR.responseText);updateTable()})
            											.catch((jqXHR, textStatus, errorThrown)=>{print(jqXHR.responseText)});
        };
    });
    
    	// *** modal *** \\
    $("body").on("click", ".modal-toggle", function(e){
        e.preventDefault();
        if($('.modal').css('display')=="none"){
            if($(this).hasClass('edit')){
				let id=$(this).attr('id');
        		id=id.substring(1,id.length);
        		curId=id;
                modalMode=1;
               drawForm(modalMode);
               Promise.all([$.get(urlIed),$.get(urlSS),$.get(urlPlants),$.get(urlDevices+"/"+id)])
               															.then((data)=>{ return new Promise((resolve,reject)=>{
																					   											appendOptions(data);
																					   											resolve(data[3]);})})
						   																															.then((data)=>{prePopulateEditForm(data)});
            }else if ($(this).hasClass('add')){
                modalMode=2;
                drawForm(modalMode);
                Promise.all([$.get(urlIed),$.get(urlSS),$.get(urlPlants)])
               															.then((data)=>{appendOptions(data);});
            }else if ($(this).hasClass('login')){
                modalMode=4;
                drawForm(modalMode);
            }else if ($(this).hasClass('report')){
                modalMode=5;
                prepareReport();
            }
            $('.modal').fadeIn( 200, "swing" );
            $('.modal').draggable();
            return;
        }
        if($(this).hasClass('close') || $(this).hasClass('submit')){
                
                
	        if($(this).hasClass('submit')){
					   e.preventDefault();
		        // *** post *** \\
		        if(modalMode==2){
		            let url=urlDevices+"/";
		            let device=new Device();
		            device.createFromForm("#form");
		            device.switchgear=swgr;
		            device.post(url).then((data, textStatus, jqXHR)=>{print(jqXHR.responseText);updateTable()}).catch((jqXHR, textStatus, errorThrown)=>{print(jqXHR.responseText)});
		             // *** put *** \\
		        } else if(modalMode==1){
		            let url=urlDevicesId+curId;
		             let device=new Device();
		            device.createFromForm("#form");
		            let request=device.put(url);
		            request.done((data, textStatus, jqXHR)=>{print(jqXHR.responseText);updateTable()}).fail((jqXHR, textStatus, errorThrown)=>{print(jqXHR.responseText)});
		        }else if(modalMode==4){
					let xhr=new XMLHttpRequest();
					xhr.open("POST", address+"/auth/authenticate", true);
					xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
					print(xhr.getAllResponseHeaders());
					xhr.send(AuthReq.createFromForm($("#form")).serialize());
				    xhr.onreadystatechange = function() {
						  if (xhr.readyState != 4 && xhr.status === 200) {
						    return
						  }
						 jwt=JSON.parse(xhr.responseText);localStorage.setItem("token", jwt.token);
						}
					//xhrobj.onreadystatechange=()=>{
						//if (this.readyState == 4 && this.status == 200) {
						//print(xhrobj.responseText)}
						//};
				//	$.ajax({  url: address+"/auth/authenticate",
			   		//		  type: 'POST',
						//      data: AuthReq.createFromForm($("#form")).serialize(),
						  //    beforeSend: function(request) { request.setRequestHeader('Accept', 'application/json');}
			  				//														}).then((data, textStatus, jqXHR)=>{jwt=JSON.parse(jqXHR.responseText);localStorage.setItem("token", jwt.token);});
				}
	        }
         modalMode=0;
         drawForm(modalMode);
			
		}
        $('.modal').fadeOut( 200, "swing" );
    });
    
	
	// *** AUX *** \\
	
	 function updateTable(){
            $("#tableName").remove();
          if( $.fn.dataTable.isDataTable( table )){
                table.destroy() ;}
                let dynUrl;
                if(ssActive){
            		dynUrl=urlSwgrName+swgr;
            }else if(plantActive){
					dynUrl=urlDivisionName+division;;
			}
            $.ajax({url:dynUrl, method: 'GET',
                    success: function (data) {
                        $('<div id="tableName" ><h5>'+data.name+'</h5><a class="add modal-toggle fa-thin fa-plus" href="#"></a></div>').insertBefore( $("#table"));
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
                                { "data": "drawerColumn",
                                   "width": "5%"
                                },
                                { "data": "drawerRow",
                                  "width": "5%"
                                },
                                { "data": "ied",
                                  "width": "5%"
                                },
                                { "data": "power"},
                                { "data": "voltage"},
                                {   "data": "hostAddress"},
                                {   "data": plantActive?"switchgear":"division"},
                                {   "data": "incomer",
                                	"className": "text-center",
                                    // //visible: (aDurchgang==1 ? true : false),
                                    // className: "text-center",
                                    render: function ( data, type, row ) {
                                        return (data === true) ? '<span class="fa-regular fa-circle-check"></span>'
                                            : '<i class="fa-regular fa-circle"></i>';}
                                },
                                {   "data": "consumer",
                                	"className": "text-center",
                                    render: function ( data, type, row ) {
                                        return (data === true) ? '<span class="fa-regular fa-circle-check"></span>'
                                            : '<i class="fa-regular fa-circle"></i>';}
                                },
                                {   "data": "description",},
                                {
                                    "data": null,
                                    "render": function (data, type, full, meta) {
                                        return '<div> <button id=e' + data.id + ' class="edit transparent far fa-pen-to-square modal-toggle" href="#"></button>' +
                                            '<button id=d' + data.id + ' class="delete  transparent far fa-trash-can" href="#"></button> </div>';
                                    }
                                }

                            ]
                        });
                    }
                }
            )
        };

	
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
        $(rowsId).append(plantActive?"<th>switchgear</th>":"<th>division</th>");
        $(rowsId).append("<th>incomer</th>");
        $(rowsId).append("<th>active</th>");
        $(rowsId).append("<th>description</th>");
        $(rowsId).append("<th>Edit</th>");
    }
      function drawForm(mode){
		  return new Promise( (resolve,fail)=>{
		  		  switch(mode){
						case 0: 
								$('.modal-input').children().remove();
								break;
						case 1:
						case 2:
								$('.modal-input').append('<input id="id" type="hidden" name="id"></input>');
								$('.modal-input').append('<div class="row"><input id="name" type="text" name="name" placeholder="name"></input><label class="label">name</label></div>');
								$('.modal-input').append('<div class="row"><input id="line" type="text" name="line" placeholder="line"></input><label class="label">line</label></div>');
								$('.modal-input').append('<div class="row"><input id="drawerColumn" type="text" name="drawerColumn" placeholder="drawerColumn"></input><label class="label">column</label></div>');
								$('.modal-input').append('<div class="row"><input id="drawerRow" type="text" name="drawerRow" placeholder="drawerRow"></input><label class="label">row</label></div>');
								$('.modal-input').append('<div class="row"><input id="power" type="text" name="power" placeholder="power"></input><label class="label">power</label></div>');
								$('.modal-input').append('<div class="row"><input id="hostAddress" type="text" name="hostAddress" placeholder="ip"></input><label class="label">ip</label></div>');
								$('.modal-input').append('<div class="row"><input id="incomer" type="checkbox" name="incomer" placeholder="incomer"></input><label class="label">incomer</label></div>');
								$('.modal-input').append('<div class="row"><input id="consumer" type="checkbox" name="consumer" placeholder="consumer"></input><label class="label">consumer</label></div>');
								$('.modal-input').append('<div class="row"><select id="ied" name="ied" ></select><label class="label">ied</label></div>');
								$('.modal-input').append('<div class="row"><select id="voltage" name="voltage" ></select><label class="label">voltage</label></div>');
								$('#voltage').append('<option>M</option>');
								$('#voltage').append('<option>L</option>');
								$('.modal-input').append('<div class="row"><select id="switchgearSelect"  name="switchgear"></select><label class="label">switchgear</label></div>');
								$('.modal-input').append('<div class="row"><select id="divisionSelect" name="division"></select><label class="label">division</label></div>');
								$('.modal-input').append('<div class="row"><input id="description" type="text" name="description" placeholder="description"></input><label class="label">description</label></div>');
						break;
						case 3:break;
						case 4:
								$('.modal-input').append('<div class="row"><input id="name" type="text" name="name" placeholder="name"></input><label class="label">name</label></div>');
								$('.modal-input').append('<div class="row"><input id="password" type="password" name="password" placeholder="password"></input><label class="label">password</label></div>');
								
							break;
						case 5:break;
						default:
					}
					resolve();
					})
	  }
	  
	  function appendOptions(data){
											    $(data[0]).each(function(key, value){
													$('#ied').append('<option>'+value+'</option>');
												});
												 $(data[1]).each(function(key, value){
													let name=value.name;
													name="oGroup"+name;
													$('#switchgearSelect').append('<optgroup id="' +name+'" label="'+value.name+'">');
													$(value.switchgears).each(function(key,value){
														$('#'+name).append( '<option value="'+value.name+'">'+value.name+'</option>');
													})
												});
												  $(data[2]).each(function(key, value){
													let name=value.name;
													name="oGroup"+name;
													$('#divisionSelect').append('<optgroup id="' +name+'" label="'+value.name+'">');
													$(value.divisions).each(function(key,value){
														$('#'+name).append( '<option value="'+value.name+'">'+value.name+'</option>');
													})
												});
	  };
	   
      function prePopulateEditForm(data){
					let form=$('.modal-input');
                    form.find("#id").val(data.id);
                    form.find("#name").val(data.name);
                    form.find("#line").val(data.line);
                    form.find("#drawerColumn").val(data.drawerColumn);
                    form.find("#drawerRow").val(data.drawerRow);
                    form.find("#power").val(data.power);
                    form.find("#hostAddress").val(data.hostAddress);
                    form.find("#description").val(data.description);
                    form.find("#ied").val(data.ied);
                    form.find("#switchgearSelect").val(data.switchgear);
                    form.find("#divisionSelect").val(data.division);
                    form.find("#voltage").val(data.voltage);
                    form.find("#incomer").prop('checked', data.incomer);
                    form.find("#consumer").prop('checked', data.consumer);
                    form.find("#incomer").val(data.incomer==true?'true':'false');
                    form.find("#consumer").val(data.consumer==true?'true':'false');
       
    };
    
	function print(arg){console.log(arg);}
	
	function delay(time) { return new Promise(resolve => setTimeout(resolve, time));}



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
        drawerColumn;
        drawerRow;
        incomer;
        consumer;
        power;
        ied;
        description;
        switchgear;
        division;
        fromArray(arr){
            let map=new Map(arr.map((obj) => [obj.name, obj.value]));
            this.id=Number.parseInt(map.get("id"));
            this.name=map.get("name");
            this.hostAddress=map.get("hostAddress");
            this.line=map.get("line");
            this.drawerColumn=Number.parseInt(map.get("drawerColumn"));
            this.drawerRow=map.get("drawerRow");
            this.power=Number.parseFloat( map.get("power"));
            this.description=map.get("description");
            this.ied=map.get("ied");
            this.incomer=map.get("incomer");
            this.voltage=map.get("voltage");
            this.consumer=map.get("consumer");
            this.division=map.get("division");
            this.switchgear=map.get("switchgear");
            return map;
        }
        post(url){
           return 	$.ajax({  url: url,
			   				  type: 'POST',
						      data: this.serialize(),
			  })
			};
        put(url){
			return 	$.ajax({ type: 'PUT',
						      url: url,
						      data: this.serialize()
			    										});
			};
        
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
    
    class AuthReq{
		name;
		password;
		constructor(name,password){
			this.name=name;
			this.password=password
		} 
		static createFromForm(form){
            let arr=$(form).serializeArray();
             let map=new Map(arr.map((obj) => [obj.name, obj.value]));
             let auth=new AuthReq();
            auth.password=map.get("password");
            auth.name=map.get("name");
            return auth;
        }
         serialize(){
          return  JSON.stringify(this);
        }
	}


    // *** END ***
})

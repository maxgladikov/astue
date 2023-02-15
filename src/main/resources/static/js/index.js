$(document).ready(function(){

// Drop down menu management
	$("body").on("click", ".dropdown > a", function(){
		$(this).parent().siblings().find('ul').fadeOut(500);
		//$(this).children().fadeIn(500);
		$(this).next().stop(true,false,true).fadeToggle(500);
		return false;
	});


// drop down menu fill in  substations
	$.ajax(
		{
			url: 'http://localhost:8080/api/v1/substations/',
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


// dynamic chart
	let table;
	$("body").on("click", ".switchgear", function(){
		if( $.fn.dataTable.isDataTable( table )){
			table.destroy() ;
			$("#table1").children().remove();
		}
		// Read data
	let swgr=$(this).text();
	let dynUrl='http://localhost:8080/api/v1/switchgears/name/'+swgr;
	$.ajax(
		{
			url:dynUrl,
			method: 'GET',
			dataType: 'json',
			success: function (data) {
				drawTable(data.devices);
				table=$('#table1').DataTable({
					// retrieve: true,
					data:data.devices,
					searching: false,
					// "paging":false,
					// "sort":false,
					// "searching":false,
					columns:[
						{   "data": "name",
							// "searchable":true,
							// "sortable":true,
						},
						{   "data": "hostAddress",
							// "searchable":true,
							// "sortable":true,
						},
						{   "data": "ied",
							// "searchable":true,
							// "sortable":true,
						}

					]
				});
			}
		}
	)
		// console.log(response.switchgears);
	});

})

// *************** Aux functions ******************** \\

// function getData(source) {
// 	let result=null;
// 	$.ajax(
// 		{
// 			url: source,
// 			method: 'GET',
// 			//dataType: 'json',
// 			success:function(data) {
// 				result=data;
// 			},
// 			error: function(jqXHR, textStatus, errorThrown) {
// 				console.log(jqXHR.status);
// 			}
// 		})
// 	return result;
// }

function drawTable(tableData) {
	const tableId="table1";
	const rowsId="#rows";
	$(rowsId).children().remove();
	$(rowsId).append("<th>name</th>>");
	$(rowsId).append("<th>host</th>>");
	$(rowsId).append("<th>ied</th>>");
	$.each(tableData, function (key, value) {
		// let row="<th>"+value.name+"</th>>";
		//let row="<th>"+key+"</th>>";

	})
}
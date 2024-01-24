
$(document).ready(function() {
    $("#btnExport").click(function(e) {
        //getting values of current time for generating the file name
    	var textTitle = $('.excel-title').html();
    	var dt = new Date();
        var day = dt.getDate();
        var month = dt.getMonth() + 1;
        var year = dt.getFullYear();
        var hour = dt.getHours();
        var mins = dt.getMinutes();
        var postfix = day + "-" + month + "-" + year;
        //creating a temporary HTML link element (they support setting file names)
        var a = document.createElement('a');
        //getting data from our div that contains the HTML table
        var data_type = 'data:application/vnd.ms-excel';
        var table_div = document.getElementById('export-excel');
        var table_html = table_div.outerHTML.replace(/ /g, '%20');
        a.href = data_type + ', ' + table_html;
        //setting the file name
        a.download = textTitle + " "+postfix + '.xls';
        //triggering the function
        a.click();
        //just in case, prevent default behaviour
        e.preventDefault();
    });
});


function fnExcelReport(idname)
{
   var tab_text="<table border='2px'><tr>";
    var textRange; var j=0;
    var textTitle = $('#tlExcel').html();
	var dt = new Date();
    var day = dt.getDate();
    var month = dt.getMonth() + 1;
    var year = dt.getFullYear();
    var hour = dt.getHours();
    var mins = dt.getMinutes();
    var postfix = day + "-" + month + "-" + year;
    var fileName = textTitle + " "+postfix;
   tab = document.getElementById(idname); // id of table
   if(tab==null || tab==""){
	   tab=document.getElementsByClassName(idname);
	   for(var i=0; i <tab.length; i++){
		   for(j = 0 ; j < tab[i].rows.length ; j++) 
		    {     
		        tab_text=tab_text+tab[i].rows[j].innerHTML+"</tr>";
		        //tab_text=tab_text+"</tr>";
		    } 
	   }
   }else{
	  
	   
	    for(j = 0 ; j < tab.rows.length ; j++) 
		    {     
		        tab_text=tab_text+tab.rows[j].innerHTML+"</tr>";
		        //tab_text=tab_text+"</tr>";
		    }
   }
   
	
	    tab_text=tab_text+"</table>";
  
    
    tab_text= tab_text.replace(/<A[^>]*>|<\/A>/g, "");//remove if u want links in your table
    tab_text= tab_text.replace(/<img[^>]*>/gi,""); // remove if u want images in your table
    tab_text= tab_text.replace(/<input[^>]*>|<\/input>/gi, ""); // reomves input params
    var a = document.createElement('a');
    var data_type = 'data:application/vnd.ms-excel';
    a.href = data_type + ', ' + encodeURIComponent(tab_text);
    a.download = fileName + '.xls';
    a.click();
    
    
    /*other browser not tested on IE 11
        sa = window.open('data:application/vnd.ms-excel,' + encodeURIComponent(tab_text));  
        sa.document.title = fileName;

   return (sa);*/
}
/***********  Export to Excel for Finance and Accounts Reports *************/
$(document).ready(function() {
	$('#importexcel tr th[data-sorter="false"]').css('background-image','none');
    $("#btnExport1").click(function(e) {
    	fnExcelReport('importexcel');
    });
   
});
/***********  End of Export to Excel for Finance and Accounts Reports *************/

/******* Export to Excel for Care Reports  ******************/
$(document).ready(function() {
    $("#btnExportbyclass").click(function(e) {
    	
    	    	fnExcelReport('care-exl-export');
    });
   
});





/******* End of Export to Excel for Care Reports ******************/



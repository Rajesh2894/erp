var url	=	'CitizenDashBoard.html';
$(document).ready(function(){
	  var userType=$('#userType').val();
	  var result=JSON.parse(__doAjaxRequest(url+'?chart','post','type='+userType+'',false,'json'));

	    Highcharts.setOptions({
	    	  colors: ['#5FD52C','#FF0000']
	    	});
	    browserData = [];
	    for (var i = 0; i < result.length; i++) {
			browserData.push({
	            name: result[i].name,
	            y: result[i].y,
	         }); 
		}
		
	   $('#container').highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
	        },
	        title: {
	        	 text: ' '
	        },
	        tooltip: {
	        	formatter: function() {
	        		return  '<b>'+'<span>'+ this.point.name +'</span><br/>' + getLocalMessage('citizen.dashboard.count') +':</b> '+this.point.y;
	            }
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: false
	                },
	                showInLegend: true,
	                point: {
	                    events: {
	                        click: function() {
	                        	     $("ul#tabs li.active").removeClass("active");
	                                 $("ul#tab li.active").removeClass("active");
	                                 var tabsId=((this).statusId).split(" ").join("");
	                                 $("#"+tabsId).addClass('active');
	                                 var tabId=((this).statusId).slice(0, 2);
	                                 $("#"+tabId).addClass('active');
	                        	},
	                    }
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: '#',
	            data:result
	        }]
	    });
	   
	
});


function dashboardView(appId,orgId,serviceId,shortName,flag){
	var divName	=	formDivName;
    var requestData = 'appId='+appId+'&orgId='+orgId+'&serviceId='+serviceId+'&shortName='+shortName+'&flag='+flag;
	
	var ajaxResponse	=	doAjaxLoading(url+'?viewFormDetails', requestData, 'html', divName);
	
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	
	
	$(divName).show();
	
	
}
function downloadDocs(appId,orgId,serviceId)
{
	 
	 var requestData = 'appId='+appId+'&orgId='+orgId+'&serviceId='+serviceId;
     
    var data='';
	var lookUpList	=__doAjaxRequest(url+'?docDownload','POST', requestData, false,'json');
	if( lookUpList != ''){
				data='<div class="overflow_auto"><table class="gridtable paginated"><thead><tr><th scope="col" width="8%">Sr No</th><th scope="col" width="20%">Document Type</th><th scope="col" width="70%">FileName</th></tr></thead>';
				$.each(lookUpList, function(index){
					var lookUp = lookUpList[index];
					var filePath=lookUp.descLangFirst;
					var value=filePath.replace(/\\/g,"\\\\");
					data+="<tr><td>"+ ++index +"</td><td>"+lookUp.lookUpType+"</td><td><div class='fa fa-download'><a href='javascript:void(0);' onclick=\"downloadFinalFile('"+value+"','CitizenDashBoard.html?Download')\">"+lookUp.descLangSecond+"</a></div></td></tr>";
				});
	}else{
	    data ="No files are available for this application.";
	}
	var childPoppup = '.popup-dialog';
	$(childPoppup).addClass('login-dialog');
	$(childPoppup).html(data);
	$(childPoppup).show();
	showModalBox(childPoppup);
		
}

function downloadFinalFile(filePath,action)
{
	var my_form=document.createElement('FORM');
	my_form.name='myForm';
	my_form.method='POST';
	my_form.action=action;
	my_form.target='_blank';
	
	var	my_tb=document.createElement('INPUT');
	my_tb.type='HIDDEN';
	my_tb.name='downloadLink';
	my_tb.value=filePath;
	my_form.appendChild(my_tb); 
	
	document.body.appendChild(my_form);
	my_form.submit();
}

function downloadDashboardFile(srNo,fileName,modelName){
	var my_form=document.createElement('FORM');
	my_form.name='myForm';
	my_form.method='POST';
	my_form.action='CitizenDashBoard.html?downloading';
	my_form.target='_blank';

	
	var	my_tb=document.createElement('INPUT');
	my_tb.type='HIDDEN';
	my_tb.name='srNo';
	my_tb.value=srNo;
	my_form.appendChild(my_tb);

	var	my_tb=document.createElement('INPUT');
	my_tb.type='HIDDEN';
	my_tb.name='fileName';
	my_tb.value=fileName;
	my_form.appendChild(my_tb);

	var	my_tb=document.createElement('INPUT');
	my_tb.type='HIDDEN';
	my_tb.name='modelName';
	my_tb.value=modelName;
	my_form.appendChild(my_tb);
	
	document.body.appendChild(my_form);
	my_form.submit();
	
}

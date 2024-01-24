 

$(document).ready(function(){

	 $('.hasNumber').blur(function () { 
		    this.value = this.value.replace(/[^0-9]/g,'');
	  });
	 $('.alfaNumric').blur(function () { 
		    this.value = this.value.replace(/[^a-zA-Z0-9 ]/g,'');;
	  });
            	$("#customFields").on('click','.addCF',function(i){
             		var row=0;
            		var errorList = [];
            		errorList = validateFlowMapping(errorList);
            			if (errorList.length == 0 ) {
            				var content = $(this).closest('tr').clone();
            				$(this).closest("tr").after(content);
            				var clickedIndex = $(this).parent().parent().index() - 1;	
            				content.find("input:text").val('');
            				content.find("input:hidden").val('');
            				content.find("select").val('0');
            	            content.find(".multiselect-container").empty();
            	            content.find(".multiselect-selected-text").text("None selected");
            				$('.error-div').hide();
            				reOrderTableIdSequence();
            				$("#mapOrgId_"+(clickedIndex+1)).val($("#onOrgSelect").val());
            				if($("#complaintId :selected").attr('code')>0){
            					$('#mapDeptId_'+(clickedIndex+1)).val($("#complaintId :selected").attr('code'));
            				}else{
            					$('#mapDeptId_'+(clickedIndex+1)).val($("#departmentId").val());
            				}
            			}else {
            				displayErrorsOnPageView(errorList);
            			}
            });
            	
            $("#customFields").on('click','.remCF',function(){
            	
            	if($("#customFields tr").length != 2)
            		{
            			 $(this).parent().parent().remove();
            			 reOrderTableIdSequence();
            		}
               else{
            	      var errorList = [];
            	      errorList.push(getLocalMessage("rl.subChild.deletrw.validtn"));   
            	      displayErrorsOnPageView(errorList);
            		}
             });
            
            });
 
<%@ taglib tagdir="/WEB-INF/tags" prefix="apptags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>.modal-backdrop.in{opacity: 0;}</style>
<script>
$('.hasOneDecimal').keyup(function () {
	var regx	=   /^\d+(\.\d{0,2})?$/;
	var amount	=	$(this).val();
	if(!regx.test(amount))
	{
		amount = amount.substring(0, amount.length-1);
		$(this).val(amount);	
	}   
});

	$('.required-control').next().children().addClass('mandColorClass');

	$("#subLinkNameEn").focusout(function() {
		var sublinkname = $.trim($('#subLinkNameEn').val());
		$("#subLinkNameEn").val(sublinkname);

		jQuery('.hasNotAllowSpecialLangVal').blur(function() {
			this.value = this.value.replace(/[^a-zA-Z0-9. _-]/g, '');

		});

	});
</script>
<script>
$(document).ready(function(){

	var pathname  = document.URL ;
	var txt ="AdminFaqCheker";
	var txt2 =$("#isCheckerFlag").val();
	if(pathname.indexOf(txt) > -1 || txt2=="Y") {
	
		/* $("#AdminFaqCheker").show() */
		$("#isCheckerFlag").val('Y');
		$(".remark").prop("disabled",false);
		$("#chekkerflag1").val("Y");
		$(".AdminFaqbutton").hide();
		$(".AdminFaqChekerbutton").show();
		$("#AdminFaqChekerback").show();
		$("#AdminFaqback").hide();
		$("#Rejction").hide();
		
		$(".radiobutton").click(function() {
			 if( $(this).is(":checked") ){ 
		            var val = $(this).val(); 
		            if( val =='Y')
		            	{
		            	  $("#Rejction").hide();
		            	  $("#Rejction").val('');
		            	}
		            else
		            	{
		            	 $("#Rejction").show();
		            	}
		            
		         
		        };
	    });
		
	/* 	if($("input:radio:checked").val()=='Y')
         {
			$("#AdminFaqCheker").show();
			
         }
		
		if($("input:radio:checked").val()=='N')
			{
			$("#AdminFaqCheker").show();
			
			} */
			}
	   else {
		
			/* $("#AdminFaqCheker").hide() */
			$(".remark").prop("disabled",true);
			$("#chekkerflag1").val("N");
			$(".AdminFaqbutton").show();
			$(".AdminFaqChekerbutton").hide();
			$("#AdminFaqChekerback").hide();
			$("#AdminFaqback").show();
			 $(".radiobutton").prop('disabled',true);
			 $(".radiobutton").removeClass('mandClassColor');
			 $(".radiobutton").addClass('disablefield');
			 $("#checkerFlag1").val("N");
		/* 	if($('input[class=radiobutton]:checked').val()=='Y')
	         {
			
				$("#AdminFaqCheker").show();
				 $(".radiobutton").prop('disabled',true);
				 $(".radiobutton").removeClass('mandClassColor');
				 $(".radiobutton").addClass('disablefield');
	         }
			
			if($('input[class=radiobutton]:checked').val()=='N')
				{
			
				$("#AdminFaqCheker").show();
				 $(".radiobutton").prop('disabled',true);
				 $(".radiobutton").removeClass('mandClassColor');
				 $(".radiobutton").addClass('disablefield');
				} */
		}
});
function getFunction(parent)
{
	var theForm	=	'';
	theForm	=	'#'+findClosestElementId(parent,'form');
	
	var data = __serializeForm(theForm);
	
	var url	=	'SectionEntryForm.html'+'?function';
	$('#id').empty();
	var lookUpList=__doAjaxRequest(url,'post',data,false,'json');
	
	var  optionsAsString="<option value= 0> Select Option </option>"
    
	for(var i = 0; i < lookUpList.length; i++)
	{
	
	    optionsAsString += "<option value='" + lookUpList[i].lookUpId + "' code='"+ lookUpList[i].lookUpId+"-"+ lookUpList[i].baAccountname +"'>" + lookUpList[i].descLangFirst+"</option>";
	}
	$("#id").append( optionsAsString );


	
}

	
$(function() {
	$("#SectionEntryForm").validate();
});
</script>
<SCRIPT>
	$(document)
			.ready(
					function() {

						$("#gridtable")
								.on('click','.addChargesLinkCollection',
										function(e) {

											var content = $(this).closest('tr')
													.clone();
										
											$("#gridtable").append(content);
											content.find("select").val('0');
											reOrderChangeIdSequence('.total');	
										}); 
						$("#gridtable").on('click','.remCF',function(){
							if($("#gridtable tr").length !=2) {
								var tr = $(this).closest("tr");
							    tr.remove();
							    reOrderChangeIdSequence('.total');
								
							} else {
								var errorList = [];
								errorList.push("First row cannot be remove.");
								$("#errorId").html(errorList);
								$("#errorDivId").show();
							}
						});

					});
				

	function reOrderChangeIdSequence(total)
	{
		$(total).each(function(i) {
			$(this).find("select:eq(0)").attr("id", "sectionType"+i);
			$(this).find("select:eq(0)").attr("name","entity.sectionType"+i);
		});
	}
	
	function sectionType(index)
	{
		var errorList=[];
		var i = $('#gridtable tr').length;
		var m=0;
		if(i>2){
		$('.total').each(function() {
				if(m!=index){
				if (($('#sectionType' + index).val() == $('#sectionType' + m).val())) {
					$('#sectionType' + index).val(0);
					errorList.push("Section Type already selected");
				}
				}
				m++;
		});
		}
		if (errorList.length > 0) {

			$("#errorId").html(errorList);

			$(".error-div").show();

			errorList = [];

			return false;
		}
		else{
			$("#errorId").html("");
			$(".error-div").hide();
		}
	}
	
	
	function closeOutErrBox() {
		$('.error-div').hide();
	}
	
	$(function(){
		 
		$('.hasSplChar').keyup(function()
		{
			var yourInput = $(this).val();			
			re = /[`~!@#$%^&*_|+\=?;:'",.<>\{\}\[\]\\\/]/gi;
			var isSplChar = re.test(yourInput);
			if(isSplChar)
			{
				var no_spl_char = yourInput.replace(re, '');
				$(this).val(no_spl_char);
			}
		});
	 
	});
	
	$('select.form-control.mandColorClass').on('blur',function(){
	     var check = $(this).val();
	     var validMsg =$(this).attr("data-msg-required");
	     if(check == '' || check == '0' || check == "null"){
	    		 $(this).parent().switchClass("has-success","has-error");
			     $(this).addClass("shake animated");
			     $('#error_msg').addClass('error');
			     $('#error_msg').css('display','block');
			     $('#error_msg').html(validMsg);}
	     else
	     {$(this).parent().switchClass("has-error","has-success");
	     $(this).removeClass("shake animated");
	     $('#error_msg').css('display','none');}
});	
	function saveOrUpdateFormSubMenu(obj, message, undefined, actionParam)
	{
		var errorList = [];
		var pathname  = document.URL ;
		var txt ="AdminFaqCheker";
		var txt2 =$("#isCheckerFlag").val();
		if( txt2=="Y") {
			if ($('.remark').val()==null || $('.remark').val()==""){
				 errorList.push(getLocalMessage('eip.admin.validatn.cheker'));   
				 $('#error_msg_cheker').addClass('error');
			     $('#error_msg_cheker').css('display','block');
			     $('#error_msg_cheker').html(getLocalMessage('eip.admin.validatn.cheker'));
			     return;
				}
		}		
		
		if (errorList.length == 0){
			return saveOrUpdateForm(obj,message,undefined,actionParam);
		}else{
			return errorList;
			}
		}	
	
	$('textarea.form-control.remark').on('blur', function() { 
		   $(this).parent().switchClass("has-error","has-success");
		   $(this).removeClass("shake animated");
		   $('#error_msg_cheker').css('display','none');
		   $('#error_msg_cheker').remove();
		     
		});	
	
</SCRIPT>
<%-- 
Allowed only  () and -  in $('.hasSplChar').keyup(function() for defect #40022
below is original code
re = /[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/]/gi; 
var no_spl_char = yourInput.replace(/[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/]/gi, '');
--%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content animated">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="eip.addlink" text="Sub Menu" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="MandatoryMsg" /></span>
			</div>
			<form:form action="SectionEntryForm.html" method="post" class="form-horizontal" id="SectionEntryForm">
			<div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
		<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close">
		<span aria-hidden="true">&times;</span>
		</button>
		<span id="errorId"></span>
		</div>
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="isChecker" id="isCheckerFlag"/>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control" for="linkId"><spring:message
							code="SectionEntryFormModel.moduleName"
							text="SectionEntryFormModel.moduleName" /></label>
					<apptags:lookupField items="${command.modules}"
						path="entity.linksMaster.linkId"
						selectOptionLabelCode="eip.selectModule" hasId="true"
						cssClass="form-control"
						changeHandler="getFunction(this)" />

					<label class="control-label col-sm-2" for="id"><spring:message
							code="SectionEntryFormModel.functionName"
							text="SectionEntryFormModel.functionName" /></label>
					<apptags:lookupField items="${command.functions}"
						path="entity.subLinkMaster.id"
						selectOptionLabelCode="eip.function.name" hasId="true"
						cssClass="form-control" />
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2" for="subLinkNameEn"><spring:message
							code="SectionEntryFormModel.sectionNameEn"
							text="SectionEntryFormModel.sectionNameEn" /><span class="mand">*</span></label>
					<div class="col-sm-4">
					        <c:set var="SectionNameEnglish" value="${command.getAppSession().getMessage('eip.admin.SectionEntryFormModel.SectionNameEnglish') }" />
                            <form:input id="subLinkNameEn" path="entity.subLinkNameEn" 
							cssClass=" form-control hasSplChar"  data-rule-required="true" data-msg-required="${SectionNameEnglish}"/>	
					</div>
					<label class="control-label col-sm-2" for="entity.subLinkNameRg"><spring:message
							code="SectionEntryFormModel.sectionNameRg"
							text="SectionEntryFormModel.sectionNameRg" /><span class="mand">*</span></label>
					<div class="col-sm-4">
					<c:set var="SectionNameRegional" value="${command.getAppSession().getMessage('eip.admin.SectionEntryFormModel.SectionNameRegional') }" />
				   <form:input id="subLinkNameRg" path="entity.subLinkNameRg" 
					cssClass=" form-control hasSplChar"  data-rule-required="true" data-msg-required="${SectionNameRegional}"/>
					</div>
				</div>

				<div class="form-group">
				<!--  	<label class="control-label col-sm-2" for="entity.imageLinkType"><spring:message
							code="SectionEntryFormModel.imageType" text="Image Type" /></label>
					<apptags:lookupField items="${command.eipImageLookUps}"
						path="entity.imageLinkType"
						selectOptionLabelCode="eip.select.image" cssClass="form-control" /> -->
					<label class="col-sm-2 control-label" for="entity.hasSubLink1"><spring:message
							code="SectionEntryFormModel.hasLink"
							text="SectionEntryFormModel.hasLink" /></label>
					<div class="col-sm-4">
						<form:checkbox path="entity.hasSubLink" value="Y"
							cssClass="margin-top-10" />
					</div>
									<label class="col-sm-2 control-label" for="entity.isArchive1"><spring:message
							code="lbl.archive" text="Archive" /></label>
					<div class="col-sm-4">
						<form:checkbox path="entity.isArchive" value="Y"
							cssClass="margin-top-10" />
					</div>
					
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2" for="entity.subLinkOrder"><spring:message
							code="SectionEntryFormModel.subLinkOrder"
							text="SectionEntryFormModel.subLinkOrder" /><span class="mand">*</span></label>
					<div class="col-sm-4">
					<c:set var="SublinkOrder" value="${command.getAppSession().getMessage('eip.admin.SectionEntryFormModel.SublinkOrder') }" />
				       <form:input id="subLinkOrder" path="entity.subLinkOrder"  maxlegnth="6"
					   cssClass=" hasOneDecimal form-control mandClassColor"  data-rule-required="true" data-msg-required="${SublinkOrder}"/>
							<button type="button" class="help-link" data-toggle="modal" data-target="#help-link" title="<spring:message code="SectionEntryFormModel.setOrder" text="How to set " />"><i class="fa fa-question-circle fa-lg"></i><span class="hidden">Help</span></button>
							
					</div>
					<label class="control-label col-sm-2 required-control" for="isLinkModify"><spring:message
							code="admin.isHighlighted" text="Is Highlighted" /></label>
					<div class="col-sm-4">
						<c:set var="HighlightedMsg" value="${command.getAppSession().getMessage('eip.admin.SectionDetails.highlightedMsg') }"></c:set>
						<form:select cssClass=" form-control mandColorClass"
							path="entity.isLinkModify" id="isLinkModify" data-rule-required="true" data-msg-required="${HighlightedMsg}">
							<form:option value="null"><spring:message
							code="admin.Select" text="Select" /></form:option>
							<form:option value="T"><spring:message
							code="admin.true" text="True" /></form:option>
							<form:option value="F"><spring:message
							code="admin.false" text="False" /></form:option>
						</form:select>
						<label id="error_msg" style="display:none; border:none;"></label>
					</div>
				</div>
				<div class="form-group">
					<%-- <apptags:textArea labelCode="eip.cheker.remark" path="entity.remark"  cssClass="remark" maxlegnth="1000"></apptags:textArea> --%>
					
					<label class="col-sm-2 control-label" for="entity.remark">
						<spring:message code="eip.cheker.remark" text="Cheker Remark"/><span class="mand">*</span>
					</label>
					
					<div class="col-sm-4">
					<c:set var="cheker" value="${command.getAppSession().getMessage('eip.admin.validatn.cheker') }" />
						<form:textarea  path="entity.remark" id = "entityRemark" cssClass="form-control remark mandColorClass"  maxlength="1000" onkeyup="countCharacter(this,1000,'DescriptionCount')"  onfocus="countCharacter(this,1000,'DescriptionCount')"  data-rule-required="true" data-msg-required="${cheker}"/>
				<label id="error_msg_cheker" style="display:none; border:none;"></label> 
				<div>
			 		<p class="charsRemaining" id="P3"><spring:message code="charcter.remain" text="characters remaining " /></p>
					<p class="charsRemaining" id="DescriptionCount"></p>
		    	</div>
					</div>
				</div>
				<table class="table table-bordered" id="gridtable">
					<tr>
						<th><spring:message code="SectionEntryFormModel.sectionType"
								text="Section Type" /></th>
								<!-- df#128589 -->
						<%-- <th width="50" id="CollectionAdd"><spring:message code="rti.addRow"
								text="Add Row" /></th>
						<th width="50" id="CollectionAdd"><spring:message code="rti.deleteRow"
								text="Remove Row" /></th> --%>
					</tr>
					
					<tr class="total">
							
							<td>
							<c:forEach items="${command.getSectionTypeList()}" var="lookUps" varStatus="lk" begin="0" end="0" >
							
							<form:select
									cssClass=" form-control mandClassColor subsize chosen-select-no-results"
									path="entity.sectionType${lk.index}" id="sectionType${lk.index}" onchange="sectionType(${lk.index})" aria-label="Select Section">
									<form:option value="0"><spring:message code="Select"  text="Select"/></form:option>
									
							 	<c:forEach items="${command.sectionType}" var="lookUpstype"
										varStatus="lktype">
										<form:option value="${lookUpstype.lookUpId}">${lookUpstype.lookUpDesc}</form:option>
									</c:forEach>
									
								</form:select>
							</c:forEach>	
								
								</td>
								<!-- df#128589 -->
							<!-- <td id="CollectionAdd1"><a href="javascript:void(0);"
								class="addChargesLinkCollection btn btn-blue-2" title="Add Row"><i
									class="fa fa-plus-circle"></i><span class="hide">Add</span></a></td>
							<td><a href="javascript:void(0);" class="remCF btn btn-danger"><i class="fa fa-minus-circle"></i><span class="hidden">Delete</span></a></td> -->
						</tr>
						
					
				</table>
				
		       <div class="form-group padding-top-10" id="AdminFaqCheker">				
					<div class="col-sm-12 text-center">
                      
                       <form:radiobutton path="entity.chekkerflag" name="radiobutton" value="Y" class="radiobutton" aria-label="Authenticate"/> <spring:message code="Authenticate" text="Authenticate" />
                       <form:radiobutton path="entity.chekkerflag" name="radiobutton" value="N" class="radiobutton" aria-label="Reject"/> <spring:message code="Reject" text="Reject" />
	                </div>					
				</div>
			
				<div class="text-center padding-top-10">
					<input type="button" class="btn btn-success" value="<spring:message code="SectionEntryFormModel.saveAndContinue" text="Save And Continue" />" onclick="return saveOrUpdateFormSubMenu(this,false, undefined,'saveAndHoldData');" />				
					<%-- <a href="SectionEntry.html" class="btn btn-danger" id="AdminFaqback"><spring:message code="portal.common.button.back" /></a> --%>
					<a href="SectionEntry.html?AdminFaqCheker" class="btn btn-danger" id="AdminFaqChekerback"><spring:message code="portal.common.button.back" /></a>
					<%-- <c:if test="${command.MODE eq 'A'}">
					<input type="button" value="<spring:message code="rstBtn"/>" onclick="resetFormOnAdd(frmProjectDetail)" class="btn btn-warning"></input>
					</c:if> --%>
					</div>
           <form:hidden path="entity.checkerFlag1" id="checkerFlag1"/> 
			</form:form>
		</div>
	</div>
</div>
<div class="modal fade" id="help-link" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title"><spring:message code="SectionEntryFormModel.setOrder" text="How to set Order"/></h4>
        </div>
        <div class="modal-body">
          <p><spring:message code="SectionEntryFormModel.setOrder1" text="1.The SubLink order field accepts Decimal as well as Numbers."/></p>
          <p><spring:message code="SectionEntryFormModel.setOrder2" text="2. If we want to change the order of the links we can do it by inputting a number lesser than the previous one or vice versa."  /></p>
        </div>
      </div>
      
    </div>
  </div>

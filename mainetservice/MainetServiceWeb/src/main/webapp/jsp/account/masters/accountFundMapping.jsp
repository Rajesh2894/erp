<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script>
$(document).ready(function() {
	if('${popupMode}'=='view')
		{
		$('select').attr("disabled", true);	
		$("#saveEdit").hide();
		$("#saveAdd").hide();
		$('#addFund').attr('disabled',true);
	    $('[id^="deleteFund"]').attr('disabled',true);
		}
	if('${popupMode}'=='update')
	{
		$(this).find("#cpdIdFundType").attr("name", "listOfTbBankaccount["+ ${count} +"].cpdIdFundType");
		reorderFundAccount();
	}
	var cntFund = $('#tblf1 tr').length - 1;
    $("#addFund").click(function(){
	var countAccount=cntFund-1;
	
     if($('#fundMap'+countAccount).val()!=0)
        { 
        var srNo=cntFund+1;
    		var row =    '<td id="fundSrNo"> '+srNo+'</td>'
							+'<td>'
                            + '<select name="listOfTbBankaccount['+${count}+'].funds['+cntFund+'].fundId" id="fundMap'+cntFund+'" class="form-control">'
							+ '<option value="0">'
							+ "Select Type"
							+ '</option>'
							+ '	<c:forEach items="${fundList}" var="lookUp">'
						    +'  <option code="${lookUp.fundCode}" value="${lookUp.fundId}">${lookUp.fundCompositecode}     ${lookUp.fundDesc}</option>'
							+ '</c:forEach>'
							+ '</select>'
						    + '</td>';
							
					$('#tblf1 tr').last().after('<tr id="trf'+cntFund+'" class="fundClass">'+ row
							+ '<td><a data-toggle="tooltip" data-placement="top" title="" class="btn btn-danger btn-sm deletClass" id="deleteFund'+cntFund+'" onclick="removeFundRow('
							+ cntFund+ ')"><i class="fa fa-trash"></i></a></td></tr>');
					        cntFund++;
		                   reorderFundAccount(); 
		                 
        }
          else
            { var errorList = [];	
		       errorList.push(getLocalMessage('Please Select Fund'));
        	  if(errorList.length > 0){ 
  		    	showAccountFundError(errorList);
  		    return false;
  		    }
            } 

});

});
function showAccountFundError(errorList)
{
var errMsg = '<ul>';
$.each(errorList, function(index) {
	errMsg += '<li>' + errorList[index] + '</li>';
});
errMsg += '</ul>';
 $('#errorFundId').html(errMsg);
$('#errorFundDivId').show();
}
function removeFundRow(cntFund)
{
	if ($('#tblf1 tr').size() > 2) {
		$("#trf"+cntFund).remove();
		cntFund--;
		reorderFundAccount();
		
		} else {
			 var errorList = [];
			 errorList.push(getLocalMessage('Can not Remove'));
			 showAccountFundError(errorList);
			}
}
function reorderFundAccount()
{
	$('.fundClass').each(function(i) {
		
	
		$(this).find(".deletClass").attr("id", "deleteAccount" + (i));
		$(this).closest("tr").attr("id", "trf" + (i));
		$(this).closest('tr').find('#fundSrNo').text(i+1);
	    $(this).find("select:eq(0)").attr("name", "listOfTbBankaccount["+ ${count} +"].funds[" + (i) + "].fundId");
	    $(this).find("select:eq(0)").attr("id", "fundMap" + (i));
	    $(this).find(".deletClass").attr("onclick", "removeFundRow(" + (i) + ")");
		});

}
function saveFundMapping(element)

{   
	 var errorList = [];	
	 var fundType = $.trim($("#cpdIdFundType").val());
	    if(fundType==0 || fundType=="")
		    {
	    	 errorList.push(getLocalMessage('Select Fund Type'));
		    }
	  
	   $('.fundClass').each(function(i) {
		
		   var srNo=i+1;
		   if($.trim($("#fundMap"+i).val())==0||$.trim($("#fundMap"+i).val())=='')
				   {
			   errorList.push(getLocalMessage("Please Select Fund for Sr no."+srNo));
			   }
		   
	   });
	
	   if(errorList.length > 0){ 
		showAccountFundError(errorList);
	    return false;
	    }
	    var	formName =	findClosestElementId(element, 'form');
	    var theForm	=	'#'+formName;
	    var requestData = __serializeForm(theForm);
	    $.ajax({
			url : "BankMaster.html?saveFundForm",
			data :requestData,
			type : 'GET',
			async : false,
			dataType : '',
		    success : function(response) {
		    	$.fancybox.close();
		    	$("#viewFund"+${tbBankmaster.row}).prop("disabled",false);
		    	
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});	
		
}
</script>
<div class="widget margin-bottom-0">
	<div class="widget-header">
		<h2>
			<strong><spring:message code="account.fund.bank.acc"
					text="Bank Account" /> </strong>
			<spring:message code="account.fund.map" text="Fund Mapping" />
		</h2>
	</div>
	<div class="padding-20">
		<c:url value="${saveAction}" var="url_form_submit" />
		<c:url value="${popupMode}" var="popupMode" />
		<form:form method="post" action="${url_form_submit}"
			id="accountFundMapping" class="form-horizontal"
			modelAttribute="tbBankmaster">
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorFundDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span>&times;</span>
				</button>
				<span id="errorFundId"></span>
			</div>
			<form:hidden path="row" id="row" />

			<c:choose>
				<c:when test="${empty tbBankmaster.listOfTbBankaccount}">
					<div class="form-group">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="account.fund.type" text="Fund Type" /></label>
						<apptags:lookupField items="${fundType}"
							path="listOfTbBankaccount[${tbBankmaster.row}].cpdIdFundType"
							cssClass="form-control" hasChildLookup="false" hasId="true"
							showAll="false"
							selectOptionLabelCode="applicantinfo.label.select"
							isMandatory="true" />

					</div>
					<div class="table-responsive">
						<table class="table table-bordered" id="tblf1">
							<tr>
								<th><spring:message code="account.cheque.dishonour.sr.no"
										text="Sr No." /><span class="mand">*</span></th>
								<th><spring:message
										code="account.budgetopenmaster.fundcode" text="Fund" /><span
									class="mand">*</span></th>
								<th><a data-toggle="tooltip" data-placement="top" title=""
									class="btn btn-blue-2 btn-sm" id="addFund"><i
										class="fa fa-plus"></i></a></th>
							</tr>
							<tr id="trf0" class="fundClass">
								<td id="fundSrNo">1</td>
								<td><form:select
										path="listOfTbBankaccount[${tbBankmaster.row}].funds[0].fundId"
										class="form-control" id="fundMap0">
										<form:option value="0">
											<spring:message
												code="account.budgetopenmaster.selectfundcode"
												text="Select Fund" />
										</form:option>
										<c:forEach items="${fundList}" var="lookUp">
											<form:option code="${lookUp.fundCode}"
												value="${lookUp.fundId}">${lookUp.fundCompositecode}     ${lookUp.fundDesc}</form:option>
										</c:forEach>
									</form:select></td>
								<td><a data-toggle="tooltip" data-placement="top" title=""
									class="btn btn-danger btn-sm deletClass" id=deleteFund0
									onclick="removeFundRow(0)"><i class="fa fa-trash"></i></a></td>
							</tr>
						</table>
					</div>

					<div class="text-center padding-top-20">
						<button type="button" class="btn btn-success btn-submit"
							onclick="saveFundMapping(this)" id="saveAdd">
							<spring:message code="account.bankmaster.save" text="Save" />
						</button>
					</div>
				</c:when>
				<c:otherwise>

					<div class="form-group">
						<label class="col-sm-2 control-label required-control"><spring:message
								code="account.fund.type" text="Fund Type" /></label>
						<apptags:lookupField items="${fundType}"
							path="listOfTbBankaccount[0].cpdIdFundType"
							cssClass="form-control" hasChildLookup="false" hasId="true"
							showAll="false"
							selectOptionLabelCode="applicantinfo.label.select"
							isMandatory="true" />

					</div>




					<div class="table-responsive">
						<table class="table table-bordered" id="tblf1">
							<tr>
								<th><spring:message code="account.cheque.dishonour.sr.no"
										text="Sr No." /><span class="mand">*</span></th>
								<th><spring:message
										code="account.budgetopenmaster.fundcode" text="Fund" /><span
									class="mand">*</span></th>
								<th><a data-toggle="tooltip" data-placement="top" title=""
									class="btn btn-blue-2 btn-sm" data-original-title="Add Fund"
									id="addFund"><i class="fa fa-plus"></i></a></th>
							</tr>
							<c:forEach items="${tbBankmaster.listOfTbBankaccount}"
								var="account" varStatus="sts">
								<c:forEach items="${account.funds}" var="funds"
									varStatus="status">
									<tr id="trf0" class="fundClass">
										<td id="fundSrNo">${status.count}</td>
										<td><form:select
												path="listOfTbBankaccount[${sts.count-1}].funds[${status.count-1}].fundId"
												class="form-control" id="fundMap0">
												<form:option value="0">
													<spring:message
														code="account.budgetopenmaster.selectfundcode"
														text="Select Fund" />
												</form:option>
												<c:forEach items="${fundList}" var="lookUp">
													<form:option code="${lookUp.fundCode}"
														value="${lookUp.fundId}">${lookUp.fundCompositecode}     ${lookUp.fundDesc}</form:option>
												</c:forEach>
											</form:select></td>
										<td><a data-toggle="tooltip" data-placement="top"
											title="" class="btn btn-danger btn-sm deletClass"
											id=deleteFund0 onclick="removeFundRow(0)"><i
												class="fa fa-trash"></i></a></td>
									</tr>
								</c:forEach>
							</c:forEach>
						</table>
					</div>



					<div class="text-center padding-top-20">
						<button type="button" class="btn btn-success btn-submit"
							onclick="saveFundMapping(this)" id="saveEdit">
							<spring:message code="account.configuration.save" text="Save" />
						</button>
					</div>
				</c:otherwise>
			</c:choose>


		</form:form>
	</div>
</div>

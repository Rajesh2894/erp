<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script>
$(document).ready(function(e){
	prepareChequeDateTag();
	
	$('.dishonorClass').each(function(i) {
		checkFiledsDishonor(i);
	});
	yearLength();
});


function yearLength(){	
	$('.dateOnly').each(function(i) {
    	
            var fieldValue = $(this).val();
            if (fieldValue.length > 10) {
                    $(this).val(fieldValue.substr(0, 10));
            }
    })
}


function compareDate(date) {
	
	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);

}

function serachChequeData(obj)
{
	var errorList = [];
	var dept =$("#department").val();
	var fromDate = $("#fromDate").val();
	var toDate=$("#toDate").val();
	if(dept=='0' || dept == undefined){
		errorList.push(getLocalMessage("account.bill.entry.department"));
	}
	if ((compareDate(fromDate)) > compareDate(toDate)) {
		errorList.push(getLocalMessage("cheque.dishonor.validation.Date"));
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
	var formName	=	findClosestElementId(obj,'form');
	
	var theForm	=	'#'+formName;
	
	var url		=	'ChequeDishonor.html?serachChequeData';
	var bankId=$("#bankList").val();
	if(bankId===undefined){
		bankId=0;
	}

	var requestData = {
			"deptId":$("#department").val(),
			"fromDate":$("#fromDate").val(),
			"toDate":$("#toDate").val(),
			"receiptNo":$("#receiptNo").val(),
			"chequeNo":$("#chequeNo").val(),
			"bankId":bankId,
	}
	var returnData =__doAjaxRequest(url,'POST',requestData, false,'html');
	$("#mainPage").html(returnData);
	prepareChequeDateTag();
	}
	
}

function saveData(element)
{
	var result= saveOrUpdateForm(element,"Cheque Entry done Successfully!", 'ChequeDishonor.html', 'saveform');
	prepareChequeDateTag();
	return result;
}
function resetChequeForm(element){
	$("#chequeDishonor").submit();
}

 function checkFiledsDishonor(index){
		if ($('#checkDishonor'+index).find(":selected").val() === '0' || $('#checkDishonor'+index).find(":selected").val() ===null) {
			$("#amonut"+index).val('');
			$("#amonut"+index).prop("disabled",true);
			$("#remark"+index).val('').prop("disabled",true);
		}
		else if ($('#checkDishonor'+index).find(":selected").val() === 'Y'){
	 		$("#amonut"+index).prop("disabled",false);
	 		$("#amonut"+index).val($("#disCharge"+index).val());
	 		$("#remark"+index).prop("disabled",false);
		}
		else if ($('#checkDishonor'+index).find(":selected").val() === 'N'){
			$("#amonut"+index).val('');
			$("#amonut"+index).prop("disabled",true);
			$("#remark"+index).val('').prop("disabled",true);
		}
 }
 
function prepareChequeDateTag() {
	var dateFields = $('.lessthancurrdate');
	dateFields.each(function () {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});
}

function chkProcessDate(i){	
	var chkdate= $("#chkdate"+i).val();
	$("#chequeDishonorDate"+i).datepicker({
	        dateFormat: 'dd/mm/yy',		
			changeMonth: true,
			changeYear: true,
			minDate:chkdate
	});
}

</script>
<div id="mainPage"> 
 <apptags:breadcrumb></apptags:breadcrumb>

    <!-- ============================================================== --> 
    <!-- Start Content here --> 
    <!-- ============================================================== -->
    <div class="content"> 
      <!-- Start info box -->
      <div class="widget">      	
        <div class="widget-header">
          <h2><spring:message code="master.chequeDisEntry" text="Cheque Dishonor/Clearance"/></h2>
          <apptags:helpDoc url="ChequeDishonor.html"></apptags:helpDoc>
        </div>
        <div class="widget-content padding">
          <div class="mand-label clearfix"><span>Field with <i class="text-red-1">*</i> is mandatory</span></div>
          <form:form action="ChequeDishonor.html" method="POST" class="form-horizontal" id="chequeDishonor">
          	<jsp:include page="/jsp/tiles/validationerror.jsp" />
            <div class="warning-div error-div alert alert-danger alert-dismissible"	id="errorDiv"></div>
            
            
            <div class="form-group">
			<label class="col-sm-2 control-label required-control"><spring:message code="master.lbl.deptName" text="Department Name"/></label>
			<div class="col-sm-4">
				<form:select path="deptId" onchange="" id="department" cssClass="form-control chosen-select-no-results">
					<form:option value="0" ><spring:message text="Select department" code="master.lbl.selDepartment"/> </form:option>
					<c:forEach items="${command.department}" var="dept">
					 <form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
					 </c:forEach>
				</form:select>              
			</div>
			
			  <c:if test="${command.accountActive eq 'Y'}"> 
			<label class="col-sm-2 control-label "><spring:message code="master.lbl.bankName" text="Bank Name"/></label>
                  <div class="col-sm-4">
						<form:select path="bankId"  id="bankList" cssClass="form-control chosen-select-no-results">
							<form:option value="0"><spring:message code="rti.sel.bank" /></form:option>
							<form:options items="${command.banks}" />
						</form:select></div>	
						</c:if>
			
		</div>
            
            
              <div class="form-group">
		 <label class="col-sm-2 control-label"><spring:message code="master.lbl.fromDate" text="From Date"/></label>
             <div class="col-sm-4">
             <div class="input-group">
			<form:input path="fromDate" type="text" id="fromDate" class="lessthancurrdate form-control"  ></form:input>

	      <label class="input-group-addon"><i class="fa fa-calendar"></i></label>
	    </div>
             
              </div>
			   <label class="control-label col-sm-2"><spring:message code="master.lbl.toDate" text="To Date"/></label>
              <div class="col-sm-4">
               <div class="input-group">
              <form:input path="toDate" type="text" id="toDate" class="lessthancurrdate form-control"  ></form:input>

	      <label class="input-group-addon"><i class="fa fa-calendar"></i></label>
	    </div>
              </div>
        </div> 
       
             
        <div class="form-group">
		 <label class="col-sm-2 control-label"><spring:message code="master.lbl.receiptNo" text="Receipt No"/></label>
             <div class="col-sm-4">
                <form:input path="receiptNo"  class="form-control hasNumber" id="receiptNo" maxlength="16"></form:input>
              </div>
			   <label class="control-label col-sm-2"><spring:message code="master.lbl.chequeNo" text="Cheque No"/></label>
              <div class="col-sm-4">
                <form:input path="chequeNo"  class="form-control hasNumber" id="chequeNo" maxlength="6"></form:input>
              </div>
        </div>
         <div class="text-center">
          <button type="button" class="btn btn-info" onclick="serachChequeData(this)"><i class="fa fa-search"></i><spring:message code="advance.requisition.search" text=" Search"/></button>
              <button type="button" class="btn btn-warning" onclick="resetChequeForm(this)"><i class="fa fa-undo"></i><spring:message code="advance.requisition.reset" text="Reset"/> </button>
              </div>
               <input type="hidden" id="hiddenRowSize" value="${fn:length(command.feeDetail)}"/>
        <c:if test="${not empty command.feeDetail}"> 
        
            <div class="table-responsive max-height-300 margin-top-10">
              <table class="table table-bordered table-condensed table-striped">
              <thead style="position: sticky; top: 0px">
                <tr>
                  <th width="20">Sr. No.</th>
                  <th width="80">Instrument No.</th>
                  <th width="80">Instrument Date</th>
                  <th width="80">Mode</th>
              	  <th width="80">Receipt No.</th> 
                  <th width="80">Receipt Date</th>
                  <th width="100">Amount</th>
                  <th width="100">Cheque Status</th>
                  <th width="100">Date</th>
                  <th width="80">Dishonor Charge</th>
                   <th width="150">Remarks</th>
                </tr></thead>
                <tbody>
                <c:set var="srNo" value="0" scope="page"/>
								<c:forEach items="${command.feeDetail}" var="data"
									varStatus="status">
									<c:forEach items="${data.receiptModeList}" var="mode"
										varStatus="modeStatus">
										<tr class="dishonorClass">
											<td>${srNo+1}</td>
											<td class="text-right">${mode.rdChequeddno}</td>
											<td><fmt:formatDate pattern="dd/MM/yyyy"
													value="${mode.rdChequedddate}" var="chequeDate" />
												${chequeDate}</td>
											<form:hidden path="" id="chkdate${srNo}"
												value="${chequeDate}" />
											<td>${mode.rdDrawnon}</td>
											<td class="text-right">${data.rmRcptno}</td>
											<td><fmt:formatDate pattern="dd/MM/yyyy"
													value="${data.rmDate}" var="receiptDate" /> ${receiptDate}</td>
											<fmt:formatNumber type="number" value="${mode.rdAmount}"
												groupingUsed="false" maxFractionDigits="2"
												minFractionDigits="2" var="amt" />
											<td class="text-right">${amt}</td>
											<td><form:select
													path="feeDetail[${status.index}].receiptModeList[${modeStatus.index}].rdSrChkDis"
													cssClass="form-control" id="checkDishonor${srNo}"
													onchange="checkFiledsDishonor(${srNo});">
													<form:option value="0">
														<spring:message code="" text="select" />
													</form:option>
													<form:option value="Y">Dishonor</form:option>
													<form:option value="N">Clear</form:option>
												</form:select>
											<td><form:input
													path="feeDetail[${status.index}].receiptModeList[${modeStatus.index}].rdSrChkDate"
													type="text" id="chequeDishonorDate${srNo}"
													class="form-control lessthancurrdate"
													onclick="chkProcessDate(${srNo});"></form:input></td>
											<td><form:input
													path=""
													type="text" id="amonut${srNo}" readonly="true"
													class="form-control  hasDecimal" maxlength="10"></form:input>
													
												
											</td>
											<td><form:input
													path="feeDetail[${status.index}].receiptModeList[${modeStatus.index}].rd_dishonor_remark"
													type="text" id="remark${srNo}" class="form-control" 
													maxlength="200"></form:input></td>
										</tr>
										<form:hidden path="feeDetail[${status.index}].receiptModeList[${modeStatus.index}].rdSrChkDisChg" id="disCharge${srNo}"/>
										<c:set var="srNo" value="${srNo + 1 }" scope="page"/>
									</c:forEach>
								</c:forEach>
								</tbody>
								
								   <%-- <c:forEach items="${command.feeDetail}" var="data"  varStatus="status">  
                <tr>
                  <td>${status.count}</td>
                  <td class="text-right">${data.receiptModeDetail.rdChequeddno}</td>
                  <td><fmt:formatDate pattern="dd/MM/yyyy" value="${data.receiptModeDetail.rdChequedddate}" var="chequeDate"/>
                  ${chequeDate}</td>
                  <form:hidden  path=""  id="chkdate${status.index}" value="${chequeDate}"/>                                   
                  <td>${data.receiptModeDetail.rdDrawnon}</td>
               	  <td class="text-right">${data.rmRcptno}</td> 
                  <td><fmt:formatDate pattern="dd/MM/yyyy" value="${data.rmDate}" var="receiptDate"/>
                  ${receiptDate}</td>
                 <fmt:formatNumber type="number" value="${data.receiptModeDetail.rdAmount}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="amt"/>
                  <td class="text-right">${amt}</td>
                  <td>
                  <form:select path="feeDetail[${status.index}].receiptModeDetail.rdSrChkDis" cssClass="form-control" id="checkDishonor${status.index}" onchange="checkFiledsDishonor(${status.index});">
					<form:option value="0"><spring:message code="" text="select"/></form:option>
					<form:option value="Y">Dishonor</form:option>
					<form:option value="N">Clear</form:option>
				</form:select>
                  <td><form:input path="feeDetail[${status.index}].receiptModeDetail.rdSrChkDate" type="text" id="chequeDishonorDate${status.index}" class="form-control" onclick="chkProcessDate(${status.index});" ></form:input> </td>
                  <td><form:input path="feeDetail[${status.index}].receiptModeDetail.rdSrChkDisChg" type="text" id="amonut${status.index}" class="form-control  hasDecimal" maxlength="10"  ></form:input> </td>
                 <td><form:input path="feeDetail[${status.index}].receiptModeDetail.rdV1" type="text" id="remark${status.index}" class="form-control"  maxlength="200"></form:input> </td>
                </tr>
        </c:forEach>  --%>
							</table>
            </div>
             <div class="text-center padding-top-10">
              <button type="button" class="btn btn-success btn-submit" onclick="saveData(this);">Submit</button>
         	 <button type="button" class="btn btn-danger" onclick="window.location.href='AdminHome.html'">Back</button>
            </div>
   </c:if> 
          </form:form>
        </div>
      </div>
      </div>
      </div>
      
      <script>
$(document).ready(function(e){
	
	$('.lessthancurrdate').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		maxDate: '-0d',
		yearRange: "-100:-0"
	});
	
	var rows=$('#hiddenRowSize').val();
	for(var i=0;i<rows ;i++)
	{
		checkFiledsDishonor(i);
		chkProcessDate(i);
	} 
});
</script>
      <!-- End of info box --> 
    
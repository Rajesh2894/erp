<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
 <script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script>

$(document).ready(function() {


    $('#adjustmentEntryForm').validate({
	onkeyup : function(element) {
	    this.element(element);
	    console.log('onkeyup fired');
	},
	onfocusout : function(element) {
	    this.element(element);
	    console.log('onfocusout fired');
	}
    });

});

function searchAjustmentData(obj)
{
	var formName	=	findClosestElementId(obj,'form');
	
	var theForm	=	'#'+formName;
	
	var url		=	'AdjustmentEntry.html?searchAdjustmentData';
	
	var requestData = {
			"deptId":$("#department").val(),
			"ccnOrPropNo":$("#ccnOrPropNo").val(),
			"oldNo":$("#oldccnOrPropNo").val()
			
	}
	var returnData =__doAjaxRequest(url,'POST',requestData, false,'html');
	$("#adjustment").html(returnData);
}

function saveData(element)
{
	var errorList = [];
    var i = 0;
    if ($.fn.DataTable.isDataTable('#AdjustmentDetails')) {
	$('#AdjustmentDetails').DataTable().destroy();
    }

    $("#AdjustmentDetails tbody tr").each(
	    function(i) {
		debugger;
		var adjAmount = $("#adjAmount" + i).val();
		var rowCount = i + 1;

		if (adjAmount == "") {
		    errorList.push(getLocalMessage("Adjustment amount cannot be empty. The value should be atleast with 0 at row")
			    + rowCount);
		}


	    });
    if (errorList.length == 0) {
    	var result= saveOrUpdateForm(element,"Adjustment done Successfully!", 'AdjustmentEntry.html', 'saveform');
    	return result;
    }else{
    	displayErrorsOnPage(errorList);
    }
	
}
function resetAdjustmentForm(element){
	$("#adjustmentEntryForm").submit();
}


function fetchAdjustmentHistory(ele)
{
	var requestData = {};
	var returnData =__doAjaxRequest('AdjustmentEntry.html?viewHistoryDetails','POST',requestData, false,'html');
	openPopupWithResponse(returnData);
}


</script>

 <div id="adjustment">
    <apptags:breadcrumb></apptags:breadcrumb>
    <!-- ============================================================== --> 
    <!-- Start Content here --> 
    <!-- ============================================================== -->
    <div class="content"> 
      <!-- Start info box -->
      <div class="widget ">
        <div class="widget-header">
          <h2><spring:message text="Adjustment Entry" code="master.lbl.adjEntry"/></h2>
          <apptags:helpDoc url="AdjustmentEntry.html"></apptags:helpDoc>
        </div>
        <div class="widget-content padding">
          <div class="mand-label clearfix"><span><spring:message code="contract.breadcrumb.fieldwith" />
				<i class="text-red-1">*</i> <spring:message
					code="contract.breadcrumb.ismandatory" /> </span></div>
          <form:form action="AdjustmentEntry.html" method="POST" class="form-horizontal" id="adjustmentEntryForm">
           	<jsp:include page="/jsp/tiles/validationerror.jsp" />
           	<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
            <h4 class="margin-top-0"><spring:message text="Adjustment Entry" code="master.lbl.adjEntry"/></h4>
            <div class="form-group">
              <label class="col-sm-2 control-label required-control" for="department" ><spring:message text="Department" code="master.lbl.dept"/></label>
              <div class="col-sm-4">
               <form:select path="adjustmentDto.dpDeptId" onchange="" id="department" cssClass="form-control chosen-select-no-results">
					<form:option value="0" ><spring:message text="Select department" code="master.lbl.selDept"/> </form:option>
					<c:forEach items="${command.department}" var="dept">
					 <form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
					 </c:forEach>
				</form:select>    
              </div>
                <label class="col-sm-2 control-label required-control" for="ccnOrPropNo"><spring:message text="Connection No./ Property No." code="master.lbl.conNo"/></label>
              <div class="col-sm-4">
                <form:input path="adjustmentDto.adjRefNo"  class="form-control" id="ccnOrPropNo"/>
              </div>
            </div>
            
             <div class="form-group">
                <label class="col-sm-2 control-label required-control" for="oldccnOrPropNo"><spring:message text="Old Connection No./ Old Property No."/></label>
              <div class="col-sm-4">
                <form:input path=""  class="form-control" id="oldccnOrPropNo"/>
              </div>
            </div>
            
            
             <div class="text-center margin-bottom-10 ">
          <button type="button" class="btn btn-info" onclick="return searchAjustmentData(this);"><i class="fa fa-search"></i><spring:message text=" Search" code="master.search"/></button>
              <button type="button" class="btn btn-warning" onclick="resetAdjustmentForm(this)"><spring:message text=" Reset" code="reset.msg"/></button>
              </div>
            
            <c:if test="${command.bill ne null && command.bill.tbWtBillDet ne null}">
            <div class="form-group">
             <label class="col-sm-2 control-label " for="owner">Owner Name </label>
             <div class="col-sm-4">
                 <form:input path="bmCcnOwner"  value="" class="form-control" readonly="true" id="owner"/>
              </div>
             <label class="control-label col-sm-2" for="address">Address </label>
              <div class="col-sm-4">
                <form:textarea path="address"  class="form-control" readonly="true" id="address"></form:textarea>
              </div>
            </div>
             <div class="form-group">
              <label class="col-sm-2 control-label " for="BmNO">Bill No. </label>
              <div class="col-sm-4">
               <form:input path="bill.bmNo" class="form-control" id="BmNO" readonly="true" />
              </div>
              <label class="col-sm-2 control-label" for="BmDate">Bill Date </label>
              <div class="col-sm-4">
                <div class="input-group">
                 <fmt:formatDate pattern="dd/MM/yyyy" value="${command.bill.bmBilldt}" var="billDate" />
                 <form:input path="" class="form-control" id="BmDate" readonly="true" value="${billDate}"/>
                  <label for="datepicker2" class="input-group-addon"><i class="fa fa-calendar"></i></label>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-2 control-label required-control" for="adType">Adjusted Type </label>
              <div class="col-sm-4">
                <form:select path="adjustmentDto.adjType" class="form-control " id="adType">
                  <form:option value="">Select</form:option>
                   	<form:option value="P">Positive</form:option>
                    <form:option value="N">Negative</form:option>
                </form:select>
              </div>
              
              <a data-toggle="tooltip" onclick="fetchAdjustmentHistory(this)"   data-placement="top" title="" class="btn btn-blue-1 btn-sm" data-original-title="History"><i class="fa fa-history"></i></a>
            </div>
            
            <h4>Distribution Details</h4>
            <div class="table-responsive">
              <table class="table table-bordered table-condensed" id="AdjustmentDetails">
                <tr>
                  <th>Sr No.</th>
                  <th>Tax Name</th>
                  <th>Tax Amount</th>
                  <th>Adjustment Amount</th>
                  <th>Remark</th>
                </tr><tbody>
                <c:forEach items="${command.bill.tbWtBillDet}" var="details" varStatus="detIndex">
                <tr>
                  <td>${detIndex.count}</td>
                  <td>
                  <form:hidden path="adjustmentDto.adjDetailDto[${detIndex.index}].taxId" value=" ${details.taxId}"/>
                  ${details.taxDesc}</td>
                  <td>${details.bdCurBalTaxamt+details.bdPrvBalArramt}</td>
                  <td>
                   <form:input path="adjustmentDto.adjDetailDto[${detIndex.index}].adjAmount"  class="form-control hasNumber" id="adjAmount${detIndex.index}" maxlength="10"/>
                  </td>
                  <td>
                   <form:input path="adjustmentDto.adjDetailDto[${detIndex.index}].adjRemark"  class="form-control" id="adjRemark${detIndex.index}" maxlength="200"/>
                  </td>
                </tr>
                </c:forEach>
                </tbody>
              </table>
            </div>
            
            
            
            <div class="overflow margin-top-10">
											<div class="table-responsive">
												<table
													class="table table-hover table-bordered table-striped">
													<tbody>
														<tr>
															<th> <spring:message
																		code="water.serialNo" text="Sr No" />
															</th>
															<th> <spring:message
																		code="water.docName" text="Document Name" />
															</th>
															<th> <spring:message
																		code="water.status" text="Status" />
															</th>
															<th> <spring:message
																		code="water.uploadText" text="Upload" />
															</th>
														</tr>
															<tr>
																<td>1</td>
																<td>Adjustment Entry</td>
																	<td> <spring:message
																				code="water.doc.mand" />
																	</td>
																<td><div id="docs_0" class="">
																		<apptags:formField fieldType="7" labelCode=""
																			hasId="true" fieldPath=""
																			isMandatory="false" showFileNameHTMLId="true"
																			fileSize="BND_COMMOM_MAX_SIZE"
																			maxFileCount="CHECK_LIST_MAX_COUNT"
																			validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
																			currentCount="0" />
																	</div>
																	</td>
															</tr>
													</tbody>
												</table>
											</div>
										</div>		
										
										
										
										
            <div class="text-center margin-top-10">
              <button type="button" class="btn btn-success btn-submit" onclick="return saveData(this);">Submit</button>
              
              <button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="window.location.href='AdminHome.html'" id="button-Cancel">
						<spring:message code="property.Cancel" text="Cancel" />
					</button>
            </div>
            </c:if>
          </form:form>
        </div>
      </div>
      </div>
      </div>
<%@page import="aj.org.objectweb.asm.Attribute"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/cfc/challan/offlinePayForMultiMode.js"></script>
<style>
.radio label, .checkbox label {
    margin-top: 14px;
}
</style>


             <div class="form-group padding-top-20">
				<label class="control-label col-sm-2"><spring:message code="rti.selectPaymrntMode" /><span class="mand">*</span></label>
				<div class="radio col-sm-8">
						<label> <form:radiobutton path="offlineDTO.onlineOfflineCheck" value="P"  id="payAtCounter" onclick="showDiv(this);" /> <spring:message code="rti.payAtCounter" />
						</label>
                <%-- <c:if test="${param.posHide ne 'POS'}">
					<label> <form:radiobutton path="offlineDTO.onlineOfflineCheck" value="POS"  id="payByPos" onclick="showDiv(this);" /> <spring:message code="water.Pos.Check" text="POS" />
					</label>
					</c:if> --%>
					</div>
				</div>
<div id="payModeData">
        <table id="billTable" class="table table-bordered">
							<tr>
							 <!--   <th width="2%" class="text-center">Sr.No</th> -->
								<th width="12%" class="text-center">Payment Mode<span class="mand">*</span></th>
								<th width="12%" class="text-center">Drawn On<span class="mand">*</span></th>
								<th width="12%" class="text-center">Pay Order/Cheque/DD Date<span class="mand">*</span></th>
								<th class="text-center">Pay Order/Cheque/DD No<span class="mand">*</span></th>
								<!-- <th class="text-center">Micr No<span class="mand">*</span></th> -->
								<th class="text-center">A/C No</th>
								<th class="text-center">Amount<span class="mand">*</span></th>
								<!-- <th class="text-center">Action</th> -->
							</tr>	
						 <c:choose>
						<c:when test="${empty command.offlineDTO.multiModeList}">
							<tr class="billDetails">
						<!-- 	<td id="academicSrNoId0">1</td> -->
								<td>
								
							<%-- 	<apptags:lookupField items="${command.userSession.paymentMode}" path="offlineDTO.multiModeList[0].payModeIn" cssClass="form-control" changeHandler="disableFieldsOnCashSelection(0)"
						          selectOptionLabelCode="rti.sel.paymentmode" hasId="true" tabIndex="0" isMandatory="true" hasTableForm="true">
					            </apptags:lookupField> --%>
					       				<c:set var="baseLookupCode" value="${command.userSession.paymentMode}" /> 									 
										<form:select path="offlineDTO.multiModeList[0].payModeIn" 
											class="form-control"   id="modeId0"  onchange="disableFieldsOnCashSelection(0)">
											<form:option value="0">Select</form:option>
											<c:forEach items="${baseLookupCode}" var="lookUp">
												<c:if test="${param.SBill ne lookUp.lookUpCode}">
												<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:if>
											</c:forEach>
										</form:select>
								</td>
								<td>
								
								<form:select path="offlineDTO.multiModeList[0].cbBankid" id="bankID0" cssClass="form-control">
							<form:option value="0"><spring:message code="rti.sel.bank" /></form:option>
							<form:options items="${command.appSession.customerBanks}" />
						</form:select>
								
								</td>
								<td>
								<div class="input-group">
								<form:input path="offlineDTO.multiModeList[0].bmChqDDDate" type="text" id="bmChqDDDate0"
									class="lessthancurrdate form-control" readonly='true'  ></form:input>
								<label for="datepicker" class="input-group-addon"><i
									class="fa fa-calendar"></i></label></div></td>
								 
								<td><form:input name="" id="chqueNoId0" class="chequeNo form-control hasNumber" type="text" value="" maxlength="6"  path="offlineDTO.multiModeList[0].bmChqDDNo"/></td>
								<%-- <td><form:input name="" id="micrNoId0" class="micrNo form-control hasNumber" type="text" value="" maxlength="09"  path="offlineDTO.multiModeList[0].micrCode"/></td> --%>
								<td><form:input name="" id="accNo0" class="totMetAmount form-control hasNumber" type="text" value="" maxlength="16"  path="offlineDTO.multiModeList[0].bmBankAccountId"/></td>
								<td><form:input name=""  id="amount0" class="totAddMultiAmount form-control has2Decimal" type="text" value=""  maxlength="10" path="offlineDTO.multiModeList[0].amount"/></td>
								<!-- <td>
								 <a    title="Add" class="academicAddRow btn btn-success btn-sm" id="billAddButton0"><i class="fa fa-plus-circle"></i></a>
								 <a  title="Delete" class="deleteMode btn btn-danger btn-sm"  id="billDelButton0"><i class="fa fa-trash-o"></i></a>
							</td> -->
							</tr>
						 </c:when>
							
							<c:otherwise>
						<c:forEach var="dataList" items="${command.offlineDTO.multiModeList}" varStatus="status">
						 
						<tr class="billDetails">
							<%-- 	<td id="academicSrNoId${status.index}">${status.index}+1</td> --%>
								<td>
						<%-- 		<apptags:lookupField items="${command.userSession.paymentMode}" path="offlineDTO.multiModeList[${status.index}].payModeIn" cssClass="form-control" changeHandler="disableFieldsOnCashSelection(${status.index})"
						          selectOptionLabelCode="rti.sel.paymentmode" hasId="true" tabIndex="${status.index}" isMandatory="true" hasTableForm="true">
					            </apptags:lookupField> --%>					         
					           		
									  <c:set var="baseLookupCode" value="${command.userSession.paymentMode}" /> 
										<form:select path="offlineDTO.multiModeList[${status.index}].payModeIn" 
											class="form-control"   id="modeId${status.index}" onchange="disableFieldsOnCashSelection(${status.index})">
											<form:option value="0">Select</form:option>
											<c:forEach items="${baseLookupCode}" var="lookUp">
												<c:if test="${param.SBill ne lookUp.lookUpCode}">
												<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:if>
											</c:forEach>
										</form:select>
								</td>
								<td>
									<form:select path="offlineDTO.multiModeList[${status.index}].cbBankid" id="bankID${status.index}" cssClass="form-control">
							<form:option value="0"><spring:message code="rti.sel.bank" /></form:option>
							<form:options items="${command.appSession.customerBanks}" />
						</form:select>
								</td>
								<td>
								<div class="input-group">
								<form:input path="offlineDTO.multiModeList[${status.index}].bmChqDDDate" type="text" id="bmChqDDDate${status.index}"
									class="lessthancurrdate form-control" readonly='true'></form:input>
								<label for="datepicker" class="input-group-addon"><i
									class="fa fa-calendar"></i></label></div></td>
								<td><form:input name="" id="chqueNoId${status.index}" class="chequeNo form-control hasNumber" type="text" value="" maxlength="6"  path="offlineDTO.multiModeList[${status.index}].bmChqDDNo"/></td>
								<%-- <td><form:input name="" id="micrNoId${status.index}" class="micrNo form-control hasNumber" type="text" value="" maxlength="9"  path="offlineDTO.multiModeList[${status.index}].micrCode"/></td> --%>
								<td><form:input name="" id="accNo${status.index}" class="totMetAmount form-control hasNumber" type="text" value="" maxlength="16"  path="offlineDTO.multiModeList[${status.index}].bmBankAccountId"/></td>
								<td><form:input name=""  id="amount${status.index}"   class="totAddMultiAmount form-control has2Decimal" type="text" value=""  maxlength="10" path="offlineDTO.multiModeList[${status.index}].amount"/></td>
								 
								<%-- <td>
								<a   data-placement="top" title="Add" class="academicAddRow btn btn-success btn-sm" id="billAddButton${status.index}"><i class="fa fa-plus-circle"></i></a>
								<a   data-placement="top" title="Delete" class="deleteMode btn btn-danger btn-sm"  id="billDelButton${status.index}"><i class="fa fa-trash-o"></i></a>
							</td> --%>
							</tr>
						 </c:forEach>
						 
						 </c:otherwise>
						</c:choose>
							<tr>
							<th colspan="5" class="text-center padding-top-10">Total Amount:</th>
							<td><form:input name=""  id="multiAmount"   class="totAddAmount form-control has2Decimal" type="text" value=""  maxlength="10" path="offlineDTO.charges" readonly="true"/></td>
							<!-- <th></th> -->
							</tr> 
 					   </table>
 					   </div>
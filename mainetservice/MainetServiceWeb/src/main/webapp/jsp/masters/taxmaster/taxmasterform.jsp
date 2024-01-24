<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%	response.setContentType("text/html; charset=utf-8"); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/masters/taxmaster/taxmaster.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<div class="widget" id="widget">
	<div class="widget-header"><h2><spring:message code='common.tax'/> <spring:message code='common.master' text="Master"/></h2></div>
	
	<div class="widget-content padding">
	<div class="mand-label clearfix">
	<span>
	<spring:message code="contract.breadcrumb.fieldwith"
				text="Field with" /> <i class="text-red-1">*</i> <spring:message
				code="common.master.mandatory" text="is mandatory" /> 
	</span>
	</div>
 		<div class="alert warning-div alert-danger alert-dismissible error-div" id="errorDivTaxMas"></div>
		<c:url value="${saveAction}" var="url_form_submit" />
		<form:form action="${url_form_submit}" method="post" class="form-horizontal" modelAttribute="tbTaxMas" commandName="tbTaxMas" id="taxForm">
			<input type="hidden" id="formModeId" value="${mode}"/>
			<input type="hidden" id="dept" value="${dept}"/>
			<input type="hidden" value="${isAccountItegrationMadi}" id="isAccountIntgMandi"/>
			<c:if test="${mode != 'create'}">
				<!-- Store data in hidden fields in order to be POST even if the field is disabled -->
				<form:hidden path="taxId" id="taxId"/>
				<form:hidden path="orgid" />
				<form:hidden path="createdBy"/>
				<input type="hidden" value="${sacHeadCount}" id="sacHeadCount"/>
				
				<%-- <form:hidden path="createdDate" id="createdDate"/> --%>
			</c:if>
			
			
			<div class="panel-group accordion-toggle" id="accordion_single_collapse">
			<div class="panel panel-default">
    		<div class="panel-heading"><h4 class="panel-title"><a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#collapse1"><spring:message code='common.tax'/> <spring:message code='common.master'/></a></h4></div>
    		<div id="collapse1" class="panel-collapse collapse in">			
			<div class="panel-body">
			
			<div class="form-group">
			<c:if test="${mode != 'create'}">
			<label class="col-sm-2 control-label" for="taxCode"><spring:message code='tax.code' text="Tax Code"/></label>
			<div class="col-sm-4"><form:input  id="taxCode"  path="taxCode" readonly="true" class="form-control" value="${tbTaxMas.taxCode}"/></div>
			</c:if>
			</div>
			
			<div class="form-group">
				<label class="col-sm-2 control-label required-control" for="taxList" id="taxDesc-label"><spring:message code='tax.name' text="Tax Name"/></label>
				<div class="col-sm-4">
 					<form:select id="taxList" class="form-control chosen-select-no-results" path="taxDescId" disabled="${mode ne 'create' ? true : false}">
					 <form:option value=""><spring:message code='master.selectDropDwn'/></form:option>
						<c:forEach items="${txnPrefixData}" var="txnPrefixData">
							<form:option value="${txnPrefixData.lookUpId}" code="${txnPrefixData.lookUpCode}">${txnPrefixData.lookUpDesc}</form:option>
						</c:forEach>
					</form:select>
 					<form:hidden path="taxDescId" id="taxDescId-hid"></form:hidden>
				</div>
				
				<label class="col-sm-2 control-label required-control" for="dpDeptId"><spring:message code='master.department' text="Department"/></label>
				<c:choose>
					<c:when test="${mode == 'create' }">
						<div class="col-sm-4">
						<form:select id="dpDeptId" class="form-control chosen-select-no-results required-chosen" path="dpDeptId" onchange="setDepartment(this);">
							<form:option value=""><spring:message code='master.selectDropDwn' text="Select"/></form:option>
							<c:forEach items="${deptList}" var="deptListData">
								<form:option value="${deptListData.dpDeptid }" code="${deptListData.dpDeptcode }">${deptListData.dpDeptdesc }</form:option>
							</c:forEach>
						</form:select>
						</div>
						
					
					</c:when>
					<c:otherwise>
						<div class="col-sm-4">
						<form:select id="dpDeptId" class="form-control chosen-select-no-results required-chosen" path="dpDeptId" onchange="setDepartment(this);" disabled="true">
							<form:option value=""><spring:message code='master.selectDropDwn' text="Select"/></form:option>
							<c:forEach items="${deptList}" var="deptListData">
								<form:option value="${deptListData.dpDeptid }" code="${deptListData.dpDeptcode }">${deptListData.dpDeptdesc }</form:option>
							</c:forEach>
						</form:select>
						</div>
					<form:hidden id="deptId" path="dpDeptId"/>
				</c:otherwise>
				</c:choose>
				<form:hidden path="deptCode" id="deptCode"/>
				
			</div>
			 
			<div class="form-group">
 			<label class="col-sm-2 control-label required-control" for="taxValueType"><spring:message code='tax.applicable' text="Applicable At"/></label>
			<div class="col-sm-4">
				<form:select id="taxValueType" class="form-control" path="taxApplicable">
				<form:option value=""><spring:message code='master.selectDropDwn'/></form:option>
				<c:forEach items="${taxApplicable}" var="taxApplicable">
				<form:option value="${taxApplicable.lookUpId }" code="${taxApplicable.lookUpCode }">${taxApplicable.lookUpDesc }</form:option>
				</c:forEach>
				</form:select>
				</div>
				
				<label class="col-sm-2 control-label required-control" for="taxMethod"><spring:message code='tax.method' text="Calculation Method"/></label>
				<div class="col-sm-4">
					<form:select id="taxMethod" class="form-control" path="taxMethod">
						<form:option value=""><spring:message code='master.selectDropDwn'/></form:option>
						<c:forEach items="${fsdPrefixData}" var="fsdPrefixData">
							<form:option value="${fsdPrefixData.lookUpId }" code="${fsdPrefixData.lookUpCode }">${fsdPrefixData.lookUpDesc }</form:option>
						</c:forEach>
					</form:select>
				</div>
 			</div>
  	
			<div class="form-group">
				<label class="col-sm-2 control-label" for="parentTaxCode"><spring:message code='tax.parent.code' text="Parent Tax Code"/></label>
				<div class="col-sm-4">
 					<form:select id="parentTaxCode" class="form-control chosen-select-no-results" path="parentCode">
						<form:option value=""><spring:message code='master.selectDropDwn'/></form:option>
						<c:forEach items="${taxCodeList}" var="taxCodeList">
							<form:option value="${taxCodeList.taxId }">${taxCodeList.taxCode }--${taxCodeList.taxDesc }</form:option>
						</c:forEach>
					</form:select>
				</div>
				
				<label class="col-sm-2 control-label required-control" for="taxGroup"><spring:message code='tax.group' text="Tax Group"/></label>
				<div class="col-sm-4">
					<form:select id="taxGroup" class="form-control" path="taxGroup">
						<form:option value="0"><spring:message code='master.selectDropDwn'/></form:option>
						<c:forEach items="${tagPrefixData}" var="tagPrefixData">
							<form:option value="${tagPrefixData.lookUpId}" code="${tagPrefixData.lookUpCode }">${tagPrefixData.lookUpDesc }</form:option>
						</c:forEach>
					</form:select>
				</div>
			</div>

			<div class="form-group">
			<apptags:lookupFieldSet baseLookupCode="TAC" hasId="true" pathPrefix="taxCategory" isMandatory="true" hasLookupAlphaNumericSort="true" hasSubLookupAlphaNumericSort="true" cssClass="form-control" showAll="true"/>
			</div>
 			<div class="form-group">	
				<div class="serviceTax">
				<label class="col-sm-2 control-label" for="serviceId" id="serviceId-lbl"><spring:message code='tax.services' text="Services"/></label>
				<div class="col-sm-4">
					<form:select id="serviceId" name="smServiceId" path="smServiceId"  class="form-control chosen-select-no-results">
						<form:option value=""><spring:message code='master.selectDropDwn' text="Select"/></form:option>
						<c:forEach items="${serviceList}" var="serviceData">
							<form:option value="${serviceData.smServiceId }" code="${serviceData.smShortdesc}">${serviceData.smServiceName }</form:option>
						</c:forEach>
					</form:select>
				</div>
				</div>	
				
				<label class="col-sm-2 control-label"><spring:message code='tax.print.on' text="Print On"/><span class="mand">*</span></label>
				<div class="col-sm-4">
				<c:forEach items="${ponPrefixData}" var="ponPrefixData" varStatus="count">
				<label class="checkbox-inline" style="margin-top: 8PX;"><form:checkbox path="taxPrintOn${count.count}" value="${ponPrefixData.lookUpId }"  id="taxPrintOn${count.count}"/> ${ponPrefixData.lookUpDesc }</label>
					</c:forEach>
			</div>
			</div>			
			<div class="form-group">
				<label class="col-sm-2 control-label" for="collSeq"><spring:message code='tax.coll.seq' text="Collection Sequence"/><span class="mand">*</span></label>
				<div class="col-sm-4"><form:input path="collSeq" id="collSeqId" type="text" class=" hasNumber form-control" onchange="validateSequence(this,'C')" />
				<form:hidden path="collSeq" id="hiddenCollSeqId"/>
				</div>
				
				<label class="col-sm-2 control-label required-control" for="taxDisplaySeq"><spring:message code='tax.display.seq' text="Display Sequence"/></label>
				<div class="col-sm-4"><form:input path="taxDisplaySeq" id="taxDisplaySeqId" type="text" class=" hasNumber form-control " onchange="validateSequence(this,'D')" />
				<form:hidden path="taxDisplaySeq" id="hiddenTaxDisplaySeqId"/>
			 </div>
			</div>
			
			 <div class="form-group">
			 <c:if test="${mode == 'edit' || mode == 'view' }">
              <label class="control-label col-sm-2"><spring:message code="master.status" text="Status" /></label>
              <div class="col-sm-4">
              	 <label class="checkbox-inline" style="margin-top: 8PX;">
              	 <form:checkbox path="activeChkBox" id="taxActive" disabled="${mode eq 'view' ? true : false }"/><spring:message code="master.active" text="Active"/> </label>
                  <form:hidden path="taxActive" id="activeChkBox"/>
              </div>
              </c:if>
            </div> 
			
			</div>
			</div>
			</div>
			
			
				<!--#####################################################################################  -->
			<input type="hidden" value="${eventMapNotSelectedList }">
			
			<div id="dependsOnFac-div" class="panel panel-default">
    		<div class="panel-heading"><h4 class="panel-title"><a data-toggle="collapse" class="collapsed" data-parent="#accordion_single_collapse" href="#collapse3"><spring:message code='tax.depends.on.fact' text="Depends On Factors"/></a></h4></div>
    		<div id="collapse3" class="panel-collapse collapse">			
			<div class="panel-body">
			
 			<div class="form-group">
					<div class="col-xs-6">
					<label for="eventMapSelectedId" class="hide">Event Map Select</label>
					<form:select path="" id="eventMapSelectedId" multiple="multiple" cssClass="form-control height-200">
 					<c:if test="${mode == 'edit' || mode == 'view'}">
						<c:forEach items="${eventMapNotSelectedList}" var="mapNotSelected">
						<form:option value="${mapNotSelected.lookUpId}" code="${mapNotSelected.lookUpCode}">
							${mapNotSelected.lookUpDesc}
						</form:option>	
						</c:forEach>
					</c:if>
					</form:select>
 				</div>
	
				<div class="col-xs-1">
					<button id="right" name="right" class="btn btn-blue-2 btn-block" type="button"><i class="fa fa-angle-right"><span class="hide">Right</span></i></button>
					<button id="rightall" name="rightall" class="btn btn-blue-3 btn-block" type="button"><i class="fa fa-angle-double-right"><span class="hide">RightAll</span></i></button>
					<button id="left" name="left" class="btn btn-blue-2 btn-block" type="button"><i class="fa fa-angle-left"><span class="hide">left</span></i></button>
					<button id="leftall" name="leftall" class="btn btn-blue-3 btn-block" type="button"><i class="fa fa-angle-double-left"><span class="hide">leftAll</span></i></button>
				</div>

					<div class="col-xs-5">
					<form:select multiple="multiple" class="form-control height-200" path="taxDetIdList" id="taxDetMasList">

					<c:if test="${mode == 'edit' || mode == 'view'}">
						<c:forEach items="${factForDept}" var="factForDept">
						<c:forEach items="${tbTaxMas.taxDetIdList}" var="taxDetIds">
						<c:if test="${factForDept.lookUpId eq taxDetIds}">
							<form:option value="${factForDept.lookUpId}" code="${factForDept.lookUpCode}">
							${factForDept.lookUpDesc}
							</form:option>	
						</c:if>
 					</c:forEach>
					</c:forEach>
					</c:if>
					</form:select>
 			</div>
			</div>
			</div>
			</div>
			</div>
			
		 <input type="hidden" value="${budgetList}" id="budgetList"/>
		 <input type="hidden" value="${tbTaxMas.taxBudgetBean}" id="taxBudgetBean"/>
		 
 		<div id="budgetcode-div" class="panel panel-default">
  		<div class="panel-heading"><h4 class="panel-title"><a data-toggle="collapse" class="collapsed" data-parent="#accordion_single_collapse" href="#collapse2"><spring:message code='tax.accIntegration' text='Account Integration'/></a></h4></div>
		<div id="collapse2" class="panel-collapse collapse">
		<div class="panel-body">
		<div class="table-overflow-sm">
			<table class="table table-bordered table-condensed" id="budget-tbl">
 				<tr>
					<th width="50%"><spring:message code='tax.table.column.budgetcode' text="Account Head"/><span class="mand">*</span></th>
					<th><spring:message code='tax.table.column.dmcClass'/><span class="mand">*</span></th>
					<th><spring:message code='tax.table.column.activeness'/></th>
					<th id="action-th"><spring:message code='tax.table.column.action' text="Add/Remove"/></th>
				</tr>
				
				<c:choose>
						<c:when test="${empty tbTaxMas.taxBudgetBean}">
							<tr id="tr0" class="budgetClass">
								<td class="mand">
								<form:select id="budgetcodeList0" class="form-control chosen-select-no-results" path="taxBudgetBean[0].sacHeadId">
									<form:option value=""><spring:message code='master.selectDropDwn' text="Select"/></form:option>
									<c:forEach  items="${budgetList}" var="budgetLst">
										<form:option value="${budgetLst.sacHeadId}">${budgetLst.acHeadCode}</form:option>
									</c:forEach>
								</form:select>
							
								</td>
								<td class="mand">
								<form:select id="demandcodeList0" class="form-control chosen-select-no-results dmdClass" path="taxBudgetBean[0].dmdClass">
									<form:option value=""><spring:message code='master.selectDropDwn' text="Select"/></form:option>
									<c:forEach  items="${dmcPrefixList}" var="dmcPrefixData">
										<form:option value="${dmcPrefixData.lookUpId}"
										code="${dmcPrefixData.lookUpCode}">${dmcPrefixData.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
							
								</td>
								
								
								<td>
								<form:select id="budgetStatus0" class="form-control" path="taxBudgetBean[0].taxbActive">
									<form:option value=""><spring:message code='master.selectDropDwn' text="Select"/></form:option>
									<c:forEach items="${acnPrefixList}" var="acnPrefixData">
									<form:option value="${acnPrefixData.lookUpCode}"
										code="${acnPrefixData.lookUpCode}">${acnPrefixData.descLangFirst}</form:option>
									</c:forEach>
 								</form:select>
 								
								</td>
								<td style="text-align: center;" id="action-td">
									<a class='btn btn-success btn-sm addClass' title='Add' onclick="addRow(0)"><i class='fa fa-plus' aria-hidden='true'></i></a>
									<a class='btn btn-danger btn-sm removeClass'  title='Remove' onclick="removeRow(0)"><i class='fa fa-minus' aria-hidden='true'></i></a>
								</td>
							
							</tr>
						</c:when>
						
						<c:otherwise>
							<c:forEach items="${tbTaxMas.taxBudgetBean}" var="data" varStatus="status">
							<tr id="tr${status.count-1}" class="budgetClass">
							<td class="mand">
								<form:select id="budgetcodeList${status.count-1}" class="form-control chosen-select-no-results isBudgetCode"  path="taxBudgetBean[${status.count-1}].sacHeadId">
									<form:option value=""><spring:message code='master.selectDropDwn' text="Select"/></form:option>
									<c:forEach  items="${budgetList}" var="budgetLst">
										<form:option value="${budgetLst.sacHeadId}">${budgetLst.acHeadCode}</form:option>
									</c:forEach>
								
								</form:select>
								<form:hidden path="taxBudgetBean[${status.count-1}].taxbId" id="taxbId${status.count-1}"/>
								<form:hidden path="taxBudgetBean[${status.count-1}].sacHeadId" id="sacHeadId${status.count-1}"/>
							</td>
							<td class="mand">
								<form:select id="demandcodeList${status.count-1}" class="form-control chosen-select-no-results dmdClass" path="taxBudgetBean[${status.count-1}].dmdClass">
									<form:option value=""><spring:message code='master.selectDropDwn' text="Select"/></form:option>
									<c:forEach  items="${dmcPrefixList}" var="dmcPrefixData">
										<form:option value="${dmcPrefixData.lookUpId}"
										code="${dmcPrefixData.lookUpCode}">${dmcPrefixData.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
							
								</td>
							<td>
								<form:select id="budgetStatus${status.count-1}" class="form-control" path="taxBudgetBean[${status.count-1}].taxbActive">
									<form:option value="0"><spring:message code='master.selectDropDwn' text="Select"/></form:option>
									<c:forEach items="${acnPrefixList}" var="acnPrefixData">
									<form:option value="${acnPrefixData.lookUpCode}"
										code="${acnPrefixData.lookUpCode}">${acnPrefixData.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
							</td>
							
							<td style="text-align: center;">
							<c:if test="${status.last}">
								<a class='btn btn-success btn-sm addClass isAdd' title='Add' onclick='addRow(${status.count-1})'><i class='fa fa-plus' aria-hidden='true'></i></a>
								<a class='btn btn-danger btn-sm removeClass'  title='Remove' onclick='removeRow(${status.count-1})'><i class='fa fa-minus' aria-hidden='true'></i></a>	
							</c:if>
							</td>
							</tr>
						</c:forEach>
						</c:otherwise>
					</c:choose>
								
                </table>
				</div>		                    
			</div>
			</div>
			</div>
			</div>
   
 			<div class="text-center margin-top-10">
			<c:if test="${mode != 'view'}"><button type="button" class="btn btn-success" onclick="saveTaxMasterForm(this);return false;"><spring:message code='master.save' text="Save"/></button></c:if>
			<button type="button" class="btn btn-danger" onclick="window.location.href='TaxMaster.html'" id="backBtn"><spring:message code='back.msg' text="Back"/></button>
			</div>
		</form:form>

		<!--Add Section End Here-->
	</div>
</div>


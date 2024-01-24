
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<div class="widget" id="widget">
	<div class="widget-header">
			<h2><spring:message code='common.tax'/> <spring:message code='common.master'/></h2>
	</div>
	<div class="widget-content padding">

		<!--Add Section Strat Here-->
		<c:url value="${saveAction}" var="url_form_submit" />
		<form:form action="${url_form_submit}" method="post" class="form-horizontal" modelAttribute="tbTaxMas" commandName="tbTaxMas">
			
			<c:if test="${mode != 'create'}">
				<!-- Store data in hidden fields in order to be POST even if the field is disabled -->
				<form:hidden path="taxId" id="taxId"/>
				<form:hidden path="orgid" />
				<form:hidden path="userId" />
				<form:hidden path="langId" />
			</c:if>
			
			<div class="form-group">
				<label class="col-sm-2 control-label"><spring:message code="common.master.tax.code" text="Tax Code"/></label>
				<div class="col-sm-4">
					<form:input path="taxCode" type="text" disabled="true" class="form-control disabled" />
				</div>
				<label class="col-sm-2 control-label required-control"><spring:message code="common.master.tax.name" text="Tax Name"/></label>
				<div class="col-sm-4">
					<form:input path="taxDesc" type="text" class="form-control disabled" disabled="true"/>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label required-control" for="dpDeptId"><spring:message code='master.department'/></label>
				<div class="col-sm-4">
					<form:select id="dpDeptId" class="form-control disabled" path="dpDeptId" onchange="setDepartment(this)" disabled="true">
						<form:option value=""><spring:message code='master.selectDropDwn'/></form:option>
						<c:forEach items="${deptList}" var="deptListData">
							<form:option value="${deptListData.dpDeptid }" code="${deptListData.dpDeptcode }">${deptListData.dpDeptdesc }</form:option>
						</c:forEach>
					</form:select>
				</div>
				
				<form:hidden path="deptCode" id="deptCode"/>
				<label class="col-sm-2 control-label required-control" for="taxApplicable"><spring:message code='tax.applicable'/></label>
				<div class="col-sm-4">
					<form:select id="taxValueType" class="form-control disabled" path="taxApplicable" disabled="true">
						<form:option value=""><spring:message code='master.selectDropDwn'/></form:option>
						<c:forEach items="${taxApplicable}" var="taxApplicable">
							<form:option value="${taxApplicable.lookUpId }" code="${taxApplicable.lookUpCode }">${taxApplicable.lookUpDesc }</form:option>
						</c:forEach>
					</form:select>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-2 control-label required-control"><spring:message code="common.master.tax.method" text="Tax Method"/></label>
				<div class="col-sm-4">
					<form:select id="taxMethod" class="form-control disabled" path="taxMethod" disabled="true">
						<form:option value="">Select</form:option>
						<c:forEach items="${fsdPrefixData}" var="fsdPrefixData">
							<form:option value="${fsdPrefixData.lookUpId }" code="${fsdPrefixData.lookUpCode }">${fsdPrefixData.lookUpDesc }</form:option>
						</c:forEach>
					</form:select>
				</div>
				<label class="col-sm-2 control-label"><spring:message code='tax.print.on'/></label>
				<div class="col-sm-4">
					<c:forEach items="${ponPrefixData}" var="ponPrefixData" varStatus="count">
							<label class="checkbox-inline"><form:checkbox path="taxPrintOn${count.count}" value="${ponPrefixData.lookUpId }" id="taxPrintOn${count.count}" disabled="true"/> ${ponPrefixData.lookUpDesc }</label>
					</c:forEach>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label required-control"><spring:message code='tax.parent.code'/></label>
				<div class="col-sm-4">
					<form:input path="parentCode" type="text" class="form-control disabled"  onkeypress="return check_digit(event,this);" maxlength="5" disabled="true"/>
				</div>
				<label class="col-sm-2 control-label required-control"><spring:message code='tax.display.seq'/></label>
				<div class="col-sm-4">
					<form:input path="taxDisplaySeq" type="text" class="form-control disabled" onblur="validateSequence(this,'D')" onkeypress="return check_digit(event,this);" disabled="true"/>
					<input type="hidden" value="${tbTaxMas.taxDisplaySeq}" id="taxDisplaySeqId"/>
				</div>
			</div>
			
			<div class="form-group">				
				<label class="col-sm-2 control-label required-control"><spring:message code='tax.group'/></label>
				<div class="col-sm-4">
					<form:select id="taxGroup" class="form-control disabled" path="taxGroup" disabled="true">
						<form:option value=""><spring:message code='master.selectDropDwn'/></form:option>
						<c:forEach items="${tagPrefixData}" var="tagPrefixData">
							<form:option value="${tagPrefixData.lookUpId }" code="${tagPrefixData.lookUpCode }">${tagPrefixData.lookUpDesc }</form:option>
						</c:forEach>
					</form:select>
				</div>
			
				
			<!-- SERVICE ################################################################# -->
				<div class="serviceTax">
				<label class="col-sm-2 control-label" for="serviceId"><spring:message code="contract.breadcrumb.services" text="Services"/></label>
				<div class="col-sm-4">
					<form:select id="serviceId" name="smServiceId" path="smServiceId"  class="form-control disabled" disabled="true">
						<form:option value=""><spring:message code='master.selectDropDwn'/></form:option>
						<c:forEach items="${serviceList}" var="serviceData">
							<form:option value="${serviceData.smServiceId }" code="${serviceData.smShortdesc}">${serviceData.smServiceName }</form:option>
						</c:forEach>
					</form:select>
				</div>
				</div>
			</div>	
			<div class="form-group">
				<label class="col-sm-2 control-label required-control"><spring:message code='tax.coll.method'/></label>
				<div class="col-sm-4">
					<form:select id="collMtd" class="form-control disabled" path="collMtd" disabled="true">
						<form:option value=""><spring:message code='master.selectDropDwn'/></form:option>
						<c:forEach items="${collMethodList}" var="collMethodData">
							<form:option value="${collMethodData.lookUpId }" code="${collMethodData.lookUpCode }">${collMethodData.lookUpDesc }</form:option>
						</c:forEach>
					</form:select>
				</div>
				
				<label class="col-sm-2 control-label required-control"><spring:message code='tax.coll.seq'/></label>
				<div class="col-sm-4">
					<form:input path="collSeq" type="text" class="form-control disabled" onblur="validateSequence(this,'C')" onkeypress="return check_digit(event,this);" disabled="true"/>
					<input type="hidden" id="collSeqId" value="${tbTaxMas.collSeq}"/>
				</div>
			</div>
			
			<!--  Change Tax catagory prifix to Hirarchical alter WTG to TAC-->
				<div class="form-group">
						<apptags:lookupFieldSet baseLookupCode="TAC" hasId="true"
							pathPrefix="taxCategory"						
							showOnlyLabelWithId="true"
							cssClass="form-control disabled"  />
					</div>
					
					
			<h4>Depend on Factors</h4>
			<div class="form-group">
				<div class="col-xs-6">
					<form:select path="" id="eventMapSelectedId" multiple="multiple" cssClass="form-control height-200 disabled" disabled="true">
						<c:forEach items="${slabDpendent}" var="slabDpendentData">
							<form:option value="${slabDpendentData.lookUpId}" label="${slabDpendentData.lookUpDesc}"></form:option>
						</c:forEach> 
					</form:select>				
					<form:hidden path="" value="1"/>
				</div>
				<div class="col-xs-1">
					<button id="right" class="btn btn-blue-2 btn-block disabled" type="button" disabled="disabled">
						<i class="fa fa-angle-right"></i>
					</button>
					<button id="rightall" class="btn btn-blue-3 btn-block disabled" type="button" disabled="disabled">
						<i class="fa fa-angle-double-right"></i>
					</button>
					<button id="left" class="btn btn-blue-2 btn-block disabled" type="button" disabled="disabled">
						<i class="fa fa-angle-left"></i>
					</button>
					<button id="leftall" class="btn btn-blue-3 btn-block disabled" type="button" disabled="disabled">
						<i class="fa fa-angle-double-left"></i>
					</button>
				</div>
				<div class="col-xs-5">
					<form:select multiple="multiple" class="form-control height-200 disabled" path="taxDetIdList" id="taxDetMasList" disabled="true">
						<c:forEach items="${slabDpendent}" var="slabDpendentData">
							<c:forEach items="${tbTaxMas.taxDetIdList}" var="taxDetIdList">
								<c:if test="${slabDpendentData.lookUpId eq taxDetIdList}">
									<form:option value="${slabDpendentData.lookUpId}" label="${slabDpendentData.lookUpDesc}"></form:option>
								</c:if>								
							</c:forEach> 
						</c:forEach> 
					</form:select>
					<form:hidden path="" value="1" />
				</div>
			</div>
			<!-- ACCOUNT HEAD MAPPING---------------------------------------------------------------------------->
			<div class="form-group">				
				<label class="col-sm-2 control-label required-control" for="fund"><spring:message code='account.fund'/></label>
				<div class="col-sm-4">
					<form:select id="fund" class="form-control" path="taxHeadMapping.fundId" disabled="true">
						<form:option value=""><spring:message code='master.selectDropDwn'/></form:option>
						<c:forEach items="${fund}" var="fund">
							<form:option value="${fund.key }">${fund.value }</form:option>
						</c:forEach>
					</form:select>
				</div>
				<label class="col-sm-2 control-label required-control" for="function"><spring:message code='account.function'/></label>
				<div class="col-sm-4">
					<form:select id="function" class="form-control" path="taxHeadMapping.functionId" disabled="true">
						<form:option value=""><spring:message code='master.selectDropDwn'/></form:option>
						<c:forEach items="${function}" var="function">
							<form:option value="${function.key }">${function.value }</form:option>
						</c:forEach>
					</form:select>
				</div>
				
			</div>		
			<div class="form-group">				
				<label class="col-sm-2 control-label required-control" for="primaryHead"><spring:message code='account.pahc'/></label>
				<div class="col-sm-4">
					<form:select id="primaryHead" class="form-control" path="taxHeadMapping.pacHeadId" disabled="true">
						<form:option value=""><spring:message code='master.selectDropDwn'/></form:option>
						<c:forEach items="${primaryHead}" var="primaryHead">
							<form:option value="${primaryHead.key }">${primaryHead.value }</form:option>
						</c:forEach>
					</form:select>
				</div>
				<label class="col-sm-2 control-label required-control" for="secondaryHead"><spring:message code='account.sahc'/></label>
				<div class="col-sm-4">
					<form:select id="secondaryHead" class="form-control" path="taxHeadMapping.sacHeadId" disabled="true">
						<form:option value=""><spring:message code='master.selectDropDwn'/></form:option>
						<c:forEach items="${secondaryHead}" var="secondaryHead">
							<form:option value="${secondaryHead.key }">${secondaryHead.value }</form:option>
						</c:forEach>
					</form:select>
				</div>
			</div>		
			<div class="form-group govtTax">				
				<label class="col-sm-2 control-label required-control" for="primaryHeadGovt"><spring:message code='account.pahc'/></label>
				<div class="col-sm-4">
					<form:select id="primaryHeadGovt" class="form-control" path="taxHeadMapping.pacHeadIdLib" disabled="true">
						<form:option value=""><spring:message code='master.selectDropDwn'/></form:option>
						<c:forEach items="${primaryHead}" var="primaryHead">
							<form:option value="${primaryHead.key }">${primaryHead.value }</form:option>
						</c:forEach>
					</form:select>
				</div>
				<label class="col-sm-2 control-label required-control" for="secondaryHeadGovt"><spring:message code='account.sahc'/></label>
				<div class="col-sm-4">
					<form:select id="secondaryHeadGovt" class="form-control" path="taxHeadMapping.sacHeadIdLib" disabled="true">
						<form:option value=""><spring:message code='master.selectDropDwn'/></form:option>
						<c:forEach items="${secondaryHeadGovt}" var="secondaryHeadGovt">
							<form:option value="${secondaryHeadGovt.key }">${secondaryHeadGovt.value }</form:option>
						</c:forEach>
					</form:select>
				</div>
			</div>
			
			<div class="text-center padding-bottom-20">
				<button type="button" class="btn btn-primary" onclick="window.location.href='TaxMaster.html'">Back</button>
			</div>
		</form:form>
</div>
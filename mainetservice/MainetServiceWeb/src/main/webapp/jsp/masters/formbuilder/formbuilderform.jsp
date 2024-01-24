<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/masters/formbuilder/formbuilder.js"></script>
<script src="js/tableHeadFixer.js"></script>


	<!-- Start info box -->
	<div class="widget" id="widget">
		<div class="widget-header">
			<h2><spring:message code="common.master.form.builder" text="Form Builder"/></h2>
			<div class="additional-btn">
			</div>
		</div>
		<div class="widget-content padding">
		<div class="mand-label clearfix"><span><spring:message code="contract.breadcrumb.fieldwith" text="Field with"/> <i class="text-red-1">*</i> is mandatory</span></div>
        
		<c:url value="${saveAction}" var="url_form_submit" />
		<form:form method="post" action="${url_form_submit}" class="form-horizontal" name="scrutinyLabelMasterForm" id="scrutinyLabelMasterForm" commandName="formBuildersDto">
			
			<div class="warning-div alert alert-danger alert-dismissible hide" id="errorDivScrutiny">
				<button type="button" class="close" aria-label="Close" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button>
				
				<ul><li><i class='fa fa-exclamation-circle'></i>&nbsp;<form:errors path="*"/></li></ul>
				<script>
					$(".warning-div ul").each(function () {
					    var lines = $(this).html().split("<br>");
					    $(this).html('<li>' + lines.join("</li><li><i class='fa fa-exclamation-circle'></i>&nbsp;") + '</li>');						
					});
		  			$('html,body').animate({ scrollTop: 0 }, 'slow');
		  		</script>
			</div>
			
			<c:if test="${mode != 'create'}">
				<!-- Store data in hidden fields in order to be POST even if the field is disabled -->
				<form:hidden path="scrutinyLabels.smShortDesc" />
			</c:if>
			<div class="form-group">
				<label class="col-sm-2 control-label required-control"><spring:message code="common.master.service.name" text="Service Name"/></label>
				<div class="col-sm-4">
					<form:select path="scrutinyLabels.smShortDesc" class="form-control" id="scrutinyId" disabled="${mode != 'create' ? true: false}">
						<form:option value=""><spring:message code="contract.label.select" text="Select"/></form:option>
						<c:forEach items="${serviceList}" var="serviceData">
							<form:option value="${serviceData.smShortDesc }">${serviceData.smServiceName }</form:option>
						</c:forEach>
					</form:select>
				</div>
			</div>
			<h4><spring:message code="common.master.form.builder" text="Form Builder"/></h4>
			<c:set var="c" value="0" scope="page" />
			<c:set var="d" value="0" scope="page" />
			<c:set var="rowNumVisible" value="-1" scope="page" />
			<div class="table-responsive">
				<table class="table table-bordered table-striped appendableClass" id="scrutinyLabelTbl">
					<c:choose>
						<c:when test="${fn:length(formBuildersDto.scrutinyLabelsList) > 0}">
							<c:forEach items="${formBuildersDto.scrutinyLabelsList}" var="scrutinyLabels" varStatus="count">
								<tbody>
									<tr>
										<th width="50"><spring:message code="common.master.seq" text="Sequence"/></th>
                    					<th width="150" class="required-control"><spring:message code="common.master.role" text="Role"/></th>
                    					<th  class="required-control"><spring:message code="common.master.english.model" text="English Label"/></th>
                    					<th  class="required-control"><spring:message code="common.master.regional.label" text="Regional Label"/></th>
										<th width="150" class="required-control"><spring:message code="common.master.datatype" text="Data Type"/></th>
                    					<th ><spring:message code="common.master.validation" text="Validation"/></th>
										<th  width="105" class="vertical-align-middle text-center">
											<a href="javascript:void(0);" class="addScrutinyLabel btn btn-success btn-xs"><i class="fa fa-plus-circle"></i></a>
											<a href="javascript:void(0);" class="deleteScrutinyLabel btn btn-danger btn-xs"><i class="fa fa-minus-circle"></i></a>
										</th>
									</tr>
									<tr>
										<td><form:input path="scrutinyLabelsList[${d}].slPosition"  class="form-control"  id="slPosition${d}" readonly="true"/>
											<form:hidden path="scrutinyLabelsList[${d}].levels" value="1" class="form-control"  id="levels${d}"/></td>
										<td>
										<form:select path="scrutinyLabelsList[${d}].gmId" class="form-control" id="gmId${d}">
												<form:option value=""><spring:message code="contract.label.select" text="Select"/></form:option>
												<c:forEach items="${groupDataList}" var="groupData">
													<form:option value="${groupData.gmId}">${groupData.grCode}</form:option>
												</c:forEach>
											</form:select>
										</td>
										<td><form:hidden path="scrutinyLabelsList[${d}].slFormMode"  class="form-control" id="slFormMode${d}" value="A" />
										<form:input path="scrutinyLabelsList[${d}].slLabel"  class="form-control"  id="slLabel${d}" onblur="validateLabel(this, ${d})"/></td>
										<td><form:input path="scrutinyLabelsList[${d}].slLabelMar"  class="form-control"  id="slLabelMar${d}"/></td>								
										<td><form:select path="scrutinyLabelsList[${d}].slDatatype" class="form-control" id="slDatatype${d}" onchange="check(this);">
												<form:option value=""><spring:message code="contract.label.select" text="Select"/></form:option>
												<c:forEach items="${lookUpList}" var="lookUp">
													<form:option value="${lookUp.lookUpDesc}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>											
											</form:select>
										</td>
										<td ><form:input path="scrutinyLabelsList[${d}].slPreValidation"  class="form-control" id="slPreValidation${d}" readonly="true"/>
											<form:hidden path="scrutinyLabelsList[${d}].slLabelId" id="slLabelId${d}"/></td>
										<td></td>			
									</tr>
								</tbody>
								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tbody>
								<tr>
									<th width="50"><spring:message code="common.master.seq" text="Sequence"/></th>
                    				<th width="150" class="required-control"><spring:message code="common.master.role" text="Role"/></th>
									<th class="required-control"><spring:message code="common.master.english.model" text="English Label"/></th>
                    				<th  class="required-control"><spring:message code="common.master.regional.label" text="Regional Label'"/></th>
									<th width="150" class="required-control"><spring:message code="common.master.datatype" text="Data Type"/></th>
                    				<th ><spring:message code="common.master.validation" text="Validation"/></th>
									<th  width="105" class="vertical-align-middle text-center">
										<a href="javascript:void(0);" class="addScrutinyLabel btn btn-success btn-xs"><i class="fa fa-plus-circle"></i></a>
										<a href="javascript:void(0);" class="deleteScrutinyLabel btn btn-danger btn-xs"><i class="fa fa-minus-circle"></i></a>
									</th>
								</tr>
								<tr>
									<td><form:input path="scrutinyLabelsList[${d}].slPosition"  class="form-control"  id="slPosition${d}" value="1" readonly="true"/>
									<form:hidden path="scrutinyLabelsList[${d}].levels" value="1" class="form-control"  id="levels${d}"/></td>
										
									<td><form:select path="scrutinyLabelsList[${d}].gmId" class="form-control" id="gmId${d}">
											<form:option value=""><spring:message code="contract.label.select" text="Select"/></form:option>
											<c:forEach items="${groupDataList}" var="groupData">
												<form:option value="${groupData.gmId}">${groupData.grCode}</form:option>
											</c:forEach>
										</form:select>
									</td>
									<td><form:hidden path="scrutinyLabelsList[${d}].slFormMode" value="A" class="form-control" id="slFormMode${d}"/>
										<form:input path="scrutinyLabelsList[${d}].slLabel"  class="form-control"  id="slLabel${d}" onblur="validateLabel(this, ${d})"/></td>
									<td><form:input path="scrutinyLabelsList[${d}].slLabelMar"  class="form-control"  id="slLabelMar${d}"/></td>								
									<td><form:select path="scrutinyLabelsList[${d}].slDatatype" class="form-control" id="slDatatype${d}" onchange="check(this);">
											<form:option value="">Select</form:option>
											<c:forEach items="${lookUpList}" var="lookUp">
												<form:option value="${lookUp.lookUpDesc}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>											
										</form:select>
									</td>
									<td><form:input path="scrutinyLabelsList[${d}].slPreValidation"  class="form-control" id="slPreValidation${d}" readonly="true"/>
										<form:hidden path="scrutinyLabelsList[${d}].slLabelId" id="slLabelId${d}"/></td>
									<td></td>	
								</tr>
							</tbody>
							<c:set var="d" value="${d + 1}" scope="page" />
						</c:otherwise>
					</c:choose>					
				</table>
				
				<div class="text-center padding-top-10">
		              <button class="btn btn-success btn-submit" type="button" onclick="submitScrutinyLabelForm(this)"><spring:message code="common.master.save" text="Save"/></button>
		              <button class="btn btn-danger" type="button" onclick="window.location.href='FormBuilder.html'" ><spring:message code="contract.label.Back" text="Back"/></button>
            	</div>
			</div>
		</form:form>
	</div>
</div>

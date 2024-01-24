<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% 	response.setContentType("text/html; charset=utf-8"); %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="common-popup">
<div class="content">
	<div class="widget margin-bottom-0">
		<div class="widget-content padding">	
			<div class="error-div alert alert-danger alert-dismissible" style="display:none;" id="errorDivOrgDet"></div>
			<form:form method="post" action="" name="designationBean" id="orgDetForm" class="form-horizontal" commandName="designationBean" modelAttribute="designationBean">
				<div class="form-group">
				<input type="hidden" value="${errormsg}" id="errormsg"/>
					<label for="desgnId" class="col-sm-4 control-label"><spring:message code="master.orgDesignationMap.Des" text="Designation"/></label>
					<div class="col-sm-8">
						<form:select id="desgnId" path="dsgid" cssClass="form-control" onchange="setDesignationData(this)">
							<form:option value=""><spring:message code="master.orgDesignationMap.selDes" text="Select Designation"/></form:option>
							<c:forEach items="${desgList}" var="desgList">
							 <c:if test="${userSession.languageId eq 1}">
								<form:option value="${desgList.dsgid}" value1="${desgList.dsgshortname}">${desgList.dsgname}</form:option>
							</c:if>
							<c:if test="${userSession.languageId eq 2}">
							<form:option value="${desgList.dsgid}" value1="${desgList.dsgshortname}">${desgList.dsgnameReg}</form:option>
							</c:if>
							</c:forEach>
						</form:select>
 						<form:errors id="tbDepartment_cpdIdDcg_errors" path="dsgid" cssClass="label label-danger" />
						<form:hidden path="dsgname" id="dsgname"/>
						<form:hidden path="dsgshortname" id="dsgshortname"/>
					</div>
				</div>
				<div class="text-center">		
					<input type="button" class="btn btn-success btn-submit" value="<spring:message code="save"/>" onclick="saveDesignationData(this);"/>
				</div>
			</form:form>
		</div>
</div>
</div>
</div>
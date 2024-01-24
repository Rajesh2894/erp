<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<script type="text/javascript" src="js/workflow/workFlowType/home.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated slideInDown">
   <div class="widget">
	<div class="widget-header">
        <h2><spring:message code="workflow.grid.workflowmaster" text="Work Flow Master"/></h2>
        <apptags:helpDoc url="WorkFlowType.html"></apptags:helpDoc>
	  </div>
	 <div class="widget-content padding">
	     <div class="mand-label clearfix">
		<span><spring:message code="contract.breadcrumb.fieldwith"
				text="Field with" /> <i class="text-red-1">*</i> <spring:message
				code="common.master.mandatory" text="is mandatory" /> </span>
		 </div>
		      <div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
				<ul>
					<li><label id="errorId"></label></li>
				</ul>
			</div>
  <form:form action="WorkFlowType.html" method="POST" name="workFlowTypeForm" class="form-horizontal"  id="workFlowTypeForm">
    <form:hidden path="orgId" id="orgId"/>
		    <div class="form-group">
                 <label class="col-sm-2 control-label required-control"><spring:message code="workflow.type.dept.name" text="Department Name" /></label>
	                 <div class="col-sm-4">
	                   <form:select path="workFlowMasDTO.deptId" id="deptId" onchange="getServices(this);"  class="form-control mandColorClass chosen-select-no-results">
								<form:option value="0"><spring:message code="selectdropdown" text="select"/></form:option>
								<c:forEach items="${command.deptList}" var="objArray">
								<c:if test="${userSession.languageId eq 1}">
								<form:option value="${objArray[0]}" label="${objArray[1]}"></form:option>
								</c:if>
								<c:if test="${userSession.languageId eq 2}">
									<form:option value="${objArray[0]}" label="${objArray[2]}"></form:option>
								</c:if>
								</c:forEach>
					   </form:select>
	                 </div>
	          <label class="col-sm-2 control-label"><spring:message code="workflow.type.service.name" text="Service Name"/></label>
				<div class="col-sm-4">
						<form:select path="workFlowMasDTO.serviceId" id="serviceId" class="form-control chosen-select-no-results">
							<form:option value="0"><spring:message code="selectdropdown" text="Select"/></form:option>
						</form:select>
				</div>           
	    </div>

	    
                <input type="hidden" value="" id="compHidden"/>
                <input type="hidden" value="" id="compId"/>
                <input type="hidden" value="" id="deptIdHidden"/>
                <input type="hidden" value="" id="serviceIdHidden"/>
                <input type="hidden" value="${userSession.languageId}" id="langId"/>
                
              <div class="text-center padding-bottom-20">
             <button type="button" class="btn btn-blue-2" id="searchWorkFlow" title="Search"><spring:message code="master.search" text="Search" /></button>
			  <button type="Reset" class="btn btn-warning" id="resetWorkFlow" onclick="back();" title="Reset"><spring:message code="bt.clear"/></button>
             <button type="button" class="btn btn-success" id="addFlowLink" title="Add"><i class="fa fa-plus-circle"></i>&nbsp;<spring:message code="master.addButton" text="Add"/></button></div>
           <div id="" align="center">
                <table id="flowGrid"></table>
                <div id="flowPager"></div>
           </div>
         </form:form>
	          </div>
      </div>
</div>

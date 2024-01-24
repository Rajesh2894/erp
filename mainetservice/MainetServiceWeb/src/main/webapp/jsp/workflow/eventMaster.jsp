<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%
   response.setContentType("text/html; charset=utf-8");
   %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/instafilta.min.js"></script>
<script type="text/javascript" src="js/mainet/ui/jquery-ui.js"></script>
<script type="text/javascript" src="js/workflow/eventMaster.js"></script>
<div class="error-div alert alert-danger alert-dismissible" id="errorDivId">
</div>

<style>
#eventMapSelectedId,#eventMapId  { overflow-x: scroll;}
</style>

<form:form action="EventMaster.html" method="POST" name="eventMasterFormName" id="eventMasterFormId" class="form-horizontal" modelAttribute="servicesEventDTO">
   <input type="hidden" id="mode" value="${mode}"/>
   <div class="form-group">
      <label class="col-sm-2 control-label required-control">
         <spring:message code="workflow.form.field.dep" />
      </label>
      <div class="col-sm-4">
         <form:select path="deptId.dpDeptid" id="departmentId" class="form-control" disabled="true">
            <form:option value="0">
               <spring:message code="workflow.form.select.department" />
            </form:option>
            <c:forEach items="${deptList}" var="deptLst">
               <form:option value="${deptLst.dpDeptid }" code="${deptLst.dpDeptcode}">${deptLst.dpDeptdesc}</form:option>
            </c:forEach>
         </form:select>
         <form:hidden path="deptId.dpDeptid"/>
      </div>
      <form:hidden path="orgId.orgid"/>
      <label class="col-sm-2 control-label required-control">
         <spring:message code="workflow.form.field.service" />
      </label>
      <div class="col-sm-4">
         <form:select path="smServiceId" id="serviceId" class="form-control" disabled="true">
            <form:option value="0">
               <spring:message code="workflow.form.select.service"/>
            </form:option>
            <c:forEach items="${serviceList}" var="objArray">
               <form:option value="${objArray[0]}" label="${objArray[1]}">${objArray[1]}</form:option>
            </c:forEach>
         </form:select>
         <form:hidden path="smServiceId"/>
      </div>
   </div>
   <h4><spring:message code="workflow.div.header1" text="Select Multiple Events"/></h4>
   <div class="form-group has-feedback" id="ex1">
      <div class="col-xs-6 padding-bottom-15">
         <input type="text" id="exf1" placeholder="<spring:message code="workflow.search.event" text="Search Event"/>" class="form-control">
         <span class="glyphicon glyphicon-search form-control-feedback"></span>	
      </div>
      <div class="additional-btn"> 
         <a href="#" data-toggle="tooltip" data-original-title="<spring:message code="workflow.multiple.events" text="Select multiple events by Ctrl+Select"/>">
         <i class="fa fa-question-circle fa-lg"></i></a> 
      </div>
      <div class="clearfix"></div>
      <div class="col-xs-6">
         <form:select path="eventMapSelectedId" id="eventMapSelectedId" class="form-control height-300">
            <c:forEach items="${eventsNotSelected}" var="objArray">
               <form:option value="${objArray[0]}" label="${objArray[1]}" class="instafilta-target"></form:option>
            </c:forEach>
         </form:select>
      </div>
      <div class="col-xs-1">
         <button type="button" class="btn btn-blue-2 btn-block" id="moveright" onclick="move_list_items('eventMapSelectedId','eventMapId');"><i class="fa fa-angle-right"></i></button>
         <button type="button" class="btn btn-blue-3 btn-block" id="moverightall" onclick="move_list_items_all('eventMapSelectedId','eventMapId');"><i class="fa fa-angle-double-right"></i></button>
         <button type="button" class="btn btn-blue-2 btn-block" id="moveleft" onclick="move_list_items('eventMapId','eventMapSelectedId');"><i class="fa fa-angle-left"></i></button>
         <button type="button" class="btn btn-blue-3 btn-block" id="moveleftall" onclick="move_list_items_all('eventMapId','eventMapSelectedId');"><i class="fa fa-angle-double-left"></i></button>
      </div>
      <div class="col-xs-5">
         <form:select path="eventMapId" id="eventMapId" multiple="multiple" cssClass="form-control height-300">
            <c:forEach items="${eventsSelectedLst}" var="evntselected">
               <form:option value="${evntselected[0]}" label="${evntselected[1]}" class="instafilta-target">
               </form:option>
            </c:forEach>
         </form:select>
      </div>
   </div>
   <div class="text-center">
   	<c:if test="${mode != 'VIEW'}">
   		<input type="button" value="<spring:message code="master.save" text="Save"/>" class="btn btn-success btn-submit" id="submitBtnId"/>
   	</c:if>     
<%--       <c:if test="${mode == 'CREATE'}">
         <input type="button" class="btn btn-warning" value="<spring:message code="reset.msg"/>" onclick="resetEvent()">
      </c:if> --%>
       <%-- <c:if test="${mode == 'EDIT'}">
         <input type="reset"class="btn btn-warning" value="<spring:message code="" text="RESET"/>">
      </c:if> --%>
      <input type="button" class="btn btn-danger back-btn" value="<spring:message code="back.msg" text="Back"/>" onclick="window.location.href='EventMaster.html'" />
   </div>
</form:form>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<% response.setContentType("text/html; charset=utf-8"); %>
<script type="text/javascript" src="js/masters/complaint/complaintList.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated slideInDown">
      <div class="widget">
	     <div class="widget-header">
	         <h2><spring:message code="master.complainttype.master"/></h2>
	          <apptags:helpDoc url="Complaint.html"></apptags:helpDoc>
	        </div>
 
 	        <div class="widget-content padding">
	        <div class="mand-label clearfix"><span><spring:message code="account.common.mandmsg"/> <i class="text-red-1">*</i> <spring:message code="account.common.mandmsg1"/></span></div>        
  			<form action="Complaint.html" method="POST" name="complaintForm" class="form-horizontal"  id="complaintForm">
 			<div class="text-center padding-bottom-10"><button type="button" class="btn btn-success btn-submit" id="addCompLink"><i class="fa fa-plus-circle"></i>&nbsp;<spring:message code="master.complaint.add.complaint"/></button></div>
			<div id="" align="center">
			   <table id="compGrid"></table>
			   <div id="compPager"></div>
			     </div>
			</form>
	          </div>
      </div>
</div>
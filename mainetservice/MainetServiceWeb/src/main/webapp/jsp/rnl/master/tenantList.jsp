<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<script src="js/rnl/master/tenantList.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>



<div  class="content animated slideInDown">
      <div class="widget">
	        <div class="widget-header">
	            <h2><spring:message code="rnl.tenant.master" text="Tenant Master"></spring:message></h2>
	            <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
	        </div>
	        <div class="widget-content padding">
	             <div class="mand-label clearfix"><span><spring:message code="master.field.message"/><i class="text-red-1">*</i> <spring:message code="master.field.mandatory.message"/></span></div>
			          <form action="" method="POST" class="form-horizontal">
			               <div class="text-center padding-bottom-10">
				              <button type="button" class="btn btn-blue-2" id="addTenantLink"><i class="fa fa-plus-circle"></i>&nbsp;<spring:message code="rnl.master.addtenant" text="Add Tenant"></spring:message></button></div>
				            <div id="" align="center">
					                <table id="tenantGrid"></table>
					                <div id="tenantPager"></div>
				            </div>
			           </form>
	          </div>
      </div>
</div>
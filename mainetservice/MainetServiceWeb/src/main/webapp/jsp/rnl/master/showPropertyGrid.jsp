<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<script src="js/rnl/master/showpropertyGrid.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>



<div  class="content animated slideInDown">
      <div class="widget">
	        <div class="widget-header">
	            <h2><spring:message code="rl.master.property.form.name"/></h2>
	            <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
	        </div>
	        <div class="widget-content padding">
	             <div class="mand-label clearfix"><span><spring:message code="master.field.message"/><i class="text-red-1">*</i> <spring:message code="master.field.mandatory.message"/></span></div>
	                  <div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
					  <ul>
						<li><label id="errorId"></label></li>
					</ul>
			    </div>
			          <form action="" method="POST" class="form-horizontal">
			                 <div id="" align="center">
					                <table id="propertyGrid"></table>
					                <div id="propertyPager"></div>
				            </div>
				             <div class="text-center padding-top-10">
				              <input type="button" id="backBtn" class="btn btn-danger" onclick="back()" value="<spring:message code="bt.backBtn"/>" />
				             </div>
			           </form>
			           
	          </div>
	          
      </div>
</div>
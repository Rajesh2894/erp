<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<script src="js/rnl/master/estateList.js"></script>


<apptags:breadcrumb></apptags:breadcrumb>

<div  class="content animated slideInDown">
      <div class="widget">
	        <div class="widget-header">
	            <h2><spring:message code="master.estate.form.name"/></h2>
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
			            
				            <div class="form-group">
					             <label class="control-label col-sm-2" for="locationId"><spring:message code="estate.label.Location" /></label>
					              <div class="col-sm-4">
					                <select class="chosen-select-no-results form-control" id="locationId">
					                  <option value=""><spring:message code="selectdropdown" /></option>
						                 <c:forEach items="${locationList}" var="objArray">
						                     <option value="${objArray[0]}"><c:choose><c:when test="${userSession.languageId eq 2}">${objArray[2]}</c:when><c:otherwise>${objArray[1]}</c:otherwise></c:choose></option>
						                </c:forEach> 
					                </select>
					              </div>
					              <label for ="estateId" class="control-label col-sm-2"><spring:message code="estate.label.name" /></label>
					              <div class="col-sm-4">
					                <select class="chosen-select-no-results form-control" id="estateId">
					                   <option value=""><spring:message code="selectdropdown" /></option>
					                 <%-- <option value="1">Samrudhi Venture Park</option> --%>
					                </select>
					              </div>
					        </div>
				            <input type="hidden" value="ALL" id="locHidden"/>
				            <input type="hidden" value="ALL" id="estateHidden"/>
				            <div class="text-center padding-bottom-10">
				              <button type="button" class="btn btn-success" id="btnsearch"><i class="fa fa-search"></i>&nbsp;<spring:message code="bt.search" text="Search"/></button>
				              
							<a role="button" class="btn btn-warning" href="EstateMaster.html"><spring:message
							code="bt.clear" text="Reset" /></a>
				              <button type="button" class="btn btn-blue-2" id="addEstateLink"><i class="fa fa-plus-circle"></i>&nbsp;<spring:message code="bt.add"/></button></div>
				            <div id="" align="center">
					                <table id="estateGrid"></table>
					                <div id="estatePager"></div>
				            </div>
			           </form>
	          </div>
      </div>
</div>
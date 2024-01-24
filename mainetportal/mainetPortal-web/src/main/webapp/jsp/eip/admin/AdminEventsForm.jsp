<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script src="js/mainet/validation.js"></script>
<script>
	$(function(e) {
		prepareTags();
	});
</script>

	
	
	<div class="content">
      <div class="widget">
        <div class="widget-header">
          <h2>Events <strong>Master</strong></h2>
        </div>
        <div class="widget-content padding">
          
          
          <form:form method="post" action="AdminEventsForm.html"
		         name="frmAdminEventsForm" id="frmAdminEventsForm" class="form-horizontal">

		     <jsp:include page="/jsp/tiles/validationerror.jsp" />
            <div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="admin.eventsFormDate" /> <span class="mand">*</span></label>
                <div class="col-sm-4"><apptags:dateField fieldclass="datepicker"
						datePath="news.newsDate" readonly="false" cssClass="form-control mandClassColor" /></div>
              </div>
            
             <div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="admin.eventsFormHeadlineEng" /> <span class="mand">*</span></label>
                <div class="col-sm-10"><apptags:inputField fieldPath="news.shortDescEn" maxlegnth="250" cssClass="form-control mandClassColor input2"></apptags:inputField></div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="admin.eventsFormHeadlineReg" /> <span class="mand">*</span></label>
                <div class="col-sm-10"><apptags:inputField fieldPath="news.shortDescReg" maxlegnth="1000" cssClass="form-control mandClassColor input2">
				</apptags:inputField></div>
            </div>
            
           
            <div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="admin.eventsFormDetailDescEng" /> <span class="mand">*</span></label>
                <div class="col-sm-10"><form:textarea path="news.longDescEn" maxlength="4000" cssClass="form-control mandClassColor input2"/></div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="admin.eventsFormDetailDescReg" />  <span class="mand">*</span></label>
                <div class="col-sm-10"><form:textarea path="news.longDescReg" maxlength="2000" cssClass="form-control mandClassColor input2"/></div>
            </div>
            <div class="text-center padding-bottom-20">
			<apptags:submitButton entityLabelCode="Admin Events"
				successUrl="AdminEvents.html"  cssClass="btn btn-success"/>
			<apptags:resetButton cssClass="btn btn-default" />
			<apptags:backButton url="AdminEvents.html" cssClass="btn btn-primary" />
		</div>
            
          </form:form>
          </div>
          </div>
          </div>

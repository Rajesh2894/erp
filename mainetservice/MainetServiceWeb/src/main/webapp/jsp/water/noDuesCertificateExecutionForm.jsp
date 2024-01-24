 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/water/noDuesCertificate.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content"> 
  <!-- Start info box -->
  <div class="widget">
    <div class="widget-header">
   
      <h2><spring:message code="water.lable.nodues.execution.noDuesService" /></h2>
      <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
    </div>
    <div class="widget-content padding">
      <div class="mand-label clearfix"><span><spring:message code="water.fieldwith"/><i class="text-red-1">*</i> <spring:message code="water.ismandtry"/></span></div>
      <form:form action="NoDuesCertificateController.html" method="get" class="form-horizontal">
                <jsp:include page="/jsp/tiles/validationerror.jsp" />
               <div class="msg-error alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
				<ul>
					<li><label id="errorDivId"></label></li>
				</ul>
			  </div>
        <h4 class="margin-top-0"><label><spring:message code="water.lable.nodues.execution.applicantdetail" /></label></h4>
        <div class="form-group">
          <label class="col-sm-2 control-label"><spring:message code="water.lable.nodues.execution.serviceName"/></label>
          <div class="col-sm-4">
            <form:input path="serviceName" name="serviceName" type="text" class="form-control" disabled="true"></form:input>
          </div>
          <label class="col-sm-2 control-label"><spring:message code="water.lable.nodues.execution.applicationId"/></label>
          <div class="col-sm-4">
            <form:input path="apmApplicationId" name="" type="text" class="form-control" disabled="true"></form:input>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label"><spring:message code="water.lable.nodues.execution.applicatName"/></label>
          <div class="col-sm-4">
            <form:input path="applicantName" name="" type="text" class="form-control" disabled="true"></form:input>
          </div>
          <label class="col-sm-2 control-label"><spring:message code="water.lable.nodues.execution.applicationDate"/></label>
          <div class="col-sm-4">
            <form:input path="applicantDate" name="" type="text" class="form-control" disabled="true"></form:input>
          </div>
        </div>
        <h4><label><spring:message code="water.lable.nodues.execution.exedetail" /></label></h4>
        <div class="form-group">
			<div class="col-sm-6 radio-inline">
				<label class="col-sm-6"><form:radiobutton path="nodueCertiDTO.noDuesExecFlag" name="executionFlag" value="Y" id="executionFlag" />
				<spring:message code="water.lable.nodues.execution.executionDeatil"/></label>
			</div>
          <label class="col-sm-2 control-label"><spring:message code="water.lable.nodues.execution.noDuesDate"/></label>
          <div class="col-sm-4">
            <div class="input-group">
              <form:input path="nodueCertiDTO.noDuesCertiDate" name="noDuesCertiDate" type="text" class="form-control" id="datepicker2"></form:input>
              <label class="input-group-addon" for="datepicker2"><i class="fa fa-calendar"></i></label>
            </div>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label"><spring:message code="water.lable.nodues.execution.approveBy"/></label>
          <div class="col-sm-4"><form:input path="nodueCertiDTO.approveBy"  id="approveBy" name="approveBy" type="text" value='${command.approveEmpName}' class="form-control"></form:input></div>
          <label class="col-sm-2 control-label"><spring:message code="water.lable.nodues.execution.approveDate"/></label>
          <div class="col-sm-4">
          	
            <div class="input-group">
              <form:input path="nodueCertiDTO.approveDate" name="approveDate" type="text" class="form-control" id="datepicker"></form:input>
              <label class="input-group-addon" for="datepicker"><i class="fa fa-calendar"></i></label>
            </div>
          </div>
        </div>
        <div class="text-center">
          <button type="button" class="btn btn-success btn-submit"  onclick="saveNoDuesCertificateExeForm(this)" id="submit"><spring:message code="water.nodues.submit"/></button>
		  <button type="button" class="btn btn-danger" onclick="window.location.href='AdminHome.html'" ><spring:message code="water.nodues.cancel"/></button>
        </div>
  
      </form:form>
    </div>
  </div>
  <!-- End of info box --> 
</div>
<!-- End Content here --> 
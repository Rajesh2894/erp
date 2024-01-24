<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<div id="Applicant" class="panel-collapse collapse in">
                <div class="panel-body">
                  <div class="form-group">
                    <label class="col-sm-2 control-label required-control" for="Title1"><spring:message code="care.title" /></label>
                    <div class="col-sm-4">
                       <form:select name="" class="form-control" id="Title1" path="careApplicantDetails.applicantTitle">
                        <form:option value=""><spring:message code="care.select" /></form:option>
                        <c:forEach var="title" items="${titles}">
                          <form:option value="${title.cpdDesc}">${title.cpdDesc}</form:option>
                        </c:forEach>
                      </form:select>
                    </div>
                    <label class="col-sm-2 control-label required-control" for="FirstName1"><spring:message code="care.firstname" /></label>
                    <div class="col-sm-4">
                      <form:input name="firstName" type="text" class="form-control" id="FirstName1" path="careApplicantDetails.applicantFirstName" readonly="true"></form:input>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="MiddleName" class="col-sm-2 control-label" id="MiddleName"><spring:message code="care.middlename" /></label>
                    <div class="col-sm-4">
                      <form:input name="MiddleName" type="text" class="form-control" id="MiddleName" path="careApplicantDetails.applicantMiddleName"></form:input>
                    </div>
                    <label class="col-sm-2 control-label required-control" for="LastName1"><spring:message code="care.lastname" /></label>
                    <div class="col-sm-4">
                      <form:input name="lastName" type="text" class="form-control" id="LastName1" path="careApplicantDetails.applicantLastName" readonly="true"></form:input>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label required-control" for="Gender"><spring:message code="care.gender" /></label>
                    <div class="col-sm-4">
                      <form:select class="form-control" name="Gender" id="Gender" path="careApplicantDetails.gender">
                        <form:option value=""><spring:message code="care.select" /></form:option>
                        <c:forEach var="gen" items="${gender}">
                          <form:option value="${gen}">${gen}</form:option>
                        </c:forEach>
                      </form:select>
                    </div>
                    <label class="col-sm-2 control-label required-control" for="MobileNumber"><spring:message code="care.mobilenumber" /></label>
                    <div class="col-sm-4">
                      <form:input name="mobileNo" type="text" class="form-control hasNumber" id="MobileNumber" path="careApplicantDetails.mobileNo" maxlength="10" readonly="true"></form:input>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label" for="EmailID"><spring:message code="care.emailid" /></label>
                    <div class="col-sm-4">
                      <form:input name="" type="email" class="form-control" id="EmailID" path="careApplicantDetails.emailId"></form:input>
                    </div>
                    <label class="col-sm-2 control-label" for="FlatNo"><spring:message code="care.flatnumber" /></label>
                    <div class="col-sm-4">
                      <form:input name="" type="text" class="form-control" id="FlatNo" path="careApplicantDetails.flatBuildingNo"></form:input>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label" for="BuildingName"><spring:message code="care.buildingname" /></label>
                    <div class="col-sm-4">
                      <form:input name="" type="text" class="form-control" id="BuildingName" path="careApplicantDetails.buildingName"></form:input>
                    </div>
                    <label class="col-sm-2 control-label" for="RoadName"><spring:message code="care.roadname" /></label>
                    <div class="col-sm-4">
                      <form:input name="" type="text" class="form-control" id="RoadName" path="careApplicantDetails.roadName"></form:input>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label" for="BlockName" ><spring:message code="care.blockname" /></label>
                    <div class="col-sm-4">
                      <form:input name="" type="text" class="form-control" id="BlockName" path="careApplicantDetails.blockName"></form:input>
                    </div>
                    <label class="col-sm-2 control-label  required-control" for="AreaName"><spring:message code="care.areaname" /></label>
                    <div class="col-sm-4">
                      <form:input name="" type="text" class="form-control" id="AreaName" path="careApplicantDetails.areaName"></form:input>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label required-control" for="Village"><spring:message code="care.village" /></label>
                    <div class="col-sm-4">
                      <form:input name="" type="text" class="form-control" id="Village" path="careApplicantDetails.villageTownSub"></form:input>
                    </div>
                    <label class="col-sm-2 control-label required-control" for="Pincode"><spring:message code="care.pincode" /></label>
                    <div class="col-sm-4">
                      <form:input name="" type="text" class="form-control hasNumber" id="Pincode" path="careApplicantDetails.pinCode" maxlength="6" readonly="true"></form:input>
                    </div>
                  </div>
                </div>
              </div>
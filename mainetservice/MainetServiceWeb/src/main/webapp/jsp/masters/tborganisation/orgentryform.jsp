
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript"
	src="js/masters/tborganisation/orgentryform.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript">
	jQuery('.hasCharSymbols').keyup(function(evt) {
		this.value = this.value.replace(/[^a-z A-Z @ # $ & - `/]/g, '');
	});
</script>
<div id="heading_wrapper">
	<div class="widget">

		<input type="hidden" id="orgDefaultStatus"
			value="${userSession.organisation.defaultStatus}"></input>
		<c:url value="${saveAction}" var="url_form_submit" />


		<form:form method="post" action="Organisation.html" name="orgMaster"
			id="orgMaster" class="form-horizontal" commandName="tbOrganisation">
			<div class="error-div alert alert-danger alert-dismissible"
				style="display: block;" id="errorDivCustBankMas">
				<input type="hidden" value="${isDefault}" id="isDefault" />
				<input type="hidden" value="${envFlag}" id="envFlag" />
			
			</div>
			<c:if test="${mode == 'update'}">
				<!-- Store data in hidden fields in order to be POST even if the field is disabled -->
				<form:hidden path="lmoddateDesc" />
				<form:hidden path="lgIpMac"/>
				<form:hidden path="userId" />

			</c:if>
			
			
			<input type="hidden" id="modeId" value="${mode}">
			<%--   <form:hidden value="${mobileNoList}" id="mobileNoList"/> --%>
			<%-- <input type="hidden" id="mobileNoList" value="${mobileNoList}"> --%>
			<form:hidden path="defaultStatus" />
			<div class="panel-group accordion-toggle"
				id="accordion_single_collapse">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-target="#a2" data-toggle="collapse" class="collapsed"
								data-parent="#accordion_single_collapse" href="#a2"><spring:message
									code="organisation.orgDetails" text="Organisation Details" /></a>
						</h4>
					</div>
					<div id="a2" class="panel-collapse collapse in">
						<div class="panel-body">

							<div class="form-group">
								<label for="tbOrganisation_ulbOrgid"
									class="col-sm-2 control-label required-control"><spring:message
										code="tbOrganisation.orgid" text="Organisation Id"/></label>
								<div class="col-sm-4">
									<form:input id="tbOrganisation_orgid" path="orgid"
										type="hidden" />

									<c:if test="${mode == 'create'}">
										<form:input id="tbOrganisation_ulbOrgid" path="ulbOrgID"
											class="form-control mandClassColor hasNumber" maxLength="500" />
									</c:if>
									<c:if test="${mode != 'create'}">
										<form:input id="tbOrganisation_ulbOrgid" path="ulbOrgID"
											class="form-control mandClassColor hasNumber" maxLength="500"
											readonly="true" />
									</c:if>
								</div>

								<label for="tbOrganisation_orgShortNm required-control"
									class="col-sm-2 control-label required-control"><spring:message
										code="tbOrganisation.orgShortNm" text="Short Code"/></label>
								<div class="col-sm-4">
									<c:if test="${mode == 'create'}">
										<form:input id="tbOrganisation_orgShortNm" path="orgShortNm"
											class="form-control mandColorClass hasCharSymbols"
											maxLength="5" style="text-transform: uppercase;" />
									</c:if>
									<c:if test="${mode != 'create'}">
										<form:input id="tbOrganisation_orgShortNm" path="orgShortNm"
											class="form-control mandClassColor hasCharSymbols"
											maxLength="5" readonly="true" />
									</c:if>
								</div>
							</div>
							<div class="form-group">

								<label for="tbOrganisation_oNlsOrgname "
									class="col-sm-2 control-label required-control"><spring:message
										code="tbOrganisation.oNlsOrgname" text="Name(Eng)"/></label>
								<div class="col-sm-4">
									<form:input id="tbOrganisation_oNlsOrgname" path="oNlsOrgname"
										class="form-control mandColorClass" maxLength="500" />
								</div>

								<label for="tbOrganisation_oNlsOrgnameMar"
									class="col-sm-2 control-label  required-control"><spring:message
										code="tbOrganisation.oNlsOrgnameMar" text="Name(Reg)"/></label>
								<div class="col-sm-4">
									<form:input id="tbOrganisation_oNlsOrgnameMar"
										path="oNlsOrgnameMar" class="form-control mandColorClass"
										maxLength="500" />
								</div>

							</div>
							
							<div class="form-group">

								<label for="tbOrganisation_esdtDate "
									class="col-sm-2 control-label required-control"><spring:message
										code="tbOrganisation.esdtDate" text="Establishment Date"/></label>
								<div class="col-sm-4">
									<div class="input-group">
										<c:choose>
											<c:when test="${mode == 'create' }">
												<form:input id="tbOrganisation_esdtDate" path="estDtStr"
													class="form-control mandColorClass datepicker1"
													maxLength="50" readonly="true" />
											</c:when>
											<c:otherwise>
												<form:input id="tbOrganisation_esdtDate" path="estDtStr"
													class="form-control mandColorClass datepicker1"
													maxLength="50" />
											</c:otherwise>
										</c:choose>
										<div class="input-group-addon">
											<i class="fa fa-calendar"></i>
										</div>
									</div>
								</div>



								<label for="tbOrganisation_trnscDate "
									class="col-sm-2 control-label required-control"><spring:message
										code="tbOrganisation.transactionDate" text="Transaction Start Date"/></label>
								<div class="col-sm-4">
									<div class="input-group">
										<c:choose>
											<c:when test="${mode == 'create' }">
												<form:input id="tbOrganisation_trnscDate" path="trnsDtStr"
													class="form-control mandColorClass datepicker2"
													maxLength="50" readonly="true" />
											</c:when>
											<c:otherwise>
												<form:input id="tbOrganisation_trnscDate" path="trnsDtStr"
													class="form-control mandColorClass datepicker2"
													maxLength="50" />
											</c:otherwise>
										</c:choose>
										<div class="input-group-addon">
											<i class="fa fa-calendar"></i>
										</div>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="tbOrganisation_orgAddress "
									class="col-sm-2 control-label"><spring:message
										code="tbOrganisation.orgAddress" text="Address" /></label>
								<div class="col-sm-4">
									<form:input id="tbOrganisation_orgAddress" path="orgAddress"
										class="form-control mandClassColor" maxLength="500" />
								</div>

								<label for="tbOrganisation_orgAddressMar "
									class="col-sm-2 control-label"><spring:message
										code="tbOrganisation.orgAddressMar" text="Address" /></label>
								<div class="col-sm-4">
									<form:input id="tbOrganisation_orgAddressMar"
										path="orgAddressMar" class="form-control mandClassColor"
										maxLength="500" />
								</div>
							</div>
                         <c:if  test="${envFlag == 'N'}">
							<div class="form-group">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="tbOrganisation.orgCpdIdState" text="State"/></label>
								<div class="col-sm-4">
									<form:select path="orgCpdIdState"
										class="form-control chosen-select-no-results mandColorClass"
										id="orgCpdIdState">
										<form:option value=""><spring:message code="tbOrganisation.select" text="Select"/></form:option>
										<c:forEach items="${sttList}" var="lookUp">
											<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select>
								</div>
								<label class="col-sm-2 control-label required-control"><spring:message
										code="tbOrganisation.orgCpdId" text="Type"/></label>
								<div class="col-sm-4">
									<form:select path="orgCpdId"
										class="form-control chosen-select-no-results" id="orgCpdId">
										<form:option value=""><spring:message code="tbOrganisation.select" text="Select"/></form:option>
										<c:forEach items="${lookUpList}" var="lookUp">
											<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="tbOrganisation.orgCpdIdDis" text="District"/></label>
								<div class="col-sm-4">
									<form:select path="orgCpdIdDis"
										class="form-control chosen-select-no-results" id="orgCpdIdDis">
										<form:option value=""><spring:message code="tbOrganisation.select" text="Select"/></form:option>
										<c:forEach items="${disList}" var="lookUp">
											<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select>
								</div>

								<label class="col-sm-2 control-label required-control"><spring:message
										code="tbOrganisation.orgCpdIdOst" text="Subtype"/></label>
								<div class="col-sm-4">
									<form:select path="orgCpdIdOst"
										class="form-control chosen-select-no-results" id="orgCpdIdOst">
										<form:option value=""><spring:message code="tbOrganisation.select" text="Select"/></form:option>
										<c:forEach items="${ostList}" var="lookUp">
											<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>
							 </c:if>

							<c:if test="${envFlag == 'Y'}">
	                                 <div class="form-group">
										<label for="State" class="col-sm-2 control-label required-control"><spring:message
												code="tbOrganisation.orgCpdIdState" text="State"> 	</spring:message></label>
										<div class="col-sm-4">
										<form:select path="sdbId1"
											class="form-control "
											id="sdbId1" onchange="getDistrictList();">
											<form:option value="">
												<spring:message code="tbOrganisation.select" text="Select" />
											</form:option>
											<c:forEach items="${stateList}" var="lookUp">
												<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
										<label for="District" class="col-sm-2 control-label required-control"><spring:message
												code="tbOrganisation.orgCpdIdDis" text="District">
											</spring:message></label>
										<div class="col-sm-4">
											<form:select path="sdbId2" id="sdbId2"
												class="form-control mandColorClass" onchange="getBlockList();">
												<form:option value="">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${districtList}" var="dist">
												<form:option value="${dist.lookUpId}">${dist.lookUpDesc}</form:option>
											</c:forEach>
											</form:select>
										</div>

									</div>
									
								<div class="form-group">
									<label for="Block" class="col-sm-2 control-label required-control"><spring:message
											code="tbOrganisation.orgBlock" text="Block"></spring:message></label>
									<div class="col-sm-4">
										<form:select path="sdbId3" id="sdbId3"
											class="form-control mandColorClass">
											<form:option value="">
												<spring:message code='master.selectDropDwn' />
											</form:option>
											<c:forEach items="${blockList}" var="block">
												<form:option value="${block.lookUpId}">${block.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>

							</c:if>
             
						

							<div class="form-group">
                               <c:if  test="${envFlag == 'N'}">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="tbOrganisation.orgCpdIdDiv" text="Division"/></label>
								<div class="col-sm-4">
									<form:select path="orgCpdIdDiv"
										class="form-control chosen-select-no-results" id="orgCpdIdDiv">
										<form:option value=""><spring:message code="tbOrganisation.select" text="Select"/></form:option>
										<c:forEach items="${divisionList}" var="lookUp">
											<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select>
								</div></c:if>

								<c:if test="${envFlag == 'Y'}">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="tbOrganisation.orgCpdId" text="Organization Type" /></label>
									<div class="col-sm-4">
										<form:select path="orgCpdId"
											class="form-control chosen-select-no-results" id="orgCpdId">
											<form:option value="">
												<spring:message code="tbOrganisation.select" text="Select" />
											</form:option>
											<c:forEach items="${lookUpList}" var="lookUp">
												<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</c:if>

								<label for="tbOrganisation_orgemailid "
									class="col-sm-2 control-label "><spring:message
										code="tbOrganisation.orgEmailId" text="Email Id"/></label>
								<div class="col-sm-4">
									<form:input id="tbOrganisation_orgemailid" path="orgEmailId"
										class="form-control mandClassColor" maxLength="500" />
								</div>

							</div>

							<div class="form-group">
								<label for="tbOrganisation_orgLatitude "
									class="col-sm-2 control-label"><spring:message
										code="tbOrganisation.orgLatitude" text="Latitude" /></label>
								<div class="col-sm-4">
									<form:input id="tbOrganisation_orgLatitude" path="orgLatitude"
										class="form-control" maxLength="10"
										onchange="validateLatitude()" />
								</div>

								<label for="tbOrganisation_orgLongitude "
									class="col-sm-2 control-label"><spring:message
										code="tbOrganisation.orgLongitude" text="Longitude" /></label>
								<div class="col-sm-4">
									<form:input id="tbOrganisation_orgLongitude"
										path="orgLongitude" class="form-control" maxLength="10"
										onchange="validateLongitude()" />
								</div>
							</div>

							<div class="form-group">

								<label for="tbOrganisation_gstNumber"
									class="col-sm-2 control-label"><spring:message code="tbOrganisation.GSTNO"
										text="GST Number" /></label>
								<div class="col-sm-4">
									<form:input id="tbOrganisation_gstNumber" path="orgGstNo"
										class="form-control" maxLength="15"
										placeholder="Ex: 22AAAAA0000A1Z5" />
								</div>
								<label for="" class="col-sm-2 control-label"><spring:message
										code="tbOrganisation.regiNo" text="Registration No." /></label>
								<div class="col-sm-4">
									<form:input id="orgRegNo" path="orgRegNo" class="form-control" maxLength="20" />
								</div>
							</div>

							<form:hidden path="deleteFlag" id="hiddenDeleteFlag" value="N" />
							<form:hidden path="filePath" id="hiddenLogoPath"
								value="${filePath}" />
							<div class="form-group">
								<label class="control-label col-sm-2"><spring:message
										code="tbOrganisation.uploadLogo" text="Upload Logo" /></label>

								<div class="col-sm-2">
									<apptags:formField fieldType="7" labelCode="" hasId="true"
										fieldPath="oLogo" isMandatory="false"
										showFileNameHTMLId="true" fileSize="BND_COMMOM_MAX_SIZE"
										maxFileCount="CHECK_LIST_MAX_COUNT"
										validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
										currentCount="0" />
								</div>
								<div id="uploadPreview" class="col-sm-4">
									<ul></ul>
								</div>
							</div>
						</div>
					</div>
				</div>

<c:if test="${mode == 'create'}">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-target="#a1" data-toggle="collapse" class="collapsed"
								data-parent="#accordion_single_collapse" href="#a1"><spring:message
									code="organisation.defLoginDet" text="Default Login Details" /></a>
						</h4>
					</div>
					<div id="a1" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="form-group">
								<!-- <input type="hidden" id="defaultRegisteredUserPass" path="defaultRegisteredUserPass"  value="">
								<input type="hidden" id="alreadyRegisteredUser"  path="alreadyRegisteredUser"  value=""> -->
								
								<form:hidden id="defaultRegisteredUserPass" path="defaultRegisteredUserPass"/>
									<form:hidden id="alreadyRegisteredUser"  path="alreadyRegisteredUser" /> 
								

								<label class="col-sm-2 control-label required-control"><spring:message code="tbOrganisation.userMobiNo"
										text="Mobile No." /></label>
								<div class="col-sm-4">
									<form:input path="defaultUserMobi" placeholder="Enter Mobile No" id="empMobNo"
										class="form-control hasMobileNo" maxlength="10"
										onchange="getUserData();" />
								</div>

								<label for="" class="col-sm-2 control-label required-control"><spring:message
										code="tbOrganisation.loginName" text="Login Name" /></label>
								<div class="col-sm-4">
									<form:input id="userName" path="defaultLoginName" placeholder="Enter Username"
										class="form-control" maxLength="20" />
								</div>
							</div>
							<div class="form-group">
								<label for="" class="col-sm-2 control-label required-control"><spring:message
										code="tbOrganisation.pass" text="Password" /> </label>
								<div class="col-sm-4">
									<form:password id="pass" path="defaultUserNewPass" placeholder="Enter Password"
										class="form-control" maxlength="50" />
								</div>

								<label class="col-sm-2 control-label required-control"><spring:message
										code="tbOrganisation.confirmpass" text="Conform New Password" /></label>
								<div class="col-sm-4">
									<form:password path="defaultUserConformPass" id="confirmpass" class="form-control"
										placeholder="Conform Password" maxlength="50" />
								</div>
							</div>

							<div class="form-group">

								<label class="col-sm-2 control-label"><spring:message
										code="tbOrganisation.useremail" text="Email Id" /></label>
								<div class="col-sm-4">
									<form:input path="defaultUserEmail" cssClass="form-control"
										placeholder="Enter Email Address" id="empemail" />
								</div>
							</div>
						</div>
					</div>

				</div>
				</c:if>
			</div>
			<div class="text-center">
				<button type="button" id="save" class="btn btn-success btn-submit"
					onclick="saveData(this)">
					<spring:message code='master.save' text="Save"/>
				</button>
				<input type="reset" id="reset" class="btn btn-warning"
					value="<spring:message code="reset.msg" text="Reset"/>" onclick="resetOrg()">
				<button type="button" id="back" class="btn btn-danger"
					onclick="window.location.href='Organisation.html'">
					<spring:message code='back' text="Back"/>
				</button>

			</div>

			<div class="table-responsive">
				<table id="childGrid" class="table table-bordered"></table>
				<div id="pagered"></div>
			</div>



		</form:form>



	</div>
</div>

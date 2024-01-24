<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<script src="js/eip/citizen/citizenContactUs.js"></script>
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<ol class="breadcrumb">
	<li><a href="CitizenHome.html"><i class="fa fa-home"></i> <spring:message code="menu.home"/></a></li>
	<li><spring:message code="ContactUs" text="Contact Us"/></li>
</ol>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="eip.citizen.footer.contactUs" />
			</h2>
			<div class="additional-btn">
				<a href="#" class="widget-toggle"><span class="hide">Addditional</span><strong class="icon-down-open-2"></strong></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="alert alert-info padding-10 text-center" role="alert">
				<p>
				<c:if test="${userSession.getCurrent().getLanguageId() == 1}">
					<spring:message code="eip.admin.contactUs.msg1" />
					<b>
						${userSession.getCurrent().getOrganisation().getONlsOrgname()}</b>
					<spring:message code="eip.admin.contactUs.msg2" />
				</c:if>
				<c:if test="${userSession.getCurrent().getLanguageId() == 2}">
					<b>${userSession.getCurrent().getOrganisation().getONlsOrgnameMar()}</b>
					<spring:message code="eip.admin.contactUs.msg1" />
					<p>
						<spring:message code="eip.admin.contactUs.msg2" />
						<br>
				</c:if>
				</p>
			</div>
			<ul id="demo2" class="nav nav-tabs">
				<li class="active"><a href="#demo2-home" title="Contact"
					data-toggle="tab"><strong class="fa fa-phone-square"></strong> <spring:message
							code="eip.citizen.footer.contactUs" /></a></li>
				<li class=""><a href="#demo2-profile" title="ULB"
					data-toggle="tab"><strong class="fa fa-info-circle"></strong> <spring:message
							code="eip.admin.contactUs.ulbdet" /></a></li>
			</ul>
			<div class="tab-content tab-boxed">
				<div class="tab-pane fade active in" id="demo2-home">
					<div class="row">
						<div class="col-sm-6">
							<form:form method="post" action="CitizenContactUs.html"
								name="frmCitizenContactUs" id="frmCitizenContactUs"
								class="form-horizontal" role="form">
								<div class="form-group">
									<label class="col-sm-3 control-label required-control" for="phoneno">Mobile No </label>
									<div class="col-sm-8">
										<form:input type="text"
											class="input2 hasMobileNo form-control" id="phoneno" name=""
											path="eipUserContactUs.phoneNo"
											placeholder="+99.99.99.999999"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label required-control" for="Fname">First
										Name</label>
									<div class="col-sm-8">
										<form:input type="text"
											class="input2 hasSpecialChara form-control" id="Fname" name=""
											path="eipUserContactUs.firstName" placeholder="First Name"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label required-control" for="Lname">Last
										Name</label>
									<div class="col-sm-8">
										<form:input type="text"
											class="input2 hasSpecialChara form-control" id="Lname" name=""
											path="eipUserContactUs.lastName" placeholder="Last Name"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label required-control" for="emailId">Email
										Address</label>
									<div class="col-sm-8">
										<form:input type="text" class="input2 form-control"
											id="emailId" name="" path="eipUserContactUs.emailId"
											placeholder="Email Address"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label required-control" for="DescQuery">Description
										of Query</label>
									<div class="col-sm-8">
										<form:textarea class="input2 form-control" id="DescQuery"
											name="" path="eipUserContactUs.descQuery"></form:textarea>
									</div>
								</div>

								<div class="form-group">

									<label class="col-sm-3 control-label" for="eipUserContactUs.attPath">
										<spring:message code="rti.UploadFile" /><span class="mand">*</span>
									</label>
									<div class="col-sm-8">
										<c:set var="errmsg">
											<spring:message code="file.upload.size" />
										</c:set>

										<apptags:formField fieldType="7" labelCode="" hasId="true"
											folderName="0" maxFileCount="CHECK_LIST_MAX_COUNT"
											fieldPath="eipUserContactUs.attPath"
											fileSize="BND_COMMOM_MAX_SIZE"
											validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
											isMandatory="false" showFileNameHTMLId="true"
											currentCount="0" />
									</div>
								</div>
								<div class="text-center">
									<input type="submit" value="Save" onClick="return saveContactForm(this);" class="btn btn-success">
									<button type="reset" class="btn btn-danger">Reset</button>
								</div>


							</form:form>
						</div>
						<div class="col-sm-6">
							<address>
								<c:choose>
									<c:when test="${not empty  command.contactUsListByFlag}">
										<c:forEach items="${command.contactUsListByFlag}" var="list1"
											varStatus="var">

											<c:choose>
												<c:when
													test="${userSession.getCurrent().getLanguageId() == 1}">

													<p>
														<strong> ${list1.contactNameEn}</strong>
													</p>
													<address>
														<p>${list1.designationEn}</p>
														<p>
															<spring:message code="eip.admin.contactUs.phoneNo" />
															: ${list1.telephoneNo1En}
														</p>
														<p>
															<spring:message code="feedback.Mobile" />
															:${list1.telephoneNo2En}
														</p>
														<a href="mailto:${list1.email1}" class="responsive">${list1.email1}</a>
													</address>
												</c:when>

												<c:otherwise>

													<strong>${list1.contactNameReg}</strong>
													<div class="responsive">
														<p>${list1.designationReg}</p>
														<p>
															<spring:message code="eip.admin.contactUs.phoneNo" />
															:${list1.telephoneNo1En}
														</p>
														<p>
															<spring:message code="feedback.Mobile" />
															:${list1.telephoneNo2En}
														</p>
														<p>
															<spring:message code="feedback.Email" />
															<a href="${list1.email1}"></a>
														</p>
														<a href="mailto:muzaffarpur.ulb@gmail.com"
															class="table-responsive">muzaffarpur.ulb@gmail.com</a>
														<hr>
													</div>
												</c:otherwise>
											</c:choose>

										</c:forEach>
									</c:when>
									<c:otherwise>
										<spring:message code="eip.admin.contactUs.nocontactmsg" />
									</c:otherwise>
								</c:choose>
							</address>
						</div>
					</div>
				</div>
				<div class="tab-pane fade table-responsive" id="demo2-profile">
					<table class="table table-hover table-striped table-bordered">
						<thead>
							<tr>
								<th><spring:message code="eip.dept.srNo" /></th>
								<th><spring:message code="eip.admin.contactUs.ulbdet" /></th>
								<th><spring:message code="eip.admin.contactUs.contactdet" /></th>
								<th><spring:message code="eip.admin.contactUs.oficeno" /></th>
								<th><spring:message code="citizen.editProfile.emailId" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.contactUsList}" var="list"
								varStatus="lk">
								<tr>
									<c:if test="${userSession.getCurrent().getLanguageId() == 1}">
										<td>${lk.count}</td>
										<td>${list.departmentEn}</td>
										<td>${list.contactNameEn},${list.designationEn},${list.address2En}</td>
										<td>
											<p>
												<spring:message code="eip.admin.contactUs.phoneNo" />
												: ${list.telephoneNo1En}
											</p>
											<p>
												<spring:message code="feedback.Mobile" />
												: ${list.telephoneNo2En}
											</p>
										</td>
										<td>
											<p>${list.email1}</p>
										</td>
									</c:if>
									<c:if test="${userSession.getCurrent().getLanguageId() == 2}">
										<td>${lk.count}</td>
										<td>${list.departmentReg}</td>
										<td>${list.contactNameReg},${list.designationReg},${list.address2Reg}</td>
										<td>
											<p>
												<spring:message code="eip.admin.contactUs.phoneNo" />
												:  ${list.telephoneNo1En}
											</p>
											<p>
												<spring:message code="feedback.Mobile" />
												:  ${list.telephoneNo2En}
											</p>
										</td>
										<td>
											<p>${list.email1}</p>
										</td>
									</c:if>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
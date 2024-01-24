<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<jsp:useBean id="stringUtility"
	class="com.abm.mainet.common.util.StringUtility" />
<script>

	
</script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="feedback.formTitle" /></strong>
			</h2>
		</div>



		<div class="widget-content padding">
			<form:form method="post" action="AdminFeedbackForm.html"
				name="frmAdminFeedbackForm" id="frmAdminFeedbackForm"
				class="form-horizontal">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div class="form-elements">
					<div class="element"></div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="feedback.name" />:</label>
					<div class="col-sm-4">
						<apptags:inputField fieldPath="entity.fdUserName" hasId="true"
							isDisabled="true" cssClass="form-control" />
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="feedback.Mobile" />:</label>
					<div class="col-sm-1">
						<apptags:inputField fieldPath="entity.moblieExtension" hasId="true"
							isDisabled="true" cssClass="form-control" />
					</div>
					<div class="col-sm-3">						
						<apptags:inputField fieldPath="entity.mobileNo" hasId="true"
							isDisabled="true" cssClass="form-control" />
				  </div>
				</div>
					
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="feedback.category" text = "Category" />:</label>
					<div class="col-sm-4">
						<apptags:inputField fieldPath="entity.categoryTypeName" hasId="true"
							isDisabled="true" cssClass="form-control" />
					</div>
				</div>
					
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="feedback.Email" />:</label>
					<div class="col-sm-4">
						<apptags:inputField fieldPath="entity.emailId" hasId="true"
							isDisabled="true" cssClass="form-control" />
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="feedback.comments" />:</label>
					<div class="col-sm-4">
						<form:textarea path="entity.feedBackDetails" disabled="true"
							cssClass="form-control" />

					</div>
				</div>
				<c:if test = "${not empty  command.entity.attPath}">
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="admin.feedBack.down.view.doc" text="Download or View Doc" />:</label>
						<div class="col-sm-4 padding-top-5">
							<c:set var="fileLinkUrl" value="${command.entity.attPath}" />
							<a href="./${fileLinkUrl}" target="_blank" class=""><i
								class="fa fa-download" aria-hidden="true"></i></a>
						</div>
					</div>
				</c:if>
				<div class="form-group">
						<apptags:checkbox labelCode="admin.publicNotice.Publish" value="P" path="entity.activeStatus"></apptags:checkbox>
						<%-- <div id="publishFeedBack">
						<apptags:submitButton actionParam="publish"
						successUrl="AdminFeedback.html"
						entityLabelCode="Feedback" cssClass="btn btn-success"
						buttonLabel="admin.publicNotice.Publish" /></div> --%>
				</div>

				<div class="container dept-feedback">
					<div class="row">
						<div class="col-md-8 col-md-offset-2">
							<div class="panel panel-primary">
								<div class="panel-heading" id="accordion">
									<span><i class="fa fa-comments" aria-hidden="true"></i></span><spring:message code="Feedback" text="Feedback"/>
									<div class="btn-group pull-right">
										<a type="button" class="btn btn-primary btn-xs"
											data-toggle="collapse" data-parent="#accordion"
											href="#collapseOne"> <span><i class="fa fa-chevron-circle-down" aria-hidden="true"></i></span>
										</a>
									</div>
								</div>
								<div class="panel-collapse " id="collapseOne">
									<div class="panel-body">
										<ul class="chat">
											<li class="left clearfix"><span
												class="chat-img pull-left"> <img
													src="http://placehold.it/50/55C1E7/fff&text=U"
													alt="User Avatar" class="img-circle" />
											</span>
												<div class="chat-body clearfix">
													<div>


														<small class="pull-right text-muted"><span><i class="fa fa-calendar" aria-hidden="true"></i></span><fmt:formatDate
																type="date" dateStyle="short" pattern="dd/MM/yyyy"
																value="${command.entity.lmodDate}" /></small>
														<strong class="primary-font">
															${command.entity.fdUserName}</strong>
													</div>
													<p>${command.entity.feedBackDetails}</p>
												</div></li>
												
												<c:set value="${command.entity.feedBackAnswar}"
															var="adminAnswars" />
														<c:set var="answars"
															value="${fn:split(adminAnswars, '~@~')}" />
														<c:forEach items="${answars}" var="answar">
															<c:if test="${not empty answar }">
																<c:set var="ans" value="${fn:split(answar, '|')}" />
											<li class="right clearfix"><span
												class="chat-img pull-right"> <img
													src="http://placehold.it/50/FA6F57/fff&text=A"
													alt="User Avatar" class="img-circle" />
											</span>
												<div class="chat-body clearfix">
												
													<div>
													
														<small class=" text-muted"><span><i class="fa fa-calendar" aria-hidden="true"></i></span>
														${ans[1]} </small>
														<strong class="pull-right primary-font">${ans[0]}</strong>
													</div>
													<p>
																${ans[2]}
													</p>
													
												</div></li>
												</c:if>
				</c:forEach>
										</ul>
									</div>
									<div class="panel-footer">
									<div class="form-group">
										<div class="input-group">
											<form:input path="feedBackDetails" cssClass="form-control" placeholder="Type Your message here...." /> <span
												class="input-group-btn">
												<apptags:submitButton actionParam="reply"
						successUrl="AdminFeedback.html"
						entityLabelCode="feedback.accept.reply" cssClass="btn btn-success"
						buttonLabel="eip.submit"/>
					<apptags:backButton url="AdminFeedback.html"
						cssClass="btn btn-warning" />
											</span>
										</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

			</form:form>

		</div>
	</div>
</div>
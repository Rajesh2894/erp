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

 

<style>
.message {
	border: 1px solid rgba(0, 153, 0, 0.2);
	background: rgba(0, 153, 0, 0.2);
	text-align: center;
	width: 95.5%;
	padding: 7px 5px;
	margin: 5px 0;
	font-style: 14px;
	color: #333;
}
</style>
<div class="row padding-40">
	
		
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					
						<div class="message alert" role="alert" style="display: none"></div>
						<div class="error-div alert alert-danger alert-dismissable"
							role="alert" style="display: none"></div>
						<form:form method="post" action="CitizenFeedBack.html"
							name="frmCitizenFeedBack" id="frmCitizenFeedBack" role="form">




							<div class="container dept-feedback">
								<div class="row">
									<div class="col-md-8 col-md-offset-2">
										<div class="panel panel-primary">
											<div class="panel-heading" id="accordion">
												<span><i class="fa fa-comments" aria-hidden="true"></i></span>
												<spring:message code="Feedback" text="Feedback" />
												<div class="btn-group pull-right">
													
												</div>
											</div>
											<div class="panel-collapse in " id="collapseOne">
												<div class="panel-body" style="height:400px !important;">
													<ul class="chat">
													
													<c:forEach items="${publishFeedBackList}" var="feedBack" varStatus="stat">
														<li class="left clearfix"><span
															class="chat-img pull-left"> <img
																src="http://placehold.it/50/55C1E7/fff&text=U"
																alt="User Avatar" class="img-circle" />
														</span>
															<div class="chat-body clearfix">
																<div class="header">


																	<strong class="primary-font">
																		${feedBack.fdUserName}</strong> <small
																		class="pull-right text-muted"><span><i
																			class="fa fa-calendar" aria-hidden="true"></i></span>
																	<fmt:formatDate type="date" dateStyle="short"
																			pattern="dd/MM/yyyy"
																			value="${feedBack.lmodDate}" /></small>
																</div>
																<p>${feedBack.feedBackDetails}</p>
															</div></li>
															
															
															
															<!--  -->

														<c:set value="${feedBack.feedBackAnswar}"
															var="adminAnswars" />
														<c:set var="answars"
															value="${fn:split(adminAnswars, ':')}" />
														<c:forEach items="${answars}" var="answar" varStatus="loop">
														<c:if test="${loop.last}">
															<c:if test="${not empty answar }">
																<c:set var="ans" value="${fn:split(answar, '|')}" />
																<li class="right clearfix"><span
																	class="chat-img pull-right"> <img
																		src="http://placehold.it/50/FA6F57/fff&text=A"
																		alt="User Avatar" class="img-circle" />
																</span>
																	<div class="chat-body clearfix">

																		<div class="header">

																			<small class=" text-muted"><span><i
																					class="fa fa-calendar" aria-hidden="true"></i></span>
																				${ans[1]} </small> <strong class="pull-right primary-font">${ans[0]}</strong>
																		</div>
																		<p>${ans[2]}</p>

																	</div></li>
															</c:if>
															</c:if>
														</c:forEach>
														</c:forEach>
													</ul>
												</div>
												<div class="panel-footer">
													<div class="form-group">
														<div class="input-group">
															 	      <a onclick="getfeedbackForm()" class="btn  btn-success" href='javascript:void(0);'><span> <spring:message code="Feedback.add" text="Add Feedback"/></span></a>
															 
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
				
		

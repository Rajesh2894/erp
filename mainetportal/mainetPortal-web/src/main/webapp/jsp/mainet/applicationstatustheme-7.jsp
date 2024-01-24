<%@ page import="org.w3c.dom.Document"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility" />
<jsp:useBean id="marathiConvert" class="com.abm.mainet.common.util.Utility"></jsp:useBean>
<%@ page import="java.util.Date" %>
<jsp:useBean id="now" class="java.util.Date" scope="page" />
<fmt:formatDate value="${now}" pattern="yyyy-MM-dd HH:mm:ss" var="myDate"/> 


<div class="section-container citizen-services panel">
			<div class="mkpay">
				<div class="panel-heading">
					<h3><div class="image">
						<!-- <i class="fa fa-credit-card" aria-hidden="true"></i> -->
						<spring:message code="application.status" text="Application Status" />
						<img alt="flashing-new" src="./assets/img/flashing-new.png" class="flash-new">
					</h3>
				</div>
			</div>
			<div class="panel-body">	
				<div id="mkpay">
					<div class="col-sm-12 col-md-12 col-lg-12 col-xs-12">
						<div class="hidden-lg hidden-md hidden-sm"
							style="margin-top: 10px;"></div>
						<div class="content-tab">
							<div class="warning-div error-div alert alert-danger alert-dismissible" id="error-msg" style="display: none;"></div>
							<form class="form">
								<div class="form-group">
									<div class="col-sm-12 propertyNo" id="application_no"><spring:message code="eg" var="placeholder4" />
									<label for="billNumber" id="billdetailtext"><spring:message code="application.no" text="Application Number" /> </label> <input
										type="text" class="form-control" name="referral"
										id="billNumber" placeholder="${placeholder4}">
									</div> 
								</div>
								<div class=" col-sm-12 text-right">
									<br />
									<button type="button" onclick=""
										class="btn btn-danger margin-top-10 margin-bottom-10">
										<spring:message code="eip.page.process" text="Proceed" />
									</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
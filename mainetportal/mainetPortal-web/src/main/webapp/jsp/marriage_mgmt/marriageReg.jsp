<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/marriage_mgmt/marriageReg.js"></script>
<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="" text="Marriage Registration" />
				</h2>
				<apptags:helpDoc url="MarriageRegistration.html"></apptags:helpDoc>
			</div>
			<div class="widget-content padding">
				<form:form id="marReg" action="MarriageRegistration.html"
					method="post" class="form-horizontal">
					<div class="compalint-error-div">
						<div class="mand-label clearfix">
							<span><spring:message code="mrm.fiels.mandatory.message" text="Field with * is mandatory" /></span>
						</div>

						<ul class="nav nav-tabs" id="mrmParentTab">

							<li id="marriage-tab" class="active"><a data-toggle="tab"
								href="#marriage" data-content-param="showMarriagePage"
								data-loaded="true"><spring:message
										code="mrm.tab.marriageDetails"  text="Marriage Details"  /></a></li>

							<li id="husband-tab" class="disabled"><a data-toggle="tab"
								href="#husband" data-content-param="showHusbandPage"
								data-loaded="false"><spring:message
										code="mrm.tab.husbandDetails" text="Husband" /></a></li>

							<li id="wife-tab" class="disabled"><a data-toggle="tab"
								href="#wife" data-content-param="showWifePage"
								data-loaded="false"><spring:message
										code="mrm.tab.wifeDetails"  text="Wife" /></a></li>


							<li id="witness-tab" class="disabled"><a data-toggle="tab"
								href="#witnessDet" data-content-param="showWitnessPage"
								data-loaded="false"><spring:message
										code="mrm.tab.witnessDetails" text="Witness" /></a></li>


						</ul>
						<div class="tab-content">

							<div id="marriage" class="tab-pane fade in active">
								<jsp:include page="/jsp/marriage_mgmt/marriage.jsp" />
							</div>

							<div id="husband" class="tab-pane fade"></div>

							<div id="wife" class="tab-pane fade"></div>

							<div id="witnessDet" class="tab-pane fade"></div>


						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
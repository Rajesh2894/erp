<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/property/PropertyOtp.js"></script>


<ol class="breadcrumb">
	<li><a href="AdminHome.html"><i class="fa fa-home"></i> Home</a></li>
	<Li>Service Dashboard</li>
	<li>NO Change in Assessment</li>

<div class="content animated slideInDown">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span id="errorId"></span>
			</div>
			<h2>NO Change in Assessment</h2>

		</div>
		<div class="widget-content padding">
			<form:form action="propertyTaxDashBoardController.html"
					method="post" class="form-horizontal form"
					name="selfAssessmentForm" id="NOchangeInAssessMentId">
				<div class="mand-label clearfix">
					<span>Enter Property No <strong class="text-red-1">OR</strong>
						OLD PID
					</span>
				</div>
				<%-- <form:form action="ServiceDashboardAuthentication.html"
					method="post" class="form-horizontal form"
					name="selfAssessmentForm"> --%>
					<div class="form-group">
						<label class="col-sm-2 control-label required-control"
							for="EnterPropertyNo">Enter Property No.</label>
						<div class="col-sm-4">
							<form:input name="" type="text" class="form-control" maxlength="9"
							path="oAssessmentDto.assPropertyNo"	id="EnterPropertyNo"/>

						</div>
						<label class="col-sm-2 control-label required-control"
							for="OLDPID">OLD PID</label>
						<div class="col-sm-4">
							<form:input name="" class="form-control" id="OLDPID" path="oAssessmentDto.assOldPid"
								maxlength="30"/>
								
						</div>
					</div>
				<%-- </form:form> --%>
				<div class="form-group">
					<div class="text-center padding-bottom-10">
						<button type="button" class="btn btn-blue-2"
							onclick="NoChangeSearchButton()">
							<i class="fa fa-search"></i> Search
						</button>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"
						for="MobileNo">Mobile No.</label>
					<div class="col-sm-4">
						<input name="" class="form-control" id="MobileNo"  
							disabled="disabled">
					</div>
					<div class="col-sm-6">
						<i class="text-blue-2">Enter mobile number associated with
							your property ID. OTP will send automatically to your mobile
							number verification</i>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for="OTP">OTP</label>
					<div class="col-sm-4">
						<input name="" type="text" class="form-control" id="OTP"
							disabled="disabled">
					</div>
				</div>
				<div class="text-center">
					<input type="button" id="NoChangeProceedBtn" value="Submit" class="btn btn-success"/>
				</div>
				<input type="hidden" id="dbMobile" />
				<input type="hidden" id="genOTP" />
				
			</form:form>
		</div>


	</div>
	<!-- End of info box -->
</div>








<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    response.setContentType("text/html; charset=utf-8");
%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<!-- <script type="text/javascript" src="js/mainet/jquery.js"></script>-->
<script src="assets/libs/lightbox/jquery.fancybox.js"></script>
<!-- LightBox -->

<!-- <script type="text/javascript" src="js/commanremark/commanremark.js"></script> -->
<script type="text/javascript">
	$(document)
			.ready(
					function() {

						$("#Submit")
								.click(
										function() {
											var serviceId = $("#woAllocation")
													.val();
											var checkedNum = $('.case:checked').length;
											var plumberId = $("#plumId").val();
											var woc = $("#WOChiddden").val();

											if (!checkedNum) {
												var errMsg = '<div class="closeme">	<ul><li><img alt="Close" title="Close" src="css/images/close.png" onclick="closeErrBox()" width="32"/>Please Select a Remark.</li></ul></div>';
												$("#serviceRuleDefMas").html(
														errMsg);
												$("#serviceRuleDefMas").show();
												return false;
											}

											$('.error-div').hide();

											var url = "LandOrder.html?create";
											var data = $("#workOrderForm")
													.serialize();
											$
													.ajax({
														url : url,
														data : data,
														type : 'POST',
														success : function(
																response) {
															/* $(".widget").html(response); */
															var record = data
																	.substring(
																			data
																					.search("&woApplicationId=") + 17)
																	.split("&")[0];
															console.log(record);
															showConfirmBox("Work order successfully generated for application number "
																	+ record);
														},
														error : function(xhr,
																ajaxOptions,
																thrownError) {
															var errorList = [];
															errorList
																	.push(getLocalMessage("admin.login.internal.server.error"));
															showError(errorList);
														}
													});

										});
						// add multiple select / deselect functionality
						$("#selectall").click(function() {
							$('.case').attr('checked', this.checked);
						});

						$(".case")
								.click(
										function() {

											var alertms = getLocalMessage('tp.app.alMessg');
											if ($(".case").length == 0) {
												alert(alertms);
											} else {

												if ($(".case").length == $(".case:checked").length) {
													$("#selectall").attr(
															"checked",
															"checked");
												} else {
													$("#selectall").removeAttr(
															"checked");
												}
											}
										});
					});

	function closeErrBox() {
		$('.error-div').hide();
	}

	function showConfirmBox(successMsg) {
		
		var errMsgDiv = '.msg-dialog-box';
		var message = '';
		var cls = getLocalMessage("works.management.proceed");

		message += '<h4 class=\"text-center text-blue-2 padding-12\">'
				+ successMsg + '</h4>';
		message += '<div class=\'text-center padding-bottom-10\'>'
				+ '<input type=\'button\' value=\'' + cls
				+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
				+ ' onclick="proceed()"/>' + '</div>';

		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();
		showModalBoxWithoutClose(errMsgDiv);
		return false;
	}

	function proceed() {
		window.location.href = "AdminHome.html";
	}
	
	function saveData(element){
		
		return saveOrUpdateForm(element,getLocalMessage("Land order successfully"), 'LandOrder.html?printLandReport', 'create');
	}
	
</script>

</head>
<ol class="breadcrumb">
	<li><a href="../index.html"><i class="fa fa-home"></i></a></li>
	<li>Land Order View</li>
</ol>
<!-- ============================================================== -->
<!-- Start Content here -->
<!-- ============================================================== -->
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>Land Order View</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span>Field with <i class="text-red-1">*</i> is mandatory
				</span>
			</div>
			<div class="error-div alert alert-danger alert-dismissible"
				style="display: none;" id="serviceRuleDefMas"></div>

			<form:form action="LandOrder.html"
				modelAttribute="tbWorkOrder" name="tbWorkOrder" method="POST"
				class="form-horizontal" id="workOrderForm">
				<form:hidden path="woApplicationId" />
				<form:hidden path="woServiceId" />
				<form:hidden path="woDeptId" />
				<form:hidden path="PlumId" />
				<form:hidden path="taskId" />
				<div class="form-group">

					<label class="col-sm-2 control-label"><spring:message
							code="" text="Owner name" /></label>
					<div class="col-sm-4">
						<input name="landOwnername" type="text" class="form-control"
							value="${landOwnername}" readonly="readonly">
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="" text="Approval Date" /></label>
					<div class="col-sm-4">
						<input name="approvalDate" type="text" class="form-control"
							value="${approvalDate}" readonly="readonly">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="" text="Land Valuation Amount" /> </label>
					<div class="col-sm-4">
						<input name="valuationAmt" type="text" class="form-control"
							value="${valuationAmt}" readonly="readonly">
					</div>
				</div>
				<div class="text-center margin-top-10">
					<button type="button" class="btn btn-success btn-submit" onclick="return saveData(this);"><spring:message code="submit.msg" text="Submit"/></button>
					<input type="button"
						onclick="window.location.href='AdminHome.html'"
						class="btn btn-danger  hidden-print" value="Back">
				</div>
			</form:form>
		</div>
	</div>
</div>

</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/asset/editAssetApproval.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="${userSession.moduleDeptCode == 'AST' ? 'asset.information.management':'asset.information.ITmanagement'}"
					text="Asset Registration" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide"><spring:message
							code="asset.information.help" /></span></a>
			</div>
		</div>
		<div class="pagediv" id="editAstApprovalDiv">
		
		</div>
		<input type="text" id="urlParam" style="display:none" value="${urlParam}"/>
		<input type="hidden" id="moduleDeptUrl"
				value="${userSession.moduleDeptCode == 'AST' ? 'AssetSearch.html':'ITAssetSearch.html'}">
		
			<form:form  action="AssetRegistration.html" id="informationTabFormApproval" method="post" class="form-horizontal" >
		<!-- It gets enable only when approval status is pending -->
		<c:if test='${(command.astDetailsDTO.assetInformationDTO.appovalStatus != null) && (command.astDetailsDTO.assetInformationDTO.appovalStatus == "P")}'>
		
		<div class="widget-content padding panel-group accordion-toggle" id="accordion_single_collapse1">
 		 	<apptags:CheckerAction hideSendback="true" hideForward="true"></apptags:CheckerAction>
		</div>
		<div class="text-center widget-content padding">
			<button type="button" id="save" class="btn btn-success btn-submit"
					onclick="saveAssetInfoApprovalData(this);">
					<spring:message code="asset.transfer.save" text="Save" />
			</button>
			<button type="button" class="button-input btn btn-danger"
				name="button-Cancel" value="Cancel"
				onclick="window.location.href='AdminHome.html'" id="button-Cancel">
				<spring:message code="asset.information.back" text="Back" />
			</button>
		</div>
		</c:if>
	</form:form>
	</div>
</div>

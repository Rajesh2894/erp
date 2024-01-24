<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script src="js/masters/tbcfcchecklistmst/documentgrouplist.js" type="text/javascript"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="master.docGroup" />
				<spring:message code="master" />
			</h2>
			<apptags:helpDoc url="DocumentGroupMaster.html" helpDocRefURL="DocumentGroupMaster.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form class="form-horizontal">
				<div
					class="error-div alert alert-danger alert-dismissible warning-div"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="master.docGroup" text="Document Group" /></label>
					<div class="col-sm-4">
						<select id="groupId" name="groupId"
							class="form-control mandClassColor">
							<option value=""><spring:message
									code="master.selectDropDwn" /></option>
							<c:forEach items="${clgPrefixList}" var="clgPrefixData">
								<option value="${clgPrefixData.lookUpId}">${clgPrefixData.lookUpDesc}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-6">
						<a href="javascript:void(0);" onclick="searchData(this)"
							class="btn btn-success btn-submit" id="btnsearch"><i
							class="fa fa-search"></i> <spring:message code="master.search" text="Search" /></a>
						<input type="button" value="<spring:message code="master.editSelected" text="Edit" />" class="btn btn-warning editClass" id="editGrpButton"> 
						<input type="button" value="<spring:message code="add.msg" text="Add" />" class="btn btn-blue-2 createData" id="createGrpButton">
					</div>
				</div>
				<table id="grid"></table>
				<div id="pagered"></div>
			</form>
		</div>
	</div>
</div>

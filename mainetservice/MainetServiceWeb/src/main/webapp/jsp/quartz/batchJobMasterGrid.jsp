
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/quartz/quartzMaster.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>

<c:if test="${command.hasValidationErrors() eq true}">
	<script type="text/javascript">
		getAvailableJobsAfterValidationError();
	</script>
</c:if>

<apptags:breadcrumb></apptags:breadcrumb>

<div id="content" class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="quartz.bredCrum.jobSchedule" />
			</h2>
			<apptags:helpDoc url="QuartzSchedulerMaster.html"></apptags:helpDoc>
		</div>
	


	<div class="widget-content padding">
<div class="mand-label clearfix">
		<span><spring:message code="contract.breadcrumb.fieldwith"
				text="Field with" /> <i class="text-red-1">*</i> <spring:message
				code="common.master.mandatory" text="is mandatory" /> </span>
	</div>
		<form:form action="QuartzSchedulerMaster.html"
			name="frmQuartzSchedulerMaster" id="frmQuartzSchedulerMaster"
			cssClass="form-horizontal">
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="error-div warning-div error-div alert alert-danger alert-dismissible" style="display: none" id="errorDivId"></div>


			<!-- Start Collapsable Panel -->

			<!-- Start Each Section -->


			<div class="form-group">
				<label class="control-label col-sm-2 required-control"><spring:message
						code="quartz.form.fieldName.department" text="Department" /> :</label>
				<div class="col-sm-4">
					<apptags:selectField items="${command.departmentList}"
						selectOptionLabelCode="quartz.form.fieldName.selectDep"
						fieldPath="departmentForQuartz"
						cssClass="input2 hasNameClass form-control" hasId="true" />
				</div>

				<div class="col-sm-6">
					<input type="submit" class="btn btn-info"
						onclick="return openBatchJobEditForm()"
						value="<spring:message code="quartz.form.button.addJob" />" />
				</div>
				<form:hidden path="" value="${command.departmentList}"
					id="deptListId" />
			</div>




			<!-- End Each Section -->



			<div class="form-group text-center clear">
				<input type="button" class="btn btn-success"
					onclick="return searchAvailableJobsByDepartment()"
					value="<spring:message code="quartz.form.search" />" />
				<apptags:resetButton />
			</div>



			<div class="panel-group accordion-toggle"
				id="accordion_single_collapse">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4>
							<spring:message code="quartz.bredCrum.header.batchJobDetails" />
						</h4>
					</div>
					<div id="a1" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="clearfix" id="contentOfJobSchedule">
								<div class="form-div">
									<apptags:jQgrid id="quartzSchedulerMasterEntryForm"
										url="QuartzSchedulerMaster.html?batchJobList" mtype="post"
										gridid="gridQuartzSchedulerMaster"
										colHeader="quartz.form.fieldName.jobName,quartz.form.fieldName.jobFrequency,quartz.form.gridLabel.status"
										colModel="[
									{name : 'jobName',index : 'jobName',editable : false,sortable : true,search : false,align : 'center',width :'250'},
									{name : 'jobFrequency',index : 'jobFrequency',align : 'center',editable:true, width :'100'},
									{name : 'jobStatus',index : 'jobStatus',align : 'center',editable:true, width :'100'}
									
		 						]"
										sortCol="rowId" isChildGrid="false" hasActive="false"
										hasViewDet="false" hasDelete="false" height="100"
										hasEdit="true"
										caption="quartz.bredCrum.header.batchJobDetails"
										loadonce="true" />
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

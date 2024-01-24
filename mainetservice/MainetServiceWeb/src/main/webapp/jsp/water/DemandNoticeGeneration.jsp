<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript">
	$(function() {
		$('#selectall').click(function(event) {
			if (this.checked) {
				$('.checkall').each(function() {
					this.checked = true;
				});
			} else {
				$('.checkall').each(function() {
					this.checked = false;

				});
			}
		});

		$('.checkall').on('click', function() {
			if ($('.checkall:checked').length == $('.checkall').length) {
				$('#selectall').prop('checked', true);
			} else {
				$('#selectall').prop('checked', false);
			}

		});
	});
	 $("#resetform").click(function() {
		this.form.reset();
		resetWaterForm();
		resetOtherFields();
	}); 
</script>
<apptags:breadcrumb></apptags:breadcrumb>


<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="demand.notice" text="Demand Notice Generation" />
			</h2>
			<apptags:helpDoc url="DemandNoticeGeneration.html"></apptags:helpDoc>
			<!--  <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div> -->
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith" /><i
					class="text-red-1">*</i> <spring:message code="water.ismandtry" /></span>
			</div>
			<form:form action="DemandNoticeGeneration.html" method="post"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />


				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#demandNotice"><spring:message
										code="demand.notice" text="Demand Notice Generation" /></a>
							</h4>
						</div>
						<div id="demandNotice" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">
									<label class="col-sm-2  required-control"><spring:message
											code="demand.notice.type" text="Notice Type" /></label>
									<c:set var="baseLookupCode" value="WDN" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}" path="dto.wdn"
										cssClass="form-control" hasChildLookup="false" hasId="true"
										showAll="false"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" />
								</div>

								<div class="form-group">
									<apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true"
										showOnlyLabel="false" pathPrefix="dto.wwz" isMandatory="true"
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true"
										cssClass="form-control  required-control" showAll="true" />
								</div>

								<div class="form-group">
									<apptags:lookupFieldSet baseLookupCode="TRF" hasId="true"
										showOnlyLabel="false" pathPrefix="dto.trf" isMandatory="true"
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true"
										cssClass="form-control  required-control" showAll="true" />
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label" for="wmn"><spring:message
											code="demand.meter" text="Metered / Non-Metered " /></label>
									<c:set var="baseLookupCode" value="WMN" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}" path="dto.wmn"
										cssClass="form-control" hasChildLookup="false" hasId="true"
										showAll="true"
										selectOptionLabelCode="applicantinfo.label.select" />

									<label class="col-sm-2 control-label" for="csz"><spring:message
											code="demand.con.size" text="Connection Size " /></label>
									<c:set var="baseLookupCode" value="CSZ" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}" path="dto.csz"
										cssClass="form-control" hasChildLookup="false" hasId="true"
										showAll="true"
										selectOptionLabelCode="applicantinfo.label.select" />
								</div>

								<div class="form-group">
									<label class="control-label col-sm-2" for="connFrom"><spring:message
											code="demand.con.from" text="Connection No. From" /></label>
									<div class="col-sm-4">
										<form:input path="dto.connFrom" cssClass="form-control"
											id="connFrom" />
									</div>
									<label class="control-label col-sm-2" for="connTo"><spring:message
											code="demand.con.to" text="Connection No. To" /></label>
									<div class="col-sm-4">
										<form:input path="dto.connTo" cssClass="form-control"
											id="connTo" />
									</div>
								</div>

								<div class="text-center padding-10">
									<button type="button" class="btn btn-success"
										onclick="saveOrUpdateForm(this,'DemandNoticeGenerationsuccessfully!','DemandNoticeGeneration.html?printDemandNotice', 'preView')">Search</button>
									<button type="Reset" class="btn btn-warning" id="resetform">
										<spring:message code="water.btn.reset" />
									</button>
									<input type="button" class="btn btn-danger" id="back"
												onclick="window.location.href='AdminHome.html'"
												value=<spring:message code="bckBtn"/> />
								
								</div>

								<c:if test="${not empty command.response}">
									<div class="table-responsive">
										<table class="table table-bordered table-condensed">
											<tr>
												<th><label class="checkbox-inline"><input
														type="checkbox" id="selectall" />
													<spring:message code="" text="Select All" /></label></th>
												<th><spring:message code="water.nodues.srno" /></th>
												<th><spring:message code="water.ConnectionNo" /></th>
												<th><spring:message code="water.consumerName" /></th>
												<th><spring:message
														code="water.demandNoticeGeneration.OutstandingAmount" /></th>
												<th><spring:message code="demand.due.date" /></th>
											</tr>
											<c:forEach items="${command.response}" var="data"
												varStatus="status">
												<tr>
													<td><label class="checkbox margin-left-20"><form:checkbox
																path="response[${status.index}].selected" value="Y"
																cssClass="checkall" id="${status.index}" /></label></td>
													<td><c:out value="${status.index+1}"></c:out></td>
													<td><c:out value="${data.connectionNo}"></c:out></td>
													<td><c:out value="${data.custName}"></c:out></td>
													<fmt:formatNumber type="number" minFractionDigits="2"
														maxFractionDigits="2" value="${data.billAmount}"
														var="billAmount" />
													<td><c:out value="${billAmount}"></c:out></td>
													<fmt:formatDate pattern="dd/MM/yyyy"
														value="${data.billDueDate}" var="date" />
													<td><c:out value="${date}"></c:out></td>
												</tr>
											</c:forEach>
										</table>
									</div>
									<%--  <div class="form-group padding-top-10">
             <label class="col-sm-2 control-label">Bill Date :</label>
          <div class="col-sm-4">
            <div class="input-group">
              <c:set var="now" value="<%=new java.util.Date()%>" />
              <fmt:formatDate pattern="dd/MM/yyyy" value="${now}" var="date"/>
              <form:input path="billDate" type="text" class="form-control" value="${date}" readonly="true"></form:input>
              <label for="datepicker" class="input-group-addon"><i class="fa fa-calendar"></i></label>
            </div>
          </div>
          <label class="col-sm-2 control-label">Remarks(if any) :</label>
          <div class="col-sm-4">
            <form:textarea path="billRemarks" type="text" class="form-control" ></form:textarea>
          </div>
        </div> --%>

									<div class="text-center padding-top-10">
										<button type="button" class="btn btn-success btn-submit"
											onclick="saveOrUpdateForm(this,'DemandNoticeGenerationsuccessfully!','DemandNoticeGeneration.html', 'saveform')">
											<spring:message code="water.GenateDemandNotice" />
										</button>
										<button type="button" class="btn btn-warning"
											onclick="resetForm(this)">
											<spring:message code="water.btn.reset" />
										</button>
										<button type="button" class="btn btn-danger"
											onclick="window.location.href='AdminHome.html'">
											<spring:message code="water.btn.cancel" />
										</button>
									</div>
								</c:if>

							</div>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
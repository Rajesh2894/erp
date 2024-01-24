<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript">
	$(document).ready(function(e) {
		$("#single").prop("checked", true);
		$('.lessthancurrdate').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '-0d',
			yearRange : "-100:-0"
		});

		if ($("#dataSize").val() > 0) {
			var radioValue = $("input[name='billCcnType']:checked").val();
			if (radioValue === 'S') {
				$('#selectall').prop('checked', true);
				$('.checkall').each(function() {
					this.checked = true;
				});
			}
		}
	
		var radioValue = $("input[name='billCcnType']:checked").val();
		if (radioValue === 'S') {
			$('#connNumber').show();
			$('#multiSearch').hide();
		} else {
			$('#connNumber').hide();
			$('#ccnNumber').val('');
			$('#multiSearch').show();
		}

		$(".conSelect").click(function() {
			var radioValueSel = $("input[name='billCcnType']:checked").val();
			if (radioValueSel === 'S') {
				$('#connNumber').show();
				$('#multiSearch').hide();
				$('#records').hide();
			} else {
				$('#connNumber').hide();
				$('#ccnNumber').val('');
				$('#records').hide();
				$('#multiSearch').show();
			}
		});

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

	function serachWaterBillData(obj) {
		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;

		var url = 'WaterBillGeneration.html?serachWaterBillData';

		$(theForm).attr('action', url);

		$(theForm).submit();
	}

	function saveData(element) {
		showloader(true);
		$(element).text("Please wait...");
		$(element).prop("disabled", true);
		setTimeout(function() {
			return saveOrUpdateForm(element,
					"Bill generation  done successfully.Proceed to see log!",
					'WaterBillGenerationLog.html', 'saveform');
		}, 2);
	}
	function resetbill(element) {
		$("#waterBillGenerationForm").submit();
	}
</script>

<style>
	.zone-ward .form-group > label[for="codDwzid3"] {
		clear: both;
	}
	.zone-ward .form-group > label[for="codDwzid3"],
	.zone-ward .form-group > label[for="codDwzid3"] + div {
		margin-top: 0.7rem;
	}
	
</style>

<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="water.bill.generation" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith" /> <i
					class="text-red-1">*</i> <spring:message code="water.ismandtry" />
				</span>
			</div>
			<form:form action="WaterBillGeneration.html" method="post"
				class="form-horizontal" name="WaterBillGeneration"
				id="waterBillGenerationForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="" value="${fn:length(command.entityList)}"
					id="dataSize" />
				<div class="form-group">
					<label class="control-label col-sm-2"><spring:message
							code="water.connectiontype" /><span class="mand">*</span></label>
					<div class="col-sm-4 padding-top-6">
						<label class="radio-inline" ><form:radiobutton
								path="billCcnType" value="S" id="single" class="conSelect" /> <spring:message
								code="water.meterReading.Single" /></label> <label class="radio-inline"><form:radiobutton
								path="billCcnType" value="M" id="All" class="conSelect" /> <spring:message
								code="water.bill.Multiple" /></label>
					</div>

					<div id='connNumber'>
						<label class="control-label col-sm-2 required-control"><spring:message
								code="water.ConnectionNo" /></label>
						<div class="col-sm-4">
							<form:input path="entity.csCcn" type="text" class="form-control"
								id="ccnNumber"></form:input>
						</div>
					</div>
				</div>


				<div id='multiSearch'>

					<div class="panel-group accordion-toggle margin-bottom-0"
						id="accordion_single_collapse">


						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#multiple">

										<spring:message code="water.meterReading.Multiple" />
									</a>
								</h4>
							</div>
							<div id="multiple" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<label class="control-label col-sm-2 required-control"><spring:message
												code="MeterReadingDTO.meterType" /></label>
										<div>
											<c:set var="baseLookupCode" value="WMN" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="entity.csMeteredccn" cssClass="form-control"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="eip.select" isMandatory="true" />
										</div>
										<label class="col-sm-2 control-label required-control"><spring:message
												code="water.ConnectionSize" /></label>
										<div>
											<c:set var="baseLookupCode" value="CSZ" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="entity.csCcnsize"
												cssClass="form-control required-control"
												hasChildLookup="false" hasId="true" showAll="true"
												selectOptionLabelCode="eip.select" isMandatory="true" />
										</div>

									</div>


								<div class="zone-ward">
									<div class="form-group  margin-bottom-0">
										<apptags:lookupFieldSet
											cssClass="form-control required-control" baseLookupCode="WWZ"
											hasId="true" pathPrefix="entity.codDwzid" isMandatory="true"
											hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true" showAll="true" />
									</div>
								</div>

									<div class="form-group  margin-bottom-0">
										<apptags:lookupFieldSet
											cssClass="form-control required-control" baseLookupCode="TRF"
											hasId="true" pathPrefix="entity.trmGroup" isMandatory="true"
											hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true" showAll="true" />
									</div>
								</div>
							</div>
						</div>

						<%-- <div class="form-group">
         
								
			  <label class="col-sm-2 control-label">Billing Frequency :</label>
           <div >
			   <c:set var="baseLookupCode" value="BSC" />
                    <apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="billFrequency" cssClass="form-control required-control"
								hasChildLookup="false" hasId="true" showAll="true"
								selectOptionLabelCode="eip.select" isMandatory="true"/> 
			</div>    
        </div>--%>

					</div>
				</div>
				<div class="text-center">
					<%-- <button type="button" class="btn btn-info"
						onclick="return serachWaterBillData(this);">
						<i class="fa fa-search"></i>
						<spring:message code="water.nodues.search" />
					</button> --%>

					<button type="button" class="btn btn-success btn-submit"
						onclick="return saveData(this);">
						<spring:message code="water.btn.submit" />
					</button>

					<button type="button" class="btn btn-warning"
						onclick="resetbill(this)">
						<spring:message code="water.nodues.reset" />
					</button>
					<%-- Defect #156202 --%>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
				<%-- <div id="records">
					<c:if test="${not empty command.entityList}">
						<div class="table-responsive max-height-300 margin-top-10">
							<table class="table table-bordered table-condensed table-striped">
								<tr>
									<th width="120"><label class="checkbox-inline"><input
											type="checkbox" id="selectall" /> <b><spring:message
													code="water.selectall" text="Select All" /></b></label></th>
									<th width="100"><spring:message code="water.nodues.srno" /></th>
									<th><spring:message code="water.ConnectionNo" /></th>
									<th><spring:message code="water.nodues.consumername" /></th>
								</tr>
								<c:forEach items="${command.entityList}" var="data"
									varStatus="status">
									<tr>
										<td><label class="checkbox margin-left-20"><form:checkbox
													path="entityList[${status.index}].pcFlg" value="Y"
													cssClass="checkall" id="${status.index}" /></label></td>
										<td><c:out value="${status.index+1}"></c:out></td>
										<td><c:out value="${data.csCcn}"></c:out></td>
										<td><c:out
												value="${data.csName} ${data.csMname} ${data.csLname}"></c:out></td>
									</tr>
								</c:forEach>
							</table>
						</div>
						<div class="form-group padding-top-10">
							<label class="col-sm-2 control-label"><spring:message
									code="water.waterBillGeneration.BillDate" /></label>
							<div class="col-sm-4">
								<div class="input-group">
									<c:set var="now" value="<%=new java.util.Date()%>" />
									<fmt:formatDate pattern="dd/MM/yyyy" value="${now}" var="date" />
									<form:input path="billDate" type="text" class="form-control"
										value="${date}" readonly="true"></form:input>
									<label for="datepicker" class="input-group-addon"><i
										class="fa fa-calendar"></i></label>
								</div>
							</div>
							<label class="col-sm-2 control-label"><spring:message
									code="water.waterBillGeneration.Remarks" /></label>
							<div class="col-sm-4">
								<form:textarea path="billRemarks" type="text"
									class="form-control" maxlength="100"></form:textarea>
							</div>
						</div>

						<div class="text-center padding-top-10">
							<button type="button" class="btn btn-success btn-submit"
								onclick="return saveData(this);">
								<spring:message code="water.btn.submit" />
							</button>
							<!--   <button type="button" class="btn btn-orange-3"  onclick="">Errors</button> -->
							<button type="button" class="btn btn-danger"
								onclick="window.location.href='AdminHome.html'">
								<spring:message code="water.btn.back" />
							</button>
						</div>
					</c:if>
				</div> --%>
			</form:form>
		</div>
	</div>
	<!-- End of info box -->
</div>

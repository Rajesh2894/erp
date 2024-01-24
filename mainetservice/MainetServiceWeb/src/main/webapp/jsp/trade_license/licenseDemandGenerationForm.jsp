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
		debugger;
		/* $('.lessthancurrdate').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '-0d',
			yearRange : "-100:-0"
		}); */

		/* if ($("#dataSize").val() > 0) {
			var radioValue = $("input[name='demandCcnType']:checked").val();
			if (radioValue === 'S') {
				$('#selectall').prop('checked', true);
				$('.checkall').each(function() {
					this.checked = true;
				});
			}
		} */

		var radioValue = $("input[name='demandCcnType']:checked").val();
		if (radioValue === 'S') {
			$('#connNumber').show();
			$('#multiSearch').hide();
		} else {
			$('#connNumber').hide();
			$('#ccnNumber').val('');
			$('#multiSearch').show();
		}

		$(".conSelect").click(function() {
			var radioValueSel = $("input[name='demandCcnType']:checked").val();
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

/* 	function serachWaterBillData(obj) {
		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;

		var url = 'LicenseDemandGenerationController.html?serachLicenseDemandlData';

		$(theForm).attr('action', url);

		$(theForm).submit();
	} */

	function saveData(element) {
		debugger;
		showloader(true);
		$(element).text("Please wait...");
		$(element).prop("disabled", true);
		setTimeout(function() {
			return saveOrUpdateForm(element,
					"License Demand  generation  done successfully.Proceed to see log!",
					'LicenseDemandGenerationController.html', 'saveform');
		}, 2);
	}
	function resetbill(element) {
		$("#licenseDemandGeneration").submit();
	}
</script>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="water.bill.generation" text=" "/>
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="water.fieldwith" /> <i
					class="text-red-1">*</i> <spring:message code="water.ismandtry" text=" " />
				</span>
			</div>
			<form:form action="LicenseDemandGenerationController.html" method="post"
				class="form-horizontal" name="licenseDemandGeneration"
				id="licenseDemandGeneration">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<%-- 	<form:hidden path="" value="${fn:length(command.entityList)}"
					id="dataSize" /> --%>
				<div class="form-group">
					<label class="control-label col-sm-2"><spring:message
							code="license.connectiontype" text="License Type"/><span class="mand">*</span></label>
					<div class="col-sm-4">
						<label class="radio-inline"><form:radiobutton
								path="demandCcnType" value="S" id="single" class="conSelect" /> <spring:message
								code="water.meterReading.Single" text=" "/></label> <label class="radio-inline"><form:radiobutton
								path="demandCcnType" value="M" id="All" class="conSelect" /> <spring:message
								code="water.bill.Multiple" text=" "/></label>
					</div>

					<div id='connNumber'>
						<label class="control-label col-sm-2 required-control"><spring:message
								code="trade.licNo" text="License No"/></label>
						<div class="col-sm-4">
							<form:input path="entity.trdLicno" type="text" class="form-control"
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

										<spring:message code="water.meterReading.Multiple" text=" "/>
									</a>
								</h4>
							</div>
							<div id="multiple" class="panel-collapse collapse in">
								<div class="panel-body">
									



									<div class="form-group  margin-bottom-0">
										<apptags:lookupFieldSet
											cssClass="form-control required-control" baseLookupCode="MWZ"
											hasId="true" pathPrefix="entity.trdWard" isMandatory="true"
											hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true" showAll="true" />
									</div>

								
								</div>
							</div>
						</div>

					

					</div>
				</div>
				<div class="text-center">
					

					<button type="button" class="btn btn-success btn-submit"
						onclick="return saveData(this);">
						<spring:message code="water.btn.submit"  text=" "/>
					</button>

					<button type="button" class="btn btn-warning"
						onclick="resetbill(this)">
						<spring:message code="water.nodues.reset"  text=" "/>
					</button>
				</div>
				
			</form:form>
		</div>
	</div>
	<!-- End of info box -->
</div>

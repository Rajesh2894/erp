<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script>
	$(document).ready(function() {


	});

	function fn_setZoneId(obj) {
		var zoneId = $("#" + obj.id + " option:selected").val();

		console.log("fn_setZoneId!" + zoneId);
		var postdata = 'zoneId=' + zoneId;
		var json = __doAjaxRequest('WardForm.html?setZoneId', 'post', postdata,
				false, 'json');
	}
</script>

<div class="clearfix" id="home_content">
	<div class="col-xs-12">

		<div class="row">

			

			<div class="reg-form-div">

				<div class="mand-label">
					<spring:message code="MandatoryMsg" text="MandatoryMsg" />
				</div>

				<form:form action="WardForm.html" name="frmWardEntryForm"
					id="frmWardEntryForm">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />

					<div class="form-elements">
						<div class="element">
							<span class="otherlink"> <apptags:submitButton
									successUrl="WardInformation.html"
									entityLabelCode="Bank Detail Entry" /> <apptags:backButton
									url="WardInformation.html" />
							</span>
						</div>
					</div>

					<div class="form-elements">
						<div class="element">
							<label for="entity_zoneid"><spring:message
									code="eip.area.SelZone" /> <span class="mand">*</span></label>
							<apptags:selectField isLookUpItem="true"
								
								items="${command.zoneLookUp}" hasId="false"
								selectOptionLabelCode="Select Zone Name"
								fieldPath="entity.zone.id" />
						</div>
					</div>


					<div class="form-elements">
						<div class="element">
							<label for="eip.ward.NameEN"><spring:message
									code="eip.ward.NameEN" /> <span class="mand">*</span></label>
							<form:input path="entity.prabhagnameen" />
						</div>
					</div>
					<div class="form-elements">
						<div class="element">
							<label for="eip.ward.NameMR"><spring:message
									code="eip.ward.NameMR" /> <span class="mand">*</span></label>
							<form:input path="entity.prabhagnamereg" />
						</div>
					</div>
				</form:form>

			</div>
		</div>
	</div>
</div>
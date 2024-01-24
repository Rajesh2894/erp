<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script>

$(document).ready(function(){
	var zoneId = '{command.zoneIdSel}';
	console.log( "ready!" +'${command.zoneIdSel}');
	$('#zoneid option[value='+'${command.zoneIdSel}'+']').attr('selected','selected');
});

function fn_setZoneId(obj) {
	var zoneId = $("#"+obj.id+" option:selected").val();

	console.log( "fn_setZoneId!" +zoneId);
	var postdata = 'zoneId='+zoneId;
	var json = __doAjaxRequest('AreaForm.html?setZoneId', 'post',	postdata, false, 'json');
} 
</script>

<div class="clearfix" id="home_content">
	<div class="col-xs-12">

		<div class="row">

		

			<div class="reg-form-div">

				<div class="mand-label">
					<spring:message code="MandatoryMsg" text="MandatoryMsg" />
				</div>

				<form:form action="AreaForm.html" name="frmAreaEntryForm"
					id="frmAreaEntryForm">
					
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					
					<div class="form-elements">
						<div class="element">
						 <span class="otherlink"> <apptags:submitButton
								successUrl="AreaInformation.html" entityLabelCode="Bank Detail Entry" />
							<apptags:backButton url="AreaInformation.html" />
						</span>
						</div>
					</div>
					
					<div class="form-elements">
						<div class="element">
							<label for="entity_zoneid"><spring:message
									code="eip.area.SelZone" /> <span class="mand">*</span></label>
							<apptags:selectField isLookUpItem="true"
								
								items="${command.allZones}"
								hasId="fals"
								selectOptionLabelCode="Select Zone Name"
								fieldPath="entity.zoneid.id" />
						</div>
					</div>
					
					
					<div class="form-elements">
						<div class="element">
							<label for="eip.area.NameEN"><spring:message
									code="eip.area.NameEN" /> <span class="mand">*</span></label>
							<form:input path="entity.longdescen"/>
						</div>
						<div class="element">
							<label for="eip.area.NameMR"><spring:message
									code="eip.area.NameMR" /> <span class="mand">*</span></label>
							<form:input path="entity.longdescreg"/>
						</div>
					</div>
					
					
					<div class="form-elements">
						<div class="element">
							<label for="eip.area.DescEN"><spring:message
									code="eip.area.DescEN" /> <span class="mand">*</span></label>
							<form:input path="entity.shortdescen"/>
						</div>
						<div class="element">
							<label for="eip.area.DescMR"><spring:message
									code="eip.area.DescMR" /> <span class="mand">*</span></label>
							<form:input path="entity.shortdescreg"/>
						</div>
					</div>
				</form:form>

			</div>
		</div>
	</div>
</div>
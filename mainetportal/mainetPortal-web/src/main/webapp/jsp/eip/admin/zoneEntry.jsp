<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script>

$(document).ready(function(){
	
	
});
</script>

<div class="clearfix" id="home_content">
	<div class="col-xs-12">

		<div class="row">

			

			<div class="reg-form-div">

				<div class="mand-label">
					<spring:message code="MandatoryMsg" text="MandatoryMsg" />
				</div>

				<form:form action="ZoneForm.html" name="frmZoneEntryForm"
					id="frmZoneEntryForm">
					
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					
					<div class="form-elements">
						<div class="element">
						 <span class="otherlink"> <apptags:submitButton
								successUrl="ZoneInformation.html" entityLabelCode="Bank Detail Entry" />
							<apptags:backButton url="ZoneInformation.html" />
						</span>
						</div>
					</div>
					
					
					
					<div class="form-elements">
						<div class="element">
							<label for="eip.zone.NameEN"><spring:message
									code="eip.zone.NameEN" /> <span class="mand">*</span></label>
							<form:input path="entity.shortdescen"/>
						</div>
					</div>
					
					<div class="form-elements">
						<div class="element">
							<label for="eip.zone.NameMR"><spring:message
									code="eip.zone.NameMR" /> <span class="mand">*</span></label>
							<form:input path="entity.shortdescreg"/>
						</div>
					</div>
					
				</form:form>

			</div>
		</div>
	</div>
</div>
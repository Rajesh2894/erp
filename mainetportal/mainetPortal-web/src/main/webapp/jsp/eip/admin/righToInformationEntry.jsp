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

				<form:form action="RightToInfoEntryForm.html" name="frmRtiEntryForm"
					id="frmRtiEntryForm">
					
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					
					<div class="form-elements">
						<div class="element">
						 <span class="otherlink"> <apptags:submitButton
								successUrl="RightToInformation.html" entityLabelCode="Right To Information Detail Entry" />
							<apptags:backButton url="RightToInformation.html" />
						</span>
						</div>
					</div>
					
					
					<div class="form-elements">
						<div class="element">
							<label for="entity_deptId_dpDeptid"><spring:message
									code="eip.deptLbl" /> <span class="mand">*</span></label>
							<apptags:selectField isLookUpItem="true"
								
								items="${command.allDepartmnents}"
								hasId="false"
								selectOptionLabelCode="Select Department Name"
								fieldPath="entity.deptId.dpDeptid" />
						</div>
					</div>
					<div class="form-elements">
						<div class="element">
							<label for="rti.subject"><spring:message
									code="rti.subject" /> <span class="mand">*</span></label>
							<form:input path="entity.rtiInfDescEng"/>
						</div>
					</div>
					
					<div class="form-elements">
						<div class="element">
							<label for="rti.subject"><spring:message
									code="eip.rti.subjectHn" /> <span class="mand">*</span></label>
							<form:input path="entity.rtiInfDescReg"/>
						</div>
					</div>
					<div class="form-elements">
						<div class="element">
							<apptags:formField fieldType="5" fieldPath="entity.rtiInfoFile" labelCode="eip.rti.uploadAttach" showFileNameHTMLId="true" />
						</div>
					</div>
				</form:form>

			</div>
		</div>
	</div>
</div>
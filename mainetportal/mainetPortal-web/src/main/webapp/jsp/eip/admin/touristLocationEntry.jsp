<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script>
	
</script>


<div class="clearfix" id="home_content">
	<div class="col-xs-12">

		<div class="row">

			



			<div class="mand-label">
				<spring:message code="MandatoryMsg" text="MandatoryMsg" />
			</div>

			<form:form action="TouristLocationForm.html"
				name="frmTouristLocEntryForm" id="frmTouristLocEntryForm">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div class="form-elements">
					<div class="element">
						<span class="otherlink"> <apptags:submitButton
								successUrl="TouristLocation.html"
								entityLabelCode="Tourist Location Entry" /> <apptags:backButton
								url="TouristLocation.html" />
						</span>
					</div>
				</div>

				<div class="form-elements">
					<div class="element">
						<label for="entity_category_id"><spring:message
								code="eip.tour.loc.SelBoxLb" /> : </label>
						<apptags:selectField isLookUpItem="true"
							items="${command.allCategories}" hasId="false"
							selectOptionLabelCode="Select Category"
							fieldPath="entity.category.catId" />
						<span class="mand">*</span>
					</div>

				</div>

				<div class="form-elements">
					<div class="element">
						<label for="eip.tour.loc.SHNameEN"><spring:message
								code="eip.tour.loc.SHNameEN" /> : </label>
						<form:input path="entity.shortdescEn" />
						<span class="mand">*</span>
					</div>

					<div class="element">
						<label for="eip.tour.loc.SHNameHN"><spring:message
								code="eip.tour.loc.SHNameHN" /> : </label>
						<form:input path="entity.shortdescReg" />
						<span class="mand">*</span>
					</div>
				</div>

				<div class="form-elements">
					<div class="element">
						<label for="eip.addressEN"><spring:message
								code="eip.addressEN" /> : </label>
						<form:input path="entity.addressEn" />
						<span class="mand">*</span>
					</div>

					<div class="element">
						<label for="eip.addressEN"><spring:message
								code="eip.addressREG" /> : </label>
						<form:input path="entity.addressReg" />
						<span class="mand">*</span>
					</div>
				</div>

				<div class="form-elements">
					<div class="element">
						<label for="eip.DescEN"><spring:message
								code="eip.DescEN" /> : </label>
						<form:textarea path="entity.longdescEn" />
						<span class="mand">*</span>
					</div>

					<div class="element">
						<label for="eip.DescReg"><spring:message
								code="eip.DescReg" /> : </label>
						<form:textarea  path="entity.longdescReg" />
						<span class="mand">*</span>
					</div>
				</div>



				<div class="form-elements">
					<div class="element">
					
						<apptags:formField fieldType="6" labelCode="eip.uploadImage" fieldPath="entity.image" showFileNameHTMLId="false"/>
					</div>
				</div>



			</form:form>

		</div>
	</div>
</div>

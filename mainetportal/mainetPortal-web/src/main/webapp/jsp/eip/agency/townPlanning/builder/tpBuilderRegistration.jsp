<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/tp/tpLicenseRegistration.js"></script>
<script src="js/tp/builderRegistration.js"></script>


<script>
	$(document).ready(function() {
		<c:if test="${command.viewdata eq 'R' || command.viewdata eq 'H' }"> 
			$('#testID').find(':input').attr('disabled', 'disabled').removeClass('mandClassColor').not(":button").addClass("disablefield");
		
		</c:if> 
	});
</script>


	
						<form:hidden path="coloumnCount1" id="coloumnCount1" value="${fn:length(command.partnerDetail)}" />
						<form:hidden path="rowCount" id="rowCount" />
						<input type="hidden" value="<spring:message code="pt.select"/>"
							id="hiddenSelect">


	 <div class="form-div" id="testID"> 
	 
						<div class="regheader"><spring:message code="tp.FirmDetails"/></div>
						<div class="form-elements clear">
							<div class="element">
								<label><spring:message code="tp.NameofFirm"/></label>
								<form:input path="entity.licAgency" cssClass="mandClassColor" disablefield="true" readonly="true" maxlength="100"/>
								<span class="mand">*</span>
							</div>
	
							<div class="element">
								<label><spring:message code="tp.TypeofFirm"></spring:message></label>
								<c:set var="baseLookupCode" value="TYF" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="entity.licFirmType" showOnlyLabel=""
									selectOptionLabelCode="tp.selTypeOfFirm" isMandatory="true"
									hasId="true" />
								<span class="mand">*</span>
							</div>
						</div>

						<div class="form-elements clear">
							<div class="element">
								<label><spring:message code="tp.DateOfBirth"></spring:message></label>
								<form:input  path="entity.licDob" maxlength="10"  cssClass="mandClassColor datepicker cal" disabled="true"/>
								<span class="mand">*</span>
							</div>
							<div class="element">
								<label><spring:message code="tp.Nationality"></spring:message></label>
								<c:set var="baseLookupCode" value="TRY" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode,1)}"
									path="entity.licNationality" showOnlyLabel=""
									selectOptionLabelCode="tp.SelectNationality" isMandatory="true"
									hasId="true" />
								<span class="mand">*</span>
							</div>
						</div>

						<div class="form-elements clear">
							<div class="element">
								<label><spring:message code="tp.RegistrationNo"></spring:message></label>
								<form:input path="entity.licCntArcregno" cssClass="registrationNo" maxlength="25" />
							</div>
							<div class="element">
								<label><spring:message code="tp.PanTin"></spring:message></label>
								<form:input path="entity.licPanno" cssClass="mandClassColor" maxlength="10"/>
								<span class="mand">*</span>
							</div>
						</div>
						<div class="form-elements clear">
							<div class="element">
								<label><spring:message code="tp.SalesTaxNo"/></label>
								<form:input path="entity.licSalesTax"
									cssClass="mandClassColor" maxlength="11"/>
								<span class="mand">*</span>
							</div>
							<div class="element">
								<label><spring:message code="tp.Vatno"/></label>
								<form:input path="entity.licVatNo"
									cssClass="mandClassColor"  maxlength="11"/>
								<span class="mand">*</span>
							</div>
						</div>


						<div class="form-elements clear">
							<div class="element">
								<label><spring:message code="tp.CategoryAppliedfor"></spring:message></label>
								<c:set var="baseLookupCode" value="CLS" />

								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="entity.licTechperClass" showOnlyLabel=""
									selectOptionLabelCode="tp.SelectCategory" isMandatory="true"
									hasId="true" />
								<span class="mand">*</span>
							</div>
							<div class="element">
								<label> <spring:message code="tp.Addres"/></label>
								<form:textarea path="entity.licApplicantAddr"
									cssClass="texboxcase mandClassColor" maxlength="500" />
								<span class="mand">*</span>
							</div>
						</div>

						<div class="form-elements">
							<div class="element">
								<label><spring:message code="tp.TelNo"/></label>
								<form:input path="entity.licPhoneno"
									cssClass=" hasNumber" maxlength="10" />
							</div>
							<div class="element">
								<label><spring:message code="tp.MobileNo"/></label>
								<form:input path="entity.licMobileno"
									cssClass="mandClassColor hasNumber disablefield" maxlength="10" readonly="true"/>
								<span class="mand">*</span>
							</div>
						</div>
						<div class="form-elements">
							<div class="element">
								<label><spring:message code="tp.EmailId"></spring:message></label>
								<form:input path="entity.licEmail" cssClass="mandClassColor" />
								<span class="mand">*</span>
							</div>
						</div>

						<div class="regheader"><spring:message code="tp.NameofPartner"></spring:message></div>
						<div class="pull-right clear">
							<input type="button" value="<spring:message code="tp.addRow"/>" id="anc_add" class="css_btn" onclick="addRow1()" /> 
								<input type="button" value="<spring:message code="tp.delRow"/>" id="anc_rem" class="css_btn" />
						</div>
						<div class="clear padding_5"></div>

						<table class="gridtable table123" id="secondParty${status.index}">
							<tbody class="hide" id="patnerdetail">
								<tr>
									<th><spring:message code="tp.BRDTitle"/> <span class="mand2">*</span></th>
									<th><spring:message code="tp.FirstName"/> <span class="mand2">*</span></th>
									<th><spring:message code="tp.MiddleName"></spring:message></th>
									<th><spring:message code="tp.LastName"/><span class="mand2">*</span></th>
								</tr>
								<c:forEach items="${command.partnerDetail}" var="in"
									varStatus="status">
									<tr class="tr_clone" id="partnerDetail${status.index}">
										<td><c:set var="baseLookupCode" value="TTL" /> <apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="partnerDetail[${status.index}].licTitle"
											cssClass="select_title"
											selectOptionLabelCode="tp.Title" hasId="true" isMandatory="true"
											tabIndex="1" showOnlyLabel="" /> 
										</td>
										<td><form:input
												path="partnerDetail[${status.index}].licFname"
												cssClass="input2 mandClassColor hasCharacter" hasId="true" /></td>
										<td><form:input
												path="partnerDetail[${status.index}].licMname"
												cssClass="input2 hasCharacter" hasId="true" /></td>
										<td><form:input
												path="partnerDetail[${status.index}].licLname"
												cssClass="input2 mandClassColor hasCharacter" hasId="true" /></td>
												</tr>
								</c:forEach>
							</tbody>
						</table>
						</div>
<script>
	$(document)
			.ready(
					function() {

						$("#anc_add")
								.click(
										function() {

											var clone = $(
													"secondParty${status.index} tr:last")
													.clone()
													.find('input')
													.val('')
													.end()
													.insertAfter(
															"secondParty${status.index} tr:last");
											
											var value1 = $("#coloumnCount1").val();
											var count = $("#rowCount").val();
											$("#rowCount").val(++count);
										});

						$("#anc_rem").click(function() {
							if ($('.table123 tr').size() > 1) {
								
								var count = $("#rowCount").val();
								$("#rowCount").val(--count);
								
								$('.table123 tr:last-child').remove();
							}
							if ($('.table123 tr').size() == 1) {
								$('#patnerdetail').addClass('hide');
							}

						});
						var length = $('.table123 tr').size();
						if(length > 1)
							$('#patnerdetail').removeClass('hide');

					});

</script>

<script>
	$(document).ready(
			function() {

				$("#cseTocId").prop('disabled', true).removeClass(
						"mandClassColor").addClass("disablefield");
				$("#cseCatId").prop('disabled', true).removeClass(
						"mandClassColor").addClass("disablefield");
				$("#cseAdvId").prop('disabled', true).removeClass(
						"mandClassColor").addClass("disablefield");
				$("#cseDeptid").prop('disabled', true).removeClass(
						"mandClassColor").addClass("disablefield");
				$("#csePeicDroa").prop('disabled', true).removeClass(
						"mandClassColor").addClass("disablefield");

				var value1 = $("#coloumnCount1").val();
				for ( var i = 0; i < value1; i++) {
					$("#peicName" + i).prop('disabled', true).removeClass(
							"mandClassColor").addClass("disablefield");
				}
				var value2 = $("#coloumnCount2").val();
				for ( var i = 0; i < value2; i++) {
					$("#droaName" + i).prop('disabled', true).removeClass(
							"mandClassColor").addClass("disablefield");
				}

			});

	function createRow1(count) {

		var str = '<tr id="partnerDetail'
				+ count
				+ '">'
				+ '<td>'
				+ '<c:set var="baseLookupCode" value="TTL" />'
				+ '<select name="partnerDetail['+ count+ '].licTitle" id="cpdTransfereeTitle'+count+'" class="mandClassColor input2">'
				+ '<option value="0">Title</option>'
				+ '	<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">'
				+ '		<option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}" >${lookUp.lookUpDesc}</option> '
				+ '</c:forEach>'
				+ '</select>'
				+ '</td>	'
				+ '<td>'
				+ '<input type="text" name="partnerDetail['+ count+ '].licFname" id="licFname'+ count+ '" Class="input2 maxLength2000 mandClassColor"/>'
				+ '</td>'
				+ '<td>'
				+ '<input type="text" name="partnerDetail['+ count+ '].licMname" id="licMname'+ count+ '" Class="input2 maxLength2000 "/>'
				+ '</td>'
				+ '<td>'
				+ '<input type="text" name="partnerDetail['+ count+ '].licLname" id="licLname'+ count+ '" Class="input2 maxLength2000 mandClassColor"/>'
				+ '</td>' + '</tr>';

		return str;
	}
</script>





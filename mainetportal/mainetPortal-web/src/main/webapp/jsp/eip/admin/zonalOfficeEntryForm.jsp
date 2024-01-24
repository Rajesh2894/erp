<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<style>
.labelwidh
{
width:20% !important;
}
</style>
<script>
$( document ).ready(function() {

	jQuery('.hasNumber').keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	  
	});
			
	jQuery('.hasCharacter').keyup(function () { 
	    this.value = this.value.replace(/[^a-z A-Z]/g,'');
	   
	});
	jQuery('.hasSpecialChara').keyup(function () { 
		
		if (this.value.match(/[^a-zA-Z ]/g )|| this.value.match(/[^\u0900-\u0954 ]/g) ){
			this.value = this.value.replace(/[^a-zA-Z\u0900-\u0954 ]/g, '');
		}   
	});

	jQuery('.maxLength20').keyup(function () { 
		 $(this).attr('maxlength','20');
	  
	});
	jQuery('.maxLength30').keyup(function () { 
		 $(this).attr('maxlength','30');
	  
	});

	jQuery('.hasDecimal').keyup(function () { 
	    this.value = this.value.replace(/[^0-9\.]/g,'');
	});

	jQuery('.hasPincode').keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	    $(this).attr('maxlength','6');
	});

	jQuery('.hasMobileNo').keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	    $(this).attr('maxlength','10');
	});

	});
</script>

<div class="form-div">
	<form:form method="post" action="ZonalOfficeEntryForm.html"
		name="frmZonalOffice" id="frmZonalOffice">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		
		
		<div class="form-elements">
			<div class="element" >
		<label for=""><spring:message code="eip.zone.zoneOfficename" /> :</label> 
		
				
					<apptags:inputField fieldPath="entity.nameEn" hasId="true" cssClass="hasSpecialChara maxLength30"/>
				
				<label for="" style="text-align: left;width:10% !important;"><spring:message code="eip.zone.eng" /></label> 
				</div>
				<div class="element">
					<apptags:inputField fieldPath="entity.nameReg" hasId="true" cssClass="hasSpecialChara maxLength30"/>
						<label for="" style="text-align: left;width:20% !important;"><spring:message code="eip.zone.reg" /></label>
						<span class="mand" style="color: red">*
				</span> 
				</div>
				
				
		
		</div>
		<div class="form-elements">
			<div class="element">
		<label for=""><spring:message code="eip.zone.prabhag" /> :</label> 
		
				
					<apptags:inputField fieldPath="entity.prabhagEn" hasId="true" cssClass="hasSpecialChara maxLength30"/>
						<label for="" style="text-align: left;width:10% !important;"><spring:message code="eip.zone.eng" /></label> 
				</div>
				<div class="element">
				<apptags:inputField fieldPath="entity.prabhagReg" hasId="true" cssClass="hasSpecialChara maxLength30"/>
				<label for="" style="text-align: left;width:20% !important;"><spring:message code="eip.zone.reg" /></label>
			
			</div>
		</div>
		<div class="form-elements">
			<div class="element">
		<label for=""><spring:message code="eip.zone.address" /> :</label> 
		
				
					<apptags:inputField fieldPath="entity.add1En" hasId="true" cssClass="maxLength30"/>
						<label for="" style="text-align: left;width:10% !important;"><spring:message code="eip.zone.eng" /></label> 
				</div>
				<div class="element">
				<apptags:inputField fieldPath="entity.add1Reg" hasId="true" cssClass="maxLength30"/>
				<label for="" style="text-align: left;width:20% !important;"><spring:message code="eip.zone.reg" /></label>
			<span class="mand" style="color: red">*
				</span> 
			</div>
		</div>
		
		<div class="form-elements">
			<div class="element">
			<label for=""></label> 
					<apptags:inputField fieldPath="entity.add2En" hasId="true" cssClass="maxLength30"/>
						<label for="" style="text-align: left;width:10% !important;"><spring:message code="eip.zone.eng" /></label> 
				</div>
				<div class="element">
				<apptags:inputField fieldPath="entity.add2Reg" hasId="true" cssClass="maxLength30"/>
				<label for="" style="text-align: left;width:20% !important;"><spring:message code="eip.zone.reg" /></label>
			
			</div>
		</div>
		
		<div class="form-elements">
			<div class="element">
			<label for=""></label> 
					<apptags:inputField fieldPath="entity.add3En" hasId="true" cssClass="maxLength30"/>
						<label for="" style="text-align: left;width:10% !important;"><spring:message code="eip.zone.eng" /></label> 
				</div>
				<div class="element">
				<apptags:inputField fieldPath="entity.add3Reg" hasId="true" cssClass="maxLength30"/>
				<label for="" style="text-align: left;width:20% !important;"><spring:message code="eip.zone.reg" /></label>
			
			</div>
		</div>
		
		<div class="form-elements">
			<div class="element">
		<label for=""><spring:message code="eip.zone.city" /> :</label> 
		
				
					<apptags:inputField fieldPath="entity.cityEn" hasId="true" cssClass="maxLength30"/>
						<label for="" style="text-align: left;width:10% !important;"><spring:message code="eip.zone.eng" /></label> 
				</div>
				<div class="element">
				<apptags:inputField fieldPath="entity.cityReg" hasId="true" cssClass="maxLength30"/>
				<label for="" style="text-align: left;width:20% !important;"><spring:message code="eip.zone.reg" /></label>
			<span class="mand" style="color: red">*
				</span> 
			</div>
		</div>
		<div class="form-elements">
			<div class="element">
		<label for=""><spring:message code="eip.zone.state" /> :</label> 
		
				
					<apptags:inputField fieldPath="entity.stateEn" hasId="true" cssClass="maxLength30"/>
						<label for="" style="text-align: left;width:10% !important;"><spring:message code="eip.zone.eng" /></label> 
				</div>
				<div class="element">
				<apptags:inputField fieldPath="entity.stateReg" hasId="true" cssClass="maxLength30"/>
				<label for="" style="text-align: left;width:20% !important;"><spring:message code="eip.zone.reg" /></label>
			<span class="mand" style="color: red">*
				</span> 
			</div>
		</div>
		<div class="form-elements">
			<div class="element">
		<label for=""><spring:message code="eip.zone.country" /> :</label> 
		
				
					<apptags:inputField fieldPath="entity.countryEn" hasId="true" cssClass="maxLength30"/>
						<label for="" style="text-align: left;width:10% !important;"><spring:message code="eip.zone.eng" /></label> 
				</div>
				<div class="element">
				<apptags:inputField fieldPath="entity.countryReg" hasId="true" cssClass="maxLength30"/>
				<label for="" style="text-align: left;width:20% !important;"><spring:message code="eip.zone.reg" /></label>
			<span class="mand" style="color: red">*
				</span> 
			</div>
		</div>
		
		<div class="form-elements">
			<div class="element">
		<label for=""><spring:message code="eip.zone.pincode" /> :</label> 
		
				
					<apptags:inputField fieldPath="entity.pincodeEn" hasId="true" cssClass="hasPincode"/>
						<label for="" style="text-align: left;width:10% !important;"><spring:message code="eip.zone.eng" /></label> 
				</div>
				<div class="element">
				<apptags:inputField fieldPath="entity.pincodeReg" hasId="true" cssClass="hasPincode"/>
				<label for="" style="text-align: left;width:20% !important;"><spring:message code="eip.zone.reg" /></label>
			<span class="mand" style="color: red">*
				</span> 
			</div>
		</div>
		
		<div class="form-elements">
			<div class="element">
		<label for=""><spring:message code="eip.zone.telephn" /> :</label> 
		
				
					<apptags:inputField fieldPath="entity.telno1En" hasId="true" cssClass="hasMobileNo"/>
						<label for="" style="text-align: left;width:10% !important;"><spring:message code="eip.zone.eng" /></label> 
				</div>
				<div class="element">
				<apptags:inputField fieldPath="entity.telno1Reg" hasId="true" cssClass="hasMobileNo" />
				<label for="" style="text-align: left;width:20% !important;"><spring:message code="eip.zone.reg" /></label>
			
			</div>
		</div>
		
		
		<div class="form-elements">
			<div class="element">
		<label for=""><spring:message code="eip.zone.email" /> :</label> 
		
				
					<apptags:inputField fieldPath="entity.email" hasId="true" cssClass="maxLength30"/>
						<span class="mand" style="color: red">*
				</span>  
				</div>
				
		</div>
		
		<div class="form-elements">
			<div class="element">
		<label for=""><spring:message code="eip.zone.incharge" /> :</label> 
		
				
					<apptags:inputField fieldPath="entity.inchargeEn" hasId="true" cssClass="hasSpecialChara maxLength30"/>
						<label for="" style="text-align: left;width:10% !important;"><spring:message code="eip.zone.eng" /></label> 
				</div>
				<div class="element">
				<apptags:inputField fieldPath="entity.inchargeReg" hasId="true" cssClass="hasSpecialChara maxLength30"/>
				<label for="" style="text-align: left;width:20% !important;"><spring:message code="eip.zone.reg" /></label>
			<span class="mand" style="color: red">*
				</span> 
			</div>
		</div>
		
		<div class="form-elements">
			<div class="element">
		<label for=""><spring:message code="eip.zone.officer1" /> :</label> 
		
				
					<apptags:inputField fieldPath="entity.officer1En" hasId="true" cssClass="hasSpecialChara maxLength30"/>
						<label for="" style="text-align: left;width:10% !important;"><spring:message code="eip.zone.eng" /></label> 
				</div>
				<div class="element">
				<apptags:inputField fieldPath="entity.officer1Reg" hasId="true" cssClass="hasSpecialChara maxLength30"/>
				<label for="" style="text-align: left;width:20% !important;"><spring:message code="eip.zone.reg" /></label>
			<span class="mand" style="color: red">*
				</span> 
			</div>
		</div>
		
		<div class="form-elements">
			<div class="element">
		<label for=""><spring:message code="eip.zone.officer2" /> :</label> 
		
				
					<apptags:inputField fieldPath="entity.officer2En" hasId="true" cssClass="hasSpecialChara maxLength30"/>
						<label for="" style="text-align: left;width:10% !important;"><spring:message code="eip.zone.eng" /></label> 
				</div>
				<div class="element">
				<apptags:inputField fieldPath="entity.officer2Reg" hasId="true" cssClass="hasSpecialChara maxLength30"/>
				<label for="" style="text-align: left;width:20% !important;"><spring:message code="eip.zone.reg" /></label>
			
			</div>
		</div>
		
		<div class="form-elements">
			<div class="element">
		<label for=""><spring:message code="eip.zone.officer3" /> :</label> 
		
				
					<apptags:inputField fieldPath="entity.officer3En" hasId="true" cssClass="hasSpecialChara maxLength30"/>
						<label for="" style="text-align: left;width:10% !important;"><spring:message code="eip.zone.eng" /></label> 
				</div>
				<div class="element">
				<apptags:inputField fieldPath="entity.officer3Reg" hasId="true" cssClass="hasSpecialChara maxLength30"/>
				<label for="" style="text-align: left;width:20% !important;"><spring:message code="eip.zone.reg" /></label>
			
			</div>
		</div>
		
		<div class="form-elements">
			<div class="element">
		<label for=""><spring:message code="eip.zone.officer4" /> :</label> 
		
				
					<apptags:inputField fieldPath="entity.officer4En" hasId="true" cssClass="hasSpecialChara maxLength30"/>
						<label for="" style="text-align: left;width:10% !important;"><spring:message code="eip.zone.eng" /></label> 
				</div>
				<div class="element">
				<apptags:inputField fieldPath="entity.officer4Reg" hasId="true" cssClass="hasSpecialChara maxLength30"/>
				<label for="" style="text-align: left;width:20% !important;"><spring:message code="eip.zone.reg" /></label>
			
			</div>
		</div>
		
		<div class="form-elements">
			<div class="element">
		<label for=""><spring:message code="eip.zone.zone" /> :</label> 
		
				<apptags:lookupField
									items="${command.getZoneLookUps()}"
									path="entity.zoneid.id" 
									selectOptionLabelCode="Select Zone" hasId="true"
									showAll="true" />  
					
						<span class="mand" style="color: red">*
				</span>  
				</div>
				
		</div>
		<div class="form-elements">
			<div class="element">
		<label for=""><spring:message code="eip.zone.school" /> :</label> 
		
				
					<apptags:inputField fieldPath="entity.schools" hasId="true" />
						<span class="mand" style="color: red">*
				</span>  
				</div>
				
		</div>
		<div class="form-elements">
			<div class="element">
		<label for=""><spring:message code="eip.zone.libraries" /> :</label> 
		
				
					<apptags:inputField fieldPath="entity.libraries" hasId="true" />
						<span class="mand" style="color: red">*
				</span>  
				</div>
				
		</div>
		
		
<div class="buttons btn-fld" align="center">
			<apptags:submitButton successUrl="ZonalOfficeDetail.html"
				entityLabelCode="Zonal Office" />
			<apptags:resetButton />
			<apptags:backButton url="ZonalOfficeDetail.html" />
		</div>
		</form:form>
		</div>
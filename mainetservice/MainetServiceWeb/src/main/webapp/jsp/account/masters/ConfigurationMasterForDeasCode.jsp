<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/validation.js"></script>
<script>
$( document ).ready(function() {
	if('${mode}' !='create')
	{
		$('input[type=text]').attr('readOnly',true);
		$('.comCpdCss').attr('disabled', 'disabled');
		
	}
	else
		{
		if($('#comCpdId').find('option').length <= 1 ) 
		{
		 var msg='<ul><li>'+getLocalMessage("account.configuration.noCompMsg")+'</li></ul>';
		  $('#errorId').html(msg);
			$('#errorDivId').show();	
		}


		}

	
	
	
	 $('#replication').hide();
	 
	 $('body').on('focus',".hasMyNumber", function(){
			$(".hasMyNumber").keyup(function(event) {
		    this.value = this.value.replace(/[^1-9]/g,'');
		    $(this).attr('maxlength', '1');
		    });
	}); 
});
$('#noOfLevel').on('input', function() {
	 var number = $('#noOfLevel').val();
	 $('#generate').html("");
	 number = number.replace(/^(0*)/,"");
	 $(this).val(number);
	
	   for (var i = 0; i < number; i++) {
		   var num=i+1;
	       $('#generate').removeClass('hide'); 
	       $('#generate').append("<div class='form-group'><label class='col-sm-2 control-label required-control'>Level "+num+" Description</label><div class='col-sm-4'><textarea id='codDescription"+i+"' name='tbAcCodingstructureDetEntity["+i+"].codDescription' class='form-control appendableDesc'></textarea></div>"
	    				+"<label class='col-sm-2 control-label required-control '>No of digits for "+num+" level</label><div class='col-sm-4'><input class='form-control hasMyNumber appendable' type='text' name='tbAcCodingstructureDetEntity["+i+"].codDigits' id='codDigits"+i+"'/></div> <input type='hidden' name='tbAcCodingstructureDetEntity["+i+"].codLevel' value='"+num+"'>");
	       }
	  });
	  


$('#comCpdId').on('change', function() {
 	if($('#comCpdId').val()!=0)
 		{
 		var compname=$('#comCpdId option:selected').text();	
 		
 		$('#desc').val(compname);
 		$('#componentName').val(compname);
 		 document.getElementsByClassName("appLable").innerHTML=compname;
 		$('#hiddenCompName').val(compname);
 		}
 	else
 		{$('#desc').val("");
 		}
 	

 });
</script>
<div class="form-div">
	<c:url value="${saveAction}" var="url_form_submit" />
	<c:url value="${mode}" var="form_mode" />
	<form:form class="form-horizontal"
		modelAttribute="tbAcCodingstructureMas" id="tbAcCodingstructureMas"
		cssClass="form-horizontal" method="POST" action="${url_form_submit}">

		<form:hidden path="alreadyExist" id="alreadyExist"></form:hidden>

		<form:hidden path="componentName" id="hiddenCompName"></form:hidden>
		<c:set var="name" value="${componentName}" />
		<div class="error-div alert alert-danger alert-dismissible"
			id="errorDivId" style="display: none;">
			<button type="button" class="close" onclick="closeOutErrBox()"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<span id="errorId"></span>
		</div>

		<c:if test="${form_mode ne 'create'}">
			<form:hidden path="codcofId" />
			<form:hidden path="userId" />
			<form:hidden path="comCpdId" />
			<form:hidden path="comChagflag" />
		</c:if>

		<div class="form-group">
			<label class="col-sm-2 control-label required-control"><spring:message
					code="account.configuration.compname"></spring:message></label>
			<c:if test="${form_mode eq 'create'}">

				<apptags:lookupField items="${component}" path="comCpdId"
					cssClass="form-control chosen-select-no-results"
					hasChildLookup="false" hasId="true" showAll="false"
					selectOptionLabelCode="applicantinfo.label.select"
					isMandatory="true" />

			</c:if>

			<c:if test="${form_mode ne 'create'}">
				<div class="col-sm-4">
					<form:select id="comCpdId" path="comCpdId"
						cssClass="form-control mandColorClass chosen-select-no-results comCpdCss"
						disabled="${viewMode}">
						<form:option value="">
							<spring:message code="account.select.componentName" text="Select Component Name" />
						</form:option>
						<c:forEach items="${tempList}" varStatus="status" var="levelChild">
							<form:option code="${levelChild.lookUpCode}"
								value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
						</c:forEach>
					</form:select>
				</div>
			</c:if>



			<label class="col-sm-2 control-label required-control"><spring:message
					code="account.configuration.nooflevel" /></label>
			<div class="col-sm-4">
				<form:input type="text" class="form-control hasMyNumber"
					path="testCodNoLevel" id="noOfLevel"></form:input>
			</div>

		</div>




		<div class="hide" id="generate"></div>
		<c:if test="${form_mode eq 'create'}">
			<c:if
				test="${defaultFlagStatus == 'Y' || nonDefaultFlagStatus == 'N'}">
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="chequebookleaf.master.default.flag" text="Default Flag" /></label>
					<div class="col-sm-4">
						<label class="checkbox-inline padding-top-0"><input
							id="defineOnflag" name="defineOnflag" type="checkbox" value="Y"></label>
					</div>
				</div>
			</c:if>
		</c:if>
		<c:if
			test="${not empty tbAcCodingstructureMas.tbAcCodingstructureDetEntity}">
			<c:forEach
				items="${tbAcCodingstructureMas.tbAcCodingstructureDetEntity}"
				var="list" varStatus="cnt">
				<div class="form-group">

					<label class="col-sm-2 control-label"><spring:message
							code="account.level" text="Level" /> ${cnt.count}
						<spring:message
							code="account.common.desc" text="Description" /></label>
					<div class="col-sm-4">
						<form:textarea
							path="tbAcCodingstructureDetEntity[${cnt.count-1}].codDescription"
							class="form-control appendableDesc"
							id="codDescription${cnt.count-1}"></form:textarea>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="account.digit" text="No of digits for" />
						${cnt.count} <spring:message
							code="account.level" text="level" /></label>
					<div class="col-sm-4">
						<form:input class="form-control appendable"
							path="tbAcCodingstructureDetEntity[${cnt.count-1}].codDigits"
							id="codDigits${cnt.count-1}" />
					</div>

					<input type="hidden"
						name="tbAcCodingstructureDetEntity[${cnt.count-1}].codLevel"
						value="${cnt.count}">
				</div>
				<form:hidden
					path="tbAcCodingstructureDetEntity[${cnt.count-1}].codcofdetId" />
				<form:hidden
					path="tbAcCodingstructureDetEntity[${cnt.count-1}].userId" />
				<form:hidden
					path="tbAcCodingstructureDetEntity[${cnt.count-1}].langId" />
				<form:hidden
					path="tbAcCodingstructureDetEntity[${cnt.count-1}].orgId" />

			</c:forEach>

		</c:if>

		<c:if test="${form_mode ne 'create'}">
			<div class="form-group">
				<c:if
					test="${defaultFlagStatus == 'Y' || nonDefaultFlagStatus == 'N'}">
					<label class="col-sm-2 control-label"><spring:message
							code="chequebookleaf.master.default.flag" text="Default Flag" /></label>
					<div class="col-sm-4">
						<label class="checkbox-inline padding-top-0"><form:checkbox
								path="defineOnflag" value="Y" id="defineOnflag" /></label>
					</div>
				</c:if>
				<label class="col-sm-2 control-label required-control"> <spring:message
						code="account.budget.code.master.status" text="Status"></spring:message>
				</label>
				<div class="col-sm-4">
					<form:select id="comAppflag" path="comAppflag"
						cssClass="form-control mandColorClass ">
						<c:forEach items="${statuslookUpId}" varStatus="status"
							var="levelChild">
							<form:option code="${levelChild.lookUpCode}"
								value="${levelChild.lookUpCode}">${levelChild.descLangFirst}</form:option>
						</c:forEach>
					</form:select>
				</div>
			</div>
		</c:if>

		<c:choose>

			<c:when test="${form_mode eq 'create'}">
				<div class="text-center padding-bottom-20" id="divSubmit">
					<button type="button" class="btn btn-success btn-submit" id="submit"
						onclick="saveConfiguration(this)">
						<spring:message code="account.bankmaster.save" text="Save" />
					</button>
					<input type="button" id="Reset" class="btn btn-warning createData"
						value="<spring:message code="account.bankmaster.reset" text="Reset"/>"></input> <input type="button" class="btn btn-danger"
						onclick="window.location.href='ConfigurationMaster.html'"
						value="<spring:message code="account.bankmaster.back" text="Back"/>" />

				</div>
			</c:when>
			<c:otherwise>
				<div class="text-center padding-bottom-20" id="divSubmit">

					<c:if test="${form_mode eq 'update'}">
						<button type="button" class="btn btn-success btn-submit" id="submitEdit"
							onclick="saveConfiguration(this)">
							<spring:message code="account.configuration.save" />
						</button>
					</c:if>
					<input type="button" class="btn btn-danger"
						onclick="window.location.href='ConfigurationMaster.html'"
						value="<spring:message code="account.bankmaster.back" text="Back"/>" id="cancelEdit" />

				</div>
			</c:otherwise>
		</c:choose>

	</form:form>
</div>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script src="js/tborganisation/orgentryform.js"></script>
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<script>
$( document ).ready(function() {
	   
	   
	   var langFlag = getLocalMessage('admin.lang.translator.flag');
		if(langFlag ==='Y'){
			$("#tbOrganisation_oNlsOrgname").keyup(function(event){
				var no_spl_char;
				no_spl_char = $("#tbOrganisation_oNlsOrgname").val().trim();
				if(no_spl_char!='')
				{
					commonlanguageTranslate(no_spl_char,'tbOrganisation_oNlsOrgnameMar',event,'');
				}else{
					$("#tbOrganisation_oNlsOrgnameMar").val('');
				}
			});
			$("#tbOrganisation_orgAddress").keyup(function(event){
				var no_spl_char;
				no_spl_char = $("#tbOrganisation_orgAddress").val().trim();
				if(no_spl_char!='')
				{
					commonlanguageTranslate(no_spl_char,'tbOrganisation_orgAddressMar',event,'');
				}else{
					$("#tbOrganisation_orgAddressMar").val('');
				}
			});
		}
});
jQuery('.hasCharSymbols').keyup(function (evt) {
	this.value = this.value.replace(/[^a-z A-Z @ # $ & - `/]/g,'');	
});

$(function() {
	$("#orgMaster").validate();
});

$('select.form-control.mandColorClass').change(function(){
    var check = $(this).val();
    var validMsg =$(this).attr("data-msg-required");
    var optID=$(this).attr('id');
    var fildID="#"+optID+"_error_msg";
    if(check == '' || check == '0'){
   		 $(this).parent().switchClass("has-success","has-error");
		     $(this).addClass("shake animated");
		     $(fildID).addClass('error');
		     $(fildID).css('display','block');
		     $(fildID).html(validMsg);
	}else
    {$(this).parent().switchClass("has-error","has-success");
    $(this).removeClass("shake animated");
    $(fildID).css('display','none');}
});
</script>
<div id="heading_wrapper">
	<div class="widget">
 
		<input type="hidden" id="orgDefaultStatus" value="${userSession.organisation.defaultStatus}"></input>
		<c:url value="${saveAction}" var="url_form_submit" />
		
	
		<form:form method="post" action="Organisation.html" name="orgMaster"
			id="orgMaster" class="form-horizontal" commandName="tbOrganisation">
			<div class="error-div alert alert-danger alert-dismissible" style="display:none;" id="errorDivCustBankMas">
				
			</div>
			<c:if test="${mode == 'create'}">
				<!-- Store data in hidden fields in order to be POST even if the field is disabled -->
			
			</c:if>
			<input type="hidden" id="modeId" value="${mode}">
			<form:hidden path="defaultStatus"/>
			<div class="form-group">
					<label for="tbOrganisation_ulbOrgid" class="col-sm-2 control-label required-control"><spring:message code="tbOrganisation.orgid"/></label>
				<div class="col-sm-4">
				<form:input id="tbOrganisation_orgid" path="orgid" type="hidden"/>
				
				<c:if test="${mode == 'create'}">
					<form:input id="tbOrganisation_ulbOrgid" path="ulbOrgID" class="form-control mandClassColor hasNumber" maxLength="500"  
					data-rule-required="true" data-msg-required="Please enter Organisation Id"/>
				</c:if>	
					<c:if test="${mode != 'create'}">	
					<form:input id="tbOrganisation_ulbOrgid" path="ulbOrgID" class="form-control mandClassColor hasNumber" maxLength="500"  readonly="true"
					data-rule-required="true" data-msg-required="Please enter Organisation Id"/>
					</c:if>
				</div>
				
				<label for="tbOrganisation_orgShortNm required-control" class="col-sm-2 control-label required-control"><spring:message code="tbOrganisation.orgShortNm"/></label>
					<div class="col-sm-4">
					<c:if test="${mode == 'create'}">
						<form:input id="tbOrganisation_orgShortNm" path="orgShortNm" class="form-control mandColorClass hasCharSymbols" maxLength="5" style="text-transform: uppercase;"
						data-rule-required="true" data-msg-required="Please enter short code"/>
					</c:if>
					<c:if test="${mode != 'create'}">
						<form:input id="tbOrganisation_orgShortNm" path="orgShortNm" class="form-control mandClassColor hasCharSymbols" maxLength="5" readonly="true"
						data-rule-required="true" data-msg-required="Please enter short code"/>
					</c:if>
					</div>
					
			
			    
				
			</div>
			<div class="form-group">
					
					<label for="tbOrganisation_oNlsOrgname " class="col-sm-2 control-label required-control"><spring:message code="tbOrganisation.oNlsOrgname"/></label>
					<div class="col-sm-4">
					<form:input id="tbOrganisation_oNlsOrgname" path="oNlsOrgname" class="form-control mandColorClass" maxLength="500"
					data-rule-required="true" data-msg-required="Please enter Name(English)"/>
					</div>
					
					<label for="tbOrganisation_oNlsOrgnameMar" class="col-sm-2 control-label  required-control"><spring:message code="tbOrganisation.oNlsOrgnameMar"/></label>
					<div class="col-sm-4">
					<form:input id="tbOrganisation_oNlsOrgnameMar" path="oNlsOrgnameMar" class="form-control mandColorClass" maxLength="500" 
					data-rule-required="true" data-msg-required="Please enter Name(Regional)" />
				</div>
				
			</div>
			<div class="form-group">
				
				<label for="tbOrganisation_esdtDate " class="col-sm-2 control-label"><spring:message code="tbOrganisation.esdtDate"/></label>
					<div class="col-sm-4">
					<div class="input-group">
					<c:choose>
						<c:when test="${mode == 'create' }">
							<form:input id="tbOrganisation_esdtDate" path="estDtStr" class="form-control mandClassColor datepicker1" maxLength="50" readonly="true"/>
						</c:when>
						<c:otherwise>
							<form:input id="tbOrganisation_esdtDate" path="estDtStr" class="form-control mandClassColor datepicker1" maxLength="50"/>
						</c:otherwise>
					</c:choose>
					<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
					</div>
				</div>
				
				
				
				<label for="tbOrganisation_trnscDate " class="col-sm-2 control-label required-control"><spring:message code="tbOrganisation.transactionDate"/></label>
					<div class="col-sm-4">
					<div class="input-group">
					<c:choose>
						<c:when test="${mode == 'create' }">
							<form:input id="tbOrganisation_trnscDate" path="trnsDtStr" class="form-control mandColorClass datepicker1" maxLength="50" readonly="true"/>  <!-- readonly="true" -->
						</c:when>
						<c:otherwise>
							<form:input id="tbOrganisation_trnscDate" path="trnsDtStr" class="form-control mandColorClass datepicker1" maxLength="50"/>
						</c:otherwise>
					</c:choose>
					<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
					</div>
				</div>
			</div>
			<div class="form-group">
					<label for="tbOrganisation_orgAddress " class="col-sm-2 control-label"><spring:message code="tbOrganisation.orgAddress" text="Address"/></label>
					<div class="col-sm-4">
					<form:input id="tbOrganisation_orgAddress" path="orgAddress" class="form-control mandClassColor" maxLength="500"  />
				</div>
				
					<label for="tbOrganisation_orgAddressMar " class="col-sm-2 control-label"><spring:message code="tbOrganisation.orgAddressMar" text="Address"/></label>
					<div class="col-sm-4">
					<form:input id="tbOrganisation_orgAddressMar" path="orgAddressMar" class="form-control mandClassColor" maxLength="500"  />
				</div>
			</div>
		
			
			<div class="form-group">
			<label class="col-sm-2 control-label required-control"><spring:message code="tbOrganisation.orgCpdIdState"/></label>
			<div class="col-sm-4">
				<spring:message code="org.state.msg" text="State must not be empty" var="StateMsg"/> 
				<form:select path="orgCpdIdState" class="form-control chosen-select-no-results mandColorClass" id="orgCpdIdState" 
				data-rule-required="true" data-msg-required="${StateMsg}">
					<form:option value="">Select</form:option>
						<c:forEach items="${sttList}" var="lookUp">
						<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
						</c:forEach>
				</form:select>
				<label id="orgCpdIdState_error_msg" style="display:none; border:none;"></label>
			</div>
			<label class="col-sm-2 control-label required-control "><spring:message code="tbOrganisation.orgCpdId"/></label>
			<div class="col-sm-4">
				<spring:message code="org.Type.msg" text="Type must not be empty" var="TypeMsg"/>
				<form:select path="orgCpdId" class="form-control chosen-select-no-results mandColorClass" id="orgCpdId"
				data-rule-required="true" data-msg-required="${TypeMsg}">
					<form:option value="">Select</form:option>
						<c:forEach items="${lookUpList}" var="lookUp">
						<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
						</c:forEach>
				</form:select>
				<label id="orgCpdId_error_msg" style="display:none; border:none;"></label>
			</div>
			</div>		
			
		<div class="form-group">
			<label class="col-sm-2 control-label required-control "><spring:message code="tbOrganisation.orgCpdIdDis"/></label>
			<div class="col-sm-4">
				<spring:message code="org.district.msg" text="District must not be empty" var="DistMsg"/>
				<form:select path="orgCpdIdDis" class="form-control chosen-select-no-results mandColorClass" id="orgCpdIdDis"
				data-rule-required="true" data-msg-required="${DistMsg}">
					<form:option value="">Select</form:option>
						<c:forEach items="${disList}" var="lookUp">
						<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
						</c:forEach>
				</form:select>
				<label id="orgCpdIdDis_error_msg" style="display:none; border:none;"></label>
			</div>
			
			<label class="col-sm-2 control-label required-control "><spring:message code="tbOrganisation.orgCpdIdOst"/></label>
			<div class="col-sm-4">
				<spring:message code="org.subtype.msg" text="Subtype must not be empty" var="SubTypeMsg"/>
				<form:select path="orgCpdIdOst" class="form-control chosen-select-no-results mandColorClass" id="orgCpdIdOst"
				data-rule-required="true" data-msg-required="${SubTypeMsg}">
					<form:option value="">Select</form:option>
						<c:forEach items="${ostList}" var="lookUp">
						<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
						</c:forEach>
				</form:select>
				<label id="orgCpdIdOst_error_msg" style="display:none; border:none;"></label>
			</div>
			</div>
			
			
			<div class="form-group">
			
						<label class="col-sm-2 control-label required-control "><spring:message code="tbOrganisation.orgCpdIdDiv"/></label>
			<div class="col-sm-4">
				<spring:message code="org.division.msg" text="Division must not be empty" var="DivisionMsg"/>
				<form:select path="orgCpdIdDiv" class="form-control chosen-select-no-results mandColorClass" id="orgCpdIdDiv"
				data-rule-required="true" data-msg-required="${DivisionMsg}">
					<form:option value="">Select</form:option>
						<c:forEach items="${divisionList}" var="lookUp">
						<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
						</c:forEach>
				</form:select>
				<label id="orgCpdIdDiv_error_msg" style="display:none; border:none;"></label>
			</div> 
			
					<label for="tbOrganisation_orgemailid " class="col-sm-2 control-label "><spring:message code="tbOrganisation.orgEmailId"/></label>
					<div class="col-sm-4">
					<form:input id="tbOrganisation_orgemailid" path="orgEmailId" class="form-control mandClassColor" maxLength="500"  />
					</div>
			
			</div>
			
			<div class="form-group">
					<label for="tbOrganisation_orgLatitude " class="col-sm-2 control-label"><spring:message code="tbOrganisation.orgLatitude" text="Latitude"/></label>
					<div class="col-sm-4">
					<form:input id="tbOrganisation_orgLatitude" path="orgLatitude" class="form-control" maxLength="10" onchange="validateLatitude()"/>
				</div>
				
					<label for="tbOrganisation_orgLongitude " class="col-sm-2 control-label"><spring:message code="tbOrganisation.orgLongitude" text="Longitude"/></label>
					<div class="col-sm-4">
					<form:input id="tbOrganisation_orgLongitude" path="orgLongitude" class="form-control" maxLength="10" onchange="validateLongitude()" />
				</div> 
	   		</div>
	   
			<div class="form-group">
			
			<label for="tbOrganisation_gstNumber" class="col-sm-2 control-label"><spring:message code="" text="GST Number"/></label>
					<div class="col-sm-4">
  						<form:input id="tbOrganisation_gstNumber" path="orgGstNo" class="form-control" maxLength="15"  />
					</div>
			</div>
		
			<form:hidden path="deleteFlag" id="hiddenDeleteFlag" value="N"/>
			<form:hidden path="filePath" id="hiddenLogoPath" value="${filePath}"/>
			<div class="form-group">
						<label class="control-label col-sm-2">Upload Logo</label>
						
							<div class="col-sm-2">
								<apptags:formField fieldType="7" labelCode=""
								hasId="true" fieldPath="OLogo"
								isMandatory="false" showFileNameHTMLId="true"
								fileSize="BND_COMMOM_MAX_SIZE"
								maxFileCount="CHECK_LIST_MAX_COUNT"
								validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
								currentCount="0" />
							</div>
							<div id="uploadPreview" class="col-sm-4">
								<ul></ul>
							</div>
			</div>
			
			<div class="text-center">
				<button type="button" id="save" class="btn btn-success" onclick="saveData(this)"><spring:message code='master.save'/></button>
				<input type="reset" class="btn btn-warning" value="<spring:message code="reset.msg"/>" onclick="resetOrg()">
				<button type="button" id="back" class="btn btn-danger" onclick="window.location.href='Organisation.html'"><spring:message code='back.msg'/></button>
			
			</div>
			
			<div class="table-responsive">				
				<table id="childGrid" class="table table-bordered"></table>
				<div id="pagered"></div> 
			</div>	
			
			
			
		</form:form>
		
		

	</div>
	</div>

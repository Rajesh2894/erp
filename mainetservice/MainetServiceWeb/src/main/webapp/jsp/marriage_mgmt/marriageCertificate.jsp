<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="marathiConvert" class="com.abm.mainet.common.utility.Utility"></jsp:useBean>
<link href="assets/css/print.css" media="print" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/common/dsc.js"></script>

<script>
    function printContent(el) {
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
    }
    
    function editMarriageDetails(marId){
    	var requestData = 'marId=' + marId + '&type=D' +'&saveMode=A';
    	var response = __doAjaxRequest('MarriageRegistration.html?form', 'POST', requestData,
    			false, 'html');
    	$('.content-page').removeClass('ajaxloader');
    	$('.content-page').html(response);
    	prepareDateTag();
    }
    
    
    function generateSerialNo(element) {
        var errorList = [];
		/* var volume = $("#volume").val();
	
		if (volume == undefined || volume == '') {
			errorList.push(getLocalMessage('mrm.vldnn.volume'));
		} */
		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
			return false;
		} else {
			var requestData = element;
	    	var divName = '.content-page'
	    	var formName = findClosestElementId(element, 'form');
	    	var theForm = '#' + formName;
	    	var requestData = __serializeForm(theForm);
	    	var ajaxResponse = __doAjaxRequest('MarriageCertificateGeneration.html?generateSerialNo', 'POST',
	    			requestData, false, '', 'html');
	    	
	    	$(divName).removeClass('ajaxloader');
	    	$(divName).html(ajaxResponse);
	    	prepareTags();	
		}

    }
    
    function concludeMRMCertificate(element){
    	/* return saveOrUpdateForm(element, "",
    			'AdminHome.html', 'finalSaveAfterMarriageCertificate'); */
    	var requestData = element;
    	var divName = '.content-page'
    	var formName = findClosestElementId(element, 'form');
    	var theForm = '#' + formName;
    	var requestData = __serializeForm(theForm);
    	var ajaxResponse = __doAjaxRequest('MarriageCertificateGeneration.html?finalSaveAfterMarriageCertificate', 'POST',
    			requestData, false, '', 'html');
    	
    	$(divName).removeClass('ajaxloader');
    	$(divName).html(ajaxResponse);
    	prepareTags();
    }
</script>

<style>
.width-auto {
	width: auto !important;
}

.height-120 {
	height: 120px !important;
}

.margin-auto {
	margin: auto;
}

.view-mode {
	border: none !important;
	border-bottom: 1px dashed #848484 !important;
}

.view-mode[disabled] {
	background-color: #fff !important;
	cursor: inherit !important;
}

.bnd-seal {
	border: 1px solid #000000;
	padding: 10px;
}

.cert-outer-div {
	border: 2px solid #000000;
	padding: 2px;
}

.cert-inner-div {
	border: 4px solid #000000;
	padding: 5px;
}

.cert-inner-div h4 {
	font-size: 18px;
	line-height: inherit;
	background: none;
	border: unset;
}

.padding-0 {
	padding: 0px !important;
}

.cert-inner-div .cert-head h4 {
	font-size: inherit;
	margin: 5px 0px;
}

.cert-inner-div .cert-body {
	padding: 40px;
}

.cert-inner-div .cert-body h4 {
	padding: 0px;
	margin: 5px 0px;
}

.cert-inner-div .cert-body .table-responsive {
	padding: 0px;
}

.cert-inner-div .cert-body .sub-ref {
	overflow: hidden;
	width: 80%;
	margin: 0 auto;
}

.cert-inner-div .cert-body .amt-words {
	background-color: #eeeeee;
	font-weight: 600;
	padding: 1.5em 0.5em;
}

.border-bottom-black {
	border-bottom: 1px solid #000000;
}

.candidate-img {
	text-align: center;
}

.candidate-img img {
	border: 1px solid #000000;
	height: 150px;
	padding: 5px;
	margin: auto;
}
.line-height-2 {
	line-height: 2rem;
}
</style>

<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content animated slideInDown">
	<div class="widget" id="receipt">
		
		<div class="widget-content padding padding-40">

			<form:form action="MarriageRegistration.html" name="certificate"
				id="certificate" class="form-horizontal">
				<div class="cert-outer-div">
					<div class="cert-inner-div">
						<div class="cert-head border-bottom-black padding-30">
							<div class="col-xs-2 col-lg-2">
								<div class="candidate-img left">
									<!--if husband photo path not found  -->
									<c:choose>
									  <c:when test="${not empty command.marriageDTO.husbPhoPath}">
									   	<img src="${command.marriageDTO.husbPhoPath}" class="img-responsive">
									  </c:when>
									  <c:otherwise>
									  	<img src="images/default_img_male.png" class="img-responsive">
									  </c:otherwise>
									</c:choose>
									
								</div>
							</div>
							<div class="text-center cert-head-center-content col-xs-8 col-lg-8 padding-top-30 padding-bottom-20">
								<img src="images/national_emblem_logo.png"
									class="img-responsive nat-emblem width-auto height-120 margin-auto padding-top-20 margin-bottom-15">
								<h3 class="text-bold">
									<spring:message code="mrm.gov.mhr" text="Maharashtra" />
								</h3>
								<h3 class="text-bold">GOVERNMENT OF MAHARASHTRA</h3>
							</div>
							<div class="col-xs-2 col-lg-2">
								<div class="candidate-img right">
									<c:choose>
									  <c:when test="${empty command.marriageDTO.wifePhoPath}">
									   	<img src="images/default_img_female.png" class="img-responsive">
									  </c:when>
									  <c:otherwise>
									  	<img src="${command.marriageDTO.wifePhoPath}" class="img-responsive">
									  </c:otherwise>
									</c:choose>
								</div>
							</div>
							<div class="clear"></div>
							<!-- D#129075 -->
							<div class="text-center padding-bottom-30 info-2">
								<h4>
									<spring:message code="mrm.kdmc.halfAdd" text="KDMC add" />
										${command.marriageDTO.ward1RegDesc}								
									<spring:message code="mrm.kdmc.fullAdd" text="KDMC add" />
								</h4>
								
								<h4>Marriage Registration Office: ${command.marriageDTO.ward1Desc} Kalyan Dombivli
									Municipal Corporation, Kalyan (W)</h4>
								<h4>
									<spring:message code="mrm.kdmc.subAdd" text="KDMC add" />
								</h4>
								<h4>Taluka: Kalyan, District: Thane, Maharashtra, India</h4>
							</div>
							
						</div>

						<!-- Certificate Body starts -->
						<div class="cert-body">
							<h5 class="text-bold text-center">
								<spring:message code="mrm.formE" text="form E" />
								/ Form 'E'
							</h5>
							<h4 class="text-center">
								<spring:message code="mrm.mrg.certificate" text="certificate" />
							</h4>
							<h4 class="text-center padding-top-20 info-3">Certificate of Registration of
								Marriage</h4>
							<h5 class="text-center info-4">
								<spring:message code="mrm.cert.section" text="Section" />
							</h5>
							<p class="margin-bottom-25 padding-top-30 line-height-2">
								<spring:message code="mrm.cert.reg.certified" text="Certified" />
								<spring:message code="mrm.cert.husb.name" text="HUSB Name" />
								<span class="text-bold">${command.marriageDTO.husbandDTO.firstNameReg}
									${command.marriageDTO.husbandDTO.middleNameReg}
									${command.marriageDTO.husbandDTO.lastNameReg},</span> <spring:message
										code="mrm.cert.UIDNO" text="UID NO" />
									${marathiConvert.convertToRegional("marathi",command.marriageDTO.husbandDTO.uidNo)} ,
									 <spring:message code="mrm.cert.locate.add" text="LCOATE" />
									${command.marriageDTO.husbandDTO.fullAddrReg}, <spring:message
										code="mrm.cert.wife.name" text="Wife Name" /> 
								<span class="text-bold">${command.marriageDTO.wifeDTO.firstNameReg}
										${command.marriageDTO.wifeDTO.middleNameReg}
										${command.marriageDTO.wifeDTO.lastNameReg} </span>, <spring:message
										code="mrm.cert.UIDNO" text="UID NO" />
									${marathiConvert.convertToRegional("marathi",command.marriageDTO.wifeDTO.uidNo)}, <spring:message
										code="mrm.cert.locate.add" text="LCOATE" />
									${command.marriageDTO.wifeDTO.fullAddrReg},
									<spring:message
										code="mrm.cert.both.mar" text="Date" />
									 <spring:message
										code="mrm.cert.date" text="Date" />
								      <fmt:formatDate value="${command.marriageDTO.marDate}" var="marDate"  pattern="dd/MM/yyyy" />
										
										<span class="text-bold">
											 ${marathiConvert.convertToRegional("marathi",marDate)}
										</span>
									<spring:message code="mrm.cert.at" text="at" />
									${command.marriageDTO.placeMarR}
									<spring:message
										code="mrm.cert.mah" text="Maharashtra" />
										<span class="text-bold"><spring:message
										code="mrm.cert.maha" text="MAHA"/>
										${marathiConvert.convertToRegional("marathi",command.marriageDTO.volume)}</span>
										<span class="text-bold"><spring:message
											code="mrm.cert.serialNo" text="serial" /> 
											${marathiConvert.convertToRegional("marathi",command.marriageDTO.serialNo)}
										</span>
										<spring:message
											code="mrm.cert.var" text="var" />
											<!--D#130809  -->
											<c:choose>
											  <c:when test="${empty command.marriageDTO.regDate}">
											    <fmt:formatDate value="<%=new java.util.Date()%>"  var="applicationDate" pattern="dd/MM/yyyy" />
											  </c:when>
											  <c:otherwise>
											    <fmt:formatDate value="${command.marriageDTO.regDate}" var="applicationDate"  pattern="dd/MM/yyyy" />
											  </c:otherwise>
											</c:choose>
										<spring:message
											code="mrm.cert.date" text="App date" />
										<span class="text-bold">
											 ${marathiConvert.convertToRegional("marathi",applicationDate)}
										</span>
											
											
								
								<spring:message code="mrm.cert.endLine" text="EOL" />
							</p>
							<p class="padding-bottom-30 line-height-2">
								Certified that marriage between, Husband's name: <span
									class="text-bold">${command.marriageDTO.husbandDTO.firstNameEng}
									${command.marriageDTO.husbandDTO.middleNameEng}
									${command.marriageDTO.husbandDTO.lastNameEng},</span> UID
								No.${command.marriageDTO.husbandDTO.uidNo} , Residing at:
								${command.marriageDTO.husbandDTO.fullAddrEng}, and Wife's name:
								<span class="text-bold">${command.marriageDTO.wifeDTO.firstNameEng}
									${command.marriageDTO.wifeDTO.middleNameEng}
									${command.marriageDTO.wifeDTO.lastNameEng}</span>, UID No.
								${command.marriageDTO.wifeDTO.uidNo}, Residing at:
								${command.marriageDTO.wifeDTO.fullAddrEng} Solemnized on <span
									class="text-bold"><fmt:formatDate
										value="${command.marriageDTO.marDate}" pattern="dd/MM/yyyy" /></span>
								at: ${command.marriageDTO.placeMarE}, is Registered by me on <span class="text-bold">
									<!--D#130809  -->
									<c:choose>
									  <c:when test="${empty command.marriageDTO.regDate}">
									    <fmt:formatDate value="<%=new java.util.Date()%>"  pattern="dd/MM/yyyy" />
									  </c:when>
									  <c:otherwise>
									    <fmt:formatDate value="${command.marriageDTO.regDate}"  pattern="dd/MM/yyyy" />
									  </c:otherwise>
									</c:choose>
								</span>
								<span class="text-bold">at Serial No. ${ command.marriageDTO.serialNo}
									of Volume ${command.marriageDTO.volume}</span> of register of
								Marriages maintained under the Maharashtra Regulation of
								Marriage Bureaus and Registration of Marriage Act, 1998.
							</p>
							<c:set var="now" value="<%=new java.util.Date()%>" />
							<div class="padding-top-30">
								<div class="text-bold pull-left">
									<p class="margin-bottom-20">Place: Kalyan</p>
									<p>
										Date:
										<fmt:formatDate value="${now}" pattern="dd/MM/yyyy" />
									</p>
								</div>
								<div class="text-bold text-center pull-right">
									<p>Registrar of Marriages</p>
									<p>${command.marriageDTO.ward1Desc} ,</p>
									<p>${userSession.organisation.ONlsOrgname}</p>
									<p>${userSession.organisation.orgAddress}</p>
								</div>
							</div>
							<div class="clear"></div>
						</div>
						<!-- Certificate Body ends -->
					</div>
				</div>

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<c:if test="${empty command.marriageDTO.volume}">
					<div class="form-group">
						<apptags:input labelCode="mrm.marriage.volume"
							path="marriageDTO.volume" isMandatory="true" maxlegnth="45" isReadonly="true" placeholder="1"
							cssClass="form-control hasNumber"></apptags:input>
							
						<apptags:input labelCode="mrm.appointment.serialNo" maxlegnth="10"
							path="marriageDTO.appointmentDTO.pageNo" isMandatory="false"
							cssClass="form-control hasNumber" isReadonly="true"></apptags:input>

					</div>
				</c:if>
				

				<div class="text-center margin-top-20" id="actionBT">

					<c:if test="${empty command.marriageDTO.serialNo}">
						<button  type="button"
							onclick="editMarriageDetails(${command.marriageDTO.marId})"
							class="btn btn-primary hidden-print">
							<i class="icon-print-2"></i>
							<spring:message code="mrm.cert.edit.page" text="Edit Marriage Details"></spring:message>
						</button>
					</c:if>

					<c:if test="${empty command.marriageDTO.serialNo}">
						<button type="button" class="btn btn-success" id="save"
							onclick="generateSerialNo(this)">
							<spring:message code="mrm.button.submit" text="Submit"></spring:message>
						</button>
					</c:if>
			
					<c:if
						test="${not empty command.marriageDTO.serialNo && command.marriageDTO.status ne 'CONCLUDE' }">
						<c:choose>
						  <c:when test="${command.printBT eq 'N'}">
						  	<button type="button" class="btn btn-success hidden-print" id="finalBT"
								onclick="concludeMRMCertificate(this)">
								<spring:message code="mrm.cert.finalize" text="FINALIZE"></spring:message>
							</button>
						  </c:when>
						  <c:otherwise>
								  	
						  </c:otherwise>
						</c:choose>
					</c:if>
					
					<c:if test="${not empty command.printBT && command.printBT eq 'N' }">
						<select name="ddl1" id="ddl1" class="hidden-print">
							<option value="0">Select Certificate</option>
							</select>
							<spring:eval
								expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getValueFromPrefixLookUp('C3','CRD',${UserSession.organisation}).getOtherField()" var="otherField"/>
								<button type="button" class="btn btn-primary btn-sm hidden-print"
									 onclick="signCertificate('${command.marriageDTO.marId}','${command.marriageDTO.marId}',${command.marriageDTO.applicationId},'MarriageCertificateGeneration.html','${otherField}')">
									 <spring:message code="property.sign.certificate" text="Sign Certificate" />
								</button>
					</c:if>
					
					
					
					<button type="button" class="btn btn-primary hidden-print"
						onclick="printContent('receipt')">
						<i class="icon-print-2"></i>
						<spring:message code="mrm.acknowledgement.print" text="Print"></spring:message>
					</button>


					<button type="button" class="btn btn-danger hidden-print"
						onclick="window.location.href='AdminHome.html'">

						<spring:message code="mrm.button.back" text="Back"></spring:message>
					</button>
					
					
					
					
				
				</div>
				
				

				</div>
			</form:form>
			<style>
				@media print {
				@page {
					size: A4 portrait;
				}
			}
			</style>
		</div>
	</div>
</div>
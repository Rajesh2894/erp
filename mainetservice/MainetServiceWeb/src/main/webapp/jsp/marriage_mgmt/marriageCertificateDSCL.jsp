<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="hindiConvert"
	class="com.abm.mainet.common.utility.Utility"></jsp:useBean>

<script type="text/javascript" src="js/mainet/validation.js"></script>

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
		var volume = $("#volume").val();
	
		if (volume == undefined || volume == '') {
			errorList.push(getLocalMessage('mrm.cert.vldnn.authoriserName'));
		}
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
.marCert.padding {
	padding: 30px;
	padding-top: 15px;
}

.marCert.padding-sm {
	padding: 15px;
}

.marCert.padding-xs {
	padding: 2%;
}

.marCert.padding-lt {
	padding: 0 12px;
}
</style>

<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content animated slideInDown">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="Marriage Certificate" />
			</h2>
		</div>
		<div class="marCert padding" id="receipt">

			<form:form action="MarriageRegistration.html" name="certificate"
				id="certificate" class="form-horizontal">

				<div class="container "
					style="border: 10px solid #333333; padding: 25px;">
					<c:choose>
						<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
							<!-- English format -->
							<div class="row">
								<div class="col-md-3 col-sm-3 col-xs-3">
									<img width="80" src="${userSession.orgLogoPath}">
								</div>
								<div class="col-md-6 col-sm-6 col-xs-6">
									<div class="col-12 text-center">
										<h2>
											<i> SPECIAL MARRIAGE ACT. 1954</i>
										</h2>
										<h5>THE Fourth Schedule</h5>
										<h5>(SECTION 13)</h5>
										<h2>
											<i>CERTIFICATE OF MARRIAGE</i>
										</h2>
									</div>
								</div>

								<div class="col-md-3 col-sm-3 col-xs-3">
									<p>No. ${command.marriageDTO.serialNo }/
										<fmt:formatDate value="${command.marriageDTO.marDate}"
											pattern="dd" />
										<fmt:formatDate value="${command.marriageDTO.marDate}"
											pattern="MMM" />
										<fmt:formatDate value="${command.marriageDTO.marDate}"
											pattern="yyyy" />
									</p>
								</div>

							</div>


							<div class="row">
								<div style="text-align: justify; padding: 15px;">
									<h5 style="line-height: 30px; font-size: 18px;">
										&nbsp; &nbsp; &nbsp; I, ${empty command.marriageDTO.volume  ? '<u>&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;</u>' : command.marriageDTO.volume}
										Marriage Office SM (SADAR) Dehradun, hereby certify that on
										<fmt:formatDate value="${command.marriageDTO.marDate}"
											pattern="dd" />
										day of
										<fmt:formatDate value="${command.marriageDTO.marDate}"
											pattern="MMM" />
										<fmt:formatDate value="${command.marriageDTO.marDate}"
											pattern="yyyy" />
										${command.marriageDTO.husbandDTO.firstNameEng}
										${command.marriageDTO.husbandDTO.middleNameEng}
										${command.marriageDTO.husbandDTO.lastNameEng} and
										${command.marriageDTO.wifeDTO.firstNameEng}
										${command.marriageDTO.wifeDTO.middleNameEng}
										${command.marriageDTO.wifeDTO.lastNameEng} appeared before me
										and that each of them, in my presence & in the presence of
										three witness who have signed here under, made the
										declarations required by section 11 & that marriage under this
										act was solemnized between them in my presence.
									</h5>
								</div>
							</div>

							<div class="row">
								<div class="col-md-8 col-sm-8 col-xs-8" style="margin-top: 50px">
									<p>(Signature of Three witnesses)</p>
									<ol style="padding-left: 15px;">
										<c:forEach items="${command.marriageDTO.witnessDetailsDTO}"
											var="witness" varStatus="lk">
											<li style="margin-left: 0px">${witness.firstNameEng}
												${witness.lastNameEng}</li>

										</c:forEach>
									</ol>
								</div>
								<div class="col-md-4 col-sm-4 col-xs-4"
									style="margin-top: 20px; margin-bottom: 20px;">
									<P>S.D.M SADAR, D.Dun</P>
									<P>& Marriage Officer</P>
									<P>District Dehra Dun</P>
									<!-- <P><u>&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;</u></P> -->
									<p style="margin-top: 25px">(Signature of Bridegroom)</p>
									<p style="margin-top: 25px">(Signature of Bride)</p>

								</div>
							</div>

							<div class="row">
								<div class="col-md-8 col-sm-8 col-xs-8" style="margin-top: 10px">
									<p>
										Dated the
										<fmt:formatDate value="${command.marriageDTO.marDate}"
											pattern="dd" />
										day of
										<fmt:formatDate value="${command.marriageDTO.marDate}"
											pattern="MMM" />
										<fmt:formatDate value="${command.marriageDTO.marDate}"
											pattern="yyyy" />
									</p>
								</div>
							</div>

						</c:when>
						<c:otherwise>
							<!-- Hind format -->
							<div class="row" style="margin-top: 10px">
								<div class="col-md-3 col-sm-3 col-xs-3">
									<img width="80" src="${userSession.orgLogoPath}">
								</div>
								<div class="col-md-6 col-sm-6 col-xs-6">
									<div class="col-12 text-center">
										<h2>
											<spring:message code="mrm.cert.act" text="Special Mar Act" />
										</h2>
										<h5>
											<spring:message code="mrm.cert.fourtSection"
												text="Fourth section" />
										</h5>
										<h2>
											<spring:message code="mrm.cert.patr" text="Certificate " />
										</h2>
									</div>
								</div>

								<div class="col-md-3 col-sm-3 col-xs-3">
									<p><spring:message code="mrm.cert.number" text="Number " /> 
										${command.marriageDTO.serialNo }/
										<fmt:formatDate value="${command.marriageDTO.marDate}"
											pattern="dd" />
										<fmt:formatDate value="${command.marriageDTO.marDate}"
											pattern="MMM" />
										<fmt:formatDate value="${command.marriageDTO.marDate}"
											pattern="yyyy" />
									</p>
								</div>


							</div>



							<div class="row">
								<div style="text-align: justify; padding: 15px;">
									<h5 style="line-height: 30px; font-size: 18px;">
										&nbsp; &nbsp; &nbsp;
										<spring:message code="mrm.cert.mainBody.letI"
											text="Body start" />

										${empty command.marriageDTO.volume  ? '<u>&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;</u>' : command.marriageDTO.volume}

										<spring:message code="mrm.cert.mainBody.start"
											text="Body start" />
										<fmt:formatDate value="${command.marriageDTO.marDate}"
											pattern="dd-MM-yyyy" />


										<fmt:formatDate value="${command.marriageDTO.marDate}"
											pattern="dd" />
										<fmt:formatDate value="${command.marriageDTO.marDate}"
											pattern="MMM" />
										<fmt:formatDate value="${command.marriageDTO.marDate}"
											pattern="yyyy" />


										<spring:message code="mrm.cert.keDin" text="ke din" />
										${command.marriageDTO.husbandDTO.firstNameReg}
										${command.marriageDTO.husbandDTO.middleNameReg}
										${command.marriageDTO.husbandDTO.lastNameReg}
										<spring:message code="mrm.cert.aur" text="Aur" />
										${command.marriageDTO.wifeDTO.firstNameReg}
										${command.marriageDTO.wifeDTO.middleNameReg}
										${command.marriageDTO.wifeDTO.lastNameReg}
										<spring:message code="mrm.cert.mainBody.end" text="Body end" />
									</h5>
								</div>
							</div>

							<div class="row">
								<div class="col-md-8 col-sm-8 col-xs-8" style="margin-top: 50px">
									<p>
										<spring:message code="mrm.cert.threeSignature"
											text="Three Signature" />
									</p>
									<ol style="padding-left: 15px;">
										<c:forEach items="${command.marriageDTO.witnessDetailsDTO}"
											var="witness" varStatus="lk">
											<li style="margin-left: 0px">${witness.firstNameReg}
												${witness.lastNameReg}</li>

										</c:forEach>
									</ol>
								</div>
								<div class="col-md-4 col-sm-4 col-xs-4"
									style="margin-top: 20px; margin-bottom: 20px;">
									<P>
										<spring:message code="mrm.cert.sdm" text="SDM sadar" />
									</P>
									<P>
										<spring:message code="mrm.cert.officer" text="officer" />
									</P>
									<P>
										<spring:message code="mrm.cert.distDoon" text="officer" />
									</P>
									<!-- <P><u>&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;</u></P> -->
									<p style="margin-top: 25px">
										<spring:message code="mrm.cert.groomSign" text="groom" />
									</p>
									<p style="margin-top: 25px">
										<spring:message code="mrm.cert.brideSign" text="bride" />
									</p>

								</div>
							</div>



							<div class="row">
								<div class="col-md-8 col-sm-8 col-xs-8" style="margin-top: 10px">
									<p>
										<fmt:formatDate value="${command.marriageDTO.marDate}"
											pattern="MMM" />
										<fmt:formatDate value="${command.marriageDTO.marDate}"
											pattern="yyyy" />
										<fmt:formatDate value="${command.marriageDTO.marDate}"
											pattern="dd" />
										<spring:message code="mrm.cert.dated" text="dated" />
									</p>
								</div>
							</div>


						</c:otherwise>
					</c:choose>
				</div>




				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<c:if test="${empty command.marriageDTO.volume}">
					<div class="form-group">
						<apptags:input labelCode="mrm.cert.authoriserName"
							path="marriageDTO.volume" isMandatory="true" maxlegnth="49"
							cssClass="form-control"></apptags:input>

					</div>
				</c:if>


				<div class="text-center margin-top-20" id="actionBT">

					<c:if test="${empty command.marriageDTO.serialNo}">
						<button type="button"
							onclick="editMarriageDetails(${command.marriageDTO.marId})"
							class="btn btn-primary hidden-print">
							<i class="icon-print-2"></i>
							<spring:message code="mrm.cert.edit.page"
								text="Edit Marriage Details"></spring:message>
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

						<c:if test="${empty command.printBT}">
							<button type="button" class="btn btn-success" id="finalBT"
								onclick="concludeMRMCertificate(this)">
								<spring:message code="mrm.cert.finalize" text="FINALIZE"></spring:message>
							</button>

						</c:if>



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
			</form:form>
		</div>
	</div>
</div>
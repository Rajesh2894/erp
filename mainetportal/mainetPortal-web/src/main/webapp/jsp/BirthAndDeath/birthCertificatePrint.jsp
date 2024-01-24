<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/birthAndDeath/printCertificate.js"></script>
<jsp:useBean id="marathiConvert" class="com.abm.mainet.common.util.Utility"></jsp:useBean>
<style>
	.width-auto { width: auto !important; }
	.bndSign .width-25{width: 5.3rem; !important; }
	.height-logo { height: 10rem; }
	.form-horizontal .form-group label {
		font-size: 0.9rem;
	}
	.form-horizontal .form-group div p.bnd-p-tag {
		padding: 6px 12px 0px;
		font-size: 1em;
	}
	.form-horizontal .form-group.eng-text div p.bnd-p-tag {
		padding: 0px 12px;
	}
	
	.form-horizontal .form-group.mar-text {
		margin-bottom: 0px !important;
	}
	.form-horizontal .form-group.eng-text {
		margin-bottom: 20px !important;
	}
	.form-horizontal .form-group.eng-text label {
		padding-top: 0px;
	}
	.widget .widget-content {
		padding: 2em 4em;
	}
	.first-section p.bnd-p-tag {
		text-indent: 6em;
	}
	.bnd-seal {
		border: 1px solid #000000;
		padding: 10px;
		display:inline-block;
		height: 150px;
		width: 150px;
	}
	.cert-outer-div {
		border: 4px solid #000000;
		padding: 5px;
	}
	.cert-outer-div-2 {
		border: 2px solid #000000;
		padding: 2em;
	}
	.border-bottom-black { border-bottom: 1px solid #000000; }
	
	.bottom-section {
		background-color: #000000;
		padding: 10px;
	}
	.bottom-section div {
		color: #ffffff;
	}
	
	@media only screen and (max-width: 1024px) {
		.widget .widget-content {
			padding: 2em;
		}
		
		.cert-outer-div-2 {
			padding: 1em;
		}
		
		.cert-outer-div-2 .border-bottom-black .bnd-logo-div {
			padding: 0px;
		}
	}
</style>

<script>
	jQuery(document).bind("keyup keydown", function(e){
	    if(e.ctrlKey && e.keyCode == 80){
	        alert('PLEASE USE THE PRINT BUTTON TO PRINT THIS DOCUMENT');
	        return false;
	    }
	});
</script>

<html>
	
<!-- Start Content here -->
<div class="content animated slideInDown" id="printDiv">
	<!-- Start info box -->
	<div class="widget">
		<!-- <div class="widget-header hidden-print">
			<h2>Birth Certificate</h2>
			<div class="additional-btn"><a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a> 
			</div>
		</div> -->
		<div class="widget-content padding widget-content-print bndSign">
			<form action="PrintCertificate.html" method="get" id="print-div" class="form-horizontal margin-top-08">
				<div class="cert-outer-div">
					<div class="cert-outer-div-2">
						<div class="text-bold margin-bottom-10 border-bottom-black">
							<span class="pull-left margin-top-08 margin-bottom-10"> 
							
									<spring:message	code="bnd.certificate.no"	text="Certificate No." /> ${command.birthRegDto.brCertNo}
								<%-- <c:choose>
									<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
									${command.birthRegDto.brCertNo}
									</c:when>
									<c:otherwise>
										${marathiConvert.convertToRegional("marathi",command.birthRegDto.brCertNo)} ,
									</c:otherwise>
								</c:choose> --%>

							</span>
							<span class="pull-right margin-top-08 margin-bottom-10">
								 <spring:message	code="bnd.numuna.no5"	text="Form -5" />
							</span>
							<div class="clear"></div>
						</div>
						<div class="border-bottom-black">
							<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
								<img alt="national_emblem_logo" src="assets/img/national_emblem_logo.png" class="img-responsive width-auto height-logo">
							</div>
							<div class="text-center col-xs-8 col-sm-8 col-md-8 col-lg-8">
								<h3 class="text-bold margin-top-0"><spring:message code="bnd.maha.gov"	text="GOVERNMENT OF MAHARASHTRA" /></h3>
								<h3 class="text-bold">GOVERNMENT OF MAHARASHTRA</h3>
								<h5 class="text-bold"><spring:message	code="bnd.health.dept"	text="HEALTH DEPARTMENT" /></h5>
								<h5 class="text-bold">HEALTH DEPARTMENT</h5>
								<h5 class="text-bold"><spring:message code="bnd.kdmc" text="Kalyan Dombivli Municipal Corporation" /></h5>
								<h5 class="text-bold">Kalyan Dombivli Municipal Corporation</h5>
							</div>
							<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2 bnd-logo-div">
								<img alt="TMC_B&D_logo" src="assets/img/TMC_B&D_logo.png" class="img-responsive width-auto height-logo pull-right">
							</div>
							<div class="clear"></div>
						</div>
						<p class="text-bold text-center bnd-p-tag"><spring:message	code="bnd.birth.certificate" text="" /></p>
						<p class="text-bold text-center bnd-p-tag">BIRTH CERTIFICATE</p>
						<div class="text-bold first-section">
							<p class="margin-bottom-10 bnd-p-tag"><spring:message	code="bnd.note1"	text="" /></p></p>
							<p class="margin-bottom-10 bnd-p-tag">(Issued under section 12/17 of the Registration of Births & Deaths Act, 1969 and Rule 8/13 of the Maharashtra Registration of Births and Deaths Rules, 2000.)</p>
							<p class="margin-bottom-10 bnd-p-tag"><spring:message	code="bnd.birth.note4"	text="" /></p>
							<p class="margin-bottom-10 bnd-p-tag">This is to certify that the following information has been taken from the original record of birth which is  the register for Kalyan Dombivli Municipal Corporation, of Area Shivaji Chowk of Tahsil/Block Kalyan of  District Thane of Maharashtra State.</p>
						</div>
						<div class="form-group mar-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.childname"	text="Name of Child" /></p></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.birthRegDto.brChildNameMar}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.gen" text="Gender" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.birthRegDto.brSexMar}</p>
							</div>
						</div>
						<div class="form-group eng-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Name of Child</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.birthRegDto.brChildName}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Sex</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.birthRegDto.brSex}</p>
							</div>
						</div>
						
						<div class="form-group mar-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.cert.dateofbirth" text="Date of Birth" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
								<fmt:formatDate pattern="dd-MM-yyyy" value="${command.birthRegDto.brDob}" var="marDate"/>
								<p class="bnd-p-tag">${marathiConvert.convertToRegional("marathi",marDate)}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.cert.birthPlace" text="Place of Birth" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.birthRegDto.brBirthPlaceMar}</p>
							</div>
						</div>
						<div class="form-group eng-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Date of Birth</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag"><fmt:formatDate pattern="dd-MM-yyyy" value="${command.birthRegDto.brDob}"/></p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Place of Birth</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.birthRegDto.brBirthPlace}</p>
							</div>
						</div>
						<div class="form-group mar-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.mother.fullname" text="Full Name of Mother" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.birthRegDto.parentDetailDTO.pdMothernameMar}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.father.fullname" text="Full Name of Father/Husband" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.birthRegDto.parentDetailDTO.pdFathernameMar}</p>
							</div>
						</div>
						<div class="form-group eng-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Full Name of Mother</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.birthRegDto.parentDetailDTO.pdMothername}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Full Name of Father</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.birthRegDto.parentDetailDTO.pdFathername}</p>
							</div>
						</div>
						<div class="form-group mar-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.father.fullname" text="Address of parents at the time of birth of the child" /> </label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.birthRegDto.parentDetailDTO.pdParaddressMar}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.parents.permenent.address" text="Permanent address of parents" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.birthRegDto.parentDetailDTO.pdAddressMar}</p>
							</div>
						</div>
						<div class="form-group eng-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Address of parents at the time of birth of the child</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.birthRegDto.parentDetailDTO.pdParaddress}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Permanent address of parents</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.birthRegDto.parentDetailDTO.pdAddress}</p>
							</div>
						</div>
						<div class="form-group mar-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.reg.no" text="Registration No" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
								<p class="bnd-p-tag">${marathiConvert.convertToRegional("marathi",command.birthRegDto.brRegNo)}</p>
								<%-- <p class="bnd-p-tag">${command.birthRegDto.brRegNo}</p> --%>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.reg.date" text="Date of Registration" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
								<fmt:formatDate pattern="dd-MM-yyyy" value="${command.birthRegDto.brRegDate}" var="marRegDate"/>
								<p class="bnd-p-tag">${marathiConvert.convertToRegional("marathi",marRegDate)}</p>
								<%-- <p class="bnd-p-tag"><fmt:formatDate pattern="dd-MM-yyyy" value="${command.birthRegDto.brRegDate}"/></p> --%>
							</div>
						</div>
						<div class="form-group eng-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Registration No</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.birthRegDto.brRegNo}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Date of Registration</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag"><fmt:formatDate pattern="dd-MM-yyyy" value="${command.birthRegDto.brRegDate}"/></p>
							</div>
						</div>
						<div class="form-group mar-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.cer.remark" text="Remarks (if any)" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.remarkReg}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.SignatureOfTheIssuingAuthority" text="Signature of the issuing authority" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag"></p>
							</div>
						</div>
						<div class="form-group eng-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Remarks (If Any)</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.remark}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Signature of the issuing authority</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
							 <c:if test="${command.birthRegDto.cpdRegUnit eq 'KYN'}">
                            <img alt="KALYAN" src="assets/img/KALYAN.png" class="img-responsive width-25" /></c:if>
                            <c:if test="${command.birthRegDto.cpdRegUnit eq 'DOM'}">
                            <img alt="DOMBIVLI" src="assets/img/DOMBIVLI.png" class="img-responsive width-25" /></c:if>
								<p class="bnd-p-tag"></p>
							</div>
						</div>
						<div class="form-group mar-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.certificate.provided.date" text="Date of issue of Certificate" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
								<fmt:formatDate pattern="dd-MM-yyyy" value="<%=new java.util.Date()%>" var="todayDate"/>
								<p class="bnd-p-tag">${marathiConvert.convertToRegional("marathi",todayDate)}</p>
								<%-- <p class="bnd-p-tag"><fmt:formatDate pattern="dd-MM-yyyy" value="${today}"/></p> --%>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.AddressOftheIssuingAuthority" text="Address of the issuing authority" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag"><spring:message code="bn.head.office" text="Head Office" /></p>
							</div>
						</div>
						<c:set var="today" value="<%=new java.util.Date()%>" />
						<div class="form-group eng-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Date of issue of Certificate</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag"><fmt:formatDate pattern="dd-MM-yyyy" value="${today}"/></p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Address of the issuing authority</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">HEAD OFFICE</p>
							</div>
						</div>
						<div class="text-center margin-top-10 margin-bottom-10">
							<span class="bnd-seal">
								<spring:message code="bnd.stam" text="Seal" />	
							</span>
						</div>

						<div class="text-bold bottom-section">
							<div class="pull-left">
								<spring:message code="bn.cert.note3" text="Ensure Registration of every birth & death" /> 
							</div>
							<div class="pull-right">
								"Ensure Registration of every birth & death"
							</div>
							<div class="clear"></div>
						</div>
					</div>
				</div>
				<input type="hidden" id="certificateNo" value="${command.birthRegDto.brCertNo}">
			    <input type="hidden" id="brOrdrID" value="${brOrDrID}">
			    <input type="hidden" id="status" value="${status}">
			    <input type="hidden" id="bdId" value="${bdId}">
			    <input type="hidden" id="serviceCode" value="${serviceCode}">
				<div class="clear text-center hidden-print margin-top-15">
					<button type="button" class="btn btn-primary" class="btn btn-primary hidden-print" onclick="printCertificate('printDiv');" data-toggle="tooltip" data-original-title="Print" ><i class="fa fa-print padding-right-5"></i>
					<spring:message code="print.cert" text="Print" /></button>

				    <apptags:backButton url="PrintCertificate.html"></apptags:backButton>
				</div>
			</form>
		</div> <!-- widget-content ends -->
	</div>
</div>
<!-- End content here -->

</html>
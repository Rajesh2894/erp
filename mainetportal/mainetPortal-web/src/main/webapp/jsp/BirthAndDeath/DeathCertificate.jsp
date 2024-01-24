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
			<h2>Death Certificate</h2>
			<div class="additional-btn"><a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a> 
			</div>
		</div> -->
		<div class="widget-content padding widget-content-print bndSign">
			<div class="mand-label clearfix"><span>Field with <i class="text-red-1">*</i> is mandatory </span> 
			</div>
			<form action="PrintCertificate.html" method="get" id="print-div" class="form-horizontal margin-top-08">
				<div class="cert-outer-div">
					<div class="cert-outer-div-2">
						<div class="text-bold margin-bottom-10 border-bottom-black">
							<span class="pull-left margin-top-08 margin-bottom-10">
							
								
						<spring:message	code="bnd.certificate.no"	text="Certificate No." />  ${command.tbDeathregDTO.drCertNo}
									<%-- <c:choose>
									<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
									${command.tbDeathregDTO.drCertNo}
									</c:when>
									<c:otherwise>
										${marathiConvert.convertToRegional("marathi",command.tbDeathregDTO.drCertNo)} ,
									</c:otherwise>
								</c:choose> --%>
								<%-- प्रमाणपत्र क्रमांक / Certificate No.  ${marathiConvert.convertToRegional("marathi",command.tbDeathregDTO.drCertNo)} , --%>
								<%-- ${command.tbDeathregDTO.drCertNo} --%>
							</span>
							<span class="pull-right margin-top-08 margin-bottom-10">
								<spring:message	code="bnd.numuna.no6"	text="Form -6" /> 
							</span>
							<div class="clear"></div>
						</div>
						<div class="border-bottom-black">
							<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
								<img alt="national_emblem_logo" src="assets/img/national_emblem_logo.png" class="img-responsive width-auto height-logo">
							</div>
							<div class="text-center col-xs-8 col-sm-8 col-md-8 col-lg-8">
								<h3 class=" margin-top-0"><spring:message code="bnd.maha.gov"	text="GOVERNMENT OF MAHARASHTRA" /></h3>
								<h3 class="text-bold">GOVERNMENT OF MAHARASHTRA</h3>
								<h5 class="text-bold"><spring:message	code="bnd.health.dept"	text="HEALTH DEPARTMENT" /></h5>
								<h5 class="text-bold">HEALTH DEPARTMENT</h5>
								<h5 class="text-bold"><spring:message code="bnd.kdmc"	text="Kalyan Dombivli Municipal Corporation" /></h5>
								<h5 class="text-bold">Kalyan Dombivli Municipal Corporation</h5>
							</div>
							<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2 bnd-logo-div">
								<img alt="TMC_B&D_logo" src="assets/img/TMC_B&D_logo.png" class="img-responsive width-auto height-logo pull-right">
							</div>
							<div class="clear"></div>
						</div>
						<p class="text-bold text-center bnd-p-tag"><spring:message	code="bnd.deathcertificate"	text="Kalyan Dombivli Municipal Corporation" /></p>
						<p class="text-bold text-center bnd-p-tag">DEATH CERTIFICATE</p>
						<div class="text-bold first-section">
							<p class="margin-bottom-10 bnd-p-tag"><spring:message	code="bnd.note1"	text="" /></p>
							<p class="margin-bottom-10 bnd-p-tag">(Issued under section 12/17 of the Registration of Births & Deaths Act, 1969 and Rule 8/13 of the Maharashtra Registration of Births and Deaths Rules, 2000.)</p>
							<p class="margin-bottom-10 bnd-p-tag"><spring:message	code="bnd.note2"	text="" /></p>
							<p class="margin-bottom-10 bnd-p-tag">This is to certify that the following information has been taken from the original record of death which is the register for Kalyan Dombivli Municipal Corporation, of Area Shivaji Chowk of Tahsil/Block Kalyan of  District Thane of Maharashtra State.</p>
						</div>
						<div class="form-group mar-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.deceased.name" text="Deceased Full Name" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drMarDeceasedname}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.gen" text="Gender" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
								<p class="bnd-p-tag">${command.tbDeathregDTO.drSexMar}</p>
							</div>
						</div>
						<div class="form-group eng-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Deceased Full Name</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drDeceasedname}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Sex</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drSex}</p>
							</div>
						</div>
						<div class="form-group mar-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.death.date" text="Date of Death" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
							    <fmt:formatDate pattern="dd-MM-yyyy" value="${command.tbDeathregDTO.drDod}" var="deathDate"/>
								<p class="bnd-p-tag">${marathiConvert.convertToRegional("marathi",deathDate)}</p>
								<%-- <p class="bnd-p-tag"><fmt:formatDate pattern="dd-MM-yyyy" value="${command.tbDeathregDTO.drDod}"/></p> --%>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.death.place" text="Place of Death" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drMarDeathplace}</p>
							</div>
						</div>
						<div class="form-group eng-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Date of Death</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag"><fmt:formatDate pattern="dd-MM-yyyy" value="${command.tbDeathregDTO.drDod}"/></p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Place of Death</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drDeathplace}</p>
							</div>
						</div>
						<div class="form-group mar-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.mother.fullname" text="Full Name of Mother" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drMarMotherName}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.father.fullname" text="Full Name of Father/Husband" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drMarRelativeName}</p>
							</div>
						</div>
						<div class="form-group eng-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Full Name of Mother</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drMotherName}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Full Name of Father/Husband</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drRelativeName}</p>
							</div>
						</div>
						<div class="form-group mar-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.add.atTimeOfDeath" text="Address of the deceased at the time of death" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drDcaddrAtdeathMar}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.PermanentAddressOfDeceased" text="Permanent address of deceased" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drMarDeceasedaddr}</p>
							</div>
						</div>
						<div class="form-group eng-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Address of the deceased at the time of death</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drDcaddrAtdeath}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Permanent address of deceased</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drDeceasedaddr}</p>
							</div>
						</div>
						<div class="form-group mar-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.reg.no" text="Registration No" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
								<p class="bnd-p-tag">${marathiConvert.convertToRegional("marathi",command.tbDeathregDTO.drRegno)} ,</p>
								<%-- <p class="bnd-p-tag">${command.tbDeathregDTO.drRegno}</p> --%>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.reg.date" text="Date of Registration" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
								<fmt:formatDate pattern="dd-MM-yyyy" value="${command.tbDeathregDTO.drRegdate}" var="marRegDate"/>
								<p class="bnd-p-tag">${marathiConvert.convertToRegional("marathi",marRegDate)}</p>
								<%-- <p class="bnd-p-tag"><fmt:formatDate pattern="dd-MM-yyyy" value="${command.tbDeathregDTO.drRegdate}"/></p> --%>
							</div>
						</div>
						<div class="form-group eng-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Registration No</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drRegno}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Date of Registration</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag"><fmt:formatDate pattern="dd-MM-yyyy" value="${command.tbDeathregDTO.drRegdate}"/></p>
							</div>
						</div>
						<div class="form-group mar-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.cer.remark" text="Remarks (if any)" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p>${command.remarkReg}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.SignatureOfTheIssuingAuthority" text="Signature of the issuing authority" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
								<p class="bnd-p-tag"></p>
							</div>
						</div>
						<div class="form-group eng-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Remarks (if any)</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.remark}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Signature of the issuing authority</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
                            <c:if test="${command.tbDeathregDTO.pdRegUnitCode eq 'KYN'}">
                            <img alt="KALYAN" src="assets/img/KALYAN.png" class="img-responsive width-25" /></c:if>
                            <c:if test="${command.tbDeathregDTO.pdRegUnitCode eq 'DOM'}">
                            <img alt="DOMBIVLI" src="assets/img/DOMBIVLI.png" class="img-responsive width-25" /></c:if>
								<p class="bnd-p-tag"></p>
							</div>
						</div>
						<c:set var="today" value="<%=new java.util.Date()%>" />
						<div class="form-group mar-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.certificate.provided.date" text="Date of issue of Certificate" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
								<fmt:formatDate pattern="dd-MM-yyyy" value="<%=new java.util.Date()%>" var="todayDate"/></p>
								<p class="bnd-p-tag">${marathiConvert.convertToRegional("marathi",todayDate)}</p>
								<%-- <p class="bnd-p-tag"><fmt:formatDate pattern="dd-MM-yyyy" value="${today}"/></p> --%>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label "><spring:message code="bnd.AddressOftheIssuingAuthority" text="Address of the issuing authority" /></label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag"><spring:message code="bn.head.office" text="Head Office" /></p>
							</div>
						</div>
						<div class="form-group eng-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Date of issue of Certificate</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
								<p class="bnd-p-tag"><fmt:formatDate pattern="dd-MM-yyyy" value="${today}"/></p>
								<%-- <p><fmt:formatDate pattern="dd-MM-yyyy" value="${today}"/></p> --%>
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
						<%-- <div class="form-group margin-top-10">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Receipt Number</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
								<c:choose>
									<c:when test="${command.tbDeathregDTO.receiptNo==0}">
										<p class="bnd-p-tag">NA</p>
									</c:when>
									<c:otherwise>
										<p class="bnd-p-tag">${command.tbDeathregDTO.receiptNo}</p>
									</c:otherwise>
								</c:choose>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Receipt Date</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

							<c:choose>
									<c:when test="${empty command.tbDeathregDTO.receiptDate}">
										<p class="bnd-p-tag">NA</p>
									</c:when>
									<c:otherwise>
										<p class="bnd-p-tag"><fmt:formatDate pattern="dd-MM-yyyy" value="${command.tbDeathregDTO.receiptDate}"/></p>
									</c:otherwise>
								</c:choose>
							</div>
						</div> --%>
						<!-- <hr/> -->
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
				<input type="hidden" id="certificateNo" value="${command.tbDeathregDTO.drCertNo}">
			    <input type="hidden" id="brOrdrID" value="${brOrDrID}">
			    <input type="hidden" id="status" value="${status}">
			    <input type="hidden" id="bdId" value="${bdId}">
			    <input type="hidden" id="serviceCode" value="${serviceCode}">
				<div class="clear text-center hidden-print margin-top-15">
					<button type="button" class="btn btn-primary" onclick="printCertificate('printDiv');" data-toggle="tooltip" data-original-title="Print" ><i class="fa fa-print padding-right-5"></i>
					<spring:message code="print.cert" text="Print" /></button>
					<apptags:backButton url="PrintCertificate.html"></apptags:backButton>
				</div>
			</form>
		</div> <!-- widget-content ends -->
	</div>
</div>
<!-- End content here -->

</html>
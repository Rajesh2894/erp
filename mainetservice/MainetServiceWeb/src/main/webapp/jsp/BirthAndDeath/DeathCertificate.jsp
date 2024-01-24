<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href="assets/css/print.css" media="print" rel="stylesheet" type="text/css"/>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/birthAndDeath/printCertificate.js"></script>
<jsp:useBean id="marathiConvert" class="com.abm.mainet.common.utility.Utility"></jsp:useBean>
<style>
	.width-auto { width: auto !important; }
	.height-logo { height: 10rem; }
	.form-horizontal .form-group label {
		font-size: 0.9em;
	}
	.form-horizontal .form-group div p.bnd-p-tag {
		padding: 6px 12px 0px;
		font-size: 0.9rem;
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
		<div class="widget-header hidden-print">
			<h2>Death Certificate</h2>
			<div class="additional-btn"><a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a> 
			</div>
		</div>
		<div class="widget-content padding widget-content-print">
			<div class="mand-label clearfix"><span>Field with <i class="text-red-1">*</i> is mandatory </span> 
			</div>
			<form action="PrintCertificate.html" method="get" id="print-div" class="form-horizontal margin-top-10">
				<div class="cert-outer-div">
					<div class="cert-outer-div-2">
						<div class="text-bold margin-bottom-10 border-bottom-black">
							<span class="pull-left margin-top-10 margin-bottom-10">
								प्रमाणपत्र क्रमांक / Certificate No : ${command.tbDeathregDTO.drCertNo}
							</span>
							<span class="pull-right margin-top-10 margin-bottom-10">
								फॉर्म -६ / Form -6
							</span>
							<div class="clear"></div>
						</div>
						<div class="border-bottom-black">
							<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
								<img src="assets/img/national_emblem_logo.png" class="img-responsive width-auto height-logo">
							</div>
							<div class="text-center col-xs-8 col-sm-8 col-md-8 col-lg-8">
								<h3 class=" margin-top-0">महाराष्ट्र शासन</h3>
								<h3 class="text-bold">GOVERNMENT OF MAHARASHTRA</h3>
								<h5 class="text-bold">आरोग्य विभाग</h5>
								<h5 class="text-bold">HEALTH DEPARTMENT</h5>
								<h5 class="text-bold">ठाणे शहर महानगरपालिका</h5>
								<h5 class="text-bold">THE MUNICIPAL CORPORATION OF THE CITY OF THANE</h5>
							</div>
							<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2 bnd-logo-div">
								<img src="assets/img/TMC_B&D_logo.png" class="img-responsive width-auto height-logo pull-right">
							</div>
							<div class="clear"></div>
						</div>
						<p class="text-bold text-center bnd-p-tag">मृत्यु प्रमाणपत्र</p>
						<p class="text-bold text-center bnd-p-tag">DEATH CERTIFICATE</p>
						<div class="text-bold first-section">
							<p class="margin-bottom-10 bnd-p-tag">(जन्म व मृत्यू नोंदणी अधिनियम, १९६९ च्या कलम १२/१७, आणि महाराष्ट्र जन्म आणि मृत्यू नोंदणी अधिनियम, २००० चे  नियम ८/१३ अन्वये देण्यात आले आहे.)</p>
							<p class="margin-bottom-10 bnd-p-tag">(Issued under section 12/17 of the Registration of Births & Deaths Act, 1969 and Rule 8/13 of the Maharashtra Registration of Births and Deaths Rules, 2000.)</p>
							<p class="margin-bottom-10 bnd-p-tag">(प्रमाणित करण्यात येत आहे कि, खालील माहिती मृत्यूच्या अभिलेखाच्या नोंदवहीतून घेण्यात आली आहे, जी कि ठाणे महानगरपालिका, ठाणे ${command.tbDeathregDTO.wardDescReg} जिल्हा ठाणे, महाराष्ट्र राज्याच्या नोंदवहीत उल्लेख आहे.)</p>
							<p class="margin-bottom-10 bnd-p-tag">This is to certify that the following information has been taken from the original record of death which is the register for Thane Municipal Corporation, of Ward ${command.tbDeathregDTO.wardDesc} of District Thane of Maharashtra State.</p>
						</div>
						<div class="form-group mar-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">मृताचे पूर्ण नाव</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drMarDeceasedname}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">लिंग</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
								<p class="bnd-p-tag">${command.tbDeathregDTO.drSexMar}</p>
							</div>
						</div>
						<div class="form-group eng-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Full Name of<br> Deceased</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drDeceasedname}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Sex</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drSex}</p>
							</div>
						</div>
						<div class="form-group mar-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">मृत्यू दिनांक</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
                                <fmt:formatDate pattern="dd-MM-yyyy" value="${command.tbDeathregDTO.drDod}" var="deathDate"/>
								<p class="bnd-p-tag">${marathiConvert.convertToRegional("marathi",deathDate)}</p>      
								<%-- <p class="bnd-p-tag"><fmt:formatDate pattern="dd-MM-yyyy" value="${command.tbDeathregDTO.drDod}"/></p> --%>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">मृत्यूचे ठिकाण</label>
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
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">आईचे पूर्ण नाव</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drMarMotherName}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">वडिलांचे/पतीचे पूर्ण नाव </label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drMarRelativeName}</p>
							</div>
						</div>
						<div class="form-group eng-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Full Name of Mother</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drMotherName}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Full Name of<br>Father/Husband</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drRelativeName}</p>
							</div>
						</div>
						<div class="form-group mar-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">मयत व्यक्तीचा मृत्यूसमयीचा<br>पत्ता</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drDcaddrAtdeathMar}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">मयत व्यक्तीचा कायमचा<br>पत्ता</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drDcaddrAtdeathMar}</p>
							</div>
						</div>
						<div class="form-group eng-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Address of the deceased<br>at the time of death</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drDcaddrAtdeath}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Permanent address<br>of deceased</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drDcaddrAtdeath}</p>
							</div>
						</div>
						<div class="form-group mar-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">नोंदणी क्रमांक</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
                                <p class="bnd-p-tag">${marathiConvert.convertToRegional("marathi",command.tbDeathregDTO.drRegno)} ,</p>
								<%-- <p class="bnd-p-tag">${command.tbDeathregDTO.drRegno}</p> --%>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">नोंदणीची दिनांक</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
                                <fmt:formatDate pattern="dd-MM-yyyy" value="${command.tbDeathregDTO.drRegdate}" var="marRegDate"/>
								<p class="bnd-p-tag">${marathiConvert.convertToRegional("marathi",marRegDate)}</p>
								<%-- <p class="bnd-p-tag"><fmt:formatDate pattern="dd-MM-yyyy" value="${command.tbDeathregDTO.drRegdate}"/></p> --%>
							</div>
						</div>
						<div class="form-group eng-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Registration Number</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.drRegno}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Date of Registration</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag"><fmt:formatDate pattern="dd-MM-yyyy" value="${command.tbDeathregDTO.drRegdate}"/></p>
							</div>
						</div>
						<div class="form-group mar-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">शेरा</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.authRemark}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">निर्गमित करणाऱ्या<br>प्राधिकाऱ्याची सही</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag"></p>
							</div>
						</div>
						<div class="form-group eng-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Remarks (If Any)</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">${command.tbDeathregDTO.authRemark}</p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Signature of the<br>issuing authority</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag"></p>
							</div>
						</div>
						<c:set var="today" value="<%=new java.util.Date()%>" />
						<div class="form-group mar-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">प्रमाणपत्र दिल्याचा दिनांक</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
                                <fmt:formatDate pattern="dd-MM-yyyy" value="<%=new java.util.Date()%>" var="todayDate"/></p>
								<p class="bnd-p-tag">${marathiConvert.convertToRegional("marathi",todayDate)}</p>
								<%-- <p class="bnd-p-tag"><fmt:formatDate pattern="dd-MM-yyyy" value="${today}"/></p> --%>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">प्राधिकाऱ्याचा पत्ता</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">मुख्यालय</p>
							</div>
						</div>
						<div class="form-group eng-text">
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Date of issue Certificate</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag"><fmt:formatDate pattern="dd-MM-yyyy" value="${today}"/></p>
							</div>
							<label for="" class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label ">Address of the<br>issuing authority</label>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">

								<p class="bnd-p-tag">HEAD OFFICE</p>
							</div>
						</div>
						<div class="text-center margin-top-10 margin-bottom-10">
							<span class="bnd-seal">
								शिक्का / Seal
							</span>
						</div>
						<div class="form-group margin-top-10">
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
						</div>
						<!-- <hr/> -->
						<div class="text-bold bottom-section">
							<div class="pull-left">
								"प्रत्येक जन्म आणि मृत्युची घटना नोंदल्याची खात्री करा"
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
					<button type="button" class="btn btn-primary" onclick="printCertificate('printDiv');" data-toggle="tooltip" data-original-title="Print" ><i class="fa fa-print padding-right-5"></i>Print</button>
					<apptags:backButton url="PrintCertificate.html"></apptags:backButton>
				</div>
			</form>
		</div> <!-- widget-content ends -->
	</div>
</div>
<!-- End content here -->

</html>
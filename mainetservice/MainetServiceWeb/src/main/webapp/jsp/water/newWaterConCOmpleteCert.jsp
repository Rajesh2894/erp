<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href="assets/css/print.css" media="print" rel="stylesheet" type="text/css"/>

<script type="text/javascript">
	jQuery(document).bind("keyup keydown", function(e){
	    if(e.ctrlKey && e.keyCode == 80){
	        alert('PLEASE USE THE PRINT BUTTON TO PRINT THIS DOCUMENT');
	        return false;
	    }
	});
	
	
	function printCertificate(el) {
		var restorepage = document.body.innerHTML;
		var printcontent = document.getElementById(el).innerHTML;
		document.body.innerHTML = printcontent;
		window.print();
		document.body.innerHTML = restorepage;
	    }
	
</script>
<style>
.width-50{
	width:"50% !important";
}

#footer {
   bottom:0;
   width:100%;
   height:30px;   /* Height of the footer */
   background:#3f48cc;
   color:white;
   padding-top: 0.3rem;
}
a:link {
  color: white;
  background-color: transparent;
  text-decoration: none;
}

</style>

<div class="content animated slideInDown">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="nwc.certi.header" text="Provisional Certificate" /></h2>
			<div class="additional-btn"><a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a> 
			</div>
		</div>
		<div class="widget-content padding">
			<!-- Print section starts here -->
			<div id="printDiv">
			<div class="">
				<form:form action="" name="" id="provisional" class="form-horizontal">
					<div>
						<div class="text-center">
							<h2 class="margin-bottom-20 text-bold">
								<img src=".\assets\img\nwc_header3.png" style="width:100%;">
							 </h2>	
						</div>
					</div>
					<div class="clear"></div>
					 <h3 class="text-center">
					 	${userSession.organisation.ONlsOrgname}
					</h3>
					
					<%-- <div class="text-bold margin-bottom-30 ">
						 <div class="col-xs-4 col-sm-6">
							<div class="">
								<spring:message code="nwc.certi.from" text="From," />
							</div>
							<div class="">
								<spring:message code="nwc.certi.from.content1" text="Municipal Engineer" />
							</div>	
							<div class="">	
								${userSession.ULBName.lookUpDesc}
							</div>
						  </div>
					 </div> --%>
					<div class="col-xs-12 col-sm-12">
					 <div class="text-bold margin-bottom-30">
						<div  class="col-xs-12 col-sm-12 text-right">
								<div><span class="text-right margin-bottom-20">
									<spring:message code="nwc.certi.application.no" text="Application No.:" />${command.provisionCertificateDTO.applicationNo}
									</span>
								<span class="col-xs-8 col-sm-6"></span>
								</div>
								<div>
									<span class="text-right margin-bottom-10">
										<spring:message code="nwc.certi.date" text="Date:" />${command.provisionCertificateDTO.applicationDate}
									</span>
									<span class="col-xs-6 col-sm-6"></span>
								</div>
					     </div>
					   </div>
	 					<div class="clear"></div>
	 				
		 				<div class="margin-top-30 ">
		 				<div class="col-xs-12 col-sm-12 text-bold">
		 					<spring:message code="nwc.certi.to" text="To," />
		 					
		 				</div>
		 				<div class=" text-bold margin-bottom-10  margin-top-10 margin-left-20">
		 				<div class="col-xs-12 col-sm-12">
		 					 <p> 
		 					 	<spring:message code="" text="Name: " /> 
		 						<span class="">${command.provisionCertificateDTO.fullName}</span>
		 					 </p>
							 <p>
							    <spring:message code="nwc.certi.address1" text="Address: " /> 
							    <span class=""> ${command.provisionCertificateDTO.address1} </span>
							 </p>
							 <p>
								<span class=""> ${command.provisionCertificateDTO.address2} </span>
		 					 </p><br>
		 				 </div>
		 			</div>
		 			</div>
		 			<div class="clear"></div>
	 				<div>
		 				<div class="col-xs-12 col-sm-12">
		 				<p class="margin-left-50 text-bold">
		 					<span><spring:message code="nwc.certi.subject" text="Subject:" /> Completion Certificate for New Tap Connection. </span>
		 					<span></span>
		 				</p>
		 				<p class="margin-top-10 margin-left-50">
		 					<span class="text-bold"><spring:message code="nwc.certi.reference" text="Reference:" /></span>	
		 					<span>
		 						<spring:message code="nwc.certi.ref.content1" text="Your Application for New Water Connection" />
		 					</span>	
		 					<span><spring:message code="nwc.certi.ref.content2" text=" Application number:" /></span>
		 					<span> ${command.provisionCertificateDTO.applicationNo} </span>
		 					<span><spring:message code="nwc.certi.ref.content3" text="and Application Date:" /></span>
		 					<span> ${command.provisionCertificateDTO.applicationDate} </span>
		 					<span><spring:message code="nwc.certi.ref.content.succ" text="Has been installed successfully ." /></span>
		 					
		 				</p>
		 				</div>
	 				 </div>
	 		      <div class="margin-top-30 ">
	 					
	 						<div class="col-xs-12 col-sm-12">
			 					<div class="text-left"> 
			 						<spring:message code="nwc.certi.sir" text="Sir/Madam," />
			 					</div>
			 					<div class="clear"></div>
			 					<p class="margin-top-10">
			 						<p><spring:message code="nwc.certi.body.content1.1" text="In the context of the application number " /> <b>${command.provisionCertificateDTO.applicationNo}</b>
			 							<spring:message code="nwc.certi.body.content1.2" text=",this is to inform you that your new tap connection has been installed successfully with the Connection number" /><b>&nbsp;${command.provisionCertificateDTO.conNo}</b>
			 							<spring:message code="nwc.certi.body.content2.1" text=", that has been approved  by the Municipal Corporation, as mentioned in the application." />
			 				
			 						</p>		 						
			 					</p>
		 					</div>
						<div class="col-xs-12 col-sm-12">
							<br><p class="margin-top-10">
								<b><span><spring:message code="nwc.certi.body.content3" text="Applicant Information" /></span>:</b>
							</p><br>
							<table class="table table table-bordered ">
								<tr>
									<td width="30%"><spring:message code="nwc.certi.tablea.content1" text="Name"/></td>
									<td class="">${command.provisionCertificateDTO.fullName}</td>
									<td width="30%"><spring:message code="" text="Father/Husband Name"/></td>
									<td class="">${command.provisionCertificateDTO.fatherHusbandName}</td>
								</tr>
								<tr>
									<td class=""><spring:message code="nwc.certi.tablea.content2" text="House No."/></td>
									<td class="">${command.provisionCertificateDTO.consumerHouseNumber}</td>
									<td class=""><spring:message code="nwc.certi.tablea.content4" text="Landmark"/></td>
									<td class="">${command.provisionCertificateDTO.consumerLandmark}</td>
								</tr>
								<tr>
									<td class=""><spring:message code="nwc.certi.tablea.content3" text="Address"/></td>
									<td class="">${command.provisionCertificateDTO.address1}  ${command.provisionCertificateDTO.address2}</td>
									<td class=""><spring:message code="nwc.certi.tablea.content5" text="Ward No."/></td>
									<td class="">${command.provisionCertificateDTO.ward}</td>
								</tr>
								<tr>
									<td class=""><spring:message code="nwc.certi.tablea.content6" text="Mobile No."/></td>
									<td class="">${command.provisionCertificateDTO.consumerMobile}</td>
									<td class=""><spring:message code="nwc.certi.tablea.content7" text="Email ID"/></td>
									<td class="">${command.provisionCertificateDTO.consumerEmail}</td>
								</tr>
							</table>
						</div><br>
						<div class="col-xs-12 col-sm-12">
							<p class="margin-top-10 clear">
								<spring:message code="nwc.certi.body.content5.1" text="Approval has been granted based on the subjects listed above, as well as the conditions approved by the Authority in accordance with the rules and the papers submitted by the applicant along with the application." />
							</p>
						</div>
	 					<!-- <p style="page-break-after: always;"></p> -->
	 					<div class="col-xs-12 col-sm-12">
		 					<p class="margin-top-30">
		 						<spring:message code="nwc.certi.note.content5.2" text="On behalf of [Water, Sewerage and Septage Charges and Connection Management], We would like to thank you for using Online New Nal Connection Service. We're committing to provide our citizen with hustle-free Services." />
		 					</p>
		 					<p>
	                          &nbsp; &nbsp; &nbsp; &nbsp; <p><spring:message code="nwc.certi.note.content5.3" text="Thanks you for your patience and support throught out the process." /></p>
							   
		 					</p>
		 					<p class="text-bold margin-top-30"><spring:message code="nwc.certi.note" text="Note" /></p>
	 					 	<p class="text-bold"><spring:message code="nwc.certi.note.contect" text="This is computer generated receipt and does not require physical signature This receipt does not prove the ownership of property or land. For details please visit https://urbanecg.gov.in" /></p>
	 				</div> 
	 			  <!-- </div> -->
	 			</div>
	 		   </div>	
	 			<div class="col-xs-12 col-sm-12">
	 			<div id="footer" class="margin-top-30 margin-bottom-30 text-center"><spring:message code="nwc.certi.footer.content" text="Urban Administration & Development, Department"/>
	 			</div>
				</div>
				<div class="clear"></div>
				</form:form>
			</div>
			<!-- Print section ends here -->	 <!-- printCertificate('printDiv') -->
		</div>	
		  <div class="text-center clear padding-top-20">
						<button onclick="printCertificate('printDiv')"
							class="btn btn-primary hidden-print">
							<i class="icon-print-2"></i>
								<spring:message code="" text="Print"></spring:message>
						</button>
						
						<button type="button" class="btn btn-danger hidden-print"
							onclick="window.location.href='AdminHome.html'">
							<spring:message code="" text="Back"></spring:message>
							
						</button>
		  </div>	
		</div>
	</div>
</div>
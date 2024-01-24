<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/birthAndDeath/bndBirtReport.js"></script> 

  <apptags:breadcrumb></apptags:breadcrumb>
  
  <div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="BNDBirtReport.label.BNDBirtReport" text="BND Birt Report" />
				<apptags:helpDoc url="BNDBirtReport.html"></apptags:helpDoc>
			</h2>
		</div>
		<div  class="widget-content padding">
		
		<!-- <div class="warning-div error-div aaaaaalert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	 	<span id="errorId"></span>
</div> -->

		<form:form action="BNDBirtReport.html" id="frmbirtreport" method="POST" commandName="command" class="form-horizontal form">
		
		<%-- <h4> <spring:message text="BND Birt Report" /> </h4> --%>
		
			<div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
				<h4> <spring:message code="BNDBirtReport.label.birthReport" text="Birth Report" /> </h4>
				<div class="list-design-serv">
					<ul>
						<li><a href="#" onclick="confirmToProceed('Birth_Register_Report.rptdesign')"  data-value="1" > <spring:message code="BNDBirtReport.label.birthRegister" /></a></li>
						<li><a href="#" onclick="confirmToProceed('RP_BNDBirthCountByFatherEducation.rptdesign')"> <spring:message code="BNDBirtReport.label.birthByLevelOfEdOfFA" /> </a></li>
						<li><a href="#" onclick="confirmToProceed('RP_BNDBirthCountByFatherOccupation.rptdesign')" > <spring:message code="BNDBirtReport.label.birthByLevelOfOpOfFA" /> </a></li>
						<li><a href="#" onclick="confirmToProceed('RP_BNDBirthCountByMotherEducation.rptdesign')" ><spring:message code="BNDBirtReport.label.birthByLevelOfEdOfMA" /> </a></li>
						<li><a href="#" onclick="confirmToProceed('RP_BNDBirthCountByMotherOccupation.rptdesign')" > <spring:message code="BNDBirtReport.label.birthByLevelOfOpOfMA" /></a></li>
						<li><a href="#" onclick="confirmToProceed('Hospital_Wise_Birth_Details_Report.rptdesign')" > <spring:message code="BNDBirtReport.label.hospitalWiseBirthDet" /></a></li>
						<li><a href="#" onclick="confirmToProceed('RP_BirthBySexAndMonthofOccurrence.rptdesign')" > <spring:message code="BNDBirtReport.label.sexAndMonthBirth" /></a></li>
						<li><a href="#" onclick="confirmToProceed('RP_MonthlySummaryReportsofBirths.rptdesign')" > <spring:message code="BNDBirtReport.label.monthlySummaryBirth" /></a></li>
						<li><a href="#" onclick="confirmToProceed('RP_SummaryofBirthwiseGenderRatio.rptdesign')" > <spring:message code="BNDBirtReport.label.summaryOfBirthWithSex" /></a></li>
						
					</ul>
				</div>
			</div>
			
			<div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
				<h4> <spring:message code="BNDBirtReport.label.deathReport" text="Death Report" /> </h4>
				<div class="list-design-serv">
					<ul>
						<li><a href="#" onclick="confirmToProceed('Death_Register_Report.rptdesign')"> <spring:message code="BNDBirtReport.label.deathRegister" /></a></li>
						<li><a href="#" onclick="confirmToProceed('RP_ChildMortalityReport.rptdesign')" > <spring:message code="BNDBirtReport.label.childMortality" /> </a></li>
						<li><a href="#" onclick="confirmToProceed('RP_DeathBySexAndMonthofOccurrence.rptdesign')" ><spring:message code="BNDBirtReport.label.sexAndMonthDeath" /></a></li>
						<li><a href="#" onclick="confirmToProceed('RP_InstitutionalDeathsByCause.rptdesign')" > <spring:message code="BNDBirtReport.label.institutionalDeaths" /></a></li>
						<li><a href="#" onclick="confirmToProceed('Death_Register_Female_Report.rptdesign')" ><spring:message code="BNDBirtReport.label.deathRegisterFemale" /></a></li>
						<li><a href="#" onclick="confirmToProceed('RP_MonthlySummaryReportsOfDeath.rptdesign')" ><spring:message code="BNDBirtReport.label.monthlySummaryDeath" /></a></li>
						
						<!-- <li><a href="#" onclick="confirmToProceed()" > Birth and Death Application Status </a></li> -->
					</ul>
				</div>
			</div>
			
			<div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
				<h4> <spring:message code="BNDBirtReport.label.birth&deathReport" text="Birth & Death Report" /> </h4>
				<div class="list-design-serv">
					<ul>
					<li><a href="#" onclick="confirmToProceed('RP_MonthWiseCertificateIssuedNotIssued.rptdesign')" > <spring:message code="BNDBirtReport.label.monthWiseCer" /></a></li>	<li></li>
					</ul>
				</div>
			</div>
			
			
			<div class="clear"></div>
		
		<div class="text-center margin-top-20">
		<apptags:backButton url="AdminHome.html"></apptags:backButton>
		</div>
		</form:form>
	 </div>
   </div>
</div>
		
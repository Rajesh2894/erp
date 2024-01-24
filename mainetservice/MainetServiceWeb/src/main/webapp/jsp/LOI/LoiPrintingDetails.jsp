<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/loi/loidashboard.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script>

$( document ).ready(function(){
	
	$('.lessthancurrdate').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		maxDate: '-0d',
		yearRange: "-100:-0"
	});
});

</script>
<style>
.outer-border {
	padding: 20px;
	border: 1px solid #000000;
	margin: 20px auto;
}
.drainageBorderDivide{
	border :1px solid #dadada;
}
table tr .verticalMiddle{
	vertical-align: middle !important;
}
</style>
<!--     <ol class="breadcrumb">
      <li><a href="admin-home.html"><i class="fa fa-home"></i></a></li>
      <li>Home</li>
      <li>LOI</li>
      <li class="active">LOI Print</li>
    </ol> -->
    <!-- ============================================================== --> 
    <!-- Start Content here --> 
    <!-- ============================================================== -->
<!--    <div id="loiPrint" class="padding-20"> -->
    <div class="content">
      <div class="widget">
        <!-- <div class="widget-header">
          <h2><strong>LOI Print</strong></h2>
        </div> -->
        <div class="widget invoice" id="loiPrint">
        <div class="widget-content padding outer-border">         
      <form:form action="#" method="POST" class="form-horizontal" id="loiprintform">	
          <c:if test="${command.loiMaster.loiRecordFound eq 'Y' }">
 	        <%-- <h4>Application Details</h4>		  
            <div class="form-group">
              <label class="col-sm-2 control-label">Service Name</label>
              <div class="col-sm-4">
                <form:input path="searchDto.serviceName" type="text" class="form-control" readonly="true"/>
              </div>
              <label class="col-sm-2 control-label">Application Number</label>
              <div class="col-sm-4">
                <form:input path="loiMaster.loiApplicationId" type="text" class="form-control" readonly="true"/>
              </div>
            </div>
			<h4>Letter of Indent (LOI) Details</h4>

            <div class="form-group">
              <label class="col-sm-2 control-label">LOI Number</label>
              <div class="col-sm-4"> <form:input path="loiMaster.loiNo" type="text" class="form-control" readonly="true"/></div> 
              <label class="col-sm-2 control-label">LOI Date</label>
              <div class="col-sm-4"><fmt:formatDate pattern="dd/MM/yyyy" value="${command.loiMaster.loiDate}" /></div>
            </div>
               
             <h4>Applicant Details</h4>  
              <div class="form-group">
              <label class="col-sm-2 control-label">Applicant Mobile No</label>
              <div class="col-sm-4">
                <form:input path="searchDto.mobileNo" type="text" class="form-control" readonly="true"/>
              </div>
              <label class="col-sm-2 control-label">Applicant EmailId</label>
              <div class="col-sm-4">
               <form:input path="searchDto.email" type="text" class="form-control" readonly="true"/>
              </div>
            	</div>
            
 			 <div class="form-group">
              <label class="col-sm-2 control-label">Applicant Name</label>
              <div class="col-sm-4">
                <form:input path="searchDto.applicantName" type="text" class="form-control" readonly="true"/>
              </div>
            </div> --%>
            
            
            <div class="row">

					<div class="col-xs-3">
						<img src="${userSession.orgLogoPath}" width="80">
					</div>
					
					<div class="col-xs-6 text-center">
						<h3 class="margin-bottom-0">
							<span class="text-bold"><spring:message code=""
								text="${userSession.organisation.ONlsOrgname}" /></span>
						</h3>
						<h3 class="text-bold text-center margin-top-20">
							<spring:message code="loi.name"
								text="Letter of Intimation" />
						</h3>
						</div>

					<%-- <div class="col-sm-6 text-center col-xs-6 letterFontSize">
						<h3 class="text-bold text-center margin-top-20">
							<spring:message code="loi.name"
								text="Letter of Intimation" />
						</h3>
					</div> --%>
				</div>
				<div class="drainageBorderDivide"></div>
					<div class="row margin-top-10 clear">
					<div class="col-sm-1 col-sm-offset-9 col-xs-2 col-xs-offset-8 ">
						<p>
							<spring:message code="loi.no"
								text="LOI No.:" />
						</p>
					</div>
					<div class="col-sm-2 col-xs-2">
						<p>
							<span class="text-bold">${command.loiMaster.loiNo}</span>
						</p>
					</div>
					<div class="col-sm-1 col-sm-offset-9 col-xs-2 col-xs-offset-8 ">
						<p>
							<spring:message code="loi.date"
								text="Date:" />
						</p>
					</div>
					<div class="col-sm-2 col-xs-2">
						<p>
							<span class="text-bold">${command.searchDto.applicationDate}</span>
						</p>
					</div>
				</div>
				
				<div class="row margin-top-20 clear">
					<div class="col-sm-12">
						<p>To,</p>
						<p class="text-bold margin-left-30">
							<!-- ApplicantName -->
							 ${command.searchDto.applicantName}
						</p>

						<p class="text-bold margin-left-30">
							<!-- ApplicantAddress -->
							${command.searchDto.address}
						</p>
						<div class="col-sm-10 col-sm-offset-2">
						<p class="padding-top-20">
							<span class="text-bold"><spring:message code="loi.subject"
								text="Subject:" /></span>
								<spring:message code ="loi.subject.detail" />
							<span class="text-bold">${command.searchDto.applicationId}</span>
						</p>
						</div>
						<p class="margin-top-20 margin-bottom-10">
							<spring:message code="loi.sirmadam" text="Sir/Madam," />
						</p>
						
						<p>
						
							<span class="margin-left-50"><spring:message code="loi.report1"></spring:message></span>
							<span class="text-bold">${command.searchDto.applicationId}</span>
							<spring:message code="loi.report2"></spring:message>
							<span class="text-bold">${command.searchDto.applicationDate}</span>
							<spring:message code="loi.report3"></spring:message>
							<span class="text-bold"><fmt:formatNumber type="number" 
							value="${command.searchDto.total}" groupingUsed="false" 
							maxFractionDigits="2" minFractionDigits="2" var="searchDtoTotal"/>${searchDtoTotal}</span>
							<spring:message code="loi.report4"></spring:message>
						<!-- 	<span class="text-bold">Days</span> -->
							<spring:message code="loi.report5"></spring:message>
							<span class="text-bold">${userSession.organisation.ONlsOrgname}</span>
							<spring:message code="loi.report6"></spring:message>
						
						</p>
					</div>
				</div>
				
            
			
			<!-- <h4>LOI Fees and Charges in Details</h4> -->
			<div class="margin-top-20">
            <table class="table table-bordered table-striped">
	<tr>
	<th scope="col" width="80">Sr. No</th>
	<th scope="col">Charge Name</th>
	<th scope="col">Amount (in Rs.)</th>
	</tr>
	<c:forEach var="charges" items="${command.searchDto.getChargeDesc()}" varStatus="status">
	<tr>
	<td class="verticalMiddle">${status.count}</td>
	<td class="verticalMiddle">${charges.key}</td>
	<td class="verticalMiddle"><%-- <form:input path="" type="text" class="form-control text-right" value="${charges.value}" readonly="true"/> --%>
	<span readonly="true" class="form-control text-right"> <fmt:formatNumber type="number" value="${charges.value}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" 
	var="chargesValue"/> ${chargesValue} </span>
	</td>
	</tr>
	</c:forEach>
	
	<tr>
	<td class="verticalMiddle" colspan="2"><span class="pull-right"><b>Total Amount</b></span></td>
	<td class="verticalMiddle"> <%-- <form:input path="searchDto.total" type="text" class="form-control text-right" readonly="true"/> --%>
	<span readonly="true" class="form-control text-right"> <fmt:formatNumber type="number" value="${command.searchDto.total}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" 
	var="totalValue"/> ${totalValue} </span>
	</td>
	</tr>
	</table>
	</div>

 <div class="text-center hidden-print padding-bottom-20 margin-top-20">
  <!--  <button type="submit" class="btn btn-success" onclick="return saveData(this);">Submit</button> -->
   <button onclick="printLoiContent('loiPrint')" class="btn btn-success"> <i class="icon-print-2"></i> <spring:message code="water.plumberLicense.print" text="Print"/></button>	
   <button type="button" class="btn btn-danger" onclick="window.location.href='LoiPrintingController.html'">Back</button>
 </div>
 </c:if>
 <c:if test="${command.loiMaster.loiRecordFound eq 'N' }">
  <span><b>No Record Found</b></span>
 </c:if>
 </form:form>
 </div>
 </div>
 </div>
 </div>
          
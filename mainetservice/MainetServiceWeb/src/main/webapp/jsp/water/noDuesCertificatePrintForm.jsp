  <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/water/noDuesCertificate.js"></script>
<script>
function printContent(el){
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
}

</script>

 <%--  <apptags:breadcrumb></apptags:breadcrumb> --%>

    <!-- ============================================================== --> 
    <!-- Start Content here --> 

    <!-- ============================================================== -->
    <div class="content"> 
      <!-- Start info box -->
      <div class="widget" id="receipt">
        <div class="widget-content padding">
        <div id="now">
          <div class="row">
            <div class="col-xs-3 text-left"><img src="../../assets/img/logo.png"></div>
            <div class="col-xs-6 text-center">
              <h4 class="margin-bottom-0">${command.nodueCertiDTO.orgName}</h4>
               <p><spring:message code="water.dept" /></p>
              <p class="margin-top-5"><strong>${command.nodueCertiDTO.serviceName}</strong></p>
            </div>
            <div class="col-xs-3 text-right"><img src="../../assets/img/logo.png"></div>
          </div>
          <p class="text-right">${command.nodueCertiDTO.noDuesCertiDate}</p>
          <p class="text-right">Certificate No. ${command.nodueCertiDTO.certificateNo}</p>
          <div class="form-group clearfix margin-top-15">
            <div class="col-xs-6">
              <p>To,</p>
            </div>
          </div>
          <div class="row clearfix margin-top-10">
            <div class="col-xs-2 col-xs-push-1">Name:</div>
            <div class="col-xs-9 col-xs-push-1">${command.nodueCertiDTO.applicantName}</div>
          </div>
          <div class="row clearfix margin-top-10">
            <div class="col-xs-2 col-xs-push-1">Subject:</div>
            <div class="col-xs-9 col-xs-push-1">${command.nodueCertiDTO.serviceName}</div>
          </div>
          <div class="row clearfix margin-top-10">
            <div class="col-xs-2 col-xs-push-1">Reference:</div>
            <div class="col-xs-9 col-xs-push-1">1) Application No. ${command.nodueCertiDTO.applicationId} Dated  ${command.nodueCertiDTO.applicantDate}</div>
            <div class="col-xs-9 col-xs-push-3">2) Water Connection Inspection</div>
            <div class="col-xs-9 col-xs-push-3">
            	<p><spring:message code="water.ward.div" />${command.nodueCertiDTO.ward} </p>
                <p><spring:message code="water.block.div" />${command.nodueCertiDTO.block}</p>
                <p><spring:message code="water.zone.div" />${command.nodueCertiDTO.zone}</p>
            </div>
          </div>
          
          
          <p class="margin-top-20">Sir/Madam,</p>
          <p class="margin-top-5">This is to certify that the connection no. ${command.nodueCertiDTO.connectionNo} Address ${command.nodueCertiDTO.applicantAdd} India stands in the name of ${command.nodueCertiDTO.applicantName}.</p>
          <p>The water tax for the said premised has been paid for the year ${command.nodueCertiDTO.fromDate} - ${command.nodueCertiDTO.toDate} as record maintained by Municipal Corporation.</p>
          <div class="row margin-top-25">
            <div class="col-xs-5 col-xs-push-7 text-center">
              <p><spring:message code="water.muncip.engg" /></p>
              <p><strong>${command.nodueCertiDTO.orgName}</strong></p>
            </div>
          </div>
          </div>
         <!--  <div class="text-center">
            <button onclick="printContent('receipt')" class="btn btn-primary hidden-print"><i class="icon-print-2"></i> Print</button>
          </div> -->
        </div>
      </div>
      </div>
     
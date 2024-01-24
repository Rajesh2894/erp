<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script>
    function printContent(el) {
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
    }
</script> 

<!-- Start Content here -->
<div class="content">
	<div class="widget-header">
				<h2><b><spring:message code="property.report.publicNotice"/></b></h2>				
	</div>
		
<!-- Start Widget Content -->
<div class="widget" id="receipt">
	<div class="widget-content padding">

                    <div class="col-xs-12 col-sm-12  text-center">
                        <h3 class="text-medium margin-bottom-0 margin-top-0 text-bold">
							${userSession.getCurrent().organisation.ONlsOrgname}
						</h3>
                        <div class="clearfix padding-10"></div>
                        
                        <h2 class="text-large margin-bottom-0 margin-top-0 text-bold">
							<%-- ${command.publicNoticeDto.deptName} --%>
						
						</h2>

                    </div>
                    <div class="clearfix padding-5"></div>

                    <%-- <div class="col-xs-12 col-sm-12 text-right">
                        <p>
                            <spring:message code="adh.report.Date" text="Notice Date"/>${command.publicNoticeDto.noticeDate}
                        </p>
                    </div> --%>
                    <hr>
                    <div class="col-xs-12 col-sm-12  text-center">
                        <h2 class="text-large margin-bottom-0 margin-top-0 text-bold">
							<spring:message code="property.report.publicNotice"/>
						</h2>
                    </div>

                    <div class="clearfix padding-5"></div>

                    <div class="col-xs-12 col-sm-12 text-right">
                        <p>
                           <spring:message code="property.report.NoticeNo"/>${command.publicNoticeDto.noticeNo} 
                            <br> <spring:message code="property.report.NoticeDate"/>${command.publicNoticeDto.noticeDate} 
                        </p>
                    </div>

                    <div class="clearfix padding-25"></div>
                    
					<div class="col-xs-11">
                        <p class="margin-top-15"><span class="text-bold"/> <spring:message code="" text="To,"/></p>
                        <p class="margin-top-15"><span class="text-bold"><spring:message code="" text="Agency Owner : "/></span>&nbsp;${command.publicNoticeDto.agencyOwner}</p>
                        <p class="margin-top-15"><span class="text-bold"><spring:message code="" text="Agency Address : "/></span>&nbsp;${command.publicNoticeDto.agencyAddress}
								<%-- ${command.publicNoticeDto.agencyAddress} --%>
								</p>
                    
                        <p class="margin-top-15"><span class="text-bold"> <spring:message code="property.report.Subject"/></span><spring:message code="" text="Notice regarding unauthorized Advertisement/Hoarding"/></p>
                        <p class="margin-top-15"><span class="text-bold"><spring:message code="property.report.Reference"/></span><spring:message code="" text="Agency Licence Number"/>
								<span class="text-bold">${command.publicNoticeDto.licenceNo}</span>
								&nbsp;<%-- ${command.publicNoticeDto.appDate} --%></p>
						 <p class="margin-top-15"><span class="text-bold"/> <spring:message code="" text="Dear Sir/Madam,"/></p>	
						 <p class="margin-top-15"><spring:message code="" text="During Inspection it has been found that unauthorized Advertisement/Hoarding has been raised by your agency."/>	
					      <spring:message code="" text="You are hereby informed that following is penalty amount details "/><span class="text-bold"> ${command.publicNoticeDto.amount}</span>
				           <spring:message code="" text="  for such unauthorized Advertisement/Hoarding"/></p>
						 <p class="margin-top-15"> <spring:message code="" text="You are requested to pay the amount and remove the unauthorized Advertisement/Hoarding within 07 days of receipt of this notice"/>
						 <spring:message code="" text="Non compilence of this notice may lead to termination of agency licence also"/>
						 </p>
						  
                    </div>
                    
                    <div class="padding-5 clear">&nbsp;</div>					
					<div class = "form-group clearfix padding-vertical-15">
							<div class="col-xs-8 text-center"></div>
							<div class="col-xs-4 text-center">
				              <p><spring:message code="property.report.content9"/></p>
				              <%-- <p class="margin-top-15">${command.publicNoticeDto.dsgName}</p> --%>
				              <p>	${userSession.getCurrent().organisation.ONlsOrgname}</p>
				            </div>
					</div>
                    <div class="padding-5 clear">&nbsp;</div>
                
	                <div class="text-center margin-top-20">
					<button onclick="printContent('receipt')"
						class="btn btn-primary hidden-print">
						<i class="icon-print-2"></i>
						<spring:message code="adh.print" text="Print"></spring:message>
					</button>
					<button type="button" class="btn btn-danger hidden-print"
						onclick="window.location.href='AdminHome.html'">
						<spring:message code="adh.close" text="Close"></spring:message>
					</button>
				</div>
            </div>
			</div><!-- End Widget Content -->		
</div><!-- End Content here -->


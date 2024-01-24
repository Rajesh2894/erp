<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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


<div class="content">
<!-- Start info box -->
	<div class="widget" id="receipt">
		<div class="widget-content padding">
		<div id="now">
			<div class="row">
				
				<div class="text-center">				
					<h3>${command.ONlsOrgname}</h3>
					<p class="margin-top-5">
						<strong></strong>
					</p>
				</div>
				
			</div>
			<div class="row margin-top-15">
				<div class="col-xs-3 col-sm-2 col-sm-offset-10 col-xs-offset-9">
					<p class="text-left margin-bottom-5">
						<span class="text-bold"><spring:message	code="trd.notice.date" text="Date : " /></span>
						<fmt:formatDate value="<%=new java.util.Date()%>" pattern="dd/MM/yyyy" />
					</p>
	
					<p class="text-left ">
						<span class="text-bold"><spring:message
								code="trd.notice.no" text="Notice No : " />${command.noticeDetailDto.noticeNo}</span>
					</p>
				</div>
			</div>
			

					
			<p><span class="text-bold"><spring:message code="trade.to" text="To," /></span></p>
			<div class="margin-left-30">
				<p class="margin-bottom-5"><spring:message code="trade.mr.mrs" text="Mr / Mrs: " />${command.noticeDetailDto.applicantName}</p>
				<p>${command.noticeDetailDto.appAdd}</p>
			</div>
				
				
			<p class="text-center margin-bottom-10">
				<span class="text-bold"><spring:message code="trd.notice.sub" text="Subject :  "/></span>
				<spring:message code="trd.reminder.subject" text="Reminder of the Renewal of License No. "/>
				<span class="text-bold">${command.noticeDetailDto.licNo}</span>
			</p><br/>
				
			<p class="margin-bottom-10"><spring:message code="trade.Sir/Madam" text="Sir/Madam,"/></p>
			<div class="margin-left-30">
				<p class="margin-top-5">
					<spring:message code="trade.remainder.para1"
						text="You are here by informed that validity of your License No. " />					
					<span class="text-bold">${command.noticeDetailDto.licNo}</span>				
					<spring:message code="trade.remainder.para2"
						text="has Expired/will Expire on date " />		
					  ${command.noticeDetailDto.toDate}<br />
					  <spring:message code="trade.remainder.para3" text="of Business" />	
					<span class="text-bold">${command.noticeDetailDto.businessName}</span>
					<spring:message code="trade.remainder.para4"
						text="However, please renew your license during this period or legal action will be taken." />
				</p>
			</div>
			
			<div class="row margin-top-30">
				<div class="col-xs-3 col-sm-2 col-sm-offset-10 col-xs-offset-9">
					<div class="text-center text-bold">
						<p>${command.designation}</p>
						<p>${command.ONlsOrgname}</p>
					</div>
				</div>
			</div>
				
				
				  <div class="text-center margin-top-20">
					<button onclick="printContent('receipt')"
						class="btn btn-primary hidden-print">
						<i class="icon-print-2"></i>
						<spring:message code="trade.print" text="Print"></spring:message>
					</button>
					
					<button type="button" class="btn btn-danger hidden-print"
						onclick="window.close();">
						<spring:message code="trade.close" text="Close"></spring:message>
					</button>
				</div>
				
				
		</div>
		
		</div>
		</div>
		

</div>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<jsp:useBean id="now" class="java.util.Date" />
<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
<link href="assets/libs/animate-css/animate.min.css" rel="stylesheet" />
<link href="assets/libs/pace/pace.css" rel="stylesheet" />
<!-- <link href="assets/css/style.css" rel="stylesheet" type="text/css"  id=""/> -->
<style>
	.topbar .navbar-default {
	    background-color: transparent;
	    border-color: transparent;
	}
	
	#print-div p, #print-div table tr th, #print-div table tr td{font-size: 15px;line-height: 20px;}
</style>
<script>
function printdiv(printpage)
{
	var headstr = "<html><head><title></title></head><body>";
	var footstr = "</body>";
	var newstr = document.all.item(printpage).innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = headstr+newstr+footstr;
	window.print();
	document.body.innerHTML = oldstr;
	return false;
}

$(document).ready(function() {
	$(this).find('html').removeClass('fancybox-lock');
});

</script>

<div class="content animated slideInDown">
<div id="print-div">
<div class="widget">
<div class="widget-content padding" style="padding:2.5rem !important;">
				<form action="" method="get" class="form-horizontal">
				
				

						<div class="form-group clearfix">
							<div class="col-xs-3 col-sm-3 text-left">
								<img src="${logo}" width="80px" height="80px">
							</div>
							<div class="col-xs-6 text-center margin-top-10">
						    <h3 class="text-extra-large  margin-bottom-0 margin-top-0">
							<strong>${userSession.getCurrent().organisation.ONlsOrgname}</strong>							
						    </h3>
						    <p class="text-bold margin-top-10" text-align:center"><span class="text-bold">${command.noticeDetailDto.deptName}</span></p>
							</div>				
						</div>
				
				    <h3 class="text-extra-large text-bold text-center"><spring:message code="trade.notice" text="Show Cause Notice"></spring:message></h3>
                    <p style="text-align:center"><spring:message code="trd.note"/></p><br/>
                    
					<p style="text-align:right"><span class="text-bold padding-right-5"><spring:message code="trd.notice.no" text="Notice No :- "/>${command.noticeDetailDto.noticeNo}</span></p><br/>	
					<p style="text-align:right"><span class="text-bold padding-right-5"><spring:message code="trd.notice.date" text="Date :- "/></span><fmt:formatDate var="date" value="${now}" pattern="dd-MM-yyyy" />${date}</p><br/>										
					<p class="text-bold"><spring:message code="trd.notice.to" text="To,"/></p>
					<p class="">${command.noticeDetailDto.applicantName}</p><br/>
					<p class="">${command.noticeDetailDto.appAdd}</p><br/>	
					<p style="text-align:center"><span class="text-bold"><spring:message code="trd.notice.sub" text="Subject :-  "/></span><spring:message code="trade.notice" text="Show cause notice"/></p><br/>
				    <p style="text-align:center"><span class="text-bold"><spring:message code="trade.ref" text="Reference :-  "/></span>${command.noticeDetailDto.ref}</p><br/>	
					<p><spring:message code="trade.Sir/Madam" text="Sir/Madam,"/></p><br/>
					<p>${command.noticeDetailDto.content}</p><br/>	
							
		
			<div class="clearfix padding-10"></div>
			<div class="table-responsive">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th width="50"><spring:message code="lgl.srno"
									text="Sr. No." /></th>
							<th width="50"><spring:message code="trd.notice.reason"
									text="Reason" /></th>
						</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.noticeDetailDtoList}"
							var="noticeList" varStatus="status">
						<tr>
						<td class="text-center">${status.count}</td>
						<td class="text-center">${noticeList.reason}</td>
						</tr>
						</c:forEach>
						</tbody>
				</table>							
		     </div>
			     
			<div class="col-sm-14 margin-top-30">
			    <p align="right">${userSession.employee.designation.dsgname}</p>
			<p align="right" class="text-bold">${userSession.ULBName.lookUpDesc}</p>
			</div>
			
			 <div class="col-sm-14 margin-top-20">
			 <p><span class="text-bold"><spring:message code="trd.notice.note" text="Note :"/></span><spring:message code="trd.note"/></p>							
			</div>
						
						
				</div>
				
				<div class="text-center hidden-print">
						<button onclick="printdiv('print-div');" class="btn btn-primary hidden-print"><i class="fa fa-print"></i> Print</button>			
					<button type="button" class="btn btn-danger hidden-print"
						onclick="window.location.href='AdminHome.html'"><spring:message code="" text="Close"></spring:message>
					</button>
					</div>
					
				</form>
				</div>
</div>

</div>

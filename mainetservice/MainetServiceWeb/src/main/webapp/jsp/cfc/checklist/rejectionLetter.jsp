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
<link href="assets/css/style.css" rel="stylesheet" type="text/css"  id=""/>
<style>
	#print-div p, #print-div table tr th, #print-div table tr td{font-size: 15px;line-height: 20px;}
</style>

<script>
	$(document).ready(function() {
		$('.fancybox').fancybox();
		$(".myTable").dataTable({
			//your normal options
			"language": { "search": "" }, 
			"lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]]
		});
		$('.chosen-select').chosen();
	});
	
	/* JS for Print button */
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
</script>
    <!-- <ol class="breadcrumb">
      <li><a href="#"><i class="fa fa-home"></i></a></li>
      <li>Rejection Letter</li>
    </ol> -->
	
	<!-- Start Content here -->
	<div class="content animated slideInDown">
		<!-- Start info box -->
		<div class="widget">
			<!-- <div class="widget-header">
				<h2>Rejection Letter</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
				</div>
			</div> -->
			<div class="widget-content padding">
				<form action="" method="get" class="form-horizontal">
				
					
					<div id="print-div" class="container">

						<div class="form-group clearfix">
							<div class="col-xs-3 col-sm-3 text-left">
								<img src="${logo}" width="80px" height="80px">
							</div>
							<div class="col-xs-6 text-center margin-top-10">
								<p class="margin-bottom-0 text-large text-bold">
									${userSession.ULBName.lookUpDesc}</p>
							</div>
						</div>



					<h3 class="text-bold text-center">${dto.lHeading}</h3>
						<p><span class="text-bold">${dto.lletterNo} ${dto.colon}</span> ${dto.tletterno}</p><br/>
						<p><span class="text-bold">${dto.ldate} ${dto.colon}</span> <fmt:formatDate var="date" value="${now}" pattern="dd/MM/yyyy" />${date}</p><br/>
						<p class="text-bold">${dto.lTo}</p>
						<p class="">${dto.applicantName}</p><br/>
						<p class="">${dto.applicationAdd}</p><br/>
						<p><span class="text-bold">${dto.lRejSub} ${dto.colon}</span> ${dto.subject} <span class="text-bold">${dto.subject1}</span>${dto.subject2}</p><br/>
						<p><span class="text-bold">${dto.lRejRef} ${dto.colon}</span> ${dto.refFill} <span class="text-bold">${dto.refFill1}</span> ${dto.refFill2} <span class="text-bold">${dto.refFill3}</span></p><br/>
						<p>${dto.lSirMadam}</p><br/>
						<p>${dto.lRefLine}<span class="text-bold"> ${dto.lRefLine1}</span> ${dto.lRefLine2}</p><br/>
						<table class="table table-bordered">
							<thead>
								<tr>
									<th class="text-center">${dto.lSrNo}</th>
									<th class="text-center">${dto.lDocName}</th>
									<th class="text-center">${dto.lObsDetail}</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${dto.childDTO}" var="doc">
								<tr>
									<td class="text-center">${doc.tSrNo}</td>
									<td class="text-center">${doc.tDocName}</td>
									<td class="text-center">${doc.tObsDetail}</td>
								</tr>
								</c:forEach>
							</tbody>
						</table><br/>
						<p>${dto.lLastLine}</p><br/>
						<p>${dto.lLastLine2}</p>
						
						<div class="col-sm-14 margin-top-30">
							<p class="text-small"><spring:message code="cfc.comp.note" text="This is a computer generated document no signature is required"/></p>
							<p class="text-bold">${userSession.ULBName.lookUpDesc}</p>
						</div>
						
					</div>
					<div class="text-center hidden-print">
						<button onclick="printdiv('print-div');" class="btn btn-primary hidden-print"><i class="fa fa-print"></i> Print</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	

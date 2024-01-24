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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script>
function printContent(el){
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
}
</script>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content"> 
      <!-- Start info box -->
      <div class="widget">
        <div class="widget-header">
          <h2>
          	<spring:message code="demand.notice.print" text="Demand Notice" />
          </h2>
          
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
        </div>
        <div class="widget-content padding">
        			<form:form action="DemandNoticePrinting.html" method="post" class="form-horizontal">
			          <c:forEach items="${command.response}" var="data"  varStatus="status"> 
			          	<c:if test="${data.selected }">
			           		  <!-- Start info box -->
					          <div class="row">
					            <div class="col-xs-3 text-left"><img src="assets/img/logo.png"></div>
					            <div class="col-xs-6 text-center">
					              <h4 class="margin-bottom-0">${command.orgName}</h4>
					              <p class="margin-top-10"><strong><spring:message code="demand.notice" text="Demand Notice"/> </strong></p>
					            </div>
					            <div class="col-xs-3 text-right"><img src="assets/img/logo.png"></div>
					          </div>
					          <div class="form-group clearfix">
					            <div class="col-xs-6">
					              <p><spring:message code="demand.number" text="Number"/>: ${data.noticeNo }</p>
					            </div>
					            <div class="col-xs-6 text-right">
					            <fmt:formatDate pattern="dd/MM/yyyy" value="${data.noticeDate}" var="noticeDate"/>
					              <p><spring:message code="demand.date" text="Date"/>: ${noticeDate }</p>
					            </div>
					          </div>
					          <div class="form-group clearfix margin-top-5">
					            <div class="col-xs-6">
					              <p><spring:message code="demand.from" text="From"/>,</p>
					            </div>
					          </div>
					          <div class="row">
					            <div class="col-xs-11 col-xs-push-1">
					              <p>...................,</p>
					              <p class="margin-top-5">${command.deptName},</p>
					              <p class="margin-top-5">${command.orgName}.</p>
					            </div>
					          </div>
					          <div class="form-group clearfix margin-top-5">
					            <div class="col-xs-6">
					              <p><spring:message code="demand.to" text="To"/>,</p>
					            </div>
					          </div>
					          <div class="row">
					            <div class="col-xs-11 col-xs-push-1">
					            <c:set value="${data.custName }" var="name"/>
					              <c:set var="custName" value="${fn:replace(name,'null','') }"/>
					              <p>${custName }</p>
					              <c:set value="${data.custAddress }" var="addr"/>
					              <c:set var="custAddr" value="${fn:replace(addr,'null','') }"/>
					              <p class="margin-top-5">${custAddr}</p>
					              <!-- <p class="margin-top-5">--------------------------------</p> -->
					            </div>
					          </div>
					          <div class="row margin-top-15">
					            <div class="col-xs-1"><spring:message code="demand.subject" text="Subject"/>:</div>
					            <div class="col-xs-11"><spring:message code="demand.subject" text="Subject" arguments="${data.connectionNo }"/></div>
					          </div>
					            <fmt:formatDate pattern="dd/MM/yyyy" value="${data.outstandangFrom}" var="fromDate"/>
					            <fmt:formatDate pattern="dd/MM/yyyy" value="${data.outstandangTo}" var="toDate"/>
					          <p class="margin-top-15"><spring:message code="demand.salutation" text="Mr. / Mrs,"/>,</p>
					          <p class="margin-top-15"><spring:message code="demand.para1" text="paragraph 1" arguments=" ${data.billAmount},${fromDate},${toDate}"/></p>
					          <p class="margin-top-10"><spring:message code="demand.para2" text="paragraph 2" arguments=" ${data.billAmount},${command.demandDays}"/></p>
					          <div class="row margin-top-20">
					            <div class="col-xs-6 text-left">
					              <p><spring:message code="demad.greets" text="Sincerely"/>,<br>
					                ..................,<br>
					                <strong>${command.deptName}.</strong></p>
					            </div>
					          </div>
		      <!-- End of info box --> 
		      		</c:if>
				  </c:forEach>
			       <div class="text-center">
				            <button onclick="printContent('receipt')" class="btn btn-primary hidden-print"><i class="icon-print-2"></i><spring:message code="water.plumberLicense.print" text="Print"/> </button>
				   </div>
		   </form:form>
        </div>
      </div>
</div>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	response.setContentType("text/html; charset=utf-8");
%> 
<script>
function printContent(el){
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
}
</script>

      <div class="widget">
        <div class="widget-content padding form-horizontal" id="printdiv"> 
          <div class="row">
            <div class="col-xs-12 text-center padding-bottom-10">
              <h3>${command.propInfoDTO.orgName}</h3>
              <p><spring:message code="rnl.book.app.info" text="Applicant Information"></spring:message></p>
            </div>
          </div>
          
            	<div class="form-group">
                	<label class="col-xs-2 control-label"><spring:message code="rl.property.label.name" text="Property Name"></spring:message></label>
                  	<div class="col-xs-4 padding-top-5">
                  		${command.propInfoDTO.propName}
                    </div>
                    <label class="col-xs-2 control-label"><spring:message code="rnl.book.Category" text="Category"></spring:message></label>
                    <div class="col-xs-4 padding-top-5">
                    	${command.propInfoDTO.category}
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-2 control-label"><spring:message code="rl.property.label.Area" text="Area"></spring:message></label>
                    <div class="col-xs-4 padding-top-5">
                    	${command.propInfoDTO.areaName}
                    </div>
                    <label class="col-xs-2 control-label"><spring:message code="rnl.payment.city" text="City"></spring:message></label>
                  	<div class="col-xs-4 padding-top-5">
                    	${command.propInfoDTO.city}
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="col-xs-2 control-label"><spring:message code="rnl.payment.pin.code" text="Pin Code"></spring:message></label>
                    <div class="col-xs-4 padding-top-5">
                    	${command.propInfoDTO.pinCode}
                    </div>
                </div>
                
                <div class="padding-top-10">
								<table class="table table-bordered table-striped">
									<tr>
										<th><spring:message code="rnl.payment.booking"    text="Booking ID"></spring:message></th>
										<th><spring:message code="rnl.payment.applicant"  text="Name of Applicant"></spring:message></th>
										<th><spring:message code="rnl.propinfo.contactno" text="Contact No."></spring:message></th>
										<th><spring:message code="rnl.prop.book.purpose"  text="Booking Purpose"></spring:message></th>
										<th><spring:message code="rnl.from.date"          text="From Date"></spring:message></th>
										<th><spring:message code="rnl.to.date"            text="To Date"></spring:message></th>
										<th>Period</th>
									</tr>
									<tr>
										<td>${command.propInfoDTO.bookingId}</td>
										<td>${command.propInfoDTO.applicantName}</td>
										<td>${command.propInfoDTO.contactNo}</td>
										<td>${command.propInfoDTO.bookingPuprpose}</td>
										<td><fmt:formatDate pattern="yyyy-MM-dd" value="${command.propInfoDTO.fromDate}" /></td>
										<td><fmt:formatDate pattern="yyyy-MM-dd" value="${command.propInfoDTO.toDate}" /></td>
										<td>${command.propInfoDTO.dayPeriod}</td> 
									</tr>
								</table>
							</div>
            
            <div class="text-center padding-top-10">
            <button onclick="printContent('printdiv')" class="btn btn-primary hidden-print"><i class="icon-print-2"></i><spring:message code="rnl.prop.info.print" text="Print"></spring:message></button>
            <input type="button" id="backBtn" class="btn btn-danger hidden-print" onclick="back()" value="<spring:message code="bt.backBtn"/>" />
          </div>
          
        </div>
      </div>
    



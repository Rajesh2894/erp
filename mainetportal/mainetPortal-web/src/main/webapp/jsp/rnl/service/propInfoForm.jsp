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
              <h2 class="text-extra-large">${command.propInfoDTO.orgName}</h2>
              <p>Applicant Information</p>
            </div>
          </div>
          
            	<div class="form-group">
                	<label class="col-xs-2 control-label">Property Name</label>
                  	<div class="col-xs-4 padding-top-5">
                  		<span class="form-control border-0 margin-top-5-offset">${command.propInfoDTO.propName}</span>
                    </div>
                    <label class="col-xs-2 control-label">Category</label>
                    <div class="col-xs-4 padding-top-5">
                    	<span class="form-control border-0 margin-top-5-offset">${command.propInfoDTO.category}</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-2 control-label">Area</label>
                    <div class="col-xs-4 padding-top-5">
                    	<span class="form-control border-0 margin-top-5-offset">${command.propInfoDTO.areaName}</span>
                    </div>
                    <label class="col-xs-2 control-label">City</label>
                  	<div class="col-xs-4 padding-top-5">
                    	<span class="form-control border-0 margin-top-5-offset">${command.propInfoDTO.city}</span>
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="col-xs-2 control-label">Pin Code</label>
                    <div class="col-xs-4 padding-top-5">
                    	<span class="form-control border-0 margin-top-5-offset">${command.propInfoDTO.pinCode}</span>
                    </div>
                </div>
                
                <div class="padding-top-10">
								<table class="table table-bordered table-striped">
									<tr>
										<th>Booking ID</th>
										<th>Name of Applicant</th>
										<th>Contact No.</th>
										<th>Booking Purpose</th>
										<th>From Date</th>
										<th>To Date</th>
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
            <button onclick="printContent('printdiv')" class="btn btn-primary hidden-print"><i class="icon-print-2"></i> Print</button>
            <input type="button" id="backBtn" class="btn btn-danger hidden-print" onclick="back()" value="<spring:message code="bt.backBtn"/>" />
          </div>
          
        </div>
      </div>
    



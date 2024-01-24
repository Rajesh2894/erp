<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />


<apptags:breadcrumb></apptags:breadcrumb>

    <div class="content animated slideInDown">
      <div class="widget">
        <div class="widget-header">
          <h2><spring:message code="rnl.estate.contract.map.summary" text="Estate Contract Mapping Summary"></spring:message></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i><span class="hide"><spring:message code="rnl.book.help" text="Help"></spring:message></span></a> </div>
        </div>
        <div class="widget-content padding">
          <div class="mand-label clearfix"><span><spring:message></spring:message><spring:message code="rnl.book.field" text="Field with"></spring:message><strong class="text-red-1">*</strong> <spring:message code="master.estate.field.mandatory.message" text="is mandatory"></spring:message></span></div>
            <div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;"></div>
          <form action="" method="get" class="form-horizontal">
            <div class="form-group">
             <label class="control-label col-sm-2" for="ContractNo"><spring:message code="rnl.master.contract.no" text="Contract No."></spring:message></label>
              <div class="col-sm-4">
                <input name="contractNo" id="contractNo" type="text" class="form-control">
              </div>
              <label class="control-label col-sm-2" for="ContractDate"><spring:message code="rnl.master.contract.date" text="Contract Date"></spring:message></label>
              <div class="col-sm-4">
                <div class="input-group">
                	<input name="conDate" id="conDate" type="text" class="form-control datepicker" id="ContractDate">
                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                </div>
              </div>                           
            </div>
            
            <div class="text-center padding-bottom-10">
                <button type="button" id="searchButtonId" class="btn btn-success btn-submit"><strong class="fa fa-search"></strong><spring:message code="rnl.master.search" text="Search"></spring:message> </button> 
                <button type="button" id="addMappingId" class="btn btn-blue-2" > <spring:message code="rnl.estate.cont.map" text="Estate Contract Mapping"></spring:message></button>
            </div>
            
            <div class="table-responsive clear">
              <table class="table table-striped table-bordered" id="datatables">
                <thead>
                  <tr>
                    <td><spring:message code="rnl.master.contract.no"      text="Contract No."></spring:message></td>
                    <td><spring:message code="rnl.master.contract.date"    text="Contract Date"></spring:message></td>
                  	<td><spring:message code="master.complaint.department" text="Department"></spring:message></td>
                  	<td><spring:message code="rnl.master.represented.by"   text="Represented By"></spring:message></td>
                    <td><spring:message code="rnl.master.vender.name"      text="Vendor Name"></spring:message></td>
                    <td><spring:message code="rnl.master.contract.from.date" text="Contract From Date"></spring:message></td>
                    <td><spring:message code="rnl.master.contract.to.date"  text="Contract To Date"></spring:message></td>
                    <td width="120"><spring:message code="rnl.master.view.map" text="View Mapping"></spring:message></td>
                    <td><spring:message code="rnl.master.print.contract"    text="Print Contract"></spring:message></td>
                  </tr>
                </thead>
                <tbody id="propertyListId">
                   <c:forEach items="${contractList}" var="data" varStatus="count">
                     <tr>  
                           <td>${data.contractNo}</td>
                           <td>
                           		<fmt:parseDate pattern="dd/mm/yyyy" value="${data.contDate}" var="date" />
								<fmt:formatDate type="date" var="formatedDate" value="${date}" pattern="yyyymmdd" />
								<span style="display:none"> ${formatedDate} </span>
								<c:out value="${data.contDate}"></c:out>
                           </td>
                           <td>${data.deptName}</td>
                           <td>${data.representedBy}</td>
                           <td>${data.vendorName}</td>
                           <td>
                           		<fmt:parseDate pattern="dd/mm/yyyy" value="${data.fromDate}" var="date" />
								<fmt:formatDate type="date" var="formatedDate" value="${date}" pattern="yyyymmdd" />
								<span style="display:none"> ${formatedDate} </span>
								<c:out value="${data.fromDate}"></c:out>
                           </td>
                           <td>
                           		<fmt:parseDate pattern="dd/mm/yyyy" value="${data.toDate}" var="date" />
								<fmt:formatDate type="date" var="formatedDate" value="${date}" pattern="yyyymmdd" />
								<span style="display:none"> ${formatedDate} </span>
								<c:out value="${data.toDate}"></c:out>
                           </td>
                           <td ><a href="javascript:void(0);" class="btn btn-blue-2 btn-sm text-center margin-left-30" data-original-title="View Mapping" onClick="showContractMapping(${data.contId})"><strong class="fa fa-eye"></strong><span class="hide"><spring:message code="rnl.master.view" text="View"></spring:message></span></a></td>
                           <td ><a href="javascript:void(0);" class="btn btn-darkblue-3 text-center margin-left-30" onClick="printContractEstate(${data.contId})"><strong><i class="fa fa-print"></i></strong></a></td>
                      </tr>
                   </c:forEach>
                  
                </tbody>
              </table>
            </div>
          </form>
        </div>
      </div>
    </div>
      <script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/rnl/master/mappingHome.js"></script>
<div id="ViewContract" class="max-width-700"></div>
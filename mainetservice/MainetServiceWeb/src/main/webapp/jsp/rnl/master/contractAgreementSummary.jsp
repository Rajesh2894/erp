<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="../assets/libs/jqueryui/jquery-ui-datepicker.css" rel="stylesheet" type="text/css">

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="js/rnl/master/contractAgreement.js"></script>
<script src="js/rnl/master/contractAgreementSummary.js"></script>

 <script>
$( document ).ready(function() {

	$(".datepicker").datepicker({
	    dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		maxDate: '-0d',
		changeYear: true,
	});
	
	});	
</script>


<apptags:breadcrumb></apptags:breadcrumb> 
    <div class="content animated slideInDown">
      <div class="widget">
        <div class="widget-header">
          <h2><spring:message code="rnl.master.contract.summary" text="Contract Summary"></spring:message></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i><span class="hide"><spring:message code="rnl.book.help" text="Help"></spring:message></span></a> </div>
        </div>
          <div class="widget-content padding">
          <div class="mand-label clearfix"><span><spring:message code="rnl.book.field" text="Field with"></spring:message> <strong class="text-red-1">*</strong><spring:message code="master.estate.field.mandatory.message" text="is mandatory"></spring:message></span></div>
                  <form:form action="ContractAgreement.html"
					class="form-horizontal form" name="ContractAgreementSummary"
					id="ContractAgreement">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
            <div class="form-group">
             <label class="control-label col-sm-2" for="ContractNo"><spring:message code="rnl.master.contract.no" text="Contract No."></spring:message></label>
              <div class="col-sm-4">
                <form:input path="" type="text" class="form-control" id="contractNo"/>          
              </div>
              <label class="control-label col-sm-2" for="ContractDate"><spring:message code="rnl.master.contract.date" text="Contract Date"></spring:message></label>
              <div class="col-sm-4">
                <div class="input-group">
                	<form:input path="" type="text" class="form-control datepicker" id="contractDate"/>
                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                </div>
              </div>
                           
            </div>
            <div class="form-group">
             <label class="control-label col-sm-2" for="Department"><spring:message code="master.complaint.department" text="Department"></spring:message></label>
              <div class="col-sm-4">
                         <select class="chosen-select-no-results form-control" id="departmentId">
					                  <option value=""><spring:message code="selectdropdown" /></option>
						                 <c:forEach items="${departmentList}" var="deptArray">
						                     <option value="${deptArray[0]}"><c:choose><c:when test="${userSession.languageId eq 2}">${deptArray[2]}</c:when><c:otherwise>${deptArray[1]}</c:otherwise></c:choose></option>
						                </c:forEach> 
					                </select>
              </div>
              <label class="control-label col-sm-2" for="VendorName"><spring:message code="rnl.master.vender.name" text="Vendor Name"></spring:message></label>
              <div class="col-sm-4">
                  <select class="chosen-select-no-results form-control" id="vendorId">
					                  <option value=""><spring:message code="selectdropdown" /></option>
						                 <c:forEach items="${getVenderList}" var="venArray">
						                     <option value="${venArray[0]}">${venArray[1]}</option>
						                </c:forEach> 
					                </select> 
              </div>
            </div>
            <div class="form-group">
             <label class="control-label col-sm-2" for="ViewClosedContracts"><spring:message code="rnl.master.view" text="View Closed Contracts"></spring:message></label>
              <div class="col-sm-4">
               <select name="" id="viewClosedContracts" class="form-control"><option>Select</option><option>Yes</option><option selected>No</option></select>
              </div>
            </div>
            <div class="text-center padding-bottom-10">
                <button type="button" id="btnsearch" class="btn btn-success"><strong class="fa fa-search"></strong> <spring:message code="rnl.master.search" text="Search"></spring:message></button>
                <form:input path="" id="addEstateLink" value="Add Contract Master" class="btn btn-blue-2" type="BUTTON"/></div>
            <div id="" align="center">
					                <table id="contractGrid"></table>
					                <div id="estatePager"></div>
				            </div>
        </form:form>
        </div>
                </div>
 
  </div>
   
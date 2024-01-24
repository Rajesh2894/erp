<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="js/masters/book/bookReceipt.js"> </script>
<div id="mainPage"> 
 <apptags:breadcrumb></apptags:breadcrumb>

    <div class="content"> 
      <!-- Start info box -->
      <div class="widget">
        <div class="widget-header">
          <h2><spring:message code="book.receiptbook" text="Receipt Book Details"/></h2>
          <apptags:helpDoc url="bookreceipt.html"></apptags:helpDoc>
        </div>
        <div class="widget-content padding">
          <div class="mand-label clearfix"><span>Field with <i class="text-red-1">*</i> is mandatory</span></div>
          <form:form action="bookreceipt.html" method="POST" class="form-horizontal" id="ReceiptBookId">
          	<jsp:include page="/jsp/tiles/validationerror.jsp" />
            
            <div class="form-group">
			<label class="col-sm-2 control-label required-control"><spring:message code="book.employeeName" text="Employee"/></label>
			<div class="col-sm-4">
				<form:select path="" onchange="" id="employeeId" cssClass="form-control chosen-select-no-results">
					<form:option value="" ><spring:message text="Select Employee Name" code="book.option.employeeName"/> </form:option>
					<c:forEach items="${command.employeeList}" var="lookUp">
					 <form:option value="${lookUp.lookUpId}">${lookUp.lookUpType}</form:option>
					 </c:forEach>
				</form:select>              
			</div>
		
			<label class="col-sm-2 control-label required-control"><spring:message code="book.financialYear" text="Financial"/></label>
			<div class="col-sm-4">
				<form:select path="" onchange="" id="fayearId" cssClass="form-control chosen-select-no-results">
					<form:option value=""><spring:message text="Select Financial year" code="book.option.financialYear"/> </form:option>
					<c:forEach items="${command.fiancialYearList}" var="lookUp">
					 <form:option value="${lookUp.lookUpId}">${lookUp.lookUpType}</form:option>
					 </c:forEach>
				</form:select>              
			</div>
			
	
		   </div>
            
            <div class="text-center padding-bottom-10">
				
					 <input type="button" value="<spring:message code="book.bt.search" text="Search"/>" class="btn btn-success" onclick="return searchReceiptBookData()"/>
					
				     <input type="button" value="<spring:message code="book.bt.clear" text="Reset"/>" class="btn btn-warning" onclick="resetReceipBookForm()"/>		
				
					 <button type="button" class="btn btn-success"  onclick="addFormData(this)"><spring:message code="book.bt.add" text="Add"/></button>
				
		   </div>	
		   
		    <div class="form-group">      
				        <div id="" align="center">
					    <table id="ReceiptBookGrid"></table>
					    <div id="ReceiptBookPager"></div>
				       </div>
		   
		   </div>
		   				
 </form:form>
 </div>
 </div>
 </div>
 </div>
 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="js/masters/book/bookReceipt.js"> </script>
	
<div id="mainPage"> 

    <div class="content"> 
      <!-- Start info box -->
      <div class="widget">
        <div class="widget-header">
          <h2><spring:message code="book.receiptbook" text="receipt Book Details"/></h2>
           <apptags:helpDoc url="bookreceipt.html"></apptags:helpDoc>
        </div>
        <div class="widget-content padding">
          <div class="mand-label clearfix"><span>Field with <i class="text-red-1">*</i> is mandatory</span></div>
          <form:form action="bookreceipt.html" method="POST" class="form-horizontal" id="RecieptForm">
          	<jsp:include page="/jsp/tiles/validationerror.jsp" />
          	
          	
          	<div class="warning-div error-div alert alert-danger alert-dismissible"
				 id="errorDiv" >
					<!-- style="display: none;" -->
			</div>
           <div class="form-group">
			<label class="col-sm-2 control-label required-control"><spring:message code="book.employeeName" text="Employee Name"/></label>
			<div class="col-sm-4">
			<form:select path= "bookReceiptDTO.empId"  onchange="" id="employeeId" disabled="${command.bookReceiptDTO.formMode eq 'V' ? true : false}" cssClass="form-control chosen-select-no-results">
			         <form:option value=""><spring:message text="Select Employee Name" code="book.option.employeeName"/> </form:option>
			         <c:forEach items="${command.employeeList}" var="lookUp">
					 <form:option value="${lookUp.lookUpId}">${lookUp.lookUpType}</form:option>
					 </c:forEach>
				     </form:select>              
			</div>  
		  
			<label class="col-sm-2 control-label required-control"><spring:message code="book.financialYear" text="Financial Year"/></label>
			<div class="col-sm-4">
				<form:select path="bookReceiptDTO.faYearId" onchange="" id="fayearId" disabled="${command.bookReceiptDTO.formMode eq 'V' ? true : false}" cssClass="form-control chosen-select-no-results">
					<form:option value="" ><spring:message text="Select Financial year" code="book.option.financialYear"/> </form:option>
					<c:forEach items="${command.fiancialYearList}" var="lookUp">
					 <form:option value="${lookUp.lookUpId}">${lookUp.lookUpType}</form:option>
					 </c:forEach>
				</form:select>              
			</div> 
		
		</div>
		     
		
		     
		     
		     
        <div class="form-group">
           <label  class="col-sm-2 control-label required-control"  ><spring:message code="book.receiptbookNo" text="Receipt Book No"/></label>
           <div class="col-sm-4">
           <form:input path= "bookReceiptDTO.bookreceiptNo" value="${bookReceiptDTO.bookreceiptNo}" class="form-control hasNumber" id="receiptBookNo" disabled="${command.bookReceiptDTO.formMode eq 'V' ? true : false}"  ></form:input>
           </div> 
          <%--   <apptags:input labelCode="Receipt Book No." path="bookReceiptDTO.bookreceiptNo" isMandatory="true"></apptags:input>   --%>
              
		 <label  class="col-sm-2 control-label required-control"  ><spring:message code="book.receipbookNoFrom" text="Receipt Book No From"/></label>
             <div class="col-sm-4">
              <form:input path= "bookReceiptDTO.bookreceiptNoFrom" value="${bookReceiptDTO.bookreceiptNoFrom}"  onkeyup="changeTotalBookReceiptNos(this)"  class="form-control hasNumber" id="receiptNoFrom" disabled="${command.bookReceiptDTO.formMode eq 'V' ? true : false}"  ></form:input>
              </div> 
			<%-- <apptags:input labelCode="Receipt No From" path="bookReceiptDTO.bookreceiptNoFrom" isMandatory="true" ></apptags:input> --%>
              
        </div>
		 <div class="form-group">
		 	<%-- <apptags:input labelCode="Receipt No To" path="bookReceiptDTO.bookreceiptNoTo" isMandatory="true" ></apptags:input> --%>
		       <label class="col-sm-2 control-label required-control"><spring:message code="book.receiptbookNoTo" text="Receipt Book No To"/></label> 
               <div class="col-sm-4"> 
               <form:input path= "bookReceiptDTO.bookreceiptNoTo"  value="${bookReceiptDTO.bookreceiptNoTo}" onkeyup="changeTotalBookReceiptNos(this)"   class="form-control hasNumber" id="receiptNoTo" disabled="${command.bookReceiptDTO.formMode eq 'V' ? true : false}" ></form:input> 
               </div> 
		<%-- <label class="control-label col-sm-2"><spring:message code="book.totalreceiptbookNo" text="Total Receipt Nos"/></label> 
            <div class="col-sm-4"> 
                 <form:input path= "bookReceiptDTO.totalbookReceiptNo" value="${bookReceiptDTO.totalbookReceiptNo}"  class="form-control hasNumber" id="totalReceiptNo" disabled="${command.bookReceiptDTO.formMode eq 'V' ? true : false}" ></form:input>  --%>
              <apptags:input labelCode="Total Receipt Nos" path="bookReceiptDTO.totalbookReceiptNo" isMandatory="true" isDisabled="${command.bookReceiptDTO.formMode eq 'V' ? true : false}"></apptags:input>
            <!--   </div>  -->
		
		</div> 
		
		    
       <div class="text-center padding-bottom-10">
       
                    <c:if test="${command.bookReceiptDTO.formMode ne 'V' }">
                     <input type="button" value="<spring:message code="bt.submit" text="Save"/>" class="btn btn-success btn-submit"
							onclick="saveBookForm(this)">
				    </c:if>
					<input type="button" value="<spring:message code="bt.backBtn" text="Back"/>" class="btn btn-danger"
						onclick="back()" id="backBtn">
					 <!-- <button type="button" class="btn btn-warning" onclick="addFormData(form)">Reset</button> -->
					
				</div>					
 </form:form>
 </div>
 </div>
 </div>
 </div>

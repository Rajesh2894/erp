<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
  <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <script type="text/javascript" src="js/mainet/validation.js"></script>
 <script type="text/javascript" src="js/property/propertyBillPayment.js" ></script>   
 <script type="text/javascript" src="js/mainet/file-upload.js"></script>
 
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here --> 
<div class="content"> 
  <div class="widget">
    <div class="widget-header">
      <h2><spring:message code="propertyBill.BillCollection"/></h2>
     
      	<apptags:helpDoc url="PropertyBillPayment.html"></apptags:helpDoc>	     
     
    </div>
    <div class="widget-content padding">
      <form:form action="PropertyBillPayment.html" class="form-horizontal" name="PropertyBillPayment" id="PropertyBillPayment">
       		<jsp:include page="/jsp/tiles/validationerror.jsp" />
       		<form:hidden path="billingMethod" id="billingMethod"/>
       		<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
       		
		
            <div class="panel-group accordion-toggle" id="#accordion_single_collapse">  
        	<div class="panel panel-default" id="paymentInfo">
        		<c:if test="${command.parentGrpFlag eq 'N' }">
	                <div class="panel-heading">
	                  <h4 class="panel-title"><a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#ConnectionDetails"><spring:message code="property.Propertydetail" text="Property Details"/></a></h4>
	  				</div>
	  				<div id="ConnectionDetails" class="panel-collapse collapse in">
				         <div class="panel-body">
	  				
					<div class="form-group" >		
						<apptags:input labelCode="propertydetails.PropertyNo." path="billPayDto.propNo" cssClass="mandColorClass" isReadonly="true"></apptags:input>
						<apptags:input labelCode="propertydetails.oldpropertyno" path="billPayDto.oldpropno" cssClass="mandColorClass" isReadonly="true"></apptags:input>		
					</div>
  				
				         
				         <div class="form-group">
				         <apptags:input labelCode="Flat No" path="propBillPaymentDto.flatNo" isReadonly="true"></apptags:input>		         
				         	<apptags:input labelCode="ownerdetail.Ownername" path="billPayDto.primaryOwnerName" isDisabled="true" ></apptags:input>				         	
				         </div>
				         </div>
		         	</div>		        
					
						<div class="accordion-toggle">
						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#propertyAddress"><spring:message
									code="property.Propertyaddress" /></a>
						</h4>
						<div class="panel-collapse collapse in" id="propertyAddress">
							<div class="form-group">
								
								<apptags:textArea isMandatory="true" labelCode="property.propertyaddress" path="billPayDto.address" isDisabled="true"></apptags:textArea>
								<apptags:input labelCode="property.location" path="billPayDto.location" isDisabled="true"></apptags:input>
								<%-- <apptags:select cssClass="chosen-select-no-results" labelCode="property.location" items="" path="" isMandatory="true" isLookUpItem="true" selectOptionLabelCode="select Location">
								</apptags:select> --%>		
							</div>
							
							<div class="form-group">					
								<apptags:input cssClass="form-control hasPincode" labelCode="property.pincode" path="billPayDto.pinCode"  isDisabled="true"></apptags:input>							
							</div>
					     </div> 
		            </div>
           		</c:if>
           		
          <!-- Group property -->
						<c:if test="${command.parentGrpFlag eq 'Y' }">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse"
										href="#Propertydetail"><spring:message
											code="property.Propertydetail" text="Property Details" /></a>
								</h4>
							</div>
							<div id="ConnectionDetails" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="property.parentPropName"
											path="billPayDto.parentPropName" cssClass="mandColorClass"
											isReadonly="true"></apptags:input>
										<apptags:input labelCode="propertydetails.parentPropNo"
											path="billPayDto.parentPropNo" cssClass="mandColorClass"
											isReadonly="true"></apptags:input>
									</div>
								</div>
							</div>
						</c:if>
						<!-- Group property -->
         

        	<div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"><a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#PayableAmountDetails"><spring:message code="property.ReceiptAmountDetails" text="Receipt Amount Details"/></a></h4>
  				</div>
  				
  				<div id="PayableAmountDetails" class="panel-collapse collapse in">
         			<div class="panel-body">

									<c:if test="${command.billPayDto.billDisList  ne null && not empty command.billPayDto.billDisList}">
									 <div class="table-responsive">
							            <table class="table table-bordered table-condensed">
							            
							              <tr>
							                <th><spring:message code="propertyBill.TaxDescription"/></th>
							                <th><spring:message code="propertyBill.BalanceArrears"/></th>
							                <th><spring:message code="propertyBill.CurrentTax"/></th>
							                <th><p class="text-right"><spring:message code="propertyBill.TotalBalance"/></p></th>
							              </tr>
						               <c:forEach items="${command.billPayDto.billDisList}" var="billDet" varStatus="detStatus">  
						            <tr>
						              <td><p class="text-right">${billDet.taxDesc}</p></td> 
						                <%-- <td><p class="text-right">${billDet.arrearsTaxAmt}</p></td> --%>
						                <td><p class="text-right"><fmt:formatNumber type="number" pattern = "0.0" value="${billDet.arrearsTaxAmt}" /></p></td> 
						                <%-- <td><p class="text-right">${billDet.currentYearTaxAmt}</p></td> --%>
						                <td><p class="text-right"><fmt:formatNumber type="number"   pattern = "0.0" value="${billDet.currentYearTaxAmt}" /></p></td>
						               <%-- <td><p class="text-right">${billDet.totalTaxAmt}</p></td> --%> 
						               <td><p class="text-right"><fmt:formatNumber type="number"  pattern = "0.0"  value="${billDet.totalTaxAmt}" /></p></td>
							             </tr> 
							               </c:forEach> 
							            </table>
							          </div>
									<c:choose>
	              	<c:when test="${command.billPayDto.totalPenalty gt 0}">
	              	

  
	              	<tr>
	              	
	              	<td><p>Total Penalty</p><p class="text-right">${command.billPayDto.totalPenalty}</p></td> 
	              
	              	<%-- <th width="500" colspan="2">Total Penalty</th>
	              		
	              	<th width="500" class="text-right" colspan="3">${command.billPayDto.totalPenalty}</th> --%>
	              
	              	</tr>
	              	</c:when>
	              	<c:otherwise>	              	
	                      
					</c:otherwise>
					</c:choose>
								</c:if> 
							        <div class="form-group padding-top-10" >
									<label class="col-sm-2 control-label"><spring:message code="propertyBill.TotalReceivable"/></label>
									<div class="col-sm-4">
									<div class="input-group">
							          <form:input path="billPayDto.totalPayableAmt" cssClass="form-control" id="totalPayable" readonly="true"/>
							          <label class="input-group-addon"><i class="fa fa-inr"></i></label>
							        </div>
									</div>
									
									<c:choose>
	              					<c:when test="${command.billPayDto.partialAdvancePayStatus eq 'PAI'}">	              					
									<label class="col-sm-2 control-label"><spring:message code="propertyBill.EnterReceiptAmount"/></label>
									<div class="col-sm-4">
 									<div class="input-group"> 
								         <form:input path="billPayDto.totalPaidAmt" cssClass="form-control mandColorClass" maxlength="10" id="payAmount" value="${command.billPayDto.totalPayableAmt}" readonly="true"/>
								         <label class="input-group-addon"><i class="fa fa-inr"></i></label>
								     </div> 
									</div>
									</c:when>
									<c:otherwise>
									<label class="col-sm-2 control-label"><spring:message code="propertyBill.EnterReceiptAmount"/></label>
									<div class="col-sm-4">
 									<div class="input-group"> 
								      	   <form:input path="billPayDto.totalPaidAmt" cssClass="form-control mandColorClass" maxlength="10" id="payAmount" />
								           <label class="input-group-addon"><i class="fa fa-inr"></i></label>
								     </div> 
									</div>
									</c:otherwise>
									</c:choose>
									</div>
		<c:if test="${command.billPayDto.billDisList eq null || empty command.billPayDto.billDisList}">
		 <div class="form-group" >
		<label class="col-sm-10 text-red"><spring:message code="property.billDueValid" text="Since No Dues are pending, Your Payment will be considered as Advance Payment"/></label>
		</div>
		</c:if> 
									
				<c:if test="${command.receiptType eq 'M'}">
<div class="form-group padding-top-10" >	
			<apptags:input labelCode="Manual Receipt No" path="manualReceiptNo"></apptags:input>
			<apptags:input labelCode="Manual Receipt Date" path="manualReeiptDate"  isReadonly="true" cssClass="trimDateTime"></apptags:input>
			</div>
			
			
		<div class="overflow margin-top-10">
											<div class="table-responsive">
												<table
													class="table table-hover table-bordered table-striped">
													<tbody>
														<tr>
															<th> <spring:message
																		code="water.serialNo" text="Sr No" />
															</th>
															<th> <spring:message
																		code="water.docName" text="Document Name" />
															</th>
															<th> <spring:message
																		code="water.status" text="Status" />
															</th>
															<th> <spring:message
																		code="water.uploadText" text="Upload" />
															</th>
														</tr>
															<tr>
																<td>1</td>
																<td>Manual Receipt</td>
																	<td> <spring:message
																				code="water.doc.mand" />
																	</td>
																<td><div id="docs_0" class="">
																		<apptags:formField fieldType="7" labelCode=""
																			hasId="true" fieldPath=""
																			isMandatory="false" showFileNameHTMLId="true"
																			fileSize="BND_COMMOM_MAX_SIZE"
																			maxFileCount="CHECK_LIST_MAX_COUNT"
																			validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
																			currentCount="0" />
																	</div>
																	</td>
															</tr>
													</tbody>
												</table>
											</div>
										</div>									
			

</c:if>					
								<%-- <c:if test="${not empty command.message && command.message ne null }">
								 <div class="form-group" >
								<label class="col-sm-10 control-label text-red">${command.message}</label>
								</div>
								</c:if> --%> 
					</div>
				</div>
			</div>
		 <div class="panel panel-default">
		<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp"/>
		</div> 
		<%-- <div class="panel panel-default">
		<jsp:include page="/jsp/cfc/Challan/offlinePayForMultiMode.jsp"/>
		</div> --%>
		
         <div class="text-center padding-top-10">
          <button type="button" class="btn btn-success btn-submit"  onclick="savePropertyFrom(this)"><spring:message code="propertyBill.Submit"/></button>
           <button type="button" class="btn btn-danger" onclick="backToMain(this)"><spring:message code="propertyBill.Back"/></button>
         </div>
<%--        </c:if> --%>
       </div>
       </div>
      </form:form>
    </div>
  </div>
</div>


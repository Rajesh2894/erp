<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/property/selfAssessment.js"></script>
<script type="text/javascript" src="js/property/ownershipDetailsForm.js"></script>
<script type="text/javascript" src="js/property/propertyBillRevise.js"></script>
<script type="text/javascript" src="js/property/changeUnitDetails.js"></script>
<div id="validationDiv">

	<!-- Start breadcrumb Tags -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<!-- End breadcrumb Tags -->

	<!-- Start Content here -->
	<div class="content">
		<!-- Start Main Page Heading -->
		<div class="widget">
			<div class="widget-header">
				<h2>
					<strong><spring:message code=""
							text="Revision of property" /></strong>
				</h2>

				<apptags:helpDoc url="SelfAssessmentForm.html"></apptags:helpDoc>

			</div>

			<!-- End Main Page Heading -->

			<!-- Start Widget Content -->
			<div class="widget-content padding">


				<!-- Start mand-label -->
				<div class="mand-label clearfix">
					<span><spring:message code="property.Fieldwith" /><i
						class="text-red-1">* </i>
					<spring:message code="property.ismandatory" /> </span>
				</div>
				<!-- End mand-label -->

				<!-- Start Form -->
				<form:form action="SelfAssessmentForm.html"
					class="form-horizontal form" name="frmSelfAssessmentForm"
					id="frmSelfAssessmentForm">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>


					<input type="hidden" id="ownershipCode" />
					<input type="hidden" value="${command.orgId}" id="orgId" />
					<input type="hidden" value="${command.deptId}" id="deptId" />
					<input type="hidden" value="${command.showTaxDetails}" id="showTaxDetails" />

					<!-- End Each Section -->
					<div class="form-group">
					<apptags:input labelCode="Property No" path="provisionalAssesmentMstDto.assNo" isReadonly="true"></apptags:input>
					<apptags:input labelCode="Old Property No" path="provisionalAssesmentMstDto.assOldpropno" isReadonly="true"></apptags:input>
					</div>
					<div class="form-group">
					<apptags:input labelCode="Owner Name" path="ownerName" isReadonly="true"></apptags:input>
					<apptags:input labelCode="House No" path="provisionalAssesmentMstDto.tppPlotNo" isReadonly="true"></apptags:input>
					</div>
					<div class="form-group">
					<apptags:input labelCode="New House No" path="provisionalAssesmentMstDto.newHouseNo" isReadonly="true"></apptags:input>
					</div>

						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#taxZoneDetails"><spring:message
									code="property.taxZoneDetails" /></a>
						</h4>


						<div class="panel-collapse collapse in" id="taxZoneDetails">
							<c:set var="orgId"
								value="${userSession.getCurrent().organisation.orgid}" />
							<div class="form-group">
								<apptags:lookupFieldSet cssClass="form-control required-control"
									baseLookupCode="WZB" hasId="true"
									pathPrefix="provisionalAssesmentMstDto.assWard"
									hasLookupAlphaNumericSort="true"
									hasSubLookupAlphaNumericSort="true" showAll="false"
									disabled="true" isMandatory="true" />

							</div>
							<div class="form-group">
								<label for="road-type"
									class="col-sm-2 control-label required-control"><spring:message
										code="unitdetails.RoadType" /> </label>
								<div>
									<c:set var="baseLookupCode" value="RFT" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="provisionalAssesmentMstDto.propLvlRoadType"
										cssClass="form-control" hasChildLookup="false" hasId="true"
										showAll="false" selectOptionLabelCode="prop.selectRoadFactor"
										isMandatory="true" />
								</div>

								<div class="col-sm-4">
									<label class="text-red-1" for=""><spring:message
											code="property.propNote" /> <a
										href="WZB/${orgId}_fileDoc.pdf" target="_blank"><spring:message
												code="property.clickHere" /></a></label>
								</div>
							</div>

						</div>






						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#BuildingDetails"><spring:message
									code="property.buildingdetails" /></a>
						</h4>
						
						<div class="panel-collapse collapse in" id="BuildingDetails">
							<div class="form-group">
								<apptags:input labelCode="property.yearofacquisition"
									path="provisionalAssesmentMstDto.assAcqDate"								
									cssClass="dateClass lessthancurrdate" isReadonly="true"></apptags:input>

								<label class="col-sm-2 control-label required-control"><spring:message
										code="property.totalplot" text="Total plot area" /></label>
								<div class="col-sm-4">
									<form:input cssClass="form-control mandColorClass text-right"
										onkeypress="return hasAmount(event, this, 15, 2)"
										id="totalplot" path="provisionalAssesmentMstDto.assPlotArea"
										placeholder="999999.99"
										onchange="getAmountFormatInDynamic((this),'totalplot')"
										data-rule-required="true"></form:input>
								</div>

							</div>

						</div>
						
						
						<div class="panel-collapse collapse in" id="BuildingDetails">
							<div class="form-group">
								<label class="col-sm-2 control-label required-control"><spring:message
										code=""  text="Revised Assesment Date" /></label>
								<div class="col-sm-4">
									<div class="input-group">
										<form:input path="provisionalAssesmentMstDto.reviseAssessmentDate"
											class="lessthancurrdate form-control mandColorClass dateClass addColor"
											id="proAssAcqDate" onChange="getFinancialYearForRevise();"
											data-rule-required="true" placeholder="DD/MM/YYYY"
											autocomplete="off" readonly="true" />
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i></span>
										<form:hidden path="leastFinYear" id="minFinancialYear" />
									</div>
								</div>

							</div>

						</div>
						
						<jsp:include page="/jsp/property/propertyBillReviseUnitDetails.jsp"></jsp:include>
						
						 <div class="form-group">
              <label class="col-sm-2 control-label required-control" for="adType">Interest Waive Off Appl </label>
              <div class="col-sm-4">
                <form:select path="interWaiveOffAppl" class="form-control " id="adType">
                  <form:option value="">Select</form:option>
                   	<form:option value="Y">Yes</form:option>
                    <form:option value="N">No</form:option>
                </form:select>
              </div>
              
            </div>
						
						
						<div class="table-responsive clear" id="PropDetails">
				<c:set var="d" value="0" scope="page" />
					<table id="datatables" class="table table-striped table-bordered">
						<thead>
							<tr>

								<th width="15%"><spring:message code="" text="Tax Name"/></th>
								
								<th width="20%"><spring:message code="Arrear Amount" text="Arrear Amount" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="reviseArrear"
								items="${command.getRevisedArrearList()}" varStatus="status">
								<tr>
									<td align="left" class="">${reviseArrear.taxDesc}</td>
									<td class="text-center"><form:input path="revisedArrearList[${d}].arrearsTaxAmt" cssClass="form-control" 
							
							id="billDistribDate${d}" /></td>
								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>
						</tbody>
					</table>

				</div>
				
				<c:if test="${command.getShowTaxDetails() eq 'Y'}">
						
						<div class="table-responsive clear" id="PropDetails">
				<c:set var="d" value="0" scope="page" />
					<table id="datatables" class="table table-striped table-bordered">
						<thead>
							<tr>
                                <th width="20%"><spring:message code="" text="Year"/></th>
								<th width="20%"><spring:message code="" text="Unit No"/></th>
								<th width="10%"><spring:message code="" text="Standard Rate" /></th>
								<th width="10%"><spring:message code="" text="ARV" /></th>
								<th width="20%"><spring:message code="" text="RV" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="propList"
								items="${command.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()}" varStatus="status">
								<tr>
		 					
		 					        <td class="text-center">${propList.proFaYearIdDesc}</td>
									<td class="text-center">${propList.assdUnitNo}</td>
									<td class="text-center">${propList.assdStdRate}</td>
									<td class="text-center">${propList.assdAlv}</td>
									<td class="text-center">${propList.assdRv}</td>
								</tr>
								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>
						</tbody>
					</table>

				</div>
				
						</c:if>
						

<%-- <div>
<button type="button" class="btn btn-success btn-submit"
								onclick="confirmToProceedToGetCharges(this)" id="proceed">
								<spring:message code="unit.proceed" />
							</button>
</div> --%>
<c:if test="${command.getShowTaxDetails() eq 'Y'}">


<!--   Tax Calculation -->
 
   <h4 class="margin-top-10 margin-bottom-10 panel-title " id="taxDetails">
   <a data-toggle="collapse" href="#TaxCalculation" class="contentRemove"><spring:message code="viewPropDetails.TaxCalculation" text="Tax Calculation"/></a>
    <a style="color: blue; text-decoration:underline;" onclick="downloadSheet()"><spring:message code="viewPropDetails.DownloadTaxDetails" text="(Download year wise tax details)"/></a>
    </h4>         
    <div class="panel-collapse collapse in" id="TaxCalculation">      
     
      <c:set var="totPayAmt" value="0"/>
            <c:forEach var="entry" items="${command.getDisplayMap()}">
            <c:set var="taxValue" value="${entry.value}"/>
            <c:set var="totArrTax" value="0"/>
            <c:set var="totCurrTax" value="0"/>
            <c:set var="totTotTax" value="0"/>
            <div class="table-responsive">
              <table class="table table-striped table-condensed table-bordered">
                <tbody>
                  <tr>
                    <th colspan="5">${entry.key}</th>
                  </tr>
                  <tr>
                    <th width="50"><spring:message code="propertyTax.SrNo"/></th>
                    <th width="400"><spring:message code="propertyTax.TaxName"/></th>
                    <th width="200" class="text-right"><spring:message code="propertyTax.Arrears"/></th>
                    <th width="200" class="text-right"><spring:message code="propertyTax.CurrentYear"/></th>
                    <th width="200" class="text-right"><spring:message code="propertyTax.Total"/></th>
                  </tr>
                   <c:choose>
  				<c:when test="${empty taxValue}">
		  				  <td>--</td>
		                  <td>--</td>
		                  <td class="text-right">--</td>
		                  <td class="text-right">--</td>
		                  <td class="text-right">--</td>
  					 </c:when>
  				<c:otherwise>
                <c:forEach var="tax" items="${taxValue}"  varStatus="status">
                  <tr>
                  <c:set var="totArrTax" value="${totArrTax+tax.getArrearsTaxAmt()}" > </c:set>
                  <c:set var="totCurrTax" value="${totCurrTax+tax.getCurrentYearTaxAmt()}" > </c:set>
                  <c:set var="totTotTax" value="${totTotTax+tax.getTotalTaxAmt()}" > </c:set>
                  <c:set var="totPayAmt" value="${totTotTax+tax.getTotalTaxAmt()}" > </c:set>
                  <td>${status.count}</td>
                  <td>${tax. getTaxDesc()}</td>
                   <td class="text-right"><fmt:formatNumber type = "number" minFractionDigits="2"  value = "${tax.getArrearsTaxAmt()}" /></td>
                   <td class="text-right"><fmt:formatNumber type = "number" minFractionDigits="2"  value = "${tax.getCurrentYearTaxAmt()}" /></td>
                   <td class="text-right"><fmt:formatNumber type = "number" minFractionDigits="2"  value = "${tax.getTotalTaxAmt()}" /></td>
                </tr>
                </c:forEach>
                </c:otherwise>
                </c:choose>
                  <tr>
                    <th colspan="2" class="text-right"><spring:message code="propertyTax.Total"/></th>
                   <%--  <th class="text-right">${totArrTax}</th>
					<th class="text-right">${totCurrTax}</th>
					<th class="text-right">${totTotTax}</th> --%>
					<th class="text-right"><fmt:formatNumber type = "number" minFractionDigits="2"  value = "${totArrTax}" /></th>
                    <th class="text-right"><fmt:formatNumber type = "number" minFractionDigits="2"  value = "${totCurrTax}" /></th>
                    <th class="text-right"><fmt:formatNumber type = "number" minFractionDigits="2"  value = "${totTotTax}" /></th>
                  </tr>
                </tbody>
              </table>
            </div>
            </c:forEach>
							<c:if test="${command.getInterWaiveOffAppl() eq 'Y'}">
								<div class="form-group">
									<label class="col-sm-10 control-label text-red">Note :
										Total interst will get waive off</label>
								</div>
							</c:if>

							<div class="table-responsive margin-top-10">
              <table class="table table-striped table-bordered">
                <tr>
                  <th width="500"><spring:message code="propertyTax.TotalTaxPayable"/></th>
                  <th width="500" class="text-right">
                  	<%-- ${command.provisionalAssesmentMstDto.billTotalAmt} --%>
                  	<fmt:formatNumber type = "number" minFractionDigits="2"  value = "${command.provisionalAssesmentMstDto.billTotalAmt}" />
                  </th>
                </tr>
              </table>
            </div>
          </div>
						<div class="form-group">
							<apptags:input labelCode="Last Receipt Amount" path="lastReceiptAmount"></apptags:input>
							
						</div>

						<div class="overflow margin-top-10" id="document">
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
																<td>Property Form</td>
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
			
			
			

<div class="text-center padding-bottom-10" id="updateButtons">
<button type="button" class="btn btn-success btn-submit" id="proceed12"
									onclick="updaeReviseBill(this)">
									<spring:message code="" text="Update"/>
								</button>
								<button type="button" class="btn btn-blue-2" id="proceed12"
									onclick="editButton(this)">
									<spring:message code="" text="Edit"/>
								</button>
								</div>

</c:if>

						<!-- Start button -->
<div class="text-center padding-bottom-10" id="fetchTaxDet">
							<button type="button" class="btn btn-success btn-submit" id="proceed1"
									onclick="confirmToProceedToGetCharges(this)">
									<spring:message code="" text="Proceed"/>
								</button>
</div>

						<!--  End button -->
							

				</form:form>
				<!-- End Form -->
			</div>
			<!-- End Widget Content here -->
		</div>
		<!-- End Widget  here -->
	</div>
	<!-- End of Content -->
</div>


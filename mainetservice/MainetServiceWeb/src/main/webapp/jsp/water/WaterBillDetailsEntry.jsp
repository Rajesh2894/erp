<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/water/waterDataEntry.js"></script>
 

<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
	<div class="widget-header">
				<h2><spring:message code="water.bill.detail.entry"/></strong></h2>				
				<div class="additional-btn">
					<apptags:helpDoc url="WaterDataEntrySuite.html"></apptags:helpDoc>
				</div>
	</div>
	<!-- End Main Page Heading -->
		
<!-- Start Widget Content -->
	<div class="widget-content padding">
		<!-- Start mand-label -->
		<div class="mand-label clearfix">
			<span><spring:message code="property.Fieldwith"/><i class="text-red-1">* </i><spring:message code="property.ismandatory"/>
			</span>
		</div>
		<!-- End mand-label -->
		
		<!-- Start Form -->
	 <form:form action="WaterDataEntrySuite.html"
					class="form-horizontal form" name="WaterDataEntrySuiteOutstanding"
					id="WaterDataEntrySuiteOutstanding">	
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
		<div class="form-group">
					<label class="col-sm-10 control-label text-red">Note: If
						you are adding arrears currently for FY 2019-20 then enter arrears
						under March month & then proceed</label>
				</div>
		<div class="form-group">
		
		 <label  class="col-sm-2  ">
			<spring:message code="property.financialyear"/>
			</label>
			<div class="col-sm-4">
				<form:select id="financialYear" path="schduleId" class="form-control mandColorClass" disabled="${command.modeType eq 'E' && not empty command.getBillMasList()}" onchange="getYear(this)">
					<form:option value="0">
						<spring:message code="property.sel.optn" text="Select" />
					</form:option>
					<c:forEach items="${command.sortedschedule}" var="billschedule">
					<form:option value="${billschedule.lookUpId}">${billschedule.descLangFirst}</form:option> 
					</c:forEach>
				</form:select>

			</div>
			<%-- <label class="col-sm-2 control-label "><spring:message code="" text="Consolidated Amount"/></label>
			<div class="col-sm-4">
		 <div class="input-group"> 
		<form:input path="billMasList[0].bmTotalArrearsWithoutInt" class="form-control" id="billDate"/>

		</div>
			</div> --%>
			</div>
		
	
		<c:choose>
		<c:when test="${not empty command.getBillMasList()}"> 
			<c:set var="d" value="0" scope="page" />
                <table id="taxdetailTable" class="table table-striped table-bordered appendableClass taxDetails">
                    <tbody>
                        <tr>
                        <th width="20%"><spring:message code="property.financialyear"/></th>
                            <th width="50%"><spring:message code="taxdetails.taxdescription"/></th>
                     	 	<th width="30%"><spring:message code="taxdetails.arrears"/></th>
                     	 	<%-- <th width="8%"><spring:message code="taxdetails.currentbill"/></th>
                     	 	<th width="8%"><spring:message code="taxdetails.total"/></th>
                     	 	<th width="8"><a href="javascript:void(0);" title="Add" class="addCF btn btn-success btn-sm unit" id="addUnitRow" ><i class="fa fa-plus-circle"></i></a></th>
                         --%>
                        </tr>
                    <c:forEach var="billMas" items="${command.getBillMasList()}" varStatus="masStatus" >              
                     <c:forEach var="billDet" items="${billMas.getTbWtBillDet()}" varStatus="detStatus" >                   
                  <tr class="firstUnitRow  ${masStatus.count%2==0? "trfirst": "trSecond"}">
                      		 <td>                                       	
                        	    <%-- <form:select path="billMasList[${masStatus.count-1}].bmYear" id="year0" class="form-control disabled text-center mandColorClass displayYearList" data-rule-required="true" disabled="true">
		  						<form:option value="0" label="Select Year"></form:option>
		  						<c:forEach items="${command.financialYearMap}" var="yearMap">
		  						<form:option value="${yearMap.key}" label="${yearMap.value}"></form:option>
		  						</c:forEach>
		 						</form:select> --%>
		 						
		 			<form:input path="" type="text" class="form-control has2Decimal mandColorClass text-center" id="year0"  readonly="true"
		 			value="${billMas.bmGenDes}" disabled="true"/>			
		 						
<%-- 		 						<form:hidden path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].faYearId" id="hiddenYear0"/>
 --%><%-- 		 					<form:hidden path="leastFinYear" id="minYear"/>	 --%>
		 						    
		 					</td>
                    <td width="150"> 
	           			 <form:select id="taxDesc" path="billMasList[${masStatus.count-1}].tbWtBillDet[${detStatus.count-1}].taxId" class="form-control mandColorClass"  disabled="true" >
							<form:option value="0">
								<spring:message code="property.sel.optn" text="Select" />
							</form:option>
							<c:forEach items="${command.taxesMaster}" var="tbTax">
							<form:option value="${tbTax.taxId}">${tbTax.taxDesc}</form:option>
							</c:forEach>
					  </form:select>
			   	</td>
							<td width="150"><form:input path="billMasList[${masStatus.count-1}].tbWtBillDet[${detStatus.count-1}].bdCsmp" type="text" class="form-control has2Decimal mandColorClass text-right" id="areear0"  /></td>
							<%-- <td width="150"><form:input path="tbBillMas.tbWtBillDet[${status.count-1}].bdCurTaxamt" type="text" class="form-control has2Decimal mandColorClass text-right" id="current0" data-rule-required="true"  placeholder="999999.99" onchange="calculate('0')"/></td>
							<td width="150"><form:input path="tbBillMas.tbWtBillDet[${status.count-1}].total"  type="text"  class="form-control has2Decimal mandColorClass text-right" id="total0"   data-rule-required="true" placeholder="999999.99" disabled="true"  /></td>
						    <td width="150" class="text-center"><a href="javascript:void(0);" class="remCF btn btn-danger btn-xs delete"  id="removeRow"><i class="fa fa-minus-circle"></i></a></td> --%>
                       	</tr> 
                       	</c:forEach>
                       	</c:forEach>
                    </tbody>
                 </table>
                 </c:when>

             </c:choose>
			
			<!-- Start button -->
			<div class="text-center padding-10">
				  
					<button type="button" class="btn btn-success btn-submit"
					 onclick="saveFrom(this)" id="nextView"><spring:message code="water.btn.submit"/></button>
					 <button type="button" class="btn btn-danger"
					 onclick="backToEntry(this)" id="backEntry"><spring:message code="water.btn.back"/></button>
				   
			</div>
			
			<!--  End button -->
			
			
		</form:form>
		<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
		</div>
		<!-- End Widget  here -->							
<!-- End of Content -->
 
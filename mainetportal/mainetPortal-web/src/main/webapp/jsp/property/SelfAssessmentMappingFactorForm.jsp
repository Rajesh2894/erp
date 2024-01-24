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
<script src="js/mainet/validation.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script src="js/property/MappingFactor.js"></script>

<link rel="stylesheet" href="assets/libs/bootstrap-multiselect/css/bootstrap-multiselect.css" type="text/css">
<script src="assets/libs/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>

<div class="accordion-toggle">
 <h4 class="margin-top-20 margin-bottom-10 panel-title">
	<a data-toggle="collapse" href="#factordetails"><spring:message code="property.mappingfactor"/></a>
 </h4>
<div class="panel-collapse collapse in" id="factordetails">
	<div id="table-responsive2">
            <table id="customFields1" class="table table-striped table-bordered">
                <tbody>
                    <tr>
                        <th>Factors</th>
                        <th>Factor Value</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th width="150">Sr No.</th>
                        <th>Add/Delete</th>
                    </tr>
                    <tr class="factorTableClass">
                        <td>
                        <c:set var="d" value="0" scope="page" />
                		<input type="hidden" id="srNoId_${d}" value="">
                        <c:set var="baseLookupCode" value="FCT" /> 
                        <form:select
								path="tableList[${d}].assfFactor" onchange="getFactorValue(${d})" class="form-control" id="factorType_${d}">
								<form:option value="">
									<spring:message code="property.sel.optn" />
								</form:option>
								<c:forEach items="${command.getLevelData(baseLookupCode)}"
									var="lookUp">
									<form:option value="${lookUp.lookUpId}"
										code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>
							</form:select></td>
                        <td><form:select id="factorValue_${d}" path="tableList[${d}].assfFactorValue"
								class="form-control">
								<form:option value="">
									<spring:message code="property.sel.optn" text="" />
								</form:option>

							</form:select></td>
							
                        <td><form:input type="text" path ="tableList[${d}].assFromDate" id="fromDate_${d}" class="form-control datepicker2" />	</td>
                        <td><form:input type="text" path ="tableList[${d}].assToDate" 	id="toDate_${d}" class="form-control datepicker2" />	</td>
                        <td>  
	                      <form:select class="srNoCss form-control" multiple="multiple" id="assfActive_${d}" path="tableList[${d}].assfActive">
	                   	  </form:select>
                                        
                     <%--    <form:select id="srId_${d}" path="" class="form-horizontal srNoCss">
								<form:option value="">
									<spring:message code="property.sel.optn" text="" />
								</form:option>

							</form:select> --%></td>
                        <td>
                        <a href="javascript:void(0);" title="Add" class="addCF2 btn btn-success btn-sm"><i class="fa fa-plus-circle"></i></a>
						<a href="javascript:void(0);" title="Delete" class="remCF2 btn btn-danger btn-sm"><i class="fa fa-trash-o"></i></a>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        
</div>
</div>	

<!-- <script>
    $(document).ready(function() {
        
    });
</script> -->
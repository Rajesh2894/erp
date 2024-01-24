<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<script>
$(document).ready(function(){
	
	$('#propertyNo_0').val('');
	$('#usage_0').val('');
	$('#unit_0').val('');
	$('#floor_0').val('');
	$('#totalArea_0').val('');
	
	
	$(".addCF2").click(function(){
		var row=0;
		var errorList = [];
		errorList = validateEstate(errorList);
		 if (errorList.length == 0) {
			           if(checkPropertyType()){
			                errorList = validateProperty(errorList);
							if (errorList.length == 0) {
							        var content = $(this).closest('tr').next().closest('tr').clone();
				    				$(this).closest("tr").after(content);
									var clickedIndex = $(this).parent().parent().index() - 1;	
									content.find("input:text").val('');
									content.find("input:hidden").val('');
									content.find('div.chosen-container').remove();
				    				content.find("select").chosen().trigger("chosen:updated");
				    				content.find("select").val('0');
				    				$('.error-div').hide();
				    				reOrderSequenceForContract();
							}else {
								displayErrorsOnPageView(errorList);
							}
			      }else{
					showAddPropertyValidation(getLocalMessage("rl.common.can.not.add.property"));
			     }	
		 }else{
			 displayErrorsOnPageView(errorList); 
		 }
	
    });

 $("#customFields2").on('click','.remCF2',function(){
		if($("#customFields2 tr").length != 2)
			{
				 $(this).parent().parent().remove();
				 reOrderSequenceForContract();
			}
	   else
			{
		   var errorList = [];
		   errorList.push(getLocalMessage("water.additnlowner.deletrw.validtn"));   
		   displayErrorsOnPageView(errorList);
		}
	 });
})

</script>
<form:form name="workFlowSubTypeForm" id="workFlowSubTypeForm">
<table class="table table-bordered table-striped" id="customFields2">
                        <tr id="ads">
                          <th width="200"><spring:message code="rl.property.label.name" text="Property Name"></spring:message></th>
                          <th><spring:message code="rnl.master.property" text="Property No."></spring:message></th>
                          <th><spring:message code="rl.property.label.Usage" text="Usage"></spring:message></th>
                          <th><spring:message code="rnl.master.unit" text="Unit"></spring:message></th>
                          <th><spring:message code="rl.property.label.Floor" text="Floor"></spring:message></th>
                          <th><spring:message code="rl.property.label.totalArea" text="Total Area (Sq.Ft)"></spring:message></th>
                          <th width="50"><a title="Include Property" href="javascript:void(0);" class="addCF2 btn btn-blue-2"><i class="fa fa-plus-circle"></i></a> </th>
                        </tr>
                        <tr class="appendableClass" >
                          <td width="200">
                          <form:select path="estateContMappingDTO.contractPropListDTO[0].propId" class="chosen-select-no-results form-control" id="propId_0" onchange="showDetails(this);">
								<form:option value="0"><spring:message code="rnl.master.select" text="select"/></form:option>
								  <c:forEach items="${command.props}" var="objArray">
                                     <form:option value="${objArray[0]}">${objArray[1]}</form:option>
                                 </c:forEach> 
						   </form:select><script>$(document).ready(function(){$('#propId_0').val('0'); $('#propId_0').chosen().trigger("chosen:updated");})</script></td>
                          <td><form:input path="estateContMappingDTO.contractPropListDTO[0].propertyNo" id="propertyNo_0" type="text" class="form-control" readonly="true"></form:input></td>
                          <td><form:input path="estateContMappingDTO.contractPropListDTO[0].usage" id="usage_0" type="text" class="form-control" readonly="true"></form:input></td>
                          <td><form:input path="estateContMappingDTO.contractPropListDTO[0].unit" id="unit_0" type="text" class="form-control" readonly="true"></form:input></td>
                          <td><form:input path="estateContMappingDTO.contractPropListDTO[0].floor" id="floor_0" type="text" class="form-control" readonly="true"></form:input></td>
                          <td><form:input path="estateContMappingDTO.contractPropListDTO[0].totalArea" id="totalArea_0" type="text" class="form-control" readonly="true"></form:input></td>
                          <td><a title="Remove Property" class="btn btn-danger remCF2" href="javascript:void(0);"><i class="fa fa-trash-o"></i></a></td>
                        </tr>
</table>
</form:form>

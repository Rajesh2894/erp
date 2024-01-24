<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.io.*,java.util.*"%>
<link href="../assets/libs/jqueryui/jquery-ui-datepicker.css"
	rel="stylesheet" type="text/css">
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="js/masters/contract/contractPartyDetails.js"></script>


<script>
var newULBWit = $('#customFields3 tr').length; 
var uploadCount=2;
var uploadtype="UW";
function addCF3Row() {
 	$("#customFields3").append('<tr class="appendableClass"> <td> <input name="contractMastDTO.contractPart1List['+newULBWit+'].contp1Name" type="text" class="form-control mandColorClass" id="contp1Name'+newULBWit+'"/> </td> '+
 	'<td> <input name="contractMastDTO.contractPart1List['+newULBWit+'].contp1Address" type="text" class="form-control mandColorClass" id="contp1Address'+newULBWit+'"/> </td> '+
 	'<td> <input name="contractMastDTO.contractPart1List['+newULBWit+'].contp1ProofIdNo" type="text" class="form-control hasNumber mandColorClass" maxlength="12"  id="contp1ProofIdNo'+newULBWit+'"/> </td>'+
 	'<input type="hidden" name="contractMastDTO.contractPart1List['+newULBWit+'].contp1Type"  id="ULBType'+newULBWit+'" value="W"> '+
 	'<input type="hidden" name="contractMastDTO.contractPart1List['+newULBWit+'].active"  id="presentRow'+newULBWit+'" value="Y"/> '+
 	'<td><a class="btn btn-blue-2 uploadbtn" data-toggle="collapse" onclick="fileUpload('+uploadCount+',\''+uploadtype+'\')"><i class="fa fa-camera"></i> Upload & View</a></td>'+
 	'<td class="text-center"><a title="Add" href="javascript:void(0);" class="addCF3 btn btn-success margin-right-5" onclick="addCF3Row();"><i class="fa fa-plus-circle"></i><span class="hide"><spring:message code="rl.property.label.add" text="Add"></spring:message></span></a><a title="Delete" class="btn btn-danger remCF3" onclick="deleteCompleteRow('+uploadCount+',\''+uploadtype+'\')" href="javascript:void(0);"><i class="fa fa-trash-o"></i><span class="hide">Delete</span></a></td> </tr>');
 	newULBWit++;
 	uploadCount++;
 	reorderULB();
}

var newVen = $('#customFields5 tr').length;
var uploadCountV=12;
var uploadtypeV="V";
var uploadCountW=112
var uploadtypeW="VW";
function addCF5Row() {
	$("#customFields5").append('<tr class="appendableVenClass">'+
	'<td><c:set var="baseLookupCode" value="VNT" /> <select name="contractMastDTO.contractPart2List['+newVen+'].contp2vType" class="form-control" id="venderType'+newVen+'" onchange="getVenderNameOnVenderType('+newVen+')"> <option value="0"> Select Vendor Type </option>'+
	'<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp"> <option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</option> </c:forEach> </select></td>'+
	'<td> <select name="contractMastDTO.contractPart2List['+newVen+'].vmVendorid" class="form-control" id="vendorId'+newVen+'"> <option value="0"> Select Vendor </option> </select> </td>'+
	'<td><input name="contractMastDTO.contractPart2List['+newVen+'].contp2Name" type="text" class="form-control mandColorClass" id="venderName'+newVen+'"/></td>'+ 
    '<td><input type="radio" name="contractMastDTO.contractPart2List['+newVen+'].contp2Primary" value="Y" onchange="OnChangePrimaryVender('+newVen+')"  class="contpPrimary mandColorClass margin-left-20 margin-top-10" id="contp2Primary'+newVen+'"/></td>'+
    '<input type="hidden" name="contractMastDTO.contractPart2List['+newVen+'].contp2Type"  id="contp2Type'+newVen+'" value="V">'+
    '<input type="hidden" name="contractMastDTO.contractPart2List['+newVen+'].active"  id="presentRow'+newVen+'" value="Y"/> '+
    '<td><a class="btn btn-blue-2 uploadbtn" data-toggle="collapse" onclick="fileUpload('+uploadCountV+',\''+uploadtypeV+'\')"><i class="fa fa-camera"></i> Upload & View</a></td>'+
	'<td class="text-center"><a title="Add" href="javascript:void(0);" id="addVendor" class="addCF5 btn btn-success margin-right-5" onclick="addCF5Row();"><i class="fa fa-plus-circle"></i><span class="hide">Add</span></a><a title="Delete" class="btn btn-danger remCF5" onclick="deleteCompleteRow('+uploadCountV+',\''+uploadtypeV+'\')" href="javascript:void(0);"><i class="fa fa-trash-o"></i><span class="hide">Delete</span></a></td> </tr>');
	newVen++;
	uploadCountV++;
	reorderVender();
}

function addCF4Row() {
   	$("#customFields4").append('<tr class="appendableWitClass"> <td>'+
	'<input name="contractMastDTO.contractPart2List['+newVen+'].contp2Name" type="text" class="form-control mandColorClass" id="contp2Name'+newVen+'"/> </td>'+
	'<td><input name="contractMastDTO.contractPart2List['+newVen+'].contp2Address" type="text" class="form-control mandColorClass" id="contp2Address'+newVen+'"/>'+
	'</td> <td> <input name="contractMastDTO.contractPart2List['+newVen+'].contp2ProofIdNo" type="text" class="form-control hasNumber mandColorClass" maxlength="12"  id="contp2ProofIdNo'+newVen+'"/></td>'+
	'<input type="hidden" name="contractMastDTO.contractPart2List['+newVen+'].contp2Type"  id="contp2Type'+newVen+'" value="W">' +
	'<input type="hidden" name="contractMastDTO.contractPart2List['+newVen+'].active"  id="presentRow'+newVen+'" value="Y"/> '+
	'<td><a class="btn btn-blue-2 uploadbtn" data-toggle="collapse" onclick="fileUpload('+uploadCountW+',\''+uploadtypeW+'\')"><i class="fa fa-camera"></i> Upload & View</a></td>'+
	'<td class="text-center"><a title="Add" href="javascript:void(0);" class="addCF4 btn btn-success margin-right-5" onclick="addCF4Row();"><i class="fa fa-plus-circle"></i><span class="hide">Add</span></a><a title="Delete" class="btn btn-danger remCF4" onclick="deleteCompleteRow('+uploadCountW+',\''+uploadtypeW+'\')" href="javascript:void(0);"><i class="fa fa-trash-o"></i><span class="hide">Delete</span></a></td> </tr>');
	newVen++;
   	uploadCountW++;
   	reorderVender();
}

$(document).ready(function(){	
    $("#customFields3").on('click','.remCF3',function(){
		{
		if($("#customFields3 tr").length != 2)
			{
				 $(this).parent().parent().remove();
				 reorderULB();
				 newULBWit--; 
				 uploadCount--;
			}
	   else
			{
				alert("You cannot delete first row");
			}
		}
	});
    
    $("#customFields5").on('click','.remCF5',function(){
    	{
    	if($("#customFields5 tr").length != 2)
    		{
    			 $(this).parent().parent().remove();
    			 reorderVender();
    			 newVen--;
    			 uploadCountV--;
    		}
       else
    		{
    			alert("You cannot delete first row");
    		}
    	}
    });	
    		
    $("#customFields4").on('click','.remCF4',function(){
    		{
    		if($("#customFields4 tr").length != 2)
    			{
    				 $(this).parent().parent().remove();
    				 reorderVender();	
    				 newVen--;
    				 uploadCountW--;
    				 }
    	   else
    			{
    				alert("You cannot delete first row");
    			}
    		}
     });
});

</script>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="agreement.partyDetail" text="Party Details" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="ContractAgreement.html" class="form-horizontal"
				name="partyDetails" id="partyDetails">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<form:hidden path="modeType" id="hiddeMode" />
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="agreement.ULBParty" text="ULB Party1" /></a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">

								<c:choose>
									<c:when
										test="${empty command.getContractMastDTO().getContractPart1List()}">
										<div class="table-overflow">

											<table class="table table-bordered table-striped"
												id="customFields1">

												<tr>
													<th width="400"><spring:message
															code="contract.label.department" /></th>
													<th width="300"><spring:message
															code="contract.label.designation" /></th>
													<th width="300"><spring:message
															code="contract.label.representedBy" /></th>
													<th width="100"><spring:message
															code="contract.label.photoThumb" /></th>
													<!--            <th width="50"><a title="Add" href="javascript:void(0);" class="addCF1 btn btn-success"><i class="fa fa-plus-circle"></i><span class="hide">Add</span></a></th>
 -->
												</tr>
												<tr>
													<td><form:select
															path="contractMastDTO.contractPart1List[0].dpDeptid"
															class="form-control chosen-select-no-results" id="deptId"
															onchange="getDesgBasedOnDept(this)">
															<form:option value="0">
																<spring:message code="common.master.select.dept"
																	text="select Department"></spring:message>
															</form:option>
															<c:forEach items="${command.getMapDeptList()}"
																var="lookUp">
																<form:option value="${lookUp.dpDeptid}"
																	code="${lookUp.dpDeptcode}">${lookUp.dpDeptdesc}</form:option>
															</c:forEach>
														</form:select></td>
													<td><form:select
															path="contractMastDTO.contractPart1List[0].dsgid"
															class="form-control chosen-select-no-results" id="desigantionId"
															onchange="getEmpBasedOnDesgnation(this)">
															<form:option value="0">
																<spring:message
																	code="contract.master.select.designation"
																	text="select Designation"></spring:message>
															</form:option>
															<%-- <c:forEach items="${command.getMapDesignationList()}"
																var="lookUp">
																<form:option value="${lookUp.dsgid}"
																	code="${lookUp.desgName}">${lookUp.desgName}</form:option>
															</c:forEach> --%>
														</form:select></td>
													<td><form:select
															path="contractMastDTO.contractPart1List[0].empid"
															class="form-control chosen-select-no-results" id="representBy">
															<form:option value="0">
																<spring:message code="contract.master.select.employee"
																	text="select Employee"></spring:message>
															</form:option>
														</form:select></td>
													<form:hidden
														path="contractMastDTO.contractPart1List[0].contp1Name"
														id="empName" />
													<form:hidden
														path="contractMastDTO.contractPart1List[0].contp1Type"
														id="ULBType" value="U" />
													<form:hidden
														path="contractMastDTO.contractPart1List[0].active"
														id="presentRow1" value="Y" />
													<td><a class="btn btn-blue-2 uploadbtn"
														data-toggle="+" onclick="fileUpload(0,'U')"><i
															class="fa fa-camera"></i> <spring:message
																code="contract.label.uploadView" /></a></td>
													<!-- 		<td><a title="Delete" class="btn btn-danger remCF3" href="javascript:void(0);"><i class="fa fa-trash-o"></i><span class="hide">Delete</span></a></td>
 -->
												</tr>
											</table>
										</div>

										<h4>
											<spring:message code="contract.label.witness" />
										</h4>
										<div class="table-overflow">
											<table class="table table-bordered table-striped"
												id="customFields3">
												<tr>
													<th width="400"><spring:message
															code="contract.label.name" /></th>
													<th width="400"><spring:message
															code="contract.label.address" /></th>
													<th width="200"><spring:message
															code="contract.label.aadhaarNo" /></th>
													<th width="100"><spring:message
															code="contract.label.photoThumb" /></th>
													<th width="8%"><spring:message
															code="common.master.complaintType.action" text="Action" /></th>
												</tr>
												<tr class="appendableClass">
													<td><form:input
															path="contractMastDTO.contractPart1List[1].contp1Name"
															type="text" class="form-control mandColorClass"
															id="contp1Name1" /></td>
													<td><form:input
															path="contractMastDTO.contractPart1List[1].contp1Address"
															type="text" class="form-control mandColorClass"
															id="contp1Address1" /></td>
													<td><form:input
															path="contractMastDTO.contractPart1List[1].contp1ProofIdNo"
															type="text" class="form-control hasNumber mandColorClass"
															maxlength="12" data-mask="9999 9999 9999"
															id="contp1ProofIdNo1" /></td>
													<form:hidden
														path="contractMastDTO.contractPart1List[1].contp1Type"
														id="ULBType1" value="W" />
													<form:hidden
														path="contractMastDTO.contractPart1List[1].active"
														id="presentRow1" value="Y" />

													<td><a class="btn btn-blue-2 uploadbtn"
														data-toggle="collapse" onclick="fileUpload(1,'UW')"><i
															class="fa fa-camera"></i> <spring:message
																code="contract.label.uploadView" /></a></td>
													<td class="text-center">
														<a title="Add"
														href="javascript:void(0);" class="addCF3 btn btn-success" onclick="addCF3Row();"><i
															class="fa fa-plus-circle"></i><span class="hide"><spring:message
																	code="rl.property.label.add" text="Add"></spring:message></span></a>
														<a title="Delete" class="btn btn-danger remCF3"
														onclick="deleteCompleteRow(1,'UW')"
														href="javascript:void(0);"><i class="fa fa-trash-o"></i><span
															class="hide"><spring:message
																	code="contract.label.delete" /></span></a>
													</td>
												</tr>

											</table>
										</div>
									</c:when>
									<c:otherwise>

										<div class="table-overflow">
											<table class="table table-bordered table-striped"
												id="customFields1">
												<tr>
													<th width="400"><spring:message
															code="contract.label.department" /></th>
													<th width="300"><spring:message
															code="contract.label.designation" /></th>
													<th width="300"><spring:message
															code="contract.label.representedBy" /></th>
													<th width="100"><spring:message
															code="contract.label.photoThumb" /></th>
													<!--                           <th width="50"><a title="Add" href="javascript:void(0);" class="addCF1 btn btn-success"><i class="fa fa-plus-circle"></i><span class="hide">Add</span></a></th>
 -->
												</tr>


												<%--     <c:forEach items="${command.getContractMastDTO().getContractPart1List()}" var="details" varStatus="status">         
                         <c:if test="${details.getContp1Type() eq 'U'}">  --%>
												<tr>
													<td><form:select
															path="contractMastDTO.contractPart1List[0].dpDeptid"
															class="form-control chosen-select-no-results" id="deptId"
															onchange="getDesgBasedOnDept(this)" readonly="">
															<form:option value="0">
																<spring:message code="common.master.select.dept"
																	text="select Department"></spring:message>
															</form:option>
															<c:forEach items="${command.getMapDeptList()}"
																var="lookUp">
																<form:option value="${lookUp.dpDeptid}"
																	code="${lookUp.dpDeptcode}">${lookUp.dpDeptdesc}</form:option>
															</c:forEach>
														</form:select></td>
													<td><form:select
															path="contractMastDTO.contractPart1List[0].dsgid"
															class="form-control chosen-select-no-results" id="desigantionId"
															onchange="getEmpBasedOnDesgnation(this)">
															<form:option value="0">
																<spring:message
																	code="contract.master.select.designation"
																	text="select Designation"></spring:message>
															</form:option>
															<%-- <c:forEach items="${command.getMapDesignationList()}"
																var="lookUp">
																<form:option value="${lookUp.dsgid}"
																	code="${lookUp.desgName}">${lookUp.desgName}</form:option>
															</c:forEach> --%>
															<c:forEach
																	items="${command.contractMastDTO.contractPart1List[0].getDesgList()}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}">${lookUp.descLangFirst}</form:option>
															</c:forEach>
														</form:select></td>
													<td><c:if test="${command.modeType eq 'V'}">
															<form:input
																path="contractMastDTO.contractPart1List[0].contp1Name"
																class="form-control" id="representBy" />
														</c:if> <c:if test="${command.modeType ne 'V'}">
															<form:select
																path="contractMastDTO.contractPart1List[0].empid"
																class="form-control chosen-select-no-results" id="representBy">
																<form:option value="0">
																	<spring:message code="contract.master.select.employee"
																		text="select Employee"></spring:message>
																</form:option>
																<%-- 								   <c:set var="count" value="${status.count-1}" > </c:set>
 --%>
																<c:forEach
																	items="${command.contractMastDTO.contractPart1List[0].getContp1NameList()}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}">${lookUp.descLangFirst}</form:option>
																</c:forEach>
															</form:select>
														</c:if></td>
													<form:hidden
														path="contractMastDTO.contractPart1List[0].contp1Name"
														id="empName" />
													<form:hidden
														path="contractMastDTO.contractPart1List[0].contp1Type"
														id="ULBType" value="U" />
													<form:hidden
														path="contractMastDTO.contractPart1List[0].active"
														id="presentRow1" value="Y" />

													<td><a class="btn btn-blue-2 uploadbtn"
														data-toggle="collapse" onclick="fileUpload(0,'U')"><i
															class="fa fa-camera"></i> <spring:message
																code="contract.label.uploadView" /></a></td>
												</tr>
											</table>
										</div>
										<h4>
											<spring:message code="contract.label.witness" />
										</h4>
										<div class="table-overflow">
											<table class="table table-bordered table-striped"
												id="customFields3">
												<tr>
													<th width="400"><spring:message
															code="contract.label.name" /></th>
													<th width="400"><spring:message
															code="contract.label.address" /></th>
													<th width="200"><spring:message
															code="contract.label.aadhaarNo" /></th>
													<th width="100"><spring:message
															code="contract.label.photoThumb" /></th>
													<th width="8%"><spring:message
															code="common.master.complaintType.action" text="Action" /></th>
												</tr>
												<c:set var="p1" value="1">
												</c:set>
												<c:forEach
													items="${command.getContractMastDTO().getContractPart1List()}"
													var="details" varStatus="status">
													<c:if test="${details.getContp1Type() eq 'W'}">

														<tr class="appendableClass">
															<td><form:input
																	path="contractMastDTO.contractPart1List[${status.count-1}].contp1Name"
																	type="text" class="form-control mandColorClass"
																	id="contp1Name${status.count-1}" /></td>
															<td><form:input
																	path="contractMastDTO.contractPart1List[${status.count-1}].contp1Address"
																	type="text" class="form-control mandColorClass"
																	id="contp1Address${status.count-1}" /></td>
															<td><form:input
																	path="contractMastDTO.contractPart1List[${status.count-1}].contp1ProofIdNo"
																	type="text"
																	class="form-control hasNumber mandColorClass"
																	maxlength="12" data-mask="9999 9999 9999"
																	id="contp1ProofIdNo${status.count-1}" /></td>

															<form:hidden
																path="contractMastDTO.contractPart1List[${status.count-1}].contp1Type"
																id="ULBType${status.count-1}" value="W" />
															<form:hidden
																path="contractMastDTO.contractPart1List[${status.count-1}].active"
																id="presentRow${status.count-1}" value="Y" />
															<c:if test="${command.modeType eq 'V'}">
																<td><a class="btn btn-blue-2 uploadbtn"
																	data-toggle="collapse"
																	onclick="fileUpload(${status.count-1},'UW')"><i
																		class="fa fa-camera"></i> <spring:message
																			code="contract.label.uploadView" /></a></td>
															</c:if>
															<c:if test="${command.modeType eq 'E'}">
																<td><a class="btn btn-blue-2 uploadbtn"
																	data-toggle="collapse" onclick="fileUpload(${p1},'UW')"><i
																		class="fa fa-camera"></i> <spring:message
																			code="contract.label.uploadView" /></a></td>

															</c:if>

															<td class="text-center">
																<a title="Add"
																	href="javascript:void(0);" class="addCF3 btn btn-success" onclick="addCF3Row();"><i
																	class="fa fa-plus-circle"></i><span class="hide"><spring:message
																	code="rl.property.label.add" text="Add"></spring:message></span></a>
																<a title="Delete" class="btn btn-danger remCF3"
																onclick="deleteCompleteRow(${p1},'UW')"
																href="javascript:void(0);"><i class="fa fa-trash-o"></i><span
																	class="hide"><spring:message
																			code="contract.label.delete" /></span></a>
															</td>
															<c:set var="p1" value="${p1+1}">
															</c:set>
														</tr>
													</c:if>
												</c:forEach>
											</table>
										</div>
									</c:otherwise>
								</c:choose>

							</div>
						</div>
					</div>


					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a2"><spring:message
										code="contract.label.partyVendor" text="" /></a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:choose>
									<c:when
										test="${empty command.getContractMastDTO().getContractPart2List()}">

										<%-- <form:hidden path="" class="form-control" value="${command.contractMastDTO.contractPart2List[0].vmVendorid}"
								id="contractVenderId"/> --%>

										<div class="table-overflow-sm">
											<table class="table table-bordered table-striped"
												id="customFields5">
												<tr>
													<th width="400"><spring:message
															code="contract.label.vendorType" /><span class="mand">*</span></th>
													<th width="300"><spring:message
															code="contract.label.vendorName" /><span class="mand">*</span></th>
													<th width="300"><spring:message
															code="contract.label.representedBy" /></th>
													<th width="50"><spring:message
															code="contract.label.primaryVender" /><span class="mand">*</span></th>
													<th width="100"><spring:message
															code="contract.label.photoThumb" /></th>
													<th width="8%"><spring:message
															code="common.master.complaintType.action" text="Action" /></th>
												</tr>
												<tr class="appendableVenClass">
													<td><c:set var="baseLookupCode" value="VNT" /> <form:select
															path="contractMastDTO.contractPart2List[0].contp2vType"
															class="form-control chosen-select-no-results" id="venderType0"
															onchange="getVenderNameOnVenderType(0)">
															<form:option value="0">
																<spring:message
																	code="contract.master.select.vendor.type"
																	text="Select Vendor Type"></spring:message>
															</form:option>
															<c:forEach
																items="${command.getLevelData(baseLookupCode)}"
																var="lookUp">
																<form:option value="${lookUp.lookUpId}"
																	code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
															</c:forEach>
														</form:select></td>
													<td><form:select
															path="contractMastDTO.contractPart2List[0].vmVendorid"
															class="form-control chosen-select-no-results" id="vendorId0">
															<form:option value="0">
																<spring:message code="contract.master.select.vendor"
																	text="Select Vendor"></spring:message>
															</form:option>

														</form:select></td>
													<td><form:input
															path="contractMastDTO.contractPart2List[0].contp2Name"
															type="text" class="form-control mandColorClass"
															id="venderName0" /></td>
													<td><form:radiobutton
															path="contractMastDTO.contractPart2List[0].contp2Primary"
															value="Y" name=""
															class="contpPrimary mandColorClass margin-left-20 margin-top-10"
															onchange="OnChangePrimaryVender(0)" id="contp2Primary0" /></td>
													<form:hidden
														path="contractMastDTO.contractPart2List[0].contp2Type"
														id="contp2Type0" value="V" />
													<form:hidden
														path="contractMastDTO.contractPart2List[0].active"
														id="presentRow0" value="Y" />

													<td><a class="btn btn-blue-2 uploadbtn"
														data-toggle="collapse" onclick="fileUpload(11,'V')"><i
															class="fa fa-camera"></i> <spring:message
																code="contract.label.uploadView" /></a></td>
													<td class="text-center">
														<a title="Add"
														href="javascript:void(0);" id="addVendor"
														class="addCF5 btn btn-success" onclick="addCF5Row();"><i
															class="fa fa-plus-circle"></i><span class="hide">Add</span></a>
														<a title="Delete" class="btn btn-danger remCF5"
														onclick="deleteCompleteRow(11,'V')"
														href="javascript:void(0);"><i class="fa fa-trash-o"></i><span
															class="hide"><spring:message
																	code="contract.label.delete" /></span></a></td>
												</tr>
											</table>
										</div>
										<h4>
											<spring:message code="contract.label.witness" />
										</h4>
										<div class="table-overflow-sm">
											<table class="table table-bordered table-striped"
												id="customFields4">
												<tr>
													<th width="400"><spring:message
															code="contract.label.name" /><span class="mand"></span></th>
													<th width="400"><spring:message
															code="contract.label.address" /><span class="mand"></span></th>
													<th width="200"><spring:message
															code="contract.label.aadhaarNo" /><span class="mand"></span></th>
													<th width="100"><spring:message
															code="contract.label.photoThumb" /></th>
													<th width="8%"><spring:message
															code="common.master.complaintType.action" text="Action" /></th>
												</tr>
												<tr class="appendableWitClass">
													<td><form:input
															path="contractMastDTO.contractPart2List[1].contp2Name"
															type="text" class="form-control mandColorClass"
															id="contp2Name1" /></td>
													<td><form:input
															path="contractMastDTO.contractPart2List[1].contp2Address"
															type="text" class="form-control mandColorClass"
															id="contp2Address1" /></td>
													<td><form:input
															path="contractMastDTO.contractPart2List[1].contp2ProofIdNo"
															type="text" class="form-control hasNumber mandColorClass"
															maxlength="12" data-mask="9999 9999 9999"
															id="contp2ProofIdNo1" /></td>
													<form:hidden
														path="contractMastDTO.contractPart2List[1].contp2Type"
														value="W" id="contp2Type" />
													<form:hidden
														path="contractMastDTO.contractPart2List[1].active"
														id="presentRow1" value="Y" />
													<td><a class="btn btn-blue-2 uploadbtn"
														data-toggle="collapse" onclick="fileUpload(111,'VW')"><i
															class="fa fa-camera"></i> <spring:message
																code="contract.label.uploadView" /></a></td>
													<td class="text-center">
														<a title="Add"
														href="javascript:void(0);" class="addCF4 btn btn-success" onclick="addCF4Row();"><i
															class="fa fa-plus-circle"></i><span class="hide">Add</span></a>
														<a title="Delete" class="btn btn-danger remCF4"
														onclick="deleteCompleteRow(111,'VW')"
														href="javascript:void(0);"><i class="fa fa-trash-o"></i><span
															class="hide"><spring:message
																	code="contract.label.delete" /></span></a></td>
												</tr>
											</table>
										</div>
									</c:when>
									<c:otherwise>

										<div class="table-overflow-sm">
											<table class="table table-bordered table-striped"
												id="customFields5">
												<tr>
													<th width="400"><spring:message
															code="contract.label.vendorType" /><span class="mand">*</span></th>
													<th width="300"><spring:message
															code="contract.label.vendorName" /><span class="mand">*</span></th>
													<th width="300"><spring:message
															code="contract.label.representedBy" /></th>
													<th width="50"><spring:message
															code="contract.label.primaryVender" /><span class="mand">*</span></th>
													<th width="100"><spring:message
															code="contract.label.photoThumb" /></th>
													<th width="8%"><spring:message
															code="common.master.complaintType.action" text="Action" /></th>
												</tr>
												<c:set var="p2" value="11">
												</c:set>
												<c:forEach
													items="${command.getContractMastDTO().getContractPart2List()}"
													var="details" varStatus="status">

													<c:if test="${details.getContp2Type() eq 'V'}">
														<tr class="appendableVenClass">

															<td><c:set var="baseLookupCode" value="VNT" /> <form:select
																	path="contractMastDTO.contractPart2List[${status.count-1}].contp2vType"
																	class="form-control chosen-select-no-results" id="venderType${status.count-1}"
																	onchange="getVenderNameOnVenderType(${status.count-1})"
																	readonly=""
																	disabled="${command.modeType ne 'E' && command.contracterNameFlag eq 'Y'}">
																	<form:option value="0">
																		<spring:message
																			code="contract.master.select.vendor.type"
																			text="Select Vendor Type"></spring:message>
																	</form:option>
																	<c:forEach
																		items="${command.getLevelData(baseLookupCode)}"
																		var="lookUp">
																		<form:option value="${lookUp.lookUpId}"
																			code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>
																</form:select></td>
															<td><c:if test="${command.modeType eq 'V'}">
																	<form:input
																		path="contractMastDTO.contractPart2List[${status.count-1}].venderName"
																		class="form-control" id="vendorId${status.count-1}" />
																</c:if> <c:if test="${command.modeType ne 'V'}">
																	<form:select
																		path="contractMastDTO.contractPart2List[${status.count-1}].vmVendorid"
																		class="form-control chosen-select-no-results" id="vendorId${status.count-1}"
																		readonly=""
																		disabled="${command.modeType ne 'E' && command.contracterNameFlag eq 'Y'}">
																		<c:set var="count" value="${status.count-1}">
																		</c:set>
																		<form:option value="0">
																			<spring:message code="contract.master.select.vendor"></spring:message>
																		</form:option>
																		<c:forEach
																			items="${command.contractMastDTO.contractPart2List[count].getVmVendoridList()}"
																			var="lookUp">
																			<form:option value="${lookUp.lookUpId}">${lookUp.descLangFirst}</form:option>
																		</c:forEach>
																	</form:select>
																</c:if></td>
															<td><form:input
																	path="contractMastDTO.contractPart2List[${status.count-1}].contp2Name"
																	type="text" class="form-control mandColorClass"
																	id="venderName${status.count-1}" /></td>
															<td><form:radiobutton
																	path="contractMastDTO.contractPart2List[${status.count-1}].contp2Primary"
																	value="Y" name=""
																	onchange="OnChangePrimaryVender(${status.count-1})"
																	class="contpPrimary mandColorClass margin-left-20 margin-top-10"
																	id="contp2Primary${status.count-1}" /></td>

															<form:hidden
																path="contractMastDTO.contractPart2List[${status.count-1}].contp2Type"
																id="contp2Type${status.count-1}" value="V" />
															<form:hidden
																path="contractMastDTO.contractPart2List[${status.count-1}].active"
																id="presentRow${status.count-1}" value="Y" />
															<c:if test="${command.modeType eq 'V'}">
																<td><a class="btn btn-blue-2 uploadbtn"
																	data-toggle="collapse"
																	onclick="fileUpload(${status.count-1},'V')"><i
																		class="fa fa-camera"></i> <spring:message
																			code="contract.label.uploadView" /></a></td>
															</c:if>
															<c:if test="${command.modeType ne 'V'}">
																<td><a class="btn btn-blue-2 uploadbtn"
																	data-toggle="collapse" onclick="fileUpload(${p2},'V')"><i
																		class="fa fa-camera"></i> <spring:message
																			code="contract.label.uploadView" /></a></td>
															</c:if>
															<td class="text-center">
																<a title="Add"
																href="javascript:void(0);" id="addVendor"
																class="addCF5 btn btn-success" onclick="addCF5Row();"><i
																	class="fa fa-plus-circle"></i><span class="hide"><spring:message
																			code="rl.property.label.add" text="Add"></spring:message></span></a>
																<a title="Delete" class="btn btn-danger remCF5"
																onclick="deleteCompleteRow(${p2},'V')"
																href="javascript:void(0);"><i class="fa fa-trash-o"></i><span
																	class="hide"><spring:message
																			code="contract.label.delete" /></span></a></td>
															<c:set var="p2" value="${p2+1}">
															</c:set>
														</tr>
													</c:if>
												</c:forEach>
											</table>
										</div>
										<h4>
											<spring:message code="contract.label.witness" />
										</h4>
										<div class="table-overflow-sm">
											<table class="table table-bordered table-striped"
												id="customFields4">
												<tr>
													<th width="400"><spring:message
															code="contract.label.name" /><span class="mand"></span></th>
													<th width="400"><spring:message
															code="contract.label.address" /><span class="mand"></span></th>
													<th width="200"><spring:message
															code="contract.label.aadhaarNo" /><span class="mand"></span></th>
													<th width="100"><spring:message
															code="contract.label.photoThumb" /></th>
													<th width="8%"><spring:message
															code="common.master.complaintType.action" text="Action" /></th>
												</tr>
												<c:set var="p2W" value="111">
												</c:set>
												<c:forEach
													items="${command.getContractMastDTO().getContractPart2List()}"
													var="details" varStatus="status">
													<%-- 		<c:if test="${details.getContp2Type() eq 'W'}">

														<tr class="appendableWitClass">
															<td><form:input
																	path="contractMastDTO.contractPart2List[${status.count-1}].contp2Name"
																	type="text" class="form-control mandColorClass"
																	id="contp2Name${status.count-1}" /></td>
															<td><form:input
																	path="contractMastDTO.contractPart2List[${status.count-1}].contp2Address"
																	type="text" class="form-control mandColorClass"
																	id="contp2Address${status.count-1}" /></td>
															<td><form:input
																	path="contractMastDTO.contractPart2List[${status.count-1}].contp2ProofIdNo"
																	type="text"
																	class="form-control hasNumber mandColorClass"
																	maxlength="12" data-mask="9999 9999 9999"
																	id="contp2ProofIdNo${status.count-1}" /></td>
															<form:hidden
																path="contractMastDTO.contractPart2List[${status.count-1}].contp2Type"
																value="W" id="contp2Type${status.count-1}" />
															<form:hidden
																path="contractMastDTO.contractPart2List[${status.count-1}].active"
																id="presentRow${status.count-1}" value="Y" />
															<c:if test="${command.modeType eq 'V'}">
																<td><a class="btn btn-blue-2 uploadbtn"
																	data-toggle="collapse"
																	onclick="fileUpload(${status.count-1},'VW')"><i
																		class="fa fa-camera"></i> <spring:message
																			code="contract.label.uploadView" /></a></td>
															</c:if>
															<c:if test="${command.modeType eq 'E'}">
																<td><a class="btn btn-blue-2 uploadbtn"
																	data-toggle="collapse"
																	onclick="fileUpload(${p2W},'VW')"><i
																		class="fa fa-camera"></i> <spring:message
																			code="contract.label.uploadView" /></a></td>
															</c:if>
															<td class="text-center">
																<a title="Add"
																href="javascript:void(0);" class="addCF4 btn btn-success" onclick="addCF4Row();"><i
																class="fa fa-plus-circle"></i><span class="hide">Add</span></a>
																<a title="Delete" class="btn btn-danger remCF4"
																onclick="deleteCompleteRow(${p2W},'VW')"
																href="javascript:void(0);"><i class="fa fa-trash-o"></i><span
																	class="hide"><spring:message
																			code="contract.label.delete" /></span></a></td>
															<c:set var="p2W" value="${p2W+1}">
															</c:set>
														</tr>

													</c:if> --%>


													<c:if
														test="${details.getContp2Type() eq 'W'|| details.getContp2Type() eq 'V'}">

														<tr class="appendableWitClass">
															<td><form:input
																	path="contractMastDTO.contractPart2List[${status.count-1}].contp2Name"
																	type="text" class="form-control mandColorClass"
																	id="contp2Name${status.count-1}" /></td>
															<td><form:input
																	path="contractMastDTO.contractPart2List[${status.count-1}].contp2Address"
																	type="text" class="form-control mandColorClass"
																	id="contp2Address${status.count-1}" /></td>
															<td><form:input
																	path="contractMastDTO.contractPart2List[${status.count-1}].contp2ProofIdNo"
																	type="text"
																	class="form-control hasNumber mandColorClass"
																	maxlength="12" data-mask="9999 9999 9999"
																	id="contp2ProofIdNo${status.count-1}" /></td>
															<c:choose>
																<c:when test="${details.getContp2Type() eq 'W'}">
																	<form:hidden
																		path="contractMastDTO.contractPart2List[${status.count-1}].contp2Type"
																		value="W" id="contp2Type${status.count-1}" />
																</c:when>
																<c:otherwise>
																	<form:hidden
																		path="contractMastDTO.contractPart2List[${status.count-1}].contp2Type"
																		value="V" id="contp2Type${status.count-1}" />
																</c:otherwise>
															</c:choose>

															<form:hidden
																path="contractMastDTO.contractPart2List[${status.count-1}].active"
																id="presentRow${status.count-1}" value="Y" />

															<c:if test="${command.modeType eq 'V'}">
																<td><a class="btn btn-blue-2 uploadbtn"
																	data-toggle="collapse"
																	onclick="fileUpload(${status.count-1},'V')"><i
																		class="fa fa-camera"></i> <spring:message
																			code="contract.label.uploadView" /></a></td>
															</c:if>

															<td><a class="btn btn-blue-2 uploadbtn"
																data-toggle="collapse" onclick="fileUpload(${p2},'V')"><i
																	class="fa fa-camera"></i> <spring:message
																		code="contract.label.uploadView" /></a></td>


															<td class="text-center"><a title="Add"
																href="javascript:void(0);"
																class="addCF4 btn btn-success" onclick="addCF4Row();"><i
																	class="fa fa-plus-circle"></i><span class="hide">Add</span></a>
																<a title="Delete" class="btn btn-danger remCF4"
																onclick="deleteCompleteRow(${p2W},'VW')"
																href="javascript:void(0);"><i class="fa fa-trash-o"></i><span
																	class="hide"><spring:message
																			code="contract.label.delete" /></span></a></td>
															<c:set var="p2W" value="${p2W+1}">
															</c:set>
														</tr>
													</c:if>

												</c:forEach>

											</table>
										</div>

									</c:otherwise>
								</c:choose>

							</div>
						</div>
					</div>





					<!-- </div> -->
					<!-- Start button -->
					<c:if test="${command.contMapFlag ne 'B' }">
						<div class="text-center">
							<c:if test="${command.modeType ne 'V'}">
								<button type="button" class="btn btn-success btn-submit"
									onclick="saveContractAgreementForm(this)" id="submit">
									<spring:message code="contract.label.submit" />
								</button>
								<c:if test="${command.modeType eq 'C'}">
									<button type="reset" id="resetButton" class="btn btn-warning">
										<spring:message code="contract.label.Reset" />
									</button>
								</c:if>
							</c:if>

							<button type="button" onclick="BackToContractDetails();"
								id="backButton" class="btn btn-blue-2 backButton">
								<spring:message code="agreement.backToContract"
									text="Back To Contract Details" />
							</button>
							<button type="button" onclick="back('${command.showForm}')"
								id="backButton" class="btn btn-danger backButton">
								<spring:message code="contract.label.Back" />
							</button>
						</div>
					</c:if>
					<c:if test="${command.contMapFlag eq 'B' }">
						<div class="text-center">
							<button type="button" onclick="saveContractAgreementForm(this)"
								id="backButton" class="btn btn-success backButton">
								<spring:message code="contract.label.submit" />
							</button>
						
							<button type="button" onclick="back('${command.showForm}')"
								id="backButton" class="btn btn-danger backButton">
								<spring:message code="contract.label.Back" />
							</button>
						</div>
					</c:if>
			</form:form>
		</div>
	</div>
</div>


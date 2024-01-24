<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.io.*,java.util.*" %>
<link href="../assets/libs/jqueryui/jquery-ui-datepicker.css" rel="stylesheet" type="text/css">
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="js/rnl/master/contractAgreement.js"></script>
<script>
$(document).ready(function () {
    $('#submitInstall').click(function () {
    	checkInstallDate();
    	if(checkInstallDate()) {
	    	var total = $("#id_noa").val();
	 		if(total!="" && total!='0' )
	    	{
		 		var i = 1;
		        var path = 0;
		        $("#noOfInstallmentTable").find("tr:gt(0)").remove();
		 		$('#noa_header').show();
		         while (i <= total) {
		            $('.tab_Application').append('<tr class="appendableClassInstallments"><td><input type="text" class="form-control" disabled size="5" value="'+i+'"/></td>'+
		            '<td><select name="contractMastDTO.contractInstalmentDetailList['+path+'].conitAmtType" class="form-control mandColorClass" id="PaymentTerms'+path+'">'+ 	
		            '<c:set var="baseLookupCode" value="VTY" /> <option value="0"> Select </option> <c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">'+
					'<option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</option> </c:forEach></td>'+
					'<input type="hidden" name="contractMastDTO.contractInstalmentDetailList['+path+'].active"  id="presentRow'+path+'" value="Y"/> '+
					'<td><input type="text" name="contractMastDTO.contractInstalmentDetailList['+path+'].conitValue" onkeypress="return hasAmount(event, this, 13, 2)"  class="form-control text-right" name="amt" id="amt'+path+'" /></td>'+
					'<td><div class="input-group"><input type="text" id="installmentsDate'+path+'"  name="contractMastDTO.contractInstalmentDetailList['+path+'].conitDueDate" class="form-control dateClass mandColorClass Insdatepicker"><span class="input-group-addon"><i class="fa fa-calendar"></i></span></div></td>'+
					'<td><input type="text" size="5" name="contractMastDTO.contractInstalmentDetailList['+path+'].conitMilestone" id="mileStone'+path+'" class="form-control" /></td></tr>');
		            i = i + 1;
		            path++;  
	        }
	        while (i-1 > total) {
	            $(".tab_Application tr:last").remove();
	            i=i-1;
	        }
	        $('</table>').appendTo('.tab_Application');
	    }else 
	 		{
	 			alert("please Enter some valid value");
	 		}
    	 }
    });
    
	var newTermCon= $('#customFields2 tr').length; 
	   var count=2;
		$(".addCF2").click(function(){
		$("#customFields2").append('<tr class="appendableTermConClass"> <td width="50"><input type="text" class="form-control text-center hasNumber" id="sNo'+newTermCon+'" value="'+count+'" /></td>'+
	    '<td> <textarea name="contractMastDTO.contractTermsDetailList['+newTermCon+'].conttDescription" cols="" rows="" class="form-control mandColorClass" id="termCon'+newTermCon+'"></textarea> </td>'+
	    '<input type="hidden" name="contractMastDTO.contractTermsDetailList['+newTermCon+'].active"  id="presentRow'+newTermCon+'" value="Y"/> '+
	    '<td><a title="Delete" class="btn btn-danger remCF2" href="javascript:void(0);"><i class="fa fa-trash-o"></i><span class="hide">Delete</span></a></td> </tr>');
	count++;
	newTermCon++;
	reorderTermCon();
		});
	    $("#customFields2").on('click','.remCF2',function(){
			{
			if($("#customFields2 tr").length != 2)
				{
					 $(this).parent().parent().remove();
					 reorderTermCon();
					 newTermCon--;
				}
		   else
				{
					alert("You cannot delete first row");
				}
			}
	 });	
    
    
});


$(document).ready(function(){	
	var newULBWit = $('#customFields3 tr').length; 
	var uploadCount=2;
	var uploadtype="UW";
	$(".addCF3").click(function(){

 	$("#customFields3").append('<tr class="appendableClass"> <td> <input name="contractMastDTO.contractPart1List['+newULBWit+'].contp1Name" type="text" class="form-control mandColorClass" id="contp1Name'+newULBWit+'"/> </td> '+
 	'<td> <input name="contractMastDTO.contractPart1List['+newULBWit+'].contp1Address" type="text" class="form-control mandColorClass" id="contp1Address'+newULBWit+'"/> </td> '+
 	'<td> <input name="contractMastDTO.contractPart1List['+newULBWit+'].contp1ProofIdNo" type="text" class="form-control hasNumber mandColorClass" maxlength="12"  id="contp1ProofIdNo'+newULBWit+'"/> </td>'+
 	'<input type="hidden" name="contractMastDTO.contractPart1List['+newULBWit+'].contp1Type"  id="ULBType'+newULBWit+'" value="W"> '+
 	'<input type="hidden" name="contractMastDTO.contractPart1List['+newULBWit+'].active"  id="presentRow'+newULBWit+'" value="Y"/> '+
 	'<td><a class="btn btn-blue-2 uploadbtn" data-toggle="collapse" onclick="fileUpload('+uploadCount+',\''+uploadtype+'\')"><i class="fa fa-camera"></i> Upload & View</a></td>'+
 	'<td><a title="Delete" class="btn btn-danger remCF3" onclick="deleteCompleteRow('+uploadCount+',\''+uploadtype+'\')" href="javascript:void(0);"><i class="fa fa-trash-o"></i><span class="hide">Delete</span></a></td> </tr>');
 	newULBWit++;
 	uploadCount++;
 	reorderULB();
	});
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
});	
</script>	
 <script type='text/javascript'>
$(document).ready(function(){
	var newVen = $('#customFields5 tr').length;
	var uploadCountV=12;
	var uploadtypeV="V";
	var uploadCountW=112
	var uploadtypeW="VW";
	$(".addCF5").click(function(){
	$("#customFields5").append('<tr class="appendableVenClass">'+
	'<td><c:set var="baseLookupCode" value="VNT" /> <select name="contractMastDTO.contractPart2List['+newVen+'].contp2vType" class="form-control" id="venderType'+newVen+'" onchange="getVenderNameOnVenderType('+newVen+')"> <option value="0"> Select Vendor Type </option>'+
	'<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp"> <option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</option> </c:forEach> </select></td>'+
	'<td> <select name="contractMastDTO.contractPart2List['+newVen+'].vmVendorid" class="form-control" id="vendorId'+newVen+'"> <option value="0"> Select Vendor </option> </select> </td>'+
	'<td><input name="contractMastDTO.contractPart2List['+newVen+'].contp2Name" type="text" class="form-control mandColorClass" id="venderName'+newVen+'"/></td>'+ 
    '<td><input type="radio" name="contractMastDTO.contractPart2List['+newVen+'].contp2Primary" value="Y" onchange="OnChangePrimaryVender('+newVen+')"  class="contpPrimary mandColorClass margin-left-20 margin-top-10" id="contp2Primary'+newVen+'"/></td>'+
    '<input type="hidden" name="contractMastDTO.contractPart2List['+newVen+'].contp2Type"  id="contp2Type'+newVen+'" value="V">'+
    '<input type="hidden" name="contractMastDTO.contractPart2List['+newVen+'].active"  id="presentRow'+newVen+'" value="Y"/> '+
    '<td><a class="btn btn-blue-2 uploadbtn" data-toggle="collapse" onclick="fileUpload('+uploadCountV+',\''+uploadtypeV+'\')"><i class="fa fa-camera"></i> Upload & View</a></td>'+
	'<td><a title="Delete" class="btn btn-danger remCF5" onclick="deleteCompleteRow('+uploadCountV+',\''+uploadtypeV+'\')" href="javascript:void(0);"><i class="fa fa-trash-o"></i><span class="hide">Delete</span></a></td> </tr>');
	newVen++;
	uploadCountV++;
	reorderVender();
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
		$(".addCF4").click(function(){
 	$("#customFields4").append('<tr class="appendableWitClass"> <td>'+
		'<input name="contractMastDTO.contractPart2List['+newVen+'].contp2Name" type="text" class="form-control mandColorClass" id="contp2Name'+newVen+'"/> </td>'+
         '<td><input name="contractMastDTO.contractPart2List['+newVen+'].contp2Address" type="text" class="form-control mandColorClass" id="contp2Address'+newVen+'"/>'+
         '</td> <td> <input name="contractMastDTO.contractPart2List['+newVen+'].contp2ProofIdNo" type="text" class="form-control hasNumber mandColorClass" maxlength="12"  id="contp2ProofIdNo'+newVen+'"/></td>'+
         '<input type="hidden" name="contractMastDTO.contractPart2List['+newVen+'].contp2Type"  id="contp2Type'+newVen+'" value="W">' +
         '<input type="hidden" name="contractMastDTO.contractPart2List['+newVen+'].active"  id="presentRow'+newVen+'" value="Y"/> '+
         '<td><a class="btn btn-blue-2 uploadbtn" data-toggle="collapse" onclick="fileUpload('+uploadCountW+',\''+uploadtypeW+'\')"><i class="fa fa-camera"></i> Upload & View</a></td>'+
         '<td><a title="Delete" class="btn btn-danger remCF4" onclick="deleteCompleteRow('+uploadCountW+',\''+uploadtypeW+'\')" href="javascript:void(0);"><i class="fa fa-trash-o"></i><span class="hide">Delete</span></a></td> </tr>');
 	newVen++;
 	uploadCountW++;
 	reorderVender();
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

<!--Add Table field row--> 
<ol class="breadcrumb">
      <li><a href="AdminHome.html"><i class="fa fa-home"></i></a></li>
     <li><spring:message code="contract.breadcrumb.tran"/></li>
     <li><spring:message code="contract.breadcrumb.contractAgreement"/></li>
    </ol>
 
		<div class="widget">
			<div class="widget-header">
				<h2><spring:message code="contract.breadcrumb.contractAgreement"/>
				<apptags:helpDoc url="ContractAgreement.html" helpDocRefURL="ContractAgreement.html"></apptags:helpDoc>
				</h2>
				<!-- <div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"><i
						class="fa fa-question-circle fa-lg"><span class="hide"> -->
						<spring:message code="contract.breadcrumb.help"/></span></i></a>
				</div>
			</div>
			<div class="widget-content padding">
				<div class="mand-label clearfix">
					<span><spring:message code="contract.breadcrumb.fieldwith"/> <i class="text-red-1">*</i> <spring:message code="contract.breadcrumb.ismandatory"/>
					</span>
				</div>
         <form:form action="ContractAgreement.html"
					class="form-horizontal form" name="ContractAgreement"
					id="ContractAgreement">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					  <div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
	     <button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span>&times;</span></button>
	 	<span id="errorId"></span>
     </div> 
     
            <div class="form-group">
             <label class="col-sm-2 control-label required-control" for="AgreementDate"><spring:message code="contract.label.contractDate"/></label>
              <div class="col-sm-4">
                <div class="input-group">
                 <form:input path="contractMastDTO.contDate" type="text" class="form-control lessdatepicker mandColorClass dateClass" id="AgreementDate"/>
               <span class="input-group-addon"><i class="fa fa-calendar"></i></span> </div>
              </div></div>
              <div class="panel-group accordion-toggle" id="accordion_single_collapse">
                <div class="panel panel-default">         
                 <div class="panel-heading">
                  <h4 class="panel-title"><a data-toggle="collapse" class="collapsed" data-parent="#accordion_single_collapse" href="#Party1"><spring:message code="contract.label.partyULB"/></a> </h4>
                </div>
                
              <!--Party I (ULB)-->   
                
<div id="Party1" class="panel-collapse collapse">
<div class="panel-body">
    <c:choose>
   		<c:when test="${empty command.getContractMastDTO().getContractPart1List()}">
			<div class="table-overflow">
			<table class="table table-bordered table-striped" id="customFields1">
			
  			<tr>
    			<th width="400"><spring:message code="contract.label.department"/></th>
 				<th width="300"><spring:message code="contract.label.designation"/></th>
				<th width="300"><spring:message code="contract.label.representedBy"/></th>
				<th width="100"><spring:message code="contract.label.photoThumb"/></th>
<!--            <th width="50"><a title="Add" href="javascript:void(0);" class="addCF1 btn btn-success"><i class="fa fa-plus-circle"></i><span class="hide">Add</span></a></th>
 -->                        </tr>
			<tr>
			<td>		
            <form:select path="contractMastDTO.contractPart1List[0].dpDeptid" class="form-control" id="deptId" onchange="getAllTaxesBasedOnDept(this)">
			<form:option value="0"><spring:message code="rnl.master.select.dept" text="select Department"></spring:message></form:option>
			<c:forEach items="${command.getdeptList()}" var="lookUp">
		    <form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
			</c:forEach>
			</form:select> 
			</td> 
			<td>  
			<form:select path="contractMastDTO.contractPart1List[0].dsgid" class="form-control" id="desigantionId" onchange="getEmpBasedOnDesgnation(this)">
			<form:option value="0"><spring:message code="rnl.master.select.desg" text="select Designation"></spring:message></form:option>
			<c:forEach items="${command.getDesgnationList()}" var="lookUp">
			<form:option value="${lookUp.dsgid}" code="${lookUp.dsgname}">${lookUp.dsgdescription}</form:option>
			</c:forEach>
			</form:select> </td> 
			<td>
			<form:select path="contractMastDTO.contractPart1List[0].empid" class="form-control" id="representBy">
			<form:option value="0"><spring:message code="rnl.select.emp" text="select Employee"></spring:message></form:option>
			</form:select> 
			 </td>
             <form:hidden path="contractMastDTO.contractPart1List[0].contp1Name"  id="empName"   />  
             <form:hidden path="contractMastDTO.contractPart1List[0].contp1Type"  id="ULBType" value="U"/>  
             <form:hidden path="contractMastDTO.contractPart1List[0].active"  id="presentRow1" value="Y"/>                      
             <td><a class="btn btn-blue-2 uploadbtn" data-toggle="collapse"  onclick="fileUpload(0,'U')"><i class="fa fa-camera"></i> <spring:message code="contract.label.uploadView"/></a></td>
<!-- 		<td><a title="Delete" class="btn btn-danger remCF3" href="javascript:void(0);"><i class="fa fa-trash-o"></i><span class="hide">Delete</span></a></td>
 -->
</tr>
</table>
</div>

  <h4><spring:message code="contract.label.witness"/></h4>
       <div class="table-overflow">
       <table class="table table-bordered table-striped" id="customFields3">
  			<tr>                          
				<th width="400"><spring:message code="contract.label.name"/></th>
				<th width="400"><spring:message code="contract.label.address"/></th>
				<th width="200"><spring:message code="contract.label.aadhaarNo"/></th>
 				<th width="100"><spring:message code="contract.label.photoThumb"/></th>
				<th width="50"><a title="Add" href="javascript:void(0);" class="addCF3 btn btn-success"><i class="fa fa-plus-circle"></i><span class="hide"><spring:message code="rl.property.label.add" text="Add"></spring:message></span></a></th>
			  </tr>
			 <tr class="appendableClass">                        
			<td><form:input path="contractMastDTO.contractPart1List[1].contp1Name" type="text" class="form-control mandColorClass" id="contp1Name1"/>
			</td>
			<td><form:input path="contractMastDTO.contractPart1List[1].contp1Address" type="text" class="form-control mandColorClass" id="contp1Address1"/>
			</td>
			<td> <form:input path="contractMastDTO.contractPart1List[1].contp1ProofIdNo" type="text" class="form-control hasNumber mandColorClass" maxlength="12" data-mask="9999 9999 9999" id="contp1ProofIdNo1"/>
			</td>  
			<form:hidden path="contractMastDTO.contractPart1List[1].contp1Type"  id="ULBType1" value="W"/> 
			<form:hidden path="contractMastDTO.contractPart1List[1].active"  id="presentRow1" value="Y"/>  
			 
			<td><a class="btn btn-blue-2 uploadbtn" data-toggle="collapse" onclick="fileUpload(1,'UW')"><i class="fa fa-camera"></i> <spring:message code="contract.label.uploadView"/></a></td>
           <td><a title="Delete" class="btn btn-danger remCF3" onclick="deleteCompleteRow(1,'UW')" href="javascript:void(0);"><i class="fa fa-trash-o"></i><span class="hide"><spring:message code="contract.label.delete"/></span></a></td>
           </tr> 
                           
                        </table>
		</div>
		</c:when>
	<c:otherwise>
	
	 <div class="table-overflow">
	<table class="table table-bordered table-striped" id="customFields1">
   <tr>
                     <th width="400"><spring:message code="contract.label.department"/></th>
 				<th width="300"><spring:message code="contract.label.designation"/></th>
				<th width="300"><spring:message code="contract.label.representedBy"/></th>
				<th width="100"><spring:message code="contract.label.photoThumb"/></th>
<!--                           <th width="50"><a title="Add" href="javascript:void(0);" class="addCF1 btn btn-success"><i class="fa fa-plus-circle"></i><span class="hide">Add</span></a></th>
 -->                        </tr>
 
 
       <%--     <c:forEach items="${command.getContractMastDTO().getContractPart1List()}" var="details" varStatus="status">         
                         <c:if test="${details.getContp1Type() eq 'U'}">  --%>
                         <tr>
                          <td>		
             
                 <form:select path="contractMastDTO.contractPart1List[0].dpDeptid" class="form-control" id="deptId" onchange="getAllTaxesBasedOnDept(this)">
				<form:option value="0"><spring:message code="rnl.master.select.dept" text="select Department"></spring:message></form:option>
				<c:forEach items="${command.getdeptList()}" var="lookUp">
				<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
				</c:forEach>
				</form:select> 
				</td> 
                 <td>  <form:select path="contractMastDTO.contractPart1List[0].dsgid" class="form-control" id="desigantionId" onchange="getEmpBasedOnDesgnation(this)">
								<form:option value="0"><spring:message code="rnl.master.select.desg" text="select Designation"></spring:message></form:option>
							 	<c:forEach items="${command.getDesgnationList()}" var="lookUp">
								<form:option value="${lookUp.dsgid}" code="${lookUp.dsgname}">${lookUp.dsgdescription}</form:option>
								</c:forEach>
							</form:select> </td> 
                          <td>
                          	<c:if test="${command.modeType eq 'V'}">
                        <form:input path="contractMastDTO.contractPart1List[0].contp1Name" class="form-control"
								id="representBy"/>
							</c:if>
									<c:if test="${command.modeType ne 'V'}">
                        <form:select path="contractMastDTO.contractPart1List[0].empid" class="form-control"
								id="representBy">					
								<form:option value="0"><spring:message code="rnl.select.emp" text="select Employee"></spring:message></form:option>
<%-- 								   <c:set var="count" value="${status.count-1}" > </c:set>
 --%>								 <c:forEach items="${command.contractMastDTO.contractPart1List[0].getContp1NameList()}" var="lookUp">
				<form:option value="${lookUp.lookUpId}">${lookUp.descLangFirst}</form:option>
							</c:forEach> 
							</form:select> 
								</c:if>
                          </td>
                          <form:hidden path="contractMastDTO.contractPart1List[0].contp1Name"  id="empName"   />  
                          <form:hidden path="contractMastDTO.contractPart1List[0].contp1Type"  id="ULBType" value="U"/> 
                          <form:hidden path="contractMastDTO.contractPart1List[0].active"  id="presentRow1" value="Y"/>  
                          
                          <td><a class="btn btn-blue-2 uploadbtn" data-toggle="collapse" onclick="fileUpload(0,'U')"><i class="fa fa-camera"></i> <spring:message code="contract.label.uploadView"/></a></td>
</tr>
</table>
</div>
   <h4><spring:message code="contract.label.witness"/></h4>
                    <div class="table-overflow">
                      <table class="table table-bordered table-striped" id="customFields3">
                        <tr>                          
                 	<th width="400"><spring:message code="contract.label.name"/></th>
					<th width="400"><spring:message code="contract.label.address"/></th>
					<th width="200"><spring:message code="contract.label.aadhaarNo"/></th>
 					<th width="100"><spring:message code="contract.label.photoThumb"/></th>
                          <th width="50"><a title="Add" href="javascript:void(0);" class="addCF3 btn btn-success"><i class="fa fa-plus-circle"></i><span class="hide"><spring:message code="rl.property.label.add" text="Add"></spring:message></span></a></th>
                        </tr>
                           <c:set var="p1" value="1" > </c:set>
                           <c:forEach items="${command.getContractMastDTO().getContractPart1List()}" var="details" varStatus="status">         
                         <c:if test="${details.getContp1Type() eq 'W'}"> 
                        
                        <tr class="appendableClass">                        
                          <td>
          					<form:input path="contractMastDTO.contractPart1List[${status.count-1}].contp1Name" type="text" class="form-control mandColorClass" id="contp1Name${status.count-1}"/>
                          </td>
                          <td>
                          <form:input path="contractMastDTO.contractPart1List[${status.count-1}].contp1Address" type="text" class="form-control mandColorClass" id="contp1Address${status.count-1}"/>
						</td>
   						<td>
                          <form:input path="contractMastDTO.contractPart1List[${status.count-1}].contp1ProofIdNo" type="text" class="form-control hasNumber mandColorClass" maxlength="12" data-mask="9999 9999 9999"  id="contp1ProofIdNo${status.count-1}"/>
						</td>  
						
						 <form:hidden path="contractMastDTO.contractPart1List[${status.count-1}].contp1Type"  id="ULBType${status.count-1}" value="W"/>  
						 <form:hidden path="contractMastDTO.contractPart1List[${status.count-1}].active"  id="presentRow${status.count-1}" value="Y"/>  
						<c:if test="${command.modeType eq 'V'}">
						 <td><a class="btn btn-blue-2 uploadbtn" data-toggle="collapse" onclick="fileUpload(${status.count-1},'UW')"><i class="fa fa-camera"></i> <spring:message code="contract.label.uploadView"/></a></td>
						 </c:if>
						 	<c:if test="${command.modeType eq 'E'}">
						  <td><a class="btn btn-blue-2 uploadbtn" data-toggle="collapse" onclick="fileUpload(${p1},'UW')"><i class="fa fa-camera"></i> <spring:message code="contract.label.uploadView"/></a></td>
						   
						  </c:if>
						 
                          <td><a title="Delete" class="btn btn-danger remCF3"  onclick="deleteCompleteRow(${p1},'UW')" href="javascript:void(0);"><i class="fa fa-trash-o"></i><span class="hide"><spring:message code="contract.label.delete"/></span></a></td>
                        <c:set var="p1" value="${p1+1}" > </c:set>
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
 
 
<!--Party II (Vendor)--> 
<div class="panel panel-default">
<div class="panel-heading">
 <h4 class="panel-title"><a data-toggle="collapse" class="collapsed" data-parent="#accordion_single_collapse" href="#Party2"> <spring:message code="contract.label.partyVendor"/></a> </h4>
 </div>
<div id="Party2" class="panel-collapse collapse">
 <div class="panel-body">
                 <div class="panel panel-default">
<c:choose>
<c:when test="${empty command.getContractMastDTO().getContractPart2List()}">
	<div class="table-overflow-sm">
	<table class="table table-bordered table-striped" id="customFields5">
	<tr>
    <th width="400"><spring:message code="contract.label.vendorType"/></th>
 	<th width="300"><spring:message code="contract.label.vendorName"/></th>
	<th width="300"><spring:message code="contract.label.representedBy"/></th>
	<th width="50"><spring:message code="contract.label.primaryVender"/></th>
	<th width="100"><spring:message code="contract.label.photoThumb"/></th>
	<th width="50"><a title="Add" href="javascript:void(0);" id="addVendor"  class="addCF5 btn btn-success"><i class="fa fa-plus-circle"></i><span class="hide">Add</span></a></th>
	</tr>
     <tr class="appendableVenClass">
    <td><c:set var="baseLookupCode" value="VNT" /> <form:select path="contractMastDTO.contractPart2List[0].contp2vType" class="form-control"
																id="venderType0" onchange="getVenderNameOnVenderType(0)">
	<form:option value="0"><spring:message code="rnl.select.vender.type" text="Select Vendor Type"></spring:message></form:option>
	<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
	<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
	</c:forEach> 
	</form:select>
	</td>
    <td> <form:select path="contractMastDTO.contractPart2List[0].vmVendorid" class="form-control" id="vendorId0">
	<form:option value="0"><spring:message code="rnl.select.vender" text="Select Vendor"></spring:message></form:option>
	</form:select>
    </td>
     <td>
     <form:input path="contractMastDTO.contractPart2List[0].contp2Name" type="text" class="form-control mandColorClass" id="venderName0"/>
     </td>
      <td>
     <form:radiobutton path="contractMastDTO.contractPart2List[0].contp2Primary" value="Y" name="" class="contpPrimary mandColorClass margin-left-20 margin-top-10" onchange="OnChangePrimaryVender(0)" id="contp2Primary0"/>
     </td>
	<form:hidden path="contractMastDTO.contractPart2List[0].contp2Type"  id="contp2Type0" value="V"/>  
	<form:hidden path="contractMastDTO.contractPart2List[0].active"  id="presentRow0" value="Y"/>  
	
    <td><a class="btn btn-blue-2 uploadbtn" data-toggle="collapse" onclick="fileUpload(11,'V')"><i class="fa fa-camera"></i> <spring:message code="contract.label.uploadView"/></a></td>
    <td><a title="Delete" class="btn btn-danger remCF5" onclick="deleteCompleteRow(11,'V')" href="javascript:void(0);"><i class="fa fa-trash-o"></i><span class="hide"><spring:message code="contract.label.delete"/></span></a></td>
    </tr>
    </table>
   </div>
    <h4><spring:message code="contract.label.witness"/></h4>
    <div class="table-overflow-sm">
    <table class="table table-bordered table-striped" id="customFields4">
    <tr>
	<th width="400"><spring:message code="contract.label.name"/></th>
				<th width="400"><spring:message code="contract.label.address"/></th>
				<th width="200"><spring:message code="contract.label.aadhaarNo"/></th>
 				<th width="100"><spring:message code="contract.label.photoThumb"/></th>
    <th width="50"><a title="Add" href="javascript:void(0);" class="addCF4 btn btn-success"><i class="fa fa-plus-circle"></i><span class="hide">Add</span></a></th>
    </tr>
    <tr class="appendableWitClass">
    <td><form:input path="contractMastDTO.contractPart2List[1].contp2Name" type="text" class="form-control mandColorClass" id="contp2Name1"/>
     </td>
     <td><form:input path="contractMastDTO.contractPart2List[1].contp2Address" type="text" class="form-control mandColorClass" id="contp2Address1"/>
     </td>
     <td><form:input path="contractMastDTO.contractPart2List[1].contp2ProofIdNo" type="text" class="form-control hasNumber mandColorClass" maxlength="12" data-mask="9999 9999 9999" id="contp2ProofIdNo1"/>
     </td>
     <form:hidden path="contractMastDTO.contractPart2List[1].contp2Type"  value="W" id="contp2Type"/>  
     <form:hidden path="contractMastDTO.contractPart2List[1].active"  id="presentRow1" value="Y"/>  
     <td><a class="btn btn-blue-2 uploadbtn" data-toggle="collapse" onclick="fileUpload(111,'VW')"><i class="fa fa-camera"></i> <spring:message code="contract.label.uploadView"/></a></td>
     <td><a title="Delete" class="btn btn-danger remCF4" onclick="deleteCompleteRow(111,'VW')"  href="javascript:void(0);"><i class="fa fa-trash-o"></i><span class="hide"><spring:message code="contract.label.delete"/></span></a></td>
     </tr>
     </table>
     </div>
 </c:when>
<c:otherwise>
          	
<div class="table-overflow-sm">
    <table class="table table-bordered table-striped" id="customFields5">
     <tr>
    <th width="400"><spring:message code="contract.label.vendorType"/></th>
 	<th width="300"><spring:message code="contract.label.vendorName"/></th>
	<th width="300"><spring:message code="contract.label.representedBy"/></th>
	<th width="50"><spring:message code="contract.label.primaryVender"/></th>
	<th width="100"><spring:message code="contract.label.photoThumb"/></th>
 	<th width="50"><a title="Add" href="javascript:void(0);" id="addVendor"  class="addCF5 btn btn-success"><i class="fa fa-plus-circle"></i><span class="hide"><spring:message code="rl.property.label.add" text="Add"></spring:message></span></a></th>
     </tr>
      <c:set var="p2" value="11" > </c:set>
     <c:forEach items="${command.getContractMastDTO().getContractPart2List()}" var="details" varStatus="status">    
          
     <c:if test="${details.getContp2Type() eq 'V'}"> 
     <tr class="appendableVenClass">
     <td><c:set var="baseLookupCode" value="VNT" /> <form:select path="contractMastDTO.contractPart2List[${status.count-1}].contp2vType" class="form-control"
																id="venderType${status.count-1}" onchange="getVenderNameOnVenderType(${status.count-1})">
	<form:option value="0"><spring:message code="rnl.select.ved.type" text="Select Vendor Type"></spring:message></form:option>
	<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
	<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
	</c:forEach> 
	</form:select></td>
    <td>     
	<c:if test="${command.modeType eq 'V'}">
   <form:input path="contractMastDTO.contractPart2List[${status.count-1}].venderName" class="form-control"
								id="vendorId${status.count-1}"/>
							</c:if>
	<c:if test="${command.modeType ne 'V'}">
	   <form:select path="contractMastDTO.contractPart2List[${status.count-1}].vmVendorid" class="form-control" id="vendorId${status.count-1}">
	   <c:set var="count" value="${status.count-1}" > </c:set>
	<form:option value="0"> Select Vendor </form:option>
							 <c:forEach items="${command.contractMastDTO.contractPart2List[count].getVmVendoridList()}" var="lookUp">
				<form:option value="${lookUp.lookUpId}">${lookUp.descLangFirst}</form:option>
							</c:forEach> 
	</form:select>
	</c:if> 
     </td>
     <td><form:input path="contractMastDTO.contractPart2List[${status.count-1}].contp2Name" type="text" class="form-control mandColorClass" id="venderName${status.count-1}"/>
     </td>
         <td>
     <form:radiobutton path="contractMastDTO.contractPart2List[${status.count-1}].contp2Primary" value="Y" name="" onchange="OnChangePrimaryVender(${status.count-1})"  class="contpPrimary mandColorClass margin-left-20 margin-top-10" id="contp2Primary${status.count-1}"/>
     </td>
	
	<form:hidden path="contractMastDTO.contractPart2List[${status.count-1}].contp2Type"  id="contp2Type${status.count-1}" value="V"/> 
	  <form:hidden path="contractMastDTO.contractPart2List[${status.count-1}].active"  id="presentRow${status.count-1}" value="Y"/>   
	   <c:if test="${command.modeType eq 'V'}">
     <td><a class="btn btn-blue-2 uploadbtn" data-toggle="collapse" onclick="fileUpload(${status.count-1},'V')"><i class="fa fa-camera"></i> <spring:message code="contract.label.uploadView"/></a></td>
    </c:if>
     <c:if test="${command.modeType eq 'E'}">
          <td><a class="btn btn-blue-2 uploadbtn" data-toggle="collapse" onclick="fileUpload(${p2},'V')"><i class="fa fa-camera"></i> <spring:message code="contract.label.uploadView"/></a></td>
     </c:if>
      <td><a title="Delete" class="btn btn-danger remCF5" onclick="deleteCompleteRow(${p2},'V')" href="javascript:void(0);"><i class="fa fa-trash-o"></i><span class="hide"><spring:message code="contract.label.delete"/></span></a></td>
       <c:set var="p2" value="${p2+1}" > </c:set>
      </tr>
   </c:if>
</c:forEach>
</table>
</div>
      <h4><spring:message code="contract.label.witness"/></h4>
             <div class="table-overflow-sm">
               <table class="table table-bordered table-striped" id="customFields4">
                 <tr>
	            <th width="400"><spring:message code="contract.label.name"/></th>
				<th width="400"><spring:message code="contract.label.address"/></th>
				<th width="200"><spring:message code="contract.label.aadhaarNo"/></th>
 				<th width="100"><spring:message code="contract.label.photoThumb"/></th>
                   <th width="50"><a title="Add" href="javascript:void(0);" class="addCF4 btn btn-success"><i class="fa fa-plus-circle"></i><span class="hide">Add</span></a></th>
                 </tr>
                  <c:set var="p2W" value="111" > </c:set>
                 <c:forEach items="${command.getContractMastDTO().getContractPart2List()}" var="details" varStatus="status">         
                  <c:if test="${details.getContp2Type() eq 'W'}">
                 
                 <tr class="appendableWitClass">
                   <td><form:input path="contractMastDTO.contractPart2List[${status.count-1}].contp2Name" type="text" class="form-control mandColorClass" id="contp2Name${status.count-1}"/>
                   </td>
                    <td><form:input path="contractMastDTO.contractPart2List[${status.count-1}].contp2Address" type="text" class="form-control mandColorClass" id="contp2Address${status.count-1}"/>
                   </td>
                    <td><form:input path="contractMastDTO.contractPart2List[${status.count-1}].contp2ProofIdNo" type="text" class="form-control hasNumber mandColorClass" maxlength="12" data-mask="9999 9999 9999" id="contp2ProofIdNo${status.count-1}"/>
                   </td>
                	<form:hidden path="contractMastDTO.contractPart2List[${status.count-1}].contp2Type"  value="W" id="contp2Type${status.count-1}"/>  
                	<form:hidden path="contractMastDTO.contractPart2List[${status.count-1}].active"  id="presentRow${status.count-1}" value="Y"/>  
                     <c:if test="${command.modeType eq 'V'}">
                   <td><a class="btn btn-blue-2 uploadbtn" data-toggle="collapse" onclick="fileUpload(${status.count-1},'VW')"><i class="fa fa-camera"></i> <spring:message code="contract.label.uploadView"/></a></td>
                   </c:if>
                   <c:if test="${command.modeType eq 'E'}">
                    <td><a class="btn btn-blue-2 uploadbtn" data-toggle="collapse" onclick="fileUpload(${p2W},'VW')"><i class="fa fa-camera"></i> <spring:message code="contract.label.uploadView"/></a></td>
                      </c:if>
                   <td><a title="Delete" class="btn btn-danger remCF4" onclick="deleteCompleteRow(${p2W},'VW')" href="javascript:void(0);"><i class="fa fa-trash-o"></i><span class="hide"><spring:message code="contract.label.delete"/></span></a></td>
                                      <c:set var="p2W" value="${p2W+1}" > </c:set>
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
        	  </div>
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"><a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#Contract"><spring:message code="contract.label.contract"/></a> </h4>
                </div>
                <div id="Contract" class="panel-collapse collapse in">
                  <div class="panel-body">
                               <div class="form-group">
                      <label class="col-sm-2 control-label required-control" for="ResulationNo"><spring:message code="contract.label.resolutionNo"/></label>
                      <div class="col-sm-4">
          					<form:input path="contractMastDTO.contRsoNo" type="text" class="form-control mandColorClass" id="resulationNo"/>
                      </div>
                      <label class="col-sm-2 control-label required-control" for="ResulationDate"><spring:message code="contract.label.resolutionDate"/></label>
                      <div class="col-sm-4">
                        <div class="input-group">
                 	<form:input path="contractMastDTO.contRsoDate" type="text" class="form-control dateClass datepickerResulation mandColorClass" id="resolutionDate"/>
                          <span class="input-group-addon"><i class="fa fa-calendar"></i></span> </div>
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="col-sm-2 control-label required-control" for="TenderName"><spring:message code="contract.label.tenderNo"/></label>
                      <div class="col-sm-4">
                        <div class="input-group">
          					<form:input path="contractMastDTO.contTndNo" type="text" class="form-control mandColorClass" id="TenderNo"/>
                                                   <a href="#" class="input-group-addon"><i class="fa fa-search"></i></a> </div>
                      </div>
                      <label class="col-sm-2 control-label required-control" for="TenderDate"><spring:message code="contract.label.tenderDate"/></label>
                      <div class="col-sm-4">
                        <div class="input-group">                      
                 	<form:input path="contractMastDTO.contTndDate" type="text" class="form-control datepickerTender dateClass mandColorClass" id="tenderDate"/>
                          <span class="input-group-addon"><i class="fa fa-calendar"></i></span> </div>
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="col-sm-2 control-label required-control" for="ContractType"><spring:message code="contract.label.contractType"/></label>
                      <div class="col-sm-4">       
                         <c:set var="baseLookupCode" value="CNT" /><form:select path="contractMastDTO.contType" class="form-control" id="ContractType">
							<form:option value="0">
							<spring:message code="rnl.select.contract.type" text="Select Contract Type"></spring:message>
							</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
							<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
                       </div>                   
                    </div>
                    <div class="form-group">
                      <label class="col-sm-2 control-label required-control" for="ContractFromDate"><spring:message code="contract.label.contractFromDate"/></label>
                      <div class="col-sm-4">
                        <div class="input-group">
                 	<form:input path="contractMastDTO.contractDetailList[0].contFromDate" type="text" class="form-control dateClass lessthancurrdatefrom mandColorClass" id="contractFromDate"/>
                          <span class="input-group-addon"><i class="fa fa-calendar"></i></span> </div>
                      </div>
                      <label class="col-sm-2 control-label required-control" for="ContractToDate"><spring:message code="contract.label.contractToDate"/></label>
                      <div class="col-sm-4">
                        <div class="input-group">
                 	<form:input path="contractMastDTO.contractDetailList[0].contToDate" type="text" class="form-control dateClass lessthancurrdateto mandColorClass" id="contractToDate" />
                          <span class="input-group-addon"><i class="fa fa-calendar"></i></span> </div>
                      </div>
                    </div>
                    
                   <form:hidden path="modeType"  id="hiddeMode"/>  
                    <div class="form-group">
                      <label class="col-sm-2 control-label"><spring:message code="contract.label.contractMode"/> <span class="mand">*</span></label>
                      <div class="col-sm-4">			
						             	<label
					class="radio-inline">     
                          <form:radiobutton  path="contractMastDTO.contMode"  class="ContractMode" value="C" />
                          <spring:message code="contract.label.commercial"/></label>
                          
                                      	<label
					class="radio-inline">     
                          <form:radiobutton  path="contractMastDTO.contMode"   class="ContractMode" value="N" />
                          <spring:message code="contract.label.nonCommercial"/></label>			
			</div>
                      <label class="control-label col-sm-2 required-control" for="AllowRenewal"><spring:message code="contract.label.allowRenewal"/></label>
              <div class="col-sm-4">
              <form:select path="contractMastDTO.contRenewal" class="form-control mandColorClass" id="allowRenewal">
																<form:option value="0"><spring:message code="rnl.master.select" text="Select"></spring:message></form:option>
																<form:option value="Y"><spring:message code="rnl.master.yes" text="Yes"></spring:message></form:option>
																<form:option value="N"><spring:message code="rnl.master.no" text="No"></spring:message></form:option>
															</form:select>
              </div>
         </div>
                  </div>
               <div class="display-hide Commercial">
                     <div class="form-group">
                        <label class="col-sm-2 control-label" for="ContractPayment"><spring:message code="contract.label.contractPayment"/> <span class="mand">*</span></label>
                        <div class="col-sm-4">
                        <form:select path="contractMastDTO.contPayType" class="form-control"
																id="ContractPayment">
                       
                         		<form:option value="0"><spring:message code="rnl.master.select" text="Select"></spring:message></form:option>
								<form:option value="P"><spring:message code="rnl.master.payable" text="Payable"></spring:message></form:option>
								<form:option value="R"><spring:message code="rnl.master.receivable" text="Receivable"></spring:message></form:option>
       </form:select>                  
                      </div>
                              <label class="col-sm-2 control-label required-control" for="ContractAmount"><spring:message code="contract.label.contractAmount"/></label>
                      <div class="col-sm-4">
                        <div class="input-group">
           		  <form:input path="contractMastDTO.contractDetailList[0].contAmount" type="text" class="form-control text-right mandColorClass hasNumber" id="ContractAmount"/>                    
                        <span class="input-group-addon"><i class="fa fa-inr"></i></span> </div>
                      </div>
                    </div>
                    </div>
                  <div class="display-hide Commercial">
                    <div class="form-group">
                      <label class="col-sm-2 control-label" for="SecurityDepositReceipt"><spring:message code="contract.label.securityDepositReceipt"/></label>
                      <div class="col-sm-4">
                        <form:input path="contractMastDTO.contractDetailList[0].contSecRecNo" type="text" class="form-control mandColorClass" id="SecurityDepositReceipt"/>
                      </div>
                      <label class="col-sm-2 control-label" for="SecurityDepositDate"><spring:message code="contract.label.securityDepositDate"/></label>
                      <div class="col-sm-4">
                        <div class="input-group">
                        <form:input path="contractMastDTO.contractDetailList[0].contSecRecDate" type="text" class="form-control datepicker dateClass mandColorClass" id="SecurityDepositDate"/>
                   
                          <span class="input-group-addon"><i class="fa fa-calendar"></i></span> </div>
                      </div>
                    </div>
                      <div class="form-group">
                      <label class="col-sm-2 control-label required-control" for="SecurityDeposit"><spring:message code="contract.label.securityDepositAmount"/></label>
                      <div class="col-sm-4">
                        <div class="input-group">
				  <form:input path="contractMastDTO.contractDetailList[0].contSecAmount" type="text" class="form-control mandColorClass hasNumber text-right" id="SecurityDeposit"/>         
                          <span class="input-group-addon"><i class="fa fa-inr"></i></span> </div>
                      </div>
                        <label class="col-sm-2 control-label required-control" for="ContractType"><spring:message code="contract.label.taxCode"/></label>
                        <div class="col-sm-4">  
                        <c:if test="${command.modeType ne 'V' && command.modeType ne 'E'}">
                       	<form:select path="contractMastDTO.taxId" class="form-control" id="taxId">
						<form:option value="0">select Tax</form:option>
					</form:select> 
					</c:if>
					  <c:if test="${command.modeType eq 'V' || command.modeType eq 'E'}">
						   <form:select path="contractMastDTO.taxId" class="form-control" id="taxId">
	                  <form:option value="0"><spring:message code="rnl.master.select.tax" text="Select tax"></spring:message></form:option>
							 <c:forEach items="${command.contractMastDTO.getTaxCodeList()}" var="lookUp">
				<form:option value="${lookUp.lookUpId}">${lookUp.descLangFirst}</form:option>
							</c:forEach> 
	</form:select>
	</c:if>
	
					</div>
                      
                    </div>
                    <div class="form-group">
                  <label class="col-sm-2 control-label required-control" for="NoofInstallments"><spring:message code="contract.label.noOfInstallments"/></label>
                  <div class="col-sm-4">
               <form:input path="contractMastDTO.contractDetailList[0].contInstallmentPeriod" type="text" class="form-control hasNumber mandColorClass" id="id_noa"/>
                     </div>
                    <button type="button" class="btn btn-success" id="submitInstall"><spring:message code="contract.label.createInstallment"/></button>
	                        </div>
	            </div>
            
	<div class="table-overflow display-hide Commercial">
    <table class="table table-bordered table-striped tab tab_Application" id="noOfInstallmentTable">
    	<tbody>
        	<tr id='noa_header' style="display:none;">
      	<th width="5%"><spring:message code="contract.label.no"/></th>
        <th width="10%"><spring:message code="contract.label.select"/></th>
 		<th width="10%"><spring:message code="contract.label.value"/></th>
		<th width="10%"><spring:message code="contract.label.dueDate"/></th>
		 <th width="30%"><spring:message code="contract.label.Milestone"/></th>
  		</tr>
   		 <c:if test="${command.modeType eq 'V' || command.modeType eq 'E'}">
		<c:forEach items="${command.getContractMastDTO().getContractInstalmentDetailList()}" var="details" varStatus="status">         
		<tr class="appendableClassInstallments">
      	<td>
         <form:input path="" type="text" class="form-control" size="5"  value="${status.count}" id="contractNo${status.count-1}"/></td>
 		<td>
     	<form:select path="contractMastDTO.contractInstalmentDetailList[${status.count-1}].conitAmtType" class="form-control mandColorClass" id="PaymentTerms${status.count-1}"> 	
		<c:set var="baseLookupCode" value="VTY" /> 
		<form:option value="0"><spring:message code="rnl.master.select" text="Select"></spring:message></form:option>
		<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
		<form:option  value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
		</c:forEach>
		</form:select>
		</td>
		<td>
		<form:input type="text" path="contractMastDTO.contractInstalmentDetailList[${status.count-1}].conitValue" class="form-control text-right hasNumber" id="amt${status.count-1}" /></td>
		 <form:hidden path="contractMastDTO.contractInstalmentDetailList[${status.count-1}].active"  id="presentRow${status.count-1}" value="Y"/>  
		<td><div class="input-group">
		<form:input type="text" id="installmentsDate${status.count-1}"  path="contractMastDTO.contractInstalmentDetailList[${status.count-1}].conitDueDate" class="form-control dateClass mandColorClass Insdatepicker" /><span class="input-group-addon"><i class="fa fa-calendar"></i></span></div></td>
		<td><form:input type="text" size="5" path="contractMastDTO.contractInstalmentDetailList[${status.count-1}].conitMilestone" id="mileStone${status.count-1}" class="form-control" /></td>
		</tr>
	</c:forEach>
	</c:if>
	</tbody>
</table>
</div>

  </div>
</div>
   
<div class="panel panel-default">
<div class="panel-heading">
 <h4 class="panel-title"><a data-toggle="collapse" class="collapsed" data-parent="#accordion_single_collapse" href="#Terms"><spring:message code="contract.label.termsConditions"/></a> </h4>
</div>
     <div id="Terms" class="panel-collapse collapse">
     <div class="panel-body">
     <div class="table-responsive">
      <table class="table table-bordered table-striped" id="customFields2">
      <tr>
       <th><spring:message code="contract.label.srNo"/></th>
       <th><spring:message code="contract.label.termsConditions"/><span class="mand">*</span></th>
       <th width="50"><a title="Add" href="javascript:void(0);" class="addCF2 btn btn-success"><i class="fa fa-plus-circle"></i><span class="hide"><spring:message code="rl.property.label.add" text="Add"></spring:message></span></a></th>
      </tr>
  <c:choose>
  <c:when test="${empty command.getContractMastDTO().getContractTermsDetailList()}">
    <tr class="appendableTermConClass" >
      <td width="50"><form:input type="text" path="" class="form-control text-center hasNumber" id="sNo0" value="1" /></td>
       <td>
       <form:textarea path="contractMastDTO.contractTermsDetailList[0].conttDescription" cols="" rows="" class="form-control mandColorClass" id="termCon0"></form:textarea>
       </td>
         <form:hidden path="contractMastDTO.contractTermsDetailList[0].active"  id="presentRow0" value="Y"/>  
       <td><a title="Delete" class="btn btn-danger remCF2" href="javascript:void(0);"><i class="fa fa-trash-o"></i><span class="hide"><spring:message code="rnl.master.delete" text="Delete"></spring:message></span></a></td>
       </tr>
   </c:when>
  <c:otherwise>
  <c:forEach items="${command.getContractMastDTO().getContractTermsDetailList()}" var="details" varStatus="status">
       <tr class="appendableTermConClass" >
       <td width="50"><form:input type="text" path="" class="form-control text-center hasNumber" id="sNo${status.count-1}" value="${status.count}" /></td>
       <td>
      <form:textarea path="contractMastDTO.contractTermsDetailList[${status.count-1}].conttDescription" cols="" rows="" class="form-control mandColorClass" id="termCon${status.count-1}"></form:textarea>
     </td>
      <form:hidden path="contractMastDTO.contractTermsDetailList[${status.count-1}].active"  id="presentRow${status.count-1}" value="Y"/>  
     <td><a title="Delete" class="btn btn-danger remCF2" href="javascript:void(0);"><i class="fa fa-trash-o"></i><span class="hide">Delete</span></a></td>
     </tr>
  </c:forEach>
</c:otherwise>
</c:choose>
</table>
</div>
</div>
</div>
</div>
</div>
            <div class="text-center">
            <c:if test="${command.modeType ne 'V'}">
              <button type="button" class="btn btn-success btn-submit" onclick="saveContractAgreementForm(this)" id="submit"><spring:message code="contract.label.submit"/></button>
              <button type="reset" id="resetButton" class="btn btn-warning"><spring:message code="contract.label.Reset"/></button>
              </c:if>
              <button type="button" onclick="back('${command.showForm}')" id="backButton" class="btn btn-danger"><spring:message code="contract.label.Back"/></button>
            </div>
           
          </form:form>
                        </div>
                      </div>
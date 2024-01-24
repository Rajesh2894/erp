<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/water/newWaterDetails.js"></script>

	
<script type="text/javascript">
$(document).ready(
		function() {
			if ( $("#typeOfApplication").val() == 'T') {
				
				$("#fromtoperiod").show();
				
			}
			else
				{
				$("#fromtoperiod").hide();
				}
			
			var cntConnection = $('#tbl2 tr').length-1;
			var cnt = $('#tbl1 tr').length - 1;
			
			 if(('${command.scrutinyApplicable}')=='true')
				{
				 $("#scrutinyDiv").show();
					} 
			 else
				 {
				 $("#scrutinyDiv").hide();
				 }
			 $("#addOwner").click(function(){
				
				 var countOwner=cnt-1;
		            if($('#ownerTitle'+countOwner).val()!='0' && $('#ownerFName'+countOwner).val()!='' && $('#ownerLName'+countOwner).val()!='' && $('#ownerGender'+countOwner).val()!='0')
		                {
					var row = '<td>'
							+ '<c:set var="baseLookupCode" value="TTL" />'
							+ '<select name="csmrInfo.ownerList['+cnt+'].ownerTitle" id="ownerTitle'+cnt+'" class="form-control">'
							+ '<option value="0">'
							+ "Select Title"
							+ '</option>'
							+ '	<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">'
							+ '		<option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}" >${lookUp.lookUpDesc}</option> '
							+ '</c:forEach>'
							+ '</select>'
							+ '</td>	'
							+ '<td><input type="text" class="form-control" name="csmrInfo.ownerList['+cnt+'].ownerFirstName" id="ownerFName'+cnt+'"></input></td>'
							+ '<td><input  type="text" class="form-control" name="csmrInfo.ownerList['+cnt+'].ownerMiddleName" id="ownerMName'+cnt+'"></input></td>'
							+ '<td><input  type="text" class="form-control" name="csmrInfo.ownerList['+cnt+'].ownerLastName" id="ownerLName'+cnt+'"></input></td>'
							+'<td>'
							+ '<c:set var="baseLookupCode" value="GEN" />'
							+ '<select name="csmrInfo.ownerList['+cnt+'].gender" id="ownerGender'+cnt+'" class="form-control">'
							+ '<option value="0">'
							+ "Select Title"
							+ '</option>'
							+ '	<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">'
							+ '		<option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}" >${lookUp.lookUpDesc}</option> '
							+ '</c:forEach>'
							+ '</select>'
							+ '</td>'
							+ '<td><input  type="text" class="form-control hasNumber" name="csmrInfo.ownerList['+cnt+'].caoUID" id="ownerUID'+cnt+'"></input></td>';
					$('#tbl1 tr').last().after(
									'<tr id="tr'+cnt+'" class="ownerClass">'
											+ row
											+ '<td><a data-toggle="tooltip" data-placement="top" title="" class="btn btn-danger btn-sm" data-original-title="Delete Owner" id="deleteOwner" onclick="removeRow('
											+ cnt
											+ ')"><i class="fa fa-trash"></i></a></td></tr>');
					cnt++;
					reorderOwner();
		                }
		            else
					  {
					   showErrormsgboxTitle("water.enter.mand.fields");
					   }

				});

        
			 
			 $("#addConnection").click(function(){
				
				 var count=cntConnection-1;
				
					if($('#consumerNo'+count).val()!='')
							{
					 var row= 
						    '<td><input type="text" class="form-control" name="csmrInfo.linkDetails['+cntConnection+'].lcOldccn" id="consumerNo'+cntConnection+'"></input></td>'+
						    '<td>  <c:set var="baseLookupCode" value="CSZ" />'
							+ '<select name="csmrInfo.linkDetails['+cntConnection+'].lcOldccnsize" id="noOfTaps'+cntConnection+'" class="form-control" >'
							+ '<option value="0">'
							+ "Select Connection Size"
							+ '</option>'
							+ '	<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">'
							+ '		<option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}" >${lookUp.lookUpDesc}</option> '
							+ '</c:forEach>'
							+ '</select></td>'+
						   '<td><input  type="text" class="form-control" name="csmrInfo.linkDetails['+cntConnection+'].lcOldtaps"  id="noOfTaps'+cntConnection+'" ></input></td>';
						   $('#tbl2 tr').last().after('<tr id="row'+cntConnection+'" class="appendableClass">'+row+'<td><a data-toggle="tooltip" data-placement="top" title="" class="btn btn-danger btn-sm" data-original-title="Delete Connection" id="deleteConnection1" onclick="removeConnection('+cntConnection+')"><i class="fa fa-trash"></i></a></td></tr>');
						cntConnection++;
						reorderConnection();
							}
					else
					{
					showErrormsgboxTitle("water.dataentry.old.con.num.empty");
					}
			 
				
			});
			 
				
		});
function removeRow(cnt)
{
	if ($('#tbl1 tr').size() > 2) {
		$("#tr"+cnt).remove();
		reorderOwner();
		cnt--;
		} else {

				var msg = "can not remove";
				showErrormsgboxTitle(msg);
			}
	
	
	}
function removeConnection(cntConnection)
{
	if($('#tbl2 tr').size()>2){
	
		$("#row"+cntConnection).remove();
		reorderConnection();
		cntConnection--;
		}else{
			
			/* var msg = getLocalMessage('lgl.cantRemove');*/
			var msg="cant not remove";
				showErrormsgboxTitle(msg);
		}
	
	}

function reorderConnection()
{
	$('.appendableClass').each(function(i) {
	
		 //Ids
		$(this).find("input:text:eq(0)").attr("id", "consumerNo" + (i));
	    $(this).find("input:text:eq(1)").attr("id", "noOfTaps" + (i));
		$(this).find("select:eq(0)").attr("id", "connectionSize" + (i));
		$(this).closest("tr").attr("id", "row" + (i));
	    //names
		$(this).find("input:text:eq(0)").attr("name", "csmrInfo.linkDetails[" + (i) + "].lcOldccn");
		$(this).find("input:text:eq(1)").attr("name", "csmrInfo.linkDetails[" + (i) + "].lcOldtaps");
	    $(this).find("select:eq(0)").attr("name", "csmrInfo.linkDetails[" + (i) + "].lcOldccnsize");
	    $(this).find("#deleteConnection").attr("onclick", "removeConnection(" + (i) + ")");
		});

}
function reorderOwner()
{
	$('.ownerClass').each(function(i) {
		 //Ids
		$(this).find("input:text:eq(0)").attr("id", "ownerFName" + (i));
	    $(this).find("input:text:eq(1)").attr("id", "ownerMName" + (i));
	    $(this).find("input:text:eq(2)").attr("id", "ownerLName" + (i));
	    $(this).find("input:text:eq(3)").attr("id", "ownerUID" + (i));
		$(this).find("select:eq(0)").attr("id", "ownerTitle" + (i));
		$(this).find("select:eq(1)").attr("id", "ownerGender" + (i));
		$(this).closest("tr").attr("id", "tr" + (i));
	    //names
		$(this).find("input:text:eq(0)").attr("name", "csmrInfo.ownerList[" + (i) + "].ownerFirstName");
		$(this).find("input:text:eq(1)").attr("name", "csmrInfo.ownerList[" + (i) + "].ownerMiddleName");
		$(this).find("input:text:eq(2)").attr("name", "csmrInfo.ownerList[" + (i) + "].ownerLastName");
		$(this).find("input:text:eq(3)").attr("name", "csmrInfo.ownerList[" + (i) + "].caoUID");
	    $(this).find("select:eq(0)").attr("name", "csmrInfo.ownerList[" + (i) + "].ownerTitle");
	    $(this).find("select:eq(1)").attr("name", "csmrInfo.ownerList[" + (i) + "].gender");
	    $(this).find("#deleteOwner").attr("onclick", "removeRow(" + (i) + ")");
		});

}
</script>				
							
<apptags:breadcrumb></apptags:breadcrumb>

<!-- ============================================================== -->
<!-- Start Content here -->
<!-- ============================================================== -->
<div class="content">
	<!-- Start info box -->
	<div class="row">
		<div class="col-md-12">
			<div class="widget">
			<div class="widget-header">
				<h2><spring:message code="water.scrutiny.masg"/></h2></div>				
				<div class="widget-content padding">
				<form:form action="NewWaterConnectionForm.html"
						class="form-horizontal form" name="frmNewWaterForm"
						id="frmNewWaterForm">
					<!--New Connection-->
						<!-- <h4 class="margin-top-0">Applicant Information</h4> -->
							<div class="form-group">
				<%-- 
							<label class="col-sm-2 control-label required-control"><spring:message code="water.applicantType"/></label>
							<div class="col-sm-4">
								<c:set var="baseLookupCode" value="APT" /> <form:select
										path="cfcEntity.ccdApmType" class="form-control"
										id="applicantType">
											<form:option value="0"><spring:message code="water.sel.applType"/></form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
									</form:select>
						             </div> --%>
					 	
							
							<label class="col-sm-2 control-label required-control"><spring:message code="water.typeOfApplication"/>
								</label>
									<div class="col-sm-4">
									<form:select path="csmrInfo.typeOfApplication" class="form-control" id="typeOfApplication" disabled="true">
									<form:option value=" "><spring:message code="water.sel.typeAppl"/></form:option>
									<form:option value="P"><spring:message code="water.perm"/></form:option>
									<form:option value ="T"><spring:message code="water.temp"/></form:option>
									</form:select>
									</div>
									</div>
									<%-- <div class="form-group" id="OrgName">
									<label class="col-sm-2 control-label required-control"><spring:message code="water.owner.details.orgname"/></label>
							    <div class="col-sm-4">
							   <form:input path="csmrInfo.csOrgName" cssClass="form-control"  id="orgName"></form:input>
									</div>
									</div> --%>
									<div class="form-group" id="fromtoperiod">
					
							<label class="col-sm-2 control-label required-control"><spring:message code="water.fromPeriod"/></label>
							<div class="col-sm-4">
							<form:input path="csmrInfo.fromDate" cssClass="datepicker cal form-control"  id="fromdate"/>
							</div>
							<label class="col-sm-2 control-label required-control"><spring:message code="water.toPeriod"/></label>
							<div class="col-sm-4">
							<form:input path="csmrInfo.toDate" cssClass="datepicker cal form-control"  id="todate"/>
							</div>
							</div>
						
					
							<div class="form-group">
		         <label class="col-sm-2 control-label required-control"><spring:message code="applicantinfo.label.title"/></label>
		               <c:set var="baseLookupCode" value="TTL" />
		          <apptags:lookupField items="${command.getLevelData(baseLookupCode)}"
		  	path="cfcEntity.apmTitle" cssClass="form-control"
			hasChildLookup="false" hasId="true" showAll="false"
			selectOptionLabelCode="applicantinfo.label.select" isMandatory="true" />
		<label class="col-sm-2 control-label required-control"><spring:message code="applicantinfo.label.firstname"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"
				path="cfcEntity.apmFname" id="firstName"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label"><spring:message code="applicantinfo.label.middlename"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"
				path="cfcEntity.apmMname" id="middleName"></form:input>
		</div>
		<label class="col-sm-2 control-label required-control"><spring:message code="applicantinfo.label.lastname"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"
				path="cfcEntity.apmLname" id="lastName"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label required-control"><spring:message code="applicantinfo.label.gender"/></label>
			<c:set var="baseLookupCode" value="GEN" />
		<apptags:lookupField items="${command.getLevelData(baseLookupCode)}"
			path="applicantDetailDto.gender" cssClass="form-control"
			hasChildLookup="false" hasId="true" showAll="false"
			selectOptionLabelCode="applicantinfo.label.select" isMandatory="true" />
		<label class="col-sm-2 control-label required-control"><spring:message code="applicantinfo.label.mobile"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control hasNumber"
				maxlength="10" path="cfcAddressEntity.apaMobilno" id="mobileNo"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label"><spring:message code="applicantinfo.label.email"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"
				path="cfcAddressEntity.apaEmail" id="emailId"></form:input>
		</div>
		<label class="col-sm-2 control-label"><spring:message code="applicantinfo.label.flatbuildingno"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"
				path="cfcAddressEntity.apaFlatBuildingNo" id="flatNo"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label"><spring:message code="applicantinfo.label.buildingname"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"
				path="cfcAddressEntity.apaBldgnm" id="buildingName"></form:input>
		</div>
		<label class="col-sm-2 control-label"><spring:message code="applicantinfo.label.roadname"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"
				path="cfcAddressEntity.apaRoadnm" id="roadName"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label"><spring:message code="applicantinfo.label.blockname"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"
				path="cfcAddressEntity.apaBlockName" id="blockName"></form:input>
		</div>
		<label class="col-sm-2 control-label required-control"><spring:message code="applicantinfo.label.areaname"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"
				path="cfcAddressEntity.apaAreanm" id="areaName"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label required-control"><spring:message code="applicantinfo.label.villtowncity"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"
				path="cfcAddressEntity.apaCityName" id="villTownCity"></form:input>
		</div>
		<label class="col-sm-2 control-label required-control"><spring:message code="applicantinfo.label.pincode"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control hasNumber"
				path="cfcAddressEntity.apaPincode" id="pinCode" maxlength="6"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label"><spring:message code="applicantinfo.label.aadhaar"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control hasNumber"
				path="cfcEntity.apmUID" id="aadharNo" maxlength="12" data-mask="9999 9999 9999"/>
		</div>
		
		<label class="col-sm-2 control-label"><spring:message code="applicantinfo.label.bplno"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"
				path="cfcEntity.apmBplNo" id="bplNo" maxlength="50" />
		</div>
	
		
	</div>
<div class="form-group"> 
    <apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true" showOnlyLabel="false"
									pathPrefix="csmrInfo.codDwzid" isMandatory="false" hasLookupAlphaNumericSort="false" hasSubLookupAlphaNumericSort="false" cssClass="form-control" showAll="true"/>
 </div>

	

									
					<c:if test="${not empty command.csmrInfo.ownerList}">
						<h4><spring:message code="water.additionalOwner"/></h4>
						<div class="table-responsive">
							<table class="table table-bordered" id="tbl1">
								<tr >
									<th><spring:message code="water.title"/><span class="mand">*</span></th>
									<th><spring:message code="water.owner.details.fname"/><span class="mand">*</span></th>
									<th><spring:message code="water.owner.details.mname"/></th>
									<th><spring:message code="water.owner.details.lname"/><span class="mand">*</span></th>
									<th><spring:message code="water.owner.details.gender" text="Gender"/><span class="mand">*</span></th>
									<th><spring:message code="water.owner.details.uid" text="Adhar No."/></th>
									<th><a data-toggle="tooltip" data-placement="top" title=""
										class="btn btn-blue-2 btn-sm" data-original-title="Add Owner"
										id="addOwner" disabled="disabled"><i class="fa fa-plus"></i></a></th>
								</tr>
								
								<c:forEach items="${command.csmrInfo.ownerList}" var="ownerlist" varStatus="cnt">
							
								<tr id="tr${cnt.count-1}" class="ownerClass">
								<form:hidden path="csmrInfo.ownerList[${cnt.count-1}].cao_id"/>
									<td> 
								<c:set var="baseLookupCode" value="TTL" /> <form:select
										path="csmrInfo.ownerList[${cnt.count-1}].ownerTitle" class="form-control"
										id="ownerTitle0">
											<form:option value="0"><spring:message code="water.sel.title"/></form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
									</form:select>
								</td>
								<td><form:input name="" type="text" class="form-control"
											path="csmrInfo.ownerList[${cnt.count-1}].ownerFirstName" id="ownerFName"></form:input></td>
									<td><form:input name="" type="text" class="form-control"
											path="csmrInfo.ownerList[${cnt.count-1}].ownerMiddleName" id="ownerMName"></form:input></td>
									<td><form:input name="" type="text" class="form-control"
											path="csmrInfo.ownerList[${cnt.count-1}].ownerLastName" id="ownerLName"></form:input></td> 
											<td><c:set var="baseLookupCode" value="GEN" /> <form:select
										path="csmrInfo.ownerList[${cnt.count-1}].gender" class="form-control"
										id="ownerGender${cnt.count-1}">
											<form:option value="0"><spring:message code="water.sel.gen"/></form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
									</form:select></td>
									<td><form:input name="" type="text" class="form-control hasNumber"
											path="csmrInfo.ownerList[${cnt.count-1}].caoUID" id="ownerUID${cnt.count-1}"></form:input></td>
									<td><a  disabled="disabled" data-toggle="tooltip" data-placement="top" title=""
										class="btn btn-danger btn-sm"
										data-original-title="Delete Owner" id=deleteOwner onclick="removeRow(${cnt.count-1})"><i
											class="fa fa-trash"></i></a></td>
								</tr>
								</c:forEach>
					
							</table>
						</div>
						</c:if>
						
						
						<c:if test="${not empty command.csmrInfo.linkDetails}">
					  <h4 id="consumerDiv"><spring:message code="water.consumer.details" text="Existing Consumer Details"/></h4>
						<div class="table-responsive">
							<table class="table table-bordered" id="tbl2">
								<tr>
									<th><spring:message code="water.ConnectionNo"/><span class="mand">*</span></th>
									<th><spring:message code="water.ConnectionSize"/></th>
									<th><spring:message code="water.NoofTaps"/></th>
									<th><a data-toggle="tooltip" data-placement="top" title=""
										class="btn btn-blue-2 btn-sm"
										data-original-title="Add Connection" id="addConnection" disabled="disabled"><i
											class="fa fa-plus"></i></a></th>
								</tr>
								<c:forEach items="${command.csmrInfo.linkDetails}" var="connectionlist" varStatus="cnt">
								<tr id="row${cnt.count-1}" class="appendableClass">
							      <form:hidden path="csmrInfo.linkDetails[${cnt.count-1}].lcId"/>
									<td><form:input type="text" class="form-control"
											path="csmrInfo.linkDetails[${cnt.count-1}].lcOldccn" id="consumerNo${cnt.count-1}"></form:input></td>
									<td>
										
								<c:set var="baseLookupCode" value="CSZ" /> <form:select
										path="csmrInfo.linkDetails[${cnt.count-1}].lcOldccnsize" class="form-control"
										id="connectionSize${cnt.count-1}">
											<form:option value="0"><spring:message code="water.sel.connectionsize"/></form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
									</form:select>
									
											</td>
									<td><form:input type="text" class="form-control"
											path="csmrInfo.linkDetails[${cnt.count-1}].lcOldtaps" id="noOfTaps${cnt.count-1}"></form:input></td>
									<td><a disabled="disabled" data-toggle="tooltip" data-placement="top" title=""
										class="btn btn-danger btn-sm"
										data-original-title="<spring:message code="water.delete"/>" id=deleteConnection1 onclick="removeConnection(${cnt.count-1})"><i
											class="fa fa-trash"></i></a></td>
								</tr>
								</c:forEach>
							</table>
						</div>
						</c:if>
                          <div class="form-group padding-top-10">
               				 <label class="col-sm-2 control-label"><spring:message code="water.propertydetails.prtyNo"/></label>
  						  <div class="col-sm-4">
      					<form:input name="" type="text" class="form-control" path="reqDTO.propertyNo" id="properyNo"></form:input>
    							</div>
    						<label class="col-sm-2 control-label"><spring:message code="water.outStanding"/></label>
  						  <div class="col-sm-4">
      					<form:input name="" type="text" class="form-control" path="reqDTO.propertyOutStanding" id="outStanding"></form:input>
    							</div>	
    							
 							 </div>

						<!--Connection Details-->
						<h4><spring:message code="water.connectiondetails"/></h4>
						 <div class="form-group">
							
								  <apptags:lookupFieldSet baseLookupCode="CCG" hasId="true" showOnlyLabel="false"
									pathPrefix="csmrInfo.csCcncategory" isMandatory="false" hasLookupAlphaNumericSort="true" hasSubLookupAlphaNumericSort="true" cssClass="form-control" showAll="true"/>
  
             	</div>
						<div class="form-group">
                    <label class="col-sm-2 control-label"
								id="familymember"><spring:message code="water.noOfFamily"/></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control"
									path="csmrInfo.noOfFamilies" id="noOfPerson"></form:input>
							</div>
						

							<label class="col-sm-2 control-label"
								id="familymember"><spring:message code="water.noOfUser"/></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control"
									path="csmrInfo.csNoofusers" id="noOfPerson"></form:input>
							</div>
						</div>
						<div class="form-group">
								<apptags:lookupFieldSet cssClass="form-control required-control" baseLookupCode="TRF" hasId="true"
									pathPrefix="csmrInfo.trmGroup" isMandatory="true"
									hasLookupAlphaNumericSort="false"
									hasSubLookupAlphaNumericSort="false" />



							
						</div>

					 <div class="form-group">
                            
							<label class="col-sm-2 control-label"><spring:message code="water.plumber.licno"/></label>
							<div class="col-sm-4">

								<form:input name="" type="text" class="form-control"
									path="plumberNo" id="plumberNo"></form:input>
							</div>
							<label class="col-sm-2 control-label required-control"><spring:message code="water.road.applNo"/></label>
							<div class="col-sm-4">
								<form:input type="text" class="form-control"
											path="csmrInfo.applicationNo" id="applicationNo"></form:input>
											</div> 
											
							
						</div> 
                      <div class="form-group">
                 
                        <label class="col-sm-2 control-label required-control"><spring:message code="water.ConnectionSize"/></label>
							
                            <c:set var="baseLookupCode" value="CSZ" />
                            <apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="csmrInfo.csCcnsize" cssClass="form-control"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="pt.select" isMandatory="true" />
									
                     
                       <label class="col-sm-2 control-label"><spring:message code="water.NoofTaps"/></label>
                      	<div class="col-sm-4">
                      <form:input type="text" class="form-control"
											path="csmrInfo.csNooftaps" id="newnoOfTaps"></form:input>
                      </div>
                      </div>
                    
                     <c:if test="${not empty command.documentList}">	
							<fieldset class="fieldRound">
								<div class="overflow">
									<div class="table-responsive">
										<table class="table table-hover table-bordered table-striped">
											<tbody>
												<tr>
													<th><label class="tbold"><spring:message
																code="water.serialNo" text="Sr No" /></label></th>
													<th><label class="tbold"><spring:message
																code="water.docName" text="Document Name" /></label></th>
												     <th><label class="tbold"><spring:message
																code="water.download"/></label></th>
												</tr>

												<c:forEach items="${command.documentList}" var="lookUp"
													varStatus="lk">
													<tr>
														<td><label>${lookUp.clmSrNo}</label></td>
														<c:choose>
															<c:when
																test="${userSession.getCurrent().getLanguageId() eq 1}">
																<td><label>${lookUp.clmDescEngl}</label></td>
															</c:when>
															<c:otherwise>
																<td><label>${lookUp.clmDesc}</label></td>
															</c:otherwise>
														</c:choose>
														  <td>
														  <c:set var="links" value="${fn:substringBefore(lookUp.attPath, lookUp.attFname)}" />
													<apptags:filedownload filename="${lookUp.attFname}" filePath="${lookUp.attPath}" dmsDocId="${lookUp.dmsDocId}" actionUrl="NewWaterConnectionForm.html?Download"></apptags:filedownload>
					                                    </td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</fieldset>
					 </c:if> 
						
					
							 <div class="text-center padding-top-10">
						 <button type="button" class="btn btn-blue-2" onclick="editForm(this)" id="edit"><spring:message code="water.btn.edit"/></button>
						   <button type="button" class="btn btn-blue-2" onclick="showConfirmBoxForSave(this)" id="submitdiv"><spring:message code="water.btn.submit"/></button>
						 </div>
							 
              </form:form>
					<div id="scrutinyDiv">
					<jsp:include page="/jsp/cfc/sGrid/scrutinyButtonTemplet.jsp"></jsp:include>
                    </div>
				</div>
			</div>
		</div>
	</div>
	</div>




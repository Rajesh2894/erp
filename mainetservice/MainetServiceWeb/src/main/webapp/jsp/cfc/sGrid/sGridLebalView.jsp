<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/cfc/scrutiny.js"></script>
<style>
.table-scrutiny-qts {
	max-height: 17.6rem;
}
.table-scrutiny-qts table thead tr th {
	position: sticky;
    top: -1px;
    z-index: 1;
}
.table-scrutiny-qts table thead tr:nth-child(2) th {
	top: 2rem;
}
#frmScrutinyLabel .table-responsive {
	overflow-y: unset;
}
</style>

<script type="text/javascript">
$(document).ready(function() {
	//105334-> date of inspection should not be greater than license end date
	var licenseDate = $("#licsenseDate").val();
	
	if(licenseDate!="" && licenseDate!=null && licenseDate != undefined){
		$('.datepicker').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			//Defect #173199
			//minDate: 0,
			maxDate: licenseDate
			
		});
	}
	else {
		$('.datepicker').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			minDate: 0
		});
		}
	
	
		
 	/* $(".datepicker").datepicker({
	    readonly = true;
	}); */  
	
 	/* $(".nonEditable").datepicker({
	    editable = false;
	}); */  
	
 	
	$('#sUpload1').show();
	
	$('#sUpload2').hide();
	
	$('#sUpload3').hide();
	
	//var value	=	$("#coloumnCount").val();
	
	if($("#levelVal").val()==1)
	{
		$("#sendtobackId").hide();
		
	}
	
	/* for(var i=1;i<=value;i++)
	{
		addRowData(i);
	} */
	
	$("#addRow").click(function() 
	{
		
		var valuesgrid=$("#coloumnCount").val();
		
		var value	=parseInt(valuesgrid)+1;
		
		addRowData(value);
	});

	
	function addRowData(value)
	{
		if(value >= 1 && value <= 3)
		{
			if($('#sUpload'+1).is(":hidden"))
			$('#sUpload'+1).show();
			else if($('#sUpload'+2).is(":hidden"))
				$('#sUpload'+2).show();
			else if($('#sUpload'+3).is(":hidden"))
				$('#sUpload'+3).show();

			$("#coloumnCount").val(parseInt(value));
		
		}
		else
		{
			showErrormsgboxTitle(getLocalMessage('cfc.maxRow'));
		}
	}
	
});

function deleteFileRow(index)
{
	
		var value	=	$("#coloumnCount").val();
		
		if(value == 1)
		{
			showErrormsgboxTitle(getLocalMessage('cfc.minRow'));
		}
		else
		{
			if(value >= 1 && value <= 3)
			{
				$('#docRemark'+(parseInt(index))).val('');
				$('#sUpload'+(index+1)).hide();
				
				var fileid = $('#0_file_'+(parseInt(index)));
				
				value = parseInt(value) - 1;
				
				$("#coloumnCount").val(value);
				
				if(fileid.length)
				{
					doFileDelete(fileid);
				}
			}
		}
}

/* 
 $( document ).ajaxComplete(function() {

	debugger;
	$(".datepicker").datepicker({
	    dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		changeYear: true
	});
	
}); */


</script>

<ol class="breadcrumb">
	   	  <li><a href="AdminHome.html"><spring:message code="menu.home"/></a></li>
	      <li><a href="javascript:void(0);"><spring:message code="cfc.cfcHeader"/></a></li>
	      <li><a href="javascript:void(0);"><spring:message code="audit.transactions"/></a></li>
	      <li><spring:message code="cfc.sGridHeader" text="Scrutiny Label View"/></li>
</ol>
     <div class="content"> 
      <!-- Start info box -->
      <div class="widget">
        <div class="widget-header">
          <h2><spring:message code="cfc.sGridHeader" text="Scrutiny Label View"/></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
        </div>
        <div class="widget-content padding form-horizontal">
        <jsp:include page="/jsp/tiles/validationerror.jsp" />
             <div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
				<ul>
					<li><label id="errorId"></label></li>
				</ul>
			</div>
    <h4 class="margin-top-0"><spring:message code="cfc.applicantdetail"  text="Applicant Details"/>:</h4>
			<div class="form-group">
                <label class="col-sm-2 control-label">
                	<spring:message code="checklistVerification.serviceName" text="Service Name"/>:
                </label>
                <div class="col-sm-4">
                	<span class="form-control height-auto">${command.scrutinyLabelDTO.serviceName}</span>
                </div>
				<label class="col-sm-2 control-label">
					<spring:message code="TCP.applicant.name" text="Name Of Applicant"/>:
				</label>
				<div class="col-sm-4">
					<span class="form-control height-auto">${command.scrutinyLabelDTO.applicantName}</span>
				</div>
			</div>

			<div class="form-group">
                <label class="col-sm-2 control-label">
                	<spring:message code="cfc.applId" text="Application Id"/>:</label>
				<c:choose>
                <c:when test="${not empty command.scrutinyLabelDTO.refNo}">
                	<div class="col-sm-4">
					<span class="form-control">${command.scrutinyLabelDTO.refNo}</span>
				</div>
                </c:when>
                <c:otherwise>
				<div class="col-sm-4">
					<span class="form-control">${command.scrutinyLabelDTO.applicationId}</span>
				</div>
				</c:otherwise>
				</c:choose>
				    <label class="col-sm-2 control-label">
                	<spring:message code="master.loi.applicant.mob" text="Applicant Mobile No"/>:</label>
				<div class="col-sm-4">
					<span class="form-control">${command.scrutinyLabelDTO.mobNo}</span>
				</div>
			</div>
			<div class="form-group">
                <label class="col-sm-2 control-label">
                	<spring:message code="cfc.applicant.email" text="Applicant Email Id"/>:</label>
				<div class="col-sm-4">
					<span class="form-control">${command.scrutinyLabelDTO.email}</span>
				</div>
			</div>
  
    <h4><spring:message code="cfc.label" text="Label Details"/></h4>

    
              
      <c:set value="${userSession.employee.designation.dsgid}" var="dsgId" />
      <form:form action="ScrutinyLabelView.html" name="frmScrutinyLabel" id="frmScrutinyLabel" class="form">
      <div class="table-responsive table-scrutiny-qts">
	      <form:hidden path="scrutinyLabelDTO.coloumnCount" id="coloumnCount" />
	      <form:hidden path="scrutinyLabelDTO.licsenseDate" id="licsenseDate" />
	      <form:hidden path="scrutinyLabelDTO.reamrkValidFlag" id="reamrkValidFlag" />
	      
	        <table class="table table-hover table-bordered table-striped">
	          <thead>
		          <tr>
		            <th width="6%"><spring:message code="tp.serialNo" text="Sr No"/></th>
		            <th width="65%"><spring:message code="cfc.scrutinyqn" text="Scrutiny Question"/></th>
		            <th width="17%"><spring:message code="cfc.Answer" text="Answer"/></th>
		            <th width="10%"><spring:message code="cfc.View" text="View"/></th>
		          </tr>
		      </thead>
		          <c:forEach items="${command.scrutinyLabelDTO.desgWiseScrutinyLabelMap}" var="desgMapVar">
		          <tr>
	              	<th colspan="4" >${command.scrutinyLabelDTO.desgNameMap.get(desgMapVar.key)}</th>
	              </tr>
	            
	          
	            
	          <c:forEach items="${command.scrutinyLabelDTO.desgWiseScrutinyLabelMap}" var="desgMap">
		          <tbody>
		            <c:forEach items="${desgMap.value}" var="lookUp" varStatus="lk">  
		            <!-- setting condition for bifurcating role code wise label for D#131765 -->
		            <c:if test="${lookUp.slDsgid ==desgMapVar.key}">       
		            <tr>
		                <td>${lk.count}</td>
		                <td>
		               
		                	<c:if test="${userSession.languageId eq '1'}">
								<span>${lookUp.slLabel}</span>
							</c:if>
							<c:if test="${userSession.languageId eq '2'}">
								<span>${lookUp.slLabelMar}</span>
							</c:if>
		           
		               </td>
		                <td>
		                 <c:choose>
		                    <c:when test="${fn:contains(command.scrutinyLabelDTO.roleId, lookUp.slDsgid)}">
		                      <c:if test="${fn:containsIgnoreCase(lookUp.slDatatype, 'Number')}">
		                        <form:input path="scrutinyLabelDTO.desgWiseScrutinyLabelMap[${lookUp.slDsgid}][${lk.index}].svValue" id="lValue${lookUp.slLabelId}" cssClass="hasDecimal form-control" onclick="validateOnBlur(this,'${lookUp.slLabelId}','${lookUp.levels}')"   onchange="saveLableValue(this,'${lookUp.slLabelId}','${lookUp.levels}')"  />
		                      </c:if>
		                      <c:if test="${fn:containsIgnoreCase(lookUp.slDatatype, 'Varchar2')}">
		                      <form:input path="scrutinyLabelDTO.desgWiseScrutinyLabelMap[${lookUp.slDsgid}][${lk.index}].svValue" id="lValue${lookUp.slLabelId}" cssClass="form-control nonEditable" onclick="validateOnBlur(this,'${lookUp.slLabelId}','${lookUp.levels}')" onchange="saveLableValue(this,'${lookUp.slLabelId}','${lookUp.levels}')" editable="false"/>
		                      </c:if>
		                      <c:if test="${fn:containsIgnoreCase(lookUp.slDatatype, 'Date')}">
		                        <form:input path="scrutinyLabelDTO.desgWiseScrutinyLabelMap[${lookUp.slDsgid}][${lk.index}].svValue" id="lValue${lookUp.slLabelId}" cssClass="datepicker form-control"  maxlength="10" placeholder="dd/mm/yyyy" onclick="validateOnBlur(this,'${lookUp.slLabelId}','${lookUp.levels}')"  onchange="saveLableValue(this,'${lookUp.slLabelId}','${lookUp.levels}')" />
		                      </c:if>
		                      <c:if test="${fn:containsIgnoreCase(lookUp.slDatatype, 'List')}">
		                     <c:set var="disableflag" value="${command.scrutinyLabelDTO.desgWiseScrutinyLabelMap[lookUp.slDsgid][lk.index].svValue}"/>
		                     <form:hidden path="" id="levelVal" value="${lookUp.levels}"/>
		             
		                     <c:set var="Textflag" value="${lookUp.slValidationText}" property="textflagId"/>
		                    
		                        <form:select path="scrutinyLabelDTO.desgWiseScrutinyLabelMap[${lookUp.slDsgid}][${lk.index}].svValue" id="lValue${lookUp.slLabelId}" cssClass="form-control" maxlength="10"  onchange="onChangeLabel(this,'${lookUp.slLabelId}','${lookUp.levels}','${lookUp.slFormName}')"  tabindex="${lk.count}">										
									<form:option value="-1">
									<spring:message code="siteinspection.vldn.answer" />
									</form:option>
									<c:forEach items="${command.scrutinyLabelDTO.dislist}" var="obj">	
										<form:option value="${obj}" label="${obj}"><spring:message code="${obj}" text="${obj}"/></form:option>
									</c:forEach>
								</form:select>         
		                     </c:if>                                                              
		                    </c:when>
		                    <c:otherwise>
		                      <label>${lookUp.svValue}</label>
		                     
		                    </c:otherwise>
		                  </c:choose>
		                </td>
		                <td class="text-center"><c:choose>
		                    <c:when test="${(not empty lookUp.slFormName) and (fn:contains(command.scrutinyLabelDTO.roleId, lookUp.slDsgid))}"> 
								<div>
		                           <input id="lDiv${lookUp.slLabelId}"  ${disableflag eq Textflag  ?  ' ' : 'disabled="disabled"'} title="View Details" value='<spring:message code="cfc.View" text="View"/>' class="btn btn-blue-2 btn-sm cal"  type="button"  onclick ="openScrutinyForm(this,${command.scrutinyLabelDTO.applicationId},'${lookUp.slFormName}','${lookUp.slFormMode}','${lookUp.slLabelId}','${lookUp.levels}','${command.scrutinyLabelDTO.smServiceId}')" >							
								</div>
							</c:when>
		                    <c:otherwise> <span></span> </c:otherwise>
		                  </c:choose>
		                  </td>
		              </tr>
		              </c:if>
		            </c:forEach>
		          </tbody>
	          </c:forEach>
	         </c:forEach>
	        </table>
      </div>
    <c:if test="${fn:length(command.scrutinyLabelDTO.dsgWiseScrutinyDocMap) gt 0}">
    <h4><spring:message code="cfc.uoloadedDoc" text="Uploaded Document"/>:</h4>
    <div class="table-responsive">
    	<table class="table table-hover table-bordered table-striped">
				<tr>
					<th><spring:message code="cfc.designation" text="Designation" /></th>
					<th><spring:message code="cfc.download" text="Download" /></th>
					<th><spring:message code="checklistVerification.docDesc" text="Document Description" /></th>
				</tr>
				<c:forEach var="entry" items="${command.scrutinyLabelDTO.dsgWiseScrutinyDocMap}">
					<c:forEach var="lookUp" items="${entry.value}" varStatus="lk">
					<tr>
						<c:if test="${lk.index eq 0}">					
							<td rowspan="${fn:length(entry.value)}">${command.scrutinyLabelDTO.desgNameMap.get(entry.key)}</td>
						</c:if>
							<td>
							<form action="ScrutinyLabelView.html?Download" method="post"
								id="frm${idappender}_${status.count}" target="_blank"></form>
							<div>
								<c:set var="links" value="${fn:split(lookUp.lookUpDesc,',')}" />
								<c:forEach items="${links}" var="download" varStatus="status">
									<apptags:filedownload filename="${lookUp.lookUpCode}" filePath="${download}" dmsDocId="${lookUp.lookUpType}" actionUrl="ScrutinyLabelView.html?Download"></apptags:filedownload>
								</c:forEach>
							</div>
							</td>
							<td>${lookUp.otherField}</td>
					</tr>
					</c:forEach>	
				</c:forEach>
			</table>
	</div>
	</c:if>
		 <div class="table-responsive">
              <table class="table table-hover table-bordered table-striped" id="caseHearingTable">
					<tr>								
						<th><spring:message code="eip.agency.upload" /></th>
						<c:if test="${command.scrutinyLabelDTO.reamrkValidFlag eq 'Y'}">
						<th><spring:message code="checklistVerification.docDesc" text="Document Description" /></c:if>
						<c:if test="${command.scrutinyLabelDTO.reamrkValidFlag ne 'Y'}">
						<th><spring:message code="checklistVerification.docDesc" text="Document Description" /></th></c:if>
						<th><input type="button" value="+" id="addRow" class="btn btn-success"></th>
					</tr>
			
				<tr id="sUpload1">										
					<td>					
					<apptags:formField fieldType="7" labelCode="" hasId="true" folderName="0" maxFileCount="CHECK_LIST_MAX_COUNT"
							fieldPath="scrutinyDocs[0].attPath" fileSize="FILE_SIZE"
							validnFunction="CHECK_LIST_VALIDATION_EXTENSION"  isMandatory="false"
							showFileNameHTMLId="true" currentCount="0" />
						<small class="text-blue-2"> <spring:message code="cfc.doc.sizeAndType"
						               text="(Upload Document File upto 20 MB)" />
						</small>
					</td>
					<td ><form:textarea path="scrutinyDocs[0].clmRemark" id="docRemark0"  cssClass="form-control"/></td>
					<td class="text-center"><input type="button" value="-" class="btn btn-danger removeRow" onclick="deleteFileRow(0)"></td>
					
				</tr>
				
				<tr id="sUpload2">										
					<td>
					<apptags:formField fieldType="7" labelCode="" hasId="true" folderName="1" maxFileCount="CHECK_LIST_MAX_COUNT"
							fieldPath="scrutinyDocs[1].attPath" fileSize="COMMOM_MAX_SIZE"
							validnFunction="CHECK_LIST_VALIDATION_EXTENSION"  isMandatory="false"
							showFileNameHTMLId="true" currentCount="1" />
						<small class="text-blue-2"> <spring:message code="cfc.doc.sizeAndType"
						               text="(Upload Document File upto 20 MB)" />
						</small>	
					</td>
					<td ><form:textarea path="scrutinyDocs[1].clmRemark" id="docRemark1" cssClass="form-control"/></td>
					<td class="text-center"><input type="button" value="-"  class="btn btn-danger removeRow" onclick="deleteFileRow(1)"></td>
					
				</tr>
				<tr id="sUpload3">										
					<td>
					<apptags:formField fieldType="7" labelCode="" hasId="true" folderName="2" maxFileCount="CHECK_LIST_MAX_COUNT"
							fieldPath="scrutinyDocs[2].attPath" fileSize="COMMOM_MAX_SIZE"
							validnFunction="CHECK_LIST_VALIDATION_EXTENSION"  isMandatory="false"
							showFileNameHTMLId="true" currentCount="2" />
						<small class="text-blue-2"> <spring:message code="cfc.doc.sizeAndType"
						               text="(Upload Document File upto 20 MB)" />
						</small>
					</td>
					<td ><form:textarea path="scrutinyDocs[2].clmRemark" id="docRemark2" cssClass="form-control"/></td>
					<td class="text-center"><input type="button" value="-"  class="btn btn-danger removeRow" onclick="deleteFileRow(2)"></td>
					
				</tr>
		</table>
		</div>
		
		
		
	 	<div class="form-group padding-top-10">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="final.decision" text="Final Decision" /></label>
					<div class="col-sm-4">
						<form:select path="wokflowDecision" cssClass="form-control" id="wokflowDecision">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<form:option value="APPROVED">
								<spring:message code="workflow.action.decision.approved" text="APPROVED" />
							</form:option>
							<form:option value="REJECTED">
								<spring:message code="workflow.action.decision.rejected" text="REJECTED" />
							</form:option>
						</form:select>
					</div>	
					
					<c:choose>
						<c:when test="${command.scrutinyLabelDTO.reamrkValidFlag eq 'Y'}">
							<apptags:input labelCode="cfc.remark" path="scrutinyDecisionRemark" isMandatory="true"></apptags:input>
						</c:when>
						<c:otherwise>
							<apptags:input labelCode="cfc.remark" path="scrutinyDecisionRemark"></apptags:input>
						</c:otherwise>
					</c:choose>					
				</div> 
				
        <div class="text-center padding-top-10"> 
        		<input type="submit" id="sendtobackId" class="btn btn-danger" onclick="return saveScrutinyLabels(this,'<spring:message code="cfc.scrSave" />','sendToback','AdminHome.html');" value="<spring:message code="scrutiny.sendToBack" />" />
  			    <input type="submit" class="btn btn-blue-2" onclick="return saveScrutinyLabels(this,'<spring:message code="cfc.scrSave" />','saveScrutinyValue','AdminHome.html');" value="<spring:message code="scrutiny.save" />" />
  				<input type="submit" class="btn btn-success btn-submit" onclick="return submitScrutinyLabels(this,'<spring:message code="cfc.scrSave" />','submitScrutinyValue','AdminHome.html');" value="<spring:message code="scrutiny.submit" />" />
            <apptags:resetButton cssClass="btn btn-warning"  />
          <%--   <apptags:backButton url="AdminHome.html"></apptags:backButton>  --%>
           <input type="button"  class="btn btn-danger" onclick="backToApplicationForm(${userSession.scrutinyCommonParamMap.APM_APPLICATION_ID},${userSession.scrutinyCommonParamMap.taskId},'ApplicationAuthorization.html',${userSession.scrutinyCommonParamMap.SM_SERVICE_ID},${userSession.scrutinyCommonParamMap.workflowId})" value="<spring:message code="back.msg" />">
		</div>
      </form:form>
    </div>
  </div>
</div></div>

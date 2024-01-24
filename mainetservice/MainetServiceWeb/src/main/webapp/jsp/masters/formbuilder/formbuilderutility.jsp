<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/masters/formbuilder/formbuilderutility.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	debugger;
	
	$(".datepicker").datepicker({
	    dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		changeYear: true
	});  
	
	$('#sUpload1').hide();
	
	$('#sUpload2').hide();
	
	$('#sUpload3').hide();
	
	var value	=	$("#coloumnCount").val();
	
	if($("#levelVal").val()==1)
	{
		$("#sendtobackId").hide();
		
	}
	
	for(var i=1;i<=value;i++)
	{
		addRowData(i);
	}
	
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
			$('#sUpload'+value).show();

			$("#coloumnCount").val(parseInt(value));
		}
		else
		{
			showErrormsgboxTitle(getLocalMessage('cfc.maxRow'));
		}
	}
	
	$("#removeRow").click(function()
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
					$('#sUpload'+value).hide();
					
					var fileid = $('#0_file_'+(parseInt(value) - 1));
					
					value = parseInt(value) - 1;
					
					$("#coloumnCount").val(value);
					
					if(fileid.length)
					{
						doFileDelete(fileid);
					}
					
					$('#docRemark'+(parseInt(value) - 1)).val('');
				}
			}
	}); 
	
});

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

<%-- <ol class="breadcrumb">
	   	  <li><a href="AdminHome.html"><spring:message code="menu.home"/></a></li>
	      <li><a href="javascript:void(0);"><spring:message code="cfc.cfcHeader"/></a></li>
	      <li><a href="javascript:void(0);"><spring:message code="audit.transactions"/></a></li>
	      <li><spring:message code="cfc.sGridHeader" text="Scrutiny Label View"/></li>
</ol> --%>
      <!-- Start info box -->
      <div class="widget">
        <div class="widget-header">
          <h2><spring:message code="common.master.form.builder.util" text="Form Builder Utility"/></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
        </div>
        <div class="widget-content padding form-horizontal">
        
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
			</div>

  
    <h4><spring:message code="cfc.label" text="Label Details"/></h4>

    <div class="table-responsive">
              
      <c:set value="${userSession.employee.designation.dsgid}" var="dsgId" />
      <form:form action="FormBuilderUtility.html" name="frmScrutinyLabel" id="frmScrutinyLabel" class="form">
      
      <form:hidden path="scrutinyLabelDTO.coloumnCount" id="coloumnCount" />
        <table class="table table-hover table-bordered table-striped">
          <tr>
            <th width="6%"><spring:message code="common.master.seq.report" text="Report Parameter Sequence No."/></th>
            <th width="65%"><spring:message code="cfc.qn" text="Question"/></th>
            <th width="17%"><spring:message code="cfc.Answer" text="Answer"/></th>
           <%--  <th width="10%"><spring:message code="cfc.View" text="View"/></th> --%>
          </tr>
            
          <c:forEach items="${command.scrutinyLabelDTO.desgWiseScrutinyLabelMap}" var="desgMap">
            <tr>
              <th colspan="5" >${command.scrutinyLabelDTO.desgNameMap.get(desgMap.key)}</th>
            </tr>
            <c:forEach items="${desgMap.value}" var="lookUp" varStatus="lk">         
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
<%--                     <c:when test="${fn:contains(command.scrutinyLabelDTO.roleId, lookUp.slDsgid)}"> --%>
                    <c:when test="${command.mode ne 'V'}">
                    <c:if test="${command.mode ne 'C'}">
                      <form:hidden path="scrutinyLabelDTO.desgWiseScrutinyLabelMap[${command.scrutinyLabelDTO.roleId}][${lk.index}].formId" id="formId"
													disabled="${command.mode eq 'V'}" />
					</c:if>
                      <c:if test="${fn:containsIgnoreCase(lookUp.slDatatype, 'Text Box')}">
                        <form:input path="scrutinyLabelDTO.desgWiseScrutinyLabelMap[${command.scrutinyLabelDTO.roleId}][${lk.index}].svValue" id="lValue${lookUp.slLabelId}" cssClass="form-control"    tabindex="${lk.count}" maxLength="200" disabled="${command.mode eq 'V' ? true : false }"/>
                      </c:if>
                       <c:if test="${fn:containsIgnoreCase(lookUp.slDatatype, 'Text Area')}">
                        <form:textarea path="scrutinyLabelDTO.desgWiseScrutinyLabelMap[${command.scrutinyLabelDTO.roleId}][${lk.index}].svValue" id="lValue${lookUp.slLabelId}" cssClass="form-control"    tabindex="${lk.count}" maxLength="400" disabled="${command.mode eq 'V' ? true : false }"/>
                      </c:if>
                       <c:if test="${fn:containsIgnoreCase(lookUp.slDatatype, 'Check Box')}">
                        <form:checkbox id="lValue${lookUp.slLabelId}" path="scrutinyLabelDTO.desgWiseScrutinyLabelMap[${command.scrutinyLabelDTO.roleId}][${lk.index}].svValue" value="Y"  cssClass="margin-left-50" disabled="${command.mode eq 'V' ? true : false }"/>
                      </c:if>
                      <%-- <c:if test="${fn:containsIgnoreCase(lookUp.slDatatype, 'Radio')}">
                        <form:radiobutton path="scrutinyLabelDTO.desgWiseScrutinyLabelMap[${command.scrutinyLabelDTO.roleId}][${lk.index}].svValue" value="D" id="lValue${lookUp.slLabelId}" cssClass="margin-left-50" disabled="${command.mode eq 'V' ? true : false }"/>
                      </c:if> --%>
                    <c:if test="${fn:containsIgnoreCase(lookUp.slDatatype, 'Geo Tag')}">
									<form:hidden path="filePath" value="${command.filePath}" id="hiddenlocPath" />

									<div class="form-group">
										<c:if test="${command.mode ne 'V' }">
											<div id="uploadDoc">
												<%-- <label class="control-label col-sm-2"><spring:message
														code="location.uploadImage" /></label> --%>
												<div class="col-sm-4">
													<apptags:formField fieldType="7" labelCode=""
														hasId="true" fieldPath="geoTagDoc[0].doc_DESC_ENGL" isMandatory="false"
														showFileNameHTMLId="true"
														fileSize="BND_COMMOM_MAX_SIZE"
														maxFileCount="CHECK_LIST_MAX_COUNT"
														validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
														currentCount="100" />
												</div>
											</div>
										</c:if>
										<c:if test="${command.mode ne 'C'}">
										<c:forEach items="${command.geoTagDocsList}" var="doc">
											<apptags:filedownload filename="${doc.attFname}"
														filePath="${doc.attPath}"
														actionUrl="FormBuilderUtility.html?Download" />
										</c:forEach>
										</c:if>
									</div>
									<form:hidden path="" id="locChnage" />
									<div class="form-group">
										<label class="control-label col-sm-6" for="latitudeId">
										<spring:message code="locationMas.latitude" />/<spring:message
												code="locationMas.longitude" /></label><span id="latlong" class="col-sm-12"> ${lookUp.svValue}</span>
										<div class="col-sm-4">
											<form:hidden path="scrutinyLabelDTO.desgWiseScrutinyLabelMap[${command.scrutinyLabelDTO.roleId}][${lk.index}].svValue" id="latitudeId"
													disabled="${command.mode eq 'V'}" />
											
										</div>
										

										<%-- <label class="control-label col-sm-2" for="longitudeId"><spring:message
												code="locationMas.longitude" /></label> --%>
										<%-- <div class="col-sm-4">
											<form:input path="scrutinyLabelDTO.desgWiseScrutinyLabelMap[${command.scrutinyLabelDTO.roleId}][${lk.index}].svValue"
												id="longitudeId" type="text" class="form-control"
												disabled="${command.mode eq 'V'}" />
										</div> --%>
									</div>


								</c:if>
                     <%--   <c:if test="${fn:containsIgnoreCase(lookUp.slDatatype, 'File Upload')}">
                      	 -- File Upload --
                      </c:if> --%>
                      
                      <c:if test="${fn:containsIgnoreCase(lookUp.slDatatype, 'Date')}">
                        <form:input path="scrutinyLabelDTO.desgWiseScrutinyLabelMap[${command.scrutinyLabelDTO.roleId}][${lk.index}].svValue" id="lValue${lookUp.slLabelId}" cssClass="datepicker form-control"  maxlength="10" placeholder="dd/mm/yyyy"  tabindex="${lk.count}" disabled="${command.mode eq 'V' ? true : false }"/>
                      </c:if>
                      <c:if test="${fn:containsIgnoreCase(lookUp.slDatatype, 'List')}">
                     <c:set var="disableflag" value="${command.scrutinyLabelDTO.desgWiseScrutinyLabelMap[lookUp.slDsgid][lk.index].svValue}"/>
                     <form:hidden path="" id="levelVal" value="${lookUp.levels}"/>
             
                     <c:set var="Textflag" value="${lookUp.slValidationText}" property="textflagId"/>
                    
                    	<c:set value="${lookUp.slPreValidation}" var="prefix"/>
                    	<c:choose>
                    		<c:when test="${not empty prefix }">
								<form:select class="form-control "
									path="scrutinyLabelDTO.desgWiseScrutinyLabelMap[${command.scrutinyLabelDTO.roleId}][${lk.index}].svValue" id="lValue${lookUp.slLabelId}">
									<form:option value="-1">
										<spring:message code="Select" text="Select" />
									</form:option>
									<c:forEach items="${command.getLevelData(prefix)}" var="prfixItem">
											<form:option value="${prfixItem.lookUpDesc}" code="${prfixItem.lookUpCode}">${prfixItem.lookUpDesc}</form:option>
									</c:forEach>
								</form:select>
							</c:when>
                    		<c:otherwise>
									<form:select
										path="scrutinyLabelDTO.desgWiseScrutinyLabelMap[${command.scrutinyLabelDTO.roleId}][${lk.index}].svValue"
										id="lValue${lookUp.slLabelId}" cssClass="form-control"
										maxlength="10" tabindex="${lk.count}"
										disabled="${command.mode eq 'V' ? true : false }">
										<form:option value="-1">
											<spring:message code="siteinspection.vldn.answer" />
										</form:option>
										<c:forEach items="${command.scrutinyLabelDTO.dislist}"
											var="obj">
											<form:option value="${obj}" label="${obj}"></form:option>
										</c:forEach>
									</form:select>
							</c:otherwise>
                    	</c:choose>
                              
                     </c:if>                                                              
                    </c:when>
                    <c:otherwise>
                      <label>${lookUp.svValue}</label>
                      
                      
                      <c:if test="${fn:containsIgnoreCase(lookUp.slDatatype, 'Geo Tag')}">
									<form:hidden path="filePath" value="${command.filePath}" id="hiddenlocPath" />

									<div class="">
										
										<c:if test="${command.mode ne 'C'}">
										<c:forEach items="${command.geoTagDocsList}" var="doc">
											<apptags:filedownload filename="${doc.attFname}"
														filePath="${doc.attPath}"
														actionUrl="FormBuilderUtility.html?Download" />
										</c:forEach>
										</c:if>
									</div>


								</c:if>
                      
                      
                    </c:otherwise>
                  </c:choose>
                </td>
               <%--  <td><c:choose>
                    <c:when test="${(not empty lookUp.slFormName) and (fn:contains(command.scrutinyLabelDTO.roleId, lookUp.slDsgid))}"> 
						<div>
                           <input id="lDiv${lookUp.slLabelId}"  ${disableflag eq Textflag  ?  ' ' : 'disabled="disabled"'} title="View Details" value="View" class="btn btn-blue-2 btn-sm cal"  type="button"  onclick ="openScrutinyForm(this,${command.scrutinyLabelDTO.applicationId},'${lookUp.slFormName}','${lookUp.slFormMode}','${lookUp.slLabelId}','${lookUp.levels}','${command.scrutinyLabelDTO.smServiceId}')" >							
						</div>
					</c:when>
                    <c:otherwise> <span></span> </c:otherwise>
                  </c:choose>
                  </td> --%>
              </tr>
            </c:forEach>
          </c:forEach>
        </table>
  <%--   <c:if test="${fn:length(command.scrutinyLabelDTO.dsgWiseScrutinyDocMap) gt 0}">
    <h4><spring:message code="cfc.uoloadedDoc" text="Uploaded Document"/>:</h4>
    	<table class="table table-hover table-bordered table-striped">
				<tr>
					<th><spring:message code="cfc.designation" text="Designation" /></th>
					<th><spring:message code="cfc.download" text="Download" /></th>
					<th><spring:message code="cfc.remark" text="Remark" /></th>
				</tr>
				<c:forEach var="entry" items="${command.scrutinyLabelDTO.dsgWiseScrutinyDocMap}">
					<c:forEach var="lookUp" items="${entry.value}" varStatus="lk">
					<tr>
						<c:if test="${lk.index eq 0}">					
							<td rowspan="${fn:length(entry.value)}">${command.scrutinyLabelDTO.desgNameMap.get(entry.key)}</td>
						</c:if>
							<td>
							<form action="FormBuilderUtility.html?Download" method="post"
								id="frm${idappender}_${status.count}" target="_blank"></form>
							<div>
								<c:set var="links" value="${fn:split(lookUp.lookUpDesc,',')}" />
								<c:forEach items="${links}" var="download" varStatus="status">
									<apptags:filedownload filename="${lookUp.lookUpCode}" filePath="${download}" dmsDocId="${lookUp.lookUpType}" actionUrl="FormBuilderUtility.html?Download"></apptags:filedownload>
								</c:forEach>
							</div>
							</td>
							<td>${lookUp.otherField}</td>
					</tr>
					</c:forEach>	
				</c:forEach>
			</table>
		</c:if> --%>
		 <!-- FILE UPLOAD------------------------------------------------------------------------------------------------>
				
				<div class="panel-group accordion-toggle"
				id="accordion_single_collapse">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="" class=""
								data-parent="#accordion_single_collapse" href="#a1"> <spring:message
									code="property.documentsupload" text="Documents Upload" /></a>
						</h4>
					</div>
					<div id="a1" class="panel-collapse collapse in">
						<form:hidden path="removeCommonFileById" id="removeCommonFileById" />

						<div class="panel-body">

							<c:if test="${fn:length(command.attachDocsList)>0}">
								<div class="table-responsive">
									<table class="table table-bordered table-striped"
										id="deleteCommonDoc">
										<tr>
											<th width="" align="center"><spring:message
													code="ser.no" text="" /><input type="hidden" id="srNo"></th>
											<th scope="col" width="64%" align="center"><spring:message
													code="work.estimate.document.description"
													text="Document Description" /></th>
											<th scope="col" width="30%" align="center"><spring:message
													code="scheme.view.document" /></th>
											<c:if test="${command.mode ne 'V'}">
												<th scope="col" width="8%"><spring:message
														code="works.management.action" text=""></spring:message></th>
											</c:if>
										</tr>
										<c:set var="e" value="0" scope="page" />
										<c:forEach items="${command.attachDocsList}" var="lookUp">
											<tr>
												<td>${e+1}</td>
												<td>${lookUp.dmsDocName}</td>
												<td><apptags:filedownload filename="${lookUp.attFname}"
														filePath="${lookUp.attPath}"
														actionUrl="FormBuilderUtility.html?Download" /></td>
												<c:if test="${command.mode ne 'V'}">
													<td class="text-center"><a href='#'
														id="deleteCommonFile" onclick="return false;"
														class="btn btn-danger btn-sm"><i class="fa fa-trash"></i></a>
														<form:hidden path="" value="${lookUp.attId}" /></td>
												</c:if>
											</tr>
											<c:set var="e" value="${e + 1}" scope="page" />
										</c:forEach>
									</table>
								</div>
								<br>
							</c:if>

							<div id="doCommonFileAttachment">
								<div class="table-responsive">
									<c:set var="cd" value="0" scope="page" />
									<c:if test="${command.mode ne 'V'}">
										<table class="table table-bordered table-striped"
											id="attachCommonDoc">
											<tr>
												<th><spring:message
														code="work.estimate.document.description"
														text="Document Description" /></th>
												<th><spring:message code="work.estimate.upload"
														text="Upload Document" /></th>
												<th scope="col" width="8%"><a
													onclick='doCommonFileAttachment(this);'
													class="btn btn-blue-2 btn-sm addButton"><i
														class="fa fa-plus-circle"></i></a></th>
											</tr>

											<tr class="appendableUploadClass">


												<td><form:input
														path="commonFileAttachment[${cd}].doc_DESC_ENGL"
														class=" form-control" /></td>
												<td class="text-center"><apptags:formField
														fieldType="7"
														fieldPath="commonFileAttachment[${cd}].doc_DESC_ENGL"
														currentCount="${cd}" showFileNameHTMLId="true"
														fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
														maxFileCount="CHECK_LIST_MAX_COUNT"
														validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
														callbackOtherTask="callbackOtherTask()">
													</apptags:formField></td>
												<td class="text-center"><a href='#' id="0_file_${cd}"
													onclick="doFileDelete(this)"
													class='btn btn-danger btn-sm delButton'><i
														class="fa fa-trash"></i></a></td>
											</tr>
											<c:set var="cd" value="${cd+1}" scope="page" />
										</table>
									</c:if>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
				<!--  -->

        <div class="text-center padding-top-10"> 
        	<c:if test="${command.mode ne 'V'}">
  			 <input type="submit" class="btn btn-success btn-submit" onclick="return saveScrutinyLabels(this,'<spring:message code="cfc.scrSave" />','saveScrutinyValue','FormBuilderUtility.html');" value="<spring:message code="scrutiny.save" />" />
  			 </c:if>
            <apptags:backButton url="FormBuilderUtility.html"></apptags:backButton>
		</div>
		
      </form:form>
    </div>
  </div>
</div>

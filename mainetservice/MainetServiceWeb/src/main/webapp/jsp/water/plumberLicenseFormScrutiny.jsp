
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
<script type="text/javascript" src="js/water/plumberLicenseScrutiny.js"></script>
<link rel="stylesheet" type="text/css" href="css/mainet/themes/jquery-ui-timepicker-addon.css" />
<script type="text/javascript" src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="js/water/plumberLicenseScrutiny.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$(".datepicker").datepicker({
    	dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		changeYear: true,
		yearRange: "-100:-0",
		onSelect: function(selected,evnt) {
	         updateExperienceDetails();
	    }
	});
	
	if (('${command.scrutinyApplicable}') == 'true') {
		$("#scrutinyDiv").show();
	} else {
		$("#scrutinyDiv").hide();
	}

	$("#academicTableId").on('click','.academicAddRow',function(e){
	  
		$('#errorDivId').hide();
		var errorList = [];
		var row=e;
  		var val=(row + 1);
	     $('.academicAppendable').each(function(i) {
			errorList = validateAcademicDetails(errorList,i);
	     });
	     if(errorList.length > 0){ 
	    	 displayErrorsOnPage(errorList);
	    	 return false;
	 	 }
	     var content = $(this).closest('tr').clone();
	     $(this).closest("tr").after(content);
	     var clickedIndex = $(this).parent().parent().index() - 1;	
		
		// for reset all value 
		//content.find("select:eq(0)").attr("value", "");
		content.find("input:text").val("");
		content.find("input:hidden:eq(0)").attr("value", "");
		
		// for generating dynamic Id
	 	$(content).find("td:eq(0)").attr("id", "academicSrNoId"+val);
		$(content).find("input:text:eq(0)").attr("id", "qualificationId"+val);
		$(content).find("input:text:eq(1)").attr("id", "instituteNameId"+val);
		$(content).find("input:text:eq(2)").attr("id", "instituteAddrsId"+val);
		$(content).find("input:text:eq(3)").attr("id", "passYearId"+val);
		$(content).find("input:text:eq(4)").attr("id", "passMonthId"+val);
		$(content).find("input:text:eq(5)").attr("id", "percentGradeId"+val);
		$(content).find("input:hidden:eq(0)").attr("id", "plumQualHiddenId"+val);
		content.find('.academicAddRow').attr("id", "academicAddButton"+ val);
	 	content.find('.academicDeleteRow').attr("id", "academicDelButton"+ val);
	 	
	    // for generating dynamic path
		content.find("input:text:eq(0)").attr("name","plumberQualificationDTOList[" + (clickedIndex + 1)+ "].plumQualification");
		content.find("input:text:eq(1)").attr("name","plumberQualificationDTOList[" + (clickedIndex + 1)+ "].plumInstituteName");
		content.find("input:text:eq(2)").attr("name","plumberQualificationDTOList[" + (clickedIndex + 1)+ "].plumInstituteAddress");
		content.find("input:text:eq(3)").attr("name","plumberQualificationDTOList[" + (clickedIndex + 1)+ "].plumPassYear");
		content.find("input:text:eq(4)").attr("name","plumberQualificationDTOList[" + (clickedIndex + 1)+ "].plumPassMonth");
		content.find("input:text:eq(5)").attr("name","plumberQualificationDTOList[" + (clickedIndex + 1)+ "].plumPercentGrade");
		content.find("input:hidden:eq(0)").attr("name","plumberQualificationDTOList[" + (clickedIndex + 1)+ "].plumQualId");
		reOrderTableIdSequence();
	});
    
	$("#academicTableId").on('click','.academicDeleteRow',function(){
		
		if($("#academicTableId tr").length != 2){
			$(this).parent().parent().remove();
			var qualDeleteId = $(this).parent().parent().find("input:hidden:eq(1)").val();
			if(qualDeleteId != ""){
				var qualDeleted = $("#plumQualDeletedId").val();
				if(qualDeleted == ""){
					$("#plumQualDeletedId").val(qualDeleteId);
				}else{
					var q = qualDeleted + ","+ qualDeleteId;
					$("#plumQualDeletedId").val(q);
				}
			}
		}else{
				alert("You can not delete first row");
		}
		reOrderTableIdSequence();
 	});
 
 $("#experienceTableId").on('click','.experienceAddRow',function(e){
	
	 $('#errorDivId').hide();
	 var errorList = [];
	 var row=e;
     $('.experienceAppendable').each(function(i) {
			errorList = validateExperienceDetails(errorList,i);
     });
	  
     if(errorList.length > 0){ 
    	 displayErrorsOnPage(errorList);
    	 return false;
 	 }
	
		$(".datepicker").datepicker("destroy");
		var content = $(this).closest("tr").clone();
		$(this).closest("tr").after(content);

		// for reset all value 
		content.find("select:eq(0)").attr("value", "");
		content.find("input:text").val("");
		content.find("input:hidden:eq(0)").attr("value", "");
		// for generating dynamic Id
	 	$(content).find("td:eq(0)").attr("id", "experienceSrNoId" + (row + 1));
		$(content).find("input:text:eq(0)").attr("id", "companyNameId" + (row + 1));
		$(content).find("input:text:eq(1)").attr("id", "companyAddrsId" + (row + 1));
		$(content).find("input:text:eq(2)").attr("id", "expFromDateId"+ (row + 1));
		$(content).find("input:text:eq(3)").attr("id", "expToDateId" + (row + 1));
		$(content).find("input:text:eq(4)").attr("id", "experienceId" + (row + 1));
		$(content).find("select:eq(0)").attr("id", "firmTypeId" +(row + 1));
		$(content).find("input:hidden:eq(0)").attr("id", "plumExpHiddenId"+(row + 1));
	
		content.find('.experienceAddRow').attr("id", "experienceAddButton"+ (row + 1));
 		content.find('.experienceDeleteRow').attr("id", "experienceDelButton"+ (row + 1));
 	
    	// for generating dynamic path
		content.find("input:text:eq(0)").attr("name","plumberExperienceDTOList[" + (row + 1)+ "].plumCompanyName");
		content.find("input:text:eq(1)").attr("name","plumberExperienceDTOList[" + (row + 1)+ "].plumCompanyAddress");
		content.find("input:text:eq(2)").attr("name","plumberExperienceDTOList[" + (row + 1)+ "].expFromDate");
		content.find("input:text:eq(3)").attr("name","plumberExperienceDTOList[" + (row + 1)+ "].expToDate");
		content.find("input:text:eq(4)").attr("name","plumberExperienceDTOList[" + (row + 1)+ "].experience");
		content.find("select:eq(0)").attr("name","plumberExperienceDTOList[" + (row + 1)+ "].firmType");
		content.find("input:hidden:eq(0)").attr("name","plumberExperienceDTOList[" + (row + 1)+ "].plumExpId");

			// to add date picker on dynamically created Date fields
			$(".datepicker").datepicker({
	        	dateFormat: 'dd/mm/yy',		
				changeMonth: true,
				changeYear: true,
				yearRange: "-100:-0",
				onSelect: function(selected,evnt) {
			         updateExperienceDetails();
			    }
			});
 	
			reOrderExperienceTableIdSequence();
		});

		$("#experienceTableId").on('click','.experienceDeleteRow',function(){
	
			if($("#experienceTableId tr").length != 2){
				/*alert('inside remove');*/
				 $(this).parent().parent().remove();
				 var expDeleteId = $(this).parent().parent().find("input:hidden:eq(1)").val();
					if(expDeleteId != ""){
						var expDeletedRow = $("#plumExpDeletedId").val();
						if(expDeletedRow == ""){
							$("#plumExpDeletedId").val(expDeleteId);
						}else{
							var e = expDeletedRow + ","+ expDeleteId;
							$("#plumExpDeletedId").val(e);
						}
					}
			}else{
				alert("You can not delete first row");
			}
			reOrderExperienceTableIdSequence();
		});
	});
$(function(){
 	$('#dateWithTimeDtPickrId').datetimepicker();
});

$("#submitBtn").hide();
$('input[type=text]').attr('disabled',true);
$('textarea').attr('readonly',true); 
$('select').attr("disabled", true);
$(".academicAddRow").attr("disabled", true);
$(".academicDeleteRow").attr("disabled", true);
$(".experienceAddRow").attr("disabled", true);
$(".experienceDeleteRow").attr("disabled", true);
$("#editId").show();


</script>
<c:if test="${command.hasValidationErrors()}">
	<script type="text/javascript">
	$("#submitBtn").show();
	$('input[type=text]').attr('disabled',false);
	$('textarea').attr('readonly',false); 
	$('select').attr("disabled", false);
	$(".academicAddRow").attr("disabled", false);
	$(".academicDeleteRow").attr("disabled", false);
	$(".experienceAddRow").attr("disabled", false);
	$(".experienceDeleteRow").attr("disabled", false);
	$("#editId").hide();
	
	</script>
</c:if>


<apptags:breadcrumb></apptags:breadcrumb>

<!-- ============================================================== -->
<!-- Start Content here -->
<!-- ============================================================== -->
<div class="content">
	<!-- Start info box -->
			<div class="widget">
				<div class="widget-header">
					<h2><spring:message code="water.plumberLicense.headerPlumLicense"/></h2>
					<!-- <div class="additional-btn">
						<a href="#" data-toggle="tooltip" data-original-title="Help"><i
							class="fa fa-question-circle fa-lg"></i></a>
					</div> -->
				</div>
				
				<div class="widget-content padding">
					<div class="mand-label clearfix">
						<span><spring:message code="water.fieldwith"/> <i class="text-red-1">*</i><spring:message code="water.ismandtry"/> 
						</span>
					</div>
					
		<div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		</div>
		<form:form 	action="PlumberLicenseForm.html" class="form-horizontal form" name="frmPlumberLicenseForm" 	id="frmPlumberLicenseForm">
						
						<jsp:include page="/jsp/tiles/validationerror.jsp" />

						<jsp:include page="/jsp/mainet/applicantDetails.jsp"></jsp:include>
					
					<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message code="water.plumberLicense.uploadedPhoto"/></label>
							<div class="col-sm-4">
							<img class="img_thumbnail" src="${command.fileDownLoadPath}">
							</div>
					</div>
						
						
				 <h4><spring:message code="water.plumberLicense.academicProfessionDetails"/></h4>
				 
				 <c:set var="d" value="0" scope="page" />
				 <c:set var="e" value="0" scope="page" />
			 <div class="table-responsive" id="academicId">
		        <form:hidden path="plumQualDeletedRow" id="plumQualDeletedId" />
				<table class="table table-bordered table-striped" id="academicTableId">
						<tr>
							<th><spring:message code="water.serialNo"/></th>
							<th><spring:message code="water.plumberLicense.qualificationGained"/></th>
							<th><spring:message code="water.plumberLicense.instituteName"/></th>
							<th><spring:message code="water.plumberLicense.instituteAddress"/></th>
							<th><spring:message code="water.plumberLicense.yearOfPassing"/></th>
							<th><spring:message code="water.plumberLicense.monthOfPassing"/></th>
							<th><spring:message code="water.plumberLicense.percentGrade"/></th>
							<th width="100"><spring:message code="water.Action"/></th>
						</tr>
				<c:choose>
					<c:when test="${empty command.plumberQualificationDTOList}">
						<tr class="academicAppendable">
						
							<td id="academicSrNoId${d}">${d + 1}</td>
							<td><form:input path="plumberQualificationDTOList[${d}].plumQualification" name="" class="form-control" id="qualificationId${d}" value=""/></td>
							<td><form:input path="plumberQualificationDTOList[${d}].plumInstituteName" name="" class="form-control" id="instituteNameId${d}" value=""/></td>
							<td><form:input path="plumberQualificationDTOList[${d}].plumInstituteAddress" name="" class="form-control" id="instituteAddrsId${d}" value=""/></td>
							<td><form:input path="plumberQualificationDTOList[${d}].plumPassYear" name=""  class="form-control hasNumber" id="passYearId${d}" value=""/></td>
							<td><form:input path="plumberQualificationDTOList[${d}].plumPassMonth" name=""  class="form-control hasNumber " id="passMonthId${d}" value=""/></td>
							<td><form:input path="plumberQualificationDTOList[${d}].plumPercentGrade" name="" class="form-control " id="percentGradeId${d}" value=""/></td>
							<td><a data-toggle="tooltip" data-placement="top" title="Add" class="academicAddRow btn btn-success btn-sm" id="academicAddButton${d}"><i class="fa fa-plus-circle"></i></a>
								<a data-toggle="tooltip" data-placement="top" title="Delete" class="academicDeleteRow btn btn-danger btn-sm"  id="academicDelButton${d}"><i class="fa fa-trash-o"></i></a>
							</td>
						</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="dataList" items="${command.plumberQualificationDTOList}" varStatus="status"> 
							<tr class="academicAppendable">
							<td id="academicSrNoId${d}">${d + 1}</td>
							<td><form:input path="plumberQualificationDTOList[${d}].plumQualification" name="" class="form-control" id="qualificationId${d}" value=""/>
								<form:hidden path="plumberQualificationDTOList[${d}].plumQualId" id="plumQualHiddenId${d}" /></td>
							<td><form:input path="plumberQualificationDTOList[${d}].plumInstituteName" name="" class="form-control" id="instituteNameId${d}" value=""/></td>
							<td><form:input path="plumberQualificationDTOList[${d}].plumInstituteAddress" name="" class="form-control" id="instituteAddrsId${d}" value=""/></td>
							<td><form:input path="plumberQualificationDTOList[${d}].plumPassYear" name="" class="form-control" id="passYearId${d}" value=""/></td>
							<td><form:input path="plumberQualificationDTOList[${d}].plumPassMonth" name="" class="form-control" id="passMonthId${d}" value=""/></td>
							<td><form:input path="plumberQualificationDTOList[${d}].plumPercentGrade" name="" class="form-control" id="percentGradeId${d}" value=""/></td>
							<td><a data-toggle="tooltip" data-placement="top" title="Add" class="academicAddRow btn btn-success btn-sm" id="academicAddButton${d}"><i class="fa fa-plus-circle"></i></a>
								<a data-toggle="tooltip" data-placement="top" title="Delete" class="academicDeleteRow btn btn-danger btn-sm"  id="academicDelButton${d}"><i class="fa fa-trash-o"></i></a>
							</td>
						</tr>
							<c:set var="d" value="${d + 1}" scope="page" />
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</table>
	</div>
			<h4><spring:message code="water.plumberLicense.experianceDetails"/></h4>
			 <div class="table-responsive" id="experienceId">
				<form:hidden path="plumExpDeletedRow" id="plumExpDeletedId" />
				<table class="table table-bordered table-striped" id="experienceTableId">
						<tr>
							<th><spring:message code="water.serialNo"/></th>
							<th><spring:message code="water.plumberLicense.employerName"/></th>
							<th><spring:message code="water.plumberLicense.employerAddress"/></th>
							 <th width="140"><spring:message code="water.plumberLicense.frmDate"/></th>
							<th width="140"><spring:message code="water.plumberLicense.toDate"/></th> 
							<th><spring:message code="water.plumberLicense.experience"/></th>
							<th width="160"><spring:message code="water.plumberLicense.firmType"/></th>
							<th width="100"><spring:message code="water.Action"/></th>
						</tr>
						
			<c:choose>
				<c:when test="${empty command.plumberExperienceDTOList}">
						<tr class="experienceAppendable">
							<td id="experienceSrNoId${e}">${e + 1}</td>
							<td><form:input path="plumberExperienceDTOList[${e}].plumCompanyName" name="" class="form-control" id="companyNameId${e}" value=""/></td>
							<td><form:input path="plumberExperienceDTOList[${e}].plumCompanyAddress" name="" class="form-control" id="companyAddrsId${e}" value=""/></td>
							<td><form:input path="plumberExperienceDTOList[${e}].expFromDate" name=""  cssClass="datepicker cal form-control" id="expFromDateId${e}" value=""/></td>
							<td><form:input path="plumberExperienceDTOList[${e}].expToDate" name=""  cssClass="datepicker cal form-control" id="expToDateId${e}" value=""/></td>
							<td><form:input path="plumberExperienceDTOList[${e}].experience" name=""  readonly="true" class="form-control" id="experienceId${e}" value=""/></td>
							<td><c:set var="baseLookupCode" value="PFT" /> 
								<form:select path="plumberExperienceDTOList[${e}].firmType" class="form-control" id="firmTypeId${e}">
									<form:option value="0"><spring:message code="water.plumberLicense.valMsg.selectFrmType"/></form:option>
									<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
										<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select>
							</td>
							<td><a data-toggle="tooltip" data-placement="top" title="Add" class="experienceAddRow btn btn-success btn-sm" id="experienceAddButton${e}"><i class="fa fa-plus-circle"></i></a>
								<a data-toggle="tooltip" data-placement="top" title="Delete" class="experienceDeleteRow btn btn-danger btn-sm"  id="experienceDelButton${e}"><i class="fa fa-trash-o"></i></a>
							</td>
						</tr>
					</c:when>
					<c:otherwise> 
						<c:forEach var="dataList" items="${command.plumberExperienceDTOList}" varStatus="status"> 
							<tr class="experienceAppendable">
								<td id="experienceSrNoId${e}">${e + 1}</td>
								<td><form:input path="plumberExperienceDTOList[${e}].plumCompanyName" name="" class="form-control" id="companyNameId${e}" value=""/>
								<form:hidden path="plumberExperienceDTOList[${e}].plumExpId" id="plumExpHiddenId${e}" /></td>
								<td><form:input path="plumberExperienceDTOList[${e}].plumCompanyAddress" name="" class="form-control" id="companyAddrsId${e}" value=""/></td>
								<td><form:input path="plumberExperienceDTOList[${e}].expFromDate" name=""  cssClass="datepicker cal form-control" id="expFromDateId${e}" value=""/></td>
								<td><form:input path="plumberExperienceDTOList[${e}].expToDate" name=""  cssClass="datepicker cal form-control" id="expToDateId${e}" value=""/></td>
								<td><form:input path="plumberExperienceDTOList[${e}].experience" name=""  readonly="true" class="form-control" id="experienceId${e}" value=""/></td>
								<td><c:set var="baseLookupCode" value="PFT" /> 
									<form:select path="plumberExperienceDTOList[${e}].firmType" class="form-control" id="firmTypeId${e}">
										<form:option value="0"><spring:message code="water.plumberLicense.valMsg.selectFrmType"/></form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
											<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select>
								</td> 
								<td><a data-toggle="tooltip" data-placement="top" title="Add" class="experienceAddRow btn btn-success btn-sm" id="experienceAddButton${e}"><i class="fa fa-plus-circle"></i></a>
									<a data-toggle="tooltip" data-placement="top" title="Delete" class="experienceDeleteRow btn btn-danger btn-sm"  id="experienceDelButton${e}"><i class="fa fa-trash-o"></i></a>
								</td>
							</tr>
								<c:set var="e" value="${e + 1}" scope="page" />
						</c:forEach> 
					</c:otherwise>
			</c:choose> 
						<tfoot>
						<tr>
						<th colspan="5"><span class="pull-right"><spring:message code="water.plumberLicense.totalExperience"/></span></th>
						<td><form:input path="totalExp" id="totalExpId" readonly="true" class="form-control" /></td>
						<th colspan="2"></th>
						</tr>
						</tfoot>
		</table>
	</div>
				<h4><spring:message code="water.plumberLicense.interviewScheduleDetails"/></h4>
					<div class="form-group">
						<label class="col-sm-2 control-label required-control"><spring:message code="water.plumberLicense.interviewDateAndTime"/></label>
						<div class="col-sm-4">
						<form:input path="interviewDateTime" name="" value="${command.interviewDateTimeD}" cssClass="cal form-control" id="dateWithTimeDtPickrId"/>
						</div>
						<label class="col-sm-2 control-label required-control"><spring:message code="water.plumberLicense.remarks"/></label>
						<div class="col-sm-4">
							<form:textarea path="interviewRemark" name="" cssClass="form-control " id= "" value=""/>
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
													<apptags:filedownload filename="${lookUp.attFname}" filePath="${lookUp.attPath}" dmsDocId="${lookUp.dmsDocId}" actionUrl="PlumberLicenseForm.html?Download"></apptags:filedownload>
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
						<button type="button" id="editId" class="btn btn-success" onclick="editForm(this)"><spring:message code="water.btn.edit"/></button>
						<button type="button" class="btn btn-success" id="submitBtn" onclick="updatedPlumberLicenseDetailsOnScrutiny(this)"><spring:message code="water.btn.submit"/></button>
					</div>
					</form:form>
					<div id="scrutinyDiv">
						<jsp:include page="/jsp/cfc/sGrid/scrutinyButtonTemplet.jsp"></jsp:include>
                    </div>
				</div>
			</div>
		</div>

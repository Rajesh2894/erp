<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<link href="css/mainet/ui.jqgrid.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/mainet/themes/humanity/jquery.ui.all.css" />
<script src="js/mainet/ui/i18n/grid.locale-en.js" type="text/javascript"></script>
<script src="js/mainet/jquery.jqGrid.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script src="js/masters/formbuilder/formbuilderutility.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content" id="content">
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="common.master.form.builder.util" text="Form Builder Utility"/></h2>
			<apptags:helpDoc url="FormBuilderUtility.html"></apptags:helpDoc>
			
		</div>

		<div class="widget-content padding">		
			<form:form action="FormBuilderUtility.html" name="frmFormLabel" id="frmFormLabel" class="form">
			<div class="warning-div alert alert-danger alert-dismissible error-div" style="display: none;" id="errorDivScrutiny"></div>
			
			  	<div class="form-group">
			  		
			  		<div id="service-div">
					<label class="col-sm-2 control-label "><spring:message code="trutirejection.ServiceName" text="Service Name"/></label>
					<div class="col-sm-4">
						<form:select path="serviceCode" class="form-control chosen-select-no-results mandColorClass" id="serviceId">
							<form:option value=""><spring:message code="contract.label.select" text="Select"/></form:option>
								<c:forEach items="${serviceList}" var="serv">						
									<form:option value="${serv.smShortDesc}">${serv.smServiceName}</form:option>						
								</c:forEach>
						</form:select>
					</div>
					</div>
				</div> 
				<div class="clear"></div>
				<div class="text-center padding-20 col-sm-12">
				 <a href="javascript:void(0);"  onclick="searchScrutinyData(this)" class="btn btn-success btn-submit" id="btnsearch"><i class="fa fa-search"></i>&nbsp;<spring:message code="master.search" text="Search"/></a> 
				<button class="btn btn-warning" onclick="proceed()"><spring:message code="contract.label.Reset" text="Reset"/></button>
				<button type="button" class="btn btn-blue-2 addClass"> <i class="fa fa-plus-circle"></i>&nbsp;<spring:message code='master.addButton'/></button>
				<button type="button" class="btn btn-success save"
							name="button-Cancel" value="export" onclick="downloadTamplate();"
							id="import">
							<spring:message code="" text="Download Template" />
						</button>
				</div>
						
				<!------------------------- this is for download template and upload excel file start--------------------------->
				<div class="form-group" id="reload">
					<label class="col-sm-2 control-label" for="ExcelFileUpload"><spring:message
							code="excel.file.upload" text="Excel File Upload" /></label>
					<div class="col-sm-2 text-left">
						<apptags:formField fieldPath="uploadFileName"
							showFileNameHTMLId="true" fileSize="WORK_COMMON_MAX_SIZE"
							maxFileCount="CHECK_LIST_MAX_COUNT"
							validnFunction="EXCEL_IMPORT_VALIDATION_EXTENSION"
							currentCount="0" fieldType="7">
						</apptags:formField>
						<small class="text-blue-2">(Upload Excel upto 5MB )</small>
					</div>
					<div class="col-sm-2 text-left">
					<button type="button" class="btn btn-success save"
						name="button-save" value="saveExcel"
						onclick="uploadExcelFile(this);" id="button-save">
						<spring:message code="" text="Import" />
				</button>
					</div>
					
					
					
					
					<form:hidden path="uploadFileName" id="filePath" />
				</div>
				<div class="clear"></div>
				<!------------------------- this is for download template and upload excel file end--------------------------->
				
						
				
				
				<table id="grid"></table>
				<div id="pagered"></div>
				
				
				</div>
			
				
						
					
				
			
			</form:form>
			
		</div>
	</div>

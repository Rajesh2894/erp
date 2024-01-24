<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<link href="css/mainet/ui.jqgrid.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/mainet/themes/humanity/jquery.ui.all.css" />
<script src="js/mainet/ui/i18n/grid.locale-en.js" type="text/javascript"></script>
<script src="js/mainet/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="js/masters/formbuilder/formbuilder.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#service-div').hide();
});
</script>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content" id="content">
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="common.master.form.builder" text="Form Builder"/></h2>
			<apptags:helpDoc url="FormBuilder.html" helpDocRefURL="FormBuilder.html"></apptags:helpDoc>
			
		</div>

		<div class="widget-content padding">		
			<form:form method="post" action="" class="form-horizontal" >
			<div class="warning-div alert alert-danger alert-dismissible error-div" style="display: none;" id="errorDivScrutiny"></div>
			  	<div class="form-group">
			  		
			  		<label class="col-sm-2 control-label" ><spring:message code="" text="Department Name"/></label>
					<div class="col-sm-4 ">
						<form:select path="" class="form-control chosen-select-no-results mandColorClass" id="depId" onchange="refreshService(this)">
							<form:option value=""><spring:message code="contract.label.select" text="Select"/></form:option>
								<c:forEach items="${deptList}" var="dept">		
									<form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>						
								</c:forEach>
						</form:select>
					</div>
			  		<div id="service-div">
					<label class="col-sm-2 control-label "><spring:message code="" text="Service Name"/></label>
					<div class="col-sm-4">
						<form:select path="" class="form-control chosen-select-no-results" id="serviceId">
							<form:option value=""><spring:message code="contract.label.select" text="Select"/></form:option>
						</form:select>
					</div>
					</div>
				</div> 
				
				<div class="text-center padding-bottom-10">
				 <a href="javascript:void(0);"  onclick="searchScrutinyData(this)" class="btn btn-success btn-submit" id="btnsearch"><i class="fa fa-search"></i>&nbsp;<spring:message code="master.search" text="Search"/></a> 
				<button class="btn btn-warning" onclick="proceed()"><spring:message code="contract.label.Reset" text="Reset"/></button>
				<!-- <input type="button" value="Add" class="btn btn-blue-2" onclick="openScrutinyAddForm()"> -->
				<button type="button" class="btn btn-blue-2" onclick="openScrutinyAddForm()"> <i class="fa fa-plus-circle"></i>&nbsp;<spring:message code='master.addButton'/></button></div>
				</div>
			
				<table id="grid"></table>
				<div id="pagered"></div>
			
			</form:form>
		</div>
	</div>
</div>

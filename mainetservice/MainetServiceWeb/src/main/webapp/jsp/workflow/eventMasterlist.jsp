<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/workflow/eventMasterList.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
       <div class="content"> 
	<div class="widget">
            <div class="widget-header">
              <h2><spring:message code="workflow.div.header2" text="Event Service Mapping"/></h2>
              <apptags:helpDoc url="EventMaster.html"></apptags:helpDoc>
            </div>
            <div class="widget-content padding">
			<div class="error-div alert alert-danger alert-dismissible" style="display:none;" id="errorDivDeptMas"></div>
			<form class="form-horizontal" id="eventmaster">
				<div class="form-group">
					<label class="col-sm-2 control-label required-control">
					<spring:message code="workflow.form.field.dep" /></label>
					<div class="col-sm-4">
						<select id="dpDeptId" class="form-control chosen-select-no-results" onchange="getServices()">
							<option value=""><spring:message code="workflow.form.select.department" /></option>
							<c:forEach items="${deptList}" var="deptLst">
								<option value="${deptLst.dpDeptid }">${deptLst.dpDeptdesc }</option>
							</c:forEach>
						</select>
					</div>
					
					<div id="service-div">
					<label class="col-sm-2 control-label required-control"><spring:message code="workflow.form.field.service" /></label>
					<div class="col-sm-4">
					<select id="serviceId" class="form-control chosen-select-no-results">
						<option value=""><spring:message code="workflow.form.select.service" /></option>
					</select>
					</div>
					</div>

				</div>
			
			<div class="text-center padding-top-10 clear">
			<button type="button" id="createData" class="btn btn-success">
					<spring:message code='master.addButton'/></button>
					<button type="button" id="search" class="btn btn-blue-2" onclick="searchEvents()">
						<spring:message code='search.data'/></button>
					<input type="reset" id="reset" value="<spring:message code='reset.msg'/>" 
						class="btn btn-warning" onclick="eventMstReset();"/>
					
				
			</div>
			
			<div id="eventDiv" class="clear padding-top-10">
			<table id="grid"></table>
			<div id="pagered"></div> 
			</div>	
			<input type="hidden" id="langId" value="${userSession.languageId}"/>
			<div class="text-center padding-top-10 clear">
				
				<button type="button" id="editData" class="btn btn-warning" onclick="updateViewForm('EDIT');">
					<spring:message code='master.editSelected'/></button>
				<button type="button" id="viewData" class="btn btn-blue-2" onclick="updateViewForm('VIEW');">
					<spring:message code='view.msg'/></button>
			</div>
					
			</form>

</div>
</div>
</div>
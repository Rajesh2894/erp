<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/audit/EmployeeDesignation.js"></script>

<div id="heading_wrapper">
		
	<div id="heading_bredcrum">
		<ul>
	   	  <li><a href="CitizenHome.html"><spring:message code="menu.home"/></a></li>
	      <li>&gt;</li>
	      <li><a href="javascript:void(0);"><spring:message code="menu.audit"/></a></li>
	      <li>&gt;</li>
	      <li><a href="javascript:void(0);"><spring:message code="audit.master"/></a></li>
	      <li>&gt;</li>
	      <!-- <li><a href="javascript:void(0);">Assign HOD</a></li>
	      <li>&gt;</li> -->
	      <li><spring:message code="EmployeeDesignation.FormName"/></li>
		</ul>
	</div>
		
</div>
<div class="clearfix" id="home_content">
	<div class="col-xs-12">
		<div class="row">
			<div class="form-div">
				<form:form action="EmployeeDesignation.html"
					name="frmEmployeeDesignation" id="frmEmployeeDesignation">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div class="form-elements">

						<span class="otherlink"> <a href="javascript:void(0);"
							class="btn btn-primary" onclick="findAll(this)">Search</a> <a
							href="javascript:void(0);" class="btn btn-primary"
							onclick="emptyForm(this)">Clear</a> <a href="javascript:void(0);"
							class="btn btn-primary"
							onclick="openForm('EmployeeDesignationForm.html')">Add</a>
						</span>
						<div class="element">
							<label for=deptId><spring:message code="rti.depName" />
								:</label> <span> <apptags:lookupField
									items="${command.getDepartmentLookUp()}" path="deptId" changeHandler="fn_setEmployee()"
									selectOptionLabelCode="Select Department" hasId="true"
									showAll="true" />
							</span><span class="mand">*</span>
						</div>
					</div>
					<div class="form-elements">
						<div class="element">

							<label for="empId">Employee</label>
							<apptags:selectField isLookUpItem="true"
								items="${command.allEmployee}"
								selectOptionLabelCode="Select Employee" fieldPath="empId" />

						</div>

					</div>
				</form:form>
				<div class="grid-class" id="quickEmployeeDesignation">
					<apptags:jQgrid id="EmployeeDesignationForm"
						url="EmployeeDesignation.html?EmployeeDesignationList"
						mtype="post" gridid="gridEmployeeDesignationForm"
						colHeader="EmployeeDesignation.departmentName,ChargeDetail.cdFromDt,ChargeDetail.cdToDt,EmployeeDesignation.designationType,Employee Name"
						colModel="[
												{name : 'departmentLookup.lookUpDesc',index : 'departmentLookup.lookUpDesc', editable : false,sortable : false,search : false, align : 'center' },
												{name : 'fromDate',index : 'fromDate', editable : false,sortable : false,search : false, align : 'center'  ,formatter : dateTemplate},
												{name : 'toDate',index : 'toDate', editable : false,sortable : false,search : false, align : 'center'  ,formatter : dateTemplate},
												{name : 'designationName',index : 'designationName', editable : false,sortable : false,search : false, align : 'center' },
												{name : 'employee.lookUpCode',index : 'department.lookUpCode', editable : false,sortable : false,search : false, align : 'center' }
												
								  ]"
						height="200" caption="EmployeeDesignation.gridCatption"
						isChildGrid="false" hasActive="true" hasViewDet="true"
						hasDelete="true" loadonce="true" sortCol="rowId" showrow="true" />


				</div>
			</div>
		</div>
	</div>
</div>

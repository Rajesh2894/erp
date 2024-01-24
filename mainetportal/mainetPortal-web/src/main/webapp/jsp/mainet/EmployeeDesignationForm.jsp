<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/audit/EmployeeDesignation.js"></script>

<div class="clearfix" id="home_content">
	<div class="col-xs-12">
		<div class="row">
			<div class="form-div">
				<form:form action="EmployeeDesignationForm.html"
					name="frmEmployeeDesignation" id="frmEmployeeDesignation">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />

					<span class="otherlink"> <apptags:submitButton
							successUrl="EmployeeDesignation.html"
							entityLabelCode="Employee Designation " />
						<apptags:resetButton /> <apptags:backButton
							url="EmployeeDesignation.html" />
					</span>
					<div class="form-elements">

						<div class="element">
							<label for="entity_department_dpDeptid"><spring:message
									code="EmployeeDesignation.departmentName" /> :</label>
							<apptags:lookupField items="${command.departmentList}"
								changeHandler="fn_searchByDepartment()"
								path="entity.department.dpDeptid" showOnlyLabel="false"
								hasId="true" selectOptionLabelCode="Select Department" />
							<span class="mand">*</span>
						</div>
					</div>
					<div class="form-elements">
						<div class="element">
							<label for="entity_emp_empId"><spring:message
									code="EmployeeDesignation.employeename" /> :</label> <span><apptags:selectField
									isLookUpItem="true" items="${command.allEmployee}" hasId="true"
									selectOptionLabelCode="Select Employee"
									fieldPath="entity.emp.empId" /></span> <span class="mand">*</span>
						</div>
					</div>
					<div class="form-elements">
						<div class="element">
							<label for="entity.designationType.dsgid"><spring:message
									code="EmployeeDesignation.designationType" /> :</label>
							<apptags:selectField isLookUpItem="false"
								selectOptionLabelCode="Select Designation type"
								fieldPath="entity.designationType.dsgid"
								items="${command.designations}" />
							<span class="mand">*</span>
						</div>
					</div>

					<div class="form-elements">
						<div class="element">
							<label for="entity.fromDate"><spring:message
									code="ChargeDetail.cdFromDt" /> :</label> <span><apptags:dateField
									datePath="entity.fromDate" fieldclass="datepicker"
									readonly="false"></apptags:dateField></span><span class="mand">*</span>
						</div>
					</div>
					<div class="form-elements">
						<div class="element">
							<label for="entity.toDate"><spring:message
									code="ChargeDetail.cdToDt" /> :</label> <span><apptags:dateField
									datePath="entity.toDate" fieldclass="datepicker"
									readonly="false"></apptags:dateField></span><span class="mand">*</span>
						</div>
					</div>
					
				</form:form>



			</div>
		</div>
	</div>
</div>
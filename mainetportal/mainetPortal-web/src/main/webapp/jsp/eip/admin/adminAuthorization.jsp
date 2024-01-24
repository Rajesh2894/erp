<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<ul class="breadcrumbs">
<li><a href="CitizenHome.html"><spring:message code="menu.home"/></a></li>
<li><a href="javascript:void(0);"><spring:message code="eip.authentication"/></a></li>
<li><a href="javascript:void(0);"><spring:message code="eip.transactions"/></a></li>
<li class="active"><spring:message code="" text="Department Authorization"/></li>
</ul>
<h1>Department Authorization</h1>

<div class="clearfix" id="content">

			<div class="form-div">
			<form:form action="AdminAuthorization.html" method="POST" class="form">
			  <jsp:include page="/jsp/tiles/validationerror.jsp"/>
			  <div class="form-elements">
			  		<div class="element">
				
				<label for="type">
					<spring:message	code="eip.dept.auth.deptType" text="Type of Dept" /> :
					</label>
					<form:select cssClass="mandClassColor" path="entity.dpDeptid">
							<form:option value="0">Select Department Type</form:option>
							<c:forEach items="${command.getDepartmentLookUp()}" var="deptlookup">
								<form:option value="${deptlookup.lookUpId}">${deptlookup.lookUpDesc}</form:option>
							</c:forEach>
						</form:select> <span class="mand">*</span>
						
						</div>
						
						<div class="element">
				 		<label for=""><spring:message code="eip.dept.auth.mobNum" text="Mobile Number"/> :</label>
							<span>
								<apptags:inputField cssClass="hasMobileNo" fieldPath="entity.empmobno" />
							</span>
						</div>
						
					</div> 
					
					<div class="form-elements clear">
					     <div class="element">
					        <label for=""><spring:message code="eip.dept.auth.sortBy" text="Sort By"/></label>
					        <form:select cssClass="mandClassColor" path="entity.authStatus">
							<form:option value="0">Select Status</form:option>
							<form:option value="null">Pending</form:option>
							<form:option value="A">Approve</form:option>
							<form:option value="R">Rejected</form:option>
							<form:option value="H">On Hold</form:option>
						</form:select>
					     </div>
					</div>        
	
</body>
</html>
				
			<div class="clear btn_fld padding_top_10">
			 	
					<a href="javascript:void(0);" class="css_btn" onclick="findAll(this)"><spring:message code="eip.admin.auth.search" /></a>
				
			 </div>                    
				</form:form>		
					
					<div class="grid-class clear padding_top_10">
					<apptags:jQgrid id="deptAuthorization"
					mtype="post"
					url = "AdminAuthorization.html?Dept_List"
					 
					colHeader="eip.dept.auth.empName,eip.dept.auth.mobNo,eip.dept.auth.PanNo,eip.agency.Status" 
					colModel="[
					{name : 'empname',index :'empname',align :'center',width :'90'},
					
					
					{name : 'empmobno',index :'empmobno',align : 'center',width :'90'},
					
					{name : 'panCardNo',index :'panCardNo',editable : false,sortable : true,search : false,align : 'center',width :'90'},
					{name : 'authStatus',index : 'authStatus',editable : false,sortable : true,search : false,align : 'center',width :'90'}
					]"
					gridid="gridDeptAuthorizationForm" 
					sortCol="rowId"
					isChildGrid="false"
					hasActive="false"
					hasViewDet="true"
					hasDelete="false"
					height="200" 
					caption="eip.deptGridCaption" 
					loadonce="true"  ></apptags:jQgrid>
					</div>
					</div>
					</div>
					
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<div class="dialog-header">
	<h2><spring:message code="eip.admin.auth.serachEmpDetails" /></h2>	
</div>

<div class="popup-form-div">
<form:form 	method="post" action="${command.empformName}" name="frmChildMaster" id="frmEmpMaster" required="true" cssClass="form">
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div class="form-elements clear">	
						<div class="element pull-left">
									<label><spring:message code="eip.admin.auth.enterEmpName" /> : </label>
									<form:input path="employeeName" cssClass="hasSpecialChara"/>
						</div>
							<div class="element">
									<div class="otherlink"> 
									 	<a href="javascript:void(0);" class="css_btn" onclick="return serachEmpDetails(this);"><spring:message code="bt.search"/></a>
									 	<a href="javascript:void(0);" class="css_btn" onclick="clearChildForm(this)"><spring:message code="bt.clear"/></a>
									</div>
							</div>		
						</div>
			
		
				
			<fieldset class="fieldRound">
			<br>
				<div class="data">	
				<apptags:jQgrid id="PropertyTaxObjectionEntry"
					url="${command.empformName}?EMP_SEARCH_RESULT" mtype="post"
					gridid="gridPropertyTaxObjectionEntry2"
					colHeader="eip.admin.auth.name,eip.admin.reg.empNo,ptdept.select"
					colModel="[	
								{name : 'employeeName',index : 'employeeName',editable : false,sortable : false,search : false,align : 'center',width :320},							
								{name : 'id',index : 'id',editable : false,sortable : false,search : false,align : 'center',width :105},
								{name : 'singleSelectTemplete',index : 'singleSelectTemplete',editable : false,sortable : false,search : false,align : 'center',width:'50'}
							]"
					sortCol="rowId" isChildGrid="false" hasActive="false"
					hasViewDet="false" hasDelete="false" height="150" showrow="true"
					caption="EmployeeDesignation.gridCatption" loadonce="true" />
					
					</div>
		</fieldset>
	</form:form>		

</div>
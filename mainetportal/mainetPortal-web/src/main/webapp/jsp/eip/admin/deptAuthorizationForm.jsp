<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<style>
.row1{
 max-width:140px; word-wrap:break-word;
}
</style>

<script>
$(document).ready(function(){
	
	
	$('#app1').prop("disabled",true);
	$("#authStatusReject").prop("disabled",true);

	$("[id^=approve]").click(function(){

          count=0;
	      $("[id^=approve]").each(function () {
                     if($(this).is(":checked")){
                         if($(this).parent().parent().find('[id^=hiddenValue]').val() == 1){
                          count+=1;      
                         }
                       
                        }
                      if(count == $("#checker").val()){
                        $('#app1').prop("disabled",false);
                        $("#app1").prop('checked', true);
                       
	    	           }
                    });   
                 });

		$("[id^=reject]").click(function(){
			 if($(this).parent().parent().find('[id^=hiddenValue]').val() == 1){
				    $("#app1").prop('checked', false);
					$('#app1').prop("disabled",true);
					
				}

             if ($("[id^=approve]").is(":checked") || $("[id^=reject]").is(":checked"))
                {
                    $("#authStatusReject").prop("disabled",false);
                }
                else
                {
                   $("#authStatusReject").prop("disabled",true);
       			   $("#authStatusReject").prop('checked', false);
                }
			
		 }); 


		
		
	    

});


</script>
<jsp:useBean id="stringUtility"
	class="com.abm.mainet.common.util.StringUtility" />

<div class="clearfix" id="home_content">
	<div class="col-xs-12">
		<div class="row">
			<div class="form-div">
			
				<form:form 	action="DeptAuthorizationForm.html" name="frmDeptAuthorizationForm" id="frmDeptAuthorizationForm" class="form">	
				 
				<jsp:include page="/jsp/tiles/validationerror.jsp"/>
				<div class="form-elements">							
						<div class="element">
							<label for=""><spring:message code="eip.dept.auth.Deptname" /> :</label>
							<form:input path="" type="text" disabled="disabled" value="${command.getDeptName()}" readonly="true" cssClass="disablefield"/>
							
						</div>
					
					
						<div class="element">
							<label for=""><spring:message code="eip.dept.auth.DesgName" /> :</label>
							<form:input path="" type="text" disabled="disabled" value="${command.getDesgName()}" readonly="true" cssClass="disablefield"/>
						</div>
				</div>
				
				
					<div class="form-elements">
						<div class="element">
							<label for=""><spring:message code="eip.dept.auth.firstname" /> :</label>
							<form:input path="" type="text" disabled="disabled" value="${command.entity.getEmpname()}" readonly="true" cssClass="disablefield"/>
						</div>
						
						
						<div class="element">
							<label for=""><spring:message code="eip.dept.auth.lastName" /> :</label>
							<form:input path="" type="text" disabled="disabled" value="${command.entity.getEmpLName()}" readonly="true" cssClass="disablefield"/>
						</div>
					</div>
			
					<div class="form-elements">
						<div class="element">
							<label for=""><spring:message code="eip.dept.auth.empId" /> :</label>
							<form:input path="" type="text" disabled="disabled" value="${command.entity.getEmpId()}" readonly="true" cssClass="disablefield"/>
						</div>
					
					
					
						<div class="element">
							<label for=""><spring:message code="eip.dept.auth.dob" /> :</label>
							<form:input path="" type="text" disabled="disabled" value="${command.entity.getEmpdob()}" readonly="true" cssClass="disablefield"/>
						</div>
					</div>
					
					<div class="form-elements">
						<div class="element">
							<label for=""><spring:message code="eip.dept.auth.pmtAddress" /> :</label>
							<form:input path="" type="text" disabled="disabled" value="${command.entity.getEmpAddress()}" readonly="true" cssClass="disablefield"/>
						</div>
					
						<div class="element">
							<label for=""><spring:message code="eip.dept.auth.mobNo" /> :</label>
							<form:input path="" type="text" disabled="disabled" value="${command.entity.getEmpmobno()}" readonly="true" cssClass="disablefield"/>
						</div>
					</div>
					
					<div class="form-elements clear">
						<div class="element">
							<label for=""><spring:message code="eip.dept.auth.email" /> :</label>
							<form:input path="" type="text" disabled="disabled" value="${command.entity.getEmpemail()}" readonly="true" cssClass="disablefield"/>
						</div>
					
						<div class="element">
							<label for=""><spring:message code="eip.dept.auth.panNo" /> :</label>
							<form:input path="" type="text" disabled="disabled" value="${command.entity.getPanCardNo()}" readonly="true" cssClass="disablefield"/>
						</div>
						
					</div>
					
					<div class="form-elements">
				<label for="type"> <spring:message
											code="" text="Group_ID" /> :
									</label>
								    <div class="element">
								    <form:select path="entity.gmid">
								    <form:option value="0" label="--Select Group --" />
								    <form:options items="${agencyList}"/>
								    </form:select>
								    </div>
								    
								   <div class="element">
								     <label for="type"> <spring:message code="" text="Department Location" /> :
									</label>
								    <form:select path="entity.departmentLocation.departmentId" id="location">
								    <form:option value="0" label="--Select Location --" />
								  
								     <form:options itemLabel="descLangFirst" items="${locationList}" itemValue="lookUpId"/> 
								    </form:select>
								    </div>   
								    </div>
				
	<div class="overflow clear padding_10">
	        
			<table class="gridtable">
						<tr>
								<th><spring:message code="eip.dept.auth.srNo"/></th>
								<th><spring:message code="eip.dept.auth.viewAttachments"/></th>
								<th><spring:message code="eip.dept.auth.status" text="Status"/></th>
								<th><spring:message code="eip.dept.auth.docstatus" text="Document Status"/></th>
								<th><spring:message code="eip.dept.auth.verify" text="Verify"/></th>
								<th><spring:message code="eip.dept.auth.remark" text="Remark"/></th>
			     		</tr>
				
			                <c:set var="logCount" value="0"/>
							<c:forEach items="${command.allEmployeeDoc}"  var="lookUp" varStatus="lk">
							<tr>
							
								<td>${lk.count}</td>
								
								<td> 
									  <c:set var="links" value="${fn:split(lookUp.lookUpDesc,',')}" />
									<c:forEach items="${links}" var="download" varStatus="status">
										<c:set var="link" value="${lookUp.lookUpCode}" />
										
                         <apptags:filedownload filename="${link}" filePath="${download}" actionUrl="DeptAuthorizationForm.html?Download"></apptags:filedownload>
 		</c:forEach>  
									
								</td>
							
							<td>
							           
								       <c:choose>
											<c:when test="${lookUp.lookUpId eq '1'}">
											<spring:message code="eip.dept.auth.view.mandatory" text="Mandatory"/>
											     <c:if test="${lookUp.lookUpType ne 'N'}">
											        <c:set var="logCount" value="${logCount+1}"/>
											    </c:if> 
											</c:when>
								            <c:when test="${lookUp.lookUpId eq '0'}">
											     <spring:message code="eip.dept.auth.view.option" text="Optional"/>
											</c:when>
										</c:choose>
										
							</td>
							       <td>
							
							               <c:choose>
											<c:when test="${lookUp.lookUpType eq 'Y'}">
											<spring:message code="eip.dept.auth.view.approved" text="Approved"/>
											</c:when>
								
											<c:when test="${lookUp.lookUpType eq 'N'}">
											<spring:message code="eip.dept.auth.view.rejected" text="Rejected"/>
											</c:when>
											
											<c:when test="${lookUp.lookUpType eq null}">
											<spring:message code="eip.dept.auth.view.pending" text="Pending"/>
											</c:when>
										</c:choose>
							   </td>
							
<td> 										
<div id="loopValue${lk.count-1}">						
<c:if test="${lookUp.lookUpType eq 'Y'}">
<label>
<form:radiobutton disabled="true"  value="Y" path="entity.cfcAttachments[${lk.count-1}].docApprStatus"  id="approve${lk.count-1}"/>
<spring:message code="eip.dept.auth.view.yes" text="Yes"/>
</label>



<label>
<form:radiobutton disabled="true" value="N" path="entity.cfcAttachments[${lk.count-1}].docApprStatus" id="reject${lk.count-1}" />
<spring:message code="eip.dept.auth.view.no" text="No  "/>
</label>

</c:if>

<c:if test="${lookUp.lookUpType eq 'N'}">
<label>
<form:radiobutton    value="Y" path="entity.cfcAttachments[${lk.count-1}].docApprStatus"  id="approve${lk.count-1}" />
<spring:message code="eip.dept.auth.view.yes" text="Yes"/>
</label>


<label>
<form:radiobutton   value="N" path="entity.cfcAttachments[${lk.count-1}].docApprStatus" id="reject${lk.count-1}" />
<spring:message code="eip.dept.auth.view.no" text="No  "/>
</label>

</c:if>
<c:if test="${lookUp.lookUpType eq null}">
<label>
<form:radiobutton value="Y" path="entity.cfcAttachments[${lk.count-1}].docApprStatus"  id="approve${lk.count-1}"/>
<spring:message code="eip.dept.auth.view.yes" text="Yes"/>
</label>


<label>
<form:radiobutton value="N" path="entity.cfcAttachments[${lk.count-1}].docApprStatus" id="reject${lk.count-1}" />
<spring:message code="eip.dept.auth.view.no" text="No  "/> 
</label>

</c:if>

<input type="hidden" value="${lookUp.lookUpId}"  id="hiddenValue${lk.count-1}">
</div>
</td> 

<td>
<form:input path="entity.cfcAttachments[${lk.count-1}].rejectedMsg"></form:input>
</td>

</tr>
</c:forEach>
	</table>
	</div> 	
	 	
	<div class="form-elements clear margin_top_10">
							
							<label>
							  <c:choose>
									       <c:when test="${empty command.allEmployeeDoc}">
									          <form:radiobutton path="entity.authStatus" value="deptA"  /><spring:message code="eip.dept.auth.approve" />
									       </c:when>
							     <c:otherwise>
								         
								       <c:choose>
								              
									          <c:when test="${logCount eq 0}">
									                 <form:radiobutton path="entity.authStatus" value="deptA" /><spring:message code="eip.dept.auth.approve" />
									          </c:when>
									          <c:otherwise>
									                 <form:radiobutton path="entity.authStatus" value="A" id="app1" /><spring:message code="eip.dept.auth.approve" /> 
									          </c:otherwise>
								       </c:choose>
							     </c:otherwise>
							  </c:choose>
							  </label>
							  <label>
							   <c:choose>
									       <c:when test="${empty command.allEmployeeDoc}">
									          <form:radiobutton path="entity.authStatus" value="deptR"  /><spring:message code="eip.dept.auth.reject" />
									       </c:when>
							     <c:otherwise>
								         
								       <c:choose>
								              
									          <c:when test="${logCount eq 0}">
									                 <form:radiobutton path="entity.authStatus" value="deptR" /><spring:message code="eip.dept.auth.reject" />
									          </c:when>
									          <c:otherwise>
									                 <form:radiobutton path="entity.authStatus" value="R" id="authStatusReject"/><spring:message code="eip.dept.auth.reject" /> 
									          </c:otherwise>
								       </c:choose>
							     </c:otherwise>
							  </c:choose>
							    
							        
							 
							
							</label>
							<label><form:radiobutton path="entity.authStatus"  value="H" /><spring:message code="eip.dept.auth.hold" /></label> 
						
							
						</div>
						
                 <input type="hidden" value="${logCount}" id="checker">
                 <form:hidden path="entity.empId" value="${command.entity.empId}" />
                 <form:hidden path="entity.emplType" value="${command.entity.emplType}" />
	
			 <div class="btn_fld clear padding_10">
					     <apptags:submitButton entityLabelCode="Authorization" successUrl="AdminAuthorization.html"/>
						    <apptags:backButton url="AdminAuthorization.html" />
					</div>
					</form:form>
	</div> 
				
		</div>
	</div>
</div>

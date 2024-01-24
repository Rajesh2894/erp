
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
               
<script type="text/javascript" src="js/commanremark/commanremark.js" >
</script>
        
<c:if test="${mode == 'create'}">

<div class="table-responsive">
    <table class="table table-hover table-bordered table-striped" id="reasonTableID">
				<tr>
					<th><spring:message code="common.master.remark.remeng" text="Remark (English)"/></th> 
					<th><spring:message code="common.master.remark.remreg" text="Remark (Hindi)"/></th> 
				</tr>
 				<tr class="remarkClass">
 		        <td>
		        <textarea id="remarkList0"  name="remarkList[0]" Class="input2 mandClassColor hasempNameClass form-control"   maxlength="1000"></textarea>
	            </td>

	            <td>
	            <textarea  id="remarkListreg0" name="remarkListreg[0]"  maxlength="1000" class="hasempNameClass  mandClassColor input2 form-control"></textarea>
	           </td> 
 				</tr>
 	</table>
	</div>
	    <div id="buttonAddReomoveBuildingDet"  class="text-center morebuttondetailsBuild padding-top-20">
	                            <input type="button" value="<spring:message code="master.save" text="Save"/>" class="btn btn-success btn-submit" onclick="openSaveForm()">	
								<input type="button" value="<spring:message code="common.master.btn.addrem" text="Add Remarks"/>" id="addMoreReasonDet"  class="btn btn-blue-2">
								<input type="button" value="<spring:message code="common.master.btn.rem" text="Remove"/>" id="removeMoreRejDet" class="btn btn-warning" >
								<c:url var="cancelButtonURL" value="/CommonRemarkMaster.html" />
				                <a role="button" class="btn btn-danger" href="${cancelButtonURL}"><spring:message code="contract.label.Back" text="Back"/></a>
		
			</div>
</c:if>
<c:if test="${mode != 'create'}">
<form:form id="serviceruleDefForm2" modelAttribute="tbApprejMas" name="tbApprejMas"  class="form-horizontal" action="#" method="post" commandName="tbApprejMas">
<c:if test="${mode == 'view'}">
<script>
$(document).ready(
		function() {
$('.form-control').attr('disabled','disabled');
								
		});					
										
	</script>									

</c:if>
<form:hidden path="artId"/>
<form:hidden path="lgIpMac"/>

<div class="table-responsive">
    <table class="table table-hover table-bordered table-striped" id="reasonTableID">
				<tr>
					<th><spring:message code="common.master.remark.remeng" text="Remark(English)"/></th> 
					<th><spring:message code="common.master.remark.remreg" text="Remark(Hindi)"/></th> 
				</tr>
				 <c:if test="${mode != 'create'}">	
				<tr class="remarkClass">
		        <td>
		        <form:textarea path="artRemarks"   id="remarkList0"  cssClass="input2 mandClassColor hasempNameClass form-control"   maxlength="1000"/>
	            </td>

	            <td>
	              <form:textarea path="artRemarksreg"   id="remarkListreg0"  cssClass="input2 mandClassColor hasempNameClass form-control"   maxlength="1000"/>
	           </td> 
	          
				</tr>
		    
				</c:if>
				<c:if test="${mode == 'create'}">	
				<tr >
				
		        <td>
		       <textarea  id="remarkList[0]"  name="remarkList[0]" Class="input2 mandClassColor hasempNameClass form-control"  maxlength="1000"></textarea>
	            </td>

	            <td>
	            <textarea id="remarkList[${count.index}]" name="remarkListreg[0]"  maxlength="1000" class="hasempNameClass  mandClassColor input2 form-control"></textarea> 
	           </td> 
	          
				</tr>
		    
				</c:if>
			
	</table>
	</div>
	    <div id="buttonAddReomoveBuildingDet"  class="text-center morebuttondetailsBuild margin-top-10">
	                            <input type="button" value="Submit" id="saveBtn" class="btn btn-success btn-submit" onclick="openSaveForm()">
	                         <c:if test="${EDIT == 'EDIT'}">
	                            <input type="button" value=" Inactivate Remarks" id="deletMoreReasonDet"  class="btn btn-danger" onclick="resetHomePage()">
	                            </c:if>
	                            <c:if test="${mode == 'create'}">	
								<input type="button" value="Add Remarks" id="addMoreReasonDet"  class="btn btn-success btn-submit">
								</c:if>
								<c:if test="${mode == 'create'}">
								<input type="button" value=" Inactivate Remarks" id="deletMoreReasonDet"  class="btn btn-danger">
								</c:if>
								<c:if test="${mode != 'view'}">
								<input type="button" value="Remove" id="removeMoreRejDet" class="btn btn-danger" >
								</c:if>
								<c:url var="cancelButtonURL" value="/CommonRemarkMaster.html" />
				                <a role="button" class="btn btn-danger" href="${cancelButtonURL}"><spring:message code="contract.label.Back" text="Back"/></a>
		
			</div>

</form:form>
</c:if>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script >


$( document ).ready(function() {

	$("#addMoreReasonDet").click(function () 
			{	
	
			if(checkValidnReasonDetails($("#reasonCountID").val()))
				{
				
					$("#reasonCountID").val(parseInt($("#reasonCountID").val())	+	1);	
					$('#reasonTableID tr:last').after(generateReasonTable($("#reasonCountID").val(),$('#hearingId').val(),$('#appStatus').val()));

				}		
			
			});
	
	$("#removeMoreRejDet").click(function () 
			{	
	
				var counter	=	$("#reasonCountID").val();
		
				if(counter > 0)
		 		{
					$("#reasonCurrentId"+counter).remove();
					
			  		counter	=	parseInt(counter)	-	1;
		
			  		$("#reasonCountID").val(counter);
				}
				else
					showErrormsgboxTitle("Atleast One" + $("#hoticeHearderID").html() + " needed"); 

			
			});
	});

function checkValidnReasonDetails(count)
{
	
	var firstName=	$("input[name='listOfAdditionalOwnerDetail["+count+"].additionalOwnerFirstName']").val();
	var title=   	$("select[name='listOfAdditionalOwnerDetail["+count+"].additionalOwnertTitle']").val();
	var lastName=   $("input[name='listOfAdditionalOwnerDetail["+count+"].additionalOwnerLastName']").val();
	
	if(firstName=="" && lastName=="" && title=="0"){
	showErrormsgboxTitle("Please Enter value in blank the field");
	return false;
	}else{
	return true;
	}
}
function generateReasonTable(count,hearingId,type)
{
	var str	='<tr id="reasonCurrentId'+count +'">'+	
	'<td>'+
	'<c:set var="baseLookupCode" value="TTL" />'+	
	 '<select name="listOfAdditionalOwnerDetail['+ count +'].additionalOwnertTitle" id="cpdTitle'+ count +'"  class="mandClassColor input2 ownerDetailsClass form-control" >'+
	 '<option value="0">'+$("#hiddenSelect").val()+'</option>'+
	 '	<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">'+
		'		<option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}" >${lookUp.lookUpDesc}</option> '+
		'</c:forEach>'+				
	'</select>'+ '</td>'+
		'<td><input id="listOfAdditionalOwnerDetail['+count+'].additionalOwnerFirstName"  name="listOfAdditionalOwnerDetail['+count+'].additionalOwnerFirstName" class=" mandClassColor input2 form-control"  type="text" value="" maxlength="40">'+
	'</td>	'+

	'<td>'+
		'<input id="listOfAdditionalOwnerDetail['+count+'].additionalOwnerMiddleName" name="listOfAdditionalOwnerDetail['+count+'].additionalOwnerMiddleName" type="text" maxlength="30" class="mandClassColor input2 form-control ">'+	
	'</td> '+
	'<td>'+
	'<input id="listOfAdditionalOwnerDetail['+count+'].additionalOwnerLastName'+count+'" name="listOfAdditionalOwnerDetail['+count+'].additionalOwnerLastName" type="text" maxlength="30" class="mandClassColor input2 form-control ">'+	
'</td> '+'</tr>';

	return str;

}
</script>



<form:hidden path="rowCount" id="reasonCountID" />
<form:hidden path="" id="hiddenSelect" value="Select" />

<div class="table-responsive">
    <table class="table table-hover table-bordered table-striped" id="reasonTableID">
				<tr>
					<th><spring:message code="tp.title" text="Title"/></th>	
					<th><spring:message code="tp.firstName" text="First Name"/></th> 
					<th><spring:message code="tp.middleName " text="Middle Name"/></th> 
					<th><spring:message code="tp.lastName" text="Last Name"/></th> 
				</tr>
				<c:forEach items="${command.listOfAdditionalOwnerDetail}" var="singleDoc" varStatus="count">	
				<tr>
				<td>
	              <c:set var="baseLookupCode" value="TTL" />	
	              <form:select path="listOfAdditionalOwnerDetail[${count.index}].additionalOwnertTitle" id="cpdTitle'+ ${count.index} +'"  class="mandClassColor input2 ownerDetailsClass form-control" >
	                 <form:option value="0">Select </form:option>form:option
		            <c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
		             <form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}" >${lookUp.lookUpDesc} </form:option> 
		            </c:forEach>			
	              </form:select>
	            </td>
		        <td>
		        <form:input  id="listOfAdditionalOwnerDetail[${count.index}].additionalOwnerFirstName"  path="listOfAdditionalOwnerDetail[${count.index}].additionalOwnerFirstName" cssClass="input2 mandClassColor hasempNameClass form-control"  type="text" value="" maxlength="40"/>
	            </td>

	            <td>
                <form:input id="listOfAdditionalOwnerDetail[${count.index}].additionalOwnerMiddleName" path="listOfAdditionalOwnerDetail[${count.index}].additionalOwnerMiddleName" type="text" maxlength="30" class="hasempNameClass  mandClassColor input2 form-control"/>	
	           </td> 
	            <td>
	           <form:input id="listOfAdditionalOwnerDetail[${count.index}].additionalOwnerLastName'${count.index}'" path="listOfAdditionalOwnerDetail[${count.index}].additionalOwnerLastName" type="text" maxlength="30" class="hasempNameClass  mandClassColor input2 form-control"/>	
               </td>
				</tr>
				
				</c:forEach>
	</table>
	</div>
	<div id="buttonAddReomoveBuildingDet"  class="morebuttondetailsBuild pull-right clear padding_top_10">			
								<input type="button" value="Add Additional Owner Details" id="addMoreReasonDet"  class="btn btn-success">
								<input type="button" value="Remove" id="removeMoreRejDet" class="btn btn-danger">
		
			</div>
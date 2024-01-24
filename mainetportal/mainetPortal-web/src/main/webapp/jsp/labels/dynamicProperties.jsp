<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="<c:url value ='/js/labels/dynamicLabels.js'/>"></script>
<h1>Label Update</h1>
	
	<div class="clear" id="content">

				<div class="form-div">
				<form:form method="post" action="DynamicLabelProperties.html" id="myForm" class="form" >
					
				<div class="table clear">
					<div class="col-7">
				<label for=""></label><b>Property File</b> 
				<form:select path="propertieFile" onchange="showKyeValueList(this)" id="selectedPropertiesFile"
								cssClass="mandClassColor input2">
							<form:option value="0" label="Select FileName" />
							<form:options items="${propertiesFileList}" />
						</form:select>
						</div>
						</div>
						
				<div id="id_div_property_dropdown" class="clear padding_10"></div>
				

				
				</form:form>
				<div class="overflow_auto"><table id='example' class='display clear'>
					<thead><tr><th>Name</th><th>Value</th><th>Action</th></tr></thead>
				</table>
				</div>
				

		
				</div>
			</div>
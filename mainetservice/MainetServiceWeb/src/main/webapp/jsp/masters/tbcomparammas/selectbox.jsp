<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<%
    response.setContentType("text/html; charset=utf-8");
%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">
	
	$( document ).ready(function() {
		var parentPrefix = $("#prefixName").val().split(',');
		var prefixCount = parentPrefix.length;
		for(var counter = 0 ; counter < prefixCount ; counter++) {		
			if( parentPrefix[counter].indexOf("[") != -1 || parentPrefix[counter].indexOf("]") != -1 ) {		
				var prefixTempstr = parentPrefix[counter].replace("[", "").replace("]", "");
				$("#prefixId_" + counter).text(prefixTempstr + " : ");			
			} else {				
				$("#prefixId_" + counter).text(parentPrefix[counter] + " : ");
			}			
		}		
	});
	
	function getComparamNextLevelData(obj) {
		
		var countVal = $("#countId").val();
		
		var curId = obj.id.split("_")[1];
		
		for(var c = curId ; c <= countVal ; c++) {
			
			$('#parentDataId_' + (parseInt(c) + 1)).html("");
			$('#parentDataId_' + (parseInt(c) + 1)).append($("<option></option>")
					   .attr("value","")
					   .text('select'));
			
		}
		
		
		var selectedId = $(obj).val();
		var url = "ComparamMaster.html?getComparentNextLevelData";
		var requestData = "selectedId="+selectedId;
		
		$.ajax({
			url : url,
			data : requestData,
			success : function(response) {
				
				$('#parentDataId_' + (obj.id.split("_")[1]) + 1).html("");
				$('#parentDataId_' + (obj.id.split("_")[1]) + 1)
			   .append($("<option></option>")
			   .attr("value","")
			   .text('select'));
				
				$.each(response, function(key, value) {
				 
					$('#parentDataId_' + (parseInt(obj.id.split("_")[1]) + 1))
					   .append($("<option></option>")
					   .attr("value",key)
					   .text(value));
				
				 });
				
				$("#comparentDetChildGrid").jqGrid('setGridParam', { datatype : 'json' }).trigger('reloadGrid');				
				
			},
			error : function(xhr, ajaxOptions, thrownError) {
				var errorList = [];
				errorList.push(getLocalMessage("admin.login.internal.server.error"));
				showError(errorList);
			}
		});	
		$("#comparentDetChildGrid").jqGrid('setGridParam', { datatype : 'json' }).trigger('reloadGrid');
	}
	
</script>

<c:set value="0" var="count" scope="page"></c:set>
<input type="hidden" id="prefixName" value="${prefixName}"/>
<c:forEach items="${parentDataList}" var="parentDataList">
	<div class="form-group padding-top-10">
    	<label class="col-sm-2 control-label required-control" id="prefixId_${count}"></label>
		<div class="col-sm-4">
			<select id="parentDataId_${count}" class="form-control" onchange="getComparamNextLevelData(this)">
				<option value=""><spring:message code="master.selectDropDwn"/></option>
				<c:forEach var="parentData" items="${parentDataList}" varStatus="itemsRow">
				<option value="${ parentData.key}">${parentData.value } </option>
				</c:forEach>
			</select>
		</div>
	</div>	
	<c:set value="${count+1}" var="count" scope="page"></c:set>
	
</c:forEach>
<input type="hidden" id="countId" value="${count}"/>
<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib	prefix="apptags" 	tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="/js/mainet/ui/jquery-ui.js"></script>
<script type="text/javascript" src="js/signpathmaster/signpathmaster.js"></script>

<script type="text/javascript">
	
	var i=0;
	var z=0;
	var x=0;

	$(document).ready(function(){
		
		if ($('#viewFlag').val() == "view") {	
			
			$('table.gridtable tr td textarea').addClass('input-disabled').attr('disabled','disabled');
			$('table.gridtable tr td select').addClass('input-disabled').attr('disabled','disabled');
			
			$('.form-elements .element textarea').addClass('input-disabled').attr('disabled','disabled');
			$('.form-elements .element input[type=text]').addClass('input-disabled').attr('disabled','disabled');
			
			$('table.gridtable tr td:last-child').hide()
			$('table.gridtable tr th:last-child').hide()
			$("#saveSignaDAta").hide();
		}
		
		
	if ($('#editFlag').val() == "edit") {	
			
			$('table.gridtable tr td textarea').removeClass('input-disabled').removeAttr('disabled','disabled');
			$('table.gridtable tr td select').removeClass('input-disabled').removeAttr('disabled','disabled');
			$('table.gridtable tr td:last-child').show()
			$('table.gridtable tr th:last-child').show()
			$("#saveSignaDAta").show();
		}
		
		});
	

 $("#wordwisetable").on(
		"click", '.addChargesLink',
	  function(e) {
			
			if($('#sizeOfZoneDetailsList').val() > 0){
			z = $('#sizeOfZoneDetailsList').val();
			
			i = Number(z) + x - 1;
			x++;
			}
		  	var content = $(this).closest('tr').clone();
			$(this).closest("tr").after(content);
			
			content.closest("tr").find("#parentLocationName").val('0')
			content.closest("tr").find("#parentLocationName").attr("name","tbSignPathZonedets["+(i+1)+"].codDwzid1")
			
			content.closest("tr").find("#desigEng").val('');
			content.closest("tr").find("#desigEng").attr("name","tbSignPathZonedets["+(i+1)+"].desigEng")
			
			content.closest("tr").find("#desigReg").val('');
			content.closest("tr").find("#desigReg").attr("name","tbSignPathZonedets["+(i+1)+"].desigReg")
			
			content.closest("tr").find("#signpath").val('');
			content.closest("tr").find("#signpath").attr("name","tbSignPathZonedets["+(i+1)+"].signpath")
			
			content.find("input:hidden:eq(0)").attr("value", "");
			content.find("input:hidden:eq(0)").attr("id", "spzId" + (i + 1));
			content.find("input:hidden:eq(0)").attr("name", "tbSignPathZonedets[" + (i + 1) + "].spzId");
			
			if($('#sizeOfZoneDetailsList').val() == NaN){
			i++;
			
			}
		}); 
 
</script>

<h1 class="form-elements padding_top_10"><spring:message code="" text="Report Details" /></h1>

<div id="Childcontent1" class="about">
			
	<c:url value="${saveAction}" var="action_url_form_submit" />
	
	
	<form:form action="${action_url_form_submit}" method="GET" name="" class="form" modelAttribute="tbSignPathDet">
		
		<form:hidden path="signId"  id="signId"/>
		<form:hidden path="addFlag"  id="addFlag"/>
		<form:hidden path="viewFlag"  id="viewFlag"/>
		<form:hidden path="editFlag"  id="editFlag"/>
		<form:hidden path="signdetId" id="signdetId"/>
		<form:hidden path="langId"  id="langId"/>
		<form:hidden path="orgid"  id="orgid"/>
		<c:set var="d" value="0"  scope="page"/>
		 
				<table class="gridtable padding_10 clear">
				<tr>
				<th width="20%"><spring:message code="common.master.report.name" text="Report Name"/></th>
				<th width="25%"><spring:message code="common.master.report.name.regional" text="Report Name Regional"/></th>
				<th width="25%"><spring:message code="common.master.report.desc" text="Report Description"/></th>
				<th width="25%"><spring:message code="common.master.prefix.name" text="Prefix Name"/></th>
				<th width="5%" ><spring:message code="common.master.hirarchical" text="Hirarchical SignPath"/></th>
				</tr>
				
				<tr>
				<td><form:textarea path="rdfname" class="input2 textarea-min mandClassColor" readonly="false" /></td>
				<td><form:textarea path="rdfnameReg" class="input2 textarea-min mandClassColor" readonly="false" /></td>
				<td><form:textarea path="reportdesc" class="input2 textarea-min mandClassColor" readonly="false" /></td>
				<td><form:textarea path="prefixName" class="input2 textarea-min mandClassColor" readonly="false" /></td>
				
				<c:choose>
				<c:when test="${tbSignPathDet.viewFlag eq 'view' || tbSignPathDet.editFlag eq 'edit'}">
				<td><form:checkbox path="prefixFlag" id="prefixFlag" value="Y" onclick="returnwithoutwordWeise()" disabled="disabled"/></td>
				</c:when>
				<c:otherwise>
				<td><form:checkbox path="prefixFlag" id="prefixFlag" value="Y" onclick="returnwithoutwordWeise()"/></td>
				</c:otherwise>		
				</c:choose>
				
				
				
				
				</tr>
				</table>
				
				<div class="element">
				<label><spring:message code=""  text="Secpath English"/> :</label>			
				<form:textarea path="secpathEng" class="mandClassColor" ></form:textarea>
				</div>	
				
				<div class="element">
				<label><spring:message code=""  text="Secpath Hindi"/> :</label>			
				<form:textarea path="secpathReg"  class="mandClassColor" ></form:textarea>
				</div>	
							
				<div class="element">
				<label><spring:message code=""  text="Note English"/> :</label>			
				<form:textarea path="noteEng" class="mandClassColor" ></form:textarea>
				</div>	
				
				<div class="element">
				<label><spring:message code=""  text="Note Hindi"/> :</label>			
				<form:textarea path="noteReg"  class="mandClassColor" ></form:textarea>
				</div>	
				
				<div class="element">
				<label><spring:message code=""  text="From No English"/> :</label>			
				<form:input path="formnoEng"  class="mandClassColor" ></form:input>
				</div>
				
				<div class="element">
				<label><spring:message code=""  text="From No Hindi"/> :</label>			
				<form:input path="formnoReg"  class="mandClassColor" ></form:input>
				</div>
				
				<table class="gridtable margin_top_20 clear" id="wordwisetable">
				<tr>
				<th width="20%"><spring:message code="common.master.ward" text="Ward"/></th>
				<th width="25%"><spring:message code="common.master.designation" text="Designation Eng"/></th>
				<th width="25%"><spring:message code="common.master.designation.hin" text="Designation Hindi"/></th>
				<th width="25%"><spring:message code="common.master.signature.path" text="Signature Path"/></th>
				<th width="5%" id="actionll"><spring:message code="common.master.action" text="Action"/></th>
				</tr>
				
				
				<c:choose>
				
  				<c:when test="${tbSignPathDet.viewFlag eq 'view' || tbSignPathDet.editFlag eq 'edit'}">
  				<input type="hidden" id="sizeOfZoneDetailsList" value="${tbSignPathDet.tbSignPathZonedets.size()}">
  				
    			<c:forEach var="tableEntity" items='${tbSignPathDet.tbSignPathZonedets}' varStatus="count">
				<tr  class="appendableClass" >
				<form:hidden path="tbSignPathZonedets[${count.index}].spzId"/>
				<td>
				<form:select path="tbSignPathZonedets[${count.index}].codDwzid1" id="parentLocationName" class="input2 mandClassColor">
    			<form:option value="0" label="-- Select --"></form:option>
				<c:forEach items="${wardList}" var="objArray">
				<form:option value="${objArray.lookUpId}" label="${objArray.descLangFirst}"></form:option>
				</c:forEach>
				</form:select>
				</td>
				<td><form:textarea path="tbSignPathZonedets[${count.index}].desigEng"  id="desigEng"  class="input2 textarea-min mandClassColor" readonly="false" /></td>
				<td><form:textarea path="tbSignPathZonedets[${count.index}].desigReg" id="desigReg" class="input2 textarea-min mandClassColor" readonly="false" /></td>
				<td><form:textarea path="tbSignPathZonedets[${count.index}].signpath" id="signpath" class="input2 textarea-min mandClassColor" readonly="false"  /></td>
				<td class="txt_center" ><a href='#'  onclick='return false;' class='addChargesLink'><i class="fa fa-plus-circle fa-2x"></i></a></td>
				</tr>
				</c:forEach>
  				</c:when>
  				
  				<c:otherwise>
    			<tr  class="appendableClass" >
    			
    			<form:hidden path="tbSignPathZonedets[${d}].spzId"  id="spzId" />
    			
				<td>
				<form:select path="tbSignPathZonedets[${d}].codDwzid1" id="parentLocationName" class="input2 mandClassColor">
    			<form:option value="0" label="-- Select --"></form:option>
				<c:forEach items="${wardList}" var="objArray">
				<form:option value="${objArray.lookUpId}" label="${objArray.descLangFirst}"></form:option>
				</c:forEach>
				</form:select>
				</td>
				<td><form:textarea path="tbSignPathZonedets[${d}].desigEng"  id="desigEng"  class="input2 textarea-min mandClassColor" readonly="false" /></td>
				<td><form:textarea path="tbSignPathZonedets[${d}].desigReg" id="desigReg" class="input2 textarea-min mandClassColor" readonly="false" /></td>
				<td><form:textarea path="tbSignPathZonedets[${d}].signpath" id="signpath" class="input2 textarea-min mandClassColor" readonly="false"  /></td>
				<td class="txt_center" ><a href='#'  onclick='return false;' class='addChargesLink'><i class="fa fa-plus-circle fa-2x"></i></a></td>
				<c:set var="d" value="${d + 1}" scope="page"/>
				</tr>
  				</c:otherwise>
				</c:choose>
				
				</table>
			
				<div class="form-elements padding_top_10" align="center">
				<input name="" type="button" value="Save" class="css_btn" id="saveSignaDAta" onclick="add(this)" />
				<c:if test="${tbSignPathDet.addFlag eq 'add' || tbSignPathDet.editFlag eq 'edit'}">
				<apptags:resetButton/>	
				</c:if>
				</div>
		
	</form:form>
	
	</div>
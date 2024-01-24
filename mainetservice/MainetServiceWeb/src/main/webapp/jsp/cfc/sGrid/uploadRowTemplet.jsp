<%@ taglib	prefix="apptags" 	tagdir="/WEB-INF/tags"%>
<%@ taglib	prefix="form"		uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="count" value="${command.scrutinyLabelDTO.coloumnCount - 1}"/>   
<tr id="sUpload${count}">										
	<td>
	<apptags:formField fieldType="7" labelCode="" hasId="true" folderName="${count}" maxFileCount="CHECK_LIST_MAX_COUNT"
			fieldPath="scrutinyDocs[${count}].attPath" fileSize="COMMOM_MAX_SIZE"
			validnFunction="CHECK_LIST_VALIDATION_EXTENSION"  isMandatory="false"
			showFileNameHTMLId="true" currentCount="${count}" />
			
	</td>
	<td><form:input path="scrutinyDocs[${count}].clmRemark"/></td>
</tr>
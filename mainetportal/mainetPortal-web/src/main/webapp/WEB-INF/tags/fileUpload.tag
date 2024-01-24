<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%> 

<%@ attribute name="fieldPath" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="labelCode" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="isMandatory" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="cssClass" required="false" rtexprvalue="true" type="java.lang.String" description="Custome CSS class(es) to be apply for element." %>
<%@ attribute name="showFileNameHTMLId" required="false" rtexprvalue="true" type="java.lang.String"%>

<jsp:useBean id="stringResolver" class="com.abm.mainet.common.util.StringUtility"/>

<c:set var="idappender" value="<%=java.util.UUID.randomUUID()%>" />
<c:set var="idappender" value="${fn:replace(idappender,'-','')}" />
<c:set var="id" value="${stringResolver.replaceAllDotPrefix(fieldPath)}"/>

<label for="${id}_${idappender}"><spring:message code="${labelCode}" text="${labelCode}"/> :</label>
<span><input type="file" name="${id}_${idappender}[]" id="${id}_${idappender}" onchange="doFileUploading(this,${id}_${idappender},'${id}','${showFileNameHTMLId}')" /></span>
<c:if test="${isMandatory}"><span class="mand">*</span></c:if>
<input type="hidden" id="elemPath_${id}_${idappender}" value="${stringResolver.getStringAfterChar('.',fieldPath)}"/>
<c:if test="${showFileNameHTMLId}">
	<div id="${id}_${idappender}_file_list"></div>
</c:if>
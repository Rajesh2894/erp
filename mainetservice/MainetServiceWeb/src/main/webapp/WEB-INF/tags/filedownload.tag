<%@ tag language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ attribute name="filename" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="filePath" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="actionUrl" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="eipFileName" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="dmsDocId" required="false"  %>
<%@ attribute name="elem" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="callback" required="false" rtexprvalue="true" type="java.lang.Boolean" %>

<!--escape quote [start] this use to remove ' from file name and add \' start  -->
<c:set var="fileName" value="${filename}" />
<c:set var="quote" value="'" />
<c:set var="escapeQuote" value="\\&apos;" />
<c:if test="${fn:contains(filename,quote)}">
<c:set var="fileName" value="${fn:replace(filename,quote,escapeQuote)}"/>
</c:if>
<!--escape quote [end]  -->
<c:set var="search" value="\\" />
<c:set var="replace" value="\\\\" />
<c:set var="path" value="${fn:replace(filePath,search,replace)}" />
<c:if test="${filename eq 'EIP'}">
<a href="javascript:void(0);" onclick="downloadFileInTag('${path}','${actionUrl}','${callback}','${elem}')"><i class="fa fa-download"></i> ${eipFileName}</a>
</c:if>
<c:if test="${filename ne 'EIP'}">
<c:choose>
<c:when test="${command.isDMS}">
<c:set var = "urlValue" value = "${actionUrl}"/>
 <c:set var = "url" value ="${fn:replace(urlValue, 'Download', 'DownloadFile')}" />
<%--  <c:set var = "url" value ="${command.getDmsPath()}" /> --%>
 <c:set var = "id" value ="${dmsDocId}" />
 
 <c:set var = "url" value ="${fn:replace(url, 'DOC_ID', id)}" />
 <c:set var = "url" value ="${fn:replace(url, 'DOC_ID', id)}" />
 <a href="javascript:void(0);" onClick="downloadDmsFile('${dmsDocId}','${fileName}','${url}')"> <i class="fa fa-download"></i> ${filename}</a>
</c:when>
<c:otherwise>
<!-- Defect #127371  -->
<a href="javascript:void(0);" onclick="downloadFileInTag('${path}${replace}${fileName}','${actionUrl}','${callback}','${elem}')"><i class="fa fa-download"></i> ${fileName}</a>
<%-- <a href="javascript:void(0);" onclick="downloadFile('${path}${replace}${fileName}','${actionUrl}')"><i class="fa fa-download"></i> ${filename}</a> --%>
</c:otherwise>
</c:choose>
</c:if>


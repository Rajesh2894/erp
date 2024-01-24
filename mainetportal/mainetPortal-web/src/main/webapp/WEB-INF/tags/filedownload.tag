<%@ tag language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ attribute name="filename" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="filePath" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="actionUrl" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="eipFileName" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="showIcon" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="showFancyBox" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="docImage" required="false" rtexprvalue="false" type="java.lang.Boolean" %>

<c:set var="search" value="\\" />
<c:set var="replace" value="\\\\" />
<c:set var="path" value="${fn:replace(filePath,search,replace)}" />
<c:set var="fileExtn" value="${fn:split(filename, '.')}" />
<c:set var="extn" value="${fileExtn[fn:length(fileExtn)-1]}" />
<c:set var="extnList" value="jpeg,jpg,png,gif,bmp"/>
<c:set var="videoExtnList" value="mp4,avi,3gp,mpg,swf,flv"/>
<%-- <spring:eval expression="T(com.abm.mainet.common.util.Utility).fileSize(filePath,filename)" var="fileSize"/> --%>	

<c:if test="${filename eq 'EIP'}">
 <a href="javascript:void(0);" aria-label="Download ${eipFileName}"onclick="downloadFile('${path}','${actionUrl}')" title="${eipFileName}">${eipFileName}<i class="fa fa-download" aria-hidden="true"></i></a>
</c:if>

<c:if test="${filename ne '' && filename ne 'EIP'}">

	<c:if test="${showIcon ne true }"><div class="card border-light"></c:if>
	
	<jsp:useBean id="test" class="com.abm.mainet.common.util.Utility"/>
	<c:set value="${test.getImageDetails(path)}" var="fpath"></c:set>
	<c:set value="${test.fileSize(filePath,'')}" var="fileSize"></c:set>
	
	
	<c:if test="${fpath eq ''}">
		<c:set var="myVar1" value="/${filename}" />
		<c:set var="myVar2" value="${path}${myVar1}" />
		<c:set value="${test.getImageDetails(myVar2)}" var="fpath"></c:set>
		<c:set value="${test.fileSize(filePath,filename)}" var="fileSize"></c:set>
	</c:if>
	
	<%-- <spring:eval expression="T(com.abm.mainet.common.util.Utility).getImageDetails('${path}${replace}${filename}')" var="imagePath"/> --%>
			<c:choose>
			<c:when test="${fn:contains(extnList, extn) }">
				<c:if test="${docImage ne true}">
					<c:if test="${showFancyBox eq true}">
						<a class="fancybox thumbnail" href="${fpath}" data-fancybox-group="images">
	                       <img src="${fpath}" class="img-thumbnail" alt="Loading please wait">
	                    </a>
					</c:if>
					<c:if test="${showFancyBox ne true}">
						<a href="javascript:void(0);" aria-label="Download ${filename}" onclick="downloadFile('${path}${replace}${filename}','${actionUrl}')" title="${filename}">
							<img src="./${fpath}" class="img-thumb"/>
						</a>
					</c:if>
					<div class="card-header2">
						<i class="fa fa-picture-o red-thumb" aria-hidden="true"></i>
						<div class="file-name"><span>${filename}</span>
						<p>${fileSize}</p></div>
					<a href="./${fpath}" title="Download" download><i class="fa fa-download" aria-hidden="true"></i></a>
					</div>
				</c:if>
				<c:if test="${docImage eq true}">
					<c:set value="${path}/${filename}" var="docimagepath"></c:set>
					<c:set value="${test.getImageDetails(docimagepath)}" var="fimagepath"></c:set>
						<a href="${fimagepath}" title="Download" download><i class="fa fa-download" aria-hidden="true"></i></a>
				</c:if>
			 
			
			</c:when>
			
			<c:when test="${fn:contains(videoExtnList, extn) }">
			<video width="100%" controls="controls">
				<source src="./${fpath}" type='video/mp4; codecs="avc1.42E01E, mp4a.40.2"'>
			</video>
			<div class="card-header2">
				<i class="fa fa-file-video-o red-thumb" aria-hidden="true"></i>
				<div class="file-name"><span>${filename}</span>
				<p>${fileSize}</p></div>
			<a href="./${fpath}" title="Download" download><i class="fa fa-download" aria-hidden="true"></i></a>
			</div>
			</c:when>
			
			<c:otherwise>
			<a href="javascript:void(0);" aria-label="Download ${filename}" onclick="downloadFile('${path}${replace}${filename}','${actionUrl}')" title="${filename}" class="view-extn-cont">
			<c:if test="${showIcon ne true}">
			<div class="view-${extn} img-thumb"></div>
			</c:if>
			<c:if test="${showIcon eq true }">
			<div class="view-${extn}-large"></div>
			</c:if></a>
		<%-- 	<embed src="./${imagePath}" class="view-${extn}" class="img-thumb"> --%>
		<%-- <object type="application/vnd.oasis.opendocument.text" data="./${imagePath}"></object> --%>
		<%-- <object width="775px" height="500px" classid="clsid:00020906-0000-0000-C000-000000000046" data="./${imagePath}"></object> --%>
			<c:if test="${showIcon ne true}">
			<div class="card-header2">
				<div class="thumbnail-${extn}"></div>
				<div class="file-name"><span>${filename}</span>
				<p>${fileSize}</p></div>
			<a href="./${fpath}" aria-label="Download ${filename}" title="Download" download><i class="fa fa-download" aria-hidden="true"></i></a>
			</div>
			</c:if>
			</c:otherwise>
			</c:choose>		
			
<c:if test="${showIcon ne true}"></div></c:if>
</c:if>
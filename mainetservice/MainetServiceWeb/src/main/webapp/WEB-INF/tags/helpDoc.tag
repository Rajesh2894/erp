<%@tag import="com.abm.mainet.common.exception.FrameworkException"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ attribute name="url" required="true" rtexprvalue="true" type="java.lang.String" description="url of the helpdoc"%>
<%@ attribute name="helpDocRefURL" required="false" rtexprvalue="true" type="java.lang.String" description="url of the helpdoc"%>


<div class="additional-btn">
		<c:choose>
			<c:when test="${not empty helpDocRefURL}">
			 <c:if test="${helpdocFile ne null}">
				<a href="${URL}?ShowHelpDoc&helpDocRefURL=${helpDocRefURL}" target="_new" data-toggle="tooltip" data-original-title="<spring:message code="help.doc" text="Help"/>"><i
						class="fa fa-question-circle fa-lg"><span class="hide"><spring:message code="help.doc" text="Help"/></span></i></a>
			</c:if> 
			<c:if test="${helpdocFile eq null}">
			<a onclick="helpDocMsg();" href="javascript:void(0);" data-toggle="tooltip" data-original-title="<spring:message code="help.doc" text="Help"/>"><i
						class="fa fa-question-circle fa-lg"><span class="hide"><spring:message code="help.doc" text="Help"/></span></i></a>
			</c:if>
			</c:when>
			<c:otherwise>
				<c:if test="${command.commonHelpDoc.filePath ne null and (not empty command.commonHelpDoc.filePath) }">
					<a href="${URL}?ShowHelpDoc" target="_new" data-toggle="tooltip" data-original-title="<spring:message code="help.doc" text="Help"/>"><i
						class="fa fa-question-circle fa-lg"><span class="hide"><spring:message code="help.doc" text="Help"/></span></i></a>

				</c:if>
				<c:if test="${command.commonHelpDoc.filePath eq null}">
					<a onclick="helpDocMsg();" href="javascript:void(0);" data-toggle="tooltip" data-original-title="<spring:message code="help.doc" text="Help"/>"><i
						class="fa fa-question-circle fa-lg"><span class="hide"><spring:message code="help.doc" text="Help"/></span></i></a>
				</c:if>
			</c:otherwise>
		</c:choose>
</div>
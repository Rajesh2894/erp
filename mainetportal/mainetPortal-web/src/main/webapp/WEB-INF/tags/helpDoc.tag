<%@tag import="com.abm.mainet.common.exception.FrameworkException"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ attribute name="url" required="true" rtexprvalue="true" type="java.lang.String" description="url of the helpdoc"%>

<div class="helpDocViewLabel">

<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
 <c:if test="${command.commonHelpDoc.filePath ne null and (not empty command.commonHelpDoc.filePath) }">
		<div class="additional-btn"><a href="${URL}?ShowHelpDoc" target="_blank"  data-toggle="tooltip" data-original-title="<spring:message code="cms.helpLable" />"><i class="fa fa-question-circle fa-lg"></i><span class="hide"><spring:message code="cms.helpLable" /> ?</span></a></div>
	</c:if>
	<c:if test="${command.commonHelpDoc.filePath eq null }">
		<div class="additional-btn"><a onclick="helpDocMsg();" id="help" class="tooltips"><i class="fa fa-question-circle fa-lg"></i><span class="hide"><spring:message code="cms.helpLable" /> ?</span></a></div>
	</c:if>
</c:if>
<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
<c:if test="${not empty command.commonHelpDoc.filePathReg and command.commonHelpDoc.filePathReg ne null  }">
		<div class="additional-btn"><a href="${URL}?ShowHelpDoc" target="_blank"  data-toggle="tooltip" data-original-title="<spring:message code="cms.helpLable" />"><i class="fa fa-question-circle fa-lg"></i><span class="hide"><spring:message code="cms.helpLable" /> ?</span></a></div>
	</c:if>
	<c:if test="${(command.commonHelpDoc.filePathReg eq null) or  (empty command.commonHelpDoc.filePathReg )}">
		<div class="additional-btn"><a onclick="helpDocMsg();" id="help" class="tooltips"><i class="fa fa-question-circle fa-lg"></i><span class="hide"><spring:message code="cms.helpLable" /> ?</span></a></div>
	</c:if>
</c:if>
    
	
</div>
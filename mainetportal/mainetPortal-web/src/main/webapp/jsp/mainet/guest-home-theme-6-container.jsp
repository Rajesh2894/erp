<%@ page import="org.w3c.dom.Document"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility" />

<c:set var="org_code" value="${userSession.getCurrent().getOrganisation().getOrgShortNm() }"/>
<c:choose>
	<c:when test="${org_code eq 'KDMC'}">
		<jsp:include page="/jsp/mainet/guest-home-theme-6.jsp" />
	</c:when>

	<c:when test="${org_code eq 'SKDCL'}">
		<jsp:include page="/jsp/mainet/guest-home-skdcl.jsp" />
	</c:when>
	<c:otherwise><!--ABM  -->
	<jsp:include page="/jsp/mainet/guest-home-theme-6.jsp" />
	</c:otherwise>
</c:choose>


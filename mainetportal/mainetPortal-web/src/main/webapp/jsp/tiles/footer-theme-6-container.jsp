<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="org_code" value="${userSession.getCurrent().getOrganisation().getOrgShortNm()}"/>
<c:choose>
	<c:when test="${org_code eq 'KDMC'}">
		<jsp:include page="/jsp/tiles/footer-theme-6.jsp" />
	</c:when>
	<c:when test="${org_code eq 'SKDCL'}">
		<jsp:include page="/jsp/tiles/footer-skdcl.jsp" />
	</c:when>
	<c:otherwise>
	<jsp:include page="/jsp/tiles/footer-theme-6.jsp" />
	</c:otherwise>
</c:choose>
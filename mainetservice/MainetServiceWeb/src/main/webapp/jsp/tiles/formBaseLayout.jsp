<%@ page import="com.abm.mainet.common.exception.FrameworkException" %>
<%@ page errorPage="/jsp/mainet/defaultExceptionView.jsp" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<tiles:insertAttribute name="app-body" ignore="false" />
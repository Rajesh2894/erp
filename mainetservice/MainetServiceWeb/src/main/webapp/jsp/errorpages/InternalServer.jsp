

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1>Internal Server Occur</h1>

<form:form action="CitizenHome.html" id="CitizenHome" method="get" class="form">

	<jsp:include page="/jsp/tiles/validationerror.jsp" />
	
	<input type="submit" value="Home" class="css_btn"/>

</form:form>
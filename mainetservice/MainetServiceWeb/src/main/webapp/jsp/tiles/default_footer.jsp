<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="year" value="${now}" pattern="YYYY" />
<div class="cover-footer clearfix">
    	<ul>
            <li></li>
            <li></li>
		  <a title="Copyright &copy; ABM" href="http://abmindia.com/" target="_new"><spring:message text="Copyright &copy; ABM" code="eip.copyright" /> ${year}</a>
        </ul>
    </div>
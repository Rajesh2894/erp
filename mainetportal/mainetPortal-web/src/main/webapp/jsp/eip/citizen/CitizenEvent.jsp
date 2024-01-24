<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<c:forEach items="${command.newsList}" var="list" varStatus="status">

Duration   : ${list.startDate}
Start Date : ${list.endDate}

</c:forEach>
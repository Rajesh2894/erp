<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<c:forEach var="lookUp" items="${command.touristLocationInfo}">
	<div class="section">
		<div class="text">${lookUp.title}</div>
		<div class="text">${lookUp.address}</div>
		<div class="description">${lookUp.lookUpDesc}</div>
		<c:if test="${not empty lookUp.locationImage}">
		<div class="profile_img"><img src="${lookUp.locationImage}" alt="${lookUp.locationImage}" title="${lookUp.locationImage}" /> </div>
		</c:if>
	</div>
</c:forEach>
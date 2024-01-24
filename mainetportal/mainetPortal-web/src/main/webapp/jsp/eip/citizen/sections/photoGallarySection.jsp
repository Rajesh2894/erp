<%@ taglib tagdir="/WEB-INF/tags" prefix="apptags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility"/>

<div class="section">
	<table class="RTI-form-table" style="width: 98%;">
		<c:forEach items="${lookUpList}" var="lookUps" varStatus="lk">
			
			<c:if test="${lookUp.lookUpType==MainetConstants.PROFILE_IMG}">
									<c:if test="${not empty lookUp.lookUpDesc}">
										
										<div class="profile_img move_up" style="position: absolute;top: 0px;right: 0px;"><img src="./${lookUp.lookUpDesc}" alt="${lookUp.lookUpDesc}" title="${lookUp.lookUpDesc}"/></div>
										
									</c:if>
			</c:if>	
			
		</c:forEach>
	</table>
</div>
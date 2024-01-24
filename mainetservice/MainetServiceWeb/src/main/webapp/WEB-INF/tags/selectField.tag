<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ attribute name="fieldPath" required="true" rtexprvalue="true"%>
<%@ attribute name="items" required="true" rtexprvalue="true" type="java.util.Collection"%>
<%@ attribute name="selectOptionLabelCode" required="true" rtexprvalue="true"%>
<%@ attribute name="hasId" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="changeHandler" required="false" rtexprvalue="true"%>
<%@ attribute name="isLookUpItem" required="false" rtexprvalue="true" description="Type of data-items pass for display. Default is true." %>
<%@ attribute name="readOnly" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="cssClass" required="false" rtexprvalue="true" type="java.lang.String" description="Custome CSS class(es) to be apply for element." %>

<jsp:useBean id="stringResolver" class="com.abm.mainet.common.utility.StringUtility"/>

<c:set var="id" value="${stringResolver.replaceAllDotPrefix(fieldPath)}"/>	
<c:choose>
	
	<c:when test="${readOnly}">
		<c:set var="fieldPropertyExpression" value="command.${fieldPath}"/>
		<spring:eval expression="${fieldPropertyExpression}" var="fieldPropertyValue"/>
		<c:forEach items="${items}" var="entry">
			<c:if test="${entry.lookUpCode eq fieldPropertyValue}">
				${entry.lookUpDesc}
				<form:hidden path="${fieldPath}" id="${id}"/>
			</c:if>
		</c:forEach>		
	</c:when>
	
	<c:otherwise>

		<c:choose>
			
			<c:when test="${isLookUpItem}">
				<c:choose>
					<c:when test="${hasId}">
							<form:select path="${fieldPath}" id="${id}" cssClass="${cssClass}" onchange="${changeHandler}">
								<form:option value="0"><spring:message code="${selectOptionLabelCode}" text="${selectOptionLabelCode}"/></form:option>
							   	<c:forEach var="lookUp" items="${items}">
							   		<form:option value="${lookUp.lookUpId}" code='${lookUp.lookUpCode}'>${lookUp.lookUpDesc}</form:option>
							   	</c:forEach>
							</form:select>
					</c:when>
					<c:otherwise>
						<form:select path="${fieldPath}" cssClass="${cssClass}" onchange="${changeHandler}">
							<form:option value="0"><spring:message code="${selectOptionLabelCode}" text="${selectOptionLabelCode}"/></form:option>
							   	<c:forEach var="lookUp" items="${items}">
							   		<form:option value="${lookUp.lookUpId}" code='${lookUp.lookUpCode}'>${lookUp.lookUpDesc}</form:option>
							   	</c:forEach>
							</form:select>
					</c:otherwise>
				</c:choose>
			</c:when>
			
			
			<c:otherwise>
			<c:choose>
				<c:when test="${hasId}">
					<form:select path="${fieldPath}" id="${id}" cssClass="${cssClass}" onchange="${changeHandler}">
						<form:option value="0"><spring:message code="${selectOptionLabelCode}" text="${selectOptionLabelCode}"/></form:option>
						<c:forEach items="${items}" var="entry">
							<form:option value="${entry.lookUpCode}">${entry.lookUpDesc}</form:option>
						</c:forEach>
					</form:select>
				</c:when>
				<c:otherwise>
					<form:select path="${fieldPath}" cssClass="${cssClass}" onchange="${changeHandler}">
						<form:option value="0"><spring:message code="${selectOptionLabelCode}" text="${selectOptionLabelCode}"/></form:option>
						<c:forEach items="${items}" var="entry">
							<form:option value="${entry.lookUpCode}">${entry.lookUpDesc}</form:option>
						</c:forEach>
					</form:select>
				</c:otherwise>
			</c:choose>
			</c:otherwise>
		</c:choose>
	
	</c:otherwise>
	
</c:choose>

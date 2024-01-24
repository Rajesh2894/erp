<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ attribute name="path" required="true" rtexprvalue="true"%>
<%@ attribute name="items" required="true" rtexprvalue="true" type="java.util.Collection"%>
<%@ attribute name="labelCode" required="true" rtexprvalue="false" type="java.lang.String"%>
<%@ attribute name="selectOptionLabelCode" required="true" rtexprvalue="true"%>
<%@ attribute name="changeHandler" required="false" rtexprvalue="true"%>
<%@ attribute name="isLookUpItem" required="false" rtexprvalue="true" description="Type of data-items pass for display. Default is true." %>
<%@ attribute name="readOnlyLabel" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="cssClass" required="false" rtexprvalue="true" type="java.lang.String" description="Custome CSS class(es) to be apply for element." %>
<%@ attribute name="isMandatory" required="false" type="java.lang.String" description="To show astrik mark if the field is mandatory."%>
<%@ attribute name="showAll" required="false" rtexprvalue="true" type="java.lang.Boolean" description="To show All Option"%>
<%@ attribute name="disabledOption" required="false" rtexprvalue="true" type="java.lang.Boolean"%>

<jsp:useBean id="stringResolver" class="com.abm.mainet.common.util.StringUtility"/>

<c:set var="id" value="${stringResolver.replaceAllDotPrefix(path)}"/>

<c:choose>
	<c:when test="${isMandatory}">
		<c:set var="mandcolor" value="mandColorClass" />
		<c:set var="requiredControl" value="required-control" />
	</c:when>
	<c:otherwise>
		<c:set var="mandcolor" value="" />
		<c:set var="requiredControl" value="" />
	</c:otherwise>
</c:choose>

<label class="col-sm-2 control-label ${requiredControl}" for="${id}"><spring:message code="${labelCode}" text="${labelCode}"/></label>
	<div class="col-sm-4">
<c:choose>
	<c:when test="${readOnlyLabel}">
		<c:set var="fieldPropertyExpression" value="command.${path}"/>
		<spring:eval expression="${fieldPropertyExpression}" var="fieldPropertyValue"/>
		<c:forEach items="${items}" var="entry">
			<c:if test="${entry.lookUpCode eq fieldPropertyValue}">
				${entry.lookUpDesc}
				<form:hidden path="${path}" id="${id}"/>
			</c:if>
		</c:forEach>		
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${isLookUpItem}">
							<form:select path="${path}" id="${id}" cssClass="form-control ${cssClass} ${mandcolor} " onchange="${changeHandler}" data-rule-prefixValidation="${isMandatory}" disabled="${disabledOption}">
								<form:option value="0"><spring:message code="${selectOptionLabelCode}" text="${selectOptionLabelCode}"/></form:option>
							   	<c:forEach var="lookUp" items="${items}">
							   		<form:option value="${lookUp.lookUpId}" code='${lookUp.lookUpCode}'>${lookUp.lookUpDesc}</form:option>
							   	</c:forEach>
							   	<c:if test="${showAll}">
									<form:option value="-1" code="ALL"><spring:message code="common.all" text="All" /></form:option>
								</c:if>	
							</form:select>
			</c:when>
			<c:otherwise>
					<form:select path="${path}" id="${id}" cssClass="form-control ${cssClass} ${mandcolor} " onchange="${changeHandler}" data-rule-prefixValidation="${isMandatory}" disabled="${disabledOption}">
						<form:option value="0"><spring:message code="${selectOptionLabelCode}" text="${selectOptionLabelCode}"/></form:option>
						<c:forEach items="${items}" var="entry">
							<form:option value="${entry.lookUpCode}">${entry.lookUpDesc}</form:option>
						</c:forEach>
						<c:if test="${showAll}">
							<form:option value="-1" code="ALL"><spring:message code="common.all" text="All" /></form:option>
						</c:if>	
					</form:select>
			</c:otherwise>
		</c:choose>
	
	</c:otherwise>
	
</c:choose>
</div>

<script>
$(document).ready(function(){
	   jQuery.validator.addMethod("prefixValidation", function(value, element) {
    	    return this.optional(element) || (parseFloat(value) > 0);
       }, getLocalMessage("common.empty.validation.message"));
	
});
</script>

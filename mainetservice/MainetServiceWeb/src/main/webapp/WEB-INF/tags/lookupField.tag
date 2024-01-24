<%@tag import="com.abm.mainet.common.exception.FrameworkException"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ attribute name="path" required="true" rtexprvalue="true"
	type="java.lang.String" description=""%>
<%@ attribute name="items" required="true" rtexprvalue="true"
	type="java.util.Collection" description=""%>
<%@ attribute name="selectedOption" required="false" rtexprvalue="true"
	type="java.lang.Long" description=""%>
<%@ attribute name="hasId" required="false" rtexprvalue="true"
	type="java.lang.Boolean" description=""%>
<%@ attribute name="selectOptionLabelCode" required="false"
	rtexprvalue="true" type="java.lang.String" description=""%>
<%@ attribute name="showAll" required="false" rtexprvalue="true"
	type="java.lang.Boolean" description=""%>
<%@ attribute name="hasChildLookup" required="false" rtexprvalue="true"
	type="java.lang.Boolean" description=""%>
<%@ attribute name="showOnlyLabel" required="false" rtexprvalue="true"
	type="java.lang.Boolean" description=""%>
<%@ attribute name="showOnlyLabelWithId" required="false"
	rtexprvalue="true" type="java.lang.Boolean" description=""%>
<%@ attribute name="changeHandler" required="false" rtexprvalue="true"
	type="java.lang.String" description=""%>
<%@ attribute name="cssClass" required="false" rtexprvalue="true"
	type="java.lang.String"
	description="Custome CSS class(es) to be apply for element."%>
<%@ attribute name="tabIndex" required="false" rtexprvalue="true"
	type="java.lang.String" description="Tabindex"%>
<%@ attribute name="isMandatory" required="false"
	type="java.lang.String"
	description="To show astrik mark if the field is mandatory."%>
<%@ attribute name="hasTableForm" required="false" rtexprvalue="true"
	type="java.lang.Boolean" description="Required in tabular format."%>
<%@ attribute name="disabled" required="false" rtexprvalue="true"
	type="java.lang.Boolean"%>
<%@ attribute name="pageId" required="false" rtexprvalue="false" type="java.lang.String"%>
<%@ attribute name="labelCode" required="false" rtexprvalue="false" type="java.lang.String"%>
<%@ attribute name="isNotInForm" required="false" rtexprvalue="true" type="java.lang.Boolean"%>

<jsp:useBean id="stringResolver"
	class="com.abm.mainet.common.utility.StringUtility" />

<c:choose>
				<c:when
						test="${pageId != null and labelCode != null and pageId != '' and labelCode != '' and command.appSession.isResAttrsOverridden(pageId,labelCode)}">
						<c:choose>
							<c:when test="${command.appSession.isMandatory(pageId,labelCode)}">
								<c:set var="mandcolor" value="mandColorClass" />
								<c:set var="isMandatory" value="true"/>
							</c:when>
							<c:otherwise>
								<c:set var="mandcolor" value="" />
								<c:set var="isMandatory" value="false"/>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${command.appSession.isVisible(pageId,labelCode)}">
								<c:set var="inputStyle" value="" />
							</c:when>
							<c:otherwise>
								<c:set var="inputStyle" value="display:none" />
							</c:otherwise>
						</c:choose>
						
					</c:when>
					 <c:otherwise>
						 <c:choose>
								<c:when test="${isMandatory}">
									<c:set var="mandcolor" value="mandColorClass" />
	            				</c:when>
								<c:otherwise>
									<c:set var="mandcolor" value="" />
								</c:otherwise>
							</c:choose>
						 </c:otherwise>
    			</c:choose>

<%
if((hasChildLookup!=null && hasChildLookup) && (changeHandler!=null	&&	changeHandler.length()	>	0))
{
	throw new FrameworkException("For any parent you cannot set 'changeHandler' when use for multilevel.");
}

if((showOnlyLabel!=null	&&	showOnlyLabel)	&&	(selectOptionLabelCode!=null	&&	selectOptionLabelCode.length()	==	0))
{
	throw new FrameworkException("'selectOptionLabelCode' must be specify when not use for only display	.");
}

%>

<c:if test="${!showOnlyLabel && !showOnlyLabelWithId}">

	<c:set var="onChange" value="${changeHandler}" />

	<c:set var="id" value="${stringResolver.replaceAllDotPrefix(path)}" />
	<c:if test="${showOnlyLabel ne null && !showOnlyLabel}">
	<c:choose>
		<c:when test="${!isNotInForm}">
			<c:set var="fieldPropertyExpression" value="command.${path}" />
		</c:when>
		<c:otherwise>
			<c:set var="fieldPropertyExpression" value="${path}" />
		</c:otherwise>
	</c:choose>
		<spring:eval expression="${fieldPropertyExpression}"
			var="fieldPropertyValue" />
	</c:if>



	<c:if test="${hasChildLookup}">
		<c:set var="cc"
			value="${fn:substring(id, fn:length(id)-1, fn:length(id))+1}" />
		<c:set var="childLookUpId"
			value="${fn:substring(id, 0, fn:length(id)-1)}${cc}" />
		<c:set var="onChange" value="getSubDetails(this,'${childLookUpId}')" />
	</c:if>

	<c:choose>
		<c:when test="${hasId}">
			<c:choose>
				<c:when test="${hasTableForm}">
					<form:select path="${path}" cssClass="${cssClass} ${mandcolor}"
						code="" id="${id}" onchange="${onChange}" disabled="${disabled}"
						tabindex="${tabIndex}">
						<form:option value="0" code="">
							<spring:message code="${selectOptionLabelCode}"
								text="${selectOptionLabelCode}" />
						</form:option>
						<c:forEach items="${items}" var="lookUp">
							<c:set var="selected" value="" />
							<c:choose>
								<c:when
									test="${not empty fieldPropertyValue && fieldPropertyValue ne 0}">
									<c:if test="${lookUp.lookUpId==fieldPropertyValue}">
										<c:set var="selected" value="selected" />
									</c:if>
								</c:when>
								<c:otherwise>
									<c:if test="${lookUp.defaultVal eq 'Y'}">
										<c:set var="selected" value="selected" />
									</c:if>
								</c:otherwise>
							</c:choose>
							<form:option value="${lookUp.lookUpId}"
								code="${lookUp.lookUpCode}" selected="${selected}">${lookUp.lookUpDesc}</form:option>
						</c:forEach>
						<c:if test="${showAll}">
							<form:option value="-1" code="ALL">
								<spring:message code="common.all" text="All" />
							</form:option>
						</c:if>
					</form:select>
				</c:when>
				<c:otherwise>
					<div class="col-sm-4" style="${inputStyle}">
						<form:select path="${path}"
							data-rule-prefixValidation="${isMandatory}"
							style="${inputStyle}"
							cssClass="${cssClass} ${mandcolor}" code="" id="${id}"
							disabled="${disabled}" onchange="${onChange}"
							tabindex="${tabIndex}">
							<form:option value="0" code="">
								<spring:message code="${selectOptionLabelCode}"
									text="${selectOptionLabelCode}" />
							</form:option>
							<c:forEach items="${items}" var="lookUp">
								<c:set var="selected" value="" />
								<c:choose>
									<c:when
										test="${not empty fieldPropertyValue && fieldPropertyValue ne 0}">
										<c:if test="${lookUp.lookUpId==fieldPropertyValue}">
											<c:set var="selected" value="selected" />
										</c:if>
									</c:when>
									<c:otherwise>
										<c:if test="${lookUp.defaultVal eq 'Y'}">
											<c:set var="selected" value="selected" />
										</c:if>
									</c:otherwise>
								</c:choose>
								<form:option value="${lookUp.lookUpId}" data-otherValue="${lookUp.otherField}"
									code="${lookUp.lookUpCode}" selected="${selected}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
							<c:if test="${showAll}">
								<form:option value="-1" code="ALL">
									<spring:message code="common.all" text="All" />
								</form:option>
							</c:if>
						</form:select>
					</div>
				</c:otherwise>
			</c:choose>



		</c:when>
		<c:otherwise>
			<form:select path="${path}"
				data-rule-prefixValidation="${isMandatory}"							
				style="${inputStyle}"
				cssClass="${cssClass} ${mandcolor}" code="" disabled="${disabled}"
				onchange="${onChange}" tabindex="${tabIndex}">
				<form:option value="0" code="">
					<spring:message code="${selectOptionLabelCode}"
						text="${selectOptionLabelCode}" />
				</form:option>
				<c:forEach items="${items}" var="lookUp">
					<c:set var="selected" value="" />
					<c:choose>
						<c:when
							test="${not empty fieldPropertyValue && fieldPropertyValue ne 0}">
							<c:if test="${lookUp.lookUpId eq fieldPropertyValue}">
								<c:set var="selected" value="selected" />
							</c:if>
						</c:when>
						<c:otherwise>
							<c:if test="${lookUp.defaultVal eq 'Y' }">
								<c:set var="selected" value="selected" />
							</c:if>
						</c:otherwise>
					</c:choose>
					<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}"
						selected="${selected}">${lookUp.lookUpDesc}</form:option>
				</c:forEach>
				<c:if test="${showAll}">
					<form:option value="-1" code="ALL">
						<spring:message code="common.all" text="All" />
					</form:option>
				</c:if>
			</form:select>
		</c:otherwise>
	</c:choose>
</c:if>

<c:if test="${showOnlyLabel}">
	<c:if test="${not empty selectedOption}">
		<c:set var="fieldPropertyValue" value="${selectedOption}" />
	</c:if>

	<c:if test="${empty selectedOption}">
	<c:choose>
		<c:when test="${!isNotInForm}">
			<c:set var="fieldPropertyExpression" value="command.${path}" />
		</c:when>
		<c:otherwise>
			<c:set var="fieldPropertyExpression" value="${path}" />
		</c:otherwise>
	</c:choose>
		<spring:eval expression="${fieldPropertyExpression}"
			var="fieldPropertyValue" />
	</c:if>
	<c:set value="0" var="allVar"></c:set>

	<c:forEach items="${items}" var="lookUp">
		<c:if test="${lookUp.lookUpId eq fieldPropertyValue}">
		<div class="col-sm-4 col-xs-4">	<span>${lookUp.lookUpDesc}</span></div>
			<c:set value="1" var="allVar"></c:set>
		</c:if>
	</c:forEach>

	<c:if test="${allVar eq 0}">
		<div class="col-sm-4 col-xs-4"><span> <spring:message code="common.all" text="All" /></span></div>
	</c:if>

</c:if>
<c:if test="${showOnlyLabelWithId}">
	<c:set var="id" value="${stringResolver.replaceAllDotPrefix(path)}" />
	<c:choose>
		<c:when test="${!isNotInForm}">
			<c:set var="fieldPropertyExpression" value="command.${path}" />
		</c:when>
		<c:otherwise>
			<c:set var="fieldPropertyExpression" value="${path}" />
		</c:otherwise>
	</c:choose>
	<spring:eval expression="${fieldPropertyExpression}"
		var="fieldPropertyValue" />

	<c:set value="0" var="allVar"></c:set>

	<c:forEach items="${items}" var="lookUp">
		<c:if test="${lookUp.lookUpId eq fieldPropertyValue}">
			<div class="col-sm-4">
				<form:input path="" id="${id}" value="${lookUp.lookUpDesc}"
					readonly="true" cssClass="${cssClass}" />
			</div>
			<c:set value="1" var="allVar"></c:set>
		</c:if>
	</c:forEach>

	<c:if test="${allVar eq 0}">

	</c:if>

</c:if>
<script>
$(document).ready(function(){
	   jQuery.validator.addMethod("prefixValidation", function(value, element) {
    	    return this.optional(element) || (parseFloat(value) > 0);
       }, getLocalMessage("common.empty.validation.message"));
	
});
</script>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ attribute name="path" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="isReadonly" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="isDisabled" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="cssClass" required="false" rtexprvalue="true" type="java.lang.String" description="Custome CSS class(es) to be apply for element." %>
<%@ attribute name="maxlegnth" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="isMandatory" required="false" type="java.lang.String" description="To show astrik mark if the field is mandatory."%>
<%@ attribute name="labelCode" required="true" rtexprvalue="false" type="java.lang.String"%>
<%@ attribute name="dataRuleMinlength" required="false" rtexprvalue="false" type="java.lang.String"%>
<%@ attribute name="pageId" required="false" rtexprvalue="false" type="java.lang.String"%>
<%@ attribute name="dataRuleEmail" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="onBlur" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="placeholder" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="onChange" required="false" rtexprvalue="true" type="java.lang.String"%>

<jsp:useBean id="stringResolver" class="com.abm.mainet.common.utility.StringUtility"/>

<c:set var="id" value="${stringResolver.replaceAllDotPrefix(path)}"/>	




				
			<c:choose>
				<c:when
						test="${pageId != null and labelCode != null and pageId != '' and labelCode != '' and command.appSession.isResAttrsOverridden(pageId,labelCode)}">
						<c:choose>
							<c:when test="${command.appSession.isMandatory(pageId,labelCode)}">
								<c:set var="mandcolor" value="mandColorClass" />
								<c:set var="requiredControl" value="required-control" />
								<c:set var="isMandatory" value="true"/>
							</c:when>
							<c:otherwise>
								<c:set var="mandcolor" value="" />
								<c:set var="requiredControl" value="" />
								<c:set var="isMandatory" value="false"/>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${command.appSession.isVisible(pageId,labelCode)}">
								<c:set var="inputStyle" value="" />
								<c:set var="labelstyle" value="" />
							</c:when>
							<c:otherwise>
								<c:set var="inputStyle" value="display:none" />
								<c:set var="labelstyle" value="display:none" />
								
							</c:otherwise>
						</c:choose>
						
					</c:when>
					 <c:otherwise>
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
					 </c:otherwise>
    			</c:choose> 
				<label style="${labelstyle}" class="col-sm-2 control-label ${requiredControl}" for="${id}"><spring:message code="${labelCode}" text="${labelCode}"/></label>
						
						<div class="col-sm-4" style="${inputStyle}">
						
						<form:input path="${path}" 
							style="${inputStyle}" 
							id="${id}" 
							cssClass="form-control ${cssClass} ${mandcolor}"
							readonly="${isReadonly}" 
							disabled="${isDisabled}" 
							maxlength="${maxlegnth}"
							data-rule-required="${isMandatory}"
							data-rule-minlength="${dataRuleMinlength}"
							data-rule-email="${dataRuleEmail}"
							onblur="${onBlur}"
							placeholder="${placeholder}"
							onchange="${onChange}"
							autocomplete="off"/>
						</div>
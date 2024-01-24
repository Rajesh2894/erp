<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%> 
<%@ attribute name="preHtml" required="false" rtexprvalue="true" type="java.lang.String" description="HTML content before lookUp feild tag."%>
<%@ attribute name="postHtml" required="false" rtexprvalue="true" type="java.lang.String" description="HTML content after lookUp feild tag."%>
<%@ attribute name="pathPrefix" required="true" rtexprvalue="true" description="LookUp field prefix path name."%>
<%@ attribute name="baseLookupCode" required="true" rtexprvalue="true" description="Prefix code."%>
<%@ attribute name="hasId" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Whether required manual id or not."%>
<%@ attribute name="showAll" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Need All option at the last"%>
<%@ attribute name="showOnlyLabel" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Whether to display only descption or not."%>
<%@ attribute name="showOnlyLabelWithId" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Whether to display only descption or not."%>
<%@ attribute name="cssClass" required="false" rtexprvalue="true" type="java.lang.String" description="Custome CSS class(es) to be apply for element." %>
<%@ attribute name="isMandatory" required="false" type="java.lang.String" description="To show astrik mark if the field is mandatory."%>
<%@ attribute name="hasLookupAlphaNumericSort" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Whether alphanumeric sort required."%>
<%@ attribute name="hasSubLookupAlphaNumericSort" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Whether alphanumeric sort required."%>
<%@ attribute name="disabled" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="hasTableForm" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Required in tabular format."%>
<%@ attribute name="showData" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Required to show header or Data format."%>
<%@ attribute name="columnWidth" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="isNotInForm" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<jsp:useBean id="stringResolver" class="com.abm.mainet.common.util.StringUtility"/>

<c:set var="select"><spring:message code="eip.select" text="Select" /></c:set>

<c:choose>
	<c:when test="${isMandatory}">
		<c:set var="mandcolor" value="mandColorClass" />
	</c:when>
	<c:otherwise>
		<c:set var="mandcolor" value="" />
	</c:otherwise>
</c:choose>
<c:set var="lookUps" value="${command.getLookupLabel(baseLookupCode)}"/>  

<c:choose>
		<c:when test="${hasTableForm}">
		
		
		<c:if test="${not showData}">
	<c:forEach items="${lookUps}" var="lookUp" varStatus="status">
	<c:set var="fieldPath" value="${pathPrefix}${status.count}" />	
	<c:set var="forId" value="${fieldPath}"/>	

		<c:if test="${hasId}">
			<c:set var="id" value="${stringResolver.replaceAllDotPrefix(fieldPath)}"/>	
			<c:set var="forId" value="${id}"/>
		</c:if>	
		<c:choose>
		<c:when test="${not empty columnWidth}">
		<th width="${columnWidth}">${lookUp.lookUpDesc}</th>	
		</c:when>	
			
		<c:otherwise>
		<th>${lookUp.lookUpDesc}</th>
		</c:otherwise>
		</c:choose>
      <%--  <c:if test="${!showOnlyLabel}">
        <c:if test="${isMandatory}">
				<label class="required-control"></label>
			</c:if>
        </c:if>  --%>
        </c:forEach>
        
        </c:if>
     
     <c:if test="${showData}">
        
     <c:forEach items="${lookUps}" var="lookUp" varStatus="status">
	<c:set var="fieldPath" value="${pathPrefix}${status.count}" />	
	<c:set var="forId" value="${fieldPath}"/>	

		<c:if test="${hasId}">
			<c:set var="id" value="${stringResolver.replaceAllDotPrefix(fieldPath)}"/>	
			<c:set var="forId" value="${id}"/>
		</c:if>	
		
		<c:choose>
			<c:when test="${not empty columnWidth}">
			<td width="${columnWidth}">	
			</c:when>	
			<c:otherwise>
			<td>
			</c:otherwise>
		</c:choose>
		<c:choose>
		<c:when test="${hasLookupAlphaNumericSort}">
			<c:choose>
				<c:when test="${fn:contains(pathPrefix, 'command.') and isNotInForm}">	
					<c:set var="pathPrefixTemp" value="${fn:substringAfter(pathPrefix, 'command.')}"></c:set>	
				</c:when>	
				<c:otherwise>
					<c:set var="pathPrefixTemp" value="${pathPrefix}"></c:set>
				</c:otherwise>
			</c:choose>
		<apptags:lookupField items="${command.getAlphaNumericSortedLevelData(baseLookupCode, status.count,pathPrefix)}" path="${fieldPath}" isMandatory="${isMandatory}" changeHandler="getSubAlphanumericLookUps(this,'${fn:length(lookUps)}','${hasSubLookupAlphaNumericSort}')" cssClass="${cssClass} ${mandcolor}" hasChildLookup="false" hasId="${hasId}" selectOptionLabelCode="${select}" showAll="${showAll}" showOnlyLabel="${showOnlyLabel}" hasTableForm="${hasTableForm}" isNotInForm="${isNotInForm}"/>
		</c:when>
		<c:when test="${showOnlyLabelWithId}">
		<apptags:lookupField items="${command.getLevelData(baseLookupCode, status.count)}" path="${fieldPath}" isMandatory="${isMandatory}" changeHandler="getSubAlphanumericLookUps(this,'${fn:length(lookUps)}','${hasSubLookupAlphaNumericSort}')" cssClass="${cssClass} ${mandcolor}" hasChildLookup="false" hasId="${hasId}" selectOptionLabelCode="${select}" showAll="${showAll}" showOnlyLabelWithId="${showOnlyLabelWithId}" hasTableForm="${hasTableForm}"/>
		</c:when>
		<c:otherwise>
		<apptags:lookupField items="${command.getLevelData(baseLookupCode, status.count)}" path="${fieldPath}" isMandatory="${isMandatory}" changeHandler="getSubAlphanumericLookUps(this,'${fn:length(lookUps)}','${hasSubLookupAlphaNumericSort}')" cssClass="${cssClass} ${mandcolor}" hasChildLookup="false" hasId="${hasId}" selectOptionLabelCode="${select}" showAll="${showAll}" showOnlyLabel="${showOnlyLabel}" hasTableForm="${hasTableForm}"/>
		</c:otherwise>
		</c:choose>	
		</td>
			
		
		
        </c:forEach>
        
        </c:if>
        
        
        
        </c:when>
        <c:otherwise>
<c:forEach items="${lookUps}" var="lookUp" varStatus="status">
	
	<c:set var="fieldPath" value="${pathPrefix}${status.count}" />	
	<c:set var="forId" value="${fieldPath}"/>	

		<c:if test="${hasId}">
			<c:set var="id" value="${stringResolver.replaceAllDotPrefix(fieldPath)}"/>	
			<c:set var="forId" value="${id}"/>
		</c:if>		

		<c:choose>
 	<c:when test="${isMandatory}">
		<label class="col-sm-2 col-xs-2 control-label required-control" for="${forId}">${lookUp.lookUpDesc}</label>
	</c:when>
	<c:otherwise>
		<label class="col-sm-2 col-xs-2 control-label" for="${forId}">${lookUp.lookUpDesc}</label>
	</c:otherwise>
    </c:choose>
		
		

		<c:choose>
		<c:when test="${hasLookupAlphaNumericSort}">
		<c:choose>
			<c:when test="${fn:contains(pathPrefix, 'command.') and isNotInForm}">	
				<c:set var="pathPrefixTemp" value="${fn:substringAfter(pathPrefix, 'command.')}"></c:set>	
			</c:when>	
			<c:otherwise>
				<c:set var="pathPrefixTemp" value="${pathPrefix}"></c:set>
			</c:otherwise>
		</c:choose>
		<apptags:lookupField isNotInForm="${isNotInForm}" items="${command.getAlphaNumericSortedLevelData(baseLookupCode, status.count,pathPrefix)}" path="${fieldPath}" isMandatory="${isMandatory}" changeHandler="getSubAlphanumericLookUps(this,'${fn:length(lookUps)}','${hasSubLookupAlphaNumericSort}')" cssClass="${cssClass} ${mandcolor}" hasChildLookup="false" hasId="${hasId}" selectOptionLabelCode="${select}" showAll="${showAll}" showOnlyLabel="${showOnlyLabel}" disabled="${disabled}"/>
		</c:when>
		<c:when test="${showOnlyLabelWithId}">
		<apptags:lookupField items="${command.getLevelData(baseLookupCode, status.count)}" path="${fieldPath}" isMandatory="${isMandatory}" changeHandler="getSubAlphanumericLookUps(this,'${fn:length(lookUps)}','${hasSubLookupAlphaNumericSort}')" cssClass="${cssClass} ${mandcolor}" hasChildLookup="false" hasId="${hasId}" selectOptionLabelCode="${select}" showAll="${showAll}" showOnlyLabelWithId="${showOnlyLabelWithId}" disabled="${disabled}"/>
		</c:when>
		<c:otherwise>
		<apptags:lookupField items="${command.getLevelData(baseLookupCode, status.count)}" path="${fieldPath}" isMandatory="${isMandatory}" changeHandler="getSubAlphanumericLookUps(this,'${fn:length(lookUps)}','${hasSubLookupAlphaNumericSort}')" cssClass="${cssClass} ${mandcolor}" hasChildLookup="false" hasId="${hasId}" selectOptionLabelCode="${select}" showAll="${showAll}" showOnlyLabel="${showOnlyLabel}" disabled="${disabled}"/>
		</c:otherwise>
		</c:choose>
     <%--  <c:if test="${!showOnlyLabel}">
       		<c:if test="${isMandatory}">
				<label class="required-control"></label>
			</c:if>
        </c:if> --%>
</c:forEach>
</c:otherwise>
</c:choose>


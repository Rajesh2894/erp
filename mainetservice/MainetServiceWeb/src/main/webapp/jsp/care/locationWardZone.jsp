<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



			<input type="hidden" path="" id="mobileNoRefId" value="${command.applicantDetailDto.mobileNo}" />	
						
	<apptags:lookupFieldSet baseLookupCode="${command.prefixName}" hasId="true" pathPrefix="command.careRequest.ward"
						isMandatory="true" hasLookupAlphaNumericSort="true" hasSubLookupAlphaNumericSort="true"
						cssClass="form-control required-control"  showAll="false" isNotInForm="true"/>    
 
						
					
						
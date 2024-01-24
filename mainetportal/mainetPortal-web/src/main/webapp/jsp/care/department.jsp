<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
			

<form:form>                  
 <apptags:select labelCode="care.department" items="${command.deptLookups}"  path="careRequestDto.departmentComplaint"  cssClass="chosen-select-no-results"
			                    selectOptionLabelCode="Select" isMandatory="true" isLookUpItem="true" changeHandler="getComplaintTypeAndLoc()"></apptags:select> 	
</form:form>


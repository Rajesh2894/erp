<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script
	src="js/mainet/SqlFormulaMaster.js"></script>

<script>


</script>


	<!--PAGE HEADING SECTION  -->



	<h1>
		<spring:message text="SQL Code Formula" code="adv.rep.sqlFormula" />
	</h1>
	<div id="content">
			

<c:if test="${command.entity.mode ne 'R'}">
					<div class="mand-label"><spring:message code="MandatoryMsg" text="MandatoryMsg" /></div>
</c:if>

					<form:form action="SqlFormulaMaster.html"
						name="SqlFormulaMaster" method="post" class="form"
						id="frmMaster">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						
						<div class="regheader"><spring:message text="Formula Details" code="adv.sqlHeading" /></div>
						
					<form:hidden path="entity.mode" id="mode"/>
                       <div class="form-elements">

							<div class="element" id="">
								<label for=""><spring:message code="UWMS.report.departmentname"
										text="Department Name" />:</label>
								<apptags:lookupField items="${command.departmentList}"
									path="entity.dpDeptid" showOnlyLabel="false" hasId="true"
									selectOptionLabelCode="aud.select.department"
									cssClass="mandClassColor" />
									<c:if test="${command.entity.mode ne 'R'}">
									<c:if test="${command.entity.mode ne 'E'}">
									
								<span class="mand">*</span>
								</c:if>
								</c:if>
							</div>

						</div>

						<div class="form-elements">
							<div class="element">
								<label for=""> <spring:message
										code="adv.rep.formulaCode" text="Formula Code"></spring:message>
									:
								</label>
								<form:input path="entity.sfCode" id="sfCode"
									cssClass="maxLength20  subsize mandClassColor " onblur="checkDuplicate()"
									readonly="false" hasId="true" />
									<c:if test="${command.entity.mode ne 'E'}">
									<c:if test="${command.entity.mode ne 'R'}">
								<span class="mand">*</span>
								</c:if>
								</c:if>
							</div>
						</div>

						<div class="form-elements clear">
							<div class="table clear">
								<div class="col-155">
									<label for=""> <spring:message code="adv.rep.stmt"
											text=" SQL statement"></spring:message> :
									</label>
								</div>

								<div class="col-9 padding_5">
									<form:textarea path="entity.sfSqlstmt" id="sfSqlstmt"
										cssClass="maxLength3000 mandClassColor input2"
										readonly="false" hasId="true" />
											<c:if test="${command.entity.mode ne 'R'}">
									
									</c:if>
								</div>
								<span class="mand">*</span>
							</div>
						</div>


						<div class="form-elements clear">
							<div class="table clear">
								<div class="col-155">

									<label for=""> <spring:message code="adv.rep.remarks" text=" Remarks"></spring:message>
										:
									</label>
								</div>
								<div class="col-9 padding_5">
									<form:textarea path="entity.sfRemarks" id="sfRemarks"
										cssClass="maxLength1950  input2"
										readonly="false" hasId="true" />
								</div>
							</div>
						</div>

						<c:if test="${command.entity.mode ne 'R'}">
							<div align="center" class="form-elements padding_10">
								
								<input class="css_btn" type="button" id="submit" value="<spring:message code="adv.rep.submit"></spring:message>"
									onclick="return saveOrUpdateForm(this, 'Submitted successfully!', 'SqlFormulaMaster.html','save');"></input>
								<input type="button" value="<spring:message code="rstBtn"/>"
								onclick="clearErrorDiv(this)"
									id="resettButon" class="css_btn">
						
							<apptags:backButton url="SqlFormulaMaster.html" />
							</div>
						</c:if>
						
						<c:if test="${command.entity.mode eq 'R'}">
						<div class="btn_fld margin_top_10">
						   <apptags:backButton url="SqlFormulaMaster.html" />
						 </div>  
						</c:if>
					</form:form>
					</div>
			
			
			
	
	




<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header" id="hiddenDiv">
			<h2>
				<spring:message code="bin.def.heading" text="Bin Definition Master" />
			</h2>
		</div>
		<div class="mand-label clearfix">
			<span><spring:message code="material.management.mand"
					text="Field with" /> <i class="text-red-1">*</i> <spring:message
					code="material.management.mand.field" text="is mandatory" /> </span>
		</div>
		<div class="widget-content padding ">

			<form:form class="form-horizontal" commandName="command"
				action="BinDefMaster.html" method="POST" name="binDefMasterFrm"
				id="binDefMasterFrm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<div class="form-group">
					<label class="control-label col-sm-2 required-control"> <spring:message
							code="binDefMaster.defName" text="Definition Name"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:input id="defName" path="binDefMasDto.defName"
							class="form-control" data-rule-required="true"
							data-rule-maxLength="200" onchange="" readonly="${command.modeType eq 'V'}"/>
					</div>

					<label class="control-label col-sm-2 required-control"> <spring:message
							code="binDefMasDto.priority" text="Priority"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:input path="binDefMasDto.priority" id="priority"
							cssClass="form-control hasNumber"
							data-rule-required="true" maxlength="4" readonly="${command.modeType eq 'V'}"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="control-label col-sm-2 required-control"> <spring:message
							code="material.item.master.description" text="Description"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:textarea id="description" path="binDefMasDto.description"
							class="form-control" data-rule-required="true"
							data-rule-maxLength="200" readonly="${command.modeType eq 'V'}"/>
					</div>
				</div>
                <br/>                
                
                <div class="form-group">
					<div class="text-center padding-bottom-20" id="divSubmit">
                        <c:if test="${command.modeType ne 'V'}">
							<button type="button" class="btn btn-success btn-submit"
								id="submit" onclick="proceed(this)">
								<spring:message code="material.management.submit" text="Submit"></spring:message>
							</button>
				        </c:if>		
						<input type="button" class="btn btn-danger"
							onclick="javascript:openRelatedForm('BinDefMaster.html');"
							value="<spring:message code="material.management.back" text="Back"/>" /> 
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>

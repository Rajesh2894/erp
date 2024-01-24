<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/asset/assetSchedulerMaster.js"></script>
<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content" id="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="asset.quartz.schedular"/>
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"><i
						class="fa fa-question-circle fa-lg"></i></a>
				</div>
			</div>
			
			
			<div class="widget-content padding">
				<form:form action="AssetSchedulerMaster.html"
					class="form-horizontal" name="AssetSchedulerMaster"
					id="AssetSchedulerMaster">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
						<c:if
					test="${fn:length(command.astschDto.errorSet)==0 && fn:length(command.astschDto.valuationSet)==0 }">
						<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="TypeOfDeprecation" ><spring:message
												code="asset.quartz.typedepreciation"/></label>
												<div class="col-sm-4">
												<label class="radio-inline"> <form:radiobutton
													path="astschDto.calculationType" value="Atype"
													class="radio_button" data-ptag="asset-type"
													label="Asset Code Wise"
													disabled="false" onclick="showAssetcode(this);"/>
											</label> <label class="radio-inline"> <form:radiobutton
													path="astschDto.calculationType" value="Aclass"
													class="radio_button" data-ptag="asset-class"
													label="Asset Class Type Wise "
													disabled="false"  onclick="showAssetcode(this);"/>
											</label>					
											</div>						
									</div>
								<div id="depre-dpt" class="p_element">	
									<div class="form-group">
										<apptags:input labelCode="asset.information.assetcode"
								    path="astschDto.assetCode"
								   isMandatory="true"
								   isDisabled="false" maxlegnth="50" ></apptags:input>
									</div>
									
									<%-- <form:input class="form-control datepicker"
													id="assetDateField" maxlength="10"
													data-label="#dispoDate"
													path="AstSchedulerMasterDTO.assetDateField" isMandatory="true" onchange="assetdatewisedetails();"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span> --%>
										</div>			
									</div>				
										
                                         
									
									
									
									
									<div id="p_assetclass" class="p_assetclass">
									<div class="form-group">
									<label class="col-sm-2 control-label required-control" for=""> <spring:message
									code="asset.info.assetclass" />
							      </label>
									<div class="col-sm-4">
										<form:select
											path="astschDto.assetClass"
											id="assetClass" data-rule-required="true" isMandatory="true"
											disabled="false"
											cssClass="form-control required-control chosen-select-no-results">
											<form:option value="">
												<spring:message code="asset.info.select" />
											</form:option>
											<c:forEach items="${assetClassAST}" var="lookUp">
												<form:option value="${lookUp.lookUpId}">${lookUp.descLangFirst}</form:option>
											</c:forEach>
										</form:select>	
										</div>	
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label required-control" for=""><spring:message
												text="Date Field" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control datepicker"
													id="assetDateField" maxlength="10"
													data-label="#dispoDate"
													path="astschDto.assetDateField" isMandatory="true"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>			
									</div>		
									</c:if>
<!------------------------- this is for error if present in your depreciation file it will show  start --------------------------->			
					<c:if test="${fn:length(command.astschDto.errorSet)>0}">
					<h4 class="margin-bottom-10">Error Log</h4>
					<div id="errorTable">
						<table class="table table-bordered table-striped"
							id="errorTableRateType" >
							<thead>
								<tr>
									<%-- <th width="20%"><spring:message
											code="asset.quartz.orgid"/></th> --%>
									<th width="20%"><spring:message
											code="asset.quartz.assetcode" /></th>
									<th width="80%"><spring:message
											code="asset.quartz.errordata" /></th>
								</tr>
							</thead>
							<c:forEach items="${command.astschDto.errorSet}"
								var="errordto">
								<tr>
									<%-- <td><form:input id="orgId"
											path="astQuartschDto.asetErrorDto.orgId" class=" form-control"
											readonly="true" /></td> --%>
									<td>${errordto.assetCode}</td>
									<td>${errordto.error}</td>
								</tr>
								</c:forEach>
						</table>
					</div>
				</c:if>	
<!--------------------------- this is for error if present in your depreciation  file it will show  end -------------------------------->

<!------------------------- this is for error if present in your depreciation done file it will show  start --------------------------->			
					<c:if test="${fn:length(command.astschDto.valuationSet)>0}">
					<h4 class="margin-bottom-10">Depreciation Details</h4>
					<div id="depTable">
						<table class="table table-bordered table-striped"
							id="depTableRateType">
							<thead>
								<tr>
									<th><spring:message
											code="asset.quartz.assetcode"/></th>
									<th><spring:message
											code="asset.quartz.accumlateddep"/></th>
									<th><spring:message
											code="asset.quartz.bookvalue"/></th>
											<th><spring:message
											code="asset.quartz.bookendvalue"/></th>
											<th><spring:message
											code="asset.quartz.deprecaitioncharge"/></th>
								</tr>
							</thead>
							<c:forEach items="${command.astschDto.valuationSet}"
								var="valudto">
								<tr>
									<td align="center">${valudto.assetCode}</td>
											<td align="right">${valudto.accumDeprValue}</td>
									<td align="right">${valudto.previousBookValue}</td>	
											<td align="right">${valudto.currentBookValue}</td>
											<td align="right">${valudto.deprValue}</td>
								</tr>
								</c:forEach>
						</table>
					</div>
				</c:if>	
<!--------------------------- this is for error if present in your depreciation done  file it will show  end -------------------------------->
<div class="text-center padding-top-20">
<c:if
					test="${fn:length(command.astschDto.valuationSet)==0  && fn:length(command.astschDto.errorSet)==0 }">
					<button type="button" class="button-input btn btn-success"
						name="button" value="Save" onclick="CalculateDeprecation(this)"
						id="save">
						<spring:message code="asset.quartz.calculatedep"/>
					</button>
					</c:if>
					 <button type="Reset" class="btn btn-warning" onclick="resetData()" ><spring:message code="asset.quartz.reset"/></button>
					<apptags:backButton url="AdminHome.html"
						cssClass="btn btn-primary"></apptags:backButton>
			</div>

				</form:form>
			</div>

		</div>
	</div>
</div>
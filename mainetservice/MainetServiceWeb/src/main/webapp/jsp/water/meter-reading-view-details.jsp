<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<!-- <ol class="breadcrumb">
  <li><a href="../index.html"><i class="fa fa-home"></i></a></li>
  <li>Services</li>
  <li>Meter View Details</li>
</ol> -->
<!-- Start Content here -->
<div class="content max-width-700"> 
  <!-- Start info box -->
  <div class="widget">
    <div class="widget-header">
      <h2><spring:message code="water.meter.view.details"/></h2>
    </div>
    <div class="widget-content padding">
      <form:form action="MeterReading.html" method="post" class="form-horizontal">
        <div class="form-group">
          <label class="col-sm-2 control-label"><spring:message code="water.meterReadingViewDetails.Name"/></label>
          <div class="col-sm-4">
            <form:input path="viewDTO.name" type="text" class="form-control" readonly="true"></form:input>
          </div>
          <label class="col-sm-2 control-label"><spring:message code="water.plumberLicense.address"/><spring:message code="water.meterReadingViewDetails.Address"/></label>
          <div class="col-sm-4">
            <form:input path="viewDTO.address" type="text" class="form-control" readonly="true"></form:input>
          </div>
        </div>
        
        <div class="form-group">
          <label class="control-label col-sm-2"><spring:message code="water.meterReadingViewDetails.PhysicalConnectionDate"/></label>
          <div class="col-sm-4">
            <div class="input-group">
            <fmt:formatDate pattern="dd/MM/yyyy"  value="${command.viewDTO.pcDate}" var="pcdate"/>
              <form:input path="" id="datepicker" value="${pcdate}" type="text" class="form-control" readonly="true"></form:input>
              <label for="datepicker" class="input-group-addon"><i class="fa fa-calendar"></i></label>
            </div>
          </div>
          <label class="control-label col-sm-2"><spring:message code="water.meterReadingViewDetails.MeterInstallationDate"/></label>
          <div class="col-sm-4">
            <div class="input-group">
            <fmt:formatDate pattern="dd/MM/yyyy"  value="${command.meterMas.mmInstallDate}" var="installdate"/>
              <form:input path="" value="${installdate}"  id="datepicker2" type="text" class="form-control" readonly="true"></form:input>
              <label for="datepicker2" class="input-group-addon"><i class="fa fa-calendar"></i></label>
            </div>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label"><spring:message code="water.meterDet.mtrMake"/></label>
          <div class="col-sm-4">
            <form:input path="meterMas.mmMtrmake" type="text" class="form-control" readonly="true"></form:input>
          </div>
          <label class="col-sm-2 control-label"><spring:message code="water.meterDet.metNo"/></label>
          <div class="col-sm-4">
            <form:input path="meterMas.mmMtrno" type="text" class="form-control" readonly="true"></form:input>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label"><spring:message code="water.meterDet.mtrCost"/></label>
          <div class="col-sm-4">
            <form:input path="meterMas.mmMtrcost" type="text" class="form-control" readonly="true"></form:input>
          </div>
          <label class="col-sm-2 control-label"><spring:message code="water.meterDet.ownerShip"/></label>
          <div class="col-sm-4">
            <form:input path="meterMas.meterOwnerShip" type="text" class="form-control" readonly="true"></form:input>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label"><spring:message code="water.meter.cutOffMeterIntialReading"/></label>
          <div class="col-sm-4">
            <form:input path="meterMas.mmInitialReading" type="text" class="form-control" readonly="true"></form:input>
          </div>
          <label class="col-sm-2 control-label"><spring:message code="water.meterDet.mtrMaxRead"/></label>
          <div class="col-sm-4">
            <form:input path="meterMas.maxMeterRead" type="text" class="form-control" readonly="true"></form:input>
          </div>
        </div>
        
        <div class="table-responsive">
        
            <table class="table table-bordered table-condensed">
              
              <tr><th><spring:message code="WaterDisconnectionModel.connectionNo"/></th>
                  <th><spring:message code="water.meterReadingViewDetails.ConnectionStatus"/></th>
                  <th><spring:message code="water.meter.cutOffMeterReadingDate"/></th>
                  <th><spring:message code="water.meterReadingViewDetails.MeterReading"/></th>
                  <th><spring:message code="water.meterReadingViewDetails.Typeofperiod"/></th>
                  <th><spring:message code="water.met.status"/></th>
                  <th><spring:message code="water.meterReading.GapCode"/></th>
                  </tr>
                <tr>
                 <td><form:input path="viewDTO.csCcn" type="text" class="form-control" readonly="true"></form:input></td>
              <td>
              <%-- <form:input path="viewDTO.conStatus" type="text" class="form-control" readonly="true"></form:input> --%>
               <form:input path="" type="text" class="form-control" value="Active" readonly="true"></form:input>
              </td>
              <td>
               <fmt:formatDate pattern="dd/MM/yyyy"  value="${command.viewDTO.mrdMrdate}" var="readDate"/>
              <form:input path="" value="${readDate}" type="text" class="form-control" readonly="true"></form:input>
              </td>
              <td><form:input path="viewDTO.mrdMtrread" type="text" class="form-control" readonly="true"></form:input></td>
              <td><form:input path="viewDTO.status" type="text" class="form-control" readonly="true"></form:input></td>
              <td><form:input path="viewDTO.meterStatusCode" type="text" class="form-control" readonly="true"></form:input></td>
              <td><form:input path="viewDTO.meterGapCode" type="text" class="form-control" readonly="true"></form:input></td>
                  </tr>
                <tr>
                  <th><spring:message code="water.meterReadingViewDetails.TotalDays"/></th>
                  <th><spring:message code="water.meterReadingViewDetails.Consumption"/></th>
                  <th><spring:message code="water.meterReadingViewDetails.MeterPhoto"/></th>
                  <th><spring:message code="water.meterReadingViewDetails.BillGenerationStatus"/></th>
                  <th><spring:message code="water.meterReadingViewDetails.BillNo."/></th>
                  <th><spring:message code="water.meterReadingViewDetails.BillFromDate"/></th>
                  <th><spring:message code="water.metetReadingViewDetails.BillToDate"/></th>
                </tr>
                <tr>
                  <td><form:input path="viewDTO.ndays" type="text" class="form-control" readonly="true"></form:input></td>
              <td><form:input path="viewDTO.csmp" type="text" class="form-control" readonly="true"></form:input></td>
              
               <td><%-- <c:set var="links" value="${fn:substringBefore(command.viewDTO.mrdImagePath, command.viewDTO.mrdImageName)}" /> --%>
			  <c:if test="${not empty command.viewDTO.mrdImagePath}">
			   <apptags:filedownload filename="${command.viewDTO.mrdImageName}" filePath="${command.viewDTO.mrdImagePath}" actionUrl="MeterReading.html?Download"></apptags:filedownload>
				</c:if>
				<c:if test="${empty command.viewDTO.mrdImagePath}">
				<span><spring:message code="water.meter.image"/></span>
				</c:if>
				</td>	
             <%--  <td><form:input path="viewDTO.mrdImagePath" type="text" class="form-control"></form:input></td> --%>
              
              <td><form:input path="billMas.bmRemarks" type="text" class="form-control" readonly="true"></form:input></td>
              <td><form:input path="billMas.bmNo" type="text" class="form-control" readonly="true"></form:input></td>
              <td>
               <fmt:formatDate pattern="dd/MM/yyyy"  value="${command.billMas.bmFromdt}" var="billFromDate"/>
              <form:input path="" type="text" class="form-control" value="${billFromDate}"  readonly="true"></form:input>
              </td>
              <td>
               <fmt:formatDate pattern="dd/MM/yyyy"  value="${command.billMas.bmTodt}" var="billTODate"/>
              <form:input path="" type="text" value="${billTODate}" class="form-control" readonly="true"></form:input>
              </td>
                
                
<!--                   <td class="text-center"><a data-toggle="tooltip" data-placement="top" title="" class="btn btn-blue-2 btn-sm" data-original-title="View"><i class="fa fa-file-text-o"></i></a></td>
 -->                  
                </tr>
                
              </table>
          
        </div>
       <!--  <div class="text-center padding-top-10">
          <button type="button" class="btn btn-success">Back</button>
        </div> -->
      </form:form>
    </div>
  </div>
  <!-- End of info box --> 
</div>
<!-- End Content here --> 

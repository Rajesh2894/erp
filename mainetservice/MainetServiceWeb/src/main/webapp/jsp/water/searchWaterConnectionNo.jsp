 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="widget max-width-700 margin-bottom-0">
  <div class="widget-header">
    <h2><strong><spring:message code="water.connnection" /></strong> <spring:message code="water.search.number" /></h2>
  </div>
  <div class="widget-content">
    <form:form action="WaterDisconnectionForm.html" class="form-horizontal" name="frmwaterDisconnectionSearch" id="frmwaterDisconnectionSearch" method="post">
      
       		 
   		
				<apptags:jQgrid id="WaterDisconnection"
					url="WaterDisconnectionForm.html?WATER_CONNECTION_NO_SEARCH" mtype="post"
					gridid="gridWaterDisconnection"
					colHeader="water.consumerName,water.ConnectionNo,water.consumerName,water.areaName,water.select"
					colModel="[
								{name : 'csName',index : 'csName',editable : false,sortable : true,search : true, align : 'center'}, 
								{name : 'csCcn',index : 'csCcn',editable : false,sortable : true,search : true, align : 'center'},
								{name : 'consumerName',index : 'consumerName',editable : false,sortable : true,search : true, align : 'center'},
								{name : 'csAdd',index : 'csAdd',editable : false,sortable : true,search : true, align : 'center'},
								{name : 'viewMode',index : 'viewMode',width :'70',sortable : false,formatter  : viewModeTemplate,search : false}
								]"
					height="200" caption="" isChildGrid="true" hasActive="false"
					hasViewDet="false" hasEdit="false" hasDelete="false"
					loadonce="true" sortCol="rowId" showrow="false" />

			
   		 
    </form:form>
  </div>
</div>

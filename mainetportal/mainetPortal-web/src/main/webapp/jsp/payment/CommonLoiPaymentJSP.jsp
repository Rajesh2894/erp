<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script src="js/payment/CommonLoiPayment.js"></script>

<div class="form-div">

	<ul class="breadcrumbs">
		<li><a href="CitizenHome.html"><spring:message
					code="menu.home" /></a></li>
        <li><a href="#"><spring:message code="onl.payment"  /></a></li>
		<li class="active"><spring:message 	code="onl.loi.payment" /></li>
	</ul>

	
	<h1>
		<spring:message  code="onl.loi.payment" />
	</h1>



	<div id="content">
		

		<form:form action="CommonLoiPayment.html" cssClass="form clear">
			<jsp:include page="/jsp/tiles/validationerror.jsp" />

		
			<div id="deptName" class="form-elements">
						<div class="element">
								<label for="">
									
									<spring:message	code="onl.loi.deptName"   /> :
								</label>
								<span>
									<apptags:lookupField items="${command.getDepartmentLookUp()}" 
					   					 path="entity.deptId"
					   					 changeHandler=""
										 cssClass="subsize"
										 selectOptionLabelCode="--Select Your Dept--" hasId="true"  isMandatory="true"/>
								</span>
								
								
						</div>
						</div>
			<div class="form-elements clear">
				<div class="element">
					<label for="LOI No."><spring:message code="onl.loi.loiNo" /> </label>
				     <form:input path="entity.loiNo"  id="loiNoId" maxlegnth="16" /> 
				</div>
				<div class="element">
					<label for="Application No."><spring:message
							code="onl.loi.applNo" /> </label>
					 <form:input path="entity.apmApplicationId" id="appId" cssClass="hasNumber" maxlegnth="16"/>	
				</div>
			</div>
			
			<div class="btn_fld padding_10">
				       
				        <input type="button" value="<spring:message code="bt.search"/>"  onclick="findAll(this)" class="css_btn"/>
		                
		                <a href="#"  class="css_btn" onclick="emptyForm(this)">
						   <spring:message code="bt.clear" />
					   </a>	
			 </div>
		

		<div class="margin_top_10">
			<apptags:jQgrid id="LOIPaymentSearch" url="CommonLoiPayment.html?LANDING_PAGE_SEARCH_RESULTS"
				mtype="post" gridid="gridCommonLoiPayment"
				colHeader="onl.loi.loiNo,onl.loi.loiDate,onl.loi.applNo,onl.loi.serviceName"
				colModel="[                                 
                           {name : 'loiNo',index : 'loiNo',editable : false,sortable : true,search : true,width :'90'},
                           {name : 'loiDate',index : 'loiDate',editable : false,sortable : true,search : true,width :'80',formatter : dateTemplate},                                         
                           {name : 'apmApplicationId',index : 'apmApplicationId',editable : false,sortable : true,search : true,width :'90'},
                           {name : 'smServiceName',index : 'smServiceName',editable : false,sortable : true,search : true,width :'235'}
                          ]"
				sortCol="rowId" isChildGrid="false" hasActive="false" viewAjaxRequest="true"
				hasViewDet="true" hasDelete="false" height="350" showrow="true"
				caption="LOI Payment Details" loadonce="true" />
		</div>
		</form:form>
	</div>
</div>


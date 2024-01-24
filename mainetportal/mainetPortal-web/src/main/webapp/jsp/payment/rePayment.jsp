<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

		
	

<div class="clearfix" id="home_content">
	<div class="col-xs-12">

		<div class="row">
		
		
			<div class="form-div">
							<h3 align="center"><spring:message code=""  text="Failure Services Information"/></h3>
			
				<!-- <div class="form-elements">

					<span class="otherlink"> 
					<a href="javascript:void(0);" class="btn btn-primary" onclick="findAll(this)">
					</span>
				</div> -->
				<form:form method="post" action="RePayment.html">
				<jsp:include page="/jsp/tiles/validationerror.jsp"></jsp:include>
				<div class="mand-label"><spring:message code="MandatoryMsg" text="MandatoryMsg"/></div>
				
				<div class="form-element">
				<span class="otherlink">
					<a href="javascript:void(0);" class="btn btn-primary" onclick="findAll(this)"><spring:message code="eip.search"></spring:message></a>
					
				</span>
					<div class="element">
						&nbsp;
						<br>
						&nbsp;&nbsp;
						<span><label><spring:message code="eip.citizen.repayment.servicetype"></spring:message></label>
						&nbsp;&nbsp;
						<%-- <span>
						<form:select path="serviceId">
							<form:option value="0">Select Service</form:option>
							<c:forEach items="${command.serviceLookUps}" var="lookUp" varStatus="">
								<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}" >${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
						</span> --%>
						
					<apptags:lookupField items="${command.serviceLookUps}" path="serviceId" isMandatory="true" selectOptionLabelCode="Select Service"
										cssClass="mandClassColor">
					
					
								
					
					</apptags:lookupField></span>
					<span class="mand">*</span>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span><label><spring:message code="rti.selectPaymrntMode"></spring:message></label></span>
						&nbsp;&nbsp;
					<form:radiobutton path="onlOflflag" value="O" name="entity.onlineOfflineCheck" checked="checked"/><spring:message code="rti.onlinePay"></spring:message>
					&nbsp;
                    <form:radiobutton path="onlOflflag" value="F" name="entity.onlineOfflineCheck"/><spring:message code="rti.offlinePay"></spring:message>
					<span class="mand">*</span>
					
					
		
		

		
				
					</div>
						
					<br>
					<div class="grid-class" id="publicNoticeGrid">
					<apptags:jQgrid id="publicNotice"
						url="RePayment.html?SEARCH_RESULTS" mtype="post"
						gridid="gridRePaymentForm"
						colHeader="eip.citizen.repayment.applicationid,eip.payment.mobileNo,eip.payment.email,eip.stp.orderno.challan.repayment,eip.stp.tranStatus"
						colModel="[
						{name : 'id',index : 'apmApplicationId', editable : false,sortable : false,search : false, align : 'center' },
								{name : 'phoneNo',index : 'sendPhone', editable : false,sortable : false,search : false, align : 'center' },
								{name : 'email',index : 'sendEmail', editable : false,sortable : false,search : false, align : 'center' },
								{name : 'orderNo',index : 'tranCmId', editable : false,sortable : false,search : false, align : 'center' },
								{name : 'payStatus',index : 'recvStatus', editable : false,sortable : false,search : false, align : 'center' }
				  ]"
						height="200" caption="eip.citizen.repayment" isChildGrid="false"
						hasActive="false" hasViewDet="true" hasDelete="false"
						loadonce="true" sortCol="rowId" showrow="true" />
				</div>
					
					
				
				</div>
				</form:form>
			</div>
		</div>
	</div>
</div>









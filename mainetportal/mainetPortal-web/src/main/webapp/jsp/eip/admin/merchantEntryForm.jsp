
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>


<script src="js/propertytax/propertytax.js"></script>
<script>
jQuery('.hasSpecialChara').keyup(function () { 
	
	if (this.value.match(/[^a-zA-Z ]/g )|| this.value.match(/[^\u0900-\u0954 ]/g) ){
		this.value = this.value.replace(/[^a-zA-Z\u0900-\u0954 ]/g, '');
	}   
});

jQuery('.hasNumber').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
  
});
</script>

<div class="content">
  
   
      <div class="widget">
        <div class="widget-header">
          <h2>Merchant <strong>Entry</strong></h2>
        </div> 
     <div class="widget-content padding">   
	<form:form method="post" action="MerchantEntryForm.html"
		name="frmMerchantMaster" id="frmMerchantMaster" class="form-horizontal">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		
		 <div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="pg.bankname" /><span class="mand">*</span></label>
                <div class="col-sm-4"><apptags:lookupField
									items="${command.getBankLookUps()}"
									path="entity.mmcbbankid.cbbankid" 
									selectOptionLabelCode="Select Bank" hasId="true" cssClass="form-control mandClassColor subsize"
									 />  </div>
                <label class="col-sm-2 control-label"><spring:message code="pg.merchid" /> <span class="mand">*</span></label>
                <div class="col-sm-4"><apptags:inputField  fieldPath="entity.mmmrcntid" hasId="true" cssClass="form-control mandClassColor subsize" maxlegnth="50"  /></div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="pg.merchName" /><span class="mand">*</span></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="entity.mmmrcntname" hasId="true" cssClass="form-control hasSpecialChara mandClassColor subsize"  maxlegnth="100"/></div>
                <label class="col-sm-2 control-label"><spring:message code="pg.merchshort" /><span class="mand">*</span></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="entity.mmmrcntcode" hasId="true" cssClass="form-control mandClassColor subsize hasSpecialChara" maxlegnth="50"/></div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="pg.mrchTrmnId" /></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="entity.mmmrcnttrmnlid" hasId="true" cssClass=" form-control txtwidth hasNumber" maxlegnth="100"/></div>
                <label class="col-sm-2 control-label"><spring:message code="pg.mrchTrmName" /></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="entity.mmmrcnttrmnlname" hasId="true" cssClass="form-control txtwidth hasSpecialChara" maxlegnth="100"/></div>
              </div>
		
		      <div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="pg.mrchCcNum" /></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="entity.mmmrcntccnum" hasId="true"  cssClass=" form-control txtwidth hasNumber" maxlegnth="100"/></div>
                <label class="col-sm-2 control-label"><spring:message code="pg.mrchCcAlpha" /></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="entity.mmmrcntccalp" hasId="true" cssClass=" form-control txtwidth hasSpecialChara" maxlegnth="100"/></div>
              </div>
		      <div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="pg.mrchCcExp" /></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="entity.mmmrcntccexp" hasId="true" cssClass="form-control txtwidth" maxlegnth="40"/></div>
                
              </div>
               <div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="pg.mrchResUrl" /><span class="mand">*</span></label>
                <div class="col-sm-4"><apptags:inputField fieldPath="entity.mmmrcntresurl" hasId="true" cssClass="form-control  mandClassColor subsize" maxlegnth="100"/></div>
                <label class="col-sm-2 control-label"><spring:message code="pg.mrchErrUrl" /><span class="mand">*</span></label>
                <div class="col-sm-4">	<apptags:inputField fieldPath="entity.mmmrcnterrurl" hasId="true" cssClass="form-control mandClassColor subsize" maxlegnth="100"/></div>
              </div>
		
		<div class="text-center padding-bottom-20">
			<apptags:submitButton successUrl="MerchantDetail.html"
				entityLabelCode="Merchant Master" cssClass="btn btn-success" />
			<apptags:resetButton cssClass="btn btn-default" />
			<apptags:backButton url="MerchantDetail.html" cssClass ="btn btn-primary"/>
		</div>
	</form:form>
	</div>
</div>
</div>

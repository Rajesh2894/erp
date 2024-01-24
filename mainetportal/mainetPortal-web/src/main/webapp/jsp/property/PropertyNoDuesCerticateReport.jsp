<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script src="js/mainet/validation.js"></script>
<style>
@media print { .cls .margin-top-15{margin-top:0px !important;}
 }

.widget-content.padding {
    padding: 20px;
}
.widget-content {
    border: 1px solid #000;
}
.widget {
    padding: 40px;
}
.padding-left-20p {
    padding-left: 20%;
}
.text-bold{font-weight: bold;font-size: 1.1em;}
.widget-content.padding p {
    color: #5a5a5a;
    font-size: 1em;
    margin: 0px;
    padding: 0px;
    font-weight: initial;
}
</style>
 <apptags:breadcrumb></apptags:breadcrumb>
    <!-- Start Content here -->
    <div class="content animated slideInDown"> 
      <div class="widget" id="receipt">
        <div class="widget-content padding">
        <form action="" method="get">
        <div class="col-xs-3">
        <img alt="Image" src=""/>
        </div>
            <div class="col-xs-6  text-center">
              <h3 class="margin-bottom-10">${userSession.organisation.ONlsOrgname}</h3>
            </div>
            
            <div class="col-xs-6 col-xs-offset-3 text-center">
              <h4 class="margin-bottom-0">${command.serviceName}</h4>
            </div>
            
            <div class="row margin-top-30">
            <div class="col-xs-2 col-xs-offset-8 text-right"><spring:message code="property.noDues.Date:" text="Date:"/></div>
            <div class="col-xs-2">${command.date}</div>
          
          </div>
          
          
            <div class="clearfix padding-25"></div>

                    <div class="col-xs-12">
                        <p class="margin-top-15"><span class="text-bold"> <spring:message code="property.noDues.report" text="Subject"/></span>&nbsp; &nbsp; &nbsp;<spring:message code="property.noDues.content1" text=":Application For"/>&nbsp;${command.serviceName} </p>
                        <p class="margin-top-15"><span class="text-bold"><spring:message code="property.noDues.report.reference" text="Reference"/></span><spring:message code="property.noDues.content2" text=":Application for the No Dues Certificate Application number:"/><span class="text-bold">${command.noDuesCertificateDto.apmApplicationId}</span><spring:message code="property.noDues.content10" text=",and Application Date:"/><span class="text-bold">${command.date}</span>
                        </p>
                    </div>
          
            		<div class="col-xs-12">
                        <p class="margin-top-15 text-justify"><spring:message code="property.noDues.content3" text="Dear Sir/Madam,"/></p>
                    </div>
                    
                    <div class="col-xs-11 col-xs-offset-1">
                        <p class="margin-top-15 text-justify"><spring:message code="property.noDues.content4" text="This is to certified that ,the property no"/>&nbsp;<span class="text-bold">${command.noDuesCertificateDto.propNo}</span><spring:message code="property.noDues.content5" text=",near"/>&nbsp;<span class="text-bold">${command.noDuesCertificateDto.ownerAddress}</span>&nbsp;<spring:message code="property.noDues.content11" text="stand in the name of" />&nbsp;<span class="text-bold">${command.noDuesCertificateDto.ownerName}</span></p>
                    </div>
                    
                     <div class="col-xs-12 cls">
                        <p class="margin-top-15 text-justify"> <spring:message code="property.noDues.content6" text="The Property tax for the said premised has been paid amounting to"/>&nbsp;<span class="text-bold">${command.noDuesCertificateDto.totalOutsatandingAmt}</span>&nbsp;<spring:message code="property.noDues.content7" text="for the year"/>&nbsp;<span class="text-bold">${command.finYear}</span>&nbsp;<spring:message code="property.noDues.content8" text="as record  maintained by" />&nbsp;${userSession.organisation.ONlsOrgname}</p>
                    </div>
                   
                      <div class="padding-5 clear">&nbsp;</div>					
					<div class = "form-group clearfix padding-vertical-15">
							<div class="col-xs-8 text-center"></div>
							<div class="col-xs-4 text-center">
				              <p><spring:message code="property.report.content9"/></p>
				              <p class="margin-top-30"><spring:message code="property.noDues.content9" text="Commissioner,"/></p>
				              <p>	${userSession.getCurrent().organisation.ONlsOrgname}</p>
				            </div>
					</div>
    
          <div class="text-center margin-top-10">
            <button onclick="printContent('receipt')" class="btn btn-primary hidden-print"><i class="fa fa-print"></i> <spring:message code="property.noDues.Print" text="Print"/></button>
             <button type="button" class="btn btn-danger hidden-print" onclick="window.location.href='CitizenHome.html'"><spring:message code="property.noDues.Back" text="Back"/></button>
          </div>
        
          </form>
        </div>
      </div>
      <!-- End of info box --> 
    </div>
 

<script>
function printContent(el){
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
}
$("html, body").animate({ scrollTop: 0 }, "slow");
</script>


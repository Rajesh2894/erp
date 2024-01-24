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
<script type="text/javascript" src="js/mainet/validation.js"></script>
<style>
	.widget{
		padding: 40px;
	}
	.widget-content{
		border: 1px solid #000;
	}
	.widget-content.padding{
		padding: 20px;
	}
	.margin-top-60{
		margin-top: 60px;
	}
	.padding-left-20p{
		padding-left:20%;
	}
</style>


 <apptags:breadcrumb></apptags:breadcrumb>
    <!-- Start Content here -->
    <div class="content animated slideInDown"> 
      <div class="widget" id="receipt">
        <div class="widget-content padding">
        <form action="" method="get">
        
        <div class="row">
        	<div class="col-xs-2">
				<h1>
						<img width="80" src="${userSession.orgLogoPath}">
				</h1>
			</div>
            <div class="col-xs-10 padding-left-20p">
              <h3 class="margin-bottom-0">${userSession.organisation.ONlsOrgname}</h3>
            </div>
            
            <div class="col-xs-12 text-center">
              <h4 class="margin-bottom-0 margin-top-0">${command.serviceName}</h4>
            </div>
       </div>
            
            <div class="row margin-top-10">
            <div class="col-xs-2 col-xs-offset-8 text-right"><spring:message code="property.noDues.Date:" text="Date:"/></div>
            <div class="col-xs-2">${command.date}</div>
          
          </div>
          
          
            <div class="clearfix"></div>

                    <div class="col-xs-10 margin-top-10">
                        <p class="margin-top-15"><span class="text-bold"> <spring:message code="property.noDues.report" text="Subject"/></span>&nbsp; &nbsp; &nbsp;<spring:message code="property.noDues.content1" text=":Application For"/> ${command.serviceName} </p>
                        <p class="margin-top-15"><span class="text-bold"><spring:message code="property.noDues.report.reference" text="Reference"/></span><spring:message code="property.noDues.content2" text=":Application for the Extraction Of Property Application number:"/><span class="text-bold">${command.noDuesCertificateDto.apmApplicationId}</span><spring:message code="property.noDues.content10" text=",and Application Date:"/><span class="text-bold">${command.date}</span>
                        </p>
                    </div>
          
            		<div class="col-xs-12">
                        <p class="margin-top-15 text-justify"><spring:message code="property.noDues.content3" text="Dear Sir/Madam,"/></p>
                    </div>
                    
                    <div class="col-xs-11 col-xs-offset-1">
                       <p class="margin-top-15 text-justify"><spring:message code="property.noDues.content4" text="This is to certified that ,the property no"/>&nbsp;<span class="text-bold">${command.noDuesCertificateDto.propNo}</span><spring:message code="property.noDues.content5" text=",Near"/><span class="text-bold">${command.noDuesCertificateDto.ownerAddress}</span>&nbsp;<spring:message code="property.noDues.content11" text="stand in the name of" />&nbsp;<span class="text-bold">${command.noDuesCertificateDto.ownerName}</span></p>
                    </div>
                    
                     <div class="col-xs-12">
                        <p class="margin-top-15 text-justify"> <spring:message code="property.noDues.content6" text="The Property tax for the said premised has been paid amounting to"/> <span class="text-bold">${command.noDuesCertificateDto.totalOutsatandingAmt}</span>&nbsp;<spring:message code="property.noDues.content7" text="For the year"/>&nbsp;<span class="text-bold">${command.finYear}</span>&nbsp;<spring:message code="property.noDues.content8" text="as record  maintained by" />  ${userSession.organisation.ONlsOrgname}</p>
                    </div>
                    
                      <div class="padding-5 clear">&nbsp;</div>					
					<div class = "form-group clearfix padding-vertical-15">
							<div class="col-xs-8 text-center"></div>
							<div class="col-xs-4 text-center">
				              <p><spring:message code="property.report.content9"/></p>
				              <p class="margin-top-60"><spring:message code="property.noDues.content9" text="Commissioner,"/></p>
				              <p>	${userSession.getCurrent().organisation.ONlsOrgname}</p>
				            </div>
					</div>
    
          <div class="text-center margin-top-10">
            <button onclick="printContent('receipt')" class="btn btn-primary hidden-print"><i class="fa fa-print"></i> <spring:message code="property.noDues.Print" text="Print"/></button>
             <button type="button" class="btn btn-danger hidden-print" onclick="window.location.href='AdminHome.html'"><spring:message code="property.noDues.Back" text="Back"/></button>
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


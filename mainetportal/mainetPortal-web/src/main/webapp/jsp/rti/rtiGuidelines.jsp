<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script>
function acceptanceCheck(){
	if($('#accept').is(':checked')){
		var ajaxResponse = __doAjaxRequest(
				'RtiApplicationUserDetailForm.html?accept', 'POST',
				{}, false, 'html');
		
		$(formDivName).html(ajaxResponse);
		return false;
	}else{
			showErrormsgboxTitle("Please accept Gudelines");
		}
}
</script>
<apptags:breadcrumb></apptags:breadcrumb>
    <!-- Start Content here -->
    <div class="content"> 
    
      <!-- Start info box -->
      <div class="widget">
        <div class="widget-header">
          <h2><spring:message code="rti.guildlines.page.heading" text="GUIDELINES FOR USE OF RTI ONLINE PORTAL"/></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a> </div>
        </div>
        <div class="widget-content padding">
          <!-- <div class="mand-label clearfix"><span>Field with <i class="text-red-1">*</i> is mandatory</span></div> -->
          <form:form action="RtiApplicationUserDetailForm.html" method="POST" class="form-horizontal" id="rtiGuideline">
                 
<div style="overflow-y: scroll; height : 90%; max-height:500px"> 
          

<p></p>
<ol type="1">
<li><spring:message code="rti.guildline.1" text="1.This Web Portal can be used by Indian citizens to file RTI application online and also to make payment for RTI application online. First appeal can also be filed online."/></li>
<li><spring:message code="rti.guildline.2" text="2.An Applicant who desires to obtain any information under the RTI Act can make a request through this Web Portal to the Ministries/ Departments of Government of India."/></li>
<li><spring:message code="rti.guildline.3" text="3.On clicking at 'Submit Request', the applicant has to fill the required details on the page that will appear. The fields marked * are mandatory while the others are optional."/></li>
<li><spring:message code="rti.guildline.4" text="4.The text of the application may be written at the prescribe column."/></li>

<li><spring:message code="rti.guildline.5" text="5.At present, the text of an application that can be uploaded at the prescribed column is confirmed to 500 characters only."/></li>
<li><spring:message code="rti.guildline.6" text="6.In case an application contains more than 500 characters, it can be uploaded as an attachment, by using Button 'Upload'."/></li>
<li><spring:message code="rti.guildline.7" text="7.After filling the first page, the applicant must click on 'Submit' to make payment of the prescribed fee."/></li>
<li><spring:message code="rti.guildline.8" text="8.The Applicant can pay the prescribed fee through the following modes: 
(A)	Internet banking through SBI and its associated Banks;
(B)	Using Credit/Debit card of Master /Visa."/></li>
<li><spring:message code="rti.guildline.9" text="9.Fee for making an application is as prescribed in the RTI Rules, 2012."/></li>
<li><spring:message code="rti.guildline.10" text="10.After making payment, an application can be submitted"/></li>
<li><spring:message code="rti.guildline.11" text="11.No RTI fee is required to be paid by any citizen who is below poverty line as per RTI Rules, 2012. However, the applicant must attach a copy of the certificate issued by the appropriate government in this regard, along with the application."/></li>
<li><spring:message code="rti.guildline.12" text="12.On submission of an application, a unique registration number would be issued, which may be referred by the applicant for any references in future."/></li>
<li><spring:message code="rti.guildline.13" text="13.The application filled through this Web Portal would reach electronically to the 'Officer' of concerned Department, If Attachments are present with application then officer do the verification & transmit the RTI application electronically to the concerned PIO."/></li>
<li><spring:message code="rti.guildline.14" text="14.In case additional fee is required representing the cost for providing information, the PIO would intimate the applicant through this portal. This information can be seen by the applicant through status report or through his/her e-mail alert."/></li>

<li><spring:message code="rti.guildline.15" text="15.For making an appeal to the first Appellate Authority, the applicant has to click at 'Submit First Appeal' and fill up the page that will appear."/></li>
<li><spring:message code="rti.guildline.16" text="16.The Registration number of original application has to be used for reference."/></li>
<li><spring:message code="rti.guildline.17" text="17.As per RTI Act,  fee has to be paid for first appeal"/></li>
<li><spring:message code="rti.guildline.18" text="18.The applicant/ The Appellant should submit his/her mobile number to receive SMS alert."/></li>

<li><spring:message code="rti.guildline.19" text="19.Status of the RTI application/ First appeal filed online can be seen by the applicant/appellant by clicking at 'View Status'."/></li>
<li><spring:message code="rti.guildline.20" text="20.All the requirements for filling as RTI application and first appeal as well as other previous regarding time limit, exemptions etc., as  provided in the RTI Act, 2005 will continue to apply."/></li>
<li><spring:message code="rti.guildline.21" text="UPI and other payment method available with departments. "/></li>
</ol>

         </div> 
         	<div class="form-group padding-top-10">
            	<div class="col-sm-12">
					<label class="checkbox-inline"><input name="" type="checkbox" value="Y" id="accept" data-rule-required="true"><spring:message code="rti.guildline.read.understood" text="I have read and understood the above guidelines."/></label>
                </div>
            </div> 
            
            <div class="text-center">
            	<button type="button" class="btn btn-blue-2" onclick="acceptanceCheck();" id="discBtn"><i class="fa fa-file-text-o margin-right-5"></i><spring:message code="rti.guildline.file.rti" text="Click here to file RTI Application"/></button>
            </div>
       </form:form>
        </div>
        </div>
        </div>
        
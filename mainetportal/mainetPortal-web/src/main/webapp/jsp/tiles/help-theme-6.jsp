<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style>
	.cookietypes3 > .list1{
		list-style: disc;
    	margin-left: 20px;
	}
	.semiBold{
		font-weight:600;
	}
</style>
<a id="CitizenService"></a>
<ol class="breadcrumb">
   <c:if
      test="${empty userSession.employee.emploginname or userSession.employee.emploginname eq'NOUSER' }"
      var="user">
      <li><a href="CitizenHome.html"><i class="fa fa-home"></i><spring:message code="menu.home" text="Home"/></a></li>
   </c:if>
   <c:if
      test="${ !empty userSession.employee.emploginname and userSession.employee.emploginname ne'NOUSER' }"
      var="user">
      <li><a href="CitizenHome.html"><i class="fa fa-home"></i><spring:message code="menu.home" text="Home"/></a></li>
   </c:if>
   <li>
      <spring:message code="help.footer" text="Help"/>
   </li>
</ol>

<div class="widget">
   	<div class="widget-header">
   		<h2><spring:message code="help.footer" text="HELP"/></h2>
   	</div>
	<div class="widget-content padding">
		<form action="" method="get" class="form-horizontal" novalidate>
			<p>
			   <strong>
			      <spring:message code="help1" text="Viewing Information in Various File Formats"/>
			   </strong>
			</p>
			<p>
			   <spring:message code="help2" text="The information provided by this website is available in various file formats, such as Portable Document Format (PDF), Word, Excel and PowerPoint. To view the information properly, your browser needs to have the required plug-ins or software. For example, the Adobe Flash software is required to view the Flash files. In case your system does not have this software, you can download it from the Internet for free. The table lists the required plug-ins needed to view the information in various file formats."/>
			</p>
			<p>
			   <strong>
			      <spring:message code="help3" text="Plug-in for alternate document types"/>
			   </strong>
			</p>
			<table class="table table-bordered table-striped table-help">
				<thead>
					<tr>
						<th>
							<spring:message code="help4" text="Document Type"/>
						</th>
						<th>
							<spring:message code="help5" text="Plug-in for Download"/>
						</th>
					</tr>
				</thead>
				<tbody>
					<%-- <tr>
						<td>
						   <spring:message code="help6" text="Help Document for Portal"/>
						</td>
						<td>
							<a href="assets/img/Help_Document.pdf" target="_blank">
							   <i class="fa fa-file-pdf-o" aria-hidden="true"></i>
							   <spring:message code="help7" text="Help Document Portal"/>
						   </a>
						</td>
					</tr> --%>
					<tr>
						<td>
							<spring:message code="help8" text="Portable Document Format (PDF) files"/>
						</td>
						<td>
							<a href="http://get.adobe.com/reader/" target="_blank" aria-label="Download PDF Reader">
							   <i class="fa fa-file-pdf-o" aria-hidden="true"></i>
							   <spring:message code="help9" text="Adobe Acrobat Reader"/>
							</a>
							<br>
							<spring:message code="help10" text="Convert a PDF file online into HTML or text format"/>
						</td>
					</tr>
					<tr>
						<td>
						   <spring:message code="help11" text="Word files"/>
						</td>
						<td>
							<a href="http://www.microsoft.com/downloads/details.aspx?familyid=941b3470-3ae9-4aee-8f43-c6bb74cd1466&amp;displaylang=en" target="_blank">
							<i class="fa fa-file-word-o" aria-hidden="true"></i>
							<spring:message code="help12" text="Word Viewer (in any version till 2003)"/>
							</a>
							<br>
							<spring:message code="help13" text="Microsoft Office Compatibility Pack for Word (for 2007 version)"/>
						</td>
					</tr>
					<tr>
						<td>
							<spring:message code="help14" text="Excel files"/>
						</td>
						<td>
							<a href="http://www.microsoft.com/downloads/details.aspx?FamilyID=c8378bf4-996c-4569-b547-75edbd03aaf0&amp;displaylang=EN" target="_blank">
							   <i class="fa fa-file-excel-o" aria-hidden="true"></i>
							   <spring:message code="help15" text="Excel Viewer 2003 (in any version till 2003)"/>
							</a>
							<br>
							<spring:message code="help16" text="Microsoft Office Compatibility Pack for Excel (for 2007 version)"/>
						</td>
					</tr>
					<tr>
						<td>
							<spring:message code="help17" text="PowerPoint presentations"/>
						</td>
						<td>
							<a href="http://www.microsoft.com/downloads/details.aspx?familyid=941b3470-3ae9-4aee-8f43-c6bb74cd1466&amp;displaylang=en" target="_blank">
							   <i class="fa fa-file-powerpoint-o" aria-hidden="true"></i>
							   <spring:message code="help18" text="PowerPoint Viewer 2003 (in any version till 2003)"/>
							</a>
							<br>
							<spring:message code="help19" text="Microsoft Office Compatibility Pack for PowerPoint (for 2007 version)"/>
						</td>
					</tr>
					<tr>
						<td>
							<spring:message code="help20" text="Flash content"/>
						</td>
						<td>
							<a href="http://get.adobe.com/flashplayer/" target="_blank">
							   <i class="fa fa-file-video-o" aria-hidden="true"></i>
							   <spring:message code="help21" text="Adobe Flash Player"/>
							</a>
						</td>
					</tr>
				</tbody>
			</table>
			<br>
			<p>
			   <strong>
			      <spring:message code="help22" text="COPYRIGHT POLICY"/>
			   </strong>
			</p>
			<p>
			   <spring:message code="help23" text="Material featured on this website may be reproduced free of charge. However, the material has to be reproduced accurately and not to be used in a derogatory manner or in a misleading context. Wherever the material is being published or issued to others, the source must be prominently acknowledged. However, the permission to reproduce this material shall not extend to any material which is identified as being copyright of a third party. Authorization to reproduce such material must be obtained from the department/copyright holder concerned."/>
			</p>
			
			<p>
			   <strong>
			      <spring:message code="help24" text="PRIVACY POLICY"/>
			   </strong>
			</p>
			<p>
			   <spring:message code="help25" text="Thanks for visiting website of Kalyan Dombivali Municipal Corporation (i.e. KDMC) and reviewing our privacy policy."/>
			</p>
			<p>
			   <spring:message code="help26" text="This website does not automatically capture any specific personal information from you (like name, phone number or e-mail address),that allows us to identify you individually. If you choose to provide us with your personal information like names or addresses ,when you visit our website we use it only to fulfil your request for information. We do not sell or share any personally identifiable information volunteered on this site to any third party (public/private). Any information provided to this website will be protected from loss, misuse, unauthorized access or disclosure, alteration, or destruction."/>
			</p>
			<p>
				<spring:message code="help27" text="We gather certain information about the User such as Internet protocol (IP) address, domain name, browser type, operating system, the date and time of the visit and the pages visited. We make no attempt to link these addresses with the identity of individuals visiting our site unless an attempt to damage the site has been detected. This information is only used to help us make the site more useful for you. With this data, we learn about the number of visitors to our site and the types of technology our visitors use. We never track or record information about individuals and their visits."/>
			</p>
			
			<p>
			   <strong>
			      <spring:message code="help28" text="COOKIES POLICY"/>
			   </strong>
			</p>
			<p>
			   <spring:message code="help29" text="A cookie is a piece of software code that an internet website sends to your browser when you access information at that site. A cookie is stored as a simple text file on your computer or mobile device by a websites server and only that server will be able to retrieve or read the contents of that cookie. Cookies let you navigate between pages efficiently as they store your preferences and generally improve your experience of a website."/>
			</p>
			<p>
			   <spring:message code="help30" text="We are using following types of cookies in our site: -"/>
			</p>
			<ul class ="cookietypes3">
				<li class="list1"><spring:message code="help31" text="Analytics cookies for anonymously remembering your computer or mobile device when you visit our website to keep track of browsing patterns."/></li>
				<li class="list1"><spring:message code="help32" text="Service cookies for helping us to make our website work efficiently, remembering your registration and login details, settings preferences, and keeping track of the pages you view."/></li>
				<li class="list1"><spring:message code="help33" text="Non-persistent cookies a.k.a. per-session cookies. Per-session cookies serve technical purposes like providing seamless navigation through this website. These cookies do not collect personal information on users and they are deleted as soon as you leave our website. The cookies do not permanently record data and they are not stored on your computers hard drive. The cookies are stored in memory and are only available during an active browser session. Again once you close your browser the cookie disappears."/></li>
			</ul>
			<br>
			<p>
				<strong>
			      <spring:message code="help.content.archival" text="Content Archival Policy"/>
			   </strong>
			</p>
			<p >
			      <spring:message code="help.content.archival1" text="The content components are created with metadata, source, and validity date."/> 
			</p>
			<p>
			      <spring:message code="help.content.archival2" text="Some of the short lived content components like tenders, recruitment, draft for discussion, etc "/>
			</p>
			<p>
			      <spring:message code="help.content.archival3" text="=The content is to be reviewed at least two weeks prior to the validity date and if required revalidate the content and modify the validity date. If the content is not relevant, then the content is archived and no longer published on the Website."/> 
			</p>
			<p>
				<strong>
			      <spring:message code="help34" text="HYPERLINKING POLICY"/>
			   </strong>
			</p>
			<p  class="semiBold">
			      <spring:message code="help35" text="Links to external websites/portals"/> :-
			</p>
			<p>
			      <spring:message code="help36" text="At many places in this website, you shall find links to other websites/portals. These links have been placed for your convenience. Kalyan Dombivali Municipal Corporation (i.e. KDMC) Office is not responsible for the contents of the linked websites and does not necessarily endorse the views expressed in them. Mere presence of the link or its listing on this website should not be assumed as endorsement of any kind. "/>
			</p>
			<p  class="semiBold">
			      <spring:message code="help37" text="Links to Kalyan Dombivali Municipal Corporation (i.e. KDMC) Office Website by other websites/portals"/> :-
			</p>
			<p>
				<spring:message code="help38" text="We do not object to you linking directly to the information that is hosted on this web site and no prior permission is required for the same. However, we would like you to inform us about any links provided to this website so that you can be informed of any changes or updates therein. Also,we do not permit our pages to be loaded into frames on your site. The pages belonging to this website must load into a newly opened browser window of the User."/>
			</p>
			
			<p>
				<strong>
			      <spring:message code="help74" text="TERMS & CONDITIONS"/>
			   </strong>
			</p>
			<p>
				<spring:message code="help39" text="Though all efforts have been made to ensure the accuracy and currency of the content on this website, the same should not be construed as a statement of law or used for any legal purposes. In case of any ambiguity or doubts, users are advised to verify/check with the Kalyan Dombivali Municipal Corporation Office,GoM and/or other source(s),and to obtain appropriate professional advice. Under no circumstances will Kalyan Dombivali Municipal Corporation Office,GoM be liable for any expense, loss or damage including, without limitation ,indirect or consequential loss or damage,or any expense, loss or damage whatsoever arising from use, or loss of use, of data, arising out of or in connection with the use of this website."/>
				<br><spring:message code="help40" text="These terms and conditions shall be governed by and construed in accordance with the Indian Laws. Any dispute arising under these terms and conditions shall be subject to the jurisdiction of the courts of India."/>
			</p>
			<p>
			      <spring:message code="help41" text="The information posted on this website could include hypertext links or pointers to information created and maintained by non-Government/private organisations. Kalyan Dombivali Municipal Corporation Office,GoM is providing these links and pointers solely for your information and convenience. When you select a link to an external website, you are leaving the Kalyan Dombivali Municipal Corporation Office,GoM website and are subject to the privacy and security policies of the owners/sponsors of the external website. Kalyan Dombivali Municipal Corporation Office,GoM does not guarantee the availability of such linked pages at all times. Prime Ministers Office cannot authorise the use of copyrighted materials contained in a linked website. Users are advised to request such authorisation from the owner of the linked website. Kalyan Dombivali Municipal Corporation Office, GoM does not guarantee that linked websites comply with Indian Government Web Guidelines."/>
			</p>
			
			<p>
				<strong>
			      <spring:message code="help42" text="REGIONAL LANGUAGE POLICY"/>
			   </strong>
			</p>
			<p>
			     	<spring:message code="help43" text="Though all efforts have been made to ensure the accuracy of the content in regional languages on this Portal, the same should not be construed as a statement of law or used for any legal purposes. For any discrepancy in the regional language content you may refer to the original English content. For any error in language, you may report the same through the online feedback form."/>
					<br><spring:message code="help44" text="Kalyan Dombivali Municipal Corporation Office,GoM accepts no responsibility in relation to the accuracy, completeness, usefulness or otherwise, of the contents. In no event will the Government be liable for any expense, loss or damage including, without limitation, indirect or consequential loss or damage, or any expense, loss or damage whatsoever arising from use, or loss of use, of data, arising out of or in connection with the use of this Portal."/>
			</p>
			<p  class="semiBold">
			      <spring:message code="help45" text="Content Review Policy"/>
			</p>
			<p>
				<spring:message code="help46" text="Content Contribution, Moderation & Approval (CMAP) Policy, Content Archival (CAP), Policy, All possible efforts need to be taken to keep the content on the Website current and up-to-date. This Content Review Policy defines the roles and responsibilities of the website content review and the manner in which it need to be carried out. Review Policies are defined for the diverse content elements."/>
				<br><spring:message code="help47" text="The Review Policy is based on different type of content elements ,its validity and relevance as well as the archival policy."/>
				<br><spring:message code="help48" text="The entire website content would be reviewed for syntax checks once a month by the Kalyan Dombivali Municipal Corporation Office, Team."/>
			</p>
			<p  class="semiBold">
			      <spring:message code="help49" text="Website Monitoring Policy"/>
			</p>
			<p>
						<spring:message code="help50" text="Kalyan Dombivali Municipal Corporation Office has a Website Monitoring Policy in place and the website is monitored periodically to address and fix the quality and compatibility issues around the following parameters:"/>
					<br> <span class="semiBold"> <spring:message code="help.performance" text="Performance:"/></span> <span><spring:message code="" text="Site download time is optimized for a variety of network connections as well as devices. All important pages of the website are tested for this."/></span>
					<br> <span class="semiBold"> <spring:message code="help.functionality" text="Functionality:"/></span> <span><spring:message code="help52" text="All modules of the website are tested for their functionality. The interactive components of the site such as, feedback forms are working smoothly."/></span>
					<br> <span class="semiBold"> <spring:message code="help.broken.links" text="Broken Links:"/></span> <span><spring:message code="help53" text="The website is thoroughly reviewed to rule out the presence of any broken links or errors."/></span>
					<br> <span class="semiBold"> <spring:message code="help.traffic.analysis" text="Traffic Analysis:"/></span> <span><spring:message code="help54" text="The site traffic is regularly monitored to analyse the usage patterns as well as visitors profile and preferences."/></span>
					<br> <span class="semiBold"> <spring:message code="help.feedback" text="Feedback:"/></span> <span><spring:message code="help55" text="Feedback from the visitors is the best way to judge a websites performance and make necessary improvements. A proper mechanism for feedback is in place to carry out the changes and enhancements as suggested by the visitors."/></span>
			</p>
			<p  class="semiBold">
			      <spring:message code="help56" text="Website Contingency Management Policy"/>
			</p>
			<p>
				<spring:message code="help57" text="The presence of the website on the Internet and very importantly the site is fully functional all the times. It is expected of the Government websites to deliver information and services on a 24X7 basis. Hence, all efforts should be made to minimize the downtime of the website as far as possible."/>
			</p>
			<p>
			    <spring:message code="help58" text="It is therefore necessary that a proper Contingency Plan to be prepared in handle any eventualities and restore the site in the shortest possible time. The possible contingencies include:"/>
				<br><span class="semiBold"> <spring:message code="help.defacement" text="Defacement of the website :"/></span> <span><spring:message code="help59" text="All possible security measures must be taken for the website to prevent any possible defacement/hacking by unscrupulous elements. However, if despite the security measures in place, such an eventuality occurs, there must be a proper contingency plan, which should immediately come into force. If it has been established beyond doubt that the website has been defaced, the site must be immediately blocked. The contingency plan must clearly indicate as to who is the person authorised to decide on the further course of action in such eventualities. The complete contact details of this authorised person must be available at all times with the web management team. Efforts should be made to restore the original site in the shortest possible time. At the same time, regular security reviews and checks should be conducted in order to plug any loopholes in the security."/></span>
			</p>
			<p>
				<span class="semiBold"> <spring:message code="help.data" text="Data Corruption :"/></span> <span><spring:message code="help60" text="A proper mechanism has to be worked out by the concerned in consultation with their web hosting service provider to ensure that appropriate and regular back-ups of the website data are being taken. These enable a fast recovery and uninterrupted availability of the information to the citizens in view of any data corruption."/></span>
			</p>
			<p>
				<span class="semiBold"> <spring:message code="help.hardware.software" text="Hardware/Software Crash :"/></span> <span><spring:message code="help61" text="Though such an occurrence is a rarely, still in case the server on which the website is being hosted crashes due to some unforeseen reason,the web hosting service provider must have enough redundant infrastructure available to restore the website at the earliest."/></span>
			</p>
			<p>
				<span class="semiBold"> <spring:message code="help.disasters" text="Natural Disasters :"/></span> <span><spring:message code="help62" text="There could be circumstances whereby due to some natural calamity, the entire data center where the website is being hosted gets destroyed or ceases to exist. A well planned contingency mechanism has to be in place for such eventualities whereby is should be ensured that the Hosting Service Provider has a Disaster Recover Centre (DRC) set up at a geographically remote location and the website is switched over to the DRC with minimum delay and restored on the Net."/></span>
			</p>
			<p>
				<spring:message code="help63" text="Apart from the above,in the event of any National Crisis or unforeseen calamity,Government websites are looked upon as a reliable and fast source of information to the public. A well defined contingency plan for all such eventualities must be in place so that the emergency information/contact help-lines could be displayed on the website without any delay. For this, the concerned person in the Kalyan Dombivali Municipal Corporation Office, GoM responsible for publishing such emergency information must be identified and the complete contact details should be available at all times."/>
			</p>
			
			<p  class="semiBold">
			      <spring:message code="help64" text="Website Security Policy"/>
			</p>
			<p>
				<spring:message code="help65" text="Kalyan Dombivali Municipal Corporation Office website contains information which is freely accessible,and may be viewed by any visitor. However,the website maintains a copyright interest in the contents of all of its websites."/>
				<br><spring:message code="help66" text="Except for authorized security investigations and data collection, no attempts will be made to identify individual users. Accumulated data logs will be scheduled for regular deletion. The Website Privacy Policy details our position regarding the use of personal information provided by customers/visitors."/>
				<br><spring:message code="help67" text="Unauthorized attempts to upload information or change information are strictly prohibited, and may be punishable under the Information Technology Act, 2000."/>
			</p>
			<p>
				<span class="semiBold"><spring:message code="help68" text="User ID and Password Policy:"/></span>
				<br><spring:message code="help69" text="Access to sensitive or proprietary business information on Kalyan Dombivali Municipal Corporation Office, websites is limited to users who have been determined to have an appropriate official reason for having access to such data. All registered users who are granted security access will be identified by a user name provided by webmaster."/>
				<br><spring:message code="help70" text="Users who are granted password access to restricted information are prohibited from sharing those passwords with or divulging those passwords to any third parties. User will notify us immediately in the event a User ID or password is lost or stolen or if User believes that a non-authorized individual has discovered the User ID or password."/>
				<br><spring:message code="help71" text="If you have any questions or comments regarding Kalyan Dombivali Municipal Corporation Office Website Security Policy,please contact the Web Information Manager by using the Feedback option in Kalyan Dombivali Municipal Corporation Office website."/>
			</p>
			<p>
			<!--CMAP Policy Start  -->
			<p  class="semiBold">
			      <spring:message code="camp1" text="Content Contribution, Moderation & Approval Policy (CMAP)"/>
			</p>	
			<p>
				<spring:message code="camp2" text="Content needs to be contributed by the authorized DMC from Departments of Municipal"/>
			</p>
			<p>
				<spring:message code="camp3" text="The content on the portal goes through the entire life-cycle process of"/>
			</p>
			<ul class="cookietypes3">
				<li class="list1"><spring:message code="camp.creation" text="Creation"/></li>
				<li class="list1"><spring:message code="camp.modification" text="Modification"/></li>
				<li class="list1"><spring:message code="camp.approval" text="Approval"/></li>
				<li class="list1"><spring:message code="camp.moderation" text="Moderation"/></li>
				<li class="list1"><spring:message code="camp.publishing" text="Publishing"/></li>
				<li class="list1"><spring:message code="camp.expiry" text="Expiry"/></li>
				<li class="list1"><spring:message code="camp.archival" text="Archival"/></li>
			</ul>
			<p><spring:message code="camp4" text="Once the content is contributed it needs to be approved and moderated prior to being published on "/></p>
			<p><spring:message code="camp5" text="Different Content Element is categorized as"/></p>
            <ul class="cookietypes3">
				<li class="list1"><spring:message code="camp.routine" text="Routine"/></li>
				<li class="list1"><spring:message code="camp.priority" text="Priority and"/></li>
				<li class="list1"><spring:message code="camp.express" text="Express"/></li>
			</ul>
			 <table class="table table-bordered table-striped table-help">
			 	<thead>
			 		<tr>
			 			<th><spring:message code="eip.srno" text="Sr. No."/></th>
			 			<th><spring:message code="camp.content.element" text="Content Element"/></th>
			 			<th colspan="3"><spring:message code="camp.type.content" text="Type of Content"/></th>
			 			<th><spring:message code="camp.moderator" text="Moderator"/></th>
			 			<th><spring:message code="camp.approver" text="Approver"/></th>
			 			<th><spring:message code="camp.contributor" text="Contributor" /></th>
			 		</tr>
			 		<tr>
			 			<th></th>
                		<th></th>
                		<th><spring:message code="camp.routine" text="Routine"/></th>
                		<th><spring:message code="camp.priority" text="Priority and"/></th>
                		<th><spring:message code="camp.express" text="Express"/></th>
                		<th></th>
                		<th></th>
                		<th></th>
			 		</tr>
			 	</thead>
			 	<tbody>
			 		<tr>
			 			  <td>1</td>
		                  <td><spring:message code="camp.aboutus" text="About Us"/></td>
		                  <td><i class="fa fa-check-circle" aria-hidden="true"></i></td>
		                  <td></td>
		                  <td></td>
		                  <td><spring:message code="camp.wim" text="WIM"/></td>
		                  <td><spring:message code="camp.dmc" text="DMC"/></td>
		                  <td><spring:message code="camp.du" text="DU"/></td>
			 			
			 		</tr>
			 		<tr>
			 			  <td>2</td>
		                  <td><spring:message code="camp.contactus" text="Contact Us"/></td>
		                  <td><i class="fa fa-check-circle" aria-hidden="true"></i></td>
		                  <td></td>
		                  <td></td>
		                  <td><spring:message code="camp.wim" text="WIM"/></td>
		                  <td><spring:message code="camp.dmc" text="DMC"/></td>
		                  <td><spring:message code="camp.du" text="DU"/></td>
			 			
			 		</tr>
			 		<tr>
			 			  <td>3</td>
		                  <td><spring:message code="camp.mediagallery" text="Media Gallery"/></td>
		                  <td><i class="fa fa-check-circle" aria-hidden="true"></i></td>
		                  <td></td>
		                  <td></td>
		                  <td><spring:message code="camp.wim" text="WIM"/></td>
		                  <td><spring:message code="camp.dmc" text="DMC"/></td>
		                  <td><spring:message code="camp.du" text="DU"/></td>
			 			
			 		</tr>
			 		<tr>
			 			  <td>4</td>
		                  <td><spring:message code="camp.imp.contact" text="Important Contacts "/></td>
		                  <td><i class="fa fa-check-circle" aria-hidden="true"></i></td>
		                  <td></td>
		                  <td></td>
		                  <td><spring:message code="camp.wim" text="WIM"/></td>
		                  <td><spring:message code="camp.dmc" text="DMC"/></td>
		                  <td><spring:message code="camp.du" text="DU"/></td>
			 			
			 		</tr>
			 		<tr>
			 			  <td>5</td>
		                  <td><spring:message code="camp.recent.announcement" text="Recent Announcement "/></td>
		                  <td><i class="fa fa-check-circle" aria-hidden="true"></i></td>
		                  <td></td>
		                  <td></td>
		                  <td><spring:message code="camp.wim" text="WIM"/></td>
		                  <td><spring:message code="camp.dmc" text="DMC"/></td>
		                  <td><spring:message code="camp.du" text="DU"/></td>
			 			
			 		</tr>
			 		<tr>
			 			  <td>6</td>
		                  <td><spring:message code="camp.external.links" text="External Links"/></td>
		                  <td><i class="fa fa-check-circle" aria-hidden="true"></i></td>
		                  <td></td>
		                  <td></td>
		                  <td><spring:message code="camp.wim" text="WIM"/></td>
		                  <td><spring:message code="camp.dmc" text="DMC"/></td>
		                  <td><spring:message code="camp.du" text="DU"/></td>
			 			
			 		</tr>
			 		<tr>
			 			  <td>7</td>
		                  <td><spring:message code="camp.quotations" text="Quotations"/></td>
		                  <td></td>
		                  <td><i class="fa fa-check-circle" aria-hidden="true"></i></td>
		                  <td></td>
		                  <td><spring:message code="camp.wim" text="WIM"/></td>
		                  <td><spring:message code="camp.dmc" text="DMC"/></td>
		                  <td><spring:message code="camp.du" text="DU"/></td>
			 			
			 		</tr>
			 		<tr>
			 			  <td>8</td>
		                  <td><spring:message code="camp.deptarmants" text="Departments"/></td>
		                  <td><i class="fa fa-check-circle" aria-hidden="true"></i></td>
		                  <td></td>
		                  <td></td>
		                  <td><spring:message code="camp.wim" text="WIM"/></td>
		                  <td><spring:message code="camp.dmc" text="DMC"/></td>
		                  <td><spring:message code="camp.du" text="DU"/></td>
			 			
			 		</tr>
			 		<tr>
			 			  <td>9</td>
		                  <td><spring:message code="camp.publication" text="Publications"/></td>
		                  <td></td>
		                  <td></td>
		                  <td><i class="fa fa-check-circle" aria-hidden="true"></i></td>
		                  <td><spring:message code="camp.wim" text="WIM"/></td>
		                  <td><spring:message code="camp.dmc" text="DMC"/></td>
		                  <td><spring:message code="camp.du" text="DU"/></td>
			 			
			 		</tr>
			 		<tr>
			 			  <td>10</td>
		                  <td><spring:message code="camp.opinion.poll" text="Opinion Poll "/></td>
		                  <td><i class="fa fa-check-circle" aria-hidden="true"></i></td>
		                  <td></td>
		                  <td></td>
		                  <td><spring:message code="camp.wim" text="WIM"/></td>
		                  <td><spring:message code="camp.dmc" text="DMC"/></td>
		                  <td><spring:message code="camp.du" text="DU"/></td>
			 			
			 		</tr>
			 	</tbody>
			 </table>
			 <p><spring:message code="camp.wim" text="WIM"/> = <spring:message code="camp.web.information.manager" text=" Web Information Manager"/></p>
			 <p><spring:message code="camp.du" text="DU"/> = <spring:message code="camp.departmant.user" text="Department User"/></p>
			 <p><spring:message code="camp.dmc" text="DMC"/> = <spring:message code="camp.dept.municipal.commissioner" text="Deputy Municipal Commissioner "/></p>
			<!--CMAP Policy END  -->
				<strong>
			      <spring:message code="help72" text="DISCLAIMER"/>
			   </strong>
			</p>
			<p>
			      <spring:message code="help73" text="This website of the Kalyan Dombivali Municipal Corporation Office, GoM is being maintained for information purposes only. Even though every effort is taken to provide accurate and up to date information,officers making use of the circulars posted on the website are advised to get in touch with the Kalyan Dombivali Municipal Corporation Office,GoM whenever there is any doubt regarding the correctness of the information contained therein. In the event of any conflict between the contents of the circulars on the website and the hard copy of the circulars issued by Kalyan Dombivali Municipal Corporation Office,GoM,the information in the hard copy should be relied upon and the matter shall be brought to the notice of the Kalyan Dombivali Municipal Corporation Office,GoM."/>
			</p>
			
		</form>
	</div>
</div>
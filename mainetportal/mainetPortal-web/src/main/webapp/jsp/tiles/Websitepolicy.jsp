<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<a id="CitizenService"></a>
<ol class="breadcrumb">
   <c:if
      test="${empty userSession.employee.emploginname or userSession.employee.emploginname eq'NOUSER' }"
      var="user">
      <li><a href="CitizenHome.html"><i class="fa fa-home"></i> Home </a></li>
   </c:if>
   <c:if
      test="${ !empty userSession.employee.emploginname and userSession.employee.emploginname ne'NOUSER' }"
      var="user">
      <li><a href="CitizenHome.html"><i class="fa fa-home"> Home </i></a></li>
   </c:if>
   <li>
      <spring:message code="website.policies" text="Website Policies"/>
   </li>
</ol>

<div class="container-fluid dashboard-page">
   <div class="col-sm-12" id="nischay">
      <h3>
         <spring:message code="website.policy" text="Website Policy"/>
      </h3>
      <div class="widget">
         <div class="form-horizontal padding widget-content">
            <div class="col-sm-12">
               <div class="accordion-toggle panel-group" id="accordion_single_collapse" style="font-size: 0.8em;">
                  <div class="panel panel-default">
                     <div class="panel-heading">
                        <h4>
                           <spring:message code="copyright.policy" text="COPYRIGHT POLICY"/>
                        </h4>
                     </div>
                     <div class="collapse in panel-collapse">
                        <div class="panel-body">
                           <p>
                              <spring:message code="copyright.policy1" text="Material featured on this website may be reproduced free of charge. However, the material has to be reproduced accurately and not to be used in a derogatory manner or in a misleading context. Wherever the material is being published or issued to others, the source must be prominently acknowledged. However, the permission to reproduce this material shall not extend to any material which is identified as being copyright of a third party. Authorization to reproduce such material must be obtained from the department/copyright holder concerned."/>
                           </p>
                        </div>
                     </div>
                  </div>
                  <div class="panel panel-default">
                     <div class="panel-heading">
                        <h4>
                           <spring:message code="privacy.policy" text="PRIVACY POLICY"/>
                        </h4>
                     </div>
                     <div class="collapse in panel-collapse">
                        <div class="panel-body">
                           <p>
                              <spring:message code="privacy.policy1" text="Thanks for visiting website of Information and Public Relation Department (i.e. IPRD) and reviewing our privacy policy."/>
                           </p>
                           <p>
                              <spring:message code="privacy.policy2" text="This website does not automatically capture any specific personal information from you (like name, phone number or e-mail address), that allows us to identify you individually. If you choose to provide us with your personal information, like names or addresses, when you visit our website, we use it only to fulfil your request for information.
                                 We do not sell or share any personally identifiable information volunteered on this site to any third party (public/private). Any information provided to this website will be protected from loss, misuse, unauthorized access or disclosure, alteration, or destruction."/>
                           </p>
                           <p>
                              <spring:message code="privacy.policy3" text="We gather certain information about the User, such as Internet protocol (IP) address, domain name, browser type, operating system, the date and time of the visit and the pages visited. We make no attempt to link these addresses with the identity of individuals visiting our site unless an attempt to damage the site has been detected.
                                 This information is only used to help us make the site more useful for you. With this data, we learn about the number of visitors to our site and the types of technology our visitors use. We never track or record information about individuals and their visits."/>
                           </p>
                        </div>
                     </div>
                  </div>
                  <div class="panel panel-default">
                     <div class="panel-heading">
                        <h4>
                           <spring:message code="cookies.policy" text="COOKIES POLICY"/>
                        </h4>
                     </div>
                     <div class="collapse in panel-collapse">
                        <div class="panel-body">
                           <p>
                              <spring:message code="cookies.policy1" text="A cookie is a piece of software code that an internet web site sends to your browser when you access information at that site. A cookie is stored as a simple text file on your computer or mobile device by a websiteâs server and only that server will be able to retrieve or read the contents of that cookie. Cookies let you navigate between pages efficiently as they store your preferences, and generally improve your experience of a website."/>
                           </p>
                           <p>
                              <spring:message code="cookies.policy2" text="We are using following types of cookies in our site: -"/>
                           </p>
                           <ul>
                              <li>
                                 <spring:message code="cookies.policy3" text="Analytics cookies for anonymously remembering your computer or mobile device when you visit our website to keep track of browsing patterns."/>
                              </li>
                              <li>
                                 <spring:message code="cookies.policy4" text="Service cookies for helping us to make our website work efficiently, remembering your registration and login details, settings preferences, and keeping track of the pages, you view."/>
                              </li>
                              <li>
                                 <spring:message code="cookies.policy5" text="Non-persistent cookies a.k.a. per-session cookies. Per-session cookies serve technical purposes, like providing seamless navigation through this website. These cookies do not collect personal information on users and they are deleted as soon as you leave our website. The cookies do not permanently record data and they are not stored on your computerâs hard drive. The cookies are stored in memory and are only available during an active browser session. Again, once you close your browser, the cookie disappears."/>
                              </li>
                           </ul>
                        </div>
                     </div>
                  </div>
                  <div class="panel panel-default">
                     <div class="panel-heading">
                        <h4>
                           <spring:message code="hyperlink.policy" text="HYPERLINKING POLICY"/>
                        </h4>
                     </div>
                     <div class="collapse in panel-collapse">
                        <div class="panel-body">
                           <p>
                              <spring:message code="hyperlink.policy1" text="Links to external websites/portals"/>
                           </p>
                           <p>
                              <spring:message code="hyperlink.policy2" text="At many places in this website, you shall find links to other websites/portals. These links have been placed for your convenience. Information and Public Relation Department (i.e. IPRD) Office is not responsible for the contents of the linked websites and does not necessarily endorse the views expressed in them. Mere presence of the link or its listing on this website should not be assumed as endorsement of any kind. We cannot guarantee that these links will work all the time and we have no control over availability of linked destinations."/>
                           </p>
                           <h1>
                              <spring:message code="hyperlink.policy3" text="Links to Information and Public Relation Department (i.e. IPRD) Office Website by other websites/portals"/>
                           </h1>
                           <p>
                              <spring:message code="hyperlink.policy4" text="We do not object to you linking directly to the information that is hosted on this web site and no prior permission is required for the same. However, we would like you to inform us about any links provided to this website so that you can be informed of any changes or updates therein. Also, we do not permit our pages to be loaded into frames on your site. The pages belonging to this website must load into a newly opened browser window of the User."/>
                           </p>
                        </div>
                     </div>
                  </div>
                  <div class="panel panel-default">
                     <div class="panel-heading">
                        <h4>
                           <spring:message code="terms.conditions" text="TERMS & CONDITIONS"/>
                        </h4>
                     </div>
                     <div class="collapse in panel-collapse">
                        <div class="panel-body">
                           <p>
                              <spring:message code="terms.conditions1" text="Though all efforts have been made to ensure the accuracy and currency of the content on this website, the same should not be construed as a statement of law or used for any legal purposes. In case of any ambiguity or doubts, users are advised to verify/check with the Information and Public Relation Departmentâs Office, GoB and/or other source(s), and to obtain appropriate professional advice."/>
                           </p>
                           <p>
                              <spring:message code="terms.conditions2" text="Under no circumstances will Information and Public Relation Departmentâs Office, GoB be liable for any expense, loss or damage including, without limitation, indirect or consequential loss or damage, or any expense, loss or damage whatsoever arising from use, or loss of use, of data, arising out of or in connection with the use of this website."/>
                           </p>
                           <p>
                              <spring:message code="terms.conditions3" text="These terms and conditions shall be governed by and construed in accordance with the Indian Laws. Any dispute arising under these terms and conditions shall be subject to the jurisdiction of the courts of India."/>
                           </p>
                           <p>
                              <spring:message code="terms.conditions4" text="The information posted on this website could include hypertext links or pointers to information created and maintained by non-Government/private organisations. Information and Public Relation Departmentâs Office, GoB is providing these links and pointers solely for your information and convenience. When you select a link to an external website, you are leaving the Information and Public Relation Departmentâs Office, GoB website and are subject to the privacy and security policies of the owners/sponsors of the external website. Information and Public Relation Departmentâs Office, GoB does not guarantee the availability of such linked pages at all times. Prime Ministerâs Office cannot authorise the use of copyrighted materials contained in a linked website. Users are advised to request such authorisation from the owner of the linked website. Information and Public Relation Departmentâs Office, GoB does not guarantee that linked websites comply with Indian Government Web Guidelines."/>
                           </p>
                        </div>
                     </div>
                  </div>
                  <div class="panel panel-default">
                     <div class="panel-heading">
                        <h4>
                           <spring:message code="regional.language.policy" text="REGIONAL LANGUAGE POLICY"/>
                        </h4>
                     </div>
                     <div class="collapse in panel-collapse">
                        <div class="panel-body">
                           <p>
                              <spring:message code="regional.language.policy1" text="Though all efforts have been made to ensure the accuracy of the content in regional languages on this Portal, the same should not be construed as a statement of law or used for any legal purposes. For any discrepancy in the regional language content you may refer to the original English content. For any error in language, you may report the same through the online feedback form."/>
                           </p>
                           <p>
                              <spring:message code="regional.language.policy2" text="Information and Public Relation Departmentâs Office, GoB accepts no responsibility in relation to the accuracy, completeness, usefulness or otherwise, of the contents. In no event will the Government be liable for any expense, loss or damage including, without limitation, indirect or consequential loss or damage, or any expense, loss or damage whatsoever arising from use, or loss of use, of data, arising out of or in connection with the use of this Portal."/>
                           </p>
                        </div>
                     </div>
                  </div>
                  <div class="panel panel-default">
                     <div class="panel-heading">
                        <h4>
                           <spring:message code="discalimer" text="DISCLAIMER"/>
                        </h4>
                     </div>
                     <div class="collapse in panel-collapse">
                        <div class="panel-body">
                           <p>
                              <spring:message code="discalimer1" text="This website of the Information and Public Relation Departmentâs Office, GoB is being maintained for information purposes only. Even though every effort is taken to provide accurate and up to date information, officers making use of the circulars posted on the website are advised to get in touch with the Information and Public Relation Departmentâs Office, GoB whenever there is any doubt regarding the correctness of the information contained therein. In the event of any conflict between the contents of the circulars on the website and the hard copy of the circulars issued by Information and Public Relation Departmentâs Office, GoB, the information in the hard copy should be relied upon and the matter shall be brought to the notice of the Information and Public Relation Departmentâs Office, GoB."/>
                           </p>
                        </div>
                     </div>
                  </div>
                  <div class="panel panel-default">
                     <div class="panel-heading">
                        <h4>
                           <spring:message code="archival.policy" text="Content Archival Policy"/>
                        </h4>
                     </div>
                     <div class="collapse in panel-collapse">
                        <div class="panel-body">
                           <p>
                              <spring:message code="archival.policy1" text="The content components are created with metadata, source and validity date. There would be some content which is permanent in nature and for such content it is assumed that the content would reviewed in specific period of time unless it is edited / deleted based on requirement. The content shall not be displayed on the Website after the validity date."/>
                           </p>
                           <p>
                              <spring:message code="archival.policy2" text="Some of the short lived content components like tenders, recruitment etc., which will not have any relevance on the website after the intended purpose."/>
                           </p>
                           <p>
                              <spring:message code="archival.policy3" text="The content components like Office documents, Public Notice, latest news is regularly reviewed as per the Content Review Policy."/>
                           </p>
                           <p>
                              <spring:message code="archival.policy4" text="The content is reviewed before the validity date and if required content will be revalidated and validity date is modified. If content is not relevant, then the content is archived and no longer published on the Website."/>
                           </p>
                           <p>
                              <spring:message code="archival.policy5" text="The above mentioned policy in force and will be followed while maintaining the website."/>
                           </p>
                        </div>
                     </div>
                  </div>
                  <div class="panel panel-default">
                     <div class="panel-heading">
                        <h4>
                           <spring:message code="review.policy" text="Content Review Policy"/>
                        </h4>
                     </div>
                     <div class="collapse in panel-collapse">
                        <div class="panel-body">
                           <p>
                              <spring:message code="review.policy1" text="All possible efforts need to be taken to keep the content on the Website current and up-to-date. This Content Review Policy defines the roles and responsibilities of the website content review and the manner in which it need to be carried out. Review Policies are defined for the diverse content elements."/>
                           </p>
                           <p>
                              <spring:message code="review.policy2" text="The Review Policy is based on different type of content elements, its validity and relevance as well as the archival policy."/>
                           </p>
                           <p>
                              <spring:message code="review.policy3" text="The entire website content would be reviewed by the “Municipal Corporation of Chhattisgarh” Team."/>
                           </p>
                        </div>
                     </div>
                  </div>
                  <div class="panel panel-default">
                     <div class="panel-heading">
                        <h4>
                           <spring:message code="approval.policy" text="Content Creation And Approval Policy"/>
                        </h4>
                     </div>
                     <div class="collapse in panel-collapse">
                        <div class="panel-body">
                           <p>
                              <spring:message code="approval.policy1" text="Content would be created by the authorized Content Maker User in a consistent fashion to maintain uniformity and to bring in standardization along with associated metadata and keywords. In order to present the content as per the requirement of the viewer, organize the content in categorized manner and to retrieve the relevant content efficiently, the content is contributed to the website through a Content Management System which would be web-based having user-friendly interface."/>
                           </p>
                           <p>
                              <spring:message code="approval.policy2" text="The content on the website goes through the entire life-cycle process of:-"/>
                           </p>
                           <ul>
                              <li>
                                 <spring:message code="approval.policy3" text="Creation "/>
                              </li>
                              <li>
                                 <spring:message code="approval.policy4" text="Approval"/>
                              </li>
                              <li>
                                 <spring:message code="approval.policy5" text="Publishing on website"/>
                              </li>
                           </ul>
                           <p>
                              <spring:message code="approval.policy6" text="Once the content is created  it is approved  by Checker User  prior to being published on the Website. If the content is rejected at any level then it is reverted back to the originator of the content ."/>
                           </p>
                        </div>
                     </div>
                  </div>
                  <div class="panel panel-default">
                     <div class="panel-heading">
                        <h4>
                           <spring:message code="management.policy" text="Website Contingency Management Policy"/>
                        </h4>
                     </div>
                     <div class="collapse in panel-collapse">
                        <div class="panel-body">
                           <p>
                              <spring:message code="management.policy1" text="Data Corruption:� A proper mechanism has to be worked out by the concerned in consultation with their web hosting service provider to ensure that appropriate and regular back-ups of the website data are being taken. These enable a fast recovery and uninterrupted availability of the information to the citizens in view of any data corruption."/>
                           </p>
                        </div>
                     </div>
                  </div>
                  <div class="panel panel-default">
                     <div class="panel-heading">
                        <h4>
                           <spring:message code="monitoring.policy" text="Website Monitoring Policy"/>
                        </h4>
                     </div>
                     <div class="collapse in panel-collapse">
                        <div class="panel-body">
                           <p>
                              <spring:message code="monitoring.policy1" text="This website� has a Website Monitoring Policy in place and the website is monitored periodically to address and fix the quality and compatibility issues around the following parameters:"/>
                           </p>
                           <p>
                              <spring:message code="monitoring.policy2" text="Functionality :� All modules of the website are tested for their functionality. The interactive components of the site such as, feedback forms are working smoothly."/>
                           </p>
                           <p>
                              <spring:message code="monitoring.policy3" text="Broken Links :� The website is thoroughly reviewed to rule out the presence of any broken links or errors."/>
                           </p>
                           <p>
                              <spring:message code="monitoring.policy4" text="Feedback :� Feedback from the visitors is the best way to judge a website’s performance and make necessary improvements. A proper mechanism for feedback is in place to carry out the changes and enhancements as suggested by the visitors."/>
                           </p>
                        </div>
                     </div>
                  </div>
                  <div class="panel panel-default">
                     <div class="panel-heading">
                        <h4>
                           <spring:message code="security.policy" text="Website Security Policy"/>
                        </h4>
                     </div>
                     <div class="collapse in panel-collapse">
                        <div class="panel-body">
                           <h1>
                              <spring:message code="security.policy1" text="User ID and Password Policy:"/>
                           </h1>
                           <p>
                              <spring:message code="security.policy2" text="Access to sensitive or proprietary business information on  websites is limited to users who have been determined to have an appropriate official reason for having access to such data. All registered users who are granted security access will be identified by a user name provided by webmaster."/>
                           </p>
                           <p>
                              <spring:message code="security.policy3" text="Users who are granted password access to restricted information are prohibited from sharing those passwords with or divulging those passwords to any third parties. User will notify us immediately in the event a User ID or password is lost or stolen or if User believes that a non-authorized individual has discovered the User ID or password."/>
                           </p>
                           <p>
                              <spring:message code="security.policy4" text="If you have any questions or comments regarding Website Security Policy, please contact the Web Information Manager by using the Feedback website. "/>
                           </p>
                        </div>
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </div>
</div>
<hr/>
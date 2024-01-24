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
         <li><a href="CitizenHome.html"><i class="fa fa-home"></i> Home </a></li>
      </c:if>
      <li>
         <spring:message code="help.footer" text="Help"/>
      </li>
   </ol>
   
   <div class="container-fluid">
      <div class="col-sm-12" id="nischay">
         <div class="widget">
         	<div class="widget-header">
         		<h2>
		            <spring:message code="help.footer" text="HELP"/>
		        </h2>
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
								<th><spring:message code="help4" text="Document Type"/></th>
								<th><spring:message code="help5" text="Plug-in for Download"/></th>
							</tr>
						</thead>
					     <tbody>
					      <tr>
					         <td>
					            <spring:message code="help6" text="Help Document for Portal"/>
					         </td>
					         <td>
					            <a href="assets/img/Help_Document.pdf" target="_blank">
					               <i class="fa fa-file-pdf-o" aria-hidden="true"></i>
					               <spring:message code="help7" text="Help Document Portal"/>
					            </a>
					         </td>
					      </tr>
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
	                        
	            </form>
               <div class="clear"></div>
            </div>
         </div>
      </div>
   </div>
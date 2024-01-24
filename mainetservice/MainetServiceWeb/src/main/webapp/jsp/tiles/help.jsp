<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

 <div class="container-fluid dashboard-page">
<!-- English starts -->
<c:if test="${userSession.languageId eq 1}"> 
  <div class="col-sm-12" id="nischay">
  	<h4><strong><spring:message code="help.footer" text="Help"/></strong></h4>
     <div class="widget">
            <div class="widget-content padding">
               <div class="col-sm-12">
                  <form action="" method="get" class="form-horizontal" novalidate>
                     <div class="panel-group accordion-toggle" id="accordion_single_collapse">
                        <div  class="panel panel-default">
                           <div id="accordion1" class="panel-collapse collapse in">
                              <div class="panel-body">
                                 <p>
                                    <strong>
                                       <spring:message code="help1.eng" text="Viewing Information in Various File Formats"/>
                                    </strong>
                                 </p>
                                 <p>
                                    <spring:message code="help2.eng" text="The information provided by this website is available in various file formats, such as Portable Document Format (PDF), Word, Excel and PowerPoint. To view the information properly, your browser needs to have the required plug-ins or software. For example, the Adobe Flash software is required to view the Flash files. In case your system does not have this software, you can download it from the Internet for free. The table lists the required plug-ins needed to view the information in various file formats."/>
                                 </p>
                                 <p>
                                    <strong>
                                       <spring:message code="help3.eng" text="Plug-in for alternate document types"/>
                                    </strong>
                                 </p><br/><br/>
                                 <table class="table table-bordered table-help">
                                    <tbody>
                                       <tr>
                                          <th>
                                             <spring:message code="help4.eng" text="Document Type"/>
                                          </th>
                                          <th>
                                             <spring:message code="help5.eng" text="Plug-in for Download"/>
                                          </th>
                                       </tr>
                                       <tr>
                                          <td>
                                             <spring:message code="help6.eng" text="Help Document for Portal"/>
                                          </td>
                                          <td>
                                             <a href="assets/img/Help_Document.pdf" target="_blank">
                                                <i class="fa fa-file-pdf-o" aria-hidden="true"></i>
                                                <spring:message code="help7.eng" text="Help Document Portal"/>
                                             </a>
                                          </td>
                                       </tr>
                                       <tr>
                                          <td>
                                             <spring:message code="help8.eng" text="Portable Document Format (PDF) files"/>
                                          </td>
                                          <td>
                                             <a href="http://get.adobe.com/reader/" target="_blank" aria-label="Download PDF Reader">
                                                <i class="fa fa-file-pdf-o" aria-hidden="true"></i>
                                                <spring:message code="help9.eng" text="Adobe Acrobat Reader"/>
                                             </a>
                                             <br>
                                             <spring:message code="help10.eng" text="Convert a PDF file online into HTML or text format"/>
                                          </td>
                                       </tr>
                                       <tr>
                                          <td>
                                             <spring:message code="help11.eng" text="Word files"/>
                                          </td>
                                          <td>
                                             <a href="http://www.microsoft.com/downloads/details.aspx?familyid=941b3470-3ae9-4aee-8f43-c6bb74cd1466&amp;displaylang=en" target="_blank">
                                                <i class="fa fa-file-word-o" aria-hidden="true"></i>
                                                <spring:message code="help12.eng" text="Word Viewer (in any version till 2003)"/>
                                             </a>
                                             <br>
                                             <spring:message code="help13.eng" text="Microsoft Office Compatibility Pack for Word (for 2007 version)"/>
                                          </td>
                                       </tr>
                                       <tr>
                                          <td>
                                             <spring:message code="help14.eng" text="Excel files"/>
                                          </td>
                                          <td>
                                             <a href="http://www.microsoft.com/downloads/details.aspx?FamilyID=c8378bf4-996c-4569-b547-75edbd03aaf0&amp;displaylang=EN" target="_blank">
                                                <i class="fa fa-file-excel-o" aria-hidden="true"></i>
                                                <spring:message code="help15.eng" text="Excel Viewer 2003 (in any version till 2003)"/>
                                             </a>
                                             <br>
                                             <spring:message code="help16.eng" text="Microsoft Office Compatibility Pack for Excel (for 2007 version)"/>
                                          </td>
                                       </tr>
                                       <tr>
                                          <td>
                                             <spring:message code="help17.eng" text="PowerPoint presentations"/>
                                          </td>
                                          <td>
                                             <a href="http://www.microsoft.com/downloads/details.aspx?familyid=941b3470-3ae9-4aee-8f43-c6bb74cd1466&amp;displaylang=en" target="_blank">
                                                <i class="fa fa-file-powerpoint-o" aria-hidden="true"></i>
                                                <spring:message code="help18.eng" text="PowerPoint Viewer 2003 (in any version till 2003)"/>
                                             </a>
                                             <br>
                                             <spring:message code="help19.eng" text="Microsoft Office Compatibility Pack for PowerPoint (for 2007 version)"/>
                                          </td>
                                       </tr>
                                       <tr>
                                          <td>
                                             <spring:message code="help20.eng" text="Flash content"/>
                                          </td>
                                          <td>
                                             <a href="http://get.adobe.com/flashplayer/" target="_blank">
                                                <i class="fa fa-file-video-o" aria-hidden="true"></i>
                                                <spring:message code="help21.eng" text="Adobe Flash Player"/>
                                             </a>
                                          </td>
                                       </tr>
                                    </tbody>
                                 </table>
                              </div>
                           </div>
                        </div>
                     </div>
                  </form>
               </div>
            </div>
         </div>
      
     </div>
     </c:if>
<!-- English ends -->

<!-- Hindi starts -->
<c:if test="${userSession.languageId ne 1}">
<div class="col-sm-12" id="nischay">
	<h4><strong><spring:message code="help.footer.reg" text="Help"/></strong></h4>
     <div class="widget">
            <div class="widget-content padding">
               <div class="col-sm-12">
                  <form action="" method="get" class="form-horizontal" novalidate>
                     <div class="panel-group accordion-toggle" id="accordion_single_collapse">
                        <div  class="panel panel-default">
                           <div id="accordion1" class="panel-collapse collapse in">
                              <div class="panel-body">
                                 <p>
                                    <strong>
                                       <spring:message code="help1.reg" text="Viewing Information in Various File Formats"/>
                                    </strong>
                                 </p>
                                 <p>
                                    <spring:message code="help2.reg" text="The information provided by this website is available in various file formats, such as Portable Document Format (PDF), Word, Excel and PowerPoint. To view the information properly, your browser needs to have the required plug-ins or software. For example, the Adobe Flash software is required to view the Flash files. In case your system does not have this software, you can download it from the Internet for free. The table lists the required plug-ins needed to view the information in various file formats."/>
                                 </p>
                                 <p>
                                    <strong>
                                       <spring:message code="help3.reg" text="Plug-in for alternate document types"/>
                                    </strong>
                                 </p><br/><br/>
                                 <table class="table table-bordered table-help">
                                    <tbody>
                                       <tr>
                                          <th>
                                             <spring:message code="help4.reg" text="Document Type"/>
                                          </th>
                                          <th>
                                             <spring:message code="help5.reg" text="Plug-in for Download"/>
                                          </th>
                                       </tr>
                                       <tr>
                                          <td>
                                             <spring:message code="help6.reg" text="Help Document for Portal"/>
                                          </td>
                                          <td>
                                             <a href="assets/img/Help_Document.pdf" target="_blank">
                                                <i class="fa fa-file-pdf-o" aria-hidden="true"></i>
                                                <spring:message code="help7.reg" text="Help Document Portal"/>
                                             </a>
                                          </td>
                                       </tr>
                                       <tr>
                                          <td>
                                             <spring:message code="help8.reg" text="Portable Document Format (PDF) files"/>
                                          </td>
                                          <td>
                                             <a href="http://get.adobe.com/reader/" target="_blank" aria-label="Download PDF Reader">
                                                <i class="fa fa-file-pdf-o" aria-hidden="true"></i>
                                                <spring:message code="help9.reg" text="Adobe Acrobat Reader"/>
                                             </a>
                                             <br>
                                             <spring:message code="help10.reg" text="Convert a PDF file online into HTML or text format"/>
                                          </td>
                                       </tr>
                                       <tr>
                                          <td>
                                             <spring:message code="help11.reg" text="Word files"/>
                                          </td>
                                          <td>
                                             <a href="http://www.microsoft.com/downloads/details.aspx?familyid=941b3470-3ae9-4aee-8f43-c6bb74cd1466&amp;displaylang=en" target="_blank">
                                                <i class="fa fa-file-word-o" aria-hidden="true"></i>
                                                <spring:message code="help12.reg" text="Word Viewer (in any version till 2003)"/>
                                             </a>
                                             <br>
                                             <spring:message code="help13.reg" text="Microsoft Office Compatibility Pack for Word (for 2007 version)"/>
                                          </td>
                                       </tr>
                                       <tr>
                                          <td>
                                             <spring:message code="help14.reg" text="Excel files"/>
                                          </td>
                                          <td>
                                             <a href="http://www.microsoft.com/downloads/details.aspx?FamilyID=c8378bf4-996c-4569-b547-75edbd03aaf0&amp;displaylang=EN" target="_blank">
                                                <i class="fa fa-file-excel-o" aria-hidden="true"></i>
                                                <spring:message code="help15.reg" text="Excel Viewer 2003 (in any version till 2003)"/>
                                             </a>
                                             <br>
                                             <spring:message code="help16.reg" text="Microsoft Office Compatibility Pack for Excel (for 2007 version)"/>
                                          </td>
                                       </tr>
                                       <tr>
                                          <td>
                                             <spring:message code="help17.reg" text="PowerPoint presentations"/>
                                          </td>
                                          <td>
                                             <a href="http://www.microsoft.com/downloads/details.aspx?familyid=941b3470-3ae9-4aee-8f43-c6bb74cd1466&amp;displaylang=en" target="_blank">
                                                <i class="fa fa-file-powerpoint-o" aria-hidden="true"></i>
                                                <spring:message code="help18.reg" text="PowerPoint Viewer 2003 (in any version till 2003)"/>
                                             </a>
                                             <br>
                                             <spring:message code="help19.reg" text="Microsoft Office Compatibility Pack for PowerPoint (for 2007 version)"/>
                                          </td>
                                       </tr>
                                       <tr>
                                          <td>
                                             <spring:message code="help20.reg" text="Flash content"/>
                                          </td>
                                          <td>
                                             <a href="http://get.adobe.com/flashplayer/" target="_blank">
                                                <i class="fa fa-file-video-o" aria-hidden="true"></i>
                                                <spring:message code="help21.reg" text="Adobe Flash Player"/>
                                             </a>
                                          </td>
                                       </tr>
                                    </tbody>
                                 </table>
                              </div>
                           </div>
                        </div>
                     </div>
                  </form>
               </div>
            </div>
         </div>
      
     </div>
</c:if>
<!-- Hindi ends -->
  </div>

</body>
</html>
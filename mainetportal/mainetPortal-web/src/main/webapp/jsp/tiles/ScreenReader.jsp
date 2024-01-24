<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

   <c:set var="htmlContent" value="${command.getAllhtml('Screen-Reader')}" />
   <a id="CitizenService"></a>
   <c:forEach items="${htmlContent}" var="Allhtml">
      ${Allhtml}
   </c:forEach>
   <c:if test="${htmlContent eq null || empty htmlContent}">
      <div class="content-page">
         <ol class="breadcrumb" id="CitizenService">
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
               <spring:message code="screen.reader" text="Screen Reader"/>
            </li>
         </ol>
         <div id="content" class="content">
            <div class="widget">
               <div class="widget-header">
                  <h2>
                     <spring:message code="screen.reader" text="Screen Reader"/>
                  </h2>
               </div>
               <div class="widget-content padding">                  
                  <p>
                     <spring:message code="screen.reader1" text="The Department of Information Technology website complies
                        with World Wide Web Consortium (W3C) Web Content Accessibility
                        Guidelines (WCAG) 2.0 level A. This will enable people with visual
                        impairments access the website using Assistive Technologies, such
                        as screen readers. The information of the website is accessible
                        with different screen readers, such as JAWS."/>
                  </p>
                  <p>
                     <spring:message code="screen.reader2" text="Following table lists the information about different screen
                        readers:"/>
                  </p>
                  <p>
                     <spring:message code="screen.reader3" text="Information related to the various screen readers :"/>
                  </p>
                  <div class="table-responsive">
	                  <table
	                     class="table table-bordered table-condensed" role="presentation">
	                     <thead>
	                        <tr>
	                           <th scope="col">
	                              <spring:message code="screen.reader" text="Screen Reader"/>
	                           </th>
	                           <th scope="col">
	                              <spring:message code="screen.reader4" text="Website"/>
	                           </th>
	                           <th scope="col">
	                              <spring:message code="screen.reader5" text="Free / Commercial"/>
	                           </th>
	                        </tr>
	                     </thead>
	                     <tbody>
	                        <tr>
	                           <td>
	                              <spring:message code="screen.reader6" text="Screen Access For All (SAFA)"/>
	                           </td>
	                           <td>
	                              <a title="External website that opens in a new window"
	                                 onclick="return confirmContinue();"
	                                 href="http://safa.sourceforge.net/" target="_blank" >
	                                 <spring:message code="screen.reader7" text="http://safa.sourceforge.net/"/>
	                                 <span
	                                    class="hide">
	                                    <spring:message code="screen.reader8" text="External website that opens in a new
	                                       window"/>
	                                 </span>
	                              </a>
	                           </td>
	                           <td>
	                              <spring:message code="screen.reader9" text="Free"/>
	                           </td>
	                        </tr>
	                        <tr>
	                           <td>
	                              <spring:message code="screen.reader10" text="Non Visual Desktop Access (NVDA)"/>
	                           </td>
	                           <td>
	                              <a title="External website that opens in a new window "
	                                 onclick="return confirmContinue();"
	                                 href="http://www.nvda-project.org/" target="_blank" >
	                                 <spring:message code="screen.reader11" text="http://www.nvda-project.org/"/>
	                                 <span class="hide">
	                                    <spring:message code="screen.reader12" text="External website that opens in a
	                                       new window"/>
	                                 </span>
	                              </a>
	                           </td>
	                           <td>
	                              <spring:message code="screen.reader9" text="Free"/>
	                           </td>
	                        </tr>
	                        <tr>
	                           <td>
	                              <spring:message code="screen.reader13" text="System Access To Go"/>
	                           </td>
	                           <td>
	                              <a title="External website that opens in a new window "
	                                 onclick="return confirmContinue();"
	                                 href="http://www.satogo.com/" target="_blank" >
	                                 <spring:message code="screen.reader14" text="http://www.satogo.com/"/>
	                                 <span class="hide">
	                                    <spring:message code="screen.reader12" text="External website that opens in a
	                                       new window"/>
	                                 </span>
	                              </a>
	                           </td>
	                           <td>
	                              <spring:message code="screen.reader9" text="Free"/>
	                           </td>
	                        </tr>
	                        <tr>
	                           <td>
	                              <spring:message code="screen.reader15" text="Thunder"/>
	                           </td>
	                           <td>
	                              <a title="External website that opens in a new window"
	                                 onclick="return confirmContinue();"
	                                 href="http://www.screenreader.net/index.php" target="_blank"
	                                 >
	                                 <spring:message code="screen.reader16" text="http://www.screenreader.net/index.php"/>
	                                 <span
	                                    class="hide">
	                                    <spring:message code="screen.reader12" text="External website that opens in a new
	                                       window"/>
	                                 </span>
	                              </a>
	                           </td>
	                           <td>
	                              <spring:message code="screen.reader9" text="Free"/>
	                           </td>
	                        </tr>
	                        <tr>
	                           <td>
	                              <spring:message code="screen.reader17" text="WebAnywhere"/>
	                           </td>
	                           <td>
	                              <a title="External website that opens in a new window"
	                                 onclick="return confirmContinue();"
	                                 href="http://webanywhere.cs.washington.edu/wa.php"
	                                 target="_blank" >
	                                 <spring:message code="screen.reader18" text="http://webanywhere.cs.washington.edu/wa.php"/>
	                                 <span class="hide">
	                                    <spring:message code="screen.reader12" text="External website that opens in a
	                                       new window"/>
	                                 </span>
	                              </a>
	                           </td>
	                           <td>
	                              <spring:message code="screen.reader9" text="Free"/>
	                           </td>
	                        </tr>
	                        <tr>
	                           <td>
	                              <spring:message code="screen.reader19" text="Hal"/>
	                           </td>
	                           <td>
	                              <a title="External website that opens in a new window"
	                                 onclick="return confirmContinue();"
	                                 href="http://www.yourdolphin.co.uk/productdetail.asp?id=5"
	                                 target="_blank" >
	                                 <spring:message code="screen.reader20" text="http://www.yourdolphin.co.uk/productdetail.asp?id=5"/>
	                                 <span class="hide">
	                                    <spring:message code="screen.reader12" text="External website that opens in a
	                                       new window"/>
	                                 </span>
	                              </a>
	                           </td>
	                           <td>
	                              <spring:message code="screen.reader21" text="Commercial"/>
	                           </td>
	                        </tr>
	                        <tr>
	                           <td>
	                              <spring:message code="screen.reader22" text="JAWS"/>
	                           </td>
	                           <td>
	                              <a title="External website that opens in a new window"
	                                 onclick="return confirmContinue();"
	                                 href="http://www.freedomscientific.com/jaws-hq.asp"
	                                 target="_blank" >
	                                 <spring:message code="screen.reader23" text="http://www.freedomscientific.com/jaws-hq.asp"/>
	                                 <span class="hide">
	                                    <spring:message code="screen.reader12" text="External website that opens in a
	                                       new window"/>
	                                 </span>
	                              </a>
	                           </td>
	                           <td>
	                              <spring:message code="screen.reader21" text="Commercial"/>
	                           </td>
	                        </tr>
	                        <tr>
	                           <td>
	                              <spring:message code="screen.reader24" text="Supernova"/>
	                           </td>
	                           <td>
	                              <a title="External website that opens in a new window"
	                                 onclick="return confirmContinue();"
	                                 href="http://www.yourdolphin.co.uk/productdetail.asp?id=1"
	                                 target="_blank" >
	                                 <spring:message code="screen.reader25" text="http://www.yourdolphin.co.uk/productdetail.asp?id=1"/>
	                                 <span class="hide">
	                                    <spring:message code="screen.reader12" text="External website that opens in a
	                                       new window"/>
	                                 </span>
	                              </a>
	                           </td>
	                           <td>
	                              <spring:message code="screen.reader21" text="Commercial"/>
	                           </td>
	                        </tr>
	                        <tr>
	                           <td>
	                              <spring:message code="screen.reader26" text="Window-Eyes"/>
	                           </td>
	                           <td>
	                              <a title="External website that opens in a new window"
	                                 onclick="return confirmContinue();"
	                                 href="http://www.gwmicro.com/Window-Eyes/" target="_blank"
	                                 >
	                                 <spring:message code="screen.reader27" text="http://www.gwmicro.com/Window-Eyes/"/>
	                                 <span
	                                    class="hide">
	                                    <spring:message code="screen.reader12" text="External website that opens in a new
	                                       window"/>
	                                 </span>
	                              </a>
	                           </td>
	                           <td>
	                              <spring:message code="screen.reader21" text="Commercial"/>
	                           </td>
	                        </tr>
	                     </tbody>
	                  </table>
                  </div>
               </div>
            </div>
         </div>
      </div>
      <hr/>
   </c:if>
</html>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<c:set var="htmlContent" value="${command.getAllhtml('Screen-Reader')}" />

<c:forEach items="${htmlContent}" var="Allhtml">
		${Allhtml}
</c:forEach>
<c:if test="${htmlContent eq null || empty htmlContent}">
	<div class="content-page">
		<ol class="breadcrumb">
			<c:if
				test="${empty userSession.employee.emploginname or userSession.employee.emploginname eq'NOUSER' }"
				var="user">
			<!-- <li><a href="CitizenHome.html"><i class="fa fa-home"></i>&nbsp;Home</a></li> -->
			</c:if>
			<c:if
				test="${ !empty userSession.employee.emploginname and userSession.employee.emploginname ne'NOUSER' }"
				var="user">
				<!-- <li><a href="CitizenHome.html"><i class="fa fa-home"></i>&nbsp;Home</a></li> -->
			</c:if>
            <%-- <li>              	
               <spring:message code="screen.reader.eng" text="Screen Reader"/>
            </li> --%>
		</ol>
		<div id="content" class="content">
		<!-- English starts -->
		<div class="container-fluid dashboard-page">
		<div class="col-sm-12" id="nischay">
		<c:if test="${userSession.languageId eq 1}">
			<h4><strong><spring:message code="screen.reader.eng" text="Screen Reader"/></strong></h4>
			<div class="widget">
				<div class="widget-content padding">
                  <p>
                     <spring:message code="screen.reader.eng1" text="The Department of Information Technology website complies
                        with World Wide Web Consortium (W3C) Web Content Accessibility
                        Guidelines (WCAG) 2.0 level A. This will enable people with visual
                        impairments access the website using Assistive Technologies, such
                        as screen readers. The information of the website is accessible
                        with different screen readers, such as JAWS."/>
                  </p>
                  <p>
                     <spring:message code="screen.reader.eng2" text="Following table lists the information about different screen
                        readers:"/>
                  </p>
                  <p>
                     <spring:message code="screen.reader.eng3" text="Information related to the various screen readers :"/>
                  </p><br/><br/>
                  <div class="table-responsive blues oranges greens">
					<table summary="This table displays content of Screen Reader"
						class="table table-bordered table-condensed" role="presentation">
						<thead>
                        <tr>
                           <th scope="col">
                              <spring:message code="screen.reader.eng" text="Screen Reader"/>
                           </th>
                           <th scope="col">
                              <spring:message code="screen.reader.eng4" text="Website"/>
                           </th>
                           <th scope="col">
                              <spring:message code="screen.reader.eng5" text="Free / Commercial"/>
                           </th>
                        </tr>
                     </thead>
                     <tbody>
                        <tr>
                           <td>
                              <spring:message code="screen.reader.eng6" text="Screen Access For All (SAFA)"/>
                           </td>
                           <td>
                              <a title="External website that opens in a new window"
                                 onclick="return confirmContinue();"
                                 href="http://safa.sourceforge.net/" target="_blank" >
                                 <spring:message code="screen.reader.eng7" text="http://safa.sourceforge.net/"/>
                                 <span
                                    class="hide">
                                    <spring:message code="screen.reader.eng8" text="External website that opens in a new
                                       window"/>
                                 </span>
                              </a>
                           </td>
                           <td>
                              <spring:message code="screen.reader.eng9" text="Free"/>
                           </td>
                        </tr>
                        <tr>
                           <td>
                              <spring:message code="screen.reader.eng10" text="Non Visual Desktop Access (NVDA)"/>
                           </td>
                           <td>
                              <a title="External website that opens in a new window "
                                 onclick="return confirmContinue();"
                                 href="http://www.nvda-project.org/" target="_blank" >
                                 <spring:message code="screen.reader.eng11" text="http://www.nvda-project.org/"/>
                                 <span class="hide">
                                    <spring:message code="screen.reader.eng12" text="External website that opens in a
                                       new window"/>
                                 </span>
                              </a>
                           </td>
                           <td>
                              <spring:message code="screen.reader.eng9" text="Free"/>
                           </td>
                        </tr>
                        <tr>
                           <td>
                              <spring:message code="screen.reader.eng13" text="System Access To Go"/>
                           </td>
                           <td>
                              <a title="External website that opens in a new window "
                                 onclick="return confirmContinue();"
                                 href="http://www.satogo.com/" target="_blank" >
                                 <spring:message code="screen.reader.eng14" text="http://www.satogo.com/"/>
                                 <span class="hide">
                                    <spring:message code="screen.reader.eng12" text="External website that opens in a
                                       new window"/>
                                 </span>
                              </a>
                           </td>
                           <td>
                              <spring:message code="screen.reader.eng9" text="Free"/>
                           </td>
                        </tr>
                        <tr>
                           <td>
                              <spring:message code="screen.reader.eng15" text="Thunder"/>
                           </td>
                           <td>
                              <a title="External website that opens in a new window"
                                 onclick="return confirmContinue();"
                                 href="http://www.screenreader.net/index.php" target="_blank"
                                 >
                                 <spring:message code="screen.reader.eng16" text="http://www.screenreader.net/index.php"/>
                                 <span
                                    class="hide">
                                    <spring:message code="screen.reader.eng12" text="External website that opens in a new
                                       window"/>
                                 </span>
                              </a>
                           </td>
                           <td>
                              <spring:message code="screen.reader.eng9" text="Free"/>
                           </td>
                        </tr>
                        <tr>
                           <td>
                              <spring:message code="screen.reader.eng17" text="WebAnywhere"/>
                           </td>
                           <td>
                              <a title="External website that opens in a new window"
                                 onclick="return confirmContinue();"
                                 href="http://webanywhere.cs.washington.edu/wa.php"
                                 target="_blank" >
                                 <spring:message code="screen.reader.eng18" text="http://webanywhere.cs.washington.edu/wa.php"/>
                                 <span class="hide">
                                    <spring:message code="screen.reader.eng12" text="External website that opens in a
                                       new window"/>
                                 </span>
                              </a>
                           </td>
                           <td>
                              <spring:message code="screen.reader.eng9" text="Free"/>
                           </td>
                        </tr>
                        <tr>
                           <td>
                              <spring:message code="screen.reader.eng19" text="Hal"/>
                           </td>
                           <td>
                              <a title="External website that opens in a new window"
                                 onclick="return confirmContinue();"
                                 href="http://www.yourdolphin.co.uk/productdetail.asp?id=5"
                                 target="_blank" >
                                 <spring:message code="screen.reader.eng20" text="http://www.yourdolphin.co.uk/productdetail.asp?id=5"/>
                                 <span class="hide">
                                    <spring:message code="screen.reader.eng12" text="External website that opens in a
                                       new window"/>
                                 </span>
                              </a>
                           </td>
                           <td>
                              <spring:message code="screen.reader.eng21" text="Commercial"/>
                           </td>
                        </tr>
                        <tr>
                           <td>
                              <spring:message code="screen.reader.eng22" text="JAWS"/>
                           </td>
                           <td>
                              <a title="External website that opens in a new window"
                                 onclick="return confirmContinue();"
                                 href="http://www.freedomscientific.com/jaws-hq.asp"
                                 target="_blank" >
                                 <spring:message code="screen.reader.eng23" text="http://www.freedomscientific.com/jaws-hq.asp"/>
                                 <span class="hide">
                                    <spring:message code="screen.reader.eng12" text="External website that opens in a
                                       new window"/>
                                 </span>
                              </a>
                           </td>
                           <td>
                              <spring:message code="screen.reader.eng21" text="Commercial"/>
                           </td>
                        </tr>
                        <tr>
                           <td>
                              <spring:message code="screen.reader.eng24" text="Supernova"/>
                           </td>
                           <td>
                              <a title="External website that opens in a new window"
                                 onclick="return confirmContinue();"
                                 href="http://www.yourdolphin.co.uk/productdetail.asp?id=1"
                                 target="_blank" >
                                 <spring:message code="screen.reader.eng25" text="http://www.yourdolphin.co.uk/productdetail.asp?id=1"/>
                                 <span class="hide">
                                    <spring:message code="screen.reader.eng12" text="External website that opens in a
                                       new window"/>
                                 </span>
                              </a>
                           </td>
                           <td>
                              <spring:message code="screen.reader.eng21" text="Commercial"/>
                           </td>
                        </tr>
                        <tr>
                           <td>
                              <spring:message code="screen.reader.eng26" text="Window-Eyes"/>
                           </td>
                           <td>
                              <a title="External website that opens in a new window"
                                 onclick="return confirmContinue();"
                                 href="http://www.gwmicro.com/Window-Eyes/" target="_blank"
                                 >
                                 <spring:message code="screen.reader.eng27" text="http://www.gwmicro.com/Window-Eyes/"/>
                                 <span
                                    class="hide">
                                    <spring:message code="screen.reader.eng12" text="External website that opens in a new
                                       window"/>
                                 </span>
                              </a>
                           </td>
                           <td>
                              <spring:message code="screen.reader.eng21" text="Commercial"/>
                           </td>
                        </tr>
                     </tbody>
                  </table>
                  </div>
               </div>
			</div>
			</c:if>
			<!-- English ends -->
			
			<!-- Hindi starts -->
			<c:if test="${userSession.languageId ne 1}">
			<h4><strong><spring:message code="screen.reader.reg" text="Screen Reader"/></strong></h4>
			<div class="widget">
				<div class="widget-content padding">
                  <p>
                     <spring:message code="screen.reader.reg1" text="The Department of Information Technology website complies
                        with World Wide Web Consortium (W3C) Web Content Accessibility
                        Guidelines (WCAG) 2.0 level A. This will enable people with visual
                        impairments access the website using Assistive Technologies, such
                        as screen readers. The information of the website is accessible
                        with different screen readers, such as JAWS."/>
                  </p>
                  <p>
                     <spring:message code="screen.reader.reg2" text="Following table lists the information about different screen
                        readers:"/>
                  </p>
                  <p>
                     <spring:message code="screen.reader.reg3" text="Information related to the various screen readers :"/>
                  </p><br/><br/>
                  <div class="table-responsive">
					<table summary="This table displays content of Screen Reader"
						class="table table-bordered table-condensed" role="presentation">
						<thead>
                        <tr>
                           <th scope="col">
                              <spring:message code="screen.reader.reg" text="Screen Reader"/>
                           </th>
                           <th scope="col">
                              <spring:message code="screen.reader.reg4" text="Website"/>
                           </th>
                           <th scope="col">
                              <spring:message code="screen.reader.reg5" text="Free / Commercial"/>
                           </th>
                        </tr>
                     </thead>
                     <tbody>
                        <tr>
                           <td>
                              <spring:message code="screen.reader.reg6" text="Screen Access For All (SAFA)"/>
                           </td>
                           <td>
                              <a title="External website that opens in a new window"
                                 onclick="return confirmContinue();"
                                 href="http://safa.sourceforge.net/" target="_blank" >
                                 <spring:message code="screen.reader.reg7" text="http://safa.sourceforge.net/"/>
                                 <span
                                    class="hide">
                                    <spring:message code="screen.reader.reg8" text="External website that opens in a new
                                       window"/>
                                 </span>
                              </a>
                           </td>
                           <td>
                              <spring:message code="screen.reader.reg9" text="Free"/>
                           </td>
                        </tr>
                        <tr>
                           <td>
                              <spring:message code="screen.reader.reg10" text="Non Visual Desktop Access (NVDA)"/>
                           </td>
                           <td>
                              <a title="External website that opens in a new window "
                                 onclick="return confirmContinue();"
                                 href="http://www.nvda-project.org/" target="_blank" >
                                 <spring:message code="screen.reader.reg11" text="http://www.nvda-project.org/"/>
                                 <span class="hide">
                                    <spring:message code="screen.reader.reg12" text="External website that opens in a
                                       new window"/>
                                 </span>
                              </a>
                           </td>
                           <td>
                              <spring:message code="screen.reader.reg9" text="Free"/>
                           </td>
                        </tr>
                        <tr>
                           <td>
                              <spring:message code="screen.reader.reg13" text="System Access To Go"/>
                           </td>
                           <td>
                              <a title="External website that opens in a new window "
                                 onclick="return confirmContinue();"
                                 href="http://www.satogo.com/" target="_blank" >
                                 <spring:message code="screen.reader.reg14" text="http://www.satogo.com/"/>
                                 <span class="hide">
                                    <spring:message code="screen.reader.reg12" text="External website that opens in a
                                       new window"/>
                                 </span>
                              </a>
                           </td>
                           <td>
                              <spring:message code="screen.reader.reg9" text="Free"/>
                           </td>
                        </tr>
                        <tr>
                           <td>
                              <spring:message code="screen.reader.reg15" text="Thunder"/>
                           </td>
                           <td>
                              <a title="External website that opens in a new window"
                                 onclick="return confirmContinue();"
                                 href="http://www.screenreader.net/index.php" target="_blank"
                                 >
                                 <spring:message code="screen.reader.reg16" text="http://www.screenreader.net/index.php"/>
                                 <span
                                    class="hide">
                                    <spring:message code="screen.reader.reg12" text="External website that opens in a new
                                       window"/>
                                 </span>
                              </a>
                           </td>
                           <td>
                              <spring:message code="screen.reader.reg9" text="Free"/>
                           </td>
                        </tr>
                        <tr>
                           <td>
                              <spring:message code="screen.reader.reg17" text="WebAnywhere"/>
                           </td>
                           <td>
                              <a title="External website that opens in a new window"
                                 onclick="return confirmContinue();"
                                 href="http://webanywhere.cs.washington.edu/wa.php"
                                 target="_blank" >
                                 <spring:message code="screen.reader.reg18" text="http://webanywhere.cs.washington.edu/wa.php"/>
                                 <span class="hide">
                                    <spring:message code="screen.reader.reg12" text="External website that opens in a
                                       new window"/>
                                 </span>
                              </a>
                           </td>
                           <td>
                              <spring:message code="screen.reader.reg9" text="Free"/>
                           </td>
                        </tr>
                        <tr>
                           <td>
                              <spring:message code="screen.reader.reg19" text="Hal"/>
                           </td>
                           <td>
                              <a title="External website that opens in a new window"
                                 onclick="return confirmContinue();"
                                 href="http://www.yourdolphin.co.uk/productdetail.asp?id=5"
                                 target="_blank" >
                                 <spring:message code="screen.reader.reg20" text="http://www.yourdolphin.co.uk/productdetail.asp?id=5"/>
                                 <span class="hide">
                                    <spring:message code="screen.reader.reg12" text="External website that opens in a
                                       new window"/>
                                 </span>
                              </a>
                           </td>
                           <td>
                              <spring:message code="screen.reader.reg21" text="Commercial"/>
                           </td>
                        </tr>
                        <tr>
                           <td>
                              <spring:message code="screen.reader.reg22" text="JAWS"/>
                           </td>
                           <td>
                              <a title="External website that opens in a new window"
                                 onclick="return confirmContinue();"
                                 href="http://www.freedomscientific.com/jaws-hq.asp"
                                 target="_blank" >
                                 <spring:message code="screen.reader.reg23" text="http://www.freedomscientific.com/jaws-hq.asp"/>
                                 <span class="hide">
                                    <spring:message code="screen.reader.reg12" text="External website that opens in a
                                       new window"/>
                                 </span>
                              </a>
                           </td>
                           <td>
                              <spring:message code="screen.reader.reg21" text="Commercial"/>
                           </td>
                        </tr>
                        <tr>
                           <td>
                              <spring:message code="screen.reader.reg24" text="Supernova"/>
                           </td>
                           <td>
                              <a title="External website that opens in a new window"
                                 onclick="return confirmContinue();"
                                 href="http://www.yourdolphin.co.uk/productdetail.asp?id=1"
                                 target="_blank" >
                                 <spring:message code="screen.reader.reg25" text="http://www.yourdolphin.co.uk/productdetail.asp?id=1"/>
                                 <span class="hide">
                                    <spring:message code="screen.reader.reg12" text="External website that opens in a
                                       new window"/>
                                 </span>
                              </a>
                           </td>
                           <td>
                              <spring:message code="screen.reader.reg21" text="Commercial"/>
                           </td>
                        </tr>
                        <tr>
                           <td>
                              <spring:message code="screen.reader.reg26" text="Window-Eyes"/>
                           </td>
                           <td>
                              <a title="External website that opens in a new window"
                                 onclick="return confirmContinue();"
                                 href="http://www.gwmicro.com/Window-Eyes/" target="_blank"
                                 >
                                 <spring:message code="screen.reader.reg27" text="http://www.gwmicro.com/Window-Eyes/"/>
                                 <span
                                    class="hide">
                                    <spring:message code="screen.reader.reg12" text="External website that opens in a new
                                       window"/>
                                 </span>
                              </a>
                           </td>
                           <td>
                              <spring:message code="screen.reader.reg21" text="Commercial"/>
                           </td>
                        </tr>
                     </tbody>
                  </table>
                  </div>
               </div>
			</div>
			</c:if>
			<!-- Hindi ends -->
		</div>
	</div>
	</div>
	</div>
</c:if>
</html>
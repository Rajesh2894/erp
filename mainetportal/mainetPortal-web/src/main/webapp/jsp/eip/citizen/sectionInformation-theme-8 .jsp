<%@ taglib tagdir="/WEB-INF/tags" prefix="apptags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility"/>
<style>.card-header2{display:none;}#date_filter span{line-height:31px}</style>
<script>
   $(document).ready(
   		function() {
   			if ($('div').find('video')){
   					$('.content').removeClass('slideInDown');
   				}
   			
   			$("table.gridtable tr th").filter(function() {
   				if ($.trim($(this).text()) == "{mel") {
   					$(this).addClass('fontfix');
   				}
   			});
   			$('table.gridtable tr th[class]').each(
   					function(idx) {
   						$(
   								'table.gridtable tr td:nth-child('
   										+ ($(this).index() + 1) + ')')
   								.addClass('arial')
   					})
   
   			$("table.gridtable tr th").filter(function() {
   				if ($.trim($(this).text()) == "{mel") {
   					$(this).addClass('fontfix');
   				}
   			});
   			$('table.gridtable tr th[class]').each(
   					function(idx) {
   						$(
   								'table.gridtable tr td:nth-child('
   										+ ($(this).index() + 1) + ')')
   								.addClass('arial')
   					})
   
   			$("table.gridtable tr th").filter(function() {
   				if ($.trim($(this).text()) == "{-mel") {
   					$(this).addClass('fontfix');
   				}
   			});
   			$('table.gridtable tr th[class]').each(
   					function(idx) {
   						$(
   								'table.gridtable tr td:nth-child('
   										+ ($(this).index() + 1) + ')')
   								.addClass('arial')
   					})
   
   			$("table.gridtable tr th").filter(function() {
   				if ($.trim($(this).text()) == "{melAayDI") {
   					$(this).addClass('fontfix');
   				}
   			});
   			$('table.gridtable tr th[class]').each(
   					function(idx) {
   						$(
   								'table.gridtable tr td:nth-child('
   										+ ($(this).index() + 1) + ')')
   								.addClass('arial')
   					})
   
   			$("table.gridtable tr th").filter(function() {
   				if ($.trim($(this).text()) == "{meLa AayDI") {
   					$(this).addClass('fontfix');
   				}
   			});
   			$('table.gridtable tr th[class]').each(
   					function(idx) {
   						$(
   								'table.gridtable tr td:nth-child('
   										+ ($(this).index() + 1) + ')')
   								.addClass('arial')
   					})
   
   			$("table.gridtable tr th").filter(function() {
   				if ($.trim($(this).text()) == "{-mel AayDI") {
   					$(this).addClass('fontfix');
   				}
   			});
   			$('table.gridtable tr th[class]').each(
   					function(idx) {
   						$(
   								'table.gridtable tr td:nth-child('
   										+ ($(this).index() + 1) + ')')
   								.addClass('arial')
   					})
   
   			$("table.gridtable tr th").filter(function() {
   				if ($.trim($(this).text()) == "jNmidvs") {
   					$(this).addClass('fontfix');
   				}
   			});
   			$('table.gridtable tr th[class]').each(
   					function(idx) {
   						$(
   								'table.gridtable tr td:nth-child('
   										+ ($(this).index() + 1) + ')')
   								.addClass('arial')
   					})
   
   		});
   
   var childDivName = '.child-popup-dialog';
   
   function showImage(image) {
   	var message = ' ';
   
   	message += '<div >';
   
   	message += '<img alt="imageclose" src=css/images/imageclose.ico  + " style=\'position:absolute;margin-top:0;margin-left:97%;z-index:999999999;\' onclick=\'dispose()\'+ height=\'20\' >';
   	message += '<img alt="Image" src=' + image
   			+ " style='margin-bottom:-2px;'   width=\'100%'";
   	message += '>';
   	message += '</div>';
   
   	$(childDivName).html(message);
   	$(childDivName).show();
   
   	showImageBox(childDivName);
   }
   
   function showImageBox(childDialog) {
   	var options = {
   		top : 100,
   		overlay : 0.5
   	};
   
   	var overlay = $("<div id='"+modalDivName+"'></div>");
   	$("body").append(overlay);
   
   	$('#' + modalDivName).css({
   		"display" : "block",
   		opacity : 0.5
   	});
   
   	$('#' + modalDivName).fadeTo(200, options.overlay);
   
   	var modal_width = $('.dialog').outerWidth();
   	$(childDialog).css({
   		"display" : "block",
   		"position" : "fixed",
   		"background-size" : "cover",
   		"opacity" : 10,
   		"z-index" : 110000,
   		"left" : 50 + "%",
   		"margin-left" : -(modal_width / 2) + "px",
   		"top" : "10%",
   		"width" : "50%",
   		"height" : "50%"
   	});
   
   	$(childDialog).fadeTo(200, 2);
   
   	$('#' + modalDivName).show();
   
   	return false;
   }
   
   function dispose() {
   
   	$('.dialog').html('');
   	$('.dialog').hide();
   	disposeModalBox();
   }
   function checklocation(element) {
   	var id = $(element).attr('id');
   
   	$("#idformap").val(id);
   
   	var postdata = 'id=' + id;
   	var json = __doAjaxRequest('SectionInformation.html?id', 'POST',
   			postdata, false, 'json');
   
   }
</script>
<ol class="breadcrumb" id="CitizenService">
   <li>
      <a href="CitizenHome.html">
         <spring:message code="menu.home" />
      </a>
   </li>
   <c:if test="${command.subLinkParentLabel ne null && fn:trim(command.subLinkParentLabel) ne 'Smart City'}">
			<li>${command.subLinkParentLabel}</li>
   </c:if>
   <c:if test="${not empty command.entity.subLinkMaster}">
     
      <c:set var = "breadCrumList" value ="${command.levelOrderMenu(command.entity.subLinkMaster)}"/>
       <c:forEach items="${breadCrumList}" var="li" varStatus="lk">
        <li>
        ${li}
        </li>
       </c:forEach>      
   </c:if>
   <li class="active">${command.subLinkLabel}</li>
</ol>
<div class="content animated">
   <div class="widget">
      <div class="widget-header">
         
            <c:if test="${not empty command.subLinkLabel}">
            	<h2>${command.subLinkLabel}</h2>
             </c:if>
              
      </div>
      <c:set var="lookUpList" value="${command.sectionInformation}" />
      <c:if test="${fn:length(lookUpList) == 0}">
         <div class="widget-content padding text-center">
            <img src="images/page-not-found.jpg" alt="Page not found" class="img-responsive text-center" style="margin:auto;">
         </div>
      </c:if>
      <div class="widget-content padding">
     
         <c:forEach items="${command.entity.sectiontypevalue}" var="list1"
            varStatus="LKlist1">
            <c:if test="${list1.sectionType eq command.getLookUpId('MAP')}">
               <c:forEach items="${lookUpList}" var="lookUps" varStatus="lk">
                  <div class="section">
                     <c:forEach items="${lookUps}" var="lookUp">
                        <c:if
                           test="${lookUp.lookUpType==MainetConstants.PROFILE_IMG && lookUp.lookUpExtraLongOne == list1.sectionType}">
                           <c:if test="${not empty lookUp.lookUpDesc}">
                              <div align="center">
                                 <%-- <img src=".cache/${lookUp.lookUpDesc}" alt=""
                                    usemap="#planetmap" class="img_style" /> --%>
                                    <c:set var="imgs"
                                       value="${fn:split(lookUp.lookUpDesc, ',')}" />
                                    <c:forEach items="${imgs}" var="img"
                                          varStatus="status">
                                    <apptags:filedownload filename="${img}"
                                                filePath="${img}"
                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload>
                                    </c:forEach>
                                 <map name="planetmap">
                                    <c:forEach items="${command.subLinkMasterList}" var="list1">
                                       <form:hidden path="command.idformap" id="idformap"/>
                                       <c:forEach items="${userSession.getMapList()}" var="mapCord" varStatus="status">
                                          <area shape="poly" coords="
                                          <spring:message code="${mapCord.key}" text="0,0,0,0"/>
                                          " alt="Sun" title='WARD ${status.count}' href="javascript:void(0);" onmouseup="checklocation(this)" id="
                                          <spring:message code="${mapCord.value}" text="WARD0"/>
                                          " onclick="$('#frm${list1.id}').submit();"> 
                                       </c:forEach>
                                    </c:forEach>
                                 </map>
                              </div>
                           </c:if>
                        </c:if>
                     </c:forEach>
                  </div>
               </c:forEach>    
            </c:if>
            <c:if test="${list1.sectionType eq command.getLookUpId('TABLE')}">
            
               <c:if test="${list1.fieldType eq 8}">
                  <c:forEach items="${lookUpList}" var="lookUps" varStatus="lk">
                     <c:forEach items="${lookUps}" var="lookUp">
                        <c:if test="${lookUp.lookUpType eq 8}">
                           ${lookUp.lookUpDesc}
                        </c:if>
                     </c:forEach>
                  </c:forEach>
               </c:if>
               <c:if test="${list1.fieldType ne 8}">
                  <br>
                  <c:if test="${not empty lookUpList}">
                  <c:set var="count" value="0" scope="page"></c:set>          
                  <div class="table-responsives">                  	         	 					                  	
                     <table class="table table-striped table-bordered display -nowrap" id="data">
                     <c:if test="${command.showOnlyHeader eq true}">
								<c:forEach items="${lookUpList}" var="lookUps" varStatus="lk">
								 <c:if test="${lk.index eq 0}">
									<thead class="tg_thdwn">
										<tr>
										<th width="5%"><spring:message code="help.doc.slno" text="Sr.no."></spring:message></th>										    
											<c:forEach items="${lookUps}" var="lookUp"> 
											 	<c:choose>
											 	<c:when test="${lookUp.lookUpDesc eq 'Date'}">
													<c:choose>
													<c:when test="${command.subLinkLabel eq 'Tenders EOI'}">
														<th class="f_tdr">${lookUp.lookUpDesc}</th>
													</c:when>
													<c:when test="${command.subLinkLabel eq 'Letters'}">
														<th class="f_ltr">${lookUp.lookUpDesc}</th>
													</c:when>
													<c:otherwise> 
														<th>${lookUp.lookUpDesc}</th>
													</c:otherwise>
													</c:choose>
											 	</c:when>
											 	<c:otherwise> 
													<th>${lookUp.lookUpDesc}</th>
												</c:otherwise>
												</c:choose>												
											</c:forEach>
										</tr>
										</thead>
									</c:if>
									</c:forEach>
								</c:if>
                     <c:if test="${command.showOnlyHeader eq false}">
                     
                     <div id="date_filter" class="form-group" style="display:none;margin-bottom:15px!important;">
  								  <span id="date-label-from" class="date-label col-sm-2 col-sm-offset-2 text-right">From Date: </span><div class="col-sm-2"><input class="date_range_filter date form-control" type="text" id="min" autocomplete="off" placeholder="Enter from date"/></div>
    							<span id="date-label-to" class="date-label col-sm-1 text-right">To Date:</span><div class="col-sm-2"><input class="date_range_filter date form-control" type="text" id="max" autocomplete="off" placeholder="Enter to date"/></div>
					<div class="clearfix"></div>
					</div>
                        <c:forEach items="${lookUpList}" var="lookUps" varStatus="lk">
                           <c:if test="${lk.index eq 0}">
                              <thead class="tg_thdwn">
                                 <tr>
                                 <th width="5%"><spring:message code="help.doc.slno" text="Sr.no."></spring:message></th>
                                    <c:forEach items="${lookUps}" var="lookUp">
                                       <c:if test="${not empty lookUp.lookUpCode  && lookUp.lookUpExtraLongOne == list1.sectionType && lookUp.lookUpType ne MainetConstants.TEXT_AREA_HTML}">
                                             <c:choose>
                                           	 <c:when test="${lookUp.lookUpCode eq 'Date'}">
												 <c:choose>
												 <c:when test="${command.subLinkLabel eq 'Tenders EOI'}">
												 	<th class="f_tdr">${lookUp.lookUpCode}</th>
												 </c:when>
												 <c:when test="${command.subLinkLabel eq 'Letters'}">
												 	<th class="f_ltr">${lookUp.lookUpCode}</th>
												 </c:when>
												 <c:otherwise> 
												 	<th>${lookUp.lookUpCode}</th>
												 </c:otherwise>
												 </c:choose>
										 	 </c:when>											 
											 <c:otherwise> 
												<th>${lookUp.lookUpCode}</th>
											 </c:otherwise>
											 </c:choose>                                          
                                       </c:if>
                                    </c:forEach>
                                 </tr>
                              </thead>
                           </c:if>
                           <tr>
										<%--  <td class="text-center">${count + 1}</td>
                                              <c:set var="count" value="${count + 1}" scope="page"></c:set> --%>
									
                              <c:forEach items="${lookUps}" var="lookUp">
													<c:if test="${lookUp.lookUpType eq 'IS_HIGHLIGHTED' }">
														<c:choose>
															<c:when test="${ lookUp.lookUpCode eq 'Y'}">
																<td class="text-center">${count + 1}<img alt="flashing-new"
																	src="./assets/img/flashing-new.png" class="flash-new"
																	style="position: absolute;"></td>
															</c:when>
															<c:otherwise>
																<td class="text-center">${count + 1}</td>
															</c:otherwise>
														</c:choose>
													</c:if>


													<c:if  test="${lookUp.lookUpType eq null}">
                                 </c:if>
                                 <c:if
                                    test="${lookUp.lookUpType==MainetConstants.TEXT_AREA_HTML && lookUp.lookUpExtraLongOne == list1.sectionType }">
                                    <td>${lookUp.lookUpDesc}</td>
                                 </c:if>
                                
                                 <c:if
                                    test="${lookUp.lookUpType==MainetConstants.LINK_FIELD && lookUp.lookUpExtraLongOne == list1.sectionType }">
                                   <%--  <td><a href="https://www.${lookUp.lookUpDesc}" target="_blank" class="external">${lookUp.lookUpDesc}</a></td> --%>
                                       <td><a href="${lookUp.lookUpDesc}" target="_blank" class="external">${lookUp.lookUpDesc}</a></td>
                                 </c:if>								
								  
								 <c:if
									test="${lookUp.lookUpType==MainetConstants.TEXT_FIELD && lookUp.lookUpExtraLongOne == list1.sectionType }">
									<td>${lookUp.lookUpDesc}</td>
								 </c:if>								

                                 <c:if
                                    test="${lookUp.lookUpType==MainetConstants.TEXT_AREA && lookUp.lookUpExtraLongOne == list1.sectionType}">
                                    <td>${lookUp.lookUpDesc}</td>
                                 </c:if>
                                 <c:if
                                    test="${lookUp.lookUpType==MainetConstants.DATE_PICKER && lookUp.lookUpExtraLongOne == list1.sectionType}">
                                    <td>${lookUp.lookUpDesc}</td>
                                 </c:if>
                                 <c:if
	                                  	test="${lookUp.lookUpType==MainetConstants.DROP_DOWN_BOX && lookUp.lookUpExtraLongOne == list1.sectionType}">
	                                    <td>${lookUp.lookUpDesc}</td>
	                                 </c:if>
                                 <c:if
                                    test="${lookUp.lookUpType==MainetConstants.PROFILE_IMG && lookUp.lookUpExtraLongOne == list1.sectionType}">
                                    <%-- <c:choose>
                                       <c:when test="${not empty lookUp.lookUpDesc}">
                                       	<td><c:set var="imgs"
                                       			value="${fn:split(lookUp.lookUpDesc, '|')}" /> <c:forEach
                                       			items="${imgs}" var="img">
                                       			
                                       			<a class="fancybox" href="${img}"
                                       				data-fancybox-group="images"> <img src="${img}"
                                       				alt="${img}" class="img-thumbnail" width="300px"></a>
                                       		</c:forEach></td>
                                       </c:when>
                                       <c:otherwise>
                                       	<td><div align="center">
                                       			<spring:message code="eip.quicklinkNoImage" />
                                       		</div></td>
                                       </c:otherwise>
                                       </c:choose> --%>
                                    <c:set var="imgs"
                                       value="${fn:split(lookUp.lookUpDesc, ',')}" />
                                    <td>
                                       <c:forEach items="${imgs}" var="img"
                                          varStatus="status">
                                          <c:set var="link"
                                             value="${stringUtility.getStringAfterChar('/',img)}" />
                                          <c:set var="path"
                                             value="${stringUtility.getStringBeforeChar('/',img)}" />
                                          <c:if test="${not empty lookUp.lookUpDesc}">
                                             <apptags:filedownload filename="${link}"
                                                filePath="${lookUp.lookUpDesc}"
                                                actionUrl="SectionInformation.html?Download" showFancyBox="true"></apptags:filedownload>
                                          </c:if>
                                          <c:if test="${empty lookUp.lookUpDesc}">
                                          </c:if>
                                       </c:forEach>
                                    </td>
                                 </c:if>
                                 <c:if
                                    test="${lookUp.lookUpType==MainetConstants.VIDEO && lookUp.lookUpExtraLongOne == list1.sectionType}">
                                    <c:set var="videos"
                                       value="${fn:split(lookUp.lookUpDesc, ',')}" />
                                    <td>
                                       <c:forEach items="${videos}" var="video"
                                          varStatus="status">
                                          <c:set var="link"
                                             value="${stringUtility.getStringAfterChar('/',video)}" />
                                          <c:set var="path"
                                             value="${stringUtility.getStringBeforeChar('/',video)}" />
                                          <c:if test="${not empty lookUp.lookUpDesc}">
                                             <apptags:filedownload filename="${link}"
                                                filePath="${path}"
                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload>
                                          </c:if>
                                          <c:if test="${empty lookUp.lookUpDesc}">
                                          </c:if>
                                       </c:forEach>
                                    </td>
                                    <%-- 	<c:choose>
                                       <c:when test="${not empty lookUp.lookUpDesc}">
                                       	<td><c:set var="videos"
                                       			value="${fn:split(lookUp.lookUpDesc, '|')}" /> <c:forEach
                                       			items="${videos}" var="video">
                                       			<video style="width:100%" controls>
                                       				<source src="${video}"
                                       					type='video/mp4; codecs="avc1.42E01E, mp4a.40.2"'>
                                       			</video>
                                       		</c:forEach></td>
                                       </c:when>
                                       <c:otherwise>
                                       	<td><div align="center">
                                       			<spring:message code="" text="Video Not found" />
                                       		</div></td>
                                       </c:otherwise>
                                       </c:choose> --%>
                                 </c:if>
                                 <c:if
                                    test="${lookUp.lookUpType==MainetConstants.ATTACHMENT_FIELD && lookUp.lookUpExtraLongOne == list1.sectionType}">
                                    <c:set var="links"
                                       value="${fn:split(lookUp.lookUpDesc, ',')}" />
                                    <td class="text-center">
                                       <c:forEach items="${links}" var="download"
                                          varStatus="status">
                                          <div class="uploaded-file">
	                                          <c:set var="link"
	                                             value="${stringUtility.getStringAfterChar('/',download)}" />
	                                          <c:set var="path"
	                                             value="${stringUtility.getStringBeforeChar('/',download)}" />
	                                          <c:if test="${not empty lookUp.lookUpDesc}">
	                                             <apptags:filedownload filename="${link}"
	                                                filePath="${path}" showIcon="true" docImage="true"
	                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload>
	                                          </c:if>
	                                          <c:if test="${empty lookUp.lookUpDesc}">
	                                          </c:if>
	                                          <span class="uploaded-file-name">${link}</span>
                                          </div>
                                       </c:forEach>
                                      
                                    </td>
                                 </c:if>
                              </c:forEach>
                               <c:set var="count" value="${count + 1}" scope="page"></c:set>
                           </tr>
                        </c:forEach>
                     </c:if>
                     </table>                     
                     </div>
                  </c:if>
               </c:if>
               
            </c:if>          
              
            <c:if test="${list1.sectionType  eq command.getLookUpId('SEC')}">
               <c:if test="${list1.fieldType eq 8}">
                  <c:forEach items="${lookUpList}" var="lookUps" varStatus="lk">
                     <c:forEach items="${lookUps}" var="lookUp">
                        <c:if test="${lookUp.lookUpType eq 8}">
                           ${lookUp.lookUpDesc}
                        </c:if>
                     </c:forEach>
                  </c:forEach>
               </c:if>
               <c:if test="${list1.fieldType ne 8}">
                  <div class="table-responsives">
                     <table class="table table-striped table-bordered" id="data">
                        <c:forEach items="${lookUpList}" var="lookUps" varStatus="lk">
                           <c:if test="${lk.index eq 0}">
                           	  <thead>	
                              <tr>
                                 <c:forEach items="${lookUps}" var="lookUp">
                                    <c:if
                                       test="${not empty lookUp.lookUpCode && lookUp.lookUpExtraLongOne == list1.sectionType && lookUp.lookUpType ne MainetConstants.TEXT_AREA_HTML}">
                                       <th>${lookUp.lookUpCode}</th>
                                    </c:if>
                                 </c:forEach>
                              </tr>
                              </thead>
                           </c:if>
                           <tr>
                              <c:forEach items="${lookUps}" var="lookUp" varStatus="lk">
                                 <c:if
                                    test="${lookUp.lookUpType==MainetConstants.TEXT_AREA_HTML  && lookUp.lookUpExtraLongOne == list1.sectionType}">
                                    <c:if test="${not empty lookUp.lookUpDesc}">
                                       <td>
                                          <div class="text">${lookUp.lookUpDesc}</div>
                                       </td>
                                    </c:if>
                                    <c:if test="${empty lookUp.lookUpDesc}">
                                       <td>
                                          <div class="text">${lookUp.lookUpDesc}</div>
                                       </td>
                                    </c:if>
                                 </c:if>
                                 <c:if
                                    test="${lookUp.lookUpType==MainetConstants.TEXT_FIELD  && lookUp.lookUpExtraLongOne == list1.sectionType}">
                                    <c:if test="${not empty lookUp.lookUpDesc}">
                                       <td>
                                          <div class="text">${lookUp.lookUpDesc}</div>
                                       </td>
                                    </c:if>
                                    <c:if test="${empty lookUp.lookUpDesc}">
                                       <td>
                                          <div class="text">${lookUp.lookUpDesc}</div>
                                       </td>
                                    </c:if>
                                 </c:if>
                                 <c:if
	                                    test="${lookUp.lookUpType==MainetConstants.DROP_DOWN_BOX && lookUp.lookUpExtraLongOne == list1.sectionType}">
	                                    <div class="text">${lookUp.lookUpDesc}</div>
	                                 </c:if>
                                 
                                 <c:if
                                    test="${lookUp.lookUpType==MainetConstants.TEXT_AREA && lookUp.lookUpExtraLongOne == list1.sectionType}">
                                    <c:if test="${not empty lookUp.lookUpDesc}">
                                       <td>
                                          <div class="description">${lookUp.lookUpDesc}</div>
                                       </td>
                                    </c:if>
                                    <c:if test="${empty lookUp.lookUpDesc}">
                                       <td>
                                          <div class="description">${lookUp.lookUpDesc}</div>
                                       </td>
                                    </c:if>
                                 </c:if>
                                 <c:if
                                    test="${lookUp.lookUpType==MainetConstants.DATE_PICKER && lookUp.lookUpExtraLongOne == list1.sectionType }">
                                    <c:if test="${not empty lookUp.lookUpDesc}">
                                       <td>
                                          <div class="text arial">${lookUp.lookUpDesc}</div>
                                       </td>
                                    </c:if>
                                    <c:if test="${empty lookUp.lookUpDesc}">
                                       <td>
                                          <div class="text">${lookUp.lookUpDesc}</div>
                                       </td>
                                    </c:if>
                                 </c:if>
                                 <c:if
                                    test="${lookUp.lookUpType==MainetConstants.PROFILE_IMG && lookUp.lookUpExtraLongOne == list1.sectionType }">
                                    <%-- <c:choose>
                                       <c:when test="${not empty lookUp.lookUpDesc}">
                                       	<td><c:set var="imgs"
                                       			value="${fn:split(lookUp.lookUpDesc, '|')}" />
                                       			<c:forEach
                                       			items="${imgs}" var="img">
                                       			
                                       				<a class="fancybox" href="./${img}"
                                       					data-fancybox-group="images"> <img src="./${img}"
                                       					alt="${img}" class="img-thumbnail" width="300px"></a>
                                       	
                                       		</c:forEach></td>
                                       </c:when>
                                       <c:otherwise>
                                       	<td><div align="center">
                                       			<spring:message code="eip.quicklinkNoImage" />
                                       		</div></td>
                                       </c:otherwise>
                                       </c:choose> --%>
                                    <c:set var="imgs"
                                       value="${fn:split(lookUp.lookUpDesc, ',')}" />
                                    <td>
                                       <c:forEach items="${imgs}" var="img"
                                          varStatus="status">
                                          <c:set var="link"
                                             value="${stringUtility.getStringAfterChar('/',img)}" />
                                          <c:set var="path"
                                             value="${stringUtility.getStringBeforeChar('/',img)}" />
                                           
                                          <c:if test="${not empty lookUp.lookUpDesc}">
                                             <apptags:filedownload filename="${link}"
                                                filePath="${lookUp.lookUpDesc}"
                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload>
                                          </c:if>
                                          <c:if test="${empty lookUp.lookUpDesc}">
                                          </c:if>
                                       </c:forEach>
                                    </td>
                                 </c:if>
                                 <c:if test="${lookUp.lookUpType==MainetConstants.VIDEO && lookUp.lookUpExtraLongOne == list1.sectionType}">
                                    <%-- <c:choose>
                                       <c:when test="${not empty lookUp.lookUpDesc}">
                                       	<td><c:set var="videos"
                                       			value="${fn:split(lookUp.lookUpDesc, '|')}" /> <c:forEach
                                       			items="${videos}" var="video">
                                       			<video controls style="width:300%"> 
                                       				<source src="${video}"
                                       					type='video/mp4; codecs="avc1.42E01E, mp4a.40.2"'>
                                       			</video>
                                       		</c:forEach></td>
                                       </c:when>
                                       <c:otherwise>
                                       	<td><div align="center">
                                       			<spring:message code="" text="Video Not found" />
                                       		</div></td>
                                       </c:otherwise>
                                       </c:choose> --%>
                                    <c:set var="videos"
                                       value="${fn:split(lookUp.lookUpDesc, ',')}" />
                                    <td>
                                       <c:forEach items="${videos}" var="video"
                                          varStatus="status">
                                          <c:set var="link"
                                             value="${stringUtility.getStringAfterChar('/',video)}" />
                                          <c:set var="path"
                                             value="${stringUtility.getStringBeforeChar('/',video)}" />
                                          <c:if test="${not empty lookUp.lookUpDesc}">
                                             <apptags:filedownload filename="${link}"
                                                filePath="${path}"
                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload>
                                          </c:if>
                                          <c:if test="${empty lookUp.lookUpDesc}">
                                          </c:if>
                                       </c:forEach>
                                    </td>
                                 </c:if>
                                 <c:if
                                    test="${lookUp.lookUpType==MainetConstants.ATTACHMENT_FIELD && lookUp.lookUpExtraLongOne == list1.sectionType}">
                                    <td>
                                       <c:set var="links"
                                          value="${fn:split(lookUp.lookUpDesc,',')}" />
                                       <c:forEach
                                          items="${links}" var="download" varStatus="status">
                                          <c:set var="link"
                                             value="${stringUtility.getStringAfterChar('/',download)}" />
                                          <c:set var="doctype"
                                             value="${stringUtility.getDocType(link)}" />
                                          <c:set var="fileNames"
                                             value="${stringUtility.getFileName(link)}" />
                                          <c:set var="path"
                                             value="${stringUtility.getStringBeforeChar('/',download)}" />
                                          <c:if test="${not empty lookUp.lookUpDesc}">
                                             <apptags:filedownload filename="${link}"
                                                filePath="${path}"
                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload>
                                          </c:if>
                                          <c:if test="${empty lookUp.lookUpDesc}">
                                          </c:if>
                                       </c:forEach>
                                    </td>
                                 </c:if>
                              </c:forEach>
                           </tr>
                        </c:forEach>
                     </table>
                  </div>
               </c:if>
            </c:if>
            <c:if test="${list1.sectionType eq command.getLookUpId('LBL')}">
               <c:forEach items="${lookUpList}" var="lookUps" varStatus="lk">
                  <div class="section" style="position: relative;">
                     <c:forEach items="${lookUps}" var="lookUp">
                        <c:if
                           test="${lookUp.lookUpType==MainetConstants.TEXT_FIELD && lookUp.lookUpExtraLongOne == list1.sectionType}">
                           <c:if test="${not empty lookUp.lookUpDesc}">
                              <div class="text">
                                 <label for="">${lookUp.lookUpCode} :</label>
                                 ${lookUp.lookUpDesc}
                              </div>
                           </c:if>
                        </c:if>
                        <c:if
                           test="${lookUp.lookUpType==MainetConstants.TEXT_AREA && lookUp.lookUpExtraLongOne == list1.sectionType}">
                           <c:if test="${not empty lookUp.lookUpDesc}">
                              <div class="description">
                                 <label for="">${lookUp.lookUpCode} :</label><br />${lookUp.lookUpDesc}
                              </div>
                           </c:if>
                        </c:if>
                         <c:if
	                                    test="${lookUp.lookUpType==MainetConstants.DROP_DOWN_BOX && lookUp.lookUpExtraLongOne == list1.sectionType}">
	                                    <div class="description">
                                 <label for="">${lookUp.lookUpCode} :</label><br />${lookUp.lookUpDesc}
                              </div>
	                                 </c:if>
                        <c:if
                           test="${lookUp.lookUpType==MainetConstants.DATE_PICKER && lookUp.lookUpExtraLongOne == list1.sectionType}">
                           <c:if test="${not empty lookUp.lookUpDesc}">
                              <div class="text arial">
                                 <label for="">${lookUp.lookUpCode} :</label>${lookUp.lookUpDesc}
                              </div>
                           </c:if>
                        </c:if>
                        <c:if
                           test="${lookUp.lookUpType==MainetConstants.PROFILE_IMG && lookUp.lookUpExtraLongOne == list1.sectionType}">
                           <c:if test="${not empty lookUp.lookUpDesc}">
                              <div class="profile_img move_up"
                                 style="position: absolute; top: 0px; right: 0px;">
                                 <img src="./${lookUp.lookUpDesc}" alt="${lookUp.lookUpDesc}"
                                    title="${lookUp.lookUpDesc}" />
                              </div>
                           </c:if>
                        </c:if>
                        <c:if
                           test="${lookUp.lookUpType==MainetConstants.ATTACHMENT_FIELD && lookUp.lookUpExtraLongOne == list1.sectionType}">
                           <c:set var="links" value="${fn:split(lookUp.lookUpDesc,',')}" />
                           <c:forEach items="${links}" var="download" varStatus="status">
                              <c:set var="link"
                                 value="${stringUtility.getStringAfterChar('/',download)}" />
                              <c:set var="path"
                                 value="${stringUtility.getStringBeforeChar('/',download)}" />
                              <apptags:filedownload filename="${link}" filePath="${path}"
                                 actionUrl="SectionInformation.html?Download"></apptags:filedownload>
                           </c:forEach>
                        </c:if>
                     </c:forEach>
                  </div>
               </c:forEach>
            </c:if>
            <c:if test="${list1.sectionType eq command.getLookUpId('PHOTO')}">
               <div class="section gallery photogallery">
                  <ul class="pagination2 photos">
                     <c:forEach items="${lookUpList}" var="lookUps" varStatus="lk">
                        <c:forEach items="${lookUps}" var="lookUp">
                           <c:if test="${lookUp.lookUpType==MainetConstants.PROFILE_IMG && lookUp.lookUpExtraLongOne == list1.sectionType}">
                              <%-- <c:if test="${not empty lookUp.lookUpDesc}">
                                 <c:set var="imgs" value="${fn:split(lookUp.lookUpDesc, '|')}" />
                                 <c:forEach items="${imgs}" var="img">
                                    <li>
                                       <a class="fancybox thumbnail" href="${img}"
                                          data-fancybox-group="images" title="${lookUp.otherField}">
                                          <img
                                             src="${img}" class="img-thumbnail" alt="Loading please wait" title="${lookUp.otherField}">
                                          <c:if test="${not empty lookUp.otherField}"><span class="gallery-caption">${lookUp.otherField}</span></c:if>
                                       </a>
                                    </li>
                                 </c:forEach>
                              </c:if> --%>
                               <c:set var="imgs"
                                       value="${fn:split(lookUp.lookUpDesc, ',')}" />
                                    <li>
                                       <c:forEach items="${imgs}" var="img"
                                          varStatus="status">
                                          <c:set var="link"
                                             value="${stringUtility.getStringAfterChar('/',img)}" />
                                          <c:set var="path"
                                             value="${stringUtility.getStringBeforeChar('/',img)}" />
                                          <c:if test="${not empty lookUp.lookUpDesc}">
                                             <apptags:filedownload filename="${link}"
                                                filePath="${lookUp.lookUpDesc}" showFancyBox="true"
                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload>
                                          </c:if>
                                          <c:choose>
                                          <c:when test="${not empty lookUp.otherField && (not empty lookUp.defaultVal and lookUp.defaultVal eq 'IS_HIGHLIGHTED') }">
                                         <span class="gallery-caption">${lookUp.otherField}<img alt="flashing-new"
																	src="./assets/img/flashing-new.png" class="flash-new"
																	style="position: absolute;"></span>
                                          </c:when>
                                          <c:when test="${not empty lookUp.otherField }">
                                          <span class="gallery-caption">${lookUp.otherField}</span>
                                          </c:when>
                                          <c:when test="${not empty lookUp.defaultVal and lookUp.defaultVal eq 'IS_HIGHLIGHTED' }">
                                         <span class="gallery-caption"><img alt="flashing-new"
																	src="./assets/img/flashing-new.png" class="flash-new"
																	style="position: absolute;"></span>
                                          </c:when>
                                          <c:otherwise>
                                          </c:otherwise>
                                          </c:choose>
                                        
                                          <c:if test="${empty lookUp.lookUpDesc}">
                                          </c:if>
                                        
                                       </c:forEach>
                                    </li>
                           </c:if>
                        </c:forEach>
                     </c:forEach>
                  </ul>
               </div>
            </c:if>
            <c:if
               test="${list1.sectionType eq command.getLookUpId('PORTRAIT')}">
               <c:forEach items="${lookUpList}" var="lookUps" varStatus="lk">
                  <div class="section-new" style="position: relative;">
                     <c:forEach items="${lookUps}" var="lookUp">
                        <c:if
                           test="${lookUp.lookUpType==MainetConstants.TEXT_FIELD && lookUp.lookUpExtraLongOne == list1.sectionType}">
                           <c:if test="${not empty lookUp.lookUpDesc}">
                              <div class="text">
                                 <label for="">${lookUp.lookUpCode} :</label>
                                 ${lookUp.lookUpDesc}
                              </div>
                           </c:if>
                        </c:if>
                        <c:if
                           test="${lookUp.lookUpType==MainetConstants.DROP_DOWN_BOX && lookUp.lookUpExtraLongOne == list1.sectionType}">
                           <c:if test="${not empty lookUp.lookUpDesc}">
                              <div class="text">
                                 <label for="">${lookUp.lookUpCode} :</label>
                                 ${lookUp.lookUpDesc}
                              </div>
                           </c:if>
                        </c:if>
                        <c:if
                           test="${lookUp.lookUpType==MainetConstants.PROFILE_IMG && lookUp.lookUpExtraLongOne == list1.sectionType}">
                           <%-- <c:if test="${not empty lookUp.lookUpDesc}">
                              <c:set var="imgs" value="${fn:split(lookUp.lookUpDesc, '|')}" />
                              <c:forEach items="${imgs}" var="img">
                                 <img class="" src="./${img}" alt="${img}" title="${img}" />
                              </c:forEach>
                           </c:if> --%>
                            <c:set var="imgs"
                                       value="${fn:split(lookUp.lookUpDesc, ',')}" />
                                       <!--  <ul class="pagination2 photos">
                                      <li> -->
                                       <c:forEach items="${imgs}" var="img"
                                          varStatus="status">
                                          <c:set var="link"
                                             value="${stringUtility.getStringAfterChar('/',img)}" />
                                          <c:set var="path"
                                             value="${stringUtility.getStringBeforeChar('/',img)}" />
                                          <c:if test="${not empty lookUp.lookUpDesc}">
                                             <apptags:filedownload filename="${link}"
                                                filePath="${lookUp.lookUpDesc}" showFancyBox="true"
                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload>
                                          </c:if>
                                          </c:forEach>
                                          <!-- </li>
                                          </ul> -->
                        </c:if>
                        <c:if
                           test="${lookUp.lookUpType==MainetConstants.TEXT_AREA && lookUp.lookUpExtraLongOne == list1.sectionType}">
                           <c:if test="${not empty lookUp.lookUpDesc}">
                              <label for="">${lookUp.lookUpCode} :</label> 
                              ${lookUp.lookUpDesc}
                           </c:if>
                        </c:if>
                     </c:forEach>
                  </div>
               </c:forEach>
            </c:if>
            <%-- <c:if test="${list1.sectionType eq command.getLookUpId('VD')}">
               <div class="row">
                  <c:forEach items="${lookUpList}" var="lookUps" varStatus="lk">
                     <div class="col-sm-4">
                        <c:forEach items="${lookUps}" var="lookUp">
                           <c:if test="${lookUp.lookUpType==MainetConstants.VIDEO && lookUp.lookUpExtraLongOne == list1.sectionType}">
                              <div class="video-main">
                                 <c:if test="${not empty lookUp.descLangFirst}">
                                    <video class="text-center" controls style="width:100%">
                                       <source src="./${lookUp.descLangFirst}" type='video/mp4; codecs="avc1.42E01E, mp4a.40.2"'>
                                    </video>
                                    <c:if test="${not empty lookUp.otherField}">
                                       <span class="gallery-caption">${lookUp.otherField}</span>
                                    </c:if>
                                 </c:if>
                              </div>
                           </c:if>
                        </c:forEach>
                     </div>
                  </c:forEach>
               </div>
            </c:if> --%>
            <c:if test="${list1.sectionType eq command.getLookUpId('VD')}">
            <div class="row">
             <c:forEach items="${lookUpList}" var="lookUps" varStatus="lk">
              <div class="col-sm-3">
                        <c:forEach items="${lookUps}" var="lookUp">
            <c:if test="${lookUp.lookUpType==MainetConstants.VIDEO && lookUp.lookUpExtraLongOne == list1.sectionType}">
                                    <c:set var="videos"
                                       value="${fn:split(lookUp.lookUpDesc, ',')}" />
                                       <c:forEach items="${videos}" var="video"
                                          varStatus="status">
                                          <c:set var="link"
                                             value="${stringUtility.getStringAfterChar('/',video)}" />
                                          <c:set var="path"
                                             value="${stringUtility.getStringBeforeChar('/',video)}" />
                                          <c:if test="${not empty lookUp.lookUpDesc}">
                                          
                                            
                                             <apptags:filedownload filename="${link}"
                                                filePath="${lookUp.lookUpDesc}"
                                                actionUrl="SectionInformation.html?Download"></apptags:filedownload>
                                          </c:if>
                                         
                                     <c:choose>
                                          <c:when test="${not empty lookUp.otherField && (not empty lookUp.defaultVal and lookUp.defaultVal eq 'IS_HIGHLIGHTED') }">
                                         <span class="gallery-caption">${lookUp.otherField}<img alt="flashing-new"
																	src="./assets/img/flashing-new.png" class="flash-new"
																	style="position: absolute;"></span>
                                          </c:when>
                                          <c:when test="${not empty lookUp.otherField }">
                                          <span class="gallery-caption">${lookUp.otherField}</span>
                                          </c:when>
                                          <c:when test="${not empty lookUp.defaultVal and lookUp.defaultVal eq 'IS_HIGHLIGHTED' }">
                                         <span class="gallery-caption"><img alt="flashing-new"
																	src="./assets/img/flashing-new.png" class="flash-new"
																	style="position: absolute;"></span>
                                          </c:when>
                                          <c:otherwise>
                                          </c:otherwise>
                                          </c:choose>
                                          <c:if test="${empty lookUp.lookUpDesc}">
                                          </c:if>
                                       </c:forEach>
                                 </c:if>
                                 </c:forEach>
                                 </div>
                                 </c:forEach>
                                 </div>
            </c:if>
            
            
            <c:if test="${list1.sectionType == 0}">
               <c:forEach items="${lookUps}" var="lookUp">
                  <c:if
                     test="${lookUp.lookUpType==MainetConstants.TEXT_FIELD && lookUp.lookUpExtraLongOne == list1.sectionType}">
                     <c:if test="${not empty lookUp.lookUpDesc}">
                        <div class="text">${lookUp.lookUpDesc}</div>
                     </c:if>
                  </c:if>
                  <c:if
                     test="${lookUp.lookUpType==MainetConstants.DROP_DOWN_BOX && lookUp.lookUpExtraLongOne == list1.sectionType}">
                     <c:if test="${not empty lookUp.lookUpDesc}">
                        <div class="text">${lookUp.lookUpDesc}</div>
                     </c:if>
                  </c:if>
                  <c:if
                     test="${lookUp.lookUpType==MainetConstants.TEXT_AREA && lookUp.lookUpExtraLongOne == list1.sectionType}">
                     <c:if test="${not empty lookUp.lookUpDesc}">
                        <div class="description">${lookUp.lookUpDesc}</div>
                     </c:if>
                  </c:if>
                  <c:if
                     test="${lookUp.lookUpType==MainetConstants.DATE_PICKER && lookUp.lookUpExtraLongOne == list1.sectionType}">
                     <c:if test="${not empty lookUp.lookUpDesc}">
                        <div class="text arial">${lookUp.lookUpDesc}</div>
                     </c:if>
                  </c:if>
                  <c:if
                     test="${lookUp.lookUpType==MainetConstants.PROFILE_IMG && lookUp.lookUpExtraLongOne == list1.sectionType}">
                     <c:if test="${not empty lookUp.lookUpDesc}">
                        <c:if test="${subLinkMaster.sectionType0 ne MAP }">
                           <div class="profile_img move_up">
                              <img src="./${lookUp.lookUpDesc}" alt="${lookUp.lookUpDesc}"
                                 title="${lookUp.lookUpDesc}" />
                           </div>
                        </c:if>
                        <c:if test="${subLinkMaster.sectionType0 eq MAP }">
                           <div align="center">
                              <img src="./${lookUp.lookUpDesc}" alt="${lookUp.lookUpDesc}"
                                 title="${lookUp.lookUpDesc}" />
                           </div>
                        </c:if>
                     </c:if>
                  </c:if>
                  <c:if
                     test="${lookUp.lookUpType==MainetConstants.ATTACHMENT_FIELD  && lookUp.lookUpExtraLongOne == list1.sectionType}">
                     <c:set var="links" value="${fn:split(lookUp.lookUpDesc,',')}" />
                     <c:forEach items="${links}" var="download" varStatus="status">
                        <c:set var="link"
                           value="${stringUtility.getStringAfterChar('/',download)}" />
                        <c:set var="path"
                           value="${stringUtility.getStringBeforeChar('/',download)}" />
                        <p>
                           <apptags:filedownload filename="${link}" filePath="${path}"
                              actionUrl="SectionInformation.html?Download"></apptags:filedownload>
                        <p>
                     </c:forEach>
                  </c:if>
               </c:forEach>
            </c:if>
         </c:forEach>
      </div>
   </div>
</div>
<script>
	$(window).on('load', function(){
	   var lang = ${userSession.languageId};
	   if(lang=="1"){   
	   var tender_titles = ["Tender Date","Received By Data Center On Date"];
	   var letter_titles = ["Date","Date Of Uploading Of Letter"];}else{
	   var tender_titles = ["\u0928\u093F\u0935\u093F\u0926\u093E \u0924\u093F\u0925\u093F","\u0921\u0947\u091F\u093E \u0915\u0947\u0902\u0926\u094D\u0930 \u0915\u094B \u092A\u094D\u0930\u093E\u092A\u094D\u0924 \u0924\u093F\u0925\u093F"];
	   var letter_titles = ["\u0924\u093F\u0925\u093F","\u092A\u0924\u094D\u0930 \u0905\u092A\u0932\u094B\u0921 \u0915\u0930\u0928\u0947 \u0915\u0940 \u0924\u093F\u0925\u093F"];}
	   $(".f_tdr" ).each(function( index ) {
	 	  $(this).text(tender_titles[index]);
	 	});
	   $(".f_ltr" ).each(function( index ) {
	 	  $(this).text(letter_titles[index]);
	 	});
	   $("#data_wrapper .row").first().after("<div class='row' id='btn-dt-navi'><button class='left dt-navi'><i class='fa fa-arrow-left' aria-hidden='true'></i></button><button class='right dt-navi'><i class='fa fa-arrow-right' aria-hidden='true'></i></button></div>");
	   $('button.dt-navi').on('click',function(){
		    var pwidth = $('div.dataTables_wrapper').width();	    
		    if($(this).hasClass('left')){	    	
		    	$('.dataTables_scrollBody').scrollLeft( $('.dataTables_scrollBody').scrollLeft()-200 );	        
		     } else {
		    	 $('.dataTables_scrollBody').scrollLeft($('.dataTables_scrollBody').scrollLeft()+200 );
		     };
		});
	   if($('.dataTables_scrollBody').hasScrollBar() == false){
		   $("#btn-dt-navi").hide();
	   }
	   });	 
	 
</script>
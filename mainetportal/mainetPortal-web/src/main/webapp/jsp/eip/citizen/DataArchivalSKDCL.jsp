<%@ page import="org.w3c.dom.Document"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility" />
<jsp:useBean id="test" class="com.abm.mainet.common.util.Utility" />
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script>
$(document).ready(function() {
	$("div.bhoechie-tab-menu>div.list-group>a").click(
		function(e) {
			e.preventDefault();
			$(this).siblings('a.active').removeClass("active");
			$(this).addClass("active");
			var index = $(this).index();
			$("div.bhoechie-tab>div.bhoechie-tab-content")
					.removeClass("active");
			$("div.bhoechie-tab>div.bhoechie-tab-content").eq(index).addClass("active");
			$($.fn.dataTable.tables(true)).DataTable().columns.adjust().responsive.recalc();
		});
 			
	$(".press-release").click(function(){
	    var imageId = $(this).data('id');
	    var imageList=imageId.split(",");
	    $('div#press-modal-body-div > div').remove();	
	    $('div#press-modal-body-div > img').remove();
		for(i=0;i<imageList.length;i++){
			if(i==0){
				$("<div>", {
				    'class': "item active press-release-item"
				}).appendTo('.press-modal-body');					
			}
			else{
				$("<div>", {
				    'class': "item press-release-item"
				}).appendTo('.press-modal-body');
			}

			var img = $('<img class="img-responsive" />').attr({
	            'id': 'myImage'+i,
	            'src': './'+imageList[i]
	        }).appendTo('.press-release-item');
	    }
		$('#pressreleasediv').modal('show');
   });
 			
});
</script>
<style>
.displayimagef,.displayimage{float: left;height:90px;overflow:hidden}
.displayimagef img{height: 90px;width: 100%;}
.displayimage img{height: 90px;width: 100px;margin-right: 10px;}
#collapseTwo .scrolllistbox1 .displayimage img{height: 50px;width: 80px;margin-right: 10px;}
#collapseTwo .scrolllistbox1 .displayimage {height:50px;overflow:hidden}
.dataTables_paginate .pagination .paginate_button a{
	background: #FFF;
    color: #000;
}
</style>
<ol class="breadcrumb" id="CitizenService">
   <li class="breadcrumb-item">
      <a href="CitizenHome.html">
         <i class="fa fa-home"></i> 
         <spring:message code="home"/>
      </a>
   </li>
   <li class="breadcrumb-item active">
      <spring:message code="lbl.archiveData" text="Archive Data"/>
   </li>
</ol>
<div class="container-fluid dashboard-page" id="datarch">
   <div class="col-sm-12" id="nischay">
   		<h2>
         <spring:message code="lbl.archiveData" text="Archive Data"/>
      </h2>   
      <div class="widget">
         <div class="widget-content padding">
            <div class="col-sm-12">
               <form action="" method="get" class="form-horizontal" novalidate>
                  <div class="panel-group accordion-toggle"
                     id="accordion_single_collapse">
                     <div class="panel panel-default">
                        <div class="panel-heading margin-bottom-20">
                           <%-- <h4 class="panel-title">
                              <spring:message code="lbl.data.archival" text="Data Archival"/>
                           </h4> --%>
                        </div>
                        <div id="accordion1" class="panel-collapse collapse in">
                           <div class="panel-body">
                              <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 bhoechie-tab-container">
                                 <c:set var="sectionList" value="${command.getArchiveSection()}" />
                                 <div
                                    class="col-lg-2 col-md-2 col-sm-4 col-xs-12 bhoechie-tab-menu">
                                    <div class="list-group ">
                                    
                                       <%-- <a href="#" class="list-group-item text-center active">
                                          <spring:message code="portal.newlink" text="New Link"/>
                                       </a> --%>
                                       <a href="#" class="list-group-item text-center active">
                                          <spring:message code="portal.importantlink" text="Important Link"/>
                                       </a>
                                       <a href="#" class="list-group-item text-center">
                                          <spring:message code="portal.OngoingProject" text="Ongoing Projects"/>
                                       </a>
                                       <a href="#" class="list-group-item text-center">
                                          <spring:message code="portal.externallink" text="External Link"/>
                                       </a>
                                       <a href="#" class="list-group-item text-center">
                                          <spring:message code="portal.qoutation" text="Quotations"/>
                                       </a>
                                       
                                       <a href="#"	class="list-group-item text-center">
                                          <spring:message code="theme6.portal.press.release" text="Press Release" />
                                       </a>
                                       <c:forEach items="${sectionList}" var="section">
                                          <a href="#" class="list-group-item text-center">
                                          ${section.lookUpCode}</a>
                                       </c:forEach>
                                    </div>
                                 </div>
                                 <div class="col-lg-10 col-md-10 col-sm-8 col-xs-12 bhoechie-tab">
                                    <%-- --------------------------------------------Schemes----------------------------------------------- --%>														
                                    <div class="bhoechie-tab-content active">
                                      <%--  <h3>
                                          <spring:message code="" text="Schemes"/>
                                       </h3> --%>
                                       <table class="table table-bordered dataTableNormal">
                                          <thead>
                                             <tr>
                                                <th>
                                                   <spring:message code="rti.srno" text="SR NO"/>
                                                </th>
                                                <th>
                                                   <spring:message code="eip.details" text="details"/>
                                                </th>
                                             </tr>
                                          </thead>
                                          <tbody>
                                             <c:set var="schemeCount" value="0" scope="page"/>
                                             <c:if test="${command.scheme eq true}">
                                                <c:forEach items="${command.publicNotices}" var="lookUp" varStatus="lk">
                                                   <c:if test="${lookUp.isUsefullLink eq 'I' }">
                                                      <c:set var="schemeCount" value="${schemeCount + 1}" scope="page"/>
                                                      <tr>
                                                         <td>${schemeCount}</td>
                                                         <c:choose>
                                                            <c:when test="${not empty lookUp.profileImgPath}">
																	<c:choose>
																		<c:when
																			test="${userSession.getCurrent().getLanguageId() eq 1}">
																			<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:set var="exlink" value="${lookUp.link}" />																			 
																			<%-- <apptags:filedownload filename="EIP"
																				filePath="${lookUp.profileImgPath}"
																				actionUrl="CitizenPublicNotices.html?Download"
																				eipFileName="${lookUp.noticeSubEn}"></apptags:filedownload> --%>
																			<td>	
																			<c:forEach items="${links}" var="downloadL" varStatus="count">
																			 <c:set var="lnk1" value="${stringUtility.getStringAfterChar('/',downloadL)}" />
																			 <c:set var="path" value="${stringUtility.getStringBeforeChar('/',downloadL)}" />
																				<c:if test="${count.index eq 0 }">
																					<c:if test="${lnk1 ne './cache/'}">
																						<a href="${lnk1}" target="_blank" class="">
																						${lookUp.noticeSubEn}&nbsp;<br><i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																					</c:if>
																					<c:if test="${lnk1 eq './cache/'}">
																						${lookUp.noticeSubEn}
																					</c:if>
																				</c:if>
																				<c:if test="${count.index ne 0 }">
																				<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																				</c:if>
										                                        <c:set value="${test.fileSize(path, lnk1)}" var="fileSize"></c:set>
										                                        <strong style="display: block; ">${lnk1}<br>${fileSize}</strong>
																			</c:forEach>
																			</td>
																		</c:when>
																		<c:otherwise>
																			<%-- <apptags:filedownload filename="EIP"
																				filePath="${lookUp.profileImgPath}"
																				actionUrl="CitizenPublicNotices.html?Download"
																				eipFileName="${lookUp.noticeSubReg}"></apptags:filedownload> --%>
																				
																			<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:set var="exlink" value="${lookUp.link}" />
																			<td>
																			<c:forEach items="${links}" var="downloadL" varStatus="count">
																			 <c:set var="lnk1" value="${stringUtility.getStringAfterChar('/',downloadL)}" />
																			 <c:set var="path" value="${stringUtility.getStringBeforeChar('/',downloadL)}" />
																				<c:if test="${count.index eq 0 }">
																					<c:if test="${lnk1 ne './cache/'}">
																						<a href="${lnk1}" target="_blank" class="">${lookUp.noticeSubReg}&nbsp;<br><i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																					</c:if>
																					<c:if test="${lnk1 eq './cache/'}">
																						${lookUp.noticeSubReg}
																					</c:if>
																				</c:if>
																				<c:if test="${count.index ne 0 }">
																				<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																				</c:if>
																				<c:set value="${test.fileSize(path, lnk1)}" var="fileSize"></c:set>
										                                        <strong style="display: block; ">${lnk1}<br>${fileSize}</strong>
																			</c:forEach>
																			</td>
																		</c:otherwise>
																	</c:choose>
																</c:when>
                                                            <c:otherwise>
																	<c:if test="${not empty lookUp.imagePath}">																	
																	<c:set var="links" value="${fn:split(lookUp.imagePath,',')}" />
																		<c:set var="lnk1" value="./cache/${downloadL}" />																	
																		<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
																		<td>
																		
                                                                    <c:if test="${not empty lookUp.noticeSubEn}">
																			<a href="${lookUp.link}" target="_self">
																			<div class="displayimage">
																				<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																				
																			 		<img alt="Image" src="${downloadL}" class="img-responsive sml-slide" />
																			 	</c:forEach>
																			 	</div>${lookUp.noticeSubEn}
																			 </a>
																			</c:if>
																			<c:if test="${empty lookUp.noticeSubEn}">
																			<div class="displayimage">
																			<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																			 	<img alt="Image" src="${downloadL}" class="img-responsive sml-slide" />
																			 </c:forEach>
																			</div>
																			</c:if>
																			
																		</td>	
																		</c:if>
																		<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
																		<td>
																			
                                                                      <c:if test="${not empty lookUp.noticeSubReg}">
																			<a href="${lookUp.link}" target="_self">
																			<div class="displayimage">
																				<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																					<img alt="Image" src="${downloadL}" class="img-responsive sml-slide" />
																				</c:forEach>
																				</div>${lookUp.noticeSubReg}
																			</a>
																			</c:if>
																			<c:if test="${empty lookUp.noticeSubReg}">
																			<div class="displayimage">
																				<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																					<img alt="Image" src="${downloadL}" class="img-responsive sml-slide" />
																				</c:forEach>
																			</div>
																			</c:if>
																			</td>
																		
																		</c:if>
																	</c:if>
																</c:otherwise>
                                                         </c:choose>
                                                      </tr>
                                                   </c:if>
                                                </c:forEach>
                                             </c:if>
                                          </tbody>
                                       </table>
                                    </div>
                                    <%-- --------------------------------------------Schemes End----------------------------------------------- --%>														
                                    
                                    <%-- --------------------------------------------Ongoing Project----------------------------------------------- --%>														
                                    <div class="bhoechie-tab-content">
                                       <%-- <h3>
                                          <spring:message code="" text="Highlightes"/>
                                       </h3> --%>
                                       <table class="table table-bordered dataTableNormal">
                                          <thead>
                                             <tr>
                                                <th>
                                                   <spring:message code="rti.srno" text="SR NO"/>
                                                </th>
                                                <th>
                                                   <spring:message code="eip.details" text="Details"/>
                                                </th>
                                             </tr>
                                          </thead>
                                          <tbody>
                                             <c:set var="count" value="0" scope="page"/>
                                             <c:if test="${command.highlighted eq true}">
                                                <c:forEach items="${command.publicNotices}" var="lookUp" varStatus="lk">
                                                   <c:if test="${lookUp.isUsefullLink eq 'P'}">
                                                      <c:set var="count" value="${count + 1}" scope="page"/>
                                                      <tr>
                                                         <td>${lk.count}</td>
                                                         <c:choose>
                                                            <c:when test="${not empty lookUp.profileImgPath}">
																	<c:choose>
																		<c:when
																			test="${userSession.getCurrent().getLanguageId() eq 1}">
																			<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:set var="exlink" value="${lookUp.link}" />																			 
																			<%-- <apptags:filedownload filename="EIP"
																				filePath="${lookUp.profileImgPath}"
																				actionUrl="CitizenPublicNotices.html?Download"
																				eipFileName="${lookUp.noticeSubEn}"></apptags:filedownload> --%>
																			<td>	
																			<c:forEach items="${links}" var="downloadL" varStatus="count">
																			 <c:set var="lnk1" value="${stringUtility.getStringAfterChar('/',downloadL)}" />
																			 <c:set var="path" value="${stringUtility.getStringBeforeChar('/',downloadL)}" />
																				<c:if test="${count.index eq 0 }">
																					<c:if test="${lnk1 ne './cache/'}">
																						<a href="${lnk1}" target="_blank" class="">
																						${lookUp.noticeSubEn}&nbsp;<br><i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																					</c:if>
																					<c:if test="${lnk1 eq './cache/'}">
																						${lookUp.noticeSubEn}
																					</c:if>
																				</c:if>
																				<c:if test="${count.index ne 0 }">
																				<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																				</c:if>
																				<c:set value="${test.fileSize(path, lnk1)}" var="fileSize"></c:set>
										                                        <strong style="display: block; ">${lnk1}<br>${fileSize}</strong>
																			</c:forEach>
																			</td>
																		</c:when>
																		<c:otherwise>
																			<%-- <apptags:filedownload filename="EIP"
																				filePath="${lookUp.profileImgPath}"
																				actionUrl="CitizenPublicNotices.html?Download"
																				eipFileName="${lookUp.noticeSubReg}"></apptags:filedownload> --%>
																				
																			<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:set var="exlink" value="${lookUp.link}" />
																			<td>
																			<c:forEach items="${links}" var="downloadL" varStatus="count">
																			 <c:set var="lnk1" value="${stringUtility.getStringAfterChar('/',downloadL)}" />
																			 <c:set var="path" value="${stringUtility.getStringBeforeChar('/',downloadL)}" />
																				<c:if test="${count.index eq 0 }">
																					<c:if test="${lnk1 ne './cache/'}">
																						<a href="${lnk1}" target="_blank" class="">${lookUp.noticeSubReg}&nbsp;<br><i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																					</c:if>
																					<c:if test="${lnk1 eq './cache/'}">
																						${lookUp.noticeSubReg}
																					</c:if>
																				</c:if>
																				<c:if test="${count.index ne 0 }">
																				<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																				</c:if>
																				<c:set value="${test.fileSize(path, lnk1)}" var="fileSize"></c:set>
										                                        <strong style="display: block; ">${lnk1}<br>${fileSize}</strong>
																			</c:forEach>
																			</td>
																		</c:otherwise>
																	</c:choose>
																</c:when>
                                                            <c:otherwise>
																	<c:if test="${not empty lookUp.imagePath}">																	
																	<c:set var="links" value="${fn:split(lookUp.imagePath,',')}" />
																		<c:set var="lnk1" value="./cache/${downloadL}" />																	
																		<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
																		<td>
																		
																			<c:if test="${not empty lookUp.noticeSubEn}">
																			<a href="${lookUp.link}" target="_self">
																			<div class="displayimage">
																				<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																			 		<img alt="Image" src="${downloadL}" class="img-responsive sml-slide" />
																			 	</c:forEach>
																			 	</div>${lookUp.noticeSubEn}
																			 </a>
																			</c:if>
																			<c:if test="${empty lookUp.noticeSubEn}">
																			<div class="displayimage">
																			<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																			 	<img alt="Image" src="${downloadL}" class="img-responsive sml-slide" />
																			 </c:forEach>
																			</div>
																			</c:if>
																		</td>	
																		</c:if>
																		<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
																		<td>
																		
																			<c:if test="${not empty lookUp.noticeSubReg}">
																			<a href="${lookUp.link}" target="_self">
																			<div class="displayimage">
																				<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																					<img alt="Image" src="${downloadL}" class="img-responsive sml-slide" />
																				</c:forEach>
																				</div>${lookUp.noticeSubReg}
																			</a>
																			</c:if>
																			<c:if test="${empty lookUp.noticeSubReg}">
																			<div class="displayimage">
																				<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																					<img alt="Image" src="${downloadL}" class="img-responsive sml-slide" />
																				</c:forEach>
																			</div>
																			</c:if>
																			</td>
																		</c:if>
																	</c:if>
																</c:otherwise>
                                                         </c:choose>
                                                      </tr>
                                                   </c:if>
                                                </c:forEach>
                                             </c:if>
                                          </tbody>
                                       </table>
                                    </div>
                                    <%-- --------------------------------------Ongoing Project End--------------------------------- --%>
                                    
                                    <%-- --------------------------------------------Tender Project----------------------------------------------- --%>														
                                    <div class="bhoechie-tab-content">
                                       <%-- <h3>
                                          <spring:message code="" text="Highlightes"/>
                                       </h3> --%>
                                       <table class="table table-bordered dataTableNormal">
                                          <thead>
                                             <tr>
                                                <th>
                                                   <spring:message code="rti.srno" text="SR NO"/>
                                                </th>
                                                <th>
                                                   <spring:message code="eip.details" text="Details"/>
                                                </th>
                                             </tr>
                                          </thead>
                                          <tbody>
                                             <c:set var="count" value="0" scope="page"/>
                                             <c:if test="${command.highlighted eq true}">
                                                <c:forEach items="${command.publicNotices}" var="lookUp" varStatus="lk">
                                                   <c:if test="${lookUp.isUsefullLink eq 'T'}">
                                                      <c:set var="count" value="${count + 1}" scope="page"/>
                                                      <tr>
                                                         <td>${lk.count}</td>
                                                         <c:choose>
                                                            <c:when test="${not empty lookUp.profileImgPath}">
																	<c:choose>
																		<c:when
																			test="${userSession.getCurrent().getLanguageId() eq 1}">
																			<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:set var="exlink" value="${lookUp.link}" />																			 
																			<%-- <apptags:filedownload filename="EIP"
																				filePath="${lookUp.profileImgPath}"
																				actionUrl="CitizenPublicNotices.html?Download"
																				eipFileName="${lookUp.noticeSubEn}"></apptags:filedownload> --%>
																			<td>	
																			<c:forEach items="${links}" var="downloadL" varStatus="count">
																			 <c:set var="lnk1" value="${stringUtility.getStringAfterChar('/',downloadL)}" />
																			 <c:set var="path" value="${stringUtility.getStringBeforeChar('/',downloadL)}" />
																				<c:if test="${count.index eq 0 }">
																					<c:if test="${lnk1 ne './cache/'}">
																						<a href="${lnk1}" target="_blank" class="">
																						${lookUp.noticeSubEn}&nbsp;<br><i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																					</c:if>
																					<c:if test="${lnk1 eq './cache/'}">
																						${lookUp.noticeSubEn}
																					</c:if>
																				</c:if>
																				<c:if test="${count.index ne 0 }">
																				<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																				</c:if>
																				<c:set value="${test.fileSize(path, lnk1)}" var="fileSize"></c:set>
										                                        <strong style="display: block; ">${lnk1}<br>${fileSize}</strong>
																			</c:forEach>
																			</td>
																		</c:when>
																		<c:otherwise>
																			<%-- <apptags:filedownload filename="EIP"
																				filePath="${lookUp.profileImgPath}"
																				actionUrl="CitizenPublicNotices.html?Download"
																				eipFileName="${lookUp.noticeSubReg}"></apptags:filedownload> --%>
																				
																			<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:set var="exlink" value="${lookUp.link}" />
																			<td>
																			<c:forEach items="${links}" var="downloadL" varStatus="count">
																			 <c:set var="lnk1" value="${stringUtility.getStringAfterChar('/',downloadL)}" />
																			 <c:set var="path" value="${stringUtility.getStringBeforeChar('/',downloadL)}" />
																				<c:if test="${count.index eq 0 }">
																					<c:if test="${lnk1 ne './cache/'}">
																						<a href="${lnk1}" target="_blank" class="">${lookUp.noticeSubReg}&nbsp;<br><i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																					</c:if>
																					<c:if test="${lnk1 eq './cache/'}">
																						${lookUp.noticeSubReg}
																					</c:if>
																				</c:if>
																				<c:if test="${count.index ne 0 }">
																				<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																				</c:if>
																				<c:set value="${test.fileSize(path, lnk1)}" var="fileSize"></c:set>
										                                        <strong style="display: block; ">${lnk1}<br>${fileSize}</strong>
																			</c:forEach>
																			</td>
																		</c:otherwise>
																	</c:choose>
																</c:when>
                                                            <c:otherwise>
																	<c:if test="${not empty lookUp.imagePath}">																	
																	<c:set var="links" value="${fn:split(lookUp.imagePath,',')}" />
																		<c:set var="lnk1" value="./cache/${downloadL}" />																	
																		<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
																		<td>
																		
                                                             <c:if test="${not empty lookUp.noticeSubEn}">
																			<a href="${lookUp.link}" target="_self">
																			<div class="displayimage">
																				<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																			 		<img alt="Image" src="${downloadL}" class="img-responsive sml-slide" />
																			 	</c:forEach>
																			 	</div>${lookUp.noticeSubEn}
																			 </a>
																			</c:if>
																			<c:if test="${empty lookUp.noticeSubEn}">
																			<div class="displayimage">
																			<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																			 	<img alt="Image" src="${downloadL}" class="img-responsive sml-slide" />
																			 </c:forEach>
																			</div>
																			</c:if>																		</td>	
																		</c:if>
																		<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
																		<td>
																		
																			<c:if test="${not empty lookUp.noticeSubReg}">
																			<a href="${lookUp.link}" target="_self">
																			<div class="displayimage">
																				<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																					<img alt="Image" src="${downloadL}" class="img-responsive sml-slide" />
																				</c:forEach>
																				</div>${lookUp.noticeSubReg}
																			</a>
																			</c:if>
																			<c:if test="${empty lookUp.noticeSubReg}">
																			<div class="displayimage">
																				<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																					<img alt="Image" src="${downloadL}" class="img-responsive sml-slide" />
																				</c:forEach>
																			</div>
																			</c:if>
																			</td>
																		</c:if>
																	</c:if>
																</c:otherwise>
                                                         </c:choose>
                                                      </tr>
                                                   </c:if>
                                                </c:forEach>
                                             </c:if>
                                          </tbody>
                                       </table>
                                    </div>
                                    <%-- --------------------------------------Tender End--------------------------------- --%>
                                    
                                    <%-- --------------------------------------------Qoutation----------------------------------------------- --%>														
                                    <div class="bhoechie-tab-content">
                                       <%-- <h3>
                                          <spring:message code="" text="Highlightes"/>
                                       </h3> --%>
                                       <table class="table table-bordered dataTableNormal">
                                          <thead>
                                             <tr>
                                                <th>
                                                   <spring:message code="rti.srno" text="SR NO"/>
                                                </th>
                                                <th>
                                                   <spring:message code="eip.details" text="Details"/>
                                                </th>
                                             </tr>
                                          </thead>
                                          <tbody>
                                             <c:set var="count" value="0" scope="page"/>
                                             <c:if test="${command.highlighted eq true}">
                                                <c:forEach items="${command.publicNotices}" var="lookUp" varStatus="lk">
                                                   <c:if test="${lookUp.isUsefullLink eq 'Q'}">
                                                      <c:set var="count" value="${count + 1}" scope="page"/>
                                                      <tr>
                                                         <td>${lk.count}</td>
                                                         <c:choose>
                                                            <c:when test="${not empty lookUp.profileImgPath}">
																	<c:choose>
																		<c:when
																			test="${userSession.getCurrent().getLanguageId() eq 1}">
																			<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:set var="exlink" value="${lookUp.link}" />																			 
																			<%-- <apptags:filedownload filename="EIP"
																				filePath="${lookUp.profileImgPath}"
																				actionUrl="CitizenPublicNotices.html?Download"
																				eipFileName="${lookUp.noticeSubEn}"></apptags:filedownload> --%>
																			<td>	
																			<c:forEach items="${links}" var="downloadL" varStatus="count">
																			 <c:set var="lnk1" value="${stringUtility.getStringAfterChar('/',downloadL)}" />
																			 <c:set var="path" value="${stringUtility.getStringBeforeChar('/',downloadL)}" />
																				<c:if test="${count.index eq 0 }">
																					<c:if test="${lnk1 ne './cache/'}">
																						<a href="${lnk1}" target="_blank" class="">
																						${lookUp.noticeSubEn}&nbsp;<br><i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																					</c:if>
																					<c:if test="${lnk1 eq './cache/'}">
																						${lookUp.noticeSubEn}
																					</c:if>
																				</c:if>
																				<c:if test="${count.index ne 0 }">
																				<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																				</c:if>
																				<c:set value="${test.fileSize(path, lnk1)}" var="fileSize"></c:set>
										                                        <strong style="display: block; ">${lnk1}<br>${fileSize}</strong>
																			</c:forEach>
																			</td>
																		</c:when>
																		<c:otherwise>
																			<%-- <apptags:filedownload filename="EIP"
																				filePath="${lookUp.profileImgPath}"
																				actionUrl="CitizenPublicNotices.html?Download"
																				eipFileName="${lookUp.noticeSubReg}"></apptags:filedownload> --%>
																				
																			<c:set var="links" value="${fn:split(lookUp.profileImgPath,',')}" />
																			<c:set var="exlink" value="${lookUp.link}" />
																			<td>
																			<c:forEach items="${links}" var="downloadL" varStatus="count">
																			 <c:set var="lnk1" value="${stringUtility.getStringAfterChar('/',downloadL)}" />
																			 <c:set var="path" value="${stringUtility.getStringBeforeChar('/',downloadL)}" />
																				<c:if test="${count.index eq 0 }">
																					<c:if test="${lnk1 ne './cache/'}">
																						<a href="${lnk1}" target="_blank" class="">${lookUp.noticeSubReg}&nbsp;<br><i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																					</c:if>
																					<c:if test="${lnk1 eq './cache/'}">
																						${lookUp.noticeSubReg}
																					</c:if>
																				</c:if>
																				<c:if test="${count.index ne 0 }">
																				<a href="${lnk1}" target="_blank" class="">&nbsp;<i class="fa fa-file-pdf-o" style="color:red" ></i>&nbsp;</a>
																				</c:if>
																				<c:set value="${test.fileSize(path, lnk1)}" var="fileSize"></c:set>
										                                        <strong style="display: block; ">${lnk1}<br>${fileSize}</strong>
																			</c:forEach>
																			</td>
																		</c:otherwise>
																	</c:choose>
																</c:when>
                                                            <c:otherwise>
																	<c:if test="${not empty lookUp.imagePath}">																	
																	<c:set var="links" value="${fn:split(lookUp.imagePath,',')}" />
																		<c:set var="lnk1" value="./cache/${downloadL}" />																	
																		<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
																		<td>
																		
																		<c:if test="${not empty lookUp.noticeSubEn}">
																			<a href="${lookUp.link}" target="_self">
																			<div class="displayimage">
																				<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																			 		<img alt="Image" src="${downloadL}" class="img-responsive sml-slide" />
																			 	</c:forEach>
																			 	</div>${lookUp.noticeSubEn}
																			 </a>
																			</c:if>
																			<c:if test="${empty lookUp.noticeSubEn}">
																			<div class="displayimage">
																			<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																			 	<img alt="Image" src="${downloadL}" class="img-responsive sml-slide" />
																			 </c:forEach>
																			</div>
																			</c:if>	
																		</td>	
																		</c:if>
																		<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
																		<td>
																		
																		<c:if test="${not empty lookUp.noticeSubReg}">
																			<a href="${lookUp.link}" target="_self">
																			<div class="displayimage">
																				<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																					<img alt="Image" src="${downloadL}" class="img-responsive sml-slide" />
																				</c:forEach>
																				</div>${lookUp.noticeSubReg}
																			</a>
																			</c:if>
																			<c:if test="${empty lookUp.noticeSubReg}">
																			<div class="displayimage">
																				<c:forEach items="${links[0]}" var="downloadL" varStatus="count"> 
																					<img alt="Image" src="${downloadL}" class="img-responsive sml-slide" />
																				</c:forEach>
																			</div>
																			</c:if>	
																			</td>
																		</c:if>
																	</c:if>
																</c:otherwise>
                                                         </c:choose>
                                                      </tr>
                                                   </c:if>
                                                </c:forEach>
                                             </c:if>
                                          </tbody>
                                       </table>
                                    </div>
                                    <%-- --------------------------------------Qoutation End--------------------------------- --%>
                                    
                                    <%-- --------------------------------------Announcement / Press Release Start ----------------------------------- --%>		
                                    <div class="bhoechie-tab-content">
										<div class="press-release-content">
											<table class="table table-bordered table-striped dataTableNormal">
												<thead>
													<tr>
														<th><spring:message code="theme3.portal.details.info" text="Details / Information"/></th>
														<th class="pr-date"><spring:message code="theme3.portal.date" text="Date"/></th>							
														<th><spring:message code="theme3.portal.images" text="Images"/></th>
														<th><spring:message code="theme3.portal.download" text="Download"/></th>
														<th><spring:message code="theme6.portal.internal.external.links" text="Internal / External Links"/></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${command.eipAnnouncement}" var="lookUp" varStatus="counter">
														<tr>
															<td>
																<c:choose>
																	<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
																		<c:choose>
																			<c:when
																				test="${(not empty lookUp.linkType) and (lookUp.linkType eq 'E' || lookUp.linkType eq 'L') }">
																				${lookUp.announceDescEng}
																			</c:when>
																			<c:otherwise>
																				${lookUp.announceDescEng}
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<c:choose>
																			<c:when test="${(not empty lookUp.linkType) and (lookUp.linkType eq 'E' || lookUp.linkType eq 'L') }">
																				${lookUp.announceDescReg}
																			</c:when>
																			<c:otherwise>
																				${lookUp.announceDescReg}
																			</c:otherwise>
																		</c:choose>
																	</c:otherwise>
																</c:choose>
															</td>
															<td class="pr-date text-center">
																<span class="date">
																	<fmt:formatDate type="date" value="${lookUp.newsDate}" dateStyle="SHORT" pattern="dd-MM-yyyy"/>
																</span>
															</td>
															<td  class="text-center">
															<c:set var="flagCnt" value="0" />
															<c:set var="links" value="${fn:split(lookUp.attachImage,',')}" />
															<c:forEach items="${links}" var="download" varStatus="count">
																<c:if test="${not empty download && fn:trim(download) ne ''}">
																	
																	<c:set var="flagCnt" value="1" />
																</c:if>
															</c:forEach>
																			
															<c:if test="${not empty lookUp.attachImage && flagCnt eq 1}">
																<a class="press-release" data-toggle="modal" data-id="${lookUp.attachImage}"  ><i class="fa fa-picture-o " ></i></a>
															</c:if>	
															</td>
															<td class="text-center">
															<c:if test="${not empty lookUp.attach}">
															<c:set var="links" value="${fn:split(lookUp.attach,',')}" />
															<c:forEach items="${links}" var="download" varStatus="count">
															<c:set var="fileName" value="${stringUtility.getStringAfterChar('/',download)}" />
															<c:set var="path" value="${stringUtility.getStringBeforeChar('/',download)}" />
															<c:set value="${test.fileSize(path, link)}" var="fileSize"></c:set>
																<a href="/cache/${download}" target="_blank" class="">&nbsp;<i class="fa fa-download" ></i>&nbsp;</a>
																<strong style="display: block;">${fileName}<br>${fileSize}</strong>
															</c:forEach>	
															</c:if>
															</td>
															<td>
																<c:choose>
																	<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
																		<c:choose>
																			<c:when
																				test="${(not empty lookUp.linkType) and (lookUp.linkType eq 'E' || lookUp.linkType eq 'L') }">
																				<a href="${lookUp.link }"><span class="external_link_icon"></span></a>
																			</c:when>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<c:choose>
																			<c:when test="${(not empty lookUp.linkType) and (lookUp.linkType eq 'E' || lookUp.linkType eq 'L') }">
																				<a href="${lookUp.link }"><span class="external_link_icon"></span></a>
																			</c:when>
																		</c:choose>
																	</c:otherwise>
																</c:choose>
															</td>				
														</tr>
													</c:forEach>
												</tbody>
											</table>
											
											<!-- Modal for Press Release Events starts -->
											<div id="pressreleasediv" class="modal fade press-modal press-release-modal-class" role="dialog">
												<div class="modal-dialog modal-lg">
													<!-- Modal content-->
													<div class="modal-content">
														<button type="button" class="close" data-dismiss="modal">
															<i class="fa fa-times" aria-hidden="true"></i>
														</button>
														<div class="modal-body ">
															<div id="myCarousel-1" class="carousel slide carousel-fade" data-ride="carousel">
																<!-- Wrapper for slides -->
																<div class="carousel-inner press-modal-body" id="press-modal-body-div">
							
																</div>
										
																<!-- Left and right controls -->
																<a class="left carousel-control hidden-xs" href="#myCarousel-1"	data-slide="prev">
																	<i class="fa fa-angle-left" aria-hidden="true"></i>
																	<!-- <span class="sr-only">Previous</span> -->
																</a>
																<a class="right carousel-control hidden-xs" href="#myCarousel-1" data-slide="next">
																	<i class="fa fa-angle-right" aria-hidden="true"></i>
																	<!-- <span class="sr-only">Next</span> -->
																</a>
															</div>
															
														</div>
													</div>
							
												</div>
											</div>
											<!-- Modal for Press Release Events ends -->
										</div>
                                    </div> 
                                    <%-- --------------------------------------------Announcement / Press Release End-------------------------------- --%>		
                                    <c:forEach items="${sectionList}" var="section">
                                       <div class="bhoechie-tab-content">
                                          <c:set var="lookUpList" value="${command.getSectionInformation(section.lookUpId)}" />
                                          <%-- <h3>${section.lookUpCode}</h3> --%>
                                          <table class="table table-bordered dataTableNormal">
                                             <c:forEach items="${lookUpList}" var="lookUps"
                                                varStatus="lk">
                                                <c:if test="${lk.index eq 0}">
                                                   <thead>
                                                      <tr>
                                                         <c:forEach items="${lookUps}" var="lookUp" varStatus="count">
                                                            <c:if
                                                               test="${not empty lookUp.lookUpCode && lookUp.lookUpType ne MainetConstants.TEXT_AREA_HTML}">
                                                               <th width="20%">${lookUp.lookUpCode}</th>
                                                            </c:if>
                                                         </c:forEach>
                                                      </tr>
                                                   </thead>
                                                </c:if>
                                                <tr>
                                                   <c:forEach items="${lookUps}" var="lookUp">
                                                   <c:if
                                                         test="${lookUp.lookUpType==MainetConstants.VALIDITY_DATE_CODE}">
                                                         <td>${lookUp.lookUpDesc}</td>
                                                      </c:if>
                                                      <c:if
                                                         test="${lookUp.lookUpType==MainetConstants.TEXT_AREA_HTML}">
                                                         <td>${lookUp.lookUpDesc}</td>
                                                      </c:if>
                                                      <c:if
                                                         test="${lookUp.lookUpType==MainetConstants.TEXT_FIELD}">
                                                         <td style="word-break: break-all;">${lookUp.lookUpDesc}</td>
                                                      </c:if>
                                                      <c:if
                                                         test="${lookUp.lookUpType==MainetConstants.TEXT_AREA}">
                                                         <td style="word-break: break-all;">${lookUp.lookUpDesc}</td>
                                                      </c:if>
                                                      <c:if
                                                         test="${lookUp.lookUpType==MainetConstants.DATE_PICKER}">
                                                         <td>
                                                            ${lookUp.lookUpDesc}
                                                         </td>
                                                      </c:if>
                                                      <c:if
                                                         test="${lookUp.lookUpType==MainetConstants.PROFILE_IMG}">
                                                         <c:choose>
                                                            <c:when test="${not empty lookUp.lookUpDesc}">
                                                               <td>
                                                                  <c:set var="imgs"
                                                                     value="${fn:split(lookUp.lookUpDesc, '|')}" />
                                                                  <c:forEach items="${imgs}" var="img">
                                                                     <a class="fancybox" href="${img}"
                                                                        data-fancybox-group="images"> <img
                                                                        src="${img}" alt="${img}" class="img-thumbnail"
                                                                        width="300px"></a>
                                                                  </c:forEach>
                                                               </td>
                                                            </c:when>
                                                            <c:otherwise>
                                                               <td>
                                                                  <div align="center">
                                                                  </div>
                                                               </td>
                                                            </c:otherwise>
                                                         </c:choose>
                                                      </c:if>
                                                      <c:if
                                                         test="${lookUp.lookUpType==MainetConstants.VIDEO}">
                                                         <c:choose>
                                                            <c:when test="${not empty lookUp.lookUpDesc}">
                                                               <td>
                                                                  <c:set var="videos"
                                                                     value="${fn:split(lookUp.lookUpDesc, '|')}" />
                                                                  <c:forEach items="${videos}" var="video">
                                                                     <a class="fancybox" href="${video}"
                                                                        data-fancybox>
                                                                        <video alt="Image" width="100%" controls>
                                                                           <source src="${video}"
                                                                              type='video/mp4; codecs="avc1.42E01E, mp4a.40.2"'>
                                                                        </video>
                                                                     </a>
                                                                  </c:forEach>
                                                               </td>
                                                            </c:when>
                                                            <c:otherwise>
                                                               <td>
                                                                  <div align="center">
                                                                  </div>
                                                               </td>
                                                            </c:otherwise>
                                                         </c:choose>
                                                      </c:if>
                                                      <c:if
                                                         test="${lookUp.lookUpType==MainetConstants.ATTACHMENT_FIELD}">
                                                         <c:set var="links"
                                                            value="${fn:split(lookUp.lookUpDesc, ',')}" />
                                                         <td>
                                                            <c:forEach items="${links}" var="download"
                                                               varStatus="status">
                                                               <c:set var="link"
                                                                  value="${stringUtility.getStringAfterChar('/',download)}" />
                                                               <c:set var="path"
                                                                  value="${stringUtility.getStringBeforeChar('/',download)}" />
                                                               <c:if test="${not empty lookUp.lookUpDesc}">
                                                               <div style="text-align: center;">
                                                                  <apptags:filedownload filename="${link}"
                                                                     filePath="${path}"
                                                                     showIcon="true"
                                                                     actionUrl="SectionInformation.html?Download"></apptags:filedownload>
                                                               </div>
                                                               </c:if>
						                                        <c:set value="${test.fileSize(path, link)}" var="fileSize"></c:set>
						                                        <strong style="display: block; text-align: center;">${link}<br>${fileSize}</strong>
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
                                    </c:forEach>
                                 </div>
                              </div>
                           </div>
                        </div>
                     </div>
                  </div>
               </form>
            </div>
            <div class="clearfix"></div>
         </div>
      </div>
   </div>
</div>
<script>
$(window).on('load', function(){$("select.form-control").attr("aria-label","select");});
</script>
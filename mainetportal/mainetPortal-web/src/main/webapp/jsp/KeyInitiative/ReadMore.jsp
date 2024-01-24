<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set value="${pageName}" var="page"/>
<ol class="breadcrumb" id="CitizenService">
   <li  class="breadcrumb-item"><a href="CitizenHome.html"><strong class="fa fa-home"></strong> Home </a></li>
   <li class="breadcrumb-item active">
      <c:out value="${page}"/>
   </li>
</ol>
<div class="widget-content padding">	
   <c:choose>
      <c:when test="${fn:containsIgnoreCase(page,'photo gallery')}">
         <div class="widget-content padding form-horizontal">
            <div class="panel-group accordion-toggle" id="accordion_single_collapse">
               <div class="panel panel-default">
                  <div class="panel-heading">
                     <h4 class="panel-title">
                        <spring:message code="PhotoGallery" text="Photo Gallery"/>
                     </h4>
                  </div>
                  <div class="panel-collapse collapse in">
                     <div class="panel-body">
                        <div class="photogallery">
                           <ul>
                              <c:forEach items="${command.getAllphotos('photo gallery')}" var="dept">
                                 <li>
                                    <a class="thumbnail fancybox" data-rel="ligthbox" href="./${dept.imagePath}" title="${dept.caption}">
                                       <img src="${dept.imagePath}" class="img-thumbnail"  alt="Loading please wait" title="${dept.caption}"/>
                                       <c:if test="${not empty dept.caption}"><span class="gallery-caption">${dept.caption}</span></c:if>
                                    </a>
                                 </li>
                              </c:forEach>
                           </ul>
                        </div>
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </c:when>
      <c:when test="${fn:containsIgnoreCase(page,'video gallery') }">
         <div class="form-horizontal vg-main">
            <div class="panel-group accordion-toggle" id="accordion_single_collapse">
               <div class="panel panel-default">
                  <div class="panel-heading margin-bottom-20">
                     <h4 class="panel-title">
                        <spring:message code="VideoGallery" text="Video Gallery"/>
                     </h4>
                  </div>
                  <div class="panel-collapse collapse in">
                     <div class="panel-body">
                        <div class="videogallery">
                           <ul>
                              <c:forEach items="${command.getAllvideos('video gallery')}" var="dept2">
                                 <li>
                                    <video alt="Videos" style="width:100%" controls="controls">
                                       <source src="./${dept2.imagePath}" type='video/mp4'>
                                    </video>
                                    <c:if test="${not empty dept2.caption}"><span class="gallery-caption">${dept2.caption}</span></c:if>
                                 </li>
                              </c:forEach>
                           </ul>
                        </div>
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </c:when>
      <c:otherwise>
         <c:forEach items="${command.getAllhtml(page)}" var="Allhtml">
            ${Allhtml}
         </c:forEach>
      </c:otherwise>
   </c:choose>
</div>
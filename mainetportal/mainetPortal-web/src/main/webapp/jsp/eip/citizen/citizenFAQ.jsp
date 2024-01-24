<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

 <ol class="breadcrumb" id="CitizenService">
	<li class="breadcrumb-item"><a href="CitizenHome.html"><strong class="fa fa-home"></strong><spring:message code="top.home" text="Home"/></a></li>
	<li class="breadcrumb-item active"><spring:message code="eip.citizen.faq.heading" /></li>
</ol>
 


<div class="content animated">
  <div class="widget">
    <div class="widget-header">
      <h2><spring:message code="eip.citizen.faq.heading" /></h2>
      </div>

<div class="widget-content padding form-horizontal">

		 <div class="panel-group accordion-toggle" id="accordionfaq">
	         <c:forEach items="${command.faqHistList}" var="faq" varStatus="loop">
				 <c:if test="${userSession.languageId eq MainetConstants.ENGLISH}">
			         <div class="panel panel-default">
			           <div class="panel-heading">
			          <h4 class="panel-title">   <a class="collapsed" data-toggle="collapse" data-parent="#accordionfaq" href="#accordion${loop.index + 1}">  ${faq.questionEn} </a></h4>
			           </div>
			           <div id="accordion${loop.index + 1}" role="region" class="panel-collapse collapse">
			             <div class="panel-body"> ${faq.answerEn} </div>
			           </div>
			         </div>
				</c:if>
				
				<c:if test="${userSession.languageId eq MainetConstants.MARATHI	}">
			         <div class="panel panel-default">
			           <div class="panel-heading">
			            <h4 class="panel-title"> <a class="collapsed" data-toggle="collapse" data-parent="#accordionfaq" href="#accordion${loop.index + 1}">${faq.questionReg}</a></h4>
			           </div>
			           <div id="accordion${loop.index + 1}" role="region" class="panel-collapse collapse">
			             <div class="panel-body tab-boxed"> ${faq.answerReg} </div>
			           </div>
			         </div>
				</c:if>
	         
	         </c:forEach>   
         </div>
</div>
		</div>
	</div>
<hr/>
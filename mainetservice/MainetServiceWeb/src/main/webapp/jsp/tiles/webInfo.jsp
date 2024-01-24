<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<div class="container-fluid dashboard-page">
<div class="col-sm-12" id="nischay">
<!-- English starts -->
<c:if test="${userSession.languageId eq 1}">
<h4><strong><spring:message code="web.information.eng" text="Web Information Manager"/></strong></h4>
<div class="widget">
<div class="form-horizontal padding widget-content">
<div class="col-sm-12">
<div class="accordion-toggle panel-group" id="accordion_single_collapse">
<div class="panel panel-default">
<div class="collapse in panel-collapse">
<div class="panel-body">
<p><spring:message code="web.info.office.eng" text="SUDA Office"/></p>
</div>
</div>
</div>
</div>
</div>
</div>
</div>
</c:if>
<!-- English ends -->

<!-- Hindi starts -->
<c:if test="${userSession.languageId ne 1}">
<h4><strong><spring:message code="web.information.reg" text="Web Information Manager"/></strong></h4>
<div class="widget">
<div class="form-horizontal padding widget-content">
<div class="col-sm-12">
<div class="accordion-toggle panel-group" id="accordion_single_collapse">
<div class="panel panel-default">
<div class="collapse in panel-collapse">
<div class="panel-body">
<p><spring:message code="web.info.office.reg" text="SUDA Office"/></p>
</div>
</div>
</div>
</div>
</div>
</div>
</div>
</c:if>
</div>
</div>

</body>
</html>
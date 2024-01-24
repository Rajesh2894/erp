 <%@ page isErrorPage="true" %>
 <%@ page import="com.abm.mainet.common.exception.FrameworkException" %>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<i class="fa fa-exclamation-triangle"></i> Warning
			</h2>
		</div>
		<div class="widget-content padding">
		<div class="full-content-center animated flipInX">
			<h3>ERROR CODE</h3>${errCode}  <%= (exception.getCause() != null ? (exception.getCause().getCause() != null ? exception.getCause().getCause().getMessage() : exception.getMessage()) : exception.getMessage()) %>
            <h3>ERROR MESSAGE</h3>${errMsg}
            <h3>TRACE ERROR</h3>${error}
            <h3>Sorry, Unable to process your request.</h3>
            <h3>Please contact to Administrator and try	again later!</h3>
            <br>
          </div>
		</div>
	</div>
</div>

<%@ page pageEncoding="UTF-8"%>
<% response.setContentType("text/html; charset=utf-8"); %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div id="content" class="browser-version" style="background:#ffffff">
	<div class="text-center">
		<img src="images/computer.png" width="160px" alt="Incompatible Browser Version"/><br/>
		<small><spring:message code="browser.versiontext" text="It looks like you are running an older version of Internet Explorer, which is no longer supported by our website.
		Please install an updated version of your browser or download from the below options."/></small><br/><br/>
	</div>
	<div class="text-center well">
		<a href="https://www.google.com/chrome/" target="_blank" aria-label="Download chrome Browser"><img src="images/browser_chrome.png" width="60px" alt="Download Chrome Browser"/></a>
		<a href="https://www.mozilla.org/en-US/firefox/new/" target="_blank" aria-label="Download Firefox Browser"><img src="images/browser_firefox.png" width="60px" alt="Download Firefox Browser"/></a>
		<a href="https://www.microsoft.com/en-in/download/Internet-Explorer-11-for-Windows-7-details.aspx" target="_blank" aria-label="Download Internet Explorer Browser"><img src="images/browser_ie.png" width="60px" alt="Download Internet Explorer Browser"/></a>
		<div><br/><small>This website can be best viewed in 1024 x 768 pixels resolution using Chrome 3, Firefox 3.5 and higher. In other browsers, the view may look distorted.</small></div>
	</div>     
 </div>
 <div class="clearfix" style="margin-bottom: 20px"></div>

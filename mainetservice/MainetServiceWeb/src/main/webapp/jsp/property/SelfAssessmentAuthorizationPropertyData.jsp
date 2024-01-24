<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/property/propertyAuthorization.js"></script>
<div class="table-responsive margin-top-10">
					<table class="table table-bordered table-striped">
						<tr>
							<th>Application No.</th>
							<th>Application Date</th>
							<th>Property No</th>
							<th>Owner Name</th>
							<th>Owner Address</th>
							<th class="text-center">View</th>
						</tr>
						<tr>
							<td>${command.apmApplicationId}</td>
							<td>${command.createdDate}</td>
							<td>${command.proAssNo}</td>
							<td>${command.assOwnerName}</td>
							<td>${command.proAssAddress}</td>
							<td class="text-center">
								<button type="button" class="btn btn-blue-2 btn-sm" id="viewAuthFormBtn">
										<i class="fa fa-eye"></i>
								</button>
							</td>
						</tr>
						
					</table>

				</div>
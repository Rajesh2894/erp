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
<script type="text/javascript" src="js/propertytax/propertyAuth.js"></script>
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
							<td>${command.entity1.assApplicationNo}</td>
							<td>10/02/2016</td>
							<td>${command.entity1.assPropertyno}</td>
							<td>${command.entity1.assOwnerName}</td>
							<td>Midc Centeral Andheri East</td>
							<td class="text-center"><button type="button"
									class="btn btn-blue-2 btn-sm" id="viewAuthFormBtn">
									<i class="fa fa-eye"></i>
								</button></td>
						</tr>
						
					</table>

				</div>
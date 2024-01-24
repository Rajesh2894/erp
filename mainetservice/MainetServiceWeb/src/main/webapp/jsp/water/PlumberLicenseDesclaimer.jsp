<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/water/plumberLicense.js"></script>


<div class="content animated slideInDown">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>Licence Plumber</h2>
			<apptags:helpDoc url=""></apptags:helpDoc>

		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span>Field with <i class="text-red-1">*</i> is mandatory
				</span>
			</div>

			<form:form action="PlumberLicense.html" class="form-horizontal form"
				name="PlumberLicenseDescForm" id="PlumberLicenseDescForm">
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<!-- Consumer Details -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 type="h4" class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a1">Submission
									of Online Application for Plumbing Licence.</a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body padding-top-20">
								<h3>Disclaimer</h3>
								<p>
									The following terms and conditions apply to Plumbing Licence
									Application facility made by
									<c:if test="${userSession.languageId eq 1}">
										<spring:message code=""
											text="${userSession.organisation.ONlsOrgname}" />
									</c:if>
									<c:if test="${userSession.languageId ne 1}">
										<spring:message code=""
											text="${userSession.organisation.oNlsOrgnameMar}" />
									</c:if>
									to the citizens. All citizens using the online application
									facility are requested to, please, read them carefully before
									applying plumbing licence application. By using the online
									application facility on this portal you accept these terms and
									conditions.
								</p>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 type="h4" class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a2">List of
									required documents for Plumbing Licence Application</a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<ol type="1" class="margin-left-20">
									<li>Diploma / Degree Certificate</li>
									<li>School / College leaving certificate / Birth
										Certificate.</li>
									<li>Residential proof (Electricity Bill / Telephone Bill /
										Passport / Adhar Card )</li>
									<li>Two passport size latest photograph</li>
									<li>Pan Card</li>
								</ol>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 type="h4" class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a3">Plumbing
									Licence will be issued only to those applicants who possess one
									of the following qualifications:- </a>
							</h4>
						</div>
						<div id="a3" class="panel-collapse collapse in">
							<div class="panel-body">
								<ol type="1" class="margin-left-20">
									<li>B.E.(Civil),(Mech.) & (Elect.)</li>
									<li>A.M.I.E.(Civil)/(Mech.)</li>
									<li>B.Architect</li>
									<li>Diploma in Civil,Mech.andElect.Engg</li>
									<li>Govt.Diploma in Arch.</li>
									<li>Diploma in Construction Technology/Const.Engg.</li>
									<li>Diploma in Civil & Rural Engg.</li>
									<li>Licentiate of Civil and Sanitary Engineering of
										V.J.T.I.</li>
									<li>Licentiate of Civil Engg.,Mech.Engg.andElect.Engg. of
										V.J.T.I.</li>
									<li>Licentiate of Civil & Environmental Engg. of V.J.T.I.
									</li>
									<li>Licentiate and Diploma Granted by other Govt.
										Institutions.</li>
								</ol>
								<p class="text-bold margin-top-5 margin-bottom-5">Courses
									conducted by Technical Education Board and for which, minimum
									Secondary School Certificate is necessary and course is not
									less than 6 Semester (for Sr.No.4 to 11 above).</p>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 type="h4" class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a4">Terms
									and Conditions</a>
							</h4>
						</div>
						<div id="a4" class="panel-collapse collapse in">
							<div class="panel-body">
								<ol type="1" class="margin-left-20">
									<li>If applicant does not approach personally to receive
										plumbing licence within 3 months after the date of payment the
										application for the plumbing licence will be cancelled.</li>
									<li>Applicant will be able to pay the required fees for
										the plumbing licence only after submission of valid documents.</li>
									<li>Applicant must submit the signed copy of Online
										Application Form and Xerox copies of the required documents
										alongwith Original for verification within 1 month in the
										office of <c:if test="${userSession.languageId eq 1}">
											<spring:message code=""
												text="${userSession.organisation.ONlsOrgname}" />
										</c:if> <c:if test="${userSession.languageId ne 1}">
											<spring:message code=""
												text="${userSession.organisation.oNlsOrgnameMar}" />
										</c:if>
										<ul type="disc" class="margin-left-20">
											<li>To submit Original Certificates for verification
												from the date of Application in 5 days.</li>
											<li>To verify and sanction the documents. in 5 days.</li>
											<li>To pay necessary fees / Deposit and issue licence in
												5 days</li>
											<li>If applicant failed to comply as above, it will be
												treated that He/She is not interested for New Plumbing
												Licence, and said application will be cancelled.</li>
										</ul>
									</li>
									<li>Applicant who have not renewed their licence for more
										than three years, must submit required documents in Hydraulic
										Engineering office personally and to pay all necessary fees,
										penalty and fresh deposit etc.</li>
									<li>The deposit (Rs 500) is forefeited if the licence is
										not renewed every year</li>
									<li>New plumbing licence / renewed licence will be issued
										to the licence holder only.</li>
									<!-- <li>In case of any technical problem in submission of
										online application, Payment, Renewal of licence etc. Please
										contact to hydraulic engineers office personally or contact on
										the 022-24955268</li> -->
								</ol>
							</div>
						</div>
					</div>

					<div class="text-center col-sm-12 margin-bottom-10">
						<button type="button" class="btn btn-success"
							onclick="acceptDesclimer(this)" title="Accept">Accept</button>
					</div>
				</div>
			</form:form>

		</div>
	</div>
</div>
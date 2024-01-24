<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="css/report.css" rel="stylesheet" type="text/css">
<link href="css/kendo.common-material.min.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet" href="css/kendo.material.min.css" type="text/css" />
<script type="text/javascript" src="js/mainet/ui/kendo.all.min.js"></script>
<script type="text/javascript" src="js/mainet/ui/jszip.min.js"></script>
<script type="text/javascript"
	src="js/works_management/tenderinitiation.js"></script>

<script type="text/javascript"
	src="js/works_management/reports/noticeInvitingTender.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<div class="content">
	<div class="widget">
		<header class="fixed" align="center">
			<button class="button" onclick="ExportPdfPQR()">Download as
				.pdf</button>
			<button class="button" onclick="exportHTMLPQR();">Download
				as .doc</button>
			<button class="button" onclick="backTenderPage();">
				<i class="fa fa-chevron-circle-left padding-right-5"></i>
				<spring:message code="works.management.back" text="" />
			</button>
		</header>

		<!-- Scroll to Top button -->
		<button onclick="topFunction()" id="myBtn" title="Go to top">Top</button>

		<form action="" method="POST" class="form-horizontal">

			<!---  Report Page Start Here --->
			<div class="report" id="myCanvas">
				<!---  01 Page Start Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h1>
							<b>${userSession.getCurrent().organisation.ONlsOrgname}</b><br>
							Pre qualification document<br /> <br />for </b>
						</h1>
						<br /> <br />
						<div style="font-size: 1.20em;">
							<p>
								<b>Name of Work-</b> <b>${command.preQualDocument.workName}</b>
							</p>
							<br />
							<p>
								<b>Probable Amount of Contract -</b> Rs. <b>${command.preQualDocument.workEstimateAmt}
									Lacs</b>

							</p>
							<br /> <br /> <br /> <br />
							<p style="text-align: center;">Issued by</p>
							<br /> <br /> <br /> <br /> <br />
							<p class="pull-right" style="text-align: center;">
								Chief Engineer <br /> <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
							</p>
						</div>
					</div>
				</div>
				<!---  01 Page Ends Here --->
				<!---  02 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b>Qualification criteria</b>
						</h2>
						<p>
							<b>1&emsp;&emsp; To qualify for award of the Contract, each
								Prime contractor in the same name and style (tenderer), in its
								name must have in the last five years</b>
						</p>
						<ul>
							<li>(a) Achieved in <b>"any one financial year"</b> a
								financial turnover (in all classes of civil engineering
								construction works) of construction work of at least <b>60%</b>
								(Sixty percent) of the probable amount of contract for which bid
								has been invited.
							</li>
							<li>(b) (i) Satisfactorily completed at least one similar
								work equal in value <b>50%</b> (fifty percent) <mark>of</mark>the
								Probable amount of contract as on date of submission of
								financial offer.
							</li>
						</ul>
						<p style="text-align: center;">OR</p>
						<p>(ii) Satisfactorily completed at least two similar works
							each costing minimum 40% (forty percent) of the probable amount
							of contract for which the tender is invited as on date of
							submission of financial offer.</p>
						<p style="text-align: center;">OR</p>
						<p>(iii) Satisfactorily executing at least one similar work
							having received payment of value not less than 60% (sixty
							percent) of the value of probable amount of contract as on date
							of submission of financial offer.</p>
						<p>Note :- 1) The turn over shall be indexed at the compounded
							rate of 10% (Ten percent)for each earlier years.</p>
						<p>
							(ii) The value of completed work shall be updated to the value of
							current financial year @ compounded rate of 10% (Ten percent) per
							year from completion year of work. The
							<mark>completion</mark>
							year shall be taken as base year.
						</p>
						<p>
							<b>(iii) Similar work means B.T. Pavement Construction</b>
						</p>
						<p>
							<b>2&emsp;&emsp; (a)</b> Each tenderer must enclose.
						</p>
						<ul>
							<li><b>(i)</b> Copy of certificate issued by competent
								authority of the department in respect of Income Tax return,
								Balance Sheet, Profit & Loss Account including audit report of
								chartered accountant for the last 5 years.</li>
							<li><b>(ii)</b> Other certificates as required by department</li>
							<li><b>(iii)</b> An affidavit that all the information
								furnished with the pre qualification document is correct in all
								respects; and</li>
						</ul>
						<p>
							<b>&emsp;&emsp;&emsp;(b)</b> Each tenderer <b>MUST</b> submit
							detail information regarding -
						</p>
						<ul>
							<li><b>(i)</b> Availability for construction Plants and
								machineries, Key equipments required for establishing
								laboratories to perform mandatory tests at the prescribed
								frequency owned/lease/on hire, as stated in the <b>enclosed
									list</b></li>
							<li><b>(ii)</b> Availability of consultancy firm/technical
								personals for construction supervision and quality control of
								the work as stated in the <b>enclosed list</b></li>
						</ul>
						<p>
							<b>3&emsp;&emsp;</b> Tenderer who meets the minimum qualification
							criteria will be qualified only if their <b>available bid
								capacity</b> for construction work is equal to or more than the
							probable amount of contract. The available bid capacity will be
							calculated as under:
						</p>
						<p style="text-align: center;">
							<b>Assessed Available Bid capacity = (A*N*M - B)</b>
						</p>
					</div>
				</div>
				<!---  02 Page Ends Here --->
				<!---  03 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<p>
							<b>where,</b>
						</p>
						<ul>
							<li><b>A</b> = Maximum value of all civil engineering work
								executed in “ any one financial year” during the last five
								year (updated to the price level at the current financial year
								at the compounded rate of 10% (Ten percent)a year taking into
								account the completed as well as work in progress.</li>
							<li><b>N</b> = Number of years prescribed for completion of
								the works for which tender is invited (period up to 6 months to
								be taken as half-year and more than 6 months as one year). Any
								period beyond 12 months, the period actually mentioned in the
								N.I.T. shall be considered.</li>
							<li><b>M</b> = 2.5</li>
							<li><b>B</b> = Value, of existing commitments and on-going
								works be completed during the period of completion of the work
								for which tender is invited (period up to 6 months to be taken
								as half-year and more than 6 months as one year). Any period
								beyond 12 months, the period actually mentioned in the N.I.T.
								shall be considered.</li>
						</ul>
						<p>
							<b>Note:</b> <i>The statements showing the value of existing
								commitments and on-going works, as well as the stipulated period
								of completion remaining, for each of the works so listed should
								be countersigned by the Engineer-in-charge, not below the rank
								of an Executive Engineer or equivalent.</i>
						</p>
						<p>
							<b>4&emsp;&emsp;Even though the tenderer meet the above
								qualifying criteria, they are subject to be disqualified if they
								have:</b>
						</p>
						<p>(i) Made misleading, incorrect or false representations in
							the forms, statements, affidavits and attachments submitted in
							proof of the qualification requirements.</p>
						<p style="text-align: center;">
							<b>And/or</b>
						</p>
						<p>(ii) Record of poor performance such as abandoning the
							works, not properly completing the contract, unsatisfactory
							quality of work, inordinate delays in completion, claim and
							litigation history, or financial failures etc in any department
							of Govt. of Chhattisgarh or the state Govt. organization
							/services/corporations/local body etc.(by whatever names these
							are called) within State territory of Chhattisgarh.</p>
					</div>
				</div>
				<!---  03 Page Ends Here --->
				<!---  04 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b><u>ANNEXURE-1</u></b>
							</p>
							<p>
								<b>5&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Qualification
									Information</b>
							</p>
							<br />
							<table class="table table-bordered" style="font-size: 14px;">
								<tr>
									<td rowspan="4">1.1</td>
									<td colspan="2" style="text-align: left;">Constitution or
										legal status of Bidder<i>[attach copy]</i>
									</td>
									<td colspan="4" style="width: 600px;"></td>
								</tr>
								<tr>
									<td colspan="2" style="text-align: left; width: 150px;">Place
										of registration of Firm/ Company (in case of other than
										individuals)</td>
									<td colspan="4"></td>
								</tr>
								<tr>
									<td colspan="2" style="text-align: left; width: 150px;">Principal
										place of business:</td>
									<td colspan="4"></td>
								</tr>
								<tr>
									<td colspan="2" style="text-align: left; width: 150px;">Name
										of Power of attorney holder of signatory of Bid (bidder)<i>[attach
											copy]</i>
									</td>
									<td colspan="4"></td>
								</tr>
								<tr>
									<td rowspan="7">1.2</td>
									<td colspan="2" rowspan="7"
										style="text-align: left; width: 150px;">Total annual
										volume of civil engineering construction work executed and
										payments received each year in the immediate five years
										preceding the year in which tenders are invited. (Attach
										certificate from Chartered Accountant or certificate issued by
										Engineer in Chief's committee )- indexed @ 10% (ten percent)
										compounded per year</td>
									<td rowspan="2">Financial Year</td>
									<td colspan="3"><b>(Rs. in crores)</b></td>
								</tr>
								<tr>
									<td>"Civil engineering construction work"<br />Turn over
										in the year
									</td>
									<td>Add for indexing</td>
									<td>Total</td>

								</tr>
								<tr>
									<td>13-14</td>
									<td></td>
									<td>1.61</td>
									<td></td>

								</tr>
								<tr>
									<td>14-15</td>
									<td></td>
									<td>1.46</td>
									<td></td>

								</tr>
								<tr>
									<td>15-16</td>
									<td></td>
									<td>1.33</td>
									<td></td>

								</tr>
								<tr>
									<td>16-17</td>
									<td></td>
									<td>1.21</td>
									<td></td>
								</tr>
								<tr>
									<td>17-18</td>
									<td></td>
									<td>1.10</td>
									<td></td>
								</tr>
							</table>
							<br />

							<p>
								<b>Note: -</b>
							</p>
							<p>
								<i>1.1 Proprietary firm, partnership firm with the
									certificate of registration by register, article and Memorandum
									of Association with Certificate of Incorporation.</i>
							</p>
							<p>
								<i>1.2 Mention and highlights the year, which the tenderer
									considers for evaluation for the Committee.</i>
							</p>
					</div>
				</div>
				<!---  04 Page Ends Here --->
				<!---  05 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b><u>ANNEXURE-2</u></b>
							</p>
							<h2>
								<b>Information regarding minimum one similar work, Performed
									by Prime Contractor.</b>
							</h2>
							<p>
								<b>(i) One Work completed as similar work during last five
									years</b>
							</p>
							<p>
								<b>(ii) Or being executing one such similar work</b>
							</p>
							<br />
							<table class="table table-bordered" style="font-size: 14px;">
								<thead>
									<tr>
										<th>Sno</th>
										<th>Project Name</th>
										<th>Name of Employer</th>
										<th>Value of contract</th>
										<th>Contract No.</th>
										<th>Date of Issue of Work Order</th>
										<th>Stipulated Date of Completion</th>
										<th>Actual Date of Completion</th>
										<th>Value of work done</th>
										<th>Remarks <br />Remarks explaining reasons for Delay,
											if any; and the amount of deductions due to delay also
											mention if any claim or dispute is pending in any forum.
										</th>
									</tr>
								</thead>
								<tr>
									<td>1</td>
									<td>2</td>
									<td>3</td>
									<td>4</td>
									<td>5</td>
									<td>6</td>
									<td>7</td>
									<td>8</td>
									<td>9</td>
									<td>10</td>
								</tr>
								<tr>
									<td height=300></td>
									<td height=300></td>
									<td height=300></td>
									<td height=300></td>
									<td height=300></td>
									<td height=300></td>
									<td height=300></td>
									<td height=300></td>
									<td height=300></td>
									<td height=300></td>
								</tr>
							</table>
							<br />
							<p>
								<b>Note:</b>
							</p>
							<p>
								<i>(i) Attach certificates from the Engineer in charge not
									below the rank of Executive Engineer or equivalent</i>
							</p>
							<p>
								<i>(ii) Tenderer may attach certified copies of work order
									and completion certificate issued by Engineer in charge not
									below the rank of Executive Engineer.</i>
							</p>
					</div>
				</div>
				<!---  05 Page Ends Here --->
				<!---  06 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b><u>ANNEXURE-3</u></b>
							</p>
							<h2>
								<b>Work performed by Prime Contractor on all classes of
									Civil Engineering Construction Works over the last five years </b>
							</h2>
							<br />
							<table class="table table-bordered"
								style="font-size: 10.65px; margin-left: -26px;">
								<thead>
									<tr>
										<th rowspan="2">Sno</th>
										<th rowspan="2">Project Name</th>
										<th rowspan="2">Name of Employer</th>
										<th rowspan="2">Description of work</th>
										<th rowspan="2">Value of contract</th>
										<th rowspan="2">Contract No.</th>
										<th rowspan="2">Date of Issue of Work Order</th>
										<th rowspan="2">Stipulated Date of Completion</th>
										<th rowspan="2">Actual Date of Completion</th>
										<th colspan="6">Year wise value of work done as per
											certificate of employer <br />Rs. In Lacs
										</th>
										<th rowspan="2">Remarks explaining reasons for Delay, if
											any; and the amount of deductions due to delay also mention
											if any claim or dispute is pending in any forum.</th>
									</tr>
									<tr>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
									</tr>
								</thead>
								<tr>
									<td>1</td>
									<td>2</td>
									<td>3</td>
									<td>4</td>
									<td>5</td>
									<td>6</td>
									<td>7</td>
									<td>8</td>
									<td>9</td>
									<td>10</td>
									<td>11</td>
									<td>12</td>
									<td>13</td>
									<td>14</td>
									<td>15</td>
									<td>16</td>
								</tr>
								<tr>
									<td height=300></td>
									<td height=300></td>
									<td height=300></td>
									<td height=300></td>
									<td height=300></td>
									<td height=300></td>
									<td height=300></td>
									<td height=300></td>
									<td height=300></td>
									<td height=300></td>
									<td height=300></td>
									<td height=300></td>
									<td height=300></td>
									<td height=300></td>
									<td height=300></td>
									<td height=300></td>
								</tr>
							</table>
							<br />
							<p>
								<b>Note: </b>
							</p>
							<p>
								<i>(i) Attach certificates issued by competent authority of
									the Department duly signed by an officer not below the rank of
									Executive Engineer.</i>
							</p>
							<p>
								<i>(ii) T.D.S. or other certificates shall not be considered
									for calculation of bid capacity.</i>
							</p>
					</div>
				</div>
				<!---  06 Page Ends Here --->
				<!---  07 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b><u>ANNEXURE-4</u></b>
							</p>
							<h2>
								<b>Existing commitments and on going all classes of civil
									engineering construction works, by the Prime contractor.</b>
							</h2>
							<br />
							<table class="table table-bordered"
								style="font-size: 8.85px; margin-left: -26px;">
								<thead>
									<tr>
										<th>S.no.</th>
										<th>Project Name</th>
										<th>Description of work</th>
										<th>Contract No & Year</th>
										<th>Name & address of the employer</th>
										<th>Value of contract <br />(Rs. Lakhs)
										</th>
										<th>Date of Issue of Work Order</th>
										<th>Stipulated Date of Completion</th>
										<th>Stipulated period of completion in months</th>
										<th>Anticipated date of completion</th>
										<th>Value of work done up to date of issue of N.I.T. <br />(Rs.
											Lakhs)**
										</th>
										<th>Probable value of works remaining to be completed <br />(Rs.
											Lakhs) **
										</th>
										<th>Anticipated months required for completion of balance
											works</th>
										<th>Value of claims or dispute if any, pending</th>
									</tr>
								</thead>
								<tr>
									<td>1</td>
									<td>2</td>
									<td>3</td>
									<td>4</td>
									<td>5</td>
									<td>6</td>
									<td>7</td>
									<td>8</td>
									<td>9</td>
									<td>10</td>
									<td>11</td>
									<td>12</td>
									<td>13</td>
									<td>14</td>
								</tr>
								<tr>
									<td height=200></td>
									<td height=200></td>
									<td height=200></td>
									<td height=200></td>
									<td height=200></td>
									<td height=200></td>
									<td height=200></td>
									<td height=200></td>
									<td height=200></td>
									<td height=200></td>
									<td height=200></td>
									<td height=200></td>
									<td height=200></td>
									<td height=200></td>
								</tr>
							</table>
							<br />
							<p>
								<b>Note: </b>
							</p>
							<p>
								<i>(i) ** Enclose certificates from Engineer(s) in charge
									(Not below the rank of Executive Engineer or equivalent) for
									value of work remaining to be completed, value of work done,
									anticipated date of completion.</i>
							</p>
							<p>
								<i>(ii ) Tenderer may attach certified copies of work order
									issued by Engineer in charge not below the rank of Executive
									Engineer</i>
							</p>
							<p>
								<i>(iii) Tenderer must submit, calculation of bid capacity
									in separate sheet.</i>
							<p>
					</div>
				</div>
				<!---  07 Page Ends Here --->
				<!---  08 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b><u>ANNEXURE-5</u></b>
							</p>
							<h2>
								<b> Availability of Major items of Contractor's Equipment
									proposed for carrying out the Works. List all information
									requested below.</b>
								</p>
								<br />
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>Item of Equipment</th>
											<th>Total number available</th>
											<th>Description, make, and age (Years), and capacity</th>
											<th>Condition (new, good, poor) and number available</th>
											<th>Nos.(i)Owned, (ii) leased, or (iii) to be purchased</th>
											<th>If these are in use in some work, mention the
												details.</th>
											<th>No. of equipments proposed to be utilised <i><u>in
														this work</u></i><br /> (Out of total Nos.)
											</th>
										</tr>
									</thead>
									<tr>
										<td>1</td>
										<td>2</td>
										<td>3</td>
										<td>4</td>
										<td>5</td>
										<td>6</td>
										<td>7</td>
									</tr>
									<tr>
										<td height=300></td>
										<td height=300></td>
										<td height=300></td>
										<td height=300></td>
										<td height=300></td>
										<td height=300></td>
										<td height=300></td>
									</tr>
								</table>
								<br />
								<p>
									<b>Note:-</b>
								</p>
								<p>
									<i>(i) Enclose the certificate ( invoice or relevant
										documents/ registered agreements ) for owned, leased and
										hiring of above equipments.</i>
								</p>
								<p>
									<i>(ii) Enclose the invoice or relevant documents of each
										equipments to verify the age.</i>
								</p>
					</div>
				</div>
				<!---  08 Page Ends Here --->
				<!---  09 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b><u>ANNEXURE-6</u></b>
							</p>
							<h2>
								<b> Qualifications of consultants/each technical personnel
									proposed for the Contract.</b>
								</p>
								<br />
								<table class="table table-bordered">
									<thead>
										<tr>
											<th rowspan="2">Position</th>
											<th rowspan="2">Name</th>
											<th rowspan="2">Qualification</th>
											<th rowspan="2">Date from which they are working in the
												bidders organisation</th>
											<th colspan="4">Years of experience</th>
											<th rowspan="2">Remark</th>
										</tr>
										<tr>
											<th>Road Works</th>
											<th>Building Works</th>
											<th>Bridge works</th>
											<th>Others</th>
										</tr>
									</thead>
									<tr>
										<td>1</td>
										<td>2</td>
										<td>3</td>
										<td>4</td>
										<td>5(a)</td>
										<td>5(b)</td>
										<td>5(c)</td>
										<td>5(d)</td>
										<td>6</td>
									</tr>
									<tr>
										<td height=200></td>
										<td height=200></td>
										<td height=200></td>
										<td height=200></td>
										<td height=200></td>
										<td height=200></td>
										<td height=200></td>
										<td height=200></td>
										<td height=200></td>
									</tr>
								</table>
								<br />
								<p>
									<b>Note :-</b>
								</p>
								<p>
									<i>1- Bidder shall submit certificates (copy of mark
										sheet/degree) of technical personnels which are working in
										bidder's organisation</i>
								</p>
								<p>
									<i>2- If any personal is proposed to be engaged, furnish
										details here under:- (if necessary use separate sheet for each
										-for C.V.)(Enclose certificates)</i>
								</p>
								<p>
									<i>3- If any technical persons are to be changed during the
										construction periods, than it can be changed with prior
										intimation to the Engineer in charge.</i>
								</p>
					</div>
				</div>
				<!---  09 Page Ends Here --->
				<!---  10 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b><u>ANNEXURE-7</u></b>
							</p>
							<h2>
								<b>Financial reports for the immediate previous five years
									(base year 2017-18): balance sheets, profit and loss
									statements, audited auditors' reports, etc., list below and
									attach copies.</b>
								</p>
								<br />
								<table class="table table-bordered">
									<thead>
										<tr>
											<th width=90>Year</th>
											<th>Income Tax Clearance Certificate (optional)</th>
											<th>Balance Sheet</th>
											<th>Profit & loss statements</th>
											<th>Reserve brought forward in any</th>
											<th>Net credit Balance if any [for debit show (-)]</th>
											<th>Auditors' Report</th>
											<th>Other information if the bidder wishes to submit</th>
										</tr>
									</thead>
									<tr>
										<td>1</td>
										<td>2</td>
										<td>3</td>
										<td>4</td>
										<td>5</td>
										<td>6</td>
										<td>7</td>
										<td>8</td>
									</tr>
									<tr>
										<td>2013-14</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>2014-15</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>2015-16</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>2016-17</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>2017-18</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</table>
					</div>
				</div>
				<!---  10 Page Ends Here --->
				<!---  11 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b><u>ANNEXURE-8</u></b>
							</p>
							<h2>
								<b>Information on current claims, arbitration, litigation in
									which the Bidder is involved.</b>
								</p>
								<br />
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>Sl. No.</th>
											<th>Name of Other party(s)</th>
											<th>Agt. No. date year and Deptt.</th>
											<th>Brief of cause of claims, arbitration /dispute (give
												reference of contract details)</th>
											<th>Where Litigation pending (in the
												department/Court/arbitration) (mention Deptt./Court
												/Arbitration)</th>
											<th>Amount involved/ claimed</th>
										</tr>
									</thead>
									<tr>
										<td height=200></td>
										<td height=200></td>
										<td height=200></td>
										<td height=200></td>
										<td height=200></td>
										<td height=200></td>
									</tr>
								</table>
								<p>&emsp;&emsp;&emsp;&emsp;Can use separate sheets for each
									agreements if necessary.</p>
					</div>
				</div>
				<!---  11 Page Ends Here --->
				<!---  12 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b><u>ANNEXURE-9</u></b>
							</p>
							<h2>
								<b>List of key plant & Equipment to be deployed on Contract
									Work (Building/Bridge) to be filled by the Superintending
									Engineer as per their requirements</b>
								</p>
								<br />
								<table class="table table-bordered">
									<thead>
										<tr>
											<th rowspan="2">Sl.</th>
											<th rowspan="2">Type of Equipment</th>
											<th rowspan="2">Maximum age as on 1.04.15 (years)</th>
											<th colspan="2">Contract Package Size</th>
										</tr>
										<tr>
											<th>From Rs. 5 Crores to Rs.10 Crores ?</th>
											<th>From Rs. Rs.10 Crores to above ?</th>
										</tr>
									</thead>
									<tr>
										<td>1</td>
										<td>2</td>
										<td>3</td>
										<td>4</td>
										<td>7</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>?</td>
										<td>?</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>Total</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
								</table>
								<p class="na">Not Applicable</p>
					</div>
				</div>
				<!---  12 Page Ends Here --->
				<!---  13 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b><u>ANNEXURE-10</u></b>
							</p>
							<h2>
								<b>List of key plant & Equipment to be deployed on Contract
									Work (Road)</b>
								</p>
								<br />
								<table class="table table-bordered">
									<thead>
										<tr>
											<th rowspan="2">Sl.</th>
											<th rowspan="2">Type of Equipment</th>
											<th rowspan="2">Maximum age as on <br />1.4.2018
												(years)
											</th>
											<th>Contract Package Size</th>
										</tr>
										<tr>
											<th>From Rs. 10 Cores to Above</th>
										</tr>
									</thead>
									<tr>
										<td>1</td>
										<td>2</td>
										<td>3</td>
										<td>5</td>
									</tr>
									<tr>
										<td>1</td>
										<td>Motor Grader</td>
										<td>5</td>
										<td>1</td>
									</tr>
									<tr>
										<td>2</td>
										<td>Dozer</td>
										<td>5</td>
										<td>2</td>
									</tr>
									<tr>
										<td>3</td>
										<td>Front end Loader</td>
										<td>5</td>
										<td>2</td>
									</tr>
									<tr>
										<td>4</td>
										<td>Smooth Wheel Roller</td>
										<td>5</td>
										<td>3</td>
									</tr>
									<tr>
										<td>5</td>
										<td>Vibratory Roller</td>
										<td>5</td>
										<td>3</td>
									</tr>
									<tr>
										<td>6</td>
										<td>Hot Mix Plant with <br />Electronic Controls <br />(Minimum
											40/60 TPH Capacity)
										</td>
										<td>5</td>
										<td>1</td>
									</tr>
									<tr>
										<td>7</td>
										<td>Sensor Paver Finisher</td>
										<td>5</td>
										<td>1</td>
									</tr>
									<tr>
										<td>8</td>
										<td>Water Tanker</td>
										<td>5</td>
										<td>2</td>
									</tr>
									<tr>
										<td>9</td>
										<td>Bitumen Sprayer</td>
										<td>5-7</td>
										<td>2</td>
									</tr>
									<tr>
										<td>10</td>
										<td>Tandem Roller</td>
										<td>5</td>
										<td>1</td>
									</tr>
									<tr>
										<td>11</td>
										<td>Emulsion pressure <br />Distributor
										</td>
										<td>5</td>
										<td>2</td>
									</tr>
									<tr>
										<td>12</td>
										<td>Needle vibrator</td>
										<td>5</td>
										<td>5</td>
									</tr>
									<tr>
										<td>13</td>
										<td>Plate vibrator</td>
										<td>5</td>
										<td>2</td>
									</tr>
								</table>
								<br />
								<p>
									<b>Note: - The list & other Details of the equipment and
										plants as mentioned above are tentative. S.E. can modify the
										above list of the plant and equipment as per their
										requirements.</b>
								</p>
					</div>
				</div>
				<!---  13 Page Ends Here --->
				<!---  14 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b><u>ANNEXURE-11</u></b>
							</p>
							<h2>
								<b>List of Technical person to be deployed on Contract work</u>
							</h2>
							<br />
							<table class="table table-bordered">
								<thead>
									<tr>
										<th rowspan="2">Sl.</th>
										<th rowspan="2">Personnel</th>
										<th rowspan="2">Qualification</th>
										<th>Contract Package Size</th>
									</tr>
									<tr>
										<th>From Rs. 10 Crores to above ?</th>
									</tr>
								</thead>
								<tr>
									<td>1</td>
									<td>2</td>
									<td>3</td>
									<td>4</td>
								</tr>
								<tr>
									<td>1</td>
									<td>Project Manager</td>
									<td>B.E. Civil+15years Exp. (5 years as manager)</td>
									<td>1</td>
								</tr>
								<tr>
									<td>2</td>
									<td>Site Engineer</td>
									<td>B.E. Civil+10years Exp. (5 years in Road construction</td>
									<td>3</td>
								</tr>
								<tr>
									<td>3</td>
									<td>Plant Engineer</td>
									<td>B.E. Mech.+10 Years Exp. or Dip. Mech+15 years Exp.</td>
									<td>1</td>
								</tr>
								<tr>
									<td>4</td>
									<td>Quantity Surveyor</td>
									<td>B.E. Civil+7 Years Exp. or Dip. Civil+10 Years Exp.</td>
									<td>2</td>
								</tr>
								<tr>
									<td>5</td>
									<td>Soil & Material Engineer</td>
									<td>B.E. Civil +10 years Exp.</td>
									<td>1</td>
								</tr>
								<tr>
									<td>6</td>
									<td>Survey Engineer</td>
									<td>B.E. Civil +5 years Exp. or Dip. Civil+8 years Exp.</td>
									<td>1</td>
								</tr>
							</table>
							<br />
							<p>
								<b>Note:- The list of the Technical persons as mentioned
									above are tentative. S.E. can modify the above list of the
									Technical persons as per their requirements.</b>
							</p>
					</div>
				</div>
				<!---  14 Page Ends Here --->
				<!---  15 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b><u>ANNEXURE-12</u></b>
							</p>
							<h2>
								<b><u>CONTACT PERSONS</u></b>
								</p>
								<br />
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>S.no.</th>
											<th>Name of the authority issuing certificate</th>
											<th>Office Address</th>
											<th>STD Code</th>
											<th>Mobile No. / Phone No. Office/ residence</th>
											<th>Email ID</th>
										</tr>
										<tr>
											<th>1</th>
											<th>2</th>
											<th>3</th>
											<th>4</th>
											<th>5</th>
											<th>6</th>
										</tr>
									</thead>
									<tr>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
									</tr>
									<tr>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
									</tr>
									<tr>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
									</tr>
									<tr>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
									</tr>
									<tr>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
									</tr>
									<tr>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
										<td>&nbsp;<br />&nbsp;
										</td>
									</tr>
								</table>
					</div>
				</div>
				<!---  15 Page Ends Here --->
				<!---  16 Page Starts Here --->
				<div class="page" id="pageNumber">
					<div class="subpage">
						<h2>
							<b><u>ANNEXURE-13</u></b>
							</p>
							<h2>
								<b><u>Affidavit</u></b>
							</h2>
							<p>&emsp;&emsp;I
								................................................ S/o
								......................................................................
								Aged .................. years ............................
								resident
								...........................................................................
								of (address
								.....................................................................................................................................................)
								(For and on behalf of
								..................................................................),
								do here by and herewith solemnly affirm / state on oath that : -</p>
							<p>1. All documents and Information furnished are correct in
								all respects to the best of my knowledge and belief</p>
							<p>2. I have not suppressed or omitted any information as is
								required.</p>
							<p>3. I am/We are/none or our partner of director is neither
								black listed nor debarred by Govt.of India/Other State Govt.
								Departments/Chhattisgarh State Govt. Departments/Semi Govt.
								Departments (C.G. & other Govt.).</p>
							<p>4. I do here by and herewith solemnly affirm/state on oath
								that all information furnished in annexure 5 is correct. Plants
								& machineries shown in said annexure are hire/leased/owned by
								me/our firm/our company. Plants & machineries which are shown in
								the said annexure of P.Q. document will be deployed on the work
								before 15 days of start of the activity (requiring the use of
								plant/machinery) as mentioned in work programme given by me/our
								firm/our company.</p>
							<p>5. I do here by and here with solemnly affirm/state on
								oath that all information furnished in annexure 6 is correct.
								Technical persons shown in said annexure are employed with
								me/our firm/our company. Technical persons which are not shown
								in the said annexure, but required as per annexure 11 will be
								arranged by me before signing the agreement, when work is
								allotted to me/our firm/our company.</p>
							<p>6. I hereby authorize the UAD Officials to get all the
								documents verified from appropriate source(s).</p>
							<br /> <br /> <br />
							<p class="pull-right" style="text-align: center;">
								Deponent <br />(..............................................)
								<br />Authorized signatory / <br />for and on behalf of <br />.............................
								<br />(affix seal)
							</p>
							<br /> <br /> <br /> <br /> <br /> <br /> <br />
							<p style="text-align: center;">
								<b>Verification</b>
							</p>
							<p>I ........................................ S/o
								................................................... do here by
								affirm that the contents stated in Para 1 to 6 above are true to
								the best of my knowledge and believe and are based on my / our
								record. Verified that this .................... date of
								.................... 200.... at (Place) ....................</p>
							<br /> <br />
							<p>
								Seal of attestation by a Public Deponent <br />Notary with date
							</p>
							<br />
							<p class="pull-right" style="text-align: center;">
								(...........................................................) <br />Authorized
								signature / <br />for and on behalf of
								.......................... <br /> <b>(affix seal)</b>
							</p>
					</div>
				</div>
				<!---  16 Page Ends Here --->
			</div>


		</form>
	</div>
</div>


<!-- Start JSP Necessary Tags -->
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
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-content padding">
			<header align="center">
				<button class="button" onclick="ExportPdfFormA()">Download
					as Pdf</button>
				<button class="button" onclick="exportHTMLFormA();">Download
					as Word</button>
				<button class="button" onclick="backTenderPage();">
					<i class="fa fa-chevron-circle-left padding-right-5"></i>
					<spring:message code="works.management.back" text="" />
				</button>
				<!--<button class="button" onclick="exportTableToExcel('myCanvas', 'Form A')">Export To Excel File</button>-->
			</header>
			<!-- Scroll to Top button -->
			<button onclick="topFunction()" id="myBtn" title="Go to top">Top</button>

			<form:form action="" method="post" id="formA">
				<!---  01 Page Start Here --->
				<div class="report" id="myCanvas">
					<div class="page" id="pageNumber">
						<div class="subpage">
							<h1>
								<!-- <small><b>APPENDIX 2.13 (See Paragraph 2.019)</b></small><br /> -->
								<b>FORM 'A'<br />${userSession.getCurrent().organisation.ONlsOrgname}<br /></b>
							</h1>
							<table style="font-size: 0.92em; text-align: left;">
								<tr>
									<td>Issued to Shri/M/s. &emsp;&emsp;&emsp;</td>
									<td colspan="2">.........................................................................................................................</td>
								</tr>
								<tr>
									<td>Class of Contractor.</td>
									<td>Registration
										No.......................................................</td>
									<td>Date................................</td>
								</tr>
								<tr>
									<td>Name of Work</td>
									<td colspan="2"><b>${command.tenderWorksForms.workName}</b></td>
								</tr>
								<tr>
									<td>Amount of Contract</td>
									<td colspan="2"><b>Rs.
											${command.tenderWorksForms.workEstimateAmt}</b></td>
								</tr>
								<tr>
									<td>Amount of EMD/FDR Rs.</td>
									<td colspan="2"><b>Rs.
											${command.tenderWorksForms.tenderSecAmt} </b></td>
								</tr>
								<tr>
									<td>Cost of Tender Form</td>
									<td colspan="2"><b>Rs.
											${command.tenderWorksForms.tenderFeeAmt} </b>(to be paid online)</td>
								</tr>
								<tr>
									<td>Vide M.R. No. & Date</td>
									<td colspan="2">..........................................</td>
								</tr>
								<tr>
									<td colspan="3">Time allowed for Completion <b>${command.tenderWorksForms.vendorWorkPeriod}</b>
										months from the reckoned date including/excluding rainy season
									</td>
								</tr>
								<tr>
									<td>Date of opening Tender</td>
									<td colspan="2">.........................................................................................................................</td>
								</tr>
							</table>
							<p>${userSession.getCurrent().organisation.ONlsOrgname}</p>
							<br /> <br>
							<h2>
								<b>PERCENTAGE RATE TENDER AND CONTRACT FOR WORKS<br />(Based
									on applicable Schedule of Rates)<br />General Rules and
									Direction for the Guidance of Contractors
								</b>
							</h2>
							<br>
							<p>
								<b>1.</b> Tenders must be invited for all works proposed to be
								given on contract unless the amount of works proposed to be
								given on contract is Rs 50,000 or less. The N.I.T. shall be
								posted in public places signed by the authority inviting the
								tenders.
							</p>
							<p>N.I.T. will state the work to be carried out as well as
								the date for submitting and opening tenders and the time allowed
								for carrying out the work, also the amount of earnest money to
								be deposited with the tender and the amount of the security
								deposit to be deposited by the successful tenderer & the
								percentage, if any to be deducted from bills, it will also state
								whether a refund of quarry fees, royalties and ground rents will
								be granted. Copies of the specifications, designs and drawings
								and a schedule of items quantities and rates of the various
								description of work and any other documents required in
								connection with the work signed for the purpose of
								identification by the authority competent to approve the tender
								shall also be open for inspection by the contractor at the
								office of the authority selling the tender forms during office
								hours.</p>
							<p>Further that the schedule of items along with the
								quantities and rates payable shall be attached to the tender
								documents and in the event of variation in rates given in such
								list with the schedule of Rates the rates given in the S.O.R.
								approved by the competent authority shall prevail.</p>
							<p>
								<b>2.</b> In the event of the tender being submitted by a firm
								it must be signed separately by each member thereof, in the
								event of the absence of any partner it must be signed on its
								behalf by a person holding a power of attorney authorizing him
								to do so, such power of attorney should be produced with the
								tender and it must disclose that the firm is duly registered
								under the Indian partnership Act.
							</p>
						</div>
					</div>
					<!---  01 Page Ends Here --->
					<!---  02 Page Starts Here --->
					<div class="page" id="pageNumber">
						<div class="subpage">
							<p>
								<b>3.</b> Any person who submits a tender shall fill up above or
								below the S.O.R. specified in rule he is willing to undertake
								the work. Only one rate of percentage above or below the S.O.R.
								on all the scheduled terms shall be named. Tenders that propose
								any alteration in the work specified in the said N.I.T. or in
								the time allowed for carrying out the work or which contain any
								other conditions of any sort will be liable to rejection.No
								single tender shall include more than one work but contractors
								who wish to tender for two or more works shall submit a seprate
								tender for each tenders shall have the name and number of the
								work to which they refer written outside the envelope .
							</p>

							<p>
								<b>4.</b> The authority receiving tenders or his duly authorised
								assistant will open tenders in the presence of any intending
								contractors who may be present at the time and will enter the
								amount of the several tenders in a comparative statement in a
								suitable form. Receipts for earnest money will be given to all
								tenderers except those whose tenders are rejected and whose
								earnest money is refunded on the day the tenders are opened
							</p>

							<p>
								<b>5.</b> The Officers competent to dispose of the tenders shall
								have right of rejecting all or any of the tenders <b>without
									assigning any reason thereof.</b>
							</p>
							<p>
								<b>6.</b> The memorandum of work tendered for be filled in and
								completed before the tender form is issued. If a form is issued
								to an intending tenderer without having been so filled in and
								completed he shall request the office to have this done before
								he completes and delivers his tender.
							</p>
							<h2>
								<b>Tender for Works</b>
							</h2>
							<p>
								I/We hereby tender for the execution, for the Governor of
								Chhattisgarh of the work specified in under written memorandum
								within time specified in such memorandum at <br />(In Figures):
								.......................................................................................
								<br />(In Words):
								.......................................................................................<br />Percent
								below / above/at par with the applicable Schedule of Rates and
								in accordance in all respects with the specifications, designs,
								drawings and instructions in writing referred to in rule 1
								thereof and in clause 12 of the annexed conditions and with such
								materials as are provided for by, and in all other respects in
								accordance with such conditions as far as applicable.
							</p>
							<h2>
								<b>Memorandum</b>
							</h2>
							<ul>
								<li>(a) Name of work :- <b>${command.tenderWorksForms.workName}</b></li>
								<li>(b) Cost of work put to Tender Rs. <b>${command.tenderWorksForms.workEstimateAmt}</b></li>
								<li>(c) Earnest money <b>Rs.${command.tenderWorksForms.tenderSecAmt}</b></li>
								<li>(d) Security deposit (including earnest money) 5%</li>
								<li>(e) Percentage, if any to deducted from
									bills(Performance Gurantee)
									.....................................</li>
								<li>(f) Time allowed for the work ....................
									months from the reckoned date including/excluding rainy season</li>
							</ul>
							<p>(From 16th June to 15th October) (Delete whichever is not
								applicable)</p>
							<p>
								Should this tender be accepted I/we hereby agree to abide by and
								fulfill all terms and provisions of the said condition of the
								contract annexed hereto as far as applicable or in default,
								thereof to forfeit & pay to the <b><u>
										${userSession.getCurrent().organisation.ONlsOrgname}</u></b> or his
								successors in office the sums of money mentioned in the said
								conditions. A separate sealed cover duly super scribed
								containing the sum of Rs. .................... as earnest money
								the full value of which is to be absolutely forfeited to the
								said <b><u>
										${userSession.getCurrent().organisation.ONlsOrgname}</u></b> or his
								successors in office without prejudice to any other rights or
								remedies of the said <b><u>
										${userSession.getCurrent().organisation.ONlsOrgname}</u></b> or his
								successors in office should I/we fail to commence the work
								specified in the above memorandum or should I/we not deposit the
								full amount of security deposit specified in the above
								memorandum, in accordance with clause 1 of the said conditions
								of the contract,
							</p>

						</div>
					</div>
					<!---  02 Page Ends Here --->
					<!---  03 Page Ends Here --->
					<div class="page" id="pageNumber">
						<div class="subpage">

							<p>
								otherwise the said sum of Rs. .................... shall be
								retained by <b><u>
										${userSession.getCurrent().organisation.ONlsOrgname}</u></b> on
								account of such security deposit as aforesaid or the full value
								of which shall be retained by <b><u>
										${userSession.getCurrent().organisation.ONlsOrgname}</u></b> Name on
								account of the security deposit specified in clause 1 of the
								said conditions of the contract.
							</p>
							<br>
							<p>
								<span class="pull-left">Signature of witness to
									Contractor's Signature<br /> <br /> <br /> <br />Dated
									the&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;day of<br />..............................&emsp;&emsp;
									2015
								</span> <span class="pull-right">Signature of the Contractor
									before<br />submission of tender (with name and seal)<br /> <br />
									<br />Dated the&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;day of<br />..............................&emsp;&emsp;
									2015
								</span> <br /> <br /> <br />
							</p>
							<br /> <br /> <br /> <br />
							<p>Name and Address of the witness:
								......................................................................</p>
							<p>Occupation of the witness:
								...................................................................................</p>
							<p>
								The above tender is hereby accepted by me for and on behalf of
								the <b><u>
										${userSession.getCurrent().organisation.ONlsOrgname}</u></b><br>
								<br>
							</p>
							<p>Dated the .................... day of ....................
								2015</p>

							<p class="pull-right" style="text-align: center;">
								<br /> <br />.................................................................
								<br />Signature of the Officer by whom accepted <br />With
								designation and seal of office
							</p>
							<br /> <br /> <br /> <br /> <br /> <br />
							<h2>
								<b>CONDITIONS OF CONTRACT<br />Definition
								</b>
							</h2>
							<p>
								<b>1.</b> The contract means the documents, forming the notice
								inviting tenders and tender documents submitted by the tenderer
								and the acceptance thereof including the formal agreement
								executed between the <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
								and the contractor.
							</p>
							<p>
								<b>2.</b> In the contract the following expressions shall unless
								otherwise required by the context have the meanings hereby
								respectively assigned to them: -
							</p>
							<ul>
								<li><b>(a)</b> The expression "works" or "work" shall
									unless thereby mean something either in the subject or context
									repugnant to such construction be construed and taken to mean
									the works or by virtue of the contract contracted to be
									executed whether temporary or permanent and whether original,
									altered, substituted or additional.</li>
								<li><b>(b)</b> The "site" shall mean the land and/or other
									places on, into or through which work is to be executed under
									the contract or any adjacent land path or street through which
									work is to be executed under the contract or any adjacent land,
									path, or street which maybe allotted or used for the purpose of
									carrying out the contract.</li>
								<li><b>(c)</b> The <b><u>"Commissinor/CMO,
											${userSession.getCurrent().organisation.ONlsOrgname}"</u></b> means <b><u>Commissinor/Chief
											Municipal officer of the,
											${userSession.getCurrent().organisation.ONlsOrgname}</u></b> and his
									successors in Office.</li>
								<li><b>(d)</b> The "Officers/Engineer-in-Charge" means the
									Commissioner/Chief Municipal officer as the case may be who
									shall supervise and be in charge of the work and who shall sign
									the contract on behalf of the <b><u>Commissinor/CMO,
											${userSession.getCurrent().organisation.ONlsOrgname}.</u></b></li>

							</ul>
						</div>
					</div>
					<!---  03 Page Ends Here --->
					<!---  04 Page Ends Here --->
					<div class="page" id="pageNumber">
						<div class="subpage">
							<ul>
								<li><b>(e)</b> "Competent Authority means Commissioner/CMO,
									MIC/PIC, General Body/Parishad as the case may be.</u></b></li>

								<li><b>(f)</b> The term "Engineer-In Charge" means the
									Engineer of the
									${userSession.getCurrent().organisation.ONlsOrgname}.</li>
								<%-- <li><b>(f)</b> The term "Superintending Engineer" means the
									Superintending Engineer <b><u>${userSession.getCurrent().organisation.ONlsOrgname}.</u></b></li>
								<li><b>(g)</b> The term "Executive Engineer" means the
									Executive Engineer, <b><u>${userSession.getCurrent().organisation.ONlsOrgname}.</u></b></li>
								<li><b>(h)</b> The term "Assistant Engineer" means the
									Assistant Engineer <b><u>${userSession.getCurrent().organisation.ONlsOrgname}.</u></b></li>
								<li><b>(i)</b> The word "Sub Engineer" shall mean "Sub
									Engineer" <b><u>${userSession.getCurrent().organisation.ONlsOrgname}.</u></b></li> --%>
							</ul>
							<br> <br>
							<p>
								<b>Note:-</b> "Words" importing the singular number include
								plural number and vice-versa,
							</p>
							<br> <br>
							<h2>
								<b><u>SECURITY DEPOSIT</u></b>
							</h2>
							<p>
								<b>Clause 1 -</b> The person whose tender may be accepted
								(hereinafter called the contractor which expression shall unless
								excluded by or repugnant to the context include his heirs
								executers, administrators representatives and assigns) shall
								permit <b><u>${userSession.getCurrent().organisation.ONlsOrgname}</u></b>
								at the time of making any payments to him for the value of work
								done under the contract to deduct the security deposit as under.
							</p>
							<p>The Security Deposit to be taken for the due performance
								of the contract under the terms & conditions printed on the
								tender form will be the earnest money plus a deduction of 5
								(Five) percent from the payment made in the running bills.</p>
							<h2>
								<b><u>COMPENSATION FOR DELAY</u></b>
							</h2>
							<p>
								<b>Clause 2 -</b> The time allowed for carrying out the work, as
								entered in the tender form, shall be strictly observed by the
								contractor and shall be deemed to be the essence of the contract
								and shall be reckoned from the fifteenth day after the date on
								which the order to commence the work is issued to the
								contractor, for a work where completion is up to 6 months
							</p>
							<p>For works, for which the completion period is beyond six
								months: -</p>

							<p>The period will be reckoned from the thirtieth day after
								the date on which the order to commence the work is issued to
								contractor.</p>
							<p>The work shall throughout the stipulated period of
								contract be proceeded with all due diligence, keeping in view
								that time is the essence of the contract. The contractor shall
								be bound in all cases, in which the time allowed for any work
								exceeds one month, to complete 1/8th of the whole work before
								1/4th of the whole time allowed under the contract has elapsed,
								3/8 th of the work before 1/2 of such time has elapsed and 3/4th
								of the work before 3/4th of such time has elapsed. In the event
								of the contractor failing to comply with the above conditions,
								the Commissioner/CMO shall levy on the contractor, as
								compensation an amount equal to: 0.5% (zero point five percent)
								of the value of work (contract sum) for each week of delay,
								provided that the total amount of compensation under provision
								of this clause shall be limited to 6% (six percent) of value of
								work. (Contract sum)</p>
							<p>Provided further that if the contractor fails to achieve
								30% (thirty percent) progress in 1/2 (half) of original or
								validly extended period of time (reference clause 5 below) the
								contract shall stand terminated after due notice to the
								contractor and his contract finallised, with earnest money and
								or security deposit forfeited and levy of further compensation
								at the rate of 10% of the balance amount of contract left
								incomplete, either from the bill, and or from available
								security/performance guarantee or shall be recovered as "Arrears
								of land revenue".</p>
							<p>The decision of the Commissioner/CMO in the matter of
								grant of extension of time only (reference clause 5 below) shall
								be final, binding and conclusive. But he has no right to change
								either the rate of compensation or reduce and or condone the
								period of delay- once such an order is passed by him (on each
								extension application of the contractor) it shall not be open
								for a revision.</p>

						</div>
					</div>
					<!---  04 Page Ends Here --->
					<!---  05 Page Starts Here --->
					<div class="page" id="pageNumber">
						<div class="subpage">
							<p>
								Where the Commissioner/CMO decides that the contractor is liable
								to pay compensation for not giving proportionate progress under
								this clause and the compensation is recommended during the
								intermediate period, such compensation shall be kept in deposit
								and shall be refunded if the contractor subsequently makes up
								the progress for the lost time, within the period of contract
								including extension granted, if any failing which the
								compensation amount shall be forfeited in favour of the <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>.
							</p>
							<h2>
								<b><u>Action when the work is left incomplete abandoned
										or delayed beyond the time limit permitted by the
										Commissioner/CMO</u></b>
							</h2>
							<p>
								<b>Clause 3:-</b>
							</p>
							<ul>
								<p>
									<b>(i)</b> The Executive Engineer, may terminate the contract
									if the contractor causes a fundamental breach of the contract.
								</p>
								<p>
									<b>(ii)</b> Fundamental breach of contract shall include, but
									not be limited to, the following: -
								</p>
								<li><b>(a)</b> The contractor stops work for four weeks,
									when no stoppage of work is shown on the current programme or
									the stoppage has not been authorised by the Commissioner/CMO.</li>
								<li><b>(b)</b> (b) The Commissioner/CMO gives notice that
									failure to correct a particular defect is a fundamental breach
									of contract and the contractor fails to correct it within
									reasonable period of time determined by the Commissioner/CMO in
									the said notice.</li>
								<li><b>(c)</b> The contractor has delayed the completion of
									work by the number of weeks [12 (Twelve) weeks] for which the
									maximum amount of compensation of 6% of contract sum is
									exhausted.</li>
								<li><b>(d)</b> If the contractor has not completed at least
									thirty percent of the value of construction work required to be
									completed in half of the completion period (Including validly
									extended period if any).</li>
							</ul>
							<ul>
								<li><b>(e)</b> If the contractor fails to appoint the
									technical staff and if appointed do not function properly for 4
									weeks even after due written notice by the Commissioner/CMO.</li>
								<li><b>(f)</b> If he violates labour laws.</li>
								<li><b>(g)</b> If the Contractor fails to set up field
									laboratory with appropriate equipments, within 30 day from the
									reckoned date. (* for each contract valued more than Rupees 3
									crores).</li>
								<li><b>(h)</b> Any other deficiency which goes to the root
									of the contract Performance</li>
								<p>
									<b>(iii)</b> If the contract is terminated, the contractor
									shall stop work immediately, make the site safe and secure and
									leave the site as soon as reasonably possible.
								</p>
								<p>
									<b>(iv)</b> The Executive Engineer shall cause recording and
									checking of measurements of all items of work done (taking in
									to account quality and quantity of items actually executed) and
									prepare the final bill after adjusting all pervious outstanding
									dues. Such recording of measurements shall be done after due
									notice regarding time and date of recording measurement and
									directing the contractor to either remain present himself or
									his authorised representative so as to satisfy himself that the
									recording of measurement is just and proper. Failure on his
									parts either to attend and or refusing to acknowledge the
									measurement so recorded in the department measurement book,
									shall be at his sole risk and responsibility.
								</p>
								<p>
									<b>(v)</b> In addition to the provision contained in clause 2
									above the Executive Engineer shall forfeit the earnest money
									and or security deposit and further recover/deduct/adjust a
									compensation @ 10% (ten percent) of the balance value of work
									left incomplete either from the bill, and or from available
									security/performance guarantee or shall be recovered as
									"Arrears of land revenue"
								</p>
							</ul>

						</div>
					</div>
					<!---  05 Page Ends Here --->
					<!---  06 Page Starts Here --->
					<div class="page" id="pageNumber">
						<div class="subpage">
							<h2>
								<b><u>Power to take possession of or require removal of
										Materials Tools and Plants or sale of Contractor's Plants etc</u></b>
							</h2>
							<p>
								<b>Clause 4:</b> In any case in which any of the powers,
								conferred upon the Executive Engineer by clause - 3 hereof shall
								have become exercisable and the same shall not be exercised, the
								non-exercise thereof shall not constitute a waiver of any of the
								conditions hereof and such powers shall notwithstanding be
								exercisable in the event of any future case of default by the
								contractor for which by any clause or clauses hereof he is
								declared liable to pay compensation shall remain unaffected. In
								the event of the Executive Engineer putting in force either of
								the power clause 3 vested in him under the preceding clause he
								may, if he so desires, take possession of all or any tools,
								plant materials, and stores in or upon the works, or the site
								thereof or belonging to the contractor or procured by him and
								intended to be used for the execution of the work or any part
								thereof paying or allowing for the same in account at the
								contract rates, or in case of these not being applicable, at
								current market rates to be certified by Executive Engineer ,
								whose certificate thereof shall be final; otherwise the
								Executive Engineer may by notice in writing to the contractor or
								his clerk of the works foreman or authorised agent require him
								to remove such tools plant, materials or stores from the
								premises (within a time to be specified in such notice) and in
								the event of the contractor failing to comply with any such
								requisition, the Executive Engineer may remove them at the
								contractors expense sell them by auction or private sale on
								account of the contractor & at his risk in all respects and the
								certificate of the Executive Engineer as to the expense of any
								such removal and the amount of the proceeds and expense of any
								such sale shall be final and conclusive against the contractor.
							</p>
							<h2>
								<b><u>EXTENSION OF TIME</u></b>
							</h2>
							<p>
								<b>Clause 5</b>
							</p>
							<p>
								<b>5.1 -</b> If the Contractor shall desire an extension of time
								for completion of work on ground of his having been
								"UNAVOIDABLY" hindered in its execution or on any other ground,
								he must apply giving all and complete details of each of such
								hindrances or other causes in writing, to the Commissioner/CMO
								positively within 15 days of occurrence of such hindrance(s) and
								seek specific extension of time (period from
								.................... to ................)
							</p>
							<p>In case the grounds shown by the contactor are reasonable,
								the Executive Engineer shall be competent to grant the extension
								himself as under:-</p>
							<p>For building work - Three months (Only for new building
								construction works)</p>
							<p>For road and bridge work - One month or 10% (ten percent)
								of the stipulated period of completion, whichever is more.</p>
							<p>It the extension of time sought is more than above period
								mentioned, then the Executive Engineer shall refer the case to
								the Superintending Engineer with his recommendation and only
								after his decision in this regard, the Executive Engineer shall
								sanction extension of such time as decided by the Superintending
								Engineer . Once the Executive Engineer/Superintending Engineer
								has decided the case of extension of time with reference to the
								particular application of the contractor, it will not be
								competent for them to review/change such a decision later on.
								However, the Superintending Engineer and the Executive Engineer
								shall give the contractor an opportunity to be heard (orally and
								or in writing), before taking any final decision either of
								granting extension of time or permitting the contractor to
								complete the work by the delayed date (under clause 2 of the
								contact) or before refusing both.</p>
							<p>Provided further where the Executive Engineer has
								recommended grant of extension of particular time under clause
								5.1 of the contract or has refused to recommend extension of
								time but has recommended permitting the contractor for delayed
								completion, (clause 2) the contractor shall continue with the
								work till the final decision by Executive
								Engineer/Superintending Engineer.</p>


						</div>
					</div>
					<!---  06 Page Ends Here --->
					<!---  07 Page Starts Here --->
					<div class="page" id="pageNumber">
						<div class="subpage">

							<p>Failure on the part of the contractor for not applying
								extension of time even within 30 days of the cause of such an
								hindrance, it shall be deemed that the contractor does not
								desire extension of time and that he has "Waived" his right if
								any, to claim extension of time for such cause of hindrance.</p>
							<p>Once the Executive Engineer /Superintending Engineer has
								heard (oral and or in writing) the contractor on this subject
								matter of extension of time and if Executive
								Engineer/Superintending Engineer fails to communicate his
								decision within a period of 30 days of such hearing, it shall be
								deemed that the contractor has been granted extension of time
								for the period as applied by him.</p>
							<p>
								<b>5.2 Compensation Events: - Compensation Events for
									consideration of extension of time without penalty.</b>
							</p>
							<p>The following mutually agreed Compensation Events unless
								they are caused by the contractor would be applicable;</p>

							<ul>
								<li>(a) The Commissioner/CMO does not give access to a part
									of the site.</li>
								<li>(b) The Commissioner/CMO modifies the schedule of other
									contractor in a way, which affects the work of the contractor
									under the contract.</li>
								<li>(c) The Commissioner/CMO orders a delay or does not
									issue drawings, specification or instructions
									/decisions/approval required for execution of works on time.
								<li>
								<li>(d) The Commissioner/CMO instructs the contractor to
									uncover or to carry out additional tests upon work, which is
									then found to have no defects.</li>

								<li>(e) The Commissioner/CMO gives an instruction for
									additional work required for safety or other reasons.1</li>
								<li>(f) The advance payment and or payment of running bills
									(complete in all respect) are delayed.</li>
								<li>(g) The Commissioner/CMO unreasonably delays issuing a
									Certificate of Completion</li>
								<li>(h) Other compensation events mentioned in contract if
									any</li>
							</ul>
							<h2>
								<b><u>FINAL CERTIFICATE:</u><b>
							</h2>
							<p>
								<b>Clause 6 -</b> On completion of the work the contractor shall
								be furnished with a certificate by the Commissioner/CMO
								(hereinafter called the Commissioner/CMO) of such completion in
								the form appended at the end, but no such certificate shall be
								given, nor shall the work be considered to be complete until the
								contractor shall have removed from the premises on which the
								works shall be executed, all scaffolding surplus materials and
								rubbish, and cleaned off the dirt from all wood-work, doors
								windows walls, floors or other parts of any building in upon or
								about which the work is to be executed or of which he may have
								had possession for the purpose of the execution there of nor
								until the work; shall have been measured by the
								Engineer-in-charge whose measurements shall be binding and
								conclusive against the contractor. If the contractor shall fail
								to comply with the requirements of this clause as to removal of
								scaffolding surplus materials and rubbish and cleaning of dirt
								on or before the date fixed for the completion of the work, the
								Engineer-in-charge may, at the expense of the contractor remove
								such scaffolding, surplus materials and rubbish and dispose of
								the same as he thinks fit and clean off such dirt as aforesaid
								and the contractor shall forthwith pay the amount of all
								expenses so incurred, and shall have no claim in respect of any
								such scaffolding or surplus materials as aforesaid, except for
								any sum actually realised by the sale thereof.
							</p>
							<h2>
								<b><u>PAYMENT ON INTERMEDIATE CERTIFICATE TO BE REGARDED
										AS ADVANCES:</u></b>
								</p>
								<p>
									<b>Clause 7 -</b> No payments shall ordinarily be made for work
									estimated to cost less then Rs. 1,000/- (Rs. One Thousand) till
									after the whole of the works shall have been completed and
									certificate of completion given but if intermediate payment
									during the course of execution of works is considered desirable
									in the interest
								</p>
						</div>
					</div>
					<!---  07 Page Ends Here --->
					<!---  08 Page Starts Here --->
					<div class="page" id="pageNumber">
						<div class="subpage">
							<p>of works, the contractor may be paid at the discretion of
								the Commissioner/CMO But in the case of works estimated to cost
								more then rupees one thousand, the contractor shall on
								submitting the bill therefore be entitled to receive a monthly
								payment proportionate to the part thereof then approved and
								passed by the Commissioner/CMO whose certificate of such
								approval and passing of the sum so payable shall be final and
								conclusive against the contractor. But all such intermediate
								payments shall be regarded as payments by way of advance against
								the final payment for works actually done and completed and
								shall not preclude the requiring of bad unsound and imperfect or
								unskillful work to be removed and taken away and reconstructed
								or erected or be considered as an admission of the due
								performance of the contract or any such part thereof, in any
								respect, or the accruing of any claim, nor shall it conclude
								determine, or affect in any way the powers of the
								Commissioner/CMO under these conditions or any of them as to the
								final settlement and adjustment of the accounts or otherwise or
								in any other way vary or affect the contract. The final bill
								shall be submitted by the contractor within one month of the
								date fixed for completion of the work, otherwise the Engineer
								-in-charge's certificate of the measurement and of the total
								amount payable for work accordingly shall be final and binding
								on all parties.</p>
							<h2>
								<b><u>Bills to be submitted monthly:</u></b>
							</h2>
							<p>
								<b>Clause 8 -</b> "A bill shall be submitted by the contractor
								by 15th day of each month for all works executed by him till the
								end of previous month less the gross amount received by him till
								the last previous month. This bill must be supported by records
								of detail measurement of quantities of all executed items of
								work along with true copies of record and result of all tests
								conducted in the previous month (date wise). The
								Commissioner/CMO shall take or cause to be taken the requisite
								measurement for purpose of having the same verified/checked by
								the sub Engineer/Assistant Engineer Commissioner/CMO concern for
								quantity, quality and specification and examining all the "test
								results" and record the same in the Departmental measurement,
								book. Based on above record measurement bill shall be corrected
								/prepared afresh. The contractor shall sign the measurement and
								the bill. The Commissioner/CMO shall pay running bills by 25 th
								day of the month subject to availability of the funds
							</p>
							<p>If the contractor fails to submit, the bill on or before
								the day prescribed, the Commissioner/CMO after waiting for
								another 15 days shall depute a subordinate to measure the said
								work in the presence of contractor and or his authorised
								Engineer/Representative, whose counter signature to the
								measurement recorded with quantity and quality remark will be
								sufficient proof for acceptance of the same and shall be binding
								on the contractor</p>
							<p>All such running bill payments are by way of "Advances"
								and shall be subject to final adjustment.</p>
							<h2>
								<b><u>BILLS TO BE ON PRINTED FORMS:</u></b>
							</h2>
							<p>
								<b>Clause 9 -</b> The contractor shall submit all bills on
								printed forms to be had on application at the office of the
								Engineer-in-charge, and the charges in the bills shall always be
								entered at the rates specified in the tender or in the case of
								any extra work ordered in pursuance of these conditions, and not
								mentioned or provided for in the tender at the rates hereinafter
								provided for such work. The deduction or addition as the case
								may be of the percentage will be calculated on the amount of the
								bill for the work done, after deducting the cost of materials
								supplied departmentally at rates specified in the agreement.
							</p>
							<h2>
								<b><u>RECEIPTS TO BE SIGNED BY PARTNERS OF PERSONS
										HAVING AUTHORITY TO DO SO:</u></b>
							</h2>
							<p>
								<b>Clause 10 -</b> Receipts for payments made on account of a
								work when executed by a firm must also be signed by the several
								partners, except where the contractors are described in their
								tender as a firm in which case the receipt must be signed in the
								name of-the firm by one of the partners, or by some other person
								having authority to give effectual receipt for the firm.
							</p>

						</div>
					</div>
					<!---  08 Page Ends Here --->
					<!---  09 Page Starts Here --->
					<div class="page" id="pageNumber">
						<div class="subpage">

							<p>
								<b>Clause 11 - Reimbursement/Refund on Variation in Prices
									of Materials / P. O. L. and Labour Wages, only for the works
									contract value more than Rs.3 Cr.</b>
							</p>
							<p>Price Adjustment: -</p>
							<p>(A) Contract price shall be adjusted for increase or
								decrease in rates and price of labour, materials, POL, in
								accordance with the following principles and procedure and as
								per formula given below.</p>
							<p>
								Note: - Price adjustment shall be applicable <b>from
									reckoned date</b> and upto validly extended period under clause 5.1
								above but shall not apply to the period when, work is carried
								out under clause 2 above.
							</p>
							<p>(B) The price adjustment shall be determined during each
								month from the formula given in the hereunder.</p>
							<p>
								(C) Following expressions and meanings are assigned to the work
								done during each month: <br />To the extent that full
								compensation for any rise or fall in costs to the contractor is
								not covered by the provisions of this or other clauses in the
								contract, the unit rates and prices included in the contract
								shall be deemed to include amounts to cover the contingency of
								such other rise or fall in costs.
							</p>


							<p>
								<b>The formula (e) for adjustment of prices are: -</b>
							</p>
							<p>R= Total value of work done during the month. It would
								include the amount of secured advance granted, if any, during
								the month, less the amount of secured advance recovered, if any
								during the month. It will exclude value for works executed under
								variations for which price adjustment will be worked separately
								based on the terms mutually agreed.</p>
							<p>
								<b>Adjustment for labour component</b>
							</p>
							<p>(i) Price adjustment for increase or decrease in the cost
								due to labour shall be paid in accordance with the following
								formula:</p>
							<ul>
								<li>V<sub>L</sub> = 0.85 x P<sub>1</sub>/100 x R x (L<sub>i</sub>
									-L<sub>0</sub>)/L<sub>0</sub>
								</li>
								<li>V<sub>L</sub> = increase or decrease in the cost of
									work during the month under consideration due to changes in
									rates for local labour.
								</li>
								<li>L<sub>0</sub> = the consumer price index for industrial
									workers at the town nearest to the site or work as published by
									Labour Bureau, Ministry of Labour, Govt. of India. on the date
									of inviting tender
								</li>
								<li>L<sub>i</sub> = The consumer price index for industrial
									workers at the town nearest to the site of work for the month
									under consideration as published by Labour Bureau, Ministry of
									Labour, Government of India.
								</li>
								<li>P<sub>1</sub> = Percentage of labour component of the
									work.
								</li>
							</ul>
							<p>
								<b>Adjustment for cement component</b>
							</p>
							<p>(ii) Price adjustment for increase or decrease in the cost
								of cement procured by the contractor shall be paid in accordance
								with the following formula</p>
							<ul>
								<li>V<sub>c</sub> = 0.85 x P<sub>c</sub>/100 x R x (C<sub>i</sub>
									-C<sub>0</sub>)/C<sub>0</sub>
								</li>
								<li>V<sub>c</sub> = increase or decrease in the cost of
									work during the month under consideration due to changes in
									rates for cement
								</li>
								<li>C<sub>0</sub> = The all India wholesale price index for
									cement as published by the Ministry of Industrial Development,
									Government of India, New Delhi. on the date of inviting tender
								</li>
								<li>C<sub>i</sub> = The all India average wholesale price
									index for cement for the month under consideration as published
									by Ministry of Industrial Development, Govt. of India, New
									Delhi.
								</li>
								<li>P<sub>C </sub> = Percentage of cement component of the
									work.
								</li>
							</ul>


						</div>
					</div>
					<!---  09 Page Ends Here --->
					<!---  10 Page Starts Here --->
					<div class="page" id="pageNumber">
						<div class="subpage">
							<p>
								<b>Adjustment for steel component</b>
							</p>
							<p>(iii) Price adjustment for increase or decrease in the
								cost of steel procured by the Contractor shall be paid in
								accordance with the following formula;</p>
							<ul>
								<li>V<sub>s</sub> = 0.85 x P<sub>s</sub>/100 x R x (S<sub>i</sub>
									- S<sub>0</sub>)/S<sub>0</sub>
								</li>
								<li>V<sub>s</sub> = increase or decrease in the cost of
									work during the month under consideration due to changes in the
									rates for steel.
								</li>
								<li>S<sub>0</sub> = The all India wholesale price index for
									steel (Bar and Rods) as published by the Ministry of Industrial
									Development, Government of India, New Delhi. on the date of
									inviting tender
								</li>
								<li>S<sub>i</sub> = The all India average wholesale price
									index for steel (Bar and Roads) for the month under
									consideration as published by Ministry of Industrial
									Development, New Delhi.
								</li>
								<li>P<sub>s</sub> = Percentage of steel component of the
									work.
								</li>
							</ul>
							<p>
								<b>Note:-</b><i> for the application of this clause, index
									of Bars and Rods has been to represent steel group.</i>
							</p>
							<p>
								<b>Adjustment of bitumen component</b>
							</p>
							<p>(iv) Price adjustment for increase or decrease in the cost
								of bitumen shall be paid in accordance with the following
								formula;</p>
							<ul>
								<li>V<sub>L</sub> = 0.85 x P<sub>b</sub>/1 00 x R x (B<sub>i</sub>
									-B<sub>0</sub>) / B<sub>0</sub>
								</li>
								<li>V<sub>b</sub> = Increase or decrease in the cost of
									work during the month under consideration due to changes in
									rates for bitumen.
								</li>
							</ul>

							<ul>
								<li>B<sub>0</sub> = The official retail price of bitumen at
									the IOC depot at nearest centre on the date of inviting tender.
								</li>
								<li>B<sub>i</sub> = The official retail price of bitumen of
									IOC depot at nearest center for the 15th day of the month under
									consideration.
								</li>
								<li>P<sub>b</sub> = Percentage of bitumen component of the
									work.
								</li>
							</ul>
							<p>
								<b>Adjustment of POL (fuel and lubricant) component</b>
							</p>
							<p>(v) Price adjustment for increase or decrease in cost of
								POL (fuel and lubricant) shall be paid in accordance with the
								following formula;</p>
							<ul>
								<li>V<sub>f</sub> = 0.85 x P<sub>f</sub>/100 x R x (F<sub>i</sub>
									- F<sub>0</sub>)/F<sub>0</sub>
								</li>
								<li>V<sub>f</sub> = Increase or decrease in the cost or
									work during the month under consideration due to changes in
									rates for fuel and lubricants.
								</li>
								<li>F<sub>0</sub>= The official price of High Speed Diesel
									(HSD) at the existing consumer Diesel pumps out let at nearest
									center on the date of inviting tender
								</li>
								<li>F<sub>i</sub> = The official retail price of HSD at the
									existing consumer pumps of IOC at nearest center for the 15th
									day of month under consideration.
								</li>
								<li>P<sub>f</sub> = Percentage of fuel and lubricants
									component of the work.
								</li>
							</ul>
							<p>
								<b>Note: -</b> For the application of this clause, the price of
								High speed Diesel Oil has been chosen to represent fuel and
								lubricants group.
							</p>

						</div>
					</div>
					<!---  10 Page Ends Here --->
					<!---  11 Page Starts Here --->
					<div class="page" id="pageNumber">
						<div class="subpage">

							<p>
								<b>Adjustment of Other Materials Component</b>
							</p>
							<p>(vii) Price adjustment for increase or decrease in cost of
								local materials other than Cement, steel, Bitumen and POL
								procured by the contractor shall lay in accordance with the
								following formula;</p>
							<ul>
								<li>V<sub>m</sub> = 0.85 x P<sub>m</sub> /100 X R x (M<sub>i</sub>
									- M<sub>0</sub>)/M<sub>0</sub>
								</li>
								<li>V<sub>m</sub> = Increase or decrease in the cost of
									work during the month under consideration due to changes in
									rates for local materials other than cement, steel, bitumen and
									POL.
								</li>
								<li>M<sub>0</sub> = The all India wholesale price index
									(all commodities) as published by the Ministry of Industrial
									Development, Govt. of India, New Delhi. on the date of inviting
									tender
								</li>
								<li>M<sub>i</sub> = The all India Wholesale price index
									(all commodities) for the month under consideration as
									published by Ministry of Industrial Development, Govt. of
									India, New Delhi.
								</li>
								<li>P<sub>m</sub> = Percentage of local material component
									(Other than cement, steel, bitumen and POL) of the work.
								</li>
							</ul>

							<h2>
								<b>The following percentages will govern the price
									adjustment for the entire contract:</b>
								</p>
								<table class="table table-bordered" style="font-weight: 500">
									<thead>
										<tr>
											<th>Sl. No.</th>
											<th>Components</th>
											<th>For road</th>
											<th>For Building</th>
											<th>For bridge</th>
										</tr>
									</thead>
									<tr>
										<td>1</td>
										<td>Lbour- P<sub>1</sub></td>
										<td>25%</td>
										<td>35%</td>
										<td>30%</td>
									</tr>
									<tr>
										<td>2</td>
										<td>Cement - P<sub>c</sub></td>
										<td>5%</td>
										<td>10%</td>
										<td>25%</td>
									</tr>
									<tr>
										<td>3</td>
										<td>Steel - P<sub>s</sub></td>
										<td>5%</td>
										<td>10%</td>
										<td>25%</td>
									</tr>
									<tr>
										<td>4</td>
										<td>Bitumen - P<sub>b</sub></td>
										<td>10%</td>
										<td>-</td>
										<td>-</td>
									</tr>
									<tr>
										<td>5</td>
										<td>POL - P<sub>f</sub></td>
										<td>10%</td>
										<td>10%</td>
										<td>10%</td>
									</tr>
									<tr>
										<td>6</td>
										<td>Other materials - P<sub>m</sub></td>
										<td>45%</td>
										<td>35%</td>
										<td>10%</td>
									</tr>
									<tr>
										<td></td>
										<td><b>Total: -</b></td>
										<td><b>100%</b></td>
										<td><b>100%</b></td>
										<td><b>100%</b></td>
									</tr>
								</table>
								<p>
									Note :-If in the execution of contract for Road works use of
									certain material(s) is/are not involved (Viz cement, steel,
									Bitumen etc.), then the percentage of other material-Pm shall
									be increased to that extent<br />Example: - Say in a contract
									of roadwork steel is not required (Ps-5%).Pm shall become
									45%+5%=50%
								</p>
								<p style="text-align: center;">Or</p>
								<p>Say cement & steel not required then Pm shall become
									45%+5%+5%=55% and so on</p>

								<h2>
									<b><u>Work to be executed in Accordance with
											Specification, Drawing, Order, etc.:</u></b>
								</h2>
								<p>
									<b>Clause 12:</b> The contractor shall execute the whole and
									every part of work in the most substantial and workman like
									manner, and both as regards materials and otherwise in every
									respect in strict accordance with the specifications. The
									contractor shall also conform exactly fully and faithfully to
									the designs, drawings and instructions in writing relating to
									the work signed by the Engineer – in – charge and lodged in
									his office and to which the contractor shall be entitled to
									have access at such office or on the site of the work for the
									purpose of inspection during office hours and the contractor
									shall, if he so requires, be entitled at his own expense to
									take or cause to be made copies of the specifications, and of
									all such designs, drawings and instructions as aforesaid.
									MORTH/IRC specifications for road and bridges, specifications
									for rural roads and other I.R.C. publications and their manual,
									latest CPWD specifications/I.S.I. codes for buildings or
									special specifications whenever enclosed separately shall apply
									in the case of any variance the following.
								</p>
						</div>
					</div>
					<!---  11 Page Ends Here --->
					<!---  12 Page Starts Here --->
					<div class="page" id="pageNumber">
						<div class="subpage">

							<p>
								<b><u>Order of precedence shall prevail: -</u></b>
							</p>
							<ul>
								<li>1. Specifications as per NIT.</li>
								<li>2. Specifications as per S.O.R.</li>
								<li>3 MORTH/IRC specifications for road and bridges,
									Specifications for rural roads and other I.R.C. Publications
									and their manual, latest CPWD specifications/I.S.I. codes for
									buildings or special specifications whenever enclosed
									separately</li>
								<li>4 Mode of measurements for building shall be as
									provided in the S.O.R. applicable to the contract. Where such
									mode of measurement is not specified in the S.O.R. it shall be
									done as per l.S.l. Code of building measurement. However if any
									mode of measurement is specifically mentioned in the N.l.T. the
									same will get precedence over all the above.</li>
							</ul>
							<p>
								<b>Clause 12 -A:</b> Deleted
							</p>

							<h2>
								<b><u>Variations</u></b>
							</h2>
							<p>
								<b>Clause 13 - Additions, Alterations in Specifications and
									Designs.</b>
							</p>
							<p>
								The Commissioner/CMO shall have power to make any alterations
								in, omissions from, additions to, or substitutions for, the
								original specifications, drawings, designs and instruction, that
								may appear to him to be necessary or advisable during the
								progress of the work, and the contractor shall be bound to carry
								out the work in accordance with any instruction which may be
								given to him in writing, signed by the Commissioner/CMO and such
								alterations, omissions, additions or substitution shall not
								invalidate the contract and any altered, additional works, or
								substituted work, which the contractor may be directed to do in
								the manner above specified as part of the work; shall be carried
								out by the contractor on the same conditions in all respects on
								which be agreed to do the main work and at the same rates as are
								specified in the tender for the main work, provided the total
								value of all such increased or altered or substituted work <b>does
									not exceed 25% (Twenty Five percent)</b> of the amount put to
								tender inclusive of contractor percentage. If such value <b>exceeds
									25% (Twenty Five percent)</b> it shall be open to the contractor
								either to determine the contract or apply for extension.
								<!-- But in
								no case the contractor shall be entitled to any rate other than
								the accepted rate. -->
							</p>
							<!-- <p>
								13.1 <b>For rate of any extra item Engineer-in-Charge shall
									pay 75% of the provisional rate till such time as the rates are
									finally determined by the Superintending Engineer.</b>
							</p> -->
							<p>
								<b>Note: -</b> Such additions, alterations, substitution, shall
								have to be within the Scope of work tendered for
							</p>
							<h2>
								<b><u>Rates for works not in schedule of rates</u></b>
							</h2>
							<p>
								If during the course of execution, where it is found necessary
								that certain item/items of work not provided for in the S.O.R.
								required to be carried out then the Engineer - in - charge shall
								identify such item / items including approximate quantity of the
								contract and ask the contractor to submit his rates in writing
								supported by the requisite data within <b>a period of 7
									days.</b> The <b>Engineer - in - charge</b> shall obtain approval /
								modification of the proposed rate from the superintending
								Engineer and communicate the same within a period of 4 weeks to
								the contractor, in case the contractor agrees to the above rates
								as fixed by the superintending Engineer then they shall form a
								part of supplementary schedule of the contract agreement .lf the
								contractor does not agree to the rate of the superintending
								Engineer then it shall be open for the Engineer - in - charge to
								get the work executed through any other agency. The contractor
								will not however be entitled to any compensation due to delay or
								hindrance or loss of profit accruing on account of this extra
								work executed by alternative agency.
							</p>
							<p>If the contractor commences non-schedule work or incur
								expenditure in regard thereto before the rates shall have been
								determined by the superintending Engineer, then he shall be
								entitled for payment for the work done as may be finally decided
								by the superintending Engineer. In the event of dispute, the
								decision of the Chief Engineer shall be final. Such a decision
								shall be given by the C.E. within a period of 30 (Thirty) days
								and it shall be open to the contractor not to continue that item
								further. In such an event that item shall be got executed by
								other agency at such an approved rate by superintending
								Engineer. Contractor may either determine his contract if
								variations exceeds 10 (Ten) % of the Administrative approval, or
								may apply for extension.</p>

						</div>
					</div>
					<!---  12 Page Ends Here --->
					<!---  13 Page Starts Here --->
					<div class="page" id="pageNumber">
						<div class="subpage">

							<h2>
								<b><u>Extension of time in consequence of variations</u></b>
							</h2>
							<p>The time for the completion of work shall be extended in
								proportion of the variation of the work bear to the original
								contract work and certificate of Engineer-in-Charge shall be
								conclusive as to such proportion.</p>

							<p>
								<b><u>NO CLAIM TO ANY PAYMENT OR COMPENSATION FOR
										ALTERATION IN OR RESTRICTION OF WORKS:</u></b>
							</p>
							<p>
								<b>Clause 14 -</b> If at any time after the execution of the
								contract documents, Commissioner/CMO shall for any reason
								whatsoever require the whole or any part of the work as
								specified in the tender to be stopped for any period or shall
								not require the whole or part of the work to be carried out at
								all or to be carried out by the contractor he shall give notice
								in writing of the fact to the contractor who shall there upon
								suspend or stop the work totally or partially, as the case may
								be.
							</p>
							<p>If any such case, except as provided hereunder, the
								contractor shall have no claim to any payment or compensation
								what so ever on account of any profit or advantage which he
								might have derived from the execution of the work in full, but
								which he did not so derive in consequence of the full amount of
								the work not having been carried out, or on account of any loss
								that he may be put to on account of materials purchased or for
								unemployment of labour recruited by him. He shall not also have
								any claim for compensation by reason of any alteration having
								been made in the original specifications, drawing, designs and
								instructions, which may involve any curtailment of the work as
								originally contemplated. Where, however, materials have already
								been purchased or agreed to be purchased by the contractor shall
								be paid for such materials at the rates determined by the
								Commissioner/CMO, provided they are not in excess of requirement
								and of approved quality and / or shall be compensated for the
								loss, if any that he may be put to, in respect of materials
								agreed to be purchased by him, the amount of such compensation
								to be determined by the Commissioner/CMO whose decision shall be
								final. If the contractor suffers any loss on account of his
								having to pay labour charges during the period during which the
								stoppage of work has been ordered under this clause, the
								contractor shall, on application be entitled to such
								compensation on account of labour charges as the
								Commissioner/CMO, whose decision shall be final, may consider
								reasonable provided that the contractor shall not be entitled to
								any compensation on account of labour charges, if in the opinion
								of the Commissioner/CMO, the labour could have been employed by
								the contractor else where for the whole or part of the period
								during which the stoppage of the work has been ordered as
								aforesaid. If the total duration of suspension of the work is
								more than the six months, then this suspension of the work will
								be considered as permanent stoppage of the work, and the
								contractor can determine the contract, if he so desires.</p>
							<p>
								<b><u>ACTION AND COMPENSATION PAYABLE IN CASE OF BAD
										WORK:</u></b>
							</p>
							<p>
								<b>Clause 15 -</b> If at any time before the security deposit is
								refunded to the contractor, it shall appear to the
								Commissioner/CMO or his subordinate in charge of the work, that
								any work has been executed with unsound, imperfect or unskillful
								workmanship or with material of inferior quality or that any
								materials or articles provided by him for the execution of the
								work are unsound, or of a quality inferior to that contracted
								for, or are otherwise not in accordance with the contract, it
								shall be lawful for the Commissioner/CMO to intimate this fact
								in writing to the contractor and then notwithstanding the fact
								that the work, materials or articles complained of may have been
								Inadvertently passed, certified and paid for contractor shall be
								bound forthwith to rectify, or remove and reconstruct the work
								so specified in whole or in part, as the case may require, or if
								so required, shall remove the materials or articles so specified
								and provide other proper and suitable materials or articles at
								his own proper charge and cost, and in the event of his failing
								to do so with in a period to be specified by the
								Commissioner/CMO in the written intimation aforesaid ,the
								contractor shall be liable to pay compensation at the rate of
								one percent on the amount of contract put to tender every day
								not exceeding ten days, during which the failure so, continues
								and in the case of any such failure the Commissioner/CMO may
								rectify or remove and, re-execute the work or remove and replace
								the materials or articles complained of as the case may be at
								the risk and expense in all respects of the
							</p>
						</div>
					</div>
					<!---  13 Page Ends Here --->
					<!---  14 Page Starts Here --->
					<div class="page" id="pageNumber">
						<div class="subpage">

							<p>contractor. Should the Commissioner/CMO consider that any
								such inferior work or materials as described above may be
								accepted or made use of it shall be within his discretion to
								accept to the same at such reduced rates as he may fix therefore</p>
							<p>
								<b><u>WORK TO BE OPEN FOR INSPECTION-CONTRACTOR OR
										RESPONSIBLE AGENT TO BE PRESENT:</u></b>
							</p>
							<p>
								<b>Clause 16-</b> All work under or in course of execution or
								executed in pursuance of the contract shall at all time be open
								to the inspection and supervision of the Commissioner/CMO and
								his subordinates and the contractor shall at all time during the
								usual working hours, and at all other times at which reasonable
								notice of the intention of the Commissioner/CMO or his
								subordinate to visit the work shall have been given to the
								contractor, either himself be present to receive orders and
								instruction or have a responsible agent duly accredited in
								writing present for that purpose. Orders given to the
								contractor's agent shall be considered to have the same force as
								if they had been given to the contractor himself.
							</p>
							<p>
								<b><u>NOTICE TO BE GIVEN BEFORE WORK IS COVERED UP:</u></b>
							</p>
							<p>
								<b>Clause 17 -</b> The contractor shall give not less than five
								days notice in writing to the Commissioner/CMO or his
								subordinate in charge of the work before covering tip or
								otherwise placing beyond the reach of measurement any work in
								order that the same may be measured, and correct dimensions
								thereof be taken before the same is so covered up or placed
								beyond the reach of measurement, any work without the consent in
								writing of the Commissioner/CMO or his subordinate in charge of
								the work and if any work shall be covered up or placed beyond
								the reach of measurement with out such notice having been given
								or consent obtained, the same shall be uncovered at the
								contractors expenses, or in default thereof, no payment or
								allowance shall be made for such work or the materials with
								which the same was executed.
							</p>
							<p>
								<b><u>CONTRACTOR LIABLE FOR DAMAGE DONE AND FOR
										IMPERFECTIONS AFTER CERTIFICATE OF COMPLETION</u></b>
							</p>
							<p>
								<b>Clause18-</b> If the contractor or his work people or
								servants shall break, deface injure or destroy any part of
								building in which they may be working or any building, road,
								road curbs, fences, enclosures, water pipes, cables drains,
								electric or telephone posts or Wires trees grass or grassland or
								cultivated ground continuous to the premises on which the work
								or any part of it is being executed, or if any damage shall
								happen to the work while in progress, from any cause whatever,
								or any imperfections become apparent ,the contractor shall make
								good the same at his own expense or in default, the
								Commissioner/CMO may cause the same to be made good by other
								workmen and deduct the expense of which certificate of the
								Engineer-in-charge shall be final) from any sums that may be
								then or at any time thereafter, may become due to the contractor
								or from his security deposits, or the proceeds of sale thereof
								or of a sufficient portion thereof.
							</p>
							<p>
								<b>The security deposit</b> of the contractor to <b>the
									extent of 50%</b> shall be refunded on his getting the completion
								certificate, provided that all the recoveries outstanding
								against him are realised. <b>Balance 50% of the amount</b> shall
								be refunded after four months of completion of work or final
								bill paid which ever is earlier.
							</p>
							<p>
								<b><u>CONTRACTOR TO SUPPLY PLANT, LADDERS, SCAFFOLDING,
										ETC.:</u></b>
							</p>
							<p>
								<b>Clause 19 -</b> The contractor shall supply at his own cost
								materials (except such special materials if any, as may in
								accordance with the contractor be supplied from the
								Commissioner/CMO Stores) plants, tool, appliances, implements,
								ladders, cordage, tackle, Scaffolding and temporary work
								requisite for the proper execution the work whether original, or
								altered or substituted, and whether included in the
								specification or other documents forming part of the contractor
								referred to in these condition or not or which may be necessary
								for the purpose of satisfying or complying with the requirement
								of the Commissioner/CMO as to any matter as to which under these
								conditions he is entitled to be satisfied, or which he is
								entitled to require together with carriage there for to and from
								the work . The contractor shall also supply without charge
								requisite number of persons with the means and materials
								necessary for the purpose of setting out works,
							</p>
						</div>
					</div>
					<!---  14 Page Ends Here --->
					<!---  15 Page Starts Here --->
					<div class="page" id="pageNumber">
						<div class="subpage">
							<p>and counting, weighing & assisting in the measurement or
								examination at any time and from time to time of the work, or
								materials. Failing his so doing the same may be provided by the
								Commissioner/CMO at the expenses of the contractor and the
								expenses may be deducted from any money due to the contractor
								under the contract, or from his security deposit or the proceeds
								of sale thereof, or of a sufficient portion thereof.</p>
							<p>Contractor is liable for damages arising from
								non-provision of lights fencing etc. The contractor shall also
								provide at his own cost except when the contract specifically
								provides otherwise and except for payments due under clause all
								necessary fencing and lights required to protect the public from
								accident and shall be bound to bear the expenses of defence of
								every suit, action or proceedings at law that may be brought by
								any person for injury sustained owing to neglect of the above
								precautions & to pay any damage and costs which may be awarded
								in any such suit, action or proceedings to any such person or
								which may with the consent of the contractor be paid to
								compromise any claim by any such person.</p>
							<p>
								<b><u>COMPENSATION UNDER SECTION 12 SUB-SECTION (1) OF
										THE WORKMAN’S COMPENSATION ACT 1923:</u></b>
							</p>
							<p>
								<b>Clause 20 -</b> ln every case in which by virtue of the
								provisions of section 12 sub-section (1) of the workman’s
								compensation Act 1923 Commissioner/CMO is obliged to pay
								compensation to a workman employed by the contractor in
								execution of the works, and will recover from the contractor the
								amount of compensation so paid Commissioner/CMO shall be at
								liberty to recover the amount or any part there of by deducting
								it from the security deposit or from any sum due by
								Commissioner/CMO to the contractor whether under this contract
								or otherwise. Commissioner/CMO may not be bound to contest any
								claim made against them under section - 12 sub-section (1) of
								the said Act except on the written request of the contractor and
								upon his giving to Commissioner/CMO full security for all cases
								for which Commissioner/CMO might become liable in consequence
								contesting such claim.
							</p>
							<h2>
								<b><u>LABOUR</u></b>
							</h2>
							<p>
								<b>Clause 21 -</b> The contractor should get himself registered
								under contract - labour regulations and abolition Act 1970
								including its amendments after getting a certificate from the
								principal employer, who will be the Engineer - in - charge.
							</p>
							<p>
								<b>Clause 22 -</b> Labour below the age of 14 years - No labour
								below the age of 14 years shall be employed on the work.
							</p>
							<h2>
								<b><u>FAIR WAGE:</u></b>
							</h2>
							<p>
								<b>Clause 23 -</b> The contractor shall pay not less than fair
								wage to labour engaged by him on the work.
							</p>
							<p>
								<b>Explanation - </b>
							</p>
							<ul>
								<li><b>(a)</b> Fair wage' means wage(s) whether for time or
									piece work notified during the period of execution of contract
									for the work and where such wages have not been so notified,
									the wages prescribed by the Works Department SOR for that
									period</li>
								<li><b>(b)</b> The contractor shall, notwithstanding the
									provisions of any contract to the contrary cause to be paid a
									fair wage to labourers indirectly engaged on the work including
									any labour engaged by his sub-contractors in connection with
									the said work, as if the labourers had been immediately
									employed by him.</li>
								<li><b>(c)</b> In respect of labour directly or indirectly
									employed on the work for the performance of the contractors
									part of this agreement the contractor shall comply with or
									cause to be complied with the Labour Act in force.</li>

							</ul>
						</div>
					</div>
					<!---  15 Page Ends Here --->
					<!---  16 Page Starts Here --->
					<div class="page" id="pageNumber">
						<div class="subpage">
							<ul>
								<li><b>(d)</b> The Executive Engineer shall have the right
									to deduct, from the moneys due to the contractor, any sum
									required or estimated to be required for making good the loss
									suffered by a worker or workers by reasons of non-fulfillment
									to the conditions of the contract for the benefit of the
									workers nonpayment of wages or deductions made from his or
									their wages, which are not justified by the terms of the
									contract or non observance of the regulations.</li>
								<li><b>(e)</b> The contractor shall be primarily liable for
									all payments to be made under and for observance of the
									regulations afore said with out prejudice to his right to claim
									indemnity from his sub-contractors.</li>
								<li><b>(f)</b> The regulations aforesaid shall be deemed to
									be a part of this contract and any breach thereof shall be
									deemed to be a breach of this contract.</li>
							</ul>
							<h2>
								<b><u>Subletting of works</u></b>
							</h2>
							<p>
								<b>Clause 24 :-</b> The contract may be rescinded and security
								deposit forfeited, for subletting the work beyond permissible
								limits as per clause 7.1 of appendix 2.10 or if contractor
								becomes insolvent: -
							</p>
							<p>
								<b>"Note: Such subletting/assignment shall not be made to
									any other Contractor registered in Class A1 to A5 Category in
									the Public Works Department of Chhattisgarh or similar Category
									in other Deptt. Of the State or in other organization or agency
									(Class with about similar financial capacity) by whatever name
									these are called"</b>
							</p>
							<p>
								<b>24.1</b> The contract shall not be assigned or sublet without
								prior sanction of the authority who has accepted the tender in
								writing. And if the contractor assign or sublet his contract,
								for more than permissible limits as per clause 7.1 of appendix
								2.10 or attempt to do so, or become insolvent commence any
								insolvency proceedings or make any composition with his
								creditors, or attempt to do so or if any gratuity, gift, loan,
								perquisite, reward of and advantage pecuniary or otherwise,
								shall either directly or indirectly be given, promised or
								offered by the contractor, or any of his servants or agents or
								to any public officer or person in the employ of
								Commissioner/CMO in any way relating to his office or
								employment, or if any such officer or person shall become in any
								way directly or indirectly interested in the contract, the
								Executive Engineer may there upon by notice in writing rescind
								the contract, and the S.D. of the contractor shall there upon
								stand forfeited and be absolutely at the disposal of <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
								and the same consequences shall ensure as if the contract had
								been rescinded under clause 3 thereof, and in addition the
								contractor shall not be entitled to recover or be paid for an
								MJy work thereto for actually performed under the contract. Any
								such assignment/subletting within the limit of 25% by the
								authority who has accepted the tenders OR 50 % by the next
								higher authority accepting the tender or Govt. as the case may
								be ,shall not diminish or dilute the liability/ responsibility
								of the contractor. If the contractor gets item / items of work
								executed on a task rate basis <b>without</b> materials, this
								shall not amount to subletting of the contract.
							</p>
							<p>
								<b>Any sub contracted work, done in Chhattisgarh state with
									prior approval of competent authority, such subcontractor will
									also get the credit for work towards his experience.</b>
							</p>
							<!-- <p>
								<b><u>Additional condition issued by Govt.of Chattisgarh
										memo no. 9942/4351/T/11/19/Nivida Raipur dated 21-12-11</u></b>
							</p> -->
							<p>
								<b>i) The Commissioner/CMO shall be empowered to terminate
									any contract if the contractor sublets the works to some other
									person on the basis of power of attorney.</b>
							</p>
							<p>
								<b>ii) Subletting of work shall result in reduction in
									experience of the main contractor to the extent of the sublet.</b>
							</p>
							<h2>
								<b><u>Sum payable by way of Compensation to be
										considered as Reasonable Compensation Without Reference to
										Actual Loss:</u></b>
							</h2>
							<p>
								<b>Clause 25:</b> All sums payable by way of compensation under
								any of these conditions shall be considered as reasonable
								compensation to be applied to the use of
								${userSession.getCurrent().organisation.ONlsOrgname} without
								reference to the actual loss or damage sustained, and whether or
								not any damage shall have been sustained.
							</p>

						</div>
					</div>
					<!---  16 Page Ends Here --->
					<!---  17 Page Starts Here --->
					<div class="page" id="pageNumber">
						<div class="subpage">

							<h2>
								<b><u>CHANGE IN THE CONSTITUTION OF FIRM:</u></b>
							</h2>
							<p>
								<b>Clause 26 -</b> In the case of tender by partners any change
								in the constitution of the firm shall be forthwith notified by
								the contractor to the Commissioner/CMO for his information, and
								contractor shall initiate steps for fresh & new registration
								which shall be assessed & decided by the competent authority for
								fresh registration
							</p>

							<h2>
								<b><u>WORK TO BE UNDER DIRECTION OF EXECUTIVE ENGINEER /
										SUPERINTENDING ENGINEER:</u></b>
							</h2>
							<p>
								<b>Clause 27 -</b> All works to be executed under the contract
								shall be executed under the direction and subject to the
								approval in all respect of the Executive Commissioner/CMO of the
								<b>${userSession.getCurrent().organisation.ONlsOrgname}</b> for
								the time being who shall be entitled to direct at what point or
								points and in what manner they are to commenced and from time to
								time carried on.
							</p>
							<h2>
								<b><u>ARBITRATION CLAUSE:</u></b>
							</h2>
							<p>
								<b>Clause 28</b> Except as otherwise provided in this contract
								all question and dispute relating to the meaning of the
								specification, designs, drawings and instruction herein before
								mentioned as to thing whatsoever in any way arising out of or
								relating to the contract designs, drawings, specification,
								estimate, concerning the works, or the execution or failure to
								execute the same, whether arising during the progress of the
								work, or a after the abandonment there of shall be referred to
								the Commissioner/CMO for his decision, within a period of 30
								(thirty) days of such an occurrence (s). There upon the
								Commissioner/CMO shall give his written instructions and/or
								decisions, after hearing the contractor and Executive Engineer
								within a period of 15 (fifteen) days of such request. This
								period can be extended by mutual consent of parties.
							</p>
							<p>Upon receipt of written instructions or decisions, of
								Commissioner/CMO the parties shall promptly proceed without
								delay to comply such instructions or decisions. If the
								Superintending Engineer fails to give his instruction or
								decisions in writing within a period of 15 (fifteen) days or
								mutually agreed time after being requested and/or, if the party
								(es) is/are aggrieved against the decision of the
								Commissioner/CMO, the aggrieved party may within 30 days prefer
								an appeal to the Competent Authority, who shall afford an
								opportunity to the parties of being heard and to offer evidence
								in support of his appeal. The, Commissioner/CMO will give his
								decision within 30 (thirty) days, or such, mutually agreed
								period.</p>
							<p>If any party is not satisfied with the decision of the
								Chief Engineer he can file the petition for resolving the
								dispute through arbitration in the arbitration tribunal.</p>
							<p>A reference to Arbitration Tribunal shall be no ground for
								not continuing the work on the part of the Contractor. Payment
								as per original terms and condition of the agreement shall be
								continued by the Commissioner/CMO in accordance with clause 8
								above.</p>
							<h2>
								<b><u>LUMP SUM IN ESTIMATE:</u></b>
							</h2>
							<p>
								<b>Clause 29 -</b> When the estimate on which a tender is made
								includes lump sums in respect of part of the works, the
								contractor shall be entitled to payment in respect of the items
								of work involved or the part of the work in the question at the
								same rates as are payable under this contract for such items, or
								if the part of the work in question is not, in the opinion of
								the Engineer- in-charge, capable of measurement, the Engineer -
								in - charge may at the his discretion pay the lump sum amount
								entered in the estimates , and the certificate in writing of the
								Engineer -in - charge shall be final and conclusive against the
								contractor with regard to any sum or sums payable to him under
								the provisions of this clause.
							</p>

						</div>
					</div>
					<!---  17 Page Ends Here --->
					<!---  18 Page Starts Here --->
					<div class="page" id="pageNumber">
						<div class="subpage">

							<h2>
								<b><u>Action where no specification:</u></b>
							</h2>
							<p>
								<b>Clause 30 -</b> In the case of any class of work for which
								there is no specification as is mentioned in Rule such work
								shall be carried out in accordance with the specification
								approved by Superintending Engineer / Chief Engineer for
								application to works
							</p>
							<h2>
								<b><u>Contractor’s Percentage whether Applied to Net
										or Gross Amounts of Bills:</u></b>
							</h2>
							<p>
								<b>Clause 31 -</b> The percentage referred to at Para 7 of the
								tender will be deducted from/added to the gross amount of the
								bills for work done after deduction of the cost of materials
								supplied by the department.
							</p>

							<h2>
								<b><u>Claim for Quantities Entered in the Tender or
										Estimate:</u></b>
							</h2>
							<p>
								<b>Clause 32 -</b> Quantities shown in the tender are
								approximate and no claim shall be entertained for quantities of
								work executed being either more or less than those entered in
								the tender of estimate. This is subject to the limitations as
								provided for in clause 13 and 14 above
							</p>
							<h2>
								<b><u>Claim for Compensation for Delay In Starting the
										Work:</u></b>
							</h2>
							<p>
								<b>Clause 33 -</b> No compensation shall be allowed for any
								delay caused, except as provided under clause 5.3, in starting
								of the work on any other ground or reasons whatsoever.
							</p>
							<h2>
								<b><u>EMPLOYMENT OF SCARCITY LABOUR:</u></b>
							</h2>
							<p>
								<b>Clause 34-</b> If Government declare a state of Scarcity or
								famine to exist in any village situated within sixteen
								kilometers of the work the contractor, shall employ upon such
								parts of the work as are suitable for unskilled labour, any
								person certified to him by the Competent Authority or by any
								person to whom the Competent Authority may have delegated this
								duty in writing to be in need of relief and shall be bound to
								pay to such persons wages not below the minimum which Government
								may have fixed in this behalf. Any dispute, which may arise in
								connection with the implementation of this clause, shall be
								decided by the Executive Engineer whose decision shall be final
								and binding on the contractor
							</p>
							<h2>
								<b><u>Royalty on Minor Minerals</u></b>
							</h2>
							<p>
								<b>Clause 35: -</b> The contractor shall pay all quarry, Royalty
								charges etc. If the contractor fails to produce the royalty
								clearance certificate from concerned department then the
								Commissioner/CMO shall deduct the royalty charges from his bills
								and keep in deposit head, which shall be refunded to the
								contractor on production of royalty clearance certificate from
								the concerned department. If he fails to produce the royalty
								clearance certificate with in 30 days of submission of final
								bill, then royalty charges which was keep under deposit head by
								the Commissioner/CMO shall be deposited to the concerned
								department and his final bill payment shall be released

							</p>
							<p>Any change in the royalty rates of minor minerals notified
								by the state government, after the date of submission of
								financial offer by the bidder/contractor, then this
								increase/decrease in the rates shall be reimbursed/deducted on
								actual basis.</p>
							<h2>
								<b><u>TECHNICAL EXAMINATION</u></b>
							</h2>
							<p>
								<b>Clause 36 -</b> The Commissioner/CMO shall have the right to
								cause Audit and Technical Examination of the works and the final
								bills of the contractor including all supporting vouchers,
								abstracts etc. to be made as per payments of the final bills and
								if as a result of such Audit & Technical Examination the sum is
								found to have been overpaid in respect of any work done by the
								contractor under the contract or any work claimed by him to has
								been done under contract and found not to have been executed,
								the contractor shall be liable to refund the amount of over
								payment and it shall be lawful for the Commissioner/CMO to
								recover the same from the security deposit of the contractor or
								from any dues payable to the
						</div>
					</div>
					<!---  18 Page Ends Here --->
					<!---  19 Page Starts Here --->
					<div class="page" id="pageNumber">
						<div class="subpage">
							<p>contractor from the Commissioner/CMO account if it is
								found that the contractor was paid lesser than what was due to
								him under the contract in respect any work executed by him under
								it, the amount of such under payment shall be duly paid by the
								Commissioner/CMO to the contractor.</p>
							<p>In the case of any audit examination and recovery
								consequent on the same the contractor shall be given an
								opportunity to explain his case and decision of the
								Superintending Engineer shall be final.</p>
							<p>In the case of Technical Audit, consequent on which there
								is a recovery from the contractor, no recovery, should be made
								without orders of the Chief Engineer whose decision shall be
								final. AII action under this clause should be initiated and
								intimated to the contractor within a period of Twenty four
								months form the date of completion of work.</p>

							<h2>
								<b><u>DEATH OF PERMANENT INVALIDITY OF CONTRACTOR:</u></b>
							</h2>
							<p>
								<b>Clause 37 -</b> If the contractor is an individual or a
								proprietary concern, partnership concern, dies during the
								currency of the contract or becomes permanently incapacitated,
								where the surviving partners are only minors the contract shall
								be closed without levying any damages/compensation as provided
								for in clause 3 of the contract agreement.
							</p>
							<p>However, if Commissioner/CMO is satisfied about the
								competence of the surviving, then the Commissioner/CMO shall
								enter into a fresh agreement for the remaining work strictly on
								the same terms and conditions, under which the contract was
								awarded.</p>
							<h2>
								<b><u>PENALTY FOR BREACH OF CONTRACT:</u></b>
							</h2>
							<p>
								<b>Clause 38 -</b> On the breach of any term or condition of
								this contract by the contractor the said the <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
								shall be entitled to forfeit the Security deposit or the balance
								thereof that may at the time be remaining, and to realise and
								retain the same as damages and compensation for the said breach
								but without
							</p>
							<p>
								prejudice to the right of the <b>${userSession.getCurrent().organisation.ONlsOrgname}</b>
								to recover further sums as damages from any sums due or which
								may become due to the contractor by.
							</p>
							<br />

						</div>
					</div>
					<!---  19 Page Ends Here --->

					<!---  20 Page Starts Here --->
					<div class="page" id="pageNumber">
						<div class="subpage">

							<p style="text-align: center;">
								<u>NOTICE TO THE CONTRACTOR TO START WORK</u>
							</p>
							<p>Your contract for the work __________________ has been
								accepted by me/Superintending Engineer / Chief Engineer on
								behalf of the Commissioner/CMO on the ___________________ day of
								_____________ 200_______ and you are, hereby ordered to commence
								the work. The commencement date reckoned
								shall__________________________________be___________________________________</p>
							<br /> <br />
							<p>
								<span class="pull-right" style="text-align: center;"><b>Signature<br />Executive
										Engineer<br />
								</b> <b>${userSession.getCurrent().organisation.ONlsOrgname}</b></span>
							</p>
							<p style="clear: both;"></p>
							<br />
							<p>The above notice to the contractor (s) to commence work
								from the reckoned __________ day of __________(month)
								___________200__________ (year) was issued vide this office
								memorandum No. _______________ dated the ________ 200 _____</p>
							<br /> <br />
							<p>
								<span class="pull-left"><b><br /> <br /> <br />Signature
										of Contractor</b></span><span class="pull-right"
									style="text-align: center;"><b>Signature<br />Executive
										Engineer<br />
								</b> <b>${userSession.getCurrent().organisation.ONlsOrgname}</b></span>
							</p>
							<p style="clear: both;"></p>
							<br />
							<h2>
								<b><u>COMPLETION CERTIFICATE</u></b>
							</h2>
							<p>In pursuance of clause 7 of the agreement in form A,
								No.______________/Dt of________) dated ____/_____/________
								between the contactor Shri/Ms.
								.......................................................
								__________________________________ and the Commissioner/CMO; it
								is hereby certified that the said Contractor has duly completed
								the execution of the work under; taken by him there under on
								this __________ day of _______ 200 and this certificate was
								issued to the contractor vide office memo No.
								......................................................
								Dt............</p>
							<p>
								<span class="pull-right"><b><br /> <br />(Signature
										of the Engineer - in - charge)</b></span>
							</p>
						</div>
					</div>
					<!---  20 Page Ends Here --->

				</div>
				<!---  Report Page Ends Here --->
			</form:form>
		</div>
	</div>
</div>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<!--Strat View Contract-->
<div class="widget margin-bottom-0" id="receipt">

<div class="widget-content padding">
<h3 class="text-center"><spring:message code="rnl.contract.agreement" text="Contract Agreement"/> <button onclick="printContent('receipt')" class="btn btn-primary hidden-print pull-right"><i class="fa fa-print"></i><spring:message code="rnl.prop.info.print" text="Print"/></button></h3> 
<p><spring:message code="rnl.contract.agreement" text="This Contract Agreement made and entered into at #Location# this 01 day of December Two Thousand Sixteen"/> </p>

<h4 class="text-center"><spring:message code="rnl.contract.between" text="BETWEEN"/></h4>

<p><spring:message code="rnl.contract.the" text="The"/> <strong>${command.orgName}</strong> <spring:message code="rnl.contract.the.corporate" text="a body corporate having  perpetual succession and a common seal constituted by the Municipal Corporation  Act, 
1888, hereinafter referred to as"/> &ldquo;<strong><spring:message code="rnl.contract.master.the" text="the"/> ${command.orgCode}</strong>&rdquo;<spring:message code="rnl.contract.master.repugnant" text="(which expression  shall unless repugnant to 
the context or meaning thereof be deemed to mean and  include its successor or successors, administrator and assigns)"/> </p>

<h4 class="text-center"><spring:message code="rnl.contract.rep.by" text="REPRESENTED BY"/></h4>

<p><spring:message code="Shri" text="Shri"/> ${command.representedBy} <strong>${command.desgName}</strong> <strong>(${command.deptLoc}</strong>) <spring:message code="rnl.contract.hereinafter" text="hereinafter referred to as"/>  &ldquo;<strong><spring:message code="rnl.contract.master.the" text="the"/></strong>
<strong>${command.desgName}&rdquo; </strong> <spring:message code="rnl.contract.expression" text="(which expressions shall unless repugnant to the context  or meaning thereof be deemed 
to mean and include the successor or successors  for the time being holding of the office of the"/>${command.desgName}) <strong><spring:message code="rnl.contract.first.part" text="of the First Part"/></strong></p>
<h4 class="text-center"><spring:message code="rnl.contract.and" text="AND"/></h4>
<p><spring:message code="rnl.contract.sole.proprietor" text="Sole Proprietor of"/><br>
<spring:message code="rnl.contract.ms" text="M/s."/>${command.vendorName} <spring:message code="rnl.contract.office" text="having his  office at"/>${command.vendorAddress}, <spring:message code="rnl.contract.hereinafter" text="hereinafter referred to as"/> &ldquo;<strong><spring:message code="rnl.master.contract" text="the Contractor"/></strong>&rdquo;<spring:message code="rnl.master.status" text=" (which expression shall unless repugnant to the context or meaning thereof be  deemed to mean and include his heirs, executors, administrators and assigns)"/>  <strong><spring:message code="rnl.contract.secnd.part" text="of the Second Part"/></strong>.</p>
<h4 class="text-center"><spring:message code="rnl.contract.where" text="WHEREAS"/></h4>

<p><spring:message code="rnl.contract.muncipal" text="(A) The Municipal Commissioner for"/><strong>${command.orgName}</strong><spring:message code="" text=" has deputed under Section 56 as contained in Chapter II"/> 
<spring:message code="rnl.contract.municipal" text="of the Municipal  Corporation Act 1888, the powers of the Municipal Commissioner for"/><strong>${command.orgName}</strong><spring:message code="rnl.contract.tothe" text="to the"/><strong>${command.desgName}</strong><spring:message code="rnl.contract.party" text="(Party I Representative)."/></p>

<p><spring:message code="rnl.contract.e-quotations" text="(B) After scrutiny of various e-quotations / e-tender  received, the e-quotations / e-tender submitted by"/>${command.tendorNo} <spring:message code="rnl.contract.resolutionno" text="i.e. the  Contractor herein was accepted by the Ward Committee vide Resolution No."/> ${command.rsoNo} <spring:message code="dated" text="dated"/> ${command.rsoDate}<spring:message code="rnl.contract.matter" text="in the matter of the award of the said  contract subject to the Contractor agreeing to comply with the terms and  conditions hereinafter appearing which the Contractor has agreed to comply."/></p>

<p><spring:message code="rnl.contract.compliance" text="(C) The Contractor having agreed to comply with the terms  and conditions hereinafter appearing is desirous of recording the same pursuant  to compliance whereof the"/> ${command.desgName}<spring:message code="rnl.contract.behalf" text="for and on behalf of  the"/> ${command.orgCode}&rdquo;<spring:message code="rnl.contract.award" text="has agreed to award the said contract to the Contractor."/></p>

<p><spring:message code="rnl.contract.hereto" text="THE PARTIES HERETO AS FOLLOWS:"/></p>

<p><spring:message code="rnl.contract.hereto" text="1. The parties hereto agree that the recitals herein  above enumerated shall be deemed to form part and parcel of the terms and  conditions of this Contract Agreement."/></p>

<p><spring:message code="rnl.contract.the2" text="2. The"/>${command.desgName}<spring:message code="rnl.cpntract.behalf" text="for and on behalf of  the"/>${command.orgCode}&rdquo;<spring:message code="rnl.contract.awards.unto" text="hereby awards unto"/> ${command.toDate}<spring:message code="rnl.contract.appearing" text="i.e. the Contractor the  said contract on the terms and conditions hereinafter appearing."/></p>

<p><spring:message code="rnl.contract.commence"      text="3. The contract period of the said contract shall  commence from"/>${command.fromDate}<spring:message code="rnl.contract.forceup" text="and shall remain in force up to"/> ${command.toDate}<spring:message code="rnl.contract.terminated.by" text="unless this Contract Agreement is terminated by the"/> ${command.desgName} <spring:message code="rnl.contract.behalf" text="for and on behalf of the"/> ${command.orgCode}&rdquo;<spring:message code="rnl.contract.agreement" text="for breach of any of the  terms and conditions of this Contract Agreement."/></p>
<p><spring:message code="rnl.contract.msg"           text=" 4. The Contractor hereby agrees to undertake the said  contract during the contract period as enumerated in Clause 3 herein above  in consonance and in compliance of the terms and conditions as contained in the  documents hereinafter mentioned and on the terms and conditions hereinafter  appearing."/></p> 
<p><spring:message code="rnl.contract.agreement.msg" text="5. The following documents are and shall be deemed to form part of  this Contract Agreement and shall be read and construed as part of this  Contract Agreement as if they were incorporated in this Contract Agreement."/></p>
<p><spring:message code="rnl.contract.etender"       text="(i) e-Quotation /e-tender document dated"/>${command.tendorDate}<br>
<spring:message    code="rnl.contract.spec.msg"      text="(ii) Specification, terms and conditions, special  conditions, General"/><br>
<spring:message    code="rnl.contract.term.cond" 	 text="terms and conditions and schedule of quantities  enumerated in the e- Quotation /e-tender as annexed herewith"/><br>
<spring:message    code="rnl.contract.etender.contract" text="(iii) e-quotations/ e-tender submitted by the Contractor"/><br>
<spring:message    code="rnl.contract.financial"      text="(iv) Financial offer (percentage) submitted by the  Contractor"/><br>
<spring:message    code="rnl.contract.offer.letter"   text="(v) Offer letters awarding the said contract to the  Contractor."/></p>
<p><spring:message code="rnl.contract.consd.payment"  text="6. In consideration of the payment to be made by the"/>${command.desgName}<spring:message code="rnl.contract.behalfof" text="for and on behalf of the"/>${command.orgCode}&rdquo; <spring:message code="rnl.contract.covenants" text="to the Contractor  as hereinafter mentioned for execution of the said contract during the  contract period the Contractor hereby covenants with the"/> ${command.desgName} <spring:message code="rnl.contract.execte.docs" text="to execute the said contract in conformity in all respects  with the terms contained in the hereinabove referred documents."/></p>
<p><spring:message code="rnl.contract.seventh.the"    text="7. The"/> ${command.desgName} <spring:message code="rnl.contract.behalf" text="for and on behalf of  the"/>${command.orgCode}&rdquo;<spring:message code="rnl.contract.price" text="covenants to pay to the Contractor in consideration of the  execution of the said contract during the contract period the contract price  of Rs."/>  ${command.amount}/-<spring:message code="rnl.contract.agreements" text="at the times and in the manner prescribed by the  hereinabove referred documents which are deemed to form part of this Contract  Agreement."/></p>
<p><spring:message code="rnl.contract.eigth"          text="8. The"/> ${command.desgName}<spring:message code="rnl.contract.event" text="shall be entitled to  terminate this Contract Agreement by serving upon the Contractor a notice in  the event the"/>  ${command.desgName}<spring:message code="rnl.contract.refered.docs" text=" forms an opinion that the  Contractor during the contract period has failed to execute the said contract  work in conformity in all respects with the terms and conditions contained in  the hereinabove referred documents."/></p>
<p><spring:message code="rnl.contract.lawful"         text="9. The Contractor hereby agrees that it shall be lawful  for the"/> ${command.desgName}<spring:message code="rnl.contracct.clause" text="to terminate this Contract Agreement for  the reasons as enumerated in Clause 8 above."/></p>
<p><spring:message code="rnl.contract.tenth.dipute"   text="10. All cases of dispute shall be referred to the Addl.  Municipal Commissioner and the decision of the Addl. Municipal Commissioner on  such cases of dispute shall be final and binding upon the contractor. 11. The  legal charges (if any), stamp duty and registration charges of this Contract  Agreement shall be borne and paid by the Contractor."/></p>
 <br>
<p class="text-left">
<spring:message code="rnl.contract.agreement"   text="A G R E E M E N T"/><br>
<spring:message code="rnl.contract.shri.xxxxx"  text="Shri XXXXX"/><br>
<spring:message code="rnl.contract.law.officer" text="Law Officer,"/><br>
<spring:message code="rnl.contract.municipal.corporation" text="Municipal Corporation"/><br>
of ${command.orgName}&rdquo;,<br>
${command.orgAddress}</p>        
</div>
</div>

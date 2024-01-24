<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>

      <div class="widget ">
        <div class="widget-header">
          <h2>Adjustment History</h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
        </div>
        <div class="widget-content padding">
          <form:form action="AdjustmentEntry.html" method="POST" class="form-horizontal" id="adjustmentHistory">
          <c:if test="${command.historyMsg eq null}">
          <c:forEach items="${command.adjustmentHistoryDto}" var="dto" varStatus="dtoIndex">  
          
          <h4 class="margin-top-0">${dto.adjType} Adjustment (${dto.adjDate})</h4>
          
           <div class="table-responsive">
              <table class="table table-bordered table-condensed">
                <tr>
                  <th>Sr No.</th>
                  <th>Tax Name</th>
                  <th>Adjustment Amount</th>
                  <th>Adjusted Amount</th>
                   <th>Balance Amount</th>
                  <th>Remark</th>
                </tr>
                <c:forEach items="${dto.adjDetailDto}" var="detailsDTO" varStatus="detIndexdto">
                <tr>
                  <td>${detIndexdto.count}</td>
                  <td>
                  ${detailsDTO.taxDesc}</td>
                  <td>${detailsDTO.adjAmount}</td>
                  <td>
                 ${detailsDTO.adjAdjustedAmount}
                  </td>
                  <td>
            ${detailsDTO.adjBalanceAmount}
                  </td>
                  <td>
            ${detailsDTO.adjRemark}
                  </td>
                </tr>
                </c:forEach>
              </table> 
            </div>
            </c:forEach>
            </c:if>
        <c:if test="${command.historyMsg ne null}">
        ${command.historyMsg}
        </c:if>
            
          </form:form>
        </div>
      </div>

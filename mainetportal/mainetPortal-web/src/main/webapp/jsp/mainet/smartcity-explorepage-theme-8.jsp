<%@ page import="org.w3c.dom.Document"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility" />
<section id="explore">
    <div class="container-fluid explore-wrap">
      <div class="row">
        <div class="col-md-6 text-center explore-left">
          <img alt="logo" src="assets/img/logo.jpg" style="width: 15%;">
          <div class="section-title">
              <!-- <h2>Prayagraj Smart City</h2> -->
              <p style="color: #ed502e;">Welcome in Prayagraj <br />Smart City</p>
          </div>
          <div>
            <p class="project-text text-left">
              Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer
              
            </p>
            <button class="explore-btn" onclick="skipContent();">Explore</button>
            
          </div>
        </div>
        <div class="col-md-6 pl-0 pr-0 explore-right">
          <div class="owl-carousel explore-carousel">
              <div class="explore-wrap">
                <div class="explore-item">                
                    <div style="background:#FF0000; height: 100vh;">
                      <img alt="slide-1" src="assets/img/slide-1.jpg">
                    </div>
                </div>
              </div>

              <div class="explore-wrap">
                <div class="explore-item">                
                    <div style="background:#000; height: 100vh;">
                      <img src="assets/img/slide-2.jpg">
                    </div>
                </div>
              </div>

              <div class="explore-wrap">
                <div class="explore-item">
                    <div style="background:#CCC; height: 100vh;">
                      <img alt="slide-3" src="assets/img/slide-3.jpg">
                    </div>
                    
                </div>
              </div>
        </div>
      </div>
    </div>
  </section>
 
		
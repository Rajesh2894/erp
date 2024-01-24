package com.abm.mainet.authentication.security;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.Filepaths;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.HttpHelper;
import com.abm.mainet.common.util.JsonHelper;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.document.upload.protection.detector.ClamavDetector;
import com.abm.mainet.document.upload.protection.detector.DocumentDetector;
import com.abm.mainet.document.upload.protection.detector.ExcelDocumentDetectorImpl;
import com.abm.mainet.document.upload.protection.detector.PdfDocumentDetectorImpl;
import com.abm.mainet.document.upload.protection.detector.PowerpointDocumentDetectorImpl;
import com.abm.mainet.document.upload.protection.detector.WordDocumentDetectorImpl;
import com.abm.mainet.document.upload.protection.sanitizer.DocumentSanitizer;
import com.abm.mainet.document.upload.protection.sanitizer.IVirusDetector;
import com.abm.mainet.document.upload.protection.sanitizer.ImageDocumentSanitizerImpl;

public class GlobalRequestInterceptor extends HandlerInterceptorAdapter {
    private final static String homePagePath = "/CitizenHome.html";

    private static final Logger LOGGER = Logger.getLogger(GlobalRequestInterceptor.class);

    private boolean isHomePageUrl(final HttpServletRequest request) {

        LOGGER.info("request.getServletPath():---- " + request.getServletPath());
        return request.getServletPath().equalsIgnoreCase(homePagePath)
                || request.getServletPath().contains("HitCounterCookies.html")
                || request.getServletPath().contains("/ULBHome.html")
                || request.getServletPath().contains("/ULBHome.html?ulbDomain")
                || request.getServletPath().contains("RouteInfo.html")
                || request.getServletPath().contains("CitizenHome.html");
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
    	LOGGER.info(" Before GlobalRequestInterceptor preHandle isHomePageUrl  " );
    	
        if (isHomePageUrl(request)) {
            return true;
        }
    	LOGGER.info(" After GlobalRequestInterceptor preHandle isHomePageUrl " + request.getParameter("session"));
//As per Palam Sir Code for User session null after payment gateway page open and payment done 
        HttpSession session=null;
   String sessionId=request.getParameter("session");
   if(sessionId!=null && sessionId.trim().length()>0) {
	   session= request.getSession(false);
   }
   else {
	   session= request.getSession(false);  
   }
   LOGGER.info(" After GlobalRequestInterceptor preHandle Session Id " + sessionId);
        

        if (!isSessionValid(session)) {
            redirectToHomePage(request, response);
            return false;
        }
        
        if(request.getContentType() != null && request.getContentType().contains("multipart/form-data")) {
	        if (!isRequestedFileValid(request)) {
	            //redirectToHomePage(request, response);
	            //response.sendRedirect("CitizenHome.html");
				/*
				 * String url = "http://" + request.getServerName() + ":" +
				 * request.getServerPort() + request.getContextPath() + "/AdminHome.html";
				 * LOGGER.info("url:---- " + url); //response.sendRedirect(url);
				 * redirectToHomePage1(request, response);
				 */
	            //response.sendRedirect("/CitizenHome.html");
	        	
	            return false;
	        }
        }

        return true;
    }

    @Override
    public void postHandle(final HttpServletRequest request,
            final HttpServletResponse response, final Object handler,
            final ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setDateHeader("Expires", 0); // Proxies.
        // response.addHeader("P3P", "CP=\"IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT\"");
        response.setHeader("SET-COOKIE", "JSESSIONID=" + request.getSession().getId() + "; HttpOnly"+";SameSite=None; Secure");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
       response.setHeader("Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept");
        response.setHeader("Access-Control-Max-Age", "18000");

    }

    private void redirectToHomePage(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final String redirectLoginUrl = StringUtils.replace(request.getRequestURL().toString(), request.getServletPath(),
                homePagePath);

        final boolean asyncRequest = HttpHelper.isAjaxRequest(request);

        if (asyncRequest) {
            response.setContentType("application/json");

            final Map<String, String> jsonMap = new HashMap<>();
            final String msg = "<h5 class='text-center padding-10 text-blue-2'>"
                    + ApplicationSession.getInstance().getMessage("eip.admin.session")
                    + "<h5><div class='text-center'><a href='CitizenHome.html' class='btn btn-info'>click here</a></div>";
            jsonMap.put(msg, null);
            String output = JsonHelper.toJsonString(jsonMap);
            output = output.replaceAll("[{}:,]", MainetConstants.BLANK);
            output = output.replace("null", MainetConstants.BLANK);
            final int outputLength = output.length();
            if ((outputLength >= 2) && (output.charAt(0) == '"') && (output.charAt(outputLength - 1) == '"')) {
                output = output.substring(1, outputLength - 1);
            }
            response.getOutputStream().write(output.getBytes());
        } else {
            response.sendRedirect(redirectLoginUrl);
        }
    }
    
    public Boolean isSessionValid(final HttpSession session) {
        boolean validSession = false;
        if ((session != null) && (UserSession.getCurrent().getOrganisation() != null)) {
            validSession = true;
        }
        return validSession;

    }
    private boolean isRequestedFileValid(final HttpServletRequest req) {
    	File tmpFile = null;
        
        String path=Filepaths.getfilepath();
        InputStream inputStream = null;
        
        try {
 
            
			/*
			 * final MultipartHttpServletRequest multiRequest =
			 * (MultipartHttpServletRequest) req; MultipartFile file =
			 * multiRequest.getFile("files");
			 */
        	
	        	MultipartRequest multipartRequest = (MultipartRequest) req;
	        	Map map = multipartRequest.getFileMap();
	        	MultipartFile file = null;
	        	for (Iterator iter = map.values().iterator(); iter.hasNext();) {
	        	 file = (MultipartFile) iter.next();
	        	 
	        	
	
	            String fileType = file.getContentType();
	            
	            fileType = fileType.split("/")[1];
	            fileType = fileType.toUpperCase();
	            if ((fileType == null) || (fileType.trim().length() == 0)) {
	                throw new IllegalArgumentException("Unknown file type specified !");
	            }
	            if(fileType.equals("JPG") || fileType.equals("JPEG")) {
	            	return true;
	            }
	
	            if ((file == null) || (file.getInputStream() == null)) {
	                throw new IllegalArgumentException("Unknown file content specified !");
	            }
	
	            
	            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	            path = path + "/RestictedFile";
	            File dir = new File(path);
	            if(!dir.exists()) {
	            	dir.mkdir();
	            }
	            path = path+"/temp"+timestamp.getTime()+"."+fileType;	 
	            tmpFile = new File(path);
	           
	            inputStream = file.getInputStream();
	            
	            FileUtils.copyInputStreamToFile(inputStream, tmpFile);
	            
	            inputStream.close();
	
	            
	            boolean isSafe;
	            
	            if(fileType.equals("PNG") || fileType.equals("PCX") || fileType.equals("TIFF") || 
	            		fileType.equals("DCX") || fileType.equals("BMP") || fileType.equals("GIF") || 
	            		fileType.equals("WBMP") || fileType.equals("XBM") || fileType.equals("XPM")
	            		) {
                    fileType = "IMAGE";
                }else if(fileType.equals("XLS") || fileType.equals("XLSX") || fileType.equals("XLSM") 
	            		|| fileType.equals("XLSB") || fileType.equals("XLT") || fileType.equals("XLTM") || fileType.equals("VND.MS-EXCEL") 
	            		|| fileType.equals("VND.OPENXMLFORMATS-OFFICEDOCUMENT.SPREADSHEETML.SHEET") ){
	            	fileType = "EXCEL";
	            }else if(fileType.equals("MSWORD") || fileType.equals("DOC") || fileType.equals( "DOCX") || fileType.equals( "DOCM") 
	            		|| fileType.equals( "WML") || fileType.equals( "DOT") || fileType.equals( "DOTM")
	            		 || fileType.equals("VND.OPENXMLFORMATS-OFFICEDOCUMENT.WORDPROCESSINGML.DOCUMENT")) {
	            	fileType = "WORD";
	            }else if(fileType.equals("PPT") || fileType.equals("PPTX")) {
	            	fileType = "POWERPOINT";
	            }else if(fileType.equals("EXE")) {
	            	return false;
	            }else if(fileType.equals("FLV") || fileType.equals("MP4") || fileType.equals("AVI") 
	            		|| fileType.equals("GIF") || fileType.equals("RM") 
	            		|| fileType.equals("M2V") || fileType.equals("MPG") || fileType.equals("MPEG")
	            		|| fileType.equals("3GP") || fileType.equals("3G2") || fileType.equals("RM")
	            		){
	            	return true;
	            }
	            
	  
	            DocumentDetector documentDetector;
	            DocumentSanitizer documentSanitizer;
	            switch (fileType) {
	                case "PDF":
	                    documentDetector = new PdfDocumentDetectorImpl();
	                    isSafe = documentDetector.isSafe(tmpFile);
	                    break;
	                case "WORD":
	                    documentDetector = new WordDocumentDetectorImpl();
	                    isSafe = documentDetector.isSafe(tmpFile);
	                    break;
	                case "EXCEL":
	                    documentDetector = new ExcelDocumentDetectorImpl();
	                    isSafe = documentDetector.isSafe(tmpFile);
	                    break;
	                case "POWERPOINT":
	                    documentDetector = new PowerpointDocumentDetectorImpl();
	                    isSafe = documentDetector.isSafe(tmpFile);
	                    break;
	                case "IMAGE":
	                    documentSanitizer = new ImageDocumentSanitizerImpl();
	                    isSafe = documentSanitizer.madeSafe(tmpFile);
	                    break;
	                default:
	                    throw new IllegalArgumentException("Unknown file type specified !");
	            } 
	
	            if (!isSafe) {
	                return false;
	            } 
	            //virus scan code is de activeted
	            /*if(isSafe) { 
	            	if(isVirusSafe(file)) { 
	            		return false; 
	            	} 
	            }*/
        	}

        } catch (Exception e) {
            return false;
        }finally {
        	try {
        		if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException io) {

                    }
                }
        		new FileOutputStream(tmpFile).close();
        		
				  if(tmpFile.exists()) { 
					  tmpFile.delete(); 
				  }
				 
			} catch (Exception e2) {
				
				LOGGER.error("tmpFile not delete:---- ");
			}
        }
    	return true;
    }
    
    public boolean isVirusSafe(MultipartFile fileToScan) {
    	final IVirusDetector virusDetector = new ClamavDetector();
		return virusDetector.isSafe(fileToScan);

	}
}

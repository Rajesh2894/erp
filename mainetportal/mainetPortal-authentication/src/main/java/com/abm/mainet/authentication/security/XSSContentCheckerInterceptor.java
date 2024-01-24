package com.abm.mainet.authentication.security;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.security.web.util.UrlUtils;

//import org.apache.log4j.Logger;
import com.abm.mainet.common.constant.MainetConstants;

public class XSSContentCheckerInterceptor extends HttpServletRequestWrapper {
	//private static final Logger LOG = Logger.getLogger(XSSContentCheckerInterceptor.class); 
	
	private Map<String, String[]> sanitizedQueryString;
	
    private static Pattern[] patterns = new Pattern[]{
    		Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
            Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
            Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
            Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
            Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("\\.\\/", Pattern.CASE_INSENSITIVE),
            Pattern.compile("\\.\\.\\/", Pattern.CASE_INSENSITIVE),
            Pattern.compile("<html>(.*?)</html>", Pattern.CASE_INSENSITIVE),
            Pattern.compile("<embed(.*?)>", Pattern.CASE_INSENSITIVE),
            Pattern.compile("<marquee(.*?)>", Pattern.CASE_INSENSITIVE),
            Pattern.compile("<audio(.*)?>", Pattern.CASE_INSENSITIVE  | Pattern.MULTILINE | Pattern.DOTALL),//<aUdIo SrC=x OnErRoR=alert(78347)>
            Pattern.compile("<video(.*)?>", Pattern.CASE_INSENSITIVE  | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("onerror(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("src=(.*?)", Pattern.CASE_INSENSITIVE  | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("onmouseover(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("onmousemove(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("onmousedown(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("onmouseout(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("onunload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("onresize(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("onclick(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("ondbclick(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("onchange(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("onblur(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("onfocus(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("onselect(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("onsubmit(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("onreset(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("onkeydown(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("onkeyup(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("onkeypress(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL), 
            Pattern.compile("<.*?>(.*?)</.*?>", Pattern.CASE_INSENSITIVE),           
            Pattern.compile("</.*?>", Pattern.CASE_INSENSITIVE),
            Pattern.compile("<.*?(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
            
        };
    private static Pattern[] patternsSec = new Pattern[]{
       //Allowed script tag in CKEditor to support GIS integration which require script tag in CKEditor
		/*	Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
			Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
			Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),*/
			Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
			Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
					| Pattern.DOTALL),
    		Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
    		Pattern.compile("\\.\\/", Pattern.CASE_INSENSITIVE),
            Pattern.compile("\\.\\.\\/", Pattern.CASE_INSENSITIVE),
    		Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
    		Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
    		Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
    		Pattern.compile("<audio(.*)?>", Pattern.CASE_INSENSITIVE  | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("onerror(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
        };
    private static List<String> urlSanitizeList = Arrays.asList("\'", "\"","//",":","./");
    private static List<String> reqSanitizeList = Arrays.asList("\'", "\"");
    
    public XSSContentCheckerInterceptor(final HttpServletRequest request) {
        super(request);
    }

    
    @Override
    public String[] getParameterValues(final String parameter) {
        final String[] values = super.getParameterValues(parameter);   
        if (values == null) {
            return null;
        }
        final int count = values.length;
        final String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			
			if (parameter.trim().equalsIgnoreCase(MainetConstants.STRING1)
					|| parameter.trim().equalsIgnoreCase(MainetConstants.STRING2)) {
				encodedValues[i] = stripXSSOne(values[i]);
			} else {
				encodedValues[i] = stripXSS(values[i]);
			}
			if ( parameter.equalsIgnoreCase("url")) {
				encodedValues[i] = stripXSSUrls(encodedValues[i],urlSanitizeList);
			}
		}
       
        return encodedValues;
    }
   
    
    @Override
    public String getParameter(final String parameter) {
         String value = super.getParameter(parameter);      
        if(value !=null) {
        	if(UrlUtils.isValidRedirectUrl(value) == true) {
        		 return MainetConstants.operator.EMPTY;
			 }
        	 if(parameter.trim().equalsIgnoreCase(MainetConstants.STRING1) || parameter.trim().equalsIgnoreCase(MainetConstants.STRING2)) {    	      	
        		 return stripXSSOne(value);
             }else {	
            	 return  stripXSS(value);
             }
        	 
        }
        return value;       
    }

    @Override

    public String getHeader(final String name) {
        final String value = super.getHeader(name);
        return stripXSS(value);
    }
    private String stripXSS(String value) {
        if (value != null) {
        	for (Pattern scriptPattern : patterns){
                value = scriptPattern.matcher(value).replaceAll("");
            }
        	value =  stripXSSUrls(value,reqSanitizeList);
        }
        return value;
    }
    private String stripXSSOne(String value) {
        if (value != null) {
        	for (Pattern scriptPattern : patternsSec){
                value = scriptPattern.matcher(value).replaceAll("");
            }
        }
        return value;
    }
    private String stripXSSUrls(String urlVal,List<String> sanitizeList) {
        if (urlVal != null && !urlVal.isEmpty()) {
        	int firstIndex =0;
			int lastIndex =0;
			int diff =0;
			for (String sa : sanitizeList) {
				 firstIndex = urlVal.indexOf(sa);
				 lastIndex = urlVal.lastIndexOf(sa);
				 diff = Math.subtractExact(lastIndex, firstIndex);
				if (diff != 0 || (lastIndex != -1 && diff == 0)) {
					urlVal = urlVal.substring(0, firstIndex) + urlVal.substring(lastIndex + 1);
				}
			}
			return urlVal;
        }
        return urlVal;
    }
    @Override 
	  public String getQueryString() {
		  String value = super.getQueryString(); 
		  if (value != null) { 
			  try { 
				  value =URLDecoder.decode(value, "UTF-8");
				  if( UrlUtils.isValidRedirectUrl(value) == true) {
					  return MainetConstants.operator.EMPTY;
				  }
					  value = stripXSSUrls(value,urlSanitizeList);
				  String changedValue = stripXSS(value);
				  if(value.equalsIgnoreCase(changedValue)) {
					  value = changedValue; 
				  }else {
					  return changedValue;
				  }
				} catch (Exception e) { // TODO: handle exception 
					
				}
		  } 
		  return value; 
	  }
    
    @Override
	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(getParameterMap().keySet());
	}
    
    @Override
	public Map<String, String[]> getParameterMap() {
		if (sanitizedQueryString == null) {
			Map<String, String[]> res = new HashMap<String, String[]>();
			Map<String, String[]> originalQueryString = super.getParameterMap();
			if (originalQueryString != null) {
				for (String key : (Set<String>) originalQueryString.keySet()) {
					String[] rawVals = originalQueryString.get(key);
					
					String[] snzVals = new String[rawVals.length];
					for (int i = 0; i < rawVals.length; i++) {
						snzVals[i] = stripXSS(rawVals[i]);
					}
					res.put(stripXSS(key), snzVals);
				}
			}
			sanitizedQueryString = res;
		}
		return sanitizedQueryString;
	}
	
	@Override
	public String getRequestURI() {
		String reqUri = super.getRequestURI();
		if(reqUri !=null && !reqUri.isEmpty()) {
			try {
				reqUri =URLDecoder.decode(reqUri, "UTF-8");
				reqUri =	stripXSSUrls(reqUri,urlSanitizeList);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return reqUri;
	}

}

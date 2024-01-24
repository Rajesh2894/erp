package com.abm.mainet.security;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
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

import com.abm.mainet.common.constant.MainetConstants;

public class XSSContentCheckerInterceptor extends HttpServletRequestWrapper {

    private static List<Pattern> patternLookUp = new ArrayList<>();
    private Map<String, String[]> sanitizedQueryString;
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
        	if (parameter!=null && parameter.contains("resolutionComments") || parameter.equals("scrutinyDecisionRemark")) {//#134164 for ck-editor pages
        		 encodedValues[i] = stripXSSOne(values[i]);
        	}else {//non-ck editor
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
        final String value = super.getParameter(parameter);

        return stripXSS(value);

    }

    @Override

    public String getHeader(final String name) {
        final String value = super.getHeader(name);
        return stripXSS(value);

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
				e.printStackTrace();
			}
			
		}
		return reqUri;
	}
    private String stripXSS(String value) {
        if (value != null) {
            for (final Pattern pattern : getPatternLookUp()) {
                if (pattern.matcher(value).find()) {
                    value = stripXSS(pattern.matcher(value).replaceAll(""));
                }
                value =  stripXSSUrls(value,reqSanitizeList);
            }
        }
        return value;
    }
    private String stripXSSOne(String value) {//#134164 ck-editor pages
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
    public static void setPatternList() {
        getPatternLookUp().add(Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE));
        getPatternLookUp().add(Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        getPatternLookUp().add(Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        getPatternLookUp().add(Pattern.compile("</script>", Pattern.CASE_INSENSITIVE));
        getPatternLookUp().add(Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        getPatternLookUp().add(Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        getPatternLookUp()
                .add(Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        getPatternLookUp().add(Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE));
        getPatternLookUp().add(Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE));
        getPatternLookUp().add(Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        getPatternLookUp().add(Pattern.compile("\\.\\/", Pattern.CASE_INSENSITIVE));
        getPatternLookUp().add(Pattern.compile("\\.\\.\\/", Pattern.CASE_INSENSITIVE));
        getPatternLookUp().add(Pattern.compile("<html>(.*?)</html>", Pattern.CASE_INSENSITIVE));
        getPatternLookUp().add(Pattern.compile("<embed(.*?)>", Pattern.CASE_INSENSITIVE));
        getPatternLookUp().add(Pattern.compile("<img .*?>", Pattern.CASE_INSENSITIVE));
        getPatternLookUp().add(Pattern.compile("<img .*?", Pattern.CASE_INSENSITIVE));
        getPatternLookUp().add(Pattern.compile("(<svg)([^<]*|[^>]*)", Pattern.CASE_INSENSITIVE));
        getPatternLookUp().add(Pattern.compile("onerror(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        getPatternLookUp().add(Pattern.compile("<a .*?>(.*?)</a>", Pattern.CASE_INSENSITIVE));
        
        getPatternLookUp().add(Pattern.compile("<marquee(.*?)>", Pattern.CASE_INSENSITIVE));
        getPatternLookUp().add(Pattern.compile("<audio(.*)?>", Pattern.CASE_INSENSITIVE));
        getPatternLookUp().add(Pattern.compile("<video(.*)?>", Pattern.CASE_INSENSITIVE));
        getPatternLookUp().add(Pattern.compile("onerror(.*?)=", Pattern.CASE_INSENSITIVE));
        getPatternLookUp().add(Pattern.compile("src=(.*?)", Pattern.CASE_INSENSITIVE));
        
        getPatternLookUp().add(Pattern.compile("onmouseover(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        getPatternLookUp().add(Pattern.compile("onmousemove(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        getPatternLookUp().add(Pattern.compile("onmousedown(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        getPatternLookUp().add(Pattern.compile("onmouseout(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        getPatternLookUp().add(Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        getPatternLookUp().add(Pattern.compile("onunload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        getPatternLookUp().add(Pattern.compile("onresize(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        getPatternLookUp().add(Pattern.compile("onclick(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        getPatternLookUp().add(Pattern.compile("ondbclick(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        getPatternLookUp().add(Pattern.compile("onchange(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        getPatternLookUp().add(Pattern.compile("onblur(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        getPatternLookUp().add(Pattern.compile("onfocus(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        getPatternLookUp().add(Pattern.compile("onselect(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        getPatternLookUp().add(Pattern.compile("onsubmit(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        getPatternLookUp().add(Pattern.compile("onreset(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        getPatternLookUp().add(Pattern.compile("onkeydown(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        getPatternLookUp().add(Pattern.compile("onkeyup(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        getPatternLookUp().add(Pattern.compile("onkeypress(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)); 
        getPatternLookUp().add(Pattern.compile("<.*?>(.*?)</.*?>", Pattern.CASE_INSENSITIVE));           
        getPatternLookUp().add(Pattern.compile("</.*?>", Pattern.CASE_INSENSITIVE));
        getPatternLookUp().add(Pattern.compile("<.*?(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        }

    private static List<Pattern> getPatternLookUp() {
        return patternLookUp;
    }
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

}

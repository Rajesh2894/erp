package com.abm.mainet.common.rest.ui.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;

@RestController
@RequestMapping("/health/api")
public class APIHealthStatusController {

    private static final String SECRET_KEY = "abm";
    
    private static final Logger LOGGER = LoggerFactory.getLogger(APIHealthStatusController.class);


    @RequestMapping(value = "/prefix/cache/hiview", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> viewHiPrefixCache(@RequestParam(value="key", defaultValue="") String key, @RequestParam(value="orgid") Integer orgid,@RequestParam(value="prefix") String prefix, final HttpServletRequest request) {

	Map<String, Object> prefixCache = new HashMap<>();
	
	if(!SECRET_KEY.equals(key)){
	    prefixCache.put(HttpStatus.NOT_ACCEPTABLE.toString(), HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
	    return prefixCache;
	}
	

	
	ApplicationSession session =  ApplicationSession.getInstance();
	
	if(orgid != null && StringUtils.isNoneEmpty(prefix)){
	    prefixCache.put("HirachicalDetailMap", session.getHirachicalDetailMap().get(orgid).get(prefix));    
	}else if(orgid != null && StringUtils.isBlank(prefix)){
	    prefixCache.put("HirachicalDetailMap", session.getHirachicalDetailMap().get(orgid));
	}else{
	    prefixCache.put("HirachicalDetailMap", session.getHirachicalDetailMap());
	}
	
	if(orgid != null && StringUtils.isNoneEmpty(prefix)){
	    Iterator<Entry<String, Map<Long, LookUp>>> itr = session.getHirachicalLevelMap().get(orgid).entrySet().iterator();
	    while(itr.hasNext()){
		Entry<String, Map<Long, LookUp>> fistlevel = itr.next();
		if(fistlevel.getKey().equalsIgnoreCase(prefix)){
		    prefixCache.put("HirachicalLevelMap{"+orgid+"}{"+fistlevel.getKey()+"}", fistlevel.getValue());
		}
	    }
	        
	}else if(orgid != null && StringUtils.isBlank(prefix)){
	    prefixCache.put("HirachicalLevelMap", session.getHirachicalLevelMap().get(orgid));
	}else{
	    prefixCache.put("HirachicalLevelMap", session.getHirachicalLevelMap());
	}
	
	return prefixCache;
	
    }
    
    @RequestMapping(value = "/prefix/cache/nonReplicatePrefix", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> viewNonReplicatePrefix(@RequestParam(value="key", defaultValue="") String key, final HttpServletRequest request) {
	Map<String, Object> prefixCache = new HashMap<>();
	if(!SECRET_KEY.equals(key)){
	    prefixCache.put(HttpStatus.NOT_ACCEPTABLE.toString(), HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
	    return prefixCache;
	}
	
	ApplicationSession session =  ApplicationSession.getInstance();
	
	prefixCache.put("NonReplicatePrefix", session.getNonReplicatePrefix());
	return prefixCache;
	
    }

    
    
    
    @RequestMapping(value = "/prefix/cache/nonhiview", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> viewNonHiPrefixCache(@RequestParam(value="key", defaultValue="") String key, @RequestParam(value="orgid") Integer orgid, final HttpServletRequest request) {

	Map<String, Object> prefixCache = new HashMap<>();
	
	if(!SECRET_KEY.equals(key)){
	    prefixCache.put(HttpStatus.NOT_ACCEPTABLE.toString(), HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
	    return prefixCache;
	}
	
	
	ApplicationSession session =  ApplicationSession.getInstance();
	if(orgid != null){
	    prefixCache.put("NonHirachicalPrefixDetails", session.getNonHirachicalDetailMap().get(orgid));    
	}else{
	    prefixCache.put("NonHirachicalPrefixDetails", session.getNonHirachicalDetailMap());
	}
	
	return prefixCache;
	
    }
    
    
    @RequestMapping(value = "/prefix/cache/reload", method = RequestMethod.GET)
    @ResponseBody
    public String relaodPrefixCache(@RequestParam(value="key", defaultValue="") String key,final HttpServletRequest request) {
	if(!SECRET_KEY.equals(key)){
	    return HttpStatus.NOT_ACCEPTABLE.getReasonPhrase();
	}
	ApplicationSession session =  ApplicationSession.getInstance();
	session.updatePrefixCache("H");
	session.updatePrefixCache("NONH");
	
	return HttpStatus.OK.name();
    }
    
    @RequestMapping(value = "/prefix/cache/init", method = RequestMethod.GET)
    @ResponseBody
    public String initPrefixCache(@RequestParam(value="key", defaultValue="") String key, final HttpServletRequest request) {
	if(!SECRET_KEY.equals(key)){
	    return HttpStatus.NOT_ACCEPTABLE.getReasonPhrase();
	}
	ApplicationSession session =  ApplicationSession.getInstance();
	session.init();
	return HttpStatus.OK.name();
    }
    
}

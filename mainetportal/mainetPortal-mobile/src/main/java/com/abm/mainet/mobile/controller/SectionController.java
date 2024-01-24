
package com.abm.mainet.mobile.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.cms.domain.Feedback;
import com.abm.mainet.cms.domain.OpinionPollOptionResponse;
import com.abm.mainet.cms.dto.OpinionDTO;
import com.abm.mainet.cms.dto.OpinionPollDTO;
import com.abm.mainet.cms.service.ISectionService;
import com.abm.mainet.cms.service.IThemeMasterService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.mobile.dto.CommonAppResponseDTO;
import com.abm.mainet.mobile.service.IOpinionPollService;

@ServletSecurity(httpMethodConstraints = {
		@HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/section")
public class SectionController {

	private static final Logger LOG = Logger.getLogger(OpinionPollController.class);

	@Autowired
    private ISectionService iSectionService;
	
    @Autowired
	private IThemeMasterService themeMasterService;

	@RequestMapping(value = "/name/{name}/lang/{langid}/org/{orgid} ", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String,List<String>> getOpinionPollResponse(@PathVariable String name,@PathVariable long langid,@PathVariable long orgid) {
		Map<String,List<String>> data = new HashMap<>();
		try {
			Map<String, String>  themeExistsCheck = new HashMap<String, String>();
			List<String> messageList = new ArrayList<String>();
			name =(name !=null) ? name.trim() : "";
			try {
					themeExistsCheck = themeMasterService.getThemeMasterMapByOrgid(orgid);
			}catch(Exception e) {
				LOG.error("Exception occured while fetching data from theme Map");
			}
          if(themeExistsCheck ==null || themeExistsCheck.isEmpty() || themeExistsCheck.get(name) == null || themeExistsCheck.get(name).isEmpty() || !themeExistsCheck.get(name).equals(MainetConstants.IsLookUp.INACTIVE)) {
	        messageList = iSectionService.getAllhtml(name,langid,orgid);
          }
			
			if(messageList != null && messageList.size() > 0) {
				data.put("messageList", messageList);
			}
			
		} catch (final Exception exception) {
			LOG.error("Exception occure during fetching Organisations List");
			data.put("messageList", null);
		}
		return data;
		
	}
	
}


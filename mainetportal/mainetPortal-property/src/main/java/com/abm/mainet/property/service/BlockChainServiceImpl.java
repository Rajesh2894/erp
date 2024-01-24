package com.abm.mainet.property.service;


import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.property.dto.BlockChainDTO;
import com.abm.mainet.property.dto.BlockChainResponseDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;


@Service
public class BlockChainServiceImpl implements BlockChainService{

    @Override
    public ProvisionalAssesmentMstDto getOwnerDetails(String propNo, String oldPropNo, Long orgId) {   
        ProvisionalAssesmentMstDto dto = new ProvisionalAssesmentMstDto();       
        DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("propNo", propNo);
        requestParam.put("oldPropNo", oldPropNo);
        requestParam.put("orgId", orgId.toString());
        /*URI url = dd.expand("http://192.168.100.60:8085/MainetService/services/property/rest/mutation/fetchDetailForMutataion/propNo/{propNo}/oldPropNo/{oldPropNo}/orgId/{orgId}", requestParam);*/
       
        StringBuilder url = new StringBuilder("http://192.168.100.60:8085/MainetService/services/property/rest/mutation/fetchDetailForMutataion/9000019/null/9");
        dto = (ProvisionalAssesmentMstDto) JersyCall.callRestTemplateClient(null, url.toString(), new ParameterizedTypeReference<ProvisionalAssesmentMstDto>(){ });
      
        return dto;
    }

    @SuppressWarnings("unchecked")
    @Override
    public BlockChainResponseDto getPropertyDetails(Map<String, Object> blockChainBody) {
        BlockChainDTO dto = new BlockChainDTO();
        // StringBuilder url = new StringBuilder("https://54A2FD24794543F5927CDF5F4D172EAE.blockchain.ocp.oraclecloud.com:443/restproxy1/bcsgw/rest/v1/transaction/invocation ");        
        //JersyCall.callRestTemplateClient(null, url.toString()); 
        final Object responseObj = JesryCallWithAuthetication.callRestTemplateClient(blockChainBody, ApplicationSession.getInstance().getMessage("https://landrecbcinst1-indiafieldse.blockchain.ocp.oraclecloud.com:443/restproxy1/bcsgw/rest/v1/transaction/invocation"));
        
        ObjectMapper objMapper = new ObjectMapper();
       return objMapper.convertValue(responseObj, BlockChainResponseDto.class);
                  
        //final String d = new JSONObject(responseObj).toString();
        

        //return responseObj;
    } 

}

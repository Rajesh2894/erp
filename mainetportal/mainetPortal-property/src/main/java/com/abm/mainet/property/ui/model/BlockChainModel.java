package com.abm.mainet.property.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.property.dto.BlockChainDTO;
import com.abm.mainet.property.dto.BlockChainResponseDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.service.BlockChainService;

@Component
@Scope("session")
public class BlockChainModel extends AbstractFormModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(BlockChainModel.class);

    @Autowired
    BlockChainService blockChainService;

    BlockChainDTO blockChainDTO = new BlockChainDTO();

    private ProvisionalAssesmentMstDto provisionalAssesmentMstDto = new ProvisionalAssesmentMstDto();// Main DTO to Bind Data

    private List<ProvisionalAssesmentMstDto> provAssMstDtoList = new ArrayList<>();

    @Override
    public boolean saveForm() {
        try {
             ArrayList<String> args = new ArrayList<>();
            Map<String, Object> BlockChainBody = new HashMap<>();
            //ArrayList<Object> args = new ArrayList<Object>();
           // String Str = blockChainDTO.getPropNo().replaceAll("\\\\", " ") + "," +blockChainDTO.getWitnessDetails().get(1).getWitnessName().toString();
            int s= blockChainDTO.getPropNo().length();
            args.add(blockChainDTO.getPropNo().substring(1, s-1));
            args.add(blockChainDTO.getWitnessDetails().get(1).getWitnessName().toString());

            BlockChainBody.put("channel", "assettransfer");
            BlockChainBody.put("chaincode", "cityassetrule");
            BlockChainBody.put("method", "transferMarble");
            BlockChainBody.put("args", args);
            BlockChainBody.put("chaincodeVer", "v0");
            BlockChainResponseDto dto = blockChainService.getPropertyDetails(BlockChainBody);
            if (dto.getReturnCode().equalsIgnoreCase("Success")) {
                LOGGER.info(dto);
                this.setSuccessMessage(getAppSession().getMessage("Property Transfer Successfully"));
            }
        } catch (Exception e) {
            throw new FrameworkException("Error occured", e);

        }

        return true;
    }

    public ProvisionalAssesmentMstDto getProvisionalAssesmentMstDto() {
        return provisionalAssesmentMstDto;
    }

    public void setProvisionalAssesmentMstDto(ProvisionalAssesmentMstDto provisionalAssesmentMstDto) {
        this.provisionalAssesmentMstDto = provisionalAssesmentMstDto;
    }

    public List<ProvisionalAssesmentMstDto> getProvAssMstDtoList() {
        return provAssMstDtoList;
    }

    public void setProvAssMstDtoList(List<ProvisionalAssesmentMstDto> provAssMstDtoList) {
        this.provAssMstDtoList = provAssMstDtoList;
    }

    public BlockChainDTO getBlockChainDTO() {
        return blockChainDTO;
    }

    public void setBlockChainDTO(BlockChainDTO blockChainDTO) {
        this.blockChainDTO = blockChainDTO;
    }

}

package com.abm.mainet.property.ui.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.property.dto.BlockChainResponseDto;
import com.abm.mainet.property.dto.OwnerDTO;
import com.abm.mainet.property.dto.WitnessDTO;
import com.abm.mainet.property.service.BlockChainService;
import com.abm.mainet.property.ui.model.BlockChainModel;

@Controller
@RequestMapping("/BlockChain.html")
public class BlockChainController extends AbstractFormController<BlockChainModel> {

    private static final Logger LOGGER = Logger.getLogger(BlockChainController.class);

    @Autowired
    BlockChainService blockChainService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) {
        this.sessionCleanup(request);
        this.getModel();
        return defaultResult();
    }

    @RequestMapping(params = "initiateTransfer", method = { RequestMethod.POST })
    public ModelAndView initiateTransfer(HttpServletRequest request) {

        return new ModelAndView("BlockChainTransfer", MainetConstants.FORM_NAME, this.getModel());
    }
    
    @RequestMapping(params = "back", method = { RequestMethod.POST })
    public ModelAndView back(HttpServletRequest request) {
        this.getModel();
        return new ModelAndView("BackBlockChain", MainetConstants.FORM_NAME, this.getModel());
    }
    
    @RequestMapping(params = "initiateTransferConfirmation", method = { RequestMethod.POST })
    public ModelAndView initiateTransferConfirmation(HttpServletRequest request,String oName,String oContactNo,String oAddress,String oPanNo) {
        String form = "BlockChainTransfer";
        try {
        ArrayList<String> args = new ArrayList<>();
        Map<String, Object> BlockChainBody = new HashMap<>();
       
        //ArrayList<Object> args = new ArrayList<Object>();
       // String Str = blockChainDTO.getPropNo().replaceAll("\\\\", " ") + "," +blockChainDTO.getWitnessDetails().get(1).getWitnessName().toString();
        int s= this.getModel().getBlockChainDTO().getPropNo().length();
        args.add(this.getModel().getBlockChainDTO().getPropNo());
        //args.add(this.getModel().getBlockChainDTO().getWitnessDetails().get(1).getWitnessName().toString());
        args.add(oName);
            BlockChainBody.put("channel", "testchannel");
            BlockChainBody.put("chaincode", "testChaincode");
        BlockChainBody.put("method", "transferMarble");
        BlockChainBody.put("args", args);
        BlockChainBody.put("chaincodeVer", "v0");
        WitnessDTO witness= new WitnessDTO();
        witness.setWitnessAddress(oAddress);
        witness.setWitnessContactNo(oContactNo);
        witness.setWitnessName(oName);
        witness.setWitnessPANNo(oPanNo);
        
        this.getModel().getBlockChainDTO().getWitnessDetails().add(witness);
        BlockChainResponseDto dto = blockChainService.getPropertyDetails(BlockChainBody);
        if (dto.getReturnCode().equalsIgnoreCase("Success")) {
            LOGGER.info(dto);
            form = "BlockChainTransferConfirmation";
           
        }
    } catch (Exception e) {
        throw new FrameworkException("Error occured", e);

    }

       
        return new ModelAndView(form , MainetConstants.FORM_NAME, this.getModel());
    }
    
    @RequestMapping(params = "SearchProperty", method = { RequestMethod.POST })
    public ModelAndView searchProperty(HttpServletRequest request) {

        return new ModelAndView("BlockChainSearch", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "searchPropertyOwnerDetails", method = { RequestMethod.POST })
    public ModelAndView searchPropertyOwnerDetails(@RequestParam(required = false) String propNo, HttpServletRequest request) {
       
        try {
            Map<String, Object> BlockChainBody = new HashMap<>();
            ArrayList<String> args = new ArrayList<String>();
            args.add(propNo);
            // this.getModel().getBlockChainDTO().getArgs().add(propNo);
            BlockChainBody.put("channel", "testchannel");
            BlockChainBody.put("chaincode", "testChaincode");
            BlockChainBody.put("method", "getHistoryForMarble");
            BlockChainBody.put("args", args);
            BlockChainBody.put("chaincodeVer", "v0");
            BlockChainResponseDto dto = blockChainService.getPropertyDetails(BlockChainBody);
            LOGGER.info(dto.getResult());
            LOGGER.info(dto.getResult().getPayload());
            //ObjectMapper objMapper = new ObjectMapper();
            // BlockChainPayload[] payload = objMapper.readValue(dto.getResult().getPayload(), BlockChainPayload[].class);
            // LOGGER.info(payload[0].getValue());
            // LOGGER.info(payload[0].getValue().getName());
            String x = dto.getResult().getPayload().toString().substring((dto.getResult().getPayload().toString().length()-248), dto.getResult().getPayload().toString().length());
            String[] y = x.split(":");
            String[] z;
            ArrayList<String> value = new ArrayList<String>();

            for (int i = 0; i < y.length; i++) {
                z = y[i].split(",");
                for (int m = 0; m < z.length; m++) {
                    value.add(z[m]);
                }
            }
            LOGGER.info(value);

            OwnerDTO tr = new OwnerDTO();

            tr.setOwnerName(value.get(11).replaceAll("}", " "));
            tr.setOwnerContactNo("9967805156");
           tr.setOwnerAddress("304 ABM House Linking Road");
            tr.setOwnerPANNo("ADUPT441N");
            this.getModel().getBlockChainDTO().setPropNo("Flat4");

            this.getModel().getBlockChainDTO().getOwnerDetails().add(tr);

            // this.getModel().setBlockChainDTO(dto);
            /*
             * ProvisionalAssesmentMstDto dto = blockChainService.getOwnerDetails(propNo, null,
             * UserSession.getCurrent().getOrganisation().getOrgid()); this.getModel().setProvisionalAssesmentMstDto(dto);
             */
        } catch (Exception e) {
        	throw new FrameworkException("Error occured", e);

        }

        return new ModelAndView("BlockChainSearch", MainetConstants.FORM_NAME, this.getModel());
    }
    
    
    @RequestMapping(params = "blockChainSearchPrint", method = { RequestMethod.POST })
    public ModelAndView blockChainsearchPrint(HttpServletRequest request) {
       

        return new ModelAndView("BlockChainSearchPrint", MainetConstants.FORM_NAME, this.getModel());
    }
    
    @RequestMapping(params = "blockChainPrint", method = { RequestMethod.POST })
    public ModelAndView blockChainPrint(@RequestParam(required = false) String propNo,HttpServletRequest request) {
        String form = "BlockChainSearchPrint";
        try {
            Map<String, Object> BlockChainBody = new HashMap<>();
            ArrayList<String> args = new ArrayList<String>();
            args.add(propNo);
            // this.getModel().getBlockChainDTO().getArgs().add(propNo);
            BlockChainBody.put("channel", "testchannel");
            BlockChainBody.put("chaincode", "testChaincode");
            BlockChainBody.put("method", "getHistoryForMarble");
            BlockChainBody.put("args", args);
            BlockChainBody.put("chaincodeVer", "v0");
            BlockChainResponseDto dto = blockChainService.getPropertyDetails(BlockChainBody);
            if(dto.getReturnCode().equalsIgnoreCase("Success")) {
                form = "BlockChainTransferPrint";
            }
            LOGGER.info(dto.getResult());
            LOGGER.info(dto.getResult().getPayload());
            //ObjectMapper objMapper = new ObjectMapper();
            // BlockChainPayload[] payload = objMapper.readValue(dto.getResult().getPayload(), BlockChainPayload[].class);
            // LOGGER.info(payload[0].getValue());
            // LOGGER.info(payload[0].getValue().getName());
            String x = dto.getResult().getPayload().toString().substring((dto.getResult().getPayload().toString().length()-248), dto.getResult().getPayload().toString().length());
            String[] y = x.split(":");
            String[] z;
            ArrayList<String> value = new ArrayList<String>();

            for (int i = 0; i < y.length; i++) {
                z = y[i].split(",");
                for (int m = 0; m < z.length; m++) {
                    value.add(z[m]);
                }
            }
            LOGGER.info(value);

            OwnerDTO tr = new OwnerDTO();

            tr.setOwnerName(value.get(11).replaceAll("}", " "));
            tr.setOwnerContactNo("9967805156");
           tr.setOwnerAddress("304 ABM House Linking Road");
            tr.setOwnerPANNo("ADUPT441N");
            this.getModel().getBlockChainDTO().setPropNo("Flat4");

            this.getModel().getBlockChainDTO().getOwnerDetails().add(tr);

            // this.getModel().setBlockChainDTO(dto);
            /*
             * ProvisionalAssesmentMstDto dto = blockChainService.getOwnerDetails(propNo, null,
             * UserSession.getCurrent().getOrganisation().getOrgid()); this.getModel().setProvisionalAssesmentMstDto(dto);
             */
        } catch (Exception e) {
        	throw new FrameworkException("Error occured", e);

        }

        return new ModelAndView(form, MainetConstants.FORM_NAME, this.getModel());
    }

}

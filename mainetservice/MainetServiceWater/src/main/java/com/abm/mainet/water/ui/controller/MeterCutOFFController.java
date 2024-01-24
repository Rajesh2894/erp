package com.abm.mainet.water.ui.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbMeterMasEntity;
import com.abm.mainet.water.domain.TbWaterCutRestoration;
import com.abm.mainet.water.dto.MeterCutOffRestorationDTO;
import com.abm.mainet.water.dto.MeterReadingDTO;
import com.abm.mainet.water.service.MeterCutOffRestorationService;
import com.abm.mainet.water.service.TbMrdataService;
import com.abm.mainet.water.service.WaterCommonService;
import com.abm.mainet.water.ui.model.MeterCutOFFModel;

/**
 * @author Arun.Chavda
 *
 */

@Controller
@RequestMapping("/MeterCutOFF.html")
public class MeterCutOFFController extends AbstractFormController<MeterCutOFFModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeterCutOFFController.class);

    @Autowired
    private MeterCutOffRestorationService meterCutOffRestorationService;

    @Autowired
    private WaterCommonService waterCommonService;
    
    @Autowired
    private TbMrdataService tbMrdataService;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        getModel().bind(httpServletRequest);
        final MeterCutOFFModel model = getModel();
        model.setCommonHelpDocs("MeterCutOFF.html");
        ModelAndView mv = null;
        mv = new ModelAndView("MeterCutOFF", MainetConstants.FORM_NAME, model);
        mv.addObject(MainetConstants.CommonConstants.COMMAND, model);
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, params = "getMeterDetails")
    public ModelAndView getMeterDetails(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        ModelAndView mv = null;
        mv = new ModelAndView("MeterDetails", MainetConstants.FORM_NAME, getModel());
        mv.addObject(MainetConstants.CommonConstants.COMMAND, getModel());
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, params = "getPreviousMeterCutOff")
    public ModelAndView getPreviousMeterDetails(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        ModelAndView mv = null;
        final Long meterId = getModel().getMeterMasPreviousDetailDTO().getMmMtnid();
        final Long consumerId = getModel().getMeterCutOffResDTO().getCsIdn();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final String cutOffResFlag = MainetConstants.RnLCommon.Flag_C;
        List<MeterCutOffRestorationDTO> meterCutResHitsDTO = null;
        if (MainetConstants.RnLCommon.Y_FLAG.equals(getModel().getMeteredFlag())) {
        	if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL) ) {
        		meterCutResHitsDTO = meterCutOffRestorationService.getPreviousMeterCutOffDetailsOnCsIdn(consumerId, orgId,
                        cutOffResFlag);
        	}
        	else {
        		meterCutResHitsDTO = meterCutOffRestorationService.getPreviousMeterCutOffDetails(meterId, consumerId, orgId,
                        cutOffResFlag);
        	}
        } else if (MainetConstants.RnLCommon.N_FLAG.equals(getModel().getMeteredFlag())) {
            meterCutResHitsDTO = meterCutOffRestorationService.getNonMeterPreviousDetails(consumerId, orgId, cutOffResFlag);
        }

        getModel().setCutOffRestorationDTO(meterCutResHitsDTO);
        mv = new ModelAndView("PreviousMeterCutOff", MainetConstants.FORM_NAME, getModel());
        mv.addObject(MainetConstants.CommonConstants.COMMAND, getModel());
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, params = "getConnectionDetails")
    public @ResponseBody String getConnectionDetails(final HttpServletRequest httpServletRequest,
            @RequestParam("connectionNo") final String connectionNo) {
        bindModel(httpServletRequest);

        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final TbKCsmrInfoMH consumerInfo = waterCommonService.getConnectionDetail(organisation.getOrgid(), connectionNo);
        final StringBuilder builder = new StringBuilder();
        final MeterCutOFFModel meterCutOFFModel = getModel();
        TbWaterCutRestoration meterCutRestoreObj = null;
        if (Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SKDCL) && consumerInfo.getCsMeteredccn() != null) {
    		if(!MainetConstants.NewWaterServiceConstants.METER.equals(CommonMasterUtility.getNonHierarchicalLookUpObject(consumerInfo.getCsMeteredccn(), organisation).getLookUpCode()) ){
    			builder.append("NM");
    			return builder.toString();
    		}
    		if (null != consumerInfo) {
    			meterCutRestoreObj = meterCutOffRestorationService.getCutOffRestore(consumerInfo.getCsIdn(), organisation.getOrgid());
    			if(meterCutRestoreObj != null && MainetConstants.MeterCutOffRestoration.METER_CUTOFF.equals(meterCutRestoreObj.getMmCutResFlag()) ){
    				builder.append("C");
        			return builder.toString();
    			}
    		}
        }
        
        if (null != consumerInfo) {
            getModel().getMeterCutOffResDTO().setCsIdn(consumerInfo.getCsIdn());
            String premiseType = MainetConstants.BLANK;
            String tarrifCategory = MainetConstants.BLANK;
            String connectionSize = MainetConstants.BLANK;
            String status = MainetConstants.BLANK;
            String cutOffResRead = MainetConstants.BLANK;
            String meterStatus = MainetConstants.BLANK;
            meterCutOFFModel.setConsumerName(consumerInfo.getCsName());
            final Long consumerInfoId = meterCutOFFModel.getMeterCutOffResDTO().getCsIdn();
            final TbMeterMasEntity tbMeterMasEntity = meterCutOffRestorationService.getMeterDetails(consumerInfoId,
                    organisation.getOrgid());
            if (null != tbMeterMasEntity) {
                meterCutOFFModel.getMeterMasPreviousDetailDTO().setMmMtnid(tbMeterMasEntity.getMmMtnid());
                meterCutOFFModel.getMeterMasPreviousDetailDTO().setMmMtrno(tbMeterMasEntity.getMmMtrno());
                // LookUp lookUp
                //meterCutOFFModel.getMeterMasPreviousDetailDTO().setMmOwnership(tbMeterMasEntity.getMmOwnership());setMeterOwnerShip
                if (tbMeterMasEntity.getMmOwnership() != null) {
                    final LookUp meterOwnerShip = CommonMasterUtility.getNonHierarchicalLookUpObject(tbMeterMasEntity.getMmOwnership());
                    meterCutOFFModel.getMeterMasPreviousDetailDTO().setMeterOwnerShip(meterOwnerShip.getLookUpDesc());
                }
                meterCutOFFModel.getMeterMasPreviousDetailDTO().setMmMtrcost(tbMeterMasEntity.getMmMtrcost());
                final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
                meterCutOFFModel.getMeterMasPreviousDetailDTO()
                        .setMeterInstallDate(simpleDateFormat.format(tbMeterMasEntity.getMmInstallDate()));
                meterCutOFFModel.getMeterMasPreviousDetailDTO().setMmMtrmake(tbMeterMasEntity.getMmMtrmake());
                meterCutOFFModel.getMeterMasPreviousDetailDTO().setMaxMeterRead(tbMeterMasEntity.getMaxMeterRead());
                meterCutOFFModel.getMeterMasPreviousDetailDTO().setMmInitialReading(tbMeterMasEntity.getMmInitialReading());
            }

            if (consumerInfo.getCsCcnsize() != null) {
                final LookUp connSize = CommonMasterUtility.getNonHierarchicalLookUpObject(consumerInfo.getCsCcnsize(),
                        organisation);
                connectionSize = connSize.getLookUpDesc();
                meterCutOFFModel.setConnectionSize(connectionSize);
            }
            if (consumerInfo.getTrdPremise() != null) {
                final LookUp preType = CommonMasterUtility.getHierarchicalLookUp(consumerInfo.getTrdPremise(), organisation);
                premiseType = preType.getLookUpDesc();
                meterCutOFFModel.setPremiseType(premiseType);
            }

            if (consumerInfo.getTrmGroup1() != null) {
                final LookUp tarrifCate = CommonMasterUtility.getHierarchicalLookUp(consumerInfo.getTrmGroup1(), organisation);
                tarrifCategory = tarrifCate.getLookUpDesc();
                meterCutOFFModel.setTarrifCategory(tarrifCategory);
            }

            if (consumerInfo.getCsMeteredccn() != null) {
                final LookUp connectionType = CommonMasterUtility.getNonHierarchicalLookUpObject(consumerInfo.getCsMeteredccn(),
                        organisation);
                status = connectionType.getLookUpCode();
                if(Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SKDCL) ) {
                	if(MainetConstants.NewWaterServiceConstants.METER.equals(status)) {
                		status = MainetConstants.RnLCommon.Y_FLAG;
                	}
                	else {
                		status = MainetConstants.RnLCommon.N_FLAG;
                	}
                }
                meterCutOFFModel.setMeteredFlag(status);
            }
            
            List tbMrDataList = tbMrdataService.getMeterStatusAndReadByCsidn(consumerInfo.getCsIdn(), organisation.getOrgid());
            if(tbMrDataList != null && tbMrDataList.size() > 1) {
            	if(tbMrDataList.get(0) != null) {
            		meterStatus = tbMrDataList.get(0).toString();
            		meterCutOFFModel.getMeterCutOffResDTO().setMeterStatus(Long.valueOf(meterStatus));
            	}
            	
            	if(tbMrDataList.get(1) != null) {
            		cutOffResRead = tbMrDataList.get(1).toString();
            		meterCutOFFModel.getMeterCutOffResDTO().setCutResRead(Long.valueOf(cutOffResRead));
            	}
            }
            
            if(Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SKDCL) ) {
            	builder.append(consumerInfo.getCsName());
                builder.append(MainetConstants.operator.TILDE);
                builder.append(tarrifCategory);
                builder.append(MainetConstants.operator.TILDE);
                builder.append(premiseType);
                builder.append(MainetConstants.operator.TILDE);
                builder.append(connectionSize);
                builder.append(MainetConstants.operator.TILDE);
                builder.append(status);
                builder.append(MainetConstants.operator.TILDE);
                builder.append(cutOffResRead);
                builder.append(MainetConstants.operator.TILDE);
                builder.append(meterStatus);
            }
            else  {
            	builder.append(consumerInfo.getCsName());
                builder.append(MainetConstants.operator.COMMA);
                builder.append(tarrifCategory);
                builder.append(MainetConstants.operator.COMMA);
                builder.append(premiseType);
                builder.append(MainetConstants.operator.COMMA);
                builder.append(connectionSize);
                builder.append(MainetConstants.operator.COMMA);
                builder.append(status);
                builder.append(MainetConstants.operator.COMMA);
                builder.append(cutOffResRead);
                builder.append(MainetConstants.operator.COMMA);
                builder.append(meterStatus);
            }
        } else {
            builder.append("N");
        }
        return builder.toString();
    }

    @Override
    @RequestMapping(params = "saveform", method = RequestMethod.POST)
    public ModelAndView saveform(final HttpServletRequest httpServletRequest) {

        bindModel(httpServletRequest);
        final MeterCutOFFModel model = getModel();
        try {
            if (model.saveForm()) {
                return jsonResult(JsonViewObject
                        .successResult(getApplicationSession().getMessage("water.meterCutOff.MSG.meterDetailsSaved")));
            }
        } catch (final Exception ex) {
            LOGGER.error("Error Occured during Saved Meter Cut-Off data", ex);
            return defaultExceptionFormView();
        }
        return defaultMyResult();
    }
}

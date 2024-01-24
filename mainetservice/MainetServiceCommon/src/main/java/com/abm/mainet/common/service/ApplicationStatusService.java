package com.abm.mainet.common.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.IApplicationStatusRepository;
import com.abm.mainet.common.domain.ApplicationStatusEntity;
import com.abm.mainet.common.dto.ApplicationDetail;
import com.abm.mainet.common.dto.ApplicationStatusRequestVO;
import com.abm.mainet.common.integration.dto.WebServiceResponseDTO;

/**
 * @author vishnu.jagdale
 *
 */
@Service
public class ApplicationStatusService implements IApplicationStatusService {

    @Resource
    IApplicationStatusRepository ApplicationStatusRepository;

    /*
     * (non-Javadoc)
     * @see com.abm.mainetsource.applicaitonstatus.service.IApplicationStatusService#getApplicationStatusList()
     */
    @Override
    public List<ApplicationStatusEntity> getApplicationStatusList() {
        return ApplicationStatusRepository.getApplicationStatusList();
    }

    @Override
    public List<WebServiceResponseDTO> validateInput(final ApplicationStatusRequestVO requestDTO, final String flag)
            throws RuntimeException {

        WebServiceResponseDTO wsResponseDTO = null;
        final List<WebServiceResponseDTO> wsResponseList = new ArrayList<>();

        for (int i = 0; i < requestDTO.getAppDetailList().size(); i++) {
            final ApplicationDetail appDetail = requestDTO.getAppDetailList().get(i);

            if ((appDetail.getApmApplicationId() == null)
                    || (appDetail.getApmApplicationId() == MainetConstants.CommonConstant.ZERO_LONG)) {
                wsResponseDTO = new WebServiceResponseDTO();
                wsResponseDTO.setErrorCode(MainetConstants.InputError.ERROR_CODE);
                wsResponseDTO.setErrorMsg(MainetConstants.InputError.APPLICATION_NO_NOT_FOUND);
                wsResponseList.add(wsResponseDTO);
            }
            if ((appDetail.getOrgId() == null)
                    || (appDetail.getOrgId() == MainetConstants.CommonConstant.ZERO_LONG)) {
                wsResponseDTO = new WebServiceResponseDTO();
                wsResponseDTO.setErrorCode(MainetConstants.InputError.ERROR_CODE);
                wsResponseDTO.setErrorMsg(MainetConstants.InputError.ORGID_NOT_FOUND);
                wsResponseList.add(wsResponseDTO);
            }
            if ((appDetail.getServiceId() == null)
                    || (appDetail.getServiceId() == MainetConstants.CommonConstant.ZERO_LONG)) {
                wsResponseDTO = new WebServiceResponseDTO();
                wsResponseDTO.setErrorCode(MainetConstants.InputError.ERROR_CODE);
                wsResponseDTO.setErrorMsg(MainetConstants.InputError.SERVICE_ID_NOT_FOUND);
                wsResponseList.add(wsResponseDTO);
            }
            if ((appDetail.getApmStatus() == null) ||
                    MainetConstants.CommonConstant.BLANK.equals(appDetail.getApmStatus().trim())) {
                wsResponseDTO = new WebServiceResponseDTO();
                wsResponseDTO.setErrorCode(MainetConstants.InputError.ERROR_CODE);
                wsResponseDTO.setErrorMsg(MainetConstants.InputError.Application_Status_Not_Found);
                wsResponseList.add(wsResponseDTO);
            }
        }

        return wsResponseList;

    }

    @Override
    public List<WebServiceResponseDTO> validateInputWithoutOrganisation(final ApplicationStatusRequestVO requestDTO,
            final String flag)
            throws RuntimeException {

        WebServiceResponseDTO wsResponseDTO = null;
        final List<WebServiceResponseDTO> wsResponseList = new ArrayList<>();

        for (int i = 0; i < requestDTO.getAppDetailList().size(); i++) {
            int j = 0;
            final ApplicationDetail appDetail = requestDTO.getAppDetailList().get(i);

            if ((appDetail.getApmApplicationId() == null)
                    || (appDetail.getApmApplicationId() == MainetConstants.CommonConstant.ZERO_LONG)) {
                wsResponseDTO = new WebServiceResponseDTO();
                wsResponseDTO.setErrorCode(MainetConstants.InputError.ERROR_CODE);
                wsResponseDTO.setErrorMsg(MainetConstants.InputError.APPLICATION_NO_NOT_FOUND);
                wsResponseList.add(wsResponseDTO);
                j++;
                System.out.println(
                        "----appDetail.getApmApplicationId()------" + appDetail.getApmApplicationId() + "gggg" + j + "888" + i);
            }

            if ((appDetail.getServiceId() == null)
                    || (appDetail.getServiceId() == MainetConstants.CommonConstant.ZERO_LONG)) {
                wsResponseDTO = new WebServiceResponseDTO();
                wsResponseDTO.setErrorCode(MainetConstants.InputError.ERROR_CODE);
                wsResponseDTO.setErrorMsg(MainetConstants.InputError.SERVICE_ID_NOT_FOUND);
                wsResponseList.add(wsResponseDTO);
                System.out.println("----appDetail.SERVICE_ID_NOT_FOUND()------" + appDetail.getApmApplicationId());

            }
        }

        return wsResponseList;

    }

    @Override
    @Transactional
    public List<ApplicationDetail> getApplicationStatusListOpenForUser(final ApplicationStatusRequestVO requestDTO)
            throws RuntimeException {

        final List<ApplicationStatusEntity> appStsEntityList = ApplicationStatusRepository
                .getApplicationStatusListOpenForUser(requestDTO);

        requestDTO.getAppDetailList();

        final List<ApplicationDetail> appListDetailResponse = new ArrayList<>();

        ApplicationDetail appDetailResponse = null;

        if (appStsEntityList != null) {
            for (int i = 0; i < appStsEntityList.size(); i++) {

                ApplicationStatusEntity appStsEntity = null;
                appDetailResponse = new ApplicationDetail();

                appStsEntity = appStsEntityList.get(i);

                appDetailResponse.setApmApplicationId(appStsEntity.getApmApplicationId());
                appDetailResponse.setOrgId(appStsEntity.getOrgId());
                appDetailResponse.setApmStatus(appStsEntity.getApmStatus());
                appDetailResponse.setServiceId(appStsEntity.getServiceId());

                appListDetailResponse.add(appDetailResponse);

            }
        }
        return appListDetailResponse;
    }

    @Override
    @Transactional
    public List<ApplicationDetail> getApplicationStatusDetail(final ApplicationStatusRequestVO requestDTO)
            throws RuntimeException {

        final List<ApplicationStatusEntity> appStsEntityList = ApplicationStatusRepository.getApplicationStatusDetail(requestDTO);

        requestDTO.getAppDetailList();

        final List<ApplicationDetail> appListDetailResponse = new ArrayList<>();

        ApplicationDetail appDetailResponse = null;

        if (appStsEntityList != null) {
            for (int i = 0; i < appStsEntityList.size(); i++) {

                ApplicationStatusEntity appStsEntity = null;
                appDetailResponse = new ApplicationDetail();

                appStsEntity = appStsEntityList.get(i);

                appDetailResponse.setApmApplicationId(appStsEntity.getApmApplicationId());
                appDetailResponse.setOrgId(appStsEntity.getOrgId());
                appDetailResponse.setApmStatus(appStsEntity.getApmStatus());
                appDetailResponse.setServiceId(appStsEntity.getServiceId());
                appDetailResponse.setApmServiceName(appStsEntity.getApmServiceName());
                appDetailResponse.setApmApplicationDate(appStsEntity.getApmApplicationDate());
                appDetailResponse.setApmApplicationNameEng(appStsEntity.getApmApplicationNameEng());

                appListDetailResponse.add(appDetailResponse);

            }
        }
        return appListDetailResponse;
    }

}

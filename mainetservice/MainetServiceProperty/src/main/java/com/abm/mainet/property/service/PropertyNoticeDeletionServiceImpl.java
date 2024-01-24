package com.abm.mainet.property.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.repository.TbFinancialyearJpaRepository;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.property.domain.AssesmentMastEntity;
import com.abm.mainet.property.domain.AssesmentOwnerDtlEntity;
import com.abm.mainet.property.domain.AssessNoticeMasterEntity;
import com.abm.mainet.property.dto.NoticeGenSearchDto;
import com.abm.mainet.property.repository.AssesmentMstRepository;
import com.abm.mainet.property.repository.AssessNoticeMasterRepository;
import com.abm.mainet.property.repository.PropertyNoticeDeletionRepository;
import com.abm.mainet.property.ui.model.PropertyDemandNoticeGenerationModel;

@Service
public class PropertyNoticeDeletionServiceImpl implements PropertyNoticeDeletionService {

    @Autowired
    private PropertyNoticeDeletionRepository propertyNoticeDeletionRepository;

    @Autowired
    ILocationMasService iLocationMasService;

    @Autowired
    AssessNoticeMasterRepository assessNoticeMasterRepository;

    @Autowired
    AssesmentMstRepository assesmentMstRepository;

    @Autowired
    TbFinancialyearJpaRepository tbFinancialyearJpaRepository;

    @Autowired
    private IFinancialYearService iFinancialYearService;

    @Override
    public boolean validateNoticeDeletion(long noticeNo, LookUp typelookUp, long noticeType, long orgId) {

        if (typelookUp.getLookUpCode().equals("DN")) {

            Organisation organisation = UserSession.getCurrent().getOrganisation();

            LookUp demandlookUp = CommonMasterUtility.getValueFromPrefixLookUp("WN", "NTY",
                    organisation);

            long lookUpIdForWN = demandlookUp.getLookUpId();

            int countPro = propertyNoticeDeletionRepository.propertyNoticeDeletion(noticeType, lookUpIdForWN,
                    noticeNo, orgId);

            if (countPro <= 0) {
                return true;
            }

        }
        if (typelookUp.getLookUpCode().equals("SP")) {

            int countForObjection = propertyNoticeDeletionRepository.specialNoticeValidation(String.valueOf(noticeNo), orgId);

            if (countForObjection <= 0) {
                return true;
            }

        }

        return false;
    }

    @Override
    @Transactional
    public void deleteNoticByNoticeNO(long noticeId, Long orgId)

    {

        propertyNoticeDeletionRepository.deleteByMnNotidAndOrgid(noticeId, orgId);

        // propertyNoticeDeletionRepository.delete(noticeId);

    }

    @Override
    @Transactional
    public List<NoticeGenSearchDto> fetchPropertyForNoticeDeletion(PropertyDemandNoticeGenerationModel model, long orgId,
            long noticeType) {

        Long noticeNo = model.getSpecialNotGenSearchDto().getNoticeNo();

        List<NoticeGenSearchDto> notGenShowList = new ArrayList<>();

        AssessNoticeMasterEntity assessNoticeMasterEntity = assessNoticeMasterRepository.getNoticesDetailsByNoticeNo(orgId,
                noticeNo, noticeType);

        if (assessNoticeMasterEntity == null) {
            return null;

        } else {
            String mnAssNo = assessNoticeMasterEntity.getMnAssNo();

            AssesmentMastEntity assMas = assesmentMstRepository.fetchPropertyByPropNo(mnAssNo);

            NoticeGenSearchDto notGen = new NoticeGenSearchDto();
            notGen.setNoticeId(assessNoticeMasterEntity.getMnNotid());
            notGen.setPropertyNo(assMas.getAssNo());
            notGen.setApplicationId(assMas.getApmApplicationId());
            notGen.setAssId(assMas.getProAssId());
            notGen.setLocationName(iLocationMasService.getLocationNameById(assMas.getLocId(), orgId));

            List<AssesmentOwnerDtlEntity> ownerList = assMas.getAssesmentOwnerDetailEntityList();

            if (ownerList != null && !ownerList.isEmpty())

                for (AssesmentOwnerDtlEntity owner : ownerList) {
                    // if (owner.getAssoOType() != null && owner.getAssoOType().equals(MainetConstants.Property.PRIMARY_OWN))
                    {
                        notGen.setOwnerName(owner.getAssoOwnerName());
                        notGen.setMobileNo(owner.getAssoMobileno());
                    }
                }

            notGenShowList.add(notGen);

            return notGenShowList;
        }
    }

    @Override
    @Transactional
    public boolean validateNoticeForExistOrNot(long noticeNo, LookUp typelookUp, long noticeType, long orgId) {

        String lookUpCode = typelookUp.getLookUpCode();

        Long finYearId = iFinancialYearService.getFinanceYearId(new Date());
        int count = propertyNoticeDeletionRepository.noticeValidationForCurrentYear(noticeNo, noticeType,
                finYearId, lookUpCode,
                orgId);

        if (count > 0) {

            return true;

        }

        return false;
    }

}

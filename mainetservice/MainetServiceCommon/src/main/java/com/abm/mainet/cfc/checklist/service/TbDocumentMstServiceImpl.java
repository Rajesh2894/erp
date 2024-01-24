package com.abm.mainet.cfc.checklist.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.abm.mainet.cfc.checklist.domain.TbDocumentGroupEntity;
import com.abm.mainet.cfc.checklist.dto.TbCfcChecklistMstDto;
import com.abm.mainet.cfc.checklist.dto.TbDocumentGroup;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.ServiceChecklistDTO;
import com.abm.mainet.common.master.mapper.DocumentGroupMstServiceMapper;
import com.abm.mainet.common.master.repository.TbDocumentGroupRepository;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author hiren.poriya
 *
 */
@Service
public class TbDocumentMstServiceImpl implements TbDocumentMstService {

    @Resource
    private TbDocumentGroupRepository tbDocumentGroupRepository;
    @Resource
    private DocumentGroupMstServiceMapper documentGroupMstServiceMapper;

    @Override
    public void saveDocumentlist(final TbCfcChecklistMstDto cfcChecklistMstDto, final List<TbDocumentGroup> tbDocGrouptList) {

        TbDocumentGroupEntity tbDocGroupEntity = null;
        final Date curDate = new Date();
        for (final TbDocumentGroup docGrouptMst : tbDocGrouptList) {

            tbDocGroupEntity = new TbDocumentGroupEntity();
            tbDocGroupEntity.setGroupCpdId(cfcChecklistMstDto.getDocGroupMst().getHiddenGroupCpdId());
            tbDocGroupEntity.setDocName(docGrouptMst.getDocName());
            tbDocGroupEntity.setDocNameReg(docGrouptMst.getDocNameReg());
            tbDocGroupEntity.setDocType(docGrouptMst.getDocType());
            tbDocGroupEntity.setDocTypeReg(docGrouptMst.getDocTypeReg());
            tbDocGroupEntity.setDocSize(docGrouptMst.getDocSize());
            tbDocGroupEntity.setCcmValueset(docGrouptMst.getCcmValueset());
            tbDocGroupEntity.setDocStatus(MainetConstants.STATUS.ACTIVE);
            tbDocGroupEntity.setDocSrNo(docGrouptMst.getDocSrNo());
            tbDocGroupEntity.setLgIpMac(Utility.getMacAddress());
            tbDocGroupEntity.setCreatedDate(curDate);
            tbDocGroupEntity.setTbOrganisation(UserSession.getCurrent().getOrganisation());
            tbDocGroupEntity.setCreatedBy(Long.valueOf((UserSession.getCurrent().getEmployee().getUserId())));
            tbDocGroupEntity.setDocPrefixRequired(docGrouptMst.getDocPrefixRequired());
            tbDocGroupEntity.setPrefixName(docGrouptMst.getPrefixName());
            tbDocumentGroupRepository.save(tbDocGroupEntity);
        }
    }

    @Override
    public void updateDocumentList(final TbCfcChecklistMstDto cfcChecklistMstDto, final List<TbDocumentGroup> tbDocGrouptList,
            final List<TbDocumentGroup> DocumentTemp) {

        TbDocumentGroupEntity tbDocumentGroupEntity = null;
        final Date curDate = new Date();
        final List<TbDocumentGroup> finalDocumentList = new ArrayList<>();
        for (final TbDocumentGroup newListElement : cfcChecklistMstDto.getDocGroupList()) {
            int dbDataFound = 0;
            for (TbDocumentGroup dbListElement : tbDocGrouptList) {
                if (newListElement.getDgId() != null) {
                    if (dbListElement.getDgId().equals(newListElement.getDgId())) {
                        setDocumentMaster(dbListElement, newListElement);
                        dbListElement.setUpdatedDate(curDate);
                        dbListElement.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                        finalDocumentList.add(dbListElement);
                        dbDataFound++;
                        break;
                    }
                } else {
                    dbListElement = new TbDocumentGroup();
                    setDocumentMaster(dbListElement, newListElement);
                    dbListElement.setCreatedDate(curDate);
                    dbListElement.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                    dbListElement.setDocStatus(MainetConstants.STATUS.ACTIVE);
                    finalDocumentList.add(dbListElement);

                    dbDataFound++;
                    break;
                }
            }
            if (dbDataFound == 0) {
                finalDocumentList.add(newListElement);
            }
        }
        int count = 0;
        for (final TbDocumentGroup dbListElement : tbDocGrouptList) {
            for (final TbDocumentGroup fianlListElement : finalDocumentList) {

                if ((fianlListElement.getDgId() != null) && fianlListElement.getDgId().equals(dbListElement.getDgId())) {
                    count++;
                }
            }
            if (count == 0) {
                dbListElement.setDocStatus(MainetConstants.STATUS.INACTIVE);
                finalDocumentList.add(dbListElement);
            }
        }

        for (final TbDocumentGroup documentGrpMst : finalDocumentList) {
            tbDocumentGroupEntity = new TbDocumentGroupEntity();

            documentGrpMst.setGroupCpdId(cfcChecklistMstDto.getDocGroupMst().getGroupCpdId());

            if (documentGrpMst.getGroupCpdId() != null) {       	
                documentGrpMst.setOrgid(DocumentTemp.get(0).getOrgid());
                documentGroupMstServiceMapper.mapTbDocumentGroupMstToTbDocumentGroupEntity(documentGrpMst,
                        tbDocumentGroupEntity);

                tbDocumentGroupEntity.setTbOrganisation(UserSession.getCurrent().getOrganisation());

                tbDocumentGroupRepository.save(tbDocumentGroupEntity);
            }
        }
    }

    /**
     * @param dbListElement
     * @param newListElement
     */
    private void setDocumentMaster(final TbDocumentGroup dbListElement, final TbDocumentGroup newListElement) {
        dbListElement.setDocType(newListElement.getDocType());
        dbListElement.setDocName(newListElement.getDocName());
        dbListElement.setDocSize(newListElement.getDocSize());
        dbListElement.setCcmValueset(newListElement.getCcmValueset());
        dbListElement.setDocSrNo(newListElement.getDocSrNo());
        dbListElement.setLgIpMacUpd(Utility.getMacAddress());
        dbListElement.setDocNameReg(newListElement.getDocNameReg());
        dbListElement.setDocTypeReg(newListElement.getDocTypeReg());
        dbListElement.setDocPrefixRequired(newListElement.getDocPrefixRequired());
        dbListElement.setPrefixName(newListElement.getPrefixName());
    }

    @Override
    public List<TbDocumentGroup> findAllByGroupIdIdOrgId(final Long orgId, final Long groupId) {

        final List<TbDocumentGroupEntity> entities = tbDocumentGroupRepository.findAllByGroupIdIdOrgId(orgId.longValue(),
                groupId);
        final List<TbDocumentGroup> beans = new ArrayList<>();
        TbDocumentGroup tbDocumetnGroup = null;
        final Date curDate = new Date();
        for (final TbDocumentGroupEntity tbDocumentGroupEntity : entities) {
            tbDocumetnGroup = documentGroupMstServiceMapper
                    .mapTbDocumentGroupEntityToTbDocumentGroupMst(tbDocumentGroupEntity);
            tbDocumetnGroup.setUpdatedDate(curDate);
            tbDocumetnGroup.setUpdatedBy(Long.valueOf((UserSession.getCurrent().getEmployee().getUserId())));
            beans.add(tbDocumetnGroup);
        }
        return beans;
    }

    @Override
    public List<ServiceChecklistDTO> getSearchDocumentData(final Long orgId, final Long groupId) {
        final List<Object> docList = tbDocumentGroupRepository.getDocumentDetails(orgId, groupId);
        final List<ServiceChecklistDTO> beans = new ArrayList<>();
        ServiceChecklistDTO serviceDoclistDTO = null;

        final int dataSize = docList.size();
        for (int counter = 0; counter < dataSize; counter++) {
            serviceDoclistDTO = new ServiceChecklistDTO();
            final Object[] obj = (Object[]) docList.get(counter);

            if (obj != null) {
                if (obj[0] != null) {
                    serviceDoclistDTO.setDocName(obj[0].toString());
                }
                if (obj[1] != null) {
                    serviceDoclistDTO.setDocNameReg(obj[1].toString());
                }
                if (obj[2] != null) {
                    serviceDoclistDTO.setDocType(obj[2].toString());
                }
                if (obj[3] != null) {
                    serviceDoclistDTO.setDocTypeReg(obj[3].toString());
                }
                if (obj[4] != null) {
                    serviceDoclistDTO.setDocSize((Long) obj[4]);
                }

                if (obj[5] != null) {
                    serviceDoclistDTO.setDocMandatory(setDocumentDescription((Long) obj[5], orgId));
                }

                if (obj[6] != null) {
                    serviceDoclistDTO.setDocSrNo((Long) obj[6]);
                }

                if (obj[7] != null) {
                    serviceDoclistDTO.setDgId((Long) obj[7]);
                }
                
                if (obj[8] != null) {
                    serviceDoclistDTO.setDocPrefixRequired(obj[8].toString());
                }
                
                if (obj[9] != null) {
                    serviceDoclistDTO.setPrefixName(obj[9].toString());
                }

                serviceDoclistDTO.setDocGroup(obj.toString());
            }
            beans.add(serviceDoclistDTO);
        }
        return beans;
    }

    private String setDocumentDescription(final Long mandatoryId, Long orgId) {

        return CommonMasterUtility.findLookUpDesc("SET", orgId,
                mandatoryId);
    }
}

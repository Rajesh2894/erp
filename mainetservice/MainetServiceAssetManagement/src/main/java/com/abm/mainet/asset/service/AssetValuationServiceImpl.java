/**
 * 
 */
package com.abm.mainet.asset.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.domain.AssetValuationDetails;
import com.abm.mainet.asset.mapper.EvaluationMapper;
import com.abm.mainet.asset.repository.AssetValuationRepository;
import com.abm.mainet.asset.ui.dto.AssetValuationDetailsDTO;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author sarojkumar.yadav
 *
 */
@Service
public class AssetValuationServiceImpl implements IAssetValuationService {

    @Autowired
    private AssetValuationRepository valuationRepo;

    /**
     * Used to add entry in valuation table
     * 
     * @param AssetValuationDetailsDTO
     * @return primary key of valuation table
     */
    @Override
    @Transactional
    public Long addEntry(AssetValuationDetailsDTO dto) {
        try {
            AssetValuationDetails entity = EvaluationMapper.mapToEntity(dto);
            entity = valuationRepo.save(entity);
            return entity.getValuationDetId();
        } catch (Exception exp) {
            throw new FrameworkException("Unable to save data in asset valution table ", exp);
        }
    }

    /**
     * Used to get list of DepreciationAssetDTO on the basis of asset id and orgId
     * 
     * @param AssetValuationDetailsDTO
     * @return list of DepreciationAssetDTO
     */
    @Override
    @Transactional(readOnly=true)
    public List<AssetValuationDetailsDTO> findAllByAssetId(final Long orgId, final Long assetId) {
        try {
            List<AssetValuationDetails> entityList = valuationRepo.findAllByAssetId(orgId, assetId);
            if (entityList != null && !entityList.isEmpty()) {
                List<AssetValuationDetailsDTO> dtoList = new ArrayList<>();
                for (AssetValuationDetails entity : entityList) {
                    final AssetValuationDetailsDTO depDTO = EvaluationMapper.mapToDTO(entity);
                    dtoList.add(depDTO);
                }
                return dtoList;
            }
            return null;
        } catch (Exception exp) {
            throw new FrameworkException("Unable to get data from asset valution table by asset Id", exp);
        }
    }

    /**
     * Used to get latest value of DepreciationAssetDTO on the basis of asset id and orgId
     * 
     * @param orgId
     * @param assetId
     * @return DepreciationAssetDTO
     */
    @Override
    @Transactional(readOnly=true)
    public AssetValuationDetailsDTO findLatestAssetId(final Long orgId, final Long assetId) {
        try {
            List<AssetValuationDetails> entityList = valuationRepo.findAllByAssetId(orgId, assetId);
            if (entityList != null && !entityList.isEmpty()) {
                final AssetValuationDetails entity = entityList.get(0);
                final AssetValuationDetailsDTO depDTO = EvaluationMapper.mapToDTO(entity);
                return depDTO;
            }
            return null;
        } catch (Exception exp) {
            throw new FrameworkException("Unable to get latest data from asset valution table by asset Id", exp);
        }
    }

    /**
     * find Asset Valuation Details details by Asset Id between two dates
     * 
     * @param orgId
     * @param assetId
     * @param startDate
     * @param endDate
     * @return list of AssetValuationDetails with All details records if found else return null.
     */
    @Override
    @Transactional(readOnly=true)
    public List<AssetValuationDetailsDTO> findAssetBetweenDates(final Long orgId, final Long assetId,
            final Date startDate, final Date endDate) {
        try {
            List<AssetValuationDetails> entityList = valuationRepo.findAssetBetweenDates(orgId, assetId, startDate,
                    endDate);
            if (entityList != null && !entityList.isEmpty()) {
                List<AssetValuationDetailsDTO> dtoList = new ArrayList<>();
                for (AssetValuationDetails entity : entityList) {
                    final AssetValuationDetailsDTO depDTO = EvaluationMapper.mapToDTO(entity);
                    dtoList.add(depDTO);
                }
                return dtoList;
            }
            return null;
        } catch (Exception exp) {
            throw new FrameworkException(
                    "Unable to get data from asset valution table by asset Id with start and end dates", exp);
        }
    }

    /**
     * find Asset Valuation Details details by Asset Id for a financial year with change type equals to "DPR"
     * 
     * @param orgId
     * @param assetId
     * @param bookFinYear
     * @param changeType
     * @return list of AssetValuationDetails with All details records if found else return null.
     */
    @Override
    @Transactional(readOnly=true)
    public List<AssetValuationDetailsDTO> findAssetInFinYear(final Long orgId, final Long assetId,
            final Long bookFinYear, final String changeType) {
        try {
            List<AssetValuationDetails> entityList = valuationRepo.findAssetInFinYear(orgId, assetId, bookFinYear,
                    changeType);
            if (entityList != null && !entityList.isEmpty()) {
                List<AssetValuationDetailsDTO> dtoList = new ArrayList<>();
                for (AssetValuationDetails entity : entityList) {
                    final AssetValuationDetailsDTO depDTO = EvaluationMapper.mapToDTO(entity);
                    dtoList.add(depDTO);
                }
                return dtoList;
            }
            return null;
        } catch (Exception exp) {
            throw new FrameworkException(
                    "Unable to get data from asset valution table by asset Id for a financial year with change type equals to DPR",
                    exp);
        }
    }

    /**
     * Used to get latest value of DepreciationAssetDTO on the basis of asset id and orgId for change type "DPR"
     * 
     * @param orgId
     * @param assetId
     * @param changeType
     * @return DepreciationAssetDTO
     */
    @Override
    @Transactional(readOnly=true)
    public AssetValuationDetailsDTO findLatestAssetIdWithChangeType(final Long orgId, final Long assetId,
            final String changeType) {
        try {
            List<AssetValuationDetails> entityList = valuationRepo.findLatestAssetIdWithChangeType(orgId, assetId,
                    changeType);
            if (entityList != null && !entityList.isEmpty()) {
                final AssetValuationDetails entity = entityList.get(0);
                final AssetValuationDetailsDTO depDTO = EvaluationMapper.mapToDTO(entity);
                return depDTO;
            }
            return null;
        } catch (Exception exp) {
            throw new FrameworkException(
                    "Unable to get latest data from asset valution table by asset Id for a given valuation change type",
                    exp);
        }
    }

    /**
     * Used to get list of DepreciationAssetDTO on the basis of asset id and orgId for change type other than passed change Type
     * 
     * @param orgId
     * @param assetId
     * @param changeType
     * @return DepreciationAssetDTO
     */
    @Override
    @Transactional(readOnly=true)
    public List<AssetValuationDetailsDTO> checkTransaction(final Long orgId, final Long assetId,
            final String changeType) {
        try {
            List<AssetValuationDetails> entityList = valuationRepo.checkTransaction(orgId, assetId, changeType);
            if (entityList != null && !entityList.isEmpty()) {
                List<AssetValuationDetailsDTO> dtoList = new ArrayList<>();
                for (AssetValuationDetails entity : entityList) {
                    final AssetValuationDetailsDTO depDTO = EvaluationMapper.mapToDTO(entity);
                    dtoList.add(depDTO);
                }
                return dtoList;
            }
            return null;
        } catch (Exception exp) {
            throw new FrameworkException(
                    "Unable to get data from asset valution table by asset Id for change type other than " + changeType,
                    exp);
        }
    }

    @Override
    @Transactional(readOnly=true)
    public List<AssetValuationDetailsDTO> findAssetTillDate(Long orgId, Long assetId, Date endDate) {
        try {
            List<AssetValuationDetails> entityList = valuationRepo.findAssetTillDate(orgId, assetId,
                    endDate);
            if (entityList != null && !entityList.isEmpty()) {
                List<AssetValuationDetailsDTO> dtoList = new ArrayList<>();
                for (AssetValuationDetails entity : entityList) {
                    final AssetValuationDetailsDTO depDTO = EvaluationMapper.mapToDTO(entity);
                    dtoList.add(depDTO);
                }
                return dtoList;
            }
            return null;
        } catch (Exception exp) {
            throw new FrameworkException(
                    "Unable to get data from asset valution table by asset Id with start and end dates", exp);
        }
    }

    @Override
    @Transactional(readOnly=true)
    public Date findLatestBookEndDate(Long orgId, Long assetId) {
        try {
            Date bookEndDate = valuationRepo.findLatestBookEndDate(orgId, assetId);
            if (bookEndDate != null) {
                return bookEndDate;
            }
            return null;
        } catch (Exception exp) {
            throw new FrameworkException("Unable to get latest data from asset valution table by asset Id", exp);
        }
    }
}

package com.abm.mainet.common.master.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TaxDefinationEntity;
import com.abm.mainet.common.integration.acccount.dto.TaxDefinationDto;
import com.abm.mainet.common.master.dao.TaxDefinationDao;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.repository.TaxDefinationRepository;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Service
public class TaxDefinationServiceImpl implements ITaxDefinationService {

	@Autowired
	private TaxDefinationRepository taxDefinationRepository;

	@Autowired
	private TbTaxMasService taxMasService;

	@Autowired
	private TaxDefinationDao taxDefinationDao;

	@Override
	@Transactional
	public void saveUpdateTaxDefinationList(List<TaxDefinationDto> taxDefinationList, List<Long> removeTaxId) {
		taxDefinationList.forEach(dto -> {
			TaxDefinationEntity entity = new TaxDefinationEntity();
			BeanUtils.copyProperties(dto, entity);
			taxDefinationRepository.save(entity);
		});
		if (removeTaxId != null && !removeTaxId.isEmpty())
			for (Long taxdefId : removeTaxId) {
				taxDefinationRepository.delete(taxdefId);
			}
	}

	@Override
	@Transactional(readOnly = true)
	public List<TaxDefinationDto> getTaxDefinationList(Long orgId) {

		List<TaxDefinationDto> dtoList = new ArrayList<>();
		TaxDefinationDto dto = null;

		try {
			final List<TaxDefinationEntity> entities = taxDefinationRepository.getTaxDefinationList(orgId);
			for (TaxDefinationEntity definationEntity : entities) {
				dto = new TaxDefinationDto();
				org.springframework.beans.BeanUtils.copyProperties(definationEntity, dto);
				TbTaxMas taxMas = taxMasService.findTaxByTaxIdAndOrgId(dto.getTaxId(), orgId);
				dto.setTaxDesc(taxMas.getTaxDesc());
				if (definationEntity.getRaTaxFact() != null)
					dto.setLookUpDesc(CommonMasterUtility.getCPDDescription(definationEntity.getRaTaxFact(),
							MainetConstants.MODE_EDIT));
				if (definationEntity.getCpdVendorSubType() != null)
					dto.setVendorSubTypeDesc(CommonMasterUtility
							.getCPDDescription(definationEntity.getCpdVendorSubType(), MainetConstants.MODE_EDIT));
				if (definationEntity.getTaxUnit() != null)
					dto.setTaxUnitDesc(CommonMasterUtility.getCPDDescription(definationEntity.getTaxUnit(),
							MainetConstants.MODE_EDIT));
				LookUp obj = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(taxMas.getTaxDescId(), orgId,
						MainetConstants.PG_REQUEST_PROPERTY.TXN);
                //#120829				if(obj != null) {
				if (obj.getOtherField() != null && !obj.getOtherField().isEmpty()) {
					dto.setLookUpOtherField(obj.getOtherField());
				} else {
					dto.setLookUpOtherField(MainetConstants.BLANK);
				}
				}
				dtoList.add(dto);
			}
		} catch (final Exception exception) {
			throw new FrameworkException("Error occured while searching Tax Defination List Data", exception);
		}
		return dtoList;
	}

	@Override
	public TaxDefinationDto getTaxDefinitionById(Long taxDefId) {
		TaxDefinationDto dto = null;
		TaxDefinationEntity entity = taxDefinationRepository.findOne(taxDefId);
		if (entity != null) {
			dto = new TaxDefinationDto();
			BeanUtils.copyProperties(entity, dto);
		}
		return dto;
	}

	@Override
	public List<TaxDefinationDto> findByAllGridSearchData(Long taxId, String panApp, Long orgId) {
		List<TaxDefinationDto> dtoList = new ArrayList<>();
		TaxDefinationDto dto = null;
		BigDecimal zero = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		try {
			final List<TaxDefinationEntity> entities = taxDefinationDao.findByAllGridSearchData(taxId, panApp, orgId);
			for (TaxDefinationEntity definationEntity : entities) {
				dto = new TaxDefinationDto();
				org.springframework.beans.BeanUtils.copyProperties(definationEntity, dto);
				TbTaxMas taxMas = taxMasService.findTaxByTaxIdAndOrgId(dto.getTaxId(), orgId);
				dto.setTaxDesc(taxMas.getTaxDesc());
				dto.setLookUpDesc(CommonMasterUtility.getCPDDescription(definationEntity.getRaTaxFact(),
						MainetConstants.MODE_EDIT));
				if (definationEntity.getCpdVendorSubType() != null)
					dto.setVendorSubTypeDesc(CommonMasterUtility
							.getCPDDescription(definationEntity.getCpdVendorSubType(), MainetConstants.MODE_EDIT));
				if (definationEntity.getTaxUnit() != null)
					dto.setTaxUnitDesc(CommonMasterUtility.getCPDDescription(definationEntity.getTaxUnit(),
							MainetConstants.MODE_EDIT));
				if (definationEntity.getRaTaxValue() == null)
					dto.setRaTaxValue(zero);
				if (definationEntity.getTaxThreshold() == null)
					dto.setTaxThreshold(zero);
				dtoList.add(dto);
			}
		} catch (final Exception exception) {
			throw new FrameworkException("Error occured while findByAllGridSearchData()", exception);
		}
		return dtoList;
	}

}

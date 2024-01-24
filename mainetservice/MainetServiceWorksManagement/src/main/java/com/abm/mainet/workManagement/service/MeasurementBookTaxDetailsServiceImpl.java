/**
 * 
 */
package com.abm.mainet.workManagement.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.workManagement.domain.MeasurementBookMaster;
import com.abm.mainet.workManagement.domain.MeasurementBookTaxDetails;
import com.abm.mainet.workManagement.dto.MeasurementBookTaxDetailsDto;
import com.abm.mainet.workManagement.repository.MeasurementBookTaxDetailRepository;

/**
 * @author Saiprasad.Vengurekar
 *
 */

@Service
public class MeasurementBookTaxDetailsServiceImpl implements MeasurementBookTaxDetailsService {
	private static final Logger LOGGER = Logger.getLogger(MeasurementBookTaxDetailsServiceImpl.class);

	@Autowired
	private MeasurementBookTaxDetailRepository mbTaxDetailRepository;

	@Override
	@Transactional
	public void saveUpdateMbTaxDetails(List<MeasurementBookTaxDetailsDto> mbTaxDtoList, List<Long> removeMbTaxDetIds) {
		MeasurementBookTaxDetails mbTaxDetailEntity = null;
		MeasurementBookMaster mbMaster = null;
		TbTaxMasEntity tbTaxMaster = null;
		LOGGER.info("saveUpdateMbTaxDetails method started");
		try {
			for (MeasurementBookTaxDetailsDto mbTaxDto : mbTaxDtoList) {
				mbTaxDetailEntity = new MeasurementBookTaxDetails();
				mbMaster = new MeasurementBookMaster();
				tbTaxMaster = new TbTaxMasEntity();
				BeanUtils.copyProperties(mbTaxDto, mbTaxDetailEntity);
				mbMaster.setWorkMbId(mbTaxDto.getMbId());
				tbTaxMaster.setTaxId(mbTaxDto.getTaxId());
				mbTaxDetailEntity.setMbMaster(mbMaster);
				mbTaxDetailEntity.setTbTaxMaster(tbTaxMaster);
				mbTaxDetailRepository.save(mbTaxDetailEntity);
			}
			if (removeMbTaxDetIds != null && !removeMbTaxDetIds.isEmpty()) {
				mbTaxDetailRepository.deleteMbTaxDetByMbTaxId(removeMbTaxDetIds);
			}
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling method saveUpdateMbTaxDetails " + e);
		}
		LOGGER.info("saveUpdateMbTaxDetails method End");
	}

	@Override
	@Transactional(readOnly = true)
	public List<MeasurementBookTaxDetailsDto> getMbTaxDetails(Long mbId,Long orgId) {
		List<MeasurementBookTaxDetailsDto> mbTaxDetDto = new ArrayList<>();
		MeasurementBookTaxDetailsDto mbTaxDto = null;
		List<MeasurementBookTaxDetails> mbTaxDetailEntity = mbTaxDetailRepository.getMBTaxDetByMbId(mbId,orgId);
		if (mbTaxDetailEntity != null) {
			for (MeasurementBookTaxDetails mbTaxDet : mbTaxDetailEntity) {
				mbTaxDto = new MeasurementBookTaxDetailsDto();
				BeanUtils.copyProperties(mbTaxDet, mbTaxDto);
				mbTaxDto.setMbId(mbTaxDet.getMbMaster().getWorkMbId());
				mbTaxDto.setTaxId(mbTaxDet.getTbTaxMaster().getTaxId());
				mbTaxDetDto.add(mbTaxDto);
			}

		}
		return mbTaxDetDto;
	}

}

/**
 *
 */
package com.abm.mainet.common.master.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.TbTaxAcMappingEntity;
import com.abm.mainet.common.domain.TbTaxAcMappingEntityHistory;
import com.abm.mainet.common.master.dto.TbTaxAcMappingBean;
import com.abm.mainet.common.master.mapper.TbTaxAcMappingServiceMapper;
import com.abm.mainet.common.master.repository.TbTaxBudgetcodeJpaRepository;

/**
 * @author Harsha.Ramachandran
 *
 */
@Service
public class TbTaxAcMappingServiceImpl implements TbTaxAcMappingService {

	@Resource
	private TbTaxAcMappingServiceMapper tbTaxBudgetcodeServiceMapper;

	@Resource
	private TbTaxBudgetcodeJpaRepository tbTaxBudgetcodeJpaRepository;

	@Autowired
	private AuditService auditService;

	@Override
	@Transactional
	public TbTaxAcMappingBean create(final TbTaxAcMappingBean tbTaxBudgetcodebean) {

		final TbTaxAcMappingEntity taxBudgetEntity = new TbTaxAcMappingEntity();

		tbTaxBudgetcodeServiceMapper.mapTbTaxBudgetCodeBeanToTbTaxBudgetCodeEntity(tbTaxBudgetcodebean,
				taxBudgetEntity);
		TbTaxAcMappingEntity taxBudgetEntitySaved = tbTaxBudgetcodeJpaRepository.save(taxBudgetEntity);

		TbTaxAcMappingEntityHistory mappingEntity = new TbTaxAcMappingEntityHistory();
		mappingEntity.setStatus(MainetConstants.InsertMode.ADD.getStatus());
		auditService.createHistory(taxBudgetEntity, mappingEntity);
		return tbTaxBudgetcodeServiceMapper.mapTbTaxBudgetCodeEntityToTbTaxBudgetCodeBean(taxBudgetEntitySaved);
	}

	@Override
	@Transactional
	public List<TbTaxAcMappingBean> getByTaxIdOrgId(final Long orgId, final Long taxId) {

		final List<TbTaxAcMappingEntity> entityList = tbTaxBudgetcodeJpaRepository.getByTaxIdOrgId(orgId, taxId);
		final List<TbTaxAcMappingBean> beanList = new ArrayList<>();
		TbTaxAcMappingBean bean = null;
		for (final TbTaxAcMappingEntity entity : entityList) {
			bean = new TbTaxAcMappingBean();
			bean = tbTaxBudgetcodeServiceMapper.mapTbTaxBudgetCodeEntityToTbTaxBudgetCodeBean(entity);
			beanList.add(bean);
		}
		return beanList;
	}

	@Override
	@Transactional
	public List<Object> getbudgetCodes(final List<Long> budgetCodeList) {
		final List<Object> list = tbTaxBudgetcodeJpaRepository.getBudgetCodeList(budgetCodeList);
		return list;
	}

	@Override
	@Transactional
	public TbTaxAcMappingBean update(final TbTaxAcMappingBean bean) {
		final TbTaxAcMappingEntity entity = new TbTaxAcMappingEntity();
		tbTaxBudgetcodeServiceMapper.mapTbTaxBudgetCodeBeanToTbTaxBudgetCodeEntity(bean, entity);
		TbTaxAcMappingEntity entitySaved = tbTaxBudgetcodeJpaRepository.save(entity);

		TbTaxAcMappingEntityHistory mappingEntity = new TbTaxAcMappingEntityHistory();
		mappingEntity.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
		auditService.createHistory(entity, mappingEntity);
		return tbTaxBudgetcodeServiceMapper.mapTbTaxBudgetCodeEntityToTbTaxBudgetCodeBean(entitySaved);
	}

	@Override
	@Transactional
	public TbTaxAcMappingBean findTbTaxBudgetCode(final Long taxId, final Long orgId) {
		final TbTaxAcMappingEntity entity = tbTaxBudgetcodeJpaRepository.findTbTaxBudgetCode(taxId, orgId);
		final TbTaxAcMappingBean bean = tbTaxBudgetcodeServiceMapper
				.mapTbTaxBudgetCodeEntityToTbTaxBudgetCodeBean(entity);
		return bean;
	}

	@Override
	@Transactional
	public void delete(final Long taxbId) {
		final TbTaxAcMappingEntity entity = tbTaxBudgetcodeJpaRepository.findOne(taxbId);
		tbTaxBudgetcodeJpaRepository.delete(entity);

	}

	@Override
	public TbTaxAcMappingBean findById(final Long taxbId) {
		final TbTaxAcMappingEntity entity = tbTaxBudgetcodeJpaRepository.findOne(taxbId);
		return tbTaxBudgetcodeServiceMapper.mapTbTaxBudgetCodeEntityToTbTaxBudgetCodeBean(entity);
	}

	@Override
	public TbTaxAcMappingBean getBySacHeadId(Long sacHeadId, Long dmdClass, Long orgId) {
		final TbTaxAcMappingEntity entity = tbTaxBudgetcodeJpaRepository.getBySacHeadId(sacHeadId, dmdClass, orgId);
		return tbTaxBudgetcodeServiceMapper.mapTbTaxBudgetCodeEntityToTbTaxBudgetCodeBean(entity);
	}

}

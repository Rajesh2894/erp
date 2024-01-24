package com.abm.mainet.water.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbWtBillMasEntity;
import com.abm.mainet.water.domain.WaterNoDuesEntity;
import com.abm.mainet.water.dto.NoDuesCertificateReqDTO;

/**
 * @author umashanker.kanaujiya
 *
 */
@Repository
public class WaterNoDuesCertificateDaoImpl extends AbstractDAO<TbKCsmrInfoMH> implements WaterNoDuesCertificateDao {

    private static final Logger LOGGER = Logger.getLogger(WaterNoDuesCertificateDaoImpl.class);

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.WaterNoDuesCertificateDao# getApplicantInformationById(java.lang.Long, java.lang.Long)
     */
    @Override
    public WaterNoDuesEntity getApplicantInformationById(final Long applicationId,
            final Long orgId) {

        LOGGER.info("start the getApplicantInformationById()....");
        WaterNoDuesEntity duesEntity = null;
        try {
            final Query query = createQuery("select am from WaterNoDuesEntity am  WHERE  am.orgId=:orgId and am.wtV1 =:wtV1 ");
            query.setParameter("orgId", orgId);
            query.setParameter("wtV1", String.valueOf(applicationId));
            duesEntity = (WaterNoDuesEntity) query.getSingleResult();

        } catch (final Exception exception) {
            LOGGER.error("Exception occur in getApplicantInformationById() ", exception);

        }
        return duesEntity;
    }

    @Override
    public TbKCsmrInfoMH getWaterConnByConsNo(final NoDuesCertificateReqDTO requestDTO) {

        LOGGER.info("start the getWaterConnByConsNo()....");
        TbKCsmrInfoMH tbKCsmrInfoMH = null;
        try {
            final Query query = createQuery("select am from TbKCsmrInfoMH am  WHERE  am.orgId=:orgId and am.csCcn =:csCcn");
            query.setParameter("orgId", requestDTO.getOrgId());
            query.setParameter("csCcn", requestDTO.getConsumerNo());
            tbKCsmrInfoMH = (TbKCsmrInfoMH) query.getSingleResult();

        } catch (final Exception exception) {
            LOGGER.error("Exception occur in getWaterConnByConsNo() ", exception);

        }
        return tbKCsmrInfoMH;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.WaterNoDuesCertificateDao#getWaterDues (long, java.lang.Long)
     */
    @Override
    public TbWtBillMasEntity getWaterDues(final long csIdn, final Long orgId) {

        LOGGER.info("start the getWaterDues()....");
        TbWtBillMasEntity tbKCsmrInfoMH = null;
        try {
            final Query query = createQuery(
                    "select am from TbWtBillMasEntity am  WHERE  am.orgid=:orgId and am.csIdn =:csIdn order by bmIdno desc ");
            query.setParameter("orgId", orgId);
            query.setParameter("csIdn", csIdn);
            final List<TbWtBillMasEntity> list = query.getResultList();
            if ((list != null) && !list.isEmpty()) {
                tbKCsmrInfoMH = list.get(0);
            }
        } catch (final Exception exception) {
            LOGGER.error("Exception occur in getWaterDues() ", exception);

        }
        return tbKCsmrInfoMH;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.WaterNoDuesCertificateDao#saveFormData (com.abm.mainet.water.domain.WaterNoDuesEntity)
     */
    @Override
    public WaterNoDuesEntity saveFormData(final WaterNoDuesEntity entity) {

        LOGGER.info("start the getWaterConnByConsNo()....");
        try {
            entityManager.persist(entity);

        } catch (final Exception exception) {
            LOGGER.error("Exception occur in getWaterConnByConsNo() ", exception);

        }
        return entity;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.water.dao.WaterNoDuesCertificateDao#update(com.abm.mainet.water.domain.WaterNoDuesEntity)
     */
    @Override
    public Boolean update(final WaterNoDuesEntity entity) {

        LOGGER.info("start the update()....");
        try {
            entityManager.merge(entity);
            return true;

        } catch (final Exception exception) {
            LOGGER.error("Exception occur in update() ", exception);
            return false;
        }

    }

	@Override
	public List<TbKCsmrInfoMH> getWaterConnByPropNoNFlatNo(String propNo, String flatNo, Long orgId) {

		Query query = createQuery(buildQuery(propNo, flatNo, orgId));
		query.setParameter("orgId", orgId);
		query.setParameter("propNo", propNo);
		if (StringUtils.isNoneBlank(flatNo) && flatNo != null) {
			query.setParameter("flatNo", flatNo);
		}
		
		final List<TbKCsmrInfoMH> list = query.getResultList();
		if ((list != null) && !list.isEmpty()) {
			return list;
		}

		return null;
	}

	private String buildQuery(String propNo, String flatNo, Long orgId) {
		StringBuilder searchQuery = new StringBuilder(
				"select am from TbKCsmrInfoMH am  WHERE  am.orgId=:orgId and am.propertyNo =:propNo");

		if (StringUtils.isNoneBlank(flatNo) && flatNo != null) {
			searchQuery.append(" and am.flatNo=:flatNo");
		}

		return searchQuery.toString();
	}

}

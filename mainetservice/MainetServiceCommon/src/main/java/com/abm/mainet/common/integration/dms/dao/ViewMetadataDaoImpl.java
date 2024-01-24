package com.abm.mainet.common.integration.dms.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.integration.dms.domain.DmsDocsMetadata;
import com.abm.mainet.common.integration.dms.domain.DmsDocsMetadataDet;
import com.aspose.slides.Collections.ArrayList;

@Repository
public class ViewMetadataDaoImpl extends AbstractDAO<DmsDocsMetadata> implements IViewMetadataDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<DmsDocsMetadata> getMetadataDetails(String deptId, String metadataId, String metadataValue, Long roleId,
			long orgid, Long zone, Long ward, Long mohalla, String docRefNo, Long docType) {
		String doc = String.valueOf(docType);
		StringBuilder queryString = new StringBuilder(
				"select dt1 from DmsDocsMetadata dt1,DmsDocsMetadataDet dt2 where dt1.deptId=:deptId "
						+ " and dt1.dmsId=dt2.dmsDocsMetadata.dmsId  and dt1.orgId.orgid=:orgid and dt1.isActive='A'  and dt1.wfStatus='APPROVED' ");

		if (metadataId != null && !metadataId.equalsIgnoreCase("0")) {
			//queryString.append(" and dt2.mtKey=:mtKey and dt2.mtVal like :mtVal ");
			//queryString.append(" and dt2.mtKey=:mtKey and dt2.mtVal=:mtVal ");
			queryString.append(" and dt2.mtKey=:mtKey");
		}
		if (!StringUtils.isBlank(metadataValue)) {
			queryString.append(" and dt2.mtVal=:mtVal ");
		}
		if (zone != null && zone != 0) {
			queryString.append(" and dt1.zone=:zone");
		}
		if (ward != null && ward != 0) {
			queryString.append(" and dt1.ward=:ward");
		}
		if (mohalla != null && mohalla != 0) {
			queryString.append(" and dt1.mohalla=:mohalla");
		}
		if (StringUtils.isNotEmpty(docRefNo)) {
			queryString.append(" and dt1.docRefNo=:docRefNo");
		}
		if (docType != null && docType != 0) {
			queryString.append(" and dt1.docType like :doc");
		}

		Query query = this.createQuery(queryString.toString());
		if (deptId != null) {
			query.setParameter("deptId", deptId);
		}
		if (metadataId != null && !metadataId.equalsIgnoreCase("0")) {
			query.setParameter("mtKey", metadataId);
			//query.setParameter("mtVal", "%" + metadataValue + "%");
			// query.setParameter("mtVal", metadataValue);
		}
		if (!StringUtils.isBlank(metadataValue)) {
			query.setParameter("mtVal", metadataValue);
		}
		query.setParameter("orgid", orgid);
		if (zone != null && zone != 0) {
			query.setParameter("zone", zone);
		}
		if (ward != null && ward != 0) {
			query.setParameter("ward", ward);
		}
		if (mohalla != null && mohalla != 0) {
			query.setParameter("mohalla", mohalla);
		}
		if (StringUtils.isNotEmpty(docRefNo)) {
			query.setParameter("docRefNo", docRefNo);
		}
		if (docType != null && docType != 0) {
			query.setParameter("doc", "%" + doc + "%");
		}

		List<DmsDocsMetadata> entity = query.getResultList();
		/*List<Long> dmsId = new ArrayList();
		entity.forEach(action -> {
			dmsId.add(action.getDmsId());
		});*/
		/*List<DmsDocsMetadataDet> detEntity = getMetadataDetDetails(dmsId, orgid);
		entity.forEach(data -> {
			List<DmsDocsMetadataDet> parentList = new ArrayList();
			detEntity.forEach(childData -> {
				if (childData.getDmsDocsMetadata().getDmsId().equals(data.getDmsId())) {
					parentList.add(childData);
				}
			});
			data.setDmsDocsMetadataDetList(parentList);
		});*/
		return entity;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DmsDocsMetadataDet> getMetadataDetDetails(List<Long> dmsId, long orgid) {
		StringBuilder queryString = new StringBuilder(
				"select dt from DmsDocsMetadataDet dt where dt.orgId.orgid=:orgid ");

		if (dmsId != null && !dmsId.isEmpty()) {
			StringBuilder builder = new StringBuilder();
			dmsId.forEach(data -> {
				builder.append(data + MainetConstants.operator.COMMA);
			});
			builder.deleteCharAt((builder.length() - 1));
			queryString.append(" and dt.dmsDocsMetadata.dmsId in (" + builder + ")");
		}
		Query query = this.createQuery(queryString.toString());
		query.setParameter("orgid", orgid);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DmsDocsMetadata> getApprovalMetadataDetails(String seqNo, String wfStatus, Long orgid) {
		StringBuilder queryString = new StringBuilder(
				"select dt1 from DmsDocsMetadata dt1 where dt1.orgId.orgid=:orgid and dt1.isActive='A'");

		if (StringUtils.isNotEmpty(seqNo)) {
			queryString.append(" and dt1.seqNo=:seqNo");
		}
		if (StringUtils.isNotEmpty(wfStatus)) {
			queryString.append(" and dt1.wfStatus=:wfStatus");
		}
		Query query = this.createQuery(queryString.toString());

		query.setParameter("orgid", orgid);

		if (StringUtils.isNotEmpty(seqNo)) {
			query.setParameter("seqNo", seqNo);
		}
		if (StringUtils.isNotEmpty(wfStatus)) {
			query.setParameter("wfStatus", wfStatus);
		}
		List<DmsDocsMetadata> entity = query.getResultList();
		List<Long> dmsId = new ArrayList();
		entity.forEach(action -> {
			dmsId.add(action.getDmsId());
		});
		List<DmsDocsMetadataDet> detEntity = getMetadataDetDetails(dmsId, orgid);
		entity.forEach(data -> {
			List<DmsDocsMetadataDet> parentList = new ArrayList();
			detEntity.forEach(childData -> {
				if (childData.getDmsDocsMetadata().getDmsId().equals(data.getDmsId())) {
					parentList.add(childData);
				}
			});
			data.setDmsDocsMetadataDetList(parentList);
		});
		return entity;
	}

}

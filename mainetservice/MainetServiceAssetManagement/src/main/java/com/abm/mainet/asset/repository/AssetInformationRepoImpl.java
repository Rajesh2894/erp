/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.abm.mainet.asset.domain.AssetInformation;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * Repository Implementation Class for Asset Information
 * 
 * @author sarojkumar.yadav
 *
 */
@Repository
public class AssetInformationRepoImpl extends AbstractDAO<Long> implements AssetInformationRepoCustom {

	/**
	 * update Asset information Entity with primary Key AssetId.
	 * 
	 * @param AssetInformation entity
	 */
	@Override
	public void updateByAssetId(final Long assetId, final AssetInformation entity) {
		final Query query = createQuery(buildUpdateDataQuery(assetId, entity.getAssetName(), entity.getSerialNo(),
				entity.getBarcodeNo(), entity.getRfiId(), entity.getBrandName(), entity.getAssetStatus(),
				entity.getAssetModelIdentifier(), entity.getAssetParentIdentifier(), entity.getDetails(),
				entity.getAssetGroup(), entity.getAssetClass1(), entity.getAssetClass2(), entity.getAcquisitionMethod(),
				entity.getRemark(), entity.getNoOfSimilarAsset(), entity.getInvestmentReason(), entity.getAssetType(),
				entity.getInventoryNo(), entity.getLastInventoryOn(), entity.getInventoryNote(),
				entity.getIncludeAssetInventoryLst(), entity.getCapitalizeOn(), entity.getFirstAcquisitionOn(),
				entity.getAcquisitionYear(), entity.getOrderOn(), entity.getCustodian(), entity.getEmployeeId(),
				entity.getLength(), entity.getLengthValue(), entity.getBreadth(), entity.getBreadthValue(),
				entity.getHeight(), entity.getHeightValue(), entity.getWidth(), entity.getWidthValue(),
				entity.getWeight(), entity.getWeightValue(), entity.getArea(), entity.getAreaValue(),
				entity.getVolume(), entity.getVolumeValue(), entity.getLocation(), entity.getAppovalStatus(),
				entity.getUpdatedBy(), entity.getUpdatedDate(), entity.getLgIpMacUpd(), entity.getPurpose(),
				entity.getNoOfFloor(), entity.getCarpet(), entity.getCarpetValue(), entity.getRegisterDetail(),
				entity.getScreenSize(), entity.getHardDiskSize(), entity.getOsName(), entity.getRamSize(),
				entity.getProcessor(), entity.getManufacturingYear()));

		if (entity.getAssetName() != null && !entity.getAssetName().isEmpty()) {
			query.setParameter("assetName", entity.getAssetName());
		}
		// if (entity.getSerialNo() != null && !entity.getSerialNo().isEmpty()) {
		query.setParameter("serialNo", entity.getSerialNo());
		// }

		query.setParameter("barcodeNo", entity.getBarcodeNo());
		query.setParameter("rfiId", entity.getRfiId());

		query.setParameter("brandName", entity.getBrandName());

		if (entity.getAssetStatus() != null && entity.getAssetStatus() != 0) {
			query.setParameter("assetStatus", entity.getAssetStatus());
		}
		query.setParameter("assetModelIdentifier", entity.getAssetModelIdentifier());

		query.setParameter("assetParentIdentifier", entity.getAssetParentIdentifier());

		if (entity.getDetails() != null && !entity.getDetails().isEmpty()) {
			query.setParameter("details", entity.getDetails());
		}

		/* if (entity.getAssetGroup() != null && entity.getAssetGroup() != 0) { */
		query.setParameter("assetGroup", entity.getAssetGroup());
		// }

		if (entity.getAssetClass1() != null && entity.getAssetClass1() != 0) {
			query.setParameter("assetClass1", entity.getAssetClass1());
		}

		if (entity.getAssetClass2() != null && entity.getAssetClass2() != 0) {
			query.setParameter("assetClass2", entity.getAssetClass2());
		}

		if (entity.getAcquisitionMethod() != null && entity.getAcquisitionMethod() != 0) {
			query.setParameter("acquisitionMethod", entity.getAcquisitionMethod());
		}

		query.setParameter("remark", entity.getRemark());

		if (entity.getNoOfSimilarAsset() != null && entity.getNoOfSimilarAsset() != 0) {
			query.setParameter("noOfSimilarAsset", entity.getNoOfSimilarAsset());
		}

		query.setParameter("investmentReason", entity.getInvestmentReason());
		query.setParameter("assetType", entity.getAssetType());

		query.setParameter("inventoryNo", entity.getInventoryNo());
		query.setParameter("lastInventoryOn", entity.getLastInventoryOn());
		query.setParameter("inventoryNote", entity.getInventoryNote());
		query.setParameter("includeAssetInventoryLst", entity.getIncludeAssetInventoryLst());
		query.setParameter("capitalizeOn", entity.getCapitalizeOn());
		query.setParameter("firstAcquisitionOn", entity.getFirstAcquisitionOn());
		query.setParameter("acquisitionYear", entity.getAcquisitionYear());
		query.setParameter("orderOn", entity.getOrderOn());
		query.setParameter("custodian", entity.getCustodian());
		query.setParameter("employeeId", entity.getEmployeeId());
		query.setParameter("length", entity.getLength());
		query.setParameter("lengthValue", entity.getLengthValue());
		query.setParameter("breadth", entity.getBreadth());
		query.setParameter("breadthValue", entity.getBreadthValue());
		query.setParameter("height", entity.getHeight());
		query.setParameter("heightValue", entity.getHeightValue());
		query.setParameter("width", entity.getWidth());
		query.setParameter("widthValue", entity.getWidthValue());
		query.setParameter("weight", entity.getWeight());
		query.setParameter("weightValue", entity.getWeightValue());
		query.setParameter("area", entity.getArea());
		query.setParameter("areaValue", entity.getAreaValue());
		query.setParameter("volume", entity.getVolume());
		query.setParameter("volumeValue", entity.getVolumeValue());
		query.setParameter("location", entity.getLocation());
		query.setParameter("appovalStatus", entity.getAppovalStatus());//
		query.setParameter("registerDetail", entity.getRegisterDetail());
		query.setParameter("purpose", entity.getPurpose());
		query.setParameter("noOfFloor", entity.getNoOfFloor());
		query.setParameter("carpet", entity.getCarpet());
		query.setParameter("carpetValue", entity.getCarpetValue());
		query.setParameter("astCode", entity.getAstCode());

		if (entity.getUpdatedBy() != null && entity.getUpdatedBy() != 0) {
			query.setParameter("updatedBy", entity.getUpdatedBy());
		}
		if (entity.getUpdatedDate() != null) {
			query.setParameter("updatedDate", entity.getUpdatedDate());
		}
		if (entity.getLgIpMacUpd() != null && !entity.getLgIpMacUpd().isEmpty()) {
			query.setParameter("lgIpMacUpd", entity.getLgIpMacUpd());
		}
		
		query.setParameter("screenSize", entity.getScreenSize());
		query.setParameter("hardDiskSize", entity.getHardDiskSize());
		query.setParameter("osName", entity.getOsName());
		query.setParameter("processor", entity.getProcessor());
		query.setParameter("ramSize", entity.getRamSize());
		query.setParameter("manufacturingYear", entity.getManufacturingYear());

		query.setParameter("assetId", assetId);
		int result = query.executeUpdate();
		if (!(result > 0)) {
			throw new FrameworkException("asset Information could not be updated");

		}
	}

	/**
	 * Method to create Dynamic Update Query depend upon parameters provided in the
	 * method
	 * 
	 * @return String of Dynamic query
	 */
	private String buildUpdateDataQuery(Long assetId, String assetName, String serialNo, Long barcodeNo, String rfiId,
			String brandName, Long assetStatus, String assetModelIdentifier, String assetChildIdentifier,
			String details, Long assetGroup, Long assetClass1, Long assetClass2, Long acquisitionMethod, String remark,
			Long noOfSimilarAsset, Long investmentReason, Long assetType, Long inventoryNo, Date lastInventoryOn,
			String inventoryNote, String includeAssetInventoryLst, Date capitalizeOn, Date firstAcquisitionOn,
			Long acquisitionYear, Date orderOn, String custodian, Long employeeId, BigDecimal length, Long lengthValue,
			BigDecimal breadth, Long breadthValue, BigDecimal height, Long heightValue, BigDecimal width,
			Long widthValue, BigDecimal weight, Long weightValue, BigDecimal area, Long areaValue, BigDecimal volume,
			Long volumeValue, Long location, String appovalStatus, Long updatedBy, Date updatedDate, String lgIpMacUpd,
			String purpose, Long noOfFloor, BigDecimal carpet, Long carpetValue, String registerDetail, Long screenSize,
			Long hardDiskSize, Long osName, Long ramSize, Long processor, Date manufacturingYear) {

		final StringBuilder builder = new StringBuilder();
		builder.append("update AssetInformation ai set");

		if (assetName != null) {
			builder.append(" ai.assetName=:assetName");
		}
		// if (serialNo != null) { as per requirement it is not mandatory
		builder.append(",ai.serialNo=:serialNo");
		// }
		builder.append(",ai.barcodeNo=:barcodeNo");
		builder.append(",ai.rfiId=:rfiId");

		builder.append(",ai.brandName=:brandName");

		if (assetStatus != null && assetStatus != 0) {
			builder.append(",ai.assetStatus=:assetStatus");
		}

		builder.append(",ai.assetModelIdentifier=:assetModelIdentifier");

		builder.append(",ai.assetParentIdentifier=:assetParentIdentifier");

		if (details != null && !details.isEmpty()) {
			builder.append(",ai.details=:details");
		}

		/* if (assetGroup != null && assetGroup != 0) { */
		builder.append(",ai.assetGroup=:assetGroup");
		// }

		if (assetClass1 != null && assetClass1 != 0) {
			builder.append(",ai.assetClass1=:assetClass1");
		}

		if (assetClass2 != null && assetClass2 != 0) {
			builder.append(",ai.assetClass2=:assetClass2");
		}

		if (acquisitionMethod != null && acquisitionMethod != 0) {
			builder.append(",ai.acquisitionMethod=:acquisitionMethod");
		}

		builder.append(",ai.remark=:remark");

		if (noOfSimilarAsset != null && noOfSimilarAsset != 0) {
			builder.append(",ai.noOfSimilarAsset=:noOfSimilarAsset");
		}

		builder.append(",ai.investmentReason=:investmentReason");
		builder.append(",ai.assetType=:assetType");

		builder.append(",ai.inventoryNo=:inventoryNo");
		builder.append(",ai.lastInventoryOn=:lastInventoryOn");
		builder.append(",ai.inventoryNote=:inventoryNote");
		builder.append(",ai.includeAssetInventoryLst=:includeAssetInventoryLst");
		builder.append(",ai.capitalizeOn=:capitalizeOn");
		builder.append(",ai.firstAcquisitionOn=:firstAcquisitionOn");
		builder.append(",ai.acquisitionYear=:acquisitionYear");
		builder.append(",ai.orderOn=:orderOn");
		builder.append(",ai.custodian=:custodian");
		builder.append(",ai.employeeId=:employeeId");
		builder.append(",ai.length=:length");
		builder.append(",ai.lengthValue=:lengthValue");
		builder.append(",ai.breadth=:breadth");
		builder.append(",ai.breadthValue=:breadthValue");
		builder.append(",ai.height=:height");
		builder.append(",ai.heightValue=:heightValue");
		builder.append(",ai.width=:width");
		builder.append(",ai.widthValue=:widthValue");
		builder.append(",ai.weight=:weight");
		builder.append(",ai.weightValue=:weightValue");
		builder.append(",ai.area=:area");
		builder.append(",ai.areaValue=:areaValue");
		builder.append(",ai.volume=:volume");
		builder.append(",ai.volumeValue=:volumeValue");
		builder.append(",ai.location=:location");
		builder.append(",ai.appovalStatus=:appovalStatus");///
		builder.append(",ai.registerDetail=:registerDetail");
		builder.append(",ai.purpose=:purpose");
		builder.append(",ai.noOfFloor=:noOfFloor");
		builder.append(",ai.carpet=:carpet");
		builder.append(",ai.carpetValue=:carpetValue");
		builder.append(",ai.astCode=:astCode");
		builder.append(",ai.screenSize=:screenSize");
		builder.append(",ai.hardDiskSize=:hardDiskSize");
		builder.append(",ai.osName=:osName");
		builder.append(",ai.ramSize=:ramSize");
		builder.append(",ai.processor=:processor");
		builder.append(",ai.manufacturingYear=:manufacturingYear");

		if (updatedBy != null && updatedBy != 0) {
			builder.append(",ai.updatedBy=:updatedBy");
		}
		if (updatedDate != null) {
			builder.append(",ai.updatedDate=:updatedDate");
		}
		if (lgIpMacUpd != null && !lgIpMacUpd.isEmpty()) {
			builder.append(",ai.lgIpMacUpd=:lgIpMacUpd");
		}
		builder.append(" where ai.assetId=:assetId");
		return builder.toString();
	}

	/**
	 * Used to update approval status Flag
	 * 
	 * @param assetId
	 * @param entity
	 */
	@Override
	public boolean updateAppStatusFlag(final Long assetId, final Long orgId, final String appovalStatus,
			String astAppNo) {

		String hql = "update AssetInformation ai set ai.appovalStatus=:appovalStatus, ai.astAppNo=:astAppNo where"
				+ " ai.assetId=:assetId and ai.orgId=:orgId";

		final Query query = createQuery(hql);

		if (appovalStatus != null && !appovalStatus.isEmpty()) {
			query.setParameter("appovalStatus", appovalStatus);
		}

		query.setParameter("astAppNo", astAppNo);
		if (assetId != null) {
			query.setParameter("assetId", assetId);
		}

		if (orgId != null && orgId > 0) {
			query.setParameter("orgId", orgId);
		}

		int result = query.executeUpdate();
		if (result == 1) {

			return true;
		}

		return false;
	}

	/**
	 * Used to update url parameter
	 * 
	 * @param serialNo
	 * @param orgId
	 * @param urlParam
	 */
	@Override
	public boolean updateURLParam(final Long assetId, final Long orgId, final String urlParam) {

		String hql = "update AssetInformation ai set ai.urlParam=:urlParam where"
				+ " ai.assetId=:assetId and ai.orgId=:orgId";

		final Query query = createQuery(hql);

		if (urlParam != null && !urlParam.isEmpty()) {
			query.setParameter("urlParam", urlParam);
		}

		if (assetId != null) {
			query.setParameter("assetId", assetId);
		}

		if (orgId != null && orgId > 0) {
			query.setParameter("orgId", orgId);
		}

		int result = query.executeUpdate();
		if (result == 1) {

			return true;
		}

		return false;
	}

	@Override
	public boolean updateAstCode(final Long assetId, final Long orgId, final String astCode) {

		String hql = "update AssetInformation ai set ai.astCode=:astCode where"
				+ " ai.assetId=:assetId and ai.orgId=:orgId ";

		final Query query = createQuery(hql);

		if (astCode != null && !astCode.isEmpty()) {
			query.setParameter("astCode", astCode);
		}

		if (assetId != null) {
			query.setParameter("assetId", assetId);
		}

		if (orgId != null && orgId > 0) {
			query.setParameter("orgId", orgId);
		}

		int result = query.executeUpdate();
		if (result == 1) {

			return true;
		}

		return false;
	}

	@Override
	public boolean updateStatusFlag(Long assetId, Long orgId, String appovalStatus, Long statusId, String astAppNo) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaUpdate<AssetInformation> q = criteriaBuilder.createCriteriaUpdate(AssetInformation.class);
		Root<AssetInformation> root = q.from(AssetInformation.class);
		q.set("assetStatus", statusId);
		q.set("appovalStatus", appovalStatus);
		q.set("astAppNo", astAppNo);
		q.where(criteriaBuilder.equal(root.get("assetId"), assetId), criteriaBuilder.equal(root.get("orgId"), orgId));
		int result = entityManager.createQuery(q).executeUpdate();
		if (result == 1) {
			return true;
		}
		return false;
	}

	@Override
	public List<AssetInformation> checkDuplicateSerialNo(Long orgId, String serialNo, Long assetId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AssetInformation> criteriaQuery = criteriaBuilder.createQuery(AssetInformation.class);
		Root<AssetInformation> astinfo = criteriaQuery.from(AssetInformation.class);
		criteriaQuery.select(astinfo.get("serialNo"));
		if (assetId != null) {
			criteriaQuery.where(criteriaBuilder.notEqual(astinfo.get("assetId"), assetId),
					criteriaBuilder.equal(astinfo.get("orgId"), orgId),
					criteriaBuilder.equal(astinfo.get("serialNo"), serialNo));
		} else {
			criteriaQuery.where(criteriaBuilder.equal(astinfo.get("orgId"), orgId),
					criteriaBuilder.equal(astinfo.get("serialNo"), serialNo));
		}
		Query query = entityManager.createQuery(criteriaQuery);
		List<AssetInformation> result = query.getResultList();
		return result;
	}

}

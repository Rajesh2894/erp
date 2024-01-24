package com.abm.mainet.materialmgmt.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.abm.mainet.materialmgmt.domain.InvoiceEntryEntity;


public interface IInvoiceEntryRepository extends JpaRepository<InvoiceEntryEntity, Long> {

	@Query("select distinct grn.poid, po.poNo from GoodsReceivedNotesEntity grn join grn.inspectionDetEntityList gitem,  PurchaseOrderEntity po "
			+ " where po.poId=grn.poid and grn.storeid=:storeId and gitem.decision=:decision and gitem.status=:status and grn.orgId=:orgId "
			+ " and grn.grnid not in (select ig.grnId from InvoiceEntryGRNEntity ig where ig.status=:status and ig.orgId=:orgId) order by 1")
	List<Object[]> fetchPOListByStore(@Param("storeId") Long storeId, @Param("orgId") Long orgId, @Param("decision") Character decision, 
			@Param("status") Character status);
	
	@Query("select distinct grn.grnid, grn.grnno, po.vendorId from GoodsReceivedNotesEntity grn join grn.inspectionDetEntityList gitem, "
			+ " PurchaseOrderEntity po where po.poId=grn.poid and grn.storeid=:storeId and grn.poid=:poId and gitem.decision=:decision "
			+ " and gitem.status=:status and grn.orgId=:orgId and grn.Status!=:grnStatus and grn.grnid not in (select ig.grnId from "
			+ " InvoiceEntryGRNEntity ig where ig.status=:status and ig.orgId=:orgId) order by 1")
	List<Object[]> fetchGRNListByStoreAndPoId(@Param("storeId") Long storeId, @Param("poId") Long poId, @Param("orgId") Long orgId,
			@Param("grnStatus") String grnStatus, @Param("decision") Character decision, @Param("status") Character status);

	@Query("select distinct grn.grnid, grn.grnno, grn.lmoDate FROM GoodsReceivedNotesEntity grn WHERE grn.grnid in (:grnidList) and "
			+ " grn.Status!=:status and grn.orgId=:orgId")
	public List<Object[]> getGRNDataByGRNIds(@Param("grnidList")List<Long> grnidList, @Param("orgId")Long orgId,  @Param("status") String status);

	@Query("select grn.grnid, gitem.grnitementryid, gitem.itemMasterEntity.itemId, gitem.quantity, pdet.unitPrice, pdet.tax from "
			+ "	GoodsReceivedNotesEntity grn join grn.inspectionDetEntityList gitem, PurchaseOrderDetEntity pdet where "
			+ "	pdet.purchaseOrderEntity.poId=grn.poid and pdet.itemId=gitem.itemMasterEntity.itemId and grn.grnid in (:grnidList) "
			+ " and gitem.decision=:decision and gitem.status=:status and grn.orgId=:orgId order by 1")	
	public List<Object[]> getItemDataByGRNIds(@Param("grnidList")List<Long> grnidList, @Param("orgId")Long orgId,  @Param("decision") Character decision,
				@Param("status") Character status);
	
	@Modifying
	@Query("update InvoiceEntryEntity ie set ie.invoiceStatus=:invoiceStatus, ie.wfFlag=:wfFlag where ie.invoiceNo=:invoiceNo and ie.orgId=:orgId")
	void updateWorkFlowStatus(@Param("invoiceNo") String invoiceNo, @Param("orgId") Long orgId, @Param("invoiceStatus") Character invoiceStatus, @Param("wfFlag") String wfFlag);
	
	public InvoiceEntryEntity findByInvoiceIdAndOrgId(@Param("invoiceId") Long invoiceId, @Param("orgId")Long orgId);
	
	public InvoiceEntryEntity findByInvoiceNoAndOrgId(@Param("invoiceNo") String invoiceNo, @Param("orgId")Long orgId);
	
	@Query("select distinct ie.poId, po.poNo from InvoiceEntryEntity ie, PurchaseOrderEntity po where po.poId=ie.poId and ie.orgId=:orgId order by ie.poId")
	List<Object[]> fetchPOListFromInvoice(@Param("orgId") Long orgId);
	
	@Query("select prydet.sacHeadId from PurchaseOrderDetEntity podet, PurchaseRequistionYearDetEntity prydet where "
			+ "podet.prId=prydet.purchaseRequistionEntity.prId and podet.purchaseOrderEntity.poId=:poId and podet.orgId=:orgId")
	Long fetchAccHeadIdsForInvoice(@Param("poId") Long poId, @Param("orgId") Long orgId);
	
	@Modifying
	@Query("update InvoiceEntryEntity ie set ie.paymentStatus=:paymentStatus, ie.paymentMade=:invoiceAmt where ie.invoiceNo=:invoiceNo and ie.orgId=:orgId")
	void updateInvoicePaymentDet(@Param("invoiceNo") String invoiceNo, @Param("invoiceAmt") BigDecimal invoiceAmt, @Param("orgId") Long orgId, 
			 @Param("paymentStatus") Character paymentStatus);

}

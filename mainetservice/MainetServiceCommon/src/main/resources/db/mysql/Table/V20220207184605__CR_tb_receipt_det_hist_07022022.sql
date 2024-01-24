--liquibase formatted sql
--changeset Kanchan:V20220207184605__CR_tb_receipt_det_hist_07022022.sql
drop table tb_receipt_det_hist;
--liquibase formatted sql
--changeset Kanchan:V20220207184605__CR_tb_receipt_det_hist_070220221sql
CREATE TABLE tb_receipt_det_hist (
 rf_feehistid bigint(12) primary Key NOT NULL,
 RF_FEEID bigint(12) NOT NULL,
 RM_RCPTID bigint(12) NOT NULL,
 TAX_ID bigint(12) DEFAULT NULL,
 RM_DEMAND decimal(12,2) DEFAULT NULL, 
 DPS_SLIPID bigint(12) DEFAULT NULL,
 BUDGETCODE_ID bigint(12) DEFAULT NULL, 
 SAC_HEAD_ID bigint(12) DEFAULT NULL, 
 BILLDET_ID bigint(12) DEFAULT NULL, 
 BM_IDNO bigint(12) DEFAULT NULL,
 RF_FEEAMOUNT decimal(12,2) NOT NULL, 
 RF_V1 varchar(200) DEFAULT NULL,
 RF_V2 varchar(200) DEFAULT NULL,
 RF_V3 varchar(200) DEFAULT NULL,
 RF_V4 varchar(200) DEFAULT NULL,
 RF_V5 varchar(200) DEFAULT NULL,
 RF_N4 decimal(15,0) DEFAULT NULL,
 RF_N5 decimal(15,0) DEFAULT NULL,
 RF_D1 datetime DEFAULT NULL,
 RF_D2 datetime DEFAULT NULL,
 RF_D3 datetime DEFAULT NULL,
 RF_LO1 char(1) DEFAULT NULL, 
 RF_LO2 char(1) DEFAULT NULL, 
 RF_LO3 char(1) DEFAULT NULL, 
 ORGID int(11) NOT NULL,
 CREATED_BY int(11) NOT NULL,
 CREATED_DATE datetime NOT NULL,
 UPDATED_BY int(11) DEFAULT NULL,
 UPDATED_DATE datetime DEFAULT NULL,
 LG_IP_MAC varchar(100) NOT NULL, 
 LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
 H_Status varchar(200) DEFAULT NULL
 
) ;

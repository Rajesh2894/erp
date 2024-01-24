--liquibase formatted sql
--changeset Kanchan:V20201006145442__CR_wms_bid_master_06102020.sql
DROP TABLE IF EXISTS tb_wms_bid_master;
--liquibase formatted sql
--changeset Kanchan:V20201006145442__CR_wms_bid_master_061020201.sql
create table tb_wms_bid_master (
bid_Id	bigint(12) NOT NULL Primary key,
vendor_id	bigint(12) NOT NULL, 	
bid_id_desc	varchar(50) NOT NULL ,	
overall_tender_score bigint(12) NOT NULL ,
overall_comm_score bigint(12) NOT NULL, 
status	varchar(1) NOT NULL, 
bid_type	varchar(20)  NOT NULL,
ORGID bigint(12) NOT NULL ,
 CREATED_BY bigint(12) NOT NULL ,
 CREATED_DATE datetime NOT NULL,
 UPDATED_BY bigint(12) DEFAULT NULL ,
 UPDATED_DATE datetime DEFAULT NULL,
 LG_IP_MAC varchar(100) NOT NULL, 
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL 
);

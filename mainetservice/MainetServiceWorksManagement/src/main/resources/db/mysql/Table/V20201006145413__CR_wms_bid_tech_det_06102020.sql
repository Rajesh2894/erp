--liquibase formatted sql
--changeset Kanchan:V20201006145413__CR_wms_bid_tech_det_06102020.sql
DROP TABLE IF EXISTS tb_wms_bid_tech_det;
--liquibase formatted sql
--changeset Kanchan:V20201006145413__CR_wms_bid_tech_det_061020201.sql
create table tb_wms_bid_tech_det	 (
tech_bid_id	bigInt(12) NOT NULL Primary key,
param_desc_id	bigInt(12) NOT NULL,
mark	bigInt(12) NOT NULL,
obtained	bigInt(12) NOT NULL,
weightage	decimal(3,2) NOT NULL,
final_marks	bigInt(12) NOT NULL,
bid_Id	bigInt(12) NOT NULL ,
ORGID bigint(12) NOT NULL ,
 CREATED_BY bigint(12) NOT NULL ,
 CREATED_DATE datetime NOT NULL,
 UPDATED_BY bigint(12) DEFAULT NULL ,
 UPDATED_DATE datetime DEFAULT NULL,
 LG_IP_MAC varchar(100) NOT NULL, 
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL ,
  CONSTRAINT FK_bid_Id FOREIGN KEY (bid_Id) REFERENCES tb_wms_bid_master (bid_Id) ON DELETE NO ACTION ON UPDATE NO ACTION
  );

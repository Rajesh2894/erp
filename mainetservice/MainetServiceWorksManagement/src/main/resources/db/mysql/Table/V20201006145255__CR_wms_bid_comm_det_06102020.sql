--liquibase formatted sql
--changeset Kanchan:V20201006145255__CR_wms_bid_comm_det_06102020.sql
DROP TABLE IF EXISTS tb_wms_bid_comm_det;
--liquibase formatted sql
--changeset Kanchan:V20201006145255__CR_wms_bid_comm_det_061020201.sql  
create table tb_wms_bid_comm_det	 (
comm_bid_id	bigInt(12) NOT NULL Primary key,
param_desc_id	bigInt(12) NOT NULL,
base_rate_per_tend	bigInt(12) NOT NULL,
quoted_price	decimal(15,2) NOT NULL,
mark	bigInt(12) NOT NULL,
obtained	bigInt(12) NOT NULL,
weigtage	decimal(3,2) NOT NULL,
final_mark	bigInt(12) NOT NULL,
bid_Id	bigInt(12) NOT NULL,
ORGID bigint(12) NOT NULL,
 CREATED_BY bigint(12) NOT NULL ,
 CREATED_DATE datetime NOT NULL,
 UPDATED_BY bigint(12) DEFAULT NULL ,
 UPDATED_DATE datetime DEFAULT NULL,
 LG_IP_MAC varchar(100) NOT NULL, 
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
CONSTRAINT FK_bid_1_Id FOREIGN KEY (bid_Id) REFERENCES tb_wms_bid_master (bid_Id) ON DELETE NO ACTION ON UPDATE NO ACTION
  );

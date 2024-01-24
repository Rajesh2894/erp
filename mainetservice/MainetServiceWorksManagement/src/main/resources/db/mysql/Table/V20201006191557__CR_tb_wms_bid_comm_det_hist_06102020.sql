--liquibase formatted sql
--changeset Kanchan:V20201006191557__CR_tb_wms_bid_comm_det_hist_06102020.sql
drop table if exists tb_wms_bid_comm_det_hist;
--liquibase formatted sql
--changeset Kanchan:V20201006191557__CR_tb_wms_bid_comm_det_hist_061020201.sql
CREATE TABLE tb_wms_bid_comm_det_hist(
  comm_bid_id_h bigint(12) NOT NULL,
  comm_bid_id bigint(12) NOT NULL,
  param_desc_id bigint(12) NOT NULL,
  base_rate_per_tend bigint(12) NOT NULL,
  quoted_price decimal(15,2) NOT NULL,
  mark bigint(12) NOT NULL,
  obtained bigint(12) NOT NULL,
  weigtage decimal(3,2) NOT NULL,
  final_mark bigint(12) NOT NULL,
  bid_Id bigint(12) NOT NULL,
  ORGID bigint(12) NOT NULL,
  CREATED_BY bigint(12) NOT NULL,
  CREATED_DATE datetime NOT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) NOT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
   H_STATUS char(1) DEFAULT NULL,
  PRIMARY KEY (comm_bid_id_h)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--liquibase formatted sql
--changeset Kanchan:V20201006191557__CR_tb_wms_bid_comm_det_hist_061020202.sql
drop table if exists tb_wms_bid_master_hist;
--liquibase formatted sql
--changeset Kanchan:V20201006191557__CR_tb_wms_bid_comm_det_hist_061020203.sql
CREATE TABLE tb_wms_bid_master_hist (
  bid_Id_h bigint(12) NOT NULL,
  bid_Id bigint(12) NOT NULL,
  vendor_id bigint(12) NOT NULL,
  bid_id_desc varchar(50) NOT NULL,
  overall_tender_score bigint(12) NOT NULL,
  overall_comm_score bigint(12) NOT NULL,
  status varchar(1) NOT NULL,
  bid_type varchar(20) NOT NULL,
  ORGID bigint(12) NOT NULL,
  CREATED_BY bigint(12) NOT NULL,
  CREATED_DATE datetime NOT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) NOT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
   H_STATUS char(1) DEFAULT NULL,
  PRIMARY KEY (bid_Id_h)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--liquibase formatted sql
--changeset Kanchan:V20201006191557__CR_tb_wms_bid_comm_det_hist_061020204.sql
drop table if exists tb_wms_bid_tech_det_hist;
--liquibase formatted sql
--changeset Kanchan:V20201006191557__CR_tb_wms_bid_comm_det_hist_061020205.sql
CREATE TABLE tb_wms_bid_tech_det_hist (
  tech_bid_id_h bigint(12) NOT NULL,
  tech_bid_id bigint(12) NOT NULL,
  param_desc_id bigint(12) NOT NULL,
  mark bigint(12) NOT NULL,
  obtained bigint(12) NOT NULL,
  weightage decimal(3,2) NOT NULL,
  final_marks bigint(12) NOT NULL,
  bid_Id bigint(12) NOT NULL,
  ORGID bigint(12) NOT NULL,
  CREATED_BY bigint(12) NOT NULL,
  CREATED_DATE datetime NOT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) NOT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
   H_STATUS char(1) DEFAULT NULL,
  PRIMARY KEY (tech_bid_id_h)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

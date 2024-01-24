--liquibase formatted sql
--changeset Kanchan:V20201029194251__AL_tb_wms_bid_tech_det_29102020.sql
 alter table tb_wms_bid_tech_det modify column param_desc_id varchar(50);
--liquibase formatted sql
--changeset Kanchan:V20201029194251__AL_tb_wms_bid_tech_det_291020201.sql
 alter table tb_wms_bid_comm_det modify column  param_desc_id varchar(50);
--liquibase formatted sql
--changeset Kanchan:V20201029194251__AL_tb_wms_bid_tech_det_291020202.sql
alter table tb_wms_bid_comm_det_hist modify column  param_desc_id  varchar(50);

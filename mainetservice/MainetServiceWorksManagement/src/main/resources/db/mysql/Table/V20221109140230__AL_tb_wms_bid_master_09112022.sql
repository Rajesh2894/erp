--liquibase formatted sql
--changeset Kanchan:V20221109140230__AL_tb_wms_bid_master_09112022.sql
alter table  tb_wms_bid_master
add column projectid bigint(12) NULL DEFAULT NULL,
add column TND_NO varchar(40) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20221109140230__AL_tb_wms_bid_master_091120221.sql
alter table  tb_wms_bid_master
modify column bid_id_desc varchar(50) NULL DEFAULT NULL,
modify column bid_type varchar(20) NULL DEFAULT NULL,
modify column TND_ID bigint(12) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20221109140230__AL_tb_wms_bid_master_091120222.sql
ALTER TABLE tb_wms_bid_master
CHANGE column overall_comm_score financestatus varchar(1) NULL DEFAULT NULL,
CHANGE column overall_tender_score rank varchar(1) NULL DEFAULT NULL,
CHANGE column status tenderstatus  bigint(12) NULL DEFAULT NULL;
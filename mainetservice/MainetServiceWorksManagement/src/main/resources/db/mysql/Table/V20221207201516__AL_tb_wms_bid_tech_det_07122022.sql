--liquibase formatted sql
--changeset Kanchan:V20221207201516__AL_tb_wms_bid_tech_det_07122022.sql
alter table tb_wms_bid_tech_det
add column EVALUATION varchar(50) NULL DEFAULT NULL,
add column CRITERIA varchar(10) NULL DEFAULT NULL,
add column ACCEPTREJECT varchar(10) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20221207201516__AL_tb_wms_bid_tech_det_071220221.sql
alter table tb_wms_bid_tech_det
modify column mark bigint(20) NULL DEFAULT NULL,
modify column obtained bigint(20) NULL DEFAULT NULL,
modify column weightage decimal(5,2) NULL DEFAULT NULL,
modify column final_marks bigint(20) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20221207201516__AL_tb_wms_bid_tech_det_071220222.sql
alter table tb_wms_bid_comm_det
add column TENDER_TYPE bigint(20) NULL DEFAULT NULL,
add column PERCENT_TYPE bigint(20) NULL DEFAULT NULL,
add column PERCENT_VALUE bigint(20) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20221207201516__AL_tb_wms_bid_tech_det_071220223.sql
alter table tb_wms_bid_comm_det
modify column base_rate_per_tend bigint(20) NULL DEFAULT NULL,
modify column mark bigint(20) NULL DEFAULT NULL,
modify column obtained bigint(20) NULL DEFAULT NULL,
modify column weigtage decimal(5,2) NULL DEFAULT NULL,
modify column final_mark bigint(20) NULL DEFAULT NULL;
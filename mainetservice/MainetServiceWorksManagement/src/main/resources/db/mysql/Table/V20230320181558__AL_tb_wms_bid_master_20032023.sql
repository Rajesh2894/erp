--liquibase formatted sql
--changeset Kanchan:V20230320181558__AL_tb_wms_bid_master_20032023.sql
alter table tb_wms_bid_master modify column vendor_id bigint(20) null default null;
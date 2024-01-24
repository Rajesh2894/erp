--liquibase formatted sql
--changeset Kanchan:V20230303104240__AL_tb_wms_bid_master_03032023.sql
alter table tb_wms_bid_master add COLUMN bid_NO VARCHAR(500) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230303104240__AL_tb_wms_bid_master_030320231.sql
alter table tb_wms_bid_master modify COLUMN bid_NO VARCHAR(500) null default null;
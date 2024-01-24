--liquibase formatted sql
--changeset PramodPatil:V20231205141313__AL_tb_wms_bid_comm_det_05122023.sql
alter table tb_wms_bid_comm_det add column FINAN_FLAG char(1) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231205141313__AL_tb_wms_bid_comm_det_051220231.sql
alter table tb_wms_bid_tech_det add column TECH_FLAG char(1) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231205141313__AL_tb_wms_bid_comm_det_051220232.sql
ALTER TABLE tb_wms_bid_comm_det MODIFY COLUMN PERCENT_VALUE Decimal(10,2);

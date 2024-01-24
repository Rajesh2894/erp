--liquibase formatted sql
--changeset Kanchan:V20201110172836__AL_tb_wms_bid_tech_det_hist_10112020.sql
alter table tb_wms_bid_tech_det_hist modify param_desc_id varchar(50);



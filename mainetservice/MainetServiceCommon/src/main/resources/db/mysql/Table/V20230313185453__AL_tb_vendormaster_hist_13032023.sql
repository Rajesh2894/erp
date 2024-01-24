--liquibase formatted sql
--changeset Kanchan:V20230313185453__AL_tb_vendormaster_hist_13032023.sql
ALTER TABLE tb_vendormaster_hist  Add ADD_MOBILE_NO varchar(20) null default null;
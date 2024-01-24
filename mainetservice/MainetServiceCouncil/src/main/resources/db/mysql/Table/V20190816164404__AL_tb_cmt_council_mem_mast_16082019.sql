--liquibase formatted sql
--changeset Anil:V20190816164404__AL_tb_cmt_council_mem_mast_16082019.sql
ALTER TABLE tb_cmt_council_mem_mast ADD COLUMN PARTY_NAME VARCHAR(250) NULL AFTER lg_ip_mac_upd;

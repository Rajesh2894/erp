--liquibase formatted sql
--changeset Anil:V20200110103132__AL_tb_ast_requisition_10012020.sql
ALTER TABLE tb_ast_requisition ADD COLUMN ORGID BIGINT(12) NOT NULL AFTER LG_IP_MAC_UPD;

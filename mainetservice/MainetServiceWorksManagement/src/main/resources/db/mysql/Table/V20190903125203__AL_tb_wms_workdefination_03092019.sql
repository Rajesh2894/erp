--liquibase formatted sql
--changeset Anil:V20190903125203__AL_tb_wms_workdefination_03092019.sql
ALTER TABLE tb_wms_workdefination ADD COLUMN PROPOSAL_NO varchar(50) NULL AFTER LG_IP_MAC_UPD;

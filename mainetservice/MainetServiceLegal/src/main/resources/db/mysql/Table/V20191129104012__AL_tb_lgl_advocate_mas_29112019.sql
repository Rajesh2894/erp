--liquibase formatted sql
--changeset Anil:V20191129104012__AL_tb_lgl_advocate_mas_29112019.sql
ALTER TABLE tb_lgl_advocate_mas 
CHANGE COLUMN adv_gen adv_gen BIGINT(12) NULL COMMENT 'Gender' ,
CHANGE COLUMN adv_dob adv_dob DATE NULL COMMENT 'Date of Birth' ,
ADD COLUMN adv_barCouncilNo VARCHAR(16) NULL AFTER lg_ip_mac_upd,
ADD COLUMN adv_courtNameId BIGINT(12) NULL AFTER adv_barCouncilNo,
ADD COLUMN adv_advocateTypeId BIGINT(12) NULL AFTER adv_courtNameId;


--liquibase formatted sql
--changeset priya:V20180201121514__AL_tb_eip_sub_link_fields_dtl_31012018_portal.sql
ALTER TABLE tb_eip_sub_link_fields_dtl 
ADD COLUMN CHEKER_FLAG CHAR(1) NULL COMMENT 'Checker Flag' AFTER ATT_VIDEO_PATH;



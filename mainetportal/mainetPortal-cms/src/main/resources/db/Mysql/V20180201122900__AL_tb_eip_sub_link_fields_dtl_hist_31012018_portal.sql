--liquibase formatted sql
--changeset priya:V20180201122900__AL_tb_eip_sub_link_fields_dtl_hist_31012018_portal.sql
ALTER TABLE tb_eip_sub_link_fields_dtl_hist
ADD COLUMN CHEKER_FLAG CHAR(1) NULL COMMENT 'Checker Flag' AFTER H_STATUS;
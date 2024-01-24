--liquibase formatted sql
--changeset priya:V20180222193440__AL_tb_eip_sub_links_master_hist_22022018.sql
ALTER TABLE tb_eip_sub_links_master_hist 
ADD COLUMN CHEKER_FLAG VARCHAR(1) NULL DEFAULT NULL COMMENT '' AFTER IS_STATUS;

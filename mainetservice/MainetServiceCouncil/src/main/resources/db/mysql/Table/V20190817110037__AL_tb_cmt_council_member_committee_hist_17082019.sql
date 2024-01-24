--liquibase formatted sql
--changeset Anil:V20190817110037__AL_tb_cmt_council_member_committee_hist_17082019.sql
ALTER TABLE tb_cmt_council_member_committee_hist ADD COLUMN COMMITTEE_DSG_ID BIGINT(12) NULL AFTER STATUS;

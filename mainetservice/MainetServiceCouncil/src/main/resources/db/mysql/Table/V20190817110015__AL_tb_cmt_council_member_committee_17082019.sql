--liquibase formatted sql
--changeset Anil:V20190817110015__AL_tb_cmt_council_member_committee_17082019.sql
ALTER TABLE tb_cmt_council_member_committee
ADD COLUMN COMMITTEE_DSG_ID BIGINT(12) NOT NULL AFTER STATUS;

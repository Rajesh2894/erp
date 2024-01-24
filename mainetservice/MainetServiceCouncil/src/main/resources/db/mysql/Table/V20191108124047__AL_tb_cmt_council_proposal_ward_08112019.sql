--liquibase formatted sql
--changeset Anil:V20191108124047__AL_tb_cmt_council_proposal_ward_08112019.sql
ALTER TABLE tb_cmt_council_proposal_ward CHANGE COLUMN WARD_ID WARD_ID BIGINT(12) NULL COMMENT 'WARD ID';

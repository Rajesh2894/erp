--liquibase formatted sql
--changeset Anil:V20190828110713__AL_tb_cmt_council_proposal_mast_28082019.sql
ALTER TABLE tb_cmt_council_proposal_mast CHANGE COLUMN PROPOSAL_AMOUNT PROPOSAL_AMOUNT INT(15) NULL COMMENT 'Proposal Amount';

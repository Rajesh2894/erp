--liquibase formatted sql
--changeset Anil:V20190817150448__AL_tb_cmt_council_proposal_mast_hist_17082019.sql
ALTER TABLE tb_cmt_council_proposal_mast_hist ADD COLUMN ISMOMPROPOSAL VARCHAR(1) NULL AFTER BUDGET_HEAD_DESC;

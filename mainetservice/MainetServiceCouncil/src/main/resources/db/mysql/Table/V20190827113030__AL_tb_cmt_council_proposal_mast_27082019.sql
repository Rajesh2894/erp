--liquibase formatted sql
--changeset Anil:V20190827113030__AL_tb_cmt_council_proposal_mast_27082019.sql
ALTER TABLE tb_cmt_council_proposal_mast 
ADD COLUMN PROPOSAL_SOURCE BIGINT(12) NULL AFTER ISMOMPROPOSAL,
ADD COLUMN PROPOSAL_TYPE CHAR(1) NULL AFTER PROPOSAL_SOURCE;
--liquibase formatted sql
--changeset Anil:V20190827113030__AL_tb_cmt_council_proposal_mast_270820191.sql
ALTER TABLE tb_cmt_council_proposal_mast_hist 
ADD COLUMN PROPOSAL_SOURCE BIGINT(12) NULL AFTER ISMOMPROPOSAL,
ADD COLUMN PROPOSAL_TYPE CHAR(1) NULL AFTER PROPOSAL_SOURCE;


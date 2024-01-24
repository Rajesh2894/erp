--liquibase formatted sql
--changeset Anil:V20191108124038__AL_tb_cmt_council_proposal_mast_08112019.sql
ALTER TABLE tb_cmt_council_proposal_mast ADD COLUMN purpose_remark VARCHAR(200) NULL AFTER PROPOSAL_TYPE;
--liquibase formatted sql
--changeset Anil:V20191108124038__AL_tb_cmt_council_proposal_mast_081120191.sql
ALTER TABLE tb_cmt_council_proposal_mast_hist ADD COLUMN purpose_remark VARCHAR(200) NULL AFTER PROPOSAL_TYPE;

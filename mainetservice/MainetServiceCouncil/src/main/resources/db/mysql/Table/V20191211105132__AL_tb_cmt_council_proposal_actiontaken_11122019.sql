--liquibase formatted sql
--changeset Anil:V20191211105132__AL_tb_cmt_council_proposal_actiontaken_11122019.sql
ALTER TABLE tb_cmt_council_proposal_actiontaken 
ADD COLUMN PROPOSAL_ID BIGINT(12) NOT NULL AFTER LG_IP_MAC_UPD,
ADD INDEX fk_tb_cmt_council_proposal_actiontaken_1_idx(PROPOSAL_ID);
--liquibase formatted sql
--changeset Anil:V20191211105132__AL_tb_cmt_council_proposal_actiontaken_111220191.sql
ALTER TABLE tb_cmt_council_proposal_actiontaken
ADD CONSTRAINT fk_tb_cmt_council_proposal_actiontaken_1 FOREIGN KEY(PROPOSAL_ID) REFERENCES tb_cmt_council_proposal_mast(PROPOSAL_ID) ON DELETE NO ACTION ON UPDATE NO ACTION;

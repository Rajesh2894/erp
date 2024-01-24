--liquibase formatted sql
--changeset PramodPatil: V20230724174422__AL_TB_CMT_COUNCIL_PROPOSAL_MAST_24072023.sql 
ALTER TABLE TB_CMT_COUNCIL_PROPOSAL_MAST MODIFY purpose_remark VARCHAR(3000) null default null;

--liquibase formatted sql
--changeset PramodPatil: V20230724174422__AL_TB_CMT_COUNCIL_PROPOSAL_MAST_240720231.sql 
ALTER TABLE TB_CMT_COUNCIL_PROPOSAL_MAST_HIST MODIFY purpose_remark VARCHAR(3000) null default null;
--liquibase formatted sql
--changeset PramodPatil:V20231214095423__AL_TB_CMT_COUNCIL_PROPOSAL_MAST_HIST_14122023.sql
alter table TB_CMT_COUNCIL_PROPOSAL_MAST_HIST add column FILED_ID bigint(12) null default null;

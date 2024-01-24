--liquibase formatted sql
--changeset Anil:V20190806120037__AL_TB_CMT_COUNCIL_PROPOSAL_MAST_HIST_06082019.sql
alter table TB_CMT_COUNCIL_PROPOSAL_MAST_HIST add column YEAR_ID  bigint(10) DEFAULT NULL,
add column SAC_HEAD_ID  bigint(12) DEFAULT NULL ,
add column BUDGET_HEAD_DESC varchar(100) DEFAULT NULL;

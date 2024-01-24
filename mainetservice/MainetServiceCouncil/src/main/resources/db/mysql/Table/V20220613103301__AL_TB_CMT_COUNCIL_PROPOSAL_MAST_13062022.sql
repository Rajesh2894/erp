--liquibase formatted sql
--changeset Kanchan:V20220613103301__AL_TB_CMT_COUNCIL_PROPOSAL_MAST_13062022.sql
alter table TB_CMT_COUNCIL_PROPOSAL_MAST add column FUND_ID bigint(12) null default null;

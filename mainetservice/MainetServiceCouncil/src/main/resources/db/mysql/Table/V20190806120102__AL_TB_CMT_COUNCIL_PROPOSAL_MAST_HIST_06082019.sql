--liquibase formatted sql
--changeset Anil:V20190806120102__AL_TB_CMT_COUNCIL_PROPOSAL_MAST_HIST_06082019.sql
alter table TB_CMT_COUNCIL_PROPOSAL_MAST_HIST add column UPDATED_BY char(4) default null;

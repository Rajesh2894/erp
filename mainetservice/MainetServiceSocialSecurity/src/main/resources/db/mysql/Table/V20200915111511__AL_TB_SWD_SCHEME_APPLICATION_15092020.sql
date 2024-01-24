--liquibase formatted sql
--changeset Anil:V20200915111511__AL_TB_SWD_SCHEME_APPLICATION_15092020.sql
alter table TB_SWD_SCHEME_APPLICATION add column ELECTION_NO varchar(50)  NULL;

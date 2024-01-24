--liquibase formatted sql
--changeset Anil:V20200915111522__AL_TB_SWD_SCHEME_CONFIGURATION_15092020.sql
alter table TB_SWD_SCHEME_CONFIGURATION add column TYPE_OF_SCH int(12)  NULL;
--liquibase formatted sql
--changeset Anil:V20200915111522__AL_TB_SWD_SCHEME_CONFIGURATION_150920201.sql
alter table TB_SWD_SCHEME_CONFIGURATION_HIST add column TYPE_OF_SCH int(12)  NULL;


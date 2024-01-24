--liquibase formatted sql
--changeset Kanchan:V20230210192516__AL_TB_WMS_PROJECT_MAST_10022023.sql
alter table TB_WMS_PROJECT_MAST modify column PROJ_CODE varchar(20) NOT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230210192516__AL_TB_WMS_PROJECT_MAST_100220231.sql
alter table TB_WMS_TENDER_WORK add column PR_ID bigint(12) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230210192516__AL_TB_WMS_PROJECT_MAST_100220232.sql
alter table TB_WMS_TENDER_WORK add column  DISPOSAL_ID bigint(12) Null default null;
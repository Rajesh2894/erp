--liquibase formatted sql
--changeset Kanchan:V20230220172210__AL_TB_SFAC_MEETING_MASTER_20022023.sql
alter table TB_SFAC_MEETING_MASTER add column SUGGESTIONS Varchar(500) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230220172210__AL_TB_SFAC_MEETING_MASTER_200220231.sql
alter table TB_SFAC_MEETING_DET add column COM_MEM_ID bigint(20) Null default null;
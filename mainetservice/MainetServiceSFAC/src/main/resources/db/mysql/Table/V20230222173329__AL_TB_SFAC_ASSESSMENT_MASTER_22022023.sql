--liquibase formatted sql
--changeset Kanchan:V20230222173329__AL_TB_SFAC_ASSESSMENT_MASTER_22022023.sql
Alter table TB_SFAC_ASSESSMENT_MASTER change column IA_ID CBBO_ID bigint(20) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230222173329__AL_TB_SFAC_ASSESSMENT_MASTER_220220231.sql
Alter table TB_SFAC_ASSESSMENT_MASTER change column IA_NAME CBBO_NAME varchar(200) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230222204416__AL_TB_SFAC_ASSESSMENT_MAST_HIST_22022023.sql
Alter table TB_SFAC_ASSESSMENT_MAST_HIST change column IA_ID CBBO_ID bigint(20) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230222204416__AL_TB_SFAC_ASSESSMENT_MAST_HIST_220220231.sql
Alter table TB_SFAC_ASSESSMENT_MAST_HIST change column IA_NAME CBBO_NAME varchar(200) Null default null;

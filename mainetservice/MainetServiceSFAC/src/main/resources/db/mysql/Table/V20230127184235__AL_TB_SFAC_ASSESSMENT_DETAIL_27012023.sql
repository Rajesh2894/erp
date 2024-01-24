--liquibase formatted sql
--changeset Kanchan:V20230127184235__AL_TB_SFAC_ASSESSMENT_DETAIL_27012023.sql
Alter table TB_SFAC_ASSESSMENT_DETAIL drop column KEY_PARAMETER, drop column WEIGHTAGE;
--liquibase formatted sql
--changeset Kanchan:V20230127184235__AL_TB_SFAC_ASSESSMENT_DETAIL_270120231.sql
Alter table TB_SFAC_ASSESSMENT_DET_HIST drop column KEY_PARAMETER, drop column WEIGHTAGE;
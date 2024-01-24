--liquibase formatted sql
--changeset Kanchan:V20230130104414__AL_TB_SFAC_ASSESSMENT_DETAIL_30012023.sql
Alter table TB_SFAC_ASSESSMENT_DETAIL drop column CBBO_ID;
--liquibase formatted sql
--changeset Kanchan:V20230130104414__AL_TB_SFAC_ASSESSMENT_DETAIL_300120231.sql
Alter table TB_SFAC_ASSESSMENT_DET_HIST drop column CBBO_ID;
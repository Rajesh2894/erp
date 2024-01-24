--liquibase formatted sql
--changeset Kanchan:V20230202175236__AL_TB_SFAC_ASSESSMENT_MASTER_02022023.sql
Alter table TB_SFAC_ASSESSMENT_MASTER drop column KEY_PARAMETER,drop column KEY_PARAMETER_DESC ,drop column WEIGHTAGE;
--liquibase formatted sql
--changeset Kanchan:V20230202175236__AL_TB_SFAC_ASSESSMENT_MASTER_020220231.sql
Alter table TB_SFAC_ASSESSMENT_MAST_HIST drop column KEY_PARAMETER,drop column KEY_PARAMETER_DESC ,drop column WEIGHTAGE;
--liquibase formatted sql
--changeset Kanchan:V20230202175236__AL_TB_SFAC_ASSESSMENT_MASTER_020220232.sql
drop table TB_SFAC_ASSESSMENT_DETAIL;
--liquibase formatted sql
--changeset Kanchan:V20230202175236__AL_TB_SFAC_ASSESSMENT_MASTER_020220233.sql
drop table TB_SFAC_ASSESSMENT_DET_HIST;
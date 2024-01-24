--liquibase formatted sql
--changeset Kanchan:V20230210195102__AL_TB_SFAC_ASSESSMENT_SUB_PARAM_DET_10022023.sql
alter table TB_SFAC_ASSESSMENT_SUB_PARAM_DET modify column SUB_WEIGHTAGE decimal(15,2) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230210195102__AL_TB_SFAC_ASSESSMENT_SUB_PARAM_DET_100220231.sql
alter table TB_SFAC_ASSESSMENT_SUB_PARAM_DET_HIST modify column SUB_WEIGHTAGE decimal(15,2) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230210195102__AL_TB_SFAC_ASSESSMENT_SUB_PARAM_DET_100220232.sql
alter table TB_SFAC_ASSESSMENT_KEY_PARAM modify column WEIGHTAGE decimal(15,2) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230210195102__AL_TB_SFAC_ASSESSMENT_SUB_PARAM_DET_100220233.sql
alter table TB_SFAC_ASSESSMENT_KEY_PARAM_HIST modify column WEIGHTAGE decimal(15,2) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230210195102__AL_TB_SFAC_ASSESSMENT_SUB_PARAM_DET_100220234.sql
alter table Tb_SFAC_Cbbo_Master  add column FPO_ALLOCATION_TARGET bigint(20) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230210195102__AL_TB_SFAC_ASSESSMENT_SUB_PARAM_DET_100220235.sql
alter table TB_SFAC_CBBO_MAST_HIST  add column FPO_ALLOCATION_TARGET bigint(20) Null default null;
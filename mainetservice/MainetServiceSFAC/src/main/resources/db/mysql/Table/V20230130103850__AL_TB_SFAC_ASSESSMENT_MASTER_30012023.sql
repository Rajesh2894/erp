--liquibase formatted sql
--changeset Kanchan:V20230130103850__AL_TB_SFAC_ASSESSMENT_MASTER_30012023.sql
Alter table TB_SFAC_ASSESSMENT_MASTER
add column IA_ID bigint(20) Null default null,
add column IA_NAME varchar(200) Null default null,
add column FIN_YR_ID bigint(20) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230130103850__AL_TB_SFAC_ASSESSMENT_MASTER_300120231.sql
Alter table TB_SFAC_ASSESSMENT_MAST_HIST
add column IA_ID bigint(20) Null default null,
add column IA_NAME varchar(200) Null default null,
add column FIN_YR_ID bigint(20) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230130103850__AL_TB_SFAC_ASSESSMENT_MASTER_300120232.sql
Alter table TB_SFAC_ASSESSMENT_MAST_HIST add column H_STATUS char(1) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230130103850__AL_TB_SFAC_ASSESSMENT_MASTER_300120233.sql
Alter table TB_SFAC_ASSESSMENT_DET_HIST add column H_STATUS char(1) Null default null;
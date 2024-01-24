--liquibase formatted sql
--changeset Kanchan:V20230220121342__AL_Tb_SFAC_Fpo_Master_20022023.sql
alter table Tb_SFAC_Fpo_Master change column APPROVED   APPROVED_BY_IA bigint(20) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230220121342__AL_Tb_SFAC_Fpo_Master_200220231.sql
alter table Tb_SFAC_Fpo_Master add column  APPROVED_BY_CBBO bigint(20),add APPROVED_BY_FPO  bigint (20) Null default null;
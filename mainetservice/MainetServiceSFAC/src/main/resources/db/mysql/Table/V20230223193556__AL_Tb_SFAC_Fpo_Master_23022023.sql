--liquibase formatted sql
--changeset Kanchan:V20230223193556__AL_Tb_SFAC_Fpo_Master_23022023.sql
alter table   Tb_SFAC_Fpo_Master add column  REMARK varchar(150) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230223193556__AL_Tb_SFAC_Fpo_Master_230220231.sql
alter table  TB_SFAC_FPO_MASTER_HIST add column REMARK varchar(150) Null default null;
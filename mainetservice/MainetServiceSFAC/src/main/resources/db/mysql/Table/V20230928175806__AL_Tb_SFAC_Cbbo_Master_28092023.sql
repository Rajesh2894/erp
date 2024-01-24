--liquibase formatted sql
--changeset PramodPatil:V20230928175806__AL_Tb_SFAC_Cbbo_Master_28092023.sql
Alter table Tb_SFAC_Cbbo_Master add column ACTIVE_REMARK varchar(1000) Null default null;

--liquibase formatted sql
--changeset PramodPatil:V20230928175806__AL_Tb_SFAC_Cbbo_Master_280920231.sql
Alter table TB_SFAC_CBBO_MAST_HIST add column ACTIVE_REMARK varchar(1000) Null default null;

--liquibase formatted sql
--changeset PramodPatil:V20230928175806__AL_Tb_SFAC_Cbbo_Master_280920232.sql
Alter table Tb_SFAC_Fpo_Master add column ACTIVE_REMARK varchar(1000) Null default null;

--liquibase formatted sql
--changeset PramodPatil:V20230928175806__AL_Tb_SFAC_Cbbo_Master_280920233.sql
Alter table TB_SFAC_FPO_MASTER_HIST add column ACTIVE_REMARK varchar(1000) Null default null;

--liquibase formatted sql
--changeset PramodPatil:V20230928175806__AL_Tb_SFAC_Cbbo_Master_280920234.sql
Alter table Tb_SFAC_Farmer_Mast add column ACTIVE_REMARK varchar(1000) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230710181906__AL_Tb_SFAC_Fpo_Master_10072023.sql
Alter table Tb_SFAC_Fpo_Master
add column CHANGE_REQUEST varchar(10) null default null,
add column CHANGE_REMARK varchar(100)  null default null;
--liquibase formatted sql
--changeset Kanchan:V20230710181906__AL_Tb_SFAC_Fpo_Master_100720231.sql
Alter table TB_SFAC_FPO_MASTER_HIST
add column CHANGE_REQUEST varchar(10) null default null,
add column CHANGE_REMARK varchar(100)  null default null;
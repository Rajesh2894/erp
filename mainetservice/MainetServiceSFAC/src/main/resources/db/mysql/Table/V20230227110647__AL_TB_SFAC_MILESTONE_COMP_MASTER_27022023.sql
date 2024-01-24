--liquibase formatted sql
--changeset Kanchan:V20230227110647__AL_TB_SFAC_MILESTONE_COMP_MASTER_27022023.sql
Alter table TB_SFAC_MILESTONE_COMP_MASTER
ADD Column APP_NO bigint(20) not null,
ADD Column STATUS varchar(20) null default null,
ADD Column AUTH_REMARK varchar(500) null default null;
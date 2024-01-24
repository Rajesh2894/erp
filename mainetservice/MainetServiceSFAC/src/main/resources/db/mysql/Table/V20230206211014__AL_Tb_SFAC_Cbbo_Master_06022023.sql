--liquibase formatted sql
--changeset Kanchan:V20230206211014__AL_Tb_SFAC_Cbbo_Master_06022023.sql
alter table Tb_SFAC_Cbbo_Master change column DMC_APPROVAL  APPROVED char(1) null default null;
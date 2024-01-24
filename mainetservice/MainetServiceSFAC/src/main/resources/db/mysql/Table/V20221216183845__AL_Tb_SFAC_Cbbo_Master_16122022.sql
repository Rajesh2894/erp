--liquibase formatted sql
--changeset Kanchan:V20221216183845__AL_Tb_SFAC_Cbbo_Master_16122022.sql
Alter table Tb_SFAC_Cbbo_Master add column DEPTID bigint(20) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20221216183845__AL_Tb_SFAC_Cbbo_Master_161220221.sql
Alter table Tb_SFAC_Cbbo_Master add column CBBO_APPOINTMENT_YEAR bigint(20) Null default null;
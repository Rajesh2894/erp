--liquibase formatted sql
--changeset Anil:V20190718193552__AL_TB_EIP_PROFILE_MASTER_18072019.sql
alter table TB_EIP_PROFILE_MASTER add column REMARK Varchar(1000);
--liquibase formatted sql
--changeset Anil:V20190718193552__AL_TB_EIP_PROFILE_MASTER_180720191.sql
alter table TB_EIP_PROFILE_MASTER_HIST add column REMARK Varchar(1000);

--liquibase formatted sql
--changeset Anil:V20190718193510__AL_TB_EIP_CONTACT_US_18072019.sql
alter table TB_EIP_CONTACT_US add column REMARK Varchar(1000);
--liquibase formatted sql
--changeset Anil:V20190718193510__AL_TB_EIP_CONTACT_US_180720191.sql
alter table TB_EIP_CONTACT_US_HIST add column REMARK Varchar(1000);

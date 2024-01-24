--liquibase formatted sql
--changeset Anil:V20190718193450__AL_TB_EIP_ABOUTUS_18072019.sql
alter table TB_EIP_ABOUTUS  add column REMARK Varchar(1000);
--liquibase formatted sql
--changeset Anil:V20190718193450__AL_TB_EIP_ABOUTUS_180720191.sql
alter table TB_EIP_ABOUTUS_HIST add column REMARK Varchar(1000);

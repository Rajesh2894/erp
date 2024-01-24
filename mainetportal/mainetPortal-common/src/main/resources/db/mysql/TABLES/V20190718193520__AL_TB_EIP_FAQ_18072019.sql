--liquibase formatted sql
--changeset Anil:V20190718193520__AL_TB_EIP_FAQ_18072019.sql
alter table TB_EIP_FAQ add column REMARK Varchar(1000);
--liquibase formatted sql
--changeset Anil:V20190718193520__AL_TB_EIP_FAQ_180720191.sql
alter table TB_EIP_FAQ_HIST add column REMARK Varchar(1000);

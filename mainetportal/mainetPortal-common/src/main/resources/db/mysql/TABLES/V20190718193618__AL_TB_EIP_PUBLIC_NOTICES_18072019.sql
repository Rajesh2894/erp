--liquibase formatted sql
--changeset Anil:V20190718193618__AL_TB_EIP_PUBLIC_NOTICES_18072019.sql
alter table TB_EIP_PUBLIC_NOTICES add column REMARK Varchar(1000);
--liquibase formatted sql
--changeset Anil:V20190718193618__AL_TB_EIP_PUBLIC_NOTICES_180720191.sql
alter table TB_EIP_PUBLIC_NOTICES_HIST add column REMARK Varchar(1000);

--liquibase formatted sql
--changeset Anil:V20190718193630__AL_TB_EIP_SUB_LINK_FIELDS_DTL_18072019.sql
alter table TB_EIP_SUB_LINK_FIELDS_DTL add column REMARK Varchar(1000);
--liquibase formatted sql
--changeset Anil:V20190718193630__AL_TB_EIP_SUB_LINK_FIELDS_DTL_180720191.sql
alter table TB_EIP_SUB_LINK_FIELDS_DTL_HIST add column REMARK Varchar(1000);

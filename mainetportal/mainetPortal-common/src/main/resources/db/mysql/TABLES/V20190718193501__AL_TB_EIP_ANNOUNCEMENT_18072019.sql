--liquibase formatted sql
--changeset Anil:V20190718193501__AL_TB_EIP_ANNOUNCEMENT_18072019.sql
alter table TB_EIP_ANNOUNCEMENT add column HIGHLIGHTED_DATE datetime;
--liquibase formatted sql
--changeset Anil:V20190718193501__AL_TB_EIP_ANNOUNCEMENT_180720191.sql
alter table TB_EIP_ANNOUNCEMENT_HIST add column HIGHLIGHTED_DATE datetime;
--liquibase formatted sql
--changeset Anil:V20190718193501__AL_TB_EIP_ANNOUNCEMENT_180720192.sql
alter table TB_EIP_ANNOUNCEMENT add column REMARK Varchar(1000);
--liquibase formatted sql
--changeset Anil:V20190718193501__AL_TB_EIP_ANNOUNCEMENT_180720193.sql
alter table TB_EIP_ANNOUNCEMENT_HIST add column REMARK Varchar(1000);

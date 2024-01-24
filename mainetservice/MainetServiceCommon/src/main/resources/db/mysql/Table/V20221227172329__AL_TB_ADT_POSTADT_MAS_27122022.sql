--liquibase formatted sql
--changeset Kanchan:V20221227172329__AL_TB_ADT_POSTADT_MAS_27122022.sql
Alter table TB_ADT_POSTADT_MAS modify column AUDIT_PARA_DESC varchar(5000) not null;
--liquibase formatted sql
--changeset Kanchan:V20221227172329__AL_TB_ADT_POSTADT_MAS_271220221.sql
Alter table TB_ADT_POSTADT_MAS modify column AUDIT_WORK_NAME varchar(1000) null default null;
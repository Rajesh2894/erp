--liquibase formatted sql
--changeset Kanchan:V20201127200655__AL_TB_MRM_APPOINTMENT_27112020.sql
alter table  TB_MRM_APPOINTMENT drop column SERIAL_NO , drop column VOLUME ;
--liquibase formatted sql
--changeset Kanchan:V20201127200655__AL_TB_MRM_APPOINTMENT_271120201.sql
alter table  TB_MRM_APPOINTMENT_HIST drop column SERIAL_NO, drop column VOLUME ;
--liquibase formatted sql
--changeset Kanchan:V20201127200655__AL_TB_MRM_APPOINTMENT_271120202.sql
alter table TB_MRM_MARRIAGE add column SERIAL_NO varchar(50),add column VOLUME varchar(50)null;
--liquibase formatted sql
--changeset Kanchan:V20201127200655__AL_TB_MRM_APPOINTMENT_271120203.sql
alter table TB_MRM_MARRIAGE_HIST add column SERIAL_NO varchar(50),add column VOLUME varchar(50)null;

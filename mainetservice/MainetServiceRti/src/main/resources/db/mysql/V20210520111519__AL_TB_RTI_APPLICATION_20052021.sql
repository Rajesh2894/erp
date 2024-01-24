--liquibase formatted sql
--changeset Kanchan:V20210520111519__AL_TB_RTI_APPLICATION_20052021.sql
alter table TB_RTI_APPLICATION add column RTI_FWD_ORGID int(11) NULL

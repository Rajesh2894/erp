--liquibase formatted sql
--changeset Kanchan:V20210326141052__AL_TB_RTI_APPLICATION_26032021.sql
alter table TB_RTI_APPLICATION add column RTI_DESC  varchar(500);

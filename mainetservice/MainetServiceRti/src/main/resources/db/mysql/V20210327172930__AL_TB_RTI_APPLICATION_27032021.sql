--liquibase formatted sql
--changeset Kanchan:V20210327172930__AL_TB_RTI_APPLICATION_27032021.sql
alter table TB_RTI_APPLICATION modify column RTI_DESC varchar(3000)

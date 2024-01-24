--liquibase formatted sql
--changeset Kanchan:V20221003175911__AL_TB_ORGANISATION_03102022.sql
Alter table TB_ORGANISATION modify column O_NLS_ORGNAME varchar(100) NOT NULL;
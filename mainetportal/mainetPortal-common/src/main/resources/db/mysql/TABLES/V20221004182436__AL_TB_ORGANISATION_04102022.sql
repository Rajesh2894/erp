--liquibase formatted sql
--changeset Kanchan:V20221004182436__AL_TB_ORGANISATION_04102022.sql
Alter table TB_ORGANISATION modify column O_NLS_ORGNAME varchar(100) NOT NULL;
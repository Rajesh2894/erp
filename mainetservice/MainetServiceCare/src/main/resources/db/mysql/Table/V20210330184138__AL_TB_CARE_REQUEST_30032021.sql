--liquibase formatted sql
--changeset Kanchan:V20210330184138__AL_TB_CARE_REQUEST_30032021.sql
alter table TB_CARE_REQUEST modify column COMPLAINT_DESC varchar(3000) NOt NULL;

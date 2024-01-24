--liquibase formatted sql
--changeset Kanchan:V20230612101059__AL_tb_care_request_12062023.sql
Alter table tb_care_request modify column EXT_REFERENCE_NO varchar(300) null default null;
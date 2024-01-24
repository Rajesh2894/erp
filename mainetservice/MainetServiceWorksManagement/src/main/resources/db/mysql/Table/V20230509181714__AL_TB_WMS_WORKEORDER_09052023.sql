--liquibase formatted sql
--changeset Kanchan:V20230509181714__AL_TB_WMS_WORKEORDER_09052023.sql
alter table TB_WMS_WORKEORDER add column REMARK varchar(1000) null default null;

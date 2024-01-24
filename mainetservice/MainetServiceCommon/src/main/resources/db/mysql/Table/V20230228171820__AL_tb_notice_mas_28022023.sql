--liquibase formatted sql
--changeset Kanchan:V20230228171820__AL_tb_notice_mas_28022023.sql
alter table tb_notice_mas modify column NOT_NO varchar(40) null default null;

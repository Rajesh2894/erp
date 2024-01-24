--liquibase formatted sql
--changeset Kanchan:V20201030150139__AL_ exception_log_dtl_30102020.sql
alter table exception_log_dtl add column endpoint varchar(100) null;




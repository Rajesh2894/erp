--liquibase formatted sql
--changeset Kanchan:V20210114200600__AL_tb_exception_log_dtl_14012021.sql
alter table exception_log_dtl modify column method_name  varchar(500);

--liquibase formatted sql
--changeset Vivek:V20170329182413__TB_AC_BILL_MAS_DDL.sql
alter table TB_AC_BILL_MAS modify checker_user NUMBER(12);

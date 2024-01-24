--liquibase formatted sql
--changeset Vivek:V20170331143915__TB_AC_BILL_MAS_DDL.sql
alter table TB_AC_BILL_MAS rename column fi04_lo1 to BM_PAY_STATUS;
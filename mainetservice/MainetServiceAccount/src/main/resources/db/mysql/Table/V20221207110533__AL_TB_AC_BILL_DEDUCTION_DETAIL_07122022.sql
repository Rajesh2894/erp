--liquibase formatted sql
--changeset Kanchan:V20221207110533__AL_TB_AC_BILL_DEDUCTION_DETAIL_07122022.sql
alter table TB_AC_BILL_DEDUCTION_DETAIL
add column RA_TAXFACT bigint(12) NULL DEFAULT NULL,
add column RA_TAXPER decimal(15,2) NULL DEFAULT NULL;
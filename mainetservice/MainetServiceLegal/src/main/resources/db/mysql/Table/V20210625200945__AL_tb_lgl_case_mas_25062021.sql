--liquibase formatted sql
--changeset Kanchan:V20210625200945__AL_tb_lgl_case_mas_25062021.sql
alter table tb_lgl_case_mas  add column cse_filing_no  varchar(16),add  cse_filing_date  date Null;
--liquibase formatted sql
--changeset Kanchan:V20210625200945__AL_tb_lgl_case_mas_250620211.sql
alter table  tb_lgl_case_mas_hist  add column cse_filing_no  varchar(16),add  cse_filing_date  date Null;

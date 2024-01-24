--liquibase formatted sql
--changeset Kanchan: V20210621145740__AL_tb_lgl_case_mas_21062021.sql
alter table tb_lgl_case_mas  add column cse_filing_no  varchar(16),add  cse_filing_date  date Null;
--liquibase formatted sql
--changeset Kanchan: V20210621145740__AL_tb_lgl_case_mas_210620211.sql
alter table  tb_lgl_case_mas_hist  add column cse_filing_no  varchar(16),add  cse_filing_date  date Null;
 

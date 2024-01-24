--liquibase formatted sql
--changeset Kanchan:V20211029093511__AL_tb_loi_mas_29102021.sql
alter table tb_loi_mas add column ISSUED_BY bigint(12),add ISSUED_DATE datetime NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211029093511__AL_tb_loi_mas_291020211.sql
alter table tb_work_order  add column ISSUED_BY bigint(12),add ISSUED_DATE datetime NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211029093511__AL_tb_loi_mas_291020212.sql
alter table tb_loi_mas_hist add column ISSUED_BY bigint(12),add ISSUED_DATE datetime NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211029093511__AL_tb_loi_mas_291020213.sql
alter table  tb_work_order_hist add column ISSUED_BY bigint(12),add ISSUED_DATE datetime NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211029093511__AL_tb_loi_mas_291020214.sql
alter table  tb_cfc_application_mst_hist  add column ISSUED_BY bigint(12),add ISSUED_DATE datetime NULL DEFAULT NULL;

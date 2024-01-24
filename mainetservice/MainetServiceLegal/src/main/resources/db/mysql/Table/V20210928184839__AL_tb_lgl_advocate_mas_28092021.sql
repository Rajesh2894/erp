--liquibase formatted sql
--changeset Kanchan:V20210928184839__AL_tb_lgl_advocate_mas_28092021.sql
Alter table  tb_lgl_advocate_mas add column APM_APPLICATION_ID bigint(20) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210928184839__AL_tb_lgl_advocate_mas_280920211.sql
Alter table  tb_lgl_advocate_mas modify column  adv_appfromdate date Null;
--liquibase formatted sql
--changeset Kanchan:V20210928184839__AL_tb_lgl_advocate_mas_280920212.sql
Alter table  tb_lgl_advocate_mas_hist add column APM_APPLICATION_ID bigint(20) Null;

--liquibase formatted sql
--changeset Kanchan:V20201026193405__AL_tb_employee_hist_26102020.sql
alter table employee_hist add COLUMN OTP_BP_CHECK CHAR(1) NULL;
--liquibase formatted sql
--changeset Kanchan:V20201026193405__AL_tb_employee_hist_261020201.sql
alter table employee add COLUMN OTP_BP_CHECK CHAR(1) NULL;




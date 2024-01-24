--liquibase formatted sql
--changeset Anil:V20191107183306__AL_employee_07112019.sql
ALTER TABLE employee ADD COLUMN EMP_REASON VARCHAR(200) NULL AFTER MOB_OTP;
--liquibase formatted sql
--changeset Anil:V20191107183306__AL_employee_071120191.sql
ALTER TABLE employee_hist ADD COLUMN EMP_REASON VARCHAR(200) NULL AFTER H_STATUS;


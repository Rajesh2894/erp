--liquibase formatted sql
--changeset Kanchan:V20210927141850__AL_tb_as_bill_det_27092021.sql
alter table tb_as_bill_det
add column MN_PER_RATE decimal(15,2) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210927141850__AL_tb_as_bill_det_270920211.sql
alter table tb_as_bill_det_hist
add column MN_PER_RATE decimal(15,2) NULL DEFAULT NULL;




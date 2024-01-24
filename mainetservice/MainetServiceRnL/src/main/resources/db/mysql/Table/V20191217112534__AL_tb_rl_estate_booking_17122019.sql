--liquibase formatted sql
--changeset Anil:V20191217112534__AL_tb_rl_estate_booking_17122019.sql
ALTER TABLE tb_rl_estate_booking ADD COLUMN EBK_ULB_EMPLOYEE CHAR(1) NOT NULL AFTER EBK_CANCEL_DATE;

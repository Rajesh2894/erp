--liquibase formatted sql
--changeset Anil:V20191219172839__AL_tb_rl_estate_booking_19122019.sql
ALTER TABLE tb_rl_estate_booking CHANGE COLUMN EBK_ULB_EMPLOYEE EBK_ULB_EMPLOYEE CHAR(1) NULL;

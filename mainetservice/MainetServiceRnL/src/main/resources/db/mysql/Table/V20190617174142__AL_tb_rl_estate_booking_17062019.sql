--liquibase formatted sql
--changeset Anil:V20190617174142__AL_tb_rl_estate_booking_17062019.sql
ALTER TABLE tb_rl_estate_booking
ADD COLUMN EBK_NOINVITIES BIGINT(3) NULL AFTER `EBK_DISCOUNT_AMT`;

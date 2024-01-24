--liquibase formatted sql
--changeset Anil:V20190704161810__AL_tb_rl_estate_booking_07042019.sql
ALTER TABLE tb_rl_estate_booking CHANGE COLUMN EBK_BOOK_PURPOSE EBK_BOOK_PURPOSE BIGINT(12) NULL DEFAULT NULL AFTER EBK_BOOK_SHIFT;






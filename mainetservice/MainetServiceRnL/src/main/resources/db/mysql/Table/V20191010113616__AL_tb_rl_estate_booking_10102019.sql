--liquibase formatted sql
--changeset Anil:V20191010113616__AL_tb_rl_estate_booking_10102019.sql
ALTER TABLE tb_rl_estate_booking
ADD COLUMN EBK_CANCEL_REASON VARCHAR(500) NULL AFTER LG_IP_MAC_UPD,
ADD COLUMN EBK_CANCEL_DATE DATE NULL AFTER EBK_CANCEL_REASON;

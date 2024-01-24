--liquibase formatted sql
--changeset priya:V20180123180317__al_tb_comparent_det.sql
ALTER TABLE tb_comparent_det 
ADD COLUMN COD_OTHERS VARCHAR(60) NULL AFTER LG_IP_MAC_UPD;

--liquibase formatted sql
--changeset Anil:V20191118103453__AL_tb_ac_lnmst_18112019.sql
ALTER TABLE tb_ac_lnmst ADD COLUMN LN_NO VARCHAR(50) NOT NULL AFTER LG_IP_MAC_UPD;

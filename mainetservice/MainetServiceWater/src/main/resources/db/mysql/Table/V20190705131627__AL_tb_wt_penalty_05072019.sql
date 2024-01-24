--liquibase formatted sql
--changeset Anil:V20190705131627__AL_tb_wt_penalty_05072019.sql
ALTER TABLE tb_wt_penalty
ADD COLUMN TAX_ID BIGINT(12) NULL AFTER LG_IP_MAC_UPD;



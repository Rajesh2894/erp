--liquibase formatted sql
--changeset Anil:V20190704173834__AL_tb_wt_penalty_07042019.sql
ALTER TABLE tb_wt_penalty
CHANGE COLUMN LG_IP_MAC_UPD LG_IP_MAC_UPD VARCHAR(100) NULL DEFAULT NULL ;


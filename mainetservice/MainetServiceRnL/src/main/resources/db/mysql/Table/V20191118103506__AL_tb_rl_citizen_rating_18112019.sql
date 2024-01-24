--liquibase formatted sql
--changeset Anil:V20191118103506__AL_tb_rl_citizen_rating_18112019.sql
ALTER TABLE tb_rl_citizen_rating CHANGE COLUMN LG_IP_MAC_UPD LG_IP_MAC_UPD VARCHAR(100) NULL;

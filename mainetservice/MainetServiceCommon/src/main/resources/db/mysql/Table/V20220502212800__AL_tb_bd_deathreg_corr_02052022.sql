--liquibase formatted sql
--changeset Kanchan:V20220502212800__AL_tb_bd_deathreg_corr_02052022.sql
alter table tb_bd_deathreg_corr Add column PD_REG_UNIT_ID bigint(20) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220502212800__AL_tb_bd_deathreg_corr_020520221.sql
alter table tb_bd_deathreg_corr_history Add column PD_REG_UNIT_ID bigint(20) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220502212800__AL_tb_bd_deathreg_corr_020520222.sql
alter table TB_BD_BIRTHREG_CORR_HIST Add column PD_REG_UNIT_ID bigint(20) null default null;

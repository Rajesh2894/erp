--liquibase formatted sql
--changeset Kanchan:V20201203173704__AL_TB_MRM_MARRIAGE_HIST_03122020.sql
ALTER TABLE   TB_MRM_MARRIAGE_HIST drop INDEX MAR_ID;
--liquibase formatted sql
--changeset Kanchan:V20201203173704__AL_TB_MRM_MARRIAGE_HIST_031220201.sql
ALTER TABLE   TB_MRM_MARRIAGE_HIST drop FOREIGN KEY tb_mrm_marriage_hist_ibfk_1
--liquibase formatted sql
--changeset Kanchan:V20201203173704__AL_TB_MRM_MARRIAGE_HIST_031220202.sql
ALTER TABLE   tb_mrm_husband_hist drop FOREIGN KEY tb_mrm_husband_hist_ibfk_1;
--liquibase formatted sql
--changeset Kanchan:V20201203173704__AL_TB_MRM_MARRIAGE_HIST_031220203.sql
ALTER TABLE   tb_mrm_husband_hist drop FOREIGN KEY tb_mrm_husband_hist_ibfk_2;
--liquibase formatted sql
--changeset Kanchan:V20201203173704__AL_TB_MRM_MARRIAGE_HIST_031220204.sql
ALTER TABLE   tb_mrm_husband_hist drop INDEX HUSBAND_ID;
--liquibase formatted sql
--changeset Kanchan:V20201203173704__AL_TB_MRM_MARRIAGE_HIST_031220205.sql
ALTER TABLE   tb_mrm_husband_hist drop INDEX MAR_ID;
--liquibase formatted sql
--changeset Kanchan:V20201203173704__AL_TB_MRM_MARRIAGE_HIST_031220206.sql
ALTER TABLE   tb_mrm_wife_hist drop FOREIGN KEY tb_mrm_wife_hist_ibfk_1;
--liquibase formatted sql
--changeset Kanchan:V20201203173704__AL_TB_MRM_MARRIAGE_HIST_031220207.sql
ALTER TABLE   tb_mrm_wife_hist drop FOREIGN KEY tb_mrm_wife_hist_ibfk_2;
--liquibase formatted sql
--changeset Kanchan:V20201203173704__AL_TB_MRM_MARRIAGE_HIST_031220208.sql
ALTER TABLE   tb_mrm_wife_hist drop INDEX WIFE_ID;
--liquibase formatted sql
--changeset Kanchan:V20201203173704__AL_TB_MRM_MARRIAGE_HIST_031220209.sql
ALTER TABLE   tb_mrm_wife_hist drop INDEX MAR_ID;
--liquibase formatted sql
--changeset Kanchan:V20201203173704__AL_TB_MRM_MARRIAGE_HIST_0312202010.sql
ALTER TABLE   tb_mrm_appointment_hist drop FOREIGN KEY tb_mrm_appointment_hist_ibfk_1;
--liquibase formatted sql
--changeset Kanchan:V20201203173704__AL_TB_MRM_MARRIAGE_HIST_0312202011.sql
ALTER TABLE   tb_mrm_appointment_hist drop FOREIGN KEY tb_mrm_appointment_hist_ibfk_2;
--liquibase formatted sql
--changeset Kanchan:V20201203173704__AL_TB_MRM_MARRIAGE_HIST_0312202012.sql
ALTER TABLE   tb_mrm_appointment_hist drop INDEX APPOINTMENT_ID;
--liquibase formatted sql
--changeset Kanchan:V20201203173704__AL_TB_MRM_MARRIAGE_HIST_0312202013.sql
ALTER TABLE   tb_mrm_appointment_hist drop INDEX MAR_ID;
--liquibase formatted sql
--changeset Kanchan:V20201203173704__AL_TB_MRM_MARRIAGE_HIST_0312202014.sql
ALTER TABLE   tb_mrm_witness_det_hist drop FOREIGN KEY tb_mrm_witness_det_hist_ibfk_1;
--liquibase formatted sql
--changeset Kanchan:V20201203173704__AL_TB_MRM_MARRIAGE_HIST_0312202015.sql
ALTER TABLE   tb_mrm_witness_det_hist drop FOREIGN KEY tb_mrm_witness_det_hist_ibfk_2;
--liquibase formatted sql
--changeset Kanchan:V20201203173704__AL_TB_MRM_MARRIAGE_HIST_0312202016.sql
ALTER TABLE   tb_mrm_witness_det_hist drop INDEX WITNESS_ID;
--liquibase formatted sql
--changeset Kanchan:V20201203173704__AL_TB_MRM_MARRIAGE_HIST_0312202017.sql
ALTER TABLE   tb_mrm_witness_det_hist drop INDEX MAR_ID;




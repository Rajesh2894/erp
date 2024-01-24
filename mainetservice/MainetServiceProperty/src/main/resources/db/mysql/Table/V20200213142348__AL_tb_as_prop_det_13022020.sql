--liquibase formatted sql
--changeset Anil:V20200213142348__AL_tb_as_prop_det_13022020.sql
ALTER TABLE tb_as_prop_det ADD COLUMN PD_FASSESMENT_DATE DATE NULL AFTER PM_FA_YEARID;
--liquibase formatted sql
--changeset Anil:V20200213142348__AL_tb_as_prop_det_130220201.sql
ALTER TABLE tb_as_pro_assesment_detail ADD COLUMN PRO_FASSESMENT_DATE DATE NULL AFTER pro_FA_YEARID;
--liquibase formatted sql
--changeset Anil:V20200213142348__AL_tb_as_prop_det_130220202.sql
ALTER TABLE tb_as_pro_detail_hist ADD COLUMN PRO_FASSESMENT_DATE DATE NULL AFTER pro_FA_YEARID;
--liquibase formatted sql
--changeset Anil:V20200213142348__AL_tb_as_prop_det_130220203.sql
ALTER TABLE tb_as_assesment_detail ADD COLUMN mn_assd_fassesment_date DATE NULL AFTER MN_FA_YEARID;


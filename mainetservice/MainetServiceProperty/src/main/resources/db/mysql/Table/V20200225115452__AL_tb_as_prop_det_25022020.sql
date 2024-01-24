--liquibase formatted sql
--changeset Anil:V20200225115452__AL_tb_as_prop_det_25022020.sql
ALTER TABLE tb_as_prop_det ADD COLUMN pd_nounit BIGINT(12) NULL AFTER PD_FASSESMENT_DATE;
--liquibase formatted sql
--changeset Anil:V20200225115452__AL_tb_as_prop_det_250220201.sql
ALTER TABLE tb_as_pro_assesment_detail ADD COLUMN pro_assd_nounit BIGINT(12) NULL AFTER PRO_FASSESMENT_DATE;
--liquibase formatted sql
--changeset Anil:V20200225115452__AL_tb_as_prop_det_250220202.sql
ALTER TABLE tb_as_pro_detail_hist ADD COLUMN pro_assd_nounit BIGINT(12) NULL AFTER PRO_FASSESMENT_DATE;
--liquibase formatted sql
--changeset Anil:V20200225115452__AL_tb_as_prop_det_250220203.sql
ALTER TABLE tb_as_assesment_detail ADD COLUMN mn_assd_nounit BIGINT(12) NULL AFTER mn_assd_fassesment_date;



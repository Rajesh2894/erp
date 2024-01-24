--liquibase formatted sql
--changeset Kanchan:V20210716104822__AL_tb_as_assesment_detail_hist_16072021.sql
ALTER TABLE `tb_as_assesment_detail_hist`  ADD COLUMN `APM_APPLICATION_ID` bigint(16) NULL default NULL;
--liquibase formatted sql
--changeset Kanchan:V20210716104822__AL_tb_as_assesment_detail_hist_160720211.sql
ALTER TABLE `TB_AS_PRO_ASSESMENT_MAST`  ADD COLUMN `UNIQUE_PROPERTY_ID` VARCHAR(50) NULL default NULL;
--liquibase formatted sql
--changeset Kanchan:V20210716104822__AL_tb_as_assesment_detail_hist_160720212.sql
ALTER TABLE `tb_as_assesment_mast`  ADD COLUMN `UNIQUE_PROPERTY_ID` VARCHAR(50) NULL default NULL;
--liquibase formatted sql
--changeset Kanchan:V20210716104822__AL_tb_as_assesment_detail_hist_160720213.sql
ALTER TABLE `tb_as_prop_mas`  ADD COLUMN `UNIQUE_PROPERTY_ID` VARCHAR(50) NULL default NULL;
--liquibase formatted sql
--changeset Kanchan:V20210716104822__AL_tb_as_assesment_detail_hist_160720214.sql
ALTER TABLE `TB_AS_ASSESMENT_MAST_HIST`  ADD COLUMN `UNIQUE_PROPERTY_ID` VARCHAR(50) NULL default NULL;

--liquibase formatted sql
--changeset nilima:V20190607183616__AL_tb_wms_measurementbook_mast_07062019.sql
ALTER TABLE tb_wms_measurementbook_mast 
ADD COLUMN MB_OLDMBNO VARCHAR(50) NULL AFTER MB_TAKENDATE;

--liquibase formatted sql
--changeset nilima:V20190607183616__AL_tb_wms_measurementbook_mast_hist_07062019.sql
ALTER TABLE tb_wms_measurementbook_mast_hist 
ADD COLUMN MB_OLDMBNO VARCHAR(50) NULL AFTER MB_TAKENDATE;

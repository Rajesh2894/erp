--liquibase formatted sql
--changeset Anil:V20200827192538__AL_tb_wms_mboverhead_det_27082020.sql
ALTER TABLE tb_wms_mboverhead_det ADD COLUMN MB_ID BIGINT(12) NULL;
--liquibase formatted sql
--changeset Anil:V20200827192538__AL_tb_wms_mboverhead_det_270820201.sql
ALTER TABLE tb_wms_mboverhead_det ADD CONSTRAINT FK_MB_ID_1 FOREIGN KEY (MB_ID) REFERENCES tb_wms_measurementbook_mast(MB_ID) ON DELETE NO ACTION ON UPDATE NO ACTION;

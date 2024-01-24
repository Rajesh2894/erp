--liquibase formatted sql
--changeset nilima:V20181001145622__AL_MRFID.sql
delete from tb_sw_tripsheet_gdet;

--liquibase formatted sql
--changeset nilima:V20181001145622__AL_MRFID1.sql
delete from tb_sw_tripsheet;

--liquibase formatted sql
--changeset nilima:V20181001145622__AL_MRFID2.sql
ALTER TABLE tb_sw_tripsheet 
CHANGE COLUMN DE_ID MRF_ID BIGINT(12) NULL DEFAULT NULL COMMENT 'Disposal Site ' ,
ADD INDEX FK_TRIPMRG_idx (MRF_ID ASC);
ALTER TABLE tb_sw_tripsheet 
ADD CONSTRAINT FK_TRIPMRG
  FOREIGN KEY (MRF_ID)
  REFERENCES tb_sw_mrf_mast (MRF_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

--liquibase formatted sql
--changeset nilima:V20181001145622__AL_MRFID3.sql
ALTER TABLE TB_SW_BEAT_MAST 
CHANGE COLUMN DE_ID MRF_ID BIGINT(12) NOT NULL COMMENT 'FK TB_SW_DESPOSAL_MAST' ,
ADD INDEX FK_BETMRFID_idx (MRF_ID ASC);

--liquibase formatted sql
--changeset nilima:V20181001145622__AL_MRFID12.sql
ALTER TABLE tb_sw_beat_mast 
ADD CONSTRAINT FK_BETMRFID
  FOREIGN KEY (MRF_ID)
  REFERENCES tb_sw_mrf_mast (MRF_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

--liquibase formatted sql
--changeset nilima:V20181001145622__AL_MRFID4.sql
delete from tb_sw_beat_mast;

--liquibase formatted sql
--changeset nilima:V20181001145622__AL_MRFID6.sql
ALTER TABLE TB_SW_BEAT_MAST_HIST
CHANGE COLUMN DE_ID MRF_ID BIGINT(12) NOT NULL COMMENT 'FK TB_SW_DESPOSAL_MAST' ;

--liquibase formatted sql
--changeset nilima:V20181001145622__AL_MRFID7.sql
ALTER TABLE tb_sw_tripsheet_hist
CHANGE COLUMN DE_ID MRF_ID BIGINT(12) NULL DEFAULT NULL ;

--liquibase formatted sql
--changeset nilima:V20181001145622__AL_MRFID8.sql
ALTER TABLE tb_sw_employee_scheddet_hist
CHANGE COLUMN DE_ID MRF_ID BIGINT(12) NULL DEFAULT NULL COMMENT 'FK TB_SW_DESPOSAL_MAST' ;

--liquibase formatted sql
--changeset nilima:V20181001145622__AL_MRFID9.sql
ALTER TABLE tb_sw_vehicle_tgdet
CHANGE COLUMN DE_ID MRF_ID BIGINT(12) NULL DEFAULT NULL COMMENT 'FK TB_SW_DESPOSAL_MAST' ;

--liquibase formatted sql
--changeset nilima:V20181001145622__AL_MRFID10.sql
ALTER TABLE tb_sw_vehicle_tgdet_hist
CHANGE COLUMN RO_ID BEAT_ID BIGINT(12) NULL DEFAULT NULL COMMENT 'FK tb_sw_route_mast' ,
CHANGE COLUMN DE_ID MRF_ID BIGINT(12) NULL DEFAULT NULL COMMENT 'FK TB_SW_DESPOSAL_MAST' ;

--liquibase formatted sql
--changeset nilima:V20181001145622__AL_MRFID11.sql
ALTER TABLE tb_sw_wasteseg_hist
CHANGE COLUMN DE_ID MRF_ID BIGINT(12) NULL DEFAULT NULL COMMENT 'Disposal Site' ;




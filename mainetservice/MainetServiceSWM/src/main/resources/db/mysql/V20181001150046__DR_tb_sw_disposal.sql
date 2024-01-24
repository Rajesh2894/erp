--liquibase formatted sql
--changeset nilima:V20181001150046__DR_tb_sw_disposal.sql
delete from tb_sw_employee_scheddet;
commit;

--liquibase formatted sql
--changeset nilima:V20181001150046__DR_tb_sw_disposal1.sql
ALTER TABLE tb_sw_employee_scheddet 
DROP FOREIGN KEY FK_SCHDEID;

--liquibase formatted sql
--changeset nilima:V20181001150046__DR_tb_sw_disposal2.sql
ALTER TABLE tb_sw_employee_scheddet 
CHANGE COLUMN DE_ID MRF_ID BIGINT(12) NULL DEFAULT NULL COMMENT 'FK TB_SW_DESPOSAL_MAST' ,
ADD INDEX FK_SCHDEID_idx (MRF_ID ASC),
DROP INDEX FK_DEID_idx ;

--liquibase formatted sql
--changeset nilima:V20181001150046__DR_tb_sw_disposal3.sql
ALTER TABLE tb_sw_employee_scheddet 
ADD CONSTRAINT FK_SCHDEID
  FOREIGN KEY (MRF_ID)
  REFERENCES tb_sw_mrf_mast (MRF_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

--liquibase formatted sql
--changeset nilima:V20181001150046__DR_tb_sw_disposal4.sql
delete from tb_sw_wasteseg_det;
delete from tb_sw_wasteseg;
commit;
  
  
--liquibase formatted sql
--changeset nilima:V20181001150046__DR_tb_sw_disposal5.sql
ALTER TABLE tb_sw_wasteseg
DROP FOREIGN KEY FK_WEDEID;

--liquibase formatted sql
--changeset nilima:V20181001150046__DR_tb_sw_disposal6.sql
ALTER TABLE tb_sw_wasteseg
CHANGE COLUMN DE_ID MRF_ID BIGINT(12) NOT NULL COMMENT 'Disposal Site' ,
ADD INDEX FK_WEDEID_idx (MRF_ID ASC),
DROP INDEX FK_WEDEID_idx ;

--liquibase formatted sql
--changeset nilima:V20181001150046__DR_tb_sw_disposal7.sql
ALTER TABLE tb_sw_wasteseg 
ADD CONSTRAINT FK_WEDEID
  FOREIGN KEY (MRF_ID)
  REFERENCES tb_sw_mrf_mast (MRF_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

--liquibase formatted sql
--changeset nilima:V20181001150046__DR_tb_sw_disposal8.sql
DROP TABLE tb_sw_disposal_det;
DROP TABLE tb_sw_disposal_det_hist;
DROP TABLE tb_sw_disposal_mast;
DROP TABLE tb_sw_disposal_mast_hist;
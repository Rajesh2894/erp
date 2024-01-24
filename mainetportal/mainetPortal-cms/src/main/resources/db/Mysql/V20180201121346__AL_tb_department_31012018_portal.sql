--liquibase formatted sql
--changeset priya:V20180201121346__AL_tb_department_31012018_portal1.sql
ALTER TABLE tb_department 
ADD CONSTRAINT FK_DP_SMFID
  FOREIGN KEY (DP_SMFID)
  REFERENCES tb_sysmodfunction (SMFID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
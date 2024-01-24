--liquibase formatted sql
--changeset priya:V20180208111837__AL_tb_portal_sms_integration_PORTAL_06022018.sql
ALTER TABLE tb_portal_sms_integration 
ADD INDEX FK_SMFID_idx (SMFID ASC)  COMMENT '',
ADD INDEX FK_DP_DEPTID_idx (DP_DEPTID ASC)  COMMENT '';
--liquibase formatted sql
--changeset priya:V20180208111837__AL_tb_portal_sms_integration_PORTAL_060220181.sql
ALTER TABLE tb_portal_sms_integration 
ADD CONSTRAINT FK_SMFID
  FOREIGN KEY (SMFID)
  REFERENCES tb_sysmodfunction (SMFID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT FK_DP_DEPTID
  FOREIGN KEY (DP_DEPTID)
  REFERENCES tb_department (DP_DEPTID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
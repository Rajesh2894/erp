--liquibase formatted sql
--changeset priya:V20180208111342__al_tb_portal_service_master_PORTAL_06022018.sql
ALTER TABLE tb_portal_service_master 
ADD INDEX FK_PSM_SMFID_idx (PSM_SMFID ASC)  COMMENT '';
--liquibase formatted sql
--changeset priya:V20180208111342__al_tb_portal_service_master_PORTAL_060220181.sql
ALTER TABLE tb_portal_service_master 
ADD CONSTRAINT FK_PSM_SMFID
  FOREIGN KEY (PSM_SMFID)
  REFERENCES tb_sysmodfunction (SMFID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

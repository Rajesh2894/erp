--liquibase formatted sql
--changeset PramodPatil:V20230726163037__AL_TB_WMS_MEASUREMENTBOOK_LBH_HIS_26072023.sql
ALTER TABLE TB_WMS_MEASUREMENTBOOK_LBH_HIS add column MBD_ID bigint(12) null default null;
 
--liquibase formatted sql
--changeset PramodPatil:V20230726163037__AL_TB_WMS_MEASUREMENTBOOK_LBH_HIS_260720231.sql
ALTER TABLE TB_WMS_MEASUREMENTBOOK_LBH_HIS MODIFY column MBD_ID bigint(12) null default null;
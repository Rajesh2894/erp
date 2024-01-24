--liquibase formatted sql
--changeset Kanchan:V20201022123800__AL_TB_WMS_MEASUREMENTBOOK_MAST_22102020.sql
alter table TB_WMS_MEASUREMENTBOOK_MAST add column MANUAL_MB_NO varchar(200);
--liquibase formatted sql
--changeset Kanchan:V20201022123800__AL_TB_WMS_MEASUREMENTBOOK_MAST_221020201.sql
alter table tb_wms_measurementbook_mast_hist add column MANUAL_MB_NO varchar(200);






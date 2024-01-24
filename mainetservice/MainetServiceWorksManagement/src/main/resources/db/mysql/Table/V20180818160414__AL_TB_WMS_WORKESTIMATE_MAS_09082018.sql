--liquibase formatted sql
--changeset nilima:V20180818160414__AL_TB_WMS_WORKESTIMATE_MAS_09082018.sql
ALTER TABLE TB_WMS_WORKESTIMATE_MAS 
CHANGE COLUMN WORKE_QUANTITY WORKE_QUANTITY DECIMAL(15,2) NULL DEFAULT NULL COMMENT 'Quantity' ,
CHANGE COLUMN WORKE_QUANTITY_UTL WORKE_QUANTITY_UTL DECIMAL(15,2) NULL DEFAULT NULL COMMENT 'Quantity utilise' ;

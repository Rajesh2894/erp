--liquibase formatted sql
--changeset PramodPatil: V20230724121343__AL_TB_WMS_MEASUREMENTBOOK_LBH_24072023.sql 
alter table TB_WMS_MEASUREMENTBOOK_LBH
add column MB_AREA decimal(8,4) null default null,
add column MB_WEIGHT decimal(8,4) null default null,
add column MB_VOLUME decimal(8,4) null default null;
 
--liquibase formatted sql
--changeset PramodPatil: V20230724121343__AL_TB_WMS_MEASUREMENTBOOK_LBH_240720231.sql  
alter table TB_WMS_MEASUREMENTBOOK_LBH_HIS
add column MB_AREA decimal(8,4) null default null,
add column MB_WEIGHT decimal(8,4) null default null,
add column MB_VOLUME decimal(8,4) null default null;

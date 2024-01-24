--liquibase formatted sql
--changeset Kanchan:V20220930163703__AL_TB_WMS_ROAD_CUTTING_DET_30092022.sql
alter table TB_WMS_ROAD_CUTTING_DET add column COD_ZONE1 bigint(20) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220930163703__AL_TB_WMS_ROAD_CUTTING_DET_300920221.sql
alter table TB_WMS_ROAD_CUTTING_DET add column COD_ZONE2 bigint(20) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220930163703__AL_TB_WMS_ROAD_CUTTING_DET_300920222.sql
alter table TB_WMS_ROAD_CUTTING_DET add column COD_ZONE3 bigint(20) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220930163703__AL_TB_WMS_ROAD_CUTTING_DET_300920223.sql
alter table TB_WMS_ROAD_CUTTING_DET add column COD_ZONE4 bigint(20) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220930163703__AL_TB_WMS_ROAD_CUTTING_DET_300920224.sql
alter table TB_WMS_ROAD_CUTTING_DET add column COD_ZONE5 bigint(20) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220930163703__AL_TB_WMS_ROAD_CUTTING_DET_300920225.sql
ALTER TABLE TB_WMS_ROAD_CUTTING_DET add column APM_APPLICATION_STATUS varchar(5) null default null;
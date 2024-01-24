--liquibase formatted sql
--changeset Kanchan:V20220930165139__AL_TB_WMS_ROAD_CUTTING_30092022.sql
ALTER TABLE TB_WMS_ROAD_CUTTING add column APPLICANT_NAME varchar(50) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220930165139__AL_TB_WMS_ROAD_CUTTING_300920221.sql
ALTER TABLE TB_WMS_ROAD_CUTTING add column APPLICANT_ADD varchar(100) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220930165139__AL_TB_WMS_ROAD_CUTTING_300920222.sql
ALTER TABLE TB_WMS_ROAD_CUTTING add column APPLICANT_HOUSENO varchar(50) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220930165139__AL_TB_WMS_ROAD_CUTTING_300920223.sql
ALTER TABLE TB_WMS_ROAD_CUTTING add column APPLICANT_AREA_NAME varchar(50) null default null;
--liquibase formatted sql
--changeset Kanchan:V20220930165139__AL_TB_WMS_ROAD_CUTTING_300920224.sql
ALTER TABLE TB_WMS_ROAD_CUTTING add column APPLICANT_MOBILE BIGINT(12) null default null;
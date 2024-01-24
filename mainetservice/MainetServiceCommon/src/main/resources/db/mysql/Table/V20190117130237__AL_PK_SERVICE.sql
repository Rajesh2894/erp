--liquibase formatted sql
--changeset nilima:V20190117130237__AL_PK_SERVICE1.sql
ALTER TABLE designation_hist
CHANGE COLUMN H_DSGID H_DSGID BIGINT(12) NOT NULL ,
ADD PRIMARY KEY (`H_DSGID`);

--liquibase formatted sql
--changeset nilima:V20190117130237__AL_PK_SERVICE2.sql
ALTER TABLE tb_care_feedback 
ADD PRIMARY KEY (`ID`);

--liquibase formatted sql
--changeset nilima:V20190117130237__AL_PK_SERVICE3.sql
ALTER TABLE tb_loc_revenue_wardzone_his 
CHANGE COLUMN LOCRWZMP_ID_H LOCRWZMP_ID_H BIGINT(12) NOT NULL ,
ADD PRIMARY KEY (`LOCRWZMP_ID_H`);

--liquibase formatted sql
--changeset nilima:V20190117130237__AL_PK_SERVICE4.sql
ALTER TABLE tb_location_elect_wardzone_his 
CHANGE COLUMN LOCEWZMP_ID_H LOCEWZMP_ID_H BIGINT(12) NOT NULL ,
ADD PRIMARY KEY (`LOCEWZMP_ID_H`);

--liquibase formatted sql
--changeset nilima:V20190117130237__AL_PK_SERVICE5.sql
ALTER TABLE tb_location_oper_wardzone_his 
CHANGE COLUMN LOCOWZMP_ID_H LOCOWZMP_ID_H BIGINT(12) NOT NULL ,
ADD PRIMARY KEY (`LOCOWZMP_ID_H`);

--liquibase formatted sql
--changeset nilima:V20190117130237__AL_PK_SERVICE6.sql
ALTER TABLE tb_scrutiny_values_hist 
CHANGE COLUMN H_SLLABLEID H_SLLABLEID BIGINT(12) NOT NULL ,
ADD PRIMARY KEY (`H_SLLABLEID`);

--liquibase formatted sql
--changeset nilima:V20190117130237__AL_PK_SERVICE7.sql
ALTER TABLE tb_services_mst_hist
CHANGE COLUMN ORGID ORGID BIGINT(12) NOT NULL COMMENT 'organisation id' ,
ADD PRIMARY KEY (H_SM_SERVICEID, ORGID);

--liquibase formatted sql
--changeset nilima:V20190117130237__AL_PK_SERVICE8.sql
ALTER TABLE tb_tax_ac_mapping_hist 
CHANGE COLUMN taxb_id_H taxb_id_H BIGINT(20) NOT NULL COMMENT 'primary key' ,
ADD PRIMARY KEY (`taxb_id_H`);

--liquibase formatted sql
--changeset nilima:V20190117130237__AL_PK_SERVICE9.sql
ALTER TABLE tb_wms_project_mast_hist 
CHANGE COLUMN PROJ_ID_H PROJ_ID_H BIGINT(12) NOT NULL ,
ADD PRIMARY KEY (`PROJ_ID_H`);






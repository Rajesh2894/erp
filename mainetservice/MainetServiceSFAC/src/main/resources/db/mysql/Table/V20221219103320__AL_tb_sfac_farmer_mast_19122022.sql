--liquibase formatted sql
--changeset Kanchan:V20221219103320__AL_tb_sfac_farmer_mast_19122022.sql
Alter table tb_sfac_farmer_mast
modify column FRM_LAND_UNIT bigint(20) null default null,
modify column FRM_SDB1 bigint(20)null default null,
modify column FRM_SDB2 bigint(20)null default null,
modify column FRM_SDB3 bigint(20)null default null;
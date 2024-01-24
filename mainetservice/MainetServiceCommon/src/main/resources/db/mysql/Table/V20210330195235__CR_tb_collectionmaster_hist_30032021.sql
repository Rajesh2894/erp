--liquibase formatted sql
--changeset Kanchan:V20210330195235__CR_tb_collectionmaster_hist_30032021.sql
create table tb_collectionmaster_hist(
H_cm_collnid	bigint(12) PRIMARY KEY NOT NULL,
cm_collncentreno NVARCHAR(200) NOT NULL,
cm_description	NVARCHAR(200) NOT NULL,
dwz_id	bigint(12) NOT NULL,
orgId bigint(12)  Null,
createdBy bigint(12) NULL,
createdDate datetime NULL,
updatedBy bigint(12) NULL,
updatedDate datetime NULL,
lgIpMac Varchar(100) NULL,
lgIpMacUpd Varchar(100) 
);
--liquibase formatted sql
--changeset Kanchan:V20210330195235__CR_tb_collectionmaster_hist_300320211.sql
create table tb_countermaster_hist(
H_cu_counterid bigint(12) PRIMARY KEY NOT NULL,
cm_collnid	bigint(12) NOT NULL,	
cu_countcentreno NVARCHAR(200) NOT NULL,	
cu_description	NVARCHAR(200) NOT NULL,
orgId bigint(12)  Null,
createdBy bigint(12) NULL,
createdDate datetime NULL,
updatedBy bigint(12) NULL,
updatedDate datetime NULL,
lgIpMac Varchar(100) NULL,
lgIpMacUpd Varchar(100) NULL);	
--liquibase formatted sql
--changeset Kanchan:V20210330195235__CR_tb_collectionmaster_hist_300320212.sql
create table  tb_counterschedule_hist(	
H_cs_scheduleid	bigint(12)	PRIMARY KEY NOT NULL,
cu_counterid	bigint(12)	NOT NULL,
cs_fromtime	datetime	NOT NULL,
cs_totime	datetime	NOT NULL,
cs_user_id	bigint(12)	NOT NULL,
orgId bigint(12)  Null,
createdBy bigint(12) NULL,
createdDate datetime NULL,
updatedBy bigint(12) NULL,
updatedDate datetime NULL,
lgIpMac Varchar(100) NULL,
lgIpMacUpd Varchar(100) NULL);
	

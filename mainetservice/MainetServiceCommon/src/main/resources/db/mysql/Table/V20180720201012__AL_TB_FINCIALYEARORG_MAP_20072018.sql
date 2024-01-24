--liquibase formatted sql
--changeset nilima:V20180720201012__AL_TB_FINCIALYEARORG_MAP_20072018.sql
ALTER TABLE TB_FINCIALYEARORG_MAP 
CHANGE COLUMN fa_frommonth fa_frommonth INT(3) NULL DEFAULT NULL COMMENT 'Fiscale from  Month' AFTER FA_YEARID,
CHANGE COLUMN fa_tomonth fa_tomonth INT(3) NULL DEFAULT NULL COMMENT 'Fiscale To  Month' AFTER fa_frommonth,
CHANGE COLUMN fa_fromyear fa_fromyear INT(4) NULL DEFAULT NULL COMMENT 'Fiscale From Year' AFTER fa_tomonth,
CHANGE COLUMN fa_toyear fa_toyear INT(4) NULL DEFAULT NULL COMMENT 'Fiscale To Year' AFTER fa_fromyear,
CHANGE COLUMN fa_monstatus fa_monstatus INT(12) NULL DEFAULT NULL COMMENT 'Fiscale \"SOFT\" close' AFTER fa_toyear,
CHANGE COLUMN fa_yearstatus fa_yearstatus INT(12) NULL DEFAULT NULL COMMENT 'Fiscale \"Hard\" close' AFTER fa_monstatus,
CHANGE COLUMN ORGID ORGID INT(11) NOT NULL COMMENT 'Organisation Id' AFTER fa_monstatus,
CHANGE COLUMN CREATED_BY CREATED_BY INT(11) NOT NULL COMMENT ' user id who created the record' AFTER ORGID,
CHANGE COLUMN CREATED_DATE CREATED_DATE DATETIME default '0000-00-00 00:00:00' NOT NULL COMMENT 'record creation date\n' AFTER CREATED_BY,
CHANGE COLUMN UPDATED_BY UPDATED_BY INT(11) NULL DEFAULT NULL COMMENT 'user id who updated the record' AFTER CREATED_DATE,
CHANGE COLUMN UPDATED_DATE UPDATED_DATE DATETIME NULL DEFAULT NULL COMMENT 'date on which updated the record\n' AFTER UPDATED_BY;

--liquibase formatted sql
--changeset nilima:V20170725115924__AL_TB_FINANCIALYEAR_210720171.sql
update TB_FINANCIALYEAR set lg_ip_mac='AMCTP | 192.168.0.150 | 44-87-FC-D2-66-D1';
commit;

--liquibase formatted sql
--changeset nilima:V20170725115924__AL_TB_FINANCIALYEAR_21072017.sql
ALTER TABLE TB_FINANCIALYEAR
DROP COLUMN fa_yearstatus,
DROP COLUMN fa_monstatus,
DROP COLUMN fa_toyear,
DROP COLUMN fa_fromyear,
DROP COLUMN fa_tomonth,
DROP COLUMN fa_frommonth,
CHANGE COLUMN created_by CREATED_BY BIGINT(12) NOT NULL COMMENT '' ,
CHANGE COLUMN updated_by UPDATED_BY BIGINT(12) NULL DEFAULT NULL COMMENT '' ,
CHANGE COLUMN lg_ip_mac  lg_ip_mac VARCHAR(100) NOT NULL COMMENT '' ;

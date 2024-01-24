--liquibase formatted sql
--changeset nilima:V20170718185657__AL_TB_AC_VOUCHER_DET_11072017.sql
/*ALTER TABLE TB_AC_VOUCHER_DET 
DROP FOREIGN KEY FK_VOU_DEPTID;*/
ALTER TABLE TB_AC_VOUCHER_DET 
DROP COLUMN DP_DEPTID,
CHANGE COLUMN VOUDET_ID VOUDET_ID BIGINT(12) NOT NULL DEFAULT '0' COMMENT 'Primary Key' ,
CHANGE COLUMN VOU_ID VOU_ID BIGINT(12) NOT NULL COMMENT 'FK of TB_AC_VOUCHER' ,
CHANGE COLUMN VOUDET_AMT VOUDET_AMT DECIMAL(15,2) NOT NULL COMMENT 'Voucher Detail Amount' ,
CHANGE COLUMN DRCR_CPD_ID DRCR_CPD_ID BIGINT(15) NOT NULL COMMENT 'prefix \'DCR\'' ,
CHANGE COLUMN ORGID ORGID BIGINT(12) NOT NULL COMMENT 'Organisation Id' ,
CHANGE COLUMN CREATED_BY CREATED_BY BIGINT(12) NOT NULL COMMENT 'Created User Identity' ,
CHANGE COLUMN LANG_ID LANG_ID BIGINT(12) NOT NULL COMMENT 'Languge' ,
CHANGE COLUMN CREATED_DATE CREATED_DATE DATETIME NOT NULL COMMENT 'Created Date' ,
CHANGE COLUMN UPDATED_BY UPDATED_BY BIGINT(12) NULL COMMENT 'User id who update the data' ,
CHANGE COLUMN UPDATED_DATE UPDATED_DATE DATETIME NULL DEFAULT NULL COMMENT 'Date on which data is going to update' ,
CHANGE COLUMN LG_IP_MAC LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'Client Machine?s Login Name | IP Address | Physical Address' ,
CHANGE COLUMN LG_IP_MAC_UPD LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'Updated Client Machine?s Login Name | IP Address | Physical Address' ;


--liquibase formatted sql
--changeset nilima:V20170718185657__AL_TB_AC_VOUCHER_DET_110720171.sql
ALTER TABLE TB_AC_VOUCHER_DET 
ADD CONSTRAINT FK_VOU_ID
  FOREIGN KEY (VOU_ID)
  REFERENCES TB_AC_VOUCHER (VOU_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

--liquibase formatted sql
--changeset nilima:V20171229191725__THEMEMASTER.sql
CREATE TABLE THEMEMASTER (
  ThemeId bigint(12) NOT NULL,
  section varchar(45) DEFAULT NULL,
  status varchar(1) DEFAULT NULL,
  orgid bigint(12) DEFAULT NULL,
  createdby varchar(45) DEFAULT NULL,
  createddate datetime DEFAULT NULL,
  updatedby bigint(12) DEFAULT NULL,
  updateddate datetime DEFAULT NULL,
  lgipmac varchar(100) DEFAULT NULL,
  lgipmacupd varchar(100) DEFAULT NULL,
  PRIMARY KEY (ThemeId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--liquibase formatted sql
--changeset ajit:V20180221194005__CR_2_TB_RTI_MEDIA_DETAILS_MYSQL.sql
drop table IF EXISTS TB_RTI_MEDIA_DETAILS; 
--changeset ajit:V20180221194005__CR_2_TB_RTI_MEDIA_DETAILS_MYSQL1.sql
 CREATE TABLE TB_RTI_MEDIA_DETAILS
 (RTI_MED_ID Bigint COMMENT 'Dispatch Id Primary Key' ,
  RTI_ID Bigint COMMENT 'RTI ID From TB_RTI_APPLICATION',
  LOI_ID Bigint COMMENT 'LOI ID From TB_LOI_MAS',
  CARE_REQ_ID Bigint COMMENT 'CARE_REQ_ID From tb_care_request Table',
  ORGID Bigint NOT NULL COMMENT 'Organisation Id',
  MEDIA_TYPE int COMMENT 'Media Type Ex. Paper, Mail, CD etc',
  MEDIA_QUANTITY int COMMENT 'Media Quantity',
  MEDIA_AMOUNT   DECIMAL(10,2) COMMENT 'Media Amount - LOI',
  LANG_ID int NOT NULL COMMENT 'Language Id',
  USER_ID Bigint NOT NULL COMMENT 'User Id',
  LMODDATE DATETIME NOT NULL,
  LG_IP_MAC VARCHAR(100) COMMENT 'Client Machines Login Name | IP Address | Physical Address',
  UPDATED_BY Bigint COMMENT 'Last Updated By',
  UPDATED_DATE DATETIME COMMENT 'Last Updated Date',
  LG_IP_MAC_UPD VARCHAR(100) COMMENT 'last Updated Client Machines Login Name | IP Address | Physical Address',
  ISDELETED int COMMENT 'Flag to identify whether the record is deleted or not. 1 for deleted (Inactive) and 0 for not deleted (Active) record');

--changeset ajit:V20180221194005__CR_2_TB_RTI_MEDIA_DETAILS_MYSQL2.sql
ALTER TABLE `TB_RTI_MEDIA_DETAILS` ADD CONSTRAINT PK_RTI_APP_MED_ID PRIMARY KEY (`RTI_MED_ID`);

--changeset ajit:V20180221194005__CR_2_TB_RTI_MEDIA_DETAILS_MYSQL3.sql
ALTER TABLE `TB_RTI_MEDIA_DETAILS` ADD CONSTRAINT FK_RTI_MED_RTI_ID FOREIGN KEY (`RTI_ID`) REFERENCES TB_RTI_APPLICATION (`RTI_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

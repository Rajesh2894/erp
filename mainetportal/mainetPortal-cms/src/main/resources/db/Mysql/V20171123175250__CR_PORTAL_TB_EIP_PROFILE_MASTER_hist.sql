--liquibase formatted sql
--changeset Kailash:V20171123175250__CR_PORTAL_TB_EIP_PROFILE_MASTER_hist2.sql
DROP TABLE IF EXISTS  TB_EIP_PROFILE_MASTER_HIST;

--liquibase formatted sql
--changeset Kailash:V20171123175250__CR_PORTAL_TB_EIP_PROFILE_MASTER_hist1.sql
CREATE TABLE TB_EIP_PROFILE_MASTER_HIST (
  `PROFILE_ID_H` bigint(12) ,
  `PROFILE_ID` bigint(12) ,
  `ADDRESS` varchar(200) ,
  `IMAGE_NAME` varchar(1000) ,
  `IMAGE_SIZE` varchar(150) ,
  `DESIGNATION_EN` varchar(100) ,
  `DESIGNATION_REG` varchar(100) ,
  `EMAIL_ID` varchar(150) ,
  `LINK_TITLE_EN` varchar(150) ,
  `LINK_TITLE_REG` varchar(100) ,
  `P_NAME_EN` varchar(100) ,
  `P_NAME_REG` varchar(2000) ,
  `PRABHAG_EN` varchar(100) ,
  `PRABHAG_REG` varchar(2000) ,
  `PROFILE_EN` varchar(4000) ,
  `PROFILE_REG` varchar(4000) ,
  `CPD_SECTION` bigint(12) ,
  `SUMMARY_EN` varchar(2000) ,
  `SUMMARY_REG` varchar(2000) ,
  `ISDELETED` varchar(1) ,
  `ORGID` bigint(10) ,
  `USER_ID` bigint(10) ,
  `LANG_ID` int(11) ,
  `CREATED_DATE` datetime ,
  `UPDATED_BY` bigint(10) ,
  `UPDATED_DATE` datetime ,
  `LG_IP_MAC` varchar(100) ,
  `LG_IP_MAC_UPD` varchar(100) ,
  `CHEKER_FLAG` varchar(1) ,
  `DMS_DOC_ID` varchar(100) ,
  `DMS_DOC_NAME` varchar(100) ,
  `DMS_DOC_VERSION` varchar(100) ,
  `DMS_FOLDER_PATH` varchar(100) ,
  `H_STATUS` varchar(1) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



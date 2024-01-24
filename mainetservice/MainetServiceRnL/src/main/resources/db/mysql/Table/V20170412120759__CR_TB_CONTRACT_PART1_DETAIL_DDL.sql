CREATE TABLE `TB_CONTRACT_PART1_DETAIL` (
  `CONTP1_ID` bigint(12) NOT NULL COMMENT 'primary key',
  `CONT_ID` bigint(12) NOT NULL COMMENT 'Foregin Key (TB_CONTRACT_MAST)',
  `DP_DEPTID` bigint(12) DEFAULT NULL COMMENT 'Foregin Key(TB_DEPARTMENT)',
  `DSGID` bigint(12) DEFAULT NULL COMMENT 'Foregin Key(Designation)',
  `EMPID` bigint(12) DEFAULT NULL COMMENT 'Foregin Key(Employee)',
  `CONTP1_NAME` varchar(400) CHARACTER SET latin1 DEFAULT NULL COMMENT 'Representer Name/Witness Name',
  `CONTP1_ADDRESS` varchar(1000) CHARACTER SET latin1 DEFAULT NULL COMMENT 'Witness Address',
  `CONTP1_PROOF_ID_NO` varchar(100) CHARACTER SET latin1 DEFAULT NULL COMMENT 'Proof Id No.',
  `CONTP1_PARENT_ID` bigint(12) DEFAULT NULL COMMENT 'Party1 Parent id for type Witness',
  `CONTP1_TYPE` char(1) CHARACTER SET latin1 NOT NULL COMMENT 'W->Witness , U->Ulb',
  `CONTP1_ACTIVE` char(1) CHARACTER SET latin1 NOT NULL COMMENT 'flag to identify whether the record is deleted or not. ''y'' for deleted (inactive) and ''n'' for not deleted (active) record.',
  `ORGID` bigint(12) NOT NULL COMMENT 'orgnisation id',
  `CREATED_BY` bigint(12) NOT NULL COMMENT 'user id who created the record',
  `CREATED_DATE` datetime NOT NULL COMMENT 'record creation date',
  `UPDATED_BY` bigint(12) DEFAULT NULL COMMENT 'user id who update the data',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'date on which data is going to update',
  `LG_IP_MAC` varchar(100) CHARACTER SET latin1 NOT NULL COMMENT 'client machine?s login name | ip address | physical address',
  `LG_IP_MAC_UPD` varchar(100) CHARACTER SET latin1 DEFAULT NULL COMMENT 'updated client machine?s login name | ip address | physical address',
  `CONTP1_PHOTO_FILE_NAME` varchar(200) DEFAULT NULL COMMENT 'Photo Image File Name',
  `CONTP1_PHOTO_FILE_PATH_NAME` varchar(500) DEFAULT NULL COMMENT 'Photo Image File Path Name',
  `CONTP1_THUMB_FILE_NAME` varchar(200) DEFAULT NULL COMMENT 'Thumb Image File Name',
  `CONTP1_THUMB_FILE_PATH_NAME` varchar(500) DEFAULT NULL COMMENT 'Thumb Image File Path Name',
  PRIMARY KEY (`CONTP1_ID`),
  KEY `FK_P1_CONT_ID` (`CONT_ID`),
  KEY `FK_DEPT_ID` (`DP_DEPTID`),
  KEY `FK_DSG_ID` (`DSGID`),
  KEY `FK_EMP_ID` (`EMPID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='table used to store contract ulb & witness details';

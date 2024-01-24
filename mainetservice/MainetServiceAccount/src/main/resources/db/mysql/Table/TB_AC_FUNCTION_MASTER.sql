CREATE TABLE `tb_ac_function_master` (
  `FUNCTION_ID` decimal(12,0) NOT NULL,
  `CODCOFDET_ID` decimal(12,0) DEFAULT NULL,
  `FUNCTION_CODE` varchar(10) DEFAULT NULL,
  `FUNCTION_DESC` varchar(1000) DEFAULT NULL,
  `FUNCTION_PARENT_ID` decimal(12,0) DEFAULT NULL,
  `FUNCTION_COMPCODE` varchar(20) DEFAULT NULL,
  `ORGID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  `LANG_ID` int(11) NOT NULL,
  `LMODDATE` datetime NOT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `LG_IP_MAC` varchar(100) DEFAULT NULL,
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


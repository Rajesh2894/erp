CREATE TABLE `tb_ac_codingstructure_det` (
  `CODCOFDET_ID` decimal(12,0) NOT NULL,
  `CODCOF_ID` decimal(12,0) DEFAULT NULL,
  `COD_LEVEL` int(11) DEFAULT NULL,
  `COD_DESCRIPTION` varchar(1000) DEFAULT NULL,
  `COD_DIGITS` int(11) DEFAULT NULL,
  `ORGID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  `LANG_ID` int(11) NOT NULL,
  `LMODDATE` datetime NOT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `LG_IP_MAC` varchar(100) DEFAULT NULL,
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


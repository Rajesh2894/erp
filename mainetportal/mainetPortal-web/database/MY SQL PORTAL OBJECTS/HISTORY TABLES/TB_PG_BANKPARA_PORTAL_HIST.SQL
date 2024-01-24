CREATE TABLE `tb_pg_bankpara_portal_hist` (
  `H_BANKPARID` decimal(12,0) NOT NULL,
  `BANKPARID` decimal(12,0) NOT NULL,
  `BM_BANK_ID` decimal(12,0) DEFAULT NULL,
  `PARNAME` varchar(1000) DEFAULT NULL,
  `PARVALUE` varchar(1000) DEFAULT NULL,
  `PASSVALUE` varchar(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


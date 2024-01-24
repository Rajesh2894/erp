CREATE TABLE `TB_ADJUSTMENT_MAS` (
  `adj_id` bigint(12) NOT NULL COMMENT 'Primary Key',
  `dp_deptid` bigint(12) NOT NULL COMMENT 'Department Id',
  `adj_ref_no` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT 'department wise key for eg. Property ->property no(primary key) ,water connection no(primary key)',
  `adj_date` date NOT NULL COMMENT 'Adjustment Date',
  `adj_type` char(1) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Positive->"P",Nagative->"N"',
  `orgid` bigint(12) NOT NULL,
  `created_by` bigint(12) NOT NULL,
  `created_date` date NOT NULL,
  `updated_by` bigint(12) DEFAULT NULL,
  `updated_date` date DEFAULT NULL,
  `lg_ip_mac` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `lg_ip_mac_upd` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`adj_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

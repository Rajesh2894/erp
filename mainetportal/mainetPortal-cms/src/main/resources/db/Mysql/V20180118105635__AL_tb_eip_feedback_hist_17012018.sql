--liquibase formatted sql
--changeset priya:V20180118105635__AL_tb_eip_feedback_hist_17012018.sql
drop table TB_EIP_FEEDBACK_HIST;

--liquibase formatted sql
--changeset priya:V20180118105635__AL_tb_eip_feedback_hist_170120181.sql
CREATE TABLE `tb_eip_feedback_hist` (
  `FD_ID_H` bigint(12) DEFAULT NULL,
  `FD_ID` bigint(12) DEFAULT NULL COMMENT 'Feedback Id',
  `FD_USER_NAME` varchar(1000) DEFAULT NULL COMMENT 'Feedback user Name',
  `EMPID` bigint(12) DEFAULT NULL COMMENT 'Employee id',
  `MOBILE_NO` decimal(10,0) DEFAULT NULL COMMENT 'Mobile Number',
  `EMAIL_ID` varchar(100) DEFAULT NULL COMMENT 'Email Id',
  `ISDELETED` varchar(1) DEFAULT NULL COMMENT 'Flag to identify whether the record is deleted or not. 1 for deleted (inactive) and 0 for not deleted (active) record.',
  `ORGID` int(11) DEFAULT NULL COMMENT 'Organisation Id',
  `CREATED_DATE` datetime DEFAULT NULL COMMENT 'record creation date',
  `UPDATED_BY` int(11) DEFAULT NULL COMMENT 'user id who updated the record',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'date on which updated the record',
  `LG_IP_MAC` varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  `H_STATUS` varchar(1) DEFAULT NULL COMMENT 'Status of the record',
  `FD_ANSWERS` varchar(4000) DEFAULT NULL COMMENT 'Feedback Answers',
  `FD_QUESTIONS` varchar(4000) DEFAULT NULL COMMENT 'Feedback Questions',
  `FD_FLAG` char(1) DEFAULT 'N' COMMENT '''Y''''-> Completion ''''N'''' -> non completion''',
  `CREATED_BY` bigint(12) NOT NULL COMMENT 'User Id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
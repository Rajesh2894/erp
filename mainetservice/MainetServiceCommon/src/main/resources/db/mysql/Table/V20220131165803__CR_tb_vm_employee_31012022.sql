--liquibase formatted sql
--changeset Kanchan:V20220131165803__CR_tb_vm_employee_31012022.sql
CREATE TABLE `tb_vm_employee` (
  `VM_EMPID` bigint(12) NOT NULL,
  `MRF_ID` bigint(12) NOT NULL,
  `DSGID` bigint(12) DEFAULT NULL,
  `CPD_TTL_ID` bigint(12) NOT NULL,
  `VM_EMPNAME` varchar(200) NOT NULL,
  `VM_EMPMNAME` varchar(200) DEFAULT NULL,
  `VM_EMPLNAME` varchar(200) NOT NULL,
  `VM_EMPGENDER` varchar(1) NOT NULL,
  `VM_EMPMOBNO` varchar(30) NOT NULL,
  `VM_EMPEMAIL` varchar(100) DEFAULT NULL,
  `VM_EMP_ADDRESS` varchar(100) NOT NULL,
  `VM_EMP_ADDRESS1` varchar(100) DEFAULT NULL,
  `VM_EMPPINCODE` varchar(11) DEFAULT NULL,
  `VM_EMPUID` varchar(20) DEFAULT NULL,
  `ORGID` bigint(12) NOT NULL,
  `CREATED_BY` bigint(12) NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_BY` bigint(12) DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `LG_IP_MAC` varchar(100) NOT NULL,
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`VM_EMPID`)
) ;
--liquibase formatted sql
--changeset Kanchan:V20220131165803__CR_tb_vm_employee_310120221.sql
CREATE TABLE `tb_vm_employee_hist` (
  `VM_EMPID_H` bigint(12) NOT NULL,
  `VM_EMPID` bigint(12) NOT NULL,
  `MRF_ID` bigint(12) NOT NULL,
  `DSGID` bigint(12) DEFAULT NULL,
  `CPD_TTL_ID` bigint(12) NOT NULL,
  `VM_EMPNAME` varchar(200) NOT NULL,
  `VM_EMPMNAME` varchar(200) DEFAULT NULL,
  `VM_EMPLNAME` varchar(200) NOT NULL,
  `VM_EMPGENDER` varchar(1) NOT NULL,
  `VM_EMPMOBNO` varchar(30) NOT NULL,
  `VM_EMPEMAIL` varchar(100) DEFAULT NULL,
  `VM_EMP_ADDRESS` varchar(100) NOT NULL,
  `VM_EMP_ADDRESS1` varchar(100) DEFAULT NULL,
  `VM_EMPPINCODE` varchar(11) DEFAULT NULL,
  `VM_EMPUID` varchar(20) DEFAULT NULL,
  `H_STATUS` char(1) DEFAULT NULL,
  `ORGID` bigint(12) NOT NULL,
  `CREATED_BY` bigint(12) NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  `UPDATED_BY` bigint(12) DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `LG_IP_MAC` varchar(100) NOT NULL,
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`VM_EMPID`)
) ;


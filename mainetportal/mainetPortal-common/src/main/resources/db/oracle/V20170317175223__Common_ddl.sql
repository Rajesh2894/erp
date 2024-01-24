----------------------------------------------------------
-- Export file for user PORTAL1                         --
-- Created by kailash.agarwal on 3/17/2017, 04:55:24 PM --
----------------------------------------------------------

set define off
spool table.log

prompt
prompt Creating table TB_ORGANISATION
prompt ==============================
prompt
create table TB_ORGANISATION
(
  orgid             NUMBER not null,
  o_nls_orgname     NVARCHAR2(50),
  user_id           NUMBER(7),
  lang_id           NUMBER(4),
  lmoddate          DATE,
  tds_accountno     NVARCHAR2(20),
  tds_circle        NVARCHAR2(20),
  tds_pan_gir_no    NVARCHAR2(20),
  org_tax_ded_name  NVARCHAR2(70),
  org_tax_ded_addr  NVARCHAR2(200),
  org_short_nm      NVARCHAR2(10),
  o_logo            LONG RAW,
  org_address       NVARCHAR2(200),
  o_nls_orgname_mar NVARCHAR2(100),
  org_address_mar   NVARCHAR2(200),
  updated_by        NUMBER(7),
  updated_date      DATE,
  app_start_date    DATE,
  esdt_date         DATE,
  org_status        NVARCHAR2(1),
  org_cpd_id        NUMBER(12),
  default_status    CHAR(1),
  org_cpd_id_div    NUMBER(12),
  org_cpd_id_ost    NUMBER(12),
  org_cpd_id_dis    NUMBER,
  org_email_id      NVARCHAR2(100),
  vat_circle        NVARCHAR2(200),
  vat_ded_name      NVARCHAR2(500)
)
;
comment on column TB_ORGANISATION.tds_accountno
  is 'TDS Account Number of Organsation. Used in TDS Certificates';
comment on column TB_ORGANISATION.tds_circle
  is 'TDS Circle of Organsation where Annual return is to be delivered. Used in TDS Certificates';
comment on column TB_ORGANISATION.tds_pan_gir_no
  is 'Organisation PAN/GIR No';
comment on column TB_ORGANISATION.org_tax_ded_name
  is 'Name of person against deduction tax';
comment on column TB_ORGANISATION.org_tax_ded_addr
  is 'Address of person against deduction tax';
comment on column TB_ORGANISATION.org_short_nm
  is 'Short name which may use as abbrivation';
comment on column TB_ORGANISATION.org_cpd_id_div
  is 'Division';
comment on column TB_ORGANISATION.org_cpd_id_ost
  is 'Organisation Sub-Type';
comment on column TB_ORGANISATION.org_cpd_id_dis
  is 'cpdId';
comment on column TB_ORGANISATION.org_email_id
  is 'emailId';
comment on column TB_ORGANISATION.vat_circle
  is 'circle ';
alter table TB_ORGANISATION
  add constraint PK_ORGANISATION primary key (ORGID);

prompt
prompt Creating table TB_LOCATION_MAS
prompt ==============================
prompt
create table TB_LOCATION_MAS
(
  loc_id          NUMBER(12) not null,
  loc_name_eng    NVARCHAR2(500),
  loc_name_reg    NVARCHAR2(500),
  loc_description NVARCHAR2(1000),
  loc_active      VARCHAR2(1),
  loc_address1    NVARCHAR2(350),
  loc_address2    NVARCHAR2(350),
  loc_city        NVARCHAR2(50),
  loc_dwz_id      NUMBER(12),
  loc_parentid    NUMBER(12),
  loc_source      CHAR(1),
  loc_aut_status  CHAR(1),
  loc_aut_by      NUMBER(12),
  loc_aut_date    DATE,
  orgid           NUMBER(4),
  user_id         NUMBER(12),
  lang_id         NUMBER(7),
  lmoddate        DATE,
  updated_by      NUMBER(12),
  updated_date    DATE,
  lg_ip_mac       VARCHAR2(100),
  lg_ip_mac_upd   VARCHAR2(100),
  centraleno      NVARCHAR2(50),
  aut_v1          NVARCHAR2(100),
  aut_v2          NVARCHAR2(100),
  aut_v3          NVARCHAR2(100),
  aut_v4          NVARCHAR2(100),
  aut_v5          NVARCHAR2(100),
  aut_n1          NUMBER(15),
  aut_n2          NUMBER(15),
  aut_n3          NUMBER(15),
  aut_n4          NUMBER(15),
  aut_n5          NUMBER(15),
  aut_d1          DATE,
  aut_d2          DATE,
  aut_d3          DATE,
  aut_d4          DATE,
  aut_d5          DATE
)
;
comment on column TB_LOCATION_MAS.loc_id
  is 'primary key (location id)';
comment on column TB_LOCATION_MAS.loc_name_eng
  is 'location name in english';
comment on column TB_LOCATION_MAS.loc_name_reg
  is 'location name in Hindi';
comment on column TB_LOCATION_MAS.loc_description
  is 'location description';
comment on column TB_LOCATION_MAS.loc_active
  is 'flag to identify whether the record is deleted or not. ''Y'' for deleted (inactive) and ''N'' for not deleted (active) record.';
comment on column TB_LOCATION_MAS.loc_address1
  is 'stores the address of the department. (not in use)';
comment on column TB_LOCATION_MAS.loc_address2
  is 'stores the address of the department. (not in use)';
comment on column TB_LOCATION_MAS.loc_city
  is 'stores the city information of department. stores the city information of department. (not in use)';
comment on column TB_LOCATION_MAS.loc_dwz_id
  is 'stores the value of ward id';
comment on column TB_LOCATION_MAS.loc_parentid
  is 'maintains parent child relation for  a particular record(depid)';
comment on column TB_LOCATION_MAS.loc_source
  is 'location source (U->ULB location,O->Other location)';
comment on column TB_LOCATION_MAS.loc_aut_status
  is 'Authorisation Status';
comment on column TB_LOCATION_MAS.loc_aut_by
  is 'Authorisation By (Empid)';
comment on column TB_LOCATION_MAS.loc_aut_date
  is 'Authorisation Date';
comment on column TB_LOCATION_MAS.orgid
  is 'orgnisation id';
comment on column TB_LOCATION_MAS.user_id
  is 'user id';
comment on column TB_LOCATION_MAS.lang_id
  is 'language id';
comment on column TB_LOCATION_MAS.lmoddate
  is 'creation date';
comment on column TB_LOCATION_MAS.updated_by
  is 'updated by';
comment on column TB_LOCATION_MAS.updated_date
  is 'updated time';
comment on column TB_LOCATION_MAS.lg_ip_mac
  is 'client machine�s login name | ip address | physical address';
comment on column TB_LOCATION_MAS.lg_ip_mac_upd
  is 'updated client machine�s login name | ip address | physical address';
comment on column TB_LOCATION_MAS.centraleno
  is 'server name,owner name';
comment on column TB_LOCATION_MAS.aut_v1
  is 'additional nvarchar2 aut_v1 to be used in future';
comment on column TB_LOCATION_MAS.aut_v2
  is 'additional nvarchar2 aut_v2 to be used in future';
comment on column TB_LOCATION_MAS.aut_v3
  is 'additional nvarchar2 aut_v3 to be used in future';
comment on column TB_LOCATION_MAS.aut_v4
  is 'additional nvarchar2 aut_v4 to be used in future';
comment on column TB_LOCATION_MAS.aut_v5
  is 'additional nvarchar2 aut_v5 to be used in future';
comment on column TB_LOCATION_MAS.aut_n1
  is 'additional nvarchar2 aut_n1 to be used in future';
comment on column TB_LOCATION_MAS.aut_n2
  is 'additional nvarchar2 aut_n2 to be used in future';
comment on column TB_LOCATION_MAS.aut_n3
  is 'additional nvarchar2 aut_n3 to be used in future';
comment on column TB_LOCATION_MAS.aut_n4
  is 'additional nvarchar2 aut_n4 to be used in future';
comment on column TB_LOCATION_MAS.aut_n5
  is 'additional nvarchar2 aut_n5 to be used in future';
comment on column TB_LOCATION_MAS.aut_d1
  is 'additional nvarchar2 aut_d1 to be used in future';
comment on column TB_LOCATION_MAS.aut_d2
  is 'additional nvarchar2 aut_d2 to be used in future';
comment on column TB_LOCATION_MAS.aut_d3
  is 'additional nvarchar2 aut_d3 to be used in future';
comment on column TB_LOCATION_MAS.aut_d4
  is 'additional nvarchar2 aut_d4 to be used in future';
comment on column TB_LOCATION_MAS.aut_d5
  is 'additional nvarchar2 aut_d5 to be used in future';
alter table TB_LOCATION_MAS
  add constraint PK_LOC_ID primary key (LOC_ID);
alter table TB_LOCATION_MAS
  add constraint FK_ORGID_LOC foreign key (ORGID)
  references TB_ORGANISATION (ORGID);

prompt
prompt Creating table DESIGNATION
prompt ==========================
prompt
create table DESIGNATION
(
  dsgid          NUMBER(12) not null,
  dsgname        NVARCHAR2(500) not null,
  dsgshortname   NVARCHAR2(100),
  dsgdescription NVARCHAR2(1000),
  locid          NUMBER(12),
  isdeleted      VARCHAR2(1),
  user_id        NUMBER(7) not null,
  updated_by     NUMBER(7),
  updated_date   DATE,
  lang_id        NUMBER(7),
  lg_ip_mac      VARCHAR2(100),
  lg_ip_mac_upd  VARCHAR2(100),
  aut_v1         NVARCHAR2(100),
  aut_v2         NVARCHAR2(100),
  aut_v3         NVARCHAR2(100),
  aut_v4         NVARCHAR2(100),
  aut_v5         NVARCHAR2(100),
  aut_n1         NUMBER(15),
  aut_n2         NUMBER(15),
  aut_n3         NUMBER(15),
  aut_n4         NUMBER(15),
  aut_n5         NUMBER(15),
  aut_d1         DATE,
  aut_d2         DATE,
  aut_d3         DATE,
  aut_lo1        CHAR(1),
  aut_lo2        CHAR(1),
  aut_d4         DATE,
  aut_d5         DATE,
  dsgname_reg    NVARCHAR2(500),
  aut_by         NVARCHAR2(100),
  aut_date       DATE,
  centraleno     NVARCHAR2(100),
  action         VARCHAR2(1),
  dsgoname       VARCHAR2(50),
  lmoddate       DATE,
  aut_status     CHAR(1)
)
;
comment on column DESIGNATION.dsgid
  is 'Primary Key (Designation Id)';
comment on column DESIGNATION.dsgname
  is 'Designation Name';
comment on column DESIGNATION.dsgshortname
  is 'Designation Short Name';
comment on column DESIGNATION.dsgdescription
  is 'Designation Description';
comment on column DESIGNATION.locid
  is 'Location Id';
comment on column DESIGNATION.isdeleted
  is 'Flag to identify whether the record is deleted. 1 for deleted (Inactive) and 0 for not deleted (Active) record.';
comment on column DESIGNATION.user_id
  is 'User Id';
comment on column DESIGNATION.updated_by
  is 'Updated by';
comment on column DESIGNATION.updated_date
  is 'Updated date';
comment on column DESIGNATION.lang_id
  is 'Language Id';
comment on column DESIGNATION.lg_ip_mac
  is 'Client Machine�s Login Name | IP Address | Physical Address';
comment on column DESIGNATION.lg_ip_mac_upd
  is 'Updated Client Machine�s Login Name | IP Address | Physical Address';
comment on column DESIGNATION.aut_v1
  is 'Additional nvarchar2 AUT_V1 to be used in future';
comment on column DESIGNATION.aut_v2
  is 'Additional nvarchar2 AUT_V2 to be used in future';
comment on column DESIGNATION.aut_v3
  is 'Additional nvarchar2 AUT_V3 to be used in future';
comment on column DESIGNATION.aut_v4
  is 'Additional nvarchar2 AUT_V4 to be used in future';
comment on column DESIGNATION.aut_v5
  is 'Additional nvarchar2 AUT_V5 to be used in future';
comment on column DESIGNATION.aut_n1
  is 'Additional nvarchar2 AUT_N1 to be used in future';
comment on column DESIGNATION.aut_n2
  is 'Additional nvarchar2 AUT_N2 to be used in future';
comment on column DESIGNATION.aut_n3
  is 'Additional nvarchar2 AUT_N3 to be used in future';
comment on column DESIGNATION.aut_n4
  is 'Additional nvarchar2 AUT_N4 to be used in future';
comment on column DESIGNATION.aut_n5
  is 'Additional nvarchar2 AUT_N5 to be used in future';
comment on column DESIGNATION.aut_d1
  is 'Additional nvarchar2 AUT_D1 to be used in future';
comment on column DESIGNATION.aut_d2
  is 'Additional nvarchar2 AUT_D2 to be used in future';
comment on column DESIGNATION.aut_d3
  is 'Additional nvarchar2 AUT_D3 to be used in future';
comment on column DESIGNATION.aut_lo1
  is 'Additional Logical field AUT_LO1 to be used in future';
comment on column DESIGNATION.aut_lo2
  is 'Additional Logical field AUT_LO2 to be used in future';
comment on column DESIGNATION.aut_d4
  is 'Additional nvarchar2 AUT_D4 to be used in future';
comment on column DESIGNATION.aut_d5
  is 'Additional nvarchar2 AUT_D5 to be used in future';
comment on column DESIGNATION.dsgname_reg
  is 'Designation Name regional';
comment on column DESIGNATION.aut_by
  is 'Authorisation By (Empid)';
comment on column DESIGNATION.aut_date
  is 'Authorisation Date';
comment on column DESIGNATION.centraleno
  is 'server name,owner name';
comment on column DESIGNATION.lmoddate
  is 'date modified';
comment on column DESIGNATION.aut_status
  is 'auth status';
alter table DESIGNATION
  add primary key (DSGID);
alter table DESIGNATION
  add constraint FK_LOC_ID foreign key (LOCID)
  references TB_LOCATION_MAS (LOC_ID)
  disable;
  
prompt
prompt Creating table DESIGNATION_HIST
prompt ===============================
prompt
create table DESIGNATION_HIST
(
  h_dsgid        NUMBER(12),
  dsgid          NUMBER(12),
  dsgname        NVARCHAR2(500),
  dsgshortname   NVARCHAR2(100),
  dsgdescription NVARCHAR2(1000),
  locid          NUMBER(12),
  isdeleted      VARCHAR2(1),
  user_id        NUMBER(7),
  updated_by     NUMBER(7),
  updated_date   DATE,
  lang_id        NUMBER(7),
  lg_ip_mac      VARCHAR2(100),
  lg_ip_mac_upd  VARCHAR2(100),
  aut_v1         NVARCHAR2(100),
  aut_v2         NVARCHAR2(100),
  aut_v3         NVARCHAR2(100),
  aut_v4         NVARCHAR2(100),
  aut_v5         NVARCHAR2(100),
  aut_n1         NUMBER(15),
  aut_n2         NUMBER(15),
  aut_n3         NUMBER(15),
  aut_n4         NUMBER(15),
  aut_n5         NUMBER(15),
  aut_d1         DATE,
  aut_d2         DATE,
  aut_d3         DATE,
  aut_lo1        CHAR(1),
  aut_lo2        CHAR(1),
  aut_d4         DATE,
  aut_d5         DATE,
  dsgname_reg    NVARCHAR2(500),
  aut_by         NVARCHAR2(100),
  aut_date       DATE,
  centraleno     NVARCHAR2(100),
  action         VARCHAR2(1),
  dsgoname       VARCHAR2(50),
  lmoddate       DATE,
  aut_status     CHAR(1),
  h_status       VARCHAR2(1)
);  

prompt
prompt Creating table TB_DEPARTMENT
prompt ============================
prompt
create table TB_DEPARTMENT
(
  dp_deptid     NUMBER(12) not null,
  dp_deptcode   NVARCHAR2(4),
  dp_deptdesc   NVARCHAR2(400),
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(4) not null,
  lmoddate      DATE not null,
  status        NVARCHAR2(1),
  dp_name_mar   NVARCHAR2(2000),
  sub_dept_flg  CHAR(1),
  updated_by    NUMBER(7),
  updated_date  DATE,
  dp_smfid      NUMBER(12),
  dp_check      NVARCHAR2(1),
  dp_prefix     NVARCHAR2(3),
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100)
)
;
comment on column TB_DEPARTMENT.dp_deptid
  is 'Primary Key';
comment on column TB_DEPARTMENT.dp_deptcode
  is 'Code of the Department';
comment on column TB_DEPARTMENT.dp_deptdesc
  is 'Department Name';
comment on column TB_DEPARTMENT.user_id
  is 'User Identity';
comment on column TB_DEPARTMENT.lang_id
  is 'Language Identity';
comment on column TB_DEPARTMENT.lmoddate
  is 'Last Modification Date';
comment on column TB_DEPARTMENT.status
  is 'Status Active/Inactive';
comment on column TB_DEPARTMENT.dp_name_mar
  is 'Department Marathi Name';
comment on column TB_DEPARTMENT.lg_ip_mac
  is 'Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_DEPARTMENT.lg_ip_mac_upd
  is 'Updated Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
alter table TB_DEPARTMENT
  add constraint PK_DEPARTMENT_DP_DEPTID primary key (DP_DEPTID);
alter table TB_DEPARTMENT
  add constraint UK_DP_DEPTCODE unique (DP_DEPTCODE);

prompt
prompt Creating table TB_GROUP_MAST
prompt ============================
prompt
create table TB_GROUP_MAST
(
  gm_id         NUMBER not null,
  gr_code       NVARCHAR2(20),
  gr_name       NVARCHAR2(500),
  gr_name_reg   NVARCHAR2(500),
  gr_desc_eng   NVARCHAR2(500),
  gr_desc_reg   NVARCHAR2(1000),
  orgid         NUMBER,
  org_specific  CHAR(1) default 'N',
  gr_status     CHAR(1),
  lang_id       NUMBER,
  user_id       NUMBER,
  entry_date    DATE,
  lg_ip_mac     NVARCHAR2(100),
  updated_by    NUMBER,
  updated_date  DATE,
  lg_ip_mac_upd NVARCHAR2(100),
  gr_default    CHAR(1)
)
;
alter table TB_GROUP_MAST
  add constraint PK_TB_GROUP_MAST_GM_ID primary key (GM_ID);
alter table TB_GROUP_MAST
  add constraint FKEAC3F7466078ED5 foreign key (ORGID)
  references TB_ORGANISATION (ORGID);

prompt
prompt Creating table EMPLOYEE
prompt =======================
prompt
create table EMPLOYEE
(
  empid                NUMBER(12) not null,
  empname              NVARCHAR2(500) not null,
  emposloginname       NVARCHAR2(50),
  emploginname         NVARCHAR2(50),
  emppassword          NVARCHAR2(50),
  dsgid                NUMBER(12) not null,
  locid                NUMBER(12) not null,
  emppayrollnumber     NVARCHAR2(10),
  empisecuritykey      NVARCHAR2(70),
  emppiservername      NVARCHAR2(20),
  isdeleted            VARCHAR2(1),
  synoynmx             NUMBER,
  orgid                NUMBER(4) not null,
  user_id              NUMBER(7),
  lmoddate             DATE not null,
  updated_by           NUMBER(7),
  updated_date         DATE,
  lang_id              NUMBER(7),
  empemail             NVARCHAR2(50),
  empexpiredt          DATE,
  empphoto             BLOB,
  lock_unlock          VARCHAR2(1),
  logged_in            VARCHAR2(1),
  lg_ip_mac            VARCHAR2(100),
  lg_ip_mac_upd        VARCHAR2(100),
  aut_v1               NVARCHAR2(100),
  aut_v2               NVARCHAR2(100),
  aut_v3               NVARCHAR2(100),
  aut_v4               NVARCHAR2(100),
  aut_v5               NVARCHAR2(100),
  aut_n1               NUMBER(15),
  aut_n2               NUMBER(15),
  aut_n3               NUMBER(15),
  aut_n4               NUMBER(15),
  aut_n5               NUMBER(15),
  aut_d1               DATE,
  aut_d2               DATE,
  aut_d3               DATE,
  aut_lo1              CHAR(1),
  aut_lo2              CHAR(1),
  aut_lo3              CHAR(1),
  empnew               NUMBER(1),
  dp_deptid            NUMBER(12),
  empdob               DATE,
  empmobno             VARCHAR2(30),
  empphoneno           VARCHAR2(40),
  empuwmsowner         VARCHAR2(1),
  empregistry          VARCHAR2(1) default 'N',
  emprecord            VARCHAR2(1) default 'N',
  empnetwork           VARCHAR2(1) default 'N',
  empoutward           VARCHAR2(1) default 'N',
  aut_by               NUMBER(12),
  aut_date             DATE,
  centraleno           NVARCHAR2(50),
  scansignature        VARCHAR2(2000),
  aut_d4               DATE,
  aut_d5               DATE,
  empuid               NVARCHAR2(14),
  empuiddocpath        NVARCHAR2(2000),
  empphotopath         NVARCHAR2(2000),
  empuiddocname        NVARCHAR2(100),
  add_flag             VARCHAR2(1) default 'Y',
  emp_address          VARCHAR2(100),
  emp_address1         NVARCHAR2(2000),
  emppincode           NUMBER(6),
  auth_status          NVARCHAR2(1),
  aut_mob              CHAR(1) default 'N',
  cpd_ttl_id           NUMBER(15),
  emplname             NVARCHAR2(100),
  empmname             NVARCHAR2(100),
  empl_type            NUMBER(12),
  emp_gender           VARCHAR2(1),
  isuploaded           VARCHAR2(1) default 'N',
  emp_cor_add1         NVARCHAR2(2000),
  emp_cor_add2         NVARCHAR2(2000),
  emp_cor_pincode      NUMBER(6),
  aut_email            CHAR(1) default 'N',
  employee_no          VARCHAR2(15),
  agency_location      NVARCHAR2(500),
  gm_id                NUMBER,
  pan_no               VARCHAR2(10),
  signature            NVARCHAR2(100),
  publickey            NVARCHAR2(100),
  mob_otp              NVARCHAR2(100),
  last_loggedin        DATE,
  agency_name          VARCHAR2(500),
  agency_reg_no        VARCHAR2(20),
  agency_noof_exp      VARCHAR2(100),
  agency_qualification VARCHAR2(100)
)
;
comment on column EMPLOYEE.empid
  is 'Primary Key (Employee Id)';
comment on column EMPLOYEE.empname
  is 'Employee Name';
comment on column EMPLOYEE.emposloginname
  is 'Employee Login name';
comment on column EMPLOYEE.emploginname
  is 'Employee Login Name';
comment on column EMPLOYEE.emppassword
  is 'Employee password';
comment on column EMPLOYEE.dsgid
  is 'Designation Id';
comment on column EMPLOYEE.locid
  is 'Location Id';
comment on column EMPLOYEE.emppayrollnumber
  is 'Stores the payroll number of the employee
(Not in use)
';
comment on column EMPLOYEE.empisecuritykey
  is 'Stores the Ikey Security number for a particular key
(Not in use)
';
comment on column EMPLOYEE.emppiservername
  is 'Stores servername of the employee.
(Not in use)
';
comment on column EMPLOYEE.isdeleted
  is 'Flag to identify whether the record is deleted or not. 1 for deleted (Inactive) and 0 for not deleted (Active) record.';
comment on column EMPLOYEE.synoynmx
  is 'Synoynmx (Not in use)';
comment on column EMPLOYEE.orgid
  is 'Organisation Id';
comment on column EMPLOYEE.user_id
  is 'User Id';
comment on column EMPLOYEE.lmoddate
  is 'Creation date';
comment on column EMPLOYEE.updated_by
  is 'Updated by';
comment on column EMPLOYEE.updated_date
  is 'Updated date';
comment on column EMPLOYEE.lang_id
  is 'Language Id';
comment on column EMPLOYEE.empemail
  is 'Employee Email Id';
comment on column EMPLOYEE.empexpiredt
  is 'Employee login expiry date';
comment on column EMPLOYEE.empphoto
  is 'Employee Photo';
comment on column EMPLOYEE.lock_unlock
  is 'Flag to identify whether employee  is lock or Unlock. ''L'' for lock and ''U'' for unlock';
comment on column EMPLOYEE.logged_in
  is 'Flag to identify logged in status of employee. ''Y'' for logged in and ''N'' for not.';
comment on column EMPLOYEE.lg_ip_mac
  is 'Client Machine�s Login Name | IP Address | Physical Address';
comment on column EMPLOYEE.lg_ip_mac_upd
  is 'Updated Client Machine�s Login Name | IP Address | Physical Address';
comment on column EMPLOYEE.aut_v1
  is 'additional nvarchar2 aut_v1 to be used in future';
comment on column EMPLOYEE.aut_v2
  is 'additional nvarchar2 aut_v2 to be used in future';
comment on column EMPLOYEE.aut_v3
  is 'additional nvarchar2 aut_v3 to be used in future';
comment on column EMPLOYEE.aut_v4
  is 'additional nvarchar2 aut_v4 to be used in future';
comment on column EMPLOYEE.aut_v5
  is 'additional nvarchar2 aut_v5 to be used in future';
comment on column EMPLOYEE.aut_n1
  is 'additional nvarchar2 aut_n1 to be used in future';
comment on column EMPLOYEE.aut_n2
  is 'additional nvarchar2 aut_n2 to be used in future';
comment on column EMPLOYEE.aut_n3
  is 'additional nvarchar2 aut_n3 to be used in future';
comment on column EMPLOYEE.aut_n4
  is 'additional nvarchar2 aut_n4 to be used in future';
comment on column EMPLOYEE.aut_n5
  is 'additional nvarchar2 aut_n5 to be used in future';
comment on column EMPLOYEE.aut_d1
  is 'additional nvarchar2 aut_d1 to be used in future';
comment on column EMPLOYEE.aut_d2
  is 'additional nvarchar2 aut_d2 to be used in future';
comment on column EMPLOYEE.aut_d3
  is 'additional nvarchar2 aut_d3 to be used in future';
comment on column EMPLOYEE.aut_lo1
  is 'Additional Logical field AUT_LO1 to be used in future';
comment on column EMPLOYEE.aut_lo2
  is 'Additional Logical field AUT_LO2 to be used in future';
comment on column EMPLOYEE.aut_lo3
  is 'Additional Logical field AUT_LO3 to be used in future';
comment on column EMPLOYEE.empnew
  is 'Flag to identify whether login employee is new or old.';
comment on column EMPLOYEE.dp_deptid
  is 'Department Id';
comment on column EMPLOYEE.empdob
  is 'Employee Date of Birth';
comment on column EMPLOYEE.empmobno
  is 'Employee Mobile No';
comment on column EMPLOYEE.empphoneno
  is 'Employee Phone No';
comment on column EMPLOYEE.empuwmsowner
  is 'Flag to identify whether employee is UWMS Owner or not. ';
comment on column EMPLOYEE.aut_by
  is 'employee authorizationflag (approve- a/hold-h/reject-r)';
comment on column EMPLOYEE.aut_date
  is 'Authorisation Date';
comment on column EMPLOYEE.centraleno
  is 'server name,owner name';
comment on column EMPLOYEE.scansignature
  is 'Scan Signature Path';
comment on column EMPLOYEE.aut_d4
  is 'additional nvarchar2 aut_d4 to be used in future';
comment on column EMPLOYEE.aut_d5
  is 'additional nvarchar2 aut_d5 to be used in future';
comment on column EMPLOYEE.empuid
  is 'employee UID number';
comment on column EMPLOYEE.empuiddocpath
  is 'employee UID path';
comment on column EMPLOYEE.empphotopath
  is 'employee photo path';
comment on column EMPLOYEE.empuiddocname
  is 'employee UID file Name';
comment on column EMPLOYEE.add_flag
  is 'Flag to identify correspondance address same to Address Y/N';
comment on column EMPLOYEE.emp_address
  is 'EMPLOYEE ADDRESS';
comment on column EMPLOYEE.emp_address1
  is 'Employee Address 1  ';
comment on column EMPLOYEE.emppincode
  is 'Employee PIN Code ';
comment on column EMPLOYEE.auth_status
  is 'Employee AuthorizationFlag (Approve- A/Hold-H/Reject-R)';
comment on column EMPLOYEE.aut_mob
  is 'Validate Mobile number';
comment on column EMPLOYEE.cpd_ttl_id
  is 'title ';
comment on column EMPLOYEE.emplname
  is 'Last Name';
comment on column EMPLOYEE.empmname
  is 'Middle Name';
comment on column EMPLOYEE.empl_type
  is 'New Employee Category prefix NEC';
comment on column EMPLOYEE.emp_gender
  is 'GENDER M/F/T';
comment on column EMPLOYEE.isuploaded
  is 'Flag to identify whether Agency has uploaded the documents (default to N)';
comment on column EMPLOYEE.emp_cor_add1
  is 'Correspondence Address line1';
comment on column EMPLOYEE.emp_cor_add2
  is 'Correspondence Address line2';
comment on column EMPLOYEE.emp_cor_pincode
  is 'Correspondence address pincode';
comment on column EMPLOYEE.aut_email
  is 'Validate EMail address';
comment on column EMPLOYEE.agency_location
  is 'Agency Location';
comment on column EMPLOYEE.gm_id
  is 'Group Master Id';
comment on column EMPLOYEE.signature
  is 'Signature ';
comment on column EMPLOYEE.mob_otp
  is 'Mobile OTP';
comment on column EMPLOYEE.last_loggedin
  is 'Last Log in time date';
comment on column EMPLOYEE.agency_name
  is 'Agency Name ';
comment on column EMPLOYEE.agency_reg_no
  is 'Agency Registration number';
alter table EMPLOYEE
  add primary key (EMPID);
alter table EMPLOYEE
  add constraint FK_DEPTID_EMPLOYEE foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);
alter table EMPLOYEE
  add constraint FK_DSGID_EMPLOYEE foreign key (DSGID)
  references DESIGNATION (DSGID);
alter table EMPLOYEE
  add constraint FK_GM_ID_GROUP_MST foreign key (GM_ID)
  references TB_GROUP_MAST (GM_ID);
alter table EMPLOYEE
  add constraint FK_LOC_EMPLOEE foreign key (LOCID)
  references TB_LOCATION_MAS (LOC_ID);
alter table EMPLOYEE
  add constraint FK_ORGID_EMPLOYEE foreign key (ORGID)
  references TB_ORGANISATION (ORGID);
  
prompt
prompt Creating table EMPLOYEE_HIST
prompt ============================
prompt
create table EMPLOYEE_HIST
(
  h_empid              NUMBER(12) not null,
  empid                NUMBER(12) not null,
  empname              NVARCHAR2(500) not null,
  emposloginname       NVARCHAR2(50),
  emploginname         NVARCHAR2(50),
  emppassword          NVARCHAR2(50),
  dsgid                NUMBER(12) not null,
  locid                NUMBER(12) not null,
  emppayrollnumber     NVARCHAR2(10),
  empisecuritykey      NVARCHAR2(70),
  emppiservername      NVARCHAR2(20),
  isdeleted            VARCHAR2(1),
  synoynmx             NUMBER,
  orgid                NUMBER(4) not null,
  user_id              NUMBER(7),
  lmoddate             DATE not null,
  updated_by           NUMBER(7),
  updated_date         DATE,
  lang_id              NUMBER(7),
  empemail             NVARCHAR2(50),
  empexpiredt          DATE,
  empphoto             BLOB,
  lock_unlock          VARCHAR2(1),
  logged_in            VARCHAR2(1),
  lg_ip_mac            VARCHAR2(100),
  lg_ip_mac_upd        VARCHAR2(100),
  aut_v1               NVARCHAR2(100),
  aut_v2               NVARCHAR2(100),
  aut_v3               NVARCHAR2(100),
  aut_v4               NVARCHAR2(100),
  aut_v5               NVARCHAR2(100),
  aut_n1               NUMBER(15),
  aut_n2               NUMBER(15),
  aut_n3               NUMBER(15),
  aut_n4               NUMBER(15),
  aut_n5               NUMBER(15),
  aut_d1               DATE,
  aut_d2               DATE,
  aut_d3               DATE,
  aut_lo1              CHAR(1),
  aut_lo2              CHAR(1),
  aut_lo3              CHAR(1),
  empnew               NUMBER(1),
  dp_deptid            NUMBER(12),
  empdob               DATE,
  empmobno             VARCHAR2(30),
  empphoneno           VARCHAR2(40),
  empuwmsowner         VARCHAR2(1),
  empregistry          VARCHAR2(1) default 'N',
  emprecord            VARCHAR2(1) default 'N',
  empnetwork           VARCHAR2(1) default 'N',
  empoutward           VARCHAR2(1) default 'N',
  aut_by               NUMBER(12),
  aut_date             DATE,
  centraleno           NVARCHAR2(50),
  scansignature        VARCHAR2(2000),
  aut_d4               DATE,
  aut_d5               DATE,
  empuid               NVARCHAR2(14),
  empuiddocpath        NVARCHAR2(2000),
  empphotopath         NVARCHAR2(2000),
  empuiddocname        NVARCHAR2(100),
  add_flag             VARCHAR2(1) default 'Y',
  emp_address          VARCHAR2(100),
  emp_address1         NVARCHAR2(2000),
  emppincode           NUMBER(6),
  auth_status          NVARCHAR2(1),
  aut_mob              CHAR(1) default 'N',
  cpd_ttl_id           NUMBER(15),
  emplname             NVARCHAR2(100),
  empmname             NVARCHAR2(100),
  empl_type            NUMBER(12),
  emp_gender           VARCHAR2(1),
  isuploaded           VARCHAR2(1) default 'N',
  emp_cor_add1         NVARCHAR2(2000),
  emp_cor_add2         NVARCHAR2(2000),
  emp_cor_pincode      NUMBER(6),
  aut_email            CHAR(1) default 'N',
  employee_no          VARCHAR2(15),
  agency_location      NVARCHAR2(500),
  gm_id                NUMBER,
  pan_no               VARCHAR2(10),
  signature            NVARCHAR2(100),
  publickey            NVARCHAR2(100),
  mob_otp              NVARCHAR2(100),
  last_loggedin        DATE,
  agency_name          VARCHAR2(500),
  agency_reg_no        VARCHAR2(20),
  agency_noof_exp      VARCHAR2(100),
  agency_qualification VARCHAR2(100),
  h_status             NVARCHAR2(1)
)
;
alter table EMPLOYEE_HIST
  add constraint PK_H_EMPID primary key (H_EMPID);
  

prompt
prompt Creating table TB_SYSMODFUNCTION
prompt ================================
prompt
create table TB_SYSMODFUNCTION
(
  smfid          NUMBER(12) not null,
  smfname        NVARCHAR2(1000) not null,
  smfdescription NVARCHAR2(1000),
  smfflag        NVARCHAR2(1),
  smfaction      NVARCHAR2(200),
  smfcategory    NVARCHAR2(1),
  user_id        NUMBER(7) not null,
  ondate         DATE not null,
  ontime         VARCHAR2(12) not null,
  action         VARCHAR2(1),
  isdeleted      VARCHAR2(1),
  smfsystemid    NUMBER(12),
  smfcode        NVARCHAR2(255),
  updated_by     NUMBER(7),
  updated_date   DATE,
  lang_id        NUMBER(7),
  smfname_mar    NVARCHAR2(1000),
  smfsrno        NUMBER(3),
  lg_ip_mac      VARCHAR2(100),
  lg_ip_mac_upd  VARCHAR2(100),
  sm_parent_id   NUMBER(12),
  depth          NUMBER(1),
  sm_param1      NVARCHAR2(150),
  sm_param2      NVARCHAR2(150)
)
;
comment on column TB_SYSMODFUNCTION.sm_param1
  is 'Parameter value 1 if any';
comment on column TB_SYSMODFUNCTION.sm_param2
  is 'Parameter value 2 if any';
alter table TB_SYSMODFUNCTION
  add constraint PK_SMFID primary key (SMFID);

prompt
prompt Creating table ROLE_ENTITLEMENT
prompt ===============================
prompt
create table ROLE_ENTITLEMENT
(
  role_et_id   NUMBER not null,
  role_id      NUMBER,
  smfid        NUMBER,
  updated_by   NUMBER,
  updated_date DATE,
  is_active    NVARCHAR2(1),
  et_parent_id NUMBER,
  org_id       NUMBER,
  parent_id    NUMBER,
  bu_add       VARCHAR2(1),
  bu_edit      VARCHAR2(1),
  bu_delete    VARCHAR2(1)
)
;
alter table ROLE_ENTITLEMENT
  add constraint PK_ROLE_ET_ID primary key (ROLE_ET_ID);
alter table ROLE_ENTITLEMENT
  add constraint FKE45ED6C4511F3CC9 foreign key (ROLE_ID)
  references TB_GROUP_MAST (GM_ID);
alter table ROLE_ENTITLEMENT
  add constraint FKE45ED6C4C396A42C foreign key (ORG_ID)
  references TB_ORGANISATION (ORGID);
alter table ROLE_ENTITLEMENT
  add constraint FK_SMFID_SYSMODFUNCTION foreign key (SMFID)
  references TB_SYSMODFUNCTION (SMFID);
  
prompt
prompt Creating table ROLE_ENTITLEMENT_HIST
prompt ====================================
prompt
create table ROLE_ENTITLEMENT_HIST
(
  h_role_et_id NUMBER not null,
  role_et_id   NUMBER not null,
  role_id      NUMBER,
  smfid        NUMBER,
  updated_by   NUMBER,
  updated_date DATE,
  is_active    NVARCHAR2(1),
  et_parent_id NUMBER,
  org_id       NUMBER,
  parent_id    NUMBER,
  dp_deptid    NUMBER,
  h_status     NVARCHAR2(1)
)
;
alter table ROLE_ENTITLEMENT_HIST
  add constraint PK_H_ROLE_ET_ID primary key (H_ROLE_ET_ID);  

prompt
prompt Creating table TB_CUSTBANKS_MAS
prompt ===============================
prompt
create table TB_CUSTBANKS_MAS
(
  cm_bankid     NUMBER(12) not null,
  cm_bankname   NVARCHAR2(500) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  lmoddate      DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100)
)
;
alter table TB_CUSTBANKS_MAS
  add constraint PK_CUSTBANKS_MAS_BNKID primary key (CM_BANKID);

prompt
prompt Creating table TB_CUSTBANKS
prompt ===========================
prompt
create table TB_CUSTBANKS
(
  cb_bankid     NUMBER(12) not null,
  cb_bankcode   NUMBER(12) not null,
  cb_bankname   NVARCHAR2(500) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  lmoddate      DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  cb_branchname NVARCHAR2(500),
  cb_city       NVARCHAR2(100),
  cb_address    NVARCHAR2(250),
  cm_bankid     NUMBER(12),
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  pg_flag       CHAR(2) default 'N',
  orgid         NUMBER(4)
)
;
comment on column TB_CUSTBANKS.cb_bankid
  is 'Primary Key';
comment on column TB_CUSTBANKS.cb_bankcode
  is 'Bank Code';
comment on column TB_CUSTBANKS.cb_bankname
  is 'Name of the Bank';
comment on column TB_CUSTBANKS.user_id
  is 'User Identity';
comment on column TB_CUSTBANKS.lang_id
  is 'Language Identity';
comment on column TB_CUSTBANKS.lmoddate
  is 'Last Modification Date';
comment on column TB_CUSTBANKS.updated_by
  is 'Updated by';
comment on column TB_CUSTBANKS.updated_date
  is 'Updated Date';
comment on column TB_CUSTBANKS.cb_branchname
  is 'Branch name';
comment on column TB_CUSTBANKS.cb_city
  is 'City name';
comment on column TB_CUSTBANKS.cb_address
  is 'Address';
comment on column TB_CUSTBANKS.pg_flag
  is 'Payment Gateway Flag';
alter table TB_CUSTBANKS
  add constraint PK_CUSTBANKS_BNKID primary key (CB_BANKID);
alter table TB_CUSTBANKS
  add constraint FK_CUSTBANKS_CB_BANKID foreign key (CM_BANKID)
  references TB_CUSTBANKS_MAS (CM_BANKID);

prompt
prompt Creating table TB_BANKMASTER
prompt ============================
prompt
create table TB_BANKMASTER
(
  bm_bankid         NUMBER(12) not null,
  bm_bankcode       NVARCHAR2(6) not null,
  orgid             NUMBER(4) not null,
  bm_bankname       NVARCHAR2(200) not null,
  bm_bankbranch     NVARCHAR2(100),
  bm_bankaddress    NVARCHAR2(200),
  bm_bankcontactnos NVARCHAR2(100),
  bm_bankemail      NVARCHAR2(100),
  user_id           NUMBER(7) not null,
  lang_id           NUMBER(7) not null,
  lmoddate          DATE not null,
  bm_status         NVARCHAR2(1),
  dwz_id            NUMBER(12),
  updated_by        NUMBER(7),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100),
  cb_bankid         NUMBER(12),
  fi04_n2           NUMBER(15),
  fi04_n3           NUMBER(15),
  fi04_n4           NUMBER(15),
  fi04_n5           NUMBER(15),
  fi04_v1           NVARCHAR2(100),
  fi04_v2           NVARCHAR2(100),
  fi04_v3           NVARCHAR2(100),
  fi04_v4           NVARCHAR2(100),
  fi04_v5           NVARCHAR2(100),
  fi04_d1           DATE,
  fi04_d2           DATE,
  fi04_d3           DATE,
  fi04_lo1          CHAR(1),
  fi04_lo2          CHAR(1),
  fi04_lo3          CHAR(1)
)
;
comment on table TB_BANKMASTER
  is 'Table is used to store Banks Information';
comment on column TB_BANKMASTER.bm_bankid
  is 'Generated Id';
comment on column TB_BANKMASTER.bm_bankcode
  is 'Bank Code';
comment on column TB_BANKMASTER.orgid
  is 'Organization Id';
comment on column TB_BANKMASTER.bm_bankname
  is 'Name of the Bank';
comment on column TB_BANKMASTER.bm_bankbranch
  is 'Branch of the Bank';
comment on column TB_BANKMASTER.bm_bankaddress
  is 'Address of the Bank';
comment on column TB_BANKMASTER.bm_bankcontactnos
  is 'Contace Nos.';
comment on column TB_BANKMASTER.bm_bankemail
  is 'Email Id';
comment on column TB_BANKMASTER.user_id
  is 'User Id';
comment on column TB_BANKMASTER.lang_id
  is 'Language id';
comment on column TB_BANKMASTER.lmoddate
  is 'Last Modification Date';
comment on column TB_BANKMASTER.bm_status
  is 'Status';
comment on column TB_BANKMASTER.dwz_id
  is 'Ward Id';
comment on column TB_BANKMASTER.updated_by
  is 'User id who update the data';
comment on column TB_BANKMASTER.updated_date
  is 'Date on which data is going to update';
comment on column TB_BANKMASTER.lg_ip_mac
  is 'Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_BANKMASTER.lg_ip_mac_upd
  is 'Updated Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_BANKMASTER.cb_bankid
  is 'Customer Bank Reference key';
comment on column TB_BANKMASTER.fi04_n2
  is 'Additional number FI04_N2 to be used in future';
comment on column TB_BANKMASTER.fi04_n3
  is 'Additional number FI04_N3 to be used in future';
comment on column TB_BANKMASTER.fi04_n4
  is 'Additional number FI04_N4 to be used in future';
comment on column TB_BANKMASTER.fi04_n5
  is 'Additional number FI04_N5 to be used in future';
comment on column TB_BANKMASTER.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_BANKMASTER.fi04_v2
  is 'Additional nvarchar2 FI04_V2 to be used in future';
comment on column TB_BANKMASTER.fi04_v3
  is 'Additional nvarchar2 FI04_V3 to be used in future';
comment on column TB_BANKMASTER.fi04_v4
  is 'Additional nvarchar2 FI04_V4 to be used in future';
comment on column TB_BANKMASTER.fi04_v5
  is 'Additional nvarchar2 FI04_V5 to be used in future';
comment on column TB_BANKMASTER.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_BANKMASTER.fi04_d2
  is 'Additional Date FI04_D2 to be used in future';
comment on column TB_BANKMASTER.fi04_d3
  is 'Additional Date FI04_D3 to be used in future';
comment on column TB_BANKMASTER.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
comment on column TB_BANKMASTER.fi04_lo2
  is 'Additional Logical field FI04_LO2 to be used in future';
comment on column TB_BANKMASTER.fi04_lo3
  is 'Additional Logical field FI04_LO3 to be used in future';
alter table TB_BANKMASTER
  add constraint PK_TB_BANKMASTER primary key (BM_BANKID, ORGID);
alter table TB_BANKMASTER
  add constraint FK_BANKMASTER_CB_BANKID foreign key (CB_BANKID)
  references TB_CUSTBANKS (CB_BANKID);

prompt
prompt Creating table TB_BANKACCOUNT
prompt =============================
prompt
create table TB_BANKACCOUNT
(
  ba_accountid     NUMBER(12) not null,
  ba_accountcode   NVARCHAR2(25) not null,
  orgid            NUMBER(4) not null,
  bm_bankid        NUMBER(12) not null,
  cpd_accounttype  NUMBER(12) not null,
  ba_accountname   NVARCHAR2(150) not null,
  ba_currentdate   DATE,
  ba_status        NVARCHAR2(1),
  user_id          NUMBER(7) not null,
  lang_id          NUMBER(7) not null,
  lmoddate         DATE not null,
  updated_by       NUMBER(7),
  updated_date     DATE,
  fu_obj_id        NUMBER(12),
  lg_ip_mac        VARCHAR2(100),
  lg_ip_mac_upd    VARCHAR2(100),
  fi04_n1          NUMBER(15),
  fi04_n2          NUMBER(15),
  fi04_n3          NUMBER(15),
  fi04_n4          NUMBER(15),
  fi04_n5          NUMBER(15),
  fi04_v1          NVARCHAR2(100),
  fi04_v2          NVARCHAR2(100),
  fi04_v3          NVARCHAR2(100),
  fi04_v4          NVARCHAR2(100),
  fi04_v5          NVARCHAR2(100),
  fi04_d1          DATE,
  fi04_d2          DATE,
  fi04_d3          DATE,
  fi04_lo1         CHAR(1),
  fi04_lo2         CHAR(1),
  fi04_lo3         CHAR(1),
  ac_cpd_id        NUMBER(12),
  app_challan_flag CHAR(1)
)
;
comment on table TB_BANKACCOUNT
  is 'This table stores all the details of bank account prevails in each bank of KDMC.';
comment on column TB_BANKACCOUNT.ba_accountid
  is 'Primary Key';
comment on column TB_BANKACCOUNT.ba_accountcode
  is 'Unique Account Code';
comment on column TB_BANKACCOUNT.orgid
  is 'Organization Id';
comment on column TB_BANKACCOUNT.bm_bankid
  is 'Code of the bank in which account exists coming from Bank Master';
comment on column TB_BANKACCOUNT.cpd_accounttype
  is 'Type of account with each Bank C-Current,R-Recurring,S-Saving,F-Fixed';
comment on column TB_BANKACCOUNT.ba_accountname
  is 'Account Name';
comment on column TB_BANKACCOUNT.ba_currentdate
  is 'Current Date of balance';
comment on column TB_BANKACCOUNT.ba_status
  is 'Record status';
comment on column TB_BANKACCOUNT.user_id
  is 'User Identity';
comment on column TB_BANKACCOUNT.lang_id
  is 'Language Identity';
comment on column TB_BANKACCOUNT.lmoddate
  is 'Last Modification Date';
comment on column TB_BANKACCOUNT.updated_by
  is 'User id who update the data';
comment on column TB_BANKACCOUNT.updated_date
  is 'Date on which data is going to update';
comment on column TB_BANKACCOUNT.fu_obj_id
  is 'Function Id';
comment on column TB_BANKACCOUNT.lg_ip_mac
  is 'Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_BANKACCOUNT.lg_ip_mac_upd
  is 'Updated Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_BANKACCOUNT.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_BANKACCOUNT.fi04_n2
  is 'Additional number FI04_N2 to be used in future';
comment on column TB_BANKACCOUNT.fi04_n3
  is 'Additional number FI04_N3 to be used in future';
comment on column TB_BANKACCOUNT.fi04_n4
  is 'Additional number FI04_N4 to be used in future';
comment on column TB_BANKACCOUNT.fi04_n5
  is 'Additional number FI04_N5 to be used in future';
comment on column TB_BANKACCOUNT.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_BANKACCOUNT.fi04_v2
  is 'Additional nvarchar2 FI04_V2 to be used in future';
comment on column TB_BANKACCOUNT.fi04_v3
  is 'Additional nvarchar2 FI04_V3 to be used in future';
comment on column TB_BANKACCOUNT.fi04_v4
  is 'Additional nvarchar2 FI04_V4 to be used in future';
comment on column TB_BANKACCOUNT.fi04_v5
  is 'Additional nvarchar2 FI04_V5 to be used in future';
comment on column TB_BANKACCOUNT.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_BANKACCOUNT.fi04_d2
  is 'Additional Date FI04_D2 to be used in future';
comment on column TB_BANKACCOUNT.fi04_d3
  is 'Additional Date FI04_D3 to be used in future';
comment on column TB_BANKACCOUNT.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
comment on column TB_BANKACCOUNT.fi04_lo2
  is 'Additional Logical field FI04_LO2 to be used in future';
comment on column TB_BANKACCOUNT.fi04_lo3
  is 'Additional Logical field FI04_LO3 to be used in future';
comment on column TB_BANKACCOUNT.ac_cpd_id
  is 'CPD id';
comment on column TB_BANKACCOUNT.app_challan_flag
  is 'bank for challan';
alter table TB_BANKACCOUNT
  add constraint PK_BANKACCOUNT_ACCOUNTID primary key (BA_ACCOUNTID, ORGID);
alter table TB_BANKACCOUNT
  add constraint PK_BANKACCOUNT_ACCOUNTCODE unique (BA_ACCOUNTCODE, ORGID);
alter table TB_BANKACCOUNT
  add constraint FK_BANKACCOUNT_BANKID foreign key (BM_BANKID, ORGID)
  references TB_BANKMASTER (BM_BANKID, ORGID);

prompt
prompt Creating table TB_BANKACCOUNT_HIST
prompt ==================================
prompt
create table TB_BANKACCOUNT_HIST
(
  h_baaccountid    NUMBER(12) not null,
  ba_accountid     NUMBER(12) not null,
  ba_accountcode   NVARCHAR2(25) not null,
  orgid            NUMBER(4) not null,
  bm_bankid        NUMBER(12) not null,
  cpd_accounttype  NUMBER(12) not null,
  ba_accountname   NVARCHAR2(150) not null,
  ba_currentdate   DATE,
  ba_status        NVARCHAR2(1),
  user_id          NUMBER(7) not null,
  lang_id          NUMBER(7) not null,
  lmoddate         DATE not null,
  updated_by       NUMBER(7),
  updated_date     DATE,
  fu_obj_id        NUMBER(12),
  lg_ip_mac        VARCHAR2(100),
  lg_ip_mac_upd    VARCHAR2(100),
  fi04_n1          NUMBER(15),
  fi04_n2          NUMBER(15),
  fi04_n3          NUMBER(15),
  fi04_n4          NUMBER(15),
  fi04_n5          NUMBER(15),
  fi04_v1          NVARCHAR2(100),
  fi04_v2          NVARCHAR2(100),
  fi04_v3          NVARCHAR2(100),
  fi04_v4          NVARCHAR2(100),
  fi04_v5          NVARCHAR2(100),
  fi04_d1          DATE,
  fi04_d2          DATE,
  fi04_d3          DATE,
  fi04_lo1         CHAR(1),
  fi04_lo2         CHAR(1),
  fi04_lo3         CHAR(1),
  ac_cpd_id        NUMBER(12),
  app_challan_flag CHAR(1),
  h_status         NVARCHAR2(1)
)
;
alter table TB_BANKACCOUNT_HIST
  add constraint PK_H_BAACCOUNTID primary key (H_BAACCOUNTID);

prompt
prompt Creating table TB_BANKMASTER_HIST
prompt =================================
prompt
create table TB_BANKMASTER_HIST
(
  h_bmbankid        NUMBER(12) not null,
  bm_bankid         NUMBER(12) not null,
  bm_bankcode       NVARCHAR2(6) not null,
  orgid             NUMBER(4) not null,
  bm_bankname       NVARCHAR2(200) not null,
  bm_bankbranch     NVARCHAR2(100),
  bm_bankaddress    NVARCHAR2(200),
  bm_bankcontactnos NVARCHAR2(100),
  bm_bankemail      NVARCHAR2(100),
  user_id           NUMBER(7) not null,
  lang_id           NUMBER(7) not null,
  lmoddate          DATE not null,
  bm_status         NVARCHAR2(1),
  dwz_id            NUMBER(12),
  updated_by        NUMBER(7),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100),
  cb_bankid         NUMBER(12),
  fi04_n2           NUMBER(15),
  fi04_n3           NUMBER(15),
  fi04_n4           NUMBER(15),
  fi04_n5           NUMBER(15),
  fi04_v1           NVARCHAR2(100),
  fi04_v2           NVARCHAR2(100),
  fi04_v3           NVARCHAR2(100),
  fi04_v4           NVARCHAR2(100),
  fi04_v5           NVARCHAR2(100),
  fi04_d1           DATE,
  fi04_d2           DATE,
  fi04_d3           DATE,
  fi04_lo1          CHAR(1),
  fi04_lo2          CHAR(1),
  fi04_lo3          CHAR(1),
  h_status          NVARCHAR2(1)
)
;
alter table TB_BANKMASTER_HIST
  add constraint PK_H_BMBANKID primary key (H_BMBANKID);

prompt
prompt Creating table TB_CM_ONL_TRAN_MAS_PORTAL
prompt ========================================
prompt
create table TB_CM_ONL_TRAN_MAS_PORTAL
(
  tran_cm_id            NUMBER(12) not null,
  apm_application_id    NUMBER(16),
  apm_application_date  DATE,
  sm_service_id         NUMBER(12),
  cfc_mode              CHAR(5),
  t_desc                NVARCHAR2(500),
  send_url              VARCHAR2(100) not null,
  send_key              VARCHAR2(50) not null,
  send_amount           NUMBER not null,
  send_productinfo      VARCHAR2(200) not null,
  send_firstname        NVARCHAR2(500) not null,
  send_email            NVARCHAR2(200),
  send_phone            VARCHAR2(20),
  send_surl             VARCHAR2(500) not null,
  send_furl             VARCHAR2(500) not null,
  send_salt             VARCHAR2(100) not null,
  send_hash             VARCHAR2(4000) not null,
  recv_status           VARCHAR2(20),
  recv_bank_ref_num     VARCHAR2(100),
  recv_mihpayid         VARCHAR2(500),
  recv_net_amount_debit VARCHAR2(100),
  recv_errm             VARCHAR2(1000),
  recv_mode             VARCHAR2(10),
  orgid                 NUMBER(4) not null,
  user_id               NUMBER(7) not null,
  lang_id               NUMBER(7) not null,
  lmoddate              DATE not null,
  updated_by            NUMBER(7),
  updated_date          DATE,
  lg_ip_mac             VARCHAR2(100),
  lg_ip_mac_upd         VARCHAR2(100),
  pg_type               VARCHAR2(30),
  pg_source             NVARCHAR2(30),
  recv_hash             VARCHAR2(4000),
  finyear               NUMBER,
  form_name             VARCHAR2(50),
  java_home             VARCHAR2(500),
  redirect_url          VARCHAR2(500),
  menuprm1              VARCHAR2(5),
  menuprm2              VARCHAR2(5),
  form_post             CHAR(1),
  call_from             CHAR(1),
  trans_status          VARCHAR2(1)
)
;
comment on table TB_CM_ONL_TRAN_MAS_PORTAL
  is 'This table is used to capture data before calling payment gateway';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.tran_cm_id
  is 'Primary Key  ';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.apm_application_id
  is 'Application id  ';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.apm_application_date
  is 'Application Date  ';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.sm_service_id
  is 'Service ID  ';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.cfc_mode
  is 'cfc mode  ';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.t_desc
  is 'Any description can be entered ';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.send_url
  is 'Payment gateway calling url';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.send_key
  is 'Company Key id ';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.send_amount
  is 'Amount send for';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.send_productinfo
  is 'Product information';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.send_firstname
  is 'First Name';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.send_email
  is 'Email Address';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.send_phone
  is 'Phone number';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.send_surl
  is 'Success url sent';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.send_furl
  is 'Failure Url sent';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.send_salt
  is 'Salt id of the company';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.send_hash
  is 'Hash generated and sent';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.recv_status
  is 'Status received by payment gateway ';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.recv_bank_ref_num
  is 'Bank Reference number received';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.recv_mihpayid
  is 'PAyment Id sent by Payment Gateway  ';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.recv_net_amount_debit
  is 'Actual net amount debited';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.recv_errm
  is 'ANy error received';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.recv_mode
  is 'PAyment mode received';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.orgid
  is 'Organisation ID ';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.user_id
  is 'USer id';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.lang_id
  is 'LAnguage ID ';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.lmoddate
  is 'LAst modification date';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.updated_by
  is 'UPdated by ';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.updated_date
  is 'UPdated Date';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.lg_ip_mac
  is 'complete machine address of the end user';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.lg_ip_mac_upd
  is 'UPdate machine address of the end user';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.pg_type
  is 'Payment Gate way type';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.pg_source
  is 'Payment Gateway Source';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.recv_hash
  is 'Received hash key';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.finyear
  is 'Financial Year';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.form_name
  is 'Form from where it is called';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.java_home
  is 'Java home page url';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.redirect_url
  is 'ON success redirect for final posting of data';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.menuprm1
  is 'Menu praram 1 of the form';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.menuprm2
  is 'Menu praram 2 of the form';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.form_post
  is 'N if the form is not called and Y if the transaction is called after payu';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.call_from
  is 'D for oracle forms and J for java';
alter table TB_CM_ONL_TRAN_MAS_PORTAL
  add constraint PK_TRAN_CM_ID primary key (TRAN_CM_ID, ORGID);
alter table TB_CM_ONL_TRAN_MAS_PORTAL
  add constraint FKBB441DC138B65205 foreign key (UPDATED_BY)
  references EMPLOYEE (EMPID);
alter table TB_CM_ONL_TRAN_MAS_PORTAL
  add constraint FKBB441DC16078ED5 foreign key (ORGID)
  references TB_ORGANISATION (ORGID);

prompt
prompt Creating table TB_COMPARAM_MAS
prompt ==============================
prompt
create table TB_COMPARAM_MAS
(
  cpm_id             NUMBER(12) not null,
  cpm_prefix         NVARCHAR2(3) not null,
  cpm_desc           NVARCHAR2(200) not null,
  cpm_status         NVARCHAR2(1) not null,
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(4) not null,
  lmoddate           DATE not null,
  cpm_limited_yn     NVARCHAR2(1),
  cpm_module_name    NVARCHAR2(10),
  updated_by         NUMBER(7),
  updated_date       DATE,
  cpm_config         CHAR(1),
  cpm_edit           VARCHAR2(50),
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  cpm_replicate_flag CHAR(1) default 'Y',
  cpm_type           CHAR(1),
  load_at_startup    CHAR(1) default 'N',
  cpm_edit_desc      VARCHAR2(1),
  cpm_edit_value     VARCHAR2(1),
  cpm_edit_oth       VARCHAR2(1),
  cpd_edit_default   VARCHAR2(1) default 'N',
  cpd_edit_status    VARCHAR2(1)
)
;
comment on table TB_COMPARAM_MAS
  is 'Common parameter Master';
comment on column TB_COMPARAM_MAS.cpm_id
  is 'Common Parameter Identity';
comment on column TB_COMPARAM_MAS.cpm_prefix
  is 'Prefix of the type';
comment on column TB_COMPARAM_MAS.cpm_desc
  is 'Description of the parameter';
comment on column TB_COMPARAM_MAS.cpm_status
  is 'Status of the parameter';
comment on column TB_COMPARAM_MAS.user_id
  is 'User Identity';
comment on column TB_COMPARAM_MAS.lang_id
  is 'Language Identity';
comment on column TB_COMPARAM_MAS.lmoddate
  is 'Last Modification Date';
comment on column TB_COMPARAM_MAS.updated_by
  is 'Last Updated By';
comment on column TB_COMPARAM_MAS.updated_date
  is 'Last Updated Date';
comment on column TB_COMPARAM_MAS.cpm_config
  is 'Allow Add in Detail';
comment on column TB_COMPARAM_MAS.cpm_edit
  is 'Allow Edit in Detail';
comment on column TB_COMPARAM_MAS.load_at_startup
  is 'Should load at server startup?';
alter table TB_COMPARAM_MAS
  add constraint PK_CPM_ID primary key (CPM_ID);

prompt
prompt Creating table TB_COMPARAM_DET
prompt ==============================
prompt
create table TB_COMPARAM_DET
(
  cpd_id        NUMBER(12) not null,
  orgid         NUMBER(4) not null,
  cpd_desc      NVARCHAR2(200) not null,
  cpd_value     NVARCHAR2(200),
  cpd_status    NVARCHAR2(1) not null,
  cpm_id        NUMBER(12) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(4) not null,
  lmoddate      DATE not null,
  cpd_default   CHAR(1),
  updated_by    NUMBER(7),
  updated_date  DATE,
  cpd_desc_mar  NVARCHAR2(270),
  cpd_others    VARCHAR2(60),
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100)
)
;
comment on table TB_COMPARAM_DET
  is 'Common Parameter Detail';
comment on column TB_COMPARAM_DET.cpd_id
  is 'Common Parameter Identity';
comment on column TB_COMPARAM_DET.orgid
  is 'Organization Id';
comment on column TB_COMPARAM_DET.cpd_desc
  is 'Name of the parameter';
comment on column TB_COMPARAM_DET.cpd_value
  is 'Type of Parameter';
comment on column TB_COMPARAM_DET.cpd_status
  is 'Status';
comment on column TB_COMPARAM_DET.cpm_id
  is 'tb_comparam_mast table entry id';
comment on column TB_COMPARAM_DET.user_id
  is 'User Identity';
comment on column TB_COMPARAM_DET.lang_id
  is 'Language Identity';
comment on column TB_COMPARAM_DET.lmoddate
  is 'Last Modification Date';
comment on column TB_COMPARAM_DET.cpd_others
  is 'Extra field';
alter table TB_COMPARAM_DET
  add constraint PK_CPD_ID primary key (CPD_ID);
alter table TB_COMPARAM_DET
  add constraint FK_CPD_CPM_ID foreign key (CPM_ID)
  references TB_COMPARAM_MAS (CPM_ID);

prompt
prompt Creating table TB_COMPARAM_DET_HIST
prompt ===================================
prompt
create table TB_COMPARAM_DET_HIST
(
  h_cpdid       NUMBER(12) not null,
  cpd_id        NUMBER(12) not null,
  orgid         NUMBER(4) not null,
  cpd_desc      NVARCHAR2(200) not null,
  cpd_value     NVARCHAR2(200),
  cpd_status    NVARCHAR2(1) not null,
  cpm_id        NUMBER(12) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(4) not null,
  lmoddate      DATE not null,
  cpd_default   CHAR(1),
  updated_by    NUMBER(7),
  updated_date  DATE,
  cpd_desc_mar  NVARCHAR2(270),
  cpd_others    VARCHAR2(60),
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  h_status      NVARCHAR2(1)
)
;
alter table TB_COMPARAM_DET_HIST
  add constraint PK_H_CPDID primary key (H_CPDID);

prompt
prompt Creating table TB_COMPARAM_MAS_HIST
prompt ===================================
prompt
create table TB_COMPARAM_MAS_HIST
(
  h_cpmid            NUMBER(12) not null,
  cpm_id             NUMBER(12) not null,
  cpm_prefix         NVARCHAR2(3) not null,
  cpm_desc           NVARCHAR2(200) not null,
  cpm_status         NVARCHAR2(1) not null,
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(4) not null,
  lmoddate           DATE not null,
  cpm_limited_yn     NVARCHAR2(1),
  cpm_module_name    NVARCHAR2(10),
  updated_by         NUMBER(7),
  updated_date       DATE,
  cpm_config         CHAR(1),
  cpm_edit           VARCHAR2(50),
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  cpm_replicate_flag CHAR(1) default 'Y',
  cpm_type           CHAR(1),
  cpm_edit_desc      VARCHAR2(1),
  cpm_edit_value     VARCHAR2(1),
  cpm_edit_oth       VARCHAR2(1),
  load_at_startup    CHAR(1) default 'N',
  cpd_edit_default   VARCHAR2(1) default 'N',
  cpd_edit_status    VARCHAR2(1),
  h_status           VARCHAR2(1)
)
;
alter table TB_COMPARAM_MAS_HIST
  add constraint PK_H_CPMID primary key (H_CPMID);

prompt
prompt Creating table TB_COMPARENT_MAS
prompt ===============================
prompt
create table TB_COMPARENT_MAS
(
  com_id             NUMBER(12) not null,
  cpm_id             NUMBER(12) not null,
  com_desc           NVARCHAR2(200) not null,
  com_value          NVARCHAR2(200) not null,
  com_level          NUMBER(3) not null,
  com_status         NVARCHAR2(1),
  orgid              NUMBER(4) not null,
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(7) not null,
  lmoddate           DATE not null,
  updated_by         NUMBER(7),
  updated_date       DATE,
  com_desc_mar       NVARCHAR2(400),
  com_replicate_flag CHAR(1) default 'N',
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100)
)
;
comment on table TB_COMPARENT_MAS
  is 'COMMON TABLE USED TO CREATE PARENT CHILD RELATIONSHIP';
comment on column TB_COMPARENT_MAS.com_id
  is 'PRIMARY KEY';
comment on column TB_COMPARENT_MAS.cpm_id
  is 'CPM ID OF TB_COMPARAM_MAS TABLE';
comment on column TB_COMPARENT_MAS.com_desc
  is 'DESCRIPTION OF PARENT';
comment on column TB_COMPARENT_MAS.com_value
  is 'SHORT DESCRIPTION OR PREFIX OF PARENT';
comment on column TB_COMPARENT_MAS.com_level
  is 'LEVEL ON WHICH EACH RECORD RELATES TO EACH OTHER';
comment on column TB_COMPARENT_MAS.com_status
  is 'Record status';
comment on column TB_COMPARENT_MAS.orgid
  is 'ORGANISATION ID';
comment on column TB_COMPARENT_MAS.user_id
  is 'USER ID';
comment on column TB_COMPARENT_MAS.lang_id
  is 'LANGUAGE ID';
comment on column TB_COMPARENT_MAS.lmoddate
  is 'CREATION DATE';
comment on column TB_COMPARENT_MAS.updated_by
  is 'UPDATED BY USER ID';
comment on column TB_COMPARENT_MAS.updated_date
  is 'UPDATION DATE';
alter table TB_COMPARENT_MAS
  add constraint PK_PDCOMID primary key (COM_ID);
alter table TB_COMPARENT_MAS
  add constraint FK_CPMID foreign key (CPM_ID)
  references TB_COMPARAM_MAS (CPM_ID);

prompt
prompt Creating table TB_COMPARENT_DET
prompt ===============================
prompt
create table TB_COMPARENT_DET
(
  cod_id        NUMBER(12) not null,
  com_id        NUMBER(12) not null,
  cod_desc      NVARCHAR2(200) not null,
  cod_value     NVARCHAR2(200) not null,
  parent_id     NUMBER(12),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  lmoddate      DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  cpd_default   CHAR(1) default 'N',
  cod_status    NVARCHAR2(1) default 'Y',
  cod_desc_mar  NVARCHAR2(400),
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100)
)
;
comment on table TB_COMPARENT_DET
  is 'COMMON TABLE USED TO CREATE PARENT CHILD RELATIONSHIP. THIS IS A CHILD TABLE';
comment on column TB_COMPARENT_DET.cod_id
  is 'Primary Key';
comment on column TB_COMPARENT_DET.com_id
  is 'COM ID OF TB_COMPARENT_DET TABLE(FK)';
comment on column TB_COMPARENT_DET.cod_desc
  is 'DESCRIPTION OF CHILD';
comment on column TB_COMPARENT_DET.cod_value
  is 'REGIONAL DESCRIPTION OF CHILD';
comment on column TB_COMPARENT_DET.parent_id
  is 'PARENT ID IF PRESENT TO RELATES EACH RECORD WITH OTHERS';
comment on column TB_COMPARENT_DET.orgid
  is 'ORGANISATION ID';
comment on column TB_COMPARENT_DET.user_id
  is 'USER ID';
comment on column TB_COMPARENT_DET.lang_id
  is 'LANGUAGE ID';
comment on column TB_COMPARENT_DET.lmoddate
  is 'CREATION DATE';
comment on column TB_COMPARENT_DET.updated_by
  is 'UPDATED BY USER ID';
comment on column TB_COMPARENT_DET.updated_date
  is 'UPDATION DATE';
comment on column TB_COMPARENT_DET.cod_status
  is 'Status of the record';
alter table TB_COMPARENT_DET
  add constraint PK_PDCODID primary key (COD_ID);
alter table TB_COMPARENT_DET
  add constraint FK_PDCODID foreign key (COM_ID)
  references TB_COMPARENT_MAS (COM_ID);
alter table TB_COMPARENT_DET
  add constraint FK_PDPRNTID foreign key (PARENT_ID)
  references TB_COMPARENT_DET (COD_ID);

prompt
prompt Creating table TB_COMPARENT_DET_HIST
prompt ====================================
prompt
create table TB_COMPARENT_DET_HIST
(
  h_codid       NUMBER(12) not null,
  cod_id        NUMBER(12) not null,
  com_id        NUMBER(12) not null,
  cod_desc      NVARCHAR2(200) not null,
  cod_value     NVARCHAR2(200) not null,
  parent_id     NUMBER(12),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  lmoddate      DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  cpd_default   CHAR(1) default 'N',
  cod_status    NVARCHAR2(1) default 'Y',
  cod_desc_mar  NVARCHAR2(400),
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  h_status      NVARCHAR2(1)
)
;
alter table TB_COMPARENT_DET_HIST
  add constraint PK_HCODID primary key (H_CODID);

prompt
prompt Creating table TB_COMPARENT_MAS_HIST
prompt ====================================
prompt
create table TB_COMPARENT_MAS_HIST
(
  h_comid            NUMBER(12) not null,
  com_id             NUMBER(12) not null,
  cpm_id             NUMBER(12) not null,
  com_desc           NVARCHAR2(200) not null,
  com_value          NVARCHAR2(200) not null,
  com_level          NUMBER(3) not null,
  com_status         NVARCHAR2(1),
  orgid              NUMBER(4) not null,
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(7) not null,
  lmoddate           DATE not null,
  updated_by         NUMBER(7),
  updated_date       DATE,
  com_desc_mar       NVARCHAR2(400),
  com_replicate_flag CHAR(1) default 'N',
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  h_status           NVARCHAR2(1)
)
;
alter table TB_COMPARENT_MAS_HIST
  add constraint PK_H_COMID primary key (H_COMID);

prompt
prompt Creating table TB_COM_HELP_DOCS
prompt ===============================
prompt
create table TB_COM_HELP_DOCS
(
  help_doc_id   NUMBER(12) not null,
  module_name   VARCHAR2(200) not null,
  file_name_eng VARCHAR2(100) not null,
  file_path_eng VARCHAR2(2000),
  file_type_eng VARCHAR2(1) not null,
  file_name_reg VARCHAR2(100),
  file_path_reg VARCHAR2(2000),
  file_type_reg VARCHAR2(1),
  deptname      VARCHAR2(50),
  attach_by     NUMBER(12),
  attach_on     DATE,
  isdeleted     VARCHAR2(1) default 'N' not null,
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  created_date  DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100)
)
;
comment on column TB_COM_HELP_DOCS.help_doc_id
  is 'Primary Key';
comment on column TB_COM_HELP_DOCS.module_name
  is 'Name of the module for which the help doc is being uploaded';
comment on column TB_COM_HELP_DOCS.file_name_eng
  is 'Name of the file being uploaded';
comment on column TB_COM_HELP_DOCS.file_path_eng
  is 'Path of the file being uploaded';
comment on column TB_COM_HELP_DOCS.file_type_eng
  is 'Type of the file being uploaded';
comment on column TB_COM_HELP_DOCS.file_name_reg
  is 'Name of the file being uploaded for regional';
comment on column TB_COM_HELP_DOCS.file_path_reg
  is 'Path of the file being uploaded for regional';
comment on column TB_COM_HELP_DOCS.file_type_reg
  is 'Type of the file being uploaded for regional';
comment on column TB_COM_HELP_DOCS.deptname
  is 'dept name';
comment on column TB_COM_HELP_DOCS.attach_by
  is 'Person by whom the file has been uploaded';
comment on column TB_COM_HELP_DOCS.attach_on
  is 'Date on which the file has been uploaded';
comment on column TB_COM_HELP_DOCS.isdeleted
  is 'Record Deletion flag - value N non-deleted record and Y- deleted record';
comment on column TB_COM_HELP_DOCS.orgid
  is 'Organization Id.';
comment on column TB_COM_HELP_DOCS.user_id
  is 'User Id';
comment on column TB_COM_HELP_DOCS.lang_id
  is 'Language Id';
comment on column TB_COM_HELP_DOCS.created_date
  is 'Last Modification Date';
comment on column TB_COM_HELP_DOCS.updated_by
  is 'Modified By';
comment on column TB_COM_HELP_DOCS.updated_date
  is 'Modification Date';
comment on column TB_COM_HELP_DOCS.lg_ip_mac
  is 'Client Machine''''s Login Name | IP Address | Physical Address';
comment on column TB_COM_HELP_DOCS.lg_ip_mac_upd
  is 'Updated Client Machine''''s Login Name | IP Address | Physical Address';
alter table TB_COM_HELP_DOCS
  add constraint PK_HELP_DOC_ID primary key (HELP_DOC_ID);

prompt
prompt Creating table TB_COM_HELP_DOCS_HIST
prompt ====================================
prompt
create table TB_COM_HELP_DOCS_HIST
(
  h_doc_id      NUMBER(12) not null,
  help_doc_id   NUMBER(12) not null,
  module_name   VARCHAR2(200) not null,
  file_name_eng VARCHAR2(100) not null,
  file_path_eng VARCHAR2(2000),
  file_type_eng VARCHAR2(1) not null,
  file_name_reg VARCHAR2(100),
  file_path_reg VARCHAR2(2000),
  file_type_reg VARCHAR2(1),
  deptname      VARCHAR2(50),
  attach_by     NUMBER(12),
  attach_on     DATE,
  isdeleted     VARCHAR2(1) default 'N' not null,
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  created_date  DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  h_status      VARCHAR2(1)
)
;
alter table TB_COM_HELP_DOCS_HIST
  add constraint PK_H_DOC_ID primary key (H_DOC_ID);

prompt
prompt Creating table TB_COM_JOB_MAS
prompt =============================
prompt
create table TB_COM_JOB_MAS
(
  cj_id        NUMBER(12) not null,
  dp_deptid    NUMBER(12) not null,
  cj_no        VARCHAR2(100),
  cj_desc      VARCHAR2(500),
  cj_nxdt      DATE,
  cj_interval  VARCHAR2(500),
  status       VARCHAR2(1),
  orgid        NUMBER(5),
  user_id      NUMBER(7) not null,
  lang_id      NUMBER(4) not null,
  lmoddate     DATE not null,
  updated_by   NUMBER(7),
  updated_date DATE,
  com_v1       NVARCHAR2(100),
  com_v2       NVARCHAR2(100),
  com_v3       NVARCHAR2(100),
  com_v4       NVARCHAR2(100),
  com_v5       NVARCHAR2(100),
  com_n1       NUMBER(15),
  com_n2       NUMBER(15),
  com_n3       NUMBER(15),
  com_n4       NUMBER(15),
  com_n5       NUMBER(15),
  com_d1       DATE,
  com_d2       DATE,
  com_d3       DATE,
  com_lo1      CHAR(1),
  com_lo2      CHAR(1),
  com_lo3      CHAR(1),
  cpd_id_bjo   NUMBER(12),
  cpd_id_bfr   NUMBER(12),
  cj_repeat    VARCHAR2(1),
  cj_classname VARCHAR2(1000),
  cj_funname   VARCHAR2(100),
  cj_date      DATE,
  created_date DATE
)
;
comment on column TB_COM_JOB_MAS.cj_id
  is 'Primary Key';
comment on column TB_COM_JOB_MAS.dp_deptid
  is 'department';
comment on column TB_COM_JOB_MAS.cj_no
  is 'job number';
comment on column TB_COM_JOB_MAS.cj_desc
  is 'Procedure and function to be excuted.';
comment on column TB_COM_JOB_MAS.cj_nxdt
  is 'Next date';
comment on column TB_COM_JOB_MAS.cj_interval
  is 'interval';
comment on column TB_COM_JOB_MAS.status
  is 'status';
comment on column TB_COM_JOB_MAS.user_id
  is 'User Identity';
comment on column TB_COM_JOB_MAS.lang_id
  is 'Language Identity';
comment on column TB_COM_JOB_MAS.lmoddate
  is 'Last Modification Date';
comment on column TB_COM_JOB_MAS.updated_by
  is 'Modification By';
comment on column TB_COM_JOB_MAS.updated_date
  is 'Modification Date';
alter table TB_COM_JOB_MAS
  add constraint PK_CJ_ID primary key (CJ_ID);
alter table TB_COM_JOB_MAS
  add constraint FK_COM_DEPT_ID foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);

prompt
prompt Creating table TB_CUSTBANKS_HIST
prompt ================================
prompt
create table TB_CUSTBANKS_HIST
(
  cb_hist_bankid NUMBER(12) not null,
  cb_bankid      NUMBER(12) not null,
  cb_bankcode    NUMBER(12),
  cb_bankname    NVARCHAR2(500),
  user_id        NUMBER(7),
  lang_id        NUMBER(7),
  lmoddate       DATE,
  updated_by     NUMBER(7),
  updated_date   DATE,
  cb_branchname  NVARCHAR2(500),
  cb_city        NVARCHAR2(100),
  cb_address     NVARCHAR2(250),
  cm_bankid      NUMBER(12) not null,
  lg_ip_mac      VARCHAR2(100),
  lg_ip_mac_upd  VARCHAR2(100),
  flag           VARCHAR2(1),
  lc_user_name   VARCHAR2(200),
  lc_ip_mc_clnt  VARCHAR2(200),
  deletion_date  DATE
)
;
comment on column TB_CUSTBANKS_HIST.cb_hist_bankid
  is 'History id';
comment on column TB_CUSTBANKS_HIST.cb_bankid
  is 'Primary Key';
comment on column TB_CUSTBANKS_HIST.cb_bankcode
  is 'Bank Code';
comment on column TB_CUSTBANKS_HIST.cb_bankname
  is 'Name of the Bank';
comment on column TB_CUSTBANKS_HIST.user_id
  is 'User Identity';
comment on column TB_CUSTBANKS_HIST.lang_id
  is 'Language Identity';
comment on column TB_CUSTBANKS_HIST.lmoddate
  is 'Last Modification Date';
comment on column TB_CUSTBANKS_HIST.updated_by
  is 'Updated by';
comment on column TB_CUSTBANKS_HIST.updated_date
  is 'Updated Date';
comment on column TB_CUSTBANKS_HIST.cb_branchname
  is 'Branch name';
comment on column TB_CUSTBANKS_HIST.cb_city
  is 'City name';
comment on column TB_CUSTBANKS_HIST.cb_address
  is 'Address';
comment on column TB_CUSTBANKS_HIST.lg_ip_mac
  is 'Client Machine�s Login Name | IP Address | Physical Address';
comment on column TB_CUSTBANKS_HIST.lg_ip_mac_upd
  is 'Updated Client Machine�s Login Name | IP Address | Physical Address';
comment on column TB_CUSTBANKS_HIST.flag
  is 'Updated or Deleted date';
comment on column TB_CUSTBANKS_HIST.lc_user_name
  is 'user name of local machine';
comment on column TB_CUSTBANKS_HIST.lc_ip_mc_clnt
  is 'Ip Address of local machine';
comment on column TB_CUSTBANKS_HIST.deletion_date
  is 'Deletion date';

prompt
prompt Creating table TB_CUSTBANKS_MAS_HIST
prompt ====================================
prompt
create table TB_CUSTBANKS_MAS_HIST
(
  cm_bankid_hist NUMBER(12) not null,
  cm_bankid      NUMBER(12) not null,
  cm_bankname    NVARCHAR2(500),
  user_id        NUMBER(7),
  lang_id        NUMBER(7),
  lmoddate       DATE,
  updated_by     NUMBER(7),
  updated_date   DATE,
  lg_ip_mac      VARCHAR2(100),
  lg_ip_mac_upd  VARCHAR2(100),
  flag           VARCHAR2(1),
  lc_user_name   VARCHAR2(200),
  lc_ip_mc_clnt  VARCHAR2(200),
  deletion_date  DATE
)
;
comment on table TB_CUSTBANKS_MAS_HIST
  is 'History Table of Customer Bank Master';
comment on column TB_CUSTBANKS_MAS_HIST.cm_bankid_hist
  is 'History Primary Key';
comment on column TB_CUSTBANKS_MAS_HIST.cm_bankid
  is 'Primary Key';
comment on column TB_CUSTBANKS_MAS_HIST.cm_bankname
  is 'Name of Bank';
comment on column TB_CUSTBANKS_MAS_HIST.user_id
  is 'User Id';
comment on column TB_CUSTBANKS_MAS_HIST.lang_id
  is 'Language Id';
comment on column TB_CUSTBANKS_MAS_HIST.lmoddate
  is 'Last Modification Date';
comment on column TB_CUSTBANKS_MAS_HIST.updated_by
  is 'Modified By ';
comment on column TB_CUSTBANKS_MAS_HIST.updated_date
  is 'Modified Date ';
comment on column TB_CUSTBANKS_MAS_HIST.lg_ip_mac
  is 'Client Machine�s Login Name | IP Address | Physical Address';
comment on column TB_CUSTBANKS_MAS_HIST.lg_ip_mac_upd
  is 'Updated Client Machine�s Login Name | IP Address | Physical Address';
comment on column TB_CUSTBANKS_MAS_HIST.flag
  is 'Updated or Deleted date';
comment on column TB_CUSTBANKS_MAS_HIST.lc_user_name
  is 'user name of local machine';
comment on column TB_CUSTBANKS_MAS_HIST.lc_ip_mc_clnt
  is 'Ip Address of local machine';
comment on column TB_CUSTBANKS_MAS_HIST.deletion_date
  is 'Deletion date';

prompt
prompt Creating table TB_DEPARTMENT_HIST
prompt =================================
prompt
create table TB_DEPARTMENT_HIST
(
  h_deptid      NUMBER(12) not null,
  dp_deptid     NUMBER(12) not null,
  dp_deptcode   NVARCHAR2(4),
  dp_deptdesc   NVARCHAR2(400),
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(4) not null,
  lmoddate      DATE not null,
  status        NVARCHAR2(1),
  dp_shortdesc  NVARCHAR2(10),
  dp_name_mar   NVARCHAR2(2000),
  sub_dept_flg  CHAR(1),
  updated_by    NUMBER(7),
  updated_date  DATE,
  dp_smfid      NUMBER(12),
  dp_check      NVARCHAR2(1),
  dp_prefix     NVARCHAR2(3),
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  h_status      NVARCHAR2(1)
)
;
alter table TB_DEPARTMENT_HIST
  add constraint PK_H_DEPTID primary key (H_DEPTID);

prompt
prompt Creating table TB_DEPORG_MAP
prompt ============================
prompt
create table TB_DEPORG_MAP
(
  map_id        NUMBER(12) not null,
  dp_deptid     NUMBER(12) not null,
  map_status    CHAR(1),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  lmoddate      DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  com_v1        NVARCHAR2(100),
  com_v2        NVARCHAR2(100),
  com_v3        NVARCHAR2(100),
  com_v4        NVARCHAR2(100),
  com_v5        NVARCHAR2(100),
  com_n1        NUMBER(15),
  com_n2        NUMBER(15),
  com_n3        NUMBER(15),
  com_n4        NUMBER(15),
  com_n5        NUMBER(15),
  com_d1        DATE,
  com_d2        DATE,
  com_d3        DATE,
  com_lo1       CHAR(1),
  com_lo2       CHAR(1),
  com_lo3       CHAR(1),
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100)
)
;
comment on table TB_DEPORG_MAP
  is 'COMMON TABLE USED TO CREATE PARENT CHILD RELATIONSHIP. THIS IS A CHILD TABLE';
comment on column TB_DEPORG_MAP.orgid
  is 'ORGANISATION ID';
comment on column TB_DEPORG_MAP.user_id
  is 'USER ID';
comment on column TB_DEPORG_MAP.lang_id
  is 'LANGUAGE ID';
comment on column TB_DEPORG_MAP.lmoddate
  is 'CREATION DATE';
comment on column TB_DEPORG_MAP.updated_by
  is 'UPDATED BY USER ID';
comment on column TB_DEPORG_MAP.updated_date
  is 'UPDATION DATE';
alter table TB_DEPORG_MAP
  add constraint PK_MAPID primary key (MAP_ID);
alter table TB_DEPORG_MAP
  add constraint FK_DEPTID foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);

prompt
prompt Creating table TB_DEPORG_MAP_HIST
prompt =================================
prompt
create table TB_DEPORG_MAP_HIST
(
  h_mapid       NUMBER(12) not null,
  map_id        NUMBER(12) not null,
  dp_deptid     NUMBER(12) not null,
  map_status    CHAR(1),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  lmoddate      DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  com_v1        NVARCHAR2(100),
  com_v2        NVARCHAR2(100),
  com_v3        NVARCHAR2(100),
  com_v4        NVARCHAR2(100),
  com_v5        NVARCHAR2(100),
  com_n1        NUMBER(15),
  com_n2        NUMBER(15),
  com_n3        NUMBER(15),
  com_n4        NUMBER(15),
  com_n5        NUMBER(15),
  com_d1        DATE,
  com_d2        DATE,
  com_d3        DATE,
  com_lo1       CHAR(1),
  com_lo2       CHAR(1),
  com_lo3       CHAR(1),
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  h_status      NVARCHAR2(1)
)
;
alter table TB_DEPORG_MAP_HIST
  add constraint PK_H_MAPID primary key (H_MAPID);

prompt
prompt Creating table TB_DEPT_LOCATION
prompt ===============================
prompt
create table TB_DEPT_LOCATION
(
  mapid         NUMBER(12) not null,
  dp_deptid     NUMBER(12) not null,
  loc_id        NUMBER(12),
  isdeleted     VARCHAR2(1),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(4),
  isregistry    NUMBER,
  isrecordroom  NUMBER,
  isoutward     NUMBER,
  isnetwork     NUMBER,
  dskid         NUMBER(12),
  ondate        DATE,
  ontime        VARCHAR2(12),
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  aut_v1        NVARCHAR2(100),
  aut_v2        NVARCHAR2(100),
  aut_v3        NVARCHAR2(100),
  aut_v4        NVARCHAR2(100),
  aut_v5        NVARCHAR2(100),
  aut_n1        NUMBER(15),
  aut_n2        NUMBER(15),
  aut_n3        NUMBER(15),
  aut_n4        NUMBER(15),
  aut_n5        NUMBER(15),
  aut_d1        DATE,
  aut_d2        DATE,
  aut_d3        DATE,
  aut_lo1       CHAR(1),
  aut_lo2       CHAR(1),
  aut_lo3       CHAR(1)
)
;
alter table TB_DEPT_LOCATION
  add constraint PK_PMAPID primary key (MAPID);
alter table TB_DEPT_LOCATION
  add constraint FK_TB_DP_DEPTID foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);
alter table TB_DEPT_LOCATION
  add constraint FK_TB_ORGID foreign key (ORGID)
  references TB_ORGANISATION (ORGID);

prompt
prompt Creating table TB_DEPT_LOCATION_HIST
prompt ====================================
prompt
create table TB_DEPT_LOCATION_HIST
(
  h_mapid       NUMBER(12) not null,
  mapid         NUMBER(12) not null,
  dp_deptid     NUMBER(12) not null,
  loc_id        NUMBER(12),
  isdeleted     VARCHAR2(1),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(4),
  isregistry    NUMBER,
  isrecordroom  NUMBER,
  isoutward     NUMBER,
  isnetwork     NUMBER,
  dskid         NUMBER(12),
  ondate        DATE,
  ontime        VARCHAR2(12),
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  aut_v1        NVARCHAR2(100),
  aut_v2        NVARCHAR2(100),
  aut_v3        NVARCHAR2(100),
  aut_v4        NVARCHAR2(100),
  aut_v5        NVARCHAR2(100),
  aut_n1        NUMBER(15),
  aut_n2        NUMBER(15),
  aut_n3        NUMBER(15),
  aut_n4        NUMBER(15),
  aut_n5        NUMBER(15),
  aut_d1        DATE,
  aut_d2        DATE,
  aut_d3        DATE,
  aut_lo1       CHAR(1),
  aut_lo2       CHAR(1),
  aut_lo3       CHAR(1),
  h_status      NVARCHAR2(1)
)
;
alter table TB_DEPT_LOCATION_HIST
  add constraint PK_HMAPID primary key (H_MAPID);

prompt
prompt Creating table TB_EIP_ABOUTUS
prompt =============================
prompt
create table TB_EIP_ABOUTUS
(
  aboutus_id       NUMBER(12) not null,
  description_en   VARCHAR2(3000) not null,
  description_reg  NVARCHAR2(2000),
  isdeleted        VARCHAR2(1) default 'N' not null,
  orgid            NUMBER(4) not null,
  user_id          NUMBER(7) not null,
  lang_id          NUMBER(7) not null,
  created_date     DATE not null,
  updated_by       NUMBER(7),
  updated_date     DATE,
  lg_ip_mac        VARCHAR2(100),
  lg_ip_mac_upd    VARCHAR2(100),
  description_en1  VARCHAR2(3000) not null,
  description_reg1 NVARCHAR2(2000),
  cheker_flag      VARCHAR2(1) default 'N'
)
;
comment on column TB_EIP_ABOUTUS.aboutus_id
  is 'Primary Key';
comment on column TB_EIP_ABOUTUS.description_en
  is 'Description in english';
comment on column TB_EIP_ABOUTUS.description_reg
  is 'Description in regional language';
comment on column TB_EIP_ABOUTUS.isdeleted
  is 'Record Deletion flag - value N non-deleted record and Y- deleted record';
comment on column TB_EIP_ABOUTUS.orgid
  is 'Organization Id';
comment on column TB_EIP_ABOUTUS.user_id
  is 'User Id';
comment on column TB_EIP_ABOUTUS.lang_id
  is 'Language Id';
comment on column TB_EIP_ABOUTUS.created_date
  is 'Created Date';
comment on column TB_EIP_ABOUTUS.updated_by
  is 'Modified By';
comment on column TB_EIP_ABOUTUS.updated_date
  is 'Modification Date';
comment on column TB_EIP_ABOUTUS.lg_ip_mac
  is 'Client Machine''''''''s Login Name | IP Address | Physical Address';
comment on column TB_EIP_ABOUTUS.lg_ip_mac_upd
  is 'Updated Client Machine''''''''s Login Name | IP Address | Physical Address';
comment on column TB_EIP_ABOUTUS.description_en1
  is 'Description in english (para 2)';
comment on column TB_EIP_ABOUTUS.description_reg1
  is 'Description in regional (para 2)';
comment on column TB_EIP_ABOUTUS.cheker_flag
  is 'Chekker  flag';
alter table TB_EIP_ABOUTUS
  add constraint PK_EIP_ABOUTUS_ID primary key (ABOUTUS_ID);
alter table TB_EIP_ABOUTUS
  add constraint FK_EIP_ABOUTUS_ORG_ID foreign key (ORGID)
  references TB_ORGANISATION (ORGID);

prompt
prompt Creating table TB_EIP_ABOUTUS_HIST
prompt ==================================
prompt
create table TB_EIP_ABOUTUS_HIST
(
  h_aboutusid      NUMBER(12) not null,
  aboutus_id       NUMBER(12) not null,
  description_en   VARCHAR2(3000) not null,
  description_reg  NVARCHAR2(2000),
  isdeleted        VARCHAR2(1) default 'N' not null,
  orgid            NUMBER(4) not null,
  user_id          NUMBER(7) not null,
  lang_id          NUMBER(7) not null,
  created_date     DATE not null,
  updated_by       NUMBER(7),
  updated_date     DATE,
  lg_ip_mac        VARCHAR2(100),
  lg_ip_mac_upd    VARCHAR2(100),
  description_en1  VARCHAR2(3000) not null,
  description_reg1 NVARCHAR2(2000),
  h_status         NVARCHAR2(1)
)
;
alter table TB_EIP_ABOUTUS_HIST
  add constraint PK_H_ABOUTUSID primary key (H_ABOUTUSID);

prompt
prompt Creating table TB_EIP_ANNOUNCEMENT
prompt ==================================
prompt
create table TB_EIP_ANNOUNCEMENT
(
  announce_id       NUMBER(12) not null,
  announce_desc_eng NVARCHAR2(2000) not null,
  announce_desc_reg NVARCHAR2(2000) not null,
  attach            NVARCHAR2(100),
  isdeleted         VARCHAR2(1) default 'N' not null,
  orgid             NUMBER(4) not null,
  user_id           NUMBER(7) not null,
  lang_id           NUMBER(7) not null,
  created_date      DATE not null,
  updated_by        NUMBER(7),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100),
  cheker_flag       VARCHAR2(1) default 'N'
)
;
comment on column TB_EIP_ANNOUNCEMENT.announce_id
  is 'EIP ANNOUNCEMENT Primary Key Id';
comment on column TB_EIP_ANNOUNCEMENT.announce_desc_eng
  is 'EIP DESC ENG';
comment on column TB_EIP_ANNOUNCEMENT.announce_desc_reg
  is 'EIP DESC REG';
comment on column TB_EIP_ANNOUNCEMENT.attach
  is 'ATTACHMENT';
comment on column TB_EIP_ANNOUNCEMENT.isdeleted
  is 'Record Deletion flag - value N non-deleted record and Y- deleted record';
comment on column TB_EIP_ANNOUNCEMENT.orgid
  is 'Organization Id.';
comment on column TB_EIP_ANNOUNCEMENT.user_id
  is 'User Id';
comment on column TB_EIP_ANNOUNCEMENT.lang_id
  is 'Language Id';
comment on column TB_EIP_ANNOUNCEMENT.created_date
  is 'Created Date';
comment on column TB_EIP_ANNOUNCEMENT.updated_by
  is 'Modified By';
comment on column TB_EIP_ANNOUNCEMENT.updated_date
  is 'Modification Date';
comment on column TB_EIP_ANNOUNCEMENT.lg_ip_mac
  is 'Client Machine''''s Login Name | IP Address | Physical Address';
alter table TB_EIP_ANNOUNCEMENT
  add constraint PK_ANNOUNCEMENT primary key (ANNOUNCE_ID);
alter table TB_EIP_ANNOUNCEMENT
  add constraint FK_EIP_ORGID4 foreign key (ORGID)
  references TB_ORGANISATION (ORGID);

prompt
prompt Creating table TB_EIP_ANNOUNCEMENT_HIST
prompt =======================================
prompt
create table TB_EIP_ANNOUNCEMENT_HIST
(
  h_announceid      NUMBER(12) not null,
  announce_id       NUMBER(12) not null,
  announce_desc_eng NVARCHAR2(2000) not null,
  announce_desc_reg NVARCHAR2(2000) not null,
  attach            NVARCHAR2(100),
  isdeleted         VARCHAR2(1) default 'N' not null,
  orgid             NUMBER(4) not null,
  user_id           NUMBER(7) not null,
  lang_id           NUMBER(7) not null,
  created_date      DATE not null,
  updated_by        NUMBER(7),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100),
  h_status          NVARCHAR2(1)
)
;
alter table TB_EIP_ANNOUNCEMENT_HIST
  add constraint PK_H_ANNOUNCEID primary key (H_ANNOUNCEID);

prompt
prompt Creating table TB_EIP_ANNOUNCEMENT_LANDING
prompt ==========================================
prompt
create table TB_EIP_ANNOUNCEMENT_LANDING
(
  announce_id       NUMBER(12) not null,
  announce_desc_eng NVARCHAR2(2000) not null,
  announce_desc_reg NVARCHAR2(2000) not null,
  attachment        NVARCHAR2(300),
  isdeleted         VARCHAR2(1) default 'N' not null,
  orgid             NUMBER(4) not null,
  user_id           NUMBER(7) not null,
  lang_id           NUMBER(7) not null,
  created_date      DATE not null,
  updated_by        NUMBER(7),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100),
  cheker_flag       VARCHAR2(1) default 'N'
)
;
comment on column TB_EIP_ANNOUNCEMENT_LANDING.announce_id
  is 'PRIMARY KEY';
comment on column TB_EIP_ANNOUNCEMENT_LANDING.announce_desc_eng
  is 'ANNOUNCEMENT IN ENGLISH';
comment on column TB_EIP_ANNOUNCEMENT_LANDING.announce_desc_reg
  is 'ANNOUNCEMENT IN REGIONAL';
comment on column TB_EIP_ANNOUNCEMENT_LANDING.attachment
  is 'ANNOUNCEMENT ATTACHMENT';
alter table TB_EIP_ANNOUNCEMENT_LANDING
  add constraint PK_ANNOUNCEMENT_LANDING primary key (ANNOUNCE_ID);

prompt
prompt Creating table TB_EIP_ANNOUNC_LANDING_HIST
prompt ==========================================
prompt
create table TB_EIP_ANNOUNC_LANDING_HIST
(
  h_annid           NUMBER(12) not null,
  announce_id       NUMBER(12) not null,
  announce_desc_eng NVARCHAR2(2000) not null,
  announce_desc_reg NVARCHAR2(2000) not null,
  attachment        NVARCHAR2(300),
  isdeleted         VARCHAR2(1) default 'N' not null,
  orgid             NUMBER(4) not null,
  user_id           NUMBER(7) not null,
  lang_id           NUMBER(7) not null,
  created_date      DATE not null,
  updated_by        NUMBER(7),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100),
  h_status          NVARCHAR2(1)
)
;
alter table TB_EIP_ANNOUNC_LANDING_HIST
  add constraint PK_H_ANNID primary key (H_ANNID);

prompt
prompt Creating table TB_EIP_CONTACT_US
prompt ================================
prompt
create table TB_EIP_CONTACT_US
(
  contact_id           NUMBER(19) not null,
  address1_en          VARCHAR2(500),
  address1_reg         NVARCHAR2(1000),
  address2_en          VARCHAR2(500),
  address2_reg         NVARCHAR2(1000),
  city_en              VARCHAR2(200),
  city_reg             NVARCHAR2(400),
  cname_en             VARCHAR2(100),
  cname_reg            NVARCHAR2(200),
  country_en           VARCHAR2(100),
  country_reg          VARCHAR2(200),
  created_date         DATE,
  designation_en       VARCHAR2(100),
  designation_reg      NVARCHAR2(200),
  email1               VARCHAR2(100),
  email2               VARCHAR2(100),
  faxno1_en            VARCHAR2(15),
  faxno1_reg           NVARCHAR2(30),
  faxno2_en            VARCHAR2(15),
  isdeleted            VARCHAR2(1),
  lg_ip_mac            VARCHAR2(600),
  lg_ip_mac_upd        VARCHAR2(250),
  org_id               NUMBER(19) not null,
  pincode_en           VARCHAR2(10),
  pincode_mr           VARCHAR2(20),
  remarks_en           VARCHAR2(100),
  state_en             VARCHAR2(100),
  telno1_en            VARCHAR2(15),
  telno2_en            VARCHAR2(15),
  telno3_en            VARCHAR2(15),
  updated_by           NUMBER(19),
  updated_date         DATE,
  userid               NUMBER(19),
  faxno2_reg           NVARCHAR2(30),
  remarks_reg          NVARCHAR2(200),
  state_reg            VARCHAR2(200),
  telno1_reg           NVARCHAR2(30),
  telno2_reg           NVARCHAR2(30),
  telno3_reg           VARCHAR2(30),
  dept_en              VARCHAR2(100),
  dept_reg             VARCHAR2(100),
  flag                 VARCHAR2(20),
  muncipality_name     VARCHAR2(100),
  sequence_no          NUMBER(3),
  muncipality_name_reg VARCHAR2(100),
  cheker_flag          VARCHAR2(1) default 'N'
)
;
comment on column TB_EIP_CONTACT_US.contact_id
  is 'Contact Id (PK)';
comment on column TB_EIP_CONTACT_US.address1_en
  is 'Address1 in english';
comment on column TB_EIP_CONTACT_US.address1_reg
  is 'Address1 in regional';
comment on column TB_EIP_CONTACT_US.address2_en
  is 'Address2 in english';
comment on column TB_EIP_CONTACT_US.address2_reg
  is 'Address2 in regional.';
comment on column TB_EIP_CONTACT_US.city_en
  is 'City in english';
comment on column TB_EIP_CONTACT_US.city_reg
  is 'City in regional';
comment on column TB_EIP_CONTACT_US.cname_en
  is 'Name of contact person in english';
comment on column TB_EIP_CONTACT_US.cname_reg
  is 'Name of contact person in regional';
comment on column TB_EIP_CONTACT_US.country_en
  is 'Country in English';
comment on column TB_EIP_CONTACT_US.country_reg
  is 'Country in regional';
comment on column TB_EIP_CONTACT_US.created_date
  is 'Created Date';
comment on column TB_EIP_CONTACT_US.designation_en
  is 'Designation in english';
comment on column TB_EIP_CONTACT_US.designation_reg
  is 'Designation in regional';
comment on column TB_EIP_CONTACT_US.email1
  is 'E-mail address 1';
comment on column TB_EIP_CONTACT_US.email2
  is 'E-mail address 1';
comment on column TB_EIP_CONTACT_US.faxno1_en
  is 'Fax no 1 in English';
comment on column TB_EIP_CONTACT_US.faxno1_reg
  is 'Fax no 1 in regional ';
comment on column TB_EIP_CONTACT_US.faxno2_en
  is 'Fax no 2 in English';
comment on column TB_EIP_CONTACT_US.isdeleted
  is 'Deleted the ''Y'' and if not deleted then ''N''';
comment on column TB_EIP_CONTACT_US.lg_ip_mac
  is 'For user name,Ip address, Machine Physical address';
comment on column TB_EIP_CONTACT_US.lg_ip_mac_upd
  is 'Client Machine''''''''s Login Name | IP Address | Physical Address';
comment on column TB_EIP_CONTACT_US.org_id
  is 'Foreign Key';
comment on column TB_EIP_CONTACT_US.pincode_en
  is 'Pincode in english';
comment on column TB_EIP_CONTACT_US.pincode_mr
  is 'Pincode in regional ';
comment on column TB_EIP_CONTACT_US.remarks_en
  is 'Remark in english';
comment on column TB_EIP_CONTACT_US.state_en
  is 'State In English';
comment on column TB_EIP_CONTACT_US.telno1_en
  is 'Telephone no1 in english';
comment on column TB_EIP_CONTACT_US.telno2_en
  is 'Telephone no2 in english';
comment on column TB_EIP_CONTACT_US.telno3_en
  is 'Telephone no2 in english';
comment on column TB_EIP_CONTACT_US.updated_by
  is 'Modified By';
comment on column TB_EIP_CONTACT_US.updated_date
  is 'Modification Date';
comment on column TB_EIP_CONTACT_US.userid
  is 'UserId';
comment on column TB_EIP_CONTACT_US.faxno2_reg
  is 'Fax no 2 in regional ';
comment on column TB_EIP_CONTACT_US.remarks_reg
  is 'Remark in regional ';
comment on column TB_EIP_CONTACT_US.state_reg
  is 'State In regional ';
comment on column TB_EIP_CONTACT_US.telno1_reg
  is 'Telephone no1 in regional ';
comment on column TB_EIP_CONTACT_US.telno2_reg
  is 'Telephone no2 in regional ';
comment on column TB_EIP_CONTACT_US.telno3_reg
  is 'Telephone no2 in regional ';
alter table TB_EIP_CONTACT_US
  add constraint EIP_CONTACT_US_KP primary key (CONTACT_ID);
alter table TB_EIP_CONTACT_US
  add constraint FKA98B6F21187DF1D0 foreign key (USERID)
  references EMPLOYEE (EMPID);
alter table TB_EIP_CONTACT_US
  add constraint FKA98B6F2138B65205 foreign key (UPDATED_BY)
  references EMPLOYEE (EMPID);
alter table TB_EIP_CONTACT_US
  add constraint FK_EIP_CONTACTUS_ORG_ID foreign key (ORG_ID)
  references TB_ORGANISATION (ORGID);

prompt
prompt Creating table TB_EIP_CONTACT_US_HIST
prompt =====================================
prompt
create table TB_EIP_CONTACT_US_HIST
(
  h_contactid          NUMBER(19) not null,
  contact_id           NUMBER(19) not null,
  address1_en          VARCHAR2(500),
  address1_reg         NVARCHAR2(1000),
  address2_en          VARCHAR2(500),
  address2_reg         NVARCHAR2(1000),
  city_en              VARCHAR2(200),
  city_reg             NVARCHAR2(400),
  cname_en             VARCHAR2(100),
  cname_reg            NVARCHAR2(200),
  country_en           VARCHAR2(100),
  country_reg          VARCHAR2(200),
  created_date         DATE,
  designation_en       VARCHAR2(100),
  designation_reg      NVARCHAR2(200),
  email1               VARCHAR2(100),
  email2               VARCHAR2(100),
  faxno1_en            VARCHAR2(15),
  faxno1_reg           NVARCHAR2(30),
  faxno2_en            VARCHAR2(15),
  isdeleted            VARCHAR2(1),
  lg_ip_mac            VARCHAR2(600),
  lg_ip_mac_upd        VARCHAR2(250),
  org_id               NUMBER(19) not null,
  pincode_en           VARCHAR2(10),
  pincode_mr           VARCHAR2(20),
  remarks_en           VARCHAR2(100),
  state_en             VARCHAR2(100),
  telno1_en            VARCHAR2(15),
  telno2_en            VARCHAR2(15),
  telno3_en            VARCHAR2(15),
  updated_by           NUMBER(19),
  updated_date         DATE,
  userid               NUMBER(19),
  faxno2_reg           NVARCHAR2(30),
  remarks_reg          NVARCHAR2(200),
  state_reg            VARCHAR2(200),
  telno1_reg           NVARCHAR2(30),
  telno2_reg           NVARCHAR2(30),
  telno3_reg           VARCHAR2(30),
  dept_en              VARCHAR2(100),
  dept_reg             VARCHAR2(100),
  flag                 VARCHAR2(20),
  muncipality_name     VARCHAR2(100),
  sequence_no          NUMBER(3),
  muncipality_name_reg VARCHAR2(100),
  h_status             NVARCHAR2(1)
)
;
alter table TB_EIP_CONTACT_US_HIST
  add constraint PK_H_CONTACTID primary key (H_CONTACTID);

prompt
prompt Creating table TB_EIP_FAQ
prompt =========================
prompt
create table TB_EIP_FAQ
(
  faq_id        NUMBER(12) not null,
  answer_en     VARCHAR2(500) not null,
  answer_reg    NVARCHAR2(1000),
  question_en   VARCHAR2(250) not null,
  question_reg  NVARCHAR2(1000),
  seq_no        NUMBER(19),
  isdeleted     VARCHAR2(1) default 'N' not null,
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  created_date  DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  cheker_flag   VARCHAR2(1) default 'N'
)
;
comment on column TB_EIP_FAQ.faq_id
  is 'Primary Key';
comment on column TB_EIP_FAQ.answer_en
  is 'Answer in english language';
comment on column TB_EIP_FAQ.answer_reg
  is 'Answer in regional language';
comment on column TB_EIP_FAQ.question_en
  is 'Question in english language';
comment on column TB_EIP_FAQ.question_reg
  is 'Question in regional language';
comment on column TB_EIP_FAQ.seq_no
  is 'Sequence';
comment on column TB_EIP_FAQ.isdeleted
  is 'Record Deletion flag - value N non-deleted record and Y- deleted record';
comment on column TB_EIP_FAQ.orgid
  is 'Organization Id';
comment on column TB_EIP_FAQ.user_id
  is 'User Id';
comment on column TB_EIP_FAQ.lang_id
  is 'Language Id';
comment on column TB_EIP_FAQ.created_date
  is 'Created Date';
comment on column TB_EIP_FAQ.updated_by
  is 'Modified By';
comment on column TB_EIP_FAQ.updated_date
  is 'Modification Date';
comment on column TB_EIP_FAQ.lg_ip_mac
  is 'Client Machine''''''''s Login Name | IP Address | Physical Address';
comment on column TB_EIP_FAQ.lg_ip_mac_upd
  is 'Updated Client Machine''''''''s Login Name | IP Address | Physical Address';
comment on column TB_EIP_FAQ.cheker_flag
  is 'Cheker flag';
alter table TB_EIP_FAQ
  add constraint PK_EIP_FAQ_ID primary key (FAQ_ID);
alter table TB_EIP_FAQ
  add constraint FK_EIP_FAQ_ORG_ID foreign key (ORGID)
  references TB_ORGANISATION (ORGID);
alter table TB_EIP_FAQ
  add constraint FK_FAQ_EMPID foreign key (USER_ID)
  references EMPLOYEE (EMPID);
alter table TB_EIP_FAQ
  add constraint FK_FAQ_UPD_EMPID foreign key (UPDATED_BY)
  references EMPLOYEE (EMPID);

prompt
prompt Creating table TB_EIP_FAQ_HIST
prompt ==============================
prompt
create table TB_EIP_FAQ_HIST
(
  h_faqid       NUMBER(12) not null,
  faq_id        NUMBER(12) not null,
  answer_en     VARCHAR2(500) not null,
  answer_reg    NVARCHAR2(1000),
  question_en   VARCHAR2(250) not null,
  question_reg  NVARCHAR2(1000),
  seq_no        NUMBER(19),
  isdeleted     VARCHAR2(1) default 'N' not null,
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  created_date  DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  h_status      NVARCHAR2(1)
)
;
alter table TB_EIP_FAQ_HIST
  add constraint PK_H_FAQID primary key (H_FAQID);

prompt
prompt Creating table TB_EIP_FEEDBACK
prompt ==============================
prompt
create table TB_EIP_FEEDBACK
(
  fd_id         NUMBER(12) not null,
  fd_user_name  NVARCHAR2(500),
  empid         NUMBER(12),
  mobile_no     NUMBER(10),
  email_id      VARCHAR2(100),
  fd_details    NVARCHAR2(2000),
  isdeleted     VARCHAR2(1) default 'N' not null,
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  created_date  DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100)
)
;
comment on column TB_EIP_FEEDBACK.fd_id
  is 'Primary Key';
comment on column TB_EIP_FEEDBACK.fd_user_name
  is 'Name of User who has given the feedback';
comment on column TB_EIP_FEEDBACK.empid
  is 'foreign reference in case if feedback given by login user from EMPLOYEE table.';
comment on column TB_EIP_FEEDBACK.mobile_no
  is 'Contact mobile number';
comment on column TB_EIP_FEEDBACK.email_id
  is 'Email ID';
comment on column TB_EIP_FEEDBACK.fd_details
  is 'Feedback Details';
alter table TB_EIP_FEEDBACK
  add constraint PK_FD_ID primary key (FD_ID);

prompt
prompt Creating table TB_EIP_HOME_IMAGES
prompt =================================
prompt
create table TB_EIP_HOME_IMAGES
(
  hm_img_id     NUMBER(12) not null,
  hm_img_order  NUMBER(2),
  image_name    VARCHAR2(200),
  image_path    VARCHAR2(1000),
  isdeleted     VARCHAR2(1) default 'N' not null,
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  created_date  DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  module_type   NVARCHAR2(1),
  dms_doc_id    VARCHAR2(100)
)
;
comment on column TB_EIP_HOME_IMAGES.hm_img_id
  is 'Primary Key';
comment on column TB_EIP_HOME_IMAGES.hm_img_order
  is 'order in which image should be displayed ';
comment on column TB_EIP_HOME_IMAGES.image_name
  is 'Image file Name';
comment on column TB_EIP_HOME_IMAGES.image_path
  is 'Image file path';
comment on column TB_EIP_HOME_IMAGES.isdeleted
  is 'Record Deletion flag - value N non-deleted record and Y- deleted record';
comment on column TB_EIP_HOME_IMAGES.orgid
  is 'Organization Id.';
comment on column TB_EIP_HOME_IMAGES.user_id
  is 'User Id';
comment on column TB_EIP_HOME_IMAGES.lang_id
  is 'Language Id';
comment on column TB_EIP_HOME_IMAGES.created_date
  is 'Created Date';
comment on column TB_EIP_HOME_IMAGES.updated_by
  is 'Modified By';
comment on column TB_EIP_HOME_IMAGES.updated_date
  is 'Modification Date';
comment on column TB_EIP_HOME_IMAGES.lg_ip_mac
  is 'Client Machine''''s Login Name | IP Address | Physical Address';
comment on column TB_EIP_HOME_IMAGES.lg_ip_mac_upd
  is 'Updated Client Machine''''s Login Name | IP Address | Physical Address';
alter table TB_EIP_HOME_IMAGES
  add constraint PK_EIP_HM_IMG_ID primary key (HM_IMG_ID);
alter table TB_EIP_HOME_IMAGES
  add constraint FK_EIP_HM_ID foreign key (ORGID)
  references TB_ORGANISATION (ORGID);

prompt
prompt Creating table TB_EIP_HOME_IMAGES_HIST
prompt ======================================
prompt
create table TB_EIP_HOME_IMAGES_HIST
(
  h_imgid       NUMBER(12) not null,
  hm_img_id     NUMBER(12) not null,
  hm_img_order  NUMBER(2),
  image_name    VARCHAR2(200),
  image_path    VARCHAR2(1000),
  isdeleted     VARCHAR2(1) default 'N' not null,
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  created_date  DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  module_type   NVARCHAR2(1),
  h_status      NVARCHAR2(1)
)
;
alter table TB_EIP_HOME_IMAGES_HIST
  add constraint PK_H_IMGID primary key (H_IMGID);

prompt
prompt Creating table TB_EIP_LINKS_MASTER
prompt ==================================
prompt
create table TB_EIP_LINKS_MASTER
(
  link_id         NUMBER(12) not null,
  link_path       VARCHAR2(250),
  link_image_name VARCHAR2(100),
  link_order      NUMBER(10,2) not null,
  link_title_en   VARCHAR2(500),
  link_title_reg  NVARCHAR2(1000),
  cpd_section     NUMBER(12),
  isdeleted       VARCHAR2(1) default 'N' not null,
  orgid           NUMBER(4) not null,
  user_id         NUMBER(7) not null,
  lang_id         NUMBER(7) not null,
  created_date    DATE not null,
  updated_by      NUMBER(7),
  updated_date    DATE,
  lg_ip_mac       VARCHAR2(100),
  lg_ip_mac_upd   VARCHAR2(100),
  is_link_modify  NVARCHAR2(1) default 'F',
  cheker_flag     VARCHAR2(1) default 'N'
)
;
comment on column TB_EIP_LINKS_MASTER.link_id
  is 'Link Id  which is primary key of table';
comment on column TB_EIP_LINKS_MASTER.link_path
  is 'Actual Link';
comment on column TB_EIP_LINKS_MASTER.link_image_name
  is 'Image for a link';
comment on column TB_EIP_LINKS_MASTER.link_order
  is 'Order of Link';
comment on column TB_EIP_LINKS_MASTER.link_title_en
  is 'Link Title In English';
comment on column TB_EIP_LINKS_MASTER.link_title_reg
  is 'Link Title In Regional language';
comment on column TB_EIP_LINKS_MASTER.cpd_section
  is 'Look up for Section to which the Link is mapped. prefix HQS';
comment on column TB_EIP_LINKS_MASTER.isdeleted
  is 'Record Deletion flag - value N non-deleted record and Y- deleted record';
comment on column TB_EIP_LINKS_MASTER.orgid
  is 'Organization Id';
comment on column TB_EIP_LINKS_MASTER.user_id
  is 'User Id';
comment on column TB_EIP_LINKS_MASTER.lang_id
  is 'Language Id';
comment on column TB_EIP_LINKS_MASTER.created_date
  is 'Created Date';
comment on column TB_EIP_LINKS_MASTER.updated_by
  is 'Modified By';
comment on column TB_EIP_LINKS_MASTER.updated_date
  is 'Modification Date';
comment on column TB_EIP_LINKS_MASTER.lg_ip_mac
  is 'Client Machine''''''''s Login Name | IP Address | Physical Address';
comment on column TB_EIP_LINKS_MASTER.lg_ip_mac_upd
  is 'Updated Client Machine''''''''s Login Name | IP Address | Physical Address';
comment on column TB_EIP_LINKS_MASTER.is_link_modify
  is 'FLAG';
alter table TB_EIP_LINKS_MASTER
  add constraint PK_EIP_LINK_MAS_ID primary key (LINK_ID);
alter table TB_EIP_LINKS_MASTER
  add constraint FKF50FCAC418DADB9 foreign key (USER_ID)
  references EMPLOYEE (EMPID);
alter table TB_EIP_LINKS_MASTER
  add constraint FK_EIP_LM_ORG_ID foreign key (ORGID)
  references TB_ORGANISATION (ORGID);

prompt
prompt Creating table TB_EIP_LINKS_MASTER_HIST
prompt =======================================
prompt
create table TB_EIP_LINKS_MASTER_HIST
(
  h_linkid        NUMBER(12) not null,
  link_id         NUMBER(12) not null,
  link_path       VARCHAR2(250),
  link_image_name VARCHAR2(100),
  link_order      NUMBER(10,2) not null,
  link_title_en   VARCHAR2(500),
  link_title_reg  NVARCHAR2(1000),
  cpd_section     NUMBER(12),
  isdeleted       VARCHAR2(1) default 'N' not null,
  orgid           NUMBER(4) not null,
  user_id         NUMBER(7) not null,
  lang_id         NUMBER(7) not null,
  created_date    DATE not null,
  updated_by      NUMBER(7),
  updated_date    DATE,
  lg_ip_mac       VARCHAR2(100),
  lg_ip_mac_upd   VARCHAR2(100),
  is_link_modify  NVARCHAR2(1) default 'F',
  h_status        NVARCHAR2(1)
)
;
alter table TB_EIP_LINKS_MASTER_HIST
  add constraint PK_H_LINKID primary key (H_LINKID);

prompt
prompt Creating table TB_EIP_PROFILE_MASTER
prompt ====================================
prompt
create table TB_EIP_PROFILE_MASTER
(
  profile_id      NUMBER(12) not null,
  address         VARCHAR2(200),
  image_name      VARCHAR2(1000),
  image_size      VARCHAR2(150),
  designation_en  VARCHAR2(100),
  designation_reg NVARCHAR2(1000),
  email_id        VARCHAR2(150),
  link_title_en   VARCHAR2(150),
  link_title_reg  NVARCHAR2(1000),
  p_name_en       VARCHAR2(100),
  p_name_reg      NVARCHAR2(1000),
  prabhag_en      VARCHAR2(100),
  prabhag_reg     NVARCHAR2(1000),
  profile_en      VARCHAR2(4000),
  profile_reg     NVARCHAR2(2000),
  cpd_section     NUMBER(12),
  summary_en      VARCHAR2(2000),
  summary_reg     VARCHAR2(2000),
  isdeleted       VARCHAR2(1) default 'N' not null,
  orgid           NUMBER(4) not null,
  user_id         NUMBER(7) not null,
  lang_id         NUMBER(7) not null,
  created_date    DATE not null,
  updated_by      NUMBER(7),
  updated_date    DATE,
  lg_ip_mac       VARCHAR2(100),
  lg_ip_mac_upd   VARCHAR2(100),
  cheker_flag     VARCHAR2(1) default 'N',
  dms_doc_id      VARCHAR2(100)
)
;
comment on column TB_EIP_PROFILE_MASTER.profile_id
  is 'Primary Key';
comment on column TB_EIP_PROFILE_MASTER.address
  is 'Address ';
comment on column TB_EIP_PROFILE_MASTER.image_name
  is 'Image file Name';
comment on column TB_EIP_PROFILE_MASTER.image_size
  is 'Image Size';
comment on column TB_EIP_PROFILE_MASTER.email_id
  is 'Email Id';
comment on column TB_EIP_PROFILE_MASTER.p_name_en
  is 'Person Name in English Language ';
comment on column TB_EIP_PROFILE_MASTER.p_name_reg
  is 'Person Name in Regional Language ';
comment on column TB_EIP_PROFILE_MASTER.prabhag_en
  is 'Prabhag Name in English Language ';
comment on column TB_EIP_PROFILE_MASTER.prabhag_reg
  is 'Prabhag Name in Regional  Language ';
comment on column TB_EIP_PROFILE_MASTER.cpd_section
  is 'Look up for Section to which the Link is mapped. prefix PMS';
alter table TB_EIP_PROFILE_MASTER
  add constraint PK_EIP_PROF_MAS_ID primary key (PROFILE_ID);
alter table TB_EIP_PROFILE_MASTER
  add constraint FK_EIP_PM_ORG_ID foreign key (ORGID)
  references TB_ORGANISATION (ORGID);

prompt
prompt Creating table TB_EIP_PROFILE_MASTER_HIST
prompt =========================================
prompt
create table TB_EIP_PROFILE_MASTER_HIST
(
  h_profileid     NUMBER(12) not null,
  profile_id      NUMBER(12) not null,
  address         VARCHAR2(200),
  image_name      VARCHAR2(1000),
  image_size      VARCHAR2(150),
  designation_en  VARCHAR2(100),
  designation_reg NVARCHAR2(1000),
  email_id        VARCHAR2(150),
  link_title_en   VARCHAR2(150),
  link_title_reg  NVARCHAR2(1000),
  p_name_en       VARCHAR2(100),
  p_name_reg      NVARCHAR2(1000),
  prabhag_en      VARCHAR2(100),
  prabhag_reg     NVARCHAR2(1000),
  profile_en      VARCHAR2(4000),
  profile_reg     NVARCHAR2(2000),
  cpd_section     NUMBER(12),
  summary_en      VARCHAR2(2000),
  summary_reg     VARCHAR2(2000),
  isdeleted       VARCHAR2(1) default 'N' not null,
  orgid           NUMBER(4) not null,
  user_id         NUMBER(7) not null,
  lang_id         NUMBER(7) not null,
  created_date    DATE not null,
  updated_by      NUMBER(7),
  updated_date    DATE,
  lg_ip_mac       VARCHAR2(100),
  lg_ip_mac_upd   VARCHAR2(100),
  h_status        NVARCHAR2(1)
)
;
alter table TB_EIP_PROFILE_MASTER_HIST
  add constraint PK_H_PROFILEID primary key (H_PROFILEID);

prompt
prompt Creating table TB_EIP_PROJECTINFO
prompt =================================
prompt
create table TB_EIP_PROJECTINFO
(
  info_id       NUMBER(12) not null,
  ttl_desc_en   VARCHAR2(1000) not null,
  ttl_desc_reg  VARCHAR2(1000) not null,
  info_desc_en  VARCHAR2(1000) not null,
  info_desc_reg VARCHAR2(1000) not null,
  isdeleted     VARCHAR2(1) default 'N' not null,
  orgid         NUMBER(7) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  created_date  DATE,
  updated_by    VARCHAR2(1000),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(1000),
  lg_ip_mac_upd VARCHAR2(1000),
  cheker_flag   VARCHAR2(1) default 'N'
)
;
alter table TB_EIP_PROJECTINFO
  add constraint PK_ID_PROJECTINFO primary key (INFO_ID);
alter table TB_EIP_PROJECTINFO
  add constraint PK_ORG_ID_ORGANIZATION foreign key (ORGID)
  references TB_ORGANISATION (ORGID);

prompt
prompt Creating table TB_EIP_PROJECTINFO_HIST
prompt ======================================
prompt
create table TB_EIP_PROJECTINFO_HIST
(
  h_infid       NUMBER(12) not null,
  info_id       NUMBER(12) not null,
  ttl_desc_en   VARCHAR2(1000) not null,
  ttl_desc_reg  VARCHAR2(1000) not null,
  info_desc_en  VARCHAR2(1000) not null,
  info_desc_reg VARCHAR2(1000) not null,
  isdeleted     VARCHAR2(1) default 'N' not null,
  orgid         NUMBER(7) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  created_date  DATE,
  updated_by    VARCHAR2(1000),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(1000),
  lg_ip_mac_upd VARCHAR2(1000),
  h_status      NVARCHAR2(1)
)
;
alter table TB_EIP_PROJECTINFO_HIST
  add constraint PK_H_INFOID primary key (H_INFID);

prompt
prompt Creating table TB_EIP_PUBLIC_NOTICES
prompt ====================================
prompt
create table TB_EIP_PUBLIC_NOTICES
(
  pn_id            NUMBER(12) not null,
  dept_id          NUMBER(12),
  notice_sub_en    VARCHAR2(250),
  notice_sub_reg   VARCHAR2(500),
  detail_en        VARCHAR2(500),
  detail_reg       VARCHAR2(1000),
  issue_date       DATE,
  validity_date    DATE,
  publish_flag     VARCHAR2(1),
  profile_img_path VARCHAR2(1000),
  isdeleted        VARCHAR2(1) default 'N' not null,
  orgid            NUMBER(4) not null,
  user_id          NUMBER(7) not null,
  lang_id          NUMBER(7) not null,
  created_date     DATE not null,
  updated_by       NUMBER(7),
  updated_date     DATE,
  lg_ip_mac        VARCHAR2(100),
  lg_ip_mac_upd    VARCHAR2(100),
  cheker_flag      VARCHAR2(1) default 'N'
)
;
alter table TB_EIP_PUBLIC_NOTICES
  add primary key (PN_ID);
alter table TB_EIP_PUBLIC_NOTICES
  add constraint FK886D7E8938B65205 foreign key (UPDATED_BY)
  references EMPLOYEE (EMPID);
alter table TB_EIP_PUBLIC_NOTICES
  add constraint FK886D7E89418DADB9 foreign key (USER_ID)
  references EMPLOYEE (EMPID);
alter table TB_EIP_PUBLIC_NOTICES
  add constraint FK886D7E896078ED5 foreign key (ORGID)
  references TB_ORGANISATION (ORGID);
alter table TB_EIP_PUBLIC_NOTICES
  add constraint FK886D7E8985D4DEA3 foreign key (DEPT_ID)
  references TB_DEPARTMENT (DP_DEPTID);

prompt
prompt Creating table TB_EIP_PUBLIC_NOTICES_HIST
prompt =========================================
prompt
create table TB_EIP_PUBLIC_NOTICES_HIST
(
  h_pnid           NUMBER(12) not null,
  pn_id            NUMBER(12) not null,
  dept_id          NUMBER(12),
  notice_sub_en    VARCHAR2(250),
  notice_sub_reg   VARCHAR2(500),
  detail_en        VARCHAR2(500),
  detail_reg       VARCHAR2(1000),
  issue_date       DATE,
  validity_date    DATE,
  publish_flag     VARCHAR2(1),
  profile_img_path VARCHAR2(1000),
  isdeleted        VARCHAR2(1) default 'N' not null,
  orgid            NUMBER(4) not null,
  user_id          NUMBER(7) not null,
  lang_id          NUMBER(7) not null,
  created_date     DATE not null,
  updated_by       NUMBER(7),
  updated_date     DATE,
  lg_ip_mac        VARCHAR2(100),
  lg_ip_mac_upd    VARCHAR2(100),
  h_status         VARCHAR2(1),
  dms_doc_id       VARCHAR2(100)
)
;
alter table TB_EIP_PUBLIC_NOTICES_HIST
  add primary key (H_PNID);

prompt
prompt Creating table TB_EIP_SUB_LINKS_MASTER
prompt ======================================
prompt
create table TB_EIP_SUB_LINKS_MASTER
(
  sub_link_par_id   NUMBER,
  link_id           NUMBER(10),
  has_sub_link      CHAR(1) default 'N' not null,
  sub_link_name_en  VARCHAR2(100) not null,
  sub_link_mas_id   NUMBER not null,
  is_custom         CHAR(1) default 'N',
  isdeleted         CHAR(1) default 'N' not null,
  sub_link_name_rg  NVARCHAR2(1000) not null,
  orgid             NUMBER(12) not null,
  user_id           NUMBER(12) not null,
  lang_id           NUMBER(4) not null,
  created_date      DATE not null,
  updated_by        NUMBER(12),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100) not null,
  lg_ip_mac_upd     VARCHAR2(100),
  page_url          VARCHAR2(200),
  cpd_secion_type   NUMBER(12),
  cpd_img_link_type NUMBER(12),
  sub_link_order    NUMBER(10,2) not null,
  is_link_modify    NVARCHAR2(1) default 'F',
  cpd_secion_type1  NUMBER(12),
  cpd_secion_type2  NUMBER(12),
  cpd_secion_type3  NUMBER(12),
  cpd_secion_type4  NUMBER(12),
  cheker_flag       VARCHAR2(1) default 'N'
)
;
comment on column TB_EIP_SUB_LINKS_MASTER.link_id
  is 'Foreign key column of TB_EIP_LINK_MASTER';
comment on column TB_EIP_SUB_LINKS_MASTER.isdeleted
  is 'Record is deleted or not.';
comment on column TB_EIP_SUB_LINKS_MASTER.orgid
  is 'Organisation id';
comment on column TB_EIP_SUB_LINKS_MASTER.user_id
  is 'User Id who insert new sub link information';
comment on column TB_EIP_SUB_LINKS_MASTER.lang_id
  is 'Language Id';
comment on column TB_EIP_SUB_LINKS_MASTER.created_date
  is 'Record creation date';
comment on column TB_EIP_SUB_LINKS_MASTER.updated_by
  is 'User Id who update the record';
comment on column TB_EIP_SUB_LINKS_MASTER.updated_date
  is 'Date on which record get updated';
comment on column TB_EIP_SUB_LINKS_MASTER.lg_ip_mac
  is 'Client machine IP address from which record get inserted';
comment on column TB_EIP_SUB_LINKS_MASTER.lg_ip_mac_upd
  is 'Client machine IP address from which record get updated';
comment on column TB_EIP_SUB_LINKS_MASTER.cpd_secion_type
  is 'Foreign reference from EST prefix';
comment on column TB_EIP_SUB_LINKS_MASTER.cpd_img_link_type
  is 'Foreign reference from EIL prefix';
comment on column TB_EIP_SUB_LINKS_MASTER.is_link_modify
  is 'FLAG ';
alter table TB_EIP_SUB_LINKS_MASTER
  add constraint PK_ID primary key (SUB_LINK_MAS_ID);
alter table TB_EIP_SUB_LINKS_MASTER
  add constraint FK_SUB_LINK_PAR_ID foreign key (SUB_LINK_PAR_ID)
  references TB_EIP_SUB_LINKS_MASTER (SUB_LINK_MAS_ID) on delete cascade;

prompt
prompt Creating table TB_EIP_SUB_LINKS_MASTER_HIST
prompt ===========================================
prompt
create table TB_EIP_SUB_LINKS_MASTER_HIST
(
  h_sublinkid       NUMBER not null,
  sub_link_par_id   NUMBER,
  link_id           NUMBER(10),
  has_sub_link      CHAR(1) default 'N' not null,
  sub_link_name_en  VARCHAR2(100) not null,
  sub_link_mas_id   NUMBER not null,
  is_custom         CHAR(1) default 'N',
  isdeleted         CHAR(1) default 'N' not null,
  sub_link_name_rg  NVARCHAR2(1000) not null,
  orgid             NUMBER(12) not null,
  user_id           NUMBER(12) not null,
  lang_id           NUMBER(4) not null,
  created_date      DATE not null,
  updated_by        NUMBER(12),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100) not null,
  lg_ip_mac_upd     VARCHAR2(100),
  page_url          VARCHAR2(200),
  cpd_secion_type   NUMBER(12),
  cpd_img_link_type NUMBER(12),
  sub_link_order    NUMBER(10,2) not null,
  is_link_modify    NVARCHAR2(1) default 'F',
  is_status         NVARCHAR2(1)
)
;
alter table TB_EIP_SUB_LINKS_MASTER_HIST
  add constraint PK_H_SUBLINKID primary key (H_SUBLINKID);

prompt
prompt Creating table TB_EIP_SUB_LINK_FIELDS_DTL
prompt =========================================
prompt
create table TB_EIP_SUB_LINK_FIELDS_DTL
(
  sub_link_field_dtl_id  NUMBER not null,
  sub_links_mas_id       NUMBER not null,
  txt_01_en              VARCHAR2(150),
  txt_01_rg              VARCHAR2(150),
  txt_02_en              VARCHAR2(150),
  txt_02_rg              VARCHAR2(150),
  txt_03_en              VARCHAR2(150),
  txt_03_rg              VARCHAR2(150),
  txt_04_en              VARCHAR2(150),
  txt_04_rg              VARCHAR2(150),
  txt_05_en              VARCHAR2(150),
  txt_05_rg              VARCHAR2(150),
  txt_06_en              VARCHAR2(150),
  txt_06_rg              VARCHAR2(150),
  txt_07_en              VARCHAR2(150),
  txt_07_rg              VARCHAR2(150),
  txt_08_en              VARCHAR2(150),
  txt_08_rg              VARCHAR2(150),
  txt_09_en              VARCHAR2(150),
  txt_10_en              VARCHAR2(150),
  txt_10_rg              VARCHAR2(150),
  txt_11_en              VARCHAR2(150),
  txt_11_rg              VARCHAR2(150),
  txt_12_en              VARCHAR2(150),
  txt_12_rg              VARCHAR2(150),
  txta_01_en             NVARCHAR2(2000),
  txta_01_rg             NVARCHAR2(2000),
  txta_02_en             NVARCHAR2(2000),
  txta_02_rg             NVARCHAR2(2000),
  att_01                 VARCHAR2(1000),
  att_02                 VARCHAR2(1000),
  date_01                DATE,
  date_02                DATE,
  date_03                DATE,
  date_04                DATE,
  isdeleted              CHAR(1) default 'N',
  txt_09_rg              VARCHAR2(150),
  orgid                  NUMBER(4) not null,
  user_id                NUMBER(7) not null,
  lang_id                NUMBER not null,
  created_date           DATE not null,
  updated_by             NUMBER(7),
  updated_date           DATE,
  lg_ip_mac              VARCHAR2(100) not null,
  lg_ip_mac_upd          VARCHAR2(100),
  profile_img_path       VARCHAR2(1000),
  sub_link_field_dtl_ord NUMBER(10,2),
  txt_03_en_number       NUMBER(12),
  txt_03_en_blob         BLOB,
  dms_doc_id             VARCHAR2(100)
)
;
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.sub_links_mas_id
  is 'Foreign key column of TB_SUB_LINKS_MASTER';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_01_en
  is 'Text form field 01 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_01_rg
  is 'Text form field 01 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_02_en
  is 'Text form field 02 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_02_rg
  is 'Text form field 02 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_03_en
  is 'Text form field 03 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_03_rg
  is 'Text form field 03 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_04_en
  is 'Text form field 04 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_04_rg
  is 'Text form field 04 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_05_en
  is 'Text form field 05 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_05_rg
  is 'Text form field 05 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_06_en
  is 'Text form field 06 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_06_rg
  is 'Text form field 06 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_07_en
  is 'Text form field 07 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_07_rg
  is 'Text form field 07 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_08_en
  is 'Text form field 08 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_08_rg
  is 'Text form field 08 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_09_en
  is 'Text form field 09 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_10_en
  is 'Text form field 10 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_10_rg
  is 'Text form field 10 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_11_en
  is 'Text form field 11 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_11_rg
  is 'Text form field 11 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_12_en
  is 'Text form field 12 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_12_rg
  is 'Text form field 12 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txta_01_en
  is 'Text area field 01 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txta_01_rg
  is 'Text area field 01 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txta_02_en
  is 'Text area field 02 in english';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txta_02_rg
  is 'Text area field 01 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.att_01
  is 'Attachment field 01';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.att_02
  is 'Attachment field 02';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.date_01
  is 'Date field 1';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.date_02
  is 'Date field 2';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.date_03
  is 'Date field 3';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.date_04
  is 'Date field 4';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.isdeleted
  is 'Record deleted flag status';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.txt_09_rg
  is 'Text form field 09 in regional';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.orgid
  is 'Organisation id';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.user_id
  is 'User Id who insert new record';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.created_date
  is 'Record creation date';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.updated_by
  is 'User Id who update the record';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.updated_date
  is 'Date on which record get updated';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.lg_ip_mac
  is 'Client machine IP address from which record get inserted';
comment on column TB_EIP_SUB_LINK_FIELDS_DTL.lg_ip_mac_upd
  is 'Client machine IP address from which record get updated';
alter table TB_EIP_SUB_LINK_FIELDS_DTL
  add constraint PK_FIELD_DTL_ID primary key (SUB_LINK_FIELD_DTL_ID);
alter table TB_EIP_SUB_LINK_FIELDS_DTL
  add constraint FK6BEFBD1838B65205 foreign key (UPDATED_BY)
  references EMPLOYEE (EMPID);
alter table TB_EIP_SUB_LINK_FIELDS_DTL
  add constraint FK6BEFBD18418DADB9 foreign key (USER_ID)
  references EMPLOYEE (EMPID);
alter table TB_EIP_SUB_LINK_FIELDS_DTL
  add constraint FK6BEFBD186078ED5 foreign key (ORGID)
  references TB_ORGANISATION (ORGID);
alter table TB_EIP_SUB_LINK_FIELDS_DTL
  add constraint FK_LINKS_FIELD_MAS_ID foreign key (SUB_LINKS_MAS_ID)
  references TB_EIP_SUB_LINKS_MASTER (SUB_LINK_MAS_ID) on delete cascade;

prompt
prompt Creating table TB_EIP_SUB_LINK_FIELD_MAP
prompt ========================================
prompt
create table TB_EIP_SUB_LINK_FIELD_MAP
(
  sub_link_field_id NUMBER not null,
  sub_link_mas_id   NUMBER,
  field_name_en     VARCHAR2(100) not null,
  field_type_id     NUMBER not null,
  field_seq         NUMBER not null,
  field_name_map    VARCHAR2(20) not null,
  is_used           CHAR(1) default 'Y' not null,
  is_mandatory      CHAR(1) default 'N' not null,
  field_name_rg     VARCHAR2(150) not null,
  isdeleted         CHAR(1) default 'N',
  orgid             NUMBER(12),
  user_id           NUMBER(12),
  lang_id           NUMBER(4),
  created_date      DATE,
  updated_by        NUMBER(12),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100),
  sub_section_type  NUMBER(12)
)
;
comment on column TB_EIP_SUB_LINK_FIELD_MAP.field_name_rg
  is 'Regional label name for field';
comment on column TB_EIP_SUB_LINK_FIELD_MAP.isdeleted
  is 'Record is deleted flag status';
comment on column TB_EIP_SUB_LINK_FIELD_MAP.orgid
  is 'Organisation id';
comment on column TB_EIP_SUB_LINK_FIELD_MAP.user_id
  is 'User Id who insert new record';
comment on column TB_EIP_SUB_LINK_FIELD_MAP.lang_id
  is 'Language Id';
comment on column TB_EIP_SUB_LINK_FIELD_MAP.created_date
  is 'Record creation date';
comment on column TB_EIP_SUB_LINK_FIELD_MAP.updated_by
  is 'User Id who update the record';
comment on column TB_EIP_SUB_LINK_FIELD_MAP.updated_date
  is 'Date on which record get updated';
comment on column TB_EIP_SUB_LINK_FIELD_MAP.lg_ip_mac
  is 'Client machine IP address from which record get inserted';
comment on column TB_EIP_SUB_LINK_FIELD_MAP.lg_ip_mac_upd
  is 'Client machine IP address from which record get updated';
alter table TB_EIP_SUB_LINK_FIELD_MAP
  add constraint PK_SUB_LINK_FIELD_ID primary key (SUB_LINK_FIELD_ID);
alter table TB_EIP_SUB_LINK_FIELD_MAP
  add constraint FK1404AA3538B65205 foreign key (UPDATED_BY)
  references EMPLOYEE (EMPID);
alter table TB_EIP_SUB_LINK_FIELD_MAP
  add constraint FK1404AA35418DADB9 foreign key (USER_ID)
  references EMPLOYEE (EMPID);
alter table TB_EIP_SUB_LINK_FIELD_MAP
  add constraint FK1404AA356078ED5 foreign key (ORGID)
  references TB_ORGANISATION (ORGID);
alter table TB_EIP_SUB_LINK_FIELD_MAP
  add constraint FK_SUB_LINK_MAS_ID foreign key (SUB_LINK_MAS_ID)
  references TB_EIP_SUB_LINKS_MASTER (SUB_LINK_MAS_ID) on delete cascade;

prompt
prompt Creating table TB_EIP_SUB_LINK_FIE_DTL_HIST
prompt ===========================================
prompt
create table TB_EIP_SUB_LINK_FIE_DTL_HIST
(
  h_sublinkid            NUMBER not null,
  sub_link_field_dtl_id  NUMBER not null,
  sub_links_mas_id       NUMBER not null,
  txt_01_en              VARCHAR2(150),
  txt_01_rg              VARCHAR2(150),
  txt_02_en              VARCHAR2(150),
  txt_02_rg              VARCHAR2(150),
  txt_03_en              VARCHAR2(150),
  txt_03_rg              VARCHAR2(150),
  txt_04_en              VARCHAR2(150),
  txt_04_rg              VARCHAR2(150),
  txt_05_en              VARCHAR2(150),
  txt_05_rg              VARCHAR2(150),
  txt_06_en              VARCHAR2(150),
  txt_06_rg              VARCHAR2(150),
  txt_07_en              VARCHAR2(150),
  txt_07_rg              VARCHAR2(150),
  txt_08_en              VARCHAR2(150),
  txt_08_rg              VARCHAR2(150),
  txt_09_en              VARCHAR2(150),
  txt_10_en              VARCHAR2(150),
  txt_10_rg              VARCHAR2(150),
  txt_11_en              VARCHAR2(150),
  txt_11_rg              VARCHAR2(150),
  txt_12_en              VARCHAR2(150),
  txt_12_rg              VARCHAR2(150),
  txta_01_en             NVARCHAR2(2000),
  txta_01_rg             NVARCHAR2(2000),
  txta_02_en             NVARCHAR2(2000),
  txta_02_rg             NVARCHAR2(2000),
  att_01                 VARCHAR2(1000),
  att_02                 VARCHAR2(1000),
  date_01                DATE,
  date_02                DATE,
  date_03                DATE,
  date_04                DATE,
  isdeleted              CHAR(1) default 'N',
  txt_09_rg              VARCHAR2(150),
  orgid                  NUMBER(4) not null,
  user_id                NUMBER(7) not null,
  lang_id                NUMBER not null,
  created_date           DATE not null,
  updated_by             NUMBER(7),
  updated_date           DATE,
  lg_ip_mac              VARCHAR2(100) not null,
  lg_ip_mac_upd          VARCHAR2(100),
  profile_img_path       VARCHAR2(1000),
  sub_link_field_dtl_ord NUMBER(10,2),
  h_status               NVARCHAR2(1),
  dms_doc_id             VARCHAR2(100)
)
;
alter table TB_EIP_SUB_LINK_FIE_DTL_HIST
  add constraint PK_HSUBLINKID primary key (H_SUBLINKID);

prompt
prompt Creating table TB_EIP_SUB_LINK_FIE_MAP_HIST
prompt ===========================================
prompt
create table TB_EIP_SUB_LINK_FIE_MAP_HIST
(
  h_sublink_field   NUMBER not null,
  sub_link_field_id NUMBER not null,
  sub_link_mas_id   NUMBER,
  field_name_en     VARCHAR2(100) not null,
  field_type_id     NUMBER not null,
  field_seq         NUMBER not null,
  field_name_map    VARCHAR2(20) not null,
  is_used           CHAR(1) default 'Y' not null,
  is_mandatory      CHAR(1) default 'N' not null,
  field_name_rg     VARCHAR2(150) not null,
  isdeleted         CHAR(1) default 'N',
  orgid             NUMBER(12),
  user_id           NUMBER(12),
  lang_id           NUMBER(4),
  created_date      DATE,
  updated_by        NUMBER(12),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100),
  h_status          NVARCHAR2(1)
)
;
alter table TB_EIP_SUB_LINK_FIE_MAP_HIST
  add constraint PK_H_SUBLINK_FIELD primary key (H_SUBLINK_FIELD);

prompt
prompt Creating table TB_EIP_USER_CONTACT_US
prompt =====================================
prompt
create table TB_EIP_USER_CONTACT_US
(
  att_id        NUMBER(12) not null,
  att_path      NVARCHAR2(2000),
  phone_no      NUMBER(12),
  desc_query    NVARCHAR2(500),
  first_name    NVARCHAR2(100),
  last_name     NVARCHAR2(100),
  email_id      NVARCHAR2(100),
  isdeleted     VARCHAR2(1) default 'N' not null,
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  created_date  DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  dms_doc_id    VARCHAR2(100)
)
;
alter table TB_EIP_USER_CONTACT_US
  add constraint PK_TB_EIP_USER_CONTACT_US primary key (ATT_ID);

prompt
prompt Creating table TB_FINANCIALYEAR
prompt ===============================
prompt
create table TB_FINANCIALYEAR
(
  fa_yearid      NUMBER(12) not null,
  fa_fromdate    DATE,
  fa_todate      DATE,
  fa_closed      NVARCHAR2(1),
  user_id        NUMBER(7) not null,
  lang_id        NUMBER(7) not null,
  lmoddate       DATE not null,
  fa_provclosing NVARCHAR2(1) default 'n' not null,
  updated_by     NUMBER(7),
  updated_date   DATE
)
;
comment on column TB_FINANCIALYEAR.fa_yearid
  is 'Primary Key';
comment on column TB_FINANCIALYEAR.fa_fromdate
  is 'Start date of the financial year';
comment on column TB_FINANCIALYEAR.fa_todate
  is 'End date of the financial year';
comment on column TB_FINANCIALYEAR.fa_closed
  is 'Flag for Permanent Closing';
comment on column TB_FINANCIALYEAR.user_id
  is 'User Identity';
comment on column TB_FINANCIALYEAR.lang_id
  is 'Language Identity';
comment on column TB_FINANCIALYEAR.lmoddate
  is 'Last Modification Date';
comment on column TB_FINANCIALYEAR.fa_provclosing
  is 'Flag for Provisional Closing';
comment on column TB_FINANCIALYEAR.updated_by
  is 'Updated by';
comment on column TB_FINANCIALYEAR.updated_date
  is 'Updated Date';
alter table TB_FINANCIALYEAR
  add constraint PK_FINANCIALYEAR_YEARID primary key (FA_YEARID);

prompt
prompt Creating table TB_FINANCIALYEAR_HIST
prompt ====================================
prompt
create table TB_FINANCIALYEAR_HIST
(
  h_fayearid     NUMBER(12) not null,
  fa_yearid      NUMBER(12) not null,
  fa_fromdate    DATE,
  fa_todate      DATE,
  fa_closed      NVARCHAR2(1),
  user_id        NUMBER(7) not null,
  lang_id        NUMBER(7) not null,
  lmoddate       DATE not null,
  fa_provclosing NVARCHAR2(1) default 'n' not null,
  updated_by     NUMBER(7),
  updated_date   DATE,
  h_status       NVARCHAR2(1)
)
;
alter table TB_FINANCIALYEAR_HIST
  add constraint PK_H_FAYEARID primary key (H_FAYEARID);

prompt
prompt Creating table TB_GROUP_MAST_HIST
prompt =================================
prompt
create table TB_GROUP_MAST_HIST
(
  h_gmid        NUMBER not null,
  gm_id         NUMBER not null,
  gr_code       NVARCHAR2(20),
  gr_name       NVARCHAR2(500),
  gr_name_reg   NVARCHAR2(500),
  gr_desc_eng   NVARCHAR2(500),
  gr_desc_reg   NVARCHAR2(1000),
  orgid         NUMBER,
  org_specific  CHAR(1) default 'N',
  gr_status     CHAR(1),
  lang_id       NUMBER,
  user_id       NUMBER,
  entry_date    DATE,
  lg_ip_mac     NVARCHAR2(100),
  updated_by    NUMBER,
  updated_date  DATE,
  lg_ip_mac_upd NVARCHAR2(100),
  gr_default    CHAR(1),
  h_status      NVARCHAR2(1)
)
;
alter table TB_GROUP_MAST_HIST
  add constraint PK_H_GMID primary key (H_GMID);

prompt
prompt Creating table TB_JAVA_SEQ_GENERATION
prompt =====================================
prompt
create table TB_JAVA_SEQ_GENERATION
(
  sq_id         NUMBER(12) not null,
  sq_mdl_name   NVARCHAR2(20),
  sq_tbl_name   NVARCHAR2(50),
  sq_fld_name   NVARCHAR2(50),
  sq_seq_name   NVARCHAR2(100),
  sq_str_with   NUMBER(12),
  sq_max_num    NUMBER(12),
  sq_str_date   DATE,
  sq_nxt_rst_dt DATE,
  sq_lst_rst_dt DATE,
  sq_rst_typ    NVARCHAR2(1),
  sq_ctr_id     NVARCHAR2(30)
)
;
comment on table TB_JAVA_SEQ_GENERATION
  is 'Table for Automatic Sequence Creation, Generation and Reset';
comment on column TB_JAVA_SEQ_GENERATION.sq_id
  is 'Sequnce Id(PK)';
comment on column TB_JAVA_SEQ_GENERATION.sq_mdl_name
  is 'Module Name';
comment on column TB_JAVA_SEQ_GENERATION.sq_tbl_name
  is 'Table Name';
comment on column TB_JAVA_SEQ_GENERATION.sq_fld_name
  is 'Field Name';
comment on column TB_JAVA_SEQ_GENERATION.sq_seq_name
  is 'Sequence Name';
comment on column TB_JAVA_SEQ_GENERATION.sq_str_with
  is 'Start With';
comment on column TB_JAVA_SEQ_GENERATION.sq_max_num
  is 'Maximum Number';
comment on column TB_JAVA_SEQ_GENERATION.sq_str_date
  is 'Sequnce firstly start date';
comment on column TB_JAVA_SEQ_GENERATION.sq_nxt_rst_dt
  is 'Sequence Next Reset Date';
comment on column TB_JAVA_SEQ_GENERATION.sq_lst_rst_dt
  is 'Sequnce Last Reset Date';
comment on column TB_JAVA_SEQ_GENERATION.sq_rst_typ
  is 'Reset Type(F/Y/M/D/C)';
alter table TB_JAVA_SEQ_GENERATION
  add constraint PK_JAVA_SQ_GEN_SQID primary key (SQ_ID);

prompt
prompt Creating table TB_LOCATION_MAS_HIST
prompt ===================================
prompt
create table TB_LOCATION_MAS_HIST
(
  h_locid         NUMBER(12) not null,
  loc_id          NUMBER(12) not null,
  loc_name_eng    NVARCHAR2(500),
  loc_name_reg    NVARCHAR2(500),
  loc_description NVARCHAR2(1000),
  loc_active      VARCHAR2(1),
  loc_address1    NVARCHAR2(350),
  loc_address2    NVARCHAR2(350),
  loc_city        NVARCHAR2(50),
  loc_dwz_id      NUMBER(12),
  loc_parentid    NUMBER(12),
  loc_source      CHAR(1),
  loc_aut_status  CHAR(1),
  loc_aut_by      NUMBER(12),
  loc_aut_date    DATE,
  orgid           NUMBER(4),
  user_id         NUMBER(12),
  lang_id         NUMBER(7),
  lmoddate        DATE,
  updated_by      NUMBER(12),
  updated_date    DATE,
  lg_ip_mac       VARCHAR2(100),
  lg_ip_mac_upd   VARCHAR2(100),
  centraleno      NVARCHAR2(50),
  aut_v1          NVARCHAR2(100),
  aut_v2          NVARCHAR2(100),
  aut_v3          NVARCHAR2(100),
  aut_v4          NVARCHAR2(100),
  aut_v5          NVARCHAR2(100),
  aut_n1          NUMBER(15),
  aut_n2          NUMBER(15),
  aut_n3          NUMBER(15),
  aut_n4          NUMBER(15),
  aut_n5          NUMBER(15),
  aut_d1          DATE,
  aut_d2          DATE,
  aut_d3          DATE,
  aut_d4          DATE,
  aut_d5          DATE,
  h_status        NVARCHAR2(1)
)
;
alter table TB_LOCATION_MAS_HIST
  add constraint PK_H_LOCID primary key (H_LOCID);

prompt
prompt Creating table TB_ORG_DESIGNATION
prompt =================================
prompt
create table TB_ORG_DESIGNATION
(
  map_id        NUMBER(12) not null,
  dsgid         NUMBER(12) not null,
  map_status    CHAR(1),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  lmoddate      DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  com_v1        NVARCHAR2(100),
  com_v2        NVARCHAR2(100),
  com_v3        NVARCHAR2(100),
  com_v4        NVARCHAR2(100),
  com_v5        NVARCHAR2(100),
  com_n1        NUMBER(15),
  com_n2        NUMBER(15),
  com_n3        NUMBER(15),
  com_n4        NUMBER(15),
  com_n5        NUMBER(15),
  com_d1        DATE,
  com_d2        DATE,
  com_d3        DATE,
  com_lo1       CHAR(1),
  com_lo2       CHAR(1),
  com_lo3       CHAR(1)
)
;
alter table TB_ORG_DESIGNATION
  add constraint PK_ORG_DESIGNATION_MAPID primary key (MAP_ID);
alter table TB_ORG_DESIGNATION
  add constraint FK_DSGID foreign key (DSGID)
  references DESIGNATION (DSGID);
alter table TB_ORG_DESIGNATION
  add constraint FK_TB_ORG_ID foreign key (ORGID)
  references TB_ORGANISATION (ORGID);

prompt
prompt Creating table TB_PAGE_MASTER
prompt =============================
prompt
create table TB_PAGE_MASTER
(
  page_id     NUMBER(12) not null,
  page_name   NVARCHAR2(200) not null,
  orgid       NUMBER not null,
  total_count NUMBER(20)
)
;
alter table TB_PAGE_MASTER
  add constraint PK_PAGE_ID primary key (PAGE_ID);
alter table TB_PAGE_MASTER
  add constraint FK_PG_ORG_ID foreign key (ORGID)
  references TB_ORGANISATION (ORGID);

prompt
prompt Creating table TB_PG_BANK
prompt =========================
prompt
create table TB_PG_BANK
(
  bm_bankid     NUMBER(12),
  pb_merchantid VARCHAR2(40),
  user_id       NUMBER(7),
  lang_id       NUMBER(7),
  lmoddate      DATE default (sysdate),
  orgid         NUMBER(4),
  bm_active     VARCHAR2(1) default 'A' not null
)
;
comment on column TB_PG_BANK.bm_bankid
  is 'existing in kDNET bank master';
comment on column TB_PG_BANK.pb_merchantid
  is 'marchant id given by bank to client i.e KDMC';
comment on column TB_PG_BANK.user_id
  is 'user id';
comment on column TB_PG_BANK.lang_id
  is 'Longin id';
comment on column TB_PG_BANK.lmoddate
  is 'Last Modification date 1';
comment on column TB_PG_BANK.orgid
  is 'ORGANIZATION ID';
comment on column TB_PG_BANK.bm_active
  is 'Active Flag';
alter table TB_PG_BANK
  add constraint PK_TB_PG_BANK primary key (BM_BANKID);
alter table TB_PG_BANK
  add constraint NN_PGBANK_BANKID
  check ("BM_BANKID" IS NOT NULL);
alter table TB_PG_BANK
  add constraint NN_PGBANK_LANGID
  check ("LANG_ID" IS NOT NULL);
alter table TB_PG_BANK
  add constraint NN_PGBANK_LMODDATE
  check ("LMODDATE" IS NOT NULL);
alter table TB_PG_BANK
  add constraint NN_PGBANK_MERCHANTID
  check ("PB_MERCHANTID" IS NOT NULL);
alter table TB_PG_BANK
  add constraint NN_PGBANK_ORGID
  check ("ORGID" IS NOT NULL);
alter table TB_PG_BANK
  add constraint NN_PGBANK_USERID
  check ("USER_ID" IS NOT NULL);

prompt
prompt Creating table TB_PG_BANKPARAMETER_PORTAL
prompt =========================================
prompt
create table TB_PG_BANKPARAMETER_PORTAL
(
  bankparid  NUMBER(12) not null,
  bm_bank_id NUMBER(12),
  parname    VARCHAR2(1000),
  parvalue   VARCHAR2(1000),
  passvalue  VARCHAR2(1)
)
;

prompt
prompt Creating table TB_PG_BANKPARA_PORTAL_HIST
prompt =========================================
prompt
create table TB_PG_BANKPARA_PORTAL_HIST
(
  h_bankparid NUMBER(12) not null,
  bankparid   NUMBER(12) not null,
  bm_bank_id  NUMBER(12),
  parname     VARCHAR2(1000),
  parvalue    VARCHAR2(1000),
  passvalue   VARCHAR2(1)
)
;

prompt
prompt Creating table TB_PG_BANK_DETAIL
prompt ================================
prompt
create table TB_PG_BANK_DETAIL
(
  bm_bankid    NUMBER(12),
  cpd_id       NUMBER(12),
  pb_bankurl   VARCHAR2(200),
  pb_tablename VARCHAR2(50),
  user_id      NUMBER(7),
  lang_id      NUMBER(7),
  lmoddate     DATE,
  orgid        NUMBER(4)
)
;

prompt
prompt Creating table TB_PG_BANK_DETAIL_HIST
prompt =====================================
prompt
create table TB_PG_BANK_DETAIL_HIST
(
  h_bmbankid   NUMBER(12),
  bm_bankid    NUMBER(12),
  cpd_id       NUMBER(12),
  pb_bankurl   VARCHAR2(200),
  pb_tablename VARCHAR2(50),
  user_id      NUMBER(7),
  lang_id      NUMBER(7),
  lmoddate     DATE,
  orgid        NUMBER(4),
  h_status     NVARCHAR2(1)
)
;

prompt
prompt Creating table TB_PG_BANK_HIST
prompt ==============================
prompt
create table TB_PG_BANK_HIST
(
  h_bmid        NUMBER(12) not null,
  bm_bankid     NUMBER(12),
  pb_merchantid VARCHAR2(40),
  user_id       NUMBER(7),
  lang_id       NUMBER(7),
  lmoddate      DATE default (sysdate),
  orgid         NUMBER(4),
  bm_active     VARCHAR2(1) default 'A' not null,
  h_status      VARCHAR2(1)
)
;
alter table TB_PG_BANK_HIST
  add constraint PK_H_BMID primary key (H_BMID);

prompt
prompt Creating table TB_PG_BANK_PORTAL
prompt ================================
prompt
create table TB_PG_BANK_PORTAL
(
  pbp_id         NUMBER(12) not null,
  pbp_name       NVARCHAR2(500) not null,
  pbp_short_desc NVARCHAR2(50),
  pbp_bank_code  NUMBER(20) not null,
  cb_bankid      NUMBER(12),
  orgid          NUMBER(4),
  user_id        NUMBER(7),
  lang_id        NUMBER(7),
  lmoddate       DATE,
  isdeleted      NUMBER(1),
  pbp_name_mar   NVARCHAR2(500)
)
;

prompt
prompt Creating table TB_PG_BANK_PORTAL_HIST
prompt =====================================
prompt
create table TB_PG_BANK_PORTAL_HIST
(
  h_pbpid        NUMBER(12) not null,
  pbp_id         NUMBER(12) not null,
  pbp_name       NVARCHAR2(500) not null,
  pbp_short_desc NVARCHAR2(50),
  pbp_bank_code  NUMBER(20) not null,
  cb_bankid      NUMBER(12),
  orgid          NUMBER(4),
  user_id        NUMBER(7),
  lang_id        NUMBER(7),
  lmoddate       DATE,
  isdeleted      NUMBER(1),
  pbp_name_mar   NVARCHAR2(500),
  h_status       NVARCHAR2(1)
)
;

prompt
prompt Creating table TB_PG_TRANSACTION_PORTAL
prompt =======================================
prompt
create table TB_PG_TRANSACTION_PORTAL
(
  pt_transid        NUMBER(12),
  po_service_id     NUMBER(12),
  pt_payment_status CHAR(1),
  pt_amount         NUMBER(10,2),
  bm_bankid         NUMBER(12),
  pt_bank_ref_no    VARCHAR2(25),
  pt_transdt        DATE default SYSDATE,
  ctz_citizen_id    VARCHAR2(16),
  sm_service_id     NUMBER(12),
  po_identno1       VARCHAR2(20),
  po_identno2       VARCHAR2(20),
  receipt_no        NUMBER(12),
  paid_by           VARCHAR2(50),
  pt_remark         VARCHAR2(250),
  user_id           NUMBER(7),
  lang_id           NUMBER(7) default 1,
  orgid             NUMBER(4) default 1,
  lmoddate          DATE default SYSDATE
)
;
comment on column TB_PG_TRANSACTION_PORTAL.pt_transid
  is 'Online transaction id';
comment on column TB_PG_TRANSACTION_PORTAL.po_service_id
  is 'Online Service Id';
comment on column TB_PG_TRANSACTION_PORTAL.pt_payment_status
  is 'Default P; P - Pending C - Complete, F - Fail';
comment on column TB_PG_TRANSACTION_PORTAL.pt_amount
  is 'Amount to be paid';
comment on column TB_PG_TRANSACTION_PORTAL.bm_bankid
  is 'Bank id';
comment on column TB_PG_TRANSACTION_PORTAL.pt_bank_ref_no
  is 'Bank reference no';
comment on column TB_PG_TRANSACTION_PORTAL.pt_transdt
  is 'Date 1 of the transaction';
comment on column TB_PG_TRANSACTION_PORTAL.ctz_citizen_id
  is 'Citizen id';
comment on column TB_PG_TRANSACTION_PORTAL.sm_service_id
  is 'Sm_Service_Id';
comment on column TB_PG_TRANSACTION_PORTAL.po_identno1
  is 'Po_IdentNo1';
comment on column TB_PG_TRANSACTION_PORTAL.po_identno2
  is 'Po_IdentNo2';
comment on column TB_PG_TRANSACTION_PORTAL.receipt_no
  is 'Receipt Number form Mainet';
comment on column TB_PG_TRANSACTION_PORTAL.paid_by
  is 'To capture the payee name';
comment on column TB_PG_TRANSACTION_PORTAL.pt_remark
  is 'To capture the Manual receipt genaration remark';
comment on column TB_PG_TRANSACTION_PORTAL.user_id
  is 'User id';
comment on column TB_PG_TRANSACTION_PORTAL.lang_id
  is 'Lang id';
comment on column TB_PG_TRANSACTION_PORTAL.orgid
  is 'Organisation id';
comment on column TB_PG_TRANSACTION_PORTAL.lmoddate
  is 'Lmoddate';
alter table TB_PG_TRANSACTION_PORTAL
  add constraint PK_TB_PG_TRANSACTION_PORTAL primary key (PT_TRANSID);
alter table TB_PG_TRANSACTION_PORTAL
  add constraint NN_PGTRAN_AMOUNT
  check ("PT_AMOUNT" IS NOT NULL);
alter table TB_PG_TRANSACTION_PORTAL
  add constraint NN_PGTRAN_BMBANKID
  check ("BM_BANKID" IS NOT NULL);
alter table TB_PG_TRANSACTION_PORTAL
  add constraint NN_PGTRAN_CITIZENID
  check ("CTZ_CITIZEN_ID" IS NOT NULL);
alter table TB_PG_TRANSACTION_PORTAL
  add constraint NN_PGTRAN_IDENTNO1
  check ("PO_IDENTNO1" IS NOT NULL);
alter table TB_PG_TRANSACTION_PORTAL
  add constraint NN_PGTRAN_LANGID
  check ("LANG_ID" IS NOT NULL);
alter table TB_PG_TRANSACTION_PORTAL
  add constraint NN_PGTRAN_LMODDATE
  check ("LMODDATE" IS NOT NULL);
alter table TB_PG_TRANSACTION_PORTAL
  add constraint NN_PGTRAN_ORGID
  check ("ORGID" IS NOT NULL);
alter table TB_PG_TRANSACTION_PORTAL
  add constraint NN_PGTRAN_PAYMENTSTATUS
  check ("PT_PAYMENT_STATUS" IS NOT NULL);
alter table TB_PG_TRANSACTION_PORTAL
  add constraint NN_PGTRAN_POSERVICEID
  check ("PO_SERVICE_ID" IS NOT NULL);
alter table TB_PG_TRANSACTION_PORTAL
  add constraint NN_PGTRAN_TRANSDT
  check ("PT_TRANSDT" IS NOT NULL);
alter table TB_PG_TRANSACTION_PORTAL
  add constraint NN_PGTRAN_TRANSID
  check ("PT_TRANSID" IS NOT NULL);
alter table TB_PG_TRANSACTION_PORTAL
  add constraint NN_PGTRAN_USER_ID
  check ("USER_ID" IS NOT NULL);

prompt
prompt Creating table TB_PG_TRANSACTION_PORTAL_HIST
prompt ============================================
prompt
create table TB_PG_TRANSACTION_PORTAL_HIST
(
  h_pttransid       NUMBER(12) not null,
  pt_transid        NUMBER(12),
  po_service_id     NUMBER(12),
  pt_payment_status CHAR(1),
  pt_amount         NUMBER(10,2),
  bm_bankid         NUMBER(12),
  pt_bank_ref_no    VARCHAR2(25),
  pt_transdt        DATE default SYSDATE,
  ctz_citizen_id    VARCHAR2(16),
  sm_service_id     NUMBER(12),
  po_identno1       VARCHAR2(20),
  po_identno2       VARCHAR2(20),
  receipt_no        NUMBER(12),
  paid_by           VARCHAR2(50),
  pt_remark         VARCHAR2(250),
  user_id           NUMBER(7),
  lang_id           NUMBER(7) default 1,
  orgid             NUMBER(4) default 1,
  lmoddate          DATE default SYSDATE
)
;
alter table TB_PG_TRANSACTION_PORTAL_HIST
  add constraint PK_H_PTTRANSID primary key (H_PTTRANSID);

prompt
prompt Creating table TB_PORTAL_APPLICATION_MST
prompt ========================================
prompt
create table TB_PORTAL_APPLICATION_MST
(
  pam_id                         NUMBER(12) not null,
  orgid                          NUMBER(4),
  pam_application_id             NUMBER(16),
  pam_application_date           DATE,
  sm_service_id                  NUMBER(12),
  pam_cfc_citizenid              VARCHAR2(16),
  user_id                        NUMBER(7),
  lang_id                        NUMBER(7),
  created_date                   DATE,
  created_by                     NVARCHAR2(100),
  updated_by                     NVARCHAR2(100),
  updated_date                   DATE,
  pam_sla_date                   DATE,
  pam_doc_verification_date      DATE,
  pam_first_appeal_date          DATE,
  pam_second_appeal_date         DATE,
  pam_application_status         NVARCHAR2(100),
  pam_application_rejection_date DATE,
  pam_payment_status             NVARCHAR2(20),
  pam_checklist_app              VARCHAR2(1),
  pam_app_accepted_date          DATE,
  pam_aaple_track_id             VARCHAR2(50),
  pam_aaple_status               VARCHAR2(50),
  pam_digital_sign               VARCHAR2(1) default 'N',
  pam_sign_type                  VARCHAR2(500)
)
;
comment on column TB_PORTAL_APPLICATION_MST.pam_aaple_track_id
  is 'aaple sarkar track Id';
comment on column TB_PORTAL_APPLICATION_MST.pam_aaple_status
  is 'aaple status whether succes or fail';
alter table TB_PORTAL_APPLICATION_MST
  add primary key (PAM_ID);

prompt
prompt Creating table TB_PORTAL_APPLICATION_MST_HIST
prompt =============================================
prompt
create table TB_PORTAL_APPLICATION_MST_HIST
(
  h_pamid                        NUMBER(12) not null,
  pam_id                         NUMBER(12) not null,
  orgid                          NUMBER(4),
  pam_application_id             NUMBER(16),
  pam_application_date           DATE,
  sm_service_id                  NUMBER(12),
  pam_cfc_citizenid              VARCHAR2(16),
  user_id                        NUMBER(7),
  lang_id                        NUMBER(7),
  created_date                   DATE,
  created_by                     NVARCHAR2(100),
  updated_by                     NVARCHAR2(100),
  updated_date                   DATE,
  pam_sla_date                   DATE,
  pam_doc_verification_date      DATE,
  pam_first_appeal_date          DATE,
  pam_second_appeal_date         DATE,
  pam_application_status         NVARCHAR2(100),
  pam_application_rejection_date DATE,
  pam_payment_status             NVARCHAR2(20),
  pam_checklist_app              VARCHAR2(1),
  pam_app_accepted_date          DATE,
  pam_aaple_track_id             VARCHAR2(50),
  pam_aaple_status               VARCHAR2(50),
  pam_digital_sign               VARCHAR2(1) default 'N',
  pam_sign_type                  VARCHAR2(500),
  h_status                       VARCHAR2(1)
)
;
alter table TB_PORTAL_APPLICATION_MST_HIST
  add primary key (PAM_ID);

prompt
prompt Creating table TB_PORTAL_SERVICE_MASTER
prompt =======================================
prompt
create table TB_PORTAL_SERVICE_MASTER
(
  psm_id                     NUMBER(12) not null,
  psm_service_id             NUMBER(12),
  psm_doc_verify_period      NUMBER(12),
  psm_sla_days               NUMBER(12),
  psm_first_appeal_duration  NUMBER(12),
  psm_second_appeal_duration NUMBER(12),
  psm_service_name           VARCHAR2(100),
  psm_service_name_reg       NVARCHAR2(2000),
  psm_short_name             NVARCHAR2(10),
  orgid                      NUMBER(12),
  user_id                    NUMBER(12),
  lang_id                    NUMBER(12),
  lmoddate                   DATE,
  updated_by                 NUMBER(12),
  updated_date               DATE,
  is_deleted                 NVARCHAR2(1) default 'N',
  psm_dp_deptid              NUMBER(12),
  psm_smfid                  NUMBER(12),
  psm_first_apl_autho        VARCHAR2(50),
  psm_sec_apl_autho          VARCHAR2(50)
)
;
comment on table TB_PORTAL_SERVICE_MASTER
  is 'RTS Service Appeal Master';
comment on column TB_PORTAL_SERVICE_MASTER.psm_id
  is 'Primary Key of table';
comment on column TB_PORTAL_SERVICE_MASTER.psm_service_id
  is 'Service Id';
comment on column TB_PORTAL_SERVICE_MASTER.psm_first_apl_autho
  is 'First appeal Authority';
comment on column TB_PORTAL_SERVICE_MASTER.psm_sec_apl_autho
  is 'Second appeal Authority';
alter table TB_PORTAL_SERVICE_MASTER
  add primary key (PSM_ID);

prompt
prompt Creating table TB_PORTAL_SERVICE_MASTER_HIST
prompt ============================================
prompt
create table TB_PORTAL_SERVICE_MASTER_HIST
(
  h_psmid                    NUMBER(12) not null,
  psm_id                     NUMBER(12) not null,
  psm_service_id             NUMBER(12),
  psm_doc_verify_period      NUMBER(12),
  psm_sla_days               NUMBER(12),
  psm_first_appeal_duration  NUMBER(12),
  psm_second_appeal_duration NUMBER(12),
  psm_service_name           VARCHAR2(100),
  psm_service_name_reg       NVARCHAR2(2000),
  psm_short_name             NVARCHAR2(10),
  orgid                      NUMBER(12),
  user_id                    NUMBER(12),
  lang_id                    NUMBER(12),
  lmoddate                   DATE,
  updated_by                 NUMBER(12),
  updated_date               DATE,
  is_deleted                 NVARCHAR2(1) default 'N',
  psm_dp_deptid              NUMBER(12),
  psm_smfid                  NUMBER(12),
  psm_first_apl_autho        VARCHAR2(50),
  psm_sec_apl_autho          VARCHAR2(50),
  h_status                   NVARCHAR2(1)
)
;
alter table TB_PORTAL_SERVICE_MASTER_HIST
  add primary key (H_PSMID);

prompt
prompt Creating table TB_PORTAL_SMS_INTEGRATION
prompt ========================================
prompt
create table TB_PORTAL_SMS_INTEGRATION
(
  se_id           NUMBER(12) not null,
  dp_deptid       NUMBER(12) not null,
  smfid           NUMBER(12) not null,
  alert_type      VARCHAR2(3),
  isdeleted       VARCHAR2(1) default 'N' not null,
  orgid           NUMBER(4) not null,
  user_id         NUMBER(7) not null,
  lang_id         NUMBER(7) not null,
  created_date    DATE not null,
  updated_by      NUMBER(7),
  updated_date    DATE,
  lg_ip_mac       VARCHAR2(100),
  lg_ip_mac_upd   VARCHAR2(100),
  istransactional VARCHAR2(3)
)
;
comment on column TB_PORTAL_SMS_INTEGRATION.se_id
  is 'sms templete Master Primary Key Id';
comment on column TB_PORTAL_SMS_INTEGRATION.dp_deptid
  is 'dept id ';
comment on column TB_PORTAL_SMS_INTEGRATION.smfid
  is 'foreign key for the sysmodfunction ';
comment on column TB_PORTAL_SMS_INTEGRATION.alert_type
  is 'alert type--E-email,S-SMS,B-both,N-for not applicable ';
comment on column TB_PORTAL_SMS_INTEGRATION.isdeleted
  is 'Record Deletion flag - value N non-deleted record and Y- deleted record';
comment on column TB_PORTAL_SMS_INTEGRATION.orgid
  is 'Organization Id.';
comment on column TB_PORTAL_SMS_INTEGRATION.user_id
  is 'User Id';
comment on column TB_PORTAL_SMS_INTEGRATION.lang_id
  is 'Language Id';
comment on column TB_PORTAL_SMS_INTEGRATION.created_date
  is 'Created Date';
comment on column TB_PORTAL_SMS_INTEGRATION.updated_by
  is 'Modified By';
comment on column TB_PORTAL_SMS_INTEGRATION.updated_date
  is 'Modification Date';
comment on column TB_PORTAL_SMS_INTEGRATION.lg_ip_mac
  is 'Client Machine''''s Login Name | IP Address | Physical Address';
comment on column TB_PORTAL_SMS_INTEGRATION.lg_ip_mac_upd
  is 'Updated Client Machine''''s Login Name | IP Address | Physical Address';
comment on column TB_PORTAL_SMS_INTEGRATION.istransactional
  is 'it maintains this template is for transactional service or non transactional servic,Y-transactional,N-non-transactional';
alter table TB_PORTAL_SMS_INTEGRATION
  add constraint FK74B5FDD38B65205 foreign key (UPDATED_BY)
  references EMPLOYEE (EMPID);
alter table TB_PORTAL_SMS_INTEGRATION
  add constraint FK74B5FDD418DADB9 foreign key (USER_ID)
  references EMPLOYEE (EMPID);
alter table TB_PORTAL_SMS_INTEGRATION
  add constraint FK_SMS_ORGID foreign key (ORGID)
  references TB_ORGANISATION (ORGID);

prompt
prompt Creating table TB_PORTAL_SMS_INTEGRATION_HIST
prompt =============================================
prompt
create table TB_PORTAL_SMS_INTEGRATION_HIST
(
  h_seid          NUMBER(12) not null,
  se_id           NUMBER(12) not null,
  dp_deptid       NUMBER(12) not null,
  smfid           NUMBER(12) not null,
  alert_type      VARCHAR2(3),
  istransactional VARCHAR2(3),
  isdeleted       VARCHAR2(1) not null,
  orgid           NUMBER(4) not null,
  user_id         NUMBER(7) not null,
  lang_id         NUMBER(7) not null,
  created_date    DATE not null,
  updated_by      NUMBER(7),
  updated_date    DATE,
  lg_ip_mac       VARCHAR2(100),
  lg_ip_mac_upd   VARCHAR2(100),
  h_status        NVARCHAR2(1)
)
;
alter table TB_PORTAL_SMS_INTEGRATION_HIST
  add constraint PK_HSEID primary key (H_SEID);

prompt
prompt Creating table TB_PORTAL_SMS_MAIL_TEMPLATE
prompt ==========================================
prompt
create table TB_PORTAL_SMS_MAIL_TEMPLATE
(
  tp_id         NUMBER(12) not null,
  mail_sub      NVARCHAR2(2000),
  mail_header   NVARCHAR2(2000),
  mail_footer   NVARCHAR2(2000),
  mail_body     NVARCHAR2(2000),
  se_id         NUMBER(12) not null,
  sms_body      NVARCHAR2(2000),
  message_type  VARCHAR2(5),
  mail_sub_reg  NVARCHAR2(2000),
  mail_body_reg NVARCHAR2(2000),
  sms_body_reg  NVARCHAR2(2000),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7),
  lang_id       NUMBER(7),
  updated_by    NUMBER(7),
  updated_date  DATE,
  lmoddate      DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100)
)
;

prompt
prompt Creating table TB_PORTAL_SMS_MAIL_TEMP_HIST
prompt ===========================================
prompt
create table TB_PORTAL_SMS_MAIL_TEMP_HIST
(
  h_tpid        NUMBER(12) not null,
  tp_id         NUMBER(12) not null,
  mail_sub      NVARCHAR2(2000),
  mail_header   NVARCHAR2(2000),
  mail_footer   NVARCHAR2(2000),
  mail_body     NVARCHAR2(2000),
  se_id         NUMBER(12) not null,
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7),
  lang_id       NUMBER(7),
  updated_by    NUMBER(7),
  updated_date  DATE,
  lmoddate      DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  sms_body      NVARCHAR2(2000),
  message_type  VARCHAR2(5),
  mail_sub_reg  NVARCHAR2(2000),
  mail_body_reg NVARCHAR2(2000),
  sms_body_reg  NVARCHAR2(2000),
  h_status      NVARCHAR2(1)
)
;
alter table TB_PORTAL_SMS_MAIL_TEMP_HIST
  add constraint PK_HTPID primary key (H_TPID);

prompt
prompt Creating table TB_SEQ_GENERATION
prompt ================================
prompt
create table TB_SEQ_GENERATION
(
  sq_id         NUMBER(12) not null,
  sq_mdl_name   NVARCHAR2(20),
  sq_tbl_name   NVARCHAR2(50),
  sq_fld_name   NVARCHAR2(50),
  sq_orgid      NUMBER(4),
  sq_seq_name   NVARCHAR2(100),
  sq_str_with   NUMBER(12),
  sq_max_num    NUMBER(12),
  sq_str_date   DATE,
  sq_nxt_rst_dt DATE,
  sq_lst_rst_dt DATE,
  sq_rst_typ    NVARCHAR2(1),
  sq_ctr_id     NVARCHAR2(30)
)
;
comment on table TB_SEQ_GENERATION
  is 'Table for Automatic Sequence Creation, Generation and Reset';
comment on column TB_SEQ_GENERATION.sq_id
  is 'Sequnce Id(PK)';
comment on column TB_SEQ_GENERATION.sq_mdl_name
  is 'Module Name';
comment on column TB_SEQ_GENERATION.sq_tbl_name
  is 'Table Name';
comment on column TB_SEQ_GENERATION.sq_fld_name
  is 'Field Name';
comment on column TB_SEQ_GENERATION.sq_orgid
  is 'Organization Id';
comment on column TB_SEQ_GENERATION.sq_seq_name
  is 'Sequence Name';
comment on column TB_SEQ_GENERATION.sq_str_with
  is 'Start With';
comment on column TB_SEQ_GENERATION.sq_max_num
  is 'Maximum Number';
comment on column TB_SEQ_GENERATION.sq_str_date
  is 'Sequnce firstly start date';
comment on column TB_SEQ_GENERATION.sq_nxt_rst_dt
  is 'Sequence Next Reset Date';
comment on column TB_SEQ_GENERATION.sq_lst_rst_dt
  is 'Sequnce Last Reset Date';
comment on column TB_SEQ_GENERATION.sq_rst_typ
  is 'Reset Type(F/Y/M/D/C)';
alter table TB_SEQ_GENERATION
  add constraint PK_SQ_GEN_SQID primary key (SQ_ID);

prompt
prompt Creating table TB_SYSMODFUNCTION_HIST
prompt =====================================
prompt
create table TB_SYSMODFUNCTION_HIST
(
  h_smfid        NUMBER(12) not null,
  smfid          NUMBER(12) not null,
  smfname        NVARCHAR2(1000) not null,
  smfdescription NVARCHAR2(1000),
  smfflag        NVARCHAR2(1),
  smfaction      NVARCHAR2(200),
  smfcategory    NVARCHAR2(1),
  user_id        NUMBER(7) not null,
  ondate         DATE not null,
  ontime         VARCHAR2(12) not null,
  action         VARCHAR2(1),
  isdeleted      VARCHAR2(1),
  smfsystemid    NUMBER(12),
  smfcode        NVARCHAR2(255),
  updated_by     NUMBER(7),
  updated_date   DATE,
  lang_id        NUMBER(7),
  smfname_mar    NVARCHAR2(1000),
  smfsrno        NUMBER(3),
  lg_ip_mac      VARCHAR2(100),
  lg_ip_mac_upd  VARCHAR2(100),
  sm_parent_id   NUMBER(12),
  depth          NUMBER(1),
  sm_param1      NVARCHAR2(150),
  sm_param2      NVARCHAR2(150),
  h_status       NVARCHAR2(1)
)
;
alter table TB_SYSMODFUNCTION_HIST
  add constraint PK_H_SMFID primary key (H_SMFID);


spool off

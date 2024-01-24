------------------------------------------------------------
-- Export file for user SERVICE                           --
-- Created by nilima.kshirsagar on 17-03-2017, 5:42:06 PM --
------------------------------------------------------------

set define off
spool SOURCE.log

prompt
prompt Creating table AUDIT_ORGANISATION
prompt =================================
prompt
create table AUDIT_ORGANISATION
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
comment on column AUDIT_ORGANISATION.tds_accountno
  is 'TDS Account Number of Organsation. Used in TDS Certificates';
comment on column AUDIT_ORGANISATION.tds_circle
  is 'TDS Circle of Organsation where Annual return is to be delivered. Used in TDS Certificates';
comment on column AUDIT_ORGANISATION.tds_pan_gir_no
  is 'Organisation PAN/GIR No';
comment on column AUDIT_ORGANISATION.org_tax_ded_name
  is 'Name of person against deduction tax';
comment on column AUDIT_ORGANISATION.org_tax_ded_addr
  is 'Address of person against deduction tax';
comment on column AUDIT_ORGANISATION.org_short_nm
  is 'Short name which may use as abbrivation';
comment on column AUDIT_ORGANISATION.org_cpd_id_div
  is 'Division';
comment on column AUDIT_ORGANISATION.org_cpd_id_ost
  is 'Organisation Sub-Type';
comment on column AUDIT_ORGANISATION.org_cpd_id_dis
  is 'cpdId';
comment on column AUDIT_ORGANISATION.org_email_id
  is 'emailId';
comment on column AUDIT_ORGANISATION.vat_circle
  is 'circle ';
alter table AUDIT_ORGANISATION
  add constraint PK_AUDIT_ORGANISATION primary key (ORGID);

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
  is 'History id Key (Designation Id)';
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
  status         VARCHAR2(1)
)
;

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
  vat_ded_name      NVARCHAR2(500),
  ac_go_live_date   DATE,
  ulb_org_id        NUMBER,
  org_cpd_id_state  NUMBER(12)
)
;
comment on column TB_ORGANISATION.orgid
  is 'Organisation Id';
comment on column TB_ORGANISATION.o_nls_orgname
  is 'Organisation Name Eng';
comment on column TB_ORGANISATION.user_id
  is 'Created User';
comment on column TB_ORGANISATION.lang_id
  is 'Language Id';
comment on column TB_ORGANISATION.lmoddate
  is 'Created Date';
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
comment on column TB_ORGANISATION.o_logo
  is 'Organisation Logo';
comment on column TB_ORGANISATION.org_address
  is 'Organisation Address Eng.';
comment on column TB_ORGANISATION.o_nls_orgname_mar
  is 'Organisation Name Regional';
comment on column TB_ORGANISATION.org_address_mar
  is 'Organisation Address Reg.';
comment on column TB_ORGANISATION.app_start_date
  is 'Application Start Date';
comment on column TB_ORGANISATION.esdt_date
  is 'Organisation Establishment Date';
comment on column TB_ORGANISATION.org_status
  is 'Organisation Status';
comment on column TB_ORGANISATION.org_cpd_id
  is 'Organisation Type';
comment on column TB_ORGANISATION.default_status
  is 'Default Status of Organisation';
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
comment on column TB_ORGANISATION.ac_go_live_date
  is 'This column used for account go live , ';
comment on column TB_ORGANISATION.ulb_org_id
  is 'ULB ORGANISATION ID';
comment on column TB_ORGANISATION.org_cpd_id_state
  is 'Organisation State';
alter table TB_ORGANISATION
  add constraint PK_ORGANISATION primary key (ORGID);

prompt
prompt Creating table TB_LOCATION_MAS
prompt ==============================
prompt
create table TB_LOCATION_MAS
(
  loc_id            NUMBER(12) not null,
  loc_name_eng      NVARCHAR2(500) not null,
  loc_name_reg      NVARCHAR2(500) not null,
  loc_active        VARCHAR2(1) not null,
  loc_dwz_id        NUMBER(12),
  loc_parentid      NUMBER(12),
  loc_source        VARCHAR2(1) not null,
  loc_aut_status    VARCHAR2(1),
  loc_aut_by        NUMBER(12),
  loc_aut_date      DATE,
  orgid             NUMBER(4),
  user_id           NUMBER(12),
  lang_id           NUMBER(7),
  lmoddate          DATE,
  updated_by        NUMBER(12),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100),
  centraleno        NVARCHAR2(50),
  aut_v1            NVARCHAR2(100),
  aut_v2            NVARCHAR2(100),
  aut_v3            NVARCHAR2(100),
  aut_v4            NVARCHAR2(100),
  aut_v5            NVARCHAR2(100),
  aut_n1            NUMBER(15),
  aut_n2            NUMBER(15),
  aut_n3            NUMBER(15),
  aut_n4            NUMBER(15),
  aut_n5            NUMBER(15),
  aut_d1            DATE,
  aut_d2            DATE,
  aut_d3            DATE,
  aut_d4            DATE,
  aut_d5            DATE,
  gis_no            NUMBER(30),
  loc_area          NVARCHAR2(200),
  loc_area_reg      NVARCHAR2(200),
  loc_address       NVARCHAR2(1000),
  loc_address_reg   NVARCHAR2(1000),
  pincode           NUMBER(12),
  loc_type          CHAR(1) not null,
  loc_landmark      NVARCHAR2(200),
  process_sessionid NUMBER(10),
  process_name      VARCHAR2(500)
)
;
comment on column TB_LOCATION_MAS.loc_id
  is 'primary key (location id)';
comment on column TB_LOCATION_MAS.loc_name_eng
  is 'location name in english';
comment on column TB_LOCATION_MAS.loc_name_reg
  is 'location name in Hindi';
comment on column TB_LOCATION_MAS.loc_active
  is 'flag to identify whether the record is deleted or not. ''Y'' for deleted (inactive) and ''N'' for not deleted (active) record.';
comment on column TB_LOCATION_MAS.loc_dwz_id
  is 'stores the value of ward id';
comment on column TB_LOCATION_MAS.loc_parentid
  is 'maintains parent child relation for  a particular record(depid)';
comment on column TB_LOCATION_MAS.loc_source
  is 'location Source (O->online,U Uploaded)';
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
comment on column TB_LOCATION_MAS.gis_no
  is 'GIS NO';
comment on column TB_LOCATION_MAS.loc_area
  is 'Area';
comment on column TB_LOCATION_MAS.loc_area_reg
  is 'Area (Regional)';
comment on column TB_LOCATION_MAS.loc_address
  is 'Address';
comment on column TB_LOCATION_MAS.loc_address_reg
  is 'Address(Regional)';
comment on column TB_LOCATION_MAS.loc_type
  is 'location Type (Y->ULB location,N->Other location)';
comment on column TB_LOCATION_MAS.loc_landmark
  is 'LandMark';
alter table TB_LOCATION_MAS
  add constraint PK_LOC_ID primary key (LOC_ID);
alter table TB_LOCATION_MAS
  add constraint FK_ORGID_LOC foreign key (ORGID)
  references TB_ORGANISATION (ORGID);

prompt
prompt Creating table EMPLOYEE
prompt =======================
prompt
create table EMPLOYEE
(
  empid            NUMBER(12) not null,
  empname          NVARCHAR2(500) not null,
  emposloginname   NVARCHAR2(50),
  emploginname     NVARCHAR2(50),
  emppassword      NVARCHAR2(50),
  dsgid            NUMBER(12) not null,
  locid            NUMBER(12) not null,
  emppayrollnumber NVARCHAR2(10),
  empisecuritykey  NVARCHAR2(70),
  emppiservername  NVARCHAR2(20),
  isdeleted        VARCHAR2(1),
  synoynmx         NUMBER,
  orgid            NUMBER(4) not null,
  user_id          NUMBER(7),
  lmoddate         DATE not null,
  updated_by       NUMBER(7),
  updated_date     DATE,
  lang_id          NUMBER(7),
  empemail         NVARCHAR2(50),
  empexpiredt      DATE,
  empphoto         BLOB,
  lock_unlock      VARCHAR2(1),
  logged_in        VARCHAR2(1),
  lg_ip_mac        VARCHAR2(100),
  lg_ip_mac_upd    VARCHAR2(100),
  aut_v1           NVARCHAR2(100),
  aut_v2           NVARCHAR2(100),
  aut_v3           NVARCHAR2(100),
  aut_v4           NVARCHAR2(100),
  aut_v5           NVARCHAR2(100),
  aut_n1           NUMBER(15),
  aut_n2           NUMBER(15),
  aut_n3           NUMBER(15),
  aut_n4           NUMBER(15),
  aut_n5           NUMBER(15),
  aut_d1           DATE,
  aut_d2           DATE,
  aut_d3           DATE,
  aut_lo1          CHAR(1),
  aut_lo2          CHAR(1),
  aut_lo3          CHAR(1),
  empnew           NUMBER(1),
  dp_deptid        NUMBER(12),
  empdob           DATE,
  empmobno         VARCHAR2(30),
  empphoneno       VARCHAR2(40),
  empuwmsowner     VARCHAR2(1),
  empregistry      VARCHAR2(1) default 'N',
  emprecord        VARCHAR2(1) default 'N',
  empnetwork       VARCHAR2(1) default 'N',
  empoutward       VARCHAR2(1) default 'N',
  aut_by           NUMBER(12),
  aut_date         DATE,
  centraleno       NVARCHAR2(50),
  scansignature    VARCHAR2(2000),
  aut_d4           DATE,
  aut_d5           DATE,
  empuid           NVARCHAR2(14),
  empuiddocpath    NVARCHAR2(2000),
  empphotopath     NVARCHAR2(2000),
  empuiddocname    NVARCHAR2(100),
  add_flag         VARCHAR2(1) default 'Y',
  emp_address      VARCHAR2(100),
  emp_address1     NVARCHAR2(2000),
  emppincode       NUMBER(6),
  auth_status      NVARCHAR2(1),
  aut_mob          CHAR(1) default 'N',
  cpd_ttl_id       NUMBER(15),
  emplname         NVARCHAR2(100),
  empmname         NVARCHAR2(100),
  empl_type        NUMBER(12),
  emp_gender       VARCHAR2(1),
  isuploaded       VARCHAR2(1) default 'N',
  emp_cor_add1     NVARCHAR2(2000),
  emp_cor_add2     NVARCHAR2(2000),
  emp_cor_pincode  NUMBER(6),
  aut_email        CHAR(1) default 'N',
  employee_no      VARCHAR2(15),
  agency_location  NVARCHAR2(500),
  gm_id            NUMBER,
  pan_no           VARCHAR2(10)
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
alter table EMPLOYEE
  add primary key (EMPID);
alter table EMPLOYEE
  add constraint FK_DEPTID_EMPLOYEE foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);
alter table EMPLOYEE
  add constraint FK_DSGID_EMPLOYEE foreign key (DSGID)
  references DESIGNATION (DSGID);
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
  h_empid          NUMBER(12) not null,
  empid            NUMBER(12) not null,
  empname          NVARCHAR2(500) not null,
  emposloginname   NVARCHAR2(50),
  emploginname     NVARCHAR2(50),
  emppassword      NVARCHAR2(50),
  dsgid            NUMBER(12) not null,
  locid            NUMBER(12) not null,
  emppayrollnumber NVARCHAR2(10),
  empisecuritykey  NVARCHAR2(70),
  emppiservername  NVARCHAR2(20),
  isdeleted        VARCHAR2(1),
  synoynmx         NUMBER,
  orgid            NUMBER(4) not null,
  user_id          NUMBER(7) not null,
  lmoddate         DATE not null,
  updated_by       NUMBER(7),
  updated_date     DATE,
  lang_id          NUMBER(7),
  empemail         NVARCHAR2(50),
  empexpiredt      DATE,
  empphoto         BLOB,
  lock_unlock      VARCHAR2(1),
  logged_in        VARCHAR2(1),
  lg_ip_mac        VARCHAR2(100),
  lg_ip_mac_upd    VARCHAR2(100),
  aut_v1           NVARCHAR2(100),
  aut_v2           NVARCHAR2(100),
  aut_v3           NVARCHAR2(100),
  aut_v4           NVARCHAR2(100),
  aut_v5           NVARCHAR2(100),
  aut_n1           NUMBER(15),
  aut_n2           NUMBER(15),
  aut_n3           NUMBER(15),
  aut_n4           NUMBER(15),
  aut_n5           NUMBER(15),
  aut_d1           DATE,
  aut_d2           DATE,
  aut_d3           DATE,
  aut_lo1          CHAR(1),
  aut_lo2          CHAR(1),
  aut_lo3          CHAR(1),
  empnew           NUMBER(1),
  dp_deptid        NUMBER(12),
  empdob           DATE,
  empmobno         VARCHAR2(30),
  empphoneno       VARCHAR2(40),
  empuwmsowner     VARCHAR2(1),
  empregistry      VARCHAR2(1) default 'N',
  emprecord        VARCHAR2(1) default 'N',
  empnetwork       VARCHAR2(1) default 'N',
  empoutward       VARCHAR2(1) default 'N',
  aut_by           NUMBER(12),
  aut_date         DATE,
  centraleno       NVARCHAR2(50),
  scansignature    VARCHAR2(2000),
  aut_d4           DATE,
  aut_d5           DATE,
  empuid           NVARCHAR2(14),
  empuiddocpath    NVARCHAR2(2000),
  empphotopath     NVARCHAR2(2000),
  empuiddocname    NVARCHAR2(100),
  add_flag         VARCHAR2(1) default 'Y',
  emp_address      VARCHAR2(100),
  emp_address1     NVARCHAR2(2000),
  emppincode       NUMBER(6),
  auth_status      NVARCHAR2(1),
  aut_mob          CHAR(1) default 'N',
  cpd_ttl_id       NUMBER(15),
  emplname         NVARCHAR2(100),
  empmname         NVARCHAR2(100),
  empl_type        NUMBER(12),
  emp_gender       VARCHAR2(1),
  isuploaded       VARCHAR2(1) default 'N',
  emp_cor_add1     NVARCHAR2(2000),
  emp_cor_add2     NVARCHAR2(2000),
  emp_cor_pincode  NUMBER(6),
  aut_email        CHAR(1) default 'N',
  employee_no      VARCHAR2(15),
  agency_location  NVARCHAR2(500),
  gm_id            NUMBER,
  pan_no           VARCHAR2(10),
  h_status         VARCHAR2(1)
)
;
alter table EMPLOYEE_HIST
  add constraint PK_HEMPID primary key (H_EMPID);

prompt
prompt Creating table TB_GROUP_MAST
prompt ============================
prompt
create table TB_GROUP_MAST
(
  gm_id         NUMBER not null,
  gr_code       NVARCHAR2(50),
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
prompt Creating table TB_SYSMODFUNCTION
prompt ================================
prompt
create table TB_SYSMODFUNCTION
(
  smfid          NUMBER(12) not null,
  smfname        NVARCHAR2(1000) not null,
  smfdescription NVARCHAR2(1000),
  smfflag        NVARCHAR2(2),
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
  dp_deptid    NUMBER,
  bu_add       VARCHAR2(1),
  bu_edit      VARCHAR2(1),
  bu_delete    VARCHAR2(1)
)
;
alter table ROLE_ENTITLEMENT
  add constraint PK_ROLE_ET_ID primary key (ROLE_ET_ID);
alter table ROLE_ENTITLEMENT
  add constraint FK_DPDEPT_ID foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);
alter table ROLE_ENTITLEMENT
  add constraint FK_GM_ORGID foreign key (ORG_ID)
  references TB_ORGANISATION (ORGID);
alter table ROLE_ENTITLEMENT
  add constraint FK_ROL_GM_ID foreign key (ROLE_ID)
  references TB_GROUP_MAST (GM_ID);
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
prompt Creating table TB_APPREJ_MAS
prompt ============================
prompt
create table TB_APPREJ_MAS
(
  art_id          NUMBER(12) not null,
  art_type        NUMBER(12),
  art_service_id  NUMBER(12),
  art_remarks     NVARCHAR2(2000),
  orgid           NUMBER(4) not null,
  user_id         NUMBER(4),
  lang_id         NUMBER(4),
  lmoddate        DATE,
  updated_by      NUMBER(4),
  updated_date    DATE,
  lg_ip_mac       VARCHAR2(100),
  lg_ip_mac_upd   VARCHAR2(100),
  art_v1          NVARCHAR2(100),
  art_v2          NVARCHAR2(100),
  art_v3          NVARCHAR2(100),
  art_v4          NVARCHAR2(100),
  art_v5          NVARCHAR2(100),
  art_n1          NUMBER(15),
  art_n2          NUMBER(15),
  art_n3          NUMBER(15),
  art_n4          NUMBER(15),
  art_n5          NUMBER(15),
  art_d1          DATE,
  art_d2          DATE,
  art_d3          DATE,
  art_lo1         CHAR(1),
  art_lo2         CHAR(1),
  art_lo3         CHAR(1),
  art_status      NVARCHAR2(1),
  art_remarks_reg NVARCHAR2(1000)
)
;
comment on column TB_APPREJ_MAS.art_id
  is 'ART ID Primary Key ';
comment on column TB_APPREJ_MAS.art_type
  is ' Art type id from prefix REM FOR REMARK TYPE';
comment on column TB_APPREJ_MAS.art_service_id
  is 'Service id';
comment on column TB_APPREJ_MAS.art_remarks
  is 'Remarks';
comment on column TB_APPREJ_MAS.orgid
  is 'Organization id';
comment on column TB_APPREJ_MAS.user_id
  is 'User id';
comment on column TB_APPREJ_MAS.lang_id
  is 'Lang id';
comment on column TB_APPREJ_MAS.lmoddate
  is 'Creation date';
comment on column TB_APPREJ_MAS.updated_by
  is 'Updated by';
comment on column TB_APPREJ_MAS.updated_date
  is 'Updated on date';
comment on column TB_APPREJ_MAS.lg_ip_mac
  is 'Client Machine�s Login Name | IP Address | Physical Address';
comment on column TB_APPREJ_MAS.lg_ip_mac_upd
  is 'Updated Client Machine�s Login Name | IP Address | Physical Address';
comment on column TB_APPREJ_MAS.art_v1
  is 'Additional nvarchar2 to be used in future';
comment on column TB_APPREJ_MAS.art_v2
  is 'Additional nvarchar2 to be used in future';
comment on column TB_APPREJ_MAS.art_v3
  is 'Additional nvarchar2 to be used in future';
comment on column TB_APPREJ_MAS.art_v4
  is 'Additional nvarchar2 to be used in future';
comment on column TB_APPREJ_MAS.art_v5
  is 'Additional nvarchar2 to be used in future';
comment on column TB_APPREJ_MAS.art_n1
  is 'Additional number  to be used in future';
comment on column TB_APPREJ_MAS.art_n2
  is 'Additional number  to be used in future';
comment on column TB_APPREJ_MAS.art_n3
  is 'Additional number  to be used in future';
comment on column TB_APPREJ_MAS.art_n4
  is 'Additional number  to be used in future';
comment on column TB_APPREJ_MAS.art_n5
  is 'Additional number  to be used in future';
comment on column TB_APPREJ_MAS.art_d1
  is 'Additional Date  to be used in future ';
comment on column TB_APPREJ_MAS.art_d2
  is 'Additional Date  to be used in future ';
comment on column TB_APPREJ_MAS.art_d3
  is 'Additional Date  to be used in future ';
comment on column TB_APPREJ_MAS.art_lo1
  is 'Additional Logical field  to be used in future ';
comment on column TB_APPREJ_MAS.art_lo2
  is 'Additional Logical field  to be used in future ';
comment on column TB_APPREJ_MAS.art_lo3
  is 'Additional Logical field  to be used in future ';
alter table TB_APPREJ_MAS
  add constraint PK_APPREJ_MAS_ART_ID primary key (ART_ID, ORGID);

prompt
prompt Creating table TB_APPREJ_MAS_HIST
prompt =================================
prompt
create table TB_APPREJ_MAS_HIST
(
  h_artid        NUMBER(12) not null,
  art_id         NUMBER(12) not null,
  art_type       NUMBER(12),
  art_service_id NUMBER(12),
  art_remarks    NVARCHAR2(2000),
  orgid          NUMBER(4) not null,
  user_id        NUMBER(4),
  lang_id        NUMBER(4),
  lmoddate       DATE,
  updated_by     NUMBER(4),
  updated_date   DATE,
  lg_ip_mac      VARCHAR2(100),
  lg_ip_mac_upd  VARCHAR2(100),
  art_v1         NVARCHAR2(100),
  art_v2         NVARCHAR2(100),
  art_v3         NVARCHAR2(100),
  art_v4         NVARCHAR2(100),
  art_v5         NVARCHAR2(100),
  art_n1         NUMBER(15),
  art_n2         NUMBER(15),
  art_n3         NUMBER(15),
  art_n4         NUMBER(15),
  art_n5         NUMBER(15),
  art_d1         DATE,
  art_d2         DATE,
  art_d3         DATE,
  art_lo1        CHAR(1),
  art_lo2        CHAR(1),
  art_lo3        CHAR(1),
  art_status     NVARCHAR2(1),
  h_status       NVARCHAR2(1)
)
;
alter table TB_APPREJ_MAS_HIST
  add constraint PK_H_ARTID primary key (H_ARTID);

prompt
prompt Creating table TB_SERVICES_MST
prompt ==============================
prompt
create table TB_SERVICES_MST
(
  sm_service_id           NUMBER(12) not null,
  sm_service_name         NVARCHAR2(100),
  sm_serv_own_desig       NUMBER(12),
  sm_serv_active          NUMBER(12),
  sm_serv_type            NUMBER(12),
  sm_serv_counter         NUMBER(12),
  sm_serv_duration        NUMBER(3),
  sm_appl_form            NUMBER(12),
  sm_chklst_verify        NUMBER(12),
  sm_security_deposit     NUMBER(12),
  sm_fees_schedule        NUMBER(3),
  sm_acknowledge          NUMBER(12),
  sm_scrutiny_level       NUMBER(3),
  sm_autho_level          NUMBER(12),
  sm_print_respons        NUMBER(12),
  sm_type_of_sign         NUMBER(12),
  sm_dispatch_post        NUMBER(12),
  sm_specific_info_form   NVARCHAR2(100),
  orgid                   NUMBER(12),
  user_id                 NUMBER(7),
  lang_id                 NUMBER(7),
  lmoddate                DATE,
  sm_service_name_mar     NVARCHAR2(200),
  cdm_dept_id             NUMBER(12),
  sm_approval_form        NVARCHAR2(100),
  sm_rejection_form       NVARCHAR2(100),
  sm_web_enabled          NVARCHAR2(1) default 'N',
  sm_url                  NVARCHAR2(100),
  sm_srno                 NVARCHAR2(12),
  sm_switch               NVARCHAR2(1) default 'N',
  sm_addr                 NVARCHAR2(1) default 'N',
  sm_sign_path            NVARCHAR2(200),
  updated_by              NUMBER(7),
  updated_date            DATE,
  sm_rcpt                 CHAR(1) default 'Y',
  sm_initiating_empid     NVARCHAR2(12),
  sm_cpd_id               NUMBER(12),
  sm_loi_duration         NUMBER(3),
  sm_shortdesc            VARCHAR2(5) not null,
  sm_actual               CHAR(1) default 'N',
  sm_digi_sign_appl       CHAR(1) default 'N',
  com_v3                  NVARCHAR2(100),
  com_v4                  NVARCHAR2(100),
  com_v5                  NVARCHAR2(100),
  sm_challan_duration     NUMBER(15),
  com_n2                  NUMBER(15),
  com_n3                  NUMBER(15),
  com_n4                  NUMBER(15),
  com_n5                  NUMBER(15),
  com_d1                  DATE,
  com_d2                  DATE,
  com_d3                  DATE,
  sm_csc_flag             CHAR(1) default 'N',
  sm_sms                  CHAR(1) default 'N',
  sm_email                CHAR(1) default 'N',
  srid_hours              NUMBER(2),
  srid_min                NUMBER(2),
  sm_checklist_hours      NUMBER(2),
  sm_checklist_min        NUMBER(2),
  sm_checklist_days       NUMBER(3),
  sm_serv_duration_type   NUMBER(12),
  sm_service_note         NVARCHAR2(400),
  sm_service_note_mar     NVARCHAR2(600),
  ip_mac                  VARCHAR2(100),
  ip_mac_upd              VARCHAR2(100),
  sm_appli_charge_flag    NVARCHAR2(1),
  sm_scrutiny_charge_flag NVARCHAR2(1),
  sm_scru_applicable_flag NVARCHAR2(1) default 'N'
)
;
comment on column TB_SERVICES_MST.sm_service_id
  is 'Service id';
comment on column TB_SERVICES_MST.sm_service_name
  is 'Service Name';
comment on column TB_SERVICES_MST.sm_serv_own_desig
  is 'Service owner designation';
comment on column TB_SERVICES_MST.sm_serv_active
  is 'Service Activeness';
comment on column TB_SERVICES_MST.sm_serv_type
  is 'Service Type';
comment on column TB_SERVICES_MST.sm_serv_counter
  is 'Service Counter';
comment on column TB_SERVICES_MST.sm_serv_duration
  is 'Duration of service';
comment on column TB_SERVICES_MST.sm_appl_form
  is 'Application Form';
comment on column TB_SERVICES_MST.sm_chklst_verify
  is 'Checklist Verification';
comment on column TB_SERVICES_MST.sm_security_deposit
  is 'Security Deposit';
comment on column TB_SERVICES_MST.sm_fees_schedule
  is 'field to identify whether service is chargeable or not EX: 0-Free, 1-chargable';
comment on column TB_SERVICES_MST.sm_acknowledge
  is 'Acknowledgement Format';
comment on column TB_SERVICES_MST.sm_scrutiny_level
  is 'Scrutiny Level';
comment on column TB_SERVICES_MST.sm_autho_level
  is 'Authorisation Level';
comment on column TB_SERVICES_MST.sm_print_respons
  is 'Printing Responsibility';
comment on column TB_SERVICES_MST.sm_type_of_sign
  is 'Signature on printed returned document';
comment on column TB_SERVICES_MST.sm_dispatch_post
  is 'Dispatch by Post';
comment on column TB_SERVICES_MST.sm_specific_info_form
  is 'Specific Information form name';
comment on column TB_SERVICES_MST.orgid
  is 'Organization Id';
comment on column TB_SERVICES_MST.user_id
  is 'User Id';
comment on column TB_SERVICES_MST.lang_id
  is 'Language Id';
comment on column TB_SERVICES_MST.lmoddate
  is 'Last Modification Date';
comment on column TB_SERVICES_MST.sm_service_name_mar
  is 'Service Name in Marathi';
comment on column TB_SERVICES_MST.cdm_dept_id
  is 'Department Identification';
comment on column TB_SERVICES_MST.sm_approval_form
  is 'Name of the approval form';
comment on column TB_SERVICES_MST.sm_rejection_form
  is 'Name of the rejection form';
comment on column TB_SERVICES_MST.sm_web_enabled
  is 'Web enabling flag';
comment on column TB_SERVICES_MST.sm_url
  is 'Url';
comment on column TB_SERVICES_MST.sm_srno
  is 'Serial Number';
comment on column TB_SERVICES_MST.sm_switch
  is 'Skipping print grid step.';
comment on column TB_SERVICES_MST.sm_addr
  is 'Address details';
comment on column TB_SERVICES_MST.sm_sign_path
  is 'Scanned signature path';
comment on column TB_SERVICES_MST.updated_by
  is 'Modified By';
comment on column TB_SERVICES_MST.updated_date
  is 'Modification Date';
comment on column TB_SERVICES_MST.sm_rcpt
  is 'Receipt';
comment on column TB_SERVICES_MST.sm_initiating_empid
  is 'Initial Employee Id';
comment on column TB_SERVICES_MST.sm_cpd_id
  is 'Certificate Details';
comment on column TB_SERVICES_MST.sm_loi_duration
  is 'LOI Notice Duration of service';
comment on column TB_SERVICES_MST.sm_shortdesc
  is 'Short Description';
comment on column TB_SERVICES_MST.sm_actual
  is 'Actual Service( ''Y'')';
comment on column TB_SERVICES_MST.sm_digi_sign_appl
  is 'Digital Signature Applicable (Y'')';
comment on column TB_SERVICES_MST.sm_challan_duration
  is 'ChallanValidity Period';
comment on column TB_SERVICES_MST.sm_csc_flag
  is 'Flag to identify whether service is outsource. set to ''Y'' when applicable else default ''N''';
comment on column TB_SERVICES_MST.sm_sms
  is 'flag to identify whether SMS applicable on service. set to ''Y'' when applicable else default ''N'' ';
comment on column TB_SERVICES_MST.sm_email
  is 'flag to identify whether EMAIL applicable on service. set to ''Y'' when applicable else default ''N'' ';
comment on column TB_SERVICES_MST.srid_hours
  is 'services Hours';
comment on column TB_SERVICES_MST.srid_min
  is 'service minitus';
comment on column TB_SERVICES_MST.sm_checklist_hours
  is ' Checklist hours';
comment on column TB_SERVICES_MST.sm_checklist_min
  is ' Checklist minutes';
comment on column TB_SERVICES_MST.sm_checklist_days
  is ' Checklist dayes ';
comment on column TB_SERVICES_MST.sm_serv_duration_type
  is 'Service duration type comes from prefix SPT';
comment on column TB_SERVICES_MST.sm_service_note
  is 'Note remark in English for service ';
comment on column TB_SERVICES_MST.sm_service_note_mar
  is 'Note remark in Marathi for service ';
comment on column TB_SERVICES_MST.ip_mac
  is 'Client Machine�s Login Name | IP Address | Physical Address';
comment on column TB_SERVICES_MST.ip_mac_upd
  is 'Updated Client Machine�s Login Name | IP Address | Physical Address';
comment on column TB_SERVICES_MST.sm_appli_charge_flag
  is 'Application Charge Flag';
comment on column TB_SERVICES_MST.sm_scru_applicable_flag
  is 'Flag to indentify Scrutiny Apllicable or not';
alter table TB_SERVICES_MST
  add constraint PK_SERVICE_MST primary key (SM_SERVICE_ID);
alter table TB_SERVICES_MST
  add constraint UK_SM_SHORT_CODE unique (SM_SHORTDESC, ORGID);
alter table TB_SERVICES_MST
  add constraint FK_DEPARTMENT_SERVICES_MST foreign key (CDM_DEPT_ID)
  references TB_DEPARTMENT (DP_DEPTID);

prompt
prompt Creating table TB_CFC_APPLICATION_MST
prompt =====================================
prompt
create table TB_CFC_APPLICATION_MST
(
  apm_application_id    NUMBER(16) not null,
  apm_application_date  DATE not null,
  sm_service_id         NUMBER(12) not null,
  ccd_apm_type          NUMBER(12),
  apm_orgn_name         NVARCHAR2(200),
  apm_post_in_orgn      NVARCHAR2(100),
  apm_title             NUMBER(12) not null,
  apm_lname             NVARCHAR2(100),
  apm_fname             NVARCHAR2(100) not null,
  apm_mname             NVARCHAR2(100),
  apm_sex               NVARCHAR2(1),
  apm_age               NUMBER(3),
  apm_dob               DATE,
  orgid                 NUMBER(4) not null,
  user_id               NUMBER(7) not null,
  lang_id               NUMBER(7) not null,
  lmoddate              DATE not null,
  ctz_citizenid         NVARCHAR2(16),
  ref_no                NUMBER(12),
  updated_by            NUMBER(7),
  updated_date          DATE,
  lg_ip_mac             VARCHAR2(100),
  lg_ip_mac_upd         VARCHAR2(100),
  apm_resid             NVARCHAR2(16),
  apm_cfc_ward          NUMBER(12),
  apm_user_ward         NUMBER(12),
  apm_source_flag       VARCHAR2(1) default 'C',
  rejction_no           NUMBER(18),
  rejection_dt          DATE,
  apm_pay_stat_flag     NVARCHAR2(1),
  apm_wd_id             NUMBER(12),
  apm_app_pending_to    VARCHAR2(20),
  apm_last_event_id     NUMBER,
  apm_chklst_vrfy_flag  NVARCHAR2(1),
  apm_appl_success_flag NVARCHAR2(1),
  apm_appl_closed_flag  NVARCHAR2(1),
  apm_remark            VARCHAR2(100),
  apm_app_rej_flag      NVARCHAR2(1),
  apm_app_rej_by        NUMBER(18),
  apm_app_rej_date      DATE,
  apm_bpl_no            VARCHAR2(15),
  apm_uid               VARCHAR2(15),
  apm_approve_by        NUMBER(12),
  apm_approve_date      DATE
)
;
comment on table TB_CFC_APPLICATION_MST
  is 'CFC Application Master';
comment on column TB_CFC_APPLICATION_MST.apm_application_id
  is 'Application ID';
comment on column TB_CFC_APPLICATION_MST.apm_application_date
  is 'Application Date';
comment on column TB_CFC_APPLICATION_MST.sm_service_id
  is 'Service id';
comment on column TB_CFC_APPLICATION_MST.ccd_apm_type
  is 'Applicant type individual/group/organization';
comment on column TB_CFC_APPLICATION_MST.apm_orgn_name
  is 'Organisation name';
comment on column TB_CFC_APPLICATION_MST.apm_post_in_orgn
  is 'Post in organization';
comment on column TB_CFC_APPLICATION_MST.apm_title
  is 'Title of the applicant eg. Mr,Mrs';
comment on column TB_CFC_APPLICATION_MST.apm_lname
  is 'Last Name of the applicant';
comment on column TB_CFC_APPLICATION_MST.apm_fname
  is 'First Name of the applicant';
comment on column TB_CFC_APPLICATION_MST.apm_mname
  is 'Middle Name of the applicant';
comment on column TB_CFC_APPLICATION_MST.apm_sex
  is 'Gender of the applicant';
comment on column TB_CFC_APPLICATION_MST.apm_age
  is 'Age';
comment on column TB_CFC_APPLICATION_MST.apm_dob
  is 'Birth Date';
comment on column TB_CFC_APPLICATION_MST.orgid
  is 'Organization Id';
comment on column TB_CFC_APPLICATION_MST.user_id
  is 'User Id';
comment on column TB_CFC_APPLICATION_MST.lang_id
  is 'Language Id';
comment on column TB_CFC_APPLICATION_MST.lmoddate
  is 'Last Modification Date';
comment on column TB_CFC_APPLICATION_MST.ctz_citizenid
  is 'Citizen Id';
comment on column TB_CFC_APPLICATION_MST.ref_no
  is 'Reference No. ';
comment on column TB_CFC_APPLICATION_MST.updated_by
  is 'Last Updated By';
comment on column TB_CFC_APPLICATION_MST.updated_date
  is 'Last Updated Date';
comment on column TB_CFC_APPLICATION_MST.lg_ip_mac
  is 'Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_CFC_APPLICATION_MST.lg_ip_mac_upd
  is 'Updated Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_CFC_APPLICATION_MST.apm_resid
  is 'Stores User Identification Number data';
comment on column TB_CFC_APPLICATION_MST.apm_cfc_ward
  is 'CFC ward according to Counter schedule against the login user.';
comment on column TB_CFC_APPLICATION_MST.apm_user_ward
  is 'User ward information captured from user oraganisation and location mapping';
comment on column TB_CFC_APPLICATION_MST.apm_source_flag
  is 'Flag to Identify whether the Application is through CFC or RTS';
comment on column TB_CFC_APPLICATION_MST.rejction_no
  is 'rejection no.';
comment on column TB_CFC_APPLICATION_MST.rejection_dt
  is 'rejection date';
comment on column TB_CFC_APPLICATION_MST.apm_pay_stat_flag
  is 'Payment Status Flag';
comment on column TB_CFC_APPLICATION_MST.apm_app_pending_to
  is 'we_gm_id from TB_WORKFLOW_EVENT to represent the group,current task is assigned to';
comment on column TB_CFC_APPLICATION_MST.apm_last_event_id
  is 'Last Event_id from Task_Manager';
comment on column TB_CFC_APPLICATION_MST.apm_remark
  is 'Remarks ';
comment on column TB_CFC_APPLICATION_MST.apm_app_rej_flag
  is 'Accepted or Rejected flga';
comment on column TB_CFC_APPLICATION_MST.apm_app_rej_by
  is 'Accepted or Rejected By ';
comment on column TB_CFC_APPLICATION_MST.apm_app_rej_date
  is 'Accepted or Rejected Date';
comment on column TB_CFC_APPLICATION_MST.apm_bpl_no
  is 'BPL No. Below Poverty Line No.';
comment on column TB_CFC_APPLICATION_MST.apm_uid
  is 'AADHAR NO.';
comment on column TB_CFC_APPLICATION_MST.apm_approve_by
  is 'Name of  Authorised Person';
comment on column TB_CFC_APPLICATION_MST.apm_approve_date
  is 'Approve Date.';
alter table TB_CFC_APPLICATION_MST
  add constraint PK_APM_APPL_ID primary key (APM_APPLICATION_ID);
alter table TB_CFC_APPLICATION_MST
  add constraint FK_APM_SERVICE_ID foreign key (SM_SERVICE_ID)
  references TB_SERVICES_MST (SM_SERVICE_ID);

prompt
prompt Creating table TB_CFC_CHECKLIST_MST
prompt ===================================
prompt
create table TB_CFC_CHECKLIST_MST
(
  clm_id             NUMBER(12) not null,
  sm_service_id      NUMBER(12) not null,
  clm_sr_no          NUMBER(3) not null,
  clm_desc           NVARCHAR2(2000) not null,
  clm_status         NVARCHAR2(1) default 'A',
  orgid              NUMBER(4) not null,
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(7) not null,
  lmoddate           DATE not null,
  clm_desc_engl      NVARCHAR2(2000),
  ccm_checklist_flag NUMBER(12),
  updated_by         NUMBER(7),
  updated_date       DATE,
  doc_group          VARCHAR2(200),
  com_v2             NVARCHAR2(100),
  com_v3             NVARCHAR2(100),
  com_v4             NVARCHAR2(100),
  com_v5             NVARCHAR2(100),
  com_n1             NUMBER(15),
  com_n2             NUMBER(15),
  com_n3             NUMBER(15),
  com_n4             NUMBER(15),
  com_n5             NUMBER(15),
  com_d1             DATE,
  com_d2             DATE,
  com_d3             DATE,
  com_lo1            CHAR(1),
  com_lo2            CHAR(1),
  com_lo3            CHAR(1),
  applicable_from    DATE,
  applicable_to      DATE,
  checklist_flag     NVARCHAR2(1) default 'N'
)
;
comment on table TB_CFC_CHECKLIST_MST
  is 'CFC Service Checklist Details';
comment on column TB_CFC_CHECKLIST_MST.clm_id
  is 'Identification Id';
comment on column TB_CFC_CHECKLIST_MST.sm_service_id
  is 'Service id';
comment on column TB_CFC_CHECKLIST_MST.clm_sr_no
  is 'Serial No.';
comment on column TB_CFC_CHECKLIST_MST.clm_desc
  is 'Checklist Description';
comment on column TB_CFC_CHECKLIST_MST.clm_status
  is 'Checklist Status (Active/Inactive)';
comment on column TB_CFC_CHECKLIST_MST.orgid
  is 'Organisation ID';
comment on column TB_CFC_CHECKLIST_MST.user_id
  is 'User Id';
comment on column TB_CFC_CHECKLIST_MST.lang_id
  is 'Language Id';
comment on column TB_CFC_CHECKLIST_MST.lmoddate
  is 'Last Modification Date';
comment on column TB_CFC_CHECKLIST_MST.clm_desc_engl
  is 'Description in English';
comment on column TB_CFC_CHECKLIST_MST.ccm_checklist_flag
  is 'Set of values';
comment on column TB_CFC_CHECKLIST_MST.doc_group
  is 'DOCUMENT GROUP UNIQUE ID ';
comment on column TB_CFC_CHECKLIST_MST.applicable_from
  is 'Applicable from date';
comment on column TB_CFC_CHECKLIST_MST.applicable_to
  is 'Applicable to date';
alter table TB_CFC_CHECKLIST_MST
  add constraint PK_CFC_CHKLST_MST primary key (CLM_ID);
alter table TB_CFC_CHECKLIST_MST
  add constraint FK_SERVICES_MST foreign key (SM_SERVICE_ID)
  references TB_SERVICES_MST (SM_SERVICE_ID);

prompt
prompt Creating table TB_ATTACH_CFC
prompt ============================
prompt
create table TB_ATTACH_CFC
(
  att_id         NUMBER(12) not null,
  att_date       DATE not null,
  att_path       NVARCHAR2(255),
  att_fname      NVARCHAR2(500),
  att_by         NVARCHAR2(255),
  att_from_path  NVARCHAR2(255),
  dept           NUMBER(12),
  orgid          NUMBER(12) not null,
  user_id        NUMBER(12),
  lang_id        NUMBER(12),
  updated_by     NUMBER(12),
  updated_date   DATE,
  lg_ip_mac      VARCHAR2(100),
  lg_ip_mac_upd  VARCHAR2(100),
  lmodate        DATE,
  application_id NUMBER(30),
  service_id     NUMBER(12),
  clm_id         NUMBER(12),
  clm_desc       NVARCHAR2(2000),
  clm_status     NVARCHAR2(1),
  clm_sr_no      NUMBER(3),
  chk_status     NUMBER(12),
  clm_apr_status NVARCHAR2(1),
  clm_remark     NVARCHAR2(300),
  mandatory      CHAR(1),
  clm_desc_engl  NVARCHAR2(2000)
)
;
comment on column TB_ATTACH_CFC.att_id
  is 'Attachment no ';
comment on column TB_ATTACH_CFC.att_date
  is 'Attachment Date ';
comment on column TB_ATTACH_CFC.att_path
  is 'Attachment Path ';
comment on column TB_ATTACH_CFC.att_fname
  is 'Attach File Name ';
comment on column TB_ATTACH_CFC.att_by
  is 'File Attach By ';
comment on column TB_ATTACH_CFC.att_from_path
  is 'The path of folder where file gets copied ';
comment on column TB_ATTACH_CFC.dept
  is 'Attachment From Department ';
comment on column TB_ATTACH_CFC.orgid
  is 'Organisation id ';
comment on column TB_ATTACH_CFC.user_id
  is 'User id ';
comment on column TB_ATTACH_CFC.lang_id
  is 'Language id ';
comment on column TB_ATTACH_CFC.updated_by
  is 'Updated by ';
comment on column TB_ATTACH_CFC.updated_date
  is 'Updated date ';
comment on column TB_ATTACH_CFC.lg_ip_mac
  is 'Client Machine.s Login Name | IP Address | Physical Address ';
comment on column TB_ATTACH_CFC.lg_ip_mac_upd
  is 'Updated Client Machine.s Login Name | IP Address | Physical Address ';
comment on column TB_ATTACH_CFC.lmodate
  is 'Last modification date ';
comment on column TB_ATTACH_CFC.application_id
  is 'APPLICATION ID ';
comment on column TB_ATTACH_CFC.service_id
  is 'SERVICE ID';
comment on column TB_ATTACH_CFC.clm_id
  is 'TREE ID';
comment on column TB_ATTACH_CFC.clm_desc
  is 'CHECKLIST DESCRIPTION';
comment on column TB_ATTACH_CFC.clm_status
  is 'CHECKLIST STATUS';
comment on column TB_ATTACH_CFC.clm_sr_no
  is 'CHECKLIST Sr.No.';
comment on column TB_ATTACH_CFC.chk_status
  is 'Checklist Details ';
comment on column TB_ATTACH_CFC.clm_apr_status
  is 'DOCUMENT APPROVE /REJECT FLAG(APPROVE=Y / REJECT=N)';
comment on column TB_ATTACH_CFC.clm_remark
  is 'DOCUMENT REJECT RAESON';
alter table TB_ATTACH_CFC
  add constraint PK_ATT_ID1 primary key (ATT_ID);
alter table TB_ATTACH_CFC
  add constraint FK279285B738B65205 foreign key (UPDATED_BY)
  references EMPLOYEE (EMPID);
alter table TB_ATTACH_CFC
  add constraint FK279285B76078ED5 foreign key (ORGID)
  references TB_ORGANISATION (ORGID);
alter table TB_ATTACH_CFC
  add constraint FK_APPLICATION_ID foreign key (APPLICATION_ID)
  references TB_CFC_APPLICATION_MST (APM_APPLICATION_ID);
alter table TB_ATTACH_CFC
  add constraint FK_CLM_ID foreign key (CLM_ID)
  references TB_CFC_CHECKLIST_MST (CLM_ID);
alter table TB_ATTACH_CFC
  add constraint FK_DP_DEPT foreign key (DEPT)
  references TB_DEPARTMENT (DP_DEPTID);
alter table TB_ATTACH_CFC
  add constraint FK_SERVICE_ID foreign key (SERVICE_ID)
  references TB_SERVICES_MST (SM_SERVICE_ID);

prompt
prompt Creating table TB_ATTACH_CFC_HIST
prompt =================================
prompt
create table TB_ATTACH_CFC_HIST
(
  h_attid        NUMBER(12) not null,
  att_id         NUMBER(12) not null,
  att_date       DATE not null,
  att_path       NVARCHAR2(255),
  att_fname      NVARCHAR2(500),
  att_by         NVARCHAR2(255),
  att_from_path  NVARCHAR2(255),
  dept           NUMBER(12),
  orgid          NUMBER(12) not null,
  user_id        NUMBER(12),
  lang_id        NUMBER(12),
  updated_by     NUMBER(12),
  updated_date   DATE,
  lg_ip_mac      VARCHAR2(100),
  lg_ip_mac_upd  VARCHAR2(100),
  lmodate        DATE,
  application_id NUMBER(30),
  service_id     NUMBER(12),
  clm_id         NUMBER(12),
  clm_desc       NVARCHAR2(2000),
  clm_status     NVARCHAR2(1),
  clm_sr_no      NUMBER(3),
  chk_status     NUMBER(12),
  clm_apr_status NVARCHAR2(1),
  clm_remark     NVARCHAR2(300),
  mandatory      CHAR(1),
  clm_desc_engl  NVARCHAR2(2000),
  h_status       NVARCHAR2(1)
)
;
alter table TB_ATTACH_CFC_HIST
  add constraint PK_H_ATTID primary key (H_ATTID);

prompt
prompt Creating table TB_ATTACH_DOCUMENT
prompt =================================
prompt
create table TB_ATTACH_DOCUMENT
(
  atd_id        NUMBER(12) not null,
  atd_date      DATE not null,
  atd_path      NVARCHAR2(255) not null,
  atd_fname     NVARCHAR2(500) not null,
  atd_by        NVARCHAR2(255),
  atd_from_path NVARCHAR2(255),
  atd_deptid    NUMBER(12),
  atd_idf_id    NVARCHAR2(30) not null,
  orgid         NUMBER(12) not null,
  user_id       NUMBER(12) not null,
  lang_id       NUMBER(12),
  lmodate       DATE not null,
  updated_by    NUMBER(12),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  atd_status    NVARCHAR2(1) not null,
  atd_srno      NUMBER(1) not null
)
;
comment on column TB_ATTACH_DOCUMENT.atd_id
  is 'Attachment no';
comment on column TB_ATTACH_DOCUMENT.atd_date
  is 'Attachment  Date';
comment on column TB_ATTACH_DOCUMENT.atd_path
  is 'Attachment Path';
comment on column TB_ATTACH_DOCUMENT.atd_fname
  is 'Attach File Name';
comment on column TB_ATTACH_DOCUMENT.atd_by
  is 'File Attach By';
comment on column TB_ATTACH_DOCUMENT.atd_from_path
  is 'The path of folder where file gets copied';
comment on column TB_ATTACH_DOCUMENT.atd_deptid
  is 'Attachment From Department';
comment on column TB_ATTACH_DOCUMENT.atd_idf_id
  is 'Document Identification number(Estate Code,Property code,Tenant Code)';
comment on column TB_ATTACH_DOCUMENT.orgid
  is 'Organisation id ';
comment on column TB_ATTACH_DOCUMENT.user_id
  is 'User id';
comment on column TB_ATTACH_DOCUMENT.lang_id
  is 'Language id';
comment on column TB_ATTACH_DOCUMENT.updated_by
  is 'Updated by';
comment on column TB_ATTACH_DOCUMENT.updated_date
  is 'Updated date';
comment on column TB_ATTACH_DOCUMENT.lg_ip_mac
  is 'Client Machine.s Login Name | IP Address | Physical Address ';
comment on column TB_ATTACH_DOCUMENT.lg_ip_mac_upd
  is 'Updated Client Machine.s Login Name | IP Address | Physical Address ';
comment on column TB_ATTACH_DOCUMENT.atd_status
  is 'Document Status';
comment on column TB_ATTACH_DOCUMENT.atd_srno
  is 'Document Serial No.';
alter table TB_ATTACH_DOCUMENT
  add constraint PK_ATD_ID primary key (ATD_ID);
alter table TB_ATTACH_DOCUMENT
  add constraint FK_ATD_DEPTID foreign key (ATD_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);

prompt
prompt Creating table TB_AUDIT_SERVICES_MST
prompt ====================================
prompt
create table TB_AUDIT_SERVICES_MST
(
  sm_service_id         NUMBER(12) not null,
  sm_service_name       NVARCHAR2(100),
  sm_serv_own_desig     NUMBER(12),
  sm_serv_active        NUMBER(12),
  sm_serv_type          NUMBER(12),
  sm_serv_counter       NUMBER(12),
  sm_serv_duration      NUMBER(3),
  sm_appl_form          NUMBER(12),
  sm_chklst_verify      NUMBER(12),
  sm_security_deposit   NUMBER(12),
  sm_fees_schedule      NUMBER(3),
  sm_acknowledge        NUMBER(12),
  sm_scrutiny_level     NUMBER(3),
  sm_autho_level        NUMBER(12),
  sm_print_respons      NUMBER(12),
  sm_specific_info_form NVARCHAR2(100),
  user_id               NUMBER(7),
  lang_id               NUMBER(7),
  lmoddate              DATE,
  sm_service_name_mar   NVARCHAR2(200),
  cdm_dept_id           NUMBER(12),
  sm_approval_form      NVARCHAR2(100),
  sm_rejection_form     NVARCHAR2(100),
  sm_web_enabled        NVARCHAR2(1),
  sm_url                NVARCHAR2(100),
  sm_srno               NVARCHAR2(12),
  sm_switch             NVARCHAR2(1),
  sm_addr               NVARCHAR2(1),
  updated_by            NUMBER(7),
  updated_date          DATE,
  sm_rcpt               CHAR(1),
  sm_initiating_empid   NVARCHAR2(12),
  sm_cpd_id             NUMBER(12),
  sm_loi_duration       NUMBER(3),
  sm_shortdesc          VARCHAR2(5),
  orgid                 NUMBER(4),
  srid_hours            NUMBER(2),
  srid_min              NUMBER(2),
  sm_checklist_hours    NUMBER(2),
  sm_checklist_min      NUMBER(2),
  sm_checklist_days     NUMBER(3),
  sm_serv_duration_type NUMBER(12),
  ip_mac                VARCHAR2(100),
  ip_mac_upd            VARCHAR2(100),
  backend_updated       NVARCHAR2(200),
  aud_flag              VARCHAR2(1),
  mst_seq               NUMBER(38),
  user_name             VARCHAR2(200),
  sm_service_note       VARCHAR2(400),
  sm_service_note_mar   NVARCHAR2(600),
  sm_sms                CHAR(1) default 'N',
  sm_email              CHAR(1) default 'N'
)
;
comment on column TB_AUDIT_SERVICES_MST.sm_service_id
  is 'Service id';
comment on column TB_AUDIT_SERVICES_MST.sm_service_name
  is 'Service Name';
comment on column TB_AUDIT_SERVICES_MST.sm_serv_own_desig
  is 'Service owner designation';
comment on column TB_AUDIT_SERVICES_MST.sm_serv_active
  is 'Service Activeness';
comment on column TB_AUDIT_SERVICES_MST.sm_serv_type
  is 'Service Type';
comment on column TB_AUDIT_SERVICES_MST.sm_serv_counter
  is 'Service Counter';
comment on column TB_AUDIT_SERVICES_MST.sm_serv_duration
  is 'Duration of service in Days';
comment on column TB_AUDIT_SERVICES_MST.sm_appl_form
  is 'Application Form';
comment on column TB_AUDIT_SERVICES_MST.sm_chklst_verify
  is 'Checklist Verification';
comment on column TB_AUDIT_SERVICES_MST.sm_security_deposit
  is 'Security Deposit';
comment on column TB_AUDIT_SERVICES_MST.sm_fees_schedule
  is 'Payment of fees';
comment on column TB_AUDIT_SERVICES_MST.sm_acknowledge
  is 'Acknowledgement Format';
comment on column TB_AUDIT_SERVICES_MST.sm_scrutiny_level
  is 'Scrutiny Level';
comment on column TB_AUDIT_SERVICES_MST.sm_autho_level
  is 'Authorisation Level';
comment on column TB_AUDIT_SERVICES_MST.sm_print_respons
  is 'Printing Responsibility';
comment on column TB_AUDIT_SERVICES_MST.sm_specific_info_form
  is 'Specific Information form name';
comment on column TB_AUDIT_SERVICES_MST.user_id
  is 'User Id';
comment on column TB_AUDIT_SERVICES_MST.lang_id
  is 'Language Id';
comment on column TB_AUDIT_SERVICES_MST.lmoddate
  is 'Last Modification Date';
comment on column TB_AUDIT_SERVICES_MST.sm_service_name_mar
  is 'Service Name in Marathi';
comment on column TB_AUDIT_SERVICES_MST.cdm_dept_id
  is 'Department Identification';
comment on column TB_AUDIT_SERVICES_MST.sm_approval_form
  is 'Name of the approval form';
comment on column TB_AUDIT_SERVICES_MST.sm_rejection_form
  is 'Name of the rejection form';
comment on column TB_AUDIT_SERVICES_MST.sm_web_enabled
  is 'Web enabling flag';
comment on column TB_AUDIT_SERVICES_MST.sm_url
  is 'Url';
comment on column TB_AUDIT_SERVICES_MST.sm_srno
  is 'Serial Number';
comment on column TB_AUDIT_SERVICES_MST.sm_switch
  is 'Skipping print grid step.';
comment on column TB_AUDIT_SERVICES_MST.sm_addr
  is 'Address details';
comment on column TB_AUDIT_SERVICES_MST.updated_by
  is 'Modified By';
comment on column TB_AUDIT_SERVICES_MST.updated_date
  is 'Modification Date';
comment on column TB_AUDIT_SERVICES_MST.sm_rcpt
  is 'Receipt';
comment on column TB_AUDIT_SERVICES_MST.sm_initiating_empid
  is 'Initial Employee Id';
comment on column TB_AUDIT_SERVICES_MST.sm_cpd_id
  is 'Certificate Details';
comment on column TB_AUDIT_SERVICES_MST.sm_loi_duration
  is 'LOI Notice Duration of service';
comment on column TB_AUDIT_SERVICES_MST.sm_shortdesc
  is 'Short Description Used For Prefix';
comment on column TB_AUDIT_SERVICES_MST.srid_hours
  is 'Duration of service in hours';
comment on column TB_AUDIT_SERVICES_MST.srid_min
  is 'Duration of service in Minutes';
comment on column TB_AUDIT_SERVICES_MST.sm_serv_duration_type
  is 'Service duration type comes from prifix SPT';
comment on column TB_AUDIT_SERVICES_MST.ip_mac
  is 'Client Machine�s Login Name | IP Address | Physical Address';
comment on column TB_AUDIT_SERVICES_MST.backend_updated
  is 'Client backend updated Machine�s Login Name | IP Address | Physical Address';
comment on column TB_AUDIT_SERVICES_MST.aud_flag
  is 'AUD_FLAG(I U D) ';
comment on column TB_AUDIT_SERVICES_MST.mst_seq
  is 'MST_SEQ ';
comment on column TB_AUDIT_SERVICES_MST.user_name
  is 'Updation USER NAME';
comment on column TB_AUDIT_SERVICES_MST.sm_service_note
  is 'Note remark in English for service ';
comment on column TB_AUDIT_SERVICES_MST.sm_service_note_mar
  is 'Note remark in Marathi for service ';
comment on column TB_AUDIT_SERVICES_MST.sm_sms
  is 'flag to identify whether SMS applicable on service. set to ''Y'' when applicable else default ''N'' ';
comment on column TB_AUDIT_SERVICES_MST.sm_email
  is 'flag to identify whether EMAIL applicable on service. set to ''Y'' when applicable else default ''N'' ';

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
  cpm_edit_desc      VARCHAR2(1),
  cpm_edit_value     VARCHAR2(1),
  cpm_edit_oth       VARCHAR2(1),
  load_at_startup    CHAR(1) default 'N',
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
prompt Creating table TB_BANK_MASTER
prompt =============================
prompt
create table TB_BANK_MASTER
(
  bankid        NUMBER(12) not null,
  bank          NVARCHAR2(500),
  ifsc          NVARCHAR2(11),
  micr          NVARCHAR2(9),
  branch        NVARCHAR2(200),
  address       NVARCHAR2(1000),
  contact       NVARCHAR2(200),
  city          NVARCHAR2(100),
  district      NVARCHAR2(100),
  state         NVARCHAR2(100),
  created_by    NUMBER(7) not null,
  created_date  DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lang_id       NUMBER(7) not null,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  fi04_n1       NUMBER(15),
  fi04_v1       NVARCHAR2(100),
  fi04_d1       DATE,
  fi04_lo1      CHAR(1)
)
;
comment on column TB_BANK_MASTER.bankid
  is 'Primary Key';
comment on column TB_BANK_MASTER.bank
  is 'Bank Name';
comment on column TB_BANK_MASTER.ifsc
  is 'IFSC Code';
comment on column TB_BANK_MASTER.micr
  is 'MICR Code';
comment on column TB_BANK_MASTER.branch
  is 'Bank Branch Name';
comment on column TB_BANK_MASTER.address
  is 'Bank Branch Address';
comment on column TB_BANK_MASTER.contact
  is 'Bank Branch Contact, Mobile number, Email Id';
comment on column TB_BANK_MASTER.city
  is 'Bank Branch City';
comment on column TB_BANK_MASTER.district
  is 'Bank Branch District';
comment on column TB_BANK_MASTER.state
  is 'Bank Branch State';
comment on column TB_BANK_MASTER.created_by
  is 'Created User Identity';
comment on column TB_BANK_MASTER.created_date
  is 'Created Date';
comment on column TB_BANK_MASTER.updated_by
  is 'User id who update the data';
comment on column TB_BANK_MASTER.updated_date
  is 'Date on which data is going to update';
comment on column TB_BANK_MASTER.lang_id
  is 'Language Identity';
comment on column TB_BANK_MASTER.lg_ip_mac
  is 'Client Machine is Login Name | IP Address | Physical Address';
comment on column TB_BANK_MASTER.lg_ip_mac_upd
  is 'Updated Client Machine is Login Name | IP Address | Physical Address';
comment on column TB_BANK_MASTER.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_BANK_MASTER.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_BANK_MASTER.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_BANK_MASTER.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
alter table TB_BANK_MASTER
  add constraint PK_BANKCB_BANKID primary key (BANKID);

prompt
prompt Creating table TB_ULB_BANK
prompt ==========================
prompt
create table TB_ULB_BANK
(
  ulb_bankid      NUMBER(12) not null,
  bankid          NUMBER(12),
  orgid           NUMBER(4) not null,
  created_by      NUMBER(7) not null,
  created_date    DATE not null,
  updated_by      NUMBER(7),
  updated_date    DATE,
  lang_id         NUMBER(7) not null,
  lg_ip_mac       VARCHAR2(100),
  lg_ip_mac_upd   VARCHAR2(100),
  fi04_n1         NUMBER(15),
  fi04_v1         NVARCHAR2(100),
  fi04_d1         DATE,
  fi04_lo1        CHAR(1),
  bmstatus        CHAR(1),
  cpd_id_banktype NUMBER(12)
)
;
comment on column TB_ULB_BANK.ulb_bankid
  is 'Primary Key';
comment on column TB_ULB_BANK.bankid
  is 'fk- TB_BANK_MASTER Code of the bank in which account exists coming from ulb Bank Master';
comment on column TB_ULB_BANK.orgid
  is 'Organization Id';
comment on column TB_ULB_BANK.created_by
  is 'Created User Identity';
comment on column TB_ULB_BANK.created_date
  is 'Created Date';
comment on column TB_ULB_BANK.updated_by
  is 'User id who update the data';
comment on column TB_ULB_BANK.updated_date
  is 'Date on which data is going to update';
comment on column TB_ULB_BANK.lang_id
  is 'Language Identity';
comment on column TB_ULB_BANK.lg_ip_mac
  is 'Client Machine is Login Name | IP Address | Physical Address';
comment on column TB_ULB_BANK.lg_ip_mac_upd
  is 'Updated Client Machine is Login Name | IP Address | Physical Address';
comment on column TB_ULB_BANK.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_ULB_BANK.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_ULB_BANK.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_ULB_BANK.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
comment on column TB_ULB_BANK.cpd_id_banktype
  is 'Bank type ref table tb_comparam_det';
alter table TB_ULB_BANK
  add constraint PK_ULB_BANKID primary key (ULB_BANKID);
alter table TB_ULB_BANK
  add constraint FK_BANKID foreign key (BANKID)
  references TB_BANK_MASTER (BANKID);
alter table TB_ULB_BANK
  add constraint FK_ORGID_BANK foreign key (ORGID)
  references TB_ORGANISATION (ORGID);

prompt
prompt Creating table TB_BANK_ACCOUNT
prompt ==============================
prompt
create table TB_BANK_ACCOUNT
(
  ba_accountid     NUMBER(12) not null,
  ulb_bankid       NUMBER(12),
  ba_account_no    NVARCHAR2(25) not null,
  cpd_accounttype  NUMBER(12) not null,
  ba_accountname   NVARCHAR2(150) not null,
  ba_openbal_date  DATE,
  ba_open_bal_amt  NUMBER(15,2),
  fund_id          NUMBER(12),
  field_id         NUMBER(12),
  pac_head_id      NUMBER(12),
  app_challan_flag CHAR(1),
  ac_cpd_id_status NUMBER(12),
  created_by       NUMBER(7) not null,
  created_date     DATE not null,
  updated_by       NUMBER(7),
  updated_date     DATE,
  lang_id          NUMBER(7) not null,
  lg_ip_mac        VARCHAR2(100),
  lg_ip_mac_upd    VARCHAR2(100),
  fi04_n1          NUMBER(15),
  fi04_v1          NVARCHAR2(100),
  fi04_d1          DATE,
  fi04_lo1         CHAR(1)
)
;
comment on column TB_BANK_ACCOUNT.ba_accountid
  is 'Primary Key';
comment on column TB_BANK_ACCOUNT.ulb_bankid
  is 'fk- TB_BANK_MASTER Code of the bank in which account exists coming from ulb Bank Master';
comment on column TB_BANK_ACCOUNT.ba_account_no
  is 'Bank Account number';
comment on column TB_BANK_ACCOUNT.cpd_accounttype
  is 'Type of account with each Bank C-Current,R-Recurring,S-Saving,F-Fixed';
comment on column TB_BANK_ACCOUNT.ba_accountname
  is 'Bank Account Holder Name';
comment on column TB_BANK_ACCOUNT.ba_openbal_date
  is 'Opending Balance Date';
comment on column TB_BANK_ACCOUNT.ba_open_bal_amt
  is 'Opending Balance amount';
comment on column TB_BANK_ACCOUNT.fund_id
  is 'Fund Master Reference key --TB_AC_FUND_MASTER';
comment on column TB_BANK_ACCOUNT.field_id
  is 'Field Master Reference key  --TB_AC_FIELD_MASTER';
comment on column TB_BANK_ACCOUNT.pac_head_id
  is 'Secondary Master Reference key -- tb_ac_secondaryhead_master';
comment on column TB_BANK_ACCOUNT.app_challan_flag
  is 'bank for challan';
comment on column TB_BANK_ACCOUNT.ac_cpd_id_status
  is 'Bank account status';
comment on column TB_BANK_ACCOUNT.created_by
  is 'Created User Identity';
comment on column TB_BANK_ACCOUNT.created_date
  is 'Created Date';
comment on column TB_BANK_ACCOUNT.updated_by
  is 'User id who update the data';
comment on column TB_BANK_ACCOUNT.updated_date
  is 'Date on which data is going to update';
comment on column TB_BANK_ACCOUNT.lang_id
  is 'Language Identity';
comment on column TB_BANK_ACCOUNT.lg_ip_mac
  is 'Client Machine is Login Name | IP Address | Physical Address';
comment on column TB_BANK_ACCOUNT.lg_ip_mac_upd
  is 'Updated Client Machine is Login Name | IP Address | Physical Address';
comment on column TB_BANK_ACCOUNT.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_BANK_ACCOUNT.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_BANK_ACCOUNT.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_BANK_ACCOUNT.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
alter table TB_BANK_ACCOUNT
  add constraint PK_BANKBA_ACCOUNTID primary key (BA_ACCOUNTID);
alter table TB_BANK_ACCOUNT
  add constraint FK_CPD_ACCOUNTTYPE foreign key (CPD_ACCOUNTTYPE)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_BANK_ACCOUNT
  add constraint FK_FIELD_ID_BANKAC foreign key (FIELD_ID)
  references TB_AC_FIELD_MASTER (FIELD_ID);
alter table TB_BANK_ACCOUNT
  add constraint FK_FUND_ID_BANKAC foreign key (FUND_ID)
  references TB_AC_FUND_MASTER (FUND_ID);
alter table TB_BANK_ACCOUNT
  add constraint FK_PAC_HEAD_ID_BANKAC foreign key (PAC_HEAD_ID)
  references TB_AC_PRIMARYHEAD_MASTER (PAC_HEAD_ID);
alter table TB_BANK_ACCOUNT
  add constraint FK_ULB_BANKID_ACCOUNT foreign key (ULB_BANKID)
  references TB_ULB_BANK (ULB_BANKID);

prompt
prompt Creating table TB_CFC_APPLICATION_ADDRESS
prompt =========================================
prompt
create table TB_CFC_APPLICATION_ADDRESS
(
  apm_application_id   NUMBER(16) not null,
  apa_blockno          NVARCHAR2(10),
  apa_floor            NVARCHAR2(10),
  apa_wing             NVARCHAR2(10),
  apa_bldgnm           NVARCHAR2(150),
  apa_hsg_cmplxnm      NVARCHAR2(150),
  apa_roadnm           NVARCHAR2(150),
  apa_areanm           NVARCHAR2(150),
  apa_pincode          NUMBER(6),
  apa_phone1           NVARCHAR2(25),
  apa_phone2           NVARCHAR2(25),
  apa_contact_personnm NVARCHAR2(100),
  apa_email            NVARCHAR2(100),
  apa_pagerno          NVARCHAR2(20),
  apa_mobilno          NVARCHAR2(20),
  apa_ward_no          NUMBER(12),
  apa_electoral_ward   NVARCHAR2(5),
  orgid                NUMBER(4) not null,
  user_id              NUMBER(7) not null,
  lang_id              NUMBER(7) not null,
  lmoddate             DATE not null,
  apa_city_id          NUMBER(12),
  apa_district_id      NUMBER(12),
  apa_state_id         NUMBER(12),
  updated_by           NUMBER(7),
  updated_date         DATE,
  apa_taluka_id        NUMBER(12),
  apa_country_id       NUMBER(12),
  lg_ip_mac            VARCHAR2(100),
  lg_ip_mac_upd        VARCHAR2(100),
  apa_landmark         NVARCHAR2(150),
  apa_zone_no          NUMBER(12),
  apa_block_name       VARCHAR2(50),
  apa_flat_building_no VARCHAR2(20),
  apa_city_name        VARCHAR2(50)
)
;
comment on table TB_CFC_APPLICATION_ADDRESS
  is 'Applicant Address Details';
comment on column TB_CFC_APPLICATION_ADDRESS.apm_application_id
  is 'Application ID';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_blockno
  is 'Block Number of Address';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_floor
  is 'floor of the building';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_wing
  is 'wing of the building';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_bldgnm
  is 'Building Name';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_hsg_cmplxnm
  is 'Housing Complex No.';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_roadnm
  is 'Block Number of Address';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_areanm
  is 'Block Number of Address';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_pincode
  is 'Pincode Number';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_phone1
  is 'First Phoe No';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_phone2
  is 'Second Phone No';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_contact_personnm
  is 'Name of the contact person';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_email
  is 'Email Address';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_pagerno
  is 'Pager No';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_mobilno
  is 'Mobil No';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_ward_no
  is 'Ward';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_electoral_ward
  is 'Electoral Ward';
comment on column TB_CFC_APPLICATION_ADDRESS.orgid
  is 'Organization Id';
comment on column TB_CFC_APPLICATION_ADDRESS.user_id
  is 'User Id';
comment on column TB_CFC_APPLICATION_ADDRESS.lang_id
  is 'Language Id';
comment on column TB_CFC_APPLICATION_ADDRESS.lmoddate
  is 'Last Modification Date';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_city_id
  is 'City Identification';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_district_id
  is 'District Identification';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_state_id
  is 'State Identification';
comment on column TB_CFC_APPLICATION_ADDRESS.updated_by
  is 'Last Updated By';
comment on column TB_CFC_APPLICATION_ADDRESS.updated_date
  is 'Last Updated Date';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_taluka_id
  is 'Taluka ID  ';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_country_id
  is 'Country ID ';
comment on column TB_CFC_APPLICATION_ADDRESS.lg_ip_mac
  is 'Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_CFC_APPLICATION_ADDRESS.lg_ip_mac_upd
  is 'Updated Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_landmark
  is 'Landmark information';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_zone_no
  is 'ZONE';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_block_name
  is 'BLOCK NAME';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_flat_building_no
  is 'FLAT NO./BULDING NO.';
comment on column TB_CFC_APPLICATION_ADDRESS.apa_city_name
  is 'VILLAGE/TOWN/CITY NAME';
alter table TB_CFC_APPLICATION_ADDRESS
  add constraint PK_APA_APPLICATION_ID primary key (APM_APPLICATION_ID);
alter table TB_CFC_APPLICATION_ADDRESS
  add constraint FK_APM_ADDR_APPL_ID foreign key (APM_APPLICATION_ID)
  references TB_CFC_APPLICATION_MST (APM_APPLICATION_ID);

prompt
prompt Creating table TB_CFC_APPLICATION_ADD_HIST
prompt ==========================================
prompt
create table TB_CFC_APPLICATION_ADD_HIST
(
  h_apmid              NUMBER(16) not null,
  apm_application_id   NUMBER(16) not null,
  apa_blockno          NVARCHAR2(10),
  apa_floor            NVARCHAR2(10),
  apa_wing             NVARCHAR2(10),
  apa_bldgnm           NVARCHAR2(150),
  apa_hsg_cmplxnm      NVARCHAR2(150),
  apa_roadnm           NVARCHAR2(150),
  apa_areanm           NVARCHAR2(150),
  apa_pincode          NUMBER(6),
  apa_phone1           NVARCHAR2(25),
  apa_phone2           NVARCHAR2(25),
  apa_contact_personnm NVARCHAR2(100),
  apa_email            NVARCHAR2(100),
  apa_pagerno          NVARCHAR2(20),
  apa_mobilno          NVARCHAR2(20),
  apa_ward_no          NUMBER(12),
  apa_electoral_ward   NVARCHAR2(5),
  orgid                NUMBER(4) not null,
  user_id              NUMBER(7) not null,
  lang_id              NUMBER(7) not null,
  lmoddate             DATE not null,
  apa_city_id          NUMBER(12),
  apa_district_id      NUMBER(12),
  apa_state_id         NUMBER(12),
  updated_by           NUMBER(7),
  updated_date         DATE,
  apa_taluka_id        NUMBER(12),
  apa_country_id       NUMBER(12),
  lg_ip_mac            VARCHAR2(100),
  lg_ip_mac_upd        VARCHAR2(100),
  apa_landmark         NVARCHAR2(150),
  apa_zone_no          NUMBER(12),
  apa_block_name       VARCHAR2(50),
  apa_flat_building_no VARCHAR2(20),
  apa_city_name        VARCHAR2(50),
  h_status             NVARCHAR2(1)
)
;
alter table TB_CFC_APPLICATION_ADD_HIST
  add constraint PK_H_APMID primary key (H_APMID);

prompt
prompt Creating table TB_CFC_APPLICATION_MST_HIST
prompt ==========================================
prompt
create table TB_CFC_APPLICATION_MST_HIST
(
  h_apm_applicationid   NUMBER(16) not null,
  apm_application_id    NUMBER(16) not null,
  apm_application_date  DATE not null,
  sm_service_id         NUMBER(12) not null,
  ccd_apm_type          NUMBER(12),
  apm_orgn_name         NVARCHAR2(200),
  apm_post_in_orgn      NVARCHAR2(100),
  apm_title             NUMBER(12) not null,
  apm_lname             NVARCHAR2(100),
  apm_fname             NVARCHAR2(100) not null,
  apm_mname             NVARCHAR2(100),
  apm_sex               NVARCHAR2(1),
  apm_age               NUMBER(3),
  apm_dob               DATE,
  orgid                 NUMBER(4) not null,
  user_id               NUMBER(7) not null,
  lang_id               NUMBER(7) not null,
  lmoddate              DATE not null,
  ctz_citizenid         NVARCHAR2(16),
  ref_no                NUMBER(12),
  updated_by            NUMBER(7),
  updated_date          DATE,
  lg_ip_mac             VARCHAR2(100),
  lg_ip_mac_upd         VARCHAR2(100),
  apm_resid             NVARCHAR2(16),
  apm_cfc_ward          NUMBER(12),
  apm_user_ward         NUMBER(12),
  apm_source_flag       VARCHAR2(1) default 'C',
  rejction_no           NUMBER(18),
  rejection_dt          DATE,
  apm_pay_stat_flag     NVARCHAR2(1),
  apm_wd_id             NUMBER(12),
  apm_app_pending_to    VARCHAR2(20),
  apm_last_event_id     NUMBER,
  apm_chklst_vrfy_flag  NVARCHAR2(1),
  apm_appl_success_flag NVARCHAR2(1),
  apm_appl_closed_flag  NVARCHAR2(1),
  apm_remark            VARCHAR2(100),
  apm_app_rej_flag      NVARCHAR2(1),
  apm_app_rej_by        NUMBER(18),
  apm_app_rej_date      DATE,
  apm_bpl_no            VARCHAR2(15),
  apm_uid               VARCHAR2(15),
  h_status              NVARCHAR2(1),
  apm_approve_by        NUMBER(12),
  apm_approve_date      DATE
)
;
alter table TB_CFC_APPLICATION_MST_HIST
  add constraint PK_H_APM_APPLICATIONID primary key (H_APM_APPLICATIONID);

prompt
prompt Creating table TB_CFC_CHECKLIST_MST_HIST
prompt ========================================
prompt
create table TB_CFC_CHECKLIST_MST_HIST
(
  h_clmid         NUMBER(12) not null,
  clm_id          NUMBER(12) not null,
  sm_service_id   NUMBER(12) not null,
  clm_sr_no       NUMBER(3) not null,
  clm_desc        NVARCHAR2(2000) not null,
  clm_status      NVARCHAR2(1) default 'A',
  orgid           NUMBER(4) not null,
  user_id         NUMBER(7) not null,
  lang_id         NUMBER(7) not null,
  lmoddate        DATE not null,
  clm_desc_engl   NVARCHAR2(2000),
  ccm_valueset    NUMBER(12),
  updated_by      NUMBER(7),
  updated_date    DATE,
  doc_group       VARCHAR2(200),
  com_v2          NVARCHAR2(100),
  com_v3          NVARCHAR2(100),
  com_v4          NVARCHAR2(100),
  com_v5          NVARCHAR2(100),
  com_n1          NUMBER(15),
  com_n2          NUMBER(15),
  com_n3          NUMBER(15),
  com_n4          NUMBER(15),
  com_n5          NUMBER(15),
  com_d1          DATE,
  com_d2          DATE,
  com_d3          DATE,
  com_lo1         CHAR(1),
  com_lo2         CHAR(1),
  com_lo3         CHAR(1),
  applicable_from DATE,
  applicable_to   DATE,
  checklist_flag  NVARCHAR2(1) default 'N',
  h_status        NVARCHAR2(1)
)
;
alter table TB_CFC_CHECKLIST_MST_HIST
  add constraint PK_H_CLMID primary key (H_CLMID);

prompt
prompt Creating table TB_CHALLAN_DET
prompt =============================
prompt
create table TB_CHALLAN_DET
(
  ch_id         NUMBER(12) not null,
  challan_no    NVARCHAR2(30),
  tax_id        NUMBER(12),
  rf_feeamount  NUMBER(12,2),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7),
  lang_id       NUMBER(7),
  lmoddate      DATE,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  is_deleted    CHAR(1),
  bill_detid    NUMBER(12)
)
;
comment on column TB_CHALLAN_DET.is_deleted
  is 'Flag for Deleted Entries.';
comment on column TB_CHALLAN_DET.bill_detid
  is 'bill det Id from bill det table';
alter table TB_CHALLAN_DET
  add constraint PK_CH_ID primary key (CH_ID);

prompt
prompt Creating table TB_CHALLAN_DET_HIST
prompt ==================================
prompt
create table TB_CHALLAN_DET_HIST
(
  h_chid        NUMBER(12) not null,
  ch_id         NUMBER(12) not null,
  challan_no    NVARCHAR2(30),
  tax_id        NUMBER(12),
  rf_feeamount  NUMBER(12,2),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7),
  lang_id       NUMBER(7),
  lmoddate      DATE,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  h_status      NVARCHAR2(1)
)
;
alter table TB_CHALLAN_DET_HIST
  add constraint PK_H_CHID primary key (H_CHID);

prompt
prompt Creating table TB_CHALLAN_MASTER
prompt ================================
prompt
create table TB_CHALLAN_MASTER
(
  challan_id               NUMBER(12) not null,
  orgid                    NUMBER(4) not null,
  challan_no               NVARCHAR2(30),
  challan_amount           NUMBER(15,2),
  challan_date             DATE,
  challan_vali_date        DATE,
  bm_bankid                NUMBER(12),
  ba_accountid             NUMBER(12),
  bank_trans_id            VARCHAR2(30),
  fa_yearid                NUMBER(12),
  apm_application_id       NUMBER(16),
  sm_service_id            NUMBER(12),
  dp_deptid                NUMBER(12),
  challan_rcvd_flag        CHAR(1),
  challan_rcvd_date        DATE,
  challan_rcvd_by          NUMBER(12),
  status                   CHAR(1) default 'A',
  user_id                  NUMBER(12) not null,
  lang_id                  NUMBER(7) not null,
  lmoddate                 DATE not null,
  updated_by               NUMBER(12),
  updated_date             DATE,
  lg_ip_mac                VARCHAR2(100),
  lg_ip_mac_upd            VARCHAR2(100),
  challn_gen_service_type  CHAR(1),
  ofl_payment_mode         NUMBER(12),
  bill_no                  NUMBER,
  loi_no                   NVARCHAR2(16),
  payment_receipt_category CHAR(1)
)
;
comment on column TB_CHALLAN_MASTER.challan_id
  is 'Primary Key';
comment on column TB_CHALLAN_MASTER.orgid
  is 'Organisation Id';
comment on column TB_CHALLAN_MASTER.challan_no
  is 'Challan No.';
comment on column TB_CHALLAN_MASTER.challan_amount
  is 'Challan Amount';
comment on column TB_CHALLAN_MASTER.challan_date
  is 'Challan Generated Date';
comment on column TB_CHALLAN_MASTER.challan_vali_date
  is 'Challan Valid Date';
comment on column TB_CHALLAN_MASTER.bm_bankid
  is 'Challan Bank Id';
comment on column TB_CHALLAN_MASTER.ba_accountid
  is 'Challan Bank Account';
comment on column TB_CHALLAN_MASTER.bank_trans_id
  is 'Challan Bank Transaction Id';
comment on column TB_CHALLAN_MASTER.fa_yearid
  is 'Financial Year';
comment on column TB_CHALLAN_MASTER.apm_application_id
  is 'Application Id';
comment on column TB_CHALLAN_MASTER.sm_service_id
  is 'Service Id';
comment on column TB_CHALLAN_MASTER.dp_deptid
  is 'Department Id';
comment on column TB_CHALLAN_MASTER.challan_rcvd_flag
  is 'Challan Received Flag N- No   Y- received at CFC';
comment on column TB_CHALLAN_MASTER.challan_rcvd_date
  is 'Challan Received Date';
comment on column TB_CHALLAN_MASTER.challan_rcvd_by
  is 'Challan Received By at CFC counter';
comment on column TB_CHALLAN_MASTER.status
  is 'Status';
comment on column TB_CHALLAN_MASTER.user_id
  is 'User Id';
comment on column TB_CHALLAN_MASTER.lang_id
  is 'Language Id';
comment on column TB_CHALLAN_MASTER.lmoddate
  is 'Last Modification Date';
comment on column TB_CHALLAN_MASTER.updated_by
  is 'Updated By ';
comment on column TB_CHALLAN_MASTER.updated_date
  is 'Updated Date';
comment on column TB_CHALLAN_MASTER.lg_ip_mac
  is 'client machine.s login name | ip address | physical address ';
comment on column TB_CHALLAN_MASTER.lg_ip_mac_upd
  is 'updated client machine.s login name | ip address | physical address ';
comment on column TB_CHALLAN_MASTER.challn_gen_service_type
  is '''Y''--Revenue Based , ''N''--Non -revenue based';
comment on column TB_CHALLAN_MASTER.ofl_payment_mode
  is 'offline payment mode from OFL prefix';
comment on column TB_CHALLAN_MASTER.bill_no
  is 'Connection ID/Property Id for bill payment';
comment on column TB_CHALLAN_MASTER.loi_no
  is 'loi no for Loi challan Update';
comment on column TB_CHALLAN_MASTER.payment_receipt_category
  is 'A-advance,B-bill payment';
alter table TB_CHALLAN_MASTER
  add constraint PK_TB_CHALLAN_MASTER primary key (CHALLAN_ID);
alter table TB_CHALLAN_MASTER
  add constraint FK4C03AD2938B65205 foreign key (UPDATED_BY)
  references EMPLOYEE (EMPID);
alter table TB_CHALLAN_MASTER
  add constraint FK4C03AD296078ED5 foreign key (ORGID)
  references TB_ORGANISATION (ORGID);

prompt
prompt Creating table TB_CHALLAN_MASTER_HIST
prompt =====================================
prompt
create table TB_CHALLAN_MASTER_HIST
(
  h_challanid             NUMBER(12) not null,
  challan_id              NUMBER(12) not null,
  orgid                   NUMBER(4) not null,
  challan_no              NVARCHAR2(30),
  challan_amount          NUMBER(15,2),
  challan_date            DATE,
  challan_vali_date       DATE,
  bm_bankid               NUMBER(12),
  ba_accountid            NUMBER(12),
  bank_trans_id           VARCHAR2(30),
  fa_yearid               NUMBER(12),
  apm_application_id      NUMBER(16),
  sm_service_id           NUMBER(12),
  dp_deptid               NUMBER(12),
  challan_rcvd_flag       CHAR(1),
  challan_rcvd_date       DATE,
  challan_rcvd_by         NUMBER(12),
  status                  CHAR(1) default 'A',
  user_id                 NUMBER(12) not null,
  lang_id                 NUMBER(7) not null,
  lmoddate                DATE not null,
  updated_by              NUMBER(12),
  updated_date            DATE,
  lg_ip_mac               VARCHAR2(100),
  lg_ip_mac_upd           VARCHAR2(100),
  challn_gen_service_type CHAR(1),
  ofl_payment_mode        NUMBER(12),
  receipt_id              NUMBER,
  loi_no                  NVARCHAR2(16),
  h_status                NVARCHAR2(1)
)
;
alter table TB_CHALLAN_MASTER_HIST
  add constraint PK_ primary key (H_CHALLANID);

prompt
prompt Creating table TB_CHARGE_MASTER
prompt ===============================
prompt
create table TB_CHARGE_MASTER
(
  cm_id                    NUMBER not null,
  cm_orgid                 NUMBER(4) not null,
  cm_service_id            NUMBER(12) not null,
  cm_chargeapplicableat    NUMBER(12) not null,
  cm_charge_type           NUMBER(12) not null,
  cm_slab_depend           NUMBER(12) not null,
  cm_flat_depend           NUMBER(12) not null,
  cm_chargedescription_eng VARCHAR2(100),
  cm_chargedescription_reg VARCHAR2(100),
  cm_charge_sequence       NUMBER not null,
  cm_charge_start_date     DATE not null,
  cm_charge_end_date       DATE not null,
  created_date             DATE not null,
  created_by               NUMBER(12) not null,
  updated_date             DATE,
  updated_by               NUMBER(12),
  isdeleted                VARCHAR2(1) default 'N' not null,
  lg_ip_mac                VARCHAR2(100) not null,
  lg_ip_mac_upd            VARCHAR2(100)
)
;
comment on column TB_CHARGE_MASTER.cm_id
  is 'Primary Key';
comment on column TB_CHARGE_MASTER.cm_orgid
  is 'Organization ID';
comment on column TB_CHARGE_MASTER.cm_service_id
  is 'Service Id to for which charges is created';
comment on column TB_CHARGE_MASTER.cm_chargeapplicableat
  is 'the lavel(prefix) on which charge is applicable : application/scrunity';
comment on column TB_CHARGE_MASTER.cm_charge_type
  is 'the charge type : flat/slab';
comment on column TB_CHARGE_MASTER.cm_slab_depend
  is 'the dependency on which slab depends if charges type salected as SLAB';
comment on column TB_CHARGE_MASTER.cm_flat_depend
  is 'the dependency on which charges  depends if charges type salected as FLAT';
comment on column TB_CHARGE_MASTER.cm_chargedescription_eng
  is 'the Charge description in English';
comment on column TB_CHARGE_MASTER.cm_chargedescription_reg
  is 'the Charge description in Regional Language';
comment on column TB_CHARGE_MASTER.cm_charge_sequence
  is 'the Charge description in Charge Sequence';
comment on column TB_CHARGE_MASTER.cm_charge_start_date
  is 'the date from charges is applicable';
comment on column TB_CHARGE_MASTER.cm_charge_end_date
  is 'the date to charges is applicable';
comment on column TB_CHARGE_MASTER.created_date
  is 'Workflow Created Date';
comment on column TB_CHARGE_MASTER.created_by
  is 'Workflow Created By which Employee';
comment on column TB_CHARGE_MASTER.updated_date
  is 'Workflow Updated Date';
comment on column TB_CHARGE_MASTER.updated_by
  is 'Workflow Updated By which Employee';
comment on column TB_CHARGE_MASTER.isdeleted
  is 'default value-N ,flag to identify whether Workflow is deleted or not , Y-deleted, N-not deleted';
comment on column TB_CHARGE_MASTER.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_CHARGE_MASTER.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
alter table TB_CHARGE_MASTER
  add constraint PK_TB_CHARGE_MASTER primary key (CM_ID);

prompt
prompt Creating table TB_CHARGE_MASTER_HIST
prompt ====================================
prompt
create table TB_CHARGE_MASTER_HIST
(
  h_cmid                   NUMBER not null,
  cm_id                    NUMBER not null,
  cm_orgid                 NUMBER(4) not null,
  cm_service_id            NUMBER(12) not null,
  cm_chargeapplicableat    NUMBER(12) not null,
  cm_charge_type           NUMBER(12) not null,
  cm_slab_depend           NUMBER(12) not null,
  cm_flat_depend           NUMBER(12) not null,
  cm_chargedescription_eng VARCHAR2(100),
  cm_chargedescription_reg VARCHAR2(100),
  cm_charge_sequence       NUMBER not null,
  cm_charge_start_date     DATE not null,
  cm_charge_end_date       DATE not null,
  created_date             DATE not null,
  created_by               NUMBER(12) not null,
  updated_date             DATE,
  updated_by               NUMBER(12),
  isdeleted                VARCHAR2(1) default 'N' not null,
  lg_ip_mac                VARCHAR2(100) not null,
  lg_ip_mac_upd            VARCHAR2(100),
  h_status                 NVARCHAR2(1)
)
;
alter table TB_CHARGE_MASTER_HIST
  add constraint PK_HCMID primary key (H_CMID);

prompt
prompt Creating table TB_CHECKLIST_MST
prompt ===============================
prompt
create table TB_CHECKLIST_MST
(
  orgid         NUMBER(4) not null,
  cl_id         NUMBER(12) not null,
  sm_service_id NUMBER(12) not null,
  doc_group     VARCHAR2(200) not null,
  cl_status     NVARCHAR2(1) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  created_by    NUMBER(7),
  created_date  DATE
)
;

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
prompt Creating table TB_COMPLAINT_TYPE
prompt ================================
prompt
create table TB_COMPLAINT_TYPE
(
  type_id       NUMBER(4) not null,
  orgid         NUMBER(4) not null,
  description   NVARCHAR2(1000) not null,
  status        VARCHAR2(1) not null,
  updated_by    NUMBER(4),
  updated_date  DATE,
  language_id   NUMBER(4) not null,
  create_date   DATE not null,
  created_by    NUMBER(4),
  lg_ip_mac     NVARCHAR2(300),
  lg_ip_mac_upd NVARCHAR2(300)
)
;
comment on column TB_COMPLAINT_TYPE.type_id
  is 'Primary Key for TB_COMPLAINT_TYPE';
comment on column TB_COMPLAINT_TYPE.orgid
  is 'Organisation id of the TB_COMPLAINT_TYPE';
comment on column TB_COMPLAINT_TYPE.description
  is 'Name of the TB_COMPLAINT_TYPE';
comment on column TB_COMPLAINT_TYPE.status
  is 'Status of the TB_COMPLAINT_TYPE (Active/Inactive)';
comment on column TB_COMPLAINT_TYPE.updated_by
  is 'Modification done by employee';
comment on column TB_COMPLAINT_TYPE.updated_date
  is 'Modification done date time';
comment on column TB_COMPLAINT_TYPE.language_id
  is 'Language ID';
comment on column TB_COMPLAINT_TYPE.create_date
  is 'Data inserted date time';
comment on column TB_COMPLAINT_TYPE.lg_ip_mac
  is 'Combination of loged user PC Login Name,IP Address and Mac Address';
create index COMP_TY_INDX on TB_COMPLAINT_TYPE (STATUS);
create index IX_TB_COM_TYPE on TB_COMPLAINT_TYPE (DESCRIPTION);
alter table TB_COMPLAINT_TYPE
  add constraint PK_TB_COM_TYPE primary key (TYPE_ID);

prompt
prompt Creating table TB_COMPLAINT_SUB_TYPE
prompt ====================================
prompt
create table TB_COMPLAINT_SUB_TYPE
(
  sub_type_id   NUMBER(4) not null,
  type_id       NUMBER(4) not null,
  orgid         NUMBER(4) not null,
  description   NVARCHAR2(1000) not null,
  status        VARCHAR2(1) not null,
  updated_by    NUMBER(4),
  updated_date  DATE,
  language_id   NUMBER(4) not null,
  create_date   DATE not null,
  created_by    NUMBER(4),
  lg_ip_mac     NVARCHAR2(300),
  lg_ip_mac_upd NVARCHAR2(300)
)
;
comment on column TB_COMPLAINT_SUB_TYPE.sub_type_id
  is 'Primary Key for TB_COMPLAINT_SUB_TYPE';
comment on column TB_COMPLAINT_SUB_TYPE.type_id
  is 'Foreign key w.r.t complaint_type';
comment on column TB_COMPLAINT_SUB_TYPE.orgid
  is 'Organisation id of the TB_COMPLAINT_SUB_TYPE';
comment on column TB_COMPLAINT_SUB_TYPE.description
  is 'Name of the TB_COMPLAINT_SUB_TYPE';
comment on column TB_COMPLAINT_SUB_TYPE.status
  is 'Status of the TB_COMPLAINT_SUB_TYPE(Active/Inactive)';
comment on column TB_COMPLAINT_SUB_TYPE.updated_by
  is 'Modification done by employee';
comment on column TB_COMPLAINT_SUB_TYPE.updated_date
  is 'Modification done date time';
comment on column TB_COMPLAINT_SUB_TYPE.language_id
  is 'Language id';
comment on column TB_COMPLAINT_SUB_TYPE.create_date
  is 'Data inserted date time';
comment on column TB_COMPLAINT_SUB_TYPE.lg_ip_mac
  is 'Combination of loged user PC Login Name,IP Address and Mac Address';
create index COMP_SUB_TYPE_INDX on TB_COMPLAINT_SUB_TYPE (TYPE_ID, LANGUAGE_ID, STATUS);
create index IX_TB_COMPLAINT_SUB_TYPE on TB_COMPLAINT_SUB_TYPE (DESCRIPTION);
alter table TB_COMPLAINT_SUB_TYPE
  add constraint PK_TB_COMPLAINT_SUB_TYPE primary key (SUB_TYPE_ID, TYPE_ID);
alter table TB_COMPLAINT_SUB_TYPE
  add constraint FK_COMP_SUB_COMP_TYPE foreign key (TYPE_ID)
  references TB_COMPLAINT_TYPE (TYPE_ID);

prompt
prompt Creating table TB_CONTRACT_MAST
prompt ===============================
prompt
create table TB_CONTRACT_MAST
(
  cont_id               NUMBER(12) not null,
  cont_dept             NUMBER(12) not null,
  cont_no               NVARCHAR2(20) not null,
  cont_date             DATE not null,
  cont_tnd_no           NVARCHAR2(20) not null,
  cont_tnd_date         DATE not null,
  cont_rso_no           NVARCHAR2(20) not null,
  cont_rso_date         DATE not null,
  cont_type             NUMBER(12) not null,
  cont_mode             CHAR(1),
  cont_pay_type         CHAR(1),
  cont_renewal          CHAR(1) not null,
  cont_active           CHAR(1) not null,
  cont_close_flag       CHAR(1) not null,
  cont_aut_status       CHAR(1),
  cont_aut_by           NUMBER(12),
  cont_aut_date         DATE,
  cont_terminat_by      CHAR(1),
  cont_termination_date DATE,
  orgid                 NUMBER(12) not null,
  created_by            NUMBER(12) not null,
  lang_id               NUMBER(7) not null,
  created_date          DATE not null,
  updated_by            NUMBER(12),
  updated_date          DATE,
  lg_ip_mac             VARCHAR2(100) not null,
  lg_ip_mac_upd         VARCHAR2(100)
)
;
comment on table TB_CONTRACT_MAST
  is 'Table for Contract Creation,Contract renewal,contract revision';
comment on column TB_CONTRACT_MAST.cont_id
  is 'primary key';
comment on column TB_CONTRACT_MAST.cont_dept
  is 'Contract Department(department selected in Party1 details)';
comment on column TB_CONTRACT_MAST.cont_no
  is 'Contract No. (ORGID+DEPTCODE+FINANCIAL YEAR+999)';
comment on column TB_CONTRACT_MAST.cont_date
  is 'Contract Date/Renewal Date/Revision Date';
comment on column TB_CONTRACT_MAST.cont_tnd_no
  is 'Tender No.';
comment on column TB_CONTRACT_MAST.cont_tnd_date
  is 'Tender Date';
comment on column TB_CONTRACT_MAST.cont_rso_no
  is 'Resolution No.';
comment on column TB_CONTRACT_MAST.cont_rso_date
  is 'Resolution Date';
comment on column TB_CONTRACT_MAST.cont_type
  is 'Contract Type value from Non Hirarchey Prefix(CNT)';
comment on column TB_CONTRACT_MAST.cont_mode
  is 'Contract Mode(Commercial ''C'',Non Commercial ''N'')';
comment on column TB_CONTRACT_MAST.cont_pay_type
  is 'Contract Payment Type (Payable ''P'',Receivable ''R'')';
comment on column TB_CONTRACT_MAST.cont_renewal
  is 'Renewal of Contract (''Y'',''N)';
comment on column TB_CONTRACT_MAST.cont_active
  is 'flag to identify whether the record is deleted or not. ''y'' for deleted (inactive) and ''n'' for not deleted (active) record.';
comment on column TB_CONTRACT_MAST.cont_close_flag
  is 'Contract Close Flag (N,Y)';
comment on column TB_CONTRACT_MAST.cont_aut_status
  is 'authorisation status';
comment on column TB_CONTRACT_MAST.cont_aut_by
  is 'authorisation by (empid)';
comment on column TB_CONTRACT_MAST.cont_aut_date
  is 'authorisation date';
comment on column TB_CONTRACT_MAST.cont_terminat_by
  is 'Contract Terminated By';
comment on column TB_CONTRACT_MAST.cont_termination_date
  is 'Contract Termination Date';
comment on column TB_CONTRACT_MAST.orgid
  is 'orgnisation id';
comment on column TB_CONTRACT_MAST.created_by
  is 'user id who created the record';
comment on column TB_CONTRACT_MAST.lang_id
  is 'language identity';
comment on column TB_CONTRACT_MAST.created_date
  is 'record creation date';
comment on column TB_CONTRACT_MAST.updated_by
  is 'user id who update the data';
comment on column TB_CONTRACT_MAST.updated_date
  is 'date on which data is updated';
comment on column TB_CONTRACT_MAST.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_CONTRACT_MAST.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';
create index INDX_CONT_NO on TB_CONTRACT_MAST (CONT_NO, ORGID);
alter table TB_CONTRACT_MAST
  add constraint PK_CONT_ID primary key (CONT_ID);

prompt
prompt Creating table TB_CONTRACT_DETAIL
prompt =================================
prompt
create table TB_CONTRACT_DETAIL
(
  contd_id                NUMBER(12) not null,
  cont_id                 NUMBER(12) not null,
  cont_from_date          DATE not null,
  cont_to_date            DATE not null,
  cont_amount             NUMBER(20,2),
  cont_sec_amount         NUMBER(20,2),
  cont_sec_rec_no         NVARCHAR2(20),
  cont_sec_rec_date       DATE,
  cont_pay_period         NUMBER(12),
  cont_installment_period NUMBER(12),
  cont_entry_type         CHAR(2) not null,
  contd_active            CHAR(1) not null,
  orgid                   NUMBER(12) not null,
  created_by              NUMBER(12) not null,
  lang_id                 NUMBER(7) not null,
  created_date            DATE not null,
  updated_by              NUMBER(12),
  updated_date            DATE,
  lg_ip_mac               VARCHAR2(100) not null,
  lg_ip_mac_upd           VARCHAR2(100)
)
;
comment on table TB_CONTRACT_DETAIL
  is 'Storing contract details for Contract Creation,Contract renewal,contract revision';
comment on column TB_CONTRACT_DETAIL.contd_id
  is 'primary key';
comment on column TB_CONTRACT_DETAIL.cont_id
  is 'fk key (TB_CONTRACT_MAST)';
comment on column TB_CONTRACT_DETAIL.cont_from_date
  is 'Contract From Date';
comment on column TB_CONTRACT_DETAIL.cont_to_date
  is 'Contract To Date';
comment on column TB_CONTRACT_DETAIL.cont_amount
  is 'Contracted Amount applicable for Commerciale contract';
comment on column TB_CONTRACT_DETAIL.cont_sec_amount
  is 'Security Deposite Amount applicable for Commerciale contract';
comment on column TB_CONTRACT_DETAIL.cont_sec_rec_no
  is 'Security Deposite Receipt No. applicable for Commerciale contract';
comment on column TB_CONTRACT_DETAIL.cont_sec_rec_date
  is 'Security Deposite Receipt Date applicable for Commerciale contract';
comment on column TB_CONTRACT_DETAIL.cont_pay_period
  is 'Contract Payment Terms (Non Hirarchey Prefix ''PTR'') applicable for Commerciale contract';
comment on column TB_CONTRACT_DETAIL.cont_installment_period
  is 'No. of Installment (Non Hirarchey Prefix ''NOI'') applicable for Commerciale contract';
comment on column TB_CONTRACT_DETAIL.cont_entry_type
  is '(O->Original R->Renew V->Revise S->Sub lease)';
comment on column TB_CONTRACT_DETAIL.contd_active
  is 'flag to identify whether the record is deleted or not. ''y'' for deleted (inactive) and ''n'' for not deleted (active) record.';
comment on column TB_CONTRACT_DETAIL.orgid
  is 'orgnisation id';
comment on column TB_CONTRACT_DETAIL.created_by
  is 'user id who created the record';
comment on column TB_CONTRACT_DETAIL.lang_id
  is 'language identity';
comment on column TB_CONTRACT_DETAIL.created_date
  is 'record creation date';
comment on column TB_CONTRACT_DETAIL.updated_by
  is 'user id who update the data';
comment on column TB_CONTRACT_DETAIL.updated_date
  is 'date on which data is going to update';
comment on column TB_CONTRACT_DETAIL.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_CONTRACT_DETAIL.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';
alter table TB_CONTRACT_DETAIL
  add constraint PK_CONTD_ID primary key (CONTD_ID);
alter table TB_CONTRACT_DETAIL
  add constraint FK_CONT_ID foreign key (CONT_ID)
  references TB_CONTRACT_MAST (CONT_ID);

prompt
prompt Creating table TB_CONTRACT_INSTALMENT_DETAIL
prompt ============================================
prompt
create table TB_CONTRACT_INSTALMENT_DETAIL
(
  conit_id        NUMBER(12) not null,
  cont_id         NUMBER(12) not null,
  conit_amt_type  NUMBER(12) not null,
  conit_value     NUMBER(15,2) not null,
  conit_date      DATE not null,
  conit_milestone NVARCHAR2(500),
  contt_active    CHAR(1) not null,
  conit_pr_flag   CHAR(1),
  orgid           NUMBER(12) not null,
  created_by      NUMBER(12) not null,
  lang_id         NUMBER(7) not null,
  created_date    DATE not null,
  updated_by      NUMBER(12),
  updated_date    DATE,
  lg_ip_mac       VARCHAR2(100) not null,
  lg_ip_mac_upd   VARCHAR2(100)
)
;
comment on column TB_CONTRACT_INSTALMENT_DETAIL.conit_id
  is 'primary key';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.cont_id
  is 'Foregin key(TB_CONTRACT_MAS)';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.conit_amt_type
  is 'Instalment Amount Type(Prefix VTY)';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.conit_value
  is 'Instalment Value';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.conit_date
  is 'Instalment Date';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.conit_milestone
  is 'Instalment Milestone';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.contt_active
  is 'flag to identify whether the record is deleted or not. ''y'' for deleted (inactive) and ''n'' for not deleted (active) record.';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.conit_pr_flag
  is 'Installment Paid or received flag';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.orgid
  is 'orgnisation id';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.created_by
  is 'user id who created the record';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.lang_id
  is 'language identity';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.created_date
  is 'record creation date';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.updated_by
  is 'user id who update the data';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.updated_date
  is 'date on which data is going to update';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_CONTRACT_INSTALMENT_DETAIL.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';
alter table TB_CONTRACT_INSTALMENT_DETAIL
  add constraint PK_CONIT_ID primary key (CONIT_ID);
alter table TB_CONTRACT_INSTALMENT_DETAIL
  add constraint FK_INSTALL_CONT_ID foreign key (CONT_ID)
  references TB_CONTRACT_MAST (CONT_ID);

prompt
prompt Creating table TB_CONTRACT_PART1_DETAIL
prompt =======================================
prompt
create table TB_CONTRACT_PART1_DETAIL
(
  contp1_id                   NUMBER(12) not null,
  cont_id                     NUMBER(12) not null,
  dp_deptid                   NUMBER(12),
  dsgid                       NUMBER(12),
  empid                       NUMBER(12),
  contp1_name                 NVARCHAR2(200),
  contp1_address              NVARCHAR2(500),
  contp1_proof_id_no          NVARCHAR2(50),
  contp1_parent_id            NUMBER(12),
  contp1_type                 CHAR(1) not null,
  contp1_active               CHAR(1) not null,
  orgid                       NUMBER(12) not null,
  created_by                  NUMBER(12) not null,
  lang_id                     NUMBER(7) not null,
  created_date                DATE not null,
  updated_by                  NUMBER(12),
  updated_date                DATE,
  lg_ip_mac                   VARCHAR2(100) not null,
  lg_ip_mac_upd               VARCHAR2(100),
  contp1_photo_file_name      VARCHAR2(200),
  contp1_photo_file_path_name VARCHAR2(500),
  contp1_thumb_file_name      VARCHAR2(200),
  contp1_thumb_file_path_name VARCHAR2(500)
)
;
comment on table TB_CONTRACT_PART1_DETAIL
  is 'table used to store contract ulb & witness details';
comment on column TB_CONTRACT_PART1_DETAIL.contp1_id
  is 'primary key';
comment on column TB_CONTRACT_PART1_DETAIL.cont_id
  is 'Foregin Key (TB_CONTRACT_MAST)';
comment on column TB_CONTRACT_PART1_DETAIL.dp_deptid
  is 'Foregin Key(TB_DEPARTMENT)';
comment on column TB_CONTRACT_PART1_DETAIL.dsgid
  is 'Foregin Key(Designation)';
comment on column TB_CONTRACT_PART1_DETAIL.empid
  is 'Foregin Key(Employee)';
comment on column TB_CONTRACT_PART1_DETAIL.contp1_name
  is 'Representer Name/Witness Name';
comment on column TB_CONTRACT_PART1_DETAIL.contp1_address
  is 'Witness Address';
comment on column TB_CONTRACT_PART1_DETAIL.contp1_proof_id_no
  is 'Proof Id No.';
comment on column TB_CONTRACT_PART1_DETAIL.contp1_parent_id
  is 'Party1 Parent id for type Witness';
comment on column TB_CONTRACT_PART1_DETAIL.contp1_type
  is 'W->Witness , U->Ulb';
comment on column TB_CONTRACT_PART1_DETAIL.contp1_active
  is 'flag to identify whether the record is deleted or not. ''y'' for deleted (inactive) and ''n'' for not deleted (active) record.';
comment on column TB_CONTRACT_PART1_DETAIL.orgid
  is 'orgnisation id';
comment on column TB_CONTRACT_PART1_DETAIL.created_by
  is 'user id who created the record';
comment on column TB_CONTRACT_PART1_DETAIL.lang_id
  is 'language identity';
comment on column TB_CONTRACT_PART1_DETAIL.created_date
  is 'record creation date';
comment on column TB_CONTRACT_PART1_DETAIL.updated_by
  is 'user id who update the data';
comment on column TB_CONTRACT_PART1_DETAIL.updated_date
  is 'date on which data is going to update';
comment on column TB_CONTRACT_PART1_DETAIL.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_CONTRACT_PART1_DETAIL.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';
comment on column TB_CONTRACT_PART1_DETAIL.contp1_photo_file_name
  is 'Photo Image File Name';
comment on column TB_CONTRACT_PART1_DETAIL.contp1_photo_file_path_name
  is 'Photo Image File Path Name';
comment on column TB_CONTRACT_PART1_DETAIL.contp1_thumb_file_name
  is 'Thumb Image File Name';
comment on column TB_CONTRACT_PART1_DETAIL.contp1_thumb_file_path_name
  is 'Thumb Image File Path Name';
alter table TB_CONTRACT_PART1_DETAIL
  add constraint PK_CONTP1_ID primary key (CONTP1_ID);
alter table TB_CONTRACT_PART1_DETAIL
  add constraint FK_DEPT_ID foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);
alter table TB_CONTRACT_PART1_DETAIL
  add constraint FK_DSG_ID foreign key (DSGID)
  references DESIGNATION (DSGID);
alter table TB_CONTRACT_PART1_DETAIL
  add constraint FK_EMP_ID foreign key (EMPID)
  references EMPLOYEE (EMPID);
alter table TB_CONTRACT_PART1_DETAIL
  add constraint FK_P1_CONT_ID foreign key (CONT_ID)
  references TB_CONTRACT_MAST (CONT_ID);

prompt
prompt Creating table TB_CONTRACT_PART2_DETAIL
prompt =======================================
prompt
create table TB_CONTRACT_PART2_DETAIL
(
  contp2_id                   NUMBER(12) not null,
  cont_id                     NUMBER(12) not null,
  contp2v_type                NUMBER(12),
  vm_vendorid                 NUMBER(12),
  contp2_name                 NVARCHAR2(200),
  contp2_address              NVARCHAR2(500),
  contp2_proof_id_no          NVARCHAR2(50),
  contv_active                CHAR(1) not null,
  contp2_parent_id            NUMBER(12),
  contp2_type                 CHAR(1) not null,
  orgid                       NUMBER(12) not null,
  created_by                  NUMBER(12) not null,
  lang_id                     NUMBER(7) not null,
  created_date                DATE,
  updated_by                  NUMBER(12),
  updated_date                DATE,
  lg_ip_mac                   VARCHAR2(100) not null,
  lg_ip_mac_upd               VARCHAR2(100),
  contp2_primary              VARCHAR2(1) default 'N',
  contp2_photo_file_name      VARCHAR2(200),
  contp2_photo_file_path_name VARCHAR2(500),
  contp2_thumb_file_name      VARCHAR2(200),
  contp2_thumb_file_path_name VARCHAR2(500)
)
;
comment on table TB_CONTRACT_PART2_DETAIL
  is 'table use for storing details contracted Vendor & Witness Details';
comment on column TB_CONTRACT_PART2_DETAIL.contp2_id
  is 'primary key';
comment on column TB_CONTRACT_PART2_DETAIL.cont_id
  is 'Foregin Key (TB_CONTRACT_MAST)';
comment on column TB_CONTRACT_PART2_DETAIL.contp2v_type
  is '(Value from Prefix ''VNT'')';
comment on column TB_CONTRACT_PART2_DETAIL.vm_vendorid
  is 'Foregin Key(TB_VENDORMASTER)';
comment on column TB_CONTRACT_PART2_DETAIL.contp2_name
  is 'Representer Name/Witness Name';
comment on column TB_CONTRACT_PART2_DETAIL.contp2_address
  is 'Witness Address';
comment on column TB_CONTRACT_PART2_DETAIL.contp2_proof_id_no
  is 'Proof Id No.';
comment on column TB_CONTRACT_PART2_DETAIL.contv_active
  is 'flag to identify whether the record is deleted or not. ''y'' for deleted (inactive) and ''n'' for not deleted (active) record.';
comment on column TB_CONTRACT_PART2_DETAIL.contp2_parent_id
  is 'Party2 Parent id for type Witness';
comment on column TB_CONTRACT_PART2_DETAIL.contp2_type
  is 'W->Witness , V-> Vendor';
comment on column TB_CONTRACT_PART2_DETAIL.orgid
  is 'orgnisation id';
comment on column TB_CONTRACT_PART2_DETAIL.created_by
  is 'user id who created the record';
comment on column TB_CONTRACT_PART2_DETAIL.lang_id
  is 'language identity';
comment on column TB_CONTRACT_PART2_DETAIL.created_date
  is 'record creation date';
comment on column TB_CONTRACT_PART2_DETAIL.updated_by
  is 'user id who update the data';
comment on column TB_CONTRACT_PART2_DETAIL.updated_date
  is 'date on which data is going to update';
comment on column TB_CONTRACT_PART2_DETAIL.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_CONTRACT_PART2_DETAIL.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';
comment on column TB_CONTRACT_PART2_DETAIL.contp2_primary
  is 'Primary Vendor (''Y''->YES)';
comment on column TB_CONTRACT_PART2_DETAIL.contp2_photo_file_name
  is 'Photo Image File Name';
comment on column TB_CONTRACT_PART2_DETAIL.contp2_photo_file_path_name
  is 'Photo Image File Path Name';
comment on column TB_CONTRACT_PART2_DETAIL.contp2_thumb_file_name
  is 'Thumb Image File Name';
comment on column TB_CONTRACT_PART2_DETAIL.contp2_thumb_file_path_name
  is 'Thumb Image File Path Name';
alter table TB_CONTRACT_PART2_DETAIL
  add constraint PK_CONTP2_ID primary key (CONTP2_ID);
alter table TB_CONTRACT_PART2_DETAIL
  add constraint FK_P2_CONT_ID foreign key (CONT_ID)
  references TB_CONTRACT_MAST (CONT_ID);
alter table TB_CONTRACT_PART2_DETAIL
  add constraint FK_VENDER_ID foreign key (VM_VENDORID)
  references TB_VENDORMASTER (VM_VENDORID);

prompt
prompt Creating table TB_CONTRACT_TERMS_DETAIL
prompt =======================================
prompt
create table TB_CONTRACT_TERMS_DETAIL
(
  contt_id          NUMBER(12) not null,
  cont_id           NUMBER(12) not null,
  contt_description NVARCHAR2(200) not null,
  contt_active      CHAR(1) not null,
  orgid             NUMBER(12) not null,
  created_by        NUMBER(12) not null,
  lang_id           NUMBER(7) not null,
  created_date      DATE not null,
  updated_by        NUMBER(12),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100) not null,
  lg_ip_mac_upd     VARCHAR2(100)
)
;
comment on column TB_CONTRACT_TERMS_DETAIL.contt_id
  is 'primary key';
comment on column TB_CONTRACT_TERMS_DETAIL.cont_id
  is 'Foregin key(TB_CONTRACT_MAS)';
comment on column TB_CONTRACT_TERMS_DETAIL.contt_description
  is 'Term Description';
comment on column TB_CONTRACT_TERMS_DETAIL.contt_active
  is 'flag to identify whether the record is deleted or not. ''y'' for deleted (inactive) and ''n'' for not deleted (active) record.';
comment on column TB_CONTRACT_TERMS_DETAIL.orgid
  is 'orgnisation id';
comment on column TB_CONTRACT_TERMS_DETAIL.created_by
  is 'user id who created the record
';
comment on column TB_CONTRACT_TERMS_DETAIL.lang_id
  is 'language identity';
comment on column TB_CONTRACT_TERMS_DETAIL.created_date
  is 'record creation date
';
comment on column TB_CONTRACT_TERMS_DETAIL.updated_by
  is 'user id who update the data
';
comment on column TB_CONTRACT_TERMS_DETAIL.updated_date
  is 'date on which data is going to update
';
comment on column TB_CONTRACT_TERMS_DETAIL.lg_ip_mac
  is 'client machine?s login name | ip address | physical address
';
comment on column TB_CONTRACT_TERMS_DETAIL.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address
';
alter table TB_CONTRACT_TERMS_DETAIL
  add constraint PK_CONTT_ID primary key (CONTT_ID);
alter table TB_CONTRACT_TERMS_DETAIL
  add constraint FK_TERM_CONT_ID foreign key (CONT_ID)
  references TB_CONTRACT_MAST (CONT_ID);

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
prompt Creating table TB_DOCUMENT_GRUOP
prompt ================================
prompt
create table TB_DOCUMENT_GRUOP
(
  dg_id         NUMBER(12) not null,
  group_cpd_id  NUMBER(12),
  doc_name      NVARCHAR2(100),
  doc_type      NVARCHAR2(20),
  doc_size      NUMBER(5),
  orgid         NUMBER(4) not null,
  created_by    NUMBER(7) not null,
  created_date  DATE not null,
  lang_id       NUMBER(7) not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  clm_sr_no     NUMBER(3),
  clm_status    NVARCHAR2(1)
)
;
comment on column TB_DOCUMENT_GRUOP.dg_id
  is 'Primary Key';
comment on column TB_DOCUMENT_GRUOP.group_cpd_id
  is 'Reference key  --TB_COMPARAM_DET';
comment on column TB_DOCUMENT_GRUOP.doc_name
  is 'Document Name';
comment on column TB_DOCUMENT_GRUOP.doc_type
  is 'Document Type';
comment on column TB_DOCUMENT_GRUOP.doc_size
  is 'Document Size';
comment on column TB_DOCUMENT_GRUOP.orgid
  is 'Organization Id';
comment on column TB_DOCUMENT_GRUOP.created_by
  is 'User Identity';
comment on column TB_DOCUMENT_GRUOP.created_date
  is 'Created Date';
comment on column TB_DOCUMENT_GRUOP.lang_id
  is 'Language Identity';
comment on column TB_DOCUMENT_GRUOP.updated_by
  is 'User id who update the data';
comment on column TB_DOCUMENT_GRUOP.updated_date
  is 'Date on which data is going to update';
comment on column TB_DOCUMENT_GRUOP.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_DOCUMENT_GRUOP.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
alter table TB_DOCUMENT_GRUOP
  add constraint PK_DOCUMENT_GRUOP primary key (DG_ID);
alter table TB_DOCUMENT_GRUOP
  add constraint FK_CPD_GROUP_ID foreign key (GROUP_CPD_ID)
  references TB_COMPARAM_DET (CPD_ID);

prompt
prompt Creating table TB_FINANCIALYEAR
prompt ===============================
prompt
create table TB_FINANCIALYEAR
(
  fa_yearid    NUMBER(12) not null,
  fa_fromdate  DATE,
  fa_todate    DATE,
  created_by   NUMBER(7) not null,
  lang_id      NUMBER(7) not null,
  created_date DATE not null,
  updated_by   NUMBER(7),
  updated_date DATE
)
;
comment on column TB_FINANCIALYEAR.fa_yearid
  is 'Primary Key';
comment on column TB_FINANCIALYEAR.fa_fromdate
  is 'Start date of the financial year';
comment on column TB_FINANCIALYEAR.fa_todate
  is 'End date of the financial year';
comment on column TB_FINANCIALYEAR.created_by
  is 'User Identity';
comment on column TB_FINANCIALYEAR.lang_id
  is 'Language Identity';
comment on column TB_FINANCIALYEAR.created_date
  is 'Last Modification Date';
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
  created_by     NUMBER(7) not null,
  lang_id        NUMBER(7) not null,
  created_date   DATE not null,
  fa_provclosing NVARCHAR2(1) default 'n' not null,
  updated_by     NUMBER(7),
  updated_date   DATE,
  h_status       NVARCHAR2(1)
)
;
alter table TB_FINANCIALYEAR_HIST
  add constraint PK_H_FAYEARID primary key (H_FAYEARID);

prompt
prompt Creating table TB_FINCIALYEARORG_MAP
prompt ====================================
prompt
create table TB_FINCIALYEARORG_MAP
(
  map_id         NUMBER(12) not null,
  fa_yearid      NUMBER(12) not null,
  orgid          NUMBER(4) not null,
  ya_type_cpd_id NUMBER(12),
  map_status     CHAR(1),
  remark         NVARCHAR2(300),
  created_by     NUMBER(7) not null,
  lang_id        NUMBER(7) not null,
  created_date   DATE not null,
  updated_by     NUMBER(7),
  updated_date   DATE,
  com_v1         NVARCHAR2(100),
  com_v2         NVARCHAR2(100),
  com_v3         NVARCHAR2(100),
  com_v4         NVARCHAR2(100),
  com_v5         NVARCHAR2(100),
  com_n1         NUMBER(15),
  com_n2         NUMBER(15),
  com_n3         NUMBER(15),
  com_n4         NUMBER(15),
  com_n5         NUMBER(15),
  com_d1         DATE,
  com_d2         DATE,
  com_d3         DATE,
  com_lo1        CHAR(1),
  com_lo2        CHAR(1),
  com_lo3        CHAR(1)
)
;
comment on table TB_FINCIALYEARORG_MAP
  is 'COMMON TABLE USED TO CREATE PARENT CHILD RELATIONSHIP. THIS IS A CHILD TABLE';
comment on column TB_FINCIALYEARORG_MAP.orgid
  is 'ORGANISATION ID';
comment on column TB_FINCIALYEARORG_MAP.map_status
  is 'ORGNIZATION STATUS IS ACTIVE OR INACTIVE';
comment on column TB_FINCIALYEARORG_MAP.created_by
  is 'USER ID';
comment on column TB_FINCIALYEARORG_MAP.lang_id
  is 'LANGUAGE ID';
comment on column TB_FINCIALYEARORG_MAP.created_date
  is 'CREATION DATE';
comment on column TB_FINCIALYEARORG_MAP.updated_by
  is 'UPDATED BY USER ID';
comment on column TB_FINCIALYEARORG_MAP.updated_date
  is 'UPDATION DATE';
alter table TB_FINCIALYEARORG_MAP
  add constraint PK_FINMAPID primary key (MAP_ID);
alter table TB_FINCIALYEARORG_MAP
  add constraint FK_YEARID foreign key (FA_YEARID)
  references TB_FINANCIALYEAR (FA_YEARID);

prompt
prompt Creating table TB_FINCIALYEARORG_MAP_HIST
prompt =========================================
prompt
create table TB_FINCIALYEARORG_MAP_HIST
(
  h_map_id       NUMBER(12) not null,
  map_id         NUMBER(12) not null,
  fa_yearid      NUMBER(12) not null,
  orgid          NUMBER(4) not null,
  ya_type_cpd_id NUMBER(12),
  map_status     CHAR(1),
  remark         NVARCHAR2(300),
  created_by     NUMBER(7) not null,
  lang_id        NUMBER(7) not null,
  created_date   DATE not null,
  updated_by     NUMBER(7),
  updated_date   DATE,
  com_v1         NVARCHAR2(100),
  com_v2         NVARCHAR2(100),
  com_v3         NVARCHAR2(100),
  com_v4         NVARCHAR2(100),
  com_v5         NVARCHAR2(100),
  com_n1         NUMBER(15),
  com_n2         NUMBER(15),
  com_n3         NUMBER(15),
  com_n4         NUMBER(15),
  com_n5         NUMBER(15),
  com_d1         DATE,
  com_d2         DATE,
  com_d3         DATE,
  com_lo1        CHAR(1),
  com_lo2        CHAR(1),
  com_lo3        CHAR(1),
  h_status       NVARCHAR2(1)
)
;
alter table TB_FINCIALYEARORG_MAP_HIST
  add constraint PK_H_MAP_ID primary key (H_MAP_ID);

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
  sq_str_with   NUMBER(20),
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
prompt Creating table TB_LOCATION_ADMIN_WARDZONE
prompt =========================================
prompt
create table TB_LOCATION_ADMIN_WARDZONE
(
  locwzmp_id          NUMBER(12) not null,
  orgid               NUMBER(4) not null,
  loc_id              NUMBER(12),
  cod_id_admin_level1 NUMBER(12),
  cod_id_admin_level2 NUMBER(12),
  cod_id_admin_level3 NUMBER(12),
  cod_id_admin_level4 NUMBER(12),
  cod_id_admin_level5 NUMBER(12),
  user_id             NUMBER(7) not null,
  lang_id             NUMBER(7) not null,
  lmoddate            DATE,
  updated_by          NUMBER(7),
  updated_date        DATE,
  lg_ip_mac           VARCHAR2(100),
  lg_ip_mac_upd       VARCHAR2(100),
  fi04_n1             NUMBER(15),
  fi04_v1             NVARCHAR2(100),
  fi04_d1             DATE,
  fi04_lo1            CHAR(1)
)
;
comment on table TB_LOCATION_ADMIN_WARDZONE
  is 'This table stores mapping Entry for Location and Administrative Ward Zone ';
comment on column TB_LOCATION_ADMIN_WARDZONE.locwzmp_id
  is 'Primary Key';
comment on column TB_LOCATION_ADMIN_WARDZONE.orgid
  is 'Organisation id';
comment on column TB_LOCATION_ADMIN_WARDZONE.loc_id
  is 'fk - tb_location_mas';
comment on column TB_LOCATION_ADMIN_WARDZONE.cod_id_admin_level1
  is 'Administrative ward level1';
comment on column TB_LOCATION_ADMIN_WARDZONE.cod_id_admin_level2
  is 'Administrative ward level2';
comment on column TB_LOCATION_ADMIN_WARDZONE.cod_id_admin_level3
  is 'Administrative ward level3';
comment on column TB_LOCATION_ADMIN_WARDZONE.cod_id_admin_level4
  is 'Administrative ward level4';
comment on column TB_LOCATION_ADMIN_WARDZONE.cod_id_admin_level5
  is 'Administrative ward level5';
comment on column TB_LOCATION_ADMIN_WARDZONE.user_id
  is 'User Identity';
comment on column TB_LOCATION_ADMIN_WARDZONE.lang_id
  is 'Language Identity';
comment on column TB_LOCATION_ADMIN_WARDZONE.lmoddate
  is 'Last Modification Date';
comment on column TB_LOCATION_ADMIN_WARDZONE.updated_by
  is 'User id who update the data';
comment on column TB_LOCATION_ADMIN_WARDZONE.updated_date
  is 'Date on which data is going to update';
comment on column TB_LOCATION_ADMIN_WARDZONE.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_LOCATION_ADMIN_WARDZONE.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_LOCATION_ADMIN_WARDZONE.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_LOCATION_ADMIN_WARDZONE.fi04_v1
  is 'Additional number FI04_V1 to be used in future';
comment on column TB_LOCATION_ADMIN_WARDZONE.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_LOCATION_ADMIN_WARDZONE.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
alter table TB_LOCATION_ADMIN_WARDZONE
  add constraint PK_LOCWZMP_ID primary key (LOCWZMP_ID);
alter table TB_LOCATION_ADMIN_WARDZONE
  add constraint FK_COD_ID_ADMIN_LEVEL1 foreign key (COD_ID_ADMIN_LEVEL1)
  references TB_COMPARENT_DET (COD_ID);
alter table TB_LOCATION_ADMIN_WARDZONE
  add constraint FK_COD_ID_ADMIN_LEVEL2 foreign key (COD_ID_ADMIN_LEVEL2)
  references TB_COMPARENT_DET (COD_ID);
alter table TB_LOCATION_ADMIN_WARDZONE
  add constraint FK_COD_ID_ADMIN_LEVEL3 foreign key (COD_ID_ADMIN_LEVEL3)
  references TB_COMPARENT_DET (COD_ID);
alter table TB_LOCATION_ADMIN_WARDZONE
  add constraint FK_COD_ID_ADMIN_LEVEL4 foreign key (COD_ID_ADMIN_LEVEL4)
  references TB_COMPARENT_DET (COD_ID);
alter table TB_LOCATION_ADMIN_WARDZONE
  add constraint FK_COD_ID_ADMIN_LEVEL5 foreign key (COD_ID_ADMIN_LEVEL5)
  references TB_COMPARENT_DET (COD_ID);
alter table TB_LOCATION_ADMIN_WARDZONE
  add constraint FK_LOCATION_ADMINWZMP foreign key (ORGID)
  references TB_ORGANISATION (ORGID);
alter table TB_LOCATION_ADMIN_WARDZONE
  add constraint FK_LOCATION_LOC_ID foreign key (LOC_ID)
  references TB_LOCATION_MAS (LOC_ID);

prompt
prompt Creating table TB_LOCATION_ELECT_WARDZONE
prompt =========================================
prompt
create table TB_LOCATION_ELECT_WARDZONE
(
  locewzmp_id        NUMBER(12) not null,
  orgid              NUMBER(4) not null,
  loc_id             NUMBER(12),
  cod_id_elec_level1 NUMBER(12),
  cod_id_elec_level2 NUMBER(12),
  cod_id_elec_level3 NUMBER(12),
  cod_id_elec_level4 NUMBER(12),
  cod_id_elec_level5 NUMBER(12),
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(7) not null,
  lmoddate           DATE,
  updated_by         NUMBER(7),
  updated_date       DATE,
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  fi04_n1            NUMBER(15),
  fi04_v1            NVARCHAR2(100),
  fi04_d1            DATE,
  fi04_lo1           CHAR(1)
)
;
comment on table TB_LOCATION_ELECT_WARDZONE
  is 'This table stores mapping Entry for Location and Electoral Ward Zone ';
comment on column TB_LOCATION_ELECT_WARDZONE.locewzmp_id
  is 'Primary Key';
comment on column TB_LOCATION_ELECT_WARDZONE.orgid
  is 'Organisation id';
comment on column TB_LOCATION_ELECT_WARDZONE.loc_id
  is 'fk - tb_location_mas';
comment on column TB_LOCATION_ELECT_WARDZONE.cod_id_elec_level1
  is 'Administrative ward level1';
comment on column TB_LOCATION_ELECT_WARDZONE.cod_id_elec_level2
  is 'Administrative ward level2';
comment on column TB_LOCATION_ELECT_WARDZONE.cod_id_elec_level3
  is 'Administrative ward level3';
comment on column TB_LOCATION_ELECT_WARDZONE.cod_id_elec_level4
  is 'Administrative ward level4';
comment on column TB_LOCATION_ELECT_WARDZONE.cod_id_elec_level5
  is 'Administrative ward level5';
comment on column TB_LOCATION_ELECT_WARDZONE.user_id
  is 'User Identity';
comment on column TB_LOCATION_ELECT_WARDZONE.lang_id
  is 'Language Identity';
comment on column TB_LOCATION_ELECT_WARDZONE.lmoddate
  is 'Last Modification Date';
comment on column TB_LOCATION_ELECT_WARDZONE.updated_by
  is 'User id who update the data';
comment on column TB_LOCATION_ELECT_WARDZONE.updated_date
  is 'Date on which data is going to update';
comment on column TB_LOCATION_ELECT_WARDZONE.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_LOCATION_ELECT_WARDZONE.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_LOCATION_ELECT_WARDZONE.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_LOCATION_ELECT_WARDZONE.fi04_v1
  is 'Additional number FI04_V1 to be used in future';
comment on column TB_LOCATION_ELECT_WARDZONE.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_LOCATION_ELECT_WARDZONE.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
alter table TB_LOCATION_ELECT_WARDZONE
  add constraint PK_LOCEWZMP_ID primary key (LOCEWZMP_ID);
alter table TB_LOCATION_ELECT_WARDZONE
  add constraint FK_COD_ID_ELEC_LEVEL1 foreign key (COD_ID_ELEC_LEVEL1)
  references TB_COMPARENT_DET (COD_ID);
alter table TB_LOCATION_ELECT_WARDZONE
  add constraint FK_COD_ID_ELEC_LEVEL2 foreign key (COD_ID_ELEC_LEVEL2)
  references TB_COMPARENT_DET (COD_ID);
alter table TB_LOCATION_ELECT_WARDZONE
  add constraint FK_COD_ID_ELEC_LEVEL3 foreign key (COD_ID_ELEC_LEVEL3)
  references TB_COMPARENT_DET (COD_ID);
alter table TB_LOCATION_ELECT_WARDZONE
  add constraint FK_COD_ID_ELEC_LEVEL4 foreign key (COD_ID_ELEC_LEVEL4)
  references TB_COMPARENT_DET (COD_ID);
alter table TB_LOCATION_ELECT_WARDZONE
  add constraint FK_COD_ID_ELEC_LEVEL5 foreign key (COD_ID_ELEC_LEVEL5)
  references TB_COMPARENT_DET (COD_ID);
alter table TB_LOCATION_ELECT_WARDZONE
  add constraint FK_ELEC_LOC_ID foreign key (LOC_ID)
  references TB_LOCATION_MAS (LOC_ID)
  disable;
alter table TB_LOCATION_ELECT_WARDZONE
  add constraint FK_LOCATION_ELECWZMP foreign key (ORGID)
  references TB_ORGANISATION (ORGID);

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
prompt Creating table TB_LOCATION_OPER_WARDZONE
prompt ========================================
prompt
create table TB_LOCATION_OPER_WARDZONE
(
  locowzmp_id        NUMBER(12) not null,
  orgid              NUMBER(4) not null,
  loc_id             NUMBER(12),
  cod_id_oper_level1 NUMBER(12),
  cod_id_oper_level2 NUMBER(12),
  cod_id_oper_level3 NUMBER(12),
  cod_id_oper_level4 NUMBER(12),
  cod_id_oper_level5 NUMBER(12),
  dp_deptid          NUMBER(12),
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(7) not null,
  lmoddate           DATE,
  updated_by         NUMBER(7),
  updated_date       DATE,
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  fi04_n1            NUMBER(15),
  fi04_v1            NVARCHAR2(100),
  fi04_d1            DATE,
  fi04_lo1           CHAR(1)
)
;
comment on table TB_LOCATION_OPER_WARDZONE
  is 'This table stores mapping Entry for Location and Operational Ward Zone ';
comment on column TB_LOCATION_OPER_WARDZONE.locowzmp_id
  is 'Primary Key';
comment on column TB_LOCATION_OPER_WARDZONE.orgid
  is 'Organisation id';
comment on column TB_LOCATION_OPER_WARDZONE.loc_id
  is 'fk - tb_location_mas';
comment on column TB_LOCATION_OPER_WARDZONE.cod_id_oper_level1
  is 'Operational ward level1';
comment on column TB_LOCATION_OPER_WARDZONE.cod_id_oper_level2
  is 'Operational ward level2';
comment on column TB_LOCATION_OPER_WARDZONE.cod_id_oper_level3
  is 'Operational ward level3';
comment on column TB_LOCATION_OPER_WARDZONE.cod_id_oper_level4
  is 'Operational ward level4';
comment on column TB_LOCATION_OPER_WARDZONE.cod_id_oper_level5
  is 'Operational ward level5';
comment on column TB_LOCATION_OPER_WARDZONE.dp_deptid
  is 'Department Id';
comment on column TB_LOCATION_OPER_WARDZONE.user_id
  is 'User Identity';
comment on column TB_LOCATION_OPER_WARDZONE.lang_id
  is 'Language Identity';
comment on column TB_LOCATION_OPER_WARDZONE.lmoddate
  is 'Last Modification Date';
comment on column TB_LOCATION_OPER_WARDZONE.updated_by
  is 'User id who update the data';
comment on column TB_LOCATION_OPER_WARDZONE.updated_date
  is 'Date on which data is going to update';
comment on column TB_LOCATION_OPER_WARDZONE.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_LOCATION_OPER_WARDZONE.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_LOCATION_OPER_WARDZONE.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_LOCATION_OPER_WARDZONE.fi04_v1
  is 'Additional number FI04_V1 to be used in future';
comment on column TB_LOCATION_OPER_WARDZONE.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_LOCATION_OPER_WARDZONE.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
alter table TB_LOCATION_OPER_WARDZONE
  add constraint PK_LOCOPERWZMP_ID primary key (LOCOWZMP_ID);
alter table TB_LOCATION_OPER_WARDZONE
  add constraint FK_COD_ID_OPER_LEVEL1 foreign key (COD_ID_OPER_LEVEL1)
  references TB_COMPARENT_DET (COD_ID);
alter table TB_LOCATION_OPER_WARDZONE
  add constraint FK_COD_ID_OPER_LEVEL2 foreign key (COD_ID_OPER_LEVEL2)
  references TB_COMPARENT_DET (COD_ID);
alter table TB_LOCATION_OPER_WARDZONE
  add constraint FK_COD_ID_OPER_LEVEL3 foreign key (COD_ID_OPER_LEVEL3)
  references TB_COMPARENT_DET (COD_ID);
alter table TB_LOCATION_OPER_WARDZONE
  add constraint FK_COD_ID_OPER_LEVEL4 foreign key (COD_ID_OPER_LEVEL4)
  references TB_COMPARENT_DET (COD_ID);
alter table TB_LOCATION_OPER_WARDZONE
  add constraint FK_COD_ID_OPER_LEVEL5 foreign key (COD_ID_OPER_LEVEL5)
  references TB_COMPARENT_DET (COD_ID);
alter table TB_LOCATION_OPER_WARDZONE
  add constraint FK_LOCATION_OPERWZMP foreign key (ORGID)
  references TB_ORGANISATION (ORGID);
alter table TB_LOCATION_OPER_WARDZONE
  add constraint FK_OPERLOCATION_DP_DEPTID foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);
alter table TB_LOCATION_OPER_WARDZONE
  add constraint FK_OPERLOCATION_LOC_ID foreign key (LOC_ID)
  references TB_LOCATION_MAS (LOC_ID);

prompt
prompt Creating table TB_LOCATION_REVENUE_WARDZONE
prompt ===========================================
prompt
create table TB_LOCATION_REVENUE_WARDZONE
(
  locrwzmp_id       NUMBER(12) not null,
  orgid             NUMBER(4) not null,
  loc_id            NUMBER(12),
  cod_id_rev_level1 NUMBER(12),
  cod_id_rev_level2 NUMBER(12),
  cod_id_rev_level3 NUMBER(12),
  cod_id_rev_level4 NUMBER(12),
  cod_id_rev_level5 NUMBER(12),
  user_id           NUMBER(7) not null,
  lang_id           NUMBER(7) not null,
  lmoddate          DATE,
  updated_by        NUMBER(7),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100),
  fi04_n1           NUMBER(15),
  fi04_v1           NVARCHAR2(100),
  fi04_d1           DATE,
  fi04_lo1          CHAR(1)
)
;
comment on table TB_LOCATION_REVENUE_WARDZONE
  is 'This table stores mapping Entry for Location and Revenue Ward Zone ';
comment on column TB_LOCATION_REVENUE_WARDZONE.locrwzmp_id
  is 'Primary Key';
comment on column TB_LOCATION_REVENUE_WARDZONE.orgid
  is 'Organisation id';
comment on column TB_LOCATION_REVENUE_WARDZONE.loc_id
  is 'fk - tb_location_mas';
comment on column TB_LOCATION_REVENUE_WARDZONE.cod_id_rev_level1
  is 'Administrative ward level1';
comment on column TB_LOCATION_REVENUE_WARDZONE.cod_id_rev_level2
  is 'Administrative ward level2';
comment on column TB_LOCATION_REVENUE_WARDZONE.cod_id_rev_level3
  is 'Administrative ward level3';
comment on column TB_LOCATION_REVENUE_WARDZONE.cod_id_rev_level4
  is 'Administrative ward level4';
comment on column TB_LOCATION_REVENUE_WARDZONE.cod_id_rev_level5
  is 'Administrative ward level5';
comment on column TB_LOCATION_REVENUE_WARDZONE.user_id
  is 'User Identity';
comment on column TB_LOCATION_REVENUE_WARDZONE.lang_id
  is 'Language Identity';
comment on column TB_LOCATION_REVENUE_WARDZONE.lmoddate
  is 'Last Modification Date';
comment on column TB_LOCATION_REVENUE_WARDZONE.updated_by
  is 'User id who update the data';
comment on column TB_LOCATION_REVENUE_WARDZONE.updated_date
  is 'Date on which data is going to update';
comment on column TB_LOCATION_REVENUE_WARDZONE.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_LOCATION_REVENUE_WARDZONE.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_LOCATION_REVENUE_WARDZONE.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_LOCATION_REVENUE_WARDZONE.fi04_v1
  is 'Additional number FI04_V1 to be used in future';
comment on column TB_LOCATION_REVENUE_WARDZONE.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_LOCATION_REVENUE_WARDZONE.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
alter table TB_LOCATION_REVENUE_WARDZONE
  add constraint PK_LOCRWZMP_ID primary key (LOCRWZMP_ID);
alter table TB_LOCATION_REVENUE_WARDZONE
  add constraint FK_COD_ID_REV_LEVEL1 foreign key (COD_ID_REV_LEVEL1)
  references TB_COMPARENT_DET (COD_ID);
alter table TB_LOCATION_REVENUE_WARDZONE
  add constraint FK_COD_ID_REV_LEVEL2 foreign key (COD_ID_REV_LEVEL2)
  references TB_COMPARENT_DET (COD_ID);
alter table TB_LOCATION_REVENUE_WARDZONE
  add constraint FK_COD_ID_REV_LEVEL3 foreign key (COD_ID_REV_LEVEL3)
  references TB_COMPARENT_DET (COD_ID);
alter table TB_LOCATION_REVENUE_WARDZONE
  add constraint FK_COD_ID_REV_LEVEL4 foreign key (COD_ID_REV_LEVEL4)
  references TB_COMPARENT_DET (COD_ID);
alter table TB_LOCATION_REVENUE_WARDZONE
  add constraint FK_COD_ID_REV_LEVEL5 foreign key (COD_ID_REV_LEVEL5)
  references TB_COMPARENT_DET (COD_ID);
alter table TB_LOCATION_REVENUE_WARDZONE
  add constraint FK_LOCATION_REVWZMP foreign key (ORGID)
  references TB_ORGANISATION (ORGID);
alter table TB_LOCATION_REVENUE_WARDZONE
  add constraint FK_REV_LOC_ID foreign key (LOC_ID)
  references TB_LOCATION_MAS (LOC_ID);

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
prompt Creating table TB_ORG_DESIGNATION_HIST
prompt ======================================
prompt
create table TB_ORG_DESIGNATION_HIST
(
  h_map_id      NUMBER(12) not null,
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
  com_lo3       CHAR(1),
  h_status      NVARCHAR2(1)
)
;
alter table TB_ORG_DESIGNATION_HIST
  add constraint PK_HIST_MAP_ID primary key (H_MAP_ID);

prompt
prompt Creating table TB_PG_BANK
prompt =========================
prompt
create table TB_PG_BANK
(
  pg_id         NUMBER(15) not null,
  merchant_id   NVARCHAR2(20),
  pg_name       NVARCHAR2(200),
  pg_url        NVARCHAR2(300),
  pg_status     CHAR(1),
  bankid        NUMBER(12),
  ba_accountid  NUMBER(12),
  orgid         NUMBER(4) not null,
  created_by    NUMBER(7),
  created_date  DATE,
  lang_id       NUMBER(7),
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  comm_n1       NUMBER(15),
  comm_v1       NVARCHAR2(100),
  comm_d1       DATE,
  comm_lo1      CHAR(1)
)
;
comment on column TB_PG_BANK.pg_id
  is 'Primary key';
comment on column TB_PG_BANK.merchant_id
  is 'PG Merchant id';
comment on column TB_PG_BANK.pg_name
  is 'PG Name';
comment on column TB_PG_BANK.pg_url
  is 'PG URL';
comment on column TB_PG_BANK.pg_status
  is 'Status A-Active, I - Inactive';
comment on column TB_PG_BANK.bankid
  is 'Ref key - tb_bank_master (PG bank ref)';
comment on column TB_PG_BANK.ba_accountid
  is 'REf key  -TB_BANK_ACCOUNT (ULB  Bank Id)';
comment on column TB_PG_BANK.orgid
  is 'Organization Id';
comment on column TB_PG_BANK.created_by
  is 'User Identity';
comment on column TB_PG_BANK.created_date
  is 'Created Date';
comment on column TB_PG_BANK.lang_id
  is 'Language Identity';
comment on column TB_PG_BANK.updated_by
  is 'updated by';
comment on column TB_PG_BANK.updated_date
  is 'updated Date';
comment on column TB_PG_BANK.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_PG_BANK.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_PG_BANK.comm_n1
  is 'Additional number COMM_N1 to be used in future';
comment on column TB_PG_BANK.comm_v1
  is 'Additional nvarchar2 COMM_V1 to be used in future';
comment on column TB_PG_BANK.comm_d1
  is 'Additional Date COMM_D1 to be used in future';
comment on column TB_PG_BANK.comm_lo1
  is 'Additional Logical field COMM_LO1 to be used in future';
alter table TB_PG_BANK
  add constraint PK_PG_ID primary key (PG_ID);
alter table TB_PG_BANK
  add constraint FK_BANKID_PG foreign key (BANKID)
  references TB_BANK_MASTER (BANKID);
alter table TB_PG_BANK
  add constraint FK_PG_BA_ACCOUNTID foreign key (BA_ACCOUNTID)
  references TB_BANK_ACCOUNT (BA_ACCOUNTID);

prompt
prompt Creating table TB_PG_BANK_PARAMETER_DETAIL
prompt ==========================================
prompt
create table TB_PG_BANK_PARAMETER_DETAIL
(
  pg_pram_det_id NUMBER(15) not null,
  pg_id          NUMBER(15),
  par_name       NVARCHAR2(500),
  par_value      NVARCHAR2(500),
  par_status     CHAR(1),
  orgid          NUMBER(4) not null,
  created_by     NUMBER(7),
  created_date   DATE,
  lang_id        NUMBER(7),
  updated_by     NUMBER(7),
  updated_date   DATE,
  lg_ip_mac      VARCHAR2(100),
  lg_ip_mac_upd  VARCHAR2(100),
  comm_n1        NUMBER(15),
  comm_v1        NVARCHAR2(100),
  comm_d1        DATE,
  comm_lo1       CHAR(1)
)
;
comment on column TB_PG_BANK_PARAMETER_DETAIL.pg_pram_det_id
  is 'Primary key';
comment on column TB_PG_BANK_PARAMETER_DETAIL.pg_id
  is 'Ref fk -TB_PG_BANK';
comment on column TB_PG_BANK_PARAMETER_DETAIL.par_name
  is 'Parameter Name / Description';
comment on column TB_PG_BANK_PARAMETER_DETAIL.par_value
  is 'Parameter Value';
comment on column TB_PG_BANK_PARAMETER_DETAIL.par_status
  is 'Status A-Active,  I - Inactive';
comment on column TB_PG_BANK_PARAMETER_DETAIL.orgid
  is 'Organization Id';
comment on column TB_PG_BANK_PARAMETER_DETAIL.created_by
  is 'User Identity';
comment on column TB_PG_BANK_PARAMETER_DETAIL.created_date
  is 'Created Date';
comment on column TB_PG_BANK_PARAMETER_DETAIL.lang_id
  is 'Language Identity';
comment on column TB_PG_BANK_PARAMETER_DETAIL.updated_by
  is 'updated by';
comment on column TB_PG_BANK_PARAMETER_DETAIL.updated_date
  is 'updated Date';
comment on column TB_PG_BANK_PARAMETER_DETAIL.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_PG_BANK_PARAMETER_DETAIL.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_PG_BANK_PARAMETER_DETAIL.comm_n1
  is 'Additional number COMM_N1 to be used in future';
comment on column TB_PG_BANK_PARAMETER_DETAIL.comm_v1
  is 'Additional nvarchar2 COMM_V1 to be used in future';
comment on column TB_PG_BANK_PARAMETER_DETAIL.comm_d1
  is 'Additional Date COMM_D1 to be used in future';
comment on column TB_PG_BANK_PARAMETER_DETAIL.comm_lo1
  is 'Additional Logical field COMM_LO1 to be used in future';
alter table TB_PG_BANK_PARAMETER_DETAIL
  add constraint PK_PG_PRAM_DET_ID primary key (PG_PRAM_DET_ID);
alter table TB_PG_BANK_PARAMETER_DETAIL
  add constraint FK_PG_ID foreign key (PG_ID)
  references TB_PG_BANK (PG_ID);

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
  istransactional VARCHAR2(3),
  isdeleted       VARCHAR2(1) not null,
  orgid           NUMBER(4) not null,
  user_id         NUMBER(7) not null,
  lang_id         NUMBER(7) not null,
  created_date    DATE not null,
  updated_by      NUMBER(7),
  updated_date    DATE,
  lg_ip_mac       VARCHAR2(100),
  lg_ip_mac_upd   VARCHAR2(100)
)
;

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
  sms_body_reg  NVARCHAR2(2000)
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
prompt Creating table TB_RECEIPT_DET
prompt =============================
prompt
create table TB_RECEIPT_DET
(
  rf_feeid      NUMBER(12) not null,
  rm_rcptid     NUMBER(12),
  tax_id        NUMBER(12),
  rf_feeamount  NUMBER(12,2),
  orgid         NUMBER(4) not null,
  created_by    NUMBER(7),
  lang_id       NUMBER(7),
  created_date  DATE,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  rf_v1         NVARCHAR2(100),
  rf_v2         NVARCHAR2(100),
  rf_v3         NVARCHAR2(100),
  rf_v4         NVARCHAR2(100),
  rf_v5         NVARCHAR2(100),
  rf_n2         NUMBER(15),
  rf_n3         NUMBER(15),
  rf_n4         NUMBER(15),
  rf_n5         NUMBER(15),
  rf_d1         DATE,
  rf_d2         DATE,
  rf_d3         DATE,
  rf_lo1        CHAR(1),
  rf_lo2        CHAR(1),
  rf_lo3        CHAR(1),
  billdet_id    NUMBER(12),
  dps_slipid    NUMBER(12),
  budgetcode_id NUMBER(12)
)
;
comment on column TB_RECEIPT_DET.rf_feeid
  is 'Generated Id';
comment on column TB_RECEIPT_DET.rm_rcptid
  is 'Receipt id';
comment on column TB_RECEIPT_DET.tax_id
  is 'Tax id from tax master table';
comment on column TB_RECEIPT_DET.rf_feeamount
  is 'Fees amount';
comment on column TB_RECEIPT_DET.orgid
  is 'Org ID';
comment on column TB_RECEIPT_DET.created_by
  is 'User ID';
comment on column TB_RECEIPT_DET.lang_id
  is 'Lang ID';
comment on column TB_RECEIPT_DET.created_date
  is 'Last Modification Date';
comment on column TB_RECEIPT_DET.updated_by
  is 'User id who update the data';
comment on column TB_RECEIPT_DET.updated_date
  is 'Date on which data is going to update';
comment on column TB_RECEIPT_DET.lg_ip_mac
  is 'Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_RECEIPT_DET.lg_ip_mac_upd
  is 'Updated Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_RECEIPT_DET.rf_v1
  is 'Additional nvarchar2 RF_V1 to be used in future';
comment on column TB_RECEIPT_DET.rf_v2
  is 'Additional nvarchar2 RF_V2 to be used in future';
comment on column TB_RECEIPT_DET.rf_v3
  is 'Additional nvarchar2 RF_V3 to be used in future';
comment on column TB_RECEIPT_DET.rf_v4
  is 'Additional nvarchar2 RF_V4 to be used in future';
comment on column TB_RECEIPT_DET.rf_v5
  is 'Additional nvarchar2 RF_V5 to be used in future';
comment on column TB_RECEIPT_DET.rf_n2
  is 'Additional number RF_N2 to be used in future';
comment on column TB_RECEIPT_DET.rf_n3
  is 'Additional number RF_N3 to be used in future';
comment on column TB_RECEIPT_DET.rf_n4
  is 'Additional number RF_N4 to be used in future';
comment on column TB_RECEIPT_DET.rf_n5
  is 'Additional number RF_N5 to be used in future';
comment on column TB_RECEIPT_DET.rf_d1
  is 'Additional Date RF_D1 to be used in future';
comment on column TB_RECEIPT_DET.rf_d2
  is 'Additional Date RF_D2 to be used in future';
comment on column TB_RECEIPT_DET.rf_d3
  is 'Additional Date RF_D3 to be used in future';
comment on column TB_RECEIPT_DET.rf_lo1
  is 'Additional Logical field RF_LO1 to be used in future';
comment on column TB_RECEIPT_DET.rf_lo2
  is 'Additional Logical field RF_LO2 to be used in future';
comment on column TB_RECEIPT_DET.rf_lo3
  is 'Additional Logical field RF_LO3 to be used in future';
comment on column TB_RECEIPT_DET.billdet_id
  is 'Department  wise bill details id Water(BD_BILLDETID)/ Assessment ()';
comment on column TB_RECEIPT_DET.dps_slipid
  is 'Ref. key -TB_AC_BANK_DEPOSLIP_MASTER';
comment on column TB_RECEIPT_DET.budgetcode_id
  is 'FK - tb_ac_budgetcode_mas';
alter table TB_RECEIPT_DET
  add constraint PK_SRCPTFEEDET_RF_FEEID primary key (RF_FEEID);
alter table TB_RECEIPT_DET
  add constraint FK_RECEIPT_BUDGETCODE_ID foreign key (BUDGETCODE_ID)
  references TB_AC_BUDGETCODE_MAS (BUDGETCODE_ID);
alter table TB_RECEIPT_DET
  add constraint FK_RECEIPT_DPS_SLIPID foreign key (DPS_SLIPID)
  references TB_AC_BANK_DEPOSITSLIP_MASTER (DPS_SLIPID);

prompt
prompt Creating table TB_RECEIPT_MAS
prompt =============================
prompt
create table TB_RECEIPT_MAS
(
  rm_rcptid                NUMBER(12) not null,
  rm_rcptno                NUMBER(12),
  rm_date                  DATE,
  apm_application_id       NUMBER(16),
  rm_receiptcategory_id    NUMBER(12),
  rm_amount                NUMBER(12,2),
  cm_collnid               NUMBER(12),
  cu_counterid             NUMBER(12),
  rm_receivedfrom          NVARCHAR2(100),
  rm_narration             NVARCHAR2(200),
  rm_colncnt_rcptno        NUMBER(12),
  rm_counter_rcptno        NUMBER(12),
  sm_service_id            NUMBER(12),
  orgid                    NUMBER(4) not null,
  created_by               NUMBER(7),
  lang_id                  NUMBER(7),
  created_date             DATE,
  updated_by               NUMBER(7),
  updated_date             DATE,
  lg_ip_mac                VARCHAR2(100),
  lg_ip_mac_upd            VARCHAR2(100),
  rm_v1                    NVARCHAR2(100),
  rm_v2                    NVARCHAR2(100),
  rm_v3                    NVARCHAR2(100),
  rm_v4                    NVARCHAR2(100),
  rm_v5                    NVARCHAR2(100),
  rm_n1                    NUMBER(15),
  rm_n2                    NUMBER(15),
  rm_n3                    NUMBER(15),
  rm_n4                    NUMBER(15),
  rm_n5                    NUMBER(15),
  rm_d1                    DATE,
  rm_d2                    DATE,
  rm_d3                    DATE,
  rm_lo1                   CHAR(1),
  rm_lo2                   CHAR(1),
  rm_lo3                   CHAR(1),
  rm_loi_no                NVARCHAR2(16),
  cod_dwzid1               NUMBER(12),
  cod_dwzid2               NUMBER(12),
  cod_dwzid3               NUMBER(12),
  cod_dwzid4               NUMBER(12),
  cod_dwzid5               NUMBER(12),
  additional_ref_no        VARCHAR2(25),
  dp_deptid                NUMBER(12),
  manualreceiptno          NUMBER(12),
  vm_vendorid              NUMBER(12),
  mobile_number            NVARCHAR2(10),
  email_id                 NVARCHAR2(100),
  receipt_del_flag         CHAR(1),
  receipt_del_date         DATE,
  receipt_del_posting_date DATE,
  receipt_del_remark       VARCHAR2(500),
  field_id                 NUMBER(12),
  challan_id               NUMBER(12),
  ref_id                   NUMBER(12),
  receipt_type_flag        CHAR(1)
)
;
comment on column TB_RECEIPT_MAS.rm_rcptid
  is 'Generated Id';
comment on column TB_RECEIPT_MAS.rm_rcptno
  is 'Receipt no ';
comment on column TB_RECEIPT_MAS.rm_date
  is 'Receipt date';
comment on column TB_RECEIPT_MAS.apm_application_id
  is 'Application Id';
comment on column TB_RECEIPT_MAS.rm_receiptcategory_id
  is 'Receipt category';
comment on column TB_RECEIPT_MAS.rm_amount
  is 'Receipt amount';
comment on column TB_RECEIPT_MAS.cm_collnid
  is 'Collection Id';
comment on column TB_RECEIPT_MAS.cu_counterid
  is 'Counter Id';
comment on column TB_RECEIPT_MAS.rm_receivedfrom
  is 'Received from ';
comment on column TB_RECEIPT_MAS.rm_narration
  is 'Narration';
comment on column TB_RECEIPT_MAS.rm_colncnt_rcptno
  is 'Collection receipt no';
comment on column TB_RECEIPT_MAS.rm_counter_rcptno
  is 'Counter receipt no';
comment on column TB_RECEIPT_MAS.sm_service_id
  is 'Service Id';
comment on column TB_RECEIPT_MAS.orgid
  is 'Org ID';
comment on column TB_RECEIPT_MAS.created_by
  is 'User ID';
comment on column TB_RECEIPT_MAS.lang_id
  is 'Lang ID';
comment on column TB_RECEIPT_MAS.created_date
  is 'Last Modification Date';
comment on column TB_RECEIPT_MAS.updated_by
  is 'User id who update the data';
comment on column TB_RECEIPT_MAS.updated_date
  is 'Date on which data is going to update';
comment on column TB_RECEIPT_MAS.lg_ip_mac
  is 'Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_RECEIPT_MAS.lg_ip_mac_upd
  is 'Updated Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_RECEIPT_MAS.rm_v1
  is 'Additional nvarchar2 RM_V1 to be used in future';
comment on column TB_RECEIPT_MAS.rm_v2
  is 'Additional nvarchar2 RM_V2 to be used in future';
comment on column TB_RECEIPT_MAS.rm_v3
  is 'Additional nvarchar2 RM_V3 to be used in future';
comment on column TB_RECEIPT_MAS.rm_v4
  is 'Additional nvarchar2 RM_V4 to be used in future';
comment on column TB_RECEIPT_MAS.rm_v5
  is 'Additional nvarchar2 RM_V5 to be used in future';
comment on column TB_RECEIPT_MAS.rm_n1
  is 'Additional number RM_N1 to be used in future';
comment on column TB_RECEIPT_MAS.rm_n2
  is 'Additional number RM_N2 to be used in future';
comment on column TB_RECEIPT_MAS.rm_n3
  is 'Additional number RM_N3 to be used in future';
comment on column TB_RECEIPT_MAS.rm_n4
  is 'Additional number RM_N4 to be used in future';
comment on column TB_RECEIPT_MAS.rm_n5
  is 'Additional number RM_N5 to be used in future';
comment on column TB_RECEIPT_MAS.rm_d1
  is 'Additional Date RM_D1 to be used in future';
comment on column TB_RECEIPT_MAS.rm_d2
  is 'Additional Date RM_D2 to be used in future';
comment on column TB_RECEIPT_MAS.rm_d3
  is 'Additional Date RM_D3 to be used in future';
comment on column TB_RECEIPT_MAS.rm_lo1
  is 'Additional Logical field RM_LO1 to be used in future';
comment on column TB_RECEIPT_MAS.rm_lo2
  is 'Additional Logical field RM_LO2 to be used in future';
comment on column TB_RECEIPT_MAS.rm_lo3
  is 'Additional Logical field RM_LO3 to be used in future';
comment on column TB_RECEIPT_MAS.rm_loi_no
  is 'loi number form Tb_loi_mas';
comment on column TB_RECEIPT_MAS.cod_dwzid1
  is 'ward zone ';
comment on column TB_RECEIPT_MAS.cod_dwzid2
  is 'ward zone ';
comment on column TB_RECEIPT_MAS.cod_dwzid3
  is 'ward zone ';
comment on column TB_RECEIPT_MAS.cod_dwzid4
  is 'ward zone ';
comment on column TB_RECEIPT_MAS.cod_dwzid5
  is 'ward zone ';
comment on column TB_RECEIPT_MAS.additional_ref_no
  is 'additional ref no';
comment on column TB_RECEIPT_MAS.dp_deptid
  is 'add comment --Department id - Fk Ref.-tb_department';
comment on column TB_RECEIPT_MAS.manualreceiptno
  is 'Manual Receipt No';
comment on column TB_RECEIPT_MAS.vm_vendorid
  is 'fk Ref. Vendor id tb_vendormaster';
comment on column TB_RECEIPT_MAS.mobile_number
  is 'Mobile Number';
comment on column TB_RECEIPT_MAS.email_id
  is 'Email Id';
comment on column TB_RECEIPT_MAS.receipt_del_flag
  is 'Y for delete recept';
comment on column TB_RECEIPT_MAS.receipt_del_date
  is 'Receipt Deletion Date (system date)';
comment on column TB_RECEIPT_MAS.receipt_del_posting_date
  is 'Deleted RV Posting Date';
comment on column TB_RECEIPT_MAS.receipt_del_remark
  is 'Deleted Receipt Remark';
comment on column TB_RECEIPT_MAS.field_id
  is 'Field Master Reference key  --TB_AC_FIELD_MASTER';
comment on column TB_RECEIPT_MAS.challan_id
  is 'added new column fk. tb_challan_master';
comment on column TB_RECEIPT_MAS.ref_id
  is '1. Reversal payment voucher
payment id
';
comment on column TB_RECEIPT_MAS.receipt_type_flag
  is '"R"- Receipt
"C"- Contra  (internal receipt)
receipt number is separate for internal receipt
"P"- Payment voucher delete  (internal receipt)';
alter table TB_RECEIPT_MAS
  add constraint PK_SRCPT_ID primary key (RM_RCPTID);
alter table TB_RECEIPT_MAS
  add constraint FKDEPT_TB_SERVICE_RECEIPT foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);
alter table TB_RECEIPT_MAS
  add constraint FK_CHALLAN_MASTER_RCPT foreign key (CHALLAN_ID)
  references TB_CHALLAN_MASTER (CHALLAN_ID);
alter table TB_RECEIPT_MAS
  add constraint FK_FIELD_ID_RECEIPT foreign key (FIELD_ID)
  references TB_AC_FIELD_MASTER (FIELD_ID);

prompt
prompt Creating table TB_RECEIPT_MODE
prompt ==============================
prompt
create table TB_RECEIPT_MODE
(
  rd_modesid         NUMBER(12) not null,
  rm_rcptid          NUMBER(12),
  cpd_feemode        NUMBER(12),
  rd_chequeddno      NUMBER(12),
  rd_chequedddate    DATE,
  rd_drawnon         NVARCHAR2(200),
  bankid             NUMBER(12),
  rd_amount          NUMBER(12,2),
  orgid              NUMBER(4) not null,
  created_by         NUMBER(7),
  lang_id            NUMBER(7),
  created_date       DATE,
  updated_by         NUMBER(7),
  updated_date       DATE,
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  rd_v1              NVARCHAR2(100),
  rd_v2              NVARCHAR2(100),
  rd_v3              NVARCHAR2(100),
  rd_v4              NVARCHAR2(100),
  rd_v5              NVARCHAR2(100),
  rd_n1              NUMBER(15),
  rd_n2              NUMBER(15),
  rd_n3              NUMBER(15),
  rd_n4              NUMBER(15),
  rd_n5              NUMBER(15),
  rd_d1              DATE,
  rd_d2              DATE,
  rd_d3              DATE,
  rd_lo1             CHAR(1),
  rd_lo2             CHAR(1),
  rd_lo3             CHAR(1),
  rd_sr_chk_dis      CHAR(1),
  rd_outstation_chq  NVARCHAR2(1),
  rd_sr_chk_date     DATE,
  rd_sr_chk_dis_chg  NUMBER(12,2),
  ba_accountid       NUMBER(12),
  tran_ref_number    NVARCHAR2(22),
  tran_ref_date      DATE,
  discrepancy_flag   CHAR(1) default 'N',
  discrepancydetails NVARCHAR2(500),
  budgetcode_id      NUMBER(12)
)
;
comment on column TB_RECEIPT_MODE.rd_modesid
  is 'Generated Id';
comment on column TB_RECEIPT_MODE.rm_rcptid
  is 'Receipt Id';
comment on column TB_RECEIPT_MODE.cpd_feemode
  is 'Fees mode';
comment on column TB_RECEIPT_MODE.rd_chequeddno
  is 'Cheque or DD no';
comment on column TB_RECEIPT_MODE.rd_chequedddate
  is 'Cheque or DD date';
comment on column TB_RECEIPT_MODE.rd_drawnon
  is 'Drawn on';
comment on column TB_RECEIPT_MODE.bankid
  is 'Bank Id ref key --tb_bank_master';
comment on column TB_RECEIPT_MODE.rd_amount
  is 'Received amount';
comment on column TB_RECEIPT_MODE.orgid
  is 'Org ID';
comment on column TB_RECEIPT_MODE.created_by
  is 'User ID';
comment on column TB_RECEIPT_MODE.lang_id
  is 'Lang ID';
comment on column TB_RECEIPT_MODE.created_date
  is 'Last Modification Date';
comment on column TB_RECEIPT_MODE.updated_by
  is 'User id who update the data';
comment on column TB_RECEIPT_MODE.updated_date
  is 'Date on which data is going to update';
comment on column TB_RECEIPT_MODE.lg_ip_mac
  is 'Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_RECEIPT_MODE.lg_ip_mac_upd
  is 'Updated Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_RECEIPT_MODE.rd_v1
  is 'Additional nvarchar2 RD_V1 to be used in future';
comment on column TB_RECEIPT_MODE.rd_v2
  is 'Additional nvarchar2 RD_V2 to be used in future';
comment on column TB_RECEIPT_MODE.rd_v3
  is 'Additional nvarchar2 RD_V3 to be used in future';
comment on column TB_RECEIPT_MODE.rd_v4
  is 'Additional nvarchar2 RD_V4 to be used in future';
comment on column TB_RECEIPT_MODE.rd_v5
  is 'Additional nvarchar2 RD_V5 to be used in future';
comment on column TB_RECEIPT_MODE.rd_n1
  is 'Additional number RD_N1 to be used in future';
comment on column TB_RECEIPT_MODE.rd_n2
  is 'Additional number RD_N2 to be used in future';
comment on column TB_RECEIPT_MODE.rd_n3
  is 'Additional number RD_N3 to be used in future';
comment on column TB_RECEIPT_MODE.rd_n4
  is 'Additional number RD_N4 to be used in future';
comment on column TB_RECEIPT_MODE.rd_n5
  is 'Additional number RD_N5 to be used in future';
comment on column TB_RECEIPT_MODE.rd_d1
  is 'Additional Date RD_D1 to be used in future';
comment on column TB_RECEIPT_MODE.rd_d2
  is 'Additional Date RD_D2 to be used in future';
comment on column TB_RECEIPT_MODE.rd_d3
  is 'Additional Date RD_D3 to be used in future';
comment on column TB_RECEIPT_MODE.rd_lo1
  is 'Additional Logical field RD_LO1 to be used in future';
comment on column TB_RECEIPT_MODE.rd_lo2
  is 'Additional Logical field RD_LO2 to be used in future';
comment on column TB_RECEIPT_MODE.rd_lo3
  is 'Additional Logical field RD_LO3 to be used in future';
comment on column TB_RECEIPT_MODE.rd_sr_chk_dis
  is 'flag for check disowner';
comment on column TB_RECEIPT_MODE.rd_outstation_chq
  is 'Outstation cheque or local ';
comment on column TB_RECEIPT_MODE.rd_sr_chk_date
  is 'CHEQUE CLEARED OR DISHONOR DATE';
comment on column TB_RECEIPT_MODE.rd_sr_chk_dis_chg
  is 'cheque dishonor charges';
comment on column TB_RECEIPT_MODE.ba_accountid
  is 'Ref key- TB_BANKACCOUNT for Bank , RTGS , NEFT and WEB ';
comment on column TB_RECEIPT_MODE.tran_ref_number
  is 'Bank , RTGS , NEFT and WEB system should be able to capture ULB account bank details, transaction reference number ';
comment on column TB_RECEIPT_MODE.tran_ref_date
  is 'Bank , RTGS , NEFT and WEB transaction reference date ';
comment on column TB_RECEIPT_MODE.discrepancy_flag
  is 'Update from Reconciliation form  - if discrepancy is found update "Y" else default "N"';
comment on column TB_RECEIPT_MODE.discrepancydetails
  is 'Update from Reconciliation form - if discrepancy is found then enter discrepancy details.';
comment on column TB_RECEIPT_MODE.budgetcode_id
  is 'FK - tb_ac_budgetcode_mas';
alter table TB_RECEIPT_MODE
  add constraint PK_MODESID primary key (RD_MODESID);
alter table TB_RECEIPT_MODE
  add constraint FK_MODE_BUDGETCODE_ID foreign key (BUDGETCODE_ID)
  references TB_AC_BUDGETCODE_MAS (BUDGETCODE_ID);
alter table TB_RECEIPT_MODE
  add constraint FK_RECEIPT_BANKID foreign key (BANKID)
  references TB_BANK_MASTER (BANKID);
alter table TB_RECEIPT_MODE
  add constraint FK_RECEIPT_BA_ACCOUNTID foreign key (BA_ACCOUNTID)
  references TB_BANK_ACCOUNT (BA_ACCOUNTID);

prompt
prompt Creating table TB_REJECTION_MST
prompt ===============================
prompt
create table TB_REJECTION_MST
(
  rej_id              NUMBER(16) not null,
  rej_ref_id          NUMBER(16),
  rej_application_id  NUMBER(16),
  rej_service_id      NUMBER(12),
  rej_schedule_reason VARCHAR2(200),
  rej_letter_date     DATE,
  rej_type            NUMBER(10),
  rej_remarks         NVARCHAR2(2000),
  rej_schedule        CHAR(1),
  rej_letter_no       NVARCHAR2(50),
  rej_status          CHAR(1),
  orgid               NUMBER(4) not null,
  userid              NUMBER(4),
  langid              NUMBER(4),
  lmoddate            DATE,
  updated_date        DATE,
  updated_by          NUMBER(4),
  lg_ip_mac           VARCHAR2(100),
  lg_ip_mac_upd       VARCHAR2(100)
)
;
comment on column TB_REJECTION_MST.rej_id
  is 'Primary key';
comment on column TB_REJECTION_MST.rej_ref_id
  is 'Reference Id FK';
comment on column TB_REJECTION_MST.rej_application_id
  is 'Application id';
comment on column TB_REJECTION_MST.rej_service_id
  is 'Service id';
comment on column TB_REJECTION_MST.rej_schedule_reason
  is 'Rejection Schedule reason';
comment on column TB_REJECTION_MST.rej_letter_date
  is 'Rejection Letter Date';
comment on column TB_REJECTION_MST.rej_type
  is 'Rejection Type';
comment on column TB_REJECTION_MST.rej_remarks
  is 'Rejection remarks';
comment on column TB_REJECTION_MST.rej_schedule
  is 'Rejection Flag ';
comment on column TB_REJECTION_MST.rej_letter_no
  is 'Rejection Lettter No.';
comment on column TB_REJECTION_MST.rej_status
  is 'Rejection Status';
comment on column TB_REJECTION_MST.orgid
  is 'Org id';
comment on column TB_REJECTION_MST.userid
  is 'User id';
comment on column TB_REJECTION_MST.langid
  is 'Language id';
comment on column TB_REJECTION_MST.lmoddate
  is 'Creation Date';
comment on column TB_REJECTION_MST.updated_date
  is 'Updated Date';
comment on column TB_REJECTION_MST.updated_by
  is 'Updated By ';
comment on column TB_REJECTION_MST.lg_ip_mac
  is 'Client Machine.s Login Name | IP Address | Physical Address';
comment on column TB_REJECTION_MST.lg_ip_mac_upd
  is 'Updated Client Machine.s Login Name | IP Address | Physical Address';
alter table TB_REJECTION_MST
  add constraint PK_TB_APP_REJ_MST primary key (REJ_ID, ORGID);

prompt
prompt Creating table TB_REJECTION_MST_HIST
prompt ====================================
prompt
create table TB_REJECTION_MST_HIST
(
  h_rejid             NUMBER(16) not null,
  rej_id              NUMBER(16) not null,
  rej_ref_id          NUMBER(16),
  rej_application_id  NUMBER(16),
  rej_service_id      NUMBER(12),
  rej_schedule_reason VARCHAR2(200),
  rej_letter_date     DATE,
  rej_type            NUMBER(10),
  rej_remarks         NVARCHAR2(2000),
  rej_schedule        CHAR(1),
  rej_letter_no       NVARCHAR2(50),
  rej_status          CHAR(1),
  orgid               NUMBER(4) not null,
  userid              NUMBER(4),
  langid              NUMBER(4),
  lmoddate            DATE,
  updated_date        DATE,
  updated_by          NUMBER(4),
  lg_ip_mac           VARCHAR2(100),
  lg_ip_mac_upd       VARCHAR2(100),
  h_status            NVARCHAR2(1)
)
;
alter table TB_REJECTION_MST_HIST
  add constraint PK_H_REJID primary key (H_REJID);

prompt
prompt Creating table TB_SCRUTINY_MST
prompt ==============================
prompt
create table TB_SCRUTINY_MST
(
  sm_scrutiny_id        NUMBER(12) not null,
  sm_service_id         NUMBER(12),
  orgid                 NUMBER(4) not null,
  user_id               NUMBER(7) not null,
  lang_id               NUMBER(7) not null,
  lmoddate              DATE not null,
  sm_initiating_empid   NUMBER(10),
  sm_is_mois_integrated NVARCHAR2(1),
  updated_by            NUMBER(7),
  updated_date          DATE,
  dp_deptid             NUMBER(12),
  cpm_prefix            VARCHAR2(3),
  lg_ip_mac             VARCHAR2(100),
  lg_ip_mac_upd         VARCHAR2(100),
  com_v1                NVARCHAR2(100),
  com_v2                NVARCHAR2(100),
  com_v3                NVARCHAR2(100),
  com_v4                NVARCHAR2(100),
  com_v5                NVARCHAR2(100),
  com_n1                NUMBER(15),
  com_n2                NUMBER(15),
  com_n3                NUMBER(15),
  com_n4                NUMBER(15),
  com_n5                NUMBER(15),
  com_d1                DATE,
  com_d2                DATE,
  com_d3                DATE,
  com_lo1               CHAR(1),
  com_lo2               CHAR(1),
  com_lo3               CHAR(1)
)
;
comment on table TB_SCRUTINY_MST
  is 'Scrutiny Master';
comment on column TB_SCRUTINY_MST.sm_scrutiny_id
  is 'Identification Id';
comment on column TB_SCRUTINY_MST.sm_service_id
  is 'Service Id';
comment on column TB_SCRUTINY_MST.orgid
  is 'Organization Id';
comment on column TB_SCRUTINY_MST.user_id
  is 'User Id';
comment on column TB_SCRUTINY_MST.lang_id
  is 'Language Id';
comment on column TB_SCRUTINY_MST.lmoddate
  is 'Last Modification Date';
comment on column TB_SCRUTINY_MST.sm_initiating_empid
  is 'Initating Employee''s Identification Id';
comment on column TB_SCRUTINY_MST.sm_is_mois_integrated
  is 'Mois Integration Flag';
alter table TB_SCRUTINY_MST
  add constraint PK_SM_SCRUTINY_ID_ORGID primary key (SM_SCRUTINY_ID, ORGID);
alter table TB_SCRUTINY_MST
  add constraint FK_SM_SERVICE_ID_MST foreign key (SM_SERVICE_ID)
  references TB_SERVICES_MST (SM_SERVICE_ID);

prompt
prompt Creating table TB_SCRUTINY_DET
prompt ==============================
prompt
create table TB_SCRUTINY_DET
(
  sm_scrutiny_det_id NUMBER(12) not null,
  sm_scrutiny_id     NUMBER(12) not null,
  ward_level1        NUMBER(12),
  ward_level2        NUMBER(12),
  ward_level3        NUMBER(12),
  ward_level4        NUMBER(12),
  ward_level5        NUMBER(12),
  orgid              NUMBER(4) not null,
  user_id            NUMBER(7),
  lang_id            NUMBER(7),
  lmoddate           DATE,
  updated_by         NUMBER(7),
  updated_date       DATE,
  emp_id             NUMBER(10),
  com_v1             NVARCHAR2(100),
  com_v2             NVARCHAR2(100),
  com_v3             NVARCHAR2(100),
  com_v4             NVARCHAR2(100),
  com_v5             NVARCHAR2(100),
  com_n1             NUMBER(15),
  com_n2             NUMBER(15),
  com_n3             NUMBER(15),
  com_n4             NUMBER(15),
  com_n5             NUMBER(15),
  com_d1             DATE,
  com_d2             DATE,
  com_d3             DATE,
  com_lo1            CHAR(1),
  com_lo2            CHAR(1),
  com_lo3            CHAR(1),
  trf_type           NUMBER
)
;
comment on column TB_SCRUTINY_DET.trf_type
  is 'Usage type come from prefix ';
alter table TB_SCRUTINY_DET
  add constraint PK_SM_SCRUTINY_DET_ID_ORGID primary key (SM_SCRUTINY_DET_ID, ORGID);
alter table TB_SCRUTINY_DET
  add constraint FK_SM_SCRUTINY_ID_ORGID foreign key (SM_SCRUTINY_ID, ORGID)
  references TB_SCRUTINY_MST (SM_SCRUTINY_ID, ORGID);

prompt
prompt Creating table TB_SCRUTINY_DET_HIST
prompt ===================================
prompt
create table TB_SCRUTINY_DET_HIST
(
  h_sm_detid         NUMBER(12) not null,
  sm_scrutiny_det_id NUMBER(12) not null,
  sm_scrutiny_id     NUMBER(12) not null,
  ward_level1        NUMBER(12),
  ward_level2        NUMBER(12),
  ward_level3        NUMBER(12),
  ward_level4        NUMBER(12),
  ward_level5        NUMBER(12),
  orgid              NUMBER(4) not null,
  user_id            NUMBER(7),
  lang_id            NUMBER(7),
  lmoddate           DATE,
  updated_by         NUMBER(7),
  updated_date       DATE,
  emp_id             NUMBER(10),
  com_v1             NVARCHAR2(100),
  com_v2             NVARCHAR2(100),
  com_v3             NVARCHAR2(100),
  com_v4             NVARCHAR2(100),
  com_v5             NVARCHAR2(100),
  com_n1             NUMBER(15),
  com_n2             NUMBER(15),
  com_n3             NUMBER(15),
  com_n4             NUMBER(15),
  com_n5             NUMBER(15),
  com_d1             DATE,
  com_d2             DATE,
  com_d3             DATE,
  com_lo1            CHAR(1),
  com_lo2            CHAR(1),
  com_lo3            CHAR(1),
  trf_type           NUMBER,
  h_status           NVARCHAR2(1)
)
;
alter table TB_SCRUTINY_DET_HIST
  add constraint PK_H_SM_DETID primary key (H_SM_DETID);

prompt
prompt Creating table TB_SCRUTINY_DOCS
prompt ===============================
prompt
create table TB_SCRUTINY_DOCS
(
  sd_id              NUMBER not null,
  orgid              NUMBER,
  apm_application_id NUMBER(16),
  sm_service_id      NUMBER(12),
  sm_scrutiny_id     NUMBER(12),
  sm_scrutiny_level  NUMBER(2),
  upld_doc_name      NVARCHAR2(500),
  upld_doc_path      NVARCHAR2(1000),
  upld_doc_remark    NVARCHAR2(1000),
  upld_by            NUMBER,
  upld_date          DATE,
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(7) not null,
  lmoddate           DATE not null,
  rec_status         CHAR(1),
  updated_by         NUMBER(7),
  updated_date       DATE,
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  gm_id              NUMBER
)
;
comment on column TB_SCRUTINY_DOCS.sd_id
  is 'Primary Key';
comment on column TB_SCRUTINY_DOCS.orgid
  is 'Organisation Id';
comment on column TB_SCRUTINY_DOCS.apm_application_id
  is 'Application Id';
comment on column TB_SCRUTINY_DOCS.sm_service_id
  is 'Service Id';
comment on column TB_SCRUTINY_DOCS.sm_scrutiny_id
  is 'Scrutiny Id';
comment on column TB_SCRUTINY_DOCS.sm_scrutiny_level
  is 'Scrutiny level';
comment on column TB_SCRUTINY_DOCS.upld_doc_name
  is 'Uploaded document name';
comment on column TB_SCRUTINY_DOCS.upld_doc_path
  is 'Uploaded document path';
comment on column TB_SCRUTINY_DOCS.upld_doc_remark
  is 'remark if any';
comment on column TB_SCRUTINY_DOCS.upld_by
  is 'upload by';
comment on column TB_SCRUTINY_DOCS.upld_date
  is 'upload date';
comment on column TB_SCRUTINY_DOCS.user_id
  is 'user id';
comment on column TB_SCRUTINY_DOCS.lang_id
  is 'language id';
comment on column TB_SCRUTINY_DOCS.lmoddate
  is 'entry date';
comment on column TB_SCRUTINY_DOCS.rec_status
  is 'status';
comment on column TB_SCRUTINY_DOCS.updated_by
  is 'updated by ';
comment on column TB_SCRUTINY_DOCS.updated_date
  is 'updated date';
comment on column TB_SCRUTINY_DOCS.gm_id
  is 'Desidnation id';
alter table TB_SCRUTINY_DOCS
  add constraint PK_TB_SCRUTINY_DOCS primary key (SD_ID);
alter table TB_SCRUTINY_DOCS
  add constraint FK_APPL_TB_SCRUTINY_DOCS foreign key (APM_APPLICATION_ID)
  references TB_CFC_APPLICATION_MST (APM_APPLICATION_ID);

prompt
prompt Creating table TB_SCRUTINY_DOCS_HIST
prompt ====================================
prompt
create table TB_SCRUTINY_DOCS_HIST
(
  h_sdid             NUMBER not null,
  sd_id              NUMBER not null,
  orgid              NUMBER,
  apm_application_id NUMBER(16),
  sm_service_id      NUMBER(12),
  sm_scrutiny_id     NUMBER(12),
  sm_scrutiny_level  NUMBER(2),
  upld_doc_name      NVARCHAR2(500),
  upld_doc_path      NVARCHAR2(1000),
  upld_doc_remark    NVARCHAR2(1000),
  upld_by            NUMBER,
  upld_date          DATE,
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(7) not null,
  lmoddate           DATE not null,
  rec_status         CHAR(1),
  updated_by         NUMBER(7),
  updated_date       DATE,
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  gm_id              NUMBER,
  h_status           NVARCHAR2(1)
)
;
alter table TB_SCRUTINY_DOCS_HIST
  add constraint PK_H_SDID primary key (H_SDID);

prompt
prompt Creating table TB_SCRUTINY_LABELS
prompt =================================
prompt
create table TB_SCRUTINY_LABELS
(
  sl_label_id        NUMBER not null,
  sl_label           NVARCHAR2(200),
  sl_active_status   NVARCHAR2(1) default 'a',
  sl_datatype        NVARCHAR2(8),
  sl_validation_text NVARCHAR2(1000),
  sl_table_column    NVARCHAR2(2000),
  sl_where_clause    NVARCHAR2(2000),
  sl_formula         NVARCHAR2(200),
  sl_position        NUMBER(5,2),
  sl_display_flag    NVARCHAR2(1) default 'n',
  sl_tabular_data    NVARCHAR2(1) default 'n',
  sm_scrutiny_id     NUMBER(12),
  sl_procedure       NVARCHAR2(50),
  sl_form_name       NVARCHAR2(100),
  orgid              NUMBER(4) not null,
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(7) not null,
  lmoddate           DATE not null,
  sl_access_dsgid    NUMBER(10),
  sl_pre_validation  NVARCHAR2(2000),
  sl_form_mode       NVARCHAR2(1),
  sl_authorising     NVARCHAR2(1),
  gm_id              NUMBER(12),
  sl_label_mar       NVARCHAR2(2000),
  com_v1             NVARCHAR2(100),
  com_v2             NVARCHAR2(100),
  com_v3             NVARCHAR2(100),
  com_v4             NVARCHAR2(100),
  com_v5             NVARCHAR2(100),
  com_n1             NUMBER(15),
  com_n2             NUMBER(15),
  com_n3             NUMBER(15),
  com_n4             NUMBER(15),
  com_n5             NUMBER(15),
  com_d1             DATE,
  com_d2             DATE,
  com_d3             DATE,
  com_lo1            CHAR(1),
  com_lo2            CHAR(1),
  com_lo3            CHAR(1),
  levels             NUMBER
)
;
comment on table TB_SCRUTINY_LABELS
  is 'Scrutiny Labels';
comment on column TB_SCRUTINY_LABELS.sl_label_id
  is 'Identification Id';
comment on column TB_SCRUTINY_LABELS.sl_label
  is 'Scrutiny Label';
comment on column TB_SCRUTINY_LABELS.sl_active_status
  is 'Scrutiny Status(Active/Inactive)';
comment on column TB_SCRUTINY_LABELS.sl_datatype
  is 'Scrutiny DataType';
comment on column TB_SCRUTINY_LABELS.sl_validation_text
  is 'Scrutiny Validation Text';
comment on column TB_SCRUTINY_LABELS.sl_table_column
  is 'Table Column to be Refered';
comment on column TB_SCRUTINY_LABELS.sl_where_clause
  is 'Where Clause';
comment on column TB_SCRUTINY_LABELS.sl_formula
  is 'Formula to be entered';
comment on column TB_SCRUTINY_LABELS.sl_position
  is 'Position to be entered';
comment on column TB_SCRUTINY_LABELS.sl_display_flag
  is 'Display Flag';
comment on column TB_SCRUTINY_LABELS.sl_tabular_data
  is 'Tabular Data';
comment on column TB_SCRUTINY_LABELS.sm_scrutiny_id
  is 'Scrutiny Id';
comment on column TB_SCRUTINY_LABELS.sl_procedure
  is 'Procedure';
comment on column TB_SCRUTINY_LABELS.sl_form_name
  is 'Form Name';
comment on column TB_SCRUTINY_LABELS.orgid
  is 'Organization Id';
comment on column TB_SCRUTINY_LABELS.user_id
  is 'User Id';
comment on column TB_SCRUTINY_LABELS.lang_id
  is 'Language Id';
comment on column TB_SCRUTINY_LABELS.lmoddate
  is 'Last Modification Date';
comment on column TB_SCRUTINY_LABELS.sl_access_dsgid
  is 'Access Designation ID of the person';
comment on column TB_SCRUTINY_LABELS.sl_pre_validation
  is 'Pre Validation';
comment on column TB_SCRUTINY_LABELS.sl_form_mode
  is 'Form Mode';
comment on column TB_SCRUTINY_LABELS.sl_authorising
  is 'Authorizing Flag';
alter table TB_SCRUTINY_LABELS
  add constraint PK_LABELID_ORGID primary key (SL_LABEL_ID, ORGID);
alter table TB_SCRUTINY_LABELS
  add constraint FK_GM_ID foreign key (GM_ID)
  references TB_GROUP_MAST (GM_ID);
alter table TB_SCRUTINY_LABELS
  add constraint FK_SCRUTINY_MASTER_LABELS foreign key (SM_SCRUTINY_ID, ORGID)
  references TB_SCRUTINY_MST (SM_SCRUTINY_ID, ORGID);
alter table TB_SCRUTINY_LABELS
  add constraint CK_SL_DISPLAY_FLAG
  check (sl_display_flag in ('Y','N'));

prompt
prompt Creating table TB_SCRUTINY_LABELS_HIST
prompt ======================================
prompt
create table TB_SCRUTINY_LABELS_HIST
(
  h_sl_labelid       NUMBER not null,
  sl_label_id        NUMBER not null,
  sl_label           NVARCHAR2(200),
  sl_active_status   NVARCHAR2(1) default 'a',
  sl_datatype        NVARCHAR2(8),
  sl_validation_text NVARCHAR2(1000),
  sl_table_column    NVARCHAR2(2000),
  sl_where_clause    NVARCHAR2(2000),
  sl_formula         NVARCHAR2(200),
  sl_position        NUMBER(5,2),
  sl_display_flag    NVARCHAR2(1) default 'n',
  sl_tabular_data    NVARCHAR2(1) default 'n',
  sm_scrutiny_id     NUMBER(12),
  sl_procedure       NVARCHAR2(50),
  sl_form_name       NVARCHAR2(100),
  orgid              NUMBER(4) not null,
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(7) not null,
  lmoddate           DATE not null,
  sl_access_dsgid    NUMBER(10),
  sl_pre_validation  NVARCHAR2(2000),
  sl_form_mode       NVARCHAR2(1),
  sl_authorising     NVARCHAR2(1),
  gm_id              NUMBER(12),
  sl_label_mar       NVARCHAR2(2000),
  com_v1             NVARCHAR2(100),
  com_v2             NVARCHAR2(100),
  com_v3             NVARCHAR2(100),
  com_v4             NVARCHAR2(100),
  com_v5             NVARCHAR2(100),
  com_n1             NUMBER(15),
  com_n2             NUMBER(15),
  com_n3             NUMBER(15),
  com_n4             NUMBER(15),
  com_n5             NUMBER(15),
  com_d1             DATE,
  com_d2             DATE,
  com_d3             DATE,
  com_lo1            CHAR(1),
  com_lo2            CHAR(1),
  com_lo3            CHAR(1),
  levels             NUMBER,
  h_status           NVARCHAR2(1)
)
;
alter table TB_SCRUTINY_LABELS_HIST
  add constraint PK_H_SL_LABELID primary key (H_SL_LABELID);

prompt
prompt Creating table TB_SCRUTINY_MST_HIST
prompt ===================================
prompt
create table TB_SCRUTINY_MST_HIST
(
  h_smscrutinyid        NUMBER(12) not null,
  sm_scrutiny_id        NUMBER(12) not null,
  sm_service_id         NUMBER(12),
  orgid                 NUMBER(4) not null,
  user_id               NUMBER(7) not null,
  lang_id               NUMBER(7) not null,
  lmoddate              DATE not null,
  sm_initiating_empid   NUMBER(10),
  sm_is_mois_integrated NVARCHAR2(1),
  updated_by            NUMBER(7),
  updated_date          DATE,
  dp_deptid             NUMBER(12),
  cpm_prefix            VARCHAR2(3),
  lg_ip_mac             VARCHAR2(100),
  lg_ip_mac_upd         VARCHAR2(100),
  com_v1                NVARCHAR2(100),
  com_v2                NVARCHAR2(100),
  com_v3                NVARCHAR2(100),
  com_v4                NVARCHAR2(100),
  com_v5                NVARCHAR2(100),
  com_n1                NUMBER(15),
  com_n2                NUMBER(15),
  com_n3                NUMBER(15),
  com_n4                NUMBER(15),
  com_n5                NUMBER(15),
  com_d1                DATE,
  com_d2                DATE,
  com_d3                DATE,
  com_lo1               CHAR(1),
  com_lo2               CHAR(1),
  com_lo3               CHAR(1),
  h_status              VARCHAR2(1)
)
;
alter table TB_SCRUTINY_MST_HIST
  add constraint PK_HSMSCRUTINYID primary key (H_SMSCRUTINYID);

prompt
prompt Creating table TB_SCRUTINY_VALUES
prompt =================================
prompt
create table TB_SCRUTINY_VALUES
(
  sl_label_id        NUMBER(12) not null,
  cfc_application_id NUMBER(24) not null,
  sv_value           NVARCHAR2(200),
  orgid              NUMBER(4) not null,
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(7) not null,
  lmoddate           DATE not null,
  updated_by         NUMBER(7),
  updated_date       DATE,
  levels             NUMBER
)
;
comment on table TB_SCRUTINY_VALUES
  is 'Value of Scrutiny Label for the application ID';
comment on column TB_SCRUTINY_VALUES.sl_label_id
  is 'Scrutiny Label Id';
comment on column TB_SCRUTINY_VALUES.cfc_application_id
  is 'Scrutiny Application Id';
comment on column TB_SCRUTINY_VALUES.sv_value
  is 'Scrutiny Value';
comment on column TB_SCRUTINY_VALUES.orgid
  is 'Organization Id';
comment on column TB_SCRUTINY_VALUES.user_id
  is 'User Id';
comment on column TB_SCRUTINY_VALUES.lang_id
  is 'Language Id';
comment on column TB_SCRUTINY_VALUES.lmoddate
  is 'Last Modification Date';
comment on column TB_SCRUTINY_VALUES.updated_by
  is 'Last Updated By';
comment on column TB_SCRUTINY_VALUES.updated_date
  is 'Last Updated Date';
alter table TB_SCRUTINY_VALUES
  add constraint FK_CFC_APPLICATION_ID foreign key (CFC_APPLICATION_ID)
  references TB_CFC_APPLICATION_MST (APM_APPLICATION_ID);
alter table TB_SCRUTINY_VALUES
  add constraint FK_SCRUTINY_LABELS_VALUES foreign key (SL_LABEL_ID, ORGID)
  references TB_SCRUTINY_LABELS (SL_LABEL_ID, ORGID);

prompt
prompt Creating table TB_SCRUTINY_VALUES_HIST
prompt ======================================
prompt
create table TB_SCRUTINY_VALUES_HIST
(
  h_sllableid        NUMBER(12) not null,
  sl_label_id        NUMBER(12) not null,
  cfc_application_id NUMBER(24) not null,
  sv_value           NVARCHAR2(200),
  orgid              NUMBER(4) not null,
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(7) not null,
  lmoddate           DATE not null,
  updated_by         NUMBER(7),
  updated_date       DATE,
  levels             NUMBER,
  h_status           NVARCHAR2(1)
)
;

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
  sq_rst_typ    NVARCHAR2(2),
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
prompt Creating table TB_SERVICES_EVENT
prompt ================================
prompt
create table TB_SERVICES_EVENT
(
  service_event_id NUMBER(12) not null,
  event_id         NUMBER(12) not null,
  sm_service_id    NUMBER(12) not null,
  created_by       NUMBER(12) not null,
  updated_by       NUMBER(12),
  updated_date     DATE,
  isdeleted        VARCHAR2(2) default 'N' not null,
  orgid            NUMBER(4) not null,
  lang_id          NUMBER(2) not null,
  lg_ip_mac        VARCHAR2(100) not null,
  lg_ip_mac_upd    VARCHAR2(100),
  created_date     DATE not null,
  dep_id           NUMBER(12)
)
;
comment on column TB_SERVICES_EVENT.service_event_id
  is 'Primary Key';
comment on column TB_SERVICES_EVENT.event_id
  is 'Foreign Key which is Primary Key of TB_SYSMODFUNCTION';
comment on column TB_SERVICES_EVENT.sm_service_id
  is 'Service_ID to identify for which Service this Event is mapped';
comment on column TB_SERVICES_EVENT.created_by
  is 'Service Event  Created By which Employee';
comment on column TB_SERVICES_EVENT.updated_by
  is 'Service Event  Updated By which Employee';
comment on column TB_SERVICES_EVENT.updated_date
  is 'Service Event  Updated Date';
comment on column TB_SERVICES_EVENT.isdeleted
  is 'default value-N ,flag to identify whether Service Event is deleted or not , Y-deleted, N-not deleted';
comment on column TB_SERVICES_EVENT.orgid
  is 'Organization ID';
comment on column TB_SERVICES_EVENT.lang_id
  is 'Language ID : English-1, Hindi-2';
comment on column TB_SERVICES_EVENT.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_SERVICES_EVENT.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_SERVICES_EVENT.created_date
  is 'Service Event Created Date';
comment on column TB_SERVICES_EVENT.dep_id
  is 'Depatment_Id';
alter table TB_SERVICES_EVENT
  add constraint PK_SERVICE_EVENT_ID primary key (SERVICE_EVENT_ID);
alter table TB_SERVICES_EVENT
  add constraint FK_EVENT_ID foreign key (EVENT_ID)
  references TB_SYSMODFUNCTION (SMFID);
alter table TB_SERVICES_EVENT
  add constraint FK_SERVICE_EVENT_SM_SERVICE_ID foreign key (SM_SERVICE_ID)
  references TB_SERVICES_MST (SM_SERVICE_ID)
  disable;

prompt
prompt Creating table TB_SERVICES_EVENT_HIST
prompt =====================================
prompt
create table TB_SERVICES_EVENT_HIST
(
  h_sr_eventid     NUMBER(12) not null,
  service_event_id NUMBER(12) not null,
  event_id         NUMBER(12) not null,
  sm_service_id    NUMBER(12) not null,
  created_by       NUMBER(12) not null,
  updated_by       NUMBER(12),
  updated_date     DATE,
  isdeleted        VARCHAR2(2) default 'N' not null,
  orgid            NUMBER(4) not null,
  lang_id          NUMBER(2) not null,
  lg_ip_mac        VARCHAR2(100) not null,
  lg_ip_mac_upd    VARCHAR2(100),
  created_date     DATE not null,
  dep_id           NUMBER(12),
  h_status         NVARCHAR2(1)
)
;
alter table TB_SERVICES_EVENT_HIST
  add constraint PK_H_SR_EVENTID primary key (H_SR_EVENTID);

prompt
prompt Creating table TB_SERVICES_MST_HIST
prompt ===================================
prompt
create table TB_SERVICES_MST_HIST
(
  h_sm_serviceid          NUMBER(12) not null,
  sm_service_id           NUMBER(12) not null,
  sm_service_name         NVARCHAR2(100),
  sm_serv_own_desig       NUMBER(12),
  sm_serv_active          NUMBER(12),
  sm_serv_type            NUMBER(12),
  sm_serv_counter         NUMBER(12),
  sm_serv_duration        NUMBER(3),
  sm_appl_form            NUMBER(12),
  sm_chklst_verify        NUMBER(12),
  sm_security_deposit     NUMBER(12),
  sm_fees_schedule        NUMBER(3),
  sm_acknowledge          NUMBER(12),
  sm_scrutiny_level       NUMBER(3),
  sm_autho_level          NUMBER(12),
  sm_print_respons        NUMBER(12),
  sm_type_of_sign         NUMBER(12),
  sm_dispatch_post        NUMBER(12),
  sm_specific_info_form   NVARCHAR2(100),
  orgid                   NUMBER(12),
  user_id                 NUMBER(7),
  lang_id                 NUMBER(7),
  lmoddate                DATE,
  sm_service_name_mar     NVARCHAR2(200),
  cdm_dept_id             NUMBER(12),
  sm_approval_form        NVARCHAR2(100),
  sm_rejection_form       NVARCHAR2(100),
  sm_web_enabled          NVARCHAR2(1) default 'N',
  sm_url                  NVARCHAR2(100),
  sm_srno                 NVARCHAR2(12),
  sm_switch               NVARCHAR2(1) default 'N',
  sm_addr                 NVARCHAR2(1) default 'N',
  sm_sign_path            NVARCHAR2(200),
  updated_by              NUMBER(7),
  updated_date            DATE,
  sm_rcpt                 CHAR(1) default 'Y',
  sm_initiating_empid     NVARCHAR2(12),
  sm_cpd_id               NUMBER(12),
  sm_loi_duration         NUMBER(3),
  sm_shortdesc            VARCHAR2(5),
  com_v1                  NVARCHAR2(100),
  com_v2                  NVARCHAR2(100),
  com_v3                  NVARCHAR2(100),
  com_v4                  NVARCHAR2(100),
  com_v5                  NVARCHAR2(100),
  com_n1                  NUMBER(15),
  com_n2                  NUMBER(15),
  com_n3                  NUMBER(15),
  com_n4                  NUMBER(15),
  com_n5                  NUMBER(15),
  com_d1                  DATE,
  com_d2                  DATE,
  com_d3                  DATE,
  sm_csc_flag             CHAR(1) default 'N',
  sm_sms                  CHAR(1) default 'N',
  sm_email                CHAR(1) default 'N',
  srid_hours              NUMBER(2),
  srid_min                NUMBER(2),
  sm_checklist_hours      NUMBER(2),
  sm_checklist_min        NUMBER(2),
  sm_checklist_days       NUMBER(3),
  sm_serv_duration_type   NUMBER(12),
  sm_service_note         NVARCHAR2(400),
  sm_service_note_mar     NVARCHAR2(600),
  ip_mac                  VARCHAR2(100),
  ip_mac_upd              VARCHAR2(100),
  sm_appli_charge_flag    NVARCHAR2(1),
  sm_scrutiny_charge_flag NVARCHAR2(1),
  sm_scru_applicable_flag NVARCHAR2(1),
  h_status                NVARCHAR2(1)
)
;
alter table TB_SERVICES_MST_HIST
  add constraint PK_H_SMSERVICEID primary key (H_SM_SERVICEID);

prompt
prompt Creating table TB_SERVICE_RECEIPT_MAS_HIST
prompt ==========================================
prompt
create table TB_SERVICE_RECEIPT_MAS_HIST
(
  h_rmrcptid            NUMBER(12) not null,
  rm_rcptid             NUMBER(12) not null,
  rm_rcptno             NUMBER(12),
  rm_date               DATE,
  rm_id                 NUMBER(16),
  rm_receiptcategory_id NUMBER(12),
  rm_amount             NUMBER(12,2),
  cm_collnid            NUMBER(12),
  cu_counterid          NUMBER(12),
  rm_receivedfrom       NVARCHAR2(100),
  dwz_id                NUMBER(12),
  rm_narration          NVARCHAR2(200),
  rm_year               NUMBER(4),
  rm_colncnt_rcptno     NUMBER(12),
  rm_counter_rcptno     NUMBER(12),
  sm_service_id         NUMBER(12),
  fa_yearid             NUMBER(12),
  select_ds             VARCHAR2(1),
  orgid                 NUMBER(4) not null,
  user_id               NUMBER(7),
  lang_id               NUMBER(7),
  lmoddate              DATE,
  updated_by            NUMBER(7),
  updated_date          DATE,
  lg_ip_mac             VARCHAR2(100),
  lg_ip_mac_upd         VARCHAR2(100),
  wt_v1                 NVARCHAR2(100),
  wt_v2                 NVARCHAR2(100),
  wt_v3                 NVARCHAR2(100),
  wt_v4                 NVARCHAR2(100),
  wt_v5                 NVARCHAR2(100),
  wt_n1                 NUMBER(15),
  wt_n2                 NUMBER(15),
  wt_n3                 NUMBER(15),
  wt_n4                 NUMBER(15),
  wt_n5                 NUMBER(15),
  wt_d1                 DATE,
  wt_d2                 DATE,
  wt_d3                 DATE,
  wt_lo1                CHAR(1),
  wt_lo2                CHAR(1),
  wt_lo3                CHAR(1),
  rm_sr_chk_dis         CHAR(1),
  rm_loi_no             NVARCHAR2(16),
  cod_dwzid1            NUMBER(12),
  cod_dwzid2            NUMBER(12),
  cod_dwzid3            NUMBER(12),
  cod_dwzid4            NUMBER(12),
  cod_dwzid5            NUMBER(12),
  h_status              NVARCHAR2(1)
)
;
alter table TB_SERVICE_RECEIPT_MAS_HIST
  add constraint PK_H_RMRCPTID primary key (H_RMRCPTID);

prompt
prompt Creating table TB_SRCPT_FEES_DET_HIST
prompt =====================================
prompt
create table TB_SRCPT_FEES_DET_HIST
(
  h_rfid        NUMBER(12) not null,
  rf_feeid      NUMBER(12) not null,
  rm_rcptid     NUMBER(12),
  tax_id        NUMBER(12),
  rf_feeamount  NUMBER(12,2),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7),
  lang_id       NUMBER(7),
  lmoddate      DATE,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  wt_v1         NVARCHAR2(100),
  wt_v2         NVARCHAR2(100),
  wt_v3         NVARCHAR2(100),
  wt_v4         NVARCHAR2(100),
  wt_v5         NVARCHAR2(100),
  tdd_taxid     NUMBER(15),
  wt_n2         NUMBER(15),
  wt_n3         NUMBER(15),
  wt_n4         NUMBER(15),
  wt_n5         NUMBER(15),
  wt_d1         DATE,
  wt_d2         DATE,
  wt_d3         DATE,
  wt_lo1        CHAR(1),
  wt_lo2        CHAR(1),
  wt_lo3        CHAR(1),
  rf_cpd_modeid NUMBER(12),
  rf_sr_chk_dis CHAR(1),
  h_status      NVARCHAR2(1)
)
;
alter table TB_SRCPT_FEES_DET_HIST
  add constraint PK_H_RFID primary key (H_RFID);

prompt
prompt Creating table TB_SRCPT_MODES_DET_HIST
prompt ======================================
prompt
create table TB_SRCPT_MODES_DET_HIST
(
  h_rd_modeid       NUMBER(12) not null,
  rd_modesid        NUMBER(12) not null,
  rm_rcptid         NUMBER(12),
  cpd_feemode       NUMBER(12),
  rd_chequeddno     NUMBER(12),
  rd_chequedddate   DATE,
  rd_drawnon        NVARCHAR2(200),
  cb_bankid         NUMBER(12),
  rd_amount         NUMBER(12,2),
  orgid             NUMBER(4) not null,
  user_id           NUMBER(7),
  lang_id           NUMBER(7),
  lmoddate          DATE,
  updated_by        NUMBER(7),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100),
  wt_v1             NVARCHAR2(100),
  wt_v2             NVARCHAR2(100),
  wt_v3             NVARCHAR2(100),
  wt_v4             NVARCHAR2(100),
  wt_v5             NVARCHAR2(100),
  wt_n1             NUMBER(15),
  wt_n2             NUMBER(15),
  wt_n3             NUMBER(15),
  wt_n4             NUMBER(15),
  wt_n5             NUMBER(15),
  wt_d1             DATE,
  wt_d2             DATE,
  wt_d3             DATE,
  wt_lo1            CHAR(1),
  wt_lo2            CHAR(1),
  wt_lo3            CHAR(1),
  rd_sr_chk_dis     CHAR(1),
  rd_outstation_chq NVARCHAR2(1),
  h_status          NVARCHAR2(1)
)
;
alter table TB_SRCPT_MODES_DET_HIST
  add constraint PK_H_RD_MODEID primary key (H_RD_MODEID);

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

prompt
prompt Creating table TB_TAX_MAS
prompt =========================
prompt
create table TB_TAX_MAS
(
  tax_id          NUMBER(12) not null,
  tax_desc        NVARCHAR2(50) not null,
  tax_method      NVARCHAR2(30) not null,
  tax_from_date   DATE,
  tax_to_date     DATE,
  tax_value_type  NVARCHAR2(30),
  parent_code     NUMBER(12),
  tax_frequency   NVARCHAR2(2),
  primary_tax     NVARCHAR2(1),
  tax_group       NVARCHAR2(50),
  tax_print_on1   NVARCHAR2(50),
  tax_code        NVARCHAR2(50),
  tax_display_seq NUMBER,
  dp_deptid       NUMBER,
  coll_mtd        NUMBER,
  coll_seq        NUMBER,
  orgid           NUMBER(4),
  user_id         NUMBER(7),
  lang_id         NUMBER(7),
  lmoddate        DATE,
  updated_by      NUMBER(7),
  updated_date    DATE,
  tax_category1   NUMBER(12),
  tax_category2   NUMBER(12),
  tax_category3   NUMBER(12),
  tax_category4   NUMBER(12),
  tax_category5   NUMBER(12),
  tax_applicable  NUMBER(12),
  sm_service_id   NUMBER(12),
  tax_print_on2   NVARCHAR2(50),
  tax_print_on3   NVARCHAR2(50),
  fund_id         NUMBER(12),
  function_id     NUMBER(12),
  pac_head_id     NUMBER(12),
  sac_head_id     NUMBER(12),
  pac_head_id_lib NUMBER(12),
  sac_head_id_lib NUMBER(12)
)
;
comment on column TB_TAX_MAS.tax_id
  is 'Primary Key Tax ID';
comment on column TB_TAX_MAS.tax_desc
  is 'Tax Description';
comment on column TB_TAX_MAS.tax_method
  is 'Tax Method Flat or Slab';
comment on column TB_TAX_MAS.tax_from_date
  is 'From date';
comment on column TB_TAX_MAS.tax_to_date
  is 'To date';
comment on column TB_TAX_MAS.tax_value_type
  is 'Value type Amount or Percentage';
comment on column TB_TAX_MAS.parent_code
  is 'Printing Code';
comment on column TB_TAX_MAS.tax_frequency
  is 'Frequency for tax';
comment on column TB_TAX_MAS.tax_code
  is 'TAX CODE';
comment on column TB_TAX_MAS.tax_display_seq
  is 'Dispaly Sequence';
comment on column TB_TAX_MAS.dp_deptid
  is 'Departement Id From TB_DEPARTMENT';
comment on column TB_TAX_MAS.coll_mtd
  is 'Collection Method from Prefix RAB ';
comment on column TB_TAX_MAS.coll_seq
  is 'Collection Sequence';
comment on column TB_TAX_MAS.orgid
  is 'Org id';
comment on column TB_TAX_MAS.user_id
  is 'User Id';
comment on column TB_TAX_MAS.lang_id
  is 'Language Id';
comment on column TB_TAX_MAS.lmoddate
  is 'Created Date';
comment on column TB_TAX_MAS.updated_by
  is 'Updated By ';
comment on column TB_TAX_MAS.updated_date
  is 'Updated Date';
comment on column TB_TAX_MAS.tax_category1
  is 'WTG Prefix used';
comment on column TB_TAX_MAS.tax_category2
  is 'WTG Prefix used';
comment on column TB_TAX_MAS.tax_category3
  is 'WTG Prefix used';
comment on column TB_TAX_MAS.tax_category4
  is 'WTG Prefix used';
comment on column TB_TAX_MAS.tax_category5
  is 'WTG Prefix used';
comment on column TB_TAX_MAS.tax_applicable
  is 'TAX APPLICABLE AT';
comment on column TB_TAX_MAS.sm_service_id
  is 'SERVICE ID';
comment on column TB_TAX_MAS.fund_id
  is 'Fund Master Reference key --TB_AC_FUND_MASTER';
comment on column TB_TAX_MAS.function_id
  is 'Function  Master Reference key --TB_AC_FUNCTION_MASTER';
comment on column TB_TAX_MAS.pac_head_id
  is 'Primary head Master Reference key -TB_AC_PRIMARYHEAD_MASTER';
comment on column TB_TAX_MAS.sac_head_id
  is 'Secondary Master Reference key -- tb_ac_secondaryhead_master';
alter table TB_TAX_MAS
  add constraint PK_TAX_ID_MAS primary key (TAX_ID);
alter table TB_TAX_MAS
  add constraint FK_DP_DEPID foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);
alter table TB_TAX_MAS
  add constraint FK_TAX_HEADMAPPING_FUNCTION_ID foreign key (FUNCTION_ID)
  references TB_AC_FUNCTION_MASTER (FUNCTION_ID);
alter table TB_TAX_MAS
  add constraint FK_TAX_HEADMAPPING_FUND_ID foreign key (FUND_ID)
  references TB_AC_FUND_MASTER (FUND_ID);
alter table TB_TAX_MAS
  add constraint FK_TAX_HEADMAPPING_HEADID_L foreign key (PAC_HEAD_ID_LIB)
  references TB_AC_PRIMARYHEAD_MASTER (PAC_HEAD_ID);
alter table TB_TAX_MAS
  add constraint FK_TAX_HEADMAPPING_HEAD_ID foreign key (PAC_HEAD_ID)
  references TB_AC_PRIMARYHEAD_MASTER (PAC_HEAD_ID);
alter table TB_TAX_MAS
  add constraint FK_TAX_HEADMAPPING_SAC_HEAD_ID foreign key (SAC_HEAD_ID)
  references TB_AC_SECONDARYHEAD_MASTER (SAC_HEAD_ID);
alter table TB_TAX_MAS
  add constraint FK_TAX_HMAPPING_SACHEADID_L foreign key (SAC_HEAD_ID_LIB)
  references TB_AC_SECONDARYHEAD_MASTER (SAC_HEAD_ID);

prompt
prompt Creating table TB_TAX_DET
prompt =========================
prompt
create table TB_TAX_DET
(
  td_taxdet      NUMBER(12) not null,
  tm_taxid       NUMBER(12),
  td_depend_fact NUMBER(12),
  td_value       NUMBER,
  td_formula     VARCHAR2(500),
  status         VARCHAR2(1),
  orgid          NUMBER(4) not null,
  user_id        NUMBER(7),
  lang_id        NUMBER(7),
  lmoddate       DATE,
  updated_by     NUMBER(7),
  updated_date   DATE,
  lg_ip_mac      VARCHAR2(100),
  lg_ip_mac_upd  VARCHAR2(100)
)
;
alter table TB_TAX_DET
  add constraint PK_TAX_DET primary key (TD_TAXDET);
alter table TB_TAX_DET
  add constraint FK_TAXMAS_ID foreign key (TM_TAXID)
  references TB_TAX_MAS (TAX_ID);

prompt
prompt Creating table TB_TAX_DET_HIST
prompt ==============================
prompt
create table TB_TAX_DET_HIST
(
  h_detid        NUMBER(12) not null,
  td_detid       NUMBER(12) not null,
  td_taxdet      NUMBER(12) not null,
  tm_taxid       NUMBER(12),
  td_depend_fact NUMBER(12),
  td_value       NUMBER,
  td_formula     VARCHAR2(500),
  status         VARCHAR2(1),
  orgid          NUMBER(4) not null,
  user_id        NUMBER(7),
  lang_id        NUMBER(7),
  lmoddate       DATE,
  updated_by     NUMBER(7),
  updated_date   DATE,
  lg_ip_mac      VARCHAR2(100),
  lg_ip_mac_upd  VARCHAR2(100),
  h_status       NVARCHAR2(1)
)
;
alter table TB_TAX_DET_HIST
  add constraint PK_HDETID primary key (H_DETID);

prompt
prompt Creating table TB_TAX_MAS_HIST
prompt ==============================
prompt
create table TB_TAX_MAS_HIST
(
  h_taxid         NUMBER(12) not null,
  tax_id          NUMBER(12) not null,
  tax_desc        NVARCHAR2(50) not null,
  tax_method      NVARCHAR2(30) not null,
  tax_from_date   DATE,
  tax_to_date     DATE,
  tax_value_type  NVARCHAR2(30) not null,
  parent_code     NUMBER(12),
  tax_frequency   NVARCHAR2(2),
  primary_tax     NVARCHAR2(1),
  tax_group       NVARCHAR2(50),
  tax_print_om    NVARCHAR2(50),
  tax_code        NVARCHAR2(50),
  tax_display_seq NUMBER,
  dp_deptid       NUMBER,
  coll_mtd        NUMBER,
  coll_seq        NUMBER,
  orgid           NUMBER(4),
  user_id         NUMBER(7),
  lang_id         NUMBER(7),
  lmoddate        DATE,
  updated_by      NUMBER(7),
  updated_date    DATE,
  tax_category    NUMBER(12),
  h_status        NVARCHAR2(1)
)
;
alter table TB_TAX_MAS_HIST
  add constraint PK_H_TAXID primary key (H_TAXID);

prompt
prompt Creating table TB_VISITOR_SCHEDULE
prompt ==================================
prompt
create table TB_VISITOR_SCHEDULE
(
  vis_id             NUMBER(16) not null,
  vis_application_id NUMBER(16),
  vis_service_id     NUMBER(16),
  vis_empid          NUMBER(16),
  vis_building       VARCHAR2(200),
  vis_wing           VARCHAR2(50),
  vis_date           DATE,
  vis_time           VARCHAR2(50),
  vis_status         VARCHAR2(1),
  orgid              NUMBER(4) not null,
  user_id            NUMBER(7),
  lang_id            NUMBER(7),
  lmoddate           DATE,
  updated_by         NUMBER(7),
  updated_date       DATE,
  vis_v1             NVARCHAR2(100),
  vis_v2             NVARCHAR2(100),
  vis_v3             NVARCHAR2(100),
  vis_v4             NVARCHAR2(100),
  vis_v5             NVARCHAR2(100),
  vis_n1             NUMBER(15),
  vis_n2             NUMBER(15),
  vis_n3             NUMBER(15),
  vis_n4             NUMBER(15),
  vis_n5             NUMBER(15),
  vis_d1             DATE,
  vis_d2             DATE,
  vis_d3             DATE,
  vis_lo1            CHAR(1),
  vis_lo2            CHAR(1),
  vis_lo3            CHAR(1),
  vis_type_flg       CHAR(1) default 'S',
  vis_insp_no        VARCHAR2(50),
  vis_insp_letter_dt DATE,
  vis_sp_id          NUMBER
)
;
comment on column TB_VISITOR_SCHEDULE.vis_id
  is 'Primary Key';
comment on column TB_VISITOR_SCHEDULE.vis_application_id
  is 'Application id';
comment on column TB_VISITOR_SCHEDULE.vis_service_id
  is 'Service id';
comment on column TB_VISITOR_SCHEDULE.vis_empid
  is 'Employee id';
comment on column TB_VISITOR_SCHEDULE.vis_building
  is 'Building place to visite';
comment on column TB_VISITOR_SCHEDULE.vis_wing
  is 'wing to visite';
comment on column TB_VISITOR_SCHEDULE.vis_date
  is 'vistite date';
comment on column TB_VISITOR_SCHEDULE.vis_time
  is 'visite time';
comment on column TB_VISITOR_SCHEDULE.vis_status
  is 'status';
comment on column TB_VISITOR_SCHEDULE.orgid
  is 'Org id';
comment on column TB_VISITOR_SCHEDULE.user_id
  is 'User id';
comment on column TB_VISITOR_SCHEDULE.lang_id
  is 'Language id';
comment on column TB_VISITOR_SCHEDULE.lmoddate
  is 'Creation date';
comment on column TB_VISITOR_SCHEDULE.updated_by
  is 'Update by ';
comment on column TB_VISITOR_SCHEDULE.updated_date
  is 'Update date';
comment on column TB_VISITOR_SCHEDULE.vis_v1
  is 'Additional nvarchar2 to be used in future';
comment on column TB_VISITOR_SCHEDULE.vis_v2
  is 'Additional nvarchar2 to be used in future';
comment on column TB_VISITOR_SCHEDULE.vis_v3
  is 'Additional nvarchar2 to be used in future';
comment on column TB_VISITOR_SCHEDULE.vis_v4
  is 'Additional nvarchar2 to be used in future';
comment on column TB_VISITOR_SCHEDULE.vis_v5
  is 'Additional nvarchar2 to be used in future';
comment on column TB_VISITOR_SCHEDULE.vis_n1
  is 'Additional Number to be used in future';
comment on column TB_VISITOR_SCHEDULE.vis_n2
  is 'Additional Number to be used in future';
comment on column TB_VISITOR_SCHEDULE.vis_n3
  is 'Additional Number to be used in future';
comment on column TB_VISITOR_SCHEDULE.vis_n4
  is 'Additional Number to be used in future';
comment on column TB_VISITOR_SCHEDULE.vis_n5
  is 'Additional Number to be used in future';
comment on column TB_VISITOR_SCHEDULE.vis_d1
  is 'Additional date to be used in future';
comment on column TB_VISITOR_SCHEDULE.vis_d2
  is 'Additional date to be used in future';
comment on column TB_VISITOR_SCHEDULE.vis_d3
  is 'Additional date to be used in future';
comment on column TB_VISITOR_SCHEDULE.vis_lo1
  is 'Additional char to be used in future';
comment on column TB_VISITOR_SCHEDULE.vis_lo2
  is 'Additional char to be used in future';
comment on column TB_VISITOR_SCHEDULE.vis_lo3
  is 'Additional char to be used in future';
comment on column TB_VISITOR_SCHEDULE.vis_type_flg
  is 'S - Service / P - Site progress ';
comment on column TB_VISITOR_SCHEDULE.vis_insp_no
  is 'Inspection No';
comment on column TB_VISITOR_SCHEDULE.vis_insp_letter_dt
  is 'Inspection Letter Date';
alter table TB_VISITOR_SCHEDULE
  add constraint PK_VISITOR_ID primary key (VIS_ID, ORGID);

prompt
prompt Creating table TB_VISITOR_SCHEDULE_HIST
prompt =======================================
prompt
create table TB_VISITOR_SCHEDULE_HIST
(
  h_visid            NUMBER(16) not null,
  vis_id             NUMBER(16) not null,
  vis_application_id NUMBER(16),
  vis_service_id     NUMBER(16),
  vis_empid          NUMBER(16),
  vis_building       VARCHAR2(200),
  vis_wing           VARCHAR2(50),
  vis_date           DATE,
  vis_time           VARCHAR2(50),
  vis_status         VARCHAR2(1),
  orgid              NUMBER(4) not null,
  user_id            NUMBER(7),
  lang_id            NUMBER(7),
  lmoddate           DATE,
  updated_by         NUMBER(7),
  updated_date       DATE,
  vis_v1             NVARCHAR2(100),
  vis_v2             NVARCHAR2(100),
  vis_v3             NVARCHAR2(100),
  vis_v4             NVARCHAR2(100),
  vis_v5             NVARCHAR2(100),
  vis_n1             NUMBER(15),
  vis_n2             NUMBER(15),
  vis_n3             NUMBER(15),
  vis_n4             NUMBER(15),
  vis_n5             NUMBER(15),
  vis_d1             DATE,
  vis_d2             DATE,
  vis_d3             DATE,
  vis_lo1            CHAR(1),
  vis_lo2            CHAR(1),
  vis_lo3            CHAR(1),
  vis_type_flg       CHAR(1) default 'S',
  vis_insp_no        NUMBER(12),
  vis_insp_letter_dt DATE,
  vis_sp_id          NUMBER,
  h_status           NVARCHAR2(1)
)
;
alter table TB_VISITOR_SCHEDULE_HIST
  add constraint PK_H_VISID primary key (H_VISID);

prompt
prompt Creating table TB_WF_TASK_MANAGER
prompt =================================
prompt
create table TB_WF_TASK_MANAGER
(
  awtmd_id                    NUMBER not null,
  awtmd_application_id        NUMBER,
  awtmd_step_no               NUMBER,
  awtmd_no_of_approver        NUMBER,
  awtmd_service_event         NUMBER not null,
  awtmd_event_description_eng VARCHAR2(100),
  awtmd_event_description_reg VARCHAR2(100),
  awtmd_event_url             VARCHAR2(100),
  awtmd_gm_id                 VARCHAR2(20),
  awtmd_isrequire             VARCHAR2(1) not null,
  awtmd_depends_on_steps      NUMBER(3),
  awtmd_condiatinal_next_step NUMBER(3),
  awtmd_sla                   NUMBER(3),
  awtmd_task_clamied_by       NUMBER(12),
  awtmd_task_assigned_date    DATE,
  awtmd_task_completed_date   DATE,
  awtmd_task_status           VARCHAR2(50),
  created_date                DATE not null,
  created_by                  NUMBER(12) not null,
  updated_date                DATE,
  updated_by                  NUMBER(12),
  lg_ip_mac                   VARCHAR2(100) not null,
  lg_ip_mac_upd               VARCHAR2(100)
)
;
comment on column TB_WF_TASK_MANAGER.awtmd_id
  is 'Primary Key';
comment on column TB_WF_TASK_MANAGER.awtmd_application_id
  is 'Foreign Key: which is Primary Key of tb_cfc_application_mst table';
comment on column TB_WF_TASK_MANAGER.awtmd_step_no
  is 'Step No of Event action';
comment on column TB_WF_TASK_MANAGER.awtmd_no_of_approver
  is 'No. of Approval required for Event';
comment on column TB_WF_TASK_MANAGER.awtmd_service_event
  is 'we_gm_id from TB_WORKFLOW_EVENT to represent the group,current task is assigned to';
comment on column TB_WF_TASK_MANAGER.created_date
  is 'Workflow Created Date';
comment on column TB_WF_TASK_MANAGER.created_by
  is 'Workflow Created By which Employee';
comment on column TB_WF_TASK_MANAGER.updated_date
  is 'Workflow Updated Date';
comment on column TB_WF_TASK_MANAGER.updated_by
  is 'Workflow Updated By which Employee';
comment on column TB_WF_TASK_MANAGER.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_WF_TASK_MANAGER.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
alter table TB_WF_TASK_MANAGER
  add constraint PK_TB_APPL_WF_TASK_MST_DTL primary key (AWTMD_ID);
alter table TB_WF_TASK_MANAGER
  add constraint FK_TB_APPL_MST foreign key (AWTMD_APPLICATION_ID)
  references TB_CFC_APPLICATION_MST (APM_APPLICATION_ID);

prompt
prompt Creating table TB_WF_TASK_MANAGER_HIST
prompt ======================================
prompt
create table TB_WF_TASK_MANAGER_HIST
(
  h_awtmdid                   NUMBER not null,
  awtmd_id                    NUMBER not null,
  awtmd_application_id        NUMBER,
  awtmd_step_no               NUMBER,
  awtmd_no_of_approver        NUMBER,
  awtmd_service_event         NUMBER not null,
  awtmd_event_description_eng VARCHAR2(100),
  awtmd_event_description_reg VARCHAR2(100),
  awtmd_event_url             VARCHAR2(100),
  awtmd_gm_id                 VARCHAR2(20),
  awtmd_isrequire             VARCHAR2(1) not null,
  awtmd_depends_on_steps      NUMBER(3),
  awtmd_condiatinal_next_step NUMBER(3),
  awtmd_sla                   NUMBER(3),
  awtmd_task_clamied_by       NUMBER(12),
  awtmd_task_assigned_date    DATE,
  awtmd_task_completed_date   DATE,
  awtmd_task_status           VARCHAR2(50),
  created_date                DATE not null,
  created_by                  NUMBER(12) not null,
  updated_date                DATE,
  updated_by                  NUMBER(12),
  lg_ip_mac                   VARCHAR2(100) not null,
  lg_ip_mac_upd               VARCHAR2(100),
  h_status                    VARCHAR2(1)
)
;
alter table TB_WF_TASK_MANAGER_HIST
  add constraint PK_H_AWTMDID primary key (H_AWTMDID);

prompt
prompt Creating table TB_WORKFLOW_DEFINATION
prompt =====================================
prompt
create table TB_WORKFLOW_DEFINATION
(
  wd_id             NUMBER(12) not null,
  wd_name           VARCHAR2(100),
  wd_name_regional  VARCHAR2(100),
  wd_orgid          NUMBER(4) not null,
  wd_service_id     NUMBER(12) not null,
  wd_mode           VARCHAR2(1),
  wd_lang           NUMBER(2) not null,
  wd_status         VARCHAR2(1) default 'P' not null,
  wd_isactive       VARCHAR2(1) default 'I' not null,
  created_date      DATE not null,
  created_by        NUMBER(12) not null,
  updated_date      DATE,
  updated_by        NUMBER(12),
  isdeleted         VARCHAR2(1) default 'N' not null,
  lg_ip_mac         VARCHAR2(100) not null,
  lg_ip_mac_upd     VARCHAR2(100),
  wd_desc           VARCHAR2(500),
  wd_dept_id        NUMBER(12) not null,
  ward_level_1      NUMBER(12),
  ward_level_2      NUMBER(12),
  ward_level_3      NUMBER(12),
  ward_level_4      NUMBER(12),
  ward_level_5      NUMBER(12),
  complaint_type    NUMBER(12),
  complain_sub_type NUMBER(12),
  ward_zone_type    CHAR(1),
  wd_access_type    VARCHAR2(1)
)
;
comment on column TB_WORKFLOW_DEFINATION.wd_id
  is 'Primary Key';
comment on column TB_WORKFLOW_DEFINATION.wd_name
  is 'WORKFLOW DEFINATION NAME IN ENGLISH';
comment on column TB_WORKFLOW_DEFINATION.wd_name_regional
  is 'WORKFLOW DEFINATION NAME IN REGIONAL LANGUAGE';
comment on column TB_WORKFLOW_DEFINATION.wd_orgid
  is 'Organization ID';
comment on column TB_WORKFLOW_DEFINATION.wd_service_id
  is 'Service Id to Identify for which Service this workflow made';
comment on column TB_WORKFLOW_DEFINATION.wd_mode
  is 'Payment Mode :Y-Online, N-Offline, F-Free';
comment on column TB_WORKFLOW_DEFINATION.wd_lang
  is 'Language ID : English-1, Hindi-2';
comment on column TB_WORKFLOW_DEFINATION.wd_status
  is 'default value -P ,flag to identify status of workflow : A-Approved, H-Hold, R-Rejected, P-Pending';
comment on column TB_WORKFLOW_DEFINATION.wd_isactive
  is 'default value-I-Flag to identify whether Workflow is Active(A) or Inactive(I) ';
comment on column TB_WORKFLOW_DEFINATION.created_date
  is 'Workflow Created Date';
comment on column TB_WORKFLOW_DEFINATION.created_by
  is 'Workflow Created By which Employee';
comment on column TB_WORKFLOW_DEFINATION.updated_date
  is 'Workflow Updated Date';
comment on column TB_WORKFLOW_DEFINATION.updated_by
  is 'Workflow Updated By which Employee';
comment on column TB_WORKFLOW_DEFINATION.isdeleted
  is 'default value-N ,flag to identify whether Workflow is deleted or not , Y-deleted, N-not deleted';
comment on column TB_WORKFLOW_DEFINATION.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_WORKFLOW_DEFINATION.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_WORKFLOW_DEFINATION.wd_desc
  is 'Workflow Description';
comment on column TB_WORKFLOW_DEFINATION.ward_level_1
  is 'Ward Zone Hierarchy Level 1';
comment on column TB_WORKFLOW_DEFINATION.ward_level_2
  is 'Ward Zone Hierarchy Level 2';
comment on column TB_WORKFLOW_DEFINATION.ward_level_3
  is 'Ward Zone Hierarchy Level 3';
comment on column TB_WORKFLOW_DEFINATION.ward_level_4
  is 'Ward Zone Hierarchy Level 4';
comment on column TB_WORKFLOW_DEFINATION.ward_level_5
  is 'Ward Zone Hierarchy Level 5';
comment on column TB_WORKFLOW_DEFINATION.complaint_type
  is 'Compalaint Type From TB_COMPALAIN_TYPE';
comment on column TB_WORKFLOW_DEFINATION.complain_sub_type
  is 'Compalaint  Sub Type From TB_COMPALAIN_SUB_TYPE';
comment on column TB_WORKFLOW_DEFINATION.ward_zone_type
  is 'Ward-Zone Type (A=All ,N=Ward-Zone)';
alter table TB_WORKFLOW_DEFINATION
  add constraint PK_TB_WORKFLOW_DEFINATION primary key (WD_ID);

prompt
prompt Creating table TB_WORKFLOW_DEFINATION_HIST
prompt ==========================================
prompt
create table TB_WORKFLOW_DEFINATION_HIST
(
  h_wdid            NUMBER(12) not null,
  wd_id             NUMBER(12) not null,
  wd_name           VARCHAR2(100),
  wd_name_regional  VARCHAR2(100),
  wd_orgid          NUMBER(4) not null,
  wd_service_id     NUMBER(12) not null,
  wd_mode           VARCHAR2(1) not null,
  wd_lang           NUMBER(2) not null,
  wd_status         VARCHAR2(1) default 'P' not null,
  wd_isactive       VARCHAR2(1) default 'I' not null,
  created_date      DATE not null,
  created_by        NUMBER(12) not null,
  updated_date      DATE,
  updated_by        NUMBER(12),
  isdeleted         VARCHAR2(1) default 'N' not null,
  lg_ip_mac         VARCHAR2(100) not null,
  lg_ip_mac_upd     VARCHAR2(100),
  wd_desc           VARCHAR2(500),
  wd_dept_id        NUMBER(12) not null,
  ward_level_1      NUMBER(12),
  ward_level_2      NUMBER(12),
  ward_level_3      NUMBER(12),
  ward_level_4      NUMBER(12),
  ward_level_5      NUMBER(12),
  complaint_type    NUMBER(12),
  complain_sub_type NUMBER(12),
  ward_zone_type    CHAR(1)
)
;
alter table TB_WORKFLOW_DEFINATION_HIST
  add constraint PK_H_WDID primary key (H_WDID);

prompt
prompt Creating table TB_WORKFLOW_EVENT
prompt ================================
prompt
create table TB_WORKFLOW_EVENT
(
  we_id                          NUMBER(12) not null,
  we_wd_id                       NUMBER(12),
  we_step_no                     NUMBER(3),
  we_service_event               NUMBER(12) not null,
  we_no_of_approver              NUMBER(2),
  we_orgid                       NUMBER(4) not null,
  we_gm_id                       VARCHAR2(20),
  we_isrequire                   VARCHAR2(1) not null,
  we_depends_on_steps            NUMBER(3),
  we_condiatinal_false_next_step NUMBER(3),
  we_sla                         NUMBER(3),
  we_status                      VARCHAR2(1),
  created_date                   DATE not null,
  created_by                     NUMBER(12) not null,
  updated_date                   DATE,
  updated_by                     NUMBER(12),
  isdeleted                      VARCHAR2(1) default 'N' not null,
  lg_ip_mac                      VARCHAR2(100) not null,
  lg_ip_mac_upd                  VARCHAR2(100),
  we_user_access                 VARCHAR2(20)
)
;
comment on column TB_WORKFLOW_EVENT.we_id
  is 'Primary Key';
comment on column TB_WORKFLOW_EVENT.we_wd_id
  is 'Foreign Key: which is Primary Key of TB_WORKFLOW_DEFINATION table';
comment on column TB_WORKFLOW_EVENT.we_step_no
  is 'Step No of Event action';
comment on column TB_WORKFLOW_EVENT.we_service_event
  is 'Foreign Key: which is Primary Key of tb_services_event table';
comment on column TB_WORKFLOW_EVENT.we_no_of_approver
  is 'No. of Approval required for Event';
comment on column TB_WORKFLOW_EVENT.we_orgid
  is 'Organization Id';
comment on column TB_WORKFLOW_EVENT.we_gm_id
  is 'Foreign Key: which is Primary Key of tb_group_mast table';
comment on column TB_WORKFLOW_EVENT.we_isrequire
  is 'flag to identify whether this event is mandatory or Condional or Optional:M-mandatory,C-Condional,O-Optional';
comment on column TB_WORKFLOW_EVENT.we_depends_on_steps
  is 'If Optional then on which event(step No) it''s dependent.';
comment on column TB_WORKFLOW_EVENT.we_condiatinal_false_next_step
  is 'If WE_ISREQUIRE is Condional then on which event(step No) it''s jump to.';
comment on column TB_WORKFLOW_EVENT.we_sla
  is 'SLA(Service Level Agreement): days required for Event completion';
comment on column TB_WORKFLOW_EVENT.we_status
  is 'status of Event: A-Approved, H-Hold, R-Rejected,P-Pending';
comment on column TB_WORKFLOW_EVENT.created_date
  is 'Workflow Event Created Date';
comment on column TB_WORKFLOW_EVENT.created_by
  is 'Workflow Event Created By which Employee';
comment on column TB_WORKFLOW_EVENT.updated_date
  is 'Workflow Event Updated Date ';
comment on column TB_WORKFLOW_EVENT.updated_by
  is 'Workflow Event Updated By which Employee';
comment on column TB_WORKFLOW_EVENT.isdeleted
  is 'default value-N ,flag to identify whether Workflow is deleted or not , Y-deleted, N-not deleted';
comment on column TB_WORKFLOW_EVENT.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_WORKFLOW_EVENT.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
alter table TB_WORKFLOW_EVENT
  add constraint PK_TB_WORKFLOW_EVENT primary key (WE_ID);
alter table TB_WORKFLOW_EVENT
  add constraint FK_SERVICE_EVENT_ID foreign key (WE_SERVICE_EVENT)
  references TB_SERVICES_EVENT (SERVICE_EVENT_ID);
alter table TB_WORKFLOW_EVENT
  add constraint FK_WE_WD_ID foreign key (WE_WD_ID)
  references TB_WORKFLOW_DEFINATION (WD_ID);

prompt
prompt Creating table TB_WORKFLOW_EVENT_HIST
prompt =====================================
prompt
create table TB_WORKFLOW_EVENT_HIST
(
  h_weid                         NUMBER(12) not null,
  we_id                          NUMBER(12) not null,
  we_wd_id                       NUMBER(12),
  we_step_no                     NUMBER(3),
  we_service_event               NUMBER(12) not null,
  we_no_of_approver              NUMBER(2),
  we_orgid                       NUMBER(4) not null,
  we_gm_id                       VARCHAR2(20),
  we_isrequire                   VARCHAR2(1) not null,
  we_depends_on_steps            NUMBER(3),
  we_condiatinal_false_next_step NUMBER(3),
  we_sla                         NUMBER(3),
  we_status                      VARCHAR2(1),
  created_date                   DATE not null,
  created_by                     NUMBER(12) not null,
  updated_date                   DATE,
  updated_by                     NUMBER(12),
  isdeleted                      VARCHAR2(1) default 'N' not null,
  lg_ip_mac                      VARCHAR2(100) not null,
  lg_ip_mac_upd                  VARCHAR2(100),
  h_status                       NVARCHAR2(1)
)
;
alter table TB_WORKFLOW_EVENT_HIST
  add constraint PK_H_WEID primary key (H_WEID);

prompt
prompt Creating table TB_WORK_ORDER
prompt ============================
prompt
create table TB_WORK_ORDER
(
  wo_id               NUMBER(16) not null,
  wo_order_no         NVARCHAR2(25),
  wo_order_date       DATE not null,
  wo_service_id       NUMBER(12) not null,
  wo_application_id   NUMBER(16),
  wo_application_date DATE,
  wo_dept_id          NUMBER,
  wo_allocation       NUMBER,
  wo_print_flg        NVARCHAR2(1),
  orgid               NUMBER(4) not null,
  user_id             NUMBER(7) not null,
  lang_id             NUMBER(7) not null,
  lmoddate            DATE not null,
  updated_by          NUMBER(7),
  updated_date        DATE,
  lg_ip_mac           VARCHAR2(100),
  lg_ip_mac_upd       VARCHAR2(100),
  cs_ccn              NVARCHAR2(20),
  plum_id             NUMBER
)
;
comment on column TB_WORK_ORDER.wo_id
  is 'Primary Key';
comment on column TB_WORK_ORDER.wo_order_no
  is 'Work Order No';
comment on column TB_WORK_ORDER.wo_order_date
  is 'Work Order Date';
comment on column TB_WORK_ORDER.wo_service_id
  is 'Work Order Service id';
comment on column TB_WORK_ORDER.wo_application_id
  is 'Work Order Application id';
comment on column TB_WORK_ORDER.wo_application_date
  is 'Work Order Application Date';
comment on column TB_WORK_ORDER.wo_dept_id
  is 'Departement Id';
comment on column TB_WORK_ORDER.wo_allocation
  is 'Work Order Allocation by using WPC prefix';
comment on column TB_WORK_ORDER.wo_print_flg
  is 'Work Order Print flag';
comment on column TB_WORK_ORDER.orgid
  is 'Orgid';
comment on column TB_WORK_ORDER.user_id
  is 'User id';
comment on column TB_WORK_ORDER.lang_id
  is 'Language id';
comment on column TB_WORK_ORDER.lmoddate
  is 'Ceration date';
comment on column TB_WORK_ORDER.updated_by
  is 'Updated By';
comment on column TB_WORK_ORDER.updated_date
  is 'Updated Date';
comment on column TB_WORK_ORDER.lg_ip_mac
  is 'IP Address';
comment on column TB_WORK_ORDER.lg_ip_mac_upd
  is 'Updated IP Address';
comment on column TB_WORK_ORDER.cs_ccn
  is 'Connection No.';
alter table TB_WORK_ORDER
  add constraint PK_WO_ID primary key (WO_ID);

prompt
prompt Creating table TB_WORK_ORDER_DETAIL
prompt ===================================
prompt
create table TB_WORK_ORDER_DETAIL
(
  wd_id             NUMBER(16) not null,
  wo_id             NUMBER(16),
  wd_service_id     NUMBER(12) not null,
  wd_application_id NUMBER(16),
  wd_dp_deptid      NUMBER(16),
  wd_remark_id      NUMBER,
  wd_remark         VARCHAR2(500),
  wd_oth_remark     VARCHAR2(500),
  orgid             NUMBER(4) not null,
  user_id           NUMBER(7) not null,
  lang_id           NUMBER(7) not null,
  lmoddate          DATE not null,
  updated_by        NUMBER(7),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100)
)
;
comment on column TB_WORK_ORDER_DETAIL.wd_id
  is 'Primary Key';
comment on column TB_WORK_ORDER_DETAIL.wo_id
  is 'FK from tb_work_order';
comment on column TB_WORK_ORDER_DETAIL.wd_service_id
  is 'Service id';
comment on column TB_WORK_ORDER_DETAIL.wd_application_id
  is 'Application id';
comment on column TB_WORK_ORDER_DETAIL.wd_dp_deptid
  is 'Department id';
comment on column TB_WORK_ORDER_DETAIL.wd_remark_id
  is 'Remark form Remark Master ';
comment on column TB_WORK_ORDER_DETAIL.wd_remark
  is 'Remark Description from Remark Master';
comment on column TB_WORK_ORDER_DETAIL.wd_oth_remark
  is 'Other Remark';
comment on column TB_WORK_ORDER_DETAIL.orgid
  is 'Org id';
comment on column TB_WORK_ORDER_DETAIL.user_id
  is 'User id';
comment on column TB_WORK_ORDER_DETAIL.lang_id
  is 'Language id';
comment on column TB_WORK_ORDER_DETAIL.lmoddate
  is 'Creation date';
comment on column TB_WORK_ORDER_DETAIL.updated_by
  is 'Updated By';
comment on column TB_WORK_ORDER_DETAIL.updated_date
  is 'Updatd date';
comment on column TB_WORK_ORDER_DETAIL.lg_ip_mac
  is 'Ip Address';
comment on column TB_WORK_ORDER_DETAIL.lg_ip_mac_upd
  is 'Updated IP Address';
alter table TB_WORK_ORDER_DETAIL
  add constraint PK_WD_ID primary key (WD_ID);
alter table TB_WORK_ORDER_DETAIL
  add constraint FK_WO_ID foreign key (WO_ID)
  references TB_WORK_ORDER (WO_ID);

prompt
prompt Creating table TB_WORK_ORDER_DETAIL_HIST
prompt ========================================
prompt
create table TB_WORK_ORDER_DETAIL_HIST
(
  h_wdid            NUMBER(16) not null,
  wd_id             NUMBER(16) not null,
  wo_id             NUMBER(16),
  wd_service_id     NUMBER(12) not null,
  wd_application_id NUMBER(16),
  wd_dp_deptid      NUMBER(16),
  wd_remark_id      NUMBER,
  wd_remark         VARCHAR2(500),
  wd_oth_remark     VARCHAR2(500),
  orgid             NUMBER(4) not null,
  user_id           NUMBER(7) not null,
  lang_id           NUMBER(7) not null,
  lmoddate          DATE not null,
  updated_by        NUMBER(7),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100),
  h_status          NVARCHAR2(1)
)
;
alter table TB_WORK_ORDER_DETAIL_HIST
  add constraint PK_HWDID primary key (H_WDID);

prompt
prompt Creating table TB_WORK_ORDER_HIST
prompt =================================
prompt
create table TB_WORK_ORDER_HIST
(
  h_woid              NUMBER(16) not null,
  wo_id               NUMBER(16) not null,
  wo_order_no         NVARCHAR2(25),
  wo_order_date       DATE not null,
  wo_service_id       NUMBER(12) not null,
  wo_application_id   NUMBER(16),
  wo_application_date DATE,
  wo_dept_id          NUMBER,
  wo_allocation       NUMBER,
  wo_print_flg        NVARCHAR2(1),
  orgid               NUMBER(4) not null,
  user_id             NUMBER(7) not null,
  lang_id             NUMBER(7) not null,
  lmoddate            DATE not null,
  updated_by          NUMBER(7),
  updated_date        DATE,
  lg_ip_mac           VARCHAR2(100),
  lg_ip_mac_upd       VARCHAR2(100),
  cs_ccn              NVARCHAR2(20),
  plum_id             NUMBER,
  h_status            NVARCHAR2(1)
)
;
alter table TB_WORK_ORDER_HIST
  add constraint PK_H_WOID primary key (H_WOID);


spool off

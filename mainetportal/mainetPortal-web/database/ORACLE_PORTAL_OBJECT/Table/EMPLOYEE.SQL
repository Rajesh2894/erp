-- Create table
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
);
-- Add comments to the columns 
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
  is 'Client Machine’s Login Name | IP Address | Physical Address';
comment on column EMPLOYEE.lg_ip_mac_upd
  is 'Updated Client Machine’s Login Name | IP Address | Physical Address';
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
-- Create/Recreate primary, unique and foreign key constraints 
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

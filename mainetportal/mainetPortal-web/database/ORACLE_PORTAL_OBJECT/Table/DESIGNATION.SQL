-- Create table
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
);
-- Add comments to the columns 
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
  is 'Client Machine’s Login Name | IP Address | Physical Address';
comment on column DESIGNATION.lg_ip_mac_upd
  is 'Updated Client Machine’s Login Name | IP Address | Physical Address';
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
-- Create/Recreate primary, unique and foreign key constraints 
alter table DESIGNATION
  add primary key (DSGID);
alter table DESIGNATION
  add constraint FK_LOC_ID foreign key (LOCID)
  references TB_LOCATION_MAS (LOC_ID)
  disable;

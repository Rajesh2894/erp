-- Create table
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
);
-- Add comments to the table 
comment on table TB_BANKACCOUNT
  is 'This table stores all the details of bank account prevails in each bank of KDMC.';
-- Add comments to the columns 
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
  is 'Client Machineâ€™s Login Name | IP Address | Physical Address';
comment on column TB_BANKACCOUNT.lg_ip_mac_upd
  is 'Updated Client Machineâ€™s Login Name | IP Address | Physical Address';
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
-- Create/Recreate indexes 
create index IX_BANKACCOUNT on TB_BANKACCOUNT (BM_BANKID);
create index IX_BNKACCNT_BNK_ID_ORG on TB_BANKACCOUNT (BM_BANKID, ORGID);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_BANKACCOUNT
  add constraint PK_BANKACCOUNT_ACCOUNTID primary key (BA_ACCOUNTID, ORGID);
alter table TB_BANKACCOUNT
  add constraint PK_BANKACCOUNT_ACCOUNTCODE unique (BA_ACCOUNTCODE, ORGID);
alter table TB_BANKACCOUNT
  add constraint FK_BANKACCOUNT_BANKID foreign key (BM_BANKID, ORGID)
  references TB_BANKMASTER (BM_BANKID, ORGID);

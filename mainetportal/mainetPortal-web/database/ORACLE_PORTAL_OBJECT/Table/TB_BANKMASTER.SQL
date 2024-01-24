-- Create table
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
);
-- Add comments to the table 
comment on table TB_BANKMASTER
  is 'Table is used to store Banks Information';
-- Add comments to the columns 
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
  is 'Client Machineâ€™s Login Name | IP Address | Physical Address';
comment on column TB_BANKMASTER.lg_ip_mac_upd
  is 'Updated Client Machineâ€™s Login Name | IP Address | Physical Address';
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
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_BANKMASTER
  add constraint PK_TB_BANKMASTER primary key (BM_BANKID, ORGID);
alter table TB_BANKMASTER
  add constraint FK_BANKMASTER_CB_BANKID foreign key (CB_BANKID)
  references TB_CUSTBANKS (CB_BANKID);

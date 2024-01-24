-- Create table
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
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_BANKACCOUNT_HIST
  add constraint PK_H_BAACCOUNTID primary key (H_BAACCOUNTID);

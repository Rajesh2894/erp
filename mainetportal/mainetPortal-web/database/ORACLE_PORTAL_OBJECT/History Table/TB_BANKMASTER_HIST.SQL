-- Create table
create table TB_BANKMASTER_HIST
(
  H_bmbankid        NUMBER(12) not null,
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
  H_STATUS          NVARCHAR2(1)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_BANKMASTER_HIST
  add constraint PK_H_bmbankid primary key (H_bmbankid);

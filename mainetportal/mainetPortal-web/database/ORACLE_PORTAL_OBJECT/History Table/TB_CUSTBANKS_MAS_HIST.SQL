-- Create table
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
);

-- Add comments to the table 
comment on table TB_CUSTBANKS_MAS_HIST
  is 'History Table of Customer Bank Master';
-- Add comments to the columns 
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
  is 'Client Machine’s Login Name | IP Address | Physical Address';
comment on column TB_CUSTBANKS_MAS_HIST.lg_ip_mac_upd
  is 'Updated Client Machine’s Login Name | IP Address | Physical Address';
comment on column TB_CUSTBANKS_MAS_HIST.flag
  is 'Updated or Deleted date';
comment on column TB_CUSTBANKS_MAS_HIST.lc_user_name
  is 'user name of local machine';
comment on column TB_CUSTBANKS_MAS_HIST.lc_ip_mc_clnt
  is 'Ip Address of local machine';
comment on column TB_CUSTBANKS_MAS_HIST.deletion_date
  is 'Deletion date';

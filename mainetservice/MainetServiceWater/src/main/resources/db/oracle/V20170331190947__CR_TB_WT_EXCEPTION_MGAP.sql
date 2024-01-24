--liquibase formatted sql
--changeset nilima:V20170331190947__CR_TB_WT_EXCEPTION_MGAP.sql
create table TB_WT_EXCEPTION_MGAP
( mgap_exgid    NUMBER(12) not null,
  cs_idn        NUMBER(12) not null,
  cpd_mtrstatus NUMBER(12),
  cpd_gap       NUMBER(12),
  mgap_from     DATE not null,
  mgap_to       DATE not null,
  mgap_bill_gen NVARCHAR2(1) default 'N',
  bm_idno       NUMBER(12),
  mgap_active   CHAR(1),
  orgid         NUMBER(4),
  created_by    NUMBER(12),
  created_date  DATE,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100)
);
comment on column TB_WT_EXCEPTION_MGAP.mgap_exgid
  is 'Primary Key';
comment on column TB_WT_EXCEPTION_MGAP.cs_idn
  is 'Connection number';
comment on column TB_WT_EXCEPTION_MGAP.cpd_mtrstatus
  is 'Meter Status';
comment on column TB_WT_EXCEPTION_MGAP.cpd_gap
  is 'Gap Code';
comment on column TB_WT_EXCEPTION_MGAP.mgap_from
  is 'Applicable From';
comment on column TB_WT_EXCEPTION_MGAP.mgap_to
  is 'Applicable To';
comment on column TB_WT_EXCEPTION_MGAP.mgap_bill_gen
  is 'Bill generated ("Y"->"Yes","N"->"No")';
comment on column TB_WT_EXCEPTION_MGAP.bm_idno
  is 'Bill number';
comment on column TB_WT_EXCEPTION_MGAP.mgap_active
  is 'Active->"Y'',"InActive->"N"';
comment on column TB_WT_EXCEPTION_MGAP.orgid
  is 'orgnisation id';
comment on column TB_WT_EXCEPTION_MGAP.created_by
  is 'user id who created the record';
comment on column TB_WT_EXCEPTION_MGAP.created_date
  is 'record creation date';
comment on column TB_WT_EXCEPTION_MGAP.updated_by
  is 'user id who update the data';
comment on column TB_WT_EXCEPTION_MGAP.updated_date
  is 'date on which data is going to update';
comment on column TB_WT_EXCEPTION_MGAP.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_WT_EXCEPTION_MGAP.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';

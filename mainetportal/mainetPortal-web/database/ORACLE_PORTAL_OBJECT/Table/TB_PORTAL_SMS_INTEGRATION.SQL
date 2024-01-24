-- Create table
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
);
-- Add comments to the columns 
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
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_PORTAL_SMS_INTEGRATION
  add constraint FK74B5FDD38B65205 foreign key (UPDATED_BY)
  references EMPLOYEE (EMPID);
alter table TB_PORTAL_SMS_INTEGRATION
  add constraint FK74B5FDD418DADB9 foreign key (USER_ID)
  references EMPLOYEE (EMPID);
alter table TB_PORTAL_SMS_INTEGRATION
  add constraint FK_SMS_ORGID foreign key (ORGID)
  references TB_ORGANISATION (ORGID);

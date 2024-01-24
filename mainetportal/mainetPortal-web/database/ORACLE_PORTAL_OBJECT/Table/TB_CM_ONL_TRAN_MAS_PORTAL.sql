-- Create table
create table TB_CM_ONL_TRAN_MAS_PORTAL
(
  tran_cm_id            NUMBER(12) not null,
  apm_application_id    NUMBER(16),
  apm_application_date  DATE,
  sm_service_id         NUMBER(12),
  cfc_mode              CHAR(5),
  t_desc                NVARCHAR2(500),
  send_url              VARCHAR2(100) not null,
  send_key              VARCHAR2(50) not null,
  send_amount           NUMBER not null,
  send_productinfo      VARCHAR2(200) not null,
  send_firstname        NVARCHAR2(500) not null,
  send_email            NVARCHAR2(200),
  send_phone            VARCHAR2(20),
  send_surl             VARCHAR2(500) not null,
  send_furl             VARCHAR2(500) not null,
  send_salt             VARCHAR2(100) not null,
  send_hash             VARCHAR2(4000) not null,
  recv_status           VARCHAR2(20),
  recv_bank_ref_num     VARCHAR2(100),
  recv_mihpayid         VARCHAR2(500),
  recv_net_amount_debit VARCHAR2(100),
  recv_errm             VARCHAR2(1000),
  recv_mode             VARCHAR2(10),
  orgid                 NUMBER(4) not null,
  user_id               NUMBER(7) not null,
  lang_id               NUMBER(7) not null,
  lmoddate              DATE not null,
  updated_by            NUMBER(7),
  updated_date          DATE,
  lg_ip_mac             VARCHAR2(100),
  lg_ip_mac_upd         VARCHAR2(100),
  pg_type               VARCHAR2(30),
  pg_source             NVARCHAR2(30),
  recv_hash             VARCHAR2(4000),
  finyear               NUMBER,
  form_name             VARCHAR2(50),
  java_home             VARCHAR2(500),
  redirect_url          VARCHAR2(500),
  menuprm1              VARCHAR2(5),
  menuprm2              VARCHAR2(5),
  form_post             CHAR(1),
  call_from             CHAR(1),
  trans_status          VARCHAR2(1)
)
tablespace MAINET_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table TB_CM_ONL_TRAN_MAS_PORTAL
  is 'This table is used to capture data before calling payment gateway';
-- Add comments to the columns 
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.tran_cm_id
  is 'Primary Key  ';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.apm_application_id
  is 'Application id  ';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.apm_application_date
  is 'Application Date  ';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.sm_service_id
  is 'Service ID  ';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.cfc_mode
  is 'cfc mode  ';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.t_desc
  is 'Any description can be entered ';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.send_url
  is 'Payment gateway calling url';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.send_key
  is 'Company Key id ';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.send_amount
  is 'Amount send for';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.send_productinfo
  is 'Product information';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.send_firstname
  is 'First Name';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.send_email
  is 'Email Address';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.send_phone
  is 'Phone number';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.send_surl
  is 'Success url sent';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.send_furl
  is 'Failure Url sent';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.send_salt
  is 'Salt id of the company';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.send_hash
  is 'Hash generated and sent';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.recv_status
  is 'Status received by payment gateway ';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.recv_bank_ref_num
  is 'Bank Reference number received';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.recv_mihpayid
  is 'PAyment Id sent by Payment Gateway  ';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.recv_net_amount_debit
  is 'Actual net amount debited';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.recv_errm
  is 'ANy error received';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.recv_mode
  is 'PAyment mode received';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.orgid
  is 'Organisation ID ';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.user_id
  is 'USer id';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.lang_id
  is 'LAnguage ID ';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.lmoddate
  is 'LAst modification date';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.updated_by
  is 'UPdated by ';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.updated_date
  is 'UPdated Date';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.lg_ip_mac
  is 'complete machine address of the end user';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.lg_ip_mac_upd
  is 'UPdate machine address of the end user';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.pg_type
  is 'Payment Gate way type';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.pg_source
  is 'Payment Gateway Source';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.recv_hash
  is 'Received hash key';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.finyear
  is 'Financial Year';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.form_name
  is 'Form from where it is called';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.java_home
  is 'Java home page url';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.redirect_url
  is 'ON success redirect for final posting of data';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.menuprm1
  is 'Menu praram 1 of the form';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.menuprm2
  is 'Menu praram 2 of the form';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.form_post
  is 'N if the form is not called and Y if the transaction is called after payu';
comment on column TB_CM_ONL_TRAN_MAS_PORTAL.call_from
  is 'D for oracle forms and J for java';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_CM_ONL_TRAN_MAS_PORTAL
  add constraint PK_TRAN_CM_ID primary key (TRAN_CM_ID, ORGID)
  using index 
  tablespace MAINET_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table TB_CM_ONL_TRAN_MAS_PORTAL
  add constraint FKBB441DC138B65205 foreign key (UPDATED_BY)
  references EMPLOYEE (EMPID);
alter table TB_CM_ONL_TRAN_MAS_PORTAL
  add constraint FKBB441DC16078ED5 foreign key (ORGID)
  references TB_ORGANISATION (ORGID);

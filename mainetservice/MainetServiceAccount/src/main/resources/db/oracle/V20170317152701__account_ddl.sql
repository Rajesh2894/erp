---------------------------------------------------------
-- Export file for user SERVICE1                       --
-- Created by kailash.agarwal on 3/17/2017, 2:41:32 PM --
---------------------------------------------------------

set define off
spool test2.log

prompt
prompt Creating table TB_AC_CODINGSTRUCTURE_MAS
prompt ========================================
prompt
create table TB_AC_CODINGSTRUCTURE_MAS
(
  codcof_id     NUMBER(12) not null,
  com_appflag   CHAR(1),
  com_cpd_id    NUMBER(12),
  com_chagflag  CHAR(1),
  com_desc      NVARCHAR2(200),
  define_onflag CHAR(1),
  cod_no_level  NUMBER(2),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(4) not null,
  lmoddate      DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100)
)
;
comment on column TB_AC_CODINGSTRUCTURE_MAS.codcof_id
  is 'Primary Key';
comment on column TB_AC_CODINGSTRUCTURE_MAS.com_appflag
  is 'Fund is applicable flag';
comment on column TB_AC_CODINGSTRUCTURE_MAS.com_cpd_id
  is 'cpd_id from tb_comparam_det /  Fund ,Function,Functionary ,Field,Account Head ¿ Primary Account Code,Account Head ¿ Secondary Account Code ';
comment on column TB_AC_CODINGSTRUCTURE_MAS.com_chagflag
  is 'Description of Component needs to be change  flag';
comment on column TB_AC_CODINGSTRUCTURE_MAS.com_desc
  is 'New Component description ';
comment on column TB_AC_CODINGSTRUCTURE_MAS.define_onflag
  is 'define component on State level flag - for State Y ';
comment on column TB_AC_CODINGSTRUCTURE_MAS.cod_no_level
  is 'No of level required ';
alter table TB_AC_CODINGSTRUCTURE_MAS
  add constraint PK_AC_CODCOF_ID primary key (CODCOF_ID);
alter table TB_AC_CODINGSTRUCTURE_MAS
  add constraint FK_AC_COM_CPD_ID foreign key (COM_CPD_ID)
  references TB_COMPARAM_DET (CPD_ID);

prompt
prompt Creating table TB_AC_CODINGSTRUCTURE_DET
prompt ========================================
prompt
create table TB_AC_CODINGSTRUCTURE_DET
(
  codcofdet_id    NUMBER(12) not null,
  codcof_id       NUMBER(12),
  cod_level       NUMBER(2),
  cod_description NVARCHAR2(500),
  cod_digits      NUMBER(2),
  orgid           NUMBER(4) not null,
  user_id         NUMBER(7) not null,
  lang_id         NUMBER(4) not null,
  lmoddate        DATE not null,
  updated_by      NUMBER(7),
  updated_date    DATE,
  lg_ip_mac       VARCHAR2(100),
  lg_ip_mac_upd   VARCHAR2(100)
)
;
comment on column TB_AC_CODINGSTRUCTURE_DET.codcofdet_id
  is 'Primary Key';
comment on column TB_AC_CODINGSTRUCTURE_DET.codcof_id
  is 'FK tb_ac_codingStructure_mas';
comment on column TB_AC_CODINGSTRUCTURE_DET.cod_level
  is 'Componet LEVEL 1,2,3,4';
comment on column TB_AC_CODINGSTRUCTURE_DET.cod_description
  is 'Componet description';
comment on column TB_AC_CODINGSTRUCTURE_DET.cod_digits
  is 'No. Of digits level level wise';
alter table TB_AC_CODINGSTRUCTURE_DET
  add constraint PK_AC_CODCOFDET_ID primary key (CODCOFDET_ID);
alter table TB_AC_CODINGSTRUCTURE_DET
  add constraint FK_AC_CODCOF_ID foreign key (CODCOF_ID)
  references TB_AC_CODINGSTRUCTURE_MAS (CODCOF_ID);

prompt
prompt Creating table TB_AC_FIELD_MASTER
prompt =================================
prompt
create table TB_AC_FIELD_MASTER
(
  field_id            NUMBER(12) not null,
  codcofdet_id        NUMBER(12),
  field_code          VARCHAR2(10),
  field_desc          NVARCHAR2(500),
  field_parent_id     NUMBER(12),
  field_compcode      VARCHAR2(20),
  orgid               NUMBER(4) not null,
  user_id             NUMBER(7) not null,
  lang_id             NUMBER(4) not null,
  lmoddate            DATE not null,
  updated_by          NUMBER(7),
  updated_date        DATE,
  lg_ip_mac           VARCHAR2(100),
  lg_ip_mac_upd       VARCHAR2(100),
  sup_field_parent_id NUMBER(12),
  field_status_cpd_id NUMBER(12)
)
;
comment on column TB_AC_FIELD_MASTER.field_id
  is 'Primary Key';
comment on column TB_AC_FIELD_MASTER.codcofdet_id
  is 'PK TB_AC_CODINGSTRUCTURE_DET';
comment on column TB_AC_FIELD_MASTER.field_code
  is 'FIELD Code';
comment on column TB_AC_FIELD_MASTER.field_desc
  is 'FIELD Description';
comment on column TB_AC_FIELD_MASTER.field_parent_id
  is 'PARENT_ID from FIELD_id ';
comment on column TB_AC_FIELD_MASTER.field_compcode
  is 'Field Composite Code';
comment on column TB_AC_FIELD_MASTER.orgid
  is 'Organization id';
comment on column TB_AC_FIELD_MASTER.user_id
  is 'User id';
comment on column TB_AC_FIELD_MASTER.lang_id
  is 'Language id';
comment on column TB_AC_FIELD_MASTER.lmoddate
  is 'Entry Date';
comment on column TB_AC_FIELD_MASTER.updated_by
  is 'Updated by';
comment on column TB_AC_FIELD_MASTER.updated_date
  is 'Updated Date';
comment on column TB_AC_FIELD_MASTER.lg_ip_mac
  is 'Client Machine¿s Login Name | IP Address | Physical Address';
comment on column TB_AC_FIELD_MASTER.lg_ip_mac_upd
  is 'Updated Client Machine¿s Login Name | IP Address | Physical Address';
comment on column TB_AC_FIELD_MASTER.field_status_cpd_id
  is 'Status, active, inactive, Prefix ACN';
alter table TB_AC_FIELD_MASTER
  add constraint PK_AC_FIELD_ID primary key (FIELD_ID);
alter table TB_AC_FIELD_MASTER
  add constraint FK_AC_FIELD_MASTCODFDET_ID foreign key (CODCOFDET_ID)
  references TB_AC_CODINGSTRUCTURE_DET (CODCOFDET_ID);
alter table TB_AC_FIELD_MASTER
  add constraint FK_FIELD_STATUS_CPD_ID foreign key (FIELD_STATUS_CPD_ID)
  references TB_COMPARAM_DET (CPD_ID);

prompt
prompt Creating table TB_AC_FUND_MASTER
prompt ================================
prompt
create table TB_AC_FUND_MASTER
(
  fund_id            NUMBER(12) not null,
  codcofdet_id       NUMBER(12),
  fund_code          VARCHAR2(10),
  fund_desc          NVARCHAR2(500),
  fund_compositecode VARCHAR2(20),
  fund_parent_id     NUMBER(12),
  orgid              NUMBER(4) not null,
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(4) not null,
  lmoddate           DATE not null,
  updated_by         NUMBER(7),
  updated_date       DATE,
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  fund_status_cpd_id NUMBER(12)
)
;
comment on column TB_AC_FUND_MASTER.fund_id
  is 'Primary Key';
comment on column TB_AC_FUND_MASTER.codcofdet_id
  is 'PK TB_AC_CODINGSTRUCTURE_DET';
comment on column TB_AC_FUND_MASTER.fund_code
  is 'Fund Code';
comment on column TB_AC_FUND_MASTER.fund_desc
  is 'Fund Description';
comment on column TB_AC_FUND_MASTER.fund_parent_id
  is 'PARENT_ID from FUND_ID ';
comment on column TB_AC_FUND_MASTER.orgid
  is 'Organization id';
comment on column TB_AC_FUND_MASTER.user_id
  is 'User id';
comment on column TB_AC_FUND_MASTER.lang_id
  is 'Language id';
comment on column TB_AC_FUND_MASTER.lmoddate
  is 'Entry Date';
comment on column TB_AC_FUND_MASTER.updated_by
  is 'Updated by';
comment on column TB_AC_FUND_MASTER.updated_date
  is 'Updated Date';
comment on column TB_AC_FUND_MASTER.lg_ip_mac
  is 'Client Machine¿s Login Name | IP Address | Physical Address';
comment on column TB_AC_FUND_MASTER.lg_ip_mac_upd
  is 'Updated Client Machine¿s Login Name | IP Address | Physical Address';
comment on column TB_AC_FUND_MASTER.fund_status_cpd_id
  is 'Status, active, inactive, Prefix ACN';
alter table TB_AC_FUND_MASTER
  add constraint PK_AC_FUND_ID primary key (FUND_ID);
alter table TB_AC_FUND_MASTER
  add constraint FK_AC_FUND_MASTERCODCOFDET_ID foreign key (CODCOFDET_ID)
  references TB_AC_CODINGSTRUCTURE_DET (CODCOFDET_ID);
alter table TB_AC_FUND_MASTER
  add constraint FK_FUND_STATUS_CPD_ID foreign key (FUND_STATUS_CPD_ID)
  references TB_COMPARAM_DET (CPD_ID);

prompt
prompt Creating table TB_AC_BANK_DEPOSITSLIP_MASTER
prompt ============================================
prompt
create table TB_AC_BANK_DEPOSITSLIP_MASTER
(
  dps_slipid            NUMBER(15) not null,
  dps_slipno            NUMBER(10) not null,
  dps_slipdate          DATE not null,
  dps_deposit_date      DATE,
  dps_fromdate          DATE,
  dps_todate            DATE,
  dp_deptid             NUMBER(12),
  dps_type_flag         CHAR(1),
  ba_accountid          NUMBER(12),
  fund_id               NUMBER(12),
  field_id              NUMBER(12),
  dps_remark            NVARCHAR2(500),
  dps_amount            NUMBER(15,2),
  auth_flag             CHAR(1),
  auth_by               NUMBER(7),
  auth_date             DATE,
  orgid                 NUMBER(4),
  created_by            NUMBER(7),
  created_date          DATE,
  lang_id               NUMBER(7),
  updated_by            NUMBER(7),
  updated_date          DATE,
  lg_ip_mac             VARCHAR2(100),
  lg_ip_mac_upd         VARCHAR2(100),
  fi04_n1               NUMBER(15),
  fi04_v1               NVARCHAR2(100),
  fi04_d1               DATE,
  fi04_lo1              CHAR(1),
  depositslip_type_flag CHAR(1),
  co_type_flag          CHAR(1)
)
;
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.dps_slipid
  is 'Primary key';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.dps_slipno
  is 'Deposit Number';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.dps_slipdate
  is 'Deposit Slip  Date';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.dps_deposit_date
  is 'Deposit  Date';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.dps_fromdate
  is 'From  Date';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.dps_todate
  is 'To  Date';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.dp_deptid
  is 'Department id - Fk Ref.-tb_department';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.dps_type_flag
  is 'Deposit flag C- Cash  and Q-Deposit cheque/DD';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.ba_accountid
  is 'Receipt Bank Account ID tb_bank_account';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.fund_id
  is 'Fund Master Reference key --TB_AC_FUND_MASTER';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.field_id
  is 'Field Master Reference key  --TB_AC_FIELD_MASTER';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.dps_remark
  is 'Remark ';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.dps_amount
  is 'Total Deposit Amount ';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.auth_flag
  is 'Authorization Flag ''Y'' for authorised';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.auth_by
  is 'Authorised User Identity';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.auth_date
  is 'Authorised Date';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.orgid
  is 'Organization Id';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.created_by
  is 'User Identity';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.created_date
  is 'Created Date';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.lang_id
  is 'Language Identity';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.updated_by
  is 'updated by';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.updated_date
  is 'updated Date';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.depositslip_type_flag
  is 'flag "D"-Deposit Slip
"C"- Contra ';
comment on column TB_AC_BANK_DEPOSITSLIP_MASTER.co_type_flag
  is 'CONTRA TYPE flag - T -TRANSFER ENTRY , C-CASH WITHDRAWAL ENTRY,  D-CASH DEPOSIT ENTRY';
alter table TB_AC_BANK_DEPOSITSLIP_MASTER
  add constraint PK_DPS_SLIPID primary key (DPS_SLIPID);
alter table TB_AC_BANK_DEPOSITSLIP_MASTER
  add constraint FK_AC_DEPOSLIP_FIELD_ID foreign key (FIELD_ID)
  references TB_AC_FIELD_MASTER (FIELD_ID);
alter table TB_AC_BANK_DEPOSITSLIP_MASTER
  add constraint FK_AC_DEPOSLIP_FUND_ID_PAY foreign key (FUND_ID)
  references TB_AC_FUND_MASTER (FUND_ID);
alter table TB_AC_BANK_DEPOSITSLIP_MASTER
  add constraint FK_AC_DEPO_B_ACCOUNTID foreign key (BA_ACCOUNTID)
  references TB_BANK_ACCOUNT (BA_ACCOUNTID);
alter table TB_AC_BANK_DEPOSITSLIP_MASTER
  add constraint FK_AC_DEPO_DEPARTMENT foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID)
  disable;

prompt
prompt Creating table TB_AC_BANK_DEPOSITSLIP_DENOM
prompt ===========================================
prompt
create table TB_AC_BANK_DEPOSITSLIP_DENOM
(
  cashdep_det_id NUMBER(12) not null,
  dps_slipid     NUMBER(15),
  cpd_denomid    NUMBER(12),
  denom_count    NUMBER(12,2),
  orgid          NUMBER(4),
  created_by     NUMBER(7),
  created_date   DATE,
  lang_id        NUMBER(7),
  updated_by     NUMBER(7),
  updated_date   DATE,
  lg_ip_mac      VARCHAR2(100),
  lg_ip_mac_upd  VARCHAR2(100),
  fi04_n1        NUMBER(15),
  fi04_v1        NVARCHAR2(100),
  fi04_d1        DATE,
  fi04_lo1       CHAR(1)
)
;
comment on column TB_AC_BANK_DEPOSITSLIP_DENOM.cashdep_det_id
  is 'Cash Deposit Detail Id';
comment on column TB_AC_BANK_DEPOSITSLIP_DENOM.dps_slipid
  is 'Fk. Ref. id Tb_Ac_bank_deposlip_master ';
comment on column TB_AC_BANK_DEPOSITSLIP_DENOM.cpd_denomid
  is 'Deposit Denomination Id cpd id "DEN" Prefix';
comment on column TB_AC_BANK_DEPOSITSLIP_DENOM.denom_count
  is 'Denomination Count';
comment on column TB_AC_BANK_DEPOSITSLIP_DENOM.orgid
  is 'Organization Id';
comment on column TB_AC_BANK_DEPOSITSLIP_DENOM.created_by
  is 'User Identity';
comment on column TB_AC_BANK_DEPOSITSLIP_DENOM.created_date
  is 'Created Date';
comment on column TB_AC_BANK_DEPOSITSLIP_DENOM.lang_id
  is 'Language Identity';
comment on column TB_AC_BANK_DEPOSITSLIP_DENOM.updated_by
  is 'updated by';
comment on column TB_AC_BANK_DEPOSITSLIP_DENOM.updated_date
  is 'updated Date';
comment on column TB_AC_BANK_DEPOSITSLIP_DENOM.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_BANK_DEPOSITSLIP_DENOM.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_BANK_DEPOSITSLIP_DENOM.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_BANK_DEPOSITSLIP_DENOM.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_BANK_DEPOSITSLIP_DENOM.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_BANK_DEPOSITSLIP_DENOM.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
alter table TB_AC_BANK_DEPOSITSLIP_DENOM
  add constraint PK_CASHDEP_DET_ID primary key (CASHDEP_DET_ID);
alter table TB_AC_BANK_DEPOSITSLIP_DENOM
  add constraint FK_AC_DEPOSLIP_DENO_DPS_SLIPID foreign key (DPS_SLIPID)
  references TB_AC_BANK_DEPOSITSLIP_MASTER (DPS_SLIPID);
alter table TB_AC_BANK_DEPOSITSLIP_DENOM
  add constraint FK_BANKDEPOCPD_DENOMID foreign key (CPD_DENOMID)
  references TB_COMPARAM_DET (CPD_ID);

prompt
prompt Creating table TB_AC_TENDER_MASTER
prompt ==================================
prompt
create table TB_AC_TENDER_MASTER
(
  tr_tender_id      NUMBER(12) not null,
  tr_entry_date     DATE not null,
  tr_tender_no      NVARCHAR2(20) not null,
  tr_tender_date    DATE not null,
  tr_type_cpd_id    NUMBER(12) not null,
  tr_nameofwork     NVARCHAR2(500),
  tr_emd_amt        NUMBER(15,2),
  dp_deptid         NUMBER(12) not null,
  vm_vendorid       NUMBER(12) not null,
  specialconditions NVARCHAR2(500),
  tr_tender_amount  NUMBER(15,2) not null,
  tr_proposal_no    NVARCHAR2(20) not null,
  tr_proposal_date  DATE not null,
  orgid             NUMBER(4) not null,
  created_by        NUMBER(7) not null,
  created_date      DATE not null,
  updated_by        NUMBER(7),
  updated_date      DATE,
  lang_id           NUMBER(7) not null,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100),
  fi04_n1           NUMBER(15),
  fi04_v1           NVARCHAR2(100),
  fi04_d1           DATE,
  fi04_lo1          CHAR(1)
)
;
comment on table TB_AC_TENDER_MASTER
  is 'This table stores Tender details which contains tender informations given by concerned departments to Accounts Department.';
comment on column TB_AC_TENDER_MASTER.tr_tender_id
  is 'Primary Key';
comment on column TB_AC_TENDER_MASTER.tr_entry_date
  is 'Entry Date';
comment on column TB_AC_TENDER_MASTER.tr_tender_no
  is 'Tender Number';
comment on column TB_AC_TENDER_MASTER.tr_tender_date
  is 'Tender Date';
comment on column TB_AC_TENDER_MASTER.tr_type_cpd_id
  is 'User has to select  one from : Works / Supply 
';
comment on column TB_AC_TENDER_MASTER.tr_nameofwork
  is 'Nature or Purpose of Proposal';
comment on column TB_AC_TENDER_MASTER.tr_emd_amt
  is 'Amount -Earnest Money Deposit';
comment on column TB_AC_TENDER_MASTER.dp_deptid
  is 'Department ID';
comment on column TB_AC_TENDER_MASTER.vm_vendorid
  is 'Vendor ID';
comment on column TB_AC_TENDER_MASTER.specialconditions
  is 'Special Conditions';
comment on column TB_AC_TENDER_MASTER.tr_tender_amount
  is 'Tender Amount';
comment on column TB_AC_TENDER_MASTER.tr_proposal_no
  is 'Proposal Number';
comment on column TB_AC_TENDER_MASTER.tr_proposal_date
  is 'Proposal Date';
comment on column TB_AC_TENDER_MASTER.orgid
  is 'Organisation id';
comment on column TB_AC_TENDER_MASTER.created_by
  is 'Created User Identity';
comment on column TB_AC_TENDER_MASTER.created_date
  is 'Created Date';
comment on column TB_AC_TENDER_MASTER.updated_by
  is 'User id who update the data';
comment on column TB_AC_TENDER_MASTER.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_TENDER_MASTER.lang_id
  is 'Language Identity';
comment on column TB_AC_TENDER_MASTER.lg_ip_mac
  is 'Client Machine is Login Name | IP Address | Physical Address';
comment on column TB_AC_TENDER_MASTER.lg_ip_mac_upd
  is 'Updated Client Machine is Login Name | IP Address | Physical Address';
comment on column TB_AC_TENDER_MASTER.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_TENDER_MASTER.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_TENDER_MASTER.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_TENDER_MASTER.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
alter table TB_AC_TENDER_MASTER
  add constraint PK_TR_TENDERID primary key (TR_TENDER_ID);
alter table TB_AC_TENDER_MASTER
  add constraint FK_TR_DEPTID foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);
alter table TB_AC_TENDER_MASTER
  add constraint FK_TR_TYPE_CPD_ID foreign key (TR_TYPE_CPD_ID)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_TENDER_MASTER
  add constraint FK_TR_VENDORID foreign key (VM_VENDORID)
  references TB_VENDORMASTER (VM_VENDORID);

prompt
prompt Creating table TB_AC_LIABILITY_BOOKING
prompt ======================================
prompt
create table TB_AC_LIABILITY_BOOKING
(
  lb_liability_id NUMBER(12) not null,
  lb_liability_no NUMBER(12),
  lb_entry_date   DATE,
  tr_tender_id    NUMBER(12),
  orgid           NUMBER(4) not null,
  created_by      NUMBER(7) not null,
  created_date    DATE not null,
  updated_by      NUMBER(7),
  updated_date    DATE,
  lang_id         NUMBER(7) not null,
  lg_ip_mac       VARCHAR2(100),
  lg_ip_mac_upd   VARCHAR2(100),
  fi04_n1         NUMBER(15),
  fi04_v1         NVARCHAR2(100),
  fi04_d1         DATE,
  fi04_lo1        CHAR(1)
)
;
comment on table TB_AC_LIABILITY_BOOKING
  is 'This table stores Tender details which contains tender informations given by concerned departments to Accounts Department.';
comment on column TB_AC_LIABILITY_BOOKING.lb_liability_id
  is 'Primary Key';
comment on column TB_AC_LIABILITY_BOOKING.lb_entry_date
  is 'Entry Date';
comment on column TB_AC_LIABILITY_BOOKING.orgid
  is 'Organisation id';
comment on column TB_AC_LIABILITY_BOOKING.created_by
  is 'Created User Identity';
comment on column TB_AC_LIABILITY_BOOKING.created_date
  is 'Created Date';
comment on column TB_AC_LIABILITY_BOOKING.updated_by
  is 'User id who update the data';
comment on column TB_AC_LIABILITY_BOOKING.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_LIABILITY_BOOKING.lang_id
  is 'Language Identity';
comment on column TB_AC_LIABILITY_BOOKING.lg_ip_mac
  is 'Client Machine is Login Name | IP Address | Physical Address';
comment on column TB_AC_LIABILITY_BOOKING.lg_ip_mac_upd
  is 'Updated Client Machine is Login Name | IP Address | Physical Address';
comment on column TB_AC_LIABILITY_BOOKING.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_LIABILITY_BOOKING.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_LIABILITY_BOOKING.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_LIABILITY_BOOKING.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
alter table TB_AC_LIABILITY_BOOKING
  add constraint PK_LB_LIABILITY_ID primary key (LB_LIABILITY_ID);
alter table TB_AC_LIABILITY_BOOKING
  add constraint FK_TR_TENDER_ID foreign key (TR_TENDER_ID)
  references TB_AC_TENDER_MASTER (TR_TENDER_ID);

prompt
prompt Creating table TB_AC_BILL_MAS
prompt =============================
prompt
create table TB_AC_BILL_MAS
(
  bm_id                NUMBER(12) not null,
  bm_billtype_cpd_id   NUMBER(12),
  bm_billno            NVARCHAR2(20) not null,
  bm_entrydate         DATE not null,
  dp_deptid            NUMBER(12),
  vm_vendorid          NUMBER(12),
  vm_vendorname        NVARCHAR2(200),
  bm_invoicenumber     NVARCHAR2(20),
  bm_invoicedate       DATE,
  bm_invoicevalue      NUMBER(15,2),
  bm_w_p_order_number  NVARCHAR2(20),
  bm_w_p_order_date    DATE,
  bm_resolution_number NVARCHAR2(20),
  bm_resolution_date   DATE,
  bm_narration         NVARCHAR2(700) not null,
  bm_tot_amt           NUMBER(15,2),
  bm_bal_amt           NUMBER(15,2),
  lb_liability_id      NUMBER(12),
  dep_id               NUMBER(12),
  lps_loanid           NUMBER(12),
  advtype_cpd_id       NUMBER(12),
  checker_autho        CHAR(1),
  checker_remarks      NVARCHAR2(500),
  checker_user         NVARCHAR2(100),
  checker_date         DATE,
  orgid                NUMBER(4) not null,
  created_by           NUMBER(7) not null,
  created_date         DATE not null,
  updated_by           NUMBER(7),
  updated_date         DATE,
  lang_id              NUMBER(7) not null,
  lg_ip_mac            VARCHAR2(100),
  lg_ip_mac_upd        VARCHAR2(100),
  fi04_n1              NUMBER(15),
  fi04_v1              NVARCHAR2(100),
  fi04_d1              DATE,
  fi04_lo1             CHAR(1),
  field_id             NUMBER(12)
)
;
comment on table TB_AC_BILL_MAS
  is 'This table contains Heads of Income Tax, Surcharge and Work Contract Tax, which will be used for TDS payment to government via. payment voucher.';
comment on column TB_AC_BILL_MAS.bm_id
  is 'Primary Key';
comment on column TB_AC_BILL_MAS.bm_billtype_cpd_id
  is 'Prefix - ABT';
comment on column TB_AC_BILL_MAS.bm_billno
  is 'Financial year wise unique bill number';
comment on column TB_AC_BILL_MAS.bm_entrydate
  is 'Transaction entry date';
comment on column TB_AC_BILL_MAS.dp_deptid
  is 'Department name';
comment on column TB_AC_BILL_MAS.vm_vendorid
  is 'Vendor ID';
comment on column TB_AC_BILL_MAS.vm_vendorname
  is 'Vendor name';
comment on column TB_AC_BILL_MAS.bm_invoicenumber
  is 'Vendor bill number';
comment on column TB_AC_BILL_MAS.bm_invoicedate
  is 'Vendor invoice/bill date';
comment on column TB_AC_BILL_MAS.bm_invoicevalue
  is 'Vendro invoice/bill value';
comment on column TB_AC_BILL_MAS.bm_w_p_order_number
  is 'Work order/Purchase order number';
comment on column TB_AC_BILL_MAS.bm_w_p_order_date
  is 'Work order/purchase order date';
comment on column TB_AC_BILL_MAS.bm_resolution_number
  is 'Resolution / administrative order number.';
comment on column TB_AC_BILL_MAS.bm_resolution_date
  is 'Resolution / administrative order date.';
comment on column TB_AC_BILL_MAS.bm_narration
  is 'Bill Particulars / Narration';
comment on column TB_AC_BILL_MAS.bm_tot_amt
  is 'Total bill amount';
comment on column TB_AC_BILL_MAS.bm_bal_amt
  is 'Balance amount';
comment on column TB_AC_BILL_MAS.lb_liability_id
  is 'Liability ID';
comment on column TB_AC_BILL_MAS.dep_id
  is 'Deposit ID';
comment on column TB_AC_BILL_MAS.lps_loanid
  is 'Loan ID';
comment on column TB_AC_BILL_MAS.advtype_cpd_id
  is 'Advance type';
comment on column TB_AC_BILL_MAS.checker_autho
  is 'Authorization flag (Y for authorized)';
comment on column TB_AC_BILL_MAS.checker_remarks
  is 'Authorization remarks';
comment on column TB_AC_BILL_MAS.checker_user
  is 'Authorized by';
comment on column TB_AC_BILL_MAS.checker_date
  is 'Authorization date';
comment on column TB_AC_BILL_MAS.orgid
  is 'Organisation id';
comment on column TB_AC_BILL_MAS.created_by
  is 'Created User Identity';
comment on column TB_AC_BILL_MAS.created_date
  is 'Created Date';
comment on column TB_AC_BILL_MAS.updated_by
  is 'User id who update the data';
comment on column TB_AC_BILL_MAS.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_BILL_MAS.lang_id
  is 'Language Identity';
comment on column TB_AC_BILL_MAS.lg_ip_mac
  is 'Client Machine is Login Name | IP Address | Physical Address';
comment on column TB_AC_BILL_MAS.lg_ip_mac_upd
  is 'Updated Client Machine is Login Name | IP Address | Physical Address';
comment on column TB_AC_BILL_MAS.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_BILL_MAS.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_BILL_MAS.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_BILL_MAS.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
comment on column TB_AC_BILL_MAS.field_id
  is 'Field Master Reference key  --TB_AC_FIELD_MASTER';
alter table TB_AC_BILL_MAS
  add constraint PK_BM_ID primary key (BM_ID);
alter table TB_AC_BILL_MAS
  add constraint FKBILL_MAS_FIELD_ID foreign key (FIELD_ID)
  references TB_AC_FIELD_MASTER (FIELD_ID);
alter table TB_AC_BILL_MAS
  add constraint FK_BILL_DP_DEPTID foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);
alter table TB_AC_BILL_MAS
  add constraint FK_BILL_LB_LIABILITY_ID foreign key (LB_LIABILITY_ID)
  references TB_AC_LIABILITY_BOOKING (LB_LIABILITY_ID);
alter table TB_AC_BILL_MAS
  add constraint FK_BILL_VM_VENDORID foreign key (VM_VENDORID)
  references TB_VENDORMASTER (VM_VENDORID);
alter table TB_AC_BILL_MAS
  add constraint FK_BM_BILLTYPE_CPD_ID foreign key (BM_BILLTYPE_CPD_ID)
  references TB_COMPARAM_DET (CPD_ID);

prompt
prompt Creating table TB_AC_FUNCTION_MASTER
prompt ====================================
prompt
create table TB_AC_FUNCTION_MASTER
(
  function_id            NUMBER(12) not null,
  codcofdet_id           NUMBER(12),
  function_code          VARCHAR2(10),
  function_desc          NVARCHAR2(500),
  function_parent_id     NUMBER(12),
  function_compcode      VARCHAR2(20),
  orgid                  NUMBER(4) not null,
  user_id                NUMBER(7) not null,
  lang_id                NUMBER(4) not null,
  lmoddate               DATE not null,
  updated_by             NUMBER(7),
  updated_date           DATE,
  lg_ip_mac              VARCHAR2(100),
  lg_ip_mac_upd          VARCHAR2(100),
  function_status_cpd_id NUMBER(12)
)
;
comment on column TB_AC_FUNCTION_MASTER.function_id
  is 'Primary Key';
comment on column TB_AC_FUNCTION_MASTER.codcofdet_id
  is 'PK TB_AC_CODINGSTRUCTURE_DET';
comment on column TB_AC_FUNCTION_MASTER.function_code
  is 'FUNCTION Code';
comment on column TB_AC_FUNCTION_MASTER.function_desc
  is 'FUNCTION Description';
comment on column TB_AC_FUNCTION_MASTER.function_parent_id
  is 'PARENT_ID from FUNCTION_id ';
comment on column TB_AC_FUNCTION_MASTER.function_compcode
  is 'FUNCTION Composite Code';
comment on column TB_AC_FUNCTION_MASTER.orgid
  is 'Organization id';
comment on column TB_AC_FUNCTION_MASTER.user_id
  is 'User id';
comment on column TB_AC_FUNCTION_MASTER.lang_id
  is 'Language id';
comment on column TB_AC_FUNCTION_MASTER.lmoddate
  is 'Entry Date';
comment on column TB_AC_FUNCTION_MASTER.updated_by
  is 'Updated by';
comment on column TB_AC_FUNCTION_MASTER.updated_date
  is 'Updated Date';
comment on column TB_AC_FUNCTION_MASTER.lg_ip_mac
  is 'Client Machine¿s Login Name | IP Address | Physical Address';
comment on column TB_AC_FUNCTION_MASTER.lg_ip_mac_upd
  is 'Updated Client Machine¿s Login Name | IP Address | Physical Address';
comment on column TB_AC_FUNCTION_MASTER.function_status_cpd_id
  is 'Status, active, inactive, Prefix ACN';
alter table TB_AC_FUNCTION_MASTER
  add constraint PK_AC_FUNCTION_ID primary key (FUNCTION_ID);
alter table TB_AC_FUNCTION_MASTER
  add constraint FK_AC_FUNCTION_MASTCODFDET_ID foreign key (CODCOFDET_ID)
  references TB_AC_CODINGSTRUCTURE_DET (CODCOFDET_ID)
  disable;
alter table TB_AC_FUNCTION_MASTER
  add constraint FK_FUNCTION_STATUS_CPD_ID foreign key (FUNCTION_STATUS_CPD_ID)
  references TB_COMPARAM_DET (CPD_ID);

prompt
prompt Creating table TB_AC_PRIMARYHEAD_MASTER
prompt =======================================
prompt
create table TB_AC_PRIMARYHEAD_MASTER
(
  pac_head_id                  NUMBER(12) not null,
  codcofdet_id                 NUMBER(12),
  pac_head_code                VARCHAR2(10),
  pac_head_desc                NVARCHAR2(500),
  pac_head_parent_id           NUMBER(12),
  pac_head_compo_code          VARCHAR2(20),
  orgid                        NUMBER(4) not null,
  user_id                      NUMBER(7) not null,
  lang_id                      NUMBER(4) not null,
  lmoddate                     DATE not null,
  updated_by                   NUMBER(7),
  updated_date                 DATE,
  lg_ip_mac                    VARCHAR2(100),
  lg_ip_mac_upd                VARCHAR2(100),
  cpd_id_acheadtypes           NUMBER(12),
  pac_status_cpd_id            NUMBER(12),
  cpd_id_account_type          NUMBER(12),
  cpd_id_paymode               NUMBER(12),
  secondaryhead_subledger_flag CHAR(1),
  cpd_id_bank_type             NUMBER(12)
)
;
comment on column TB_AC_PRIMARYHEAD_MASTER.pac_head_id
  is 'Primary Key';
comment on column TB_AC_PRIMARYHEAD_MASTER.codcofdet_id
  is 'PK TB_AC_CODINGSTRUCTURE_DET';
comment on column TB_AC_PRIMARYHEAD_MASTER.pac_head_code
  is 'PRIMARY  ACCOUNT Code';
comment on column TB_AC_PRIMARYHEAD_MASTER.pac_head_desc
  is 'PRIMARY  ACCOUNT Description';
comment on column TB_AC_PRIMARYHEAD_MASTER.pac_head_parent_id
  is 'PARENT_ID from pac_head_id ';
comment on column TB_AC_PRIMARYHEAD_MASTER.pac_head_compo_code
  is 'Composite Code';
comment on column TB_AC_PRIMARYHEAD_MASTER.orgid
  is 'Organization id';
comment on column TB_AC_PRIMARYHEAD_MASTER.user_id
  is 'User id';
comment on column TB_AC_PRIMARYHEAD_MASTER.lang_id
  is 'Language id';
comment on column TB_AC_PRIMARYHEAD_MASTER.lmoddate
  is 'Entry Date';
comment on column TB_AC_PRIMARYHEAD_MASTER.updated_by
  is 'Updated by';
comment on column TB_AC_PRIMARYHEAD_MASTER.updated_date
  is 'Updated Date';
comment on column TB_AC_PRIMARYHEAD_MASTER.lg_ip_mac
  is 'Client Machine¿s Login Name | IP Address | Physical Address';
comment on column TB_AC_PRIMARYHEAD_MASTER.lg_ip_mac_upd
  is 'Updated Client Machine¿s Login Name | IP Address | Physical Address';
comment on column TB_AC_PRIMARYHEAD_MASTER.cpd_id_acheadtypes
  is 'fk ref. tb_comparam_det Prefix - COA';
comment on column TB_AC_PRIMARYHEAD_MASTER.pac_status_cpd_id
  is '  status, active, inactive, prefix acn';
comment on column TB_AC_PRIMARYHEAD_MASTER.cpd_id_account_type
  is 'Prefix SAM';
comment on column TB_AC_PRIMARYHEAD_MASTER.cpd_id_paymode
  is 'Prefix PAY';
comment on column TB_AC_PRIMARYHEAD_MASTER.secondaryhead_subledger_flag
  is 'Group behaves like a sub- ledger';
comment on column TB_AC_PRIMARYHEAD_MASTER.cpd_id_bank_type
  is 'Prefix BAT';
alter table TB_AC_PRIMARYHEAD_MASTER
  add constraint PK_AC_PAC_HEAD primary key (PAC_HEAD_ID);
alter table TB_AC_PRIMARYHEAD_MASTER
  add constraint FK_AC_PRIMARYHEADCODFDET_ID foreign key (CODCOFDET_ID)
  references TB_AC_CODINGSTRUCTURE_DET (CODCOFDET_ID);
alter table TB_AC_PRIMARYHEAD_MASTER
  add constraint FK_AC_PRIMHPAC_STATUS_CPD_ID foreign key (PAC_STATUS_CPD_ID)
  references TB_COMPARAM_DET (CPD_ID);

prompt
prompt Creating table TB_AC_SECONDARYHEAD_MASTER
prompt =========================================
prompt
create table TB_AC_SECONDARYHEAD_MASTER
(
  sac_head_id                 NUMBER(12) not null,
  codcofdet_id                NUMBER(12),
  pac_head_id                 NUMBER(12),
  sac_leddger_type_cpd_id     NUMBER(12),
  sac_sub_leddger_type_cpd_id NUMBER(12),
  vm_vendorid                 NUMBER(12),
  ba_accountid                NUMBER(12),
  sac_head_code               VARCHAR2(10),
  sac_head_desc               NVARCHAR2(500),
  orgid                       NUMBER(4) not null,
  user_id                     NUMBER(7) not null,
  lang_id                     NUMBER(4) not null,
  lmoddate                    DATE not null,
  updated_by                  NUMBER(7),
  updated_date                DATE,
  lg_ip_mac                   VARCHAR2(100),
  lg_ip_mac_upd               VARCHAR2(100),
  fi04_n1                     NUMBER(15),
  fi04_v1                     NVARCHAR2(100),
  fi04_d1                     DATE,
  fi04_lo1                    CHAR(1),
  status_cpd_id               NUMBER(12),
  old_alias_ledger_code       NVARCHAR2(50)
)
;
comment on column TB_AC_SECONDARYHEAD_MASTER.sac_head_id
  is 'Primary Key';
comment on column TB_AC_SECONDARYHEAD_MASTER.codcofdet_id
  is 'FK TB_AC_CODINGSTRUCTURE_DET';
comment on column TB_AC_SECONDARYHEAD_MASTER.pac_head_id
  is 'FK TB_AC_PRIMARYHEAD_MASTER';
comment on column TB_AC_SECONDARYHEAD_MASTER.sac_leddger_type_cpd_id
  is 'CPD_id Ledger type Bank/Vendor/other Prefix- FTY';
comment on column TB_AC_SECONDARYHEAD_MASTER.sac_sub_leddger_type_cpd_id
  is 'CPD_id Ledger type - SubLedger type for other';
comment on column TB_AC_SECONDARYHEAD_MASTER.vm_vendorid
  is 'FK TB_AC_VENDORMASTER';
comment on column TB_AC_SECONDARYHEAD_MASTER.ba_accountid
  is 'FK  tb_bank_account';
comment on column TB_AC_SECONDARYHEAD_MASTER.sac_head_code
  is 'SECONDARY  ACCOUNT Code';
comment on column TB_AC_SECONDARYHEAD_MASTER.sac_head_desc
  is 'SECONDARY  ACCOUNT Description';
comment on column TB_AC_SECONDARYHEAD_MASTER.orgid
  is 'Organization id';
comment on column TB_AC_SECONDARYHEAD_MASTER.user_id
  is 'User id';
comment on column TB_AC_SECONDARYHEAD_MASTER.lang_id
  is 'Language id';
comment on column TB_AC_SECONDARYHEAD_MASTER.lmoddate
  is 'Entry Date';
comment on column TB_AC_SECONDARYHEAD_MASTER.updated_by
  is 'Updated by';
comment on column TB_AC_SECONDARYHEAD_MASTER.updated_date
  is 'Updated Date';
comment on column TB_AC_SECONDARYHEAD_MASTER.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_SECONDARYHEAD_MASTER.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_SECONDARYHEAD_MASTER.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_SECONDARYHEAD_MASTER.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_SECONDARYHEAD_MASTER.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_SECONDARYHEAD_MASTER.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
comment on column TB_AC_SECONDARYHEAD_MASTER.status_cpd_id
  is 'status cpd_id   Prefix "ACN"';
comment on column TB_AC_SECONDARYHEAD_MASTER.old_alias_ledger_code
  is 'User to enter old alias ledger code';
alter table TB_AC_SECONDARYHEAD_MASTER
  add constraint PK_AC_SAC_HEAD_ID primary key (SAC_HEAD_ID);
alter table TB_AC_SECONDARYHEAD_MASTER
  add constraint FK_AC_BANKACCOUNT_ID foreign key (BA_ACCOUNTID)
  references TB_BANK_ACCOUNT (BA_ACCOUNTID);
alter table TB_AC_SECONDARYHEAD_MASTER
  add constraint FK_AC_PAC_HEAD_ID_SE foreign key (PAC_HEAD_ID)
  references TB_AC_PRIMARYHEAD_MASTER (PAC_HEAD_ID);
alter table TB_AC_SECONDARYHEAD_MASTER
  add constraint FK_AC_SECONDARYHEADMASTER_ID foreign key (CODCOFDET_ID)
  references TB_AC_CODINGSTRUCTURE_DET (CODCOFDET_ID);
alter table TB_AC_SECONDARYHEAD_MASTER
  add constraint FK_AC_VENDORID foreign key (VM_VENDORID)
  references TB_VENDORMASTER (VM_VENDORID);

prompt
prompt Creating table TB_AC_BUDGETCODE_MAS
prompt ===================================
prompt
create table TB_AC_BUDGETCODE_MAS
(
  budgetcode_id         NUMBER(12) not null,
  cpd_id_budget_type    NUMBER(12),
  cpd_id_budget_subtype NUMBER(12),
  fund_id               NUMBER(12),
  field_id              NUMBER(12),
  function_id           NUMBER(12),
  sac_head_id           NUMBER(12),
  dp_deptid             NUMBER(12),
  budget_code           NVARCHAR2(500),
  cpd_id_status_flg     NUMBER(12) not null,
  orgid                 NUMBER(4) not null,
  created_by            NUMBER(12),
  lang_id               NUMBER(7),
  created_date          DATE,
  updated_by            NUMBER(12),
  updated_date          DATE,
  lg_ip_mac             VARCHAR2(100),
  lg_ip_mac_upd         VARCHAR2(100),
  fi04_n1               NUMBER(15),
  fi04_v1               NVARCHAR2(100),
  fi04_d1               DATE,
  fi04_lo1              CHAR(1)
)
;
comment on column TB_AC_BUDGETCODE_MAS.budgetcode_id
  is 'Primary Key';
comment on column TB_AC_BUDGETCODE_MAS.cpd_id_budget_type
  is 'PREFIX ''REX''';
comment on column TB_AC_BUDGETCODE_MAS.cpd_id_budget_subtype
  is 'PREFIX ''FTP''';
comment on column TB_AC_BUDGETCODE_MAS.fund_id
  is 'Fund Master Reference key --TB_AC_FUND_MASTER';
comment on column TB_AC_BUDGETCODE_MAS.field_id
  is 'Field Master Reference key  --TB_AC_FIELD_MASTER';
comment on column TB_AC_BUDGETCODE_MAS.function_id
  is 'Function  Master Reference key --TB_AC_FUNCTION_MASTER';
comment on column TB_AC_BUDGETCODE_MAS.sac_head_id
  is 'Secondary Master Reference key -- tb_ac_secondaryhead_master';
comment on column TB_AC_BUDGETCODE_MAS.dp_deptid
  is 'fk - TB_DEPARTMENT';
comment on column TB_AC_BUDGETCODE_MAS.budget_code
  is 'Budget Description';
comment on column TB_AC_BUDGETCODE_MAS.cpd_id_status_flg
  is 'Prefix - ''ACN''';
comment on column TB_AC_BUDGETCODE_MAS.orgid
  is 'Organisation id';
comment on column TB_AC_BUDGETCODE_MAS.created_by
  is 'User Identity';
comment on column TB_AC_BUDGETCODE_MAS.lang_id
  is 'Language Identity';
comment on column TB_AC_BUDGETCODE_MAS.created_date
  is 'Last Modification Date';
comment on column TB_AC_BUDGETCODE_MAS.updated_by
  is 'User id who update the data';
comment on column TB_AC_BUDGETCODE_MAS.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_BUDGETCODE_MAS.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_BUDGETCODE_MAS.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_BUDGETCODE_MAS.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_BUDGETCODE_MAS.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_BUDGETCODE_MAS.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_BUDGETCODE_MAS.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
alter table TB_AC_BUDGETCODE_MAS
  add constraint PK_AC_BUDGETCODE_ID primary key (BUDGETCODE_ID);
alter table TB_AC_BUDGETCODE_MAS
  add constraint FK_BUDGETCODE_DP_DEPTID foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);
alter table TB_AC_BUDGETCODE_MAS
  add constraint FK_BUDGETCODE_FIELD_ID foreign key (FIELD_ID)
  references TB_AC_FIELD_MASTER (FIELD_ID);
alter table TB_AC_BUDGETCODE_MAS
  add constraint FK_BUDGETCODE_FUNCTION_ID foreign key (FUNCTION_ID)
  references TB_AC_FUNCTION_MASTER (FUNCTION_ID);
alter table TB_AC_BUDGETCODE_MAS
  add constraint FK_BUDGETCODE_FUND_ID foreign key (FUND_ID)
  references TB_AC_FUND_MASTER (FUND_ID);
alter table TB_AC_BUDGETCODE_MAS
  add constraint FK_BUDGETCODE_SAC_HEAD_ID foreign key (SAC_HEAD_ID)
  references TB_AC_SECONDARYHEAD_MASTER (SAC_HEAD_ID);

prompt
prompt Creating table TB_AC_BILL_DEDUCTION_DETAIL
prompt ==========================================
prompt
create table TB_AC_BILL_DEDUCTION_DETAIL
(
  bdh_id         NUMBER(12) not null,
  bm_id          NUMBER(12) not null,
  deduction_rate NUMBER(15,2),
  deduction_amt  NUMBER(15,2),
  orgid          NUMBER(4) not null,
  created_by     NUMBER(7) not null,
  created_date   DATE not null,
  updated_by     NUMBER(7),
  updated_date   DATE,
  lang_id        NUMBER(7) not null,
  lg_ip_mac      VARCHAR2(100),
  lg_ip_mac_upd  VARCHAR2(100),
  fi04_n1        NUMBER(15),
  fi04_v1        NVARCHAR2(100),
  fi04_d1        DATE,
  fi04_lo1       CHAR(1),
  budgetcode_id  NUMBER(12)
)
;
comment on column TB_AC_BILL_DEDUCTION_DETAIL.bdh_id
  is 'Primary Key';
comment on column TB_AC_BILL_DEDUCTION_DETAIL.bm_id
  is 'Reference key TB_AC_BILL_MAS';
comment on column TB_AC_BILL_DEDUCTION_DETAIL.deduction_rate
  is 'Deduction Rate';
comment on column TB_AC_BILL_DEDUCTION_DETAIL.deduction_amt
  is 'Amount of Deduction';
comment on column TB_AC_BILL_DEDUCTION_DETAIL.orgid
  is 'Organisation id';
comment on column TB_AC_BILL_DEDUCTION_DETAIL.created_by
  is 'Created User Identity';
comment on column TB_AC_BILL_DEDUCTION_DETAIL.created_date
  is 'Created Date';
comment on column TB_AC_BILL_DEDUCTION_DETAIL.updated_by
  is 'User id who update the data';
comment on column TB_AC_BILL_DEDUCTION_DETAIL.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_BILL_DEDUCTION_DETAIL.lang_id
  is 'Language Identity';
comment on column TB_AC_BILL_DEDUCTION_DETAIL.lg_ip_mac
  is 'Client Machine is Login Name | IP Address | Physical Address';
comment on column TB_AC_BILL_DEDUCTION_DETAIL.lg_ip_mac_upd
  is 'Updated Client Machine is Login Name | IP Address | Physical Address';
comment on column TB_AC_BILL_DEDUCTION_DETAIL.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_BILL_DEDUCTION_DETAIL.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_BILL_DEDUCTION_DETAIL.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_BILL_DEDUCTION_DETAIL.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
comment on column TB_AC_BILL_DEDUCTION_DETAIL.budgetcode_id
  is 'FK - tb_ac_budgetcode_mas';
alter table TB_AC_BILL_DEDUCTION_DETAIL
  add constraint PK_BCH_ID_DED primary key (BDH_ID);
alter table TB_AC_BILL_DEDUCTION_DETAIL
  add constraint FK_DEDUCTION_DETAIL_BILLMAS foreign key (BM_ID)
  references TB_AC_BILL_MAS (BM_ID)
  disable;
alter table TB_AC_BILL_DEDUCTION_DETAIL
  add constraint FK_DEDU_BUDGETCODE_ID foreign key (BUDGETCODE_ID)
  references TB_AC_BUDGETCODE_MAS (BUDGETCODE_ID);

prompt
prompt Creating table TB_AC_BILL_EXP_DETAIL
prompt ====================================
prompt
create table TB_AC_BILL_EXP_DETAIL
(
  bch_id            NUMBER(12) not null,
  bm_id             NUMBER(12) not null,
  bch_charges_amt   NUMBER(15,2) not null,
  disallowed_amt    NUMBER(15,2),
  disallowed_remark NVARCHAR2(1000),
  act_amt           NUMBER(15,2),
  orgid             NUMBER(4) not null,
  created_by        NUMBER(7) not null,
  created_date      DATE not null,
  updated_by        NUMBER(7),
  updated_date      DATE,
  lang_id           NUMBER(7) not null,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100),
  fi04_n1           NUMBER(15),
  fi04_v1           NVARCHAR2(100),
  fi04_d1           DATE,
  fi04_lo1          CHAR(1),
  budgetcode_id     NUMBER(12)
)
;
comment on column TB_AC_BILL_EXP_DETAIL.bch_id
  is 'Primary Key';
comment on column TB_AC_BILL_EXP_DETAIL.bm_id
  is 'Reference key TB_AC_BILL_MAS';
comment on column TB_AC_BILL_EXP_DETAIL.bch_charges_amt
  is 'Amount of Bill Charges';
comment on column TB_AC_BILL_EXP_DETAIL.disallowed_amt
  is 'Disallowed Amount';
comment on column TB_AC_BILL_EXP_DETAIL.act_amt
  is 'Actual  Amount - Before Sanction';
comment on column TB_AC_BILL_EXP_DETAIL.orgid
  is 'Organisation id';
comment on column TB_AC_BILL_EXP_DETAIL.created_by
  is 'Created User Identity';
comment on column TB_AC_BILL_EXP_DETAIL.created_date
  is 'Created Date';
comment on column TB_AC_BILL_EXP_DETAIL.updated_by
  is 'User id who update the data';
comment on column TB_AC_BILL_EXP_DETAIL.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_BILL_EXP_DETAIL.lang_id
  is 'Language Identity';
comment on column TB_AC_BILL_EXP_DETAIL.lg_ip_mac
  is 'Client Machine is Login Name | IP Address | Physical Address';
comment on column TB_AC_BILL_EXP_DETAIL.lg_ip_mac_upd
  is 'Updated Client Machine is Login Name | IP Address | Physical Address';
comment on column TB_AC_BILL_EXP_DETAIL.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_BILL_EXP_DETAIL.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_BILL_EXP_DETAIL.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_BILL_EXP_DETAIL.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
comment on column TB_AC_BILL_EXP_DETAIL.budgetcode_id
  is 'FK - tb_ac_budgetcode_mas';
alter table TB_AC_BILL_EXP_DETAIL
  add constraint PK_BCH_ID primary key (BCH_ID);
alter table TB_AC_BILL_EXP_DETAIL
  add constraint FK_BM_BUDGETCODE_ID foreign key (BUDGETCODE_ID)
  references TB_AC_BUDGETCODE_MAS (BUDGETCODE_ID);
alter table TB_AC_BILL_EXP_DETAIL
  add constraint FK_BM_ID_BILLMAS foreign key (BM_ID)
  references TB_AC_BILL_MAS (BM_ID)
  disable;

prompt
prompt Creating table TB_AC_PROJECTEDREVENUE
prompt =====================================
prompt
create table TB_AC_PROJECTEDREVENUE
(
  pr_projectionid   NUMBER(12) not null,
  fa_yearid         NUMBER(12) not null,
  orginal_estamt    NUMBER(15,2),
  pr_projected      NUMBER(15,2) not null,
  revised_estamt    NUMBER(15,2),
  nxt_yr_oe         NUMBER(15,2),
  pr_collected      NUMBER(15,2),
  orgid             NUMBER(4) not null,
  user_id           NUMBER(7) not null,
  lang_id           NUMBER(7) not null,
  lmoddate          DATE not null,
  updated_by        NUMBER(7),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100),
  fi04_n1           NUMBER(15),
  fi04_v1           NVARCHAR2(100),
  fi04_d1           DATE,
  fi04_lo1          CHAR(1),
  dp_deptid         NUMBER(12),
  pr_rev_budgetcode NVARCHAR2(50),
  cpd_bugsubtype_id NUMBER(12),
  budgetcode_id     NUMBER(12)
)
;
comment on table TB_AC_PROJECTEDREVENUE
  is 'This table is used to store Projected Revenue.';
comment on column TB_AC_PROJECTEDREVENUE.pr_projectionid
  is 'Primary Key';
comment on column TB_AC_PROJECTEDREVENUE.fa_yearid
  is 'Year Id from financial Year';
comment on column TB_AC_PROJECTEDREVENUE.orginal_estamt
  is 'Original Estimate amount';
comment on column TB_AC_PROJECTEDREVENUE.pr_projected
  is 'Projected revenue amount';
comment on column TB_AC_PROJECTEDREVENUE.revised_estamt
  is 'Revised Estimate amount';
comment on column TB_AC_PROJECTEDREVENUE.nxt_yr_oe
  is 'Original estimate for next year';
comment on column TB_AC_PROJECTEDREVENUE.pr_collected
  is 'Targeted revenue amount';
comment on column TB_AC_PROJECTEDREVENUE.orgid
  is 'Organization Id';
comment on column TB_AC_PROJECTEDREVENUE.user_id
  is 'User Identity';
comment on column TB_AC_PROJECTEDREVENUE.lang_id
  is 'Language Identity';
comment on column TB_AC_PROJECTEDREVENUE.lmoddate
  is 'Last Modification Date';
comment on column TB_AC_PROJECTEDREVENUE.updated_by
  is 'User id who update the data';
comment on column TB_AC_PROJECTEDREVENUE.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_PROJECTEDREVENUE.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_PROJECTEDREVENUE.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_PROJECTEDREVENUE.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_PROJECTEDREVENUE.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_PROJECTEDREVENUE.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_PROJECTEDREVENUE.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
comment on column TB_AC_PROJECTEDREVENUE.dp_deptid
  is 'Department id';
comment on column TB_AC_PROJECTEDREVENUE.pr_rev_budgetcode
  is 'Budget code ';
comment on column TB_AC_PROJECTEDREVENUE.cpd_bugsubtype_id
  is 'Bugdet type, Reference key-TB_COMPARAM_DET Prefix "FTP"  Fund Type';
comment on column TB_AC_PROJECTEDREVENUE.budgetcode_id
  is 'fk -TB_AC_BUDGETCODE_MAS';
create index IND_PROJECTED_REVENUE_FINYEAR on TB_AC_PROJECTEDREVENUE (FA_YEARID);
alter table TB_AC_PROJECTEDREVENUE
  add constraint PK_PROJECTEDREVENUE_PROJID primary key (PR_PROJECTIONID);
alter table TB_AC_PROJECTEDREVENUE
  add constraint FK_DEPARTMENT_ID_PROJID foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);
alter table TB_AC_PROJECTEDREVENUE
  add constraint FK_PROJBUDGETCODE_MAS foreign key (BUDGETCODE_ID)
  references TB_AC_BUDGETCODE_MAS (BUDGETCODE_ID);
alter table TB_AC_PROJECTEDREVENUE
  add constraint FK_PROJECTEDREVENUE_YEARID foreign key (FA_YEARID)
  references TB_FINANCIALYEAR (FA_YEARID);

prompt
prompt Creating table TB_AC_PROJECTED_EXPENDITURE
prompt ==========================================
prompt
create table TB_AC_PROJECTED_EXPENDITURE
(
  pr_expenditureid  NUMBER(12) not null,
  fa_yearid         NUMBER(12) not null,
  orginal_estamt    NUMBER(15,2),
  revised_estamt    NUMBER(15,2),
  expenditure_amt   NUMBER(15,2),
  pr_balance_amt    NUMBER(15,2),
  orgid             NUMBER(4) not null,
  user_id           NUMBER(7) not null,
  lang_id           NUMBER(7) not null,
  lmoddate          DATE not null,
  updated_by        NUMBER(7),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100),
  fi04_n1           NUMBER(15),
  fi04_v1           NVARCHAR2(100),
  fi04_d1           DATE,
  fi04_lo1          CHAR(1),
  dp_deptid         NUMBER(12),
  pr_exp_budgetcode NVARCHAR2(50),
  cpd_bugsubtype_id NUMBER(12),
  budgetcode_id     NUMBER(12)
)
;
comment on table TB_AC_PROJECTED_EXPENDITURE
  is 'This table is used to store Projected EXPENDITURE.';
comment on column TB_AC_PROJECTED_EXPENDITURE.pr_expenditureid
  is 'Primary Key';
comment on column TB_AC_PROJECTED_EXPENDITURE.fa_yearid
  is 'Year Id from financial Year';
comment on column TB_AC_PROJECTED_EXPENDITURE.orginal_estamt
  is 'Original Estimate amount';
comment on column TB_AC_PROJECTED_EXPENDITURE.revised_estamt
  is 'Revised Estimate amount';
comment on column TB_AC_PROJECTED_EXPENDITURE.orgid
  is 'Organization Id';
comment on column TB_AC_PROJECTED_EXPENDITURE.user_id
  is 'User Identity';
comment on column TB_AC_PROJECTED_EXPENDITURE.lang_id
  is 'Language Identity';
comment on column TB_AC_PROJECTED_EXPENDITURE.lmoddate
  is 'Last Modification Date';
comment on column TB_AC_PROJECTED_EXPENDITURE.updated_by
  is 'User id who update the data';
comment on column TB_AC_PROJECTED_EXPENDITURE.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_PROJECTED_EXPENDITURE.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_PROJECTED_EXPENDITURE.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_PROJECTED_EXPENDITURE.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_PROJECTED_EXPENDITURE.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_PROJECTED_EXPENDITURE.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_PROJECTED_EXPENDITURE.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
comment on column TB_AC_PROJECTED_EXPENDITURE.dp_deptid
  is 'Department id';
comment on column TB_AC_PROJECTED_EXPENDITURE.pr_exp_budgetcode
  is 'Budget code';
comment on column TB_AC_PROJECTED_EXPENDITURE.cpd_bugsubtype_id
  is 'Bugdet type, Reference key-TB_COMPARAM_DET Prefix "FTP"  Fund Type';
comment on column TB_AC_PROJECTED_EXPENDITURE.budgetcode_id
  is 'fk -TB_AC_BUDGETCODE_MAS';
alter table TB_AC_PROJECTED_EXPENDITURE
  add constraint PK_PROJECTEDEXPENDITURE_EXPEID primary key (PR_EXPENDITUREID);
alter table TB_AC_PROJECTED_EXPENDITURE
  add constraint FK_DEPARTMENT_ID_PROJEXPID foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);
alter table TB_AC_PROJECTED_EXPENDITURE
  add constraint FK_PROJECTEDEXP_BUDGETCODE_ID foreign key (BUDGETCODE_ID)
  references TB_AC_BUDGETCODE_MAS (BUDGETCODE_ID);
alter table TB_AC_PROJECTED_EXPENDITURE
  add constraint FK_PROJECTEDEXP_YEARID foreign key (FA_YEARID)
  references TB_FINANCIALYEAR (FA_YEARID);

prompt
prompt Creating table TB_AC_BUDGETALLOCATION
prompt =====================================
prompt
create table TB_AC_BUDGETALLOCATION
(
  ba_id             NUMBER(12) not null,
  ba_entrydate      DATE not null,
  fa_yearid         NUMBER(12),
  cpd_bugtype_id    NUMBER(12) not null,
  pr_projectionid   NUMBER(12),
  pr_expenditureid  NUMBER(12),
  dp_deptid         NUMBER(12),
  validtill_date    DATE,
  release_per       NUMBER(7,2),
  autho_flg         CHAR(1),
  autho_id          NUMBER(7),
  autho_date        DATE,
  orgid             NUMBER(4) not null,
  created_by        NUMBER(7) not null,
  created_date      DATE not null,
  updated_by        NUMBER(7),
  updated_date      DATE,
  lang_id           NUMBER(7) not null,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100),
  fi04_n1           NUMBER(15),
  fi04_v1           NVARCHAR2(100),
  fi04_d1           DATE,
  fi04_lo1          CHAR(1),
  budgetcode_id     NUMBER(12),
  cpd_bugsubtype_id NUMBER(12)
)
;
comment on table TB_AC_BUDGETALLOCATION
  is 'This table stores all the records BUDGET ALLOCATION account head involved in the accounting system.';
comment on column TB_AC_BUDGETALLOCATION.ba_id
  is 'Primary Key';
comment on column TB_AC_BUDGETALLOCATION.ba_entrydate
  is 'Date of entry of the budget allocation';
comment on column TB_AC_BUDGETALLOCATION.fa_yearid
  is 'Reference key-TB_FINANCIALYEAR';
comment on column TB_AC_BUDGETALLOCATION.cpd_bugtype_id
  is 'Bugdet type, Reference key-TB_COMPARAM_DET Prefix "REX"  Revenue/Expenditure Type';
comment on column TB_AC_BUDGETALLOCATION.pr_projectionid
  is 'Reference key -TB_AC_PROJECTEDREVENUE';
comment on column TB_AC_BUDGETALLOCATION.pr_expenditureid
  is 'Reference key-TB_AC_PROJECTED_EXPENDITURE';
comment on column TB_AC_BUDGETALLOCATION.dp_deptid
  is 'Reference key-TB_department';
comment on column TB_AC_BUDGETALLOCATION.validtill_date
  is 'DDO will set restriction as ULB required. Default value is 31st march of budget year%';
comment on column TB_AC_BUDGETALLOCATION.release_per
  is 'Release Percentage';
comment on column TB_AC_BUDGETALLOCATION.autho_flg
  is 'Flag';
comment on column TB_AC_BUDGETALLOCATION.autho_id
  is 'Authoer Id';
comment on column TB_AC_BUDGETALLOCATION.autho_date
  is 'Sysdate';
comment on column TB_AC_BUDGETALLOCATION.orgid
  is 'Organisation id';
comment on column TB_AC_BUDGETALLOCATION.created_by
  is 'Created User Identity';
comment on column TB_AC_BUDGETALLOCATION.created_date
  is 'Created Date';
comment on column TB_AC_BUDGETALLOCATION.updated_by
  is 'User id who update the data';
comment on column TB_AC_BUDGETALLOCATION.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_BUDGETALLOCATION.lang_id
  is 'Language Identity';
comment on column TB_AC_BUDGETALLOCATION.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_BUDGETALLOCATION.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_BUDGETALLOCATION.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_BUDGETALLOCATION.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_BUDGETALLOCATION.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_BUDGETALLOCATION.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
comment on column TB_AC_BUDGETALLOCATION.budgetcode_id
  is 'FK - tb_ac_budgetcode_mas';
comment on column TB_AC_BUDGETALLOCATION.cpd_bugsubtype_id
  is 'Bugdet type, Reference key-TB_COMPARAM_DET Prefix "FTP"  Fund Type';
alter table TB_AC_BUDGETALLOCATION
  add constraint PK_BUDGETALLOCATIONBA_ID primary key (BA_ID);
alter table TB_AC_BUDGETALLOCATION
  add constraint FK_BUACPD_BUGSUBTYPE_ID foreign key (CPD_BUGSUBTYPE_ID)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_BUDGETALLOCATION
  add constraint FK_BUALLCPD_BUGTYPE_ID foreign key (CPD_BUGTYPE_ID)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_BUDGETALLOCATION
  add constraint FK_BUALLPROJADJ_ID foreign key (PR_PROJECTIONID)
  references TB_AC_PROJECTEDREVENUE (PR_PROJECTIONID);
alter table TB_AC_BUDGETALLOCATION
  add constraint FK_BUALL_BUDGETCODE_ID foreign key (BUDGETCODE_ID)
  references TB_AC_BUDGETCODE_MAS (BUDGETCODE_ID);
alter table TB_AC_BUDGETALLOCATION
  add constraint FK_BUALL_EXPENDITUREID foreign key (PR_EXPENDITUREID)
  references TB_AC_PROJECTED_EXPENDITURE (PR_EXPENDITUREID);
alter table TB_AC_BUDGETALLOCATION
  add constraint FK_BUALL_FA_YEARID foreign key (FA_YEARID)
  references TB_FINANCIALYEAR (FA_YEARID);
alter table TB_AC_BUDGETALLOCATION
  add constraint FK_DEPARTMENT_ID foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);

prompt
prompt Creating table TB_AC_BUDGETALLOCATION_HIST
prompt ==========================================
prompt
create table TB_AC_BUDGETALLOCATION_HIST
(
  h_ba_id           NUMBER(12) not null,
  ba_id             NUMBER(12),
  ba_entrydate      DATE,
  fa_yearid         NUMBER(12),
  cpd_bugtype_id    NUMBER(12),
  pr_projectionid   NUMBER(12),
  pr_expenditureid  NUMBER(12),
  dp_deptid         NUMBER(12),
  validtill_date    DATE,
  release_per       NUMBER(7,2),
  autho_flg         CHAR(1),
  autho_id          NUMBER(7),
  autho_date        DATE,
  orgid             NUMBER(4),
  created_by        NUMBER(7),
  created_date      DATE,
  updated_by        NUMBER(7),
  updated_date      DATE,
  lang_id           NUMBER(7),
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100),
  fi04_n1           NUMBER(15),
  fi04_v1           NVARCHAR2(100),
  fi04_d1           DATE,
  fi04_lo1          CHAR(1),
  h_status          CHAR(1),
  h_created_by      NUMBER(12),
  h_created_date    DATE,
  budgetcode_id     NUMBER(12),
  cpd_bugsubtype_id NUMBER(12)
)
;
comment on column TB_AC_BUDGETALLOCATION_HIST.h_ba_id
  is 'Primary key';
comment on column TB_AC_BUDGETALLOCATION_HIST.h_status
  is 'updated flag ''U'' and delete flag ''D''';
comment on column TB_AC_BUDGETALLOCATION_HIST.h_created_by
  is 'hist created user id';
comment on column TB_AC_BUDGETALLOCATION_HIST.h_created_date
  is 'hist created date ';
comment on column TB_AC_BUDGETALLOCATION_HIST.budgetcode_id
  is 'FK - tb_ac_budgetcode_mas';
comment on column TB_AC_BUDGETALLOCATION_HIST.cpd_bugsubtype_id
  is 'Bugdet type, Reference key-TB_COMPARAM_DET Prefix "FTP"  Fund Type';
alter table TB_AC_BUDGETALLOCATION_HIST
  add constraint PK_H_BA_ID primary key (H_BA_ID);

prompt
prompt Creating table TB_AC_BUDGETORY_ESTIMATE
prompt =======================================
prompt
create table TB_AC_BUDGETORY_ESTIMATE
(
  bugest_id              NUMBER(12) not null,
  fa_yearid              NUMBER(12),
  cpd_bugtype_id         NUMBER(12) not null,
  cpd_bugsubtype_id      NUMBER(12) not null,
  dp_deptid              NUMBER(12),
  last_year_count        NUMBER(3),
  estimate_for_nextyear  NUMBER(15,2),
  appr_bug_stand_com     NUMBER(15,2),
  finalized_bug_gen_body NUMBER(15,2),
  orgid                  NUMBER(4) not null,
  lang_id                NUMBER(7) not null,
  created_by             NUMBER(7) not null,
  created_date           DATE not null,
  updated_by             NUMBER(7),
  updated_date           DATE,
  lg_ip_mac              VARCHAR2(100),
  lg_ip_mac_upd          VARCHAR2(100),
  fi04_n1                NUMBER(15),
  fi04_v1                NVARCHAR2(100),
  fi04_d1                DATE,
  fi04_lo1               CHAR(1),
  budgetcode_id          NUMBER(12)
)
;
comment on table TB_AC_BUDGETORY_ESTIMATE
  is 'This table stores all the records of provision transfer of amount of account head involved in the accounting system.';
comment on column TB_AC_BUDGETORY_ESTIMATE.bugest_id
  is 'Primary Key';
comment on column TB_AC_BUDGETORY_ESTIMATE.fa_yearid
  is 'Reference key-TB_FINANCIALYEAR';
comment on column TB_AC_BUDGETORY_ESTIMATE.cpd_bugtype_id
  is 'Bugdet type, Reference key-TB_COMPARAM_DET Prefix "REX"  Revenue/Expenditure Type';
comment on column TB_AC_BUDGETORY_ESTIMATE.cpd_bugsubtype_id
  is 'Bugdet type, Reference key-TB_COMPARAM_DET Prefix "PRV"   PROVISION TYPE Provision type i.e. Prov. Adjustment or Add. Provision';
comment on column TB_AC_BUDGETORY_ESTIMATE.dp_deptid
  is 'Reference key-tb_department';
comment on column TB_AC_BUDGETORY_ESTIMATE.last_year_count
  is 'Last year count';
comment on column TB_AC_BUDGETORY_ESTIMATE.estimate_for_nextyear
  is 'ULB Estimates for Next Year';
comment on column TB_AC_BUDGETORY_ESTIMATE.appr_bug_stand_com
  is 'Approval Budget ( Standing Committee )';
comment on column TB_AC_BUDGETORY_ESTIMATE.finalized_bug_gen_body
  is 'Finalized Budget ( General Body)';
comment on column TB_AC_BUDGETORY_ESTIMATE.orgid
  is 'Organisation id';
comment on column TB_AC_BUDGETORY_ESTIMATE.lang_id
  is 'Language Identity';
comment on column TB_AC_BUDGETORY_ESTIMATE.created_by
  is 'User Identity';
comment on column TB_AC_BUDGETORY_ESTIMATE.created_date
  is 'Date on which data is going to create';
comment on column TB_AC_BUDGETORY_ESTIMATE.updated_by
  is 'User id who update the data';
comment on column TB_AC_BUDGETORY_ESTIMATE.updated_date
  is 'Last Modification Date';
comment on column TB_AC_BUDGETORY_ESTIMATE.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_BUDGETORY_ESTIMATE.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_BUDGETORY_ESTIMATE.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_BUDGETORY_ESTIMATE.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_BUDGETORY_ESTIMATE.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_BUDGETORY_ESTIMATE.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
comment on column TB_AC_BUDGETORY_ESTIMATE.budgetcode_id
  is 'FK - tb_ac_budgetcode_mas';
alter table TB_AC_BUDGETORY_ESTIMATE
  add constraint PK_BUDG_ESTIMATE_ID primary key (BUGEST_ID);
alter table TB_AC_BUDGETORY_ESTIMATE
  add constraint FK_BUDGESTIMATE_FA_YEARID foreign key (FA_YEARID)
  references TB_FINANCIALYEAR (FA_YEARID);
alter table TB_AC_BUDGETORY_ESTIMATE
  add constraint FK_BUDGESTIMATE_ID foreign key (CPD_BUGSUBTYPE_ID)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_BUDGETORY_ESTIMATE
  add constraint FK_BUDGETOR_ID foreign key (CPD_BUGTYPE_ID)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_BUDGETORY_ESTIMATE
  add constraint FK_BUGESTIMATEC_BUDGETCODE_ID foreign key (BUDGETCODE_ID)
  references TB_AC_BUDGETCODE_MAS (BUDGETCODE_ID);
alter table TB_AC_BUDGETORY_ESTIMATE
  add constraint FK_BUGESTIMATEC_DP_DEPTID foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);

prompt
prompt Creating table TB_AC_BUDGETORY_REVISED
prompt ======================================
prompt
create table TB_AC_BUDGETORY_REVISED
(
  bugrev_id         NUMBER(12) not null,
  fa_yearid         NUMBER(12),
  cpd_bugtype_id    NUMBER(12) not null,
  cpd_bugsubtype_id NUMBER(12) not null,
  dp_deptid         NUMBER(12),
  last_year_count   NUMBER(3),
  revised_amount    NUMBER(15,2),
  orgid             NUMBER(4) not null,
  lang_id           NUMBER(7) not null,
  created_by        NUMBER(7) not null,
  created_date      DATE not null,
  updated_by        NUMBER(7),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100),
  fi04_n1           NUMBER(15),
  fi04_v1           NVARCHAR2(100),
  fi04_d1           DATE,
  fi04_lo1          CHAR(1),
  budgetcode_id     NUMBER(12)
)
;
comment on column TB_AC_BUDGETORY_REVISED.bugrev_id
  is 'Primary Key';
comment on column TB_AC_BUDGETORY_REVISED.fa_yearid
  is 'Reference key-TB_FINANCIALYEAR';
comment on column TB_AC_BUDGETORY_REVISED.cpd_bugtype_id
  is 'Bugdet type, Reference key-TB_COMPARAM_DET Prefix "REX"  Revenue/Expenditure Type';
comment on column TB_AC_BUDGETORY_REVISED.cpd_bugsubtype_id
  is 'Bugdet type, Reference key-TB_COMPARAM_DET Prefix "PRV"   PROVISION TYPE Provision type i.e. Prov. Adjustment or Add. Provision';
comment on column TB_AC_BUDGETORY_REVISED.dp_deptid
  is 'Reference key-tb_department';
comment on column TB_AC_BUDGETORY_REVISED.last_year_count
  is 'Last year count';
comment on column TB_AC_BUDGETORY_REVISED.revised_amount
  is 'Revised Amount';
comment on column TB_AC_BUDGETORY_REVISED.orgid
  is 'Organisation id';
comment on column TB_AC_BUDGETORY_REVISED.lang_id
  is 'Language Identity';
comment on column TB_AC_BUDGETORY_REVISED.created_by
  is 'User Identity';
comment on column TB_AC_BUDGETORY_REVISED.created_date
  is 'Date on which data is going to create';
comment on column TB_AC_BUDGETORY_REVISED.updated_by
  is 'User id who update the data';
comment on column TB_AC_BUDGETORY_REVISED.updated_date
  is 'Last Modification Date';
comment on column TB_AC_BUDGETORY_REVISED.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_BUDGETORY_REVISED.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_BUDGETORY_REVISED.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_BUDGETORY_REVISED.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_BUDGETORY_REVISED.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_BUDGETORY_REVISED.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
comment on column TB_AC_BUDGETORY_REVISED.budgetcode_id
  is 'FK - tb_ac_budgetcode_ma';
alter table TB_AC_BUDGETORY_REVISED
  add constraint PK_REVISED_ID primary key (BUGREV_ID);
alter table TB_AC_BUDGETORY_REVISED
  add constraint FK_BUDBUGTYPE_ID foreign key (CPD_BUGTYPE_ID)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_BUDGETORY_REVISED
  add constraint FK_BUDGREVISED_BUDGETCODE_ID foreign key (BUDGETCODE_ID)
  references TB_AC_BUDGETCODE_MAS (BUDGETCODE_ID);
alter table TB_AC_BUDGETORY_REVISED
  add constraint FK_BUDGREVISED_FA_YEARID foreign key (FA_YEARID)
  references TB_FINANCIALYEAR (FA_YEARID);
alter table TB_AC_BUDGETORY_REVISED
  add constraint FK_BUDGREVISED_ID foreign key (CPD_BUGSUBTYPE_ID)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_BUDGETORY_REVISED
  add constraint FK_BUGTYPE_DP_DEPTID foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);

prompt
prompt Creating table TB_AC_BUGOPEN_BALANCE
prompt ====================================
prompt
create table TB_AC_BUGOPEN_BALANCE
(
  opn_id         NUMBER(12) not null,
  opn_entrydate  DATE not null,
  fund_id        NUMBER(12),
  function_id    NUMBER(12),
  field_id       NUMBER(12),
  pac_head_id    NUMBER(12),
  sac_head_id    NUMBER(12),
  openbal_amt    NUMBER(15,2),
  cpd_id_drcr    NUMBER(12),
  finalized_flag CHAR(1),
  fa_yearid      NUMBER(12),
  orgid          NUMBER(4) not null,
  user_id        NUMBER(7) not null,
  lang_id        NUMBER(7) not null,
  lmoddate       DATE not null,
  updated_by     NUMBER(7),
  updated_date   DATE,
  lg_ip_mac      VARCHAR2(100),
  lg_ip_mac_upd  VARCHAR2(100),
  fi04_n1        NUMBER(15),
  fi04_v1        NVARCHAR2(100),
  fi04_d1        DATE,
  fi04_lo1       CHAR(1)
)
;
comment on table TB_AC_BUGOPEN_BALANCE
  is 'Table is used to store openbalnce finyear wise';
comment on column TB_AC_BUGOPEN_BALANCE.opn_id
  is 'Primary Key';
comment on column TB_AC_BUGOPEN_BALANCE.opn_entrydate
  is 'Open Balance entry Date';
comment on column TB_AC_BUGOPEN_BALANCE.fund_id
  is 'Fund Master Reference key --TB_AC_FUND_MASTER';
comment on column TB_AC_BUGOPEN_BALANCE.function_id
  is 'Function  Master Reference key --TB_AC_FUNCTION_MASTER';
comment on column TB_AC_BUGOPEN_BALANCE.field_id
  is 'Field Master Reference key  --TB_AC_FIELD_MASTER';
comment on column TB_AC_BUGOPEN_BALANCE.pac_head_id
  is 'Primary head Master Reference key -TB_AC_PRIMARYHEAD_MASTER';
comment on column TB_AC_BUGOPEN_BALANCE.sac_head_id
  is 'Secondary Master Reference key -- tb_ac_secondaryhead_master';
comment on column TB_AC_BUGOPEN_BALANCE.openbal_amt
  is 'Opening Balance Amount';
comment on column TB_AC_BUGOPEN_BALANCE.cpd_id_drcr
  is 'Opening Balance Type  Dr/ Cr. Prefix -DCR';
comment on column TB_AC_BUGOPEN_BALANCE.finalized_flag
  is 'Finalized Balance';
comment on column TB_AC_BUGOPEN_BALANCE.fa_yearid
  is 'Financial year id';
comment on column TB_AC_BUGOPEN_BALANCE.orgid
  is 'Organisation id';
comment on column TB_AC_BUGOPEN_BALANCE.user_id
  is 'User Identity';
comment on column TB_AC_BUGOPEN_BALANCE.lang_id
  is 'Language Identity';
comment on column TB_AC_BUGOPEN_BALANCE.lmoddate
  is 'Last Modification Date';
comment on column TB_AC_BUGOPEN_BALANCE.updated_by
  is 'User id who update the data';
comment on column TB_AC_BUGOPEN_BALANCE.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_BUGOPEN_BALANCE.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_BUGOPEN_BALANCE.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_BUGOPEN_BALANCE.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_BUGOPEN_BALANCE.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_BUGOPEN_BALANCE.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_BUGOPEN_BALANCE.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
alter table TB_AC_BUGOPEN_BALANCE
  add constraint PK_AC_OPN_ID primary key (OPN_ID);
alter table TB_AC_BUGOPEN_BALANCE
  add constraint FK_BUGOPEN_BALANCE_FIELD_ID foreign key (FIELD_ID)
  references TB_AC_FIELD_MASTER (FIELD_ID);
alter table TB_AC_BUGOPEN_BALANCE
  add constraint FK_BUGOPEN_BALANCE_FUNCTION_ID foreign key (FUNCTION_ID)
  references TB_AC_FUNCTION_MASTER (FUNCTION_ID);
alter table TB_AC_BUGOPEN_BALANCE
  add constraint FK_BUGOPEN_BALANCE_FUND_ID foreign key (FUND_ID)
  references TB_AC_FUND_MASTER (FUND_ID);
alter table TB_AC_BUGOPEN_BALANCE
  add constraint FK_BUGOPEN_BALANCE_HEAD_ID foreign key (PAC_HEAD_ID)
  references TB_AC_PRIMARYHEAD_MASTER (PAC_HEAD_ID);
alter table TB_AC_BUGOPEN_BALANCE
  add constraint FK_BUGOPEN_BALSA_HEAD_ID foreign key (SAC_HEAD_ID)
  references TB_AC_SECONDARYHEAD_MASTER (SAC_HEAD_ID);
alter table TB_AC_BUGOPEN_BALANCE
  add constraint FK_BUGOPEN_DRCRTYPE foreign key (CPD_ID_DRCR)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_BUGOPEN_BALANCE
  add constraint FK_BUGOPEN_FINYEARID foreign key (FA_YEARID)
  references TB_FINANCIALYEAR (FA_YEARID);

prompt
prompt Creating table TB_AC_CHEQUEBOOKLEAF_MAS
prompt =======================================
prompt
create table TB_AC_CHEQUEBOOKLEAF_MAS
(
  chequebook_id     NUMBER(12) not null,
  ulb_bankid        NUMBER(12),
  ba_accountid      NUMBER(12),
  from_cheque_no    VARCHAR2(12) not null,
  to_cheque_no      VARCHAR2(12) not null,
  empid             NUMBER(12),
  checkbook_leave   NUMBER(12) not null,
  rcpt_chqbook_date DATE,
  issuer_empid      NUMBER(12),
  chkbook_rtn_date  DATE,
  check_book_return CHAR(1),
  orgid             NUMBER(4) not null,
  user_id           NUMBER(7) not null,
  lang_id           NUMBER(7) not null,
  lmoddate          DATE not null,
  updated_by        NUMBER(7),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100),
  fi04_n1           NUMBER(15),
  fi04_v1           NVARCHAR2(100),
  fi04_d1           DATE,
  fi04_lo1          CHAR(1),
  return_remark     NVARCHAR2(500),
  issued_date       DATE
)
;
comment on table TB_AC_CHEQUEBOOKLEAF_MAS
  is 'This table stores checkbook entries in the bank';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.chequebook_id
  is 'Generated checkbook id (primary key)';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.ulb_bankid
  is 'Bank Id (Fk TB_ULB_BANK )';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.ba_accountid
  is 'Account Id (Fk TB_BANK_ACCOUNT)';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.from_cheque_no
  is 'From Cheque No.';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.to_cheque_no
  is 'To Cheque No.';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.empid
  is 'Emp Id (Fk employee)';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.checkbook_leave
  is 'No. of leaves in checkbook';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.rcpt_chqbook_date
  is 'Cheque book received date';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.issuer_empid
  is 'Issued Employee name Emp Id (Fk employee)';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.chkbook_rtn_date
  is 'Return date';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.check_book_return
  is 'Return Flag';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.orgid
  is 'Organization Id';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.user_id
  is 'User Identity';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.lang_id
  is 'Language Identity';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.lmoddate
  is 'Last Modification Date';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.updated_by
  is 'User id who update the data';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.lg_ip_mac
  is 'Client Machine¿s Login Name | IP Address | Physical Address';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.lg_ip_mac_upd
  is 'Updated Client Machine¿s Login Name | IP Address | Physical Address';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.return_remark
  is 'Remark ';
comment on column TB_AC_CHEQUEBOOKLEAF_MAS.issued_date
  is 'Cheque book date of Issued ';
alter table TB_AC_CHEQUEBOOKLEAF_MAS
  add constraint PK_CHEQUEBOOK_ID primary key (CHEQUEBOOK_ID);
alter table TB_AC_CHEQUEBOOKLEAF_MAS
  add constraint FK_BANK_ID foreign key (ULB_BANKID)
  references TB_ULB_BANK (ULB_BANKID);
alter table TB_AC_CHEQUEBOOKLEAF_MAS
  add constraint FK_BA_ACCOUNTID_BANK foreign key (BA_ACCOUNTID)
  references TB_BANK_ACCOUNT (BA_ACCOUNTID);

prompt
prompt Creating table TB_AC_PAYMENT_MAS
prompt ================================
prompt
create table TB_AC_PAYMENT_MAS
(
  payment_id            NUMBER(12) not null,
  payment_no            NVARCHAR2(12),
  payment_date          DATE,
  cpd_id_billtype       NUMBER(12),
  dps_slipid            NUMBER(12),
  vm_vendorid           NUMBER(12),
  cpd_id_payment_mode   NUMBER(12),
  ba_accountid          NUMBER(12),
  instrument_number     NUMBER(12),
  instrument_date       DATE,
  payment_amount        NUMBER(15,2),
  narration             NVARCHAR2(1000),
  orgid                 NUMBER(4),
  created_by            NUMBER(7),
  created_date          DATE,
  updated_by            NUMBER(7),
  updated_date          DATE,
  lang_id               NUMBER(7) not null,
  lg_ip_mac             VARCHAR2(100),
  lg_ip_mac_upd         VARCHAR2(100),
  autho_id              NUMBER(7),
  autho_date            DATE,
  autho_flg             CHAR(1),
  fi04_n1               NUMBER(15),
  fi04_v2               NVARCHAR2(100),
  fi04_d1               DATE,
  fi04_lo1              CHAR(1),
  discrepancy_flag      CHAR(1) default 'N',
  discrepancydetails    NVARCHAR2(500),
  cheque_clearance_date DATE
)
;
comment on table TB_AC_PAYMENT_MAS
  is 'This table used for payment master entry.';
comment on column TB_AC_PAYMENT_MAS.payment_id
  is 'Primary Key';
comment on column TB_AC_PAYMENT_MAS.payment_no
  is 'Payment number system generated';
comment on column TB_AC_PAYMENT_MAS.payment_date
  is 'payment Date';
comment on column TB_AC_PAYMENT_MAS.cpd_id_billtype
  is 'Bill type prefix BBT - ref tb_comparam_det ';
comment on column TB_AC_PAYMENT_MAS.dps_slipid
  is 'Deposit Slip id';
comment on column TB_AC_PAYMENT_MAS.vm_vendorid
  is 'Vendor id- ref tb_vendormaster ';
comment on column TB_AC_PAYMENT_MAS.cpd_id_payment_mode
  is 'Payment Mode  from PAY prefix';
comment on column TB_AC_PAYMENT_MAS.ba_accountid
  is 'Ref. Key -TB_BANK_ACCOUN';
comment on column TB_AC_PAYMENT_MAS.instrument_number
  is 'Cheque Number/ RTGS ref. Number/ NFT ref. Number etc.';
comment on column TB_AC_PAYMENT_MAS.instrument_date
  is 'Cheque Date/ RTGS Date/ NFT Date etc.';
comment on column TB_AC_PAYMENT_MAS.payment_amount
  is 'Payment Amount';
comment on column TB_AC_PAYMENT_MAS.narration
  is 'Narration / Remark';
comment on column TB_AC_PAYMENT_MAS.orgid
  is 'Organisation Id';
comment on column TB_AC_PAYMENT_MAS.created_by
  is 'Created User Identity';
comment on column TB_AC_PAYMENT_MAS.created_date
  is 'Created Date';
comment on column TB_AC_PAYMENT_MAS.updated_by
  is 'User id who update the data';
comment on column TB_AC_PAYMENT_MAS.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_PAYMENT_MAS.lang_id
  is 'Language Identity';
comment on column TB_AC_PAYMENT_MAS.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_PAYMENT_MAS.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_PAYMENT_MAS.autho_id
  is 'Authoer Id';
comment on column TB_AC_PAYMENT_MAS.autho_date
  is 'Sysdate';
comment on column TB_AC_PAYMENT_MAS.autho_flg
  is 'Flag';
comment on column TB_AC_PAYMENT_MAS.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_PAYMENT_MAS.fi04_v2
  is 'Additional nvarchar2 FI04_V2 to be used in future';
comment on column TB_AC_PAYMENT_MAS.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_PAYMENT_MAS.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
comment on column TB_AC_PAYMENT_MAS.discrepancy_flag
  is 'Update from Reconciliation form  - if discrepancy is found update "Y" else default "N"';
comment on column TB_AC_PAYMENT_MAS.discrepancydetails
  is 'Update from Reconciliation form - if discrepancy is found then enter discrepancy details.';
comment on column TB_AC_PAYMENT_MAS.cheque_clearance_date
  is 'Clearance Date / Date of Realization update from Bank Reconciliation Entry ';
alter table TB_AC_PAYMENT_MAS
  add constraint PK_PAYMENT_ID primary key (PAYMENT_ID);
alter table TB_AC_PAYMENT_MAS
  add constraint FK_CONTRA_DPS_SLIPID foreign key (DPS_SLIPID)
  references TB_AC_BANK_DEPOSITSLIP_MASTER (DPS_SLIPID);
alter table TB_AC_PAYMENT_MAS
  add constraint FK_CPD_ID_BILLTYPE foreign key (CPD_ID_BILLTYPE)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_PAYMENT_MAS
  add constraint FK_CPD_ID_PAYMENT_MODE foreign key (CPD_ID_PAYMENT_MODE)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_PAYMENT_MAS
  add constraint FK_PAYMENT_BA_ACCOUNTID foreign key (BA_ACCOUNTID)
  references TB_BANK_ACCOUNT (BA_ACCOUNTID);
alter table TB_AC_PAYMENT_MAS
  add constraint FK_PAYMENT_VM_VENDORID foreign key (VM_VENDORID)
  references TB_VENDORMASTER (VM_VENDORID);

prompt
prompt Creating table TB_AC_CHEQUEBOOKLEAF_DET
prompt =======================================
prompt
create table TB_AC_CHEQUEBOOKLEAF_DET
(
  chequebook_detid           NUMBER(12) not null,
  chequebook_id              NUMBER(12),
  cheque_no                  NVARCHAR2(12) not null,
  cpd_idstatus               NUMBER(12),
  payment_id                 NUMBER(12),
  remark                     VARCHAR2(500),
  stop_pay_order_no          NUMBER(12),
  stop_pay_order_date        DATE,
  stop_pay_flag              CHAR(1),
  stop_pay_remark            VARCHAR2(500),
  stoppay_date               DATE,
  issuance_flag              CHAR(1),
  issuance_date              DATE,
  cancellation_flag          CHAR(1),
  cancellation_date          DATE,
  cancellation_reason        NVARCHAR2(500),
  new_issue_chequebook_detid NUMBER(12),
  orgid                      NUMBER(4) not null,
  user_id                    NUMBER(7) not null,
  lang_id                    NUMBER(7) not null,
  lmoddate                   DATE not null,
  updated_by                 NUMBER(7),
  updated_date               DATE,
  lg_ip_mac                  VARCHAR2(100),
  lg_ip_mac_upd              VARCHAR2(100),
  fi04_n1                    NUMBER(15),
  fi04_v1                    NVARCHAR2(100),
  fi04_d1                    DATE,
  fi04_lo1                   CHAR(1)
)
;
comment on table TB_AC_CHEQUEBOOKLEAF_DET
  is 'This table stores Details of the Checkbook';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.chequebook_detid
  is 'Generated checkbook id (primary key)';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.chequebook_id
  is 'Checkbook Id(Fk)';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.cheque_no
  is 'Cheque No ';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.cpd_idstatus
  is 'Cpd_idstatus (Fk -TB_COMPARAM_DET )';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.payment_id
  is 'Payment voucher  Id (Fk -TB_AC_PAYMENT_MAS) ';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.remark
  is 'Remark';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.stop_pay_order_no
  is 'Stop Payment Order No';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.stop_pay_order_date
  is 'Stop Payment Order Date';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.stop_pay_flag
  is 'Stop Payment Order as per MNAM';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.stop_pay_remark
  is 'Stop Payment Remark';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.stoppay_date
  is 'Stop Payment Date';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.issuance_flag
  is 'Payments Cheque Issuance Flag';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.issuance_date
  is 'Cheque issuance date';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.cancellation_flag
  is 'Cancellation Flag';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.cancellation_date
  is 'Cancellation Date';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.cancellation_reason
  is 'Reason for cancellation';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.new_issue_chequebook_detid
  is 'New Cheque issue id --Ref. TB_AC_CHEQUEBOOKLEAF_DET.CHEQUEBOOK_DETID ';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.orgid
  is 'Organization Id';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.user_id
  is 'User Identity';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.lang_id
  is 'Language Identity';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.lmoddate
  is 'Last Modification Date';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.updated_by
  is 'User id who update the data';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.updated_date
  is 'Last Modification Date';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.lg_ip_mac
  is 'Client Machine¿s Login Name | IP Address | Physical Address';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.lg_ip_mac_upd
  is 'Updated Client Machine¿s Login Name | IP Address | Physical Address';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_CHEQUEBOOKLEAF_DET.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
alter table TB_AC_CHEQUEBOOKLEAF_DET
  add constraint PK_CHEQUEBOOK_DET primary key (CHEQUEBOOK_DETID);
alter table TB_AC_CHEQUEBOOKLEAF_DET
  add constraint FK_CHEQUEBOOK_PAYMENT_ID foreign key (PAYMENT_ID)
  references TB_AC_PAYMENT_MAS (PAYMENT_ID);
alter table TB_AC_CHEQUEBOOKLEAF_DET
  add constraint FK_NEWISSUE_CHEQUEBOOK_DETID foreign key (NEW_ISSUE_CHEQUEBOOK_DETID)
  references TB_AC_CHEQUEBOOKLEAF_DET (CHEQUEBOOK_DETID);
alter table TB_AC_CHEQUEBOOKLEAF_DET
  add constraint TB_AC_CHEQUEBOOKLEAF_DET foreign key (CHEQUEBOOK_ID)
  references TB_AC_CHEQUEBOOKLEAF_MAS (CHEQUEBOOK_ID);

prompt
prompt Creating table TB_AC_CHEQUEBOOKLEAF_DET_HIST
prompt ============================================
prompt
create table TB_AC_CHEQUEBOOKLEAF_DET_HIST
(
  cheque_id        NUMBER(12) not null,
  chequebook_detid NUMBER(12),
  chequebook_id    NUMBER(12),
  cheque_no        VARCHAR2(12),
  cpd_idstatus     NUMBER(12),
  vh_voucherid     NUMBER(12),
  remark           VARCHAR2(500),
  stop_pay_flag    CHAR(1),
  stop_pay_remark  VARCHAR2(500),
  stoppay_date     DATE,
  orgid            NUMBER(4),
  user_id          NUMBER(7),
  lang_id          NUMBER(7),
  lmoddate         DATE,
  updated_by       NUMBER(7),
  updated_date     DATE,
  lg_ip_mac        VARCHAR2(100),
  lg_ip_mac_upd    VARCHAR2(100),
  fi04_n1          NUMBER(15),
  fi04_v1          NVARCHAR2(100),
  fi04_d1          DATE,
  fi04_lo1         CHAR(1),
  h_status         CHAR(1)
)
;
alter table TB_AC_CHEQUEBOOKLEAF_DET_HIST
  add constraint PK_CHEQUE_ID primary key (CHEQUE_ID);

prompt
prompt Creating table TB_AC_CODINGSTRUCTURE_DET_HIST
prompt =============================================
prompt
create table TB_AC_CODINGSTRUCTURE_DET_HIST
(
  h_codcofdetid   NUMBER(12) not null,
  codcofdet_id    NUMBER(12),
  codcof_id       NUMBER(12),
  cod_level       NUMBER(2),
  cod_description NVARCHAR2(500),
  cod_digits      NUMBER(2),
  orgid           NUMBER(4),
  user_id         NUMBER(7),
  lang_id         NUMBER(4),
  lmoddate        DATE,
  updated_by      NUMBER(7),
  updated_date    DATE,
  lg_ip_mac       VARCHAR2(100),
  lg_ip_mac_upd   VARCHAR2(100),
  h_status        NVARCHAR2(1)
)
;
alter table TB_AC_CODINGSTRUCTURE_DET_HIST
  add constraint PK_H_CODCOFDETID primary key (H_CODCOFDETID);

prompt
prompt Creating table TB_AC_CODINGSTRUCTURE_MAS_HIST
prompt =============================================
prompt
create table TB_AC_CODINGSTRUCTURE_MAS_HIST
(
  h_codcofid    NUMBER(12) not null,
  codcof_id     NUMBER(12),
  com_appflag   CHAR(1),
  com_cpd_id    NUMBER(12),
  com_chagflag  CHAR(1),
  com_desc      NVARCHAR2(200),
  define_onflag CHAR(1),
  cod_no_level  NUMBER(2),
  orgid         NUMBER(4),
  user_id       NUMBER(7),
  lang_id       NUMBER(4),
  lmoddate      DATE,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  h_status      NVARCHAR2(1)
)
;
alter table TB_AC_CODINGSTRUCTURE_MAS_HIST
  add constraint PK_HCODCOFID primary key (H_CODCOFID);

prompt
prompt Creating table TB_AC_DEPOADV_MAS
prompt ================================
prompt
create table TB_AC_DEPOADV_MAS
(
  dampp_id           NUMBER(12) not null,
  cpd_id_hdm         NUMBER(12),
  cpd_id_dty_aty     NUMBER(12),
  cpd_id             NUMBER(12),
  dept_id            NUMBER(12),
  remark_description NVARCHAR2(500),
  status             NVARCHAR2(1),
  orgid              NUMBER(4) not null,
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(7) not null,
  lmoddate           DATE not null,
  updated_by         NUMBER(7),
  updated_date       DATE,
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  fi04_n1            NUMBER(15),
  fi04_v1            NVARCHAR2(100),
  fi04_d1            DATE,
  fi04_lo1           CHAR(1),
  budgetcode_id      NUMBER(12)
)
;
comment on column TB_AC_DEPOADV_MAS.dampp_id
  is 'Primary Key';
comment on column TB_AC_DEPOADV_MAS.cpd_id_hdm
  is 'Reference key TB_COMPARAM_DET';
comment on column TB_AC_DEPOADV_MAS.cpd_id_dty_aty
  is 'Reference key TB_COMPARAM_DET (Deposit Prifix DTY)  (Advance  Prifix ATY)';
comment on column TB_AC_DEPOADV_MAS.cpd_id
  is 'Reference key TB_COMPARAM_DET';
comment on column TB_AC_DEPOADV_MAS.dept_id
  is 'Department id';
comment on column TB_AC_DEPOADV_MAS.remark_description
  is 'Desription';
comment on column TB_AC_DEPOADV_MAS.status
  is 'Record Status';
comment on column TB_AC_DEPOADV_MAS.orgid
  is 'Organisation id';
comment on column TB_AC_DEPOADV_MAS.user_id
  is 'User Identity';
comment on column TB_AC_DEPOADV_MAS.lang_id
  is 'Language Identity';
comment on column TB_AC_DEPOADV_MAS.lmoddate
  is 'Last Modification Date';
comment on column TB_AC_DEPOADV_MAS.updated_by
  is 'Updated User Identity';
comment on column TB_AC_DEPOADV_MAS.updated_date
  is 'Updated Modification Date';
comment on column TB_AC_DEPOADV_MAS.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_DEPOADV_MAS.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_DEPOADV_MAS.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_DEPOADV_MAS.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_DEPOADV_MAS.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_DEPOADV_MAS.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
comment on column TB_AC_DEPOADV_MAS.budgetcode_id
  is 'FK - tb_ac_budgetcode_mas';
alter table TB_AC_DEPOADV_MAS
  add constraint PK_TB_AC_DEPOADV_MAS_DAMPP_ID primary key (DAMPP_ID);
alter table TB_AC_DEPOADV_MAS
  add constraint FK_DEPARTMENT_DEPT_ID foreign key (DEPT_ID)
  references TB_DEPARTMENT (DP_DEPTID);
alter table TB_AC_DEPOADV_MAS
  add constraint FK_DEPOADV_BUDGETCODE_ID foreign key (BUDGETCODE_ID)
  references TB_AC_BUDGETCODE_MAS (BUDGETCODE_ID);
alter table TB_AC_DEPOADV_MAS
  add constraint FK_DEPOADV_CPD_ID foreign key (CPD_ID)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_DEPOADV_MAS
  add constraint FK_DEPOADV_DTY_ATY_CPD_ID foreign key (CPD_ID_DTY_ATY)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_DEPOADV_MAS
  add constraint FK_DEPOADV_HDM_CPD_ID foreign key (CPD_ID_HDM)
  references TB_COMPARAM_DET (CPD_ID);

prompt
prompt Creating table TB_AC_DEPOSITS
prompt =============================
prompt
create table TB_AC_DEPOSITS
(
  dep_id           NUMBER(12) not null,
  dep_entry_date   DATE not null,
  dep_receiptdt    DATE,
  dep_no           NUMBER(12) not null,
  cpd_deposit_type NUMBER(12) not null,
  dp_deptid        NUMBER(12) not null,
  dep_receiptno    NUMBER(12),
  cpd_source_type  NUMBER(12) not null,
  cpd_status       NUMBER(12) not null,
  dep_amount       NUMBER(12,2) not null,
  dep_refund_bal   NUMBER(12,2),
  vm_vendorid      NUMBER(12),
  dep_receivedfrom NVARCHAR2(500),
  dep_narration    NVARCHAR2(1000) not null,
  sac_head_id      NUMBER(12),
  dm_flag          CHAR(1),
  orgid            NUMBER(4) not null,
  created_by       NUMBER(7) not null,
  created_date     DATE not null,
  updated_by       NUMBER(7),
  updated_date     DATE,
  lang_id          NUMBER(7) not null,
  lg_ip_mac        VARCHAR2(100),
  lg_ip_mac_upd    VARCHAR2(100),
  fi04_n1          NUMBER(15),
  fi04_v1          NVARCHAR2(100),
  fi04_d1          DATE,
  fi04_lo1         CHAR(1)
)
;
comment on table TB_AC_DEPOSITS
  is 'This table contains list of deposits made through receipts. Here Back-dated transaction details also are captured.';
comment on column TB_AC_DEPOSITS.dep_id
  is 'Primary Key';
comment on column TB_AC_DEPOSITS.dep_entry_date
  is 'Date on which Deposit entry is made';
comment on column TB_AC_DEPOSITS.dep_receiptdt
  is 'Amount Deposited, its Receipt Date also backdated receipts can be entered';
comment on column TB_AC_DEPOSITS.dep_no
  is 'Deposit number. which will be one-up generated';
comment on column TB_AC_DEPOSITS.cpd_deposit_type
  is 'Deposit Type stored in common parameter DTY';
comment on column TB_AC_DEPOSITS.dp_deptid
  is 'department wherein its deposited';
comment on column TB_AC_DEPOSITS.dep_receiptno
  is 'Amount Deposited, its Receipt No also backdated receipt numbers can be entered';
comment on column TB_AC_DEPOSITS.cpd_source_type
  is 'Deposit Type stored in common parameter';
comment on column TB_AC_DEPOSITS.cpd_status
  is 'Deposit Type stored in common parameter';
comment on column TB_AC_DEPOSITS.dep_amount
  is 'Amount Deposited';
comment on column TB_AC_DEPOSITS.dep_refund_bal
  is 'Balance Amount yet to be refunded through payment voucher';
comment on column TB_AC_DEPOSITS.vm_vendorid
  is 'Vendor Id From Vendor Master';
comment on column TB_AC_DEPOSITS.dep_receivedfrom
  is 'Deposit Received from whom';
comment on column TB_AC_DEPOSITS.dep_narration
  is 'Narration of Deposit';
comment on column TB_AC_DEPOSITS.sac_head_id
  is 'Secondary Master Reference key -- tb_ac_secondaryhead_master';
comment on column TB_AC_DEPOSITS.dm_flag
  is 'Deposits entry Menutree - M   /Data Entry Site - D';
comment on column TB_AC_DEPOSITS.orgid
  is 'Organisation id';
comment on column TB_AC_DEPOSITS.created_by
  is 'User Identity';
comment on column TB_AC_DEPOSITS.created_date
  is 'created Date';
comment on column TB_AC_DEPOSITS.updated_by
  is 'User id who update the data';
comment on column TB_AC_DEPOSITS.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_DEPOSITS.lang_id
  is 'Language Identity';
comment on column TB_AC_DEPOSITS.lg_ip_mac
  is 'Client Machine’s Login Name | IP Address | Physical Address';
comment on column TB_AC_DEPOSITS.lg_ip_mac_upd
  is 'Updated Client Machine’s Login Name | IP Address | Physical Address';
comment on column TB_AC_DEPOSITS.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_DEPOSITS.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_DEPOSITS.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_DEPOSITS.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
alter table TB_AC_DEPOSITS
  add constraint PK_AC_DEP_ID primary key (DEP_ID);
alter table TB_AC_DEPOSITS
  add constraint FK_AC_DEP_DEPTID foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);
alter table TB_AC_DEPOSITS
  add constraint FK_CPD_DEPOSIT_TYPE foreign key (CPD_DEPOSIT_TYPE)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_DEPOSITS
  add constraint FK_CPD_SOURCE_TYPE foreign key (CPD_SOURCE_TYPE)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_DEPOSITS
  add constraint FK_CPD_STATUS foreign key (CPD_STATUS)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_DEPOSITS
  add constraint FK_TR_VENDOR_ID_DPST foreign key (VM_VENDORID)
  references TB_VENDORMASTER (VM_VENDORID);

prompt
prompt Creating table TB_AC_FIELD_MASTER_HIST
prompt ======================================
prompt
create table TB_AC_FIELD_MASTER_HIST
(
  h_field_id          NUMBER(12) not null,
  field_id            NUMBER(12),
  codcofdet_id        NUMBER(12),
  field_code          VARCHAR2(10),
  field_desc          NVARCHAR2(500),
  field_parent_id     NUMBER(12),
  field_compcode      VARCHAR2(20),
  orgid               NUMBER(4),
  user_id             NUMBER(7),
  lang_id             NUMBER(4),
  lmoddate            DATE,
  updated_by          NUMBER(7),
  updated_date        DATE,
  lg_ip_mac           VARCHAR2(100),
  lg_ip_mac_upd       VARCHAR2(100),
  sup_field_parent_id NUMBER(12),
  h_status            NVARCHAR2(1)
)
;
alter table TB_AC_FIELD_MASTER_HIST
  add constraint PK_AC_H_FIELD_ID primary key (H_FIELD_ID);

prompt
prompt Creating table TB_AC_FUND_MASTER_HIST
prompt =====================================
prompt
create table TB_AC_FUND_MASTER_HIST
(
  h_fundid           NUMBER(12) not null,
  fund_id            NUMBER(12),
  codcofdet_id       NUMBER(12),
  fund_code          VARCHAR2(10),
  fund_desc          NVARCHAR2(500),
  fund_compositecode VARCHAR2(20),
  fund_parent_id     NUMBER(12),
  orgid              NUMBER(4),
  user_id            NUMBER(7),
  lang_id            NUMBER(4),
  lmoddate           DATE,
  updated_by         NUMBER(7),
  updated_date       DATE,
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  h_status           NVARCHAR2(1)
)
;
alter table TB_AC_FUND_MASTER_HIST
  add constraint PK_AC_H_FUNDID primary key (H_FUNDID);

prompt
prompt Creating table TB_AC_LIABILITY_BOOKING_DET
prompt ==========================================
prompt
create table TB_AC_LIABILITY_BOOKING_DET
(
  lb_liability_det_id NUMBER(12) not null,
  lb_liability_id     NUMBER(12),
  liability_amount    NUMBER(15,2),
  fa_yearid           NUMBER(12),
  orgid               NUMBER(4) not null,
  created_by          NUMBER(7) not null,
  created_date        DATE not null,
  updated_by          NUMBER(7),
  updated_date        DATE,
  lang_id             NUMBER(7) not null,
  lg_ip_mac           VARCHAR2(100),
  lg_ip_mac_upd       VARCHAR2(100),
  fi04_n1             NUMBER(15),
  fi04_v1             NVARCHAR2(100),
  fi04_d1             DATE,
  fi04_lo1            CHAR(1),
  budgetcode_id       NUMBER(12)
)
;
comment on column TB_AC_LIABILITY_BOOKING_DET.lb_liability_det_id
  is 'Primary Key';
comment on column TB_AC_LIABILITY_BOOKING_DET.lb_liability_id
  is 'Reference key  -- TB_AC_LIABILITY_BOOKING';
comment on column TB_AC_LIABILITY_BOOKING_DET.liability_amount
  is 'Liability Amount';
comment on column TB_AC_LIABILITY_BOOKING_DET.fa_yearid
  is 'Financial year ID';
comment on column TB_AC_LIABILITY_BOOKING_DET.orgid
  is 'Organisation id';
comment on column TB_AC_LIABILITY_BOOKING_DET.created_by
  is 'Created User Identity';
comment on column TB_AC_LIABILITY_BOOKING_DET.created_date
  is 'Created Date';
comment on column TB_AC_LIABILITY_BOOKING_DET.updated_by
  is 'User id who update the data';
comment on column TB_AC_LIABILITY_BOOKING_DET.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_LIABILITY_BOOKING_DET.lang_id
  is 'Language Identity';
comment on column TB_AC_LIABILITY_BOOKING_DET.lg_ip_mac
  is 'Client Machine is Login Name | IP Address | Physical Address';
comment on column TB_AC_LIABILITY_BOOKING_DET.lg_ip_mac_upd
  is 'Updated Client Machine is Login Name | IP Address | Physical Address';
comment on column TB_AC_LIABILITY_BOOKING_DET.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_LIABILITY_BOOKING_DET.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_LIABILITY_BOOKING_DET.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_LIABILITY_BOOKING_DET.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
comment on column TB_AC_LIABILITY_BOOKING_DET.budgetcode_id
  is 'FK - tb_ac_budgetcode_mas';
alter table TB_AC_LIABILITY_BOOKING_DET
  add constraint PK_LB_LIABILITY_DET_ID primary key (LB_LIABILITY_DET_ID);
alter table TB_AC_LIABILITY_BOOKING_DET
  add constraint FK_LB_BUDGETCODE_ID foreign key (BUDGETCODE_ID)
  references TB_AC_BUDGETCODE_MAS (BUDGETCODE_ID);
alter table TB_AC_LIABILITY_BOOKING_DET
  add constraint FK_LB_LIABILITY_ID foreign key (LB_LIABILITY_ID)
  references TB_AC_LIABILITY_BOOKING (LB_LIABILITY_ID);

prompt
prompt Creating table TB_AC_PAYMENT_DET
prompt ================================
prompt
create table TB_AC_PAYMENT_DET
(
  paymentdet_id         NUMBER(12) not null,
  payment_id            NUMBER(12) not null,
  bch_id                NUMBER(12),
  bdh_id                NUMBER(12),
  budgetcode_id         NUMBER(12),
  payment_amt           NUMBER(15,2),
  payment_deduction_amt NUMBER(15,2),
  orgid                 NUMBER(4),
  created_by            NUMBER(12) not null,
  created_date          DATE not null,
  updated_by            NUMBER(12),
  updated_date          DATE,
  lang_id               NUMBER(7) not null,
  lg_ip_mac             VARCHAR2(100) not null,
  lg_ip_mac_upd         VARCHAR2(100),
  fi04_n1               NUMBER(15),
  fi04_v2               NVARCHAR2(100),
  fi04_d1               DATE,
  fi04_lo1              CHAR(1)
)
;
comment on table TB_AC_PAYMENT_DET
  is 'This table used for payment details entry.';
comment on column TB_AC_PAYMENT_DET.paymentdet_id
  is 'Primary Key';
comment on column TB_AC_PAYMENT_DET.payment_id
  is 'Reference key TB_AC_PAYMENT_MAS';
comment on column TB_AC_PAYMENT_DET.bch_id
  is 'Reference key TB_AC_BILL_EXP_DETAIL';
comment on column TB_AC_PAYMENT_DET.bdh_id
  is 'Reference key TB_AC_BILL_DEDUCTION_DETAIL';
comment on column TB_AC_PAYMENT_DET.budgetcode_id
  is 'Reference key TB_AC_BUDGETCODE_MAS';
comment on column TB_AC_PAYMENT_DET.payment_amt
  is 'Payment Amount';
comment on column TB_AC_PAYMENT_DET.payment_deduction_amt
  is 'Deduction Amount';
comment on column TB_AC_PAYMENT_DET.orgid
  is 'Organisation Id';
comment on column TB_AC_PAYMENT_DET.created_by
  is 'Created User Identity';
comment on column TB_AC_PAYMENT_DET.created_date
  is 'Created Date';
comment on column TB_AC_PAYMENT_DET.updated_by
  is 'User id who update the data';
comment on column TB_AC_PAYMENT_DET.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_PAYMENT_DET.lang_id
  is 'Language Identity';
comment on column TB_AC_PAYMENT_DET.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_PAYMENT_DET.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_PAYMENT_DET.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_PAYMENT_DET.fi04_v2
  is 'Additional nvarchar2 FI04_V2 to be used in future';
comment on column TB_AC_PAYMENT_DET.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_PAYMENT_DET.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
alter table TB_AC_PAYMENT_DET
  add constraint PK_PAYMENTDET_ID primary key (PAYMENTDET_ID);
alter table TB_AC_PAYMENT_DET
  add constraint FK_BILL_DET_BCH_ID foreign key (BCH_ID)
  references TB_AC_BILL_EXP_DETAIL (BCH_ID);
alter table TB_AC_PAYMENT_DET
  add constraint FK_PAYMENT_BDH_ID foreign key (BDH_ID)
  references TB_AC_BILL_DEDUCTION_DETAIL (BDH_ID);
alter table TB_AC_PAYMENT_DET
  add constraint FK_PAYMENT_BUDGETCODE_ID foreign key (BUDGETCODE_ID)
  references TB_AC_BUDGETCODE_MAS (BUDGETCODE_ID);
alter table TB_AC_PAYMENT_DET
  add constraint FK_PAYMENT_ID_PAYDET foreign key (PAYMENT_ID)
  references TB_AC_PAYMENT_MAS (PAYMENT_ID);

prompt
prompt Creating table TB_AC_PAY_TO_BANK
prompt ================================
prompt
create table TB_AC_PAY_TO_BANK
(
  ptb_id          NUMBER(12) not null,
  orgid           NUMBER(4) not null,
  ptb_entrydate   DATE not null,
  ptb_bankcode    NUMBER(12) not null,
  ptb_bankname    NVARCHAR2(500) not null,
  ptb_bankbranch  NVARCHAR2(100) not null,
  ptb_bankaddress NVARCHAR2(200) not null,
  user_id         NUMBER(7) not null,
  lang_id         NUMBER(7) not null,
  lmoddate        DATE,
  updated_by      NUMBER(7),
  updated_date    DATE,
  ptb_bsrcode     VARCHAR2(15),
  lg_ip_mac       VARCHAR2(100),
  lg_ip_mac_upd   VARCHAR2(100),
  fi04_n1         NUMBER(15),
  fi04_v1         NVARCHAR2(100),
  fi04_d1         DATE,
  fi04_lo1        CHAR(1)
)
;
comment on table TB_AC_PAY_TO_BANK
  is 'This table stores  Entry of Payment to Bank for Itax/Surcharge/WCT';
comment on column TB_AC_PAY_TO_BANK.ptb_id
  is 'Primary Key';
comment on column TB_AC_PAY_TO_BANK.orgid
  is 'Organisation id';
comment on column TB_AC_PAY_TO_BANK.ptb_entrydate
  is 'Entry Date';
comment on column TB_AC_PAY_TO_BANK.ptb_bankcode
  is 'Bank Code';
comment on column TB_AC_PAY_TO_BANK.ptb_bankname
  is 'Bank Name';
comment on column TB_AC_PAY_TO_BANK.ptb_bankbranch
  is 'Bank Branch';
comment on column TB_AC_PAY_TO_BANK.ptb_bankaddress
  is 'Bank Address';
comment on column TB_AC_PAY_TO_BANK.user_id
  is 'User Identity';
comment on column TB_AC_PAY_TO_BANK.lang_id
  is 'Language Identity';
comment on column TB_AC_PAY_TO_BANK.lmoddate
  is 'Last Modification Date';
comment on column TB_AC_PAY_TO_BANK.updated_by
  is 'User id who update the data';
comment on column TB_AC_PAY_TO_BANK.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_PAY_TO_BANK.ptb_bsrcode
  is 'Bank Unique code ';
comment on column TB_AC_PAY_TO_BANK.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_PAY_TO_BANK.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_PAY_TO_BANK.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_PAY_TO_BANK.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_PAY_TO_BANK.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_PAY_TO_BANK.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
alter table TB_AC_PAY_TO_BANK
  add constraint PK_AC_PTB_ID primary key (PTB_ID);
alter table TB_AC_PAY_TO_BANK
  add constraint UK_AC_PTB_BKCODE unique (PTB_BANKCODE);

prompt
prompt Creating table TB_AC_PRIMARYHEAD_MASTER_HIST
prompt ============================================
prompt
create table TB_AC_PRIMARYHEAD_MASTER_HIST
(
  h_pac_headid        NUMBER(12) not null,
  pac_head_id         NUMBER(12),
  codcofdet_id        NUMBER(12),
  pac_head_code       VARCHAR2(10),
  pac_head_desc       NVARCHAR2(500),
  pac_head_parent_id  NUMBER(12),
  pac_head_compo_code VARCHAR2(20),
  orgid               NUMBER(4),
  user_id             NUMBER(7),
  lang_id             NUMBER(4),
  lmoddate            DATE,
  updated_by          NUMBER(7),
  updated_date        DATE,
  lg_ip_mac           VARCHAR2(100),
  lg_ip_mac_upd       VARCHAR2(100),
  h_status            NVARCHAR2(1)
)
;
alter table TB_AC_PRIMARYHEAD_MASTER_HIST
  add constraint PK_AC_H_PAC_HEADID primary key (H_PAC_HEADID);

prompt
prompt Creating table TB_AC_PROJECTEDPROVISIONADJ
prompt ==========================================
prompt
create table TB_AC_PROJECTEDPROVISIONADJ
(
  pa_adjid             NUMBER(12) not null,
  pa_entrydate         DATE not null,
  cpd_bugtype_id       NUMBER(12) not null,
  cpd_provtype_id      NUMBER(12) not null,
  fa_yearid            NUMBER(12),
  pr_projectionid      NUMBER(12),
  pr_expenditureid     NUMBER(12),
  provision_oldamt     NUMBER(15,2),
  org_rev_balamt       NUMBER(15,2),
  transfer_amount      NUMBER(15,2),
  new_org_rev_amount   NUMBER(15,2),
  approved_by          NUMBER(12),
  remark               NVARCHAR2(200),
  orgid                NUMBER(4) not null,
  user_id              NUMBER(7) not null,
  lang_id              NUMBER(7) not null,
  lmoddate             DATE not null,
  updated_by           NUMBER(7),
  updated_date         DATE,
  lg_ip_mac            VARCHAR2(100),
  lg_ip_mac_upd        VARCHAR2(100),
  fi04_n1              NUMBER(15),
  fi04_v1              NVARCHAR2(100),
  fi04_d1              DATE,
  fi04_lo1             CHAR(1),
  dp_deptid            NUMBER(12),
  cpd_id_budgetsubtype NUMBER(12),
  budgetcode_id        NUMBER(12),
  budget_identify_flag CHAR(1)
)
;
comment on table TB_AC_PROJECTEDPROVISIONADJ
  is 'This table stores all the records of provision transfer of amount of account head involved in the accounting system.';
comment on column TB_AC_PROJECTEDPROVISIONADJ.pa_adjid
  is 'Primary Key';
comment on column TB_AC_PROJECTEDPROVISIONADJ.pa_entrydate
  is 'Date of entry of the transfer';
comment on column TB_AC_PROJECTEDPROVISIONADJ.cpd_bugtype_id
  is 'Bugdet type, Reference key-TB_COMPARAM_DET Prefix "REX"	Revenue/Expenditure Type';
comment on column TB_AC_PROJECTEDPROVISIONADJ.cpd_provtype_id
  is 'Bugdet type, Reference key-TB_COMPARAM_DET Prefix "PRV" 	PROVISION TYPE Provision type i.e. Prov. Adjustment or Add. Provision';
comment on column TB_AC_PROJECTEDPROVISIONADJ.fa_yearid
  is 'Reference key-TB_FINANCIALYEAR';
comment on column TB_AC_PROJECTEDPROVISIONADJ.pr_projectionid
  is 'Reference key -TB_AC_PROJECTEDREVENUE';
comment on column TB_AC_PROJECTEDPROVISIONADJ.pr_expenditureid
  is 'Reference key-TB_AC_PROJECTED_EXPENDITURE';
comment on column TB_AC_PROJECTEDPROVISIONADJ.provision_oldamt
  is 'Provison old amt';
comment on column TB_AC_PROJECTEDPROVISIONADJ.org_rev_balamt
  is 'OE and RE balance amount';
comment on column TB_AC_PROJECTEDPROVISIONADJ.transfer_amount
  is 'Total Transfer amount /Additional Budget Provision';
comment on column TB_AC_PROJECTEDPROVISIONADJ.new_org_rev_amount
  is 'New OE/RE Amount';
comment on column TB_AC_PROJECTEDPROVISIONADJ.approved_by
  is 'Approved by field';
comment on column TB_AC_PROJECTEDPROVISIONADJ.remark
  is 'Remark';
comment on column TB_AC_PROJECTEDPROVISIONADJ.orgid
  is 'Organisation id';
comment on column TB_AC_PROJECTEDPROVISIONADJ.user_id
  is 'User Identity';
comment on column TB_AC_PROJECTEDPROVISIONADJ.lang_id
  is 'Language Identity';
comment on column TB_AC_PROJECTEDPROVISIONADJ.lmoddate
  is 'Last Modification Date';
comment on column TB_AC_PROJECTEDPROVISIONADJ.updated_by
  is 'User id who update the data';
comment on column TB_AC_PROJECTEDPROVISIONADJ.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_PROJECTEDPROVISIONADJ.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_PROJECTEDPROVISIONADJ.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_PROJECTEDPROVISIONADJ.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_PROJECTEDPROVISIONADJ.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_PROJECTEDPROVISIONADJ.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_PROJECTEDPROVISIONADJ.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
comment on column TB_AC_PROJECTEDPROVISIONADJ.dp_deptid
  is 'Reference key-tb_department';
comment on column TB_AC_PROJECTEDPROVISIONADJ.cpd_id_budgetsubtype
  is 'Reference key-TB_COMPARAM_DET Prefix "FTP"';
comment on column TB_AC_PROJECTEDPROVISIONADJ.budgetcode_id
  is 'FK - tb_ac_budgetcode_mas';
comment on column TB_AC_PROJECTEDPROVISIONADJ.budget_identify_flag
  is ' Ra appropriation ''R''   and Additional / Supplementary Budget ''A''  Budget transaction identification flag';
alter table TB_AC_PROJECTEDPROVISIONADJ
  add constraint PK_PROVIPROJREVEXPADJ_ADJID primary key (PA_ADJID);
alter table TB_AC_PROJECTEDPROVISIONADJ
  add constraint FI_PROJREVEXPADJ_DP_DEPTID foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);
alter table TB_AC_PROJECTEDPROVISIONADJ
  add constraint FK_APPROVED_BY_EMP_ID foreign key (APPROVED_BY)
  references EMPLOYEE (EMPID);
alter table TB_AC_PROJECTEDPROVISIONADJ
  add constraint FK_CPD_BUGTYPE_ID foreign key (CPD_BUGTYPE_ID)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_PROJECTEDPROVISIONADJ
  add constraint FK_CPD_PROVTYPE_ID foreign key (CPD_PROVTYPE_ID)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_PROJECTEDPROVISIONADJ
  add constraint FK_PROJECTEDADJ_EXPENDITUREID foreign key (PR_EXPENDITUREID)
  references TB_AC_PROJECTED_EXPENDITURE (PR_EXPENDITUREID);
alter table TB_AC_PROJECTEDPROVISIONADJ
  add constraint FK_PROJECTEDPROVISIONADJ_ID foreign key (PR_PROJECTIONID)
  references TB_AC_PROJECTEDREVENUE (PR_PROJECTIONID);
alter table TB_AC_PROJECTEDPROVISIONADJ
  add constraint FK_PROJREVEXPADJ_BUDGETCODE_ID foreign key (BUDGETCODE_ID)
  references TB_AC_BUDGETCODE_MAS (BUDGETCODE_ID);
alter table TB_AC_PROJECTEDPROVISIONADJ
  add constraint FK__PROJREVEXPADJ_FA_YEARID foreign key (FA_YEARID)
  references TB_FINANCIALYEAR (FA_YEARID);

prompt
prompt Creating table TB_AC_PROJECTEDPROVISIONADJ_TR
prompt =============================================
prompt
create table TB_AC_PROJECTEDPROVISIONADJ_TR
(
  pa_adjid_tr        NUMBER(12) not null,
  pa_adjid           NUMBER(12),
  pr_projectionid    NUMBER(12),
  pr_expenditureid   NUMBER(12),
  provision_oldamt   NUMBER(15,2),
  transfer_amount    NUMBER(15,2),
  new_org_rev_amount NUMBER(15,2),
  orgid              NUMBER(4) not null,
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(7) not null,
  lmoddate           DATE not null,
  updated_by         NUMBER(7),
  updated_date       DATE,
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  fi04_n1            NUMBER(15),
  fi04_v1            NVARCHAR2(100),
  fi04_d1            DATE,
  fi04_lo1           CHAR(1),
  dp_deptid          NUMBER(12),
  org_rev_balamt     NUMBER(15,2),
  budgetcode_id      NUMBER(12)
)
;
comment on table TB_AC_PROJECTEDPROVISIONADJ_TR
  is 'This table stores all the records of provision transfer of amount of account head involved in the accounting system.';
comment on column TB_AC_PROJECTEDPROVISIONADJ_TR.pa_adjid_tr
  is 'Primary Key';
comment on column TB_AC_PROJECTEDPROVISIONADJ_TR.pa_adjid
  is 'fk ref key -TB_AC_PROJECTEDPROVISIONADJ';
comment on column TB_AC_PROJECTEDPROVISIONADJ_TR.pr_projectionid
  is 'fk ref key -  tb_ac_projectedrevenue';
comment on column TB_AC_PROJECTEDPROVISIONADJ_TR.pr_expenditureid
  is 'fk ref key  -tb_ac_projected_expenditure';
comment on column TB_AC_PROJECTEDPROVISIONADJ_TR.provision_oldamt
  is 'Old projected amt';
comment on column TB_AC_PROJECTEDPROVISIONADJ_TR.transfer_amount
  is 'Transfer amount';
comment on column TB_AC_PROJECTEDPROVISIONADJ_TR.new_org_rev_amount
  is 'Balanc amt';
comment on column TB_AC_PROJECTEDPROVISIONADJ_TR.orgid
  is 'Organisation id';
comment on column TB_AC_PROJECTEDPROVISIONADJ_TR.user_id
  is 'User Identity';
comment on column TB_AC_PROJECTEDPROVISIONADJ_TR.lang_id
  is 'Language Identity';
comment on column TB_AC_PROJECTEDPROVISIONADJ_TR.lmoddate
  is 'Last Modification Date';
comment on column TB_AC_PROJECTEDPROVISIONADJ_TR.updated_by
  is 'User id who update the data';
comment on column TB_AC_PROJECTEDPROVISIONADJ_TR.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_PROJECTEDPROVISIONADJ_TR.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_PROJECTEDPROVISIONADJ_TR.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_PROJECTEDPROVISIONADJ_TR.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_PROJECTEDPROVISIONADJ_TR.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_PROJECTEDPROVISIONADJ_TR.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_PROJECTEDPROVISIONADJ_TR.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
comment on column TB_AC_PROJECTEDPROVISIONADJ_TR.dp_deptid
  is 'Department id';
comment on column TB_AC_PROJECTEDPROVISIONADJ_TR.org_rev_balamt
  is 'OE and RE balance amount';
comment on column TB_AC_PROJECTEDPROVISIONADJ_TR.budgetcode_id
  is 'FK - tb_ac_budgetcode_ma';
alter table TB_AC_PROJECTEDPROVISIONADJ_TR
  add constraint PK_PA_ADJID_TR primary key (PA_ADJID_TR);
alter table TB_AC_PROJECTEDPROVISIONADJ_TR
  add constraint FI_PROJREVEXPA_TR_DP_DEPTID foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);
alter table TB_AC_PROJECTEDPROVISIONADJ_TR
  add constraint FK_PA_ADJID foreign key (PA_ADJID)
  references TB_AC_PROJECTEDPROVISIONADJ (PA_ADJID);
alter table TB_AC_PROJECTEDPROVISIONADJ_TR
  add constraint FK_PROJECTEDADJ_BUDGETCODE_ID foreign key (BUDGETCODE_ID)
  references TB_AC_BUDGETCODE_MAS (BUDGETCODE_ID);
alter table TB_AC_PROJECTEDPROVISIONADJ_TR
  add constraint FK_PROJECTEDADJ_TR_EXPID foreign key (PR_EXPENDITUREID)
  references TB_AC_PROJECTED_EXPENDITURE (PR_EXPENDITUREID);
alter table TB_AC_PROJECTEDPROVISIONADJ_TR
  add constraint FK_PROJECTEDPROVISIONADJ_TR_ID foreign key (PR_PROJECTIONID)
  references TB_AC_PROJECTEDREVENUE (PR_PROJECTIONID);

prompt
prompt Creating table TB_AC_TDS_TAXHEADS
prompt =================================
prompt
create table TB_AC_TDS_TAXHEADS
(
  tds_id                NUMBER(12) not null,
  orgid                 NUMBER(4) not null,
  tds_entrydate         DATE not null,
  cpd_id_deduction_type NUMBER(12),
  tds_description       NVARCHAR2(100),
  tds_status_flg        NVARCHAR2(1) not null,
  user_id               NUMBER(7) not null,
  lang_id               NUMBER(7) not null,
  lmoddate              DATE not null,
  updated_by            NUMBER(7),
  updated_date          DATE,
  lg_ip_mac             VARCHAR2(100),
  lg_ip_mac_upd         VARCHAR2(100),
  fi04_n1               NUMBER(15),
  fi04_v1               NVARCHAR2(100),
  fi04_d1               DATE,
  fi04_lo1              CHAR(1),
  budgetcode_id         NUMBER(12)
)
;
comment on table TB_AC_TDS_TAXHEADS
  is 'Table is used to store link between TDS Head and accounnt Head ';
comment on column TB_AC_TDS_TAXHEADS.tds_id
  is 'Primary Key';
comment on column TB_AC_TDS_TAXHEADS.orgid
  is 'Organisation id';
comment on column TB_AC_TDS_TAXHEADS.tds_entrydate
  is 'Entry Date';
comment on column TB_AC_TDS_TAXHEADS.cpd_id_deduction_type
  is 'Comparam det Reference key -tb_comparam_det for deduction type , Income Tax , Surcharge Head  etc.';
comment on column TB_AC_TDS_TAXHEADS.tds_description
  is 'Description of heads, purposely kept so that meaning full description can be given against heads, rather than heads description';
comment on column TB_AC_TDS_TAXHEADS.tds_status_flg
  is 'A- Active / I-Inactive';
comment on column TB_AC_TDS_TAXHEADS.user_id
  is 'User Identity';
comment on column TB_AC_TDS_TAXHEADS.lang_id
  is 'Language Identity';
comment on column TB_AC_TDS_TAXHEADS.lmoddate
  is 'Last Modification Date';
comment on column TB_AC_TDS_TAXHEADS.updated_by
  is 'User id who update the data';
comment on column TB_AC_TDS_TAXHEADS.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_TDS_TAXHEADS.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_TDS_TAXHEADS.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_TDS_TAXHEADS.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_TDS_TAXHEADS.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_TDS_TAXHEADS.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_TDS_TAXHEADS.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
comment on column TB_AC_TDS_TAXHEADS.budgetcode_id
  is 'FK - tb_ac_budgetcode_mas';
alter table TB_AC_TDS_TAXHEADS
  add constraint PK_AC_TDS_ID primary key (TDS_ID);
alter table TB_AC_TDS_TAXHEADS
  add constraint FK_TDS_TAXHEADS_CPD_IDDEDTYPE foreign key (CPD_ID_DEDUCTION_TYPE)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_TDS_TAXHEADS
  add constraint FK_TDS_TAXHEAD_BUDGETCODE_ID foreign key (BUDGETCODE_ID)
  references TB_AC_BUDGETCODE_MAS (BUDGETCODE_ID);

prompt
prompt Creating table TB_AC_TDS_TAXHEADS_HIST
prompt ======================================
prompt
create table TB_AC_TDS_TAXHEADS_HIST
(
  h_tdsid               NUMBER(12) not null,
  tds_id                NUMBER(12),
  orgid                 NUMBER(4),
  tds_entrydate         DATE,
  fund_id               NUMBER(12),
  function_id           NUMBER(12),
  field_id              NUMBER(12),
  pac_head_id           NUMBER(12),
  sac_head_id           NUMBER(12),
  cpd_id_deduction_type NUMBER(12),
  tds_description       NVARCHAR2(100),
  tds_status_flg        NVARCHAR2(1),
  user_id               NUMBER(7),
  lang_id               NUMBER(7),
  lmoddate              DATE,
  updated_by            NUMBER(7),
  updated_date          DATE,
  lg_ip_mac             VARCHAR2(100),
  lg_ip_mac_upd         VARCHAR2(100),
  fi04_n1               NUMBER(15),
  fi04_v1               NVARCHAR2(100),
  fi04_d1               DATE,
  fi04_lo1              CHAR(1),
  h_status              CHAR(1)
)
;
alter table TB_AC_TDS_TAXHEADS_HIST
  add constraint PK_AC_H_TDSID primary key (H_TDSID);

prompt
prompt Creating table TB_AC_TENDER_DET
prompt ===============================
prompt
create table TB_AC_TENDER_DET
(
  tr_tenderid_det NUMBER(12) not null,
  tr_tender_id    NUMBER(12),
  budgetary_prov  NUMBER(15,2),
  balance_prov    NUMBER(15,2),
  orgid           NUMBER(4) not null,
  created_by      NUMBER(7) not null,
  created_date    DATE not null,
  updated_by      NUMBER(7),
  updated_date    DATE,
  lang_id         NUMBER(7) not null,
  lg_ip_mac       VARCHAR2(100),
  lg_ip_mac_upd   VARCHAR2(100),
  fi04_n1         NUMBER(15),
  fi04_v1         NVARCHAR2(100),
  fi04_d1         DATE,
  fi04_lo1        CHAR(1),
  budgetcode_id   NUMBER(12)
)
;
comment on column TB_AC_TENDER_DET.tr_tenderid_det
  is 'Primary Key';
comment on column TB_AC_TENDER_DET.tr_tender_id
  is 'Reference key  -- TB_AC_TENDER_MASTER';
comment on column TB_AC_TENDER_DET.budgetary_prov
  is 'Budgetary Provision';
comment on column TB_AC_TENDER_DET.balance_prov
  is 'Balance Provision';
comment on column TB_AC_TENDER_DET.orgid
  is 'Organisation id';
comment on column TB_AC_TENDER_DET.created_by
  is 'Created User Identity';
comment on column TB_AC_TENDER_DET.created_date
  is 'Created Date';
comment on column TB_AC_TENDER_DET.updated_by
  is 'User id who update the data';
comment on column TB_AC_TENDER_DET.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_TENDER_DET.lang_id
  is 'Language Identity';
comment on column TB_AC_TENDER_DET.lg_ip_mac
  is 'Client Machine is Login Name | IP Address | Physical Address';
comment on column TB_AC_TENDER_DET.lg_ip_mac_upd
  is 'Updated Client Machine is Login Name | IP Address | Physical Address';
comment on column TB_AC_TENDER_DET.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_TENDER_DET.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_TENDER_DET.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_TENDER_DET.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
comment on column TB_AC_TENDER_DET.budgetcode_id
  is 'FK - tb_ac_budgetcode_mas';
alter table TB_AC_TENDER_DET
  add constraint PK_TR_TENDERID_DET primary key (TR_TENDERID_DET);
alter table TB_AC_TENDER_DET
  add constraint FK_TR_BUDGETCODE_ID foreign key (BUDGETCODE_ID)
  references TB_AC_BUDGETCODE_MAS (BUDGETCODE_ID);
alter table TB_AC_TENDER_DET
  add constraint FK_TR_TENDER_IDD foreign key (TR_TENDER_ID)
  references TB_AC_TENDER_MASTER (TR_TENDER_ID);

prompt
prompt Creating table TB_AC_VOUCHER
prompt ============================
prompt
create table TB_AC_VOUCHER
(
  vou_id                NUMBER(12) not null,
  vou_no                NVARCHAR2(12),
  vou_date              DATE,
  vou_posting_date      DATE,
  vou_type_cpd_id       NUMBER(12),
  vou_subtype_cpd_id    NUMBER(12),
  dp_deptid             NUMBER(12),
  vou_reference_no      NVARCHAR2(20),
  vou_reference_no_date DATE,
  narration             NVARCHAR2(1000),
  payer_payee           NVARCHAR2(1000),
  field_id              NUMBER(12),
  orgid                 NUMBER(4),
  created_by            NUMBER(7) not null,
  created_date          DATE not null,
  updated_by            NUMBER(7),
  updated_date          DATE,
  lang_id               NUMBER(7) not null,
  lg_ip_mac             VARCHAR2(100),
  lg_ip_mac_upd         VARCHAR2(100),
  autho_id              NUMBER(7),
  autho_date            DATE,
  autho_flg             CHAR(1),
  entry_type            NUMBER(2) default 1,
  fi04_n1               NUMBER(15),
  fi04_v2               NVARCHAR2(100),
  fi04_d1               DATE,
  fi04_lo1              CHAR(1)
)
;
comment on table TB_AC_VOUCHER
  is 'This table used for DEAS Voucher master entry.';
comment on column TB_AC_VOUCHER.vou_id
  is 'Primary Key';
comment on column TB_AC_VOUCHER.vou_no
  is 'Voucher number system generated';
comment on column TB_AC_VOUCHER.vou_date
  is 'Voucher entry Date';
comment on column TB_AC_VOUCHER.vou_posting_date
  is 'Voucher posting entry Date';
comment on column TB_AC_VOUCHER.vou_type_cpd_id
  is 'Voucher type jv/rv/pv/cv - ref tb_comparam_det ';
comment on column TB_AC_VOUCHER.vou_subtype_cpd_id
  is 'Voucher sub type- ref tb_comparam_det ';
comment on column TB_AC_VOUCHER.dp_deptid
  is 'Voucher Department  ref tb_department';
comment on column TB_AC_VOUCHER.vou_reference_no
  is 'Voucher ref number receipt/bill/contra etc number ';
comment on column TB_AC_VOUCHER.vou_reference_no_date
  is 'Voucher ref number receipt/bill/contra etc number ';
comment on column TB_AC_VOUCHER.narration
  is 'Voucher NARRATION ';
comment on column TB_AC_VOUCHER.payer_payee
  is 'Payer and Payee from receipt master and payment voucher ';
comment on column TB_AC_VOUCHER.field_id
  is 'Field Master Reference key  --TB_AC_FIELD_MASTER';
comment on column TB_AC_VOUCHER.orgid
  is 'Organisation Id';
comment on column TB_AC_VOUCHER.created_by
  is 'Created User Identity';
comment on column TB_AC_VOUCHER.created_date
  is 'Created Date';
comment on column TB_AC_VOUCHER.updated_by
  is 'User id who update the data';
comment on column TB_AC_VOUCHER.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_VOUCHER.lang_id
  is 'Language Identity';
comment on column TB_AC_VOUCHER.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_VOUCHER.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_VOUCHER.autho_id
  is 'Authoer Id';
comment on column TB_AC_VOUCHER.autho_date
  is 'Sysdate';
comment on column TB_AC_VOUCHER.autho_flg
  is 'Flag';
comment on column TB_AC_VOUCHER.entry_type
  is 'Voucher entry type: System :- 1,Manual :- 2';
comment on column TB_AC_VOUCHER.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_VOUCHER.fi04_v2
  is 'Additional nvarchar2 FI04_V2 to be used in future';
comment on column TB_AC_VOUCHER.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_VOUCHER.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
alter table TB_AC_VOUCHER
  add constraint PK_TB_AC_VOUCHER_HD_VOU_ID primary key (VOU_ID);
alter table TB_AC_VOUCHER
  add constraint FK_DP_DEPTID_VOUCHER foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);
alter table TB_AC_VOUCHER
  add constraint FK_VOUCHERHD_FIELD_ID foreign key (FIELD_ID)
  references TB_AC_FIELD_MASTER (FIELD_ID);
alter table TB_AC_VOUCHER
  add constraint FK_VOU_SUBTYPE_CPDID_VOUCSUBID foreign key (VOU_SUBTYPE_CPD_ID)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_VOUCHER
  add constraint FK_VOU_TYPE_CPD_ID_VOUCHER foreign key (VOU_TYPE_CPD_ID)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_VOUCHER
  add constraint NN_AC_VOUCHER_ORGID
  check ("ORGID" IS NOT NULL);
alter table TB_AC_VOUCHER
  add constraint NN_AC_VOUCHER_VOU_HD_DATE
  check ("VOU_DATE" IS NOT NULL);

prompt
prompt Creating table TB_AC_VOUCHERTEMPLATE_MAS
prompt ========================================
prompt
create table TB_AC_VOUCHERTEMPLATE_MAS
(
  template_id         NUMBER(12) not null,
  cpd_id_mapping_type NUMBER(12),
  fa_yearid           NUMBER(12),
  cpd_id_voucher_type NUMBER(12),
  dp_deptid           NUMBER(12),
  cpd_id_template_for NUMBER(12),
  cpd_id_status_flg   NUMBER(12) not null,
  orgid               NUMBER(4) not null,
  created_by          NUMBER(12) not null,
  lang_id             NUMBER(7) not null,
  created_date        DATE not null,
  updated_by          NUMBER(7),
  updated_date        DATE,
  lg_ip_mac           VARCHAR2(100),
  lg_ip_mac_upd       VARCHAR2(100),
  fi04_n1             NUMBER(15),
  fi04_v1             NVARCHAR2(100),
  fi04_d1             DATE,
  fi04_lo1            CHAR(1)
)
;
comment on column TB_AC_VOUCHERTEMPLATE_MAS.template_id
  is 'Primary Key';
comment on column TB_AC_VOUCHERTEMPLATE_MAS.cpd_id_mapping_type
  is 'Permanent / Financial year wise  prifix -"MTP"';
comment on column TB_AC_VOUCHERTEMPLATE_MAS.fa_yearid
  is 'Financial Year ID';
comment on column TB_AC_VOUCHERTEMPLATE_MAS.cpd_id_voucher_type
  is 'Prefix "VOT"';
comment on column TB_AC_VOUCHERTEMPLATE_MAS.dp_deptid
  is 'fk -TB_DEPARTMENT';
comment on column TB_AC_VOUCHERTEMPLATE_MAS.cpd_id_template_for
  is 'Prefix  "TDP"';
comment on column TB_AC_VOUCHERTEMPLATE_MAS.cpd_id_status_flg
  is 'Prefix - ''ACN''';
comment on column TB_AC_VOUCHERTEMPLATE_MAS.orgid
  is 'Organisation id';
comment on column TB_AC_VOUCHERTEMPLATE_MAS.created_by
  is 'User Identity';
comment on column TB_AC_VOUCHERTEMPLATE_MAS.lang_id
  is 'Language Identity';
comment on column TB_AC_VOUCHERTEMPLATE_MAS.created_date
  is 'Last Modification Date';
comment on column TB_AC_VOUCHERTEMPLATE_MAS.updated_by
  is 'User id who update the data';
comment on column TB_AC_VOUCHERTEMPLATE_MAS.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_VOUCHERTEMPLATE_MAS.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_VOUCHERTEMPLATE_MAS.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_VOUCHERTEMPLATE_MAS.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_VOUCHERTEMPLATE_MAS.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_VOUCHERTEMPLATE_MAS.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_VOUCHERTEMPLATE_MAS.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
alter table TB_AC_VOUCHERTEMPLATE_MAS
  add constraint PK_TEMPLATE_ID primary key (TEMPLATE_ID);
alter table TB_AC_VOUCHERTEMPLATE_MAS
  add constraint FK_CPD_ID_MAPPING_TYPE foreign key (CPD_ID_MAPPING_TYPE)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_VOUCHERTEMPLATE_MAS
  add constraint FK_TEMPLATE_DP_DEPTID foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);
alter table TB_AC_VOUCHERTEMPLATE_MAS
  add constraint FK_TEMPLATE_FA_YEARID foreign key (FA_YEARID)
  references TB_FINANCIALYEAR (FA_YEARID);
alter table TB_AC_VOUCHERTEMPLATE_MAS
  add constraint FK_TEMPL_CPD_ID_TEMPLATE_FOR foreign key (CPD_ID_TEMPLATE_FOR)
  references TB_COMPARAM_DET (CPD_ID);

prompt
prompt Creating table TB_AC_VOUCHERTEMPLATE_DET
prompt ========================================
prompt
create table TB_AC_VOUCHERTEMPLATE_DET
(
  template_id_det     NUMBER(12) not null,
  template_id         NUMBER(12),
  cpd_id_account_type NUMBER(12),
  cpd_id_drcr         NUMBER(12),
  cpd_id_pay_mode     NUMBER(12),
  budgetcode_id       NUMBER(12),
  cpd_status_flg      NUMBER(12) not null,
  orgid               NUMBER(4) not null,
  created_by          NUMBER(12) not null,
  lang_id             NUMBER(7) not null,
  created_date        DATE not null,
  updated_by          NUMBER(7),
  updated_date        DATE,
  lg_ip_mac           VARCHAR2(100),
  lg_ip_mac_upd       VARCHAR2(100),
  fi04_n1             NUMBER(15),
  fi04_v1             NVARCHAR2(100),
  fi04_d1             DATE,
  fi04_lo1            CHAR(1)
)
;
comment on column TB_AC_VOUCHERTEMPLATE_DET.template_id_det
  is 'Primary Key';
comment on column TB_AC_VOUCHERTEMPLATE_DET.template_id
  is 'fk -TB_AC_VOUCHERTEMPLATE_MAS ';
comment on column TB_AC_VOUCHERTEMPLATE_DET.cpd_id_account_type
  is 'fk - TB_COMPARAM_DET Prefix  - COA';
comment on column TB_AC_VOUCHERTEMPLATE_DET.cpd_id_drcr
  is 'fk- TB_COMPARAM_DET Prefix "DCR"';
comment on column TB_AC_VOUCHERTEMPLATE_DET.cpd_id_pay_mode
  is 'fk -TB_COMPARAM_DET Prefix "PAY"';
comment on column TB_AC_VOUCHERTEMPLATE_DET.budgetcode_id
  is 'fk TB_AC_BUDGETCODE_MAS';
comment on column TB_AC_VOUCHERTEMPLATE_DET.cpd_status_flg
  is 'fk -TB_COMPARAM_DET Prefix - ''ACN''';
comment on column TB_AC_VOUCHERTEMPLATE_DET.orgid
  is 'Organisation id';
comment on column TB_AC_VOUCHERTEMPLATE_DET.created_by
  is 'User Identity';
comment on column TB_AC_VOUCHERTEMPLATE_DET.lang_id
  is 'Language Identity';
comment on column TB_AC_VOUCHERTEMPLATE_DET.created_date
  is 'Last Modification Date';
comment on column TB_AC_VOUCHERTEMPLATE_DET.updated_by
  is 'User id who update the data';
comment on column TB_AC_VOUCHERTEMPLATE_DET.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_VOUCHERTEMPLATE_DET.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_VOUCHERTEMPLATE_DET.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_VOUCHERTEMPLATE_DET.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_VOUCHERTEMPLATE_DET.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_VOUCHERTEMPLATE_DET.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_VOUCHERTEMPLATE_DET.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
alter table TB_AC_VOUCHERTEMPLATE_DET
  add constraint PK_TEMPLATE_ID_DET primary key (TEMPLATE_ID_DET);
alter table TB_AC_VOUCHERTEMPLATE_DET
  add constraint FK_TEMP_BUDGETCODE_ID foreign key (BUDGETCODE_ID)
  references TB_AC_BUDGETCODE_MAS (BUDGETCODE_ID);
alter table TB_AC_VOUCHERTEMPLATE_DET
  add constraint FK_TEMP_CPD_ID_ACCOUNT_TYPE foreign key (CPD_ID_ACCOUNT_TYPE)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_VOUCHERTEMPLATE_DET
  add constraint FK_TEMP_CPD_ID_DRCR foreign key (CPD_ID_DRCR)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_VOUCHERTEMPLATE_DET
  add constraint FK_TEMP_CPD_ID_PAY_MODE foreign key (CPD_ID_PAY_MODE)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_VOUCHERTEMPLATE_DET
  add constraint FK_TEMP_TEMPLATE_ID foreign key (TEMPLATE_ID)
  references TB_AC_VOUCHERTEMPLATE_MAS (TEMPLATE_ID);

prompt
prompt Creating table TB_AC_VOUCHER_DET
prompt ================================
prompt
create table TB_AC_VOUCHER_DET
(
  voudet_id     NUMBER(12) not null,
  vou_id        NUMBER(12),
  voudet_amt    NUMBER(15,2),
  drcr_cpd_id   NUMBER(15),
  orgid         NUMBER(4),
  created_by    NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  created_date  DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  fi04_n1       NUMBER(15),
  fi04_v2       NVARCHAR2(100),
  fi04_d1       DATE,
  fi04_lo1      CHAR(1),
  budgetcode_id NUMBER(12)
)
;
comment on table TB_AC_VOUCHER_DET
  is 'Table is used to store voucher Debit and Credit entry';
comment on column TB_AC_VOUCHER_DET.voudet_id
  is 'Primary Key';
comment on column TB_AC_VOUCHER_DET.vou_id
  is 'Fund Master Reference key --tb_ac_voucher_hd';
comment on column TB_AC_VOUCHER_DET.voudet_amt
  is 'Voucher Amount';
comment on column TB_AC_VOUCHER_DET.drcr_cpd_id
  is 'Voucher  Dr/ Cr. Prefix -DCR';
comment on column TB_AC_VOUCHER_DET.orgid
  is 'Organisation Id';
comment on column TB_AC_VOUCHER_DET.created_by
  is 'Created User Identity';
comment on column TB_AC_VOUCHER_DET.lang_id
  is 'Language Identity';
comment on column TB_AC_VOUCHER_DET.created_date
  is 'Created Date';
comment on column TB_AC_VOUCHER_DET.updated_by
  is 'User id who update the data';
comment on column TB_AC_VOUCHER_DET.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_VOUCHER_DET.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_VOUCHER_DET.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_AC_VOUCHER_DET.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_VOUCHER_DET.fi04_v2
  is 'Additional nvarchar2 FI04_V2 to be used in future';
comment on column TB_AC_VOUCHER_DET.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_VOUCHER_DET.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
comment on column TB_AC_VOUCHER_DET.budgetcode_id
  is 'FK - tb_ac_budgetcode_mas';
alter table TB_AC_VOUCHER_DET
  add constraint PK_VOUDET_ID primary key (VOUDET_ID);
alter table TB_AC_VOUCHER_DET
  add constraint FK_DRCR_CPD_ID foreign key (DRCR_CPD_ID)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_AC_VOUCHER_DET
  add constraint FK_VOU_BUDGETCODE_ID foreign key (BUDGETCODE_ID)
  references TB_AC_BUDGETCODE_MAS (BUDGETCODE_ID);
alter table TB_AC_VOUCHER_DET
  add constraint FK_VOU_ID foreign key (VOU_ID)
  references TB_AC_VOUCHER (VOU_ID);

prompt
prompt Creating table TB_AC_VOUCHER_POST_MASTER
prompt ========================================
prompt
create table TB_AC_VOUCHER_POST_MASTER
(
  vp_id                   NUMBER(12) not null,
  tran_tokenno            NVARCHAR2(20),
  tran_date               DATE,
  trantype_cpd_id         NUMBER(12),
  dp_deptid               NUMBER(12),
  field_id                NUMBER(12),
  emp_id                  NUMBER(12),
  vouchersubtype_cpd_id   NUMBER(12),
  mode_cpd_id             NUMBER(12),
  accountentrytype_cpd_id NUMBER(12),
  postingtype_cpd_id      NUMBER(12),
  posting_date            DATE,
  posting_remark          NVARCHAR2(1000),
  orgid                   NUMBER(4) not null,
  created_by              NUMBER(7) not null,
  created_date            DATE not null,
  updated_by              NUMBER(7),
  updated_date            DATE,
  lang_id                 NUMBER(7) not null,
  lg_ip_mac               VARCHAR2(100),
  lg_ip_mac_upd           VARCHAR2(100),
  fi04_n1                 NUMBER(15),
  fi04_v1                 NVARCHAR2(100),
  fi04_d1                 DATE,
  fi04_lo1                CHAR(1)
)
;
comment on column TB_AC_VOUCHER_POST_MASTER.vp_id
  is 'Primary Key';
comment on column TB_AC_VOUCHER_POST_MASTER.tran_tokenno
  is 'Transaction Token Number';
comment on column TB_AC_VOUCHER_POST_MASTER.tran_date
  is 'Transaction Entry Date';
comment on column TB_AC_VOUCHER_POST_MASTER.trantype_cpd_id
  is 'Transaction type Receipt Voucher / Contra Voucher/ Journal Voucher/Payment Voucher  ';
comment on column TB_AC_VOUCHER_POST_MASTER.dp_deptid
  is 'Reference key  -- tb_department';
comment on column TB_AC_VOUCHER_POST_MASTER.field_id
  is 'Field Master Reference key  --TB_AC_FIELD_MASTER';
comment on column TB_AC_VOUCHER_POST_MASTER.vouchersubtype_cpd_id
  is 'Voucher Sub Type';
comment on column TB_AC_VOUCHER_POST_MASTER.mode_cpd_id
  is 'Receipt / Payment Mode Prefix PAY';
comment on column TB_AC_VOUCHER_POST_MASTER.accountentrytype_cpd_id
  is 'Cpd_id   1.Single Entry 2.Double Entry 3.Both';
comment on column TB_AC_VOUCHER_POST_MASTER.postingtype_cpd_id
  is 'Cpd ID -1.Individual               2.Group ';
comment on column TB_AC_VOUCHER_POST_MASTER.posting_date
  is 'User to enter posting date.';
comment on column TB_AC_VOUCHER_POST_MASTER.posting_remark
  is 'Remark';
comment on column TB_AC_VOUCHER_POST_MASTER.orgid
  is 'Organisation id';
comment on column TB_AC_VOUCHER_POST_MASTER.created_by
  is 'Created User Identity';
comment on column TB_AC_VOUCHER_POST_MASTER.created_date
  is 'Created Date';
comment on column TB_AC_VOUCHER_POST_MASTER.updated_by
  is 'User id who update the data';
comment on column TB_AC_VOUCHER_POST_MASTER.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_VOUCHER_POST_MASTER.lang_id
  is 'Language Identity';
comment on column TB_AC_VOUCHER_POST_MASTER.lg_ip_mac
  is 'Client Machine is Login Name | IP Address | Physical Address';
comment on column TB_AC_VOUCHER_POST_MASTER.lg_ip_mac_upd
  is 'Updated Client Machine is Login Name | IP Address | Physical Address';
comment on column TB_AC_VOUCHER_POST_MASTER.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_VOUCHER_POST_MASTER.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_VOUCHER_POST_MASTER.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_VOUCHER_POST_MASTER.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
alter table TB_AC_VOUCHER_POST_MASTER
  add constraint PK_VOUCHER_POST_MAS primary key (VP_ID);
alter table TB_AC_VOUCHER_POST_MASTER
  add constraint FK_VOUCHER_POST_MAS_DP_DEPTID foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);
alter table TB_AC_VOUCHER_POST_MASTER
  add constraint FK_VOUCHER_POST_MAS_FIELD_ID foreign key (FIELD_ID)
  references TB_AC_FIELD_MASTER (FIELD_ID);

prompt
prompt Creating table TB_AC_VOUCHER_POST_DETAIL
prompt ========================================
prompt
create table TB_AC_VOUCHER_POST_DETAIL
(
  vp_det_id     NUMBER(12) not null,
  vp_id         NUMBER(12),
  ref_id        NUMBER(12),
  orgid         NUMBER(4) not null,
  created_by    NUMBER(7) not null,
  created_date  DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lang_id       NUMBER(7) not null,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  fi04_n1       NUMBER(15),
  fi04_v1       NVARCHAR2(100),
  fi04_d1       DATE,
  fi04_lo1      CHAR(1)
)
;
comment on column TB_AC_VOUCHER_POST_DETAIL.vp_det_id
  is 'Primary Key';
comment on column TB_AC_VOUCHER_POST_DETAIL.vp_id
  is 'Reference key  -- TB_AC_VOUCHER_POST_master';
comment on column TB_AC_VOUCHER_POST_DETAIL.ref_id
  is 'Receipt ,Payment ,Bill,Contra , water bill, etc id';
comment on column TB_AC_VOUCHER_POST_DETAIL.orgid
  is 'Organisation id';
comment on column TB_AC_VOUCHER_POST_DETAIL.created_by
  is 'Created User Identity';
comment on column TB_AC_VOUCHER_POST_DETAIL.created_date
  is 'Created Date';
comment on column TB_AC_VOUCHER_POST_DETAIL.updated_by
  is 'User id who update the data';
comment on column TB_AC_VOUCHER_POST_DETAIL.updated_date
  is 'Date on which data is going to update';
comment on column TB_AC_VOUCHER_POST_DETAIL.lang_id
  is 'Language Identity';
comment on column TB_AC_VOUCHER_POST_DETAIL.lg_ip_mac
  is 'Client Machine is Login Name | IP Address | Physical Address';
comment on column TB_AC_VOUCHER_POST_DETAIL.lg_ip_mac_upd
  is 'Updated Client Machine is Login Name | IP Address | Physical Address';
comment on column TB_AC_VOUCHER_POST_DETAIL.fi04_n1
  is 'Additional number FI04_N1 to be used in future';
comment on column TB_AC_VOUCHER_POST_DETAIL.fi04_v1
  is 'Additional nvarchar2 FI04_V1 to be used in future';
comment on column TB_AC_VOUCHER_POST_DETAIL.fi04_d1
  is 'Additional Date FI04_D1 to be used in future';
comment on column TB_AC_VOUCHER_POST_DETAIL.fi04_lo1
  is 'Additional Logical field FI04_LO1 to be used in future';
alter table TB_AC_VOUCHER_POST_DETAIL
  add constraint PK_VOUCHER_POST_DETAIL primary key (VP_DET_ID);
alter table TB_AC_VOUCHER_POST_DETAIL
  add constraint FK_VOUCHER_POST_DET_VP_ID foreign key (VP_ID)
  references TB_AC_VOUCHER_POST_MASTER (VP_ID);


spool off

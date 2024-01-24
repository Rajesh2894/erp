---------------------------------------------------------
-- Export file for user SERVICE1                       --
-- Created by kailash.agarwal on 3/17/2017, 5:01:01 PM --
---------------------------------------------------------

set define off
spool water.log

prompt
prompt Creating table TB_CCNSIZE_PRM
prompt =============================
prompt
create table TB_CCNSIZE_PRM
(
  cns_id        NUMBER(12) not null,
  cns_from      NUMBER(5,2),
  cns_to        NUMBER(5,2),
  cns_value     NUMBER(5,2),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  lmoddate      DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  cns_frmdt     DATE,
  cns_todt      DATE,
  wt_v1         NVARCHAR2(100),
  wt_v2         NVARCHAR2(100),
  wt_v3         NVARCHAR2(100),
  wt_v4         NVARCHAR2(100),
  wt_v5         NVARCHAR2(100),
  wt_n1         NUMBER(15),
  wt_n2         NUMBER(15),
  wt_n3         NUMBER(15),
  wt_n4         NUMBER(15),
  wt_n5         NUMBER(15),
  wt_d1         DATE,
  wt_d2         DATE,
  wt_d3         DATE,
  wt_lo1        CHAR(1),
  wt_lo2        CHAR(1),
  wt_lo3        CHAR(1),
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100)
)
;
comment on column TB_CCNSIZE_PRM.cns_id
  is 'generated id';
comment on column TB_CCNSIZE_PRM.cns_from
  is 'ccn size value range from';
comment on column TB_CCNSIZE_PRM.cns_to
  is 'ccn size value range to';
comment on column TB_CCNSIZE_PRM.cns_value
  is 'value of recommended size';
comment on column TB_CCNSIZE_PRM.orgid
  is 'Organization Id';
comment on column TB_CCNSIZE_PRM.user_id
  is 'User id';
comment on column TB_CCNSIZE_PRM.lang_id
  is 'Language id';
comment on column TB_CCNSIZE_PRM.lmoddate
  is 'Last Modification Date';
comment on column TB_CCNSIZE_PRM.updated_by
  is 'User id who update the data';
comment on column TB_CCNSIZE_PRM.updated_date
  is 'Date on which data is going to update';
comment on column TB_CCNSIZE_PRM.cns_frmdt
  is 'Connection size From Date';
comment on column TB_CCNSIZE_PRM.cns_todt
  is 'Connection size To Date';
comment on column TB_CCNSIZE_PRM.wt_v1
  is 'Additional nvarchar2 WT_V1 to be used in future';
comment on column TB_CCNSIZE_PRM.wt_v2
  is 'Additional nvarchar2 WT_V2 to be used in future';
comment on column TB_CCNSIZE_PRM.wt_v3
  is 'Additional nvarchar2 WT_V3 to be used in future';
comment on column TB_CCNSIZE_PRM.wt_v4
  is 'Additional nvarchar2 WT_V4 to be used in future';
comment on column TB_CCNSIZE_PRM.wt_v5
  is 'Additional nvarchar2 WT_V5 to be used in future';
comment on column TB_CCNSIZE_PRM.wt_n1
  is 'Additional number WT_N1 to be used in future';
comment on column TB_CCNSIZE_PRM.wt_n2
  is 'Additional number WT_N2 to be used in future';
comment on column TB_CCNSIZE_PRM.wt_n3
  is 'Additional number WT_N3 to be used in future';
comment on column TB_CCNSIZE_PRM.wt_n4
  is 'Additional number WT_N4 to be used in future';
comment on column TB_CCNSIZE_PRM.wt_n5
  is 'Additional number WT_N5 to be used in future';
comment on column TB_CCNSIZE_PRM.wt_d1
  is 'Additional Date WT_D1 to be used in future';
comment on column TB_CCNSIZE_PRM.wt_d2
  is 'Additional Date WT_D2 to be used in future';
comment on column TB_CCNSIZE_PRM.wt_d3
  is 'Additional Date WT_D3 to be used in future';
comment on column TB_CCNSIZE_PRM.wt_lo1
  is 'Additional Logical field WT_LO1 to be used in future';
comment on column TB_CCNSIZE_PRM.wt_lo2
  is 'Additional Logical field WT_LO2 to be used in future';
comment on column TB_CCNSIZE_PRM.wt_lo3
  is 'Additional Logical field WT_LO3 to be used in future';
comment on column TB_CCNSIZE_PRM.lg_ip_mac
  is 'Store IP adress';
comment on column TB_CCNSIZE_PRM.lg_ip_mac_upd
  is 'Store IP address';
alter table TB_CCNSIZE_PRM
  add constraint PK_CNS_ID primary key (CNS_ID);

prompt
prompt Creating table TB_CCNSIZE_PRM_HIST
prompt ==================================
prompt
create table TB_CCNSIZE_PRM_HIST
(
  h_cnsid       NUMBER(12) not null,
  cns_id        NUMBER(12) not null,
  cns_from      NUMBER(5,2),
  cns_to        NUMBER(5,2),
  cns_value     NUMBER(5,2),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  lmoddate      DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  cns_frmdt     DATE,
  cns_todt      DATE,
  wt_v1         NVARCHAR2(100),
  wt_v2         NVARCHAR2(100),
  wt_v3         NVARCHAR2(100),
  wt_v4         NVARCHAR2(100),
  wt_v5         NVARCHAR2(100),
  wt_n1         NUMBER(15),
  wt_n2         NUMBER(15),
  wt_n3         NUMBER(15),
  wt_n4         NUMBER(15),
  wt_n5         NUMBER(15),
  wt_d1         DATE,
  wt_d2         DATE,
  wt_d3         DATE,
  wt_lo1        CHAR(1),
  wt_lo2        CHAR(1),
  wt_lo3        CHAR(1),
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  h_status      NVARCHAR2(1)
)
;
alter table TB_CCNSIZE_PRM_HIST
  add constraint PK_H_CNSID primary key (H_CNSID);

prompt
prompt Creating table TB_CSMR_INFO
prompt ===========================
prompt
create table TB_CSMR_INFO
(
  cs_idn                 NUMBER(12) not null,
  cs_ccn                 NVARCHAR2(20),
  cs_apldate             DATE,
  cs_oldccn              NVARCHAR2(30),
  pm_prmstid             NUMBER(12),
  cs_title               NVARCHAR2(15),
  cs_name                NVARCHAR2(300),
  cs_mname               NVARCHAR2(300),
  cs_lname               NVARCHAR2(300),
  cs_org_name            NVARCHAR2(100),
  cs_add                 NVARCHAR2(500),
  cs_flatno              NVARCHAR2(10),
  cs_bldplt              NVARCHAR2(150),
  cs_lanear              NVARCHAR2(50),
  cs_rdcross             NVARCHAR2(100),
  cs_contactno           NVARCHAR2(50),
  cs_otitle              NVARCHAR2(15),
  cs_oname               NVARCHAR2(300),
  cs_omname              NVARCHAR2(300),
  cs_olname              NVARCHAR2(300),
  cs_oorg_name           NVARCHAR2(100),
  cs_oadd                NVARCHAR2(500),
  cs_oflatno             NVARCHAR2(10),
  cs_obldplt             NVARCHAR2(150),
  cs_olanear             NVARCHAR2(50),
  cs_ordcross            NVARCHAR2(100),
  cs_ocontactno          NVARCHAR2(50),
  cs_housetype           NUMBER(12),
  cs_ccntype             NUMBER,
  cs_noofusers           NUMBER(5),
  cs_remark              NVARCHAR2(200),
  trd_premise            NUMBER(12),
  cs_nooftaps            NUMBER(12),
  cs_meteredccn          NUMBER(10),
  pc_flg                 NVARCHAR2(1),
  pc_date                DATE,
  plum_id                NUMBER(12),
  cs_ccnstatus           NVARCHAR2(1),
  cs_fromdt              DATE,
  cs_todt                DATE,
  orgid                  NUMBER(4) not null,
  user_id                NUMBER(7),
  lang_id                NUMBER(7),
  lmoddate               DATE,
  updated_by             NUMBER(7),
  updated_date           DATE,
  cs_premisedesc         NVARCHAR2(250),
  cs_bbldplt             NVARCHAR2(150),
  cs_blanear             NVARCHAR2(50),
  cs_brdcross            NVARCHAR2(100),
  cs_badd                NVARCHAR2(500),
  regno                  NVARCHAR2(50),
  meterreader            NUMBER(12),
  ported                 CHAR(1),
  electoral_ward         NVARCHAR2(5),
  cs_listatus            NUMBER(12),
  cod_dwzid1             NUMBER(12),
  cod_dwzid2             NUMBER(12),
  cod_dwzid3             NUMBER(12),
  cod_dwzid4             NUMBER(12),
  cod_dwzid5             NUMBER(12),
  cs_powner              CHAR(1),
  cpa_cscid1             NUMBER(12),
  cpa_cscid2             NUMBER(12),
  cpa_cscid3             NUMBER(12),
  cpa_cscid4             NUMBER(12),
  cpa_cscid5             NUMBER(12),
  cpa_ocscid1            NUMBER(12),
  cpa_ocscid2            NUMBER(12),
  cpa_ocscid3            NUMBER(12),
  cpa_ocscid4            NUMBER(12),
  cpa_ocscid5            NUMBER(12),
  cpa_bcscid1            NUMBER(12),
  cpa_bcscid2            NUMBER(12),
  cpa_bcscid3            NUMBER(12),
  cpa_bcscid4            NUMBER(12),
  cpa_bcscid5            NUMBER(12),
  trm_group1             NUMBER(12),
  trm_group2             NUMBER(12),
  trm_group3             NUMBER(12),
  trm_group4             NUMBER(12),
  trm_group5             NUMBER(12),
  cs_ccncategory1        NUMBER(12),
  cs_ccncategory2        NUMBER(12),
  cs_ccncategory3        NUMBER(12),
  cs_ccncategory4        NUMBER(12),
  cs_ccncategory5        NUMBER(12),
  lg_ip_mac              VARCHAR2(100),
  lg_ip_mac_upd          VARCHAR2(100),
  wt_v1                  NVARCHAR2(100),
  wt_v2                  NVARCHAR2(100),
  wt_v3                  NVARCHAR2(100),
  wt_v4                  NVARCHAR2(100),
  wt_v5                  NVARCHAR2(100),
  cs_cfc_ward            NUMBER(15),
  wt_n2                  NUMBER(15),
  wt_n3                  NUMBER(15),
  wt_n4                  NUMBER(15),
  wt_n5                  NUMBER(15),
  wt_d1                  DATE,
  wt_d2                  DATE,
  wt_d3                  DATE,
  wt_lo1                 CHAR(1),
  wt_lo2                 CHAR(1),
  wt_lo3                 CHAR(1),
  cs_bhandwali_flag      CHAR(1),
  cs_oldpropno           NVARCHAR2(30),
  cs_seqno               NUMBER(12,2),
  cs_entry_flag          VARCHAR2(1),
  cs_open_secdeposit_amt NUMBER(12,2),
  cs_bulk_entry_flag     CHAR(1),
  gis_ref                NVARCHAR2(20),
  cs_uid                 NUMBER(12),
  apm_application_id     NUMBER not null,
  cs_p_t_flag            VARCHAR2(1),
  t_from_date            DATE,
  t_to_date              DATE,
  bpl_flag               VARCHAR2(1),
  bpl_no                 VARCHAR2(16),
  cs_ccnsize             NUMBER(10),
  cs_no_of_families      NUMBER(5),
  cs_cpd_apt             NUMBER(10),
  cs_ogender             NUMBER(10),
  cs_is_billing_active   VARCHAR2(1),
  cs_bcity_name          NVARCHAR2(100)
)
;
comment on table TB_CSMR_INFO
  is 'This table stores Consumer Information.';
comment on column TB_CSMR_INFO.cs_idn
  is 'Id no. Of the Consumer';
comment on column TB_CSMR_INFO.cs_ccn
  is 'Connection Code No.';
comment on column TB_CSMR_INFO.cs_apldate
  is 'date of application for new water connection';
comment on column TB_CSMR_INFO.cs_oldccn
  is 'Old CCN';
comment on column TB_CSMR_INFO.pm_prmstid
  is 'ID of TB_PROP_MAS table';
comment on column TB_CSMR_INFO.cs_title
  is 'Consumer Title';
comment on column TB_CSMR_INFO.cs_name
  is 'Name Of the Consumer';
comment on column TB_CSMR_INFO.cs_mname
  is 'Middle Name of Consumer';
comment on column TB_CSMR_INFO.cs_lname
  is 'Last name of consumer';
comment on column TB_CSMR_INFO.cs_org_name
  is 'organisation name';
comment on column TB_CSMR_INFO.cs_add
  is 'Address of the consumer';
comment on column TB_CSMR_INFO.cs_flatno
  is 'Flat Number of consumer';
comment on column TB_CSMR_INFO.cs_bldplt
  is 'Building / Plot no. Of the Consumer';
comment on column TB_CSMR_INFO.cs_lanear
  is 'Lane / Area Of the Consumer';
comment on column TB_CSMR_INFO.cs_rdcross
  is 'Road Cross Of the Consumer';
comment on column TB_CSMR_INFO.cs_contactno
  is 'Contact number of consumer';
comment on column TB_CSMR_INFO.cs_otitle
  is 'Title of owner';
comment on column TB_CSMR_INFO.cs_oname
  is 'Owner name Of the Consumer';
comment on column TB_CSMR_INFO.cs_omname
  is 'Middle name of owner';
comment on column TB_CSMR_INFO.cs_olname
  is 'Lasr name of owner';
comment on column TB_CSMR_INFO.cs_oorg_name
  is 'Orgnaisation name for owner';
comment on column TB_CSMR_INFO.cs_oadd
  is 'Address of property owner';
comment on column TB_CSMR_INFO.cs_oflatno
  is 'Flat number of property owner';
comment on column TB_CSMR_INFO.cs_obldplt
  is 'Owner Building / Plot no. Of the Consumer';
comment on column TB_CSMR_INFO.cs_olanear
  is 'Owner lane Of the Consumer';
comment on column TB_CSMR_INFO.cs_ordcross
  is 'Owner Road Of the Consumer';
comment on column TB_CSMR_INFO.cs_ocontactno
  is 'Contact number of property owner';
comment on column TB_CSMR_INFO.cs_housetype
  is 'House type Of the Consumer';
comment on column TB_CSMR_INFO.cs_ccntype
  is 'Connection type - Bulk or Semi - Bulk or Regular ID';
comment on column TB_CSMR_INFO.cs_noofusers
  is 'No of users Of in the family';
comment on column TB_CSMR_INFO.cs_remark
  is 'Remark for this consumer';
comment on column TB_CSMR_INFO.trd_premise
  is 'Premise type for Tariff group';
comment on column TB_CSMR_INFO.cs_nooftaps
  is 'Tariff Group';
comment on column TB_CSMR_INFO.cs_meteredccn
  is 'Metered/Nonmeterd Flag WMN Perfix';
comment on column TB_CSMR_INFO.pc_flg
  is 'Physical connection Flag';
comment on column TB_CSMR_INFO.pc_date
  is 'Date of Physical connection';
comment on column TB_CSMR_INFO.plum_id
  is 'ID of tb_wt_plum_mas';
comment on column TB_CSMR_INFO.cs_ccnstatus
  is 'CPD ID of connection status';
comment on column TB_CSMR_INFO.cs_fromdt
  is 'If connection is temprory then from date';
comment on column TB_CSMR_INFO.cs_todt
  is 'If connection is temprory then to date';
comment on column TB_CSMR_INFO.orgid
  is 'Org ID';
comment on column TB_CSMR_INFO.user_id
  is 'User ID';
comment on column TB_CSMR_INFO.lang_id
  is 'Lang ID';
comment on column TB_CSMR_INFO.lmoddate
  is 'Last Modification Date';
comment on column TB_CSMR_INFO.updated_by
  is 'User id who update the data';
comment on column TB_CSMR_INFO.updated_date
  is 'Date on which data is going to update';
comment on column TB_CSMR_INFO.cs_premisedesc
  is 'Premises description';
comment on column TB_CSMR_INFO.cs_bbldplt
  is 'Billing address plot';
comment on column TB_CSMR_INFO.cs_blanear
  is 'Billing address nearer to';
comment on column TB_CSMR_INFO.cs_brdcross
  is 'Billing address road cross';
comment on column TB_CSMR_INFO.cs_badd
  is 'Billing address ';
comment on column TB_CSMR_INFO.ported
  is 'Y-Data created thru Data entry form or Data Upload';
comment on column TB_CSMR_INFO.electoral_ward
  is 'Electoral ward';
comment on column TB_CSMR_INFO.cs_listatus
  is 'Connection Status';
comment on column TB_CSMR_INFO.cod_dwzid1
  is 'Hierarchy for Ward, Zone';
comment on column TB_CSMR_INFO.cod_dwzid2
  is 'Hierarchy for Ward, Zone';
comment on column TB_CSMR_INFO.cod_dwzid3
  is 'Hierarchy for Ward, Zone';
comment on column TB_CSMR_INFO.cod_dwzid4
  is 'Hierarchy for Ward, Zone';
comment on column TB_CSMR_INFO.cod_dwzid5
  is 'Hierarchy for Ward, Zone';
comment on column TB_CSMR_INFO.cs_powner
  is 'Logical field';
comment on column TB_CSMR_INFO.cpa_cscid1
  is 'Connection owner address hierarchy';
comment on column TB_CSMR_INFO.cpa_cscid2
  is 'Connection owner address hierarchy';
comment on column TB_CSMR_INFO.cpa_cscid3
  is 'Connection owner address hierarchy';
comment on column TB_CSMR_INFO.cpa_cscid4
  is 'Connection owner address hierarchy';
comment on column TB_CSMR_INFO.cpa_cscid5
  is 'Connection owner address hierarchy';
comment on column TB_CSMR_INFO.cpa_ocscid1
  is 'Property Owner address hierarchy details';
comment on column TB_CSMR_INFO.cpa_ocscid2
  is 'Property Owner address hierarchy details';
comment on column TB_CSMR_INFO.cpa_ocscid3
  is 'Property Owner address hierarchy details';
comment on column TB_CSMR_INFO.cpa_ocscid4
  is 'Property Owner address hierarchy details';
comment on column TB_CSMR_INFO.cpa_ocscid5
  is 'Property Owner address hierarchy details';
comment on column TB_CSMR_INFO.cpa_bcscid1
  is 'Building address hierarchy details';
comment on column TB_CSMR_INFO.cpa_bcscid2
  is 'Building address hierarchy details';
comment on column TB_CSMR_INFO.cpa_bcscid3
  is 'Building address hierarchy details';
comment on column TB_CSMR_INFO.cpa_bcscid4
  is 'Building address hierarchy details';
comment on column TB_CSMR_INFO.cpa_bcscid5
  is 'Building address hierarchy details';
comment on column TB_CSMR_INFO.trm_group1
  is 'Tariff group hierarchy';
comment on column TB_CSMR_INFO.trm_group2
  is 'Tariff group hierarchy';
comment on column TB_CSMR_INFO.trm_group3
  is 'Tariff group hierarchy';
comment on column TB_CSMR_INFO.trm_group4
  is 'Tariff group hierarchy';
comment on column TB_CSMR_INFO.trm_group5
  is 'Tariff group hierarchy';
comment on column TB_CSMR_INFO.cs_ccncategory1
  is 'Connection Category hierarchy';
comment on column TB_CSMR_INFO.cs_ccncategory2
  is 'Connection Category hierarchy';
comment on column TB_CSMR_INFO.cs_ccncategory3
  is 'Connection Category hierarchy';
comment on column TB_CSMR_INFO.cs_ccncategory4
  is 'Connection Category hierarchy';
comment on column TB_CSMR_INFO.cs_ccncategory5
  is 'Connection Category hierarchy';
comment on column TB_CSMR_INFO.lg_ip_mac
  is 'Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_CSMR_INFO.lg_ip_mac_upd
  is 'Updated Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_CSMR_INFO.wt_v1
  is 'Additional nvarchar2 WT_V1 to be used in future';
comment on column TB_CSMR_INFO.wt_v2
  is 'Additional nvarchar2 WT_V2 to be used in future';
comment on column TB_CSMR_INFO.wt_v3
  is 'Additional nvarchar2 WT_V3 to be used in future';
comment on column TB_CSMR_INFO.wt_v4
  is 'Additional nvarchar2 WT_V4 to be used in future';
comment on column TB_CSMR_INFO.wt_v5
  is 'Additional nvarchar2 WT_V5 to be used in future';
comment on column TB_CSMR_INFO.cs_cfc_ward
  is 'User Ward';
comment on column TB_CSMR_INFO.wt_n2
  is 'Additional number WT_N2 to be used in future';
comment on column TB_CSMR_INFO.wt_n3
  is 'Additional number WT_N3 to be used in future';
comment on column TB_CSMR_INFO.wt_n4
  is 'Additional number WT_N4 to be used in future';
comment on column TB_CSMR_INFO.wt_n5
  is 'Additional number WT_N5 to be used in future';
comment on column TB_CSMR_INFO.wt_d1
  is 'Additional Date WT_D1 to be used in future';
comment on column TB_CSMR_INFO.wt_d2
  is 'Additional Date WT_D2 to be used in future';
comment on column TB_CSMR_INFO.wt_d3
  is 'Additional Date WT_D3 to be used in future';
comment on column TB_CSMR_INFO.wt_lo1
  is 'Additional Logical field WT_LO1 to be used in future';
comment on column TB_CSMR_INFO.wt_lo2
  is 'Additional Logical field WT_LO2 to be used in future';
comment on column TB_CSMR_INFO.wt_lo3
  is 'Additional Logical field WT_LO3 to be used in future';
comment on column TB_CSMR_INFO.cs_entry_flag
  is 'D-Data created through Data entry form, U-Data created through upload';
comment on column TB_CSMR_INFO.cs_bulk_entry_flag
  is 'Indicates whether Connection created through Bulk Data Entry';
comment on column TB_CSMR_INFO.gis_ref
  is 'Stores GIS reference (for GIS Web Services)';
comment on column TB_CSMR_INFO.cs_uid
  is 'For UID Number Provision';
comment on column TB_CSMR_INFO.apm_application_id
  is 'Application Id';
comment on column TB_CSMR_INFO.cs_p_t_flag
  is 'Permanent or Temporary Connection flag';
comment on column TB_CSMR_INFO.t_from_date
  is 'If Temporary flag active then enter from date';
comment on column TB_CSMR_INFO.t_to_date
  is 'If Temporary flag active then enter to date';
comment on column TB_CSMR_INFO.bpl_flag
  is 'Flag for to identify BPL Provision Applicable or not';
comment on column TB_CSMR_INFO.bpl_no
  is 'BPL No. of Citizen';
comment on column TB_CSMR_INFO.cs_no_of_families
  is 'No.of Families';
comment on column TB_CSMR_INFO.cs_cpd_apt
  is 'comes from APT prefix';
comment on column TB_CSMR_INFO.cs_ogender
  is 'GENDER OF OWNER GEN PREFIX';
comment on column TB_CSMR_INFO.cs_is_billing_active
  is 'Billing is Active or Not ';
comment on column TB_CSMR_INFO.cs_bcity_name
  is 'Billing VILLAGE/TOWN/CITY NAME';
alter table TB_CSMR_INFO
  add constraint PK_CSIDN_ORGID primary key (CS_IDN);
alter table TB_CSMR_INFO
  add constraint CK_CS_ENTRY_FLAG
  check (CS_ENTRY_FLAG IN ('D','U'));
alter table TB_CSMR_INFO
  add constraint CK_CS_PORTED
  check (PORTED IN ('Y','N'));

prompt
prompt Creating table TB_CHNGOWN_MAS
prompt =============================
prompt
create table TB_CHNGOWN_MAS
(
  coo_idn            NUMBER(12),
  cs_idn             NUMBER(12),
  apm_application_id NUMBER(16),
  coo_apldate        DATE,
  coo_notitle        NUMBER(12),
  coo_noname         NVARCHAR2(100),
  coo_nomname        NVARCHAR2(100),
  coo_nolname        NVARCHAR2(100),
  coo_org_name       NVARCHAR2(100),
  coo_otitle         NUMBER(12),
  coo_oname          NVARCHAR2(150),
  coo_oomname        NVARCHAR2(100),
  coo_oolname        NVARCHAR2(100),
  coo_oorg_name      NVARCHAR2(200),
  coo_remark         NVARCHAR2(200),
  coo_granted        VARCHAR2(1),
  orgid              NUMBER(4),
  user_id            NUMBER(7),
  lang_id            NUMBER(7),
  lmoddate           DATE,
  updated_by         NUMBER(7),
  updated_date       DATE,
  coo_notitle_copy   NVARCHAR2(15),
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  wt_v1              NVARCHAR2(100),
  wt_v2              NVARCHAR2(100),
  wt_v3              NVARCHAR2(100),
  wt_v4              NVARCHAR2(100),
  wt_v5              NVARCHAR2(100),
  wt_n1              NUMBER(15),
  wt_n2              NUMBER(15),
  wt_n3              NUMBER(15),
  wt_n4              NUMBER(15),
  wt_n5              NUMBER(15),
  wt_d1              DATE,
  wt_d2              DATE,
  wt_d3              DATE,
  wt_lo1             CHAR(1),
  wt_lo2             CHAR(1),
  wt_lo3             CHAR(1),
  coo_ouid_no        NUMBER(12),
  coo_nuid_no        NUMBER(12)
)
;
comment on column TB_CHNGOWN_MAS.coo_idn
  is 'Generated ID';
comment on column TB_CHNGOWN_MAS.cs_idn
  is 'Id of TB_CSMR_INFO';
comment on column TB_CHNGOWN_MAS.apm_application_id
  is 'Application id';
comment on column TB_CHNGOWN_MAS.coo_apldate
  is 'Application date';
comment on column TB_CHNGOWN_MAS.coo_notitle
  is 'New owner Title';
comment on column TB_CHNGOWN_MAS.coo_noname
  is 'New owner name';
comment on column TB_CHNGOWN_MAS.coo_nomname
  is 'New owner middle name';
comment on column TB_CHNGOWN_MAS.coo_nolname
  is 'New owner last name';
comment on column TB_CHNGOWN_MAS.coo_org_name
  is 'New organization name';
comment on column TB_CHNGOWN_MAS.coo_otitle
  is 'Old owner Title';
comment on column TB_CHNGOWN_MAS.coo_oname
  is 'Old owner name';
comment on column TB_CHNGOWN_MAS.coo_oomname
  is 'Old owner middle name';
comment on column TB_CHNGOWN_MAS.coo_oolname
  is 'Old owner last name';
comment on column TB_CHNGOWN_MAS.coo_oorg_name
  is 'Old organization name';
comment on column TB_CHNGOWN_MAS.coo_remark
  is 'Remark';
comment on column TB_CHNGOWN_MAS.coo_granted
  is 'Granted flag';
comment on column TB_CHNGOWN_MAS.orgid
  is 'Org ID';
comment on column TB_CHNGOWN_MAS.user_id
  is 'User ID';
comment on column TB_CHNGOWN_MAS.lang_id
  is 'Lang ID';
comment on column TB_CHNGOWN_MAS.lmoddate
  is 'Last Modification Date';
comment on column TB_CHNGOWN_MAS.updated_by
  is 'User id who update the data';
comment on column TB_CHNGOWN_MAS.updated_date
  is 'Date on which data is going to update';
comment on column TB_CHNGOWN_MAS.wt_v1
  is 'Additional nvarchar2 WT_V1 to be used in future';
comment on column TB_CHNGOWN_MAS.wt_v2
  is 'Additional nvarchar2 WT_V2 to be used in future';
comment on column TB_CHNGOWN_MAS.wt_v3
  is 'Additional nvarchar2 WT_V3 to be used in future';
comment on column TB_CHNGOWN_MAS.wt_v4
  is 'Additional nvarchar2 WT_V4 to be used in future';
comment on column TB_CHNGOWN_MAS.wt_v5
  is 'Additional nvarchar2 WT_V5 to be used in future';
comment on column TB_CHNGOWN_MAS.wt_n1
  is 'Additional number WT_N1 to be used in future';
comment on column TB_CHNGOWN_MAS.wt_n2
  is 'Additional number WT_N2 to be used in future';
comment on column TB_CHNGOWN_MAS.wt_n3
  is 'Additional number WT_N3 to be used in future';
comment on column TB_CHNGOWN_MAS.wt_n4
  is 'Additional number WT_N4 to be used in future';
comment on column TB_CHNGOWN_MAS.wt_n5
  is 'Additional number WT_N5 to be used in future';
comment on column TB_CHNGOWN_MAS.wt_d1
  is 'Additional Date WT_D1 to be used in future';
comment on column TB_CHNGOWN_MAS.wt_d2
  is 'Additional Date WT_D2 to be used in future';
comment on column TB_CHNGOWN_MAS.wt_d3
  is 'Additional Date WT_D3 to be used in future';
comment on column TB_CHNGOWN_MAS.wt_lo1
  is 'Additional Logical field WT_LO1 to be used in future';
comment on column TB_CHNGOWN_MAS.wt_lo2
  is 'Additional Logical field WT_LO2 to be used in future';
comment on column TB_CHNGOWN_MAS.wt_lo3
  is 'Additional Logical field WT_LO3 to be used in future';
comment on column TB_CHNGOWN_MAS.coo_ouid_no
  is 'holds the adhar no. of old consumer';
comment on column TB_CHNGOWN_MAS.coo_nuid_no
  is 'holds the adhar no. of new consumer';
alter table TB_CHNGOWN_MAS
  add constraint PK_COO_IDN primary key (COO_IDN);
alter table TB_CHNGOWN_MAS
  add constraint FK_T_CSMR foreign key (CS_IDN)
  references TB_CSMR_INFO (CS_IDN);
alter table TB_CHNGOWN_MAS
  add constraint CK_COO_GRANTED
  check (coo_granted in ('Y','N'));
alter table TB_CHNGOWN_MAS
  add constraint NN_CHNGOWN_COO_APLDATE
  check ("COO_APLDATE" IS NOT NULL);
alter table TB_CHNGOWN_MAS
  add constraint NN_CHNGOWN_COO_ID
  check ("COO_IDN" IS NOT NULL);
alter table TB_CHNGOWN_MAS
  add constraint NN_CHNGOWN_CS_IDN
  check ("CS_IDN" IS NOT NULL);
alter table TB_CHNGOWN_MAS
  add constraint NN_CHNGOWN_LANG_ID
  check ("LANG_ID" IS NOT NULL);
alter table TB_CHNGOWN_MAS
  add constraint NN_CHNGOWN_LMODDATE
  check ("LMODDATE" IS NOT NULL);
alter table TB_CHNGOWN_MAS
  add constraint NN_CHNGOWN_ORGID
  check ("ORGID" IS NOT NULL);
alter table TB_CHNGOWN_MAS
  add constraint NN_CHNGOWN_USER_ID
  check ("USER_ID" IS NOT NULL);

prompt
prompt Creating table TB_CSMRRCMD_MAS
prompt ==============================
prompt
create table TB_CSMRRCMD_MAS
(
  cs_idn           NUMBER(14) not null,
  cod_id1          NUMBER(12),
  rc_distpres      NUMBER(8,2),
  rc_disttimefr    NVARCHAR2(5),
  rc_disttimeto    NVARCHAR2(5),
  rc_distccndif    NUMBER(8,3),
  rc_dailydischg   NUMBER(7,2),
  rc_granted       NVARCHAR2(1),
  rc_status        NVARCHAR2(1),
  rc_length        NUMBER(3),
  rc_recommended   NVARCHAR2(1),
  rc_dailydischgc  NUMBER(7,2),
  orgid            NUMBER(4) not null,
  user_id          NUMBER(7),
  lang_id          NUMBER(7),
  lmoddate         DATE,
  updated_by       NUMBER(7),
  updated_date     DATE,
  rc_rhgl          NUMBER(7,2),
  rc_ahgl          NUMBER(7,2),
  rc_disp_wt       NVARCHAR2(250),
  lg_ip_mac        VARCHAR2(100),
  lg_ip_mac_upd    VARCHAR2(100),
  wt_v1            NVARCHAR2(100),
  wt_v2            NVARCHAR2(100),
  wt_v3            NVARCHAR2(100),
  wt_v4            NVARCHAR2(100),
  wt_v5            NVARCHAR2(100),
  wt_n1            NUMBER(15),
  wt_n2            NUMBER(15),
  wt_n3            NUMBER(15),
  wt_n4            NUMBER(15),
  wt_n5            NUMBER(15),
  wt_d1            DATE,
  wt_d2            DATE,
  wt_d3            DATE,
  wt_lo1           CHAR(1),
  wt_lo2           CHAR(1),
  wt_lo3           CHAR(1),
  cs_id            NUMBER not null,
  inst_id          NUMBER,
  cod_id2          NUMBER(12),
  cod_id3          NUMBER(12),
  cod_id4          NUMBER(12),
  cod_id5          NUMBER(12),
  rc_tot_dist_time NUMBER(10,2)
)
;
comment on table TB_CSMRRCMD_MAS
  is 'This table stores Recommedation details of new connection.';
comment on column TB_CSMRRCMD_MAS.cs_idn
  is 'New Connection Id No.';
comment on column TB_CSMRRCMD_MAS.cod_id1
  is ' Distibution line of new Connection Id No.';
comment on column TB_CSMRRCMD_MAS.rc_distpres
  is ' Pressure on distribution line';
comment on column TB_CSMRRCMD_MAS.rc_disttimefr
  is ' distribution timing - from';
comment on column TB_CSMRRCMD_MAS.rc_disttimeto
  is ' distribution timing - to';
comment on column TB_CSMRRCMD_MAS.rc_distccndif
  is ' Difference of water if new ccn granted';
comment on column TB_CSMRRCMD_MAS.rc_dailydischg
  is 'Quantity required for Domestic';
comment on column TB_CSMRRCMD_MAS.rc_granted
  is 'Granted Flag';
comment on column TB_CSMRRCMD_MAS.rc_status
  is 'Status of Recommendation';
comment on column TB_CSMRRCMD_MAS.rc_length
  is 'Lengh of line';
comment on column TB_CSMRRCMD_MAS.rc_recommended
  is 'Recommedation Flag';
comment on column TB_CSMRRCMD_MAS.rc_dailydischgc
  is 'Quantity required for commercial';
comment on column TB_CSMRRCMD_MAS.orgid
  is 'organisation id';
comment on column TB_CSMRRCMD_MAS.user_id
  is 'user id';
comment on column TB_CSMRRCMD_MAS.lang_id
  is 'language id';
comment on column TB_CSMRRCMD_MAS.lmoddate
  is 'last modification date';
comment on column TB_CSMRRCMD_MAS.lg_ip_mac
  is 'stores ip information';
comment on column TB_CSMRRCMD_MAS.lg_ip_mac_upd
  is 'stores ip information';
comment on column TB_CSMRRCMD_MAS.wt_v1
  is 'Additional nvarchar2 WT_V1 to be used in future';
comment on column TB_CSMRRCMD_MAS.wt_v2
  is 'Additional nvarchar2 WT_V2 to be used in future';
comment on column TB_CSMRRCMD_MAS.wt_v3
  is 'Additional nvarchar2 WT_V3 to be used in future';
comment on column TB_CSMRRCMD_MAS.wt_v4
  is 'Additional nvarchar2 WT_V4 to be used in future';
comment on column TB_CSMRRCMD_MAS.wt_v5
  is 'Additional nvarchar2 WT_V5 to be used in future';
comment on column TB_CSMRRCMD_MAS.wt_n1
  is 'Additional number WT_N1 to be used in future';
comment on column TB_CSMRRCMD_MAS.wt_n2
  is 'Additional number WT_N2 to be used in future';
comment on column TB_CSMRRCMD_MAS.wt_n3
  is 'Additional number WT_N3 to be used in future';
comment on column TB_CSMRRCMD_MAS.wt_n4
  is 'Additional number WT_N4 to be used in future';
comment on column TB_CSMRRCMD_MAS.wt_n5
  is 'Additional number WT_N5 to be used in future';
comment on column TB_CSMRRCMD_MAS.wt_d1
  is 'Additional Date WT_D1 to be used in future';
comment on column TB_CSMRRCMD_MAS.wt_d2
  is 'Additional Date WT_D2 to be used in future';
comment on column TB_CSMRRCMD_MAS.wt_d3
  is 'Additional Date WT_D3 to be used in future';
comment on column TB_CSMRRCMD_MAS.wt_lo1
  is 'Additional Logical field WT_LO1 to be used in future';
comment on column TB_CSMRRCMD_MAS.wt_lo2
  is 'Additional Logical field WT_LO2 to be used in future';
comment on column TB_CSMRRCMD_MAS.wt_lo3
  is 'Additional Logical field WT_LO3 to be used in future';
comment on column TB_CSMRRCMD_MAS.cs_id
  is 'Primary Key';
comment on column TB_CSMRRCMD_MAS.inst_id
  is 'Key of Institute type from  tbale TB_WT_INST_CSMP';
comment on column TB_CSMRRCMD_MAS.cod_id2
  is ' Distibution line of new Connection Id No.2';
comment on column TB_CSMRRCMD_MAS.cod_id3
  is ' Distibution line of new Connection Id No.3';
comment on column TB_CSMRRCMD_MAS.cod_id4
  is ' Distibution line of new Connection Id No.4';
comment on column TB_CSMRRCMD_MAS.cod_id5
  is ' Distibution line of new Connection Id No.5';
comment on column TB_CSMRRCMD_MAS.rc_tot_dist_time
  is 'Distibution Total Time';
alter table TB_CSMRRCMD_MAS
  add constraint PK_CS_ID primary key (CS_ID);
alter table TB_CSMRRCMD_MAS
  add constraint FK_CS_IDN_TBCSMR foreign key (CS_IDN)
  references TB_CSMR_INFO (CS_IDN);
alter table TB_CSMRRCMD_MAS
  add constraint CK_GRANTED
  check (RC_GRANTED in ('Y','N'));

prompt
prompt Creating table TB_CSMRRCMD_MAS_HIST
prompt ===================================
prompt
create table TB_CSMRRCMD_MAS_HIST
(
  h_csid           NUMBER not null,
  cs_idn           NUMBER(14) not null,
  cod_id1          NUMBER(12),
  rc_distpres      NUMBER(8,2),
  rc_disttimefr    NVARCHAR2(5),
  rc_disttimeto    NVARCHAR2(5),
  rc_distccndif    NUMBER(8,3),
  rc_dailydischg   NUMBER(7,2),
  rc_granted       NVARCHAR2(1),
  rc_status        NVARCHAR2(1),
  rc_length        NUMBER(3),
  rc_recommended   NVARCHAR2(1),
  rc_dailydischgc  NUMBER(7,2),
  orgid            NUMBER(4) not null,
  user_id          NUMBER(7),
  lang_id          NUMBER(7),
  lmoddate         DATE,
  updated_by       NUMBER(7),
  updated_date     DATE,
  rc_rhgl          NUMBER(7,2),
  rc_ahgl          NUMBER(7,2),
  rc_disp_wt       NVARCHAR2(250),
  lg_ip_mac        VARCHAR2(100),
  lg_ip_mac_upd    VARCHAR2(100),
  wt_v1            NVARCHAR2(100),
  wt_v2            NVARCHAR2(100),
  wt_v3            NVARCHAR2(100),
  wt_v4            NVARCHAR2(100),
  wt_v5            NVARCHAR2(100),
  wt_n1            NUMBER(15),
  wt_n2            NUMBER(15),
  wt_n3            NUMBER(15),
  wt_n4            NUMBER(15),
  wt_n5            NUMBER(15),
  wt_d1            DATE,
  wt_d2            DATE,
  wt_d3            DATE,
  wt_lo1           CHAR(1),
  wt_lo2           CHAR(1),
  wt_lo3           CHAR(1),
  inst_id          NUMBER,
  cod_id2          NUMBER(12),
  cod_id3          NUMBER(12),
  cod_id4          NUMBER(12),
  cod_id5          NUMBER(12),
  rc_tot_dist_time NUMBER(10,2),
  h_status         NVARCHAR2(1)
)
;
alter table TB_CSMRRCMD_MAS_HIST
  add constraint PK_HCSID primary key (H_CSID);

prompt
prompt Creating table TB_CSMR_INFO_HIST
prompt ================================
prompt
create table TB_CSMR_INFO_HIST
(
  h_csidn                NUMBER(12) not null,
  cs_idn                 NUMBER(12) not null,
  cs_ccn                 NVARCHAR2(20),
  cs_apldate             DATE,
  cs_oldccn              NVARCHAR2(30),
  pm_prmstid             NUMBER(12),
  cs_title               NVARCHAR2(15),
  cs_name                NVARCHAR2(300),
  cs_mname               NVARCHAR2(300),
  cs_lname               NVARCHAR2(300),
  cs_org_name            NVARCHAR2(100),
  cs_add                 NVARCHAR2(500),
  cs_flatno              NVARCHAR2(10),
  cs_bldplt              NVARCHAR2(150),
  cs_lanear              NVARCHAR2(50),
  cs_rdcross             NVARCHAR2(100),
  cs_contactno           NVARCHAR2(50),
  cs_otitle              NVARCHAR2(15),
  cs_oname               NVARCHAR2(300),
  cs_omname              NVARCHAR2(300),
  cs_olname              NVARCHAR2(300),
  cs_oorg_name           NVARCHAR2(100),
  cs_oadd                NVARCHAR2(500),
  cs_oflatno             NVARCHAR2(10),
  cs_obldplt             NVARCHAR2(150),
  cs_olanear             NVARCHAR2(50),
  cs_ordcross            NVARCHAR2(100),
  cs_ocontactno          NVARCHAR2(50),
  cs_housetype           NUMBER(12),
  cs_ccntype             NUMBER,
  cs_noofusers           NUMBER(5),
  cs_remark              NVARCHAR2(200),
  trd_premise            NUMBER(12),
  cs_nooftaps            NUMBER(12),
  cs_meteredccn          NUMBER(10),
  pc_flg                 NVARCHAR2(1),
  pc_date                DATE,
  plum_id                NUMBER(12),
  cs_ccnstatus           NVARCHAR2(1),
  cs_fromdt              DATE,
  cs_todt                DATE,
  orgid                  NUMBER(4) not null,
  user_id                NUMBER(7),
  lang_id                NUMBER(7),
  lmoddate               DATE,
  updated_by             NUMBER(7),
  updated_date           DATE,
  cs_premisedesc         NVARCHAR2(250),
  cs_bbldplt             NVARCHAR2(150),
  cs_blanear             NVARCHAR2(50),
  cs_brdcross            NVARCHAR2(100),
  cs_badd                NVARCHAR2(500),
  regno                  NVARCHAR2(50),
  meterreader            NUMBER(12),
  ported                 CHAR(1),
  electoral_ward         NVARCHAR2(5),
  cs_listatus            NUMBER(12),
  cod_dwzid1             NUMBER(12),
  cod_dwzid2             NUMBER(12),
  cod_dwzid3             NUMBER(12),
  cod_dwzid4             NUMBER(12),
  cod_dwzid5             NUMBER(12),
  cs_powner              CHAR(1),
  cpa_cscid1             NUMBER(12),
  cpa_cscid2             NUMBER(12),
  cpa_cscid3             NUMBER(12),
  cpa_cscid4             NUMBER(12),
  cpa_cscid5             NUMBER(12),
  cpa_ocscid1            NUMBER(12),
  cpa_ocscid2            NUMBER(12),
  cpa_ocscid3            NUMBER(12),
  cpa_ocscid4            NUMBER(12),
  cpa_ocscid5            NUMBER(12),
  cpa_bcscid1            NUMBER(12),
  cpa_bcscid2            NUMBER(12),
  cpa_bcscid3            NUMBER(12),
  cpa_bcscid4            NUMBER(12),
  cpa_bcscid5            NUMBER(12),
  trm_group1             NUMBER(12),
  trm_group2             NUMBER(12),
  trm_group3             NUMBER(12),
  trm_group4             NUMBER(12),
  trm_group5             NUMBER(12),
  cs_ccncategory1        NUMBER(12),
  cs_ccncategory2        NUMBER(12),
  cs_ccncategory3        NUMBER(12),
  cs_ccncategory4        NUMBER(12),
  cs_ccncategory5        NUMBER(12),
  lg_ip_mac              VARCHAR2(100),
  lg_ip_mac_upd          VARCHAR2(100),
  wt_v1                  NVARCHAR2(100),
  wt_v2                  NVARCHAR2(100),
  wt_v3                  NVARCHAR2(100),
  wt_v4                  NVARCHAR2(100),
  wt_v5                  NVARCHAR2(100),
  cs_cfc_ward            NUMBER(15),
  wt_n2                  NUMBER(15),
  wt_n3                  NUMBER(15),
  wt_n4                  NUMBER(15),
  wt_n5                  NUMBER(15),
  wt_d1                  DATE,
  wt_d2                  DATE,
  wt_d3                  DATE,
  wt_lo1                 CHAR(1),
  wt_lo2                 CHAR(1),
  wt_lo3                 CHAR(1),
  cs_bhandwali_flag      CHAR(1),
  cs_oldpropno           NVARCHAR2(30),
  cs_seqno               NUMBER(12,2),
  cs_entry_flag          VARCHAR2(1),
  cs_open_secdeposit_amt NUMBER(12,2),
  cs_bulk_entry_flag     CHAR(1),
  gis_ref                NVARCHAR2(20),
  cs_uid                 NUMBER(12),
  apm_application_id     NUMBER not null,
  cs_p_t_flag            VARCHAR2(1),
  t_from_date            DATE,
  t_to_date              DATE,
  bpl_flag               VARCHAR2(1),
  bpl_no                 VARCHAR2(16),
  cs_ccnsize             NUMBER(10),
  cs_no_of_families      NUMBER(5),
  cs_cpd_apt             NUMBER(10),
  cs_ogender             NUMBER(10),
  cs_is_billing_active   VARCHAR2(1),
  h_status               VARCHAR2(1)
)
;
alter table TB_CSMR_INFO_HIST
  add constraint PK_HCS_IDN primary key (H_CSIDN);

prompt
prompt Creating table TB_CSMR_INFO_T
prompt =============================
prompt
create table TB_CSMR_INFO_T
(
  cs_idn                 NUMBER(12) not null,
  cs_ccn                 NVARCHAR2(20),
  cs_apldate             DATE,
  cs_oldccn              NVARCHAR2(30),
  pm_prmstid             NUMBER(12),
  cs_title               NVARCHAR2(15),
  cs_name                NVARCHAR2(300),
  cs_mname               NVARCHAR2(300),
  cs_lname               NVARCHAR2(300),
  cs_org_name            NVARCHAR2(100),
  cs_add                 NVARCHAR2(500),
  cs_flatno              NVARCHAR2(10),
  cs_bldplt              NVARCHAR2(150),
  cs_lanear              NVARCHAR2(50),
  cs_rdcross             NVARCHAR2(100),
  cs_contactno           NVARCHAR2(50),
  cs_otitle              NVARCHAR2(15),
  cs_oname               NVARCHAR2(300),
  cs_omname              NVARCHAR2(300),
  cs_olname              NVARCHAR2(300),
  cs_oorg_name           NVARCHAR2(100),
  cs_oadd                NVARCHAR2(500),
  cs_oflatno             NVARCHAR2(10),
  cs_obldplt             NVARCHAR2(150),
  cs_olanear             NVARCHAR2(50),
  cs_ordcross            NVARCHAR2(100),
  cs_ocontactno          NVARCHAR2(50),
  cs_housetype           NUMBER(12),
  cs_ccntype             NUMBER,
  cs_noofusers           NUMBER(5),
  cs_remark              NVARCHAR2(200),
  trd_premise            NUMBER(12),
  cs_nooftaps            NUMBER(12),
  cs_meteredccn          NVARCHAR2(1),
  pc_flg                 NVARCHAR2(1),
  pc_date                DATE,
  plum_id                NUMBER(12),
  cs_ccnstatus           NVARCHAR2(1),
  cs_fromdt              DATE,
  cs_todt                DATE,
  orgid                  NUMBER(4) not null,
  user_id                NUMBER(7),
  lang_id                NUMBER(7),
  lmoddate               DATE,
  updated_by             NUMBER(7),
  updated_date           DATE,
  cs_premisedesc         NVARCHAR2(250),
  cs_bbldplt             NVARCHAR2(150),
  cs_blanear             NVARCHAR2(50),
  cs_brdcross            NVARCHAR2(100),
  cs_badd                NVARCHAR2(500),
  regno                  NVARCHAR2(50),
  meterreader            NUMBER(12),
  ported                 CHAR(1),
  electoral_ward         NVARCHAR2(5),
  cs_listatus            NUMBER(12),
  cod_dwzid1             NUMBER(12),
  cod_dwzid2             NUMBER(12),
  cod_dwzid3             NUMBER(12),
  cod_dwzid4             NUMBER(12),
  cod_dwzid5             NUMBER(12),
  cs_powner              CHAR(1),
  cpa_cscid1             NUMBER(12),
  cpa_cscid2             NUMBER(12),
  cpa_cscid3             NUMBER(12),
  cpa_cscid4             NUMBER(12),
  cpa_cscid5             NUMBER(12),
  cpa_ocscid1            NUMBER(12),
  cpa_ocscid2            NUMBER(12),
  cpa_ocscid3            NUMBER(12),
  cpa_ocscid4            NUMBER(12),
  cpa_ocscid5            NUMBER(12),
  cpa_bcscid1            NUMBER(12),
  cpa_bcscid2            NUMBER(12),
  cpa_bcscid3            NUMBER(12),
  cpa_bcscid4            NUMBER(12),
  cpa_bcscid5            NUMBER(12),
  trm_group1             NUMBER(12),
  trm_group2             NUMBER(12),
  trm_group3             NUMBER(12),
  trm_group4             NUMBER(12),
  trm_group5             NUMBER(12),
  cs_ccncategory1        NUMBER(12),
  cs_ccncategory2        NUMBER(12),
  cs_ccncategory3        NUMBER(12),
  cs_ccncategory4        NUMBER(12),
  cs_ccncategory5        NUMBER(12),
  lg_ip_mac              VARCHAR2(100),
  lg_ip_mac_upd          VARCHAR2(100),
  wt_v1                  NVARCHAR2(100),
  wt_v2                  NVARCHAR2(100),
  wt_v3                  NVARCHAR2(100),
  wt_v4                  NVARCHAR2(100),
  wt_v5                  NVARCHAR2(100),
  cs_cfc_ward            NUMBER(15),
  wt_n2                  NUMBER(15),
  wt_n3                  NUMBER(15),
  wt_n4                  NUMBER(15),
  wt_n5                  NUMBER(15),
  wt_d1                  DATE,
  wt_d2                  DATE,
  wt_d3                  DATE,
  wt_lo1                 CHAR(1),
  wt_lo2                 CHAR(1),
  wt_lo3                 CHAR(1),
  cs_bhandwali_flag      CHAR(1),
  cs_oldpropno           NVARCHAR2(30),
  cs_seqno               NUMBER(12,2),
  cs_entry_flag          VARCHAR2(1),
  cs_open_secdeposit_amt NUMBER(12,2),
  cs_bulk_entry_flag     CHAR(1),
  gis_ref                NVARCHAR2(20),
  cs_uid                 NUMBER(12),
  apm_application_id     NUMBER not null,
  cs_p_t_flag            VARCHAR2(1),
  t_from_date            DATE,
  t_to_date              DATE,
  bpl_flag               VARCHAR2(1),
  bpl_no                 VARCHAR2(16),
  cs_ccnsize             NUMBER(10)
)
;

prompt
prompt Creating table TB_METER_MAS
prompt ===========================
prompt
create table TB_METER_MAS
(
  mm_mtnid           NUMBER(12) not null,
  cs_idn             NUMBER(12),
  mm_mtrno           NVARCHAR2(20),
  mm_mtrmake         NVARCHAR2(40),
  mm_ownership       NUMBER(10),
  mm_mtrcost         NUMBER(12,2),
  mm_status          NVARCHAR2(1),
  mm_authorised      NVARCHAR2(1),
  orgid              NUMBER(4) not null,
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(7) not null,
  lmoddate           DATE not null,
  updated_by         NUMBER(7),
  updated_date       DATE,
  mm_max_reading     NUMBER(8),
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  mm_entry_flag      VARCHAR2(1),
  mm_ported          VARCHAR2(1),
  wt_v3              NVARCHAR2(100),
  wt_v4              NVARCHAR2(100),
  wt_v5              NVARCHAR2(100),
  wt_n1              NUMBER(15),
  wt_n2              NUMBER(15),
  wt_n3              NUMBER(15),
  wt_n4              NUMBER(15),
  wt_n5              NUMBER(15),
  wt_d1              DATE,
  wt_d2              DATE,
  wt_d3              DATE,
  wt_lo1             CHAR(1),
  wt_lo2             CHAR(1),
  wt_lo3             CHAR(1),
  mm_bulk_entry_flag CHAR(1),
  mm_initial_reading NUMBER(8),
  mm_install_date    DATE
)
;
comment on table TB_METER_MAS
  is 'Meter Cut-off/ Restoration entry is stored in this table.';
comment on column TB_METER_MAS.mm_mtnid
  is 'Meter maintenance no.';
comment on column TB_METER_MAS.cs_idn
  is ' IDN of tb_csmr_info';
comment on column TB_METER_MAS.mm_mtrno
  is ' Meter no.';
comment on column TB_METER_MAS.mm_mtrmake
  is ' Meter brand or make';
comment on column TB_METER_MAS.mm_ownership
  is ' Meter ownership - municipal or private';
comment on column TB_METER_MAS.mm_mtrcost
  is ' Meter cost';
comment on column TB_METER_MAS.mm_status
  is ' Meter status active or inactive';
comment on column TB_METER_MAS.mm_authorised
  is ' Authorisation flag';
comment on column TB_METER_MAS.mm_max_reading
  is 'Max Meter reading';
comment on column TB_METER_MAS.lg_ip_mac
  is 'stores ip information';
comment on column TB_METER_MAS.lg_ip_mac_upd
  is 'stores ip information';
comment on column TB_METER_MAS.mm_entry_flag
  is 'D-Data created through Data entry form, U-Data created through upload';
comment on column TB_METER_MAS.mm_ported
  is 'Y-Data created thru Data entry form or Data Upload';
comment on column TB_METER_MAS.wt_v3
  is 'Additional nvarchar2 WT_V3 to be used in future';
comment on column TB_METER_MAS.wt_v4
  is 'Additional nvarchar2 WT_V4 to be used in future';
comment on column TB_METER_MAS.wt_v5
  is 'Additional nvarchar2 WT_V5 to be used in future';
comment on column TB_METER_MAS.wt_n1
  is 'Additional number WT_N1 to be used in future';
comment on column TB_METER_MAS.wt_n2
  is 'Additional number WT_N2 to be used in future';
comment on column TB_METER_MAS.wt_n3
  is 'Additional number WT_N3 to be used in future';
comment on column TB_METER_MAS.wt_n4
  is 'Additional number WT_N4 to be used in future';
comment on column TB_METER_MAS.wt_n5
  is 'Additional number WT_N5 to be used in future';
comment on column TB_METER_MAS.wt_d1
  is 'Additional Date WT_D1 to be used in future';
comment on column TB_METER_MAS.wt_d2
  is 'Additional Date WT_D2 to be used in future';
comment on column TB_METER_MAS.wt_d3
  is 'Additional Date WT_D3 to be used in future';
comment on column TB_METER_MAS.wt_lo1
  is 'Additional Logical field WT_LO1 to be used in future';
comment on column TB_METER_MAS.wt_lo2
  is 'Additional Logical field WT_LO2 to be used in future';
comment on column TB_METER_MAS.wt_lo3
  is 'Additional Logical field WT_LO3 to be used in future';
comment on column TB_METER_MAS.mm_bulk_entry_flag
  is 'Indicates whether Data  entered through Bulk Data Entry';
comment on column TB_METER_MAS.mm_initial_reading
  is 'Meter Initail Reading';
comment on column TB_METER_MAS.mm_install_date
  is 'Meter Installation Date';
alter table TB_METER_MAS
  add constraint PK_MM_MTNID_KEY primary key (MM_MTNID);
alter table TB_METER_MAS
  add constraint FK_CSIDN_FK foreign key (CS_IDN)
  references TB_CSMR_INFO (CS_IDN);
alter table TB_METER_MAS
  add constraint CK_AUTH
  check (mm_authorised in ('N','C','R'));
alter table TB_METER_MAS
  add constraint CK_MM_ENTRY_FLAG
  check (MM_ENTRY_FLAG IN ('D','U'));
alter table TB_METER_MAS
  add constraint CK_MM_MM_PORTED
  check (MM_PORTED IN ('Y','N'));

prompt
prompt Creating table TB_METER_MAS_HIST
prompt ================================
prompt
create table TB_METER_MAS_HIST
(
  h_mtnid            NUMBER(12) not null,
  mm_mtnid           NUMBER(12) not null,
  cs_idn             NUMBER(12),
  mm_mtrno           NVARCHAR2(20),
  mm_mtrmake         NVARCHAR2(40),
  mm_ownership       NUMBER(10),
  mm_mtrcost         NUMBER(12,2),
  mm_status          NVARCHAR2(1),
  mm_authorised      NVARCHAR2(1),
  orgid              NUMBER(4) not null,
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(7) not null,
  lmoddate           DATE not null,
  updated_by         NUMBER(7),
  updated_date       DATE,
  mm_max_reading     NUMBER(8),
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  mm_entry_flag      VARCHAR2(1),
  mm_ported          VARCHAR2(1),
  wt_v3              NVARCHAR2(100),
  wt_v4              NVARCHAR2(100),
  wt_v5              NVARCHAR2(100),
  wt_n1              NUMBER(15),
  wt_n2              NUMBER(15),
  wt_n3              NUMBER(15),
  wt_n4              NUMBER(15),
  wt_n5              NUMBER(15),
  wt_d1              DATE,
  wt_d2              DATE,
  wt_d3              DATE,
  wt_lo1             CHAR(1),
  wt_lo2             CHAR(1),
  wt_lo3             CHAR(1),
  mm_bulk_entry_flag CHAR(1),
  mm_initial_reading NUMBER(8),
  mm_install_date    DATE,
  h_status           NVARCHAR2(1)
)
;
alter table TB_METER_MAS_HIST
  add constraint PK_H_MTNID primary key (H_MTNID);

prompt
prompt Creating table TB_WT_BILL_MAS
prompt =============================
prompt
create table TB_WT_BILL_MAS
(
  bm_idno                      NUMBER(12) not null,
  cs_idn                       NUMBER(12) not null,
  bm_year                      NUMBER(4) not null,
  bm_billdt                    DATE,
  bm_fromdt                    DATE not null,
  bm_todt                      DATE not null,
  bm_duedate                   DATE,
  bm_total_amount              NUMBER(15,2),
  bm_total_bal_amount          NUMBER(15,2),
  bm_total_arrears             NUMBER(15,2),
  bm_total_outstanding         NUMBER(15,2),
  bm_total_arrears_without_int NUMBER(15,2),
  bm_total_cum_int_arrears     NUMBER(15,2),
  bm_toatl_int                 NUMBER(15,2),
  bm_last_rcptamt              NUMBER(12,2),
  bm_last_rcptdt               DATE,
  bm_toatl_rebate              NUMBER(15,2),
  bm_paid_flag                 VARCHAR2(1),
  bm_fy_total_arrears          NUMBER(15,2),
  bm_fy_total_int              NUMBER(15,2),
  flag_jv_post                 CHAR(1) default 'N',
  dist_date                    DATE,
  bm_remarks                   NVARCHAR2(100),
  bm_printdate                 DATE,
  arrears_bill                 CHAR(1),
  bm_totpayamt_aftdue          NUMBER(15,2),
  bm_intamt_aftdue             NUMBER(15,2),
  bm_int_type                  NVARCHAR2(15),
  bm_trd_premis                NUMBER,
  bm_ccn_size                  NUMBER,
  bm_ccn_owner                 NVARCHAR2(500),
  bm_entry_flag                VARCHAR2(1),
  bm_int_charged_flag          CHAR(1),
  amend_for_bill_id            NUMBER(12),
  bm_meteredccn                NVARCHAR2(1),
  bm_duedate2                  DATE,
  ch_shd_int_charged_flag      CHAR(1) default 'N',
  bm_sec_dep_amt               NUMBER(15,2),
  bm_last_sec_dep_rcptno       NVARCHAR2(20),
  bm_last_sec_dep_rcptdt       DATE,
  wt_v1                        NVARCHAR2(100),
  wt_v2                        NVARCHAR2(100),
  wt_v3                        NVARCHAR2(100),
  wt_v4                        NVARCHAR2(100),
  wt_n1                        NUMBER(15),
  wt_n2                        NUMBER(15),
  wt_n3                        NUMBER(15),
  wt_n4                        NUMBER(15),
  wt_n5                        NUMBER(15),
  wt_d1                        DATE,
  wt_d2                        DATE,
  wt_d3                        DATE,
  wt_lo1                       CHAR(1),
  wt_lo2                       CHAR(1),
  wt_lo3                       CHAR(1),
  orgid                        NUMBER(7) not null,
  user_id                      NUMBER(7) not null,
  lang_id                      NUMBER(7) not null,
  lmoddate                     DATE not null,
  updated_by                   NUMBER(7),
  updated_date                 DATE,
  lg_ip_mac                    VARCHAR2(100),
  lg_ip_mac_upd                VARCHAR2(100),
  int_from                     DATE,
  int_to                       DATE,
  fyi_int_arrears              NUMBER(15,2),
  fyi_int_perc                 NUMBER(15,2),
  rm_rcptid                    NUMBER(12),
  bm_no                        VARCHAR2(16),
  bm_int_value                 NUMBER(15,3)
)
;
comment on table TB_WT_BILL_MAS
  is 'Master table used for water bill generation.';
comment on column TB_WT_BILL_MAS.bm_idno
  is 'Primary Id as Bill No.';
comment on column TB_WT_BILL_MAS.cs_idn
  is 'Connection ID of the TB_CSMR_INFO';
comment on column TB_WT_BILL_MAS.bm_year
  is 'Billing Year';
comment on column TB_WT_BILL_MAS.bm_billdt
  is 'Bill Generation Date';
comment on column TB_WT_BILL_MAS.bm_fromdt
  is 'Bill generated from date';
comment on column TB_WT_BILL_MAS.bm_todt
  is 'Bill generated to date';
comment on column TB_WT_BILL_MAS.bm_duedate
  is 'Bill due date';
comment on column TB_WT_BILL_MAS.bm_total_amount
  is 'Total bill amount';
comment on column TB_WT_BILL_MAS.bm_total_arrears
  is 'TOTAL ARREARS AMOUNT';
comment on column TB_WT_BILL_MAS.bm_total_outstanding
  is 'Toatl Outstanding Amount';
comment on column TB_WT_BILL_MAS.bm_total_arrears_without_int
  is 'Toatl Arrerrs without Interest';
comment on column TB_WT_BILL_MAS.bm_total_cum_int_arrears
  is 'Total Cumulative interest arrears';
comment on column TB_WT_BILL_MAS.bm_toatl_int
  is 'TOTAL INTEREST AMOUNT';
comment on column TB_WT_BILL_MAS.bm_last_rcptamt
  is 'Last receipt amt. for the CCN at the time of bill generation.';
comment on column TB_WT_BILL_MAS.bm_last_rcptdt
  is 'Last receipt date for the CCN at the time of bill generation.';
comment on column TB_WT_BILL_MAS.bm_toatl_rebate
  is 'TOTAL REBATE AMOUNT';
comment on column TB_WT_BILL_MAS.bm_paid_flag
  is 'Fully paid or not';
comment on column TB_WT_BILL_MAS.bm_fy_total_arrears
  is 'Fin Year total arrears';
comment on column TB_WT_BILL_MAS.bm_fy_total_int
  is 'Fin Year toatl Interest';
comment on column TB_WT_BILL_MAS.flag_jv_post
  is 'Deas - JV Posting Flag - N for not jv posted and Y for jv posted';
comment on column TB_WT_BILL_MAS.dist_date
  is 'Bill send date';
comment on column TB_WT_BILL_MAS.bm_remarks
  is 'Bill remarks';
comment on column TB_WT_BILL_MAS.bm_printdate
  is 'Bill print date';
comment on column TB_WT_BILL_MAS.arrears_bill
  is 'Arrear bill or normal bill';
comment on column TB_WT_BILL_MAS.bm_totpayamt_aftdue
  is 'Total payable amount (with interest) after due date.';
comment on column TB_WT_BILL_MAS.bm_intamt_aftdue
  is 'Interest amount to be charged after due date.';
comment on column TB_WT_BILL_MAS.bm_int_type
  is 'Stores ''PER'' if interest value is percentage,''MUL'' if interest value is to be multiplied or ''AMT'' if it is amount. ';
comment on column TB_WT_BILL_MAS.bm_trd_premis
  is 'Premise type for tariff group';
comment on column TB_WT_BILL_MAS.bm_ccn_size
  is 'Connection Size';
comment on column TB_WT_BILL_MAS.bm_ccn_owner
  is 'Connecton owner name';
comment on column TB_WT_BILL_MAS.bm_entry_flag
  is 'D-Data created through Data entry form, U-Data created through upload';
comment on column TB_WT_BILL_MAS.bm_int_charged_flag
  is 'Indicates wether interset is charged or not. Store ''Y'' if interest has been charged else ''N''.';
comment on column TB_WT_BILL_MAS.amend_for_bill_id
  is 'Stores Bill Id (bm_idno from tb_wt_bill_mas) for which amendment bill generated.';
comment on column TB_WT_BILL_MAS.bm_meteredccn
  is 'Stores connection type (metered/non-metered) at the time of billing';
comment on column TB_WT_BILL_MAS.bm_sec_dep_amt
  is 'Stores total security deposite amount.';
comment on column TB_WT_BILL_MAS.bm_last_sec_dep_rcptno
  is 'Stores Receipt date. of the last security deposite receipt.';
comment on column TB_WT_BILL_MAS.wt_v2
  is 'Additional nvarchar2 WT_V2 to be used in future';
comment on column TB_WT_BILL_MAS.wt_v3
  is 'Additional nvarchar2 WT_V3 to be used in future';
comment on column TB_WT_BILL_MAS.wt_v4
  is 'Additional nvarchar2 WT_V4 to be used in future';
comment on column TB_WT_BILL_MAS.wt_n1
  is 'Additional number WT_N1 to be used in future';
comment on column TB_WT_BILL_MAS.wt_n2
  is 'Additional number WT_N2 to be used in future';
comment on column TB_WT_BILL_MAS.wt_n3
  is 'Additional number WT_N3 to be used in future';
comment on column TB_WT_BILL_MAS.wt_n4
  is 'Additional number WT_N4 to be used in future';
comment on column TB_WT_BILL_MAS.wt_n5
  is 'Additional number WT_N5 to be used in future';
comment on column TB_WT_BILL_MAS.wt_d1
  is 'Additional Date WT_D1 to be used in future';
comment on column TB_WT_BILL_MAS.wt_d2
  is 'Additional Date WT_D2 to be used in future';
comment on column TB_WT_BILL_MAS.wt_d3
  is 'Additional Date WT_D3 to be used in future';
comment on column TB_WT_BILL_MAS.wt_lo1
  is 'Additional Logical field WT_LO1 to be used in future';
comment on column TB_WT_BILL_MAS.wt_lo2
  is 'Additional Logical field WT_LO2 to be used in future';
comment on column TB_WT_BILL_MAS.wt_lo3
  is 'Additional Logical field WT_LO3 to be used in future';
comment on column TB_WT_BILL_MAS.orgid
  is 'Org ID';
comment on column TB_WT_BILL_MAS.user_id
  is 'User ID';
comment on column TB_WT_BILL_MAS.lang_id
  is 'Lang ID';
comment on column TB_WT_BILL_MAS.lmoddate
  is 'Last Modification Date';
comment on column TB_WT_BILL_MAS.updated_by
  is 'User id who update the data';
comment on column TB_WT_BILL_MAS.updated_date
  is 'Date on which data is going to update';
comment on column TB_WT_BILL_MAS.lg_ip_mac
  is 'Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_WT_BILL_MAS.lg_ip_mac_upd
  is 'Updated Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_WT_BILL_MAS.int_from
  is 'Interest from date';
comment on column TB_WT_BILL_MAS.int_to
  is 'Interest to date';
comment on column TB_WT_BILL_MAS.rm_rcptid
  is 'FK FROM TB_SERVICE_RECEIPT_MAS';
comment on column TB_WT_BILL_MAS.bm_no
  is 'Bill Number ';
comment on column TB_WT_BILL_MAS.bm_int_value
  is 'Interest value to be charge if bill is not paid within due date. Value can be amount or percentage.';
alter table TB_WT_BILL_MAS
  add constraint PK_TWT_BLMST_BM_IDNO_ORGID primary key (BM_IDNO);
alter table TB_WT_BILL_MAS
  add constraint FK_WT_BILL_MAS_AMD_BILLID foreign key (AMEND_FOR_BILL_ID)
  references TB_WT_BILL_MAS (BM_IDNO);
alter table TB_WT_BILL_MAS
  add constraint CK_BM_ENTRY_FLAG
  check (BM_ENTRY_FLAG IN ('D','U'));

prompt
prompt Creating table TB_MRDATA
prompt ========================
prompt
create table TB_MRDATA
(
  mrd_id              NUMBER(12) not null,
  cs_idn              NUMBER(12) not null,
  mrd_mrdate          DATE,
  mrd_mtrread         NUMBER(8),
  cpd_mtrstatus       NUMBER(12),
  cpd_gap             NUMBER(12),
  mrd_from            DATE,
  mrd_to              DATE,
  ndays               NUMBER(5),
  csmp                NUMBER(12,2),
  bill_gen            NVARCHAR2(1) default 'N',
  mrd_cserradj        NUMBER(8),
  mrd_aprvd           NVARCHAR2(1),
  orgid               NUMBER(4),
  user_id             NUMBER(7),
  lang_id             NUMBER(7),
  lmoddate            DATE,
  updated_by          NUMBER(7),
  updated_date        DATE,
  bm_idno             NUMBER(12),
  mrd_ported          CHAR(1),
  mm_mtnid            NUMBER(12),
  lg_ip_mac           VARCHAR2(100),
  lg_ip_mac_upd       VARCHAR2(100),
  reset_flag          VARCHAR2(1),
  mrd_entry_flag      VARCHAR2(1),
  wt_v2               NVARCHAR2(100),
  wt_v3               NVARCHAR2(100),
  wt_v4               NVARCHAR2(100),
  wt_v5               NVARCHAR2(100),
  wt_n1               NUMBER(15),
  wt_n2               NUMBER(15),
  wt_n3               NUMBER(15),
  wt_n4               NUMBER(15),
  wt_n5               NUMBER(15),
  wt_d1               DATE,
  wt_d2               DATE,
  wt_d3               DATE,
  wt_lo1              CHAR(1),
  wt_lo2              CHAR(1),
  wt_lo3              CHAR(1),
  gen_id              NUMBER(12),
  gdd_id              NUMBER(12),
  previous_reading_1  NUMBER,
  previous_reading_2  NUMBER,
  previous_reading_3  NUMBER,
  previous_reading_4  NUMBER,
  previous_reading_5  NUMBER,
  previous_reading_6  NUMBER,
  previous_reading_7  NUMBER,
  previous_reading_8  NUMBER,
  previous_reading_9  NUMBER,
  previous_reading_10 NUMBER,
  previous_reading_11 NUMBER,
  previous_days_1     NUMBER,
  previous_days_2     NUMBER,
  previous_days_3     NUMBER,
  previous_days_4     NUMBER,
  previous_days_5     NUMBER,
  previous_days_6     NUMBER,
  previous_days_7     NUMBER,
  previous_days_8     NUMBER,
  previous_days_9     NUMBER,
  previous_days_10    NUMBER,
  previous_days_11    NUMBER,
  mrd_cut_res_flag    NVARCHAR2(1),
  mrd_image_path      NVARCHAR2(100),
  mrd_image_name      NVARCHAR2(100),
  mrd_cpd_id_wtp      NUMBER,
  previous_cycle_1    NVARCHAR2(50),
  previous_cycle_2    NVARCHAR2(50),
  previous_cycle_3    NVARCHAR2(50),
  previous_cycle_4    NVARCHAR2(50),
  previous_cycle_5    NVARCHAR2(50),
  previous_cycle_6    NVARCHAR2(50),
  previous_cycle_7    NVARCHAR2(50),
  previous_cycle_8    NVARCHAR2(50),
  previous_cycle_9    NVARCHAR2(50),
  previous_cycle_10   NVARCHAR2(50),
  previous_cycle_11   NVARCHAR2(50)
)
;
comment on table TB_MRDATA
  is 'Meter Reading entry is stored in this table.';
comment on column TB_MRDATA.mrd_id
  is 'Meter id';
comment on column TB_MRDATA.cs_idn
  is 'Consumer Identification Number';
comment on column TB_MRDATA.mrd_mrdate
  is 'Meter Reading Date';
comment on column TB_MRDATA.mrd_mtrread
  is 'Meter Reading';
comment on column TB_MRDATA.cpd_mtrstatus
  is 'Meter Satus';
comment on column TB_MRDATA.cpd_gap
  is 'Gap Code Id';
comment on column TB_MRDATA.mrd_from
  is 'From Date';
comment on column TB_MRDATA.mrd_to
  is 'To Date';
comment on column TB_MRDATA.ndays
  is 'Total number of days for which this reading calculated.';
comment on column TB_MRDATA.csmp
  is 'Consumption per month';
comment on column TB_MRDATA.bill_gen
  is 'This flag checks whether for this entry bill is generated or not.';
comment on column TB_MRDATA.mrd_cserradj
  is 'Consumtion Reading Adjustment';
comment on column TB_MRDATA.mrd_aprvd
  is 'Approved Flag';
comment on column TB_MRDATA.orgid
  is 'Org ID';
comment on column TB_MRDATA.user_id
  is 'User ID';
comment on column TB_MRDATA.lang_id
  is 'Lang ID';
comment on column TB_MRDATA.lmoddate
  is 'Last Modification Date';
comment on column TB_MRDATA.updated_by
  is 'User id who update the data';
comment on column TB_MRDATA.updated_date
  is 'Date on which data is going to update';
comment on column TB_MRDATA.bm_idno
  is 'Bm_idno from bill_mas';
comment on column TB_MRDATA.mrd_ported
  is 'Y-Data created thru Data entry form or Data Upload';
comment on column TB_MRDATA.mm_mtnid
  is 'Stores the meter no id';
comment on column TB_MRDATA.lg_ip_mac
  is 'stores ip information';
comment on column TB_MRDATA.lg_ip_mac_upd
  is 'stores ip information';
comment on column TB_MRDATA.reset_flag
  is 'This reading is reset reading or not';
comment on column TB_MRDATA.mrd_entry_flag
  is 'D-Data created through Data entry form, U-Data created through upload';
comment on column TB_MRDATA.wt_v2
  is 'Additional nvarchar2 WT_V2 to be used in future';
comment on column TB_MRDATA.wt_v3
  is 'Additional nvarchar2 WT_V3 to be used in future';
comment on column TB_MRDATA.wt_v4
  is 'Additional nvarchar2 WT_V4 to be used in future';
comment on column TB_MRDATA.wt_v5
  is 'Additional nvarchar2 WT_V5 to be used in future';
comment on column TB_MRDATA.wt_n1
  is 'Additional number WT_N1 to be used in future';
comment on column TB_MRDATA.wt_n2
  is 'Additional number WT_N2 to be used in future';
comment on column TB_MRDATA.wt_n3
  is 'Additional number WT_N3 to be used in future';
comment on column TB_MRDATA.wt_n4
  is 'Additional number WT_N4 to be used in future';
comment on column TB_MRDATA.wt_n5
  is 'Additional number WT_N5 to be used in future';
comment on column TB_MRDATA.wt_d1
  is 'Additional Date WT_D1 to be used in future';
comment on column TB_MRDATA.wt_d2
  is 'Additional Date WT_D2 to be used in future';
comment on column TB_MRDATA.wt_d3
  is 'Additional Date WT_D3 to be used in future';
comment on column TB_MRDATA.wt_lo1
  is 'Additional Logical field WT_LO1 to be used in future';
comment on column TB_MRDATA.wt_lo2
  is 'Additional Logical field WT_LO2 to be used in future';
comment on column TB_MRDATA.wt_lo3
  is 'Additional Logical field WT_LO3 to be used in future';
comment on column TB_MRDATA.gen_id
  is 'Generic Data Master ID';
comment on column TB_MRDATA.gdd_id
  is 'Generic Det1 ID';
comment on column TB_MRDATA.mrd_cpd_id_wtp
  is 'Types of methods WTP Prefix';
alter table TB_MRDATA
  add constraint PK_MRDID primary key (MRD_ID);
alter table TB_MRDATA
  add constraint FK_CPD_GAP foreign key (CPD_GAP)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_MRDATA
  add constraint FK_CPD_MTRSTATUS foreign key (CPD_MTRSTATUS)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_MRDATA
  add constraint FK_CSIDN_CSMR_INFO foreign key (CS_IDN)
  references TB_CSMR_INFO (CS_IDN);
alter table TB_MRDATA
  add constraint FK_MR_BM_IDNO foreign key (BM_IDNO)
  references TB_WT_BILL_MAS (BM_IDNO);
alter table TB_MRDATA
  add constraint CK_MRD_ENTRY_FLAG
  check (MRD_ENTRY_FLAG IN ('D','U'));
alter table TB_MRDATA
  add constraint CK_MRD_PORTED
  check (MRD_PORTED IN ('Y','N'));
alter table TB_MRDATA
  add constraint NN_MR_CPD_GAP
  check ("CPD_GAP" IS NOT NULL);
alter table TB_MRDATA
  add constraint NN_MR_CPD_MTRSTATUS
  check ("CPD_MTRSTATUS" IS NOT NULL);
alter table TB_MRDATA
  add constraint NN_MR_LANG_ID
  check ("LANG_ID" IS NOT NULL);
alter table TB_MRDATA
  add constraint NN_MR_LMODDATE
  check ("LMODDATE" IS NOT NULL);
alter table TB_MRDATA
  add constraint NN_MR_MRD_MRDATE
  check ("MRD_MRDATE" IS NOT NULL);
alter table TB_MRDATA
  add constraint NN_MR_ORGID
  check ("ORGID" IS NOT NULL);
alter table TB_MRDATA
  add constraint NN_MR_USER_ID
  check ("USER_ID" IS NOT NULL);
alter table TB_MRDATA
  add check (bill_gen IN ('Y','N'));

prompt
prompt Creating table TB_MRDATA_HIST
prompt =============================
prompt
create table TB_MRDATA_HIST
(
  h_mrdid             NUMBER(12) not null,
  mrd_id              NUMBER(12) not null,
  cs_idn              NUMBER(12) not null,
  mrd_mrdate          DATE,
  mrd_mtrread         NUMBER(8),
  cpd_mtrstatus       NUMBER(12),
  cpd_gap             NUMBER(12),
  mrd_from            NUMBER(10),
  mrd_to              NUMBER(10),
  ndays               NUMBER(5),
  csmp                NUMBER(12,2),
  bill_gen            NVARCHAR2(1) default 'N',
  mrd_cserradj        NUMBER(8),
  mrd_aprvd           NVARCHAR2(1),
  orgid               NUMBER(4),
  user_id             NUMBER(7),
  lang_id             NUMBER(7),
  lmoddate            DATE,
  updated_by          NUMBER(7),
  updated_date        DATE,
  bm_idno             NUMBER(12),
  mrd_ported          CHAR(1),
  mm_mtnid            NUMBER(12),
  lg_ip_mac           VARCHAR2(100),
  lg_ip_mac_upd       VARCHAR2(100),
  reset_flag          VARCHAR2(1),
  mrd_entry_flag      VARCHAR2(1),
  wt_v2               NVARCHAR2(100),
  wt_v3               NVARCHAR2(100),
  wt_v4               NVARCHAR2(100),
  wt_v5               NVARCHAR2(100),
  wt_n1               NUMBER(15),
  wt_n2               NUMBER(15),
  wt_n3               NUMBER(15),
  wt_n4               NUMBER(15),
  wt_n5               NUMBER(15),
  wt_d1               DATE,
  wt_d2               DATE,
  wt_d3               DATE,
  wt_lo1              CHAR(1),
  wt_lo2              CHAR(1),
  wt_lo3              CHAR(1),
  gen_id              NUMBER(12),
  gdd_id              NUMBER(12),
  previous_reading_1  NUMBER,
  previous_reading_2  NUMBER,
  previous_reading_3  NUMBER,
  previous_reading_4  NUMBER,
  previous_reading_5  NUMBER,
  previous_reading_6  NUMBER,
  previous_reading_7  NUMBER,
  previous_reading_8  NUMBER,
  previous_reading_9  NUMBER,
  previous_reading_10 NUMBER,
  previous_reading_11 NUMBER,
  previous_days_1     NUMBER,
  previous_days_2     NUMBER,
  previous_days_3     NUMBER,
  previous_days_4     NUMBER,
  previous_days_5     NUMBER,
  previous_days_6     NUMBER,
  previous_days_7     NUMBER,
  previous_days_8     NUMBER,
  previous_days_9     NUMBER,
  previous_days_10    NUMBER,
  previous_days_11    NUMBER,
  mrd_cut_res_flag    NVARCHAR2(1),
  mrd_image_path      NVARCHAR2(100),
  mrd_image_name      NVARCHAR2(100),
  mrd_cpd_id_wtp      NUMBER,
  h_status            NVARCHAR2(1)
)
;
alter table TB_MRDATA_HIST
  add constraint PK_H_MRDID primary key (H_MRDID);

prompt
prompt Creating table TB_SLOPE_PRM
prompt ===========================
prompt
create table TB_SLOPE_PRM
(
  sp_id         NUMBER(12) not null,
  sp_from       NUMBER(3),
  sp_to         NUMBER(3),
  sp_value      NUMBER(3,2),
  orgid         NUMBER(4),
  user_id       NUMBER(7),
  lang_id       NUMBER(7),
  lmoddate      DATE,
  updated_by    NUMBER(7),
  updated_date  DATE,
  sp_frmdt      DATE,
  sp_todt       DATE,
  wt_v1         NVARCHAR2(100),
  wt_v2         NVARCHAR2(100),
  wt_v3         NVARCHAR2(100),
  wt_v4         NVARCHAR2(100),
  wt_v5         NVARCHAR2(100),
  wt_n1         NUMBER(15),
  wt_n2         NUMBER(15),
  wt_n3         NUMBER(15),
  wt_n4         NUMBER(15),
  wt_n5         NUMBER(15),
  wt_d1         DATE,
  wt_d2         DATE,
  wt_d3         DATE,
  wt_lo1        CHAR(1),
  wt_lo2        CHAR(1),
  wt_lo3        CHAR(1),
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100)
)
;
comment on table TB_SLOPE_PRM
  is 'This table is used to define range of the length to recommed the connection size';
comment on column TB_SLOPE_PRM.sp_id
  is 'Generated id';
comment on column TB_SLOPE_PRM.sp_from
  is ' length value range from';
comment on column TB_SLOPE_PRM.sp_to
  is ' length value range to';
comment on column TB_SLOPE_PRM.sp_value
  is ' value of slope of gradient';
comment on column TB_SLOPE_PRM.orgid
  is 'Organization Id';
comment on column TB_SLOPE_PRM.user_id
  is 'User id';
comment on column TB_SLOPE_PRM.lang_id
  is 'Language id';
comment on column TB_SLOPE_PRM.lmoddate
  is 'Last Modification Date';
comment on column TB_SLOPE_PRM.updated_by
  is 'User id who update the data';
comment on column TB_SLOPE_PRM.updated_date
  is 'Date on which data is going to update';
comment on column TB_SLOPE_PRM.sp_frmdt
  is 'Slop from date';
comment on column TB_SLOPE_PRM.sp_todt
  is 'Slop To date';
comment on column TB_SLOPE_PRM.wt_v1
  is 'Additional nvarchar2 WT_V1 to be used in future';
comment on column TB_SLOPE_PRM.wt_v2
  is 'Additional nvarchar2 WT_V2 to be used in future';
comment on column TB_SLOPE_PRM.wt_v3
  is 'Additional nvarchar2 WT_V3 to be used in future';
comment on column TB_SLOPE_PRM.wt_v4
  is 'Additional nvarchar2 WT_V4 to be used in future';
comment on column TB_SLOPE_PRM.wt_v5
  is 'Additional nvarchar2 WT_V5 to be used in future';
comment on column TB_SLOPE_PRM.wt_n1
  is 'Additional number WT_N1 to be used in future';
comment on column TB_SLOPE_PRM.wt_n2
  is 'Additional number WT_N2 to be used in future';
comment on column TB_SLOPE_PRM.wt_n3
  is 'Additional number WT_N3 to be used in future';
comment on column TB_SLOPE_PRM.wt_n4
  is 'Additional number WT_N4 to be used in future';
comment on column TB_SLOPE_PRM.wt_n5
  is 'Additional number WT_N5 to be used in future';
comment on column TB_SLOPE_PRM.wt_d1
  is 'Additional Date WT_D1 to be used in future';
comment on column TB_SLOPE_PRM.wt_d2
  is 'Additional Date WT_D2 to be used in future';
comment on column TB_SLOPE_PRM.wt_d3
  is 'Additional Date WT_D3 to be used in future';
comment on column TB_SLOPE_PRM.wt_lo1
  is 'Additional Logical field WT_LO1 to be used in future';
comment on column TB_SLOPE_PRM.wt_lo2
  is 'Additional Logical field WT_LO2 to be used in future';
comment on column TB_SLOPE_PRM.wt_lo3
  is 'Additional Logical field WT_LO3 to be used in future';
comment on column TB_SLOPE_PRM.lg_ip_mac
  is 'Store IP adress';
comment on column TB_SLOPE_PRM.lg_ip_mac_upd
  is 'Store IP address';
alter table TB_SLOPE_PRM
  add constraint PK_SP_ID primary key (SP_ID);
alter table TB_SLOPE_PRM
  add constraint NN_SP_LANG_ID
  check ("LANG_ID" IS NOT NULL);
alter table TB_SLOPE_PRM
  add constraint NN_SP_LMODDATE
  check ("LMODDATE" IS NOT NULL);
alter table TB_SLOPE_PRM
  add constraint NN_SP_ORGID
  check ("ORGID" IS NOT NULL);
alter table TB_SLOPE_PRM
  add constraint NN_SP_USER_ID
  check ("USER_ID" IS NOT NULL);

prompt
prompt Creating table TB_SLOPE_PRM_HIST
prompt ================================
prompt
create table TB_SLOPE_PRM_HIST
(
  h_spid        NUMBER(12) not null,
  sp_id         NUMBER(12) not null,
  sp_from       NUMBER(3),
  sp_to         NUMBER(3),
  sp_value      NUMBER(3,2),
  orgid         NUMBER(4),
  user_id       NUMBER(7),
  lang_id       NUMBER(7),
  lmoddate      DATE,
  updated_by    NUMBER(7),
  updated_date  DATE,
  sp_frmdt      DATE,
  sp_todt       DATE,
  wt_v1         NVARCHAR2(100),
  wt_v2         NVARCHAR2(100),
  wt_v3         NVARCHAR2(100),
  wt_v4         NVARCHAR2(100),
  wt_v5         NVARCHAR2(100),
  wt_n1         NUMBER(15),
  wt_n2         NUMBER(15),
  wt_n3         NUMBER(15),
  wt_n4         NUMBER(15),
  wt_n5         NUMBER(15),
  wt_d1         DATE,
  wt_d2         DATE,
  wt_d3         DATE,
  wt_lo1        CHAR(1),
  wt_lo2        CHAR(1),
  wt_lo3        CHAR(1),
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  h_status      NVARCHAR2(1)
)
;
alter table TB_SLOPE_PRM_HIST
  add constraint PK_H_SPID primary key (H_SPID);

prompt
prompt Creating table TB_WT_BILL_DET
prompt =============================
prompt
create table TB_WT_BILL_DET
(
  bd_billdetid        NUMBER(12) not null,
  bm_idno             NUMBER(12) not null,
  tax_id              NUMBER(12) not null,
  rebate_id           NUMBER(12),
  adjustment_id       NUMBER(12),
  bd_cur_taxamt       NUMBER(15,2) not null,
  bd_cur_bal_taxamt   NUMBER(15,2),
  bd_prv_bal_arramt   NUMBER(15,2),
  bd_fyi_end_bal      NUMBER(15,2),
  tdd_taxid           NUMBER(12),
  bd_csmp             NUMBER(12,2),
  bd_cur_arramt       NUMBER(15,2),
  bd_prv_arramt       NUMBER(15,2),
  bd_cur_bal_arramt   NUMBER(15,2),
  bd_billflag         VARCHAR2(1),
  bd_cur_taxamt_print NUMBER(15,2),
  bd_demand_flag      VARCHAR2(1),
  wt_v1               NVARCHAR2(100),
  wt_v2               NVARCHAR2(100),
  wt_v3               NVARCHAR2(100),
  wt_v4               NVARCHAR2(100),
  wt_v5               NVARCHAR2(100),
  wt_n1               NUMBER(15),
  wt_n2               NUMBER(15),
  wt_n3               NUMBER(15),
  wt_n4               NUMBER(15),
  wt_n5               NUMBER(15),
  wt_d1               DATE,
  wt_d2               DATE,
  wt_d3               DATE,
  wt_lo1              CHAR(1),
  wt_lo2              CHAR(1),
  wt_lo3              CHAR(1),
  orgid               NUMBER(7) not null,
  user_id             NUMBER(7) not null,
  lang_id             NUMBER(7) not null,
  lmoddate            DATE not null,
  updated_by          NUMBER(7),
  updated_date        DATE,
  lg_ip_mac           VARCHAR2(100),
  lg_ip_mac_upd       VARCHAR2(100),
  tax_category        NUMBER(12),
  coll_seq            NUMBER(12)
)
;
comment on table TB_WT_BILL_DET
  is 'Detail table used for water bill generation.';
comment on column TB_WT_BILL_DET.bd_billdetid
  is 'Primary Id of this table';
comment on column TB_WT_BILL_DET.bm_idno
  is 'Bill id of TB_WT_BILL_MAS';
comment on column TB_WT_BILL_DET.tax_id
  is 'Tax Id of TB_WT_TAX_MAS';
comment on column TB_WT_BILL_DET.bd_cur_taxamt
  is 'Tax amount against this bill';
comment on column TB_WT_BILL_DET.bd_cur_bal_taxamt
  is 'Current year tax amount to be paid ';
comment on column TB_WT_BILL_DET.bd_prv_bal_arramt
  is 'Previous year arrear tax amount to be paid ';
comment on column TB_WT_BILL_DET.tdd_taxid
  is 'Slabwise id if slab wise tax is applicalbe';
comment on column TB_WT_BILL_DET.bd_csmp
  is 'consumption applied for slab ';
comment on column TB_WT_BILL_DET.bd_cur_arramt
  is 'Arrear amount present in the current year if present';
comment on column TB_WT_BILL_DET.bd_prv_arramt
  is 'Arrear amount present in the previous billing  year if present';
comment on column TB_WT_BILL_DET.bd_cur_bal_arramt
  is 'Current year arrear tax amount to be paid ';
comment on column TB_WT_BILL_DET.bd_billflag
  is 'This will hold B value for all taxes including outstation and dishonor when generated thru. bill package. When outstation and dishonor charges gets added in bill detail thru form, then these charges will hold O value';
comment on column TB_WT_BILL_DET.bd_cur_taxamt_print
  is 'Column to store bd_curyr_tax_amt which will be used in Bill Reports Printing';
comment on column TB_WT_BILL_DET.bd_demand_flag
  is 'Is demand notice tax or not';
comment on column TB_WT_BILL_DET.wt_v1
  is 'Additional nvarchar2 WT_V1 to be used in future';
comment on column TB_WT_BILL_DET.wt_v2
  is 'Additional nvarchar2 WT_V2 to be used in future';
comment on column TB_WT_BILL_DET.wt_v3
  is 'Additional nvarchar2 WT_V3 to be used in future';
comment on column TB_WT_BILL_DET.wt_v4
  is 'Additional nvarchar2 WT_V4 to be used in future';
comment on column TB_WT_BILL_DET.wt_v5
  is 'Additional nvarchar2 WT_V5 to be used in future';
comment on column TB_WT_BILL_DET.wt_n1
  is 'Additional number WT_N1 to be used in future';
comment on column TB_WT_BILL_DET.wt_n2
  is 'Additional number WT_N2 to be used in future';
comment on column TB_WT_BILL_DET.wt_n3
  is 'Additional number WT_N3 to be used in future';
comment on column TB_WT_BILL_DET.wt_n4
  is 'Additional number WT_N4 to be used in future';
comment on column TB_WT_BILL_DET.wt_n5
  is 'Additional number WT_N5 to be used in future';
comment on column TB_WT_BILL_DET.wt_d1
  is 'Additional Date WT_D1 to be used in future';
comment on column TB_WT_BILL_DET.wt_d2
  is 'Additional Date WT_D2 to be used in future';
comment on column TB_WT_BILL_DET.wt_d3
  is 'Additional Date WT_D3 to be used in future';
comment on column TB_WT_BILL_DET.wt_lo1
  is 'Additional Logical field WT_LO1 to be used in future';
comment on column TB_WT_BILL_DET.wt_lo2
  is 'Additional Logical field WT_LO2 to be used in future';
comment on column TB_WT_BILL_DET.wt_lo3
  is 'Additional Logical field WT_LO3 to be used in future';
comment on column TB_WT_BILL_DET.orgid
  is 'Org ID';
comment on column TB_WT_BILL_DET.user_id
  is 'User ID';
comment on column TB_WT_BILL_DET.lang_id
  is 'Lang ID';
comment on column TB_WT_BILL_DET.lmoddate
  is 'Last Modification Date';
comment on column TB_WT_BILL_DET.updated_by
  is 'User id who update the data';
comment on column TB_WT_BILL_DET.updated_date
  is 'Date on which data is going to update';
comment on column TB_WT_BILL_DET.lg_ip_mac
  is 'Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_WT_BILL_DET.lg_ip_mac_upd
  is 'Updated Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_WT_BILL_DET.tax_category
  is 'WTG prefix from tax mas ';
comment on column TB_WT_BILL_DET.coll_seq
  is 'Collection sequence ';
alter table TB_WT_BILL_DET
  add constraint PK_WT_TBLDT_BLDETID_ORGID primary key (BD_BILLDETID);
alter table TB_WT_BILL_DET
  add constraint FK_WT_TBLMS_TBLDT_BMIDNO foreign key (BM_IDNO)
  references TB_WT_BILL_MAS (BM_IDNO);
alter table TB_WT_BILL_DET
  add constraint CK_WT_BILL_DET_CUR_ARRAMT
  check (nvl(BD_CUR_ARRAMT,0) >=0);

prompt
prompt Creating table TB_WT_BILL_DET_HIST
prompt ==================================
prompt
create table TB_WT_BILL_DET_HIST
(
  h_billdetid         NUMBER(12) not null,
  bd_billdetid        NUMBER(12) not null,
  bm_idno             NUMBER(12) not null,
  tax_id              NUMBER(12) not null,
  rebate_id           NUMBER(12),
  adjustment_id       NUMBER(12),
  bd_cur_taxamt       NUMBER(15,2) not null,
  bd_cur_bal_taxamt   NUMBER(15,2),
  bd_prv_bal_arramt   NUMBER(15,2),
  bd_fyi_end_bal      NUMBER(15,2),
  tdd_taxid           NUMBER(12),
  bd_csmp             NUMBER(12,2),
  bd_cur_arramt       NUMBER(15,2),
  bd_prv_arramt       NUMBER(15,2),
  bd_cur_bal_arramt   NUMBER(15,2),
  bd_billflag         VARCHAR2(1),
  bd_cur_taxamt_print NUMBER(15,2),
  bd_demand_flag      VARCHAR2(1),
  wt_v1               NVARCHAR2(100),
  wt_v2               NVARCHAR2(100),
  wt_v3               NVARCHAR2(100),
  wt_v4               NVARCHAR2(100),
  wt_v5               NVARCHAR2(100),
  wt_n1               NUMBER(15),
  wt_n2               NUMBER(15),
  wt_n3               NUMBER(15),
  wt_n4               NUMBER(15),
  wt_n5               NUMBER(15),
  wt_d1               DATE,
  wt_d2               DATE,
  wt_d3               DATE,
  wt_lo1              CHAR(1),
  wt_lo2              CHAR(1),
  wt_lo3              CHAR(1),
  orgid               NUMBER(7) not null,
  user_id             NUMBER(7) not null,
  lang_id             NUMBER(7) not null,
  lmoddate            DATE not null,
  updated_by          NUMBER(7),
  updated_date        DATE,
  lg_ip_mac           VARCHAR2(100),
  lg_ip_mac_upd       VARCHAR2(100),
  tax_category        NUMBER(12),
  coll_seq            NUMBER(12),
  h_status            VARCHAR2(1)
)
;
alter table TB_WT_BILL_DET_HIST
  add constraint PK_H_BILLDETID primary key (H_BILLDETID);

prompt
prompt Creating table TB_WT_BILL_GEN_ERROR
prompt ===================================
prompt
create table TB_WT_BILL_GEN_ERROR
(
  err_id       NUMBER(12),
  cs_idn       NUMBER(12),
  err_date     DATE,
  err_msg      NVARCHAR2(200),
  user_id      NUMBER(10),
  updated_by   NUMBER(7),
  updated_date DATE,
  orgid        NUMBER
)
;

prompt
prompt Creating table TB_WT_BILL_MAS_HIST
prompt ==================================
prompt
create table TB_WT_BILL_MAS_HIST
(
  h_bmidno                     NUMBER(12) not null,
  bm_idno                      NUMBER(12) not null,
  cs_idn                       NUMBER(12) not null,
  bm_year                      NUMBER(4) not null,
  bm_billdt                    DATE,
  bm_fromdt                    DATE not null,
  bm_todt                      DATE not null,
  bm_duedate                   DATE,
  bm_total_amount              NUMBER(15,2),
  bm_total_bal_amount          NUMBER(15,2),
  bm_int_value                 NUMBER(15,2),
  bm_total_arrears             NUMBER(15,2),
  bm_total_outstanding         NUMBER(15,2),
  bm_total_arrears_without_int NUMBER(15,2),
  bm_total_cum_int_arrears     NUMBER(15,2),
  bm_toatl_int                 NUMBER(15,2),
  bm_last_rcptamt              NUMBER(12,2),
  bm_last_rcptdt               DATE,
  bm_toatl_rebate              NUMBER(15,2),
  bm_paid_flag                 VARCHAR2(1),
  bm_fy_total_arrears          NUMBER(15,2),
  bm_fy_total_int              NUMBER(15,2),
  flag_jv_post                 CHAR(1) default 'N',
  dist_date                    DATE,
  bm_remarks                   NVARCHAR2(100),
  bm_printdate                 DATE,
  arrears_bill                 CHAR(1),
  bm_totpayamt_aftdue          NUMBER(15,2),
  bm_intamt_aftdue             NUMBER(15,2),
  bm_int_type                  NVARCHAR2(15),
  bm_trd_premis                NUMBER,
  bm_ccn_size                  NUMBER,
  bm_ccn_owner                 NVARCHAR2(500),
  bm_entry_flag                VARCHAR2(1),
  bm_int_charged_flag          CHAR(1),
  amend_for_bill_id            NUMBER(12),
  bm_meteredccn                NVARCHAR2(1),
  bm_duedate2                  DATE,
  ch_shd_int_charged_flag      CHAR(1) default 'N',
  bm_sec_dep_amt               NUMBER(15,2),
  bm_last_sec_dep_rcptno       NVARCHAR2(20),
  bm_last_sec_dep_rcptdt       DATE,
  wt_v1                        NVARCHAR2(100),
  wt_v2                        NVARCHAR2(100),
  wt_v3                        NVARCHAR2(100),
  wt_v4                        NVARCHAR2(100),
  wt_n1                        NUMBER(15),
  wt_n2                        NUMBER(15),
  wt_n3                        NUMBER(15),
  wt_n4                        NUMBER(15),
  wt_n5                        NUMBER(15),
  wt_d1                        DATE,
  wt_d2                        DATE,
  wt_d3                        DATE,
  wt_lo1                       CHAR(1),
  wt_lo2                       CHAR(1),
  wt_lo3                       CHAR(1),
  orgid                        NUMBER(7) not null,
  user_id                      NUMBER(7) not null,
  lang_id                      NUMBER(7) not null,
  lmoddate                     DATE not null,
  updated_by                   NUMBER(7),
  updated_date                 DATE,
  lg_ip_mac                    VARCHAR2(100),
  lg_ip_mac_upd                VARCHAR2(100),
  int_from                     DATE,
  int_to                       DATE,
  fyi_int_arrears              NUMBER(15,2),
  fyi_int_perc                 NUMBER(15,2),
  rm_rcptid                    NUMBER(12),
  bm_no                        VARCHAR2(16),
  h_status                     NVARCHAR2(1)
)
;
alter table TB_WT_BILL_MAS_HIST
  add constraint PK_H_BMIDNO primary key (H_BMIDNO);

prompt
prompt Creating table TB_WT_BILL_SCHEDULE
prompt ==================================
prompt
create table TB_WT_BILL_SCHEDULE
(
  cns_id            NUMBER(12) not null,
  cns_cpdid         NUMBER(12),
  cns_ccgid1        NUMBER(12),
  cns_from_date     NUMBER(2),
  cns_to_date       NUMBER(2),
  orgid             NUMBER(4) not null,
  user_id           NUMBER(7) not null,
  lang_id           NUMBER(7) not null,
  lmoddate          DATE not null,
  updated_by        NUMBER(7),
  updated_date      DATE,
  cns_mfcpdval      NUMBER(12),
  cns_yearid        NUMBER(12),
  cns_mn            NVARCHAR2(4),
  cns_ccgid2        NUMBER(12),
  cns_ccgid3        NUMBER(2),
  cns_ccgid4        NUMBER(12),
  cns_ccgid5        NUMBER(12),
  wt_v1             NVARCHAR2(100),
  wt_v2             NVARCHAR2(100),
  wt_v3             NVARCHAR2(100),
  wt_v4             NVARCHAR2(100),
  wt_v5             NVARCHAR2(100),
  wt_n1             NUMBER(15),
  wt_n2             NUMBER(15),
  wt_n3             NUMBER(15),
  wt_n4             NUMBER(15),
  wt_n5             NUMBER(15),
  wt_d1             DATE,
  wt_d2             DATE,
  wt_d3             DATE,
  wt_lo1            CHAR(1),
  wt_lo2            CHAR(1),
  wt_lo3            CHAR(1),
  cns_prb_frq       NUMBER(12),
  copy_cns_cpdid    NVARCHAR2(2),
  copy_cns_mfcpdval NVARCHAR2(2),
  copy_cns_prb_frq  NVARCHAR2(2),
  cod_id1_wwz       NUMBER(15),
  cod_id2_wwz       NUMBER(15),
  cod_id3_wwz       NUMBER(15),
  cod_id4_wwz       NUMBER(15),
  cod_id5_wwz       NUMBER(15),
  depends_on_type   VARCHAR2(20)
)
;
comment on column TB_WT_BILL_SCHEDULE.cns_id
  is 'Consumer Identifier';
comment on column TB_WT_BILL_SCHEDULE.cns_cpdid
  is 'Bill Frequency. Linked to tb_comparam_det(cpd_id) for prefix BSC.';
comment on column TB_WT_BILL_SCHEDULE.cns_ccgid1
  is 'Connection Category. Linked to tb_comparent_det(cod_id) for hierarchy level 1 of prefix CCG.';
comment on column TB_WT_BILL_SCHEDULE.cns_from_date
  is 'From Date';
comment on column TB_WT_BILL_SCHEDULE.cns_to_date
  is 'To Date';
comment on column TB_WT_BILL_SCHEDULE.orgid
  is 'Org ID';
comment on column TB_WT_BILL_SCHEDULE.user_id
  is 'User ID';
comment on column TB_WT_BILL_SCHEDULE.lang_id
  is 'Lang ID';
comment on column TB_WT_BILL_SCHEDULE.lmoddate
  is 'Last Modification Date';
comment on column TB_WT_BILL_SCHEDULE.updated_by
  is 'User id who update the data';
comment on column TB_WT_BILL_SCHEDULE.updated_date
  is 'Date on which data is going to update';
comment on column TB_WT_BILL_SCHEDULE.cns_mfcpdval
  is 'Meter Reading Frequency. Linked to tb_comparam_det(cpd_id) for prefix MSC.';
comment on column TB_WT_BILL_SCHEDULE.cns_yearid
  is 'Yearwise billing schedule';
comment on column TB_WT_BILL_SCHEDULE.cns_mn
  is 'Meter/Non Meter flag';
comment on column TB_WT_BILL_SCHEDULE.cns_ccgid2
  is 'Connection Category. Linked to tb_comparent_det(cod_id) for hierarchy level 2 of prefix CCG.';
comment on column TB_WT_BILL_SCHEDULE.cns_ccgid3
  is 'Connection Category. Linked to tb_comparent_det(cod_id) for hierarchy level 3 of prefix CCG.';
comment on column TB_WT_BILL_SCHEDULE.cns_ccgid4
  is 'Connection Category. Linked to tb_comparent_det(cod_id) for hierarchy level 4 of prefix CCG.';
comment on column TB_WT_BILL_SCHEDULE.cns_ccgid5
  is 'Connection Category. Linked to tb_comparent_det(cod_id) for hierarchy level 5 of prefix CCG.';
comment on column TB_WT_BILL_SCHEDULE.wt_v1
  is 'Additional nvarchar2 WT_V1 to be used in future';
comment on column TB_WT_BILL_SCHEDULE.wt_v2
  is 'Additional nvarchar2 WT_V2 to be used in future';
comment on column TB_WT_BILL_SCHEDULE.wt_v3
  is 'Additional nvarchar2 WT_V3 to be used in future';
comment on column TB_WT_BILL_SCHEDULE.wt_v4
  is 'Additional nvarchar2 WT_V4 to be used in future';
comment on column TB_WT_BILL_SCHEDULE.wt_v5
  is 'Additional nvarchar2 WT_V5 to be used in future';
comment on column TB_WT_BILL_SCHEDULE.wt_n1
  is 'Additional number WT_N1 to be used in future';
comment on column TB_WT_BILL_SCHEDULE.wt_n2
  is 'Additional number WT_N2 to be used in future';
comment on column TB_WT_BILL_SCHEDULE.wt_n3
  is 'Additional number WT_N3 to be used in future';
comment on column TB_WT_BILL_SCHEDULE.wt_n4
  is 'Additional number WT_N4 to be used in future';
comment on column TB_WT_BILL_SCHEDULE.wt_n5
  is 'Additional number WT_N5 to be used in future';
comment on column TB_WT_BILL_SCHEDULE.wt_d1
  is 'Additional Date WT_D1 to be used in future';
comment on column TB_WT_BILL_SCHEDULE.wt_d2
  is 'Additional Date WT_D2 to be used in future';
comment on column TB_WT_BILL_SCHEDULE.wt_d3
  is 'Additional Date WT_D3 to be used in future';
comment on column TB_WT_BILL_SCHEDULE.wt_lo1
  is 'Additional Logical field WT_LO1 to be used in future';
comment on column TB_WT_BILL_SCHEDULE.wt_lo2
  is 'Additional Logical field WT_LO2 to be used in future';
comment on column TB_WT_BILL_SCHEDULE.wt_lo3
  is 'Additional Logical field WT_LO3 to be used in future';
comment on column TB_WT_BILL_SCHEDULE.cns_prb_frq
  is 'Pro-Rata Billing Frequency. Linked to tb_comparam_det(cpd_id) for prefix BSC.';
comment on column TB_WT_BILL_SCHEDULE.cod_id1_wwz
  is 'Ward Zone hierarchy Linked to tb_comparent_det(cod_id) for hierarchy level 1 of prefix WWZ';
comment on column TB_WT_BILL_SCHEDULE.cod_id2_wwz
  is 'Ward Zone hierarchy Linked to tb_comparent_det(cod_id) for hierarchy level 2 of prefix WWZ';
comment on column TB_WT_BILL_SCHEDULE.cod_id3_wwz
  is 'Ward Zone hierarchy Linked to tb_comparent_det(cod_id) for hierarchy level 3 of prefix WWZ';
comment on column TB_WT_BILL_SCHEDULE.cod_id4_wwz
  is 'Ward Zone hierarchy Linked to tb_comparent_det(cod_id) for hierarchy level 4 of prefix WWZ';
comment on column TB_WT_BILL_SCHEDULE.cod_id5_wwz
  is 'Ward Zone hierarchy Linked to tb_comparent_det(cod_id) for hierarchy level 5 of prefix WWZ';
comment on column TB_WT_BILL_SCHEDULE.depends_on_type
  is 'Ward/Zone Type of Consumer Type';
alter table TB_WT_BILL_SCHEDULE
  add constraint PK_CNSID_PK primary key (CNS_ID);
alter table TB_WT_BILL_SCHEDULE
  add constraint FK_CNS_BILL_FRQ foreign key (CNS_CPDID)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_WT_BILL_SCHEDULE
  add constraint FK_CNS_CCGID2 foreign key (CNS_CCGID2)
  references TB_COMPARENT_DET (COD_ID);
alter table TB_WT_BILL_SCHEDULE
  add constraint FK_CNS_CCGID3 foreign key (CNS_CCGID3)
  references TB_COMPARENT_DET (COD_ID);
alter table TB_WT_BILL_SCHEDULE
  add constraint FK_CNS_CCGID4 foreign key (CNS_CCGID4)
  references TB_COMPARENT_DET (COD_ID);
alter table TB_WT_BILL_SCHEDULE
  add constraint FK_CNS_CCGID5 foreign key (CNS_CCGID5)
  references TB_COMPARENT_DET (COD_ID);
alter table TB_WT_BILL_SCHEDULE
  add constraint FK_CNS_MRD_FRQ foreign key (CNS_MFCPDVAL)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_WT_BILL_SCHEDULE
  add constraint FK_CNS_PRB_FRQ foreign key (CNS_PRB_FRQ)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_WT_BILL_SCHEDULE
  add foreign key (CNS_CCGID1)
  references TB_COMPARENT_DET (COD_ID);

prompt
prompt Creating table TB_WT_BILL_SCHEDULE_DETAIL
prompt =========================================
prompt
create table TB_WT_BILL_SCHEDULE_DETAIL
(
  det_id        NUMBER(12) not null,
  cns_id        NUMBER(12),
  cns_from_date NUMBER(2),
  cns_to_date   NUMBER(2),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  lmoddate      DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  status        NVARCHAR2(1)
)
;
comment on column TB_WT_BILL_SCHEDULE_DETAIL.det_id
  is 'Primary key';
comment on column TB_WT_BILL_SCHEDULE_DETAIL.cns_id
  is 'FK FROM TB_WT_BILL_SCHEDULE';
comment on column TB_WT_BILL_SCHEDULE_DETAIL.cns_from_date
  is 'FROM MONTH';
comment on column TB_WT_BILL_SCHEDULE_DETAIL.cns_to_date
  is 'TO MONTH';
comment on column TB_WT_BILL_SCHEDULE_DETAIL.orgid
  is 'Org id';
comment on column TB_WT_BILL_SCHEDULE_DETAIL.user_id
  is 'User id';
comment on column TB_WT_BILL_SCHEDULE_DETAIL.lang_id
  is 'Language id';
comment on column TB_WT_BILL_SCHEDULE_DETAIL.lmoddate
  is 'created date';
comment on column TB_WT_BILL_SCHEDULE_DETAIL.updated_by
  is 'updated by';
comment on column TB_WT_BILL_SCHEDULE_DETAIL.updated_date
  is 'updated date';
comment on column TB_WT_BILL_SCHEDULE_DETAIL.status
  is 'Status ';
alter table TB_WT_BILL_SCHEDULE_DETAIL
  add constraint PK_DET_ID primary key (DET_ID);
alter table TB_WT_BILL_SCHEDULE_DETAIL
  add constraint FK_CNS_ID foreign key (CNS_ID)
  references TB_WT_BILL_SCHEDULE (CNS_ID);

prompt
prompt Creating table TB_WT_BILL_SCHEDULE_DET_HIST
prompt ===========================================
prompt
create table TB_WT_BILL_SCHEDULE_DET_HIST
(
  h_detid       NUMBER(12) not null,
  det_id        NUMBER(12) not null,
  cns_id        NUMBER(12),
  cns_from_date NUMBER(2),
  cns_to_date   NUMBER(2),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  lmoddate      DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  status        NVARCHAR2(1),
  h_status      NVARCHAR2(1)
)
;
alter table TB_WT_BILL_SCHEDULE_DET_HIST
  add constraint PK_H_DETID primary key (H_DETID);

prompt
prompt Creating table TB_WT_BILL_SCHEDULE_HIST
prompt =======================================
prompt
create table TB_WT_BILL_SCHEDULE_HIST
(
  h_cnsid           NUMBER(12) not null,
  cns_id            NUMBER(12) not null,
  cns_cpdid         NUMBER(12),
  cns_ccgid1        NUMBER(12),
  cns_from_date     NUMBER(2),
  cns_to_date       NUMBER(2),
  orgid             NUMBER(4) not null,
  user_id           NUMBER(7) not null,
  lang_id           NUMBER(7) not null,
  lmoddate          DATE not null,
  updated_by        NUMBER(7),
  updated_date      DATE,
  cns_mfcpdval      NUMBER(12),
  cns_yearid        NUMBER(12),
  cns_mn            NVARCHAR2(1),
  cns_ccgid2        NUMBER(12),
  cns_ccgid3        NUMBER(2),
  cns_ccgid4        NUMBER(12),
  cns_ccgid5        NUMBER(12),
  wt_v1             NVARCHAR2(100),
  wt_v2             NVARCHAR2(100),
  wt_v3             NVARCHAR2(100),
  wt_v4             NVARCHAR2(100),
  wt_v5             NVARCHAR2(100),
  wt_n1             NUMBER(15),
  wt_n2             NUMBER(15),
  wt_n3             NUMBER(15),
  wt_n4             NUMBER(15),
  wt_n5             NUMBER(15),
  wt_d1             DATE,
  wt_d2             DATE,
  wt_d3             DATE,
  wt_lo1            CHAR(1),
  wt_lo2            CHAR(1),
  wt_lo3            CHAR(1),
  cns_prb_frq       NUMBER(12),
  copy_cns_cpdid    NVARCHAR2(2),
  copy_cns_mfcpdval NVARCHAR2(2),
  copy_cns_prb_frq  NVARCHAR2(2),
  cod_id1_wwz       NUMBER(15),
  cod_id2_wwz       NUMBER(15),
  cod_id3_wwz       NUMBER(15),
  cod_id4_wwz       NUMBER(15),
  cod_id5_wwz       NUMBER(15),
  depends_on_type   VARCHAR2(20),
  h_status          NVARCHAR2(1)
)
;
alter table TB_WT_BILL_SCHEDULE_HIST
  add constraint PK_HCNSID primary key (H_CNSID);

prompt
prompt Creating table TB_WT_CHANGE_OF_OWNERSHIP
prompt ========================================
prompt
create table TB_WT_CHANGE_OF_OWNERSHIP
(
  ch_idn             NUMBER(12) not null,
  cs_idn             NUMBER(12),
  apm_application_id NUMBER(16),
  ch_apldate         DATE,
  ch_new_title       NUMBER(12),
  ch_new_name        NVARCHAR2(100),
  ch_new_mname       NVARCHAR2(100),
  ch_new_lname       NVARCHAR2(100),
  ch_new_gender      NUMBER(10),
  ch_new_uid_no      NUMBER(12),
  ch_new_orgname     NVARCHAR2(100),
  ch_old_title       NUMBER(12),
  ch_old_name        NVARCHAR2(150),
  ch_old_mname       NVARCHAR2(100),
  ch_old_lname       NVARCHAR2(100),
  ch_old_uid_no      NUMBER(12),
  ch_old_gender      NUMBER(10),
  ch_old_orgname     NVARCHAR2(200),
  ch_remark          NVARCHAR2(200),
  ch_granted         VARCHAR2(1),
  orgid              NUMBER(4),
  created_by         NUMBER(7),
  lang_id            NUMBER(7),
  lmoddate           DATE,
  updated_by         NUMBER(7),
  updated_date       DATE,
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  wt_v1              NVARCHAR2(100),
  wt_v2              NVARCHAR2(100),
  wt_v3              NVARCHAR2(100),
  wt_v4              NVARCHAR2(100),
  wt_v5              NVARCHAR2(100),
  wt_n1              NUMBER(15),
  wt_n2              NUMBER(15),
  wt_n3              NUMBER(15),
  wt_n4              NUMBER(15),
  wt_n5              NUMBER(15),
  wt_d1              DATE,
  wt_d2              DATE,
  wt_d3              DATE,
  wt_lo1             CHAR(1),
  wt_lo2             CHAR(1),
  wt_lo3             CHAR(1),
  ch_transfer_mode   NUMBER(12)
)
;
comment on column TB_WT_CHANGE_OF_OWNERSHIP.ch_idn
  is 'Primary Key';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.cs_idn
  is 'FK From TB_CSMR_INFO';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.apm_application_id
  is 'Appliction ID';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.ch_apldate
  is 'Apllication Date';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.ch_new_title
  is 'Title ''TTL''Prefix';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.ch_new_name
  is 'New Owner Name';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.ch_new_mname
  is 'New Owner Middle Name';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.ch_new_lname
  is 'New Owner Last Name';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.ch_new_gender
  is 'Gender';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.ch_new_uid_no
  is 'New Owner Aadhar No.';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.ch_new_orgname
  is 'New Owner Orgname';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.ch_old_title
  is 'Old Title ''TTL''Prefix';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.ch_old_name
  is 'Old Owner Name';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.ch_old_mname
  is 'Old Owner Middle Name';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.ch_old_lname
  is 'Old Owner Last Name';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.ch_old_uid_no
  is 'Old Owner Aadhar No.';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.ch_old_gender
  is 'Gender';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.ch_old_orgname
  is 'Old Owner Orgname';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.ch_remark
  is 'Remark';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.ch_granted
  is 'Granted or not Flag';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.orgid
  is 'Org ID';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.created_by
  is 'Created BY';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.lang_id
  is 'Language ID';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.lmoddate
  is 'CREATED DATE';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.updated_by
  is 'UPDATED BY';
comment on column TB_WT_CHANGE_OF_OWNERSHIP.updated_date
  is 'UPDATED DATE';
alter table TB_WT_CHANGE_OF_OWNERSHIP
  add constraint PK_CH_IDN primary key (CH_IDN);
alter table TB_WT_CHANGE_OF_OWNERSHIP
  add constraint FK_CSMR_CHNOWN foreign key (CS_IDN)
  references TB_CSMR_INFO (CS_IDN);
alter table TB_WT_CHANGE_OF_OWNERSHIP
  add constraint CH_GRANTED
  check (cH_granted in ('Y','N'));

prompt
prompt Creating table TB_WT_CHANGE_OF_OWNERSHIP_HIST
prompt =============================================
prompt
create table TB_WT_CHANGE_OF_OWNERSHIP_HIST
(
  h_chid             NUMBER(12) not null,
  ch_idn             NUMBER(12) not null,
  cs_idn             NUMBER(12),
  apm_application_id NUMBER(16),
  ch_apldate         DATE,
  ch_new_title       NUMBER(12),
  ch_new_name        NVARCHAR2(100),
  ch_new_mname       NVARCHAR2(100),
  ch_new_lname       NVARCHAR2(100),
  ch_new_gender      NUMBER(10),
  ch_new_uid_no      NUMBER(12),
  ch_new_orgname     NVARCHAR2(100),
  ch_old_title       NUMBER(12),
  ch_old_name        NVARCHAR2(150),
  ch_old_mname       NVARCHAR2(100),
  ch_old_lname       NVARCHAR2(100),
  ch_old_uid_no      NUMBER(12),
  ch_old_gender      NUMBER(10),
  ch_old_orgname     NVARCHAR2(200),
  ch_remark          NVARCHAR2(200),
  ch_granted         VARCHAR2(1),
  orgid              NUMBER(4),
  created_by         NUMBER(7),
  lang_id            NUMBER(7),
  lmoddate           DATE,
  updated_by         NUMBER(7),
  updated_date       DATE,
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  wt_v1              NVARCHAR2(100),
  wt_v2              NVARCHAR2(100),
  wt_v3              NVARCHAR2(100),
  wt_v4              NVARCHAR2(100),
  wt_v5              NVARCHAR2(100),
  wt_n1              NUMBER(15),
  wt_n2              NUMBER(15),
  wt_n3              NUMBER(15),
  wt_n4              NUMBER(15),
  wt_n5              NUMBER(15),
  wt_d1              DATE,
  wt_d2              DATE,
  wt_d3              DATE,
  wt_lo1             CHAR(1),
  wt_lo2             CHAR(1),
  wt_lo3             CHAR(1),
  h_status           NVARCHAR2(1)
)
;
alter table TB_WT_CHANGE_OF_OWNERSHIP_HIST
  add constraint PK_HCHID primary key (H_CHID);

prompt
prompt Creating table TB_WT_PLUM_MAS
prompt =============================
prompt
create table TB_WT_PLUM_MAS
(
  plum_id               NUMBER(12) not null,
  plum_appl_date        DATE,
  plum_lname            NVARCHAR2(100),
  plum_fname            NVARCHAR2(100),
  plum_mname            NVARCHAR2(100),
  plum_sex              NVARCHAR2(1),
  plum_dob              DATE,
  plum_contact_personnm NVARCHAR2(100),
  plum_lic_no           NVARCHAR2(20),
  plum_lic_issue_flg    CHAR(1),
  plum_lic_issue_date   DATE,
  plum_lic_from_date    DATE,
  plum_lic_to_date      DATE,
  orgid                 NUMBER(4),
  user_id               NUMBER(7),
  lang_id               NUMBER(7),
  lmoddate              DATE,
  plum_interview_remark NVARCHAR2(200),
  plum_interview_clr    NVARCHAR2(1),
  updated_by            NUMBER(7),
  updated_date          DATE,
  plum_lic_type         NVARCHAR2(1),
  plum_fat_hus_name     NVARCHAR2(50),
  plum_contact_no       NVARCHAR2(20),
  plum_cpdtitle         NVARCHAR2(30),
  plum_add              NVARCHAR2(200),
  plum_interview_dttm   DATE,
  plum_cscid1           NUMBER(12),
  plum_cscid2           NUMBER(12),
  plum_cscid3           NUMBER(12),
  plum_cscid4           NUMBER(12),
  plum_cscid5           NUMBER(12),
  plum_old_lic_no       NVARCHAR2(20),
  ported                VARCHAR2(1),
  lg_ip_mac             VARCHAR2(100),
  lg_ip_mac_upd         VARCHAR2(100),
  plum_entry_flag       VARCHAR2(1),
  plum_image_path       NVARCHAR2(100),
  wt_v3                 NVARCHAR2(100),
  wt_v4                 NVARCHAR2(100),
  wt_v5                 NVARCHAR2(100),
  wt_n1                 NUMBER(15),
  wt_n2                 NUMBER(15),
  wt_n3                 NUMBER(15),
  wt_n4                 NUMBER(15),
  wt_n5                 NUMBER(15),
  wt_d1                 DATE,
  wt_d2                 DATE,
  wt_d3                 DATE,
  wt_lo1                CHAR(1),
  wt_lo2                CHAR(1),
  wt_lo3                CHAR(1),
  apm_application_id    NUMBER(16),
  plum_image            NVARCHAR2(200)
)
;
comment on table TB_WT_PLUM_MAS
  is 'This table is used to store personal details of the plumber';
comment on column TB_WT_PLUM_MAS.plum_id
  is 'Generated Id';
comment on column TB_WT_PLUM_MAS.plum_appl_date
  is 'Application Date';
comment on column TB_WT_PLUM_MAS.plum_lname
  is 'Last Name of plumber';
comment on column TB_WT_PLUM_MAS.plum_fname
  is 'First Name of plumber';
comment on column TB_WT_PLUM_MAS.plum_mname
  is 'Middle Name of plumber';
comment on column TB_WT_PLUM_MAS.plum_sex
  is 'Sex of plumber';
comment on column TB_WT_PLUM_MAS.plum_dob
  is 'Date of birth';
comment on column TB_WT_PLUM_MAS.plum_contact_personnm
  is 'Contact Person';
comment on column TB_WT_PLUM_MAS.plum_lic_no
  is 'License No.';
comment on column TB_WT_PLUM_MAS.plum_lic_issue_flg
  is 'Issue flag';
comment on column TB_WT_PLUM_MAS.plum_lic_issue_date
  is 'License Issue Date';
comment on column TB_WT_PLUM_MAS.plum_lic_from_date
  is 'License from Date';
comment on column TB_WT_PLUM_MAS.plum_lic_to_date
  is 'License to Date';
comment on column TB_WT_PLUM_MAS.orgid
  is 'Org ID';
comment on column TB_WT_PLUM_MAS.user_id
  is 'User ID';
comment on column TB_WT_PLUM_MAS.lang_id
  is 'Lang ID';
comment on column TB_WT_PLUM_MAS.lmoddate
  is 'Last Modification Date';
comment on column TB_WT_PLUM_MAS.plum_interview_remark
  is 'Interview Remarks';
comment on column TB_WT_PLUM_MAS.plum_interview_clr
  is 'Interview Cleared Flag';
comment on column TB_WT_PLUM_MAS.updated_by
  is 'User id who update the data';
comment on column TB_WT_PLUM_MAS.updated_date
  is 'Date on which data is going to update';
comment on column TB_WT_PLUM_MAS.plum_lic_type
  is 'License Type';
comment on column TB_WT_PLUM_MAS.plum_fat_hus_name
  is 'Stores Father/Husband name of the Plumber';
comment on column TB_WT_PLUM_MAS.plum_contact_no
  is 'Contact No.';
comment on column TB_WT_PLUM_MAS.plum_cpdtitle
  is 'Title of plumber';
comment on column TB_WT_PLUM_MAS.plum_add
  is 'Plumber Address';
comment on column TB_WT_PLUM_MAS.plum_interview_dttm
  is 'Interview date and time';
comment on column TB_WT_PLUM_MAS.plum_cscid1
  is 'Hierarchy for country, state';
comment on column TB_WT_PLUM_MAS.plum_cscid2
  is 'Hierarchy for country, state';
comment on column TB_WT_PLUM_MAS.plum_cscid3
  is 'Hierarchy for country, state';
comment on column TB_WT_PLUM_MAS.plum_cscid4
  is 'Hierarchy for country, state';
comment on column TB_WT_PLUM_MAS.plum_cscid5
  is 'Hierarchy for country, state';
comment on column TB_WT_PLUM_MAS.plum_old_lic_no
  is 'Old License no';
comment on column TB_WT_PLUM_MAS.ported
  is 'Y-Data created thru Data entry form or Data Upload';
comment on column TB_WT_PLUM_MAS.lg_ip_mac
  is 'Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_WT_PLUM_MAS.lg_ip_mac_upd
  is 'Updated Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_WT_PLUM_MAS.plum_entry_flag
  is 'D-Data created through Data entry form, U-Data created through upload';
comment on column TB_WT_PLUM_MAS.plum_image_path
  is 'PATH FOR IMAGE';
comment on column TB_WT_PLUM_MAS.wt_v3
  is 'Additional nvarchar2 WT_V3 to be used in future';
comment on column TB_WT_PLUM_MAS.wt_v4
  is 'Additional nvarchar2 WT_V4 to be used in future';
comment on column TB_WT_PLUM_MAS.wt_v5
  is 'Additional nvarchar2 WT_V5 to be used in future';
comment on column TB_WT_PLUM_MAS.wt_n1
  is 'Additional number WT_N1 to be used in future';
comment on column TB_WT_PLUM_MAS.wt_n2
  is 'Additional number WT_N2 to be used in future';
comment on column TB_WT_PLUM_MAS.wt_n3
  is 'Additional number WT_N3 to be used in future';
comment on column TB_WT_PLUM_MAS.wt_n4
  is 'Additional number WT_N4 to be used in future';
comment on column TB_WT_PLUM_MAS.wt_n5
  is 'Additional number WT_N5 to be used in future';
comment on column TB_WT_PLUM_MAS.wt_d1
  is 'Additional Date WT_D1 to be used in future';
comment on column TB_WT_PLUM_MAS.wt_d2
  is 'Additional Date WT_D2 to be used in future';
comment on column TB_WT_PLUM_MAS.wt_d3
  is 'Additional Date WT_D3 to be used in future';
comment on column TB_WT_PLUM_MAS.wt_lo1
  is 'Additional Logical field WT_LO1 to be used in future';
comment on column TB_WT_PLUM_MAS.wt_lo2
  is 'Additional Logical field WT_LO2 to be used in future';
comment on column TB_WT_PLUM_MAS.wt_lo3
  is 'Additional Logical field WT_LO3 to be used in future';
comment on column TB_WT_PLUM_MAS.apm_application_id
  is 'Application id ';
comment on column TB_WT_PLUM_MAS.plum_image
  is 'Plum Iamge';
alter table TB_WT_PLUM_MAS
  add constraint PK_PLUMID primary key (PLUM_ID);
alter table TB_WT_PLUM_MAS
  add constraint CK_PLUM_ENTRY_FLAG
  check (PLUM_ENTRY_FLAG IN ('D','U'));
alter table TB_WT_PLUM_MAS
  add constraint CK_PLUM_PORTED
  check (PORTED IN ('Y','N'));
alter table TB_WT_PLUM_MAS
  add constraint NN_PLUM_LANG_ID
  check ("LANG_ID" IS NOT NULL);
alter table TB_WT_PLUM_MAS
  add constraint NN_PLUM_LMODDATE
  check ("LMODDATE" IS NOT NULL);
alter table TB_WT_PLUM_MAS
  add constraint NN_PLUM_ORGID
  check ("ORGID" IS NOT NULL);
alter table TB_WT_PLUM_MAS
  add constraint NN_PLUM_USERID
  check ("USER_ID" IS NOT NULL);

prompt
prompt Creating table TB_WT_CHANGE_OF_USE
prompt ==================================
prompt
create table TB_WT_CHANGE_OF_USE
(
  cis_id               NUMBER(12) not null,
  cs_idn               NUMBER(12),
  statusofwork         NVARCHAR2(1),
  dateofcomp           DATE,
  plum_id              NUMBER(12),
  remark               NVARCHAR2(2000),
  use_type             NVARCHAR2(2),
  orgid                NUMBER(4) not null,
  user_id              NUMBER(7) not null,
  lang_id              NUMBER(7) not null,
  lmoddate             DATE not null,
  updated_by           NUMBER(7),
  updated_date         DATE,
  cou_granted          NVARCHAR2(1),
  apm_application_id   NUMBER(16),
  apm_application_date DATE,
  trd_premise          NUMBER(12),
  cou_granted_dt       DATE,
  old_trd_premise      NUMBER(12),
  old_trm_group1       NUMBER(14),
  old_trm_group2       NUMBER(14),
  old_trm_group3       NUMBER(14),
  old_trm_group4       NUMBER(14),
  old_trm_group5       NUMBER(14),
  new_trm_group1       NUMBER(14),
  new_trm_group2       NUMBER(14),
  new_trm_group3       NUMBER(14),
  new_trm_group4       NUMBER(14),
  new_trm_group5       NUMBER(14),
  lg_ip_mac            VARCHAR2(100),
  lg_ip_mac_upd        VARCHAR2(100),
  wt_v1                NVARCHAR2(100),
  wt_v2                NVARCHAR2(100),
  wt_v3                NVARCHAR2(100),
  wt_v4                NVARCHAR2(100),
  wt_v5                NVARCHAR2(100),
  wt_n1                NUMBER(15),
  wt_n2                NUMBER(15),
  wt_n3                NUMBER(15),
  wt_n4                NUMBER(15),
  wt_n5                NUMBER(15),
  wt_d1                DATE,
  wt_d2                DATE,
  wt_d3                DATE,
  wt_lo1               CHAR(1),
  wt_lo2               CHAR(1),
  wt_lo3               CHAR(1),
  chan_grant_flag      CHAR(1),
  chan_aprvdate        DATE,
  chan_approvedby      NUMBER(7),
  chan_execdate        DATE
)
;
comment on column TB_WT_CHANGE_OF_USE.cis_id
  is 'change in size identification number';
comment on column TB_WT_CHANGE_OF_USE.cs_idn
  is 'connection identification';
comment on column TB_WT_CHANGE_OF_USE.statusofwork
  is 'status of work ';
comment on column TB_WT_CHANGE_OF_USE.dateofcomp
  is 'date of completion ';
comment on column TB_WT_CHANGE_OF_USE.plum_id
  is 'plumber id';
comment on column TB_WT_CHANGE_OF_USE.remark
  is 'remark ';
comment on column TB_WT_CHANGE_OF_USE.use_type
  is 'Use-- CCN Size --CS / Tariff group -TG/ Source line -SL';
comment on column TB_WT_CHANGE_OF_USE.orgid
  is 'Org ID';
comment on column TB_WT_CHANGE_OF_USE.user_id
  is 'User ID';
comment on column TB_WT_CHANGE_OF_USE.lang_id
  is 'Lang ID';
comment on column TB_WT_CHANGE_OF_USE.lmoddate
  is 'Last Modification Date';
comment on column TB_WT_CHANGE_OF_USE.updated_by
  is 'User id who update the data';
comment on column TB_WT_CHANGE_OF_USE.updated_date
  is 'Date on which data is going to update';
comment on column TB_WT_CHANGE_OF_USE.cou_granted
  is 'Checking for granted';
comment on column TB_WT_CHANGE_OF_USE.apm_application_id
  is 'Application id';
comment on column TB_WT_CHANGE_OF_USE.apm_application_date
  is 'Application date';
comment on column TB_WT_CHANGE_OF_USE.trd_premise
  is 'Premise type';
comment on column TB_WT_CHANGE_OF_USE.cou_granted_dt
  is 'Application Granted date';
comment on column TB_WT_CHANGE_OF_USE.old_trd_premise
  is 'Old premise';
comment on column TB_WT_CHANGE_OF_USE.old_trm_group1
  is 'Old Tariff group hierarchy';
comment on column TB_WT_CHANGE_OF_USE.old_trm_group2
  is 'Old Tariff group hierarchy';
comment on column TB_WT_CHANGE_OF_USE.old_trm_group3
  is 'Old Tariff group hierarchy';
comment on column TB_WT_CHANGE_OF_USE.old_trm_group4
  is 'Old Tariff group hierarchy';
comment on column TB_WT_CHANGE_OF_USE.old_trm_group5
  is 'Old Tariff group hierarchy';
comment on column TB_WT_CHANGE_OF_USE.new_trm_group1
  is 'New tariff group hierarchy';
comment on column TB_WT_CHANGE_OF_USE.new_trm_group2
  is 'New tariff group hierarchy';
comment on column TB_WT_CHANGE_OF_USE.new_trm_group3
  is 'New tariff group hierarchy';
comment on column TB_WT_CHANGE_OF_USE.new_trm_group4
  is 'New tariff group hierarchy';
comment on column TB_WT_CHANGE_OF_USE.new_trm_group5
  is 'New tariff group hierarchy';
comment on column TB_WT_CHANGE_OF_USE.lg_ip_mac
  is 'Client Machine�s Login Name | IP Address | Physical Address';
comment on column TB_WT_CHANGE_OF_USE.lg_ip_mac_upd
  is 'Updated Client Machine�s Login Name | IP Address | Physical Address';
comment on column TB_WT_CHANGE_OF_USE.wt_v1
  is 'Additional nvarchar2 WT_V1 to be used in future';
comment on column TB_WT_CHANGE_OF_USE.wt_v2
  is 'Additional nvarchar2 WT_V2 to be used in future';
comment on column TB_WT_CHANGE_OF_USE.wt_v3
  is 'Additional nvarchar2 WT_V3 to be used in future';
comment on column TB_WT_CHANGE_OF_USE.wt_v4
  is 'Additional nvarchar2 WT_V4 to be used in future';
comment on column TB_WT_CHANGE_OF_USE.wt_v5
  is 'Additional nvarchar2 WT_V5 to be used in future';
comment on column TB_WT_CHANGE_OF_USE.wt_n1
  is 'Additional number WT_N1 to be used in future';
comment on column TB_WT_CHANGE_OF_USE.wt_n2
  is 'Additional number WT_N2 to be used in future';
comment on column TB_WT_CHANGE_OF_USE.wt_n3
  is 'Additional number WT_N3 to be used in future';
comment on column TB_WT_CHANGE_OF_USE.wt_n4
  is 'Additional number WT_N4 to be used in future';
comment on column TB_WT_CHANGE_OF_USE.wt_n5
  is 'Additional number WT_N5 to be used in future';
comment on column TB_WT_CHANGE_OF_USE.wt_d1
  is 'Additional Date WT_D1 to be used in future';
comment on column TB_WT_CHANGE_OF_USE.wt_d2
  is 'Additional Date WT_D2 to be used in future';
comment on column TB_WT_CHANGE_OF_USE.wt_d3
  is 'Additional Date WT_D3 to be used in future';
comment on column TB_WT_CHANGE_OF_USE.wt_lo1
  is 'Additional Logical field WT_LO1 to be used in future';
comment on column TB_WT_CHANGE_OF_USE.wt_lo2
  is 'Additional Logical field WT_LO2 to be used in future';
comment on column TB_WT_CHANGE_OF_USE.wt_lo3
  is 'Additional Logical field WT_LO3 to be used in future';
alter table TB_WT_CHANGE_OF_USE
  add constraint PK_CIS_ID_ORGID primary key (CIS_ID);
alter table TB_WT_CHANGE_OF_USE
  add constraint FK_TPM_TWCOU foreign key (PLUM_ID)
  references TB_WT_PLUM_MAS (PLUM_ID);
alter table TB_WT_CHANGE_OF_USE
  add constraint FK_TWCOU_TCSMR_CSIDN_ORGID foreign key (CS_IDN)
  references TB_CSMR_INFO (CS_IDN);

prompt
prompt Creating table TB_WT_CSMR_ADDITIONAL_OWNER
prompt ==========================================
prompt
create table TB_WT_CSMR_ADDITIONAL_OWNER
(
  cao_id             NUMBER(12) not null,
  cs_idn             NUMBER(12) not null,
  cao_title          NVARCHAR2(15),
  cao_fname          NVARCHAR2(300),
  cao_mname          NVARCHAR2(300),
  cao_lname          NVARCHAR2(300),
  cao_address        NVARCHAR2(1000),
  cao_contactno      NVARCHAR2(50),
  orgid              NUMBER not null,
  user_id            NUMBER not null,
  lang_id            NUMBER not null,
  lmoddate           DATE not null,
  updated_by         NUMBER,
  updated_date       DATE,
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  cao_gender         NUMBER,
  cao_uid            NUMBER(12),
  cao_new_title      NUMBER(10),
  cao_new_fname      NVARCHAR2(300),
  cao_new_mname      NVARCHAR2(300),
  cao_new_lname      NVARCHAR2(300),
  cao_new_address    NVARCHAR2(300),
  cao_new_contactno  NVARCHAR2(50),
  cao_new_gender     NUMBER(10),
  cao_new_uid        NUMBER(12),
  apm_application_id NUMBER(16),
  is_deleted         NVARCHAR2(1) default 'N'
)
;
comment on table TB_WT_CSMR_ADDITIONAL_OWNER
  is 'CONSUMER ADDITIONAL OWNER MAIN TABLE';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.cao_id
  is 'PRIMARY KEY->CONSUMER ADDITIONAL OWNER TABLE';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.cs_idn
  is 'COMSUMER INFORMATION ID NO';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.cao_title
  is 'CONSUMER ADDITIONAL OWNER TITLE';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.cao_fname
  is 'CONSUMER ADDITIONAL OWNER FIRST NAME';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.cao_mname
  is 'CONSUMER ADDITIONAL OWNER MIDDLE NAME';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.cao_lname
  is 'CONSUMER ADDITIONAL OWNER LAST NAME';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.cao_address
  is 'CONSUMER ADDITIONAL OWNER ADDRESS';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.cao_contactno
  is 'CONSUMER ADDITIONAL OWNER CONTACT NO.';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.orgid
  is 'ORG ID';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.user_id
  is 'USER ID';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.lang_id
  is 'LANGUAGE ID';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.lmoddate
  is 'CREATION DATE';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.updated_by
  is 'UPDATED BY';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.updated_date
  is 'UPDATED DATE';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.lg_ip_mac
  is 'Client Machine�s Login Name | IP Address | Physical Address';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.lg_ip_mac_upd
  is 'Client Machine�s Login Name | IP Address | Physical Address';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.cao_gender
  is 'GENDER';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.cao_uid
  is 'AADHAR NO';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.cao_new_title
  is 'NEW CONSUMER ADDITIONAL OWNER TITLE';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.cao_new_fname
  is 'NEW CONSUMER ADDITIONAL OWNER FIRST NAM';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.cao_new_mname
  is 'NEW CONSUMER ADDITIONAL OWNER MIDDLE NAME';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.cao_new_lname
  is 'NEW CONSUMER ADDITIONAL OWNER LAST NAME';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.cao_new_address
  is 'NEW CONSUMER ADDITIONAL OWNER ADDRESS';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.cao_new_contactno
  is 'NEW CONSUMER ADDITIONAL OWNER CONTACT NO.';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.cao_new_gender
  is 'GENDER';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.cao_new_uid
  is 'AADHAR NO';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.apm_application_id
  is 'APPLICATION ID';
comment on column TB_WT_CSMR_ADDITIONAL_OWNER.is_deleted
  is 'Flag to identify whether the record is deleted or not. Y for deleted (Inactive) and N for not deleted (Active) record.';
alter table TB_WT_CSMR_ADDITIONAL_OWNER
  add constraint PK_CAO_ID_ORGID primary key (CAO_ID, ORGID);
alter table TB_WT_CSMR_ADDITIONAL_OWNER
  add constraint FK_CSIDN_CSMRINFO foreign key (CS_IDN)
  references TB_CSMR_INFO (CS_IDN);

prompt
prompt Creating table TB_WT_CSMR_ADDIT_OWNER_HIST
prompt ==========================================
prompt
create table TB_WT_CSMR_ADDIT_OWNER_HIST
(
  h_caoid            NUMBER(12) not null,
  cao_id             NUMBER(12) not null,
  cs_idn             NUMBER(12) not null,
  cao_title          NVARCHAR2(15),
  cao_fname          NVARCHAR2(300),
  cao_mname          NVARCHAR2(300),
  cao_lname          NVARCHAR2(300),
  cao_address        NVARCHAR2(1000),
  cao_contactno      NVARCHAR2(50),
  orgid              NUMBER not null,
  user_id            NUMBER not null,
  lang_id            NUMBER not null,
  lmoddate           DATE not null,
  updated_by         NUMBER,
  updated_date       DATE,
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  cao_gender         NUMBER,
  cao_uid            NUMBER(12),
  cao_new_title      NUMBER(10),
  cao_new_fname      NVARCHAR2(300),
  cao_new_mname      NVARCHAR2(300),
  cao_new_lname      NVARCHAR2(300),
  cao_new_address    NVARCHAR2(300),
  cao_new_contactno  NVARCHAR2(50),
  cao_new_gender     NUMBER(10),
  cao_new_uid        NUMBER(12),
  apm_application_id NUMBER(16),
  is_deleted         NVARCHAR2(1) default 'N',
  h_status           NVARCHAR2(1)
)
;
alter table TB_WT_CSMR_ADDIT_OWNER_HIST
  add constraint PK_H_CAOID primary key (H_CAOID);

prompt
prompt Creating table TB_WT_CSMR_ROAD_TYPES
prompt ====================================
prompt
create table TB_WT_CSMR_ROAD_TYPES
(
  crt_id             NUMBER(12) not null,
  crt_cs_idn         NUMBER(12) not null,
  crt_road_types     NUMBER(15) not null,
  crt_road_units     NUMBER(12,2),
  orgid              NUMBER(4) not null,
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(7) not null,
  lmoddate           DATE not null,
  updated_by         NUMBER(7),
  updated_date       DATE,
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  crt_granted        VARCHAR2(1) not null,
  crt_latest         VARCHAR2(1) not null,
  apm_application_id NUMBER(16),
  sm_service_id      NUMBER(12),
  crt_disc_type      NUMBER(12),
  crt_disc_recn_id   NUMBER(12),
  crt_road_trench    NUMBER(7,2),
  is_deleted         VARCHAR2(1) default 'N'
)
;
comment on column TB_WT_CSMR_ROAD_TYPES.crt_road_types
  is 'Road type. linked to tb_comparam_det for prefix RDT';
comment on column TB_WT_CSMR_ROAD_TYPES.crt_granted
  is 'Stores ''N'' if application is not granted at scrutiny level, ''Y'' if granted.';
comment on column TB_WT_CSMR_ROAD_TYPES.crt_latest
  is '''Y'' indiates latest road type data for a connection. This flag is updated as ''Y'' when LOI collection is done against service';
comment on column TB_WT_CSMR_ROAD_TYPES.apm_application_id
  is 'Application ID against which road types are captured';
comment on column TB_WT_CSMR_ROAD_TYPES.sm_service_id
  is 'Service ID for which road types are captured';
comment on column TB_WT_CSMR_ROAD_TYPES.crt_disc_type
  is 'This holds CPD ID of prefix DIC - Disconnection Type';
comment on column TB_WT_CSMR_ROAD_TYPES.crt_disc_recn_id
  is 'Disconnection/Reconnection table ID(created thu. node) against which road types are inserted/updated.';
comment on column TB_WT_CSMR_ROAD_TYPES.crt_road_trench
  is 'Diging to road';
comment on column TB_WT_CSMR_ROAD_TYPES.is_deleted
  is 'Flag to identify whether the record is deleted or not. Y for deleted (Inactive) and N for not deleted (Active) record.';
alter table TB_WT_CSMR_ROAD_TYPES
  add constraint PK_CRT_ID_ORGID primary key (CRT_ID, ORGID);
alter table TB_WT_CSMR_ROAD_TYPES
  add constraint FK_CRT_APPLICAITON_ID foreign key (APM_APPLICATION_ID)
  references TB_CFC_APPLICATION_MST (APM_APPLICATION_ID);
alter table TB_WT_CSMR_ROAD_TYPES
  add constraint FK_CRT_DISC_TYPE foreign key (CRT_DISC_TYPE)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_WT_CSMR_ROAD_TYPES
  add constraint FK_CRT_ROAD_TYPES foreign key (CRT_ROAD_TYPES)
  references TB_COMPARAM_DET (CPD_ID);
alter table TB_WT_CSMR_ROAD_TYPES
  add constraint FK_CRT_SERVICE_ID foreign key (SM_SERVICE_ID)
  references TB_SERVICES_MST (SM_SERVICE_ID);
alter table TB_WT_CSMR_ROAD_TYPES
  add constraint FK_CSIDN_CSMRINF foreign key (CRT_CS_IDN)
  references TB_CSMR_INFO (CS_IDN);
alter table TB_WT_CSMR_ROAD_TYPES
  add constraint CK_CRT_GRANTED
  check (crt_granted IN ('Y','N'));
alter table TB_WT_CSMR_ROAD_TYPES
  add constraint CK_CRT_LATEST
  check (crt_latest IN ('Y','N'));

prompt
prompt Creating table TB_WT_CSMR_ROAD_TYPES_HIST
prompt =========================================
prompt
create table TB_WT_CSMR_ROAD_TYPES_HIST
(
  h_crtid            NUMBER(12) not null,
  crt_id             NUMBER(12) not null,
  crt_cs_idn         NUMBER(12) not null,
  crt_road_types     NUMBER(15) not null,
  crt_road_units     NUMBER(12,2),
  orgid              NUMBER(4) not null,
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(7) not null,
  lmoddate           DATE not null,
  updated_by         NUMBER(7),
  updated_date       DATE,
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  crt_granted        VARCHAR2(1) not null,
  crt_latest         VARCHAR2(1) not null,
  apm_application_id NUMBER(16),
  sm_service_id      NUMBER(12),
  crt_disc_type      NUMBER(12),
  crt_disc_recn_id   NUMBER(12),
  crt_road_trench    NUMBER(7,2),
  is_deleted         VARCHAR2(1) default 'N',
  h_status           VARCHAR2(1)
)
;
alter table TB_WT_CSMR_ROAD_TYPES_HIST
  add constraint PK_H_CRTID primary key (H_CRTID);

prompt
prompt Creating table TB_WT_DEMAND_NOTICE
prompt ==================================
prompt
create table TB_WT_DEMAND_NOTICE
(
  nb_noticeid    NUMBER(12) not null,
  cs_idn         NUMBER(12),
  bm_idno        NUMBER(12) not null,
  cpd_nottype    NUMBER(12) not null,
  nb_noticeno    NUMBER(10) not null,
  nb_noticedt    DATE not null,
  nb_notacceptdt DATE,
  nb_notduedt    DATE,
  nb_totcopy     NUMBER(3),
  nb_printsts    VARCHAR2(1),
  tax_code       VARCHAR2(20),
  orgid          NUMBER(8) not null,
  user_id        NUMBER(12) not null,
  lang_id        NUMBER(8) not null,
  lmoddate       DATE not null,
  updated_by     NUMBER(7),
  updated_date   DATE,
  lg_ip_mac      VARCHAR2(100),
  lg_ip_mac_upd  VARCHAR2(100),
  wt_v1          NVARCHAR2(100),
  wt_v2          NVARCHAR2(100),
  wt_v3          NVARCHAR2(100),
  wt_v4          NVARCHAR2(100),
  wt_v5          NVARCHAR2(100),
  wt_n1          NUMBER(15),
  wt_n2          NUMBER(15),
  wt_n3          NUMBER(15),
  wt_n4          NUMBER(15),
  wt_n5          NUMBER(15),
  wt_d1          DATE,
  wt_d2          DATE,
  wt_d3          DATE,
  wt_lo1         CHAR(1),
  wt_lo2         CHAR(1),
  wt_lo3         CHAR(1),
  is_deleted     CHAR(1)
)
;
comment on column TB_WT_DEMAND_NOTICE.nb_noticeid
  is 'Notice ID';
comment on column TB_WT_DEMAND_NOTICE.cs_idn
  is 'Connection Number foreign key';
comment on column TB_WT_DEMAND_NOTICE.bm_idno
  is 'Bill Id no.';
comment on column TB_WT_DEMAND_NOTICE.cpd_nottype
  is 'Notice type id';
comment on column TB_WT_DEMAND_NOTICE.nb_noticeno
  is 'Notice number';
comment on column TB_WT_DEMAND_NOTICE.nb_noticedt
  is 'Notice date';
comment on column TB_WT_DEMAND_NOTICE.nb_notacceptdt
  is 'Notice sent date';
comment on column TB_WT_DEMAND_NOTICE.nb_notduedt
  is 'Notice accept date';
comment on column TB_WT_DEMAND_NOTICE.nb_totcopy
  is 'No. of copies';
comment on column TB_WT_DEMAND_NOTICE.nb_printsts
  is 'Print Status';
comment on column TB_WT_DEMAND_NOTICE.tax_code
  is 'Apllicable Tax Code  from Tax Master';
comment on column TB_WT_DEMAND_NOTICE.orgid
  is 'Org ID';
comment on column TB_WT_DEMAND_NOTICE.user_id
  is 'User ID';
comment on column TB_WT_DEMAND_NOTICE.lang_id
  is 'Lang ID';
comment on column TB_WT_DEMAND_NOTICE.lmoddate
  is 'Last Modification Date';
comment on column TB_WT_DEMAND_NOTICE.updated_by
  is 'User id who update the data';
comment on column TB_WT_DEMAND_NOTICE.updated_date
  is 'Date on which data is going to update';
comment on column TB_WT_DEMAND_NOTICE.lg_ip_mac
  is 'Client Machine�s Login Name | IP Address | Physical Address';
comment on column TB_WT_DEMAND_NOTICE.lg_ip_mac_upd
  is 'Updated Client Machine�s Login Name | IP Address | Physical Address';
comment on column TB_WT_DEMAND_NOTICE.wt_v1
  is 'Additional nvarchar2 WT_V1 to be used in future';
comment on column TB_WT_DEMAND_NOTICE.wt_v2
  is 'Additional nvarchar2 WT_V2 to be used in future';
comment on column TB_WT_DEMAND_NOTICE.wt_v3
  is 'Additional nvarchar2 WT_V3 to be used in future';
comment on column TB_WT_DEMAND_NOTICE.wt_v4
  is 'Additional nvarchar2 WT_V4 to be used in future';
comment on column TB_WT_DEMAND_NOTICE.wt_v5
  is 'Additional nvarchar2 WT_V5 to be used in future';
comment on column TB_WT_DEMAND_NOTICE.wt_n1
  is 'Additional number WT_N1 to be used in future';
comment on column TB_WT_DEMAND_NOTICE.wt_n2
  is 'Additional number WT_N2 to be used in future';
comment on column TB_WT_DEMAND_NOTICE.wt_n3
  is 'Additional number WT_N3 to be used in future';
comment on column TB_WT_DEMAND_NOTICE.wt_n4
  is 'Additional number WT_N4 to be used in future';
comment on column TB_WT_DEMAND_NOTICE.wt_n5
  is 'Additional number WT_N5 to be used in future';
comment on column TB_WT_DEMAND_NOTICE.wt_d1
  is 'Additional Date WT_D1 to be used in future';
comment on column TB_WT_DEMAND_NOTICE.wt_d2
  is 'Additional Date WT_D2 to be used in future';
comment on column TB_WT_DEMAND_NOTICE.wt_d3
  is 'Additional Date WT_D3 to be used in future';
comment on column TB_WT_DEMAND_NOTICE.wt_lo1
  is 'Additional Logical field WT_LO1 to be used in future';
comment on column TB_WT_DEMAND_NOTICE.wt_lo2
  is 'Additional Logical field WT_LO2 to be used in future';
comment on column TB_WT_DEMAND_NOTICE.wt_lo3
  is 'Additional Logical field WT_LO3 to be used in future';
comment on column TB_WT_DEMAND_NOTICE.is_deleted
  is 'Flag for Deleted Entries.';
alter table TB_WT_DEMAND_NOTICE
  add constraint PK_WT_NOTNO primary key (NB_NOTICEID);
alter table TB_WT_DEMAND_NOTICE
  add constraint FK_TB_BILL_MAS_BMIDNO foreign key (BM_IDNO)
  references TB_WT_BILL_MAS (BM_IDNO);
alter table TB_WT_DEMAND_NOTICE
  add constraint FK_TB_TCSMR_NOTICE_CSIDN foreign key (CS_IDN)
  references TB_CSMR_INFO (CS_IDN);

prompt
prompt Creating table TB_WT_DEMAND_NOTICE_HIST
prompt =======================================
prompt
create table TB_WT_DEMAND_NOTICE_HIST
(
  h_noticeid     NUMBER(12) not null,
  nb_noticeid    NUMBER(12),
  cs_idn         NUMBER(12),
  bm_idno        NUMBER(12),
  cpd_nottype    NUMBER(12),
  nb_noticeno    NUMBER(10),
  nb_noticedt    DATE,
  nb_notacceptdt DATE,
  nb_notduedt    DATE,
  nb_totcopy     NUMBER(3),
  nb_printsts    VARCHAR2(1),
  tax_code       VARCHAR2(20),
  orgid          NUMBER(8),
  user_id        NUMBER(12),
  lang_id        NUMBER(8),
  lmoddate       DATE,
  updated_by     NUMBER(7),
  updated_date   DATE,
  lg_ip_mac      VARCHAR2(100),
  lg_ip_mac_upd  VARCHAR2(100),
  wt_v1          NVARCHAR2(100),
  wt_v2          NVARCHAR2(100),
  wt_v3          NVARCHAR2(100),
  wt_v4          NVARCHAR2(100),
  wt_v5          NVARCHAR2(100),
  wt_n1          NUMBER(15),
  wt_n2          NUMBER(15),
  wt_n3          NUMBER(15),
  wt_n4          NUMBER(15),
  wt_n5          NUMBER(15),
  wt_d1          DATE,
  wt_d2          DATE,
  wt_d3          DATE,
  wt_lo1         CHAR(1),
  wt_lo2         CHAR(1),
  wt_lo3         CHAR(1),
  is_deleted     CHAR(1),
  h_status       NVARCHAR2(1)
)
;
alter table TB_WT_DEMAND_NOTICE_HIST
  add constraint PK_H_NOTICEID primary key (H_NOTICEID);

prompt
prompt Creating table TB_WT_DISCONNECTIONS
prompt ===================================
prompt
create table TB_WT_DISCONNECTIONS
(
  disc_id            NUMBER(12),
  cs_idn             NUMBER(12),
  apm_application_id NUMBER(16),
  disc_appdate       DATE,
  disc_reason        NVARCHAR2(2000),
  disc_type          NUMBER(12),
  disc_method        NUMBER(12),
  disc_grant_flag    VARCHAR2(1),
  disc_aprvdate      DATE,
  disc_approvedby    NUMBER(7),
  disc_execdate      DATE,
  orgid              NUMBER(4) not null,
  user_id            NUMBER(7),
  lang_id            NUMBER(7),
  lmoddate           DATE,
  updated_by         NUMBER(7),
  updated_date       DATE,
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  wlb_wr_prflg       CHAR(1),
  wt_v2              NVARCHAR2(100),
  wt_v3              NVARCHAR2(100),
  wt_v4              NVARCHAR2(100),
  wt_v5              NVARCHAR2(100),
  wlb_wkno           NUMBER(12),
  wt_n2              NUMBER(15),
  wt_n3              NUMBER(15),
  wt_n4              NUMBER(15),
  wt_n5              NUMBER(15),
  wlb_wkdt           DATE,
  wt_d2              DATE,
  wt_d3              DATE,
  wt_lo1             CHAR(1),
  wt_lo2             CHAR(1),
  wt_lo3             CHAR(1),
  plum_id            NUMBER(12),
  temp_dis_from_date DATE,
  temp_dis_to_date   DATE
)
;
comment on table TB_WT_DISCONNECTIONS
  is 'This table is used to store Disconnections(Temporary/Permanent) entries.';
comment on column TB_WT_DISCONNECTIONS.disc_id
  is 'Disconnection Id';
comment on column TB_WT_DISCONNECTIONS.cs_idn
  is 'Consumer Identification Number';
comment on column TB_WT_DISCONNECTIONS.apm_application_id
  is 'Application No';
comment on column TB_WT_DISCONNECTIONS.disc_appdate
  is 'Disconnection application date';
comment on column TB_WT_DISCONNECTIONS.disc_reason
  is 'Disconnection application Reason';
comment on column TB_WT_DISCONNECTIONS.disc_type
  is 'Disconnection application Type(T/P)';
comment on column TB_WT_DISCONNECTIONS.disc_method
  is 'Disconnection application Method(By Appl / By Force)';
comment on column TB_WT_DISCONNECTIONS.disc_grant_flag
  is 'Disconnection application Granted';
comment on column TB_WT_DISCONNECTIONS.disc_aprvdate
  is 'Disconnection application Approval Date';
comment on column TB_WT_DISCONNECTIONS.disc_approvedby
  is 'Disconnection application Approvaed By';
comment on column TB_WT_DISCONNECTIONS.disc_execdate
  is 'Disconnection application Execution Date';
comment on column TB_WT_DISCONNECTIONS.orgid
  is 'Org ID';
comment on column TB_WT_DISCONNECTIONS.user_id
  is 'User ID';
comment on column TB_WT_DISCONNECTIONS.lang_id
  is 'Lang ID';
comment on column TB_WT_DISCONNECTIONS.lmoddate
  is 'Last Modification Date';
comment on column TB_WT_DISCONNECTIONS.updated_by
  is 'User id who update the data';
comment on column TB_WT_DISCONNECTIONS.updated_date
  is 'Date on which data is going to update';
comment on column TB_WT_DISCONNECTIONS.lg_ip_mac
  is 'Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_WT_DISCONNECTIONS.lg_ip_mac_upd
  is 'Updated Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_WT_DISCONNECTIONS.wlb_wr_prflg
  is 'Work order print flag';
comment on column TB_WT_DISCONNECTIONS.wt_v2
  is 'Additional nvarchar2 WT_V2 to be used in future';
comment on column TB_WT_DISCONNECTIONS.wt_v3
  is 'Additional nvarchar2 WT_V3 to be used in future';
comment on column TB_WT_DISCONNECTIONS.wt_v4
  is 'Additional nvarchar2 WT_V4 to be used in future';
comment on column TB_WT_DISCONNECTIONS.wt_v5
  is 'Additional nvarchar2 WT_V5 to be used in future';
comment on column TB_WT_DISCONNECTIONS.wlb_wkno
  is 'Work order no.';
comment on column TB_WT_DISCONNECTIONS.wt_n2
  is 'Additional number WT_N2 to be used in future';
comment on column TB_WT_DISCONNECTIONS.wt_n3
  is 'Additional number WT_N3 to be used in future';
comment on column TB_WT_DISCONNECTIONS.wt_n4
  is 'Additional number WT_N4 to be used in future';
comment on column TB_WT_DISCONNECTIONS.wt_n5
  is 'Additional number WT_N5 to be used in future';
comment on column TB_WT_DISCONNECTIONS.wlb_wkdt
  is 'Work order date';
comment on column TB_WT_DISCONNECTIONS.wt_d2
  is 'Additional Date WT_D2 to be used in future';
comment on column TB_WT_DISCONNECTIONS.wt_d3
  is 'Additional Date WT_D3 to be used in future';
comment on column TB_WT_DISCONNECTIONS.wt_lo1
  is 'Additional Logical field WT_LO1 to be used in future';
comment on column TB_WT_DISCONNECTIONS.wt_lo2
  is 'Additional Logical field WT_LO2 to be used in future';
comment on column TB_WT_DISCONNECTIONS.wt_lo3
  is 'Additional Logical field WT_LO3 to be used in future';
comment on column TB_WT_DISCONNECTIONS.plum_id
  is 'Plumber ID';
comment on column TB_WT_DISCONNECTIONS.temp_dis_from_date
  is 'temporary disconnection from date';
comment on column TB_WT_DISCONNECTIONS.temp_dis_to_date
  is 'temporary disconnection to date';
alter table TB_WT_DISCONNECTIONS
  add constraint PK_DISC_ID_ORGID primary key (DISC_ID, ORGID);
alter table TB_WT_DISCONNECTIONS
  add constraint FK_CSIDN_CINF foreign key (CS_IDN)
  references TB_CSMR_INFO (CS_IDN);
alter table TB_WT_DISCONNECTIONS
  add constraint CK_DISC_GRANT_FLAG
  check (DISC_GRANT_FLAG in ('Y','N'));
alter table TB_WT_DISCONNECTIONS
  add constraint NN_DISC_CS_IDN
  check ("CS_IDN" IS NOT NULL);
alter table TB_WT_DISCONNECTIONS
  add constraint NN_DISC_ID
  check ("DISC_ID" IS NOT NULL);
alter table TB_WT_DISCONNECTIONS
  add constraint NN_DISC_METHOD
  check ("DISC_METHOD" IS NOT NULL);

prompt
prompt Creating table TB_WT_DISCONNECTIONS_HIST
prompt ========================================
prompt
create table TB_WT_DISCONNECTIONS_HIST
(
  h_discid           NUMBER(12) not null,
  disc_id            NUMBER(12),
  cs_idn             NUMBER(12),
  apm_application_id NUMBER(16),
  disc_appdate       DATE,
  disc_reason        NVARCHAR2(2000),
  disc_type          NUMBER(12),
  disc_method        NUMBER(12),
  disc_grant_flag    VARCHAR2(1),
  disc_aprvdate      DATE,
  disc_approvedby    NUMBER(7),
  disc_execdate      DATE,
  orgid              NUMBER(4) not null,
  user_id            NUMBER(7),
  lang_id            NUMBER(7),
  lmoddate           DATE,
  updated_by         NUMBER(7),
  updated_date       DATE,
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  wlb_wr_prflg       CHAR(1),
  wt_v2              NVARCHAR2(100),
  wt_v3              NVARCHAR2(100),
  wt_v4              NVARCHAR2(100),
  wt_v5              NVARCHAR2(100),
  wlb_wkno           NUMBER(12),
  wt_n2              NUMBER(15),
  wt_n3              NUMBER(15),
  wt_n4              NUMBER(15),
  wt_n5              NUMBER(15),
  wlb_wkdt           DATE,
  wt_d2              DATE,
  wt_d3              DATE,
  wt_lo1             CHAR(1),
  wt_lo2             CHAR(1),
  wt_lo3             CHAR(1),
  plum_id            NUMBER(12),
  temp_dis_from_date DATE,
  temp_dis_to_date   DATE,
  h_status           NVARCHAR2(1)
)
;
alter table TB_WT_DISCONNECTIONS_HIST
  add constraint PK_H_DISCID primary key (H_DISCID);

prompt
prompt Creating table TB_WT_EXCESS_AMT
prompt ===============================
prompt
create table TB_WT_EXCESS_AMT
(
  excess_id     NUMBER(12) not null,
  cs_idn        NUMBER(12),
  rm_rcptid     NUMBER(12),
  exc_amt       NUMBER(12,2),
  adj_amt       NUMBER(12,2),
  excadj_flag   CHAR(1),
  excess_dis    VARCHAR2(1),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  lmoddate      DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  wt_v1         NVARCHAR2(100),
  wt_n1         NUMBER(15),
  wt_d1         DATE,
  wt_lo1        CHAR(1),
  tax_id        NUMBER(12)
)
;
comment on column TB_WT_EXCESS_AMT.excess_id
  is 'Primary Key of this table';
comment on column TB_WT_EXCESS_AMT.cs_idn
  is 'Connection Number of tb_csmr_info';
comment on column TB_WT_EXCESS_AMT.rm_rcptid
  is 'receptTaxof tb_wt_rcpt_mas';
comment on column TB_WT_EXCESS_AMT.exc_amt
  is 'excess amount';
comment on column TB_WT_EXCESS_AMT.adj_amt
  is 'addjusted amount';
comment on column TB_WT_EXCESS_AMT.excadj_flag
  is 'Excess or adjustment flag';
comment on column TB_WT_EXCESS_AMT.excess_dis
  is 'Excess dishonour flag';
comment on column TB_WT_EXCESS_AMT.orgid
  is 'Org ID';
comment on column TB_WT_EXCESS_AMT.user_id
  is 'User ID';
comment on column TB_WT_EXCESS_AMT.lang_id
  is 'Lang ID';
comment on column TB_WT_EXCESS_AMT.lmoddate
  is 'Last Modification Date';
comment on column TB_WT_EXCESS_AMT.updated_by
  is 'User id who update the data';
comment on column TB_WT_EXCESS_AMT.updated_date
  is 'Date on which data is going to update';
comment on column TB_WT_EXCESS_AMT.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_WT_EXCESS_AMT.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_WT_EXCESS_AMT.wt_v1
  is 'Additional nvarchar2 WT_V1 to be used in future';
comment on column TB_WT_EXCESS_AMT.wt_n1
  is 'Additional number WT_N1 to be used in future';
comment on column TB_WT_EXCESS_AMT.wt_d1
  is 'Additional Date WT_D1 to be used in future';
comment on column TB_WT_EXCESS_AMT.wt_lo1
  is 'Additional Logical field WT_LO1 to be used in future';
comment on column TB_WT_EXCESS_AMT.tax_id
  is 'tax master taxId for advance payment';
alter table TB_WT_EXCESS_AMT
  add constraint PK_WT_EXCESS_ID_ORGID primary key (EXCESS_ID);

prompt
prompt Creating table TB_WT_EXCESS_AMT_HIST
prompt ====================================
prompt
create table TB_WT_EXCESS_AMT_HIST
(
  h_excess_id   NUMBER(12) not null,
  excess_id     NUMBER(12) not null,
  cs_idn        NUMBER(12),
  rm_rcptid     NUMBER(12),
  exc_amt       NUMBER(12,2),
  adj_amt       NUMBER(12,2),
  excadj_flag   CHAR(1),
  excess_dis    VARCHAR2(1),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  lmoddate      DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  wt_v1         NVARCHAR2(100),
  wt_n1         NUMBER(15),
  wt_d1         DATE,
  wt_lo1        CHAR(1),
  tax_id        NUMBER(12),
  h_status      NVARCHAR2(1)
)
;
comment on column TB_WT_EXCESS_AMT_HIST.h_excess_id
  is 'Primary Key of this table';
comment on column TB_WT_EXCESS_AMT_HIST.excess_id
  is 'Primary Key of TB_WT_EXCESS_AMT table';
comment on column TB_WT_EXCESS_AMT_HIST.cs_idn
  is 'Connection Number of tb_csmr_info';
comment on column TB_WT_EXCESS_AMT_HIST.rm_rcptid
  is 'receptTaxof tb_wt_rcpt_mas';
comment on column TB_WT_EXCESS_AMT_HIST.exc_amt
  is 'excess amount';
comment on column TB_WT_EXCESS_AMT_HIST.adj_amt
  is 'addjusted amount';
comment on column TB_WT_EXCESS_AMT_HIST.excadj_flag
  is 'Excess or adjustment flag';
comment on column TB_WT_EXCESS_AMT_HIST.excess_dis
  is 'Excess dishonour flag';
comment on column TB_WT_EXCESS_AMT_HIST.orgid
  is 'Org ID';
comment on column TB_WT_EXCESS_AMT_HIST.user_id
  is 'User ID';
comment on column TB_WT_EXCESS_AMT_HIST.lang_id
  is 'Lang ID';
comment on column TB_WT_EXCESS_AMT_HIST.lmoddate
  is 'Last Modification Date';
comment on column TB_WT_EXCESS_AMT_HIST.updated_by
  is 'User id who update the data';
comment on column TB_WT_EXCESS_AMT_HIST.updated_date
  is 'Date on which data is going to update';
comment on column TB_WT_EXCESS_AMT_HIST.lg_ip_mac
  is 'Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_WT_EXCESS_AMT_HIST.lg_ip_mac_upd
  is 'Updated Client Machine?s Login Name | IP Address | Physical Address';
comment on column TB_WT_EXCESS_AMT_HIST.wt_v1
  is 'Additional nvarchar2 WT_V1 to be used in future';
comment on column TB_WT_EXCESS_AMT_HIST.wt_n1
  is 'Additional number WT_N1 to be used in future';
comment on column TB_WT_EXCESS_AMT_HIST.wt_d1
  is 'Additional Date WT_D1 to be used in future';
comment on column TB_WT_EXCESS_AMT_HIST.wt_lo1
  is 'Additional Logical field WT_LO1 to be used in future';
comment on column TB_WT_EXCESS_AMT_HIST.tax_id
  is 'tax master taxId for advance payment';
alter table TB_WT_EXCESS_AMT_HIST
  add constraint PK_WT_EXCESS_ID_HIST_ORGID primary key (H_EXCESS_ID);

prompt
prompt Creating table TB_WT_INST_CSMP
prompt ==============================
prompt
create table TB_WT_INST_CSMP
(
  inst_id          NUMBER(12) not null,
  inst_frmdt       DATE,
  inst_todt        DATE,
  inst_type        VARCHAR2(300),
  inst_lit_per_day NUMBER,
  inst_flag        CHAR(1),
  orgid            NUMBER(4) not null,
  user_id          NUMBER(7) not null,
  lang_id          NUMBER(7) not null,
  lmoddate         DATE not null,
  updated_by       NUMBER(7),
  updated_date     DATE,
  lg_ip_mac        VARCHAR2(100),
  lg_ip_mac_upd    VARCHAR2(100),
  wt_v1            NVARCHAR2(100),
  wt_v2            NVARCHAR2(100),
  wt_v3            NVARCHAR2(100),
  wt_v4            NVARCHAR2(100),
  wt_v5            NVARCHAR2(100),
  wt_n1            NUMBER(15),
  wt_n2            NUMBER(15),
  wt_n3            NUMBER(15),
  wt_n4            NUMBER(15),
  wt_n5            NUMBER(15),
  wt_d1            DATE,
  wt_d2            DATE,
  wt_d3            DATE,
  wt_lo1           CHAR(1),
  wt_lo2           CHAR(1),
  wt_lo3           CHAR(1)
)
;
comment on table TB_WT_INST_CSMP
  is 'Table for Institute Wise Consumption';
comment on column TB_WT_INST_CSMP.inst_id
  is 'Primary Key';
comment on column TB_WT_INST_CSMP.inst_frmdt
  is 'Form Date';
comment on column TB_WT_INST_CSMP.inst_todt
  is 'To Date';
comment on column TB_WT_INST_CSMP.inst_type
  is 'Institutions type like  Hotel,Hospital etc.';
comment on column TB_WT_INST_CSMP.inst_lit_per_day
  is 'Institutions Consumption Liter per head per day';
comment on column TB_WT_INST_CSMP.inst_flag
  is 'Active or Inactive Flag';
comment on column TB_WT_INST_CSMP.orgid
  is 'Organization Id';
comment on column TB_WT_INST_CSMP.user_id
  is 'User id';
comment on column TB_WT_INST_CSMP.lang_id
  is 'Language id';
comment on column TB_WT_INST_CSMP.lmoddate
  is 'Creation Date';
comment on column TB_WT_INST_CSMP.updated_by
  is 'Last Modification by';
comment on column TB_WT_INST_CSMP.updated_date
  is 'Last Modification Date';
comment on column TB_WT_INST_CSMP.lg_ip_mac
  is 'Store IP adress';
comment on column TB_WT_INST_CSMP.lg_ip_mac_upd
  is 'Store  updated IP adress';
comment on column TB_WT_INST_CSMP.wt_v1
  is 'Additional nvarchar2 WT_V1 to be used in future';
comment on column TB_WT_INST_CSMP.wt_v2
  is 'Additional nvarchar2 WT_V2 to be used in future';
comment on column TB_WT_INST_CSMP.wt_v3
  is 'Additional nvarchar2 WT_V3 to be used in future';
comment on column TB_WT_INST_CSMP.wt_v4
  is 'Additional nvarchar2 WT_V4 to be used in future';
comment on column TB_WT_INST_CSMP.wt_v5
  is 'Additional nvarchar2 WT_V5 to be used in future';
comment on column TB_WT_INST_CSMP.wt_n1
  is 'Additional number WT_N1 to be used in future';
comment on column TB_WT_INST_CSMP.wt_n2
  is 'Additional number WT_N2 to be used in future';
comment on column TB_WT_INST_CSMP.wt_n3
  is 'Additional number WT_N3 to be used in future';
comment on column TB_WT_INST_CSMP.wt_n4
  is 'Additional number WT_N4 to be used in future';
comment on column TB_WT_INST_CSMP.wt_n5
  is 'Additional number WT_N5 to be used in future';
comment on column TB_WT_INST_CSMP.wt_d1
  is 'Additional Date WT_D1 to be used in future';
comment on column TB_WT_INST_CSMP.wt_d2
  is 'Additional Date WT_D2 to be used in future';
comment on column TB_WT_INST_CSMP.wt_d3
  is 'Additional Date WT_D3 to be used in future';
comment on column TB_WT_INST_CSMP.wt_lo1
  is 'Additional Logical field WT_LO1 to be used in future';
comment on column TB_WT_INST_CSMP.wt_lo2
  is 'Additional Logical field WT_LO2 to be used in future';
comment on column TB_WT_INST_CSMP.wt_lo3
  is 'Additional Logical field WT_LO3 to be used in future';
alter table TB_WT_INST_CSMP
  add constraint PK_INST_ID primary key (INST_ID);

prompt
prompt Creating table TB_WT_INST_CSMP_HIST
prompt ===================================
prompt
create table TB_WT_INST_CSMP_HIST
(
  h_instid         NUMBER(12) not null,
  inst_id          NUMBER(12) not null,
  inst_frmdt       DATE,
  inst_todt        DATE,
  inst_type        VARCHAR2(300),
  inst_lit_per_day NUMBER,
  inst_flag        CHAR(1),
  orgid            NUMBER(4) not null,
  user_id          NUMBER(7) not null,
  lang_id          NUMBER(7) not null,
  lmoddate         DATE not null,
  updated_by       NUMBER(7),
  updated_date     DATE,
  lg_ip_mac        VARCHAR2(100),
  lg_ip_mac_upd    VARCHAR2(100),
  wt_v1            NVARCHAR2(100),
  wt_v2            NVARCHAR2(100),
  wt_v3            NVARCHAR2(100),
  wt_v4            NVARCHAR2(100),
  wt_v5            NVARCHAR2(100),
  wt_n1            NUMBER(15),
  wt_n2            NUMBER(15),
  wt_n3            NUMBER(15),
  wt_n4            NUMBER(15),
  wt_n5            NUMBER(15),
  wt_d1            DATE,
  wt_d2            DATE,
  wt_d3            DATE,
  wt_lo1           CHAR(1),
  wt_lo2           CHAR(1),
  wt_lo3           CHAR(1),
  h_status         NVARCHAR2(1)
)
;
alter table TB_WT_INST_CSMP_HIST
  add constraint PK_H_INSTID primary key (H_INSTID);

prompt
prompt Creating table TB_WT_INTEREST_DET
prompt =================================
prompt
create table TB_WT_INTEREST_DET
(
  in_id                 NUMBER(12) not null,
  bm_idno               NUMBER(12) not null,
  cs_idn                NUMBER(12),
  bm_tax_amt            NUMBER(12,2),
  int_perc              NUMBER(12,2),
  bill_int_amt          NUMBER(12,2),
  col_int_amt           NUMBER(12,2),
  cum_int_amt           NUMBER(12,2),
  process_date          DATE not null,
  int_from              DATE,
  int_to                DATE,
  fyi_int_arrears       NUMBER(12,2),
  fyi_int_perc          NUMBER(12,2),
  nxt_prc_date          DATE,
  bd_billdetid          NUMBER(12) not null,
  tm_taxid              NUMBER(12) not null,
  bd_bal_tax_amt        NUMBER(12,2),
  rm_rcptid             NUMBER(12),
  ch_shd_flag           CHAR(1) default 'N',
  ch_tran_type          CHAR(1),
  ch_int_to_be_billed   CHAR(1),
  cpd_interest_type_val NVARCHAR2(200),
  ch_meteredccn         CHAR(1),
  orgid                 NUMBER(7) not null,
  user_id               NUMBER(5) not null,
  lang_id               NUMBER(5) not null,
  lmoddate              DATE not null,
  updated_by            NUMBER(5),
  updated_date          DATE,
  lg_ip_mac             VARCHAR2(100),
  lg_ip_mac_upd         VARCHAR2(100)
)
;
comment on table TB_WT_INTEREST_DET
  is 'Interest Detail Table.';
comment on column TB_WT_INTEREST_DET.in_id
  is 'Interest Detail Identification Number';
comment on column TB_WT_INTEREST_DET.bm_idno
  is 'Bill Id. (bm_idno from table : tb_wt_bill_mas)';
comment on column TB_WT_INTEREST_DET.cs_idn
  is 'Consumer Identification Number';
comment on column TB_WT_INTEREST_DET.int_perc
  is 'Interest Percentage.';
comment on column TB_WT_INTEREST_DET.process_date
  is 'Interest Processed(Calculated) date.';
comment on column TB_WT_INTEREST_DET.nxt_prc_date
  is 'Next process date';
comment on column TB_WT_INTEREST_DET.bd_billdetid
  is 'Bill detail id. (bd_billdetid from table : tb_wt_bill_det)';
comment on column TB_WT_INTEREST_DET.tm_taxid
  is 'Tax Id. (tm_taxid from table : tb_wt_tax_mast)';
comment on column TB_WT_INTEREST_DET.bd_bal_tax_amt
  is 'Balance Tax amount at the time of calculating interest.';
comment on column TB_WT_INTEREST_DET.ch_tran_type
  is 'Transaction type. Stores ''R'' if interest is applied at the time of receipt, ''B'' if interest is applied at the time of bill generation.';
comment on column TB_WT_INTEREST_DET.ch_int_to_be_billed
  is 'Flag to indicate wether interest is to be billed or not. Stores null if interest is not to be billed. ''Y'' if it is pending to be billed. ''N'' if is billed.';
comment on column TB_WT_INTEREST_DET.cpd_interest_type_val
  is 'Flag to indicate interest calculation method. Stores cpd_value for ICM prefix setting.';
comment on column TB_WT_INTEREST_DET.ch_meteredccn
  is 'Stores value for Meter => Y or Non Metered => N';
comment on column TB_WT_INTEREST_DET.orgid
  is 'Organisation ID';
comment on column TB_WT_INTEREST_DET.user_id
  is 'User ID';
comment on column TB_WT_INTEREST_DET.lang_id
  is 'Language ID';
comment on column TB_WT_INTEREST_DET.lmoddate
  is 'Record Creation Date';
comment on column TB_WT_INTEREST_DET.updated_by
  is 'User id who updated the data';
comment on column TB_WT_INTEREST_DET.updated_date
  is 'Date on which data has been updated';
comment on column TB_WT_INTEREST_DET.lg_ip_mac
  is 'Client Machines Login Name | IP Address | Physical Address';
comment on column TB_WT_INTEREST_DET.lg_ip_mac_upd
  is 'Updated Client Machines Login Name | IP Address | Physical Address';
alter table TB_WT_INTEREST_DET
  add constraint PK_IN_ID primary key (IN_ID);
alter table TB_WT_INTEREST_DET
  add constraint FK_INT_BILLDETID foreign key (BD_BILLDETID)
  references TB_WT_BILL_DET (BD_BILLDETID);
alter table TB_WT_INTEREST_DET
  add constraint FK_INT_BMIDNO foreign key (BM_IDNO)
  references TB_WT_BILL_MAS (BM_IDNO);

prompt
prompt Creating table TB_WT_INTEREST_DET1
prompt ==================================
prompt
create table TB_WT_INTEREST_DET1
(
  in_id         NUMBER(12) not null,
  orgid         NUMBER(7) not null,
  cs_idn        NUMBER(12),
  bm_idno       NUMBER(12),
  tm_taxid      NUMBER(12) not null,
  int_amt       NUMBER(12,2),
  process_date  DATE not null,
  user_id       NUMBER(5) not null,
  lang_id       NUMBER(5) not null,
  lmoddate      DATE not null,
  updated_by    NUMBER(5),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  flag_jv_post  CHAR(1) default 'N',
  ch_tran_type  CHAR(1),
  cpd_int_flag  NUMBER(12),
  ch_meteredccn CHAR(1)
)
;
comment on table TB_WT_INTEREST_DET1
  is 'Interest Detail Table. Stores interest updation made to bill table.';
comment on column TB_WT_INTEREST_DET1.in_id
  is 'Interest Detail Identification Number';
comment on column TB_WT_INTEREST_DET1.orgid
  is 'Organisation ID';
comment on column TB_WT_INTEREST_DET1.cs_idn
  is 'Consumer Identification Number';
comment on column TB_WT_INTEREST_DET1.bm_idno
  is 'Bill Id. (bm_idno from table : tb_wt_bill_mas)';
comment on column TB_WT_INTEREST_DET1.tm_taxid
  is 'Tax Id. (tm_taxid from table : tb_wt_tax_mast)';
comment on column TB_WT_INTEREST_DET1.int_amt
  is 'Interest Amount';
comment on column TB_WT_INTEREST_DET1.user_id
  is 'User ID';
comment on column TB_WT_INTEREST_DET1.lang_id
  is 'Language ID';
comment on column TB_WT_INTEREST_DET1.lmoddate
  is 'Record Creation Date';
comment on column TB_WT_INTEREST_DET1.updated_by
  is 'User id who updated the data';
comment on column TB_WT_INTEREST_DET1.updated_date
  is 'Date on which data has been updated';
comment on column TB_WT_INTEREST_DET1.lg_ip_mac
  is 'Client Machines Login Name | IP Address | Physical Address';
comment on column TB_WT_INTEREST_DET1.lg_ip_mac_upd
  is 'Updated Client Machines Login Name | IP Address | Physical Address';
comment on column TB_WT_INTEREST_DET1.flag_jv_post
  is 'Stores ''Y'' if JV posting is done in account, else ''N''.';
comment on column TB_WT_INTEREST_DET1.ch_tran_type
  is 'Stores flag which indicates that during which transaction interest was calculated.  ''B'' for Bill Generation and ''R'' for Receipt.';
comment on column TB_WT_INTEREST_DET1.cpd_int_flag
  is 'Flag to indicate interest calculation method. Stores cpd_id for ICM prefix setting.';
comment on column TB_WT_INTEREST_DET1.ch_meteredccn
  is 'Stores value for Meter => Y or Non Metered => N';
alter table TB_WT_INTEREST_DET1
  add constraint PK_INT_DET1_IN_ID_ORGID primary key (IN_ID, ORGID);
alter table TB_WT_INTEREST_DET1
  add constraint FK_INT_DET1_CPD_INTFLAG foreign key (CPD_INT_FLAG)
  references TB_COMPARAM_DET (CPD_ID);

prompt
prompt Creating table TB_WT_INTEREST_DET_HIST
prompt ======================================
prompt
create table TB_WT_INTEREST_DET_HIST
(
  h_inid                NUMBER(12) not null,
  in_id                 NUMBER(12) not null,
  bm_idno               NUMBER(12) not null,
  cs_idn                NUMBER(12),
  bm_tax_amt            NUMBER(12,2),
  int_perc              NUMBER(12,2),
  bill_int_amt          NUMBER(12,2),
  col_int_amt           NUMBER(12,2),
  cum_int_amt           NUMBER(12,2),
  process_date          DATE not null,
  int_from              DATE,
  int_to                DATE,
  fyi_int_arrears       NUMBER(12,2),
  fyi_int_perc          NUMBER(12,2),
  nxt_prc_date          DATE,
  bd_billdetid          NUMBER(12) not null,
  tm_taxid              NUMBER(12) not null,
  bd_bal_tax_amt        NUMBER(12,2),
  rm_rcptid             NUMBER(12),
  ch_shd_flag           CHAR(1) default 'N',
  ch_tran_type          CHAR(1),
  ch_int_to_be_billed   CHAR(1),
  cpd_interest_type_val NVARCHAR2(200),
  ch_meteredccn         CHAR(1),
  orgid                 NUMBER(7) not null,
  user_id               NUMBER(5) not null,
  lang_id               NUMBER(5) not null,
  lmoddate              DATE not null,
  updated_by            NUMBER(5),
  updated_date          DATE,
  lg_ip_mac             VARCHAR2(100),
  lg_ip_mac_upd         VARCHAR2(100),
  h_status              NVARCHAR2(1)
)
;
alter table TB_WT_INTEREST_DET_HIST
  add constraint PK_H_INID primary key (H_INID);

prompt
prompt Creating table TB_WT_METER_CUT_RESTORATION
prompt ==========================================
prompt
create table TB_WT_METER_CUT_RESTORATION
(
  mcr_id             NUMBER(12) not null,
  mm_mtnid           NUMBER(12),
  mm_cut_res_date    DATE,
  mm_cut_res_read    NUMBER(12),
  mm_cut_res_remark  NVARCHAR2(100),
  mm_cut_res_flag    CHAR(1),
  mm_bulk_entry_flag CHAR(1),
  plum_id            NUMBER(12),
  cs_idn             NUMBER(12),
  mm_wt_supply       VARCHAR2(1),
  mm_entry_flag      VARCHAR2(1),
  mm_ported          VARCHAR2(1),
  remark             VARCHAR2(200),
  orgid              NUMBER(4) not null,
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(7) not null,
  lmoddate           DATE not null,
  updated_by         NUMBER(7),
  updated_date       DATE,
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  wt_v3              NVARCHAR2(100),
  wt_v4              NVARCHAR2(100),
  wt_v5              NVARCHAR2(100),
  wt_n1              NUMBER(15),
  wt_n2              NUMBER(15),
  wt_n3              NUMBER(15),
  wt_n4              NUMBER(15),
  wt_n5              NUMBER(15),
  wt_d1              DATE,
  wt_d2              DATE,
  wt_d3              DATE,
  wt_lo1             CHAR(1),
  wt_lo2             CHAR(1),
  wt_lo3             CHAR(1)
)
;
comment on table TB_WT_METER_CUT_RESTORATION
  is 'This table is used to store history of cut and restoration.';
comment on column TB_WT_METER_CUT_RESTORATION.mcr_id
  is 'Cut off or restoration flag';
comment on column TB_WT_METER_CUT_RESTORATION.mm_mtnid
  is 'Meter maintenance Id.';
comment on column TB_WT_METER_CUT_RESTORATION.mm_cut_res_date
  is 'Cut off or restoration date';
comment on column TB_WT_METER_CUT_RESTORATION.mm_cut_res_read
  is 'Cut off or restoration reading';
comment on column TB_WT_METER_CUT_RESTORATION.mm_cut_res_remark
  is 'Cut off or restoration remark';
comment on column TB_WT_METER_CUT_RESTORATION.mm_cut_res_flag
  is 'Cut off or restoration flag';
comment on column TB_WT_METER_CUT_RESTORATION.mm_bulk_entry_flag
  is 'Indicates whether Data  entered through Bulk Data Entry';
comment on column TB_WT_METER_CUT_RESTORATION.plum_id
  is 'Plumber Id.';
comment on column TB_WT_METER_CUT_RESTORATION.cs_idn
  is 'Connection Id no';
comment on column TB_WT_METER_CUT_RESTORATION.mm_wt_supply
  is 'Water supply conninue or not';
comment on column TB_WT_METER_CUT_RESTORATION.mm_entry_flag
  is 'D-Data created through Data entry form, U-Data created through upload';
comment on column TB_WT_METER_CUT_RESTORATION.mm_ported
  is 'Y-Data created thru Data entry form or Data Upload';
comment on column TB_WT_METER_CUT_RESTORATION.orgid
  is 'Org ID';
comment on column TB_WT_METER_CUT_RESTORATION.user_id
  is 'User ID';
comment on column TB_WT_METER_CUT_RESTORATION.lang_id
  is 'Lang ID';
comment on column TB_WT_METER_CUT_RESTORATION.lmoddate
  is 'Last Modification Date';
comment on column TB_WT_METER_CUT_RESTORATION.updated_by
  is 'User id who update the data';
comment on column TB_WT_METER_CUT_RESTORATION.updated_date
  is 'Date on which data is going to update';
comment on column TB_WT_METER_CUT_RESTORATION.lg_ip_mac
  is 'stores ip information';
comment on column TB_WT_METER_CUT_RESTORATION.lg_ip_mac_upd
  is 'stores ip information';
comment on column TB_WT_METER_CUT_RESTORATION.wt_v3
  is 'Additional nvarchar2 WT_V3 to be used in future';
comment on column TB_WT_METER_CUT_RESTORATION.wt_v4
  is 'Additional nvarchar2 WT_V4 to be used in future';
comment on column TB_WT_METER_CUT_RESTORATION.wt_v5
  is 'Additional nvarchar2 WT_V5 to be used in future';
comment on column TB_WT_METER_CUT_RESTORATION.wt_n1
  is 'Additional number WT_N1 to be used in future';
comment on column TB_WT_METER_CUT_RESTORATION.wt_n2
  is 'Additional number WT_N2 to be used in future';
comment on column TB_WT_METER_CUT_RESTORATION.wt_n3
  is 'Additional number WT_N3 to be used in future';
comment on column TB_WT_METER_CUT_RESTORATION.wt_n4
  is 'Additional number WT_N4 to be used in future';
comment on column TB_WT_METER_CUT_RESTORATION.wt_n5
  is 'Additional number WT_N5 to be used in future';
comment on column TB_WT_METER_CUT_RESTORATION.wt_d1
  is 'Additional Date WT_D1 to be used in future';
comment on column TB_WT_METER_CUT_RESTORATION.wt_d2
  is 'Additional Date WT_D2 to be used in future';
comment on column TB_WT_METER_CUT_RESTORATION.wt_d3
  is 'Additional Date WT_D3 to be used in future';
comment on column TB_WT_METER_CUT_RESTORATION.wt_lo1
  is 'Additional Logical field WT_LO1 to be used in future';
comment on column TB_WT_METER_CUT_RESTORATION.wt_lo2
  is 'Additional Logical field WT_LO2 to be used in future';
comment on column TB_WT_METER_CUT_RESTORATION.wt_lo3
  is 'Additional Logical field WT_LO3 to be used in future';
alter table TB_WT_METER_CUT_RESTORATION
  add constraint PK_MCR_ID_PK primary key (MCR_ID);
alter table TB_WT_METER_CUT_RESTORATION
  add constraint FK_CSIDN_TBCSMR foreign key (CS_IDN)
  references TB_CSMR_INFO (CS_IDN);
alter table TB_WT_METER_CUT_RESTORATION
  add constraint CK_HMC_ENTRY_FLG
  check (MM_ENTRY_FLAG IN ('D','U'));
alter table TB_WT_METER_CUT_RESTORATION
  add constraint CK_MMM_PORTED
  check (MM_PORTED IN ('Y','N'));
alter table TB_WT_METER_CUT_RESTORATION
  add check (MM_CUT_RES_FLAG IN('C','R'));

prompt
prompt Creating table TB_WT_METER_CUT_RESTOR_HIST
prompt ==========================================
prompt
create table TB_WT_METER_CUT_RESTOR_HIST
(
  h_mcrid            NUMBER(12) not null,
  mcr_id             NUMBER(12) not null,
  mm_mtnid           NUMBER(12),
  mm_cut_res_date    DATE,
  mm_cut_res_read    NUMBER(12),
  mm_cut_res_remark  NVARCHAR2(100),
  mm_cut_res_flag    CHAR(1),
  orgid              NUMBER(4) not null,
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(7) not null,
  lmoddate           DATE not null,
  updated_by         NUMBER(7),
  updated_date       DATE,
  cs_idn             NUMBER(12),
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  mm_wt_supply       VARCHAR2(1),
  mm_entry_flag      VARCHAR2(1),
  mm_ported          VARCHAR2(1),
  wt_v3              NVARCHAR2(100),
  wt_v4              NVARCHAR2(100),
  wt_v5              NVARCHAR2(100),
  wt_n1              NUMBER(15),
  wt_n2              NUMBER(15),
  wt_n3              NUMBER(15),
  wt_n4              NUMBER(15),
  wt_n5              NUMBER(15),
  wt_d1              DATE,
  wt_d2              DATE,
  wt_d3              DATE,
  wt_lo1             CHAR(1),
  wt_lo2             CHAR(1),
  wt_lo3             CHAR(1),
  mm_bulk_entry_flag CHAR(1),
  plum_id            NUMBER(12),
  h_status           NVARCHAR2(1)
)
;
alter table TB_WT_METER_CUT_RESTOR_HIST
  add constraint PK_H_MCRID primary key (H_MCRID);

prompt
prompt Creating table TB_WT_NODUES
prompt ===========================
prompt
create table TB_WT_NODUES
(
  ca_id         NUMBER(16) not null,
  cs_idn        NUMBER(12) not null,
  ca_noticeno   NUMBER(12),
  ca_copies     NUMBER(3),
  ca_water_due  NUMBER(12,2),
  ca_propdues   NUMBER(12,2),
  rm_amount     NUMBER(12,2),
  rm_rcptid     NUMBER(12),
  from_yr       VARCHAR2(4),
  to_yr         VARCHAR2(4),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  lmoddate      DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  pr_flag       VARCHAR2(1),
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  wt_v1         NVARCHAR2(100),
  wt_v2         NVARCHAR2(100),
  wt_v3         NVARCHAR2(100),
  wt_v4         NVARCHAR2(100),
  wt_v5         NVARCHAR2(100),
  wt_n1         NUMBER(15),
  wt_n2         NUMBER(15),
  wt_n3         NUMBER(15),
  wt_n4         NUMBER(15),
  wt_n5         NUMBER(15),
  wt_d1         DATE,
  wt_d2         DATE,
  wt_d3         DATE,
  wt_lo1        CHAR(1),
  wt_lo2        CHAR(1),
  wt_lo3        CHAR(1)
)
;
comment on column TB_WT_NODUES.ca_id
  is 'Generated ID';
comment on column TB_WT_NODUES.cs_idn
  is 'ID from TB_CSMR_INFO';
comment on column TB_WT_NODUES.ca_noticeno
  is 'Notice no';
comment on column TB_WT_NODUES.ca_copies
  is 'No of copies';
comment on column TB_WT_NODUES.ca_water_due
  is 'for water dues';
comment on column TB_WT_NODUES.ca_propdues
  is 'for for property dues';
comment on column TB_WT_NODUES.rm_amount
  is 'Receipt amount';
comment on column TB_WT_NODUES.rm_rcptid
  is 'Receipt id';
comment on column TB_WT_NODUES.from_yr
  is 'From year';
comment on column TB_WT_NODUES.to_yr
  is 'To year';
comment on column TB_WT_NODUES.orgid
  is 'Org ID';
comment on column TB_WT_NODUES.user_id
  is 'User ID';
comment on column TB_WT_NODUES.lang_id
  is 'Lang ID';
comment on column TB_WT_NODUES.lmoddate
  is 'Last Modification Date';
comment on column TB_WT_NODUES.updated_by
  is 'User id who update the data';
comment on column TB_WT_NODUES.updated_date
  is 'Date on which data is going to update';
comment on column TB_WT_NODUES.pr_flag
  is 'Print flag ';
comment on column TB_WT_NODUES.lg_ip_mac
  is 'Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_WT_NODUES.lg_ip_mac_upd
  is 'Updated Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_WT_NODUES.wt_v1
  is 'Additional nvarchar2 WT_V1 to be used in future';
comment on column TB_WT_NODUES.wt_v2
  is 'Additional nvarchar2 WT_V2 to be used in future';
comment on column TB_WT_NODUES.wt_v3
  is 'Additional nvarchar2 WT_V3 to be used in future';
comment on column TB_WT_NODUES.wt_v4
  is 'Additional nvarchar2 WT_V4 to be used in future';
comment on column TB_WT_NODUES.wt_v5
  is 'Additional nvarchar2 WT_V5 to be used in future';
comment on column TB_WT_NODUES.wt_n1
  is 'Additional number WT_N1 to be used in future';
comment on column TB_WT_NODUES.wt_n2
  is 'Additional number WT_N2 to be used in future';
comment on column TB_WT_NODUES.wt_n3
  is 'Additional number WT_N3 to be used in future';
comment on column TB_WT_NODUES.wt_n4
  is 'Additional number WT_N4 to be used in future';
comment on column TB_WT_NODUES.wt_n5
  is 'Additional number WT_N5 to be used in future';
comment on column TB_WT_NODUES.wt_d1
  is 'Additional Date WT_D1 to be used in future';
comment on column TB_WT_NODUES.wt_d2
  is 'Additional Date WT_D2 to be used in future';
comment on column TB_WT_NODUES.wt_d3
  is 'Additional Date WT_D3 to be used in future';
comment on column TB_WT_NODUES.wt_lo1
  is 'Additional Logical field WT_LO1 to be used in future';
comment on column TB_WT_NODUES.wt_lo2
  is 'Additional Logical field WT_LO2 to be used in future';
comment on column TB_WT_NODUES.wt_lo3
  is 'Additional Logical field WT_LO3 to be used in future';
alter table TB_WT_NODUES
  add constraint PK_CA_APPLNID primary key (CA_ID);

prompt
prompt Creating table TB_WT_NODUES_HIST
prompt ================================
prompt
create table TB_WT_NODUES_HIST
(
  h_caid        NUMBER(16) not null,
  ca_id         NUMBER(16) not null,
  cs_idn        NUMBER(12) not null,
  ca_noticeno   NUMBER(12),
  ca_copies     NUMBER(3),
  ca_water_due  NUMBER(12,2),
  ca_propdues   NUMBER(12,2),
  rm_amount     NUMBER(12,2),
  rm_rcptid     NUMBER(12),
  from_yr       VARCHAR2(4),
  to_yr         VARCHAR2(4),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  lmoddate      DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  pr_flag       VARCHAR2(1),
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  wt_v1         NVARCHAR2(100),
  wt_v2         NVARCHAR2(100),
  wt_v3         NVARCHAR2(100),
  wt_v4         NVARCHAR2(100),
  wt_v5         NVARCHAR2(100),
  wt_n1         NUMBER(15),
  wt_n2         NUMBER(15),
  wt_n3         NUMBER(15),
  wt_n4         NUMBER(15),
  wt_n5         NUMBER(15),
  wt_d1         DATE,
  wt_d2         DATE,
  wt_d3         DATE,
  wt_lo1        CHAR(1),
  wt_lo2        CHAR(1),
  wt_lo3        CHAR(1),
  h_status      NVARCHAR2(1)
)
;
alter table TB_WT_NODUES_HIST
  add constraint PK_H_CAID primary key (H_CAID);

prompt
prompt Creating table TB_WT_OLD_NEW_CCN_LINK
prompt =====================================
prompt
create table TB_WT_OLD_NEW_CCN_LINK
(
  lc_id         NUMBER(12) not null,
  cs_idn        NUMBER(12),
  lc_oldccn     NVARCHAR2(30),
  lc_oldtaps    NUMBER(3),
  orgid         NUMBER(7) not null,
  user_id       NUMBER(7),
  lang_id       NUMBER(7),
  lmoddate      DATE,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  lc_oldccnsize NUMBER(10),
  is_deleted    VARCHAR2(1) default 'N'
)
;
comment on table TB_WT_OLD_NEW_CCN_LINK
  is 'This table is used to store link between the old CCN  CCN.';
comment on column TB_WT_OLD_NEW_CCN_LINK.lc_id
  is ' Id no. for this table - primary key';
comment on column TB_WT_OLD_NEW_CCN_LINK.cs_idn
  is 'Connection Id';
comment on column TB_WT_OLD_NEW_CCN_LINK.lc_oldccn
  is ' Old Connection code no.';
comment on column TB_WT_OLD_NEW_CCN_LINK.lc_oldtaps
  is ' No. of taps in Old Connection';
comment on column TB_WT_OLD_NEW_CCN_LINK.orgid
  is 'Org ID';
comment on column TB_WT_OLD_NEW_CCN_LINK.user_id
  is 'User ID';
comment on column TB_WT_OLD_NEW_CCN_LINK.lang_id
  is 'Lang ID';
comment on column TB_WT_OLD_NEW_CCN_LINK.lmoddate
  is 'Last Modification Date';
comment on column TB_WT_OLD_NEW_CCN_LINK.updated_by
  is 'User id who update the data';
comment on column TB_WT_OLD_NEW_CCN_LINK.updated_date
  is 'Date on which data is going to update';
comment on column TB_WT_OLD_NEW_CCN_LINK.lg_ip_mac
  is 'stores ip information';
comment on column TB_WT_OLD_NEW_CCN_LINK.lg_ip_mac_upd
  is 'stores ip information';
comment on column TB_WT_OLD_NEW_CCN_LINK.is_deleted
  is 'Flag to identify whether the record is deleted or not. Y for deleted (Inactive) and N for not deleted (Active) record.';
alter table TB_WT_OLD_NEW_CCN_LINK
  add constraint PK_LC_ID_ORGID primary key (LC_ID);
alter table TB_WT_OLD_NEW_CCN_LINK
  add constraint FK_TLKCN_TCSMR_CS_IDN foreign key (CS_IDN)
  references TB_CSMR_INFO (CS_IDN);

prompt
prompt Creating table TB_WT_OLD_NEW_CCN_LINK_HIST
prompt ==========================================
prompt
create table TB_WT_OLD_NEW_CCN_LINK_HIST
(
  h_lcid        NUMBER(12) not null,
  lc_id         NUMBER(12) not null,
  cs_idn        NUMBER(12),
  lc_oldccn     NVARCHAR2(30),
  lc_oldtaps    NUMBER(3),
  orgid         NUMBER(7) not null,
  user_id       NUMBER(7),
  lang_id       NUMBER(7),
  lmoddate      DATE,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  lc_oldccnsize NUMBER(10),
  is_deleted    VARCHAR2(1) default 'N',
  h_status      VARCHAR2(1)
)
;
alter table TB_WT_OLD_NEW_CCN_LINK_HIST
  add constraint PK_H_LCID primary key (H_LCID);

prompt
prompt Creating table TB_WT_PLUMRENEWAL_REG
prompt ====================================
prompt
create table TB_WT_PLUMRENEWAL_REG
(
  plum_rn_id         NUMBER(12),
  plum_id            NUMBER(12),
  rn_fromdate        DATE,
  rn_todate          DATE,
  rn_fees            NUMBER(12,2),
  rn_date            DATE,
  rn_remark          NVARCHAR2(200),
  rn_issued          VARCHAR2(1),
  rn_rcptflag        VARCHAR2(1),
  apm_application_id NUMBER(16),
  rm_rcptid          NUMBER(15),
  rn_entry_flag      VARCHAR2(1),
  rn_ported          VARCHAR2(1),
  wt_v3              NVARCHAR2(100),
  wt_v4              NVARCHAR2(100),
  wt_v5              NVARCHAR2(100),
  wt_n1              NUMBER(15),
  wt_n2              NUMBER(15),
  wt_n3              NUMBER(15),
  wt_n4              NUMBER(15),
  wt_n5              NUMBER(15),
  wt_d1              DATE,
  wt_d2              DATE,
  wt_d3              DATE,
  wt_lo1             CHAR(1),
  wt_lo2             CHAR(1),
  wt_lo3             CHAR(1),
  orgid              NUMBER(4),
  user_id            NUMBER(7),
  lang_id            NUMBER(7),
  lmoddate           DATE,
  updated_by         NUMBER(7),
  updated_date       DATE,
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100)
)
;
comment on table TB_WT_PLUMRENEWAL_REG
  is 'Renewal Master';
comment on column TB_WT_PLUMRENEWAL_REG.plum_rn_id
  is 'Renewal Register ID one up number';
comment on column TB_WT_PLUMRENEWAL_REG.plum_id
  is 'License ID(FK)';
comment on column TB_WT_PLUMRENEWAL_REG.rn_fromdate
  is 'Renewal From Date';
comment on column TB_WT_PLUMRENEWAL_REG.rn_todate
  is 'Renewal To Date';
comment on column TB_WT_PLUMRENEWAL_REG.rn_fees
  is 'Renewal Fees';
comment on column TB_WT_PLUMRENEWAL_REG.rn_date
  is 'Renewal Date';
comment on column TB_WT_PLUMRENEWAL_REG.rn_remark
  is 'Remarks';
comment on column TB_WT_PLUMRENEWAL_REG.rn_issued
  is 'Renewal Issued flag';
comment on column TB_WT_PLUMRENEWAL_REG.rn_rcptflag
  is 'Receipt Flag';
comment on column TB_WT_PLUMRENEWAL_REG.apm_application_id
  is 'Application ID';
comment on column TB_WT_PLUMRENEWAL_REG.rm_rcptid
  is 'Receipt Id';
comment on column TB_WT_PLUMRENEWAL_REG.rn_entry_flag
  is 'D-Data created through Data entry form, U-Data created through upload';
comment on column TB_WT_PLUMRENEWAL_REG.rn_ported
  is 'Y-Data created thru Data entry form or Data Upload';
comment on column TB_WT_PLUMRENEWAL_REG.wt_v3
  is 'Additional nvarchar2 WT_V3 to be used in future';
comment on column TB_WT_PLUMRENEWAL_REG.wt_v4
  is 'Additional nvarchar2 WT_V4 to be used in future';
comment on column TB_WT_PLUMRENEWAL_REG.wt_v5
  is 'Additional nvarchar2 WT_V5 to be used in future';
comment on column TB_WT_PLUMRENEWAL_REG.wt_n1
  is 'Additional number WT_N1 to be used in future';
comment on column TB_WT_PLUMRENEWAL_REG.wt_n2
  is 'Additional number WT_N2 to be used in future';
comment on column TB_WT_PLUMRENEWAL_REG.wt_n3
  is 'Additional number WT_N3 to be used in future';
comment on column TB_WT_PLUMRENEWAL_REG.wt_n4
  is 'Additional number WT_N4 to be used in future';
comment on column TB_WT_PLUMRENEWAL_REG.wt_n5
  is 'Additional number WT_N5 to be used in future';
comment on column TB_WT_PLUMRENEWAL_REG.wt_d1
  is 'Additional Date WT_D1 to be used in future';
comment on column TB_WT_PLUMRENEWAL_REG.wt_d2
  is 'Additional Date WT_D2 to be used in future';
comment on column TB_WT_PLUMRENEWAL_REG.wt_d3
  is 'Additional Date WT_D3 to be used in future';
comment on column TB_WT_PLUMRENEWAL_REG.wt_lo1
  is 'Additional Logical field WT_LO1 to be used in future';
comment on column TB_WT_PLUMRENEWAL_REG.wt_lo2
  is 'Additional Logical field WT_LO2 to be used in future';
comment on column TB_WT_PLUMRENEWAL_REG.wt_lo3
  is 'Additional Logical field WT_LO3 to be used in future';
comment on column TB_WT_PLUMRENEWAL_REG.orgid
  is 'Org ID';
comment on column TB_WT_PLUMRENEWAL_REG.user_id
  is 'User ID';
comment on column TB_WT_PLUMRENEWAL_REG.lang_id
  is 'Lang ID';
comment on column TB_WT_PLUMRENEWAL_REG.lmoddate
  is 'Last Modification Date';
comment on column TB_WT_PLUMRENEWAL_REG.updated_by
  is 'User id who update the data';
comment on column TB_WT_PLUMRENEWAL_REG.updated_date
  is 'Date on which data is going to update';
comment on column TB_WT_PLUMRENEWAL_REG.lg_ip_mac
  is 'Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_WT_PLUMRENEWAL_REG.lg_ip_mac_upd
  is 'Updated Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
alter table TB_WT_PLUMRENEWAL_REG
  add constraint PK_PLUM_RN_ID_ORGID primary key (PLUM_RN_ID);
alter table TB_WT_PLUMRENEWAL_REG
  add constraint CK_RN_ENTRY_FLAG
  check (RN_ENTRY_FLAG IN ('D','U'));
alter table TB_WT_PLUMRENEWAL_REG
  add constraint CK_RN_PORTED
  check (RN_PORTED IN ('Y','N'));
alter table TB_WT_PLUMRENEWAL_REG
  add constraint CK_WT_PLRENW_RCPTFLAG
  check (rn_rcptflag in ('Y','N'));
alter table TB_WT_PLUMRENEWAL_REG
  add constraint NN_WT_PLRENW_FRDATE
  check ("RN_FROMDATE" IS NOT NULL);
alter table TB_WT_PLUMRENEWAL_REG
  add constraint NN_WT_PLRENW_ID
  check ("PLUM_RN_ID" IS NOT NULL);
alter table TB_WT_PLUMRENEWAL_REG
  add constraint NN_WT_PLRENW_ISSUED
  check ("RN_ISSUED" IS NOT NULL)
  disable;
alter table TB_WT_PLUMRENEWAL_REG
  add constraint NN_WT_PLRENW_LANGID
  check ("LANG_ID" IS NOT NULL);
alter table TB_WT_PLUMRENEWAL_REG
  add constraint NN_WT_PLRENW_LMDDT
  check ("LMODDATE" IS NOT NULL);
alter table TB_WT_PLUMRENEWAL_REG
  add constraint NN_WT_PLRENW_LMID
  check ("PLUM_ID" IS NOT NULL);
alter table TB_WT_PLUMRENEWAL_REG
  add constraint NN_WT_PLRENW_ORGID
  check ("ORGID" IS NOT NULL);
alter table TB_WT_PLUMRENEWAL_REG
  add constraint NN_WT_PLRENW_RCPTFLAG
  check ("RN_RCPTFLAG" IS NOT NULL)
  disable;
alter table TB_WT_PLUMRENEWAL_REG
  add constraint NN_WT_PLRENW_TODATE
  check ("RN_TODATE" IS NOT NULL)
  disable;
alter table TB_WT_PLUMRENEWAL_REG
  add constraint NN_WT_PLRENW_USERID
  check ("USER_ID" IS NOT NULL);

prompt
prompt Creating table TB_WT_PLUMRENEWAL_REG_HIST
prompt =========================================
prompt
create table TB_WT_PLUMRENEWAL_REG_HIST
(
  h_plumid           NUMBER(12) not null,
  plum_rn_id         NUMBER(12),
  plum_id            NUMBER(12),
  rn_fromdate        DATE,
  rn_todate          DATE,
  rn_fees            NUMBER(12,2),
  rn_date            DATE,
  rn_remark          NVARCHAR2(200),
  rn_issued          VARCHAR2(1),
  rn_rcptflag        VARCHAR2(1),
  apm_application_id NUMBER(16),
  rm_rcptid          NUMBER(15),
  rn_entry_flag      VARCHAR2(1),
  rn_ported          VARCHAR2(1),
  wt_v3              NVARCHAR2(100),
  wt_v4              NVARCHAR2(100),
  wt_v5              NVARCHAR2(100),
  wt_n1              NUMBER(15),
  wt_n2              NUMBER(15),
  wt_n3              NUMBER(15),
  wt_n4              NUMBER(15),
  wt_n5              NUMBER(15),
  wt_d1              DATE,
  wt_d2              DATE,
  wt_d3              DATE,
  wt_lo1             CHAR(1),
  wt_lo2             CHAR(1),
  wt_lo3             CHAR(1),
  orgid              NUMBER(4),
  user_id            NUMBER(7),
  lang_id            NUMBER(7),
  lmoddate           DATE,
  updated_by         NUMBER(7),
  updated_date       DATE,
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  h_status           VARCHAR2(1)
)
;
alter table TB_WT_PLUMRENEWAL_REG_HIST
  add constraint PK_H_PLUMID primary key (H_PLUMID);

prompt
prompt Creating table TB_WT_PLUM_EXPERIENCE
prompt ====================================
prompt
create table TB_WT_PLUM_EXPERIENCE
(
  plum_exp_id          NUMBER(12) not null,
  plum_id              NUMBER(12),
  plum_company_name    NVARCHAR2(150),
  plum_company_address NVARCHAR2(150),
  plum_exp_month       NUMBER(2),
  plum_exp_year        NUMBER(4),
  plum_from_date       DATE,
  plum_to_date         DATE,
  plum_exp             NUMBER(5,2),
  plum_cpd_firm_type   NUMBER(12),
  orgid                NUMBER(4),
  user_id              NUMBER(7),
  lang_id              NUMBER(7),
  lmoddate             DATE,
  updated_by           NUMBER(7),
  updated_date         DATE,
  lg_ip_mac            VARCHAR2(100),
  lg_ip_mac_upd        VARCHAR2(100),
  wt_v1                NVARCHAR2(100),
  wt_v2                NVARCHAR2(100),
  wt_v3                NVARCHAR2(100),
  wt_v4                NVARCHAR2(100),
  wt_v5                NVARCHAR2(100),
  wt_n1                NUMBER(15),
  wt_n2                NUMBER(15),
  wt_n3                NUMBER(15),
  wt_n4                NUMBER(15),
  wt_n5                NUMBER(15),
  wt_d1                DATE,
  wt_d2                DATE,
  wt_d3                DATE,
  wt_lo1               CHAR(1),
  wt_lo2               CHAR(1),
  wt_lo3               CHAR(1),
  is_deleted           CHAR(1)
)
;
comment on table TB_WT_PLUM_EXPERIENCE
  is 'This table is used to store experience details of the plumber';
comment on column TB_WT_PLUM_EXPERIENCE.plum_exp_id
  is 'Generated Id';
comment on column TB_WT_PLUM_EXPERIENCE.plum_id
  is 'Reference Plumber Id';
comment on column TB_WT_PLUM_EXPERIENCE.plum_company_name
  is 'Name of Company';
comment on column TB_WT_PLUM_EXPERIENCE.plum_company_address
  is 'Address of Company';
comment on column TB_WT_PLUM_EXPERIENCE.plum_exp_month
  is 'No. Of months Experience';
comment on column TB_WT_PLUM_EXPERIENCE.plum_exp_year
  is 'No. Of yearExperience';
comment on column TB_WT_PLUM_EXPERIENCE.orgid
  is 'Org ID';
comment on column TB_WT_PLUM_EXPERIENCE.user_id
  is 'User ID';
comment on column TB_WT_PLUM_EXPERIENCE.lang_id
  is 'Lang ID';
comment on column TB_WT_PLUM_EXPERIENCE.lmoddate
  is 'Last Modification Date';
comment on column TB_WT_PLUM_EXPERIENCE.updated_by
  is 'User id who update the data';
comment on column TB_WT_PLUM_EXPERIENCE.updated_date
  is 'Date on which data is going to update';
comment on column TB_WT_PLUM_EXPERIENCE.lg_ip_mac
  is 'Client Machine�s Login Name | IP Address | Physical Address';
comment on column TB_WT_PLUM_EXPERIENCE.lg_ip_mac_upd
  is 'Updated Client Machine�s Login Name | IP Address | Physical Address';
comment on column TB_WT_PLUM_EXPERIENCE.wt_v1
  is 'Additional nvarchar2 WT_V1 to be used in future';
comment on column TB_WT_PLUM_EXPERIENCE.wt_v2
  is 'Additional nvarchar2 WT_V2 to be used in future';
comment on column TB_WT_PLUM_EXPERIENCE.wt_v3
  is 'Additional nvarchar2 WT_V3 to be used in future';
comment on column TB_WT_PLUM_EXPERIENCE.wt_v4
  is 'Additional nvarchar2 WT_V4 to be used in future';
comment on column TB_WT_PLUM_EXPERIENCE.wt_v5
  is 'Additional nvarchar2 WT_V5 to be used in future';
comment on column TB_WT_PLUM_EXPERIENCE.wt_n1
  is 'Additional number WT_N1 to be used in future';
comment on column TB_WT_PLUM_EXPERIENCE.wt_n2
  is 'Additional number WT_N2 to be used in future';
comment on column TB_WT_PLUM_EXPERIENCE.wt_n3
  is 'Additional number WT_N3 to be used in future';
comment on column TB_WT_PLUM_EXPERIENCE.wt_n4
  is 'Additional number WT_N4 to be used in future';
comment on column TB_WT_PLUM_EXPERIENCE.wt_n5
  is 'Additional number WT_N5 to be used in future';
comment on column TB_WT_PLUM_EXPERIENCE.wt_d1
  is 'Additional Date WT_D1 to be used in future';
comment on column TB_WT_PLUM_EXPERIENCE.wt_d2
  is 'Additional Date WT_D2 to be used in future';
comment on column TB_WT_PLUM_EXPERIENCE.wt_d3
  is 'Additional Date WT_D3 to be used in future';
comment on column TB_WT_PLUM_EXPERIENCE.wt_lo1
  is 'Additional Logical field WT_LO1 to be used in future';
comment on column TB_WT_PLUM_EXPERIENCE.wt_lo2
  is 'Additional Logical field WT_LO2 to be used in future';
comment on column TB_WT_PLUM_EXPERIENCE.wt_lo3
  is 'Additional Logical field WT_LO3 to be used in future';
comment on column TB_WT_PLUM_EXPERIENCE.is_deleted
  is 'Flag for deleted statement.';
alter table TB_WT_PLUM_EXPERIENCE
  add constraint PK_EXP_ID_ORGID primary key (PLUM_EXP_ID);
alter table TB_WT_PLUM_EXPERIENCE
  add constraint FK_TPM_TWPE_PLUM_ID foreign key (PLUM_ID)
  references TB_WT_PLUM_MAS (PLUM_ID);
alter table TB_WT_PLUM_EXPERIENCE
  add constraint NN_PLUM_EXP_LANG_ID
  check ("LANG_ID" IS NOT NULL);
alter table TB_WT_PLUM_EXPERIENCE
  add constraint NN_PLUM_EXP_LMODDATE
  check ("LMODDATE" IS NOT NULL);
alter table TB_WT_PLUM_EXPERIENCE
  add constraint NN_PLUM_EXP_ORGID
  check ("ORGID" IS NOT NULL);
alter table TB_WT_PLUM_EXPERIENCE
  add constraint NN_PLUM_EXP_USERID
  check ("USER_ID" IS NOT NULL);

prompt
prompt Creating table TB_WT_PLUM_EXPERIENCE_HIST
prompt =========================================
prompt
create table TB_WT_PLUM_EXPERIENCE_HIST
(
  h_plum_expid         NUMBER(12) not null,
  plum_exp_id          NUMBER(12),
  plum_id              NUMBER(12),
  plum_company_name    NVARCHAR2(150),
  plum_company_address NVARCHAR2(150),
  plum_exp_month       NUMBER(2),
  plum_exp_year        NUMBER(4),
  plum_from_date       DATE,
  plum_to_date         DATE,
  plum_exp             NUMBER(5,2),
  plum_cpd_firm_type   NUMBER(12),
  orgid                NUMBER(4),
  user_id              NUMBER(7),
  lang_id              NUMBER(7),
  lmoddate             DATE,
  updated_by           NUMBER(7),
  updated_date         DATE,
  lg_ip_mac            VARCHAR2(100),
  lg_ip_mac_upd        VARCHAR2(100),
  wt_v1                NVARCHAR2(100),
  wt_v2                NVARCHAR2(100),
  wt_v3                NVARCHAR2(100),
  wt_v4                NVARCHAR2(100),
  wt_v5                NVARCHAR2(100),
  wt_n1                NUMBER(15),
  wt_n2                NUMBER(15),
  wt_n3                NUMBER(15),
  wt_n4                NUMBER(15),
  wt_n5                NUMBER(15),
  wt_d1                DATE,
  wt_d2                DATE,
  wt_d3                DATE,
  wt_lo1               CHAR(1),
  wt_lo2               CHAR(1),
  wt_lo3               CHAR(1),
  is_deleted           CHAR(1),
  h_status             CHAR(1)
)
;
alter table TB_WT_PLUM_EXPERIENCE_HIST
  add constraint PK_HPLUM_EXPID primary key (H_PLUM_EXPID);

prompt
prompt Creating table TB_WT_PLUM_MAS_HIST
prompt ==================================
prompt
create table TB_WT_PLUM_MAS_HIST
(
  h_plumid              NUMBER(12) not null,
  plum_id               NUMBER(12) not null,
  plum_appl_date        DATE,
  plum_lname            NVARCHAR2(100),
  plum_fname            NVARCHAR2(100),
  plum_mname            NVARCHAR2(100),
  plum_sex              NVARCHAR2(1),
  plum_dob              DATE,
  plum_contact_personnm NVARCHAR2(100),
  plum_lic_no           NVARCHAR2(20),
  plum_lic_issue_flg    CHAR(1),
  plum_lic_issue_date   DATE,
  plum_lic_from_date    DATE,
  plum_lic_to_date      DATE,
  orgid                 NUMBER(4),
  user_id               NUMBER(7),
  lang_id               NUMBER(7),
  lmoddate              DATE,
  plum_interview_remark NVARCHAR2(200),
  plum_interview_clr    NVARCHAR2(1),
  updated_by            NUMBER(7),
  updated_date          DATE,
  plum_lic_type         NVARCHAR2(1),
  plum_fat_hus_name     NVARCHAR2(50),
  plum_contact_no       NVARCHAR2(20),
  plum_cpdtitle         NVARCHAR2(30),
  plum_add              NVARCHAR2(200),
  plum_interview_dttm   DATE,
  plum_cscid1           NUMBER(12),
  plum_cscid2           NUMBER(12),
  plum_cscid3           NUMBER(12),
  plum_cscid4           NUMBER(12),
  plum_cscid5           NUMBER(12),
  plum_old_lic_no       NVARCHAR2(20),
  ported                VARCHAR2(1),
  lg_ip_mac             VARCHAR2(100),
  lg_ip_mac_upd         VARCHAR2(100),
  plum_entry_flag       VARCHAR2(1),
  wt_v2                 NVARCHAR2(100),
  wt_v3                 NVARCHAR2(100),
  wt_v4                 NVARCHAR2(100),
  wt_v5                 NVARCHAR2(100),
  wt_n1                 NUMBER(15),
  wt_n2                 NUMBER(15),
  wt_n3                 NUMBER(15),
  wt_n4                 NUMBER(15),
  wt_n5                 NUMBER(15),
  wt_d1                 DATE,
  wt_d2                 DATE,
  wt_d3                 DATE,
  wt_lo1                CHAR(1),
  wt_lo2                CHAR(1),
  wt_lo3                CHAR(1),
  apm_application_id    NUMBER(16),
  plum_image            NVARCHAR2(200),
  h_status              NVARCHAR2(1)
)
;
alter table TB_WT_PLUM_MAS_HIST
  add constraint PK_HPLUMID primary key (H_PLUMID);

prompt
prompt Creating table TB_WT_PLUM_QUALIFICATION
prompt =======================================
prompt
create table TB_WT_PLUM_QUALIFICATION
(
  plum_qual_id           NUMBER(12) not null,
  plum_id                NUMBER(12),
  plum_qualification     NVARCHAR2(150),
  plum_pass_month        NUMBER(2),
  plum_pass_year         NUMBER(4),
  plum_percent_grade     NVARCHAR2(15),
  plum_institute_name    NVARCHAR2(150),
  plum_institute_address NVARCHAR2(200),
  orgid                  NUMBER(4),
  user_id                NUMBER(7),
  lang_id                NUMBER(7),
  lmoddate               DATE,
  updated_by             NUMBER(7),
  updated_date           DATE,
  lg_ip_mac              VARCHAR2(100),
  lg_ip_mac_upd          VARCHAR2(100),
  wt_v1                  NVARCHAR2(100),
  wt_v2                  NVARCHAR2(100),
  wt_v3                  NVARCHAR2(100),
  wt_v4                  NVARCHAR2(100),
  wt_v5                  NVARCHAR2(100),
  wt_n1                  NUMBER(15),
  wt_n2                  NUMBER(15),
  wt_n3                  NUMBER(15),
  wt_n4                  NUMBER(15),
  wt_n5                  NUMBER(15),
  wt_d1                  DATE,
  wt_d2                  DATE,
  wt_d3                  DATE,
  wt_lo1                 CHAR(1),
  wt_lo2                 CHAR(1),
  wt_lo3                 CHAR(1),
  is_deleted             CHAR(1)
)
;
comment on table TB_WT_PLUM_QUALIFICATION
  is 'This table is used to store acadamic qualification details of the plumber';
comment on column TB_WT_PLUM_QUALIFICATION.plum_qual_id
  is 'Generated Id';
comment on column TB_WT_PLUM_QUALIFICATION.plum_id
  is 'Refernce Plumber Id';
comment on column TB_WT_PLUM_QUALIFICATION.plum_qualification
  is 'Qualification of plumber';
comment on column TB_WT_PLUM_QUALIFICATION.plum_pass_month
  is 'Passing month of plumber';
comment on column TB_WT_PLUM_QUALIFICATION.plum_pass_year
  is 'Passing year of plumber';
comment on column TB_WT_PLUM_QUALIFICATION.plum_percent_grade
  is 'Percentage/Grade';
comment on column TB_WT_PLUM_QUALIFICATION.plum_institute_name
  is 'Institute Name';
comment on column TB_WT_PLUM_QUALIFICATION.plum_institute_address
  is 'Institute Address';
comment on column TB_WT_PLUM_QUALIFICATION.orgid
  is 'Org ID';
comment on column TB_WT_PLUM_QUALIFICATION.user_id
  is 'User ID';
comment on column TB_WT_PLUM_QUALIFICATION.lang_id
  is 'Lang ID';
comment on column TB_WT_PLUM_QUALIFICATION.lmoddate
  is 'Last Modification Date';
comment on column TB_WT_PLUM_QUALIFICATION.updated_by
  is 'User id who update the data';
comment on column TB_WT_PLUM_QUALIFICATION.updated_date
  is 'Date on which data is going to update';
comment on column TB_WT_PLUM_QUALIFICATION.lg_ip_mac
  is 'Client Machine�s Login Name | IP Address | Physical Address';
comment on column TB_WT_PLUM_QUALIFICATION.lg_ip_mac_upd
  is 'Updated Client Machine�s Login Name | IP Address | Physical Address';
comment on column TB_WT_PLUM_QUALIFICATION.wt_v1
  is 'Additional nvarchar2 WT_V1 to be used in future';
comment on column TB_WT_PLUM_QUALIFICATION.wt_v2
  is 'Additional nvarchar2 WT_V2 to be used in future';
comment on column TB_WT_PLUM_QUALIFICATION.wt_v3
  is 'Additional nvarchar2 WT_V3 to be used in future';
comment on column TB_WT_PLUM_QUALIFICATION.wt_v4
  is 'Additional nvarchar2 WT_V4 to be used in future';
comment on column TB_WT_PLUM_QUALIFICATION.wt_v5
  is 'Additional nvarchar2 WT_V5 to be used in future';
comment on column TB_WT_PLUM_QUALIFICATION.wt_n1
  is 'Additional number WT_N1 to be used in future';
comment on column TB_WT_PLUM_QUALIFICATION.wt_n2
  is 'Additional number WT_N2 to be used in future';
comment on column TB_WT_PLUM_QUALIFICATION.wt_n3
  is 'Additional number WT_N3 to be used in future';
comment on column TB_WT_PLUM_QUALIFICATION.wt_n4
  is 'Additional number WT_N4 to be used in future';
comment on column TB_WT_PLUM_QUALIFICATION.wt_n5
  is 'Additional number WT_N5 to be used in future';
comment on column TB_WT_PLUM_QUALIFICATION.wt_d1
  is 'Additional Date WT_D1 to be used in future';
comment on column TB_WT_PLUM_QUALIFICATION.wt_d2
  is 'Additional Date WT_D2 to be used in future';
comment on column TB_WT_PLUM_QUALIFICATION.wt_d3
  is 'Additional Date WT_D3 to be used in future';
comment on column TB_WT_PLUM_QUALIFICATION.wt_lo1
  is 'Additional Logical field WT_LO1 to be used in future';
comment on column TB_WT_PLUM_QUALIFICATION.wt_lo2
  is 'Additional Logical field WT_LO2 to be used in future';
comment on column TB_WT_PLUM_QUALIFICATION.wt_lo3
  is 'Additional Logical field WT_LO3 to be used in future';
comment on column TB_WT_PLUM_QUALIFICATION.is_deleted
  is 'Flag for deleted statement.';
alter table TB_WT_PLUM_QUALIFICATION
  add constraint PK_PLUM_QUAL_ORGID primary key (PLUM_QUAL_ID);
alter table TB_WT_PLUM_QUALIFICATION
  add constraint FK_TPM_TWPQ_PLUM_ID foreign key (PLUM_ID)
  references TB_WT_PLUM_MAS (PLUM_ID);
alter table TB_WT_PLUM_QUALIFICATION
  add constraint NN_PLUM_QUAL_LANG_ID
  check ("LANG_ID" IS NOT NULL);
alter table TB_WT_PLUM_QUALIFICATION
  add constraint NN_PLUM_QUAL_LMODDATE
  check ("LMODDATE" IS NOT NULL);
alter table TB_WT_PLUM_QUALIFICATION
  add constraint NN_PLUM_QUAL_ORGID
  check ("ORGID" IS NOT NULL);
alter table TB_WT_PLUM_QUALIFICATION
  add constraint NN_PLUM_QUAL_USERID
  check ("USER_ID" IS NOT NULL);

prompt
prompt Creating table TB_WT_PLUM_QUALIFICATION_HIST
prompt ============================================
prompt
create table TB_WT_PLUM_QUALIFICATION_HIST
(
  h_plumqualid           NUMBER(12) not null,
  plum_qual_id           NUMBER(12),
  plum_id                NUMBER(12),
  plum_qualification     NVARCHAR2(150),
  plum_pass_month        NUMBER(2),
  plum_pass_year         NUMBER(4),
  plum_percent_grade     NVARCHAR2(15),
  plum_institute_name    NVARCHAR2(150),
  plum_institute_address NVARCHAR2(200),
  orgid                  NUMBER(4),
  user_id                NUMBER(7),
  lang_id                NUMBER(7),
  lmoddate               DATE,
  updated_by             NUMBER(7),
  updated_date           DATE,
  lg_ip_mac              VARCHAR2(100),
  lg_ip_mac_upd          VARCHAR2(100),
  wt_v1                  NVARCHAR2(100),
  wt_v2                  NVARCHAR2(100),
  wt_v3                  NVARCHAR2(100),
  wt_v4                  NVARCHAR2(100),
  wt_v5                  NVARCHAR2(100),
  wt_n1                  NUMBER(15),
  wt_n2                  NUMBER(15),
  wt_n3                  NUMBER(15),
  wt_n4                  NUMBER(15),
  wt_n5                  NUMBER(15),
  wt_d1                  DATE,
  wt_d2                  DATE,
  wt_d3                  DATE,
  wt_lo1                 CHAR(1),
  wt_lo2                 CHAR(1),
  wt_lo3                 CHAR(1),
  is_deleted             CHAR(1),
  h_status               CHAR(1)
)
;
alter table TB_WT_PLUM_QUALIFICATION_HIST
  add constraint PK_H_PLUMQUALID primary key (H_PLUMQUALID);

prompt
prompt Creating table TB_WT_PLUM_RENWANL_SCH
prompt =====================================
prompt
create table TB_WT_PLUM_RENWANL_SCH
(
  ren_id        NUMBER(12) not null,
  serviceid     NUMBER(12),
  fromdate      DATE,
  dependon      NUMBER(10),
  todate        DATE,
  day_start     NUMBER(4),
  mon_start     NUMBER(4),
  day_end       NUMBER(4),
  mon_end       NUMBER(4),
  value         NUMBER(4),
  daymonyear    NUMBER(4),
  status        NVARCHAR2(1) not null,
  orgid         NUMBER(4) not null,
  user_id       NUMBER(4) not null,
  lang_id       NUMBER(4) not null,
  lmoddate      DATE not null,
  updated_by    NUMBER(4),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  tp_v1         NVARCHAR2(100),
  tp_v2         NVARCHAR2(100),
  tp_v3         NVARCHAR2(100),
  tp_v4         NVARCHAR2(100),
  tp_v5         NVARCHAR2(100),
  tp_n1         NUMBER(15),
  tp_n2         NUMBER(15),
  tp_n3         NUMBER(15),
  tp_n4         NUMBER(15),
  tp_n5         NUMBER(15),
  tp_d1         DATE,
  tp_d2         DATE,
  tp_d3         DATE,
  tp_lo1        CHAR(1),
  tp_lo2        CHAR(1),
  tp_lo3        CHAR(1)
)
;
comment on column TB_WT_PLUM_RENWANL_SCH.ren_id
  is 'TRS ID one up number';
comment on column TB_WT_PLUM_RENWANL_SCH.fromdate
  is 'From date';
comment on column TB_WT_PLUM_RENWANL_SCH.dependon
  is 'depend on ';
comment on column TB_WT_PLUM_RENWANL_SCH.todate
  is 'To date';
comment on column TB_WT_PLUM_RENWANL_SCH.day_start
  is 'Start day';
comment on column TB_WT_PLUM_RENWANL_SCH.mon_start
  is 'Start month';
comment on column TB_WT_PLUM_RENWANL_SCH.day_end
  is 'End day';
comment on column TB_WT_PLUM_RENWANL_SCH.mon_end
  is 'End month';
comment on column TB_WT_PLUM_RENWANL_SCH.value
  is 'TRS value';
comment on column TB_WT_PLUM_RENWANL_SCH.daymonyear
  is 'Tday /month / year';
comment on column TB_WT_PLUM_RENWANL_SCH.status
  is 'TRS status';
comment on column TB_WT_PLUM_RENWANL_SCH.orgid
  is 'Organization id';
comment on column TB_WT_PLUM_RENWANL_SCH.user_id
  is 'User_id';
comment on column TB_WT_PLUM_RENWANL_SCH.lang_id
  is 'Lang_id';
comment on column TB_WT_PLUM_RENWANL_SCH.lmoddate
  is 'last modification date';
comment on column TB_WT_PLUM_RENWANL_SCH.updated_by
  is 'Updated by';
comment on column TB_WT_PLUM_RENWANL_SCH.updated_date
  is 'Updated on';
comment on column TB_WT_PLUM_RENWANL_SCH.lg_ip_mac
  is 'Client Machine⿿s Login Name | IP Address | Physical Address';
comment on column TB_WT_PLUM_RENWANL_SCH.lg_ip_mac_upd
  is 'Updated Client Machine⿿s Login Name | IP Address | Physical Address';
comment on column TB_WT_PLUM_RENWANL_SCH.tp_v1
  is 'Additional nvarchar2 TP_V1 to be used in future';
comment on column TB_WT_PLUM_RENWANL_SCH.tp_v2
  is 'Additional nvarchar2 TP_V2 to be used in future';
comment on column TB_WT_PLUM_RENWANL_SCH.tp_v3
  is 'Additional nvarchar2 TP_V3 to be used in future';
comment on column TB_WT_PLUM_RENWANL_SCH.tp_v4
  is 'Additional nvarchar2 TP_V4 to be used in future';
comment on column TB_WT_PLUM_RENWANL_SCH.tp_v5
  is 'Additional nvarchar2 TP_V5 to be used in future';
comment on column TB_WT_PLUM_RENWANL_SCH.tp_n1
  is 'Additional number TP_N1 to be used in future';
comment on column TB_WT_PLUM_RENWANL_SCH.tp_n2
  is 'Additional number TP_N2 to be used in future';
comment on column TB_WT_PLUM_RENWANL_SCH.tp_n3
  is 'Additional number TP_N3 to be used in future';
comment on column TB_WT_PLUM_RENWANL_SCH.tp_n4
  is 'Additional number TP_N4 to be used in future';
comment on column TB_WT_PLUM_RENWANL_SCH.tp_n5
  is 'Additional number TP_N5 to be used in future';
comment on column TB_WT_PLUM_RENWANL_SCH.tp_d1
  is 'Additional Date TP_D1 to be used in future ';
comment on column TB_WT_PLUM_RENWANL_SCH.tp_d2
  is 'Additional Date TP_D2 to be used in future ';
comment on column TB_WT_PLUM_RENWANL_SCH.tp_d3
  is 'Additional Date TP_D3 to be used in future ';
comment on column TB_WT_PLUM_RENWANL_SCH.tp_lo1
  is 'Additional Logical field TP_LO1 to be used in future ';
comment on column TB_WT_PLUM_RENWANL_SCH.tp_lo2
  is 'Additional Logical field TP_LO2 to be used in future ';
comment on column TB_WT_PLUM_RENWANL_SCH.tp_lo3
  is 'Additional Logical field TP_LO3 to be used in future ';
alter table TB_WT_PLUM_RENWANL_SCH
  add constraint PK_REN_ID primary key (REN_ID);

prompt
prompt Creating table TB_WT_PLUM_RENWANL_SCH_HIST
prompt ==========================================
prompt
create table TB_WT_PLUM_RENWANL_SCH_HIST
(
  h_renid       NUMBER(12) not null,
  ren_id        NUMBER(12),
  serviceid     NUMBER(12),
  fromdate      DATE,
  dependon      NUMBER(10),
  todate        DATE,
  day_start     NUMBER(4),
  mon_start     NUMBER(4),
  day_end       NUMBER(4),
  mon_end       NUMBER(4),
  value         NUMBER(4),
  daymonyear    NUMBER(4),
  status        NVARCHAR2(1),
  orgid         NUMBER(4),
  user_id       NUMBER(4),
  lang_id       NUMBER(4),
  lmoddate      DATE,
  updated_by    NUMBER(4),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  tp_v1         NVARCHAR2(100),
  tp_v2         NVARCHAR2(100),
  tp_v3         NVARCHAR2(100),
  tp_v4         NVARCHAR2(100),
  tp_v5         NVARCHAR2(100),
  tp_n1         NUMBER(15),
  tp_n2         NUMBER(15),
  tp_n3         NUMBER(15),
  tp_n4         NUMBER(15),
  tp_n5         NUMBER(15),
  tp_d1         DATE,
  tp_d2         DATE,
  tp_d3         DATE,
  tp_lo1        CHAR(1),
  tp_lo2        CHAR(1),
  tp_lo3        CHAR(1),
  h_status      CHAR(1)
)
;
alter table TB_WT_PLUM_RENWANL_SCH_HIST
  add constraint PK_H_RENID primary key (H_RENID);

prompt
prompt Creating table TB_WT_RECONNECTION
prompt =================================
prompt
create table TB_WT_RECONNECTION
(
  cs_idn             NUMBER(12),
  rcn_idn            NUMBER(12) not null,
  rcn_date           DATE,
  rcn_status         NVARCHAR2(1),
  rcn_granted        VARCHAR2(1),
  rccn_method        NUMBER(12),
  plum_id            NUMBER(12),
  apm_application_id NUMBER(16) not null,
  rcn_remark         NVARCHAR2(200),
  rcn_exedate        DATE,
  ccn_flag           NVARCHAR2(1),
  disc_method        NUMBER(10),
  disc_type          NUMBER(10),
  disc_reason        VARCHAR2(500),
  disc_appdate       DATE not null,
  orgid              NUMBER(4) not null,
  wt_v1              NVARCHAR2(100),
  wt_v2              NVARCHAR2(100),
  wt_v3              NVARCHAR2(100),
  wt_v4              NVARCHAR2(100),
  wt_v5              NVARCHAR2(100),
  wt_n1              NUMBER(15),
  wt_n2              NUMBER(15),
  wt_n3              NUMBER(15),
  wt_n4              NUMBER(15),
  wt_n5              NUMBER(15),
  wt_d1              DATE,
  wt_d2              DATE,
  wt_d3              DATE,
  wt_lo1             CHAR(1),
  wt_lo2             CHAR(1),
  wt_lo3             CHAR(1),
  user_id            NUMBER(7),
  lang_id            NUMBER(7),
  lmoddate           DATE,
  updated_by         NUMBER(7),
  updated_date       DATE,
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100)
)
;
comment on column TB_WT_RECONNECTION.cs_idn
  is 'Consumer Identification Number';
comment on column TB_WT_RECONNECTION.rcn_idn
  is 'Consumer Conection Identification Number';
comment on column TB_WT_RECONNECTION.rcn_date
  is 'Reconnection Date';
comment on column TB_WT_RECONNECTION.rcn_status
  is 'Reconnection record  Status';
comment on column TB_WT_RECONNECTION.rcn_granted
  is 'Reconnection Grant';
comment on column TB_WT_RECONNECTION.rccn_method
  is 'RCCN application Method(By Appl / By Force)';
comment on column TB_WT_RECONNECTION.plum_id
  is 'Plumber ID';
comment on column TB_WT_RECONNECTION.apm_application_id
  is 'Application Id';
comment on column TB_WT_RECONNECTION.rcn_remark
  is 'Remarks';
comment on column TB_WT_RECONNECTION.rcn_exedate
  is 'Date of Execution';
comment on column TB_WT_RECONNECTION.ccn_flag
  is 'Physical connection flag';
comment on column TB_WT_RECONNECTION.orgid
  is 'Org ID';
comment on column TB_WT_RECONNECTION.wt_v1
  is 'Additional nvarchar2 WT_V1 to be used in future';
comment on column TB_WT_RECONNECTION.wt_v2
  is 'Additional nvarchar2 WT_V2 to be used in future';
comment on column TB_WT_RECONNECTION.wt_v3
  is 'Additional nvarchar2 WT_V3 to be used in future';
comment on column TB_WT_RECONNECTION.wt_v4
  is 'Additional nvarchar2 WT_V4 to be used in future';
comment on column TB_WT_RECONNECTION.wt_v5
  is 'Additional nvarchar2 WT_V5 to be used in future';
comment on column TB_WT_RECONNECTION.wt_n1
  is 'Additional number WT_N1 to be used in future';
comment on column TB_WT_RECONNECTION.wt_n2
  is 'Additional number WT_N2 to be used in future';
comment on column TB_WT_RECONNECTION.wt_n3
  is 'Additional number WT_N3 to be used in future';
comment on column TB_WT_RECONNECTION.wt_n4
  is 'Additional number WT_N4 to be used in future';
comment on column TB_WT_RECONNECTION.wt_n5
  is 'Additional number WT_N5 to be used in future';
comment on column TB_WT_RECONNECTION.wt_d1
  is 'Additional Date WT_D1 to be used in future';
comment on column TB_WT_RECONNECTION.wt_d2
  is 'Additional Date WT_D2 to be used in future';
comment on column TB_WT_RECONNECTION.wt_d3
  is 'Additional Date WT_D3 to be used in future';
comment on column TB_WT_RECONNECTION.wt_lo1
  is 'Additional Logical field WT_LO1 to be used in future';
comment on column TB_WT_RECONNECTION.wt_lo2
  is 'Additional Logical field WT_LO2 to be used in future';
comment on column TB_WT_RECONNECTION.wt_lo3
  is 'Additional Logical field WT_LO3 to be used in future';
comment on column TB_WT_RECONNECTION.user_id
  is 'User ID';
comment on column TB_WT_RECONNECTION.lang_id
  is 'Lang ID';
comment on column TB_WT_RECONNECTION.lmoddate
  is 'Last Modification Date';
comment on column TB_WT_RECONNECTION.updated_by
  is 'User id who update the data';
comment on column TB_WT_RECONNECTION.updated_date
  is 'Date on which data is going to update';
comment on column TB_WT_RECONNECTION.lg_ip_mac
  is 'Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
comment on column TB_WT_RECONNECTION.lg_ip_mac_upd
  is 'Updated Client Machineâ⿬⿢s Login Name | IP Address | Physical Address';
alter table TB_WT_RECONNECTION
  add constraint PK_RCN_IDN primary key (RCN_IDN);
alter table TB_WT_RECONNECTION
  add constraint CHK_RCN_GRANTED
  check (rcn_granted in ('Y','N'));
alter table TB_WT_RECONNECTION
  add constraint CHK_RCN_STATUS
  check (rcn_status in ('A','I'));

prompt
prompt Creating table TB_WT_RECONNECTION_HIST
prompt ======================================
prompt
create table TB_WT_RECONNECTION_HIST
(
  h_recid            NUMBER(12) not null,
  cs_idn             NUMBER(12),
  rcn_idn            NUMBER(12) not null,
  rcn_date           DATE,
  rcn_status         NVARCHAR2(1),
  rcn_granted        VARCHAR2(1),
  rccn_method        NUMBER(12),
  plum_id            NUMBER(12),
  apm_application_id NUMBER(16) not null,
  rcn_remark         NVARCHAR2(200),
  rcn_exedate        DATE,
  ccn_flag           NVARCHAR2(1),
  disc_method        NUMBER(10),
  disc_type          NUMBER(10),
  disc_reason        VARCHAR2(500),
  disc_appdate       DATE not null,
  orgid              NUMBER(4) not null,
  wt_v1              NVARCHAR2(100),
  wt_v2              NVARCHAR2(100),
  wt_v3              NVARCHAR2(100),
  wt_v4              NVARCHAR2(100),
  wt_v5              NVARCHAR2(100),
  wt_n1              NUMBER(15),
  wt_n2              NUMBER(15),
  wt_n3              NUMBER(15),
  wt_n4              NUMBER(15),
  wt_n5              NUMBER(15),
  wt_d1              DATE,
  wt_d2              DATE,
  wt_d3              DATE,
  wt_lo1             CHAR(1),
  wt_lo2             CHAR(1),
  wt_lo3             CHAR(1),
  user_id            NUMBER(7),
  lang_id            NUMBER(7),
  lmoddate           DATE,
  updated_by         NUMBER(7),
  updated_date       DATE,
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  h_status           NVARCHAR2(1)
)
;
alter table TB_WT_RECONNECTION_HIST
  add constraint PK_RECID primary key (H_RECID);


spool off

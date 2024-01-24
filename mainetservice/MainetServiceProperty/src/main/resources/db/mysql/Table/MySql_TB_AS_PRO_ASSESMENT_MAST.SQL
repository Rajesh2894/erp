create table TB_AS_PRO_ASSESMENT_MAST
( PRO_ASS_id              BIGINT not null ,
  PRO_ASS_no              NVARCHAR(20) not null,
  tpp_approval_no     NVARCHAR(200) not null,
  PRO_ASS_oldpropno       NVARCHAR(20) not null,
  tpp_plot_no_cs      NVARCHAR(50) not null,
  tpp_survey_number   NVARCHAR(25) not null,
  tpp_khata_no        NVARCHAR(50) not null,
  tpp_toji_no         NVARCHAR(50) not null,
  tpp_plot_no         NVARCHAR(50) not null,
  PRO_ASS_street_no       NVARCHAR(500) not null,
  tpp_village_mauja   NVARCHAR(50) not null,
  PRO_ASS_address         NVARCHAR(1000) not null,
  loc_id              BIGINT not null,
  PRO_ASS_pincode         INT not null,
  PRO_ASS_corr_address    NVARCHAR(1000) not null,
  PRO_ASS_corr_pincode    INT not null,
  PRO_ASS_corr_email      NVARCHAR(25) not null,
  PRO_ASS_lp_receipt_no   NVARCHAR(25),
  PRO_ASS_lp_receipt_amt  DECIMAL(15,2),
  PRO_ASS_lp_receipt_date DATETIME,
  PRO_ASS_lp_year         BIGINT,
  PRO_ASS_lp_bill_cycle   BIGINT,
  PRO_ASS_acq_date        DATETIME not null,
  PRO_ASS_prop_type1      BIGINT not null,
  PRO_ASS_prop_type2      BIGINT not null,
  PRO_ASS_prop_type3      BIGINT not null,
  PRO_ASS_plot_area       DECIMAL(15,2) not null,
  PRO_ASS_buit_area_gr    DECIMAL(15,2) not null,
  PRO_ASS_owner_type      BIGINT not null,
  PRO_ASS_ward1           BIGINT,
  PRO_ASS_ward2           BIGINT,
  PRO_ASS_ward3           BIGINT,
  PRO_ASS_ward4           BIGINT,
  PRO_ASS_ward5           BIGINT,
  PRO_ASS_gis_id          VARCHAR(20),
  PRO_ASS_active          CHAR(1) default 'Y' not null,
  PRO_ASS_status          CHAR(1) not null,
  PRO_ASS_aut_status      CHAR(1),
  PRO_ASS_aut_by          BIGINT,
  PRO_ASS_aut_date        DATETIME,
  orgid               BIGINT not null,
  created_by          INT not null,
  created_date        DATETIME not null,
  updated_by          INT,
  updated_date        DATETIME,
  lg_ip_mac           VARCHAR(100),
  lg_ip_mac_upd       VARCHAR(100));
  alter table TB_AS_PRO_ASSESMENT_MAST
  add constraint PK_PRO_ASS_ID primary key (PRO_ASS_ID);
alter table TB_AS_PRO_ASSESMENT_MAST
  add constraint FK_PRO_ASS_LOC_ID foreign key (LOC_ID)
  references TB_LOCATION_MAS (LOC_ID);

comment 'Assesment Number';
comment 'Building Approval No';
comment 'Old Property Number';
comment 'CSN/Khasara No';
comment 'Survey Number';
comment 'Khata Number';
comment 'Toji Number';
comment  'Plot Number';
comment 'Street Number/Name';
comment 'Village/Mauja Name';
comment 'Property Address';
comment  'Location  Foregin Key(TB_LOCATION_MAS)';
comment 'Pincode';
comment 'Correspondence Address';
comment 'Correspondence Address Pincode';
comment 'Correspondence EMAIL';
comment 'Last Payment Receipt No.';
comment 'Last Payment Receipt No.';
comment 'Last Payment Receipt Date';
comment 'Last Payment Year';
comment 'Last Payment Billing Cycle';
comment 'Acquisition Date';
comment 'Property Type';
comment 'Property SubType';
comment 'Property SubType';
comment 'Total Plot Area';
comment 'Build Up/Constructed Area on the Ground Floor';
comment 'Owner Type (Prefix OWT)';
comment 'Ward1';
comment 'Ward2';
comment 'Ward3';
comment 'Ward4';
comment 'Ward5';
comment 'Gis Id';
comment 'flag to identify whether the record is deleted or not. ''y''  for not deleted (active) record and ''n'' for deleted (inactive) . ';
comment 'Assesment flow Status';
comment 'authorisation status';
comment 'authorisation by (empid)';
comment 'authorisation date';
comment 'orgnisation id';
comment 'user id who created the record';
comment 'record creation date';
comment 'user id who update the data';
comment 'date on which data is going to update';
comment 'client machine?s login name | ip address | physical address';
comment 'updated client machine?s login name | ip address | physical address';



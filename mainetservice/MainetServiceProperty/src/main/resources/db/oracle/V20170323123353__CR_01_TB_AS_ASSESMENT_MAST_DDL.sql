-- Create table
create table TB_AS_ASSESMENT_MAST
(
  ass_id              NUMBER(12) not null,
  ass_no              NVARCHAR2(20) not null,
  tpp_approval_no     NVARCHAR2(200) not null,
  ass_oldpropno       NVARCHAR2(20) not null,
  tpp_plot_no_cs      NVARCHAR2(50) not null,
  tpp_survey_number   NVARCHAR2(25) not null,
  tpp_khata_no        NVARCHAR2(50) not null,
  tpp_toji_no         NVARCHAR2(50) not null,
  tpp_plot_no         NVARCHAR2(50) not null,
  ass_street_no       NVARCHAR2(500) not null,
  tpp_village_mauja   NVARCHAR2(50) not null,
  ass_address         NVARCHAR2(1000) not null,
  loc_id              NUMBER(12) not null,
  ass_pincode         NUMBER(6) not null,
  ass_corr_address    NVARCHAR2(1000) not null,
  ass_corr_pincode    NUMBER(6) not null,
  ass_corr_email      NVARCHAR2(25) not null,
  ass_lp_receipt_no   NVARCHAR2(25),
  ass_lp_receipt_amt  NUMBER(15,2),
  ass_lp_receipt_date DATE,
  ass_lp_year         NUMBER(12),
  ass_lp_bill_cycle   NUMBER(12),
  ass_acq_date        DATE not null,
  ass_prop_type1      NUMBER(12) not null,
  ass_prop_type2      NUMBER(12) not null,
  ass_prop_type3      NUMBER(12) not null,
  ass_plot_area       NUMBER(15,2) not null,
  ass_buit_area_gr    NUMBER(15,2) not null,
  ass_owner_type      NUMBER(12) not null,
  ass_ward1           NUMBER(12),
  ass_ward2           NUMBER(12),
  ass_ward3           NUMBER(12),
  ass_ward4           NUMBER(12),
  ass_ward5           NUMBER(12),
  ass_gis_id          VARCHAR2(20),
  ass_active          CHAR(1) default 'Y' not null,
  ass_status          CHAR(1) not null,
  ass_aut_status      CHAR(1),
  ass_aut_by          NUMBER(12),
  ass_aut_date        DATE,
  orgid               NUMBER(12) not null,
  created_by          NUMBER(7) not null,
  created_date        DATE not null,
  updated_by          NUMBER(7),
  updated_date        DATE,
  lg_ip_mac           VARCHAR2(100),
  lg_ip_mac_upd       VARCHAR2(100)
);
-- Add comments to the columns 
comment on column TB_AS_ASSESMENT_MAST.ass_id
  is 'primary key';
comment on column TB_AS_ASSESMENT_MAST.ass_no
  is 'Assesment Number';
comment on column TB_AS_ASSESMENT_MAST.tpp_approval_no
  is 'Building Approval No';
comment on column TB_AS_ASSESMENT_MAST.ass_oldpropno
  is 'Old Property Number';
comment on column TB_AS_ASSESMENT_MAST.tpp_plot_no_cs
  is 'CSN/Khasara No';
comment on column TB_AS_ASSESMENT_MAST.tpp_survey_number
  is 'Survey Number';
comment on column TB_AS_ASSESMENT_MAST.tpp_khata_no
  is 'Khata Number';
comment on column TB_AS_ASSESMENT_MAST.tpp_toji_no
  is 'Toji Number';
comment on column TB_AS_ASSESMENT_MAST.tpp_plot_no
  is 'Plot Number';
comment on column TB_AS_ASSESMENT_MAST.ass_street_no
  is 'Street Number/Name';
comment on column TB_AS_ASSESMENT_MAST.tpp_village_mauja
  is 'Village/Mauja Name';
comment on column TB_AS_ASSESMENT_MAST.ass_address
  is 'Property Address';
comment on column TB_AS_ASSESMENT_MAST.loc_id
  is 'Location  Foregin Key(TB_LOCATION_MAS)';
comment on column TB_AS_ASSESMENT_MAST.ass_pincode
  is 'Pincode';
comment on column TB_AS_ASSESMENT_MAST.ass_corr_address
  is 'Correspondence Address';
comment on column TB_AS_ASSESMENT_MAST.ass_corr_pincode
  is 'Correspondence Address Pincode';
comment on column TB_AS_ASSESMENT_MAST.ass_corr_email
  is 'Correspondence EMAIL';
comment on column TB_AS_ASSESMENT_MAST.ass_lp_receipt_no
  is 'Last Payment Receipt No.';
comment on column TB_AS_ASSESMENT_MAST.ass_lp_receipt_amt
  is 'Last Payment Receipt No.';
comment on column TB_AS_ASSESMENT_MAST.ass_lp_receipt_date
  is 'Last Payment Receipt Date';
comment on column TB_AS_ASSESMENT_MAST.ass_lp_year
  is 'Last Payment Year';
comment on column TB_AS_ASSESMENT_MAST.ass_lp_bill_cycle
  is 'Last Payment Billing Cycle';
comment on column TB_AS_ASSESMENT_MAST.ass_acq_date
  is 'Acquisition Date';
comment on column TB_AS_ASSESMENT_MAST.ass_prop_type1
  is 'Property Type';
comment on column TB_AS_ASSESMENT_MAST.ass_prop_type2
  is 'Property SubType';
comment on column TB_AS_ASSESMENT_MAST.ass_prop_type3
  is 'Property SubType';
comment on column TB_AS_ASSESMENT_MAST.ass_plot_area
  is 'Total Plot Area';
comment on column TB_AS_ASSESMENT_MAST.ass_buit_area_gr
  is 'Build Up/Constructed Area on the Ground Floor';
comment on column TB_AS_ASSESMENT_MAST.ass_owner_type
  is 'Owner Type (Prefix OWT)';
comment on column TB_AS_ASSESMENT_MAST.ass_ward1
  is 'Ward1';
comment on column TB_AS_ASSESMENT_MAST.ass_ward2
  is 'Ward2';
comment on column TB_AS_ASSESMENT_MAST.ass_ward3
  is 'Ward3';
comment on column TB_AS_ASSESMENT_MAST.ass_ward4
  is 'Ward4';
comment on column TB_AS_ASSESMENT_MAST.ass_ward5
  is 'Ward5';
comment on column TB_AS_ASSESMENT_MAST.ass_gis_id
  is 'Gis Id';
comment on column TB_AS_ASSESMENT_MAST.ass_active
  is 'flag to identify whether the record is deleted or not. ''y''  for not deleted (active) record and ''n'' for deleted (inactive) . ';
comment on column TB_AS_ASSESMENT_MAST.ass_status
  is 'Assesment flow Status';
comment on column TB_AS_ASSESMENT_MAST.ass_aut_status
  is 'authorisation status';
comment on column TB_AS_ASSESMENT_MAST.ass_aut_by
  is 'authorisation by (empid)';
comment on column TB_AS_ASSESMENT_MAST.ass_aut_date
  is 'authorisation date';
comment on column TB_AS_ASSESMENT_MAST.orgid
  is 'orgnisation id';
comment on column TB_AS_ASSESMENT_MAST.created_by
  is 'user id who created the record';
comment on column TB_AS_ASSESMENT_MAST.created_date
  is 'record creation date';
comment on column TB_AS_ASSESMENT_MAST.updated_by
  is 'user id who update the data';
comment on column TB_AS_ASSESMENT_MAST.updated_date
  is 'date on which data is going to update';
comment on column TB_AS_ASSESMENT_MAST.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_AS_ASSESMENT_MAST.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_AS_ASSESMENT_MAST
  add constraint PK_ASS_ID primary key (ASS_ID)
  using index;
alter table TB_AS_ASSESMENT_MAST
  add constraint FK_ASS_LOC_ID foreign key (LOC_ID)
  references TB_LOCATION_MAS (LOC_ID);

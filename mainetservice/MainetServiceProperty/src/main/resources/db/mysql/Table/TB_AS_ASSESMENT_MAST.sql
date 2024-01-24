create table TB_AS_ASSESMENT_MAST
(  ass_id              BIGINT not null comment 'primary key',
  ass_no              NVARCHAR(20) not null comment 'Assesment Number',
  tpp_approval_no     NVARCHAR(200) not null comment 'Building Approval No',
  ass_oldpropno       NVARCHAR(20) not null comment 'Old Property Number',
  tpp_plot_no_cs      NVARCHAR(50) not null comment 'CSN/Khasara No',
  tpp_survey_number   NVARCHAR(25) not null comment 'Survey Number',
  tpp_khata_no        NVARCHAR(50) not null comment 'Khata Number',
  tpp_toji_no         NVARCHAR(50) not null comment 'Toji Number',
  tpp_plot_no         NVARCHAR(50) not null comment 'Plot Number',
  ass_street_no       NVARCHAR(500) not null comment 'Street Number/Name',
  tpp_village_mauja   NVARCHAR(50) not null ,
  ass_address         NVARCHAR(1000) comment 'Property Address',
  loc_id              BIGINT not null not null comment 'Location  Foregin Key(TB_LOCATION_MAS)',
  ass_pincode         INT not null comment 'Pincode',
  ass_corr_address    NVARCHAR(1000) not null comment 'Correspondence Address',
  ass_corr_pincode    INT not null comment 'Correspondence Address Pincode' ,
  ass_corr_email      NVARCHAR(25) not null comment 'Correspondence EMAIL',
  ass_lp_receipt_no   NVARCHAR(25) comment 'Last Payment Receipt No.',
  ass_lp_receipt_amt  DECIMAL(15,2) ,
  ass_lp_receipt_date DATETIME comment 'Last Payment Receipt Date',
  ass_lp_year         BIGINT comment 'Last Payment Year',
  ass_lp_bill_cycle   BIGINT comment 'Last Payment Billing Cycle',
  ass_acq_date        DATETIME not null comment 'Acquisition Date',
  ass_prop_type1      BIGINT not null comment 'Property Type',
  ass_prop_type2      BIGINT not null comment 'Property SubType',
  ass_prop_type3      BIGINT not null comment 'Property SubType',
  ass_plot_area       DECIMAL(15,2) not null comment 'Total Plot Area',
  ass_buit_area_gr    DECIMAL(15,2) not null comment 'Build Up/Constructed Area on the Ground Floor',
  ass_owner_type      BIGINT not null comment 'Owner Type (Prefix OWT)',
  ass_ward1           BIGINT comment 'Ward1',
  ass_ward2           BIGINT comment 'Ward2',
  ass_ward3           BIGINT comment 'Ward3',
  ass_ward4           BIGINT comment 'Ward4',
  ass_ward5           BIGINT comment 'Ward5',
  ass_gis_id          VARCHAR(20) comment 'Gis Id',
  ass_active          CHAR(1) default 'Y' not null comment 'flag to identify whether the record is deleted or not. ''y''  for not deleted (active) record and ''n'' for deleted (inactive) . ',
  ass_status          CHAR(1) not null comment 'Assesment flow Status',
  ass_aut_status      CHAR(1) comment 'authorisation status',
  ass_aut_by          BIGINT comment 'authorisation by (empid)',
  ass_aut_date        DATETIME comment 'authorisation date',
  orgid               BIGINT not null comment 'orgnisation id',
  created_by          INT(12) not null comment 'user id who created the record',
  created_date        DATETIME not null comment 'record creation date',
  updated_by          INT(12) comment 'user id who update the data',
  updated_date        DATETIME comment 'date on which data is going to update',
  lg_ip_mac           VARCHAR(100) comment 'client machine?s login name | ip address | physical address',
  lg_ip_mac_upd       VARCHAR(100) comment 'updated client machine?s login name | ip address | physical address');
  


;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;

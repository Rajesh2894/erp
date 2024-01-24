--liquibase formatted sql
--changeset Kanchan:V20211102112547__CR_tb_fm_complain_register_hist_02112021.sql
CREATE TABLE tb_fm_complain_register_hist (
  cmplnt_id_h bigint(12) NOT NULL COMMENT 'primary key history',
  cmplnt_id bigint(12) DEFAULT NULL COMMENT 'cmplnt_id primary key',
  cmplnt_no varchar(20) DEFAULT NULL COMMENT 'complaint number',
  date date DEFAULT NULL COMMENT 'complaint registration date',
  time time DEFAULT NULL COMMENT 'complaint registration time',
  caller_name varchar(100) DEFAULT NULL COMMENT 'complaint caller name',
  caller_add varchar(1000) DEFAULT NULL COMMENT 'complaint caller address',
  caller_mobile_no varchar(15) DEFAULT NULL COMMENT 'complaint caller mobile no.',
  incident_location varchar(300) DEFAULT NULL COMMENT 'location of incident ',
  incident_desc varchar(300) DEFAULT NULL COMMENT 'details incident description',
  complaint_fee double(12,2) DEFAULT NULL COMMENT 'charges if incident out side tmc area',
  operator_remarks varchar(300) DEFAULT NULL COMMENT 'fire operators remark',
  complaint_status varchar(15) DEFAULT NULL COMMENT 'complaint approved or rejected or closed by hod',
  hod_remarks varchar(300) DEFAULT NULL COMMENT 'fire hod or sr officer remark',
  no_of_injury_male bigint(12) DEFAULT NULL COMMENT 'while complaint close enter male details no of injury',
  no_of_injury_female bigint(12) DEFAULT NULL COMMENT 'while complaint close enter female details no of injury',
  no_of_injury_child bigint(12) DEFAULT NULL COMMENT 'while complaint close enter child details no of injury',
  no_of_death_male bigint(12) DEFAULT NULL COMMENT 'while complaint close enter male details no of death',
  no_of_death_female bigint(12) DEFAULT NULL COMMENT 'while complaint close enter female details no of death',
  no_of_death_child bigint(12) DEFAULT NULL COMMENT 'while complaint close enter child details no of death',
  closer_remarks varchar(300) DEFAULT NULL COMMENT 'while complaint close enter remarks by officers',
  cpd_fire_station bigint(12) DEFAULT NULL COMMENT 'fire station',
  caller_area varchar(1) DEFAULT NULL COMMENT 'caller area is inside or outside ulb',
  no_of_dept_injury_male bigint(12) DEFAULT NULL COMMENT 'male details no of injury of depepartment',
  no_of_dept_injury_female bigint(12) DEFAULT NULL COMMENT 'female details no of injury of depepartment',
  no_of_dept_death_male bigint(12) DEFAULT NULL COMMENT 'male details no of death of depepartment',
  no_of_dept_death_female bigint(12) DEFAULT NULL COMMENT 'female details no of death of depepartment',
  cpd_call_type bigint(12) DEFAULT NULL COMMENT 'call type',
  name_of_officer varchar(100) DEFAULT NULL COMMENT 'name of officer',
  cpd_reson_of_fire bigint(12) DEFAULT NULL COMMENT 'reson of fire',
  name_of_owner varchar(100) DEFAULT NULL COMMENT 'name of owner',
  name_of_occupier varchar(100) DEFAULT NULL COMMENT 'name of occupier',
  cpd_nature_of_call bigint(12) DEFAULT NULL COMMENT 'nature of call',
  cpd_reason_of_fire bigint(12) DEFAULT NULL COMMENT 'reason of fire',
  fire_stations_attend_call varchar(100) DEFAULT NULL COMMENT 'fire station',
  call_attend_date date DEFAULT NULL COMMENT 'call attend date',
  call_attend_time time DEFAULT NULL COMMENT 'call attend time',
  reason_for_delay varchar(100) DEFAULT NULL COMMENT 'reason for delay',
  rescued_without_fire_dept_male bigint(12) DEFAULT NULL COMMENT 'rescued without fire dept male',
  rescued_without_fire_dept_female bigint(12) DEFAULT NULL COMMENT 'rescued without fire dept female',
  rescued_with_fire_dept_male bigint(12) DEFAULT NULL COMMENT 'rescued with fire dept male',
  rescued_with_fire_dept_female bigint(12) DEFAULT NULL COMMENT 'rescued with fire dept female',
  rescued_with_fire_dept_veheicle_male bigint(12) DEFAULT NULL COMMENT 'rescued with fire dept veheicle male',
  rescued_with_fire_dept_veheicle_female bigint(12) DEFAULT NULL COMMENT 'rescued with fire dept veheicle female',
  call_closed_date date DEFAULT NULL COMMENT 'call close date',
  call_closed_time time DEFAULT NULL COMMENT 'call close time',
  call_attend_employee varchar(200) DEFAULT NULL COMMENT 'call attend by employee',
  orgid bigint(12) DEFAULT NULL COMMENT 'organization id',
  created_by bigint(12) DEFAULT NULL COMMENT 'login user id',
  created_date datetime DEFAULT NULL COMMENT 'login user date',
  updated_by bigint(12) DEFAULT NULL COMMENT 'login user id after modify record',
  updated_date datetime DEFAULT NULL COMMENT 'login user date after modify record',
  lg_ip_mac varchar(100) DEFAULT NULL COMMENT 'login user machine id',
  lg_ip_mac_upd varchar(100) DEFAULT NULL COMMENT 'login user machine id after modify record',
  h_status varchar(100) DEFAULT NULL COMMENT 'history status',
  PRIMARY KEY (cmplnt_id_h)
) ;

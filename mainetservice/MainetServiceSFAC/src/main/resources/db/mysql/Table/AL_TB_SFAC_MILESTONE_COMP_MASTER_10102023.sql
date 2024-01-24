Alter table TB_SFAC_MILESTONE_COMP_MASTER
add column fm_ref_id varchar(50) default null,
add column fm_amt_app_dt datetime default null ,
add column fm_amt_approved decimal(15,2) default null;
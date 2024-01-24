--liquibase formatted sql
--changeset Kanchan:V20220107194016__CR_TB_LGL_ADV_EDUDETAILS_07012022.sql
create table TB_LGL_ADV_EDUDETAILS (
edu_id	bigint(12)	PRIMARY KEY NOT NULL,
adv_id	bigint(12)  NOT  NULL,
qualification_course	varchar(500)	DEFAULT NULL,	
institute_state	Varchar(500)	DEFAULT NULL,	
board_university 	Varchar(500)	DEFAULT NULL,	
result	Varchar(50)	DEFAULT NULL,	
passing_Year	Bigint(12)	DEFAULT NULL,	
percentage	decimal(15,2)	DEFAULT NULL,	
orgid	bigint(12)	NOT  NULL,	
created_by	bigint(12)	NOT  NULL,	
created_date	datetime	NOT  NULL,	
updated_by	bigint(12)	DEFAULT NULL,	
updated_date	datetime	DEFAULT NULL,	
lg_ip_mac	varchar(100)	NOT  NULL,	
lg_ip_mac_upd	varchar(100)	DEFAULT NULL,
FOREIGN KEY(adv_id) REFERENCES tb_lgl_advocate_mas (adv_id));

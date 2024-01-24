--liquibase formatted sql
--changeset Kanchan:V20210218172122__CR_tb_cfc_tree_Info_18022021.sql
create table tb_cfc_tree_Info(
app_id bigInt(12)PRIMARY KEY not null,
apm_application_id bigInt(20) not null,	
aplicant_name varchar(50) not null,	
mob_number bigInt(12) not null,	
email_id varchar(50) not null,	
address varchar(100) not null,	
tree_desc varchar(100) not null,	
tree_count bigInt(10) not null,	
reason varchar(100)	not null,	
location varchar(50) not null,	
orgId  bigInt(12) not null,
CREATED_BY bigint(10) not null,	
CREATED_DATE datetime not null,	
LG_IP_MAC varchar(100) not null,	
LG_IP_MAC_UPD varchar(100) null,	
UPDATED_BY bigint(12) null,	
UPDATED_DATE datetime null);









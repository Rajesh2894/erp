--liquibase formatted sql
--changeset Kanchan:V20210209120621__CR_tb_cfc_hospital_info_09022021.sql
CREATE TABLE tb_cfc_hospital_info
(hsptl_id bigInt(12) Primary Key NOT NULL,
APM_APPLICATION_ID bigInt(20) NOT NULL,
hsptl_type bigInt(12) NOT NULL,
doct_name varchar(100) NOT NULL,
mtrnty_bed_cnt int(5) NOT NULL,
othr_bed_cnt int(5) NOT NULL,
nrsng_bed_cnt int(5) NOT NULL,
totl_bed_cnt int(5) NOT NULL,
nm_add_clnc varchar(100) NOT NULL,
nm_add_hsptl varchar(100) NOT NULL,
cont_no_clinic bigInt(12) NOT NULL,
cont_no_hsptl bigInt(12) NOT NULL,
edu_elg varchar(100) NOT NULL,
reg_no varchar(50) NOT NULL,
reg_date datetime NOT NULL,
doct_cnt int(5) NOT NULL,
nurse_cnt int(5) NOT NULL,
emp_cnt int(5) NOT NULL,
sec_cnt int(5) NOT NULL,
yers_opertn int(5) NOT NULL,
reg_mnp char(1) NOT NULL,
abrtn_cntr_flag char(1) NOT NULL,
vstng_doctr_nm varchar(50) NOT NULL,
dept bigInt(12) NOT NULL,
reg_nota_fbr_des char(1) NOT NULL);
--liquibase formatted sql
--changeset Kanchan:V20210209120621__CR_tb_cfc_hospital_info_090220211.sql
CREATE TABLE tb_cfc_hospital_info_det 
   (hostl_det_id bigInt(12) NOT NULL,
   hsptl_id bigInt(12) NOT NULL,
  prog_id bigInt(12)NOT NULL,
    PRIMARY KEY (hostl_det_id),
  CONSTRAINT FK_hsptl_id FOREIGN KEY (hsptl_id) REFERENCES tb_cfc_hospital_info (hsptl_id)
);


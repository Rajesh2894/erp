--liquibase formatted sql
--changeset priya:V20180208180311__AL_tb_as_pro_assesment_owner_dtl.sql
ALTER TABLE tb_as_pro_assesment_owner_dtl 
CHANGE COLUMN pro_asso_addharno pro_asso_addharno BIGINT(20) NULL COMMENT '	Owner Addhar  No	' ;

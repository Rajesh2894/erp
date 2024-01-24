--liquibase formatted sql
--changeset Kanchan:V20210318171239__AL_TB_AS_PRO_ASSESMENT_OWNER_DTL_18032021.sql
ALTER Table TB_AS_PRO_ASSESMENT_OWNER_DTL add column
 PRO_asso_owner_name_reg varchar(50) DEFAULT NULL;

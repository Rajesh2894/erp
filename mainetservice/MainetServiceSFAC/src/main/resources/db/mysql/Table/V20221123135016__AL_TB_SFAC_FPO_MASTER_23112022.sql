--liquibase formatted sql
--changeset Kanchan:V20221123135016__AL_TB_SFAC_FPO_MASTER_23112022.sql
Alter table TB_SFAC_FPO_MASTER
modify column FPO_ADHAR_NO  varchar(100),
modify column FPO_OFF_ADDR varchar(300);
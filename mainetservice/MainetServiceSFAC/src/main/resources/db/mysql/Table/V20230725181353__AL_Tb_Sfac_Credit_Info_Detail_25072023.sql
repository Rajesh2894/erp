--liquibase formatted sql
--changeset PramodPatil:V20230725181353__AL_Tb_Sfac_Credit_Info_Detail_25072023.sql
Alter Table Tb_Sfac_Credit_Info_Detail modify column STATUS BigInt(20) null default null;

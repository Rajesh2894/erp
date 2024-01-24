--liquibase formatted sql
--changeset Kanchan:V20210810104605__AL_TB_LOI_MAS_10082021.sql
alter table TB_LOI_MAS add column LOI_PAY_MODE    varchar(100);
--liquibase formatted sql
--changeset Kanchan:V20210810104605__AL_TB_LOI_MAS_100820211.sql
alter table TB_LOI_MAS_HIST add column LOI_PAY_MODE    varchar(100);

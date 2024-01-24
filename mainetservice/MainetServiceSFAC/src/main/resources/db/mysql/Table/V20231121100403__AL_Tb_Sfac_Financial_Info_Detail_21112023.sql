--liquibase formatted sql
--changeset PramodPatil:V20231121100403__AL_Tb_Sfac_Financial_Info_Detail_21112023.sql
alter table Tb_Sfac_Financial_Info_Detail
add column REVENUE_INPUT decimal(15,2) null default null,
add column REVENUE_OUTPUT decimal(15,2) null default null,
add column REVENUE_OTHERS decimal(15,2) null default null,
add column REVENUE_TOTAL decimal(15,2) null default null,
add column MONTH varchar(25) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231121100403__AL_Tb_Sfac_Financial_Info_Detail_211120231.sql
alter table Tb_Sfac_Financial_Info_Detail_Hist
add column REVENUE_INPUT decimal(15,2) null default null,
add column REVENUE_OUTPUT decimal(15,2) null default null,
add column REVENUE_OTHERS decimal(15,2) null default null,
add column REVENUE_TOTAL decimal(15,2) null default null,
add column MONTH varchar(25) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231121100403__AL_Tb_Sfac_Financial_Info_Detail_211120232.sql
alter table tb_sfac_license_info_detail
add column LICENSE_STATUS BigInt(20) null default null,
add column LIC_DATE_OF_APPLIED datetime null default null;
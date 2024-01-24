--liquibase formatted sql
--changeset Kanchan:V20230621123527__CR_Tb_Sfac_Equity_Grant_tranche_det_21062023.sql
create table Tb_Sfac_Equity_Grant_tranche_det
(
EGTR_ID BigInt (20) not null,
EG_ID BigInt (20) not null,
TRANCHE_NO BigInt (20) null default null,
NO_SHARE_AVAIL_EQUITY BigInt (20) null default null,
EQUITY_GRT_AVAIL Decimal (15,2) null default null,
FRESH_MEM_ENROLL BigInt (20) null default null,
FRESH_CAP_RAISED Decimal (15,2) null default null,
ORGID BigInt (20) not null,
CREATED_DATE DateTime not null,
CREATED_BY BigInt(20) not null,
UPDATED_DATE DateTime null default null,
UPDATED_BY BigInt (20) null default null,
LG_IP_MAC varchar(100) not null,
LG_IP_MAC_UPD varchar(100) null default null,
primary key(EGTR_ID)
);
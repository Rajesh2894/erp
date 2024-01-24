--liquibase formatted sql
--changeset nilima:V20180123183905__TB_AS_ASSESMENT_FACTOR_DTL1.sql
drop table IF EXISTS TB_AS_ASSESMENT_FACTOR_DTL;

--liquibase formatted sql
--changeset nilima:V20180123183905__TB_AS_ASSESMENT_FACTOR_DTL2.sql
create table TB_AS_ASSESMENT_FACTOR_DTL
( MN_assf_id           BIGINT(12) not null,
  MN_assd_id           BIGINT(12) not null,
  MN_ass_id            BIGINT(12) not null,
  MN_assf_factor       BIGINT not null,
  MN_assf_factor_id    BIGINT not null,
  MN_assf_factor_value_id BIGINT not null,
  MN_assf_active       CHAR(1) default 'Y' not null,
  orgid             BIGINT(12) not null,
  created_by        BIGINT(12) not null,
  created_date      DATETIME not null,
  updated_by        BIGINT(12),
  updated_date      DATETIME,
  lg_ip_mac         VARCHAR(100),
  lg_ip_mac_upd     VARCHAR(100),
  MN_ASSO_START_DATE	date,
  MN_ASSO_END_DATE	date);
  
--liquibase formatted sql
--changeset nilima:V20180123183905__TB_AS_ASSESMENT_FACTOR_DTL3.sql
  alter table TB_AS_ASSESMENT_FACTOR_DTL  add constraint PK_MN_assf_id primary key (MN_assf_id);

  --liquibase formatted sql
--changeset nilima:V20180123183905__TB_AS_ASSESMENT_FACTOR_DTL4.sql
alter table TB_AS_ASSESMENT_FACTOR_DTL    add constraint FK_factor_MN_assd_id foreign key (MN_assd_id)
  references TB_AS_ASSESMENT_DETAIL (MN_assd_id);

--liquibase formatted sql
--changeset nilima:V20180123183905__TB_AS_ASSESMENT_FACTOR_DTL5.sql
alter table TB_AS_ASSESMENT_FACTOR_DTL    add constraint FK_factor_MN_ASS_ID foreign key (MN_ASS_ID)
  references TB_AS_ASSESMENT_MAST (MN_ASS_ID);


--liquibase formatted sql
--changeset nilima:V20170426125444__AL_TB_LOI_MAS.sql
alter table TB_LOI_MAS modify loi_status char(1);
--changeset nilima:V20170426125444__AL_TB_LOI_MAS1.sql
alter table TB_LOI_MAS add created_by NUMBER(12);
--changeset nilima:V20170426125444__AL_TB_LOI_MAS.sql
alter table TB_LOI_MAS add created_date DATE;
--changeset nilima:V20170426125444__AL_TB_LOI_MAS2.sql 
comment on column TB_LOI_MAS.loi_del_remark
  is 'Loi Deletion Remark';
--changeset nilima:V20170426125444__AL_TB_LOI_MAS3.sql
comment on column TB_LOI_MAS.loi_status
  is 'Loi Status ';
--changeset nilima:V20170426125444__AL_TB_LOI_MAS4.sql
comment on column TB_LOI_MAS.created_by
  is 'user identity';
--changeset nilima:V20170426125444__AL_TB_LOI_MAS5.sql
comment on column TB_LOI_MAS.created_date
  is 'Creation Date';

--liquibase formatted sql
--changeset nilima:V20180123183723__TB_AS_BILL_SCHEDULE_DET1.sql
drop table IF EXISTS TB_AS_BILL_SCHEDULE_DET; 

--liquibase formatted sql
--changeset nilima:V20180123183723__TB_AS_BILL_SCHEDULE_DET2.sql
create table TB_AS_BILL_SCHEDULE_DET (
SCH_detid	bigint(20)	,
as_bill_scheid bigint(20) ,
BILL_FROM_DATE	datetime	,
BILL_TO_DATE	datetime	,
ORGID	BIGINT(12)	 not null,
CREATED_BY	int(11)	not null,
CREATED_Date	datetime not null,
UPDATED_BY	BIGINT	,
UPDATED_DATE	datetime ,
STATUS	varchar(100),
Cal_frm_dt	int(11),
No_of_Days	int(11) );


--liquibase formatted sql
--changeset nilima:V20180123183723__TB_AS_BILL_SCHEDULE_DET3.sql
alter table TB_AS_BILL_SCHEDULE_DET  add constraint PK_SCH_detid primary key (SCH_detid);

--liquibase formatted sql
--changeset nilima:V20180123183723__TB_AS_BILL_SCHEDULE_DET4.sql
alter table TB_AS_BILL_SCHEDULE_DET  add constraint FK_SCH_DET_as_bill_scheid foreign key (as_bill_scheid)
references  TB_AS_BILL_SCHEDULE_MAST  (as_bill_scheid);

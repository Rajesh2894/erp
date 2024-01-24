--liquibase formatted sql
--changeset nilima:V20171214130602__TB_AS_BILL_SCHEDULE_DET.sql
create table TB_AS_BILL_SCHEDULE_DET (
SCH_detid	bigint(20)	,
as_bill_scheid bigint(20) ,
BILL_FROM_DATE	int(11)	,
BILL_TO_DATE	int(11)	,
ORGID	int(11)	 not null,
USER_ID	int(11)	not null,
LMODDATE	datetime not null,
UPDATED_BY	BIGINT	,
UPDATED_DATE	datetime ,
STATUS	varchar(100) );

--liquibase formatted sql
--changeset nilima:V20171214130602__TB_AS_BILL_SCHEDULE_DET1.sql
alter table TB_AS_BILL_SCHEDULE_DET  add constraint PK_SCH_detid primary key (SCH_detid);

--liquibase formatted sql
--changeset nilima:V20171214130602__TB_AS_BILL_SCHEDULE_DET2.sql
alter table TB_AS_BILL_SCHEDULE_DET  add constraint FK_SCH_DET_as_bill_scheid foreign key (as_bill_scheid)
references  TB_AS_BILL_SCHEDULE_MAST  (as_bill_scheid);



alter table TB_FINCIALYEARORG_MAP add fa_frommonth INT(3) comment 'Fiscale From  Month';
alter table TB_FINCIALYEARORG_MAP add fa_tomonth INT(3) comment 'Fiscale To  Month';
alter table TB_FINCIALYEARORG_MAP add fa_fromyear INT(4) comment 'Fiscale From Year';
alter table TB_FINCIALYEARORG_MAP add fa_toyear INT(4) comment 'Fiscale To Year';
alter table TB_FINCIALYEARORG_MAP add fa_monstatus INT(12) comment 'Fiscale "SOFT" close';
alter table TB_FINCIALYEARORG_MAP add fa_yearstatus INT(12) comment 'Fiscale "Hard" close'; 
alter table TB_FINCIALYEARORG_MAP drop column ya_type_cpd_id;
alter table TB_FINCIALYEARORG_MAP drop column lang_id;

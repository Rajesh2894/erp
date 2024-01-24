CREATE TABLE TEST (
  T2_ID int(11) NOT NULL AUTO_INCREMENT,
  T2_ORGID bigint(12) DEFAULT NULL,
  T2_billfreq bigint(12) DEFAULT NULL,
  T2_billfreqnm varchar(45) DEFAULT NULL,
  T2_nfafrommon bigint(12) DEFAULT NULL,
  T2_nfatomon bigint(12) DEFAULT NULL,
  T2_VORG bigint(12) DEFAULT NULL,
  PRIMARY KEY (T2_ID)
) ENGINE=InnoDB AUTO_INCREMENT=1331 DEFAULT CHARSET=utf8;

CREATE TABLE wbillfreq (
  orgid bigint(12) NOT NULL,
  freqid bigint(12) DEFAULT NULL,
  freqname varchar(45) DEFAULT NULL,
  PRIMARY KEY (orgid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into wbillfreq (orgid,freqid,frename) values (18,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=18 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (99,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=99 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (59,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=59 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (106,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=106 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (146,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=146 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (50,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=50 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (58,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=58 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (62,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=62 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (171,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=171 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (173,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=173 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (33,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=33 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (91,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=91 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (166,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=166 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (48,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=48 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (61,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=61 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (174,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=174 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (149,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=149 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (8,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=8 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (34,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=34 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (54,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=54 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (65,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=65 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (66,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=66 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (67,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=67 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (68,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=68 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (77,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=77 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (86,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=86 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (92,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=92 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (113,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=113 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (24,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=24 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (26,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=26 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (45,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=45 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (49,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=49 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (82,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=82 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (97,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=97 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (103,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=103 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (109,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=109 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (93,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=93 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (121,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=121 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (122,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=122 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (123,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=123 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (172,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=172 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (176,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=176 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (127,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=127 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (15,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=15 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (39,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=39 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (46,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=46 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (116,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=116 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (135,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=135 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (30,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=30 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (73,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=73 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (158,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=158 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (17,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=17 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (57,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=57 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (60,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=60 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (76,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=76 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (85,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=85 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (104,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=104 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (118,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=118 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (162,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=162 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (133,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=133 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (143,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=143 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (25,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=25 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (40,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=40 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (111,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=111 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (120,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=120 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (168,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=168 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (141,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=141 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (147,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=147 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (36,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=36 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (175,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=175 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (115,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=115 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (154,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=154 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (163,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=163 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (137,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=137 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (138,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=138 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (148,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=148 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (20,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=20 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (31,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=31 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (41,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=41 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (51,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=51 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (98,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=98 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (100,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=100 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (110,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=110 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (10,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=10 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (71,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=71 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (161,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=161 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (157,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=157 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (131,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=131 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (140,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=140 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (9,(select  cpd_id  from tb_comparam_det where cpd_desc='Half Yearly' and orgid=9 and cpd_status='A'),'Half Yearly');
insert into wbillfreq (orgid,freqid,frename) values (47,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=47 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (74,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=74 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (164,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=164 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (144,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=144 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (79,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=79 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (90,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=90 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (107,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=107 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (112,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=112 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (117,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=117 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (134,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=134 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (78,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=78 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (102,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=102 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (23,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=23 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (55,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=55 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (21,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=21 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (56,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=56 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (75,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=75 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (87,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=87 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (105,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly' and orgid=105 and cpd_status='A'),'Monthly');
insert into wbillfreq (orgid,freqid,frename) values (108,(select  cpd_id  from tb_comparam_det where cpd_desc='Yearly' and orgid=108 and cpd_status='A'),'Yearly');
insert into wbillfreq (orgid,freqid,frename) values (88,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly ' and orgid=88 and cpd_status='A'),'Monthly ');
insert into wbillfreq (orgid,freqid,frename) values (151,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly ' and orgid=151 and cpd_status='A'),'Monthly ');
insert into wbillfreq (orgid,freqid,frename) values (129,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly ' and orgid=129 and cpd_status='A'),'Monthly ');
insert into wbillfreq (orgid,freqid,frename) values (64,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly ' and orgid=64 and cpd_status='A'),'Monthly ');
insert into wbillfreq (orgid,freqid,frename) values (38,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly ' and orgid=38 and cpd_status='A'),'Monthly ');
insert into wbillfreq (orgid,freqid,frename) values (70,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly ' and orgid=70 and cpd_status='A'),'Monthly ');
insert into wbillfreq (orgid,freqid,frename) values (80,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly ' and orgid=80 and cpd_status='A'),'Monthly ');
insert into wbillfreq (orgid,freqid,frename) values (94,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly ' and orgid=94 and cpd_status='A'),'Monthly ');
insert into wbillfreq (orgid,freqid,frename) values (150,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly and yearly' and orgid=150 and cpd_status='A'),'Monthly and yearly');
insert into wbillfreq (orgid,freqid,frename) values (22,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly ' and orgid=22 and cpd_status='A'),'Monthly ');
insert into wbillfreq (orgid,freqid,frename) values (32,(select  cpd_id  from tb_comparam_det where cpd_desc='yearly ' and orgid=32 and cpd_status='A'),'yearly ');
insert into wbillfreq (orgid,freqid,frename) values (37,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly ' and orgid=37 and cpd_status='A'),'Monthly ');
insert into wbillfreq (orgid,freqid,frename) values (44,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly ' and orgid=44 and cpd_status='A'),'Monthly ');
insert into wbillfreq (orgid,freqid,frename) values (52,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly ' and orgid=52 and cpd_status='A'),'Monthly ');
insert into wbillfreq (orgid,freqid,frename) values (167,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly ' and orgid=167 and cpd_status='A'),'Monthly ');
insert into wbillfreq (orgid,freqid,frename) values (132,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly ' and orgid=132 and cpd_status='A'),'Monthly ');
insert into wbillfreq (orgid,freqid,frename) values (7,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly ' and orgid=7 and cpd_status='A'),'Monthly ');
insert into wbillfreq (orgid,freqid,frename) values (11,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly ' and orgid=11 and cpd_status='A'),'Monthly ');
insert into wbillfreq (orgid,freqid,frename) values (152,(select  cpd_id  from tb_comparam_det where cpd_desc='Monthly ' and orgid=152 and cpd_status='A'),'Monthly ');


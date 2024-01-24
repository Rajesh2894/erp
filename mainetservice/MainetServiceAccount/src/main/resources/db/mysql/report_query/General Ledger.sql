select x.VOU_POSTING_DATE,x.Particulars,x.VOU_NO,x.VAMT_DR,x.VAMT_CR ,y.OPENBAL_AMT,y.CPD_ID_DRCR from
(select vd.VOU_POSTING_DATE,vd.Particulars,vd.VOU_NO,vd.VAMT_DR,vd.VAMT_CR,vd.SAC_HEAD_ID,f.FA_YEARID,vd.ORGID
from vw_voucher_detail vd,tb_financialyear f 
where vd.ORGID=1515 
and vd.VOU_POSTING_DATE between  '2017-04-01' AND '2018-06-30' 
and vd.SAC_HEAD_ID=21
and f.FA_YEARID=1533)x
left join
(select b.OPENBAL_AMT,b.CPD_ID_DRCR,b.FA_YEARID,b.SAC_HEAD_ID,b.ORGID from tb_ac_bugopen_balance b,tb_financialyear f  
where  b.FA_YEARID = f.FA_YEARID )y
on x.FA_YEARID=y.FA_YEARID
and x.SAC_HEAD_ID = y.SAC_HEAD_ID;
select  ac_head_code, SAC_HEAD_ID,
		openbal_amt_DR,
      openbal_amt_CR,
      VAMT_CR , 
      VAMT_DR
      
from
  (SELECT  
    vd.ac_head_code ac_head_code,
    sum(vd.VAMT_CR) VAMT_CR, 
    sum(vd.VAMT_DR) VAMT_DR,
    vd.SAC_HEAD_ID SAC_HEAD_ID
    FROM  vw_voucher_detail vd 
      WHERE vd.VOU_POSTING_DATE  between '2012-04-01' and '2013-03-31' and
       vd.orgid=1515
      GROUP BY vd.ac_head_code,vd.SAC_HEAD_ID) a
  LEFT JOIN
        (  SELECT bg.SAC_HEAD_ID SAC_HEAD_ID1,c.AC_HEAD_CODE ac_head_code1,
              (case when cd.CPD_VALUE in ('DR' ) then bg.OPENBAL_AMT else 0 end )as openbal_amt_DR,
              (case when cd.CPD_VALUE in ('CR') then bg.OPENBAL_AMT else 0 end ) as openbal_amt_CR 
         FROM tb_ac_bugopen_balance bg,tb_comparam_det cd , tb_ac_secondaryhead_master b 
          , vw_voucher_detail c
          WHERE   bg.CPD_ID_DRCR = cd.CPD_ID and bg.ORGID=1515 and bg.FA_YEARID=1533
          and b.SAC_HEAD_ID = bg.SAC_HEAD_ID
          and b.ORGID = bg.ORGID
          and c.SAC_HEAD_ID = b.SAC_HEAD_ID
          and c.ORGID = b.ORGID) e
              ON a.sac_head_id=e.sac_head_id1
  UNION 
    select ac_head_code,SAC_HEAD_ID,
      openbal_amt_DR,
      openbal_amt_CR,
      VAMT_CR,
      VAMT_DR
      
   from
  (SELECT  
    vd.ac_head_code ac_head_code1,
    sum(vd.VAMT_CR) VAMT_CR, 
    sum(vd.VAMT_DR) VAMT_DR,
    vd.SAC_HEAD_ID  SAC_HEAD_ID1
    FROM  vw_voucher_detail vd 
      WHERE vd.VOU_POSTING_DATE between '2012-04-01' and '2013-03-31' and
       vd.orgid=1515
      GROUP BY vd.ac_head_code,vd.SAC_HEAD_ID) a
  RIGHT JOIN
       
          (  SELECT bg.SAC_HEAD_ID,c.AC_HEAD_CODE ac_head_code,
              (case when cd.CPD_VALUE in ('DR' ) then bg.OPENBAL_AMT else 0 end )as openbal_amt_DR,
              (case when cd.CPD_VALUE in ('CR') then bg.OPENBAL_AMT else 0 end ) as openbal_amt_CR 
         FROM tb_ac_bugopen_balance bg,tb_comparam_det cd , tb_ac_secondaryhead_master b 
          , vw_voucher_detail c
          WHERE   bg.CPD_ID_DRCR = cd.CPD_ID and bg.ORGID=1515 and bg.FA_YEARID=1533
          and b.SAC_HEAD_ID = bg.SAC_HEAD_ID
          and b.ORGID = bg.ORGID
          and c.SAC_HEAD_ID = b.SAC_HEAD_ID
          and c.ORGID = b.ORGID) e
              ON a.sac_head_id1 = e.sac_head_id
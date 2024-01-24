package com.abm.mainet.water.service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.WaterBillGenErrorDTO;

@Service
public class BillGenerationCalculationServiceImpl implements IBillGenerationCalculationService {

	@Autowired
	private IOrganisationService iOrganisationService;

	@Autowired
	private TbWtBillMasService tbWtBillMasService;

	@Autowired
	private BillMasterCommonService billMasterCommonService;

	@Autowired
	private NewWaterConnectionService iNewWaterConnectionService;

	@Autowired
	private ILocationMasService iLocationMasService;

	private static final Logger LOGGER = Logger.getLogger(BillGenerationCalculationServiceImpl.class);

	@Override
	@Transactional
	public void calculateMonthly(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {

		final Organisation organisation = iOrganisationService.getOrganisationById(runtimeBean.getOrgId().getOrgid());

		List<TbCsmrInfoDTO> entityList = iNewWaterConnectionService
				.getAllConnectionMasterForBillScheduler(runtimeBean.getOrgId().getOrgid());

		if (entityList != null && !entityList.isEmpty()) {
			try {
				Long locationId = null;
				final TbLocationMas locMas = iLocationMasService.findByLocationName(
						ApplicationSession.getInstance().getMessage("location.LocNameEng"),
						runtimeBean.getOrgId().getOrgid());
				if ((locMas.getLocRevenueWZMappingDto() != null) && !locMas.getLocRevenueWZMappingDto().isEmpty()) {
					locationId = locMas.getLocRevenueWZMappingDto().get(0).getCodIdRevLevel1();
				}

				final List<Long> csIdn = entityList.parallelStream()
						.filter(water -> MainetConstants.FlagA.equals(water.getCsIsBillingActive()))
						.map(TbCsmrInfoDTO::getCsIdn).collect(Collectors.toList());
				
				List<Long> billNumbers = tbWtBillMasService.billCalculationAndGeneration(organisation,
						new HashMap<Long, WaterBillGenErrorDTO>(0), entityList, "System Generated Bill",
						runtimeBean.getUserId().getEmpId(), runtimeBean.getLangId(), csIdn, runtimeBean.getLgIpMac(),locationId);
				billMasterCommonService.doVoucherPosting(billNumbers, organisation,
						MainetConstants.DEPT_SHORT_NAME.WATER, runtimeBean.getUserId().getEmpId(), locationId);

			} catch (Exception exp) {
				LOGGER.error("error while scheduling quartz for monthly basis for water bill generation", exp);
				throw new FrameworkException(
						"error while scheduling quartz for monthly basis for water bill generation ", exp);
			}
		} else {
			LOGGER.error("no Classes are available under Monthly");

		}

	}

}

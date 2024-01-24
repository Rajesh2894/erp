package com.abm.mainet.rnl.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.rnl.datamodel.RNLRateMaster;
import com.abm.mainet.rnl.domain.EstateBookingCancel;
import com.abm.mainet.rnl.dto.BookingCancelDTO;
import com.abm.mainet.rnl.dto.EstateBookingDTO;
import com.abm.mainet.rnl.dto.EstatePropResponseDTO;
import com.abm.mainet.rnl.service.BRMSRNLService;
import com.abm.mainet.rnl.service.IEstateBookingService;
import com.abm.mainet.rnl.service.IEstatePropertyService;

@Component
@Scope("session")
public class EstateBookingCancelModel extends AbstractFormModel {

    private static final long serialVersionUID = -7903611958625558578L;

    private EstateBookingDTO estateBookingDTO = null;

    private List<EstateBookingDTO> estateBookings = new ArrayList<>();

    private List<ChargeDetailDTO> chargesInfo;

    private List<BookingCancelDTO> bookingCancelList = new ArrayList<>();

    private List<TbAcVendormaster> vendorList = new ArrayList<>();
    
    private String flag;

    private double amountToPay;

    public EstateBookingDTO getEstateBookingDTO() {
        return estateBookingDTO;
    }

    public void setEstateBookingDTO(EstateBookingDTO estateBookingDTO) {
        this.estateBookingDTO = estateBookingDTO;
    }

    public List<EstateBookingDTO> getEstateBookings() {
        return estateBookings;
    }

    public void setEstateBookings(List<EstateBookingDTO> estateBookings) {
        this.estateBookings = estateBookings;
    }

    public List<ChargeDetailDTO> getChargesInfo() {
        return chargesInfo;
    }

    public void setChargesInfo(List<ChargeDetailDTO> chargesInfo) {
        this.chargesInfo = chargesInfo;
    }

    public double getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(double amountToPay) {
        this.amountToPay = amountToPay;
    }

    public List<BookingCancelDTO> getBookingCancelList() {
        return bookingCancelList;
    }

    public void setBookingCancelList(List<BookingCancelDTO> bookingCancelList) {
        this.bookingCancelList = bookingCancelList;
    }

    public List<TbAcVendormaster> getVendorList() {
        return vendorList;
    }

    public void setVendorList(List<TbAcVendormaster> vendorList) {
        this.vendorList = vendorList;
    }

    @Autowired
    private BRMSCommonService brmsCommonService;

    @Autowired
    private BRMSRNLService brmsRNLService;

    @Autowired
    private IEstatePropertyService iEstatePropertyService;

    @Autowired
    private IEstateBookingService iEstateBookingService;

    @Override
    public boolean saveForm() {
        // update in TB_RL_ESTATE_BOOKING table cancelDate and cancelReason
        // insert in TB_RL_ESTATE_BOOKING_CANCEL table
        List<EstateBookingCancel> estateBookingCancelEntities = new ArrayList<>();
        getBookingCancelList().stream().filter(cancel -> cancel.getRefundAmt().compareTo(BigDecimal.ZERO) > 0)
                .forEach(cancelDto -> {
                    cancelDto.setBookingId(estateBookingDTO.getId());// doing this because booking id present in different DTO
                    cancelDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                    cancelDto.setCreatedDate(new Date());
                    cancelDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                    cancelDto.setLgIpMac(UserSession.getCurrent().getEmployee().getLgIpMac());
                    EstateBookingCancel cancelEntity = new EstateBookingCancel();
                    BeanUtils.copyProperties(cancelDto, cancelEntity);
                    estateBookingCancelEntities.add(cancelEntity);
                });
        iEstateBookingService.updateDataForCancelBooking(estateBookingDTO, estateBookingCancelEntities,
                getBookingCancelList());

        setSuccessMessage(ApplicationSession.getInstance().getMessage("rnl.estate.booking.cancel.success",
                new Object[] { estateBookingDTO.getBookingNo().toString() }));
        return true;
    }

    @SuppressWarnings("unchecked")
    public void findApplicableCheckListAndCharges(final String serviceCode, Long propId, final long orgId) {

        final EstatePropResponseDTO estatePropResponseDTO = iEstatePropertyService
                .findPropertyForBooking(propId, orgId);

        // [START] BRMS call initialize model
        final WSRequestDTO dto = new WSRequestDTO();
        final WSRequestDTO initReqdto = new WSRequestDTO();
        initReqdto.setModelName(MainetConstants.RnLCommon.CHECKLIST_RNLRATEMASTER);

        final WSResponseDTO response = brmsCommonService.initializeModel(initReqdto);

        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            final List<Object> checklistModel = RestClient.castResponse(response, CheckListModel.class, 0);
            final List<Object> rnlRateMasterList = RestClient.castResponse(response, RNLRateMaster.class, 1);

            final CheckListModel checkListModel = (CheckListModel) checklistModel.get(0);
            final RNLRateMaster rnlRateMaster = (RNLRateMaster) rnlRateMasterList.get(0);

            checkListModel.setOrgId(orgId);
            checkListModel.setServiceCode(serviceCode);
            /*
             * checkListModel2.setApplicantType(CommonMasterUtility.getCPDDescription( changeOwnerResponse.getApplicantType(),
             * PrefixConstants.D2KFUNCTION.ENGLISH_DESC));
             */
            /*
             * checkListModel2 = settingUsageSubtype(checkListModel2, changeOwnerResponse,
             * UserSession.getCurrent().getOrganisation());
             */

            dto.setDataModel(checkListModel);

            WSRequestDTO checklistReqDto = new WSRequestDTO();
            checklistReqDto.setDataModel(checkListModel);
            WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);
            if (checklistRespDto.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
                List<DocumentDetailsVO> checkListList = Collections.emptyList();
                checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();// docs;

                long cnt = 1;
                for (final DocumentDetailsVO doc : checkListList) {
                    doc.setDocumentSerialNo(cnt);
                    cnt++;
                }
                if ((checkListList != null) && !checkListList.isEmpty()) {
                    // setCheckList(checkListList);
                }
            }

            // RNL RATE START
            dto.setDataModel(rnlRateMaster);
            final WSRequestDTO taxRequestDto = new WSRequestDTO();
            taxRequestDto.setDataModel(rnlRateMaster);
            WSResponseDTO taxResponseDTO = brmsRNLService.getApplicableTaxes(taxRequestDto);
            if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(taxResponseDTO.getWsStatus())
                    && !taxResponseDTO.isFree()) {
                final List<Object> rates = (List<Object>) taxResponseDTO.getResponseObj();
                final List<RNLRateMaster> requiredCHarges = new ArrayList<>();
                for (final Object rate : rates) {
                    RNLRateMaster rateMaster = null;
                    try {
                        rateMaster = (RNLRateMaster) rate;
                    } catch (Exception e) {
                        throw new FrameworkException("Error when charges calculate:", e);
                    }
                    rateMaster.setOrgId(orgId);
                    rateMaster.setServiceCode(serviceCode);
                    rateMaster.setDeptCode(MainetConstants.RNL_DEPT_CODE);
                    rateMaster.setChargeApplicableAt(
                            CommonMasterUtility.findLookUpDesc(PrefixConstants.LookUpPrefix.CAA,
                                    orgId, Long.parseLong(rnlRateMaster.getChargeApplicableAt())));
                    rateMaster.setTaxSubCategory(
                            getSubCategoryDesc(rateMaster.getTaxSubCategory(), UserSession.getCurrent().getOrganisation()));
                    rateMaster.setRateStartDate(new Date().getTime());
                    rateMaster.setNoOfBookingDays(1l);
                    /*
                     * LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(eventId, orgId, "EVT");
                     * rateMaster.setOccupancyType(lookUp.getLookUpDesc());
                     */
                    rateMaster.setOccupancyType(estatePropResponseDTO.getOccupancy());
                    rateMaster.setFactor4(estatePropResponseDTO.getPropName());
                    if (CollectionUtils.isNotEmpty(rateMaster.getDependsOnFactorList())) {
                        for (String dependFactor : rateMaster.getDependsOnFactorList()) {
                            if (StringUtils.equalsIgnoreCase(dependFactor, "USB")) {
                                rateMaster.setUsageSubtype1(estatePropResponseDTO.getUsage());
                            }
                            if (StringUtils.equalsIgnoreCase(dependFactor, "EST")) {
                                rateMaster.setUsageSubtype2(estatePropResponseDTO.getType());
                            }

                            if (StringUtils.equalsIgnoreCase(dependFactor, "ES")) {
                                rateMaster.setUsageSubtype3(estatePropResponseDTO.getSubType());
                            }
                            /*
                             * if (StringUtils.equalsIgnoreCase(dependFactor, "OT")) {
                             * rateMaster.setOccupancyType(CommonMasterUtility.getCPDDescription(eventId,MainetConstants.FlagE));
                             * }
                             */
                            if (StringUtils.equalsIgnoreCase(dependFactor, "FL")) {
                                rateMaster.setFloorLevel(estatePropResponseDTO.getFloor());
                            }
                        }
                    }
                    requiredCHarges.add(rateMaster);
                }
                dto.setDataModel(requiredCHarges);
                WSRequestDTO chargeReqDTO = new WSRequestDTO();
                chargeReqDTO.setDataModel(requiredCHarges);
                WSResponseDTO applicableCharges = brmsRNLService.getApplicableCharges(chargeReqDTO);
                final List<ChargeDetailDTO> output = (List<ChargeDetailDTO>) applicableCharges.getResponseObj();
                // setChargesInfo(newChargesToPay(output));
                // BigDecimal amount = calculateRentalChargesBetweenDates(model, output);
                // model.setAmountToPay(amount.doubleValue());
                // setAmountToPay(chargesToPay(getChargesInfo()));
                setChargesInfo((List<ChargeDetailDTO>) applicableCharges.getResponseObj());
                setAmountToPay(chargesToPay((List<ChargeDetailDTO>) applicableCharges.getResponseObj()));
                getOfflineDTO().setAmountToShow(getAmountToPay());
                if (getAmountToPay() == 0.0d) {
                    throw new FrameworkException("Service charge amountToPay cannot be "
                            + getAmountToPay() + " if service configured as Chageable");
                }

            } else {
                throw new FrameworkException("Problem while checking dependent param for rnl rate .");
            }
        } else {
            throw new FrameworkException("Problem while initializing CheckList and RNLRateMaster Model");
        }
    }

    private double chargesToPay(final List<ChargeDetailDTO> charges) {
        double amountSum = 0.0;
        for (final ChargeDetailDTO charge : charges) {
            amountSum = amountSum + charge.getChargeAmount();
        }
        return amountSum;
    }

    private String getSubCategoryDesc(final String taxsubCategory, final Organisation org) {
        String subCategoryDesc = "";
        final List<LookUp> subCategryLookup = CommonMasterUtility.getLevelData(PrefixConstants.TAC_PREFIX,
                MainetConstants.EstateBooking.LEVEL, org);
        for (final LookUp lookup : subCategryLookup) {
            if (lookup.getLookUpCode().equals(taxsubCategory)) {
                subCategoryDesc = lookup.getDescLangFirst();
                break;
            }
        }
        return subCategoryDesc;
    }

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

    /*
     * private List<ChargeDetailDTO> newChargesToPay(final List<ChargeDetailDTO> charges) { List<ChargeDetailDTO> chargeList = new
     * ArrayList<>(0); EstateBookingModel estateModel = getModel(); Date bookStartDate =
     * estateModel.getBookingReqDTO().getEstateBookingDTO().getFromDate(); Date bookEndDate =
     * estateModel.getBookingReqDTO().getEstateBookingDTO().getToDate(); int days = Utility.getDaysBetweenDates(bookStartDate,
     * bookEndDate); for (final ChargeDetailDTO charge : charges) { BigDecimal amount = new BigDecimal(charge.getChargeAmount());
     * amount = amount.multiply(new BigDecimal(days)); charge.setChargeAmount(amount.doubleValue()); chargeList.add(charge); }
     * return chargeList; }
     */

    /*
     * private CheckListModel settingUsageSubtype(final CheckListModel checkListModel2, final ChangeOfOwnerResponseDTO
     * responseDTO, final Organisation organisation) { final List<LookUp> usageSubtype1 = CommonMasterUtility.getLevelData(
     * PrefixConstants.NewWaterServiceConstants.TRF, 1, UserSession.getCurrent().getOrganisation()); for (final LookUp lookUp :
     * usageSubtype1) { if (responseDTO.getTrmGroup1() != null) { if (lookUp.getLookUpId() == responseDTO.getTrmGroup1()) {
     * checkListModel2.setUsageSubtype1(lookUp.getLookUpDesc()); break; } } } return checkListModel2; }
     */
}

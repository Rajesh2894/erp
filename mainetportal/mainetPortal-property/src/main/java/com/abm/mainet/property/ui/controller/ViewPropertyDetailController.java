
package com.abm.mainet.property.ui.controller;

import static com.abm.mainet.common.constant.MainetConstants.ERROR_OCCURED;
import static com.abm.mainet.common.constant.MainetConstants.FILE_PATH_SEPARATOR;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.dto.TbBillDet;
import com.abm.mainet.common.dto.TbBillMas;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.Filepaths;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.StringUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.property.dto.ArrayOfDiversionPlotDetails;
import com.abm.mainet.property.dto.ArrayOfKhasraDetails;
import com.abm.mainet.property.dto.ArrayOfPlotDetails;
import com.abm.mainet.property.dto.LandTypeApiDetailRequestDto;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentFactorDtlDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.SelfAssessmentDeaultValueDTO;
import com.abm.mainet.property.service.IViewPropertyDetailsService;
import com.abm.mainet.property.service.MutationService;
import com.abm.mainet.property.service.SelfAssessmentService;
import com.abm.mainet.property.ui.model.SelfAssesmentNewModel;
import com.sun.star.io.IOException;

@Controller
@RequestMapping("/ViewPropertyDetail.html")
public class ViewPropertyDetailController extends AbstractFormController<SelfAssesmentNewModel> {

    @Autowired
    private IViewPropertyDetailsService viewPropertyDetailsService;
    
    @Autowired
    private IPortalServiceMasterService serviceMaster;
    
    @Autowired
    private SelfAssessmentService selfAssessmentService;
    
    @Autowired
    private MutationService mutationService;

    
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ViewPropertyDetailController.class);
 
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        sessionCleanup(request);
        getModel().bind(request);
        SelfAssesmentNewModel model = this.getModel();
        model.setCommonHelpDocs("ViewPropertyDetail.html");
        long orgid = Utility.getOrgId();     
        model.setOrgId(orgid);
        final Long serviceId = serviceMaster.getServiceId(MainetConstants.Property.SAS, orgid);
        final Long deptId = serviceMaster.getServiceDeptIdId(serviceId);
        model.getSearchDto().setOrgId(Utility.getOrgId());
        model.getSearchDto().setDeptId(deptId);
        model.setDeptId(deptId);
        List<LookUp> locList=mutationService.getLocationList(Utility.getOrgId(), deptId);
        getModel().setLocation(locList);
        return defaultResult();
    }

    @RequestMapping(params = "searchData", method = RequestMethod.POST)
    public @ResponseBody List<ProperySearchDto> search(HttpServletRequest request) {
        SelfAssesmentNewModel model = this.getModel();      
        model.bind(request);       
        return viewPropertyDetailsService.searchPropertyDetails(
        			model.getSearchDto());      
    }
    
    @RequestMapping(params = "edit", method = RequestMethod.POST)
    public ModelAndView editForm(@RequestParam("propId") final String propId,
			@RequestParam("formMode") String formMode,
            final HttpServletRequest httpServletRequest) throws Exception {
        bindModel(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        getModel().setAssType(MainetConstants.Property.VIW);
        model.getSearchDto().setProertyNo(propId);
        long orgid = Utility.getOrgId();
        final ProvisionalAssesmentMstDto assMst = viewPropertyDetailsService
                .fetchPropertyByPropNo(model.getSearchDto());
        if (assMst.getAssCorrAddress() == null) {
            assMst.setProAsscheck(MainetConstants.Common_Constant.YES);
        } else {
            assMst.setProAsscheck(MainetConstants.Common_Constant.NO);
        }
        if (assMst.getAssLpYear() == null) {
            assMst.setProAssBillPayment(MainetConstants.Property.NOT_APP);
        } else {
            assMst.setProAssBillPayment(MainetConstants.Property.MANUAL);
        }
        LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                assMst.getAssOwnerType(), UserSession.getCurrent().getOrganisation());
        getModel().setOwnershipPrefix(ownerType.getLookUpCode());
        List<ProvisionalAssesmentFactorDtlDto> factorMap = factorMappingForView(assMst);
        model.setProvAsseFactDtlDto(factorMap);
        SelfAssessmentDeaultValueDTO data = selfAssessmentService.setAllDefaultFields(orgid, getModel().getDeptId());
     
       // model.setLocation(data.getLocation());
        model.setFinancialYearMap(data.getFinancialYearMap());
        if (assMst.getAssLandType() != null) {
            LookUp landTypePrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    assMst.getAssLandType(), UserSession.getCurrent().getOrganisation());
            getModel().setLandTypePrefix(landTypePrefix.getLookUpCode());
        }  
        this.getModel().setProvisionalAssesmentMstDto(assMst);
        if(getModel().getLandTypePrefix()!=null) {
            setlandTypeDetails(getModel());
        }      
        model.setDropDownValues(UserSession.getCurrent().getOrganisation());
        
        model.setBillMasList(viewPropertyDetailsService.getViewData(model.getSearchDto()));
        model.setCollectionDetails(
                viewPropertyDetailsService.getCollectionDetails(model.getSearchDto()));
        model.setAppDocument(viewPropertyDetailsService.fetchApplicaionDocuments(model.getSearchDto()));
        return new ModelAndView("ViewBillCollectionDetails", "command", model);
    }

    private void setlandTypeDetails(SelfAssesmentNewModel model) {   	
    	LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
    	dto.setLandType(model.getLandTypePrefix());
    	dto.setDistrictId(model.getProvisionalAssesmentMstDto().getAssDistrict());
    	dto.setTehsilId(model.getProvisionalAssesmentMstDto().getAssTahasil());
    	dto.setVillageId(model.getProvisionalAssesmentMstDto().getTppVillageMauja());
    	dto.setMohallaId(model.getProvisionalAssesmentMstDto().getMohalla());
    	model.setDistrict(selfAssessmentService.findDistrictByLandType(dto));     
    	model.setTehsil(selfAssessmentService.getTehsilListByDistrict(dto));
    	model.setVillage(selfAssessmentService.getVillageListByTehsil(dto));
   	 	if(model.getLandTypePrefix().equals(MainetConstants.Property.NZL) || model.getLandTypePrefix().equals(MainetConstants.Property.DIV)) {
            getModel().setMohalla(selfAssessmentService.getMohallaListByVillageId(dto));
            getModel().setBlockStreet(selfAssessmentService.getStreetListByMohallaId(dto));       
   	 }
   }
    @RequestMapping(params = "viewPropDet", method = RequestMethod.POST)
    public ModelAndView vieBillAndPropDetails(@RequestParam("bmIdNo") final long bmIdNo,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        List<TbBillMas> billMasList = new ArrayList<>(0);
        model.getBillMasList().stream().filter(bill -> bill.getBmIdno() == bmIdNo)
                .forEach(bills -> {
                    billMasList.add(bills);
                });
        billMasList.forEach(billMas -> {
        	billMas.getTbWtBillDet().sort(
    				Comparator.comparing(TbBillDet::getDisplaySeq, Comparator.nullsLast(Comparator.naturalOrder())));
		});
        model.setAuthComBillList(billMasList);
        return new ModelAndView("ViewPropertyTaxDetails", "command", model);
    }
    @RequestMapping(params = "propertyBillDownload", method = { RequestMethod.POST })
    public  @ResponseBody String printReport(@RequestParam("bmIdNo") Long bmIdNo, final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse)
            throws IOException {
        bindModel(httpServletRequest);
        ProperySearchDto propDto = new ProperySearchDto();
        propDto.setBmIdNo(bmIdNo);
        propDto.setOrgId(Utility.getOrgId());
        propDto.setLangId(UserSession.getCurrent().getOrganisation().getLangId());
        propDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        ProperySearchDto properySearchDto = viewPropertyDetailsService.getAndGenearteJasperForBill(propDto);
  
         String path= convertByteArrayToFile(properySearchDto);      
         return path;
    }
    
    @RequestMapping(params = "propertyReceiptDownload", method = { RequestMethod.POST })
    public ModelAndView printReceiptReport(@RequestParam("recptId") Long recptId,@RequestParam("receiptNo") Long receiptNo, final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse)
            throws IOException {
        bindModel(httpServletRequest);
        ProperySearchDto propDto = new ProperySearchDto();
        ProvisionalAssesmentMstDto proMstDto = getModel().getProvisionalAssesmentMstDto();
        propDto.setRecptId(recptId);
        propDto.setOrgId(Utility.getOrgId());
        propDto.setLangId(UserSession.getCurrent().getOrganisation().getLangId());
        propDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());      
        ChallanReceiptPrintDTO receiptDto  = viewPropertyDetailsService.getRevenueReceiptDetails(recptId,receiptNo,proMstDto.getAssNo());    
        getModel().setReceiptDTO(receiptDto);       
        if(receiptDto!=null) {
        return new ModelAndView("revenueReceiptPrint",
                MainetConstants.FORM_NAME, getModel());
        }
        return null;     
    }

    private String convertByteArrayToFile(ProperySearchDto properySearchDto) 
    {
      
    	byte[] billFile = properySearchDto.getBillFile();
    	
    	String existingPath=properySearchDto.getFilePath();
    	 if (MainetConstants.FILE_PATH_SEPARATOR.equals("\\")) {
             existingPath = existingPath.replace('/', '\\');
         } else {
             existingPath = existingPath.replace('\\', '/');
         }

         final String fileName = StringUtility.staticStringAfterChar(FILE_PATH_SEPARATOR, existingPath);
    	
    	
         SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.FORMAT_DDMMMYY);
         String date = formatter.format(new Date());
         String outputpath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.operator.FORWARD_SLACE
                 + Utility.getOrgId() + MainetConstants.operator.FORWARD_SLACE + date
                 +  MainetConstants.operator.FORWARD_SLACE
                 + "SHOW_DOCS";
          	
    	  FileOutputStream fos = null;
          File file = null;
    
          try {

        	  Utility.createDirectory(Filepaths.getfilepath() + outputpath);
        	
              file = new File(Filepaths.getfilepath() + outputpath + MainetConstants.operator.FORWARD_SLACE+ fileName);

              outputpath = outputpath + FILE_PATH_SEPARATOR;

              outputpath = outputpath.replace(FILE_PATH_SEPARATOR, MainetConstants.WINDOWS_SLASH);
           
              fos = new FileOutputStream(file);
              
              LOGGER.info(outputpath);

              if (null != billFile) 
              {
                  
            	  fos.write(billFile);
                  
              } else 
              {
            	  LOGGER.error("File not found");
              }

              fos.close();

          } 
          catch (final HttpClientErrorException fileException) 
          {
           
        	  LOGGER.error(ERROR_OCCURED, fileException);
              return MainetConstants.BLANK;
          }

          catch (final Exception e)
          {

        	  LOGGER.error(ERROR_OCCURED, e);
              return MainetConstants.BLANK;
          } 
          finally {
           

                  if (fos != null) {
                      try {
						fos.close();
					} catch (java.io.IOException e) 
                      {
						 LOGGER.error(ERROR_OCCURED, e);
						e.printStackTrace();
					}
                  

                   }
          }
          
          LOGGER.info("return file path="+outputpath+fileName);  
    	
    	return outputpath+fileName;
	}


	private List<ProvisionalAssesmentFactorDtlDto> factorMappingForView(final ProvisionalAssesmentMstDto assMst) {
        List<ProvisionalAssesmentFactorDtlDto> factorMap = new ArrayList<>();
        assMst.getProvisionalAssesmentDetailDtoList().forEach(propDet -> {
            propDet.getProvisionalAssesmentFactorDtlDtoList().forEach(fact -> {
                fact.setUnitNoFact(propDet.getAssdUnitNo().toString());
                fact.setProAssfFactorIdDesc(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(fact.getAssfFactorId(), UserSession.getCurrent().getOrganisation())
                        .getDescLangFirst());
                fact.setProAssfFactorValueDesc(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(fact.getAssfFactorValueId(),
                        		UserSession.getCurrent().getOrganisation())
                        .getDescLangFirst());
                fact.setFactorValueCode(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(fact.getAssfFactorId(), UserSession.getCurrent().getOrganisation())
                        .getLookUpCode());
                factorMap.add(fact);
                assMst.getProAssfactor().add(MainetConstants.Common_Constant.YES);
            });

        });
        return factorMap;
    }

    @RequestMapping(params = "backToPropDet", method = RequestMethod.POST)
    public ModelAndView backToPropDet(HttpServletRequest request) {
        SelfAssesmentNewModel model = this.getModel();
        model.bind(request);
        return new ModelAndView("ViewBillCollectionDetails", "command", model);
    }

    @RequestMapping(params = "backToSearch", method = RequestMethod.POST)
    public ModelAndView backToSearch(HttpServletRequest request) {
        SelfAssesmentNewModel model = this.getModel();
        model.bind(request);
        return defaultMyResult();
    }

    @RequestMapping(method = { RequestMethod.POST }, params = "resetViewProp")
    public ModelAndView reset(HttpServletRequest request) throws Exception {
    	sessionCleanup(request);
        getModel().bind(request);
        SelfAssesmentNewModel model = this.getModel();
        model.setCommonHelpDocs("ViewPropertyDetail.html");
       /* Long deptId = departmentService.getDepartmentIdByDeptCode(
                MainetConstants.Property.PROP_DEPT_SHORT_CODE, MainetConstants.STATUS.ACTIVE);*/
        final Long serviceId = serviceMaster.getServiceId(MainetConstants.Property.SAS, Utility.getOrgId());
        final Long deptId = serviceMaster.getServiceDeptIdId(serviceId);
        model.getSearchDto().setOrgId(Utility.getOrgId());
        model.getSearchDto().setDeptId(deptId);
        List<LookUp> locList=mutationService.getLocationList(Utility.getOrgId(), deptId);
        getModel().setLocation(locList);
        return defaultMyResult();
   
    }
    
   
    @RequestMapping(params = {"viewBillHistory"}, method = RequestMethod.POST)
    public ModelAndView viewPropertyBillHistory(@RequestParam("proertyNo") final String proertyNo,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
       
        Long orgId=Utility.getOrgId();
        final Long serviceId = serviceMaster.getServiceId(MainetConstants.Property.SAS, orgId);
        final Long deptId = serviceMaster.getServiceDeptIdId(serviceId);
        this.getModel().setAssType("D");
        ProperySearchDto searchDto=new ProperySearchDto();
        searchDto.setProertyNo(proertyNo);
        searchDto.setOrgId(Utility.getOrgId());
        searchDto.setDeptId(deptId);
        this.getModel().setBillMasList(viewPropertyDetailsService.getViewData(searchDto));
        
        return new ModelAndView("PropertyBillHistory", "command", this.getModel());
    }
     
    
    @RequestMapping(params = {"viewPaymentHistory"}, method = RequestMethod.POST)
    public ModelAndView viewPropertyPaymentHistory(@RequestParam("proertyNo") final String proertyNo,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        ProperySearchDto searchDto=new ProperySearchDto();
        searchDto.setProertyNo(proertyNo);
        searchDto.setOrgId(Utility.getOrgId());
        final Long serviceId = serviceMaster.getServiceId(MainetConstants.Property.SAS, Utility.getOrgId());
        final Long deptId = serviceMaster.getServiceDeptIdId(serviceId);
        searchDto.setDeptId(deptId);
        this.getModel().setCollectionDetails(
                viewPropertyDetailsService.getCollectionDetails(searchDto));
        
        return new ModelAndView("PropertyPaymentHistory", "command", this.getModel());
    }
     
    
    @RequestMapping(params = {"viewDetails"}, method = RequestMethod.POST)
    public ModelAndView viewPropertyDetails(@RequestParam("proertyNo") final String proertyNo,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        Long orgId=Utility.getOrgId();
        ProperySearchDto searchDto=new ProperySearchDto();
        final Long serviceId = serviceMaster.getServiceId(MainetConstants.Property.SAS, orgId);
        final Long deptId = serviceMaster.getServiceDeptIdId(serviceId);
        searchDto.setProertyNo(proertyNo);
        searchDto.setOrgId(orgId);
        searchDto.setDeptId(deptId);
        searchDto.setProertyNo(proertyNo);
        model.setDeptId(deptId);
        final ProvisionalAssesmentMstDto assMst = viewPropertyDetailsService
                .fetchPropertyByPropNo(searchDto);
        if (assMst.getAssCorrAddress() == null) {
            assMst.setProAsscheck(MainetConstants.Common_Constant.YES);
        } else {
            assMst.setProAsscheck(MainetConstants.Common_Constant.NO);
        }
        if (assMst.getAssLpYear() == null) {
            assMst.setProAssBillPayment(MainetConstants.Property.NOT_APP);
        } else {
            assMst.setProAssBillPayment(MainetConstants.Property.MANUAL);
        }
        LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                assMst.getAssOwnerType(), UserSession.getCurrent().getOrganisation());
        getModel().setOwnershipPrefix(ownerType.getLookUpCode());
        List<ProvisionalAssesmentFactorDtlDto> factorMap = factorMappingForView(assMst);
        model.setProvAsseFactDtlDto(factorMap);
        SelfAssessmentDeaultValueDTO data = selfAssessmentService.setAllDefaultFields(orgId, getModel().getDeptId());
     
        model.setFinancialYearMap(data.getFinancialYearMap());
        this.getModel().setProvisionalAssesmentMstDto(assMst);
        model.setDropDownValues();
        model.setAppDocument(viewPropertyDetailsService.fetchApplicaionDocuments(model.getSearchDto()));
        return new ModelAndView("viewPropertyAppDetails", "command", model);
    }
     
   
    @RequestMapping(params = {"BackToBillHistory"}, method = RequestMethod.POST)
    public ModelAndView BackToBillHistory(final HttpServletRequest httpServletRequest) 
    {
    	  bindModel(httpServletRequest);
          return new ModelAndView("PropertyBillHistory", "command", this.getModel());
    }
 
    @RequestMapping(params = "getLandTypeApiDetails", method = RequestMethod.POST)
    public ModelAndView getLandTypeApiDetails(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
    	SelfAssesmentNewModel model = this.getModel();
        LandTypeApiDetailRequestDto dto = new LandTypeApiDetailRequestDto();
    	dto.setLandType(model.getLandTypePrefix());
    	dto.setDistrictId(model.getProvisionalAssesmentMstDto().getAssDistrict());
    	dto.setTehsilId(model.getProvisionalAssesmentMstDto().getAssTahasil());
    	dto.setVillageId(model.getProvisionalAssesmentMstDto().getTppVillageMauja());
    	dto.setMohallaId(model.getProvisionalAssesmentMstDto().getMohalla());
        dto.setStreetNo(model.getProvisionalAssesmentMstDto().getAssStreetNo());
    	dto.setKhasraNo(model.getProvisionalAssesmentMstDto().getTppPlotNoCs());
    	dto.setPlotNo(model.getProvisionalAssesmentMstDto().getTppPlotNo());
        if(model.getLandTypePrefix().equals(MainetConstants.Property.KPK)) {
        	ArrayOfKhasraDetails response = selfAssessmentService.getKhasraDetails(dto);   
            getModel().setArrayOfKhasraDetails(response);
       }           
        else if(model.getLandTypePrefix().equals(MainetConstants.Property.NZL)) {        
        	ArrayOfPlotDetails response = selfAssessmentService.getNajoolDetails(dto); 
            model.setArrayOfPlotDetails(response);
        }
        else if( model.getLandTypePrefix().equals(MainetConstants.Property.DIV)) {
        	ArrayOfDiversionPlotDetails response = selfAssessmentService.getDiversionDetails(dto);        	
        	model.setArrayOfDiversionPlotDetails(response);  
        }            
        return new ModelAndView("showPropertyKhasaraDetails", MainetConstants.FORM_NAME, model);
    }
    
	@RequestMapping(params = "checkActiveStatus", method = RequestMethod.POST)
	public @ResponseBody Boolean checkActiveStatus(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, @RequestParam(value = "propNo") String propNo,
			@RequestParam(value = "oldPropNo") String oldPropNo) {
		getModel().getSearchDto().setProertyNo(propNo);
		getModel().getSearchDto().setOldPid(oldPropNo);
		return  selfAssessmentService.checkWhetherPropertyIsActive(propNo, oldPropNo,
				Utility.getOrgId());
		
	}
	
	@RequestMapping(params = "downloadBillBirtReport", method = RequestMethod.POST)
	public @ResponseBody String downloadDetailBill(@RequestParam("bmIdNo") Long bmIdno, @RequestParam("finYearId") Long finYearId, @RequestParam("propNo") String propNo) {
		Long orgId = Utility.getOrgId();
		return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=RP_PropertyBillViewReport_ASCL.rptdesign&Orgid=" + orgId
				+ "&bmIdNo=" + bmIdno + "&finYear=" + finYearId + "&propertyNo=" + propNo;
	}
}

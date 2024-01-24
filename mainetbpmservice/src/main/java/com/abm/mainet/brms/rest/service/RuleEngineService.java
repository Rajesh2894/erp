package com.abm.mainet.brms.rest.service;

import org.apache.log4j.Logger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.abm.mainet.brms.core.dto.WSResponseDTO;
import com.abm.mainet.brms.core.exception.RuleNotFoundException;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.constant.MainetConstants;
import com.abm.mainet.rule.Rule;
import com.abm.mainet.rule.RuleResponseHelper;
import com.abm.mainet.rule.account.datamodel.TDSCalculation;
import com.abm.mainet.rule.additionalservices.datamodel.AdditionalServicesModel;
import com.abm.mainet.rule.adh.datamodel.ADHRateMaster;
import com.abm.mainet.rule.bnd.datamodel.BndRateMaster;
import com.abm.mainet.rule.bpmsratemaster.datamodel.BPMSRateMaster;
import com.abm.mainet.rule.datamodel.CheckListModel;
import com.abm.mainet.rule.ml.datamodel.MLNewTradeLicense;
import com.abm.mainet.rule.mrm.datamodel.MRMRateMaster;
import com.abm.mainet.rule.propertytax.datamodel.ALVMasterModel;
import com.abm.mainet.rule.propertytax.datamodel.FactorMasterModel;
import com.abm.mainet.rule.propertytax.datamodel.PropertyRateMasterModel;
import com.abm.mainet.rule.propertytax.datamodel.PropertyTaxDataModel;
import com.abm.mainet.rule.rnl.datamodel.RNLRateMaster;
import com.abm.mainet.rule.rti.datamodel.RtiRateMaster;
import com.abm.mainet.rule.swm.datamodel.SWMRateMaster;
import com.abm.mainet.rule.water.datamodel.Consumption;
import com.abm.mainet.rule.water.datamodel.NoOfDays;
import com.abm.mainet.rule.water.datamodel.WaterRateMaster;
import com.abm.mainet.rule.water.datamodel.WaterTaxCalculation;
import com.abm.mainet.rule.wms.datamodel.RoadCuttingRateMaster;
import com.abm.mainet.rule.wms.datamodel.WMSRateMaster;

/**
 * @author hiren.poriya
 * @Since 09-Jun-2018
 */
@Service
public class RuleEngineService{

    private static final Logger LOGGER = Logger.getLogger(RuleEngineService.class);

    @Autowired
    private KieContainer kieChecklistContainer;

    @Autowired
    private KieContainer kieWaterRateContainer;

    @Autowired
    private KieContainer kieWaterTaxContainer;

    @Autowired
    private KieContainer kieRNLRateContainer;

    @Autowired
    private KieContainer kiePropertyALVContainer;

    @Autowired
    private KieContainer kiePropertyFactorContainer;

    @Autowired
    private KieContainer kiePropertyRateContainer;

    @Autowired
    private KieContainer kiePropertySDDRRateContainer;

    @Autowired
    private KieContainer kieRTIContainer;

    @Autowired
    private KieContainer kieTDSContainer;

    @Autowired
    private KieContainer kieSWMRateContainer;

    @Autowired
    private KieContainer kieWMSRateContainer;

    /*
     * @Autowired private KieContainer kieAquaContainer;
     */

    @Autowired
    private KieContainer kieMarketLicenseContainer;

    @Autowired
    private Environment environment;

    @Autowired
    private KieContainer kieAdhRateContainer;
    
    @Autowired
    private KieContainer kieMrmRateContainer;
    
    @Autowired
	private KieContainer kieBNDRateMasterContainer;
    
    @Autowired
    private KieContainer kieAdditionalServicesRateContainer;

    @Autowired
    private KieContainer kieBPMSRateMasterContainer;
	
	  public Object findCheckListGroup(CheckListModel model) {
		  Object ruleResult = null;
		  KieSession kSession = null;
		  try {
		  kSession = kieChecklistContainer.newKieSession();
		  kSession.insert(model);	  
		  kSession.startProcess(environment.getProperty("drools.project.checklist.brmprocess"));
		  // kSession.fireAllRules(); 
		  if(RuleResponseHelper.getRuleResponseList().isEmpty()) { 
			  throw new RuleNotFoundException("No checklist group found for provided input model"); 
		  } else { 
		  for (Rule rule : RuleResponseHelper.getRuleResponseList()) {
			  ruleResult = (Object) rule.getRuleResult(); 
		  	} 
		  } 
		 } catch (FrameworkException ex) {
			  LOGGER.error("Exception occured during finding Checklist for inserted Model="+ model, ex);
			  ruleResult = "500"; 
		   } finally {
			  RuleResponseHelper.getRuleResponseList().clear(); 
			  if (kSession != null)
				  kSession.dispose();
		  } 
		 return ruleResult; 
	}

    /**
     * method to find Water Rate
     * 2
     * @param model
     * @return updated model
     */
    public WaterRateMaster findWaterRateMaster(WaterRateMaster model) {
        WaterRateMaster rateMaster = null;
        KieSession kSession = null;
        try {

            kSession = kieWaterRateContainer.newKieSession();
            kSession.insert(model);
            kSession.startProcess(environment.getProperty("drools.project.waterrate.brmprocess"));
            kSession.fireAllRules();

            if (RuleResponseHelper.getRuleResponseList().isEmpty()) {
                LOGGER.error("Water Rate not found for provided input model [" + model + "]");
                throw new RuleNotFoundException("Water Rate not found for provided input model");
            } else {
                for (Rule rule : RuleResponseHelper.getRuleResponseList()) {
                    rateMaster = (WaterRateMaster) rule.getRuleResult();
                    rateMaster.setRuleId(rule.getRuleId());
                }
            }
        } catch (FrameworkException ex) {
            rateMaster = null;
            LOGGER.error("Exception occured during firing rules to check water rate for inserted WaterRateMaster model:"
                    + model, ex);
        } finally {
            RuleResponseHelper.getRuleResponseList().clear();
            if (kSession != null)
                kSession.dispose();
        }

        return rateMaster;
    }

    /**
     * method to find what is the water consumption
     * 
     * @param model
     * @return water consumption otherwise return "Water Consumption not found."
     */
    public String findWaterConsumption(Consumption model) {
        String consumption = "";
        KieSession kSession = null;
        try {

            kSession = kieWaterTaxContainer.newKieSession();
            kSession.insert(model);
            kSession.startProcess(environment.getProperty("drools.project.waterconsumption.brmprocess"));
            kSession.fireAllRules();

            if (RuleResponseHelper.getRuleResponseList().isEmpty()) {
                throw new RuleNotFoundException("No water consumption found for provided input model");
            } else {
                for (Rule rule : RuleResponseHelper.getRuleResponseList()) {
                    consumption = rule.getRuleResult().toString();
                }
            }
        } catch (FrameworkException ex) {
            consumption = "500";
            LOGGER.error("Exception occured during finding water consumption" + model, ex);
        } finally {
            RuleResponseHelper.getRuleResponseList().clear();
            if (kSession != null)
                kSession.dispose();
        }
        return consumption;
    }

    /**
     * method to find No. of days
     * 
     * @param model
     * @return noOfDays otherwise return "No of Days not found."
     */
    public String findNoOfDays(NoOfDays model) {

        String noOfDays = "";
        KieSession kSession = null;
        try {

            kSession = kieWaterTaxContainer.newKieSession();
            kSession.insert(model);
            kSession.startProcess(environment.getProperty("drools.project.waterconsumption.brmprocess"));
            kSession.fireAllRules();

            if (RuleResponseHelper.getRuleResponseList().isEmpty()) {
                throw new RuleNotFoundException("NoOfDays not found for provided input model");
            } else {
                for (Rule rule : RuleResponseHelper.getRuleResponseList()) {
                    noOfDays = rule.getRuleResult().toString();
                }
            }
        } catch (FrameworkException ex) {
            noOfDays = "500";
            LOGGER.error("Exception occured during firing rules to check no of days for inserted NoOfDays model :" + model, ex);
        } finally {
            RuleResponseHelper.getRuleResponseList().clear();
            if (kSession != null)
                kSession.dispose();
        }

        return noOfDays;
    }

    /**
     * method to find Water Taxes
     * 
     * @param model
     * @return charge amount otherwise return "Charge not found"
     */
    public WaterTaxCalculation findWaterTaxMaster(WaterTaxCalculation model) {
        WaterTaxCalculation ruleResult = null;
        KieSession kSession = null;
        try {

            kSession = kieWaterTaxContainer.newKieSession();
            kSession.insert(model);
            kSession.startProcess(environment.getProperty("drools.project.watertax.brmprocess"));
            kSession.fireAllRules();

            if (RuleResponseHelper.getRuleResponseList().isEmpty()) {
                LOGGER.error("Water Tax not found for provided input model [" + model + "]");
                throw new RuleNotFoundException("No water Tax found for provided input model");
            } else {
                for (Rule rule : RuleResponseHelper.getRuleResponseList()) {
                    ruleResult = (WaterTaxCalculation) rule.getRuleResult();
                }
            }
        } catch (FrameworkException ex) {
            ruleResult = null;
            LOGGER.error(
                    "Exception occured during firing rules to check Water Tax for inserted WaterTaxCalculation model:",
                    ex);
        } finally {
            RuleResponseHelper.getRuleResponseList().clear();
            if (kSession != null)
                kSession.dispose();
        }
        return ruleResult;
    }

    /**
     * 
     * @param model
     * @return
     */
    public RNLRateMaster findRnLRateMaster(RNLRateMaster model) {
        RNLRateMaster rateMaster = null;
        KieSession kSession = null;
        try {
            kSession = kieRNLRateContainer.newKieSession();
            kSession.insert(model);
            kSession.startProcess(environment.getProperty("drools.project.rnl.brmprocess"));
            kSession.fireAllRules();

            if (RuleResponseHelper.getRuleResponseList().isEmpty()) {
                LOGGER.error("RNL Rate not found for provided input model [" + model + "]");
                throw new RuleNotFoundException("RNL Rate not found for provided input model");
            } else {
                for (Rule rule : RuleResponseHelper.getRuleResponseList()) {
                    rateMaster = (RNLRateMaster) rule.getRuleResult();
                }
            }
        } catch (FrameworkException ex) {
            rateMaster = null;
            LOGGER.error("Exception occured during firing rules to check rnl rate for inserted RNLRateMaster model:"
                    + model, ex);
        } finally {
            RuleResponseHelper.getRuleResponseList().clear();
            if (kSession != null)
                kSession.dispose();
        }

        return rateMaster;
    }

    public ResponseEntity<?> calculateALV(ALVMasterModel model) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        ResponseEntity<?> ruleResponse = null;
        KieSession kSession = null;
        /*To handle multithreading ClasCastException - As per suggested by Ritesh*/
        synchronized (RuleResponseHelper.class) {
		try {

			kSession = kiePropertyALVContainer.newKieSession();
			kSession.insert(model);
			kSession.startProcess(environment.getProperty("drools.project.propertytax.alv.brmprocess"));
				if (RuleResponseHelper.getRuleResponseList().isEmpty()) {
					model.setErrorMsg("NO RULE FOUND");
					responseDTO.setResponseObj(model);
					ruleResponse = ResponseEntity.status(HttpStatus.OK).body(responseDTO);
					LOGGER.error("NO RULE FOUND! for provided input model" + model);
				} else {
					for (Rule rule : RuleResponseHelper.getRuleResponseList()) {
						model = (ALVMasterModel) rule.getRuleResult();
					}
					LOGGER.info("RULE FOUND! for provided input model" + model);
					responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
					responseDTO.setResponseObj(model);
					ruleResponse = ResponseEntity.status(HttpStatus.OK).body(responseDTO);
				}
		} catch (FrameworkException ex) {
            LOGGER.error("Exception occured during firing rules for inserted ALVMasterModel model:" + model, ex);
        } finally {
            RuleResponseHelper.getRuleResponseList().clear();
            if (kSession != null)
                kSession.dispose();
        }
    }
        return ruleResponse;
    }

    public ResponseEntity<?> calculateFactor(FactorMasterModel model) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        ResponseEntity<?> ruleResponse = null;
        KieSession kSession = null;
        /*To handle multithreading ClasCastException - As per suggested by Ritesh*/
        synchronized (RuleResponseHelper.class) {
        try {

            kSession = kiePropertyFactorContainer.newKieSession();
            kSession.insert(model);
            kSession.startProcess(environment.getProperty("drools.project.propertytax.factor.brmprocess"));

            if (RuleResponseHelper.getRuleResponseList().isEmpty()) {
                ruleResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
                LOGGER.error("NO RULE FOUND! for provided input model" + model);
            } else {
                for (Rule rule : RuleResponseHelper.getRuleResponseList()) {
                    model = (FactorMasterModel) rule.getRuleResult();
                }
                responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
                responseDTO.setResponseObj(model);
                ruleResponse = ResponseEntity.status(HttpStatus.OK).body(responseDTO);
            }
        } catch (FrameworkException ex) {
            LOGGER.error("Exception occured during firing rules for inserted FactorMasterModel model:" + model, ex);
        } finally {
            RuleResponseHelper.getRuleResponseList().clear();
            if (kSession != null)
                kSession.dispose();
        }
       }
        return ruleResponse;
    }

    public ResponseEntity<?> calculatePropertyTax(PropertyRateMasterModel model) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        ResponseEntity<?> ruleResponse = null;
        KieSession kSession = null;
        /*To handle multithreading ClasCastException - As per suggested by Ritesh*/
        synchronized (RuleResponseHelper.class) {
        try {

            kSession = kiePropertyRateContainer.newKieSession();
            kSession.insert(model);
            kSession.startProcess(environment.getProperty("drools.project.propertytax.rate.brmprocess"));
            
            if (RuleResponseHelper.getRuleResponseList().isEmpty()) {
                ruleResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
                LOGGER.error("NO RULE FOUND! for provided input model" + model);
            } else {
                for (Rule rule : RuleResponseHelper.getRuleResponseList()) {
                    model = (PropertyRateMasterModel) rule.getRuleResult();
                }
                LOGGER.error("RULE FOUND! for provided input model" + model);
                responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
                responseDTO.setResponseObj(model);
                ruleResponse = ResponseEntity.status(HttpStatus.OK).body(responseDTO);
            }
        } catch (FrameworkException ex) {
            LOGGER.error("Exception occured during firing rules for inserted PropertyRateMasterModel model:" + model, ex);
        } finally {
            RuleResponseHelper.getRuleResponseList().clear();
            if (kSession != null)
                kSession.dispose();
        }
        }
        return ruleResponse;
    }

    public ResponseEntity<?> calculateSDDRRate(PropertyTaxDataModel model) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        ResponseEntity<?> ruleResponse = null;
        KieSession kSession = null;

        try {

            kSession = kiePropertySDDRRateContainer.newKieSession();
            kSession.insert(model);
            kSession.startProcess(environment.getProperty("drools.project.propertytax.SDDRRate.brmprocess"));

            if (RuleResponseHelper.getRuleResponseList().isEmpty()) {
                ruleResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
                LOGGER.error("NO RULE FOUND! for provided input model" + model);
            } else {
                for (Rule rule : RuleResponseHelper.getRuleResponseList()) {
                    model = (PropertyTaxDataModel) rule.getRuleResult();
                }
                responseDTO.setWsStatus(MainetConstants.Status.SUCCESS);
                responseDTO.setResponseObj(model);
                ruleResponse = ResponseEntity.status(HttpStatus.OK).body(responseDTO);
            }
        } catch (FrameworkException ex) {
            LOGGER.error("Exception occured during firing rules for inserted PropertyTaxDataModel model:" + model, ex);
        } finally {
            RuleResponseHelper.getRuleResponseList().clear();
            if (kSession != null)
                kSession.dispose();
        }

        return ruleResponse;
    }

    public RtiRateMaster findRtiRateMaster(RtiRateMaster model) {
        RtiRateMaster rtiRateMaster = null;
        KieSession kSession = null;
        try {

            kSession = kieRTIContainer.newKieSession();
            kSession.insert(model);
            kSession.startProcess(environment.getProperty("drools.project.rtiTax.brmprocess"));
            kSession.fireAllRules();

            if (RuleResponseHelper.getRuleResponseList().isEmpty()) {
                throw new RuleNotFoundException("RTI Rate not found for provided input model");
            } else {
                for (Rule rule : RuleResponseHelper.getRuleResponseList()) {
                    rtiRateMaster = (RtiRateMaster) rule.getRuleResult();
                }
            }
        } catch (FrameworkException ex) {
            LOGGER.error("Exception occured during firing rules for inserted RtiRateMaster model:" + model, ex);
        } finally {
            RuleResponseHelper.getRuleResponseList().clear();
            if (kSession != null)
                kSession.dispose();
        }

        return rtiRateMaster;
    }

    public TDSCalculation calculateTdsRate(TDSCalculation factModel) {
        TDSCalculation tdsMaster = null;
        KieSession kSession = null;
        try {

            kSession = kieTDSContainer.newKieSession();
            kSession.insert(factModel);
            kSession.startProcess(environment.getProperty("drools.project.tds.brmprocess"));
            kSession.fireAllRules();

            if (RuleResponseHelper.getRuleResponseList().isEmpty()) {
                throw new RuleNotFoundException("TDSCalculation not found for provided input model");
            } else {
                for (Rule rule : RuleResponseHelper.getRuleResponseList()) {
                    tdsMaster = (TDSCalculation) rule.getRuleResult();
                }
            }
        } catch (FrameworkException ex) {
            LOGGER.error("Exception occured during firing rules for inserted TDSCalculation model:" + factModel, ex);
        } finally {
            RuleResponseHelper.getRuleResponseList().clear();
            if (kSession != null)
                kSession.dispose();
        }

        return tdsMaster;
    }

    public SWMRateMaster findSWMRateMaster(SWMRateMaster model) {
        SWMRateMaster rateMaster = null;
        KieSession kSession = null;
        try {
            kSession = kieSWMRateContainer.newKieSession();
            kSession.insert(model);
            kSession.startProcess(environment.getProperty("drools.project.swm.brmprocess"));
            kSession.fireAllRules();
            if (RuleResponseHelper.getRuleResponseList().isEmpty()) {
                LOGGER.error("SWM Rate not found for provided input model [" + model + "]");
                throw new RuleNotFoundException("SWM Rate not found for provided input model");
            } else {
                for (Rule rule : RuleResponseHelper.getRuleResponseList()) {
                    rateMaster = (SWMRateMaster) rule.getRuleResult();
                }
            }
        } catch (FrameworkException ex) {
            rateMaster = null;
            LOGGER.error("Exception occured during firing rules to check SWM rate for inserted SWMRateMaster model:"
                    + model, ex);
        } finally {
            RuleResponseHelper.getRuleResponseList().clear();
            if (kSession != null)
                kSession.dispose();
        }

        return rateMaster;
    }

    public RoadCuttingRateMaster findRoadCuttingRateMaster(RoadCuttingRateMaster model) {
        RoadCuttingRateMaster rateMaster = null;
        KieSession kSession = null;
        try {
            kSession = kieWMSRateContainer.newKieSession();
            kSession.insert(model);
            kSession.startProcess(environment.getProperty("drools.project.wms.roadcutting.brmprocess"));
            kSession.fireAllRules();
            if (RuleResponseHelper.getRuleResponseList().isEmpty()) {
                LOGGER.error("RoadCuttingRateMaster not found for provided input model [" + model + "]");
                throw new RuleNotFoundException("RoadCuttingRateMaster not found for provided input model");
            } else {
                for (Rule rule : RuleResponseHelper.getRuleResponseList()) {
                    rateMaster = (RoadCuttingRateMaster) rule.getRuleResult();
                }
            }
        } catch (FrameworkException ex) {
            rateMaster = null;
            LOGGER.error(
                    "Exception occured during firing rules to check WMS rate for inserted RoadCuttingRateMaster model:" + model,
                    ex);
        } finally {
            RuleResponseHelper.getRuleResponseList().clear();
            if (kSession != null)
                kSession.dispose();
        }

        return rateMaster;
    }

    public WMSRateMaster findWMSEmdMaster(WMSRateMaster model) {
        WMSRateMaster rateMaster = null;
        KieSession kSession = null;
        try {
            kSession = kieWMSRateContainer.newKieSession();
            kSession.insert(model);
            kSession.startProcess(environment.getProperty("drools.project.wms.brmprocess"));
            kSession.fireAllRules();
            if (RuleResponseHelper.getRuleResponseList().isEmpty()) {
                LOGGER.error("WMS EMD not found for provided input model [" + model + "]");
                throw new RuleNotFoundException("WMS EMD not found for provided input model");
            } else {
                for (Rule rule : RuleResponseHelper.getRuleResponseList()) {
                    rateMaster = (WMSRateMaster) rule.getRuleResult();
                }
            }
        } catch (FrameworkException ex) {
            rateMaster = null;
            LOGGER.error(
                    "Exception occured during firing rules to check SWM rate for inserted WMSRateMaster model:" + model, ex);
        } finally {
            RuleResponseHelper.getRuleResponseList().clear();
            if (kSession != null)
                kSession.dispose();
        }

        return rateMaster;
    }

    public MLNewTradeLicense findNewTradeLicenseFee(MLNewTradeLicense model) {
        MLNewTradeLicense rateMaster = null;
        KieSession kSession = null;
        try {
            kSession = kieMarketLicenseContainer.newKieSession();
            kSession.insert(model);
            kSession.startProcess(environment.getProperty("drools.project.ml.newtradelicense.brmprocess"));
            kSession.fireAllRules();
            if (RuleResponseHelper.getRuleResponseList().isEmpty()) {
                LOGGER.error("New Trade License Fee not found for provided input model [" + model + "]");
                throw new RuleNotFoundException("New Trade License Fee not found for provided input model");
            } else {
                for (Rule rule : RuleResponseHelper.getRuleResponseList()) {
                    rateMaster = (MLNewTradeLicense) rule.getRuleResult();
                }
            }
        } catch (FrameworkException ex) {
            rateMaster = null;
            LOGGER.error(
                    "Exception occured during firing rules to check ML rate for inserted MLNewTradeLicense model:" + model, ex);
        } finally {
            RuleResponseHelper.getRuleResponseList().clear();
            if (kSession != null)
                kSession.dispose();
        }

        return rateMaster;
    }

    public ADHRateMaster findNewADHRateMaster(ADHRateMaster model) {
        ADHRateMaster rateMaster = null;
        KieSession kieSession = null;

        try {
            kieSession = kieAdhRateContainer.newKieSession();
            kieSession.insert(model);
            kieSession.startProcess(environment.getProperty("drools.project.adh.brmprocess"));
            kieSession.fireAllRules();
            if (RuleResponseHelper.getRuleResponseList().isEmpty()) {
                LOGGER.error("New ADH Data can not found for provided input model [" + model + "]");
                throw new RuleNotFoundException("New ADH Data can not found for provided input model");
            } else {
                for (Rule rule : RuleResponseHelper.getRuleResponseList()) {
                    rateMaster = (ADHRateMaster) rule.getRuleResult();
                }
            }

        } catch (Exception exception) {
            rateMaster = null;
            LOGGER.error("Exception occured during firing rules to check ADH rate for inserted ADHRateMaster model:" + model,
                    exception);
        } finally {
            RuleResponseHelper.getRuleResponseList().clear();
            if (kieSession != null) {
            }
        }
        return rateMaster;
    }
    
    public MRMRateMaster findMRMServiceCharges(MRMRateMaster model) {
    	MRMRateMaster mrmRateMastr = null;
        KieSession kieSession = null;

        try {
            kieSession = kieMrmRateContainer.newKieSession();
            kieSession.insert(model);
            kieSession.startProcess(environment.getProperty("drools.project.mrm.brmprocess"));
            kieSession.fireAllRules();
            if (RuleResponseHelper.getRuleResponseList().isEmpty()) {
                LOGGER.error("Service Charge not found for provided input model [" + model + "]");
                throw new RuleNotFoundException("Service Charge not found for provided input model");
            } else {
                for (Rule rule : RuleResponseHelper.getRuleResponseList()) {
                	mrmRateMastr = (MRMRateMaster) rule.getRuleResult();
                }
            }

        } catch (Exception exception) {
        	mrmRateMastr = null;
            LOGGER.error("Exception occured during firing rules to check MRM Rate for inserted MRMRateMaster model:" + model,
                    exception);
        } finally {
            RuleResponseHelper.getRuleResponseList().clear();
            if (kieSession != null) {
            }
        }
        return mrmRateMastr;
    }
    
    public BndRateMaster findBndCharges(BndRateMaster model) {
		BndRateMaster chargesMaster = null;
		KieSession kSession = null;
		try {
			
			kSession = kieBNDRateMasterContainer.newKieSession();
			kSession.insert(model);
			kSession.startProcess(environment.getProperty("drools.project.bnd.brmprocess"));
			kSession.fireAllRules();
			if (RuleResponseHelper.getRuleResponseList().isEmpty()) {
				LOGGER.error("BndRateMaster not found for provided input model [" + model + "]");
				throw new RuleNotFoundException("BndRateMaster not found for provided input model");
			} else {
				for (Rule rule : RuleResponseHelper.getRuleResponseList()) {
					chargesMaster = (BndRateMaster) rule.getRuleResult();
				}
			}
		} catch (FrameworkException ex) {
			chargesMaster = null;
			LOGGER.error(
					"Exception occured during firing rules to check BND rate for inserted BndRateMaster model:"
							+ model,
					ex);
		} finally {
			RuleResponseHelper.getRuleResponseList().clear();
			if (kSession != null)
				kSession.dispose();
		}

		return chargesMaster;
	}
    
    public AdditionalServicesModel findAdditionalServicesModel(AdditionalServicesModel model) {
    	AdditionalServicesModel rateMaster = null;
        KieSession kSession = null;
        try {
            kSession = kieAdditionalServicesRateContainer.newKieSession();
            kSession.insert(model);
            kSession.startProcess(environment.getProperty("drools.project.additionalservices.brmprocess"));
            kSession.fireAllRules();
            if (RuleResponseHelper.getRuleResponseList().isEmpty()) {
                LOGGER.error("AdditionalServicesModel not found for provided input model [" + model + "]");
                throw new RuleNotFoundException("AdditionalServicesModel not found for provided input model");
            } else {
                for (Rule rule : RuleResponseHelper.getRuleResponseList()) {
                    rateMaster = (AdditionalServicesModel) rule.getRuleResult();
                }
            }
        } catch (FrameworkException ex) {
            rateMaster = null;
            LOGGER.error(
                    "Exception occured during firing rules to check AdditionalServices rate for inserted AdditionalServicesModel model:" + model,
                    ex);
        } finally {
            RuleResponseHelper.getRuleResponseList().clear();
            if (kSession != null)
                kSession.dispose();
        }

        return rateMaster;
    }
    
    
    public BPMSRateMaster findBPMSRateMaster(BPMSRateMaster model){
    	BPMSRateMaster bpmsRateMaster=null;
    	KieSession kSession = null;
    	try {
            kSession = kieBPMSRateMasterContainer.newKieSession();
            kSession.insert(model);
            kSession.startProcess(environment.getProperty("drools.project.bpms.brmprocess"));
            kSession.fireAllRules();
            if (RuleResponseHelper.getRuleResponseList().isEmpty()) {
                LOGGER.error("BPMSRateMaster not found for provided input model [" + model + "]");
                throw new RuleNotFoundException("BPMSRateMaster not found for provided input model");
            } else {
                for (Rule rule : RuleResponseHelper.getRuleResponseList()) {
                	bpmsRateMaster = (BPMSRateMaster) rule.getRuleResult();
                }
            }
        } catch (FrameworkException ex) {
        	bpmsRateMaster = null;
            LOGGER.error(
                    "Exception occured during firing rules to check BPMSRaterMaster rate for inserted BPMSRateMaster model:" + model,
                    ex);
        } finally {
            RuleResponseHelper.getRuleResponseList().clear();
            if (kSession != null)
                kSession.dispose();
        }
    	return bpmsRateMaster;
    }
    
}

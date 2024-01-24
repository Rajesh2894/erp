package com.abm.mainet.config;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.drools.compiler.kproject.ReleaseIdImpl;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.abm.mainet.bpm.domain.BpmDeployment;
import com.abm.mainet.bpm.repository.BpmDeploymentRepository;
import com.abm.mainet.constant.MainetConstants;

/**
 * @author hiren.poriya
 * @Since 09-Jun-2018
 */
@Configuration
public class RuleEngineConfig {

    private static final Logger LOGGER = Logger.getLogger(RuleEngineConfig.class);

    @Autowired
    private Environment environment;

    @Autowired
    private BpmDeploymentRepository bpmDeploymentRepository;

    private static final String SCAN_INTERVAL = "drools.artifact.scan.interval";

    private long scanInterval;

    @PostConstruct
    void artifactScannerInit() {
        scanInterval = environment.getProperty(SCAN_INTERVAL, Long.class);
    }

    /**
     * checklist artifact loading
     * @return
     */
    @Bean
    public KieContainer kieChecklistContainer() {
        LOGGER.info("Checklist artifact Loading Start...");
        KieServices kieServices = KieServices.Factory.get();
        ReleaseIdImpl releaseIdImpl = new ReleaseIdImpl(
                getReleaseId(MainetConstants.Rules.Artifacts.CHECKLIST, environment.getProperty("drools.project.checklist")));
        KieContainer container = kieServices.newKieContainer(releaseIdImpl);
        startScanArtifact(kieServices, container, scanInterval);
        LOGGER.info("Checklist artifact Loading End...");
        return container;
    }

    /**
     * water rate artifact loading
     * @return
     */

    @Bean
    public KieContainer kieWaterRateContainer() {
        LOGGER.info("Water Rate artifact Loading Start...");
        KieServices kieServices = KieServices.Factory.get();
        ReleaseIdImpl releaseIdImpl = new ReleaseIdImpl(
                getReleaseId(MainetConstants.Rules.Artifacts.WATER_RATE_MASTER,
                        environment.getProperty("drools.project.waterrate")));
        KieContainer container = kieServices.newKieContainer(releaseIdImpl);
        startScanArtifact(kieServices, container, scanInterval);
        LOGGER.info("Water Rate artifact Loading End...");
        return container;
    }

    /**
     * water tax artifact loading
     * @return
     */

    @Bean
    public KieContainer kieWaterTaxContainer() {
        LOGGER.info("Water Tax artifact Loading Start...");
        KieServices kieServices = KieServices.Factory.get();
        ReleaseIdImpl releaseIdImpl = new ReleaseIdImpl(
                getReleaseId(MainetConstants.Rules.Artifacts.WATER_TAX,
                        environment.getProperty("drools.project.watertax")));
        KieContainer container = kieServices.newKieContainer(releaseIdImpl);
        startScanArtifact(kieServices, container, scanInterval);
        LOGGER.info("Water Tax artifact Loading End...");
        return container;
    }

    /**
     * RNLRate Master artifact loading
     * @return
     */

    @Bean
    public KieContainer kieRNLRateContainer() {
        LOGGER.info(" RNLRate Master artifact Loading Start...");
        KieServices kieServices = KieServices.Factory.get();
        ReleaseIdImpl releaseIdImpl = new ReleaseIdImpl(
                getReleaseId(MainetConstants.Rules.Artifacts.RNL_RATE_MASTER,
                        environment.getProperty("drools.project.rnl")));
        KieContainer container = kieServices.newKieContainer(releaseIdImpl);
        startScanArtifact(kieServices, container, scanInterval);
        LOGGER.info(" RNLRate Master artifact Loading End...");
        return container;
    }

    /**
     * RTI artifact loading
     * @return
     */

    @Bean
    public KieContainer kieRTIContainer() {
        LOGGER.info(" RTI artifact Loading Start...");
        KieServices kieServices = KieServices.Factory.get();
        ReleaseIdImpl releaseIdImpl = new ReleaseIdImpl(
                getReleaseId(MainetConstants.Rules.Artifacts.RTI_RATE_MASTER,
                        environment.getProperty("drools.project.rti")));
        KieContainer container = kieServices.newKieContainer(releaseIdImpl);
        startScanArtifact(kieServices, container, scanInterval);
        LOGGER.info(" RTI Master artifact Loading End...");
        return container;
    }

    /**
     * ALV artifact loading
     * @return
     */

    @Bean
    public KieContainer kiePropertyALVContainer() {
        LOGGER.info(" ALV  artifact Loading Start...");
        KieServices kieServices = KieServices.Factory.get();
        ReleaseIdImpl releaseIdImpl = new ReleaseIdImpl(
                getReleaseId(MainetConstants.Rules.Artifacts.PROPERTY_ALV_MASTER,
                        environment.getProperty("drools.project.propertyalv")));
        KieContainer container = kieServices.newKieContainer(releaseIdImpl);
        startScanArtifact(kieServices, container, scanInterval);
        LOGGER.info(" ALV artifact Loading End...");
        return container;
    }

    /**
     * Property Factor artifact loading
     * @return
     */

    @Bean
    public KieContainer kiePropertyFactorContainer() {
        LOGGER.info(" Property Factor artifact Loading Start...");
        KieServices kieServices = KieServices.Factory.get();
        ReleaseIdImpl releaseIdImpl = new ReleaseIdImpl(
                getReleaseId(MainetConstants.Rules.Artifacts.PROPERTY_FACTOR_MASTER,
                        environment.getProperty("drools.project.propertyfactor")));
        KieContainer container = kieServices.newKieContainer(releaseIdImpl);
        startScanArtifact(kieServices, container, scanInterval);
        LOGGER.info(" Property Facto artifact Loading End...");
        return container;
    }

    /**
     * Property Rate artifact loading
     * @return
     */

    @Bean
    public KieContainer kiePropertyRateContainer() {
        LOGGER.info(" Property Rate artifact Loading Start...");
        KieServices kieServices = KieServices.Factory.get();
        ReleaseIdImpl releaseIdImpl = new ReleaseIdImpl(
                getReleaseId(MainetConstants.Rules.Artifacts.PROPERTY_RATE_MASTER,
                        environment.getProperty("drools.project.propertyrate")));
        KieContainer container = kieServices.newKieContainer(releaseIdImpl);
        startScanArtifact(kieServices, container, scanInterval);
        LOGGER.info(" Property Rate artifact Loading End...");
        return container;
    }

    /**
     * Property SDDR Rate artifact loading
     * @return
     */

    @Bean
    public KieContainer kiePropertySDDRRateContainer() {
        LOGGER.info(" Property SDDR Rate artifact Loading Start...");
        KieServices kieServices = KieServices.Factory.get();
        ReleaseIdImpl releaseIdImpl = new ReleaseIdImpl(
                getReleaseId(MainetConstants.Rules.Artifacts.PROPERTY_SDDR_RATE,
                        environment.getProperty("drools.project.propertysddrrate")));
        KieContainer container = kieServices.newKieContainer(releaseIdImpl);
        startScanArtifact(kieServices, container, scanInterval);
        LOGGER.info(" Property SDDR Rate artifact Loading End...");
        return container;
    }

    @Bean
    public KieContainer kieTDSContainer() {
        LOGGER.info(" TDS artifact Loading Start...");
        KieServices kieServices = KieServices.Factory.get();
        ReleaseIdImpl releaseIdImpl = new ReleaseIdImpl(
                getReleaseId(MainetConstants.Rules.Artifacts.TDS_CALCULATION,
                        environment.getProperty("drools.project.tdscalculation")));
        KieContainer container = kieServices.newKieContainer(releaseIdImpl);
        startScanArtifact(kieServices, container, scanInterval);
        LOGGER.info(" TDS artifact Loading End...");
        return container;
    }

    /**
     * start polling the newly added rules from Maven repository on regular time interval
     * 
     * @param kieServices : pass KieServices as per Module
     * @param kieContainer : pass KieContainer as per Module
     * @param milliSeconds : time interval in milliSeconds
     */
    private void startScanArtifact(KieServices kieServices, KieContainer kieContainer, long milliSeconds) {

        // control auto-scanning
        if (MainetConstants.AUTO_SCAN.equalsIgnoreCase(environment.getProperty("drools.artifact.autoscan"))) {
            KieScanner kScanner = kieServices.newKieScanner(kieContainer);
            // Start the KieScanner polling the Maven repository at provided time interval
            kScanner.start(milliSeconds);
        }
    }

    /**
     * to fetch released id from TB_BPM_DEPLOYMENT table, if released id not present than it will return default released it with
     * latest deployed version
     * @param deploymentId
     * @param defaultReleaseId
     * @return
     */
    private String getReleaseId(String deploymentId, String defaultReleaseId) {
        String releaseId = null;
        BpmDeployment bpmDeployment = bpmDeploymentRepository.findByArtifactIdAndBpmRuntime(deploymentId,
                MainetConstants.WorkFlow.ImplementationService.JBPM);
        if (bpmDeployment != null) {
            LOGGER.info("Process with Active deployment version of BPM-Deployment Master for Deployment ID : " + deploymentId);
            releaseId = bpmDeployment.toString();
        } else {
            LOGGER.info("Process with default release Id : " + defaultReleaseId);
            releaseId = defaultReleaseId;
        }
        return releaseId;
    }

    @Bean
    public KieContainer kieSWMRateContainer() {
        LOGGER.info(" SWMRate Master artifact Loading Start...");
        KieServices kieServices = KieServices.Factory.get();
        ReleaseIdImpl releaseIdImpl = new ReleaseIdImpl(
                getReleaseId(MainetConstants.Rules.Artifacts.SWM_RATE_MASTER,
                        environment.getProperty("drools.project.swm")));
        KieContainer container = kieServices.newKieContainer(releaseIdImpl);
        startScanArtifact(kieServices, container, scanInterval);
        LOGGER.info(" SWMRate Master artifact Loading End...");
        return container;
    }

    @Bean
    public KieContainer kieWMSRateContainer() {
        LOGGER.info("WMSRate Master artifact Loading Start...");
        KieServices kieServices = KieServices.Factory.get();
        ReleaseIdImpl releaseIdImpl = new ReleaseIdImpl(
                getReleaseId(MainetConstants.Rules.Artifacts.WMS_RATE_MASTER,
                        environment.getProperty("drools.project.wms")));
        KieContainer container = kieServices.newKieContainer(releaseIdImpl);
        startScanArtifact(kieServices, container, scanInterval);
        LOGGER.info(" WMSRate Master artifact Loading End...");
        return container;
    }

    @Bean
    public KieContainer kieMarketLicenseContainer() {
        LOGGER.info("ML NewTradeLicense artifact Loading Start...");
        KieServices kieServices = KieServices.Factory.get();
        ReleaseIdImpl releaseIdImpl = new ReleaseIdImpl(
                getReleaseId(MainetConstants.Rules.Artifacts.MLNewTradeLicense,
                        environment.getProperty("drools.project.ml")));
        KieContainer container = kieServices.newKieContainer(releaseIdImpl);
        startScanArtifact(kieServices, container, scanInterval);
        LOGGER.info("ML NewTradeLicense artifact Loading End...");
        return container;
    }

    /**
     * This Method is used for new Advertisement and Hoarding
     * @return
     */
    @Bean
    public KieContainer kieAdhRateContainer() {
        LOGGER.info("ADH Rate Master artifact Loading Start...");
        KieServices kieServices = KieServices.Factory.get();
        ReleaseIdImpl releaseIdImpl = new ReleaseIdImpl(
                getReleaseId(MainetConstants.Rules.Artifacts.ADH_RATE_MASTER, environment.getProperty("drools.project.adh")));
        KieContainer container = kieServices.newKieContainer(releaseIdImpl);
        startScanArtifact(kieServices, container, scanInterval);
        LOGGER.info("ADH Rate Master artifact Loading End...");
        return container;
    }
    
    @Bean
    public KieContainer kieMrmRateContainer() {
        LOGGER.info("MRM Rate Master artifact Loading Start...");
        KieServices kieServices = KieServices.Factory.get();
        ReleaseIdImpl releaseIdImpl = new ReleaseIdImpl(
                getReleaseId(MainetConstants.Rules.Artifacts.MRM_RATE_MASTER, environment.getProperty("drools.project.mrm")));
        KieContainer container = kieServices.newKieContainer(releaseIdImpl);
        startScanArtifact(kieServices, container, scanInterval);
        LOGGER.info("MRM Rate Master artifact Loading End...");
        return container;
    }
    
    @Bean
    public KieContainer kieBNDRateMasterContainer() {
        LOGGER.info("BND Rate Master artifact Loading Start...");
        KieServices kieServices = KieServices.Factory.get();
        ReleaseIdImpl releaseIdImpl = new ReleaseIdImpl(
                getReleaseId(MainetConstants.Rules.Artifacts.BND_RATE_MASTER,
                        environment.getProperty("drools.project.bnd")));
        KieContainer container = kieServices.newKieContainer(releaseIdImpl);
        startScanArtifact(kieServices, container, scanInterval);
        LOGGER.info("BND Rate Master artifact Loading End...");
        return container;
    }
    
    @Bean
    public KieContainer kieAdditionalServicesRateContainer() {
        LOGGER.info("AdditionalServicesRate Master artifact Loading Start...");
        KieServices kieServices = KieServices.Factory.get();
        ReleaseIdImpl releaseIdImpl = new ReleaseIdImpl(
                getReleaseId(MainetConstants.Rules.Artifacts.ADDITIONAL_SERVICES_RATE_MASTER,
                        environment.getProperty("drools.project.additionalservices")));
        KieContainer container = kieServices.newKieContainer(releaseIdImpl);
        startScanArtifact(kieServices, container, scanInterval);
        LOGGER.info(" AdditionalServicesRate Master artifact Loading End...");
        return container;
    }
    
    @Bean
    public KieContainer kieBPMSRateMasterContainer(){
    	LOGGER.info("BPMSRateMaster artifact Loading Start...");
        KieServices kieServices = KieServices.Factory.get();
        ReleaseIdImpl releaseIdImpl = new ReleaseIdImpl(
                getReleaseId(MainetConstants.Rules.Artifacts.BPMS_RATE_MASTER,
                        environment.getProperty("drools.project.bpms")));
        KieContainer container = kieServices.newKieContainer(releaseIdImpl);
        startScanArtifact(kieServices, container, scanInterval);
        LOGGER.info(" BPMSRateMaster Master artifact Loading End...");
        return container;
    }

}

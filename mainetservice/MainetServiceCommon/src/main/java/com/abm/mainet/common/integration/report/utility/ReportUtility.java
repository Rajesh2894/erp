/**
 *
 */
package com.abm.mainet.common.integration.report.utility;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.mapper.FileUploadValidator;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

/**
 * @author Vikrant.Thakur
 * @since 22 September 2014. This Class is Develop to call Report. From Both Side. i.e, From Oracle Reports and Jasper Report. The
 * main Intension of this class is from Regional Reports
 *
 */
@Component
public class ReportUtility {
    /**
     * @formName :contains Report Name,if dynamically fetched,pass before calling the generate Report method as in formName
     * @viewName :This contains the controller view name
     * @param parameters : it is Your Constant String comes from ReportConstant Class.
     * @param reportName : report name to be read
     * @param reportFormat : Format of remort e.g, A4,A3 etc.
     * @param inputValues : Your Runtime value pass to Oracle Report.
     * @param isDevelopment : true = when it is live release, False = for testing purpose, to see output. but while testing set
     * ReportConstant.TEMP_PDF_PATH as your lan machine ip address. so that output will generate on that machine
     * @return String value wheather Success of fail. or server error
     */

    private static Logger log = Logger.getLogger(ReportUtility.class);

    /*
     * @Autowired private IReportService iReportService;
     */

    /*
     * public static BootstrapPipeConnector bootstrapSocketConnector = null; public static XComponentContext remoteContext = null;
     */

    private static Map<String, JasperReport> jasperReportLookUp = new HashMap<>();

    /*
     * public String generateReportUsingOracleFormsAndReportService(String parameters,String codeValue,Object[]
     * inputValues,boolean isDevelopment, BindingResult bindingResult,String viewName, String formName) { String reportName="";
     * ApplicationSession appsession = ApplicationSession.getInstance(); try { if(formName == null) { reportName =
     * (iReportService.getReportName( viewName , codeValue)).toUpperCase(); } else { reportName = formName.toUpperCase(); }
     * if(reportName==null) { bindingResult.addError(new ObjectError(MainetConstants.operator.EMPTY,
     * "Report Name  Not Congigure in TB_COM_REPORT_INFO table"));
     * log.info("Report Name  Not Congigure in TB_COM_REPORT_INFO table"); return MainetConstants.SERVER_ERROR; } Long value =
     * iReportService.reportFormat(reportName); if(value == -1L) { bindingResult.addError(new
     * ObjectError(MainetConstants.operator.EMPTY, appsession.getMessage(ReportConstant.REPORT_FORMAT_ERROR)));
     * log.info("Report Format Not Congigure in TB_COM_DEPT_REPORT_FORMAT table"); return MainetConstants.SERVER_ERROR; } LookUp
     * looklup = getNonHierarchicalLookUpObject(value); String reportFormat = looklup.getDescLangFirst(); String d2kReportPortNo =
     * appsession.getMessage(ReportConstant.REPORT_PORT); String D2KContext = appsession.getMessage(ReportConstant.D2KContext);
     * String param1 = appsession.getMessage(ReportConstant.REPORT_PARAM1); String param1val =
     * appsession.getMessage(ReportConstant.REPORT_PARAM1_VAL); String param4 =
     * appsession.getMessage(ReportConstant.REPORT_PARAM4); String d2kURL = appsession.getMessage(ReportConstant.REPORT_PROTOCOL)
     * + ReportConstant.HTTP_PROTOCOL_COLON + appsession.getMessage(ReportConstant.REPORT_IP) + MainetConstants.operator.QUOTES +
     * d2kReportPortNo + D2KContext + MainetConstants.operator.QUE_MARK + param1 + MainetConstants.operator.EQUAT_TO + param1val +
     * MainetConstants.operator.AMPERSENT + param4 + MainetConstants.operator.EQUAT_TO ; String inuptFileName =
     * appsession.getMessage(ReportConstant.REPORT_PATH); String outFileName = UserSession.getCurrent().getEmployee().getEmpId() +
     * Utility.getTimestamp() + ReportConstant.PDF_EXTENSION; String dbInfo= MainetConstants.operator.AMPERSENT +
     * appsession.getMessage(ReportConstant.REPORT_PARAM3) + MainetConstants.operator.EQUAT_TO +
     * appsession.getMessage(ReportConstant.REPORT_PARAM3_VAL); // String d2kReportPrefixPath = ReportConstant.TEMP_PDF_PATH;
     * String d2kReportPrefixPath = appsession.getMessage(ReportConstant.TEMP_PDF_PATH); File file=new File(d2kReportPrefixPath);
     * if(! file.isDirectory()) { file.mkdir(); } file=new File(d2kReportPrefixPath + ReportConstant.PDFFOLDERNAME +
     * MainetConstants.WINDOWS_SLASH); if(! file.isDirectory()) { file.mkdir(); } String destnFile =
     * ReportConstant.REPORT_PARAM_STRING1 + reportFormat + ReportConstant.REPORT_PARAM_STRING2; String parameterPass =
     * parameters; Object array []= inputValues; String newparameter="";
     *//**
        * Above code is used to read data from Property file and used to generate d2k url which is required. Here All the runtime
        * value will Append to COnstant String read from ReportConstant Class.
        *
        *//*
           * int i=0; for (String retval: parameterPass.split(MainetConstants.operator.HASH)) { newparameter = newparameter +
           * retval + array[i]; i++; } String uRLGenerated = d2kURL + inuptFileName + reportName + dbInfo + destnFile +
           * d2kReportPrefixPath + ReportConstant.PDFFOLDERNAME + MainetConstants.WINDOWS_SLASH + outFileName + newparameter;
           * log.error("REPORT PARAMETER :: "+newparameter); log.info("REPORT URL :: "+uRLGenerated); String pdfFileNameGenarated
           * = ReportConstant.PDFFOLDERNAME + MainetConstants.WINDOWS_SLASH + outFileName;
           * if(generateReport(uRLGenerated,outFileName,isDevelopment)) return pdfFileNameGenarated; else {
           * bindingResult.addError(new ObjectError(MainetConstants.operator.EMPTY,
           * appsession.getMessage(ReportConstant.REPORT_FORMAT_INTERNAL_ERROR))); return MainetConstants.SERVER_ERROR; } } catch
           * (Exception e) { e.printStackTrace(); log.info(e.toString()); return MainetConstants.SERVER_ERROR; } }
           */

    /**
     *
     * @param D2kUrl = Url Generated for d2k whith their Corresponding Runtime vLue.
     * @param outFileName = OutPut file name. which is to be generate
     * @param isDevelopment : true = when it is live release, False = for testing purpose. to see output.
     * @return true when report generate , False when Some error occur
     */
    /*
     * private static boolean generateReport(String D2kUrl,String outFileName,boolean isDevelopment) { try { ApplicationSession
     * appsession = ApplicationSession.getInstance(); //log.error("D2k Report Path : "+ D2kUrl); URL obj = new URL(D2kUrl);
     * HttpURLConnection con = (HttpURLConnection) obj.openConnection(); con.connect(); // here the d2k Report will Call int
     * responseCode = con.getResponseCode(); // get the response. of call D2k Report log.error("responseCode : "+ responseCode);
     * if(responseCode == 200){ log.error("responseStatus : HTTP/1.0 200 OK"); }else if(responseCode == 401){
     * log.error("responseStatus : HTTP/1.0 401 Unauthorized"); }else if(responseCode == -1){ log.
     * error("responseStatus : no code can be discerned from the response (i.e., the response is not valid HTTP)" ); }
     *//**
        * if isDevelopment == true the files get Copy at server Directory. if false = then only file get generated to file will be
        * copy.
        */
    /*
     * // this is no longer required if(isDevelopment) { // String tempFilePath = ReportConstant.TEMP_PDF_PATH + outFileName;
     * String tempFilePath = appsession.getMessage(ReportConstant.TEMP_PDF_PATH) + outFileName; int currentLoopCount = 0;
     * checkFileisGeneratedorNotLocal(tempFilePath,currentLoopCount); File file=new File(Filepaths.getfilepath() +
     * ReportConstant.PDFFOLDERNAME); if(! file.isDirectory()) file.mkdir(); Utility.cutPasteFile(tempFilePath ,
     * Filepaths.getfilepath() + ReportConstant.PDFFOLDERNAME + MainetConstants.WINDOWS_SLASH ); } return true; } catch (Exception
     * e) { //e.printStackTrace(); log.error("Error occured while generating report ",e); return false; } }
     */
    /*
     * Below Method is overloaded For Advertising and rent module requirement. We call this method direct Service layer and not
     * require to pass model .
     */
    /*
     * public String generateReportUsingOracleFormsAndReportService(String parameters,String codeValue,Object[]
     * inputValues,boolean isDevelopment,String viewName, String formName) { String reportName=""; ApplicationSession appsession =
     * ApplicationSession.getInstance(); try { if(formName == null) { reportName = (iReportService.getReportName( viewName ,
     * codeValue)).toUpperCase(); } else { reportName = formName.toUpperCase(); } if(reportName==null) {
     * //bindingResult.addError(new ObjectError(MainetConstants.operator.EMPTY,
     * "Report Name  Not Congigure in TB_COM_REPORT_INFO table"));
     * log.error("Report Name  Not Congigure in TB_COM_REPORT_INFO table"); return MainetConstants.SERVER_ERROR; } Long value =
     * iReportService.reportFormat(reportName); if(value == -1L) { //bindingResult.addError(new
     * ObjectError(MainetConstants.operator.EMPTY, appsession.getMessage(ReportConstant.REPORT_FORMAT_ERROR)));
     * log.error("Report Format Not Congigure in TB_COM_DEPT_REPORT_FORMAT table" + "value="+value); return
     * MainetConstants.SERVER_ERROR; } LookUp looklup = getNonHierarchicalLookUpObject(value); String reportFormat =
     * looklup.getDescLangFirst(); String d2kReportPortNo = appsession.getMessage(ReportConstant.REPORT_PORT); String D2KContext =
     * appsession.getMessage(ReportConstant.D2KContext); String param1 = appsession.getMessage(ReportConstant.REPORT_PARAM1);
     * String param1val = appsession.getMessage(ReportConstant.REPORT_PARAM1_VAL); String param4 =
     * appsession.getMessage(ReportConstant.REPORT_PARAM4); String d2kURL = appsession.getMessage(ReportConstant.REPORT_PROTOCOL)
     * + ReportConstant.HTTP_PROTOCOL_COLON + appsession.getMessage(ReportConstant.REPORT_IP) + MainetConstants.operator.QUOTES +
     * d2kReportPortNo + D2KContext + MainetConstants.operator.QUE_MARK + param1 + MainetConstants.operator.EQUAT_TO + param1val +
     * MainetConstants.operator.AMPERSENT + param4 + MainetConstants.operator.EQUAT_TO ; String inuptFileName =
     * appsession.getMessage(ReportConstant.REPORT_PATH); String outFileName = UserSession.getCurrent().getEmployee().getEmpId() +
     * Utility.getTimestamp() + ReportConstant.PDF_EXTENSION; String dbInfo= MainetConstants.operator.AMPERSENT +
     * appsession.getMessage(ReportConstant.REPORT_PARAM3) + MainetConstants.operator.EQUAT_TO +
     * appsession.getMessage(ReportConstant.REPORT_PARAM3_VAL); // String d2kReportPrefixPath = ReportConstant.TEMP_PDF_PATH;
     * String d2kReportPrefixPath = appsession.getMessage(ReportConstant.TEMP_PDF_PATH); File file=new File(d2kReportPrefixPath);
     * if(! file.isDirectory()) { file.mkdir(); } file=new File(d2kReportPrefixPath + ReportConstant.PDFFOLDERNAME +
     * MainetConstants.WINDOWS_SLASH); if(! file.isDirectory()) { file.mkdir(); } String destnFile =
     * ReportConstant.REPORT_PARAM_STRING1 + reportFormat + ReportConstant.REPORT_PARAM_STRING2; String parameterPass =
     * parameters; Object array []= inputValues; String newparameter="";
     *//**
        * Above code is used to read data from Property file and used to generate d2k url which is required. Here All the runtime
        * value will Append to COnstant String read from ReportConstant Class.
        *
        *//*
           * int i=0; for (String retval: parameterPass.split(MainetConstants.operator.HASH)) { newparameter = newparameter +
           * retval + array[i]; i++; } String uRLGenerated = d2kURL + inuptFileName + reportName + dbInfo + destnFile +
           * d2kReportPrefixPath + ReportConstant.PDFFOLDERNAME + MainetConstants.WINDOWS_SLASH + outFileName + newparameter;
           * log.info("REPORT PARAMETER :: "+newparameter); String pdfFileNameGenarated = ReportConstant.PDFFOLDERNAME +
           * MainetConstants.WINDOWS_SLASH + outFileName; if(generateReport(uRLGenerated,outFileName,isDevelopment)) return
           * pdfFileNameGenarated; else { //bindingResult.addError(new ObjectError(MainetConstants.operator.EMPTY,
           * appsession.getMessage(ReportConstant.REPORT_FORMAT_INTERNAL_ERROR))); return MainetConstants.SERVER_ERROR; } } catch
           * (Exception e) { e.printStackTrace(); log.info(e.toString()); return MainetConstants.SERVER_ERROR; } }
           */

    /**
     *
     * @param request : it is HttpServletRequest request
     * @param response : it is HttpServletResponse Response
     * @param jrxmlFileLocation : location of jrxml File which to be read.
     * @param map : empty when no sub report. map contan data if report contaion subreport.
     * @param dataCollection : list of DTO required to fill the jasper Report
     * @param jasperReportPrefixPath : Prefix path which the fill generated to be store
     * @return PDF file generated name. that name is generated on the basis of empid and current timestamp
     */
    /*
     * public static String generateReportFromCollectionUtility(HttpServletRequest request, HttpServletResponse response, String
     * jrxmlFileLocation, Map<String, Object> map, List<?> dataCollection) { log.info("In Generate FIle"); String
     * pdfFileNameGenarated = MainetConstants.operator.EMPTY; try { String fileName =
     * UserSession.getCurrent().getEmployee().getEmpId() + Utility.getTimestamp() + MainetConstants.operator.EMPTY; String
     * jasperFileName = FileUploadValidator.getPrefixSuffixFromString(jrxmlFileLocation, MainetConstants.FILE_PATH_SEPARATOR,
     * FileUploadConstant.OperationMode.SUFFIX , org.apache.commons.lang.StringUtils.EMPTY); JasperReport jasperReport =
     * getJasperReportLookUp().get(jasperFileName); JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new
     * JRBeanCollectionDataSource(dataCollection)); jasperPrint.setName(fileName); log.info("In Side Method"); File file=new
     * File(Filepaths.getfilepath() + ReportConstant.PDFFOLDERNAME); if(! file.isDirectory()) file.mkdir(); String tempFileName =
     * Filepaths.getfilepath() + ReportConstant.PDFFOLDERNAME + MainetConstants.FILE_PATH_SEPARATOR + fileName +
     * ReportConstant.JRPRINT_EXTENSION ; String pdfNameGenarated = Filepaths.getfilepath() + ReportConstant.PDFFOLDERNAME +
     * MainetConstants.FILE_PATH_SEPARATOR + "JasperReports - " + fileName + ReportConstant.PDF_EXTENSION;; pdfFileNameGenarated =
     * ReportConstant.PDFFOLDERNAME + MainetConstants.WINDOWS_SLASH + "JasperReports - " + fileName +
     * ReportConstant.PDF_EXTENSION;; JRSaver.saveObject(jasperPrint, tempFileName); PrintRequestAttributeSet
     * printRequestAttributeSet = new HashPrintRequestAttributeSet(); printRequestAttributeSet.add(MediaSizeName.ISO_A4);
     * PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet(); printServiceAttributeSet.add(new
     * PrinterName(ReportConstant.PDF_CREATOR, null)); JRPrintServiceExporter exporter = new JRPrintServiceExporter();
     * exporter.setExporterInput(new SimpleExporterInput(tempFileName)); SimplePrintServiceExporterConfiguration configuration =
     * new SimplePrintServiceExporterConfiguration(); configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
     * configuration.setPrintServiceAttributeSet(printServiceAttributeSet); configuration.setDisplayPageDialog(false);
     * configuration.setDisplayPrintDialog(false); exporter.setConfiguration(configuration); log.info("Befor file Generation");
     * exporter.exportReport(); log.info("After file Generation"); // here the report will fire to PDFCreator. int
     * currentLoopCount = 0; if(! checkFileisGeneratedorNotLocal(pdfNameGenarated,currentLoopCount)) return pdfFileNameGenarated;
     * } catch (Exception exception) { exception.printStackTrace(); return MainetConstants.SERVER_ERROR; } return
     * pdfFileNameGenarated; }
     */
    public static String generateReportFromCollectionUtility(final HttpServletRequest request, final HttpServletResponse response,
            final String jrxmlFileLocation, final Map<String, Object> map, final List<?> dataCollection,Long empId) {
        log.info("In Generate FIle");

        String pdfFileNameGenarated = MainetConstants.operator.EMPTY;
        final File interfaceFile = null;
        try {
            final String fileName = empId + Utility.getTimestamp()
                    + MainetConstants.operator.EMPTY;
            log.info("fileName " + fileName);
            final String jasperFileName = FileUploadValidator.getPrefixSuffixFromString(jrxmlFileLocation,
                    MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.OperationMode.SUFFIX,
                    org.apache.commons.lang.StringUtils.EMPTY);
            log.info("jasperFileName " + jasperFileName);
            log.info("jrxmlFileLocation " + jrxmlFileLocation);
            final JasperReport jasperReport = getJasperReportLookUp().get(jasperFileName);
            log.info("getJasperReportLookUp() " + getJasperReportLookUp());
            log.info("JasperReport object " + jasperReport);
            final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map,
                    new JRBeanCollectionDataSource(dataCollection));
            log.info("jasperPrint " + jasperPrint);
            jasperPrint.setName(fileName);
            log.info("In Side Method");
            final File file = new File(Filepaths.getfilepath() + MainetConstants.PDFFOLDERNAME);
            log.info("file path " + (file!=null ? file.getPath() :"object is null"));

            if (!file.isDirectory()) {
                log.info("file is not a directory");
            	file.mkdir();
            }

            Filepaths.getfilepath();

            final String pdfNameGenarated = Filepaths.getfilepath() + MainetConstants.PDFFOLDERNAME
                    + MainetConstants.FILE_PATH_SEPARATOR + "JasperReports - " + fileName
                    + MainetConstants.PDF_EXTENSION;
            log.info("pdfNameGenarated " + pdfNameGenarated);

            pdfFileNameGenarated = MainetConstants.PDFFOLDERNAME + MainetConstants.FILE_PATH_SEPARATOR + "JasperReports - "
                    + fileName + MainetConstants.PDF_EXTENSION;
            log.info("pdfFileNameGenarated" + pdfFileNameGenarated);

            /* if(UserSession.getCurrent().getLanguageId()==1){ */
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfNameGenarated);
            log.info("After exportReportToPdfFile ");

            /*
             * }else{ String interfaceFileGenarated = Filepaths.getfilepath() + ReportConstant.PDFFOLDERNAME +
             * MainetConstants.FILE_PATH_SEPARATOR +"JasperReports - "+ fileName + ReportConstant.RTF_EXTENSION;; interfaceFile =
             * new File(interfaceFileGenarated); exportReportToRtfFile(jasperPrint,interfaceFile); String pdfFileNamePath =
             * pdfNameGenarated.replace("\\","/"); convertWithConnector(interfaceFileGenarated,pdfFileNamePath); }
             */
        } catch (final Exception exception) {
            exception.printStackTrace();
            log.error("In catch: Exception in generating file " + exception.getMessage());
            return MainetConstants.SERVER_ERROR;
        } catch (final Throwable t) {
            t.printStackTrace();
            log.error("In Throwable:  " + t.getMessage());

            return MainetConstants.SERVER_ERROR;
        } finally {
            if (interfaceFile != null) {
                if (interfaceFile.exists()) {
                    interfaceFile.delete();
                }
            }
        }
        return pdfFileNameGenarated;
    }

    public static String generateReportFromCollectionUtilityForRTI(final HttpServletRequest request, final Long appNo,
            final String jrxmlFileLocation, final Map<String, Object> map, final List<?> dataCollection,
            final String filePrefix) {
        log.info("In Generate FIle");

        String pdfFileNameGenarated = MainetConstants.operator.EMPTY;
        try {
            final String fileName = filePrefix + appNo + MainetConstants.operator.EMPTY;

            final String jasperFileName = FileUploadValidator.getPrefixSuffixFromString(jrxmlFileLocation,
                    MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.OperationMode.SUFFIX,
                    org.apache.commons.lang.StringUtils.EMPTY);

            final JasperReport jasperReport = getJasperReportLookUp().get(jasperFileName);

            // JasperDesign jasperDesign = JRXmlLoader.load(jrxmlFileLocation);
            // JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map,
                    new JRBeanCollectionDataSource(dataCollection));

            jasperPrint.setName(fileName);

            log.info("In Side Method");

            final File file = new File(Filepaths.getfilepath() + MainetConstants.PDFFOLDERNAME);

            if (!file.isDirectory()) {
                file.mkdir();
            }

            final String tempFileName = Filepaths.getfilepath() + MainetConstants.PDFFOLDERNAME
                    + MainetConstants.FILE_PATH_SEPARATOR + fileName + MainetConstants.JRPRINT_EXTENSION;

            final String pdfNameGenarated = Filepaths.getfilepath() + MainetConstants.PDFFOLDERNAME
                    + MainetConstants.FILE_PATH_SEPARATOR + "JasperReports - " + fileName
                    + MainetConstants.PDF_EXTENSION;
            ;

            pdfFileNameGenarated = MainetConstants.PDFFOLDERNAME + MainetConstants.WINDOWS_SLASH + "JasperReports - "
                    + fileName + MainetConstants.PDF_EXTENSION;
            ;

            JRSaver.saveObject(jasperPrint, tempFileName);

            final PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();

            printRequestAttributeSet.add(MediaSizeName.ISO_A4);

            final PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();

            printServiceAttributeSet.add(new PrinterName(MainetConstants.PDF_CREATOR, null));

            final JRPrintServiceExporter exporter = new JRPrintServiceExporter();

            exporter.setExporterInput(new SimpleExporterInput(tempFileName));

            final SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();

            configuration.setPrintRequestAttributeSet(printRequestAttributeSet);

            configuration.setPrintServiceAttributeSet(printServiceAttributeSet);

            configuration.setDisplayPageDialog(false);

            configuration.setDisplayPrintDialog(false);

            exporter.setConfiguration(configuration);

            log.info("Befor file Generation");

            exporter.exportReport();

            log.info("After file Generation");

            // here the report will fire to PDFCreator.

            final int currentLoopCount = 0;

            if (!checkFileisGeneratedorNotLocal(pdfNameGenarated, currentLoopCount)) {
                return pdfFileNameGenarated;
            }
        } catch (final Exception exception) {
            exception.printStackTrace();

            return MainetConstants.SERVER_ERROR;
        }

        return pdfFileNameGenarated;
    }

    /**
     * This method is used to check the file generated or not. if file is generated then it will return true. else it will wait
     * until file get generated.
     *
     * @param tempFileName : file name which is to be check weather it is generated or not.
     * @return true if file is generated else false when some error occur.
     *
     */
    public static boolean checkFileisGeneratedorNotLocal(final String tempFileName, int currentLoopCount) {
        log.info("TempFilePath: " + tempFileName);

        final File file = new File(tempFileName);

        if (currentLoopCount <= 25) {
            if (file.exists()) {
                log.info("File Found Success Fully");

                return true;
            } else {
                try {
                    Thread.sleep(1500);

                    currentLoopCount++;
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                    return false;
                }
                checkFileisGeneratedorNotLocal(tempFileName, currentLoopCount);
            }
        }
        return true;
    }

    public static LookUp getNonHierarchicalLookUpObject(final long lookUpId) {
        return ApplicationSession.getInstance()
                .getNonHierarchicalLookUp(UserSession.getCurrent().getOrganisation().getOrgid(), lookUpId);
    }

    public static ReportUtility getCurrent() {
        return ApplicationContextProvider.getApplicationContext().getBean(ReportUtility.class);
    }

    public static void compileAndLoadAllReport() {

        final String path = Filepaths.getfilepath() + MainetConstants.JASPER_REPORT_NAME;

        final File root = new File(path);

        final File[] list = root.listFiles();

        try {
            JasperDesign jasperDesign = null;

            JasperReport jasperReport = null;
            if (list != null) {
                for (final File file : list) {
                    final String fileAbsolutePath = file.getAbsolutePath();

                    final String ext = StringUtils.getFilenameExtension(fileAbsolutePath);

                    if (ext.equalsIgnoreCase(MainetConstants.REPORT_EXTENSION)) {
                        jasperDesign = JRXmlLoader.load(fileAbsolutePath);

                        jasperReport = JasperCompileManager.compileReport(jasperDesign);

                        getJasperReportLookUp().put(file.getName(), jasperReport);

                        System.out.println("Report Compile And Load : " + fileAbsolutePath);
                    }
                }
            }

        } catch (final Exception e) {

            e.printStackTrace();
        }

    }

    public static void exportReportToRtfFile(final JasperPrint jasperPrint, final File destFile) throws JRException, Exception {
        final JRRtfExporter exporter = new JRRtfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleWriterExporterOutput(destFile));
        exporter.exportReport();
    }

    public static void exportReportToDocxFile(final JasperPrint jasperPrint, final String destFilePath)
            throws JRException, Exception {
        final JRDocxExporter exporter = new JRDocxExporter();
        final File destFile = new File(destFilePath);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        // exporter.setExporterOutput(new SimpleWriterExporterOutput(destFile));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(destFile));
        exporter.exportReport();
    }

    /*
     * private static void convertWithConnector(String loadUrl, String storeUrl) throws Exception, IllegalArgumentException,
     * IOException, BootstrapException { String OOO_EXEC_FOLDER = ""; if(System.getenv("UNO_PATH")==null) { OOO_EXEC_FOLDER =
     * System.getProperty("UNO_PATH"); } else { OOO_EXEC_FOLDER =
     * System.getenv("UNO_PATH");//"C:/Program Files/LibreOffice 5/program/"; } // Create OOo server with additional
     * -nofirststartwizard option List oooOptions = OOoServer.getDefaultOOoOptions(); oooOptions.add("-nofirststartwizard");
     * OOoServer oooServer = new OOoServer(OOO_EXEC_FOLDER, oooOptions); // Connect to OOo if(bootstrapSocketConnector == null){
     * bootstrapSocketConnector = new BootstrapPipeConnector(oooServer); } if(remoteContext == null){ remoteContext =
     * bootstrapSocketConnector.connect(); } convert(loadUrl, storeUrl, remoteContext); }
     */

    /*
     * protected static void convert(String loadUrl, String storeUrl, XComponentContext remoteContext) throws
     * IllegalArgumentException, IOException, Exception { XMultiComponentFactory remoteServiceManager =
     * remoteContext.getServiceManager(); Object desktop =
     * remoteServiceManager.createInstanceWithContext("com.sun.star.frame.Desktop", remoteContext); XDesktop xDesktop = (XDesktop)
     * UnoRuntime.queryInterface( XDesktop.class, desktop); XComponentLoader xcomponentloader = (XComponentLoader)
     * UnoRuntime.queryInterface(XComponentLoader.class,xDesktop); String sUrl = "file:///" + loadUrl; PropertyValue[]
     * propertyValues = new PropertyValue[0]; propertyValues = new PropertyValue[1]; propertyValues[0] = new PropertyValue();
     * propertyValues[0].Name = "Hidden"; propertyValues[0].Value = new Boolean(true); XComponent objectDocumentToStore =
     * xcomponentloader.loadComponentFromURL(sUrl, "_blank", 0, propertyValues); PropertyValue[] conversionProperties = new
     * PropertyValue[1]; conversionProperties[0] = new PropertyValue(); conversionProperties[0].Name = "FilterName";
     * conversionProperties[0].Value = "writer_pdf_Export"; XStorable xstorable = (XStorable)
     * UnoRuntime.queryInterface(XStorable.class,objectDocumentToStore); xstorable.storeToURL("file:///" + storeUrl,
     * conversionProperties); System.out.println("*********converted************"); com.sun.star.util.XCloseable xCloseable =
     * UnoRuntime.queryInterface( com.sun.star.util.XCloseable.class, xstorable); if ( xCloseable != null ) {
     * xCloseable.close(false); } else { com.sun.star.lang.XComponent xComp = UnoRuntime.queryInterface(
     * com.sun.star.lang.XComponent.class, xstorable); xComp.dispose(); } // xDesktop.terminate(); }
     */
    /*
     * private static XComponentLoader getComponentLoader(XComponentContext remoteContext) throws Exception {
     * XMultiComponentFactory remoteServiceManager = remoteContext.getServiceManager(); Object desktop =
     * remoteServiceManager.createInstanceWithContext("com.sun.star.frame.Desktop", remoteContext); XComponentLoader
     * xcomponentloader = (XComponentLoader) UnoRuntime.queryInterface(XComponentLoader.class,desktop); return xcomponentloader; }
     */

    public static Map<String, JasperReport> getJasperReportLookUp() {
        return jasperReportLookUp;
    }

    public static void setJasperReportLookUp(final Map<String, JasperReport> jasperReportLookUp) {
        ReportUtility.jasperReportLookUp = jasperReportLookUp;
    }

}

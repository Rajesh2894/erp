package com.abm.mainet.bnd.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.domain.BirthCertificateEntity;
import com.abm.mainet.bnd.domain.BirthRegistrationEntity;
import com.abm.mainet.bnd.domain.DeathCertEntity;
import com.abm.mainet.bnd.domain.TbBdCertCopy;
import com.abm.mainet.bnd.domain.TbDeathreg;
import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.dto.TbBdCertCopyDTO;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.bnd.repository.BirthCertificateRepository;
import com.abm.mainet.bnd.repository.BirthDeathCertificateCopyRepository;
import com.abm.mainet.bnd.repository.BirthRegRepository;
import com.abm.mainet.bnd.repository.CfcInterfaceJpaRepository;
import com.abm.mainet.bnd.repository.DeathCertificateRepository;
import com.abm.mainet.bnd.repository.DeathRegistrationRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.DoubleBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

@Service
public class PrintBNDCertificateServiceImpl implements PrintBNDCertificateService{
	
	public static final float PADDING=10f;
	public static final float TEXT_SIZE_10=10f;
	public static final float TEXT_SIZE_9=9f;
	public static final float TEXT_SIZE_8=8f;
	public static final float TEXT_SIZE_12=12f;
	public static final float TEXT_SIZE_15=15f;
	private static final Logger LOGGER = Logger.getLogger(PrintBNDCertificateServiceImpl.class);

	@Autowired
	BirthDeathCertificateCopyRepository birthDeathCertificateCopyRepository;
	
	@Resource
	private CfcInterfaceJpaRepository tbBdCfcInterfaceJpaRepository;
	
	@Resource
	TbServicesMstService tbServicesMstService;
	
	@Autowired
	private DeathRegistrationRepository deathRegistrationRepository;
	
	@Autowired
	private BirthRegRepository birthRegRepository;
	
	@Autowired
	private BirthCertificateRepository birthCertificateRepository;
	
	@Autowired
	private DeathCertificateRepository deathCertificateRepository;
	
	@Autowired
	private IssuenceOfBirthCertificateService issuenceOfBirthCertificateService;
	
	@Autowired
	private IssuenceOfDeathCertificateService issuenceOfDeathCertificateService;
	
	@Value("${upload.physicalPath}")
	private String filenetPath;
	
	@Override
	@Transactional
	public List<TbBdCertCopyDTO> getPrintCertificateDetails(Long ApplicationNo,Long orgId) {
		List<TbBdCertCopyDTO> dtoList=new ArrayList<TbBdCertCopyDTO>();
		List<TbBdCertCopy> list = birthDeathCertificateCopyRepository.getprintCertificateDetail(ApplicationNo, orgId);
		if(!list.isEmpty()) {
			
		 list.forEach(entity->{
			 TbBdCertCopyDTO dto=new TbBdCertCopyDTO();
			 BeanUtils.copyProperties(entity, dto);
			 dto.setRtIdOrApplicationId(entity.getAPMApplicationId());
			 if(list.get(0).getDrId()!=null) {
				 List<TbDeathreg>  tbDeathregList= deathRegistrationRepository.findData(list.get(0).getDrId());
				 dto.setRegNo(tbDeathregList.get(0).getDrRegno());
			 }else if(list.get(0).getBrId()!=null){
				 List<BirthRegistrationEntity> birthlist= birthRegRepository.findData(list.get(0).getBrId());	
				 if(!birthlist.isEmpty()) {
				 dto.setRegNo(birthlist.get(0).getBrRegNo());
				 }
			 }else if(list.get(0).getNacDrId()!=null) {
				 List<DeathCertEntity> deathCertList= deathCertificateRepository.findData(list.get(0).getNacDrId());	
				 if(!deathCertList.isEmpty()) {
				 dto.setRegNo(deathCertList.get(0).getDrRegno());
				 dto.setServiceId(deathCertList.get(0).getSmServiceId());
				 }
			 }else {
				 List<BirthCertificateEntity> birthCertlist = birthCertificateRepository.findCertData(list.get(0).getNacBrId());	
				 if(!birthCertlist.isEmpty()) {
				 dto.setRegNo(birthCertlist.get(0).getBrRegNo());
				 dto.setServiceId(birthCertlist.get(0).getSmServiceId());
			   }
			 }
			 
			 dtoList.add(dto);
		 });
		}
		return dtoList;
	}

	 @Override
	 @Transactional
	 public String findServiceBirthOrDeath(Long ApplicationNo, Long orgId,Long smServiceId) {
	 Long serviceId = tbBdCfcInterfaceJpaRepository.findServiceBirthorDeath(ApplicationNo, orgId);
	 if(serviceId==null) {
		 serviceId = smServiceId;
	 }
	 TbServicesMst serviceMas = tbServicesMstService.findById(serviceId);
	  return serviceMas.getSmShortdesc();
	}
	 @Override
	 public boolean generateCertificate(String docPath, Long applicationNo,String certificateNo){
		
		 String serviceCode = findServiceBirthOrDeath(applicationNo, UserSession.getCurrent().getOrganisation().getOrgid(),null);
		 if(serviceCode.equals(BndConstants.IBC) || serviceCode.equals(BndConstants.BRC) ||  serviceCode.equals(BndConstants.INC) ) {
		 BirthRegistrationDTO birthRgDetail = issuenceOfBirthCertificateService.getBirthRegisteredAppliDetail(null,null,null,applicationNo.toString(),UserSession.getCurrent().getOrganisation().getOrgid());
		 SimpleDateFormat sm = new SimpleDateFormat(MainetConstants.DATE_FORMAT_CAPS);
		 String appDate = sm.format(birthRgDetail.getBrDob());
		 String appRegDate = sm.format(birthRgDetail.getBrRegDate());
		 String date=sm.format(new Date());
		 birthRgDetail.setAppDateOfBirth(appDate);
		 birthRgDetail.setAppDateOfRegistration(appRegDate);
		 birthRgDetail.setNewDate(date);
		 birthRgDetail.setBrCertNo(certificateNo);
		return generateBirthCertificate(docPath,applicationNo,birthRgDetail);
		 }else if(serviceCode.equals(BndConstants.IDC) || serviceCode.equals(BndConstants.DRC)){
			 TbDeathregDTO certificateDetailList = issuenceOfDeathCertificateService.getDeathRegisteredAppliDetail(null,null,null,applicationNo.toString(),UserSession.getCurrent().getOrganisation().getOrgid());
			 SimpleDateFormat sm = new SimpleDateFormat(MainetConstants.DATE_FORMAT_CAPS);
			 String appDate = sm.format(certificateDetailList.getDrDod());
			 String appRegDate = sm.format(certificateDetailList.getDrRegdate());
			 String date=sm.format(new Date());
			 certificateDetailList.setAppDateOfDeath(appDate);
			 certificateDetailList.setAppDateOfRegistration(appRegDate);
			 certificateDetailList.setNewDate(date);
			 certificateDetailList.setDrCertNo(certificateNo);
			 return generateDeathCertificate(docPath,applicationNo,certificateDetailList);
		 }
		 return false;
	 }
	 
	 
	 public boolean generateBirthCertificate(String docPath, Long applicationNo,BirthRegistrationDTO birthRgDetail){
		 LOGGER.info("generateBirthCertificate   method started docPath: " + docPath + "applicationNo: "+applicationNo);
		 final String MANGAL =  ApplicationSession.getInstance().getMessage("mangalFont.path");
		 LOGGER.info("MAngal Font" + MANGAL);
		  final String ARIALUNICODE = ApplicationSession.getInstance().getMessage("arialUniCode.path");
		  LOGGER.info("ARIALUNICODE Font" + ARIALUNICODE);

		try {
			PdfWriter writer = new PdfWriter(docPath);
			PdfDocument pdfDoc = new PdfDocument(writer);
			pdfDoc.addNewPage();
			Document document = new Document(pdfDoc);
			FontProgram fontProgram = FontProgramFactory.createFont(ARIALUNICODE);
			PdfFont font = PdfFontFactory.createFont(fontProgram, PdfEncodings.IDENTITY_H);
			document.setFont(font);
			document.setMargins(8f, 8f, 8f,8f);
			LOGGER.info("Documnent started");
			// Adding table
			//Table table = new Table(4);
			Table table = new Table(new float[] {145,145,145,145}) // in points
			        .setWidth(580); //100 pt
			//table.setWidth(new UnitValue(UnitValue.PERCENT, 100));
			table.setHorizontalAlignment(HorizontalAlignment.LEFT);
			Border b1 = new DoubleBorder(4);
			table.setBorder(b1);
			float cellWidth=140;
			SolidLine line = new SolidLine(1f);
			line.setColor(ColorConstants.BLACK);
			LineSeparator ls = new LineSeparator(line);
			ls.setBold();
			ls.setWidth(new UnitValue(UnitValue.PERCENT, 98));
			ls.setHorizontalAlignment(HorizontalAlignment.CENTER);
		   
			Cell keyValPara0Cell1 = new Cell(1,2);
			keyValPara0Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara0Cell1.setBorder(Border.NO_BORDER);
			ImageData orgNameData0 = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificatepara11")+ApplicationSession.getInstance().getMessage("birth.certificatepara11a")+birthRgDetail.getBrCertNo(), ARIALUNICODE, 10f, false));
			Image orgNameImg0 = new Image(orgNameData0);
			keyValPara0Cell1.add(orgNameImg0.setHorizontalAlignment(HorizontalAlignment.LEFT));
			table.addCell(keyValPara0Cell1);
			
			Cell keyValPara0Cell2 = new Cell();
			keyValPara0Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara0Cell2.setBorder(Border.NO_BORDER);
			Paragraph para1 = new Paragraph("");
			keyValPara0Cell2.add(para1);
			table.addCell(keyValPara0Cell2);
			
			Cell keyValPara0Cell3 = new Cell();
			keyValPara0Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara0Cell3.setBorder(Border.NO_BORDER);
			Paragraph para3 = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificatepara12"));
			para3.setHorizontalAlignment(HorizontalAlignment.LEFT);
			para3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			para3.setTextAlignment(TextAlignment.CENTER);
			para3.setFontSize(TEXT_SIZE_10);
			// cell5tableCell1Img.setAutoScale(true);
			keyValPara0Cell3.add(para3);
			table.addCell(keyValPara0Cell3);
			
			Cell r1Cell3c = new Cell(1,4);
			r1Cell3c.setHorizontalAlignment(HorizontalAlignment.CENTER);
			r1Cell3c.setBorder(Border.NO_BORDER);
			r1Cell3c.add(ls);
			table.addCell(r1Cell3c.setPadding(PADDING));
			
			Cell r1Cell1 = new Cell();
			 LOGGER.info("Left logo" + ApplicationSession.getInstance().getMessage("leftlogo"));
			 LOGGER.info("Left logo full path" +filenetPath + ApplicationSession.getInstance().getMessage("leftlogo"));
			ImageData leftImgData = ImageDataFactory.create(filenetPath + ApplicationSession.getInstance().getMessage("leftlogo"));
			Image logoImg = new Image(leftImgData);
			logoImg.setHeight(95f);
			logoImg.setWidth(70f);
			r1Cell1.add(logoImg);
			r1Cell1.setHorizontalAlignment(HorizontalAlignment.LEFT);
			r1Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			r1Cell1.setBorder(Border.NO_BORDER);
			table.addCell(r1Cell1);

			Cell r1Cell2 = new Cell(1, 2);
			ImageData orgNameData = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificate1"), MANGAL, 12f, false));
			Image orgNameImg = new Image(orgNameData);
			r1Cell2.add(orgNameImg.setHorizontalAlignment(HorizontalAlignment.CENTER));
			Paragraph orgNameEng = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificate2"));
			orgNameEng.setHorizontalAlignment(HorizontalAlignment.CENTER);
			orgNameEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			orgNameEng.setTextAlignment(TextAlignment.CENTER);
			orgNameEng.setFontSize(12f);
			r1Cell2.add(orgNameEng);
			
			ImageData orgNameData1 = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificate3"), MANGAL, 10f, false));
			Image orgNameImg1 = new Image(orgNameData1);
			r1Cell2.add(orgNameImg1.setHorizontalAlignment(HorizontalAlignment.CENTER));
			Paragraph orgNameEng1 = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificate4"));
			orgNameEng1.setHorizontalAlignment(HorizontalAlignment.CENTER);
			orgNameEng1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			orgNameEng1.setTextAlignment(TextAlignment.CENTER);
			orgNameEng1.setFontSize(10f);
			//orgNameEng1.setMarginLeft(35f);
			r1Cell2.add(orgNameEng1);
			
			ImageData orgNameData2 = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificate5"), MANGAL, 10f, false));
			Image orgNameImg2 = new Image(orgNameData2);
			r1Cell2.add(orgNameImg2.setHorizontalAlignment(HorizontalAlignment.CENTER));
			Paragraph orgNameEng2 = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificate6"));
			orgNameEng2.setHorizontalAlignment(HorizontalAlignment.CENTER);
			orgNameEng2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			orgNameEng2.setTextAlignment(TextAlignment.CENTER);
			orgNameEng2.setFontSize(10f);
			r1Cell2.add(orgNameEng2);
			r1Cell2.setHorizontalAlignment(HorizontalAlignment.CENTER);
			r1Cell2.setVerticalAlignment(VerticalAlignment.TOP);
			r1Cell2.setBorder(Border.NO_BORDER);
			table.addCell(r1Cell2);

			Cell r1Cell3 = new Cell();
			 LOGGER.info("Right logo" + ApplicationSession.getInstance().getMessage("rightlogo"));
			ImageData rightImgData = ImageDataFactory.create(filenetPath + ApplicationSession.getInstance().getMessage("rightlogo"));
			Image logoImgRight = new Image(rightImgData);
			logoImgRight.setWidth(70f);
			logoImgRight.setHeight(71f);
			r1Cell3.add(logoImgRight.setHorizontalAlignment(HorizontalAlignment.RIGHT));
			r1Cell3.setHorizontalAlignment(HorizontalAlignment.RIGHT);
			r1Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			r1Cell3.setBorder(Border.NO_BORDER);
			table.addCell(r1Cell3.setPadding(PADDING));
			
			Cell r1Cell3a = new Cell(1,4);
			r1Cell3a.setHorizontalAlignment(HorizontalAlignment.CENTER);
			r1Cell3a.setBorder(Border.NO_BORDER);
			r1Cell3a.add(ls);
			table.addCell(r1Cell3a.setPadding(PADDING));
			
			Cell keyValPara1Cell1 = new Cell(1,4);
			keyValPara1Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara1Cell1.setBorder(Border.NO_BORDER);
			ImageData para1tableCell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificatepara1"),MANGAL, TEXT_SIZE_10, false));
			Image para1tableCell1Img = new Image(para1tableCell1Data);
			para1tableCell1Img.setHorizontalAlignment(HorizontalAlignment.CENTER);
			//para1tableCell1Img.setTextAlignment(TextAlignment.LEFT);
			para1tableCell1Img.setTextAlignment(TextAlignment.CENTER);
			// cell4tableCell1Img.setAutoScale(true);
			keyValPara1Cell1.add(para1tableCell1Img);
			table.addCell(keyValPara1Cell1);

			Cell keyValPara2Cell1 = new Cell(1,4);
			keyValPara2Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara2Cell1.setBorder(Border.NO_BORDER);
			Paragraph para2 = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificatepara2"));
			para2.setHorizontalAlignment(HorizontalAlignment.LEFT);
			para2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			para2.setTextAlignment(TextAlignment.CENTER);
			para2.setFontSize(TEXT_SIZE_10);
			// cell5tableCell1Img.setAutoScale(true);
			keyValPara2Cell1.add(para2);
			table.addCell(keyValPara2Cell1);
			
			Cell keyValPara3Cell1 = new Cell(1,4);
			keyValPara3Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara3Cell1.setBorder(Border.NO_BORDER);
			ImageData para3tableCell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificatepara3"),MANGAL, TEXT_SIZE_9, false));
			Image para3tableCell1Img = new Image(para3tableCell1Data);
			para3tableCell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			para3tableCell1Img.setTextAlignment(TextAlignment.LEFT);
			para3tableCell1Img.setMarginLeft(20f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValPara3Cell1.add(para3tableCell1Img);
			table.addCell(keyValPara3Cell1);
			
			Cell keyValPara3bCell1 = new Cell(1,4);
			keyValPara3bCell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara3bCell1.setBorder(Border.NO_BORDER);
			ImageData para3btableCell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificatepara4"),MANGAL, TEXT_SIZE_9, false));
			Image para3btableCell1Img = new Image(para3btableCell1Data);
			para3btableCell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			para3btableCell1Img.setTextAlignment(TextAlignment.LEFT);
			para3btableCell1Img.setMarginLeft(8f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValPara3bCell1.add(para3btableCell1Img);
			table.addCell(keyValPara3bCell1);
			
			Cell keyValPara4Cell1 = new Cell(1,4);
			keyValPara4Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara4Cell1.setBorder(Border.NO_BORDER);
			Paragraph para4 = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificatepara5"));
			para4.setHorizontalAlignment(HorizontalAlignment.LEFT);
			para4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			para4.setMarginLeft(8f);
			para4.setFirstLineIndent(20f);
			para4.setFontSize(TEXT_SIZE_9);
			// cell5tableCell1Img.setAutoScale(true);
			keyValPara4Cell1.add(para4);
			table.addCell(keyValPara4Cell1);

			Cell keyValPara5Cell1 = new Cell(1,4);
			keyValPara5Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara5Cell1.setBorder(Border.NO_BORDER);
			ImageData para5tableCell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificatepara6"),MANGAL, TEXT_SIZE_9, false));
			Image para5tableCell1Img = new Image(para5tableCell1Data);
			para5tableCell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			para5tableCell1Img.setTextAlignment(TextAlignment.LEFT);
			para5tableCell1Img.setMarginLeft(20f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValPara5Cell1.add(para5tableCell1Img);
			table.addCell(keyValPara5Cell1);
			
			Cell keyValPara5bbCell1 = new Cell(1,4);
			keyValPara5bbCell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara5bbCell1.setBorder(Border.NO_BORDER);
			ImageData para5btableCell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificatepara7"),MANGAL, TEXT_SIZE_9, false));
			Image para5btableCell1Img = new Image(para5btableCell1Data);
			para5btableCell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			para5btableCell1Img.setTextAlignment(TextAlignment.LEFT);
			para5btableCell1Img.setMarginLeft(8f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValPara5bbCell1.add(para5btableCell1Img);
			table.addCell(keyValPara5bbCell1);
			
			Cell keyValPara6Cell1 = new Cell(1,4);
			keyValPara6Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara6Cell1.setBorder(Border.NO_BORDER);
			Paragraph para6 = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificatepara8"));
			para6.setHorizontalAlignment(HorizontalAlignment.LEFT);
			para6.setVerticalAlignment(VerticalAlignment.MIDDLE);
			para6.setMarginLeft(8f);
			para6.setFirstLineIndent(20f);
			para6.setFontSize(TEXT_SIZE_9);
			// cell5tableCell1Img.setAutoScale(true);
			keyValPara6Cell1.add(para6);
			table.addCell(keyValPara6Cell1);
			
			Cell keyValRow1Cell1 = new Cell();
			keyValRow1Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow1Cell1.setBorder(Border.NO_BORDER);
			ImageData cell4tableCell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificate.childNameMar"),MANGAL, TEXT_SIZE_8, false));
			Image cell4tableCell1Img = new Image(cell4tableCell1Data);
			cell4tableCell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell4tableCell1Img.setTextAlignment(TextAlignment.LEFT);
			cell4tableCell1Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow1Cell1.add(cell4tableCell1Img);
			table.addCell(keyValRow1Cell1);

			Cell keyValRow1Cell2 = new Cell();
			keyValRow1Cell2.setBorder(Border.NO_BORDER);
			keyValRow1Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData cell4tableCell2Data = ImageDataFactory.create(Utility.textToImage(birthRgDetail.getBrChildNameMar(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell4tableCell2Img = new Image(cell4tableCell2Data);
			
			
			float imageWidthRow1Cell2=cell4tableCell2Img.getImageWidth();
			
			if (imageWidthRow1Cell2 > cellWidth) {
				String addressRow1Cell2 = birthRgDetail.getBrChildNameMar();
				int sizeRow1Cell2 = Math.round(addressRow1Cell2.length() / 2f);
				String address1Row1Cell2 = addressRow1Cell2.substring(0, sizeRow1Cell2);
				address1Row1Cell2 = StringUtils.substringBeforeLast(address1Row1Cell2, " ");
				String address2Row1Cell2 = StringUtils.substringAfterLast(addressRow1Cell2, address1Row1Cell2);

				ImageData address1ImgDataRow1Cell2 = ImageDataFactory.create(Utility.textToImage(address1Row1Cell2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address1ImgRow1Cell2 = new Image(address1ImgDataRow1Cell2);

				ImageData address2ImgDataRow1Cell2 = ImageDataFactory.create(Utility.textToImage(address2Row1Cell2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address2ImgRow1Cell2 = new Image(address2ImgDataRow1Cell2);

				address1ImgRow1Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
				address1ImgRow1Cell2.setTextAlignment(TextAlignment.LEFT);
				keyValRow1Cell2.add(address1ImgRow1Cell2);
				keyValRow1Cell2.add(new Paragraph(""));
				address2ImgRow1Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
				address2ImgRow1Cell2.setTextAlignment(TextAlignment.LEFT);
				keyValRow1Cell2.add(address2ImgRow1Cell2);
				
			} else {
				cell4tableCell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				cell4tableCell2Img.setTextAlignment(TextAlignment.LEFT);
				keyValRow1Cell2.add(cell4tableCell2Img);
			}
			
			table.addCell(keyValRow1Cell2);

			Cell keyValRow1Cell3 = new Cell();
			keyValRow1Cell3.setBorder(Border.NO_BORDER);
			keyValRow1Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData cell4tableCell3Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificate.sexMar"),MANGAL, TEXT_SIZE_8, false));
			Image cell4tableCell3Img = new Image(cell4tableCell3Data);
			cell4tableCell3Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell4tableCell3Img.setTextAlignment(TextAlignment.LEFT);
			// cell4tableCell3Img.setAutoScale(true);
			keyValRow1Cell3.add(cell4tableCell3Img);
			table.addCell(keyValRow1Cell3);

			Cell keyValRow1Cell4 = new Cell();
			keyValRow1Cell4.setBorder(Border.NO_BORDER);
			keyValRow1Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData cell4tableCell4Data = ImageDataFactory.create(Utility.textToImage(birthRgDetail.getBrSexMar(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell4tableCell4Img = new Image(cell4tableCell4Data);
			cell4tableCell4Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell4tableCell4Img.setTextAlignment(TextAlignment.LEFT);
			// cell4tableCell4Img.setAutoScale(true);
			keyValRow1Cell4.add(cell4tableCell4Img);
			table.addCell(keyValRow1Cell4);

			Cell keyValRow2Cell1 = new Cell();
			keyValRow2Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow2Cell1.setBorder(Border.NO_BORDER);
			Paragraph childNameEng = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificate.childName"));
			childNameEng.setHorizontalAlignment(HorizontalAlignment.LEFT);
			childNameEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			childNameEng.setFontSize(TEXT_SIZE_8);
			childNameEng.setMarginLeft(5f);
			// cell5tableCell1Img.setAutoScale(true);
			keyValRow2Cell1.add(childNameEng);
			table.addCell(keyValRow2Cell1);

			Cell keyValRow2Cell2 = new Cell();
			keyValRow2Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow2Cell2.setBorder(Border.NO_BORDER);
			ImageData cell5tableCell2Data = ImageDataFactory.create(Utility.textToImage(birthRgDetail.getBrChildName(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell5tableCell2Img = new Image(cell5tableCell2Data);
			
            float imageWidthRow2Cell2=cell5tableCell2Img.getImageWidth();
			
			if (imageWidthRow2Cell2 > cellWidth) {
				String addressRow2Cell2 = birthRgDetail.getBrChildName();
				int sizeRow2Cell2 = Math.round(addressRow2Cell2.length() / 2f);
				String address1Row2Cell2 = addressRow2Cell2.substring(0, sizeRow2Cell2);
				address1Row2Cell2 = StringUtils.substringBeforeLast(address1Row2Cell2, " ");
				String address2Row2Cell2 = StringUtils.substringAfterLast(addressRow2Cell2, address1Row2Cell2);

				ImageData address1ImgDataRow2Cell2 = ImageDataFactory.create(Utility.textToImage(address1Row2Cell2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address1ImgRow2Cell2 = new Image(address1ImgDataRow2Cell2);

				ImageData address2ImgDataRow2Cell2 = ImageDataFactory.create(Utility.textToImage(address2Row2Cell2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address2ImgRow2Cell2 = new Image(address2ImgDataRow2Cell2);

				address1ImgRow2Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
				address1ImgRow2Cell2.setTextAlignment(TextAlignment.LEFT);
				keyValRow2Cell2.add(address1ImgRow2Cell2);
				keyValRow2Cell2.add(new Paragraph(""));
				address2ImgRow2Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
				address2ImgRow2Cell2.setTextAlignment(TextAlignment.LEFT);
				keyValRow2Cell2.add(address2ImgRow2Cell2);
				
			} else {
				cell5tableCell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				cell5tableCell2Img.setTextAlignment(TextAlignment.LEFT);
				keyValRow2Cell2.add(cell5tableCell2Img);
			}
			table.addCell(keyValRow2Cell2);

			Cell keyValRow2Cell3 = new Cell();
			keyValRow2Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow2Cell3.setBorder(Border.NO_BORDER);
			Paragraph genderNameEng = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificate.sex"));
			genderNameEng.setHorizontalAlignment(HorizontalAlignment.LEFT);
			genderNameEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			genderNameEng.setFontSize(TEXT_SIZE_8);
			// cell5tableCell3Img.setAutoScale(true);
			keyValRow2Cell3.add(genderNameEng);
			table.addCell(keyValRow2Cell3);

			Cell keyValRow2Cell4 = new Cell();
			keyValRow2Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow2Cell4.setBorder(Border.NO_BORDER);
			Paragraph genderNameData = new Paragraph(birthRgDetail.getBrSex());
			genderNameData.setHorizontalAlignment(HorizontalAlignment.LEFT);
			genderNameData.setVerticalAlignment(VerticalAlignment.MIDDLE);
			genderNameData.setFontSize(TEXT_SIZE_8);
			// cell5tableCell4Img.setAutoScale(true);
			keyValRow2Cell4.add(genderNameData);
			table.addCell(keyValRow2Cell4);
			
			Cell keyValRow11Cell1 = new Cell();
			keyValRow11Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow11Cell1.setBorder(Border.NO_BORDER);
			ImageData dateOfBirthMarK = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificate.dobMar"),MANGAL, TEXT_SIZE_8, false));
			Image dateOfBirthMarKImg = new Image(dateOfBirthMarK);
			dateOfBirthMarKImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			dateOfBirthMarKImg.setTextAlignment(TextAlignment.LEFT);
			dateOfBirthMarKImg.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow11Cell1.add(dateOfBirthMarKImg);
			table.addCell(keyValRow11Cell1);

			Cell keyValRow11Cell2 = new Cell();
			keyValRow11Cell2.setBorder(Border.NO_BORDER);
			keyValRow11Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData placeMarV = ImageDataFactory.create(Utility.textToImage(Utility.convertToRegional("marathi", birthRgDetail.getAppDateOfBirth()), ARIALUNICODE, TEXT_SIZE_8, false));
			Image placeMarVImg = new Image(placeMarV);
			placeMarVImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			placeMarVImg.setTextAlignment(TextAlignment.LEFT);
			// cell4tableCell2Img.setAutoScale(true);
			keyValRow11Cell2.add(placeMarVImg);
			table.addCell(keyValRow11Cell2);

			Cell keyValRow11Cell3 = new Cell();
			keyValRow11Cell3.setBorder(Border.NO_BORDER);
			keyValRow11Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData dateOfBirthMar = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificate.birthPlaceMar"),MANGAL, TEXT_SIZE_8, false));
			Image dateOfBirthMarImg = new Image(dateOfBirthMar);
			dateOfBirthMarImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			dateOfBirthMarImg.setTextAlignment(TextAlignment.LEFT);
			// cell4tableCell3Img.setAutoScale(true);
			keyValRow11Cell3.add(dateOfBirthMarImg);
			table.addCell(keyValRow11Cell3);

			Cell keyValRow11Cell4 = new Cell();
			keyValRow11Cell4.setBorder(Border.NO_BORDER);
			keyValRow11Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData placeValue = ImageDataFactory.create(Utility.textToImage(birthRgDetail.getBrBirthPlaceMar(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image placeValueImg = new Image(placeValue);
			
			 float imageWidthRow11Cell4=placeValueImg.getImageWidth();
				
				if (imageWidthRow11Cell4 > cellWidth) {
					String addressRow11Cell4 = birthRgDetail.getBrBirthPlaceMar();
					int sizeRow11Cell4 = Math.round(addressRow11Cell4.length() / 2f);
					String address1Row11Cell4 = addressRow11Cell4.substring(0, sizeRow11Cell4);
					address1Row11Cell4 = StringUtils.substringBeforeLast(address1Row11Cell4, " ");
					String address2Row11Cell4 = StringUtils.substringAfterLast(addressRow11Cell4, address1Row11Cell4);

					ImageData address1ImgDataRow11Cell4 = ImageDataFactory.create(Utility.textToImage(address1Row11Cell4, ARIALUNICODE, TEXT_SIZE_8, false));
					Image address1ImgRow11Cell4 = new Image(address1ImgDataRow11Cell4);

					ImageData address2ImgDataRow11Cell4 = ImageDataFactory.create(Utility.textToImage(address2Row11Cell4, ARIALUNICODE, TEXT_SIZE_8, false));
					Image address2ImgRow11Cell4 = new Image(address2ImgDataRow11Cell4);

					if (address1ImgRow11Cell4.getImageWidth() > cellWidth) {
						String[] strRow11Cell4 = new String[2];
						strRow11Cell4 = divideCell(address1Row11Cell4);

						ImageData address1aImgDataRow11Cell4= ImageDataFactory.create(Utility.textToImage(strRow11Cell4[0], ARIALUNICODE, TEXT_SIZE_8, false));
						Image address1aImgRow11Cell4 = new Image(address1aImgDataRow11Cell4);

						ImageData address2aImgDataRow11Cell4 = ImageDataFactory.create(Utility.textToImage(strRow11Cell4[1], ARIALUNICODE, TEXT_SIZE_8, false));
						Image address2aImgRow11Cell4 = new Image(address2aImgDataRow11Cell4);

						keyValRow11Cell4.add(address1aImgRow11Cell4);
						keyValRow11Cell4.add(new Paragraph(" "));
						keyValRow11Cell4.add(address2aImgRow11Cell4);
					} else {
						address1ImgRow11Cell4.setHorizontalAlignment(HorizontalAlignment.LEFT);
						address1ImgRow11Cell4.setTextAlignment(TextAlignment.LEFT);
						keyValRow11Cell4.add(address1ImgRow11Cell4);
						keyValRow11Cell4.add(new Paragraph(""));
					}

					if (address2ImgRow11Cell4.getImageWidth() > cellWidth) {
						String[] strRow11Cell4 = new String[2];
						strRow11Cell4= divideCell(address2Row11Cell4);

						ImageData address1bImgDataRow11Cell4 = ImageDataFactory.create(Utility.textToImage(strRow11Cell4[0], ARIALUNICODE, TEXT_SIZE_8, false));
						Image address1bImgRow11Cell4 = new Image(address1bImgDataRow11Cell4);

						ImageData address2bImgDataRow11Cell4 = ImageDataFactory.create(Utility.textToImage(strRow11Cell4[1], ARIALUNICODE, TEXT_SIZE_8, false));
						Image address2bImgRow11Cell4 = new Image(address2bImgDataRow11Cell4);

						keyValRow11Cell4.add(address1bImgRow11Cell4);
						keyValRow11Cell4.add(new Paragraph(" "));
						keyValRow11Cell4.add(address2bImgRow11Cell4);
					} else {
						address2ImgRow11Cell4.setHorizontalAlignment(HorizontalAlignment.LEFT);
						address2ImgRow11Cell4.setTextAlignment(TextAlignment.LEFT);
						keyValRow11Cell4.add(address2ImgRow11Cell4);
					}
				} else {
					placeValueImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
					placeValueImg.setTextAlignment(TextAlignment.LEFT);
					keyValRow11Cell4.add(placeValueImg);
				}
			table.addCell(keyValRow11Cell4);
			
			Cell keyValRow12Cell1 = new Cell();
			keyValRow12Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow12Cell1.setBorder(Border.NO_BORDER);
			Paragraph dateOfBirth = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificate.dob"));
			dateOfBirth.setHorizontalAlignment(HorizontalAlignment.LEFT);
			dateOfBirth.setVerticalAlignment(VerticalAlignment.MIDDLE);
			dateOfBirth.setFontSize(TEXT_SIZE_8);
			dateOfBirth.setMarginLeft(5f);
			// cell5tableCell1Img.setAutoScale(true);
			keyValRow12Cell1.add(dateOfBirth);
			table.addCell(keyValRow12Cell1);

			Cell keyValRow12Cell2 = new Cell();
			keyValRow12Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow12Cell2.setBorder(Border.NO_BORDER);
			ImageData dateOfBirthKey = ImageDataFactory.create(Utility.textToImage(birthRgDetail.getAppDateOfBirth(), ARIALUNICODE, TEXT_SIZE_9, false));
			Image cell12tableCell4Img = new Image(dateOfBirthKey);
			cell12tableCell4Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell12tableCell4Img.setTextAlignment(TextAlignment.LEFT);
			// cell5tableCell2Img.setAutoScale(true);
			keyValRow12Cell2.add(cell12tableCell4Img);
			table.addCell(keyValRow12Cell2);

			Cell keyValRow12Cell3 = new Cell();
			keyValRow12Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow12Cell3.setBorder(Border.NO_BORDER);
			Paragraph placeKey = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificate.birthPlace"));
			placeKey.setHorizontalAlignment(HorizontalAlignment.LEFT);
			placeKey.setVerticalAlignment(VerticalAlignment.MIDDLE);
			placeKey.setFontSize(TEXT_SIZE_8);
			// cell5tableCell3Img.setAutoScale(true);
			keyValRow12Cell3.add(placeKey);
			table.addCell(keyValRow12Cell3);

			Cell keyValRow12Cell4 = new Cell();
			keyValRow12Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow12Cell4.setBorder(Border.NO_BORDER);
			ImageData placeValueV = ImageDataFactory.create(Utility.textToImage(birthRgDetail.getBrBirthPlace(),ARIALUNICODE, TEXT_SIZE_8, false));
			Image placeValueVImg = new Image(placeValueV);
			
			float imageWidthRow12Cell4=placeValueVImg.getImageWidth();
			
			if (imageWidthRow12Cell4 > cellWidth) {
				String addressRow12Cell4 = birthRgDetail.getBrBirthPlace();
				int sizeRow12Cell4 = Math.round(addressRow12Cell4.length() / 2f);
				String address1Row12Cell4 = addressRow12Cell4.substring(0, sizeRow12Cell4);
				address1Row12Cell4 = StringUtils.substringBeforeLast(address1Row12Cell4, " ");
				String address2Row12Cell4 = StringUtils.substringAfterLast(addressRow12Cell4, address1Row12Cell4);

				ImageData address1ImgDataRow12Cell4 = ImageDataFactory.create(Utility.textToImage(address1Row12Cell4, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address1ImgRow12Cell4 = new Image(address1ImgDataRow12Cell4);

				ImageData address2ImgDataRow12Cell4 = ImageDataFactory.create(Utility.textToImage(address2Row12Cell4, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address2ImgRow12Cell4 = new Image(address2ImgDataRow12Cell4);

				if (address1ImgRow12Cell4.getImageWidth() > cellWidth) {
					String[] strRow12Cell4 = new String[2];
					strRow12Cell4 = divideCell(address1Row12Cell4);

					ImageData address1aImgDataRow12Cell4= ImageDataFactory.create(Utility.textToImage(strRow12Cell4[0], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address1aImgRow12Cell4 = new Image(address1aImgDataRow12Cell4);

					ImageData address2aImgDataRow12Cell4 = ImageDataFactory.create(Utility.textToImage(strRow12Cell4[1], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address2aImgRow12Cell4 = new Image(address2aImgDataRow12Cell4);

					keyValRow12Cell4.add(address1aImgRow12Cell4);
					keyValRow12Cell4.add(new Paragraph(" "));
					keyValRow12Cell4.add(address2aImgRow12Cell4);
				} else {
					address1ImgRow12Cell4.setHorizontalAlignment(HorizontalAlignment.LEFT);
					address1ImgRow12Cell4.setTextAlignment(TextAlignment.LEFT);
					keyValRow12Cell4.add(address1ImgRow12Cell4);
					keyValRow12Cell4.add(new Paragraph(""));
				}

				if (address2ImgRow12Cell4.getImageWidth() > cellWidth) {
					String[] strRow12Cell4 = new String[2];
					strRow12Cell4= divideCell(address2Row12Cell4);

					ImageData address1bImgDataRow12Cell4 = ImageDataFactory.create(Utility.textToImage(strRow12Cell4[0], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address1bImgRow12Cell4 = new Image(address1bImgDataRow12Cell4);

					ImageData address2bImgDataRow12Cell4 = ImageDataFactory.create(Utility.textToImage(strRow12Cell4[1], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address2bImgRow12Cell4 = new Image(address2bImgDataRow12Cell4);

					keyValRow12Cell4.add(address1bImgRow12Cell4);
					keyValRow12Cell4.add(new Paragraph(" "));
					keyValRow12Cell4.add(address2bImgRow12Cell4);
				} else {
					address2ImgRow12Cell4.setHorizontalAlignment(HorizontalAlignment.LEFT);
					address2ImgRow12Cell4.setTextAlignment(TextAlignment.LEFT);
					keyValRow12Cell4.add(address2ImgRow12Cell4);
				}
			} else {
				placeValueVImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
				placeValueVImg.setTextAlignment(TextAlignment.LEFT);
				keyValRow12Cell4.add(placeValueVImg);
			}
			table.addCell(keyValRow12Cell4);
			
			Cell keyValRow3Cell1 = new Cell();
			keyValRow3Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow3Cell1.setBorder(Border.NO_BORDER);
			ImageData cell2tableCell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificate.mothernameMar"),MANGAL, TEXT_SIZE_8, false));
			Image cell2tableCell1Img = new Image(cell2tableCell1Data);
			cell2tableCell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell2tableCell1Img.setTextAlignment(TextAlignment.LEFT);
			cell2tableCell1Img.setMarginLeft(5f);
			// cell2tableCell1Img.setAutoScale(true);
			keyValRow3Cell1.add(cell2tableCell1Img);
			table.addCell(keyValRow3Cell1);

			Cell keyValRow3Cell2 = new Cell();
			keyValRow3Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow3Cell2.setBorder(Border.NO_BORDER);
			ImageData cell2tableCell2Data = ImageDataFactory.create(Utility.textToImage(birthRgDetail.getParentDetailDTO().getPdMothernameMar(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell2tableCell2Img = new Image(cell2tableCell2Data);
			
           float imageWidthRow3Cell2=cell2tableCell2Img.getImageWidth();
			
			if (imageWidthRow3Cell2 > cellWidth) {
				String addressRow3Cell2 = birthRgDetail.getParentDetailDTO().getPdMothernameMar();
				int sizeRow3Cell2 = Math.round(addressRow3Cell2.length() / 2f);
				String address1Row3Cell2 = addressRow3Cell2.substring(0, sizeRow3Cell2);
				address1Row3Cell2 = StringUtils.substringBeforeLast(address1Row3Cell2, " ");
				String address2Row3Cell2 = StringUtils.substringAfterLast(addressRow3Cell2, address1Row3Cell2);

				ImageData address1ImgDataRow3Cell2 = ImageDataFactory.create(Utility.textToImage(address1Row3Cell2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address1ImgRow3Cell2 = new Image(address1ImgDataRow3Cell2);

				ImageData address2ImgDataRow3Cell2 = ImageDataFactory.create(Utility.textToImage(address2Row3Cell2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address2ImgRow3Cell2 = new Image(address2ImgDataRow3Cell2);

				address1ImgRow3Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
				address1ImgRow3Cell2.setTextAlignment(TextAlignment.LEFT);
				keyValRow3Cell2.add(address1ImgRow3Cell2);
				keyValRow3Cell2.add(new Paragraph(""));
				address2ImgRow3Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
				address2ImgRow3Cell2.setTextAlignment(TextAlignment.LEFT);
				keyValRow3Cell2.add(address2ImgRow3Cell2);
				
			} else {
				cell2tableCell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				cell2tableCell2Img.setTextAlignment(TextAlignment.LEFT);
				keyValRow3Cell2.add(cell2tableCell2Img);
			}
			
			table.addCell(keyValRow3Cell2);

			Cell keyValRow3Cell3 = new Cell();
			keyValRow3Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow3Cell3.setBorder(Border.NO_BORDER);
			ImageData cell2tableCell3Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificate.fathernameMar"),MANGAL, TEXT_SIZE_8, false));
			Image cell2tableCell3Img = new Image(cell2tableCell3Data);
			cell2tableCell3Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell2tableCell3Img.setTextAlignment(TextAlignment.LEFT);
			// cell2tableCell3Img.setAutoScale(true);
			keyValRow3Cell3.add(cell2tableCell3Img);
			table.addCell(keyValRow3Cell3);

			Cell keyValRow3Cell4 = new Cell();
			keyValRow3Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow3Cell4.setBorder(Border.NO_BORDER);
			ImageData cell2tableCell4Data = ImageDataFactory.create(Utility.textToImage(birthRgDetail.getParentDetailDTO().getPdFathernameMar(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell2tableCell4Img = new Image(cell2tableCell4Data);
			
            float imageWidthRow3Cell4=cell2tableCell4Img.getImageWidth();
			
			if (imageWidthRow3Cell4 > cellWidth) {
				String addressRow3Cell4 = birthRgDetail.getParentDetailDTO().getPdFathernameMar();
				int sizeRow3Cell4 = Math.round(addressRow3Cell4.length() / 2f);
				String address1Row3Cell4 = addressRow3Cell4.substring(0, sizeRow3Cell4);
				address1Row3Cell4 = StringUtils.substringBeforeLast(address1Row3Cell4, " ");
				String address2Row3Cell4 = StringUtils.substringAfterLast(addressRow3Cell4, address1Row3Cell4);

				ImageData address1ImgDataRow3Cell4 = ImageDataFactory.create(Utility.textToImage(address1Row3Cell4, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address1ImgRow3Cell4 = new Image(address1ImgDataRow3Cell4);

				ImageData address2ImgDataRow3Cell4 = ImageDataFactory.create(Utility.textToImage(address2Row3Cell4, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address2ImgRow3Cell4 = new Image(address2ImgDataRow3Cell4);

				address1ImgRow3Cell4.setHorizontalAlignment(HorizontalAlignment.LEFT);
				address1ImgRow3Cell4.setTextAlignment(TextAlignment.LEFT);
				keyValRow3Cell4.add(address1ImgRow3Cell4);
				keyValRow3Cell4.add(new Paragraph(""));
				address2ImgRow3Cell4.setHorizontalAlignment(HorizontalAlignment.LEFT);
				address2ImgRow3Cell4.setTextAlignment(TextAlignment.LEFT);
				keyValRow3Cell4.add(address2ImgRow3Cell4);
				
			} else {
				cell2tableCell4Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				cell2tableCell4Img.setTextAlignment(TextAlignment.LEFT);
				keyValRow3Cell4.add(cell2tableCell4Img);
			}
			table.addCell(keyValRow3Cell4);

			Cell keyValRow4Cell1 = new Cell();
			keyValRow4Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow4Cell1.setBorder(Border.NO_BORDER);
			Paragraph motherNameEng = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificate.mothername"));
			motherNameEng.setHorizontalAlignment(HorizontalAlignment.LEFT);
			motherNameEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			motherNameEng.setFontSize(TEXT_SIZE_8);
			motherNameEng.setMarginLeft(5f);
			// cell3tableCell1Img.setAutoScale(true);
			keyValRow4Cell1.add(motherNameEng);
			table.addCell(keyValRow4Cell1);

			Cell keyValRow4Cell2 = new Cell();
			keyValRow4Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow4Cell2.setBorder(Border.NO_BORDER);
			ImageData cell3tableCell2Data = ImageDataFactory.create(Utility.textToImage(birthRgDetail.getParentDetailDTO().getPdMothername(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell3tableCell2Img = new Image(cell3tableCell2Data);
			
           float imageWidthRow4Cell2=cell3tableCell2Img.getImageWidth();
			
			if (imageWidthRow4Cell2 > cellWidth) {
				String addressRow4Cell2 = birthRgDetail.getParentDetailDTO().getPdMothername();
				int sizeRow4Cell2 = Math.round(addressRow4Cell2.length() / 2f);
				String address1Row4Cell2 = addressRow4Cell2.substring(0, sizeRow4Cell2);
				address1Row4Cell2 = StringUtils.substringBeforeLast(address1Row4Cell2, " ");
				String address2Row4Cell2 = StringUtils.substringAfterLast(addressRow4Cell2, address1Row4Cell2);

				ImageData address1ImgDataRow4Cell2 = ImageDataFactory.create(Utility.textToImage(address1Row4Cell2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address1ImgRow4Cell2 = new Image(address1ImgDataRow4Cell2);

				ImageData address2ImgDataRow4Cell2 = ImageDataFactory.create(Utility.textToImage(address2Row4Cell2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address2ImgRow4Cell2 = new Image(address2ImgDataRow4Cell2);

				address1ImgRow4Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
				address1ImgRow4Cell2.setTextAlignment(TextAlignment.LEFT);
				keyValRow4Cell2.add(address1ImgRow4Cell2);
				keyValRow4Cell2.add(new Paragraph(""));
				address2ImgRow4Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
				address2ImgRow4Cell2.setTextAlignment(TextAlignment.LEFT);
				keyValRow4Cell2.add(address2ImgRow4Cell2);
				
			} else {
				cell3tableCell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				cell3tableCell2Img.setTextAlignment(TextAlignment.LEFT);
				keyValRow4Cell2.add(cell3tableCell2Img);
			}
			table.addCell(keyValRow4Cell2);

			Cell keyValRow4Cell3 = new Cell();
			keyValRow4Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow4Cell3.setBorder(Border.NO_BORDER);
			Paragraph fatherNameEng = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificate.fathername"));
			fatherNameEng.setHorizontalAlignment(HorizontalAlignment.LEFT);
			fatherNameEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			fatherNameEng.setFontSize(TEXT_SIZE_8);
			keyValRow4Cell3.add(fatherNameEng);
			table.addCell(keyValRow4Cell3);

			Cell keyValRow4Cell4 = new Cell();
			keyValRow4Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow4Cell4.setBorder(Border.NO_BORDER);
			ImageData cell3tableCell4Data = ImageDataFactory.create(Utility.textToImage(birthRgDetail.getParentDetailDTO().getPdFathername(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell3tableCell4Img = new Image(cell3tableCell4Data);
			
          float imageWidthRow4Cell4=cell3tableCell4Img.getImageWidth();
			
			if (imageWidthRow4Cell4 > cellWidth) {
				String addressRow4Cell4 = birthRgDetail.getParentDetailDTO().getPdFathername();
				int sizeRow4Cell4 = Math.round(addressRow4Cell4.length() / 2f);
				String address1Row4Cell4 = addressRow4Cell4.substring(0, sizeRow4Cell4);
				address1Row4Cell4 = StringUtils.substringBeforeLast(address1Row4Cell4, " ");
				String address2Row4Cell4 = StringUtils.substringAfterLast(addressRow4Cell4, address1Row4Cell4);

				ImageData address1ImgDataRow4Cell4 = ImageDataFactory.create(Utility.textToImage(address1Row4Cell4, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address1ImgRow4Cell4 = new Image(address1ImgDataRow4Cell4);

				ImageData address2ImgDataRow4Cell4 = ImageDataFactory.create(Utility.textToImage(address2Row4Cell4, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address2ImgRow4Cell4 = new Image(address2ImgDataRow4Cell4);

				address1ImgRow4Cell4.setHorizontalAlignment(HorizontalAlignment.LEFT);
				address1ImgRow4Cell4.setTextAlignment(TextAlignment.LEFT);
				keyValRow4Cell4.add(address1ImgRow4Cell4);
				keyValRow4Cell4.add(new Paragraph(""));
				address2ImgRow4Cell4.setHorizontalAlignment(HorizontalAlignment.LEFT);
				address2ImgRow4Cell4.setTextAlignment(TextAlignment.LEFT);
				keyValRow4Cell4.add(address2ImgRow4Cell4);
				
			} else {
				cell3tableCell4Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				cell3tableCell4Img.setTextAlignment(TextAlignment.LEFT);
				keyValRow4Cell4.add(cell3tableCell4Img);
			}
			table.addCell(keyValRow4Cell4);

			Cell keyValRow5Cell1 = new Cell();
			keyValRow5Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow5Cell1.setBorder(Border.NO_BORDER);
			ImageData cell6tableCell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificate.paraddressMar"),MANGAL, TEXT_SIZE_8, false));
			Image cell6tableCell1Img = new Image(cell6tableCell1Data);
			cell6tableCell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell6tableCell1Img.setTextAlignment(TextAlignment.LEFT);
			cell6tableCell1Img.setMarginLeft(5f);
			// cell6tableCell1Img.setAutoScale(true);
			keyValRow5Cell1.add(cell6tableCell1Img);
			table.addCell(keyValRow5Cell1);

			Cell keyValRow5Cell2 = new Cell();
			keyValRow5Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow5Cell2.setBorder(Border.NO_BORDER);
			ImageData cell6tableCell2Data = ImageDataFactory.create(Utility.textToImage(birthRgDetail.getBrBirthAddrMar(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell6tableCell2Img = new Image(cell6tableCell2Data);
			
	        float imageWidthRow5Cell2=cell6tableCell2Img.getImageWidth();
			
			if (imageWidthRow5Cell2 > cellWidth) {
				String addressRow5Cell2 = birthRgDetail.getBrBirthAddrMar();
				int sizeRow5Cell2 = Math.round(addressRow5Cell2.length() / 2f);
				String address1Row5Cell2 = addressRow5Cell2.substring(0, sizeRow5Cell2);
				address1Row5Cell2 = StringUtils.substringBeforeLast(address1Row5Cell2, " ");
				String address2Row5Cell2 = StringUtils.substringAfterLast(addressRow5Cell2, address1Row5Cell2);

				ImageData address1ImgDataRow5Cell2 = ImageDataFactory.create(Utility.textToImage(address1Row5Cell2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address1ImgRow5Cell2 = new Image(address1ImgDataRow5Cell2);

				ImageData address2ImgDataRow5Cell2 = ImageDataFactory.create(Utility.textToImage(address2Row5Cell2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address2ImgRow5Cell2 = new Image(address2ImgDataRow5Cell2);

				if (address1ImgRow5Cell2.getImageWidth() > cellWidth) {
					String[] strRow5Cell2 = new String[2];
					strRow5Cell2 = divideCell(address1Row5Cell2);

					ImageData address1aImgDataRow5Cell2 = ImageDataFactory.create(Utility.textToImage(strRow5Cell2[0], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address1aImgRow5Cell2 = new Image(address1aImgDataRow5Cell2);

					ImageData address2aImgDataRow5Cell2 = ImageDataFactory.create(Utility.textToImage(strRow5Cell2[1], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address2aImgRow5Cell2 = new Image(address2aImgDataRow5Cell2);

					keyValRow5Cell2.add(address1aImgRow5Cell2);
					keyValRow5Cell2.add(new Paragraph(" "));
					keyValRow5Cell2.add(address2aImgRow5Cell2);
				} else {
					address1ImgRow5Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
					address1ImgRow5Cell2.setTextAlignment(TextAlignment.LEFT);
					keyValRow5Cell2.add(address1ImgRow5Cell2);
					keyValRow5Cell2.add(new Paragraph(""));
				}

				if (address2ImgRow5Cell2.getImageWidth() > cellWidth) {
					String[] strRow5Cell2 = new String[2];
					strRow5Cell2 = divideCell(address2Row5Cell2);

					ImageData address1bImgDataRow5Cell2 = ImageDataFactory.create(Utility.textToImage(strRow5Cell2[0], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address1bImgRow5Cell2 = new Image(address1bImgDataRow5Cell2);

					ImageData address2bImgDataRow5Cell2 = ImageDataFactory.create(Utility.textToImage(strRow5Cell2[1], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address2bImgRow5Cell2 = new Image(address2bImgDataRow5Cell2);

					keyValRow5Cell2.add(address1bImgRow5Cell2);
					keyValRow5Cell2.add(new Paragraph(" "));
					keyValRow5Cell2.add(address2bImgRow5Cell2);
				} else {
					address2ImgRow5Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
					address2ImgRow5Cell2.setTextAlignment(TextAlignment.LEFT);
					keyValRow5Cell2.add(address2ImgRow5Cell2);
				}
			} else {
				cell6tableCell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				cell6tableCell2Img.setTextAlignment(TextAlignment.LEFT);
				keyValRow5Cell2.add(cell6tableCell2Img);
			}
			table.addCell(keyValRow5Cell2);

			Cell keyValRow5Cell3 = new Cell();
			keyValRow5Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow5Cell3.setBorder(Border.NO_BORDER);
			ImageData cell6tableCell3Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificate.addressMar"),MANGAL, TEXT_SIZE_8, false));
			Image cell6tableCell3Img = new Image(cell6tableCell3Data);
			cell6tableCell3Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell6tableCell3Img.setTextAlignment(TextAlignment.LEFT);
			// cell6tableCell3Img.setAutoScale(true);
			keyValRow5Cell3.add(cell6tableCell3Img);
			table.addCell(keyValRow5Cell3);

			Cell keyValRow5Cell4 = new Cell();
			keyValRow5Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow5Cell4.setBorder(Border.NO_BORDER);
			ImageData cell6tableCell4Data = ImageDataFactory.create(Utility.textToImage(birthRgDetail.getParentDetailDTO().getPdParaddressMar(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell6tableCell4Img = new Image(cell6tableCell4Data);
			
            float imageWidthRow5Cell4=cell6tableCell4Img.getImageWidth();
			
			if (imageWidthRow5Cell4 > cellWidth) {
				String addressRow5Cell4 = birthRgDetail.getParentDetailDTO().getPdParaddressMar();
				int sizeRow5Cell4 = Math.round(addressRow5Cell4.length() / 2f);
				String address1Row5Cell4 = addressRow5Cell4.substring(0, sizeRow5Cell4);
				address1Row5Cell4 = StringUtils.substringBeforeLast(address1Row5Cell4, " ");
				String address2Row5Cell4 = StringUtils.substringAfterLast(addressRow5Cell4, address1Row5Cell4);

				ImageData address1ImgDataRow5Cell4 = ImageDataFactory.create(Utility.textToImage(address1Row5Cell4, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address1ImgRow5Cell4 = new Image(address1ImgDataRow5Cell4);

				ImageData address2ImgDataRow5Cell4 = ImageDataFactory.create(Utility.textToImage(address2Row5Cell4, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address2ImgRow5Cell4 = new Image(address2ImgDataRow5Cell4);

				if (address1ImgRow5Cell4.getImageWidth() > cellWidth) {
					String[] strRow5Cell4 = new String[2];
					strRow5Cell4 = divideCell(address1Row5Cell4);

					ImageData address1aImgDataRow5Cell4 = ImageDataFactory.create(Utility.textToImage(strRow5Cell4[0], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address1aImgRow5Cell4 = new Image(address1aImgDataRow5Cell4);

					ImageData address2aImgDataRow5Cell4 = ImageDataFactory.create(Utility.textToImage(strRow5Cell4[1], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address2aImgRow5Cell4 = new Image(address2aImgDataRow5Cell4);

					keyValRow5Cell4.add(address1aImgRow5Cell4);
					keyValRow5Cell4.add(new Paragraph(" "));
					keyValRow5Cell4.add(address2aImgRow5Cell4);
				} else {
					address1ImgRow5Cell4.setHorizontalAlignment(HorizontalAlignment.LEFT);
					address1ImgRow5Cell4.setTextAlignment(TextAlignment.LEFT);
					keyValRow5Cell4.add(address1ImgRow5Cell4);
					keyValRow5Cell4.add(new Paragraph(""));
				}

				if (address2ImgRow5Cell4.getImageWidth() > cellWidth) {
					String[] strRow5Cell4 = new String[2];
					strRow5Cell4 = divideCell(address2Row5Cell4);

					ImageData address1bImgDataRow5Cell4 = ImageDataFactory.create(Utility.textToImage(strRow5Cell4[0], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address1bImgRow5Cell4 = new Image(address1bImgDataRow5Cell4);

					ImageData address2bImgDataRow5Cell4 = ImageDataFactory.create(Utility.textToImage(strRow5Cell4[1], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address2bImgRow5Cell4 = new Image(address2bImgDataRow5Cell4);

					keyValRow5Cell4.add(address1bImgRow5Cell4);
					keyValRow5Cell4.add(new Paragraph(" "));
					keyValRow5Cell4.add(address2bImgRow5Cell4);
				} else {
					address2ImgRow5Cell4.setHorizontalAlignment(HorizontalAlignment.LEFT);
					address2ImgRow5Cell4.setTextAlignment(TextAlignment.LEFT);
					keyValRow5Cell4.add(address2ImgRow5Cell4);
				}
			} else {
				cell6tableCell4Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				cell6tableCell4Img.setTextAlignment(TextAlignment.LEFT);
				keyValRow5Cell4.add(cell6tableCell4Img);
			}
			table.addCell(keyValRow5Cell4);

			Cell keyValRow6Cell1 = new Cell();
			keyValRow6Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow6Cell1.setBorder(Border.NO_BORDER);
			Paragraph addressEng = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificate.paraddress"));
			addressEng.setHorizontalAlignment(HorizontalAlignment.LEFT);
			addressEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			addressEng.setFontSize(TEXT_SIZE_8);
			addressEng.setMarginLeft(5f);
			keyValRow6Cell1.add(addressEng);
			table.addCell(keyValRow6Cell1);

			Cell keyValRow6Cell2 = new Cell();
			keyValRow6Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow6Cell2.setBorder(Border.NO_BORDER);
			ImageData cell7tableCell2Data = ImageDataFactory.create(Utility.textToImage(birthRgDetail.getBrBirthAddr(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell7tableCell2Img = new Image(cell7tableCell2Data);
			
            float imageWidthRow6Cell2=cell7tableCell2Img.getImageWidth();
			
			if (imageWidthRow6Cell2 > cellWidth) {
				String addressRow6Cell2 = birthRgDetail.getBrBirthAddr();
				int sizeRow6Cell2 = Math.round(addressRow6Cell2.length() / 2f);
				String address1Row6Cell2 = addressRow6Cell2.substring(0, sizeRow6Cell2);
				address1Row6Cell2 = StringUtils.substringBeforeLast(address1Row6Cell2, " ");
				String address2Row6Cell2 = StringUtils.substringAfterLast(addressRow6Cell2, address1Row6Cell2);

				ImageData address1ImgDataRow6Cell2 = ImageDataFactory.create(Utility.textToImage(address1Row6Cell2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address1ImgRow6Cell2 = new Image(address1ImgDataRow6Cell2);

				ImageData address2ImgDataRow6Cell2 = ImageDataFactory.create(Utility.textToImage(address2Row6Cell2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address2ImgRow6Cell2 = new Image(address2ImgDataRow6Cell2);

				if (address1ImgRow6Cell2.getImageWidth() > cellWidth) {
					String[] strRow6Cell2 = new String[2];
					strRow6Cell2 = divideCell(address1Row6Cell2);

					ImageData address1aImgDataRow6Cell2 = ImageDataFactory.create(Utility.textToImage(strRow6Cell2[0], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address1aImgRow6Cell2 = new Image(address1aImgDataRow6Cell2);

					ImageData address2aImgDataRow6Cell2 = ImageDataFactory.create(Utility.textToImage(strRow6Cell2[1], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address2aImgRow6Cell2 = new Image(address2aImgDataRow6Cell2);

					keyValRow6Cell2.add(address1aImgRow6Cell2);
					keyValRow6Cell2.add(new Paragraph(" "));
					keyValRow6Cell2.add(address2aImgRow6Cell2);
				} else {
					address1ImgRow6Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
					address1ImgRow6Cell2.setTextAlignment(TextAlignment.LEFT);
					keyValRow6Cell2.add(address1ImgRow6Cell2);
					keyValRow6Cell2.add(new Paragraph(""));
				}

				if (address2ImgRow6Cell2.getImageWidth() > cellWidth) {
					String[] strRow6Cell2 = new String[2];
					strRow6Cell2 = divideCell(address2Row6Cell2);

					ImageData address1bImgDataRow6Cell2 = ImageDataFactory.create(Utility.textToImage(strRow6Cell2[0], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address1bImgRow6Cell2 = new Image(address1bImgDataRow6Cell2);

					ImageData address2bImgDataRow6Cell2 = ImageDataFactory.create(Utility.textToImage(strRow6Cell2[1], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address2bImgRow6Cell2 = new Image(address2bImgDataRow6Cell2);

					keyValRow6Cell2.add(address1bImgRow6Cell2);
					keyValRow6Cell2.add(new Paragraph(" "));
					keyValRow6Cell2.add(address2bImgRow6Cell2);
				} else {
					address2ImgRow6Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
					address2ImgRow6Cell2.setTextAlignment(TextAlignment.LEFT);
					keyValRow6Cell2.add(address2ImgRow6Cell2);
				}
			} else {
				cell7tableCell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				cell7tableCell2Img.setTextAlignment(TextAlignment.LEFT);
				keyValRow6Cell2.add(cell7tableCell2Img);
			}
			table.addCell(keyValRow6Cell2);

			Cell keyValRow6Cell3 = new Cell();
			keyValRow6Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow6Cell3.setBorder(Border.NO_BORDER);
			Paragraph addEng = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificate.address"));
			addEng.setHorizontalAlignment(HorizontalAlignment.LEFT);
			addEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			addEng.setFontSize(TEXT_SIZE_8);
			keyValRow6Cell3.add(addEng);
			table.addCell(keyValRow6Cell3);

			Cell keyValRow6Cell4 = new Cell();
			keyValRow6Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow6Cell4.setBorder(Border.NO_BORDER);
			ImageData cell7tableCell4Data = ImageDataFactory.create(Utility.textToImage(birthRgDetail.getParentDetailDTO().getPdParaddress(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell7tableCell4Img = new Image(cell7tableCell4Data);
			
           float imageWidth=cell7tableCell4Img.getImageWidth();
			
			if(imageWidth>cellWidth) {
				String address=birthRgDetail.getParentDetailDTO().getPdParaddress();
				int size =Math.round(address.length()/2f);
				String address1=address.substring(0, size);
				address1=StringUtils.substringBeforeLast(address1, " ");
				String address2=StringUtils.substringAfterLast(address, address1);
				
				ImageData address1ImgData = ImageDataFactory.create(Utility.textToImage(address1, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address1Img = new Image(address1ImgData);
				
				ImageData address2ImgData = ImageDataFactory.create(Utility.textToImage(address2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address2Img = new Image(address2ImgData);
				
				if(address1Img.getImageWidth()>cellWidth) {
				 String[] str =  new String[2];
				 str=divideCell(address1);
					
					ImageData address1aImgData = ImageDataFactory.create(Utility.textToImage(str[0], ARIALUNICODE,TEXT_SIZE_8, false));
					Image address1aImg = new Image(address1aImgData);
					
					ImageData address2aImgData = ImageDataFactory.create(Utility.textToImage(str[1], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address2aImg = new Image(address2aImgData);
					
					keyValRow6Cell4.add(address1aImg);
					keyValRow6Cell4.add(new Paragraph(" "));
					keyValRow6Cell4.add(address2aImg);
				}
				else {
					address1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
					address1Img.setTextAlignment(TextAlignment.LEFT);
					keyValRow6Cell4.add(address1Img);
					keyValRow6Cell4.add(new Paragraph(""));
				}
				
				if(address2Img.getImageWidth()>cellWidth) {
					 String[] str =  new String[2];
					 str=divideCell(address2);
					
					ImageData address1bImgData = ImageDataFactory.create(Utility.textToImage(str[0], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address1bImg = new Image(address1bImgData);
					
					ImageData address2bImgData = ImageDataFactory.create(Utility.textToImage(str[1], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address2bImg = new Image(address2bImgData);
					
					keyValRow6Cell4.add(address1bImg);
					keyValRow6Cell4.add(new Paragraph(" "));
					keyValRow6Cell4.add(address2bImg);
				}
				else {
					address2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
					address2Img.setTextAlignment(TextAlignment.LEFT);
				    keyValRow6Cell4.add(address2Img);
				}
			}
			else {
				cell7tableCell4Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				cell7tableCell4Img.setTextAlignment(TextAlignment.LEFT);
				keyValRow6Cell4.add(cell7tableCell4Img);
			}
			table.addCell(keyValRow6Cell4);

			Cell keyValRow7Cell1 = new Cell();
			keyValRow7Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow7Cell1.setBorder(Border.NO_BORDER);
			ImageData cell8tableCell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificate.regNoMar"),MANGAL, TEXT_SIZE_8, false));
			Image cell8tableCell1Img = new Image(cell8tableCell1Data);
			cell8tableCell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell8tableCell1Img.setTextAlignment(TextAlignment.LEFT);
			cell8tableCell1Img.setMarginLeft(5f);
			// cell8tableCell1Img.setAutoScale(true);
			keyValRow7Cell1.add(cell8tableCell1Img);
			table.addCell(keyValRow7Cell1);

			Cell keyValRow7Cell2 = new Cell();
			keyValRow7Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow7Cell2.setBorder(Border.NO_BORDER);
			ImageData cell8tableCell2Data = ImageDataFactory.create(Utility.textToImage(Utility.convertToRegional("marathi", birthRgDetail.getBrRegNo()), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell8tableCell2Img = new Image(cell8tableCell2Data);
			cell8tableCell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell8tableCell2Img.setTextAlignment(TextAlignment.LEFT);
			// cell8tableCell2Img.setAutoScale(true);
			keyValRow7Cell2.add(cell8tableCell2Img);
			table.addCell(keyValRow7Cell2);

			Cell keyValRow7Cell3 = new Cell();
			keyValRow7Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow7Cell3.setBorder(Border.NO_BORDER);
			ImageData cell8tableCell3Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificate.regDateMar"),MANGAL, TEXT_SIZE_8, false));
			Image cell8tableCell3Img = new Image(cell8tableCell3Data);
			cell8tableCell3Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell8tableCell3Img.setTextAlignment(TextAlignment.LEFT);
			// cell8tableCell3Img.setAutoScale(true);
			keyValRow7Cell3.add(cell8tableCell3Img);
			table.addCell(keyValRow7Cell3);

			Cell keyValRow7Cell4 = new Cell();
			keyValRow7Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow7Cell4.setBorder(Border.NO_BORDER);
			ImageData cell8tableCell4Data = ImageDataFactory.create(Utility.textToImage(Utility.convertToRegional("marathi", birthRgDetail.getAppDateOfRegistration()), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell8tableCell4Img = new Image(cell8tableCell4Data);
			cell8tableCell4Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell8tableCell4Img.setTextAlignment(TextAlignment.LEFT);
			// cell8tableCell4Img.setAutoScale(true);
			keyValRow7Cell4.add(cell8tableCell4Img);
			table.addCell(keyValRow7Cell4);

			Cell keyValRow8Cell1 = new Cell();
			keyValRow8Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow8Cell1.setBorder(Border.NO_BORDER);
			Paragraph regNoEng = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificate.regNo"));
			regNoEng.setHorizontalAlignment(HorizontalAlignment.LEFT);
			regNoEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			regNoEng.setFontSize(TEXT_SIZE_8);
			regNoEng.setMarginLeft(5f);
			keyValRow8Cell1.add(regNoEng);
			table.addCell(keyValRow8Cell1);

			Cell keyValRow8Cell2 = new Cell();
			keyValRow8Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow8Cell2.setBorder(Border.NO_BORDER);
			ImageData cell9tableCell2Data = ImageDataFactory.create(Utility.textToImage(birthRgDetail.getBrRegNo(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell9tableCell2Img = new Image(cell9tableCell2Data);
			cell9tableCell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell9tableCell2Img.setTextAlignment(TextAlignment.LEFT);
			// cell9tableCell2Img.setAutoScale(true);
			keyValRow8Cell2.add(cell9tableCell2Img);
			table.addCell(keyValRow8Cell2);

			Cell keyValRow8Cell3 = new Cell();
			keyValRow8Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow8Cell3.setBorder(Border.NO_BORDER);
			Paragraph regDateEng = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificate.regDate"));
			regDateEng.setHorizontalAlignment(HorizontalAlignment.LEFT);
			regDateEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			regDateEng.setFontSize(TEXT_SIZE_8);
			keyValRow8Cell3.add(regDateEng);
			table.addCell(keyValRow8Cell3);

			Cell keyValRow8Cell4 = new Cell();
			keyValRow8Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow8Cell4.setBorder(Border.NO_BORDER);
			ImageData cell9tableCell4Data = ImageDataFactory.create(Utility.textToImage(birthRgDetail.getAppDateOfRegistration(), ARIALUNICODE, TEXT_SIZE_9, false));
			Image cell9tableCell4Img = new Image(cell9tableCell4Data);
			cell9tableCell4Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell9tableCell4Img.setTextAlignment(TextAlignment.LEFT);
			// cell9tableCell4Img.setAutoScale(true);
			keyValRow8Cell4.add(cell9tableCell4Img);
			table.addCell(keyValRow8Cell4);

			Cell keyValRow9Cell1 = new Cell();
			keyValRow9Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow9Cell1.setBorder(Border.NO_BORDER);
			ImageData cell10tableCell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificate.remarkMar"),MANGAL, TEXT_SIZE_8, false));
			Image cell10tableCell1Img = new Image(cell10tableCell1Data);
			cell10tableCell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell10tableCell1Img.setTextAlignment(TextAlignment.LEFT);
			cell10tableCell1Img.setMarginLeft(5f);
			// cell10tableCell1Img.setAutoScale(true);
			keyValRow9Cell1.add(cell10tableCell1Img);
			table.addCell(keyValRow9Cell1);

			Cell keyValRow9Cell2 = new Cell();
			keyValRow9Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow9Cell2.setBorder(Border.NO_BORDER);
			ImageData cell10tableCell2Data = ImageDataFactory.create(Utility.textToImage(birthRgDetail.getAuthRemark(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell10tableCell2Img = new Image(cell10tableCell2Data);
			cell10tableCell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell10tableCell2Img.setTextAlignment(TextAlignment.LEFT);
			// cell10tableCell2Img.setAutoScale(true);
			keyValRow9Cell2.add(cell10tableCell2Img);
			table.addCell(keyValRow9Cell2);

			Cell keyValRow9Cell3 = new Cell();
			keyValRow9Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow9Cell3.setBorder(Border.NO_BORDER);
			ImageData cell10tableCell3Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificate.signMar"),MANGAL, TEXT_SIZE_8, false));
			Image cell10tableCell3Img = new Image(cell10tableCell3Data);
			cell10tableCell3Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell10tableCell3Img.setTextAlignment(TextAlignment.LEFT);
			// cell10tableCell3Img.setAutoScale(true);
			keyValRow9Cell3.add(cell10tableCell3Img);
			table.addCell(keyValRow9Cell3);

			Cell keyValRow9Cell4 = new Cell();
			keyValRow9Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow9Cell4.setBorder(Border.NO_BORDER);
			ImageData cell10tableCell4Data = ImageDataFactory.create(Utility.textToImage("", ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell10tableCell4Img = new Image(cell10tableCell4Data);
			cell10tableCell4Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell10tableCell4Img.setTextAlignment(TextAlignment.LEFT);
			// cell10tableCell4Img.setAutoScale(true);
			keyValRow9Cell4.add(cell10tableCell4Img);
			table.addCell(keyValRow9Cell4);

			Cell keyValRow10Cell1 = new Cell();
			keyValRow10Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow10Cell1.setBorder(Border.NO_BORDER);
			Paragraph remarkEng = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificate.remark"));
			remarkEng.setHorizontalAlignment(HorizontalAlignment.LEFT);
			remarkEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			remarkEng.setFontSize(TEXT_SIZE_8);
			remarkEng.setMarginLeft(5f);
			// cell11tableCell1Img.setAutoScale(true);
			keyValRow10Cell1.add(remarkEng);
			table.addCell(keyValRow10Cell1);

			Cell keyValRow10Cell2 = new Cell();
			keyValRow10Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow10Cell2.setBorder(Border.NO_BORDER);
			ImageData cell11tableCell2Data = ImageDataFactory.create(Utility.textToImage(birthRgDetail.getAuthRemark(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell11tableCell2Img = new Image(cell11tableCell2Data);
			cell11tableCell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell11tableCell2Img.setTextAlignment(TextAlignment.LEFT);
			// cell11tableCell2Img.setAutoScale(true);
			keyValRow10Cell2.add(cell11tableCell2Img);
			table.addCell(keyValRow10Cell2);

			Cell keyValRow10Cell3 = new Cell();
			keyValRow10Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow10Cell3.setBorder(Border.NO_BORDER);
			Paragraph signDateEng = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificate.sign"));
			signDateEng.setHorizontalAlignment(HorizontalAlignment.LEFT);
			signDateEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			signDateEng.setFontSize(TEXT_SIZE_8);
			keyValRow10Cell3.add(signDateEng);
			table.addCell(keyValRow10Cell3);

			Cell keyValRow10Cell4 = new Cell();
			keyValRow10Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow10Cell4.setBorder(Border.NO_BORDER);
			ImageData cell11tableCell4Data = ImageDataFactory.create(Utility.textToImage("", ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell11tableCell4Img = new Image(cell11tableCell4Data);
			cell11tableCell4Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell11tableCell4Img.setTextAlignment(TextAlignment.LEFT);
			// cell11tableCell4Img.setAutoScale(true);
			keyValRow10Cell4.add(cell11tableCell4Img);
			table.addCell(keyValRow10Cell4);
			
			table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
			
			
			Cell keyValRow13Cell1 = new Cell();
			keyValRow13Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow13Cell1.setBorder(Border.NO_BORDER);
			ImageData newDateMarK = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("death.certificate.todayDateMar"),MANGAL, TEXT_SIZE_8, false));
			Image newDateMarKImg = new Image(newDateMarK);
			newDateMarKImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			newDateMarKImg.setTextAlignment(TextAlignment.LEFT);
			newDateMarKImg.setMarginLeft(5f);
			// cell10tableCell1Img.setAutoScale(true);
			keyValRow13Cell1.add(newDateMarKImg);
			table.addCell(keyValRow13Cell1);

			Cell keyValRow13Cell2 = new Cell();
			keyValRow13Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow13Cell2.setBorder(Border.NO_BORDER);
			ImageData newDateMarV = ImageDataFactory.create(Utility.textToImage(Utility.convertToRegional("marathi", birthRgDetail.getNewDate()), ARIALUNICODE, TEXT_SIZE_8, false));
			Image newDateMarVImg = new Image(newDateMarV);
			newDateMarVImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			newDateMarVImg.setTextAlignment(TextAlignment.LEFT);
			// cell10tableCell2Img.setAutoScale(true);
			keyValRow13Cell2.add(newDateMarVImg);
			table.addCell(keyValRow13Cell2);

			Cell keyValRow13Cell3 = new Cell();
			keyValRow13Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow13Cell3.setBorder(Border.NO_BORDER);
			ImageData addressMarK = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("death.certificate.addressMar"),MANGAL, TEXT_SIZE_8, false));
			Image addressMarKImg = new Image(addressMarK);
			addressMarKImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			addressMarKImg.setTextAlignment(TextAlignment.LEFT);
			// cell10tableCell3Img.setAutoScale(true);
			keyValRow13Cell3.add(addressMarKImg);
			table.addCell(keyValRow13Cell3);

			Cell keyValRow13Cell4 = new Cell();
			keyValRow13Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow13Cell4.setBorder(Border.NO_BORDER);
			ImageData addressMarV = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("death.certificate.add.headOffice"), ARIALUNICODE, TEXT_SIZE_8, false));
			Image addressMarVImg = new Image(addressMarV);
			addressMarVImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			addressMarVImg.setTextAlignment(TextAlignment.LEFT);
			// cell10tableCell4Img.setAutoScale(true);
			keyValRow13Cell4.add(addressMarVImg);
			table.addCell(keyValRow13Cell4);

			Cell keyValRow14Cell1 = new Cell();
			keyValRow14Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow14Cell1.setBorder(Border.NO_BORDER);
			Paragraph newDateK = new Paragraph(ApplicationSession.getInstance().getMessage("death.certificate.todayDate"));
			newDateK.setHorizontalAlignment(HorizontalAlignment.LEFT);
			newDateK.setVerticalAlignment(VerticalAlignment.MIDDLE);
			newDateK.setFontSize(TEXT_SIZE_8);
			newDateK.setMarginLeft(5f);
			// cell11tableCell1Img.setAutoScale(true);
			keyValRow14Cell1.add(newDateK);
			table.addCell(keyValRow14Cell1);

			Cell keyValRow14Cell2 = new Cell();
			keyValRow14Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow14Cell2.setBorder(Border.NO_BORDER);
			ImageData newDateV = ImageDataFactory.create(Utility.textToImage(birthRgDetail.getNewDate(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image newDateVImg = new Image(newDateV);
			newDateVImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			newDateVImg.setTextAlignment(TextAlignment.LEFT);
			// cell11tableCell2Img.setAutoScale(true);
			keyValRow14Cell2.add(newDateVImg);
			table.addCell(keyValRow14Cell2);

			Cell keyValRow14Cell3 = new Cell();
			keyValRow14Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow14Cell3.setBorder(Border.NO_BORDER);
			Paragraph addressK = new Paragraph(ApplicationSession.getInstance().getMessage("death.certificate.address"));
			addressK.setHorizontalAlignment(HorizontalAlignment.LEFT);
			addressK.setVerticalAlignment(VerticalAlignment.MIDDLE);
			addressK.setFontSize(TEXT_SIZE_8);
			keyValRow14Cell3.add(addressK);
			table.addCell(keyValRow14Cell3);

			Cell keyValRow14Cell4 = new Cell();
			keyValRow14Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow14Cell4.setBorder(Border.NO_BORDER);
			ImageData addressV = ImageDataFactory.create(Utility.textToImage("HEAD OFFICE", ARIALUNICODE, TEXT_SIZE_8, false));
			Image addressVImg = new Image(addressV);
			addressVImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			addressVImg.setTextAlignment(TextAlignment.LEFT);
			// cell11tableCell4Img.setAutoScale(true);
			keyValRow14Cell4.add(addressVImg);
			table.addCell(keyValRow14Cell4);
			
			
			Cell keyValRow16Cell1 = new Cell(1,4);
			keyValRow16Cell1.setHorizontalAlignment(HorizontalAlignment.CENTER);
			keyValRow16Cell1.setHeight(50f);
			keyValRow16Cell1.setWidth(40f);
			keyValRow16Cell1.setBorder(Border.NO_BORDER);
			Table table1 = new Table(1);
			table1.setHorizontalAlignment(HorizontalAlignment.CENTER);
			Cell keyValRow16aCell1 = new Cell();
			keyValRow16aCell1.setHorizontalAlignment(HorizontalAlignment.CENTER);
			keyValRow16aCell1.setHeight(100f);
			keyValRow16aCell1.setWidth(60f);
			ImageData add = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificateparaSeal"), ARIALUNICODE, TEXT_SIZE_10, false));
			Image addImg = new Image(add);
			addImg.setHorizontalAlignment(HorizontalAlignment.CENTER);
			keyValRow16aCell1.add(addImg);
			table1.addCell(keyValRow16aCell1);
			keyValRow16Cell1.add(table1);
			table.addCell(keyValRow16Cell1);
			
			table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
			
			Cell keyValParaaCell1 = new Cell(1,2);
			keyValParaaCell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			//keyValParaaCell1.setBorder(Border.NO_BORDER);
			keyValParaaCell1.setBackgroundColor(ColorConstants.BLACK);
			keyValParaaCell1.setFontColor(ColorConstants.WHITE);
			ImageData orgNameDataa = ImageDataFactory.create(Utility.textToImageColor(ApplicationSession.getInstance().getMessage("birth.certificatepara9"), ARIALUNICODE, 10f, false));
			Image orgNameImga = new Image(orgNameDataa);
			keyValParaaCell1.add(orgNameImga.setHorizontalAlignment(HorizontalAlignment.LEFT));
			table.addCell(keyValParaaCell1);
			
			Cell keyValParaaCell3 = new Cell(1,2);
			keyValParaaCell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			//keyValParaaCell3.setBorder(Border.NO_BORDER);
			keyValParaaCell3.setBackgroundColor(ColorConstants.BLACK);
			keyValParaaCell3.setFontColor(ColorConstants.WHITE);
			Paragraph para3a = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificatepara10"));
			para3a.setHorizontalAlignment(HorizontalAlignment.RIGHT);
			para3a.setVerticalAlignment(VerticalAlignment.MIDDLE);
			para3a.setTextAlignment(TextAlignment.RIGHT);
			para3a.setFontSize(TEXT_SIZE_10);
			// cell5tableCell1Img.setAutoScale(true);
			keyValParaaCell3.add(para3a);
			table.addCell(keyValParaaCell3);

			document.add(table);
			document.close();
			writer.close();
			return true;
		} catch (Exception e) {
			 LOGGER.info("Exception Occur" + e);
			return false;
		}
	}
	 
	 
	 public boolean generateDeathCertificate(String docPath, Long applicationNo,TbDeathregDTO certificateDetailList){
			
		 final String MANGAL =  ApplicationSession.getInstance().getMessage("mangalFont.path");
		  final String ARIALUNICODE = ApplicationSession.getInstance().getMessage("arialUniCode.path");

		try {
			PdfWriter writer = new PdfWriter(docPath);
			PdfDocument pdfDoc = new PdfDocument(writer);
			pdfDoc.addNewPage();
			Document document = new Document(pdfDoc);
			FontProgram fontProgram = FontProgramFactory.createFont(ARIALUNICODE);
			PdfFont font = PdfFontFactory.createFont(fontProgram, PdfEncodings.IDENTITY_H);
			document.setFont(font);
			document.setMargins(8f, 8f, 8f,8f);
			// Adding table
			//Table table = new Table(4);
			Table table = new Table(new float[] {145,145,145,145}) // in points
			        .setWidth(580); //100 pt
			 
			//table.setWidth(new UnitValue(UnitValue.PERCENT, 100));
			table.setHorizontalAlignment(HorizontalAlignment.LEFT);
			Border b1 = new DoubleBorder(4);
			table.setBorder(b1);
			float cellWidth=140;
			//table.setMargin(-1f);
			SolidLine line = new SolidLine(1f);
			line.setColor(ColorConstants.BLACK);
			LineSeparator ls = new LineSeparator(line);
			ls.setBold();
			ls.setWidth(new UnitValue(UnitValue.PERCENT, 98));
			ls.setHorizontalAlignment(HorizontalAlignment.CENTER);
			  
			Cell keyValPara0Cell1 = new Cell(1,2);
			keyValPara0Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara0Cell1.setBorder(Border.NO_BORDER);
			ImageData orgNameData0 = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificatepara11")+ApplicationSession.getInstance().getMessage("birth.certificatepara11a")+certificateDetailList.getDrCertNo(), ARIALUNICODE, 10f, false));
			Image orgNameImg0 = new Image(orgNameData0);
			keyValPara0Cell1.add(orgNameImg0.setHorizontalAlignment(HorizontalAlignment.LEFT));
			/*
			 * Text para0 = new
			 * Text(ApplicationSession.getInstance().getMessage("birth.certificatepara11a"))
			 * ; para0.setHorizontalAlignment(HorizontalAlignment.LEFT);
			 * //para0.setVerticalAlignment(VerticalAlignment.MIDDLE);
			 * para0.setTextAlignment(TextAlignment.LEFT); para0.setFontSize(TEXT_SIZE_10);
			 */
			// cell5tableCell1Img.setAutoScale(true);
			//keyValPara0Cell1.add(para0);
			table.addCell(keyValPara0Cell1);
			
			Cell keyValPara0Cell2 = new Cell();
			keyValPara0Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara0Cell2.setBorder(Border.NO_BORDER);
			Paragraph para1 = new Paragraph("");
			keyValPara0Cell2.add(para1);
			table.addCell(keyValPara0Cell2);
			
			Cell keyValPara0Cell3 = new Cell();
			keyValPara0Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara0Cell3.setBorder(Border.NO_BORDER);
			Paragraph para3 = new Paragraph(ApplicationSession.getInstance().getMessage("death.certificatepara12"));
			para3.setHorizontalAlignment(HorizontalAlignment.LEFT);
			para3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			para3.setTextAlignment(TextAlignment.CENTER);
			para3.setFontSize(TEXT_SIZE_10);
			// cell5tableCell1Img.setAutoScale(true);
			keyValPara0Cell3.add(para3);
			table.addCell(keyValPara0Cell3);
			
			Cell r1Cell3c = new Cell(1,4);
			r1Cell3c.setHorizontalAlignment(HorizontalAlignment.CENTER);
			r1Cell3c.setBorder(Border.NO_BORDER);
			r1Cell3c.add(ls);
			table.addCell(r1Cell3c.setPadding(PADDING));
		
			Cell r1Cell1 = new Cell();
			ImageData leftImgData = ImageDataFactory.create(filenetPath + ApplicationSession.getInstance().getMessage("leftlogo"));
			Image logoImg = new Image(leftImgData);
			logoImg.setHeight(95f);
			logoImg.setWidth(70f);
			r1Cell1.add(logoImg);
			r1Cell1.setHorizontalAlignment(HorizontalAlignment.LEFT);
			r1Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			r1Cell1.setBorder(Border.NO_BORDER);
			table.addCell(r1Cell1);

			Cell r1Cell2 = new Cell(1, 2);
			ImageData orgNameData = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificate1"), MANGAL, 12f, false));
			Image orgNameImg = new Image(orgNameData);
			r1Cell2.add(orgNameImg.setHorizontalAlignment(HorizontalAlignment.CENTER));
			Paragraph orgNameEng = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificate2"));
			orgNameEng.setHorizontalAlignment(HorizontalAlignment.CENTER);
			orgNameEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			orgNameEng.setTextAlignment(TextAlignment.CENTER);
			orgNameEng.setFontSize(12f);
			//orgNameEng.setMarginLeft(35f);
			r1Cell2.add(orgNameEng);
			
			ImageData orgNameData1 = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificate3"), MANGAL, 10f, false));
			Image orgNameImg1 = new Image(orgNameData1);
			r1Cell2.add(orgNameImg1.setHorizontalAlignment(HorizontalAlignment.CENTER));
			Paragraph orgNameEng1 = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificate4"));
			orgNameEng1.setHorizontalAlignment(HorizontalAlignment.CENTER);
			orgNameEng1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			orgNameEng1.setTextAlignment(TextAlignment.CENTER);
			orgNameEng1.setFontSize(10f);
			//orgNameEng1.setMarginLeft(35f);
			r1Cell2.add(orgNameEng1);
			
			ImageData orgNameData2 = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificate5"), MANGAL, 10f, false));
			Image orgNameImg2 = new Image(orgNameData2);
			r1Cell2.add(orgNameImg2.setHorizontalAlignment(HorizontalAlignment.CENTER));
			Paragraph orgNameEng2 = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificate6"));
			orgNameEng2.setHorizontalAlignment(HorizontalAlignment.CENTER);
			orgNameEng2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			orgNameEng2.setTextAlignment(TextAlignment.CENTER);
			orgNameEng2.setFontSize(10f);
			//orgNameEng2.setMarginLeft(35f);
			r1Cell2.add(orgNameEng2);
			r1Cell2.setHorizontalAlignment(HorizontalAlignment.CENTER);
			r1Cell2.setVerticalAlignment(VerticalAlignment.TOP);
			r1Cell2.setBorder(Border.NO_BORDER);
			table.addCell(r1Cell2);

			Cell r1Cell3 = new Cell();
			ImageData rightImgData = ImageDataFactory.create(filenetPath + ApplicationSession.getInstance().getMessage("rightlogo"));
			Image logoImgRight = new Image(rightImgData);
			logoImgRight.setWidth(70f);
			logoImgRight.setHeight(71f);
			r1Cell3.add(logoImgRight.setHorizontalAlignment(HorizontalAlignment.RIGHT));
			r1Cell3.setHorizontalAlignment(HorizontalAlignment.RIGHT);
			r1Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			r1Cell3.setBorder(Border.NO_BORDER);
			table.addCell(r1Cell3.setPadding(PADDING));
			
			Cell r1Cell3a = new Cell(1,4);
			r1Cell3a.setHorizontalAlignment(HorizontalAlignment.CENTER);
			r1Cell3a.setBorder(Border.NO_BORDER);
			r1Cell3a.add(ls);
			table.addCell(r1Cell3a.setPadding(PADDING));
			
			Cell keyValPara1Cell1 = new Cell(1,4);
			keyValPara1Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara1Cell1.setBorder(Border.NO_BORDER);
			ImageData para1tableCell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("death.certificatepara1"),MANGAL, TEXT_SIZE_10, false));
			Image para1tableCell1Img = new Image(para1tableCell1Data);
			para1tableCell1Img.setHorizontalAlignment(HorizontalAlignment.CENTER);
			//para1tableCell1Img.setTextAlignment(TextAlignment.LEFT);
			para1tableCell1Img.setTextAlignment(TextAlignment.CENTER);
			// cell4tableCell1Img.setAutoScale(true);
			keyValPara1Cell1.add(para1tableCell1Img);
			table.addCell(keyValPara1Cell1);

			Cell keyValPara2Cell1 = new Cell(1,4);
			keyValPara2Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara2Cell1.setBorder(Border.NO_BORDER);
			Paragraph para2 = new Paragraph(ApplicationSession.getInstance().getMessage("death.certificatepara2"));
			para2.setHorizontalAlignment(HorizontalAlignment.LEFT);
			para2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			para2.setTextAlignment(TextAlignment.CENTER);
			para2.setFontSize(TEXT_SIZE_10);
			// cell5tableCell1Img.setAutoScale(true);
			keyValPara2Cell1.add(para2);
			table.addCell(keyValPara2Cell1);
			
			Cell keyValPara3Cell1 = new Cell(1,4);
			keyValPara3Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara3Cell1.setBorder(Border.NO_BORDER);
			ImageData para3tableCell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificatepara3"),MANGAL, TEXT_SIZE_9, false));
			Image para3tableCell1Img = new Image(para3tableCell1Data);
			para3tableCell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			para3tableCell1Img.setTextAlignment(TextAlignment.LEFT);
			para3tableCell1Img.setMarginLeft(20f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValPara3Cell1.add(para3tableCell1Img);
			table.addCell(keyValPara3Cell1);
			
			Cell keyValPara3bCell1 = new Cell(1,4);
			keyValPara3bCell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara3bCell1.setBorder(Border.NO_BORDER);
			ImageData para3btableCell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificatepara4"),MANGAL, TEXT_SIZE_9, false));
			Image para3btableCell1Img = new Image(para3btableCell1Data);
			para3btableCell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			para3btableCell1Img.setTextAlignment(TextAlignment.LEFT);
			para3btableCell1Img.setMarginLeft(8f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValPara3bCell1.add(para3btableCell1Img);
			table.addCell(keyValPara3bCell1);
			
			Cell keyValPara4Cell1 = new Cell(1,4);
			keyValPara4Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara4Cell1.setBorder(Border.NO_BORDER);
			Paragraph para4 = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificatepara5"));
			para4.setHorizontalAlignment(HorizontalAlignment.LEFT);
			para4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			para4.setMarginLeft(8f);
			para4.setFirstLineIndent(20f);
			para4.setFontSize(TEXT_SIZE_9);
			// cell5tableCell1Img.setAutoScale(true);
			keyValPara4Cell1.add(para4);
			table.addCell(keyValPara4Cell1);
			
			Cell keyValPara5Cell1 = new Cell(1,4);
			keyValPara5Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara5Cell1.setBorder(Border.NO_BORDER);
			ImageData para5tableCell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificatepara6"),MANGAL, TEXT_SIZE_9, false));
			Image para5tableCell1Img = new Image(para5tableCell1Data);
			para5tableCell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			para5tableCell1Img.setTextAlignment(TextAlignment.LEFT);
			para5tableCell1Img.setMarginLeft(20f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValPara5Cell1.add(para5tableCell1Img);
			table.addCell(keyValPara5Cell1);
			
			Cell keyValPara5bbCell1 = new Cell(1,4);
			keyValPara5bbCell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara5bbCell1.setBorder(Border.NO_BORDER);
			ImageData para5btableCell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificatepara7"),MANGAL, TEXT_SIZE_9, false));
			Image para5btableCell1Img = new Image(para5btableCell1Data);
			para5btableCell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			para5btableCell1Img.setTextAlignment(TextAlignment.LEFT);
			para5btableCell1Img.setMarginLeft(8f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValPara5bbCell1.add(para5btableCell1Img);
			table.addCell(keyValPara5bbCell1);
			
			Cell keyValPara6Cell1 = new Cell(1,4);
			keyValPara6Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValPara6Cell1.setBorder(Border.NO_BORDER);
			Paragraph para6 = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificatepara8"));
			para6.setHorizontalAlignment(HorizontalAlignment.LEFT);
			para6.setVerticalAlignment(VerticalAlignment.MIDDLE);
			para6.setMarginLeft(8f);
			para6.setFirstLineIndent(20f);
			para6.setFontSize(TEXT_SIZE_9);
			// cell5tableCell1Img.setAutoScale(true);
			keyValPara6Cell1.add(para6);
			table.addCell(keyValPara6Cell1);
			
			Cell keyValRow1Cell1 = new Cell();
			keyValRow1Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow1Cell1.setBorder(Border.NO_BORDER);
			ImageData cell4tableCell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("death.certificate.drMarDeceasedname"),MANGAL, TEXT_SIZE_8, false));
			Image cell4tableCell1Img = new Image(cell4tableCell1Data);
			cell4tableCell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell4tableCell1Img.setTextAlignment(TextAlignment.LEFT);
			cell4tableCell1Img.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow1Cell1.add(cell4tableCell1Img);
			table.addCell(keyValRow1Cell1);

			Cell keyValRow1Cell2 = new Cell();
			keyValRow1Cell2.setBorder(Border.NO_BORDER);
			keyValRow1Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData cell4tableCell2Data = ImageDataFactory.create(Utility.textToImage(certificateDetailList.getDrMarDeceasedname(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell4tableCell2Img = new Image(cell4tableCell2Data);
			
			
			
			float imageWidthRow1Cell2=cell4tableCell2Img.getImageWidth();
			
			if (imageWidthRow1Cell2 > cellWidth) {
				String addressRow1Cell2 = certificateDetailList.getDrMarDeceasedname();
				int sizeRow1Cell2 = Math.round(addressRow1Cell2.length() / 2f);
				String address1Row1Cell2 = addressRow1Cell2.substring(0, sizeRow1Cell2);
				address1Row1Cell2 = StringUtils.substringBeforeLast(address1Row1Cell2, " ");
				String address2Row1Cell2 = StringUtils.substringAfterLast(addressRow1Cell2, address1Row1Cell2);

				ImageData address1ImgDataRow1Cell2 = ImageDataFactory.create(Utility.textToImage(address1Row1Cell2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address1ImgRow1Cell2 = new Image(address1ImgDataRow1Cell2);

				ImageData address2ImgDataRow1Cell2 = ImageDataFactory.create(Utility.textToImage(address2Row1Cell2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address2ImgRow1Cell2 = new Image(address2ImgDataRow1Cell2);

				address1ImgRow1Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
				address1ImgRow1Cell2.setTextAlignment(TextAlignment.LEFT);
				keyValRow1Cell2.add(address1ImgRow1Cell2);
				keyValRow1Cell2.add(new Paragraph(""));
				address2ImgRow1Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
				address2ImgRow1Cell2.setTextAlignment(TextAlignment.LEFT);
				keyValRow1Cell2.add(address2ImgRow1Cell2);
				
			} else {
				cell4tableCell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				cell4tableCell2Img.setTextAlignment(TextAlignment.LEFT);
				keyValRow1Cell2.add(cell4tableCell2Img);
			}
			table.addCell(keyValRow1Cell2);

			Cell keyValRow1Cell3 = new Cell();
			keyValRow1Cell3.setBorder(Border.NO_BORDER);
			keyValRow1Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData cell4tableCell3Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("death.certificate.drSexMar"),MANGAL, TEXT_SIZE_8, false));
			Image cell4tableCell3Img = new Image(cell4tableCell3Data);
			cell4tableCell3Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell4tableCell3Img.setTextAlignment(TextAlignment.LEFT);
			// cell4tableCell3Img.setAutoScale(true);
			keyValRow1Cell3.add(cell4tableCell3Img);
			table.addCell(keyValRow1Cell3);

			Cell keyValRow1Cell4 = new Cell();
			keyValRow1Cell4.setBorder(Border.NO_BORDER);
			keyValRow1Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData cell4tableCell4Data = ImageDataFactory.create(Utility.textToImage(certificateDetailList.getDrSexMar(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell4tableCell4Img = new Image(cell4tableCell4Data);
			cell4tableCell4Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell4tableCell4Img.setTextAlignment(TextAlignment.LEFT);
			// cell4tableCell4Img.setAutoScale(true);
			keyValRow1Cell4.add(cell4tableCell4Img);
			table.addCell(keyValRow1Cell4);

			Cell keyValRow2Cell1 = new Cell();
			keyValRow2Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow2Cell1.setBorder(Border.NO_BORDER);
			Paragraph childNameEng = new Paragraph(ApplicationSession.getInstance().getMessage("death.certificate.drDeceasedname"));
			childNameEng.setHorizontalAlignment(HorizontalAlignment.LEFT);
			childNameEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			childNameEng.setFontSize(TEXT_SIZE_8);
			childNameEng.setMarginLeft(5f);
			// cell5tableCell1Img.setAutoScale(true);
			keyValRow2Cell1.add(childNameEng);
			table.addCell(keyValRow2Cell1);

			Cell keyValRow2Cell2 = new Cell();
			keyValRow2Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow2Cell2.setBorder(Border.NO_BORDER);
			ImageData cell5tableCell2Data = ImageDataFactory.create(Utility.textToImage(certificateDetailList.getDrDeceasedname(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell5tableCell2Img = new Image(cell5tableCell2Data);
			
			float imageWidthRow2Cell2=cell5tableCell2Img.getImageWidth();
			
			if (imageWidthRow2Cell2 > cellWidth) {
				String addressRow2Cell2 = certificateDetailList.getDrDeceasedname();
				int sizeRow2Cell2 = Math.round(addressRow2Cell2.length() / 2f);
				String address1Row2Cell2 = addressRow2Cell2.substring(0, sizeRow2Cell2);
				address1Row2Cell2 = StringUtils.substringBeforeLast(address1Row2Cell2, " ");
				String address2Row2Cell2 = StringUtils.substringAfterLast(addressRow2Cell2, address1Row2Cell2);

				ImageData address1ImgDataRow2Cell2 = ImageDataFactory.create(Utility.textToImage(address1Row2Cell2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address1ImgRow2Cell2 = new Image(address1ImgDataRow2Cell2);

				ImageData address2ImgDataRow2Cell2 = ImageDataFactory.create(Utility.textToImage(address2Row2Cell2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address2ImgRow2Cell2 = new Image(address2ImgDataRow2Cell2);

				address1ImgRow2Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
				address1ImgRow2Cell2.setTextAlignment(TextAlignment.LEFT);
				keyValRow2Cell2.add(address1ImgRow2Cell2);
				keyValRow2Cell2.add(new Paragraph(""));
				address2ImgRow2Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
				address2ImgRow2Cell2.setTextAlignment(TextAlignment.LEFT);
				keyValRow2Cell2.add(address2ImgRow2Cell2);
				
			} else {
				cell5tableCell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				cell5tableCell2Img.setTextAlignment(TextAlignment.LEFT);
				keyValRow2Cell2.add(cell5tableCell2Img);
			}
			table.addCell(keyValRow2Cell2);

			Cell keyValRow2Cell3 = new Cell();
			keyValRow2Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow2Cell3.setBorder(Border.NO_BORDER);
			Paragraph genderNameEng = new Paragraph(ApplicationSession.getInstance().getMessage("death.certificate.drSex"));
			genderNameEng.setHorizontalAlignment(HorizontalAlignment.LEFT);
			genderNameEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			genderNameEng.setFontSize(TEXT_SIZE_8);
			// cell5tableCell3Img.setAutoScale(true);
			keyValRow2Cell3.add(genderNameEng);
			table.addCell(keyValRow2Cell3);

			Cell keyValRow2Cell4 = new Cell();
			keyValRow2Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow2Cell4.setBorder(Border.NO_BORDER);
			Paragraph genderNameData = new Paragraph(certificateDetailList.getDrSex());
			genderNameData.setHorizontalAlignment(HorizontalAlignment.LEFT);
			genderNameData.setVerticalAlignment(VerticalAlignment.MIDDLE);
			genderNameData.setFontSize(TEXT_SIZE_8);
			// cell5tableCell4Img.setAutoScale(true);
			keyValRow2Cell4.add(genderNameData);
			table.addCell(keyValRow2Cell4);
			
			Cell keyValRow11Cell1 = new Cell();
			keyValRow11Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow11Cell1.setBorder(Border.NO_BORDER);
			ImageData dateOfBirthMarK = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("death.certificate.drDodMar"),MANGAL, TEXT_SIZE_8, false));
			Image dateOfBirthMarKImg = new Image(dateOfBirthMarK);
			dateOfBirthMarKImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			dateOfBirthMarKImg.setTextAlignment(TextAlignment.LEFT);
			dateOfBirthMarKImg.setMarginLeft(5f);
			// cell4tableCell1Img.setAutoScale(true);
			keyValRow11Cell1.add(dateOfBirthMarKImg);
			table.addCell(keyValRow11Cell1);

			Cell keyValRow11Cell2 = new Cell();
			keyValRow11Cell2.setBorder(Border.NO_BORDER);
			keyValRow11Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData placeMarV = ImageDataFactory.create(Utility.textToImage(Utility.convertToRegional("marathi", certificateDetailList.getAppDateOfDeath()), ARIALUNICODE, TEXT_SIZE_8, false));
			Image placeMarVImg = new Image(placeMarV);
			placeMarVImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			placeMarVImg.setTextAlignment(TextAlignment.LEFT);
			// cell4tableCell2Img.setAutoScale(true);
			keyValRow11Cell2.add(placeMarVImg);
			table.addCell(keyValRow11Cell2);

			Cell keyValRow11Cell3 = new Cell();
			keyValRow11Cell3.setBorder(Border.NO_BORDER);
			keyValRow11Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData dateOfBirthMar = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("death.certificate.drMarDeathplace"),MANGAL, TEXT_SIZE_8, false));
			Image dateOfBirthMarImg = new Image(dateOfBirthMar);
			dateOfBirthMarImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			dateOfBirthMarImg.setTextAlignment(TextAlignment.LEFT);
			// cell4tableCell3Img.setAutoScale(true);
			keyValRow11Cell3.add(dateOfBirthMarImg);
			table.addCell(keyValRow11Cell3);

			Cell keyValRow11Cell4 = new Cell();
			keyValRow11Cell4.setBorder(Border.NO_BORDER);
			keyValRow11Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			ImageData placeValue = ImageDataFactory.create(Utility.textToImage(certificateDetailList.getDrMarDeathplace(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image placeValueImg = new Image(placeValue);
			
			
            float imageWidthRow11Cell4=placeValueImg.getImageWidth();
			
			if (imageWidthRow11Cell4 > cellWidth) {
				String addressRow11Cell4 = certificateDetailList.getDrMarDeathplace();
				int sizeRow11Cell4 = Math.round(addressRow11Cell4.length() / 2f);
				String address1Row11Cell4 = addressRow11Cell4.substring(0, sizeRow11Cell4);
				address1Row11Cell4 = StringUtils.substringBeforeLast(address1Row11Cell4, " ");
				String address2Row11Cell4 = StringUtils.substringAfterLast(addressRow11Cell4, address1Row11Cell4);

				ImageData address1ImgDataRow11Cell4 = ImageDataFactory.create(Utility.textToImage(address1Row11Cell4, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address1ImgRow11Cell4 = new Image(address1ImgDataRow11Cell4);

				ImageData address2ImgDataRow11Cell4 = ImageDataFactory.create(Utility.textToImage(address2Row11Cell4, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address2ImgRow11Cell4 = new Image(address2ImgDataRow11Cell4);

				if (address1ImgRow11Cell4.getImageWidth() > cellWidth) {
					String[] strRow11Cell4 = new String[2];
					strRow11Cell4 = divideCell(address1Row11Cell4);

					ImageData address1aImgDataRow11Cell4= ImageDataFactory.create(Utility.textToImage(strRow11Cell4[0], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address1aImgRow11Cell4 = new Image(address1aImgDataRow11Cell4);

					ImageData address2aImgDataRow11Cell4 = ImageDataFactory.create(Utility.textToImage(strRow11Cell4[1], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address2aImgRow11Cell4 = new Image(address2aImgDataRow11Cell4);

					keyValRow11Cell4.add(address1aImgRow11Cell4);
					keyValRow11Cell4.add(new Paragraph(" "));
					keyValRow11Cell4.add(address2aImgRow11Cell4);
				} else {
					address1ImgRow11Cell4.setHorizontalAlignment(HorizontalAlignment.LEFT);
					address1ImgRow11Cell4.setTextAlignment(TextAlignment.LEFT);
					keyValRow11Cell4.add(address1ImgRow11Cell4);
					keyValRow11Cell4.add(new Paragraph(""));
				}

				if (address2ImgRow11Cell4.getImageWidth() > cellWidth) {
					String[] strRow11Cell4 = new String[2];
					strRow11Cell4= divideCell(address2Row11Cell4);

					ImageData address1bImgDataRow11Cell4 = ImageDataFactory.create(Utility.textToImage(strRow11Cell4[0], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address1bImgRow11Cell4 = new Image(address1bImgDataRow11Cell4);

					ImageData address2bImgDataRow11Cell4 = ImageDataFactory.create(Utility.textToImage(strRow11Cell4[1], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address2bImgRow11Cell4 = new Image(address2bImgDataRow11Cell4);

					keyValRow11Cell4.add(address1bImgRow11Cell4);
					keyValRow11Cell4.add(new Paragraph(" "));
					keyValRow11Cell4.add(address2bImgRow11Cell4);
				} else {
					address2ImgRow11Cell4.setHorizontalAlignment(HorizontalAlignment.LEFT);
					address2ImgRow11Cell4.setTextAlignment(TextAlignment.LEFT);
					keyValRow11Cell4.add(address2ImgRow11Cell4);
				}
			} else {
				placeValueImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
				placeValueImg.setTextAlignment(TextAlignment.LEFT);
				keyValRow11Cell4.add(placeValueImg);
			}
			table.addCell(keyValRow11Cell4);
			
			Cell keyValRow12Cell1 = new Cell();
			keyValRow12Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow12Cell1.setBorder(Border.NO_BORDER);
			Paragraph dateOfBirth = new Paragraph(ApplicationSession.getInstance().getMessage("death.certificate.drDod"));
			dateOfBirth.setHorizontalAlignment(HorizontalAlignment.LEFT);
			dateOfBirth.setVerticalAlignment(VerticalAlignment.MIDDLE);
			dateOfBirth.setFontSize(TEXT_SIZE_8);
			dateOfBirth.setMarginLeft(5f);
			// cell5tableCell1Img.setAutoScale(true);
			keyValRow12Cell1.add(dateOfBirth);
			table.addCell(keyValRow12Cell1);

			Cell keyValRow12Cell2 = new Cell();
			keyValRow12Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow12Cell2.setBorder(Border.NO_BORDER);
			ImageData dateOfBirthKey = ImageDataFactory.create(Utility.textToImage(certificateDetailList.getAppDateOfDeath(), ARIALUNICODE, TEXT_SIZE_9, false));
			Image cell12tableCell4Img = new Image(dateOfBirthKey);
			cell12tableCell4Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell12tableCell4Img.setTextAlignment(TextAlignment.LEFT);
			// cell5tableCell2Img.setAutoScale(true);
			keyValRow12Cell2.add(cell12tableCell4Img);
			table.addCell(keyValRow12Cell2);

			Cell keyValRow12Cell3 = new Cell();
			keyValRow12Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow12Cell3.setBorder(Border.NO_BORDER);
			Paragraph placeKey = new Paragraph(ApplicationSession.getInstance().getMessage("death.certificate.drDeathplace"));
			placeKey.setHorizontalAlignment(HorizontalAlignment.LEFT);
			placeKey.setVerticalAlignment(VerticalAlignment.MIDDLE);
			placeKey.setFontSize(TEXT_SIZE_8);
			// cell5tableCell3Img.setAutoScale(true);
			keyValRow12Cell3.add(placeKey);
			table.addCell(keyValRow12Cell3);

			Cell keyValRow12Cell4 = new Cell();
			keyValRow12Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow12Cell4.setBorder(Border.NO_BORDER);
			ImageData placeValueV = ImageDataFactory.create(Utility.textToImage(certificateDetailList.getDrDeathplace(),ARIALUNICODE, TEXT_SIZE_8, false));
			Image placeValueVImg = new Image(placeValueV);

			float imageWidthRow12Cell4=placeValueVImg.getImageWidth();
			
			if (imageWidthRow12Cell4 > cellWidth) {
				String addressRow12Cell4 = certificateDetailList.getDrDeathplace();
				int sizeRow12Cell4 = Math.round(addressRow12Cell4.length() / 2f);
				String address1Row12Cell4 = addressRow12Cell4.substring(0, sizeRow12Cell4);
				address1Row12Cell4 = StringUtils.substringBeforeLast(address1Row12Cell4, " ");
				String address2Row12Cell4 = StringUtils.substringAfterLast(addressRow12Cell4, address1Row12Cell4);

				ImageData address1ImgDataRow12Cell4 = ImageDataFactory.create(Utility.textToImage(address1Row12Cell4, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address1ImgRow12Cell4 = new Image(address1ImgDataRow12Cell4);

				ImageData address2ImgDataRow12Cell4 = ImageDataFactory.create(Utility.textToImage(address2Row12Cell4, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address2ImgRow12Cell4 = new Image(address2ImgDataRow12Cell4);

				if (address1ImgRow12Cell4.getImageWidth() > cellWidth) {
					String[] strRow12Cell4 = new String[2];
					strRow12Cell4 = divideCell(address1Row12Cell4);

					ImageData address1aImgDataRow12Cell4= ImageDataFactory.create(Utility.textToImage(strRow12Cell4[0], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address1aImgRow12Cell4 = new Image(address1aImgDataRow12Cell4);

					ImageData address2aImgDataRow12Cell4 = ImageDataFactory.create(Utility.textToImage(strRow12Cell4[1], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address2aImgRow12Cell4 = new Image(address2aImgDataRow12Cell4);

					keyValRow12Cell4.add(address1aImgRow12Cell4);
					keyValRow12Cell4.add(new Paragraph(" "));
					keyValRow12Cell4.add(address2aImgRow12Cell4);
				} else {
					address1ImgRow12Cell4.setHorizontalAlignment(HorizontalAlignment.LEFT);
					address1ImgRow12Cell4.setTextAlignment(TextAlignment.LEFT);
					keyValRow12Cell4.add(address1ImgRow12Cell4);
					keyValRow12Cell4.add(new Paragraph(""));
				}

				if (address2ImgRow12Cell4.getImageWidth() > cellWidth) {
					String[] strRow12Cell4 = new String[2];
					strRow12Cell4= divideCell(address2Row12Cell4);

					ImageData address1bImgDataRow12Cell4 = ImageDataFactory.create(Utility.textToImage(strRow12Cell4[0], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address1bImgRow12Cell4 = new Image(address1bImgDataRow12Cell4);

					ImageData address2bImgDataRow12Cell4 = ImageDataFactory.create(Utility.textToImage(strRow12Cell4[1], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address2bImgRow12Cell4 = new Image(address2bImgDataRow12Cell4);

					keyValRow12Cell4.add(address1bImgRow12Cell4);
					keyValRow12Cell4.add(new Paragraph(" "));
					keyValRow12Cell4.add(address2bImgRow12Cell4);
				} else {
					address2ImgRow12Cell4.setHorizontalAlignment(HorizontalAlignment.LEFT);
					address2ImgRow12Cell4.setTextAlignment(TextAlignment.LEFT);
					keyValRow12Cell4.add(address2ImgRow12Cell4);
				}
			} else {
				placeValueVImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
				placeValueVImg.setTextAlignment(TextAlignment.LEFT);
				keyValRow12Cell4.add(placeValueVImg);
			}
			table.addCell(keyValRow12Cell4);

			Cell keyValRow3Cell1 = new Cell();
			keyValRow3Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow3Cell1.setBorder(Border.NO_BORDER);
			ImageData cell2tableCell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("death.certificate.drMarMotherName"),MANGAL, TEXT_SIZE_8, false));
			Image cell2tableCell1Img = new Image(cell2tableCell1Data);
			cell2tableCell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell2tableCell1Img.setTextAlignment(TextAlignment.LEFT);
			cell2tableCell1Img.setMarginLeft(5f);
			// cell2tableCell1Img.setAutoScale(true);
			keyValRow3Cell1.add(cell2tableCell1Img);
			table.addCell(keyValRow3Cell1);

			Cell keyValRow3Cell2 = new Cell();
			keyValRow3Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow3Cell2.setBorder(Border.NO_BORDER);
			ImageData cell2tableCell2Data = ImageDataFactory.create(Utility.textToImage(certificateDetailList.getDrMarMotherName(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell2tableCell2Img = new Image(cell2tableCell2Data);
			
			float imageWidthRow3Cell2=cell2tableCell2Img.getImageWidth();
			
			if (imageWidthRow3Cell2 > cellWidth) {
				String addressRow3Cell2 = certificateDetailList.getDrMarMotherName();
				int sizeRow3Cell2 = Math.round(addressRow3Cell2.length() / 2f);
				String address1Row3Cell2 = addressRow3Cell2.substring(0, sizeRow3Cell2);
				address1Row3Cell2 = StringUtils.substringBeforeLast(address1Row3Cell2, " ");
				String address2Row3Cell2 = StringUtils.substringAfterLast(addressRow3Cell2, address1Row3Cell2);

				ImageData address1ImgDataRow3Cell2 = ImageDataFactory.create(Utility.textToImage(address1Row3Cell2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address1ImgRow3Cell2 = new Image(address1ImgDataRow3Cell2);

				ImageData address2ImgDataRow3Cell2 = ImageDataFactory.create(Utility.textToImage(address2Row3Cell2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address2ImgRow3Cell2 = new Image(address2ImgDataRow3Cell2);

				address1ImgRow3Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
				address1ImgRow3Cell2.setTextAlignment(TextAlignment.LEFT);
				keyValRow3Cell2.add(address1ImgRow3Cell2);
				keyValRow3Cell2.add(new Paragraph(""));
				address2ImgRow3Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
				address2ImgRow3Cell2.setTextAlignment(TextAlignment.LEFT);
				keyValRow3Cell2.add(address2ImgRow3Cell2);
				
			} else {
				cell2tableCell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				cell2tableCell2Img.setTextAlignment(TextAlignment.LEFT);
				keyValRow3Cell2.add(cell2tableCell2Img);
			}
			table.addCell(keyValRow3Cell2);

			Cell keyValRow3Cell3 = new Cell();
			keyValRow3Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow3Cell3.setBorder(Border.NO_BORDER);
			ImageData cell2tableCell3Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("death.certificate.drMarRelativeName"),MANGAL, TEXT_SIZE_8, false));
			Image cell2tableCell3Img = new Image(cell2tableCell3Data);
			cell2tableCell3Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell2tableCell3Img.setTextAlignment(TextAlignment.LEFT);
			// cell2tableCell3Img.setAutoScale(true);
			keyValRow3Cell3.add(cell2tableCell3Img);
			table.addCell(keyValRow3Cell3);

			Cell keyValRow3Cell4 = new Cell();
			keyValRow3Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow3Cell4.setBorder(Border.NO_BORDER);
			ImageData cell2tableCell4Data = ImageDataFactory.create(Utility.textToImage(certificateDetailList.getDrMarRelativeName(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell2tableCell4Img = new Image(cell2tableCell4Data);
			
			float imageWidthRow3Cell4=cell2tableCell4Img.getImageWidth();
			
			if (imageWidthRow3Cell4 > cellWidth) {
				String addressRow3Cell4 = certificateDetailList.getDrMarRelativeName();
				int sizeRow3Cell4 = Math.round(addressRow3Cell4.length() / 2f);
				String address1Row3Cell4 = addressRow3Cell4.substring(0, sizeRow3Cell4);
				address1Row3Cell4 = StringUtils.substringBeforeLast(address1Row3Cell4, " ");
				String address2Row3Cell4 = StringUtils.substringAfterLast(addressRow3Cell4, address1Row3Cell4);

				ImageData address1ImgDataRow3Cell4 = ImageDataFactory.create(Utility.textToImage(address1Row3Cell4, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address1ImgRow3Cell4 = new Image(address1ImgDataRow3Cell4);

				ImageData address2ImgDataRow3Cell4 = ImageDataFactory.create(Utility.textToImage(address2Row3Cell4, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address2ImgRow3Cell4 = new Image(address2ImgDataRow3Cell4);

				address1ImgRow3Cell4.setHorizontalAlignment(HorizontalAlignment.LEFT);
				address1ImgRow3Cell4.setTextAlignment(TextAlignment.LEFT);
				keyValRow3Cell4.add(address1ImgRow3Cell4);
				keyValRow3Cell4.add(new Paragraph(""));
				address2ImgRow3Cell4.setHorizontalAlignment(HorizontalAlignment.LEFT);
				address2ImgRow3Cell4.setTextAlignment(TextAlignment.LEFT);
				keyValRow3Cell4.add(address2ImgRow3Cell4);
				
			} else {
				cell2tableCell4Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				cell2tableCell4Img.setTextAlignment(TextAlignment.LEFT);
				keyValRow3Cell4.add(cell2tableCell4Img);
			}
			table.addCell(keyValRow3Cell4);

			Cell keyValRow4Cell1 = new Cell();
			keyValRow4Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow4Cell1.setBorder(Border.NO_BORDER);
			Paragraph motherNameEng = new Paragraph(ApplicationSession.getInstance().getMessage("death.certificate.drMotherName"));
			motherNameEng.setHorizontalAlignment(HorizontalAlignment.LEFT);
			motherNameEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			motherNameEng.setFontSize(TEXT_SIZE_8);
			motherNameEng.setMarginLeft(5f);
			// cell3tableCell1Img.setAutoScale(true);
			keyValRow4Cell1.add(motherNameEng);
			table.addCell(keyValRow4Cell1);

			Cell keyValRow4Cell2 = new Cell();
			keyValRow4Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow4Cell2.setBorder(Border.NO_BORDER);
			ImageData cell3tableCell2Data = ImageDataFactory.create(Utility.textToImage(certificateDetailList.getDrMotherName(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell3tableCell2Img = new Image(cell3tableCell2Data);
			
			float imageWidthRow4Cell2=cell3tableCell2Img.getImageWidth();
			
			if (imageWidthRow4Cell2 > cellWidth) {
				String addressRow4Cell2 = certificateDetailList.getDrMotherName();
				int sizeRow4Cell2 = Math.round(addressRow4Cell2.length() / 2f);
				String address1Row4Cell2 = addressRow4Cell2.substring(0, sizeRow4Cell2);
				address1Row4Cell2 = StringUtils.substringBeforeLast(address1Row4Cell2, " ");
				String address2Row4Cell2 = StringUtils.substringAfterLast(addressRow4Cell2, address1Row4Cell2);

				ImageData address1ImgDataRow4Cell2 = ImageDataFactory.create(Utility.textToImage(address1Row4Cell2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address1ImgRow4Cell2 = new Image(address1ImgDataRow4Cell2);

				ImageData address2ImgDataRow4Cell2 = ImageDataFactory.create(Utility.textToImage(address2Row4Cell2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address2ImgRow4Cell2 = new Image(address2ImgDataRow4Cell2);

				address1ImgRow4Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
				address1ImgRow4Cell2.setTextAlignment(TextAlignment.LEFT);
				keyValRow4Cell2.add(address1ImgRow4Cell2);
				keyValRow4Cell2.add(new Paragraph(""));
				address2ImgRow4Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
				address2ImgRow4Cell2.setTextAlignment(TextAlignment.LEFT);
				keyValRow4Cell2.add(address2ImgRow4Cell2);
				
			} else {
				cell3tableCell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				cell3tableCell2Img.setTextAlignment(TextAlignment.LEFT);
				keyValRow4Cell2.add(cell3tableCell2Img);
			}
			table.addCell(keyValRow4Cell2);

			Cell keyValRow4Cell3 = new Cell();
			keyValRow4Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow4Cell3.setBorder(Border.NO_BORDER);
			Paragraph fatherNameEng = new Paragraph(ApplicationSession.getInstance().getMessage("death.certificate.drRelativeName"));
			fatherNameEng.setHorizontalAlignment(HorizontalAlignment.LEFT);
			fatherNameEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			fatherNameEng.setFontSize(TEXT_SIZE_8);
			keyValRow4Cell3.add(fatherNameEng);
			table.addCell(keyValRow4Cell3);

			Cell keyValRow4Cell4 = new Cell();
			keyValRow4Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow4Cell4.setBorder(Border.NO_BORDER);
			ImageData cell3tableCell4Data = ImageDataFactory.create(Utility.textToImage(certificateDetailList.getDrRelativeName(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell3tableCell4Img = new Image(cell3tableCell4Data);
			
			float imageWidthRow4Cell4=cell3tableCell4Img.getImageWidth();
			
			if (imageWidthRow4Cell4 > cellWidth) {
				String addressRow4Cell4 = certificateDetailList.getDrRelativeName();
				int sizeRow4Cell4 = Math.round(addressRow4Cell4.length() / 2f);
				String address1Row4Cell4 = addressRow4Cell4.substring(0, sizeRow4Cell4);
				address1Row4Cell4 = StringUtils.substringBeforeLast(address1Row4Cell4, " ");
				String address2Row4Cell4 = StringUtils.substringAfterLast(addressRow4Cell4, address1Row4Cell4);

				ImageData address1ImgDataRow4Cell4 = ImageDataFactory.create(Utility.textToImage(address1Row4Cell4, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address1ImgRow4Cell4 = new Image(address1ImgDataRow4Cell4);

				ImageData address2ImgDataRow4Cell4 = ImageDataFactory.create(Utility.textToImage(address2Row4Cell4, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address2ImgRow4Cell4 = new Image(address2ImgDataRow4Cell4);

				address1ImgRow4Cell4.setHorizontalAlignment(HorizontalAlignment.LEFT);
				address1ImgRow4Cell4.setTextAlignment(TextAlignment.LEFT);
				keyValRow4Cell4.add(address1ImgRow4Cell4);
				keyValRow4Cell4.add(new Paragraph(""));
				address2ImgRow4Cell4.setHorizontalAlignment(HorizontalAlignment.LEFT);
				address2ImgRow4Cell4.setTextAlignment(TextAlignment.LEFT);
				keyValRow4Cell4.add(address2ImgRow4Cell4);
				
			} else {
				cell3tableCell4Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				cell3tableCell4Img.setTextAlignment(TextAlignment.LEFT);
				keyValRow4Cell4.add(cell3tableCell4Img);
			}
			table.addCell(keyValRow4Cell4);

			Cell keyValRow5Cell1 = new Cell();
			keyValRow5Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow5Cell1.setBorder(Border.NO_BORDER);
			ImageData cell6tableCell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("death.certificate.drDcaddrAtdeathMar"),MANGAL, TEXT_SIZE_8, false));
			Image cell6tableCell1Img = new Image(cell6tableCell1Data);
			cell6tableCell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell6tableCell1Img.setTextAlignment(TextAlignment.LEFT);
			cell6tableCell1Img.setMarginLeft(5f);
			// cell6tableCell1Img.setAutoScale(true);
			keyValRow5Cell1.add(cell6tableCell1Img);
			table.addCell(keyValRow5Cell1);

			Cell keyValRow5Cell2 = new Cell();
			keyValRow5Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow5Cell2.setBorder(Border.NO_BORDER);
			ImageData cell6tableCell2Data = ImageDataFactory.create(Utility.textToImage(certificateDetailList.getDrDcaddrAtdeathMar(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell6tableCell2Img = new Image(cell6tableCell2Data);
			
			float imageWidthRow5Cell2=cell6tableCell2Img.getImageWidth();
			
			if (imageWidthRow5Cell2 > cellWidth) {
				String addressRow5Cell2 = certificateDetailList.getDrDcaddrAtdeathMar();
				int sizeRow5Cell2 = Math.round(addressRow5Cell2.length() / 2f);
				String address1Row5Cell2 = addressRow5Cell2.substring(0, sizeRow5Cell2);
				address1Row5Cell2 = StringUtils.substringBeforeLast(address1Row5Cell2, " ");
				String address2Row5Cell2 = StringUtils.substringAfterLast(addressRow5Cell2, address1Row5Cell2);

				ImageData address1ImgDataRow5Cell2 = ImageDataFactory.create(Utility.textToImage(address1Row5Cell2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address1ImgRow5Cell2 = new Image(address1ImgDataRow5Cell2);

				ImageData address2ImgDataRow5Cell2 = ImageDataFactory.create(Utility.textToImage(address2Row5Cell2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address2ImgRow5Cell2 = new Image(address2ImgDataRow5Cell2);

				if (address1ImgRow5Cell2.getImageWidth() > cellWidth) {
					String[] strRow5Cell2 = new String[2];
					strRow5Cell2 = divideCell(address1Row5Cell2);

					ImageData address1aImgDataRow5Cell2 = ImageDataFactory.create(Utility.textToImage(strRow5Cell2[0], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address1aImgRow5Cell2 = new Image(address1aImgDataRow5Cell2);

					ImageData address2aImgDataRow5Cell2 = ImageDataFactory.create(Utility.textToImage(strRow5Cell2[1], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address2aImgRow5Cell2 = new Image(address2aImgDataRow5Cell2);

					keyValRow5Cell2.add(address1aImgRow5Cell2);
					keyValRow5Cell2.add(new Paragraph(" "));
					keyValRow5Cell2.add(address2aImgRow5Cell2);
				} else {
					address1ImgRow5Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
					address1ImgRow5Cell2.setTextAlignment(TextAlignment.LEFT);
					keyValRow5Cell2.add(address1ImgRow5Cell2);
					keyValRow5Cell2.add(new Paragraph(""));
				}

				if (address2ImgRow5Cell2.getImageWidth() > cellWidth) {
					String[] strRow5Cell2 = new String[2];
					strRow5Cell2 = divideCell(address2Row5Cell2);

					ImageData address1bImgDataRow5Cell2 = ImageDataFactory.create(Utility.textToImage(strRow5Cell2[0], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address1bImgRow5Cell2 = new Image(address1bImgDataRow5Cell2);

					ImageData address2bImgDataRow5Cell2 = ImageDataFactory.create(Utility.textToImage(strRow5Cell2[1], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address2bImgRow5Cell2 = new Image(address2bImgDataRow5Cell2);

					keyValRow5Cell2.add(address1bImgRow5Cell2);
					keyValRow5Cell2.add(new Paragraph(" "));
					keyValRow5Cell2.add(address2bImgRow5Cell2);
				} else {
					address2ImgRow5Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
					address2ImgRow5Cell2.setTextAlignment(TextAlignment.LEFT);
					keyValRow5Cell2.add(address2ImgRow5Cell2);
				}
			} else {
				cell6tableCell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				cell6tableCell2Img.setTextAlignment(TextAlignment.LEFT);
				keyValRow5Cell2.add(cell6tableCell2Img);
			}
			table.addCell(keyValRow5Cell2);

			Cell keyValRow5Cell3 = new Cell();
			keyValRow5Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow5Cell3.setBorder(Border.NO_BORDER);
			ImageData cell6tableCell3Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("death.certificate.drMarDeceasedaddr"),MANGAL, TEXT_SIZE_8, false));
			Image cell6tableCell3Img = new Image(cell6tableCell3Data);
			cell6tableCell3Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell6tableCell3Img.setTextAlignment(TextAlignment.LEFT);
			// cell6tableCell3Img.setAutoScale(true);
			keyValRow5Cell3.add(cell6tableCell3Img);
			table.addCell(keyValRow5Cell3);

			Cell keyValRow5Cell4 = new Cell();
			keyValRow5Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow5Cell4.setBorder(Border.NO_BORDER);
			ImageData cell6tableCell4Data = ImageDataFactory.create(Utility.textToImage(certificateDetailList.getDrMarDeceasedaddr(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell6tableCell4Img = new Image(cell6tableCell4Data);
		
			float imageWidthRow5Cell4=cell6tableCell4Img.getImageWidth();
			
			if (imageWidthRow5Cell4 > cellWidth) {
				String addressRow5Cell4 = certificateDetailList.getDrMarDeceasedaddr();
				int sizeRow5Cell4 = Math.round(addressRow5Cell4.length() / 2f);
				String address1Row5Cell4 = addressRow5Cell4.substring(0, sizeRow5Cell4);
				address1Row5Cell4 = StringUtils.substringBeforeLast(address1Row5Cell4, " ");
				String address2Row5Cell4 = StringUtils.substringAfterLast(addressRow5Cell4, address1Row5Cell4);

				ImageData address1ImgDataRow5Cell4 = ImageDataFactory.create(Utility.textToImage(address1Row5Cell4, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address1ImgRow5Cell4 = new Image(address1ImgDataRow5Cell4);

				ImageData address2ImgDataRow5Cell4 = ImageDataFactory.create(Utility.textToImage(address2Row5Cell4, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address2ImgRow5Cell4 = new Image(address2ImgDataRow5Cell4);

				if (address1ImgRow5Cell4.getImageWidth() > cellWidth) {
					String[] strRow5Cell4 = new String[2];
					strRow5Cell4 = divideCell(address1Row5Cell4);

					ImageData address1aImgDataRow5Cell4 = ImageDataFactory.create(Utility.textToImage(strRow5Cell4[0], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address1aImgRow5Cell4 = new Image(address1aImgDataRow5Cell4);

					ImageData address2aImgDataRow5Cell4 = ImageDataFactory.create(Utility.textToImage(strRow5Cell4[1], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address2aImgRow5Cell4 = new Image(address2aImgDataRow5Cell4);

					keyValRow5Cell4.add(address1aImgRow5Cell4);
					keyValRow5Cell4.add(new Paragraph(" "));
					keyValRow5Cell4.add(address2aImgRow5Cell4);
				} else {
					address1ImgRow5Cell4.setHorizontalAlignment(HorizontalAlignment.LEFT);
					address1ImgRow5Cell4.setTextAlignment(TextAlignment.LEFT);
					keyValRow5Cell4.add(address1ImgRow5Cell4);
					keyValRow5Cell4.add(new Paragraph(""));
				}

				if (address2ImgRow5Cell4.getImageWidth() > cellWidth) {
					String[] strRow5Cell4 = new String[2];
					strRow5Cell4 = divideCell(address2Row5Cell4);

					ImageData address1bImgDataRow5Cell4 = ImageDataFactory.create(Utility.textToImage(strRow5Cell4[0], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address1bImgRow5Cell4 = new Image(address1bImgDataRow5Cell4);

					ImageData address2bImgDataRow5Cell4 = ImageDataFactory.create(Utility.textToImage(strRow5Cell4[1], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address2bImgRow5Cell4 = new Image(address2bImgDataRow5Cell4);

					keyValRow5Cell4.add(address1bImgRow5Cell4);
					keyValRow5Cell4.add(new Paragraph(" "));
					keyValRow5Cell4.add(address2bImgRow5Cell4);
				} else {
					address2ImgRow5Cell4.setHorizontalAlignment(HorizontalAlignment.LEFT);
					address2ImgRow5Cell4.setTextAlignment(TextAlignment.LEFT);
					keyValRow5Cell4.add(address2ImgRow5Cell4);
				}
			} else {
				cell6tableCell4Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				cell6tableCell4Img.setTextAlignment(TextAlignment.LEFT);
				keyValRow5Cell4.add(cell6tableCell4Img);
			}
			table.addCell(keyValRow5Cell4);

			Cell keyValRow6Cell1 = new Cell();
			keyValRow6Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow6Cell1.setBorder(Border.NO_BORDER);
			Paragraph addressEng = new Paragraph(ApplicationSession.getInstance().getMessage("death.certificate.drDcaddrAtdeath"));
			addressEng.setHorizontalAlignment(HorizontalAlignment.LEFT);
			addressEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			addressEng.setFontSize(TEXT_SIZE_8);
			addressEng.setMarginLeft(5f);
			keyValRow6Cell1.add(addressEng);
			table.addCell(keyValRow6Cell1);
			
			Cell keyValRow6Cell2 = new Cell();
			keyValRow6Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow6Cell2.setBorder(Border.NO_BORDER);
			ImageData cell7tableCell2Data = ImageDataFactory.create(Utility.textToImage(certificateDetailList.getDrDcaddrAtdeath(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell7tableCell2Img = new Image(cell7tableCell2Data);
			
			float imageWidthRow6Cell2=cell7tableCell2Img.getImageWidth();
			
			if (imageWidthRow6Cell2 > cellWidth) {
				String addressRow6Cell2 = certificateDetailList.getDrDcaddrAtdeath();
				int sizeRow6Cell2 = Math.round(addressRow6Cell2.length() / 2f);
				String address1Row6Cell2 = addressRow6Cell2.substring(0, sizeRow6Cell2);
				address1Row6Cell2 = StringUtils.substringBeforeLast(address1Row6Cell2, " ");
				String address2Row6Cell2 = StringUtils.substringAfterLast(addressRow6Cell2, address1Row6Cell2);

				ImageData address1ImgDataRow6Cell2 = ImageDataFactory.create(Utility.textToImage(address1Row6Cell2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address1ImgRow6Cell2 = new Image(address1ImgDataRow6Cell2);

				ImageData address2ImgDataRow6Cell2 = ImageDataFactory.create(Utility.textToImage(address2Row6Cell2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address2ImgRow6Cell2 = new Image(address2ImgDataRow6Cell2);

				if (address1ImgRow6Cell2.getImageWidth() > cellWidth) {
					String[] strRow6Cell2 = new String[2];
					strRow6Cell2 = divideCell(address1Row6Cell2);

					ImageData address1aImgDataRow6Cell2 = ImageDataFactory.create(Utility.textToImage(strRow6Cell2[0], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address1aImgRow6Cell2 = new Image(address1aImgDataRow6Cell2);

					ImageData address2aImgDataRow6Cell2 = ImageDataFactory.create(Utility.textToImage(strRow6Cell2[1], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address2aImgRow6Cell2 = new Image(address2aImgDataRow6Cell2);

					keyValRow6Cell2.add(address1aImgRow6Cell2);
					keyValRow6Cell2.add(new Paragraph(" "));
					keyValRow6Cell2.add(address2aImgRow6Cell2);
				} else {
					address1ImgRow6Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
					address1ImgRow6Cell2.setTextAlignment(TextAlignment.LEFT);
					keyValRow6Cell2.add(address1ImgRow6Cell2);
					keyValRow6Cell2.add(new Paragraph(""));
				}

				if (address2ImgRow6Cell2.getImageWidth() > cellWidth) {
					String[] strRow6Cell2 = new String[2];
					strRow6Cell2 = divideCell(address2Row6Cell2);

					ImageData address1bImgDataRow6Cell2 = ImageDataFactory.create(Utility.textToImage(strRow6Cell2[0], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address1bImgRow6Cell2 = new Image(address1bImgDataRow6Cell2);

					ImageData address2bImgDataRow6Cell2 = ImageDataFactory.create(Utility.textToImage(strRow6Cell2[1], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address2bImgRow6Cell2 = new Image(address2bImgDataRow6Cell2);

					keyValRow6Cell2.add(address1bImgRow6Cell2);
					keyValRow6Cell2.add(new Paragraph(" "));
					keyValRow6Cell2.add(address2bImgRow6Cell2);
				} else {
					address2ImgRow6Cell2.setHorizontalAlignment(HorizontalAlignment.LEFT);
					address2ImgRow6Cell2.setTextAlignment(TextAlignment.LEFT);
					keyValRow6Cell2.add(address2ImgRow6Cell2);
				}
			} else {
				cell7tableCell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				cell7tableCell2Img.setTextAlignment(TextAlignment.LEFT);
				keyValRow6Cell2.add(cell7tableCell2Img);
			}
			table.addCell(keyValRow6Cell2);

			Cell keyValRow6Cell3 = new Cell();
			keyValRow6Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow6Cell3.setBorder(Border.NO_BORDER);
			Paragraph addEng = new Paragraph(ApplicationSession.getInstance().getMessage("death.certificate.drDeceasedaddr"));
			addEng.setHorizontalAlignment(HorizontalAlignment.LEFT);
			addEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			addEng.setFontSize(TEXT_SIZE_8);
			keyValRow6Cell3.add(addEng);
			table.addCell(keyValRow6Cell3);

			Cell keyValRow6Cell4 = new Cell();
			keyValRow6Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow6Cell4.setBorder(Border.NO_BORDER);
			ImageData cell7tableCell4Data = ImageDataFactory.create(Utility.textToImage(certificateDetailList.getDrDeathaddr(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell7tableCell4Img = new Image(cell7tableCell4Data);
			
			float imageWidth=cell7tableCell4Img.getImageWidth();
			
			if(imageWidth>cellWidth) {
				String address=certificateDetailList.getDrDeathaddr();
				int size =Math.round(address.length()/2f);
				String address1=address.substring(0, size);
				address1=StringUtils.substringBeforeLast(address1, " ");
				String address2=StringUtils.substringAfterLast(address, address1);
				
				ImageData address1ImgData = ImageDataFactory.create(Utility.textToImage(address1, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address1Img = new Image(address1ImgData);
				
				ImageData address2ImgData = ImageDataFactory.create(Utility.textToImage(address2, ARIALUNICODE, TEXT_SIZE_8, false));
				Image address2Img = new Image(address2ImgData);
				
				if(address1Img.getImageWidth()>cellWidth) {
				 String[] str =  new String[2];
				 str=divideCell(address1);
					
					ImageData address1aImgData = ImageDataFactory.create(Utility.textToImage(str[0], ARIALUNICODE,TEXT_SIZE_8, false));
					Image address1aImg = new Image(address1aImgData);
					
					ImageData address2aImgData = ImageDataFactory.create(Utility.textToImage(str[1], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address2aImg = new Image(address2aImgData);
					
					keyValRow6Cell4.add(address1aImg);
					keyValRow6Cell4.add(new Paragraph(" "));
					keyValRow6Cell4.add(address2aImg);
				}
				else {
					address1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
					address1Img.setTextAlignment(TextAlignment.LEFT);
					keyValRow6Cell4.add(address1Img);
					keyValRow6Cell4.add(new Paragraph(""));
				}
				
				if(address2Img.getImageWidth()>cellWidth) {
					 String[] str =  new String[2];
					 str=divideCell(address2);
					
					ImageData address1bImgData = ImageDataFactory.create(Utility.textToImage(str[0], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address1bImg = new Image(address1bImgData);
					
					ImageData address2bImgData = ImageDataFactory.create(Utility.textToImage(str[1], ARIALUNICODE, TEXT_SIZE_8, false));
					Image address2bImg = new Image(address2bImgData);
					
					keyValRow6Cell4.add(address1bImg);
					keyValRow6Cell4.add(new Paragraph(" "));
					keyValRow6Cell4.add(address2bImg);
				}
				else {
					address2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
					address2Img.setTextAlignment(TextAlignment.LEFT);
				    keyValRow6Cell4.add(address2Img);
				}
			}
			else {
				cell7tableCell4Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
				cell7tableCell4Img.setTextAlignment(TextAlignment.LEFT);
				keyValRow6Cell4.add(cell7tableCell4Img);
			}
			table.addCell(keyValRow6Cell4);

			Cell keyValRow7Cell1 = new Cell();
			keyValRow7Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow7Cell1.setBorder(Border.NO_BORDER);
			ImageData cell8tableCell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("death.certificate.drRegnoMar"),MANGAL, TEXT_SIZE_8, false));
			Image cell8tableCell1Img = new Image(cell8tableCell1Data);
			cell8tableCell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell8tableCell1Img.setTextAlignment(TextAlignment.LEFT);
			cell8tableCell1Img.setMarginLeft(5f);
			// cell8tableCell1Img.setAutoScale(true);
			keyValRow7Cell1.add(cell8tableCell1Img);
			table.addCell(keyValRow7Cell1);

			Cell keyValRow7Cell2 = new Cell();
			keyValRow7Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow7Cell2.setBorder(Border.NO_BORDER);
			ImageData cell8tableCell2Data = ImageDataFactory.create(Utility.textToImage(Utility.convertToRegional("marathi", certificateDetailList.getDrRegno()), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell8tableCell2Img = new Image(cell8tableCell2Data);
			cell8tableCell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell8tableCell2Img.setTextAlignment(TextAlignment.LEFT);
			// cell8tableCell2Img.setAutoScale(true);
			keyValRow7Cell2.add(cell8tableCell2Img);
			table.addCell(keyValRow7Cell2);

			Cell keyValRow7Cell3 = new Cell();
			keyValRow7Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow7Cell3.setBorder(Border.NO_BORDER);
			ImageData cell8tableCell3Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("death.certificate.drRegdateMar"),MANGAL, TEXT_SIZE_8, false));
			Image cell8tableCell3Img = new Image(cell8tableCell3Data);
			cell8tableCell3Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell8tableCell3Img.setTextAlignment(TextAlignment.LEFT);
			// cell8tableCell3Img.setAutoScale(true);
			keyValRow7Cell3.add(cell8tableCell3Img);
			table.addCell(keyValRow7Cell3);

			Cell keyValRow7Cell4 = new Cell();
			keyValRow7Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow7Cell4.setBorder(Border.NO_BORDER);
			ImageData cell8tableCell4Data = ImageDataFactory.create(Utility.textToImage(Utility.convertToRegional("marathi", certificateDetailList.getAppDateOfRegistration()), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell8tableCell4Img = new Image(cell8tableCell4Data);
			cell8tableCell4Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell8tableCell4Img.setTextAlignment(TextAlignment.LEFT);
			// cell8tableCell4Img.setAutoScale(true);
			keyValRow7Cell4.add(cell8tableCell4Img);
			table.addCell(keyValRow7Cell4);

			Cell keyValRow8Cell1 = new Cell();
			keyValRow8Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow8Cell1.setBorder(Border.NO_BORDER);
			Paragraph regNoEng = new Paragraph(ApplicationSession.getInstance().getMessage("death.certificate.drRegno"));
			regNoEng.setHorizontalAlignment(HorizontalAlignment.LEFT);
			regNoEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			regNoEng.setFontSize(TEXT_SIZE_8);
			regNoEng.setMarginLeft(5f);
			keyValRow8Cell1.add(regNoEng);
			table.addCell(keyValRow8Cell1);

			Cell keyValRow8Cell2 = new Cell();
			keyValRow8Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow8Cell2.setBorder(Border.NO_BORDER);
			ImageData cell9tableCell2Data = ImageDataFactory.create(Utility.textToImage(certificateDetailList.getDrRegno(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell9tableCell2Img = new Image(cell9tableCell2Data);
			cell9tableCell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell9tableCell2Img.setTextAlignment(TextAlignment.LEFT);
			// cell9tableCell2Img.setAutoScale(true);
			keyValRow8Cell2.add(cell9tableCell2Img);
			table.addCell(keyValRow8Cell2);

			Cell keyValRow8Cell3 = new Cell();
			keyValRow8Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow8Cell3.setBorder(Border.NO_BORDER);
			Paragraph regDateEng = new Paragraph(ApplicationSession.getInstance().getMessage("death.certificate.drRegdate"));
			regDateEng.setHorizontalAlignment(HorizontalAlignment.LEFT);
			regDateEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			regDateEng.setFontSize(TEXT_SIZE_8);
			keyValRow8Cell3.add(regDateEng);
			table.addCell(keyValRow8Cell3);

			Cell keyValRow8Cell4 = new Cell();
			keyValRow8Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow8Cell4.setBorder(Border.NO_BORDER);
			ImageData cell9tableCell4Data = ImageDataFactory.create(Utility.textToImage(certificateDetailList.getAppDateOfRegistration(), ARIALUNICODE, TEXT_SIZE_9, false));
			Image cell9tableCell4Img = new Image(cell9tableCell4Data);
			cell9tableCell4Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell9tableCell4Img.setTextAlignment(TextAlignment.LEFT);
			// cell9tableCell4Img.setAutoScale(true);
			keyValRow8Cell4.add(cell9tableCell4Img);
			table.addCell(keyValRow8Cell4);

			Cell keyValRow9Cell1 = new Cell();
			keyValRow9Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow9Cell1.setBorder(Border.NO_BORDER);
			ImageData cell10tableCell1Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("death.certificate.remarkReg"),MANGAL, TEXT_SIZE_8, false));
			Image cell10tableCell1Img = new Image(cell10tableCell1Data);
			cell10tableCell1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell10tableCell1Img.setTextAlignment(TextAlignment.LEFT);
			cell10tableCell1Img.setMarginLeft(5f);
			// cell10tableCell1Img.setAutoScale(true);
			keyValRow9Cell1.add(cell10tableCell1Img);
			table.addCell(keyValRow9Cell1);

			Cell keyValRow9Cell2 = new Cell();
			keyValRow9Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow9Cell2.setBorder(Border.NO_BORDER);
			ImageData cell10tableCell2Data = ImageDataFactory.create(Utility.textToImage(certificateDetailList.getAuthRemark(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell10tableCell2Img = new Image(cell10tableCell2Data);
			cell10tableCell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell10tableCell2Img.setTextAlignment(TextAlignment.LEFT);
			// cell10tableCell2Img.setAutoScale(true);
			keyValRow9Cell2.add(cell10tableCell2Img);
			table.addCell(keyValRow9Cell2);

			Cell keyValRow9Cell3 = new Cell();
			keyValRow9Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow9Cell3.setBorder(Border.NO_BORDER);
			ImageData cell10tableCell3Data = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("death.certificate.signMar"),MANGAL, TEXT_SIZE_8, false));
			Image cell10tableCell3Img = new Image(cell10tableCell3Data);
			cell10tableCell3Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell10tableCell3Img.setTextAlignment(TextAlignment.LEFT);
			// cell10tableCell3Img.setAutoScale(true);
			keyValRow9Cell3.add(cell10tableCell3Img);
			table.addCell(keyValRow9Cell3);

			Cell keyValRow9Cell4 = new Cell();
			keyValRow9Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow9Cell4.setBorder(Border.NO_BORDER);
			ImageData cell10tableCell4Data = ImageDataFactory.create(Utility.textToImage("", ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell10tableCell4Img = new Image(cell10tableCell4Data);
			cell10tableCell4Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell10tableCell4Img.setTextAlignment(TextAlignment.LEFT);
			// cell10tableCell4Img.setAutoScale(true);
			keyValRow9Cell4.add(cell10tableCell4Img);
			table.addCell(keyValRow9Cell4);

			Cell keyValRow10Cell1 = new Cell();
			keyValRow10Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow10Cell1.setBorder(Border.NO_BORDER);
			Paragraph remarkEng = new Paragraph(ApplicationSession.getInstance().getMessage("death.certificate.remark"));
			remarkEng.setHorizontalAlignment(HorizontalAlignment.LEFT);
			remarkEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			remarkEng.setFontSize(TEXT_SIZE_8);
			remarkEng.setMarginLeft(5f);
			// cell11tableCell1Img.setAutoScale(true);
			keyValRow10Cell1.add(remarkEng);
			table.addCell(keyValRow10Cell1);

			Cell keyValRow10Cell2 = new Cell();
			keyValRow10Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow10Cell2.setBorder(Border.NO_BORDER);
			ImageData cell11tableCell2Data = ImageDataFactory.create(Utility.textToImage(certificateDetailList.getAuthRemark(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell11tableCell2Img = new Image(cell11tableCell2Data);
			cell11tableCell2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell11tableCell2Img.setTextAlignment(TextAlignment.LEFT);
			// cell11tableCell2Img.setAutoScale(true);
			keyValRow10Cell2.add(cell11tableCell2Img);
			table.addCell(keyValRow10Cell2);

			Cell keyValRow10Cell3 = new Cell();
			keyValRow10Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow10Cell3.setBorder(Border.NO_BORDER);
			Paragraph signDateEng = new Paragraph(ApplicationSession.getInstance().getMessage("death.certificate.sign"));
			signDateEng.setHorizontalAlignment(HorizontalAlignment.LEFT);
			signDateEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
			signDateEng.setFontSize(TEXT_SIZE_8);
			keyValRow10Cell3.add(signDateEng);
			table.addCell(keyValRow10Cell3);

			Cell keyValRow10Cell4 = new Cell();
			keyValRow10Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow10Cell4.setBorder(Border.NO_BORDER);
			ImageData cell11tableCell4Data = ImageDataFactory.create(Utility.textToImage("", ARIALUNICODE, TEXT_SIZE_8, false));
			Image cell11tableCell4Img = new Image(cell11tableCell4Data);
			cell11tableCell4Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
			cell11tableCell4Img.setTextAlignment(TextAlignment.LEFT);
			// cell11tableCell4Img.setAutoScale(true);
			keyValRow10Cell4.add(cell11tableCell4Img);
			table.addCell(keyValRow10Cell4);
			
			table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
			
			Cell keyValRow13Cell1 = new Cell();
			keyValRow13Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow13Cell1.setBorder(Border.NO_BORDER);
			ImageData newDateMarK = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("death.certificate.todayDateMar"),MANGAL, TEXT_SIZE_8, false));
			Image newDateMarKImg = new Image(newDateMarK);
			newDateMarKImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			newDateMarKImg.setTextAlignment(TextAlignment.LEFT);
			newDateMarKImg.setMarginLeft(5f);
			// cell10tableCell1Img.setAutoScale(true);
			keyValRow13Cell1.add(newDateMarKImg);
			table.addCell(keyValRow13Cell1);

			Cell keyValRow13Cell2 = new Cell();
			keyValRow13Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow13Cell2.setBorder(Border.NO_BORDER);
			ImageData newDateMarV = ImageDataFactory.create(Utility.textToImage(Utility.convertToRegional("marathi", certificateDetailList.getNewDate()), ARIALUNICODE, TEXT_SIZE_8, false));
			Image newDateMarVImg = new Image(newDateMarV);
			newDateMarVImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			newDateMarVImg.setTextAlignment(TextAlignment.LEFT);
			// cell10tableCell2Img.setAutoScale(true);
			keyValRow13Cell2.add(newDateMarVImg);
			table.addCell(keyValRow13Cell2);

			Cell keyValRow13Cell3 = new Cell();
			keyValRow13Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow13Cell3.setBorder(Border.NO_BORDER);
			ImageData addressMarK = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("death.certificate.addressMar"),MANGAL, TEXT_SIZE_8, false));
			Image addressMarKImg = new Image(addressMarK);
			addressMarKImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			addressMarKImg.setTextAlignment(TextAlignment.LEFT);
			// cell10tableCell3Img.setAutoScale(true);
			keyValRow13Cell3.add(addressMarKImg);
			table.addCell(keyValRow13Cell3);

			Cell keyValRow13Cell4 = new Cell();
			keyValRow13Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow13Cell4.setBorder(Border.NO_BORDER);
			ImageData addressMarV = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("death.certificate.add.headOffice"), ARIALUNICODE, TEXT_SIZE_8, false));
			Image addressMarVImg = new Image(addressMarV);
			addressMarVImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			addressMarVImg.setTextAlignment(TextAlignment.LEFT);
			// cell10tableCell4Img.setAutoScale(true);
			keyValRow13Cell4.add(addressMarVImg);
			table.addCell(keyValRow13Cell4);

			Cell keyValRow14Cell1 = new Cell();
			keyValRow14Cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow14Cell1.setBorder(Border.NO_BORDER);
			Paragraph newDateK = new Paragraph(ApplicationSession.getInstance().getMessage("death.certificate.todayDate"));
			newDateK.setHorizontalAlignment(HorizontalAlignment.LEFT);
			newDateK.setVerticalAlignment(VerticalAlignment.MIDDLE);
			newDateK.setFontSize(TEXT_SIZE_8);
			newDateK.setMarginLeft(5f);
			// cell11tableCell1Img.setAutoScale(true);
			keyValRow14Cell1.add(newDateK);
			table.addCell(keyValRow14Cell1);

			Cell keyValRow14Cell2 = new Cell();
			keyValRow14Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow14Cell2.setBorder(Border.NO_BORDER);
			ImageData newDateV = ImageDataFactory.create(Utility.textToImage(certificateDetailList.getNewDate(), ARIALUNICODE, TEXT_SIZE_8, false));
			Image newDateVImg = new Image(newDateV);
			newDateVImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			newDateVImg.setTextAlignment(TextAlignment.LEFT);
			// cell11tableCell2Img.setAutoScale(true);
			keyValRow14Cell2.add(newDateVImg);
			table.addCell(keyValRow14Cell2);

			Cell keyValRow14Cell3 = new Cell();
			keyValRow14Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow14Cell3.setBorder(Border.NO_BORDER);
			Paragraph addressK = new Paragraph(ApplicationSession.getInstance().getMessage("death.certificate.address"));
			addressK.setHorizontalAlignment(HorizontalAlignment.LEFT);
			addressK.setVerticalAlignment(VerticalAlignment.MIDDLE);
			addressK.setFontSize(TEXT_SIZE_8);
			keyValRow14Cell3.add(addressK);
			table.addCell(keyValRow14Cell3);

			Cell keyValRow14Cell4 = new Cell();
			keyValRow14Cell4.setVerticalAlignment(VerticalAlignment.MIDDLE);
			keyValRow14Cell4.setBorder(Border.NO_BORDER);
			ImageData addressV = ImageDataFactory.create(Utility.textToImage("HEAD OFFICE", ARIALUNICODE, TEXT_SIZE_8, false));
			Image addressVImg = new Image(addressV);
			addressVImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
			addressVImg.setTextAlignment(TextAlignment.LEFT);
			// cell11tableCell4Img.setAutoScale(true);
			keyValRow14Cell4.add(addressVImg);
			table.addCell(keyValRow14Cell4);
			
			Cell keyValRow16Cell1 = new Cell(1,4);
			keyValRow16Cell1.setHorizontalAlignment(HorizontalAlignment.CENTER);
			keyValRow16Cell1.setHeight(50f);
			keyValRow16Cell1.setWidth(40f);
			keyValRow16Cell1.setBorder(Border.NO_BORDER);
			Table table1 = new Table(1);
			table1.setHorizontalAlignment(HorizontalAlignment.CENTER);
			Cell keyValRow16aCell1 = new Cell();
			keyValRow16aCell1.setHorizontalAlignment(HorizontalAlignment.CENTER);
			keyValRow16aCell1.setHeight(100f);
			keyValRow16aCell1.setWidth(60f);
			ImageData add = ImageDataFactory.create(Utility.textToImage(ApplicationSession.getInstance().getMessage("birth.certificateparaSeal"), ARIALUNICODE, TEXT_SIZE_10, false));
			Image addImg = new Image(add);
			//Paragraph add = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificateparaSeal"));
			addImg.setHorizontalAlignment(HorizontalAlignment.CENTER);
			keyValRow16aCell1.add(addImg);
			table1.addCell(keyValRow16aCell1);
			keyValRow16Cell1.add(table1);
			table.addCell(keyValRow16Cell1);
			
			table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
			table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
			
			Cell keyValParaaCell1 = new Cell(1,2);
			keyValParaaCell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
			//keyValParaaCell1.setBorder(Border.NO_BORDER);
			keyValParaaCell1.setBackgroundColor(ColorConstants.BLACK);
			keyValParaaCell1.setFontColor(ColorConstants.WHITE);
			ImageData orgNameDataa = ImageDataFactory.create(Utility.textToImageColor(ApplicationSession.getInstance().getMessage("birth.certificatepara9"), ARIALUNICODE, 10f, false));
			Image orgNameImga = new Image(orgNameDataa);
			keyValParaaCell1.add(orgNameImga.setHorizontalAlignment(HorizontalAlignment.LEFT));
			table.addCell(keyValParaaCell1);
			
			Cell keyValParaaCell3 = new Cell(1,2);
			keyValParaaCell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
			//keyValParaaCell3.setBorder(Border.NO_BORDER);
			keyValParaaCell3.setBackgroundColor(ColorConstants.BLACK);
			keyValParaaCell3.setFontColor(ColorConstants.WHITE);
			Paragraph para3a = new Paragraph(ApplicationSession.getInstance().getMessage("birth.certificatepara10"));
			para3a.setHorizontalAlignment(HorizontalAlignment.RIGHT);
			para3a.setVerticalAlignment(VerticalAlignment.MIDDLE);
			para3a.setTextAlignment(TextAlignment.RIGHT);
			para3a.setFontSize(TEXT_SIZE_10);
			// cell5tableCell1Img.setAutoScale(true);
			keyValParaaCell3.add(para3a);
			table.addCell(keyValParaaCell3);
		
			document.add(table);
			document.close();
			writer.close();
			return true;
		} catch (Exception e) {
			 LOGGER.info("Exception Occur" + e);
			return false;
		}
	}
	
	 public String[] divideCell(String address) {
		 final String[] str =  new String[2];
		 int size1 =Math.round(address.length()/2f);
		 str[0]=address.substring(0, size1);
		 str[0]=StringUtils.substringBeforeLast(str[0], " ");
		 str[1]=StringUtils.substringAfterLast(address, str[0]);
		return str;
		 
	 }
	
}

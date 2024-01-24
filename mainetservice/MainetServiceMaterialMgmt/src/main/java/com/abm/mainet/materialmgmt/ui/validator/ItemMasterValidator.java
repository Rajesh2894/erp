package com.abm.mainet.materialmgmt.ui.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindingResult;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.materialmgmt.dto.ItemMasterUploadDTO;
import com.abm.mainet.materialmgmt.ui.model.ItemMasterDTO;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants;

public class ItemMasterValidator {

    public int count = 0;

    public List<ItemMasterUploadDTO> excelValidation(
            final List<ItemMasterUploadDTO> itemMasterUploadDtos,
            final BindingResult bindingResult, List<LookUp> itemMasterUoMLookUp, List<LookUp> itemMasterGroupLookup, List<LookUp> itemMasterSubGroupLookup,
            List<LookUp> itemTypeMap,List<LookUp> itemMasterCategoryList,List<LookUp> itemMasterValuationMethodsLookUp,List<LookUp> itemMasterMgmtMethodsLookUp,List<LookUp> itemMasterExpiryMethodsLookUp,List<LookUp> itemMasterassetOfMasList) {
        int rowNo = 0;
        final List<ItemMasterUploadDTO> itemMasterList = new ArrayList<>();

        final ApplicationSession session = ApplicationSession.getInstance();

        Set<ItemMasterUploadDTO> itemMasterExport = itemMasterUploadDtos.stream()
                .filter(dto -> Collections.frequency(itemMasterUploadDtos, dto) > 1)
                .collect(Collectors.toSet());

        if (itemMasterExport.isEmpty()) {

            for (ItemMasterUploadDTO itemMasterUploadDTO : itemMasterUploadDtos) {

            	ItemMasterUploadDTO dto = new ItemMasterUploadDTO();
                rowNo++;
                int itemNameCount = 0;
                if (itemMasterUploadDTO.getName() == null) {
                	itemNameCount++;
                } else {
                    dto.setName(itemMasterUploadDTO.getName());
                }
                if (itemNameCount != 0) {
                    bindingResult.addError(
                            new org.springframework.validation.FieldError(MainetMMConstants.MmItemMaster.ITEM_MASTER,
                                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                    session.getMessage("itemmaster.excel.item.name") + rowNo));
                    break;
                }

                
                int minStockLevelCount = 0;
                if (itemMasterUploadDTO.getMinLevel() == null) {
                	minStockLevelCount++;
                } else {
                    dto.setMinLevel(itemMasterUploadDTO.getMinLevel());
                }
                if (minStockLevelCount != 0) {
                    bindingResult.addError(
                            new org.springframework.validation.FieldError(MainetMMConstants.MmItemMaster.ITEM_MASTER,
                                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                    session.getMessage("itemmaster.excel.minstocklevel") + rowNo));
                    break;
                }
                
                int reOrderQuantityCount = 0;
                if (itemMasterUploadDTO.getReorderLevel() == null) {
                	reOrderQuantityCount++;
                } else {
                    dto.setReorderLevel(itemMasterUploadDTO.getReorderLevel());
                }
                if (reOrderQuantityCount != 0) {
                    bindingResult.addError(
                            new org.springframework.validation.FieldError(MainetMMConstants.MmItemMaster.ITEM_MASTER,
                                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                    session.getMessage("itemmaster.excel.reorderquantitylevel") + rowNo));
                    break;
                }
                
                
                int hsnCodeCount = 0;
                if (itemMasterUploadDTO.getHsnCode() == null) {
                	hsnCodeCount++;
                } else {
                    dto.setHsnCode(itemMasterUploadDTO.getHsnCode());
                }
                if (hsnCodeCount != 0) {
                    bindingResult.addError(
                            new org.springframework.validation.FieldError(MainetMMConstants.MmItemMaster.ITEM_MASTER,
                                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                    session.getMessage("itemmaster.excel.hsncode") + rowNo));
                    break;
                }
                
                
                int taxPerCount = 0;
                if (itemMasterUploadDTO.getTaxPercentage() == null) {
                	taxPerCount++;
                } else {
                    dto.setTaxPercentage(itemMasterUploadDTO.getTaxPercentage());
                }
                if (taxPerCount != 0) {
                    bindingResult.addError(
                            new org.springframework.validation.FieldError(MainetMMConstants.MmItemMaster.ITEM_MASTER,
                                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                    session.getMessage("itemmaster.excel.taxpercent") + rowNo));
                    break;
                }
                 
                
                int baseUOMExist = 0;
                for (LookUp list : itemMasterUoMLookUp) {
                    String baseUom = itemMasterUploadDTO.getUom().trim();
                    if (baseUom.equalsIgnoreCase(list.getLookUpDesc().trim())) {
                        dto.setUom(String.valueOf(list.getLookUpId()));
                        baseUOMExist++;
                    }

                }
                if (baseUOMExist == 0) {

                    bindingResult.addError(new org.springframework.validation.FieldError(
                    		MainetMMConstants.MmItemMaster.ITEM_MASTER, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null,
                            rowNo + session.getMessage("itemmaster.excel.uom")));
                    break;
                }
                
                int itemGroupExist = 0;
                for (LookUp list : itemMasterGroupLookup) {
                    String itemGroup = itemMasterUploadDTO.getItemGroup().trim();
                    if (itemGroup.equalsIgnoreCase(list.getLookUpDesc().trim())) {
                        dto.setItemGroup(String.valueOf(list.getLookUpId()));
                        itemGroupExist++;
                    }

                }
                if (itemGroupExist == 0) {

                    bindingResult.addError(new org.springframework.validation.FieldError(
                    		MainetMMConstants.MmItemMaster.ITEM_MASTER, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null,
                            rowNo + session.getMessage("itemmaster.excel.itemgroup")));
                    break;
                }
                
                int itemSubGroupExist = 0;
                for (LookUp list : itemMasterSubGroupLookup) {
                    String itemSubGroup = itemMasterUploadDTO.getItemSubGroup().trim();
                    if (itemSubGroup.equalsIgnoreCase(list.getLookUpDesc().trim())) {
                        dto.setItemSubGroup(String.valueOf(list.getLookUpId()));
                        itemSubGroupExist++;
                    }
                }
                if (itemSubGroupExist == 0) {

                    bindingResult.addError(new org.springframework.validation.FieldError(
                    		MainetMMConstants.MmItemMaster.ITEM_MASTER, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null,
                            rowNo + session.getMessage("itemmaster.excel.itemsubgroup")));
                    break;
                }
                
                int itemTypeExist = 0;
                for (LookUp list : itemTypeMap) {
                    String itemType = itemMasterUploadDTO.getType().trim();
                    if (itemType.equalsIgnoreCase(list.getLookUpDesc().trim())) {
                        dto.setType(String.valueOf(list.getLookUpId()));
                        itemTypeExist++;
                    }
                }
                if (itemTypeExist == 0) {

                    bindingResult.addError(new org.springframework.validation.FieldError(
                    		MainetMMConstants.MmItemMaster.ITEM_MASTER, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null,
                            rowNo + session.getMessage("itemmaster.excel.itemtype")));
                    break;
                }
                
                int itemCategoryExist = 0;
                for (LookUp list : itemMasterCategoryList) {
                    String itemCategory = itemMasterUploadDTO.getCategory().trim();
                    if (itemCategory.equalsIgnoreCase(list.getLookUpDesc().trim())) {
                        dto.setCategory(String.valueOf(list.getLookUpId()));
                        itemCategoryExist++;
                    }
                }
                if (itemCategoryExist == 0) {

                    bindingResult.addError(new org.springframework.validation.FieldError(
                    		MainetMMConstants.MmItemMaster.ITEM_MASTER, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null,
                            rowNo + session.getMessage("itemmaster.excel.itemcategory")));
                    break;
                }
                int itemMasterValMethodExist = 0;
                for (LookUp list : itemMasterValuationMethodsLookUp) {
                    String itemValuationMethod = itemMasterUploadDTO.getValueMethod().trim();
                    if (itemValuationMethod.equalsIgnoreCase(list.getLookUpDesc().trim())) {
                        dto.setValueMethod(String.valueOf(list.getLookUpId()));
                        itemMasterValMethodExist++;
                    }
                }
                if (itemMasterValMethodExist == 0) {

                    bindingResult.addError(new org.springframework.validation.FieldError(
                    		MainetMMConstants.MmItemMaster.ITEM_MASTER, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null,
                            rowNo + session.getMessage("itemmaster.excel.itemvalmeth")));
                    break;
                }
                
                int itemMasterMgmtMethodExist = 0;
                for (LookUp list : itemMasterMgmtMethodsLookUp) {
                    String itemMgmtMethod = itemMasterUploadDTO.getManagement().trim();
                    if (itemMgmtMethod.equalsIgnoreCase(list.getLookUpDesc().trim())) {
                        dto.setManagement(String.valueOf(list.getLookUpId()));
                        itemMasterMgmtMethodExist++;
                    }
                }
                if (itemMasterMgmtMethodExist == 0) {

                    bindingResult.addError(new org.springframework.validation.FieldError(
                    		MainetMMConstants.MmItemMaster.ITEM_MASTER, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null,
                            rowNo + session.getMessage("itemmaster.excel.itemmanagement")));
                    break;
                }
                
                if(itemMasterUploadDTO.getIsAsset().trim().equals(MainetMMConstants.MmItemMaster.ASSET)) {
                	
                	   int itemMasterAssetClassificationExist = 0;
                       for (LookUp list : itemMasterassetOfMasList) {
                           String itemClassification = itemMasterUploadDTO.getClassification().trim();
                           if (itemClassification.equalsIgnoreCase(list.getLookUpDesc().trim())) {
                               dto.setClassification(String.valueOf(list.getLookUpId()));
                               itemMasterAssetClassificationExist++;
                           }
                       }
                       if (itemMasterAssetClassificationExist == 0) {

                           bindingResult.addError(new org.springframework.validation.FieldError(
                           		MainetMMConstants.MmItemMaster.ITEM_MASTER, MainetConstants.BLANK,
                                   null, false, new String[] { MainetConstants.ERRORS }, null,
                                   rowNo + session.getMessage("itemmaster.excel.itemclassification")));
                           break;
                       }
                       
                	
                }
                
    
                
                if(itemMasterUploadDTO.getIsExpiry().trim().equals(MainetMMConstants.MmItemMaster.EXPIRY)) {
                	
              	   int itemMasterExpiryTypeExist = 0;
                     for (LookUp list : itemMasterExpiryMethodsLookUp) {
                         String itemMasterExpiry = itemMasterUploadDTO.getClassification().trim();
                         if (itemMasterExpiry.equalsIgnoreCase(list.getLookUpDesc().trim())) {
                             dto.setExpiryType(String.valueOf(list.getLookUpId()));
                             itemMasterExpiryTypeExist++;
                         }
                     }
                     if (itemMasterExpiryTypeExist == 0) {

                         bindingResult.addError(new org.springframework.validation.FieldError(
                         		MainetMMConstants.MmItemMaster.ITEM_MASTER, MainetConstants.BLANK,
                                 null, false, new String[] { MainetConstants.ERRORS }, null,
                                 rowNo + session.getMessage("itemmaster.excel.itemexpiry")));
                         break;
                     }
                     
              	
              }
                
                itemMasterList.add(dto);
            }
        }

        else {
            count++;
            bindingResult.addError(new org.springframework.validation.FieldError(
            		MainetMMConstants.MmItemMaster.MAIN_ENTITY_NAME, MainetConstants.BLANK, null, false,
                    new String[] { MainetConstants.ERRORS }, null,
                    session.getMessage("itemmaster.excel.duplicate")));
        }
        return itemMasterList;
    }

	public void inputValidation(final ItemMasterDTO itemMasterDto, final BindingResult bindingResult) {
		
        final ApplicationSession session = ApplicationSession.getInstance();

		
		if (null != itemMasterDto && StringUtils.isEmpty(itemMasterDto.getName())) {
			bindingResult.addError(new org.springframework.validation.FieldError(
					MainetMMConstants.MmItemMaster.MAIN_ENTITY_NAME, MainetConstants.BLANK, null, false,
					new String[] { MainetConstants.ERRORS }, null, session.getMessage("itemmaster.name")));
		}
		if (null != itemMasterDto && itemMasterDto.getUom() == null) {
			bindingResult.addError(new org.springframework.validation.FieldError(
					MainetMMConstants.MmItemMaster.MAIN_ENTITY_NAME, MainetConstants.BLANK, null, false,
					new String[] { MainetConstants.ERRORS }, null, session.getMessage("itemmaster.uom")));
		}
		
		if (null != itemMasterDto && itemMasterDto.getCategory() == null) {
			bindingResult.addError(new org.springframework.validation.FieldError(
					MainetMMConstants.MmItemMaster.MAIN_ENTITY_NAME, MainetConstants.BLANK, null, false,
					new String[] { MainetConstants.ERRORS }, null, session.getMessage("itemmaster.category")));
		}
		
		if (null != itemMasterDto && itemMasterDto.getItemSubGroup() == null) {
			bindingResult.addError(new org.springframework.validation.FieldError(
					MainetMMConstants.MmItemMaster.MAIN_ENTITY_NAME, MainetConstants.BLANK, null, false,
					new String[] { MainetConstants.ERRORS }, null, session.getMessage("itemmaster.subgroup")));
		}
		
		if (null != itemMasterDto && itemMasterDto.getItemGroup() == null) {
			bindingResult.addError(new org.springframework.validation.FieldError(
					MainetMMConstants.MmItemMaster.MAIN_ENTITY_NAME, MainetConstants.BLANK, null, false,
					new String[] { MainetConstants.ERRORS }, null, session.getMessage("itemmaster.group")));
		}
		if (null != itemMasterDto && itemMasterDto.getHsnCode() == null) {
			bindingResult.addError(new org.springframework.validation.FieldError(
					MainetMMConstants.MmItemMaster.MAIN_ENTITY_NAME, MainetConstants.BLANK, null, false,
					new String[] { MainetConstants.ERRORS }, null, session.getMessage("itemmaster.hsncode")));
		}
		
		if (null != itemMasterDto && itemMasterDto.getMinLevel() == null) {
			bindingResult.addError(new org.springframework.validation.FieldError(
					MainetMMConstants.MmItemMaster.MAIN_ENTITY_NAME, MainetConstants.BLANK, null, false,
					new String[] { MainetConstants.ERRORS }, null, session.getMessage("itemmaster.minlevel")));
		}
		
		if (null != itemMasterDto && itemMasterDto.getReorderLevel() == null) {
			bindingResult.addError(new org.springframework.validation.FieldError(
					MainetMMConstants.MmItemMaster.MAIN_ENTITY_NAME, MainetConstants.BLANK, null, false,
					new String[] { MainetConstants.ERRORS }, null, session.getMessage("itemmaster.reorderlevel")));
		}
		
		if (null != itemMasterDto && null!=itemMasterDto.getIsAsset() && itemMasterDto.getIsAsset().equals(MainetMMConstants.MmItemMaster.ASSET)) {
			
			 if(itemMasterDto.getClassification() ==null) {
					bindingResult.addError(new org.springframework.validation.FieldError(
							MainetMMConstants.MmItemMaster.MAIN_ENTITY_NAME, MainetConstants.BLANK, null, false,
							new String[] { MainetConstants.ERRORS }, null, session.getMessage("itemmaster.classification"))); 
			 }
		
		}
		
		if (null != itemMasterDto && null!=itemMasterDto.getIsExpiry() && itemMasterDto.getIsExpiry().equals(MainetMMConstants.MmItemMaster.EXPIRY)) {
			
			 if(itemMasterDto.getExpiryType() ==null) {
					bindingResult.addError(new org.springframework.validation.FieldError(
							MainetMMConstants.MmItemMaster.MAIN_ENTITY_NAME, MainetConstants.BLANK, null, false,
							new String[] { MainetConstants.ERRORS }, null, session.getMessage("itemmaster.expirytype"))); 
			 }
		
		}
		
		if (null != itemMasterDto && itemMasterDto.getManagement() == null) {
			bindingResult.addError(new org.springframework.validation.FieldError(
					MainetMMConstants.MmItemMaster.MAIN_ENTITY_NAME, MainetConstants.BLANK, null, false,
					new String[] { MainetConstants.ERRORS }, null, session.getMessage("itemmaster.management")));
		}
		
		if (null != itemMasterDto && itemMasterDto.getValueMethod() == null) {
			bindingResult.addError(new org.springframework.validation.FieldError(
					MainetMMConstants.MmItemMaster.MAIN_ENTITY_NAME, MainetConstants.BLANK, null, false,
					new String[] { MainetConstants.ERRORS }, null, session.getMessage("itemmaster.valuemethod")));
		}
		
	}

}

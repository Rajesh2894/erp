package com.abm.mainet.tradeLicense.dto;

public class TradeMasterCategoryDto {

    private TradeMasterDetailDTO tradeMasterDetailDTO = new TradeMasterDetailDTO();
    private TradeMasterDetailDTO tradeDTO = new TradeMasterDetailDTO();

    public TradeMasterDetailDTO getTradeMasterDetailDTO() {
        return tradeMasterDetailDTO;
    }

    public TradeMasterDetailDTO getTradeDTO() {
        return tradeDTO;
    }

    public void setTradeMasterDetailDTO(TradeMasterDetailDTO tradeMasterDetailDTO) {
        this.tradeMasterDetailDTO = tradeMasterDetailDTO;
    }

    public void setTradeDTO(TradeMasterDetailDTO tradeDTO) {
        this.tradeDTO = tradeDTO;
    }

}

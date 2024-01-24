package com.abm.mainet.common.master.service;

import java.util.List;

import com.abm.mainet.common.master.dto.BookReceiptDTO;

public interface BookReceiptService {

    BookReceiptDTO saveBookReceiptFormData(BookReceiptDTO bookReceiptDTO, Long orgId, String ipMac);

    List<BookReceiptDTO> allReceiptBookDataByOrgId(Long orgId);

    List<BookReceiptDTO> getReceiptBookData(Long empId, Long fayearId, Long orgId);

}

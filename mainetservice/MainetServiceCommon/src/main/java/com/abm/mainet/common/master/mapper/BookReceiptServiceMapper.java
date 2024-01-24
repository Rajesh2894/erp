package com.abm.mainet.common.master.mapper;

import org.springframework.beans.BeanUtils;

import com.abm.mainet.common.domain.BookReceiptEntity;
import com.abm.mainet.common.master.dto.BookReceiptDTO;

public class BookReceiptServiceMapper {

    public static BookReceiptEntity mapBookReceiptDTOToEntity(BookReceiptDTO bookReceiptDTO) {

        BookReceiptEntity bookReceiptEntity = new BookReceiptEntity();

        BeanUtils.copyProperties(bookReceiptDTO, bookReceiptEntity);

        return bookReceiptEntity;
    }

    public static BookReceiptDTO mapBookReceiptEntityToDTO(BookReceiptEntity bookReceiptEntity) {

        BookReceiptDTO bookReceiptDTO = new BookReceiptDTO();
        BeanUtils.copyProperties(bookReceiptEntity, bookReceiptDTO);

        return bookReceiptDTO;
    }

}

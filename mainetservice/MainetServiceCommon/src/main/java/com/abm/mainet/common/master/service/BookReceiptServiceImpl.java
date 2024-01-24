package com.abm.mainet.common.master.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.BookReceiptEntity;
import com.abm.mainet.common.domain.ReceiptBookHistoryEntity;
import com.abm.mainet.common.master.dto.BookReceiptDTO;
import com.abm.mainet.common.master.mapper.BookReceiptServiceMapper;
import com.abm.mainet.common.master.repository.ReceiptBookJpaRepository;

@Service
public class BookReceiptServiceImpl implements BookReceiptService {

    @Autowired
    ReceiptBookJpaRepository receiptBookJpaRepository;

    @Autowired
    private AuditService auditService;

    @Override
    @Transactional
    public BookReceiptDTO saveBookReceiptFormData(BookReceiptDTO bookReceiptDTO, Long userId, String ipMac) {

        bookReceiptDTO.setCreatedDate(new Date());
        bookReceiptDTO.setCreatedBy(userId);
        bookReceiptDTO.setIpMac(ipMac);

        BookReceiptEntity bookReceiptEntity = new BookReceiptEntity();

        bookReceiptEntity = BookReceiptServiceMapper.mapBookReceiptDTOToEntity(bookReceiptDTO);

        if (bookReceiptDTO.getRbId() == 0) {

            BookReceiptEntity saveBookReceiptEntity = receiptBookJpaRepository.save(bookReceiptEntity);
            ReceiptBookHistoryEntity receiptBookHistoryEntity = new ReceiptBookHistoryEntity();
            receiptBookHistoryEntity.setIsActive(MainetConstants.InsertMode.ADD.getStatus());
            auditService.createHistory(saveBookReceiptEntity, receiptBookHistoryEntity);

        } else {
            bookReceiptEntity.setIpMacUpdated(ipMac);
            bookReceiptEntity.setUpdatedBy(userId);
            bookReceiptEntity.setUpdatedDate(new Date());
            bookReceiptEntity.setRbId(bookReceiptDTO.getRbId());
            BookReceiptEntity updatebookReceiptEntity = receiptBookJpaRepository.save(bookReceiptEntity);

            ReceiptBookHistoryEntity receiptBookHistoryEntity = new ReceiptBookHistoryEntity();
            receiptBookHistoryEntity.setIsActive(MainetConstants.InsertMode.UPDATE.getStatus());
            auditService.createHistory(updatebookReceiptEntity, receiptBookHistoryEntity);

        }

        return bookReceiptDTO;

    }

    @Override
    @Transactional

    public List<BookReceiptDTO> allReceiptBookDataByOrgId(Long orgId) {
        final List<BookReceiptDTO> receiptBookDtoList = new ArrayList<>();

        receiptBookJpaRepository.getAllReceiptBookData(orgId).forEach(listObj -> {

            BookReceiptDTO bookReceiptDTO = new BookReceiptDTO();
            if (listObj[0] != null) {
                bookReceiptDTO.setRbId(listObj[0] == null ? null : (Long) listObj[0]);
                bookReceiptDTO.setBookreceiptNo(listObj[1] == null ? null : (Long) listObj[1]);
                bookReceiptDTO.setBookreceiptNoFrom(listObj[2] == null ? null : (Long) listObj[2]);
                bookReceiptDTO.setBookreceiptNoTo(listObj[3] == null ? null : (Long) listObj[3]);
                bookReceiptDTO.setTotalbookReceiptNo(listObj[4] == null ? null : (Long) listObj[4]);
                bookReceiptDTO.setFaYearId(listObj[5] == null ? null : (Long) listObj[5]);
                bookReceiptDTO.setEmpId(listObj[6] == null ? null : (Long) listObj[6]);

            }
            receiptBookDtoList.add(bookReceiptDTO);

        });

        return receiptBookDtoList;
    }

    @Override
    @Transactional
    public List<BookReceiptDTO> getReceiptBookData(Long empId, Long fayearId, Long orgId) {

        final List<BookReceiptDTO> bookReceiptDTOList = new ArrayList<>();
        if ((empId != null) && (fayearId != null)) {

            receiptBookJpaRepository.findReceiptBookData(empId, fayearId, orgId).forEach(Entity -> {

                bookReceiptDTOList.add(BookReceiptServiceMapper.mapBookReceiptEntityToDTO(Entity));

            });

        }

        return bookReceiptDTOList;
    }

}

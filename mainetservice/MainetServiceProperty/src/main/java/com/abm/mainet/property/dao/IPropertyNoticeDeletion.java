package com.abm.mainet.property.dao;

public interface IPropertyNoticeDeletion {

    int validateNoticeForDemand(long noticeNo, long noticeType, long orgId);

    int specialNoticeValidation(long noticeNo, long noticeType, long orgId);

}

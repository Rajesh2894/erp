package com.abm.mainet.lqp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.lqp.domain.QueryAnswerMaster;

@Repository
public interface QueryAnswerRepository extends JpaRepository<QueryAnswerMaster, Long> {

    QueryAnswerMaster findByQueryRegistrationMaster_questionRegId(Long qustnId);

}

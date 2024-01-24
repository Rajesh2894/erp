package com.abm.mainet.intranet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.intranet.domain.Role;
import com.abm.mainet.intranet.domain.RoleName;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}

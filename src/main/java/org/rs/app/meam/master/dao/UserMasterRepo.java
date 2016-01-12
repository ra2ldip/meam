package org.rs.app.meam.master.dao;

import org.rs.app.meam.master.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ra2ldip
 */
public interface UserMasterRepo extends JpaRepository<UserMaster, Integer> {

    UserMaster findByUserNameOrMobileNo(String userName, String mobileNo);
}

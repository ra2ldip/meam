package org.rs.app.master.dao;

import org.rs.app.master.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ra2ldip
 */
public interface UserMasterRepo extends JpaRepository<UserMaster, Integer> {

    UserMaster findByUserNameOrMobileNo(String userName, String mobileNo);
}

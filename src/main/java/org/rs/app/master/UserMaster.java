package org.rs.app.master;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author ra2ldip
 */
@Entity
public class UserMaster implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int userId;
    private String userName;

    @Column(length = 50)
    private String emailId;

    @Column(length = 10)
    private String mobileNo;

    @Column(length = 1)
//    @Type(type = "yes_no")
    private boolean sendEmail;

//    @Type(type = "yes_no")
    private boolean sendSms;

    private String userPass;
    private String firstName;
    private String lastName;
    private boolean active;

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * @param emailId the emailId to set
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    /**
     * @return the mobileNo
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * @param mobileNo the mobileNo to set
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     * @return the sendEmail
     */
    public boolean isSendEmail() {
        return sendEmail;
    }

    /**
     * @param sendEmail the sendEmail to set
     */
    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    /**
     * @return the sendSms
     */
    public boolean isSendSms() {
        return sendSms;
    }

    /**
     * @param sendSms the sendSms to set
     */
    public void setSendSms(boolean sendSms) {
        this.sendSms = sendSms;
    }

    /**
     * @return the userPass
     */
    public String getUserPass() {
        return userPass;
    }

    /**
     * @param userPass the userPass to set
     */
    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}

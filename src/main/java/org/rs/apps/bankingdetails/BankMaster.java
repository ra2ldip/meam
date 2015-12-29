package org.rs.apps.bankingdetails;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author ra2ldip
 */
@Entity
public class BankMaster implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    private String bankName;
    private int type;
    

    public BankMaster() {
    }

    public BankMaster(String bankName, int type) {
        this.bankName = bankName;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

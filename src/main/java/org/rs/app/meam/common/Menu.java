package org.rs.app.meam.common;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author ratul
 */
@Entity
public class Menu implements Serializable {

    public static final int TOP_PARENT = 0;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;
    private String url;
    private long parentId;
    private int mSequence;

    public Menu(long id, String url) {
        this.id = id;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public int getmSequence() {
        return mSequence;
    }

    public void setmSequence(int mSequence) {
        this.mSequence = mSequence;
    }

}

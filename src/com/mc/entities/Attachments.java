package com.mc.entities;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
 
@Entity(name = "attachments")
public class Attachments implements Serializable {
    private static final long serialVersionUID = 1L;
 
    public Attachments() {
        super();
    }
    
    @Id
    private int cnt_no;
    private String insertdate;
    private String org_attachment;
 
    public int getPcnt_no() {
		return cnt_no;
	}

	public void setPcnt_no(int pcnt_no) {
		this.cnt_no = pcnt_no;
	}

	public String getPinsertdate() {
		return insertdate;
	}

	public void setPinsertdate(String insertdate) {
		this.insertdate = insertdate;
	}

	public String getPorg_attachment() {
		return org_attachment;
	}

	public void setPorg_attachment(String org_attachment) {
		this.org_attachment = org_attachment;
	}

	@Override
    public String toString() {
        return "Attachments [counter=" + cnt_no + ", insert date=" + insertdate
                + ", org_attachment=" + org_attachment + "]";
    }
}
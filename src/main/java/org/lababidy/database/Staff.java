package org.lababidy.database;
// Generated Jul 2, 2014 2:35:34 AM by Hibernate Tools 3.6.0

import javax.xml.bind.annotation.XmlRootElement;



/**
 * Staff generated by hbm2java
 */
@XmlRootElement
public class Staff  implements java.io.Serializable {


     private Integer sid;
     private String sname;
     private String spassword;

    public Staff() {
    }

    public Staff(String sname, String spassword) {
       this.sname = sname;
       this.spassword = spassword;
    }
   
    public Integer getSid() {
        return this.sid;
    }
    
    public void setSid(Integer sid) {
        this.sid = sid;
    }
    public String getSname() {
        return this.sname;
    }
    
    public void setSname(String sname) {
        this.sname = sname;
    }
    public String getSpassword() {
        return this.spassword;
    }
    
    public void setSpassword(String spassword) {
        this.spassword = spassword;
    }

    @Override
    public String toString() {
        return sid + "|" + sname + "|" + spassword;
    }


}



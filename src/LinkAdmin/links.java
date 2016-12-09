/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkAdmin;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author MuHamd Gomaa
 */
public class links {
     SimpleIntegerProperty id;
        SimpleStringProperty name;

    SimpleStringProperty link;
    SimpleStringProperty linkname;

    public Integer getId() {
        return id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getLink() {
        return link.get();
    }

    public void setLink(String link) {
        this.link.set(link);
    }

    public String getLinkname() {
        return linkname.get();
    }

    public void setLinkname(String linkname) {
        this.linkname.set(linkname);
    }

    public links(Integer id, String name, String link, String linkname) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.link = new SimpleStringProperty(link);
        this.linkname = new SimpleStringProperty(linkname);
    }
     public links(String name, String link, String linkname) {
        this.name = new SimpleStringProperty(name);
        this.link = new SimpleStringProperty(link);
        this.linkname = new SimpleStringProperty(linkname);
    }

    
}

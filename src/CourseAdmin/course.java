/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CourseAdmin;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author MuHamd Gomaa
 */
public class course {
     
    SimpleIntegerProperty id;
        SimpleIntegerProperty admin_id;

    SimpleStringProperty name;
    SimpleStringProperty time;

    public Integer getAdmin_id() {
        return admin_id.get();
    }

    public void setAdmin_id(Integer admin_id) {
        this.admin_id.set(admin_id);
    }
    SimpleStringProperty finall;
        SimpleStringProperty min_grade;
    SimpleStringProperty description;
        SimpleStringProperty links;

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
                }

    public String getTime() {
        return time.get();
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getFinall() {
        return finall.get();
    }

    public void setFinall(String finall) {
        this.finall.set(finall);
    }

    public String getMin_grade() {
        return min_grade.get();
    }

    public void setMin_grade(String min_grade) {
        this.min_grade.set(min_grade);
    }

    public String getDiscription() {
        return description.get();
    }

    public void setDiscription(String discription) {
        this.description.set(discription);
    }


    public course(Integer id, String name, String time, String finall, String min_grade, String discription, Integer admin_id) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.time = new SimpleStringProperty(time);
        this.finall =new SimpleStringProperty(finall);
        this.min_grade =new SimpleStringProperty(min_grade);
        this.description = new SimpleStringProperty(discription);
        this.admin_id = new SimpleIntegerProperty(admin_id);
    }

public course(Integer id, String name, String time, String finall, String min_grade, String discription) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.time = new SimpleStringProperty(time);
        this.finall =new SimpleStringProperty(finall);
        this.min_grade =new SimpleStringProperty(min_grade);
        this.description = new SimpleStringProperty(discription);
    }



  
      
    
    public Integer getId() {
        return id.get();
    }
    
    public void setId(int id) {
        this.id.set(id);
    }
    
}

    


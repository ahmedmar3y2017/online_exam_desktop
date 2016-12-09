/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuestionAdmin;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author MuHamd Gomaa
 */
public class question {
     SimpleIntegerProperty id ;
   SimpleStringProperty question;
    SimpleStringProperty correct;
    SimpleStringProperty answer;

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getQuestion() {
        return question.get();
    }

    public void setQuestion(String question) {
        this.question.set(question);
    }

    public String getCorrect() {
        return correct.get();
    }

    public void setCorrect(String correct) {
        this.correct.set(correct);
    }

    public String getAnswer() {
        return answer.get();
    }

    public void setAnswer(String answer) {
        this.answer.set(answer);
    }
    
    public question(Integer id,String question,String correct,String answer){
       this.id= new SimpleIntegerProperty(id);
       this.question= new SimpleStringProperty(question);
       this.correct= new SimpleStringProperty(correct);
       this.answer= new SimpleStringProperty(answer);
    }
      public question(String question,String correct,String answer){
       this.question= new SimpleStringProperty(question);
       this.correct= new SimpleStringProperty(correct);
       this.answer= new SimpleStringProperty(answer);
    }
    
    
    
    
}

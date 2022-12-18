package com.example.calcurator;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CalcModel extends RealmObject {

    @PrimaryKey
    private long id;

    private String formula;

    private String answer;

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

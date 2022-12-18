package com.example.calcurator;

public class Parenthesis {

    private Integer start;
    private Integer end;

    public Parenthesis(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Parenthesis{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}

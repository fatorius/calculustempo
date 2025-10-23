package com.hugosouza.maths;

abstract public class Term {

    public static final String POSITIVE = "+";
    public static final String NEGATIVE = "-";

    String signal = POSITIVE;

    abstract public boolean equals(Term term);
    abstract public String toString();

    public String getSignal(){
        return signal;
    }

    public void setSignal(String signal){
        this.signal = signal;
    }

}

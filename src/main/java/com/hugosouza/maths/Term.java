package com.hugosouza.maths;

abstract public class Term {
    public static final String MONOMIAL = "Monomial";
    public static final String FRACTION = "Fraction";

    static String type;
    abstract public boolean equals(Term term);

    public String getType(){
        return type;
    }
}

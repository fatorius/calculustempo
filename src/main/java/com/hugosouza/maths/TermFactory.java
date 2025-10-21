package com.hugosouza.maths;

public class TermFactory {
    public static Term createTerm(String latex){
        return new Monomial(latex);
    }
}

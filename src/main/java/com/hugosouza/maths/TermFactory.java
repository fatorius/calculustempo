package com.hugosouza.maths;

public class TermFactory {
    public static Term createTerm(String latex){
        if (latex.contains("\\frac")){
            return new Fraction(latex);
        }

        if (latex.contains("^")){
            return new Power(latex);
        }


        return new SimpleTerm(latex);
    }
}

package com.hugosouza.maths;

public class Fraction extends Term{
    private Term numerator;
    private Term denominator;

    public Fraction() {
    }

    @Override
    public boolean equals(Term term) {
        if (!term.getType().equals(Term.FRACTION)) {
            return false;
        }

        Fraction fraction = (Fraction) term;

        return numerator.equals(fraction.numerator) && denominator.equals(fraction.denominator);
    }
}

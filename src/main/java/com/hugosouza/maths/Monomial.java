package com.hugosouza.maths;

public class Monomial extends Term{
    private String value;
    private String sign;

    public Monomial(String term){
        this.sign = "+";
        this.value = term;

        if (term.startsWith("-")){
            this.sign = "-";
            this.value = term.substring(1);
        }

        if (term.startsWith("+")){
            this.sign = "+";
            this.value = term.substring(1);
        }

        type = Term.MONOMIAL;
    }

    @Override
    public boolean equals(Term term) {
        if (!term.getType().equals(Term.MONOMIAL)){
            return false;
        }

        Monomial monomial = (Monomial) term;

        return this.value.equals(monomial.value) && this.sign.equals(monomial.sign);
    }
}

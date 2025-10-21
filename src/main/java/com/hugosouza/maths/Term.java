package com.hugosouza.maths;

public class Term {
    private String value;
    private String sign;

    public Term() {
        this.value = "";
        this.sign = "";
    }

    public Term(String term){
        super();

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
    }

    public boolean equals(Term term){
        return this.value.equals(term.value) && this.sign.equals(term.sign);
    }
}

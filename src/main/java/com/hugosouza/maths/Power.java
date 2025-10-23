package com.hugosouza.maths;

public class Power extends Term{
    private final SimpleTerm base;
    private final Expression exp;

    public Power(String latex){
        latex = latex.replaceAll("\\s+", "");

        int caretIndex = latex.indexOf('^');

        this.base = new SimpleTerm(latex.substring(0, caretIndex));
        this.exp = new Expression(latex.substring(caretIndex+1));
    }

    @Override
    public boolean equals(Term term) {
        if (!(term instanceof Power power)) {
            return false;
        }

        return this.base.equals(power.base) && this.exp.equals(power.exp);
    }

    @Override
    public String toString() {
        return base.toString() + "^{" + exp.toString() + "}";
    }
}

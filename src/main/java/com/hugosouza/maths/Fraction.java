package com.hugosouza.maths;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fraction extends Term{
    private final Expression numerator;
    private final Expression denominator;

    public Fraction(String fractionLatex) {
        if (fractionLatex.startsWith("-")){
            this.setSignal(NEGATIVE);
        }

        String[] parts = this.extractLatexFractionParts(fractionLatex);

        assert parts != null;
        this.numerator = new Expression(parts[0]);
        this.denominator = new Expression(parts[1]);
    }

    @Override
    public boolean equals(Term term) {
        if (!(term instanceof Fraction fraction)) {
            return false;
        }

        return numerator.equals(fraction.numerator) && denominator.equals(fraction.denominator) && Objects.equals(signal, term.getSignal());
    }

    @Override
    public String toString() {
        return this.getSignal() + "\\frac{" + numerator.toString() + "}{" + denominator.toString() + "}";
    }

    private String[] extractLatexFractionParts(String latex){
        Pattern pattern = Pattern.compile("\\\\frac\\{([^{}]+)}\\{([^{}]+)}");
        Matcher matcher = pattern.matcher(latex);

        if (matcher.find()) {
            String numerator = matcher.group(1);
            String denominator = matcher.group(2);
            return new String[]{numerator, denominator};
        }

        return null;
    }
}

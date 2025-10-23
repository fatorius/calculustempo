package com.hugosouza.maths;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression extends Term{
    private final List<Term> terms;

    public Expression (String latex){
        this.terms = new ArrayList<>();

        List<String> parts = splitTerms(latex);

        for(String part : parts){
            terms.add(TermFactory.createTerm(part));
        }
    }

    public int getNumberOfTerms(){
        return terms.size();
    }

    private Term getTerm(int index){
        return terms.get(index);
    }

    private List<String> splitTerms(String expression){
        expression = expression.replaceAll("\\s+", "");

        if (!expression.startsWith("+") && !expression.startsWith("-")) {
            expression = "+" + expression;
        }

        Pattern pattern = Pattern.compile("[+-][^+-]+");
        Matcher matcher = pattern.matcher(expression);

        List<String> terms = new ArrayList<>();

        while (matcher.find()) {
            terms.add(matcher.group());
        }

        terms.sort(Comparator.naturalOrder());

        return terms;
    }

    @Override
    public boolean equals(Term term) {
        if (!(term instanceof Expression expression)) {
            if (this.terms.size() == 1){
                return this.terms.get(0).equals(term);
            }

            return false;
        }

        boolean result = true;

        if (this.getNumberOfTerms() != expression.getNumberOfTerms()){
            return false;
        }

        int termNo = 0;
        for (Term innterTerm : this.terms) {
            if (!innterTerm.equals(expression.getTerm(termNo))){
                return false;
            }

            termNo++;
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (Term term : this.terms) {
            str.append(term.toString());
        }

        return str.toString();
    }
}

package com.hugosouza.maths;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression{
    private final List<Term> terms;

    public Expression (String latex){
        this.terms = new ArrayList<>();

        List<String> parts = splitTerms(latex);

        for(String part : parts){
            terms.add(TermFactory.createTerm(part));
        }

    }

    public boolean equals(Expression expression){
        boolean result = true;

        if (this.numberOfTerms() != expression.numberOfTerms()){
            return false;
        }

        int termNo = 0;
        for (Term term : this.terms) {
            if (!term.equals(expression.getTerm(termNo))){
                return false;
            }

            termNo++;
        }

        return result;
    }

    public int numberOfTerms(){
        return terms.size();
    }

    public Term getTerm(int index){
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
}

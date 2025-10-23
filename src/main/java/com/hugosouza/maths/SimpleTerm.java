package com.hugosouza.maths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleTerm extends Term{
    private double coefficient = 1.0;
    private String normalizedVariables = "";

    public SimpleTerm(String value) {
        if (value.startsWith("-")) {
            this.setSignal(NEGATIVE);
            value = value.substring(1);
        } else if (value.startsWith("+")) {
            value = value.substring(1);
        }

        parseValue(value.trim());
    }

    private void parseValue(String value) {
        String raw = value.replaceAll("\\s+", "").replace("*", "");

        Matcher numMatcher = Pattern.compile("[0-9]+(\\.[0-9]+)?").matcher(raw);

        double totalCoeff = 1.0;

        while (numMatcher.find()) {
            double n = Double.parseDouble(numMatcher.group());
            totalCoeff *= n;
        }
        String variablesOnly = raw.replaceAll("[0-9]+(\\.[0-9]+)?", "");

        List<Character> vars = new ArrayList<>();
        for (char c : variablesOnly.toCharArray()) {
            if (Character.isLetter(c)) {
                vars.add(c);
            }
        }

        Collections.sort(vars);

        StringBuilder normalized = new StringBuilder();
        for (char v : vars) {
            normalized.append(v);
        }

        this.coefficient = totalCoeff;
        this.normalizedVariables = normalized.toString();
    }

    @Override
    public boolean equals(Term term) {
        if (!(term instanceof SimpleTerm other)) {
            return false;
        };

        return Objects.equals(signal, other.getSignal()) &&
                Double.compare(this.coefficient, other.coefficient) == 0 &&
                this.normalizedVariables.equals(other.normalizedVariables);
    }

    @Override
    public String toString(){
        return this.getSignal() + this.coefficient + this.normalizedVariables;
    }
}

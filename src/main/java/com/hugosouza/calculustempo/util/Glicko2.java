package com.hugosouza.calculustempo.util;

public class Glicko2 {
    private static final double Q = Math.log(10) / 400.0;
    private static final double SCALE = 173.7178;
    private static final double TAU = 0.5f;

    private static double g(double phi) {
        return 1.0 / Math.sqrt(1.0 + 3.0 * Q * Q * phi * phi / (Math.PI * Math.PI));
    }

    private static double E(double mu, double muOp, double phiOp) {
        return 1.0 / (1.0 + Math.exp(-g(phiOp) * (mu - muOp)));
    }

    public static double[] update(double rating, double rd, double volatility,
                                  double ratingOp, double rdOp, double score) {

        double mu = (rating - 1500.0) / SCALE;
        double phi = rd / SCALE;
        double muOp = (ratingOp - 1500.0) / SCALE;
        double phiOp = rdOp / SCALE;


        double g = g(phiOp);
        double E = E(mu, muOp, phiOp);
        double v = 1.0 / (Q * Q * g * g * E * (1 - E));
        double delta = v * Q * g * (score - E);


        double a = Math.log(volatility * volatility);
        double A = a;
        double B;

        if (delta * delta > (phi * phi + v)) {
            B = Math.log(delta * delta - phi * phi - v);
        } else {
            double k = 1;
            while (f(a - k * TAU, delta, phi, v, a) < 0)
                k++;
            B = a - k * TAU;
        }

        double fA = f(A, delta, phi, v, a);
        double fB = f(B, delta, phi, v, a);

        int iteration = 0;
        while (Math.abs(B - A) > 1e-6 && iteration < 100) {
            double C = A + (A - B) * fA / (fB - fA);
            double fC = f(C, delta, phi, v, a);

            if (fC * fB < 0) {
                A = B;
                fA = fB;
            } else {
                fA /= 2.0;
            }

            B = C;
            fB = fC;
            iteration++;
        }

        double newSigma = Math.exp(A / 2.0);
        
        double phiStar = Math.sqrt(phi * phi + newSigma * newSigma);
        double phiPrime = 1.0 / Math.sqrt((1.0 / (phiStar * phiStar)) + (1.0 / v));
        double muPrime = mu + phiPrime * phiPrime * Q * g * (score - E);


        double ratingPrime = SCALE * muPrime + 1500.0;
        double rdPrime = SCALE * phiPrime;

        return new double[]{ratingPrime, rdPrime, newSigma};
    }

    private static double f(double x, double delta, double phi, double v, double a) {
        double ex = Math.exp(x);
        double num = ex * (delta * delta - phi * phi - v - ex);
        double den = 2.0 * (phi * phi + v + ex) * (phi * phi + v + ex);
        return num / den - (x - a) / (TAU * TAU);
    }
}

package model;

import java.util.Arrays;

public class FonteAlimento {
    private double[] posicao;
    private double fitness;
    private int tentativas;

    public FonteAlimento(double[] posicao, double fitness) {
        this.posicao = posicao;
        this.fitness = fitness;
        this.tentativas = 0;
    }

    public double[] getPosicao() {
        return posicao;
    }

    public void setPosicao(double[] posicao) {
        this.posicao = posicao;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public int getTentativas() {
        return tentativas;
    }

    public void zerarTentativas() {
        this.tentativas = 0;
    }

    public void aumentarTentativas() {
        this.tentativas++;
    }

    @Override
    public String toString() {
        return "Posição: " + Arrays.toString(posicao) +
                " | Fitness: " + fitness +
                " | Tentativas: " + tentativas;
    }
}
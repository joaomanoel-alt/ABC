package util;

public class FuncaoObjetivo {

    // Função Sphere: quanto mais próximo de 0, melhor
    public static double calcular(double[] posicao) {
        double soma = 0;

        for (double valor : posicao) {
            soma += valor * valor;
        }

        return soma;
    }
}
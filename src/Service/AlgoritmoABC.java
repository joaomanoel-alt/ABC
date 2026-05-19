package Service;

import model.FonteAlimento;
import util.FuncaoObjetivo;

import java.util.Random;

public class AlgoritmoABC {

    private FonteAlimento[] fontes;
    private FonteAlimento melhorFonte;

    private final int quantidadeFontes;
    private final int dimensao;
    private final int limiteAbandono;
    private final int maxCiclos;

    private final double minimo;
    private final double maximo;

    private final Random random = new Random();

    public AlgoritmoABC(int tamanhoColonia, int dimensao, int limiteAbandono, int maxCiclos, double minimo, double maximo) {
        this.quantidadeFontes = tamanhoColonia / 2;
        this.dimensao = dimensao;
        this.limiteAbandono = limiteAbandono;
        this.maxCiclos = maxCiclos;
        this.minimo = minimo;
        this.maximo = maximo;

        this.fontes = new FonteAlimento[quantidadeFontes];
    }

    public void executar() {
        inicializarFontes();

        for (int ciclo = 1; ciclo <= maxCiclos; ciclo++) {
            faseEmpregadas();
            faseObservadoras();
            faseExploradoras();
            atualizarMelhorFonte();

            System.out.println("Ciclo " + ciclo + " | Melhor fitness: " + melhorFonte.getFitness());
        }

        System.out.println("\nMelhor solução encontrada:");
        System.out.println(melhorFonte);
    }

    private void inicializarFontes() {
        for (int i = 0; i < quantidadeFontes; i++) {
            double[] posicao = gerarPosicaoAleatoria();
            double fitness = FuncaoObjetivo.calcular(posicao);
            fontes[i] = new FonteAlimento(posicao, fitness);
        }

        melhorFonte = fontes[0];
        atualizarMelhorFonte();
    }

    private void faseEmpregadas() {
        for (int i = 0; i < quantidadeFontes; i++) {
            gerarNovaSolucao(i);
        }
    }

    private void faseObservadoras() {
        double[] probabilidades = calcularProbabilidades();

        for (int i = 0; i < quantidadeFontes; i++) {
            int fonteEscolhida = selecionarPorRoleta(probabilidades);
            gerarNovaSolucao(fonteEscolhida);
        }
    }

    private void faseExploradoras() {
        for (int i = 0; i < quantidadeFontes; i++) {
            if (fontes[i].getTentativas() >= limiteAbandono) {
                double[] novaPosicao = gerarPosicaoAleatoria();
                double novoFitness = FuncaoObjetivo.calcular(novaPosicao);

                fontes[i].setPosicao(novaPosicao);
                fontes[i].setFitness(novoFitness);
                fontes[i].zerarTentativas();
            }
        }
    }

    private void gerarNovaSolucao(int indiceFonte) {
        FonteAlimento fonteAtual = fontes[indiceFonte];

        double[] novaPosicao = fonteAtual.getPosicao().clone();

        int coordenada = random.nextInt(dimensao);

        int k;
        do {
            k = random.nextInt(quantidadeFontes);
        } while (k == indiceFonte);

        double phi = -1 + 2 * random.nextDouble();

        novaPosicao[coordenada] = fonteAtual.getPosicao()[coordenada]
                + phi * (fonteAtual.getPosicao()[coordenada] - fontes[k].getPosicao()[coordenada]);

        novaPosicao[coordenada] = limitarValor(novaPosicao[coordenada]);

        double novoFitness = FuncaoObjetivo.calcular(novaPosicao);

        if (novoFitness < fonteAtual.getFitness()) {
            fonteAtual.setPosicao(novaPosicao);
            fonteAtual.setFitness(novoFitness);
            fonteAtual.zerarTentativas();
        } else {
            fonteAtual.aumentarTentativas();
        }
    }

    private double[] calcularProbabilidades() {
        double[] probabilidades = new double[quantidadeFontes];
        double somaQualidade = 0;

        for (FonteAlimento fonte : fontes) {
            somaQualidade += 1.0 / (1.0 + fonte.getFitness());
        }

        for (int i = 0; i < quantidadeFontes; i++) {
            double qualidade = 1.0 / (1.0 + fontes[i].getFitness());
            probabilidades[i] = qualidade / somaQualidade;
        }

        return probabilidades;
    }

    private int selecionarPorRoleta(double[] probabilidades) {
        double sorteio = random.nextDouble();
        double acumulado = 0;

        for (int i = 0; i < probabilidades.length; i++) {
            acumulado += probabilidades[i];

            if (sorteio <= acumulado) {
                return i;
            }
        }

        return probabilidades.length - 1;
    }

    private double[] gerarPosicaoAleatoria() {
        double[] posicao = new double[dimensao];

        for (int i = 0; i < dimensao; i++) {
            posicao[i] = minimo + (maximo - minimo) * random.nextDouble();
        }

        return posicao;
    }

    private double limitarValor(double valor) {
        if (valor < minimo) {
            return minimo;
        }

        if (valor > maximo) {
            return maximo;
        }

        return valor;
    }

    private void atualizarMelhorFonte() {
        for (FonteAlimento fonte : fontes) {
            if (fonte.getFitness() < melhorFonte.getFitness()) {
                melhorFonte = new FonteAlimento(fonte.getPosicao().clone(), fonte.getFitness());
            }
        }
    }

    public FonteAlimento getMelhorFonte() {
        return melhorFonte;
    }
}
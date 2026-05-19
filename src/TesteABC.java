import Service.AlgoritmoABC;

public class TesteABC {
    public static void main(String[] args) {

        AlgoritmoABC abc = new AlgoritmoABC(
                20,
                5,
                10,
                50,
                -10,
                10
        );

        abc.executar();

        if (abc.getMelhorFonte() != null) {
            System.out.println("\nTeste executado com sucesso!");
        } else {
            System.out.println("\nErro: melhor fonte não foi encontrada.");
        }
    }
}
import Service.AlgoritmoABC;

public class Main {
    public static void main(String[] args) {

        AlgoritmoABC abc = new AlgoritmoABC(
                20,     // tamanho da colônia
                5,      // dimensão do vetor
                10,     // limite de abandono
                50,     // máximo de ciclos
                -10,    // valor mínimo
                10      // valor máximo
        );

        abc.executar();
    }
}
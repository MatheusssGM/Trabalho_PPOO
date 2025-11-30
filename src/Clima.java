import java.util.Random;

public class Clima {
    private static final Random rand = new Random();

    private int temperatura;

    private int altitude;

    private Estacao estacao;

    public Clima(){
        estacao = Estacao.VERAO;
        this.altitude = 100;
        this.temperatura = 28;
    }

    public void atualisar(){

        temperatura+= rand.nextInt(5)-2;

        switch (estacao){
            case VERAO:
                temperatura +=2;
                break;
            case INVERNO:
                temperatura -=4;
                break;
            case OUTONO:
                temperatura -=1;
                break;
            case PRIMAVERA:
                temperatura +=1;
                break;
        }
        if (temperatura < -10) temperatura = 10;
        if (temperatura > 45)  temperatura = 45;
    }
    public void proximaestacao() {
        switch (estacao) {
            case VERAO:
                estacao = Estacao.OUTONO;
                break;
            case OUTONO:
                estacao = Estacao.INVERNO;
                break;
            case INVERNO:
                estacao = Estacao.PRIMAVERA;
                break;
            case PRIMAVERA:
                estacao = Estacao.VERAO;
                break;
        }
    }
}

package cena;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

public class KeyBoard implements KeyListener{
    private Cena cena;

    public KeyBoard(Cena cena){
        this.cena = cena;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);
        switch (e.getKeyChar()) {

        }

        switch (e.getKeyCode()){
            case 151://seta direita
                if (cena.movimentacaoBarra < cena.extremidadeJanela - 150) {
                    cena.movimentacaoBarra += cena.velocidadeMovimentoDaBarra;
                    cena.extremidadeDireitaBarra += cena.velocidadeMovimentoDaBarra;
                    cena.extremidadeEsquerdaBarra = cena.extremidadeDireitaBarra - 300;
                }
                break;
            case 149://seta esquerda
                if (cena.movimentacaoBarra > - cena.extremidadeJanela + 150) {
                    cena.movimentacaoBarra -= cena.velocidadeMovimentoDaBarra;
                    cena.extremidadeDireitaBarra -= cena.velocidadeMovimentoDaBarra;
                    cena.extremidadeEsquerdaBarra = cena.extremidadeDireitaBarra - 300;
                }
                break;
            case 32://barra de espaço

                if (cena.menuGameOver){
                    cena.resetarJogo();
                } else{
                    cena.jogoIniciado=!cena.jogoIniciado;
                    cena.menuPrincipalAtivado=false;
                    cena.menuPausaAtivado=!cena.jogoIniciado;
                }

                break;
//            Tecla c
            case 67:

                if (cena.corSelecionada == 3){
                    cena.corSelecionada = 0;
                } else {
                    cena.corSelecionada++;
                }

                switch (cena.corSelecionada) {
                    case 0:
                        // Cor da Bolinha:
                        cena.vermelhoBolinha = 1f;
                        cena.verdeBolinha = 1f;
                        cena.azulBolinha = 1f;
                        // Cor da Borda:
                        cena.vermelhoBorda = 0.5f;
                        cena.verdeBorda = 0.5f;
                        cena.azulBorda = 0.5f;
                        break;
                    case 1:
                        // Cor da Bolinha:
                        cena.vermelhoBolinha = 0.17f;
                        cena.verdeBolinha = 0.79f;
                        cena.azulBolinha = 1f;
                        // Cor da Borda:
                        cena.vermelhoBorda = 0.40f;
                        cena.verdeBorda = 0.60f;
                        cena.azulBorda = 1f;
                        break;
                    case 2:
                        // Cor da Bolinha:
                        cena.vermelhoBolinha = 0.49f;
                        cena.verdeBolinha = 1f;
                        cena.azulBolinha= 0.96f;
                        // Cor da Borda:
                        cena.vermelhoBorda = 0.50f;
                        cena.verdeBorda = 1f;
                        cena.azulBorda = 0.80f;
                        break;
                    case 3:
                        // Cor da Bolinha:
                        cena.vermelhoBolinha = 1f;
                        cena.verdeBolinha = 0.41f;
                        cena.azulBolinha = 0.41f;
                        // Cor da Borda:
                        cena.vermelhoBorda = 1f;
                        cena.verdeBorda = 0.70f;
                        cena.azulBorda = 0.70f;
                        break;
                }

                break;

            case 77:

                if (!cena.jogoIniciado){
                    cena.menuPausaAtivado = false;
                    cena.menuPrincipalAtivado = true;
                    cena.resetarJogo();
                }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}

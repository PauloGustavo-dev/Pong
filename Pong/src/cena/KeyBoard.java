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
        System.out.println("Key pressed: " + e.getKeyCode());
        System.out.println("Key pressed: " + e.getKeyChar());
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);
        switch (e.getKeyChar()) {

        }

        switch (e.getKeyCode()){
            case 149://seta direita
                cena.movimentacaoBarra-= 50;
                cena.extremidadeDireitaBarra -= 50;
                cena.extremidadeEsquerdaBarra =cena.extremidadeDireitaBarra -(cena.size*6);
                break;
            case 151://seta esquerda
                cena.movimentacaoBarra+= 50;
                cena.extremidadeDireitaBarra += 50;
                cena.extremidadeEsquerdaBarra =cena.extremidadeDireitaBarra -(cena.size*6);
                break;
//            case 150://seta cima
//                break;
//            case 152://seta baixo
//                break;
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
                        // Cor do Fundo:
                        cena.vermelhoFundo = 0.1f;
                        cena.verdeFundo = 0.1f;
                        cena.azulFundo = 0.1f;
                        // Cor do Barra:
                        cena.vermelhoBarra = 0.3f;
                        cena.verdeBarra = 0.3f;
                        cena.azulBarra = 0.3f;
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
                        // Cor do Fundo:
                        cena.vermelhoFundo = 0.20f;
                        cena.verdeFundo = 0.80f;
                        cena.azulFundo = 1f;
                        // Cor do Barra:
                        cena.vermelhoBarra = 0.30f;
                        cena.verdeBarra = 0.60f;
                        cena.azulBarra = 1f;
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
                        // Cor do Fundo:
                        cena.vermelhoFundo = 0.60f;
                        cena.verdeFundo = 1f;
                        cena.azulFundo = 0.90f;
                        // Cor do Barra:
                        cena.vermelhoBarra = 0.30f;
                        cena.verdeBarra = 1f;
                        cena.azulBarra = 0.86f;
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
                        // Cor do Fundo:
                        cena.vermelhoFundo = 1f;
                        cena.verdeFundo = 0.50f;
                        cena.azulFundo = 0.50f;
                        // Cor do Barra:
                        cena.vermelhoBarra = 1f;
                        cena.verdeBarra = 0.60f;
                        cena.azulBarra = 0.60f;
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
        //se a tecla for solta
        System.out.println("Key released: " + e.getKeyCode());
        if (e.getKeyChar() == 'a') { // Tecla a
        }
    }

}

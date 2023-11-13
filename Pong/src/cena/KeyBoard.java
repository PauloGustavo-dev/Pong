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
                        cena.vermelho = 1f;
                        cena.verde = 1f;
                        cena.azul = 1f;
                        break;
                    case 1:
                        cena.vermelho = 0f;
                        cena.verde = 0f;
                        cena.azul = 1f;
                        break;
                    case 2:
                        cena.vermelho = 0f;
                        cena.verde = 1f;
                        cena.azul = 0f;
                        break;
                    case 3:
                        cena.vermelho = 1f;
                        cena.verde = 0f;
                        cena.azul = 0f;
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

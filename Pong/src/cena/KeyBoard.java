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
            case 'a':
                break;
        }

        switch (e.getKeyCode()){
            case 149://seta direita
                cena.movimentacaoBarra-= 40;
                cena.extremidadeDireitaBarra -= 40;
                cena.extremidadeEsquerdaBarra =cena.extremidadeDireitaBarra -(cena.size*6);
                break;
            case 151://seta esquerda
                cena.movimentacaoBarra+= 40;
                cena.extremidadeDireitaBarra += 40;
                cena.extremidadeEsquerdaBarra =cena.extremidadeDireitaBarra -(cena.size*6);
                break;
//            case 150://seta cima
//                break;
//            case 152://seta baixo
//                break;
            case 32://barra de espaço
                cena.play=true;
                break;
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

package cena;


import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.awt.TextRenderer;
import textura.Textura;

import java.util.Random;
import java.awt.Color;
import java.awt.Font;

public class Cena implements GLEventListener{
    private float xMin, xMax, yMin, yMax, zMin, zMax;
    public final float extremidadeJanela = 1000f;
    public float size = 50;
    private TextRenderer textRenderer;
    public float extremidadeDireitaXBolinha = size, extremidadeEsquerdaXBolinha = -size;
    public float extremidadeSuperiorYBolinha = size, extremidadeInferiorYBolinha = -size;
    public float translacaoXBolinha=0, translacaoYBolinha=0;
    public final float velocidadeInicialX = 20f, velocidadeInicialY = 15f;
    public float taxaAtualizacaoX =20f , taxaAtualizacaoY =15f;
    public float movimentacaoBarra=0;
    public float extremidadeDireitaBarra = size*3 , extremidadeEsquerdaBarra =extremidadeDireitaBarra -(size*6);
    public int vidas = 5;
    public int pontuacao = 0;
    public int fase= 1;

    public final float tamanhoInicialObstaculo = 100;
    public float tamanhoObstaculo = tamanhoInicialObstaculo + (20 * (fase-1));

    public int corSelecionada = 0;
    // Variáveis para a Bolinha
    public float vermelhoBolinha = 1;
    public float verdeBolinha = 1;
    public float azulBolinha = 1;

    // Variáveis para a Barra
    public float vermelhoBarra = 1;
    public float verdeBarra = 1;
    public float azulBarra = 1;

    // Variáveis para a Borda
    public float vermelhoBorda = 1;
    public float verdeBorda = 1;
    public float azulBorda = 1;

    public boolean menuPrincipalAtivado = true;
    public boolean jogoIniciado = false;
    public boolean menuPausaAtivado = false;
    public boolean menuGameOver = false;

    //Adicionando Variáveis para textura

    public static final String texturaCoracao = "Pong/src/img_texturas/coração.png";

    public static final String texturaEspaco = "Pong/src/img_texturas/espaço2.jpg";

    public static final String texturaBarra = "Pong/src/img_texturas/nave.png";

    public static final String texturaPropulsor = "Pong/src/img_texturas/propulsor.png";

    // Adicionando Variávies para o filtro da textura
    public int filtro = GL2.GL_LINEAR; ////GL_NEAREST ou GL_LINEAR
    public int wrap = GL2.GL_REPEAT;  //GL.GL_REPEAT ou GL.GL_CLAMP
    public int modo = GL2.GL_DECAL; ////GL.GL_MODULATE ou GL.GL_DECAL ou GL.GL_BLEND

    // Importação da Classe "Textura.java"
    private Textura textura;

    //Criando variável para record
    private int record = 0;

    public int mode;
    private float margemDeErroX;
    private float margemDeErroY;
    public final float velocidadeMovimentoDaBarra = 50;
    private float movimentoFundo =1.5f;
    private float velocidadaAnimacaoDeFundo = 0.001f;
    private float movimentacaoLaser = 0;
    private float velocidadeLaser = 0.05f;
    private final int numeroDeFases = 4;

    private boolean rangeObstaculo = false;

    public void resetarPosicaoInicialBolinha(){
        if (fase>=2){
            translacaoYBolinha = tamanhoObstaculo+ size;
            extremidadeSuperiorYBolinha = translacaoYBolinha + size;
            extremidadeInferiorYBolinha = translacaoYBolinha - size;
            taxaAtualizacaoY = - taxaAtualizacaoY;

            translacaoXBolinha = 0;
            extremidadeDireitaXBolinha = size;

            margemDeErroX=0;
            margemDeErroY=0;
        }else{
            translacaoYBolinha = 0;
            extremidadeSuperiorYBolinha = size;
            extremidadeInferiorYBolinha = - size;
            taxaAtualizacaoY = - taxaAtualizacaoY;

            translacaoXBolinha = 0;
            extremidadeDireitaXBolinha = size;

            margemDeErroX=0;
            margemDeErroY=0;
        }
    }
    public void resetarJogo(){
        resetarPosicaoInicialBolinha();
        taxaAtualizacaoY = velocidadeInicialY;
        taxaAtualizacaoX = velocidadeInicialX;

        movimentacaoBarra=0;
        extremidadeDireitaBarra = size*3;
        extremidadeEsquerdaBarra =extremidadeDireitaBarra -(size*6);

        menuGameOver=false;
        vidas = 5;
        pontuacao = 0;
        fase= 1;
    }
    public void colisaoObstaculo(){
        //criando range onde será analisado margem de erro para colisão 100% com o obstaculo
        if(extremidadeDireitaXBolinha >= -tamanhoObstaculo*3 && extremidadeEsquerdaXBolinha  <= tamanhoObstaculo*3
        && extremidadeInferiorYBolinha >= -tamanhoObstaculo*3 && extremidadeSuperiorYBolinha  <= tamanhoObstaculo*3){
            rangeObstaculo= true;
        }else{rangeObstaculo=false;}

        if(rangeObstaculo){
            if(taxaAtualizacaoX>= 0){
                float pixeisAteObstaculo = (-tamanhoObstaculo/2) - extremidadeDireitaXBolinha;
                float restoMargemDeErro = pixeisAteObstaculo % taxaAtualizacaoX;
                if(restoMargemDeErro != 0){
                    margemDeErroX += 1;
                    extremidadeDireitaXBolinha += 1;
                    extremidadeEsquerdaXBolinha = extremidadeDireitaXBolinha - 100;
                }
            }else{
                float pixeisAteObstaculo = (tamanhoObstaculo/2) - extremidadeEsquerdaXBolinha;
                float restoMargemDeErro = pixeisAteObstaculo % taxaAtualizacaoX;
                if(restoMargemDeErro != 0){
                    margemDeErroX -= 1;
                    extremidadeDireitaXBolinha -= 1;
                    extremidadeEsquerdaXBolinha = extremidadeDireitaXBolinha - 100;
                }
            }
            if(taxaAtualizacaoY>=0){
                float pixeisAteObstaculo = (-tamanhoObstaculo/2) - extremidadeSuperiorYBolinha;
                float restoMargemDeErro = pixeisAteObstaculo % taxaAtualizacaoY;
                if(restoMargemDeErro != 0){
                    margemDeErroY += 1;
                    extremidadeSuperiorYBolinha += 1;
                    extremidadeInferiorYBolinha = extremidadeSuperiorYBolinha - 100;
                }
            } else {
                float pixeisAteBarra = (tamanhoObstaculo/2) - extremidadeInferiorYBolinha;
                float restoMargemDeErro = pixeisAteBarra % taxaAtualizacaoY;
                if(restoMargemDeErro != 0){
                    margemDeErroY -= 1;
                    extremidadeInferiorYBolinha -= 1;
                    extremidadeSuperiorYBolinha = extremidadeInferiorYBolinha + 100;
                }
            }
        }

        if(extremidadeDireitaXBolinha >= -tamanhoObstaculo/2 && extremidadeEsquerdaXBolinha  <= tamanhoObstaculo/2){
            if (extremidadeInferiorYBolinha  <= tamanhoObstaculo/2 && extremidadeInferiorYBolinha  >= tamanhoObstaculo/2 - (tamanhoObstaculo/4) )// parte superior
            {
                //taxa crescente, eixo y
                taxaAtualizacaoY = velocidadeInicialY + (5 * (fase-1));

                Random ran = new Random();
                int aleatorizaAcressimoX = ran.nextInt(11);// valor aleatorio de acrescimo para evitar que obstaculo entre em loop na parte superior

                if (taxaAtualizacaoX<0){
                    taxaAtualizacaoX = -velocidadeInicialX - (5 * (fase-1));
                    taxaAtualizacaoX -= aleatorizaAcressimoX;
                } else {
                    taxaAtualizacaoX = velocidadeInicialX + (5 * (fase - 1));
                    taxaAtualizacaoX += aleatorizaAcressimoX;
                }
            } else if (extremidadeSuperiorYBolinha >= - tamanhoObstaculo/2 && extremidadeSuperiorYBolinha <= - tamanhoObstaculo/2 + (tamanhoObstaculo/4))// parte inferior
            {
                //taxa decrescente, eixo y
                taxaAtualizacaoY = -velocidadeInicialY - (5 * (fase-1));

                if (taxaAtualizacaoX<0){
                    taxaAtualizacaoX = -velocidadeInicialX - (5 * (fase-1));
                } else {
                    taxaAtualizacaoX = velocidadeInicialX + (5 * (fase - 1));
                }
            } else if (extremidadeInferiorYBolinha <= tamanhoObstaculo/2 && extremidadeSuperiorYBolinha >= -tamanhoObstaculo/2)
            {
                taxaAtualizacaoX = - taxaAtualizacaoX;
            }
        }
    }

    public void movimentarBarra(){

        //verifica colisao no eixo y
        if(extremidadeDireitaXBolinha >= extremidadeEsquerdaBarra && extremidadeEsquerdaXBolinha <= extremidadeDireitaBarra){
            if (extremidadeInferiorYBolinha == -800f)
            {
                pontuacao+=50;
                if (fase<=numeroDeFases){fase = (pontuacao/200)+1;}

                //taxa crescente, eixo y
                taxaAtualizacaoY = velocidadeInicialY + (5 * (fase-1));

                Random ran = new Random();
                int aleatorizaAcressimoX = ran.nextInt(6);
                // bolinha continua o curso do eixo x que estava realizando
                if (taxaAtualizacaoX<0){
                    taxaAtualizacaoX = -velocidadeInicialX - (5 * (fase-1));//pode aumentar velocidade a depender da fase
                    taxaAtualizacaoX -= aleatorizaAcressimoX;// acressimo aleatorio para evitar repetição de colisão
                } else {
                    taxaAtualizacaoX = velocidadeInicialX + (5 * (fase - 1));//pode aumentar velocidade a depender da fase
                    taxaAtualizacaoX += aleatorizaAcressimoX;// acressimo aleatorio para evitar repetição de colisão
                }
            }else
            if (extremidadeInferiorYBolinha <= -800f && extremidadeSuperiorYBolinha >= -850f)// parte superior + margem de erro
            {
                pontuacao+=50;

                if (fase<=numeroDeFases){fase = (pontuacao/200)+1;}

                //taxa crescente, eixo y
                taxaAtualizacaoY = velocidadeInicialY + (5 * (fase-1));

                Random ran = new Random();
                int aleatorizaAcressimoX = ran.nextInt(6);
                //se bater na lateral a bolinha vai para o lado oposto em relação ao eixo x
                if (taxaAtualizacaoX < 0){
                    taxaAtualizacaoX = velocidadeInicialX + (5 * (fase-1) );//pode aumentar velocidade a depender da fase
                    taxaAtualizacaoX += aleatorizaAcressimoX;// acressimo aleatorio para evitar repetição de colisão
                } else {
                    taxaAtualizacaoX = -velocidadeInicialX - (5 * (fase - 1));//pode aumentar velocidade a depender da fase
                    taxaAtualizacaoX -= aleatorizaAcressimoX;// acressimo aleatorio para evitar repetição de colisão
                }
            }
        }
    }

    public void movimentarBolinha(){
        if (jogoIniciado && vidas!=0){
            translacaoYBolinha+= taxaAtualizacaoY;//inicia a movimentacao da bolinha no eixo y
            extremidadeSuperiorYBolinha =translacaoYBolinha + size + margemDeErroY;//armazena a extremidade Y com base na translacao e tamanho do objeto( /2 porque a bolinha é iniciada no centro da janela )
            extremidadeInferiorYBolinha =translacaoYBolinha - size + margemDeErroY;

            translacaoXBolinha+= taxaAtualizacaoX;//inicia a movimentacao da bolinha no eixo X
            extremidadeDireitaXBolinha =translacaoXBolinha + size + margemDeErroX;//armazena a extremidade X
            extremidadeEsquerdaXBolinha= translacaoXBolinha - size + margemDeErroX;
            
            if(taxaAtualizacaoX>= 0){
                float pixeisAteParede = extremidadeJanela - extremidadeDireitaXBolinha;
                float restoMargemDeErro = pixeisAteParede % taxaAtualizacaoX;
                if(restoMargemDeErro != 0){
                    margemDeErroX += 1;
                    extremidadeDireitaXBolinha += 1;
                    extremidadeEsquerdaXBolinha = extremidadeDireitaXBolinha - 100;
                }
            }else{
                float pixeisAteParede = - extremidadeJanela + extremidadeEsquerdaXBolinha;
                float restoMargemDeErro = pixeisAteParede % taxaAtualizacaoX;
                if(restoMargemDeErro != 0){
                    margemDeErroX -= 1;
                    extremidadeDireitaXBolinha -= 1;
                    extremidadeEsquerdaXBolinha = extremidadeDireitaXBolinha - 100;
                }
            }
            if(taxaAtualizacaoY>=0){
                float pixeisAteParede = extremidadeJanela - extremidadeSuperiorYBolinha;
                float restoMargemDeErro = pixeisAteParede % taxaAtualizacaoY;
                if(restoMargemDeErro != 0){
                    margemDeErroY += 1;
                    extremidadeSuperiorYBolinha += 1;
                    extremidadeInferiorYBolinha = extremidadeSuperiorYBolinha - 100;
                }
            } else {
                float pixeisAteBarra = (- 800) - extremidadeInferiorYBolinha;
                float restoMargemDeErro = pixeisAteBarra % taxaAtualizacaoY;
                if(restoMargemDeErro != 0){
                    margemDeErroY -= 1;
                    extremidadeInferiorYBolinha -= 1;
                    extremidadeSuperiorYBolinha = extremidadeInferiorYBolinha + 100;
                }
            }

            //verificar colisoes paredes
            if(extremidadeDireitaXBolinha >= extremidadeJanela ){
                taxaAtualizacaoX = - taxaAtualizacaoX;
            } else if(extremidadeEsquerdaXBolinha <= (-extremidadeJanela)){
                taxaAtualizacaoX = - taxaAtualizacaoX;
            }
            //verifica colisões teto/chão
            if(extremidadeSuperiorYBolinha >= extremidadeJanela){
                taxaAtualizacaoY = - taxaAtualizacaoY;
            }else if(extremidadeInferiorYBolinha <= - extremidadeJanela ){
                vidas-=1;
                //resetando valores iniciaais
                resetarPosicaoInicialBolinha();
            }
        }
    }
    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena
        GL2 gl = drawable.getGL().getGL2();
        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
//        xMin = yMin = zMin = extremidadeJanela;
//        xMax = yMax = zMax = extremidadeJanela;
        xMin = -Renderer.screenWidth;
        xMax = Renderer.screenWidth;
        yMin = -Renderer.screenHeight;
        yMax = Renderer.screenHeight;
        zMin = -extremidadeJanela;
        zMax = extremidadeJanela;

        //configurações de texto
        textRenderer = new TextRenderer(new Font("Serif", Font.BOLD, 30));

        //Configura a Textura
        textura = new Textura(8 );

        //Habilita o buffer de profundidade
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        GLUT glut = new GLUT();

        gl.glClearColor(0, 0, 0, 1);

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity(); //ler a matriz identidade

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, mode);

        iluminacaoDifusa(gl);
        ligarLuz(gl);

        planoDeFundo(gl);
        movimentoFundo += velocidadaAnimacaoDeFundo;
        if(movimentoFundo>=1.8f){velocidadaAnimacaoDeFundo=-velocidadaAnimacaoDeFundo;}
        else if(movimentoFundo<=1.5f){velocidadaAnimacaoDeFundo=-velocidadaAnimacaoDeFundo;}

        if (menuPrincipalAtivado){
                gerarTexto(gl, 450, 850, Color.white ,"PONG");
                gerarTexto(gl, 150, 700, Color.white ,"Como Jogar:");
                gerarTexto(gl, 150, 650, Color.white ,"Use Esc para fechar o jogo");
                gerarTexto(gl, 150, 600, Color.white ,"<-  Movimentar barra p/ esquerda");
                gerarTexto(gl, 150, 550, Color.white ,"->  Movimentar barra p/ direita");
                gerarTexto(gl, 150, 450, Color.white ,"Selecione a tecla 'c' para mudar a cor da bolinha:");
                gl.glPushMatrix();
                translacaoXBolinha = 0;
                translacaoYBolinha = 0;
                gl.glTranslatef(0, -250, 0);
                bolinha(gl,glut, vermelhoBolinha, verdeBolinha, azulBolinha);
                gl.glPopMatrix();
                gerarTexto(gl, 275, 250, Color.white ,"Aperte espaço para começar/pausar o jogo");
        } else if (jogoIniciado && vidas != 0) {//começar os desenhos
            bordas(gl,glut,vermelhoBorda,verdeBorda, azulBorda);
            corações(gl,glut);
            gerarTexto(gl, 800, 950, Color.white ,"Score");
            gerarTexto(gl, 800, 900, Color.white , String.valueOf(pontuacao));
            gerarTexto(gl, 800, 850, Color.white ,"Fase");
            gerarTexto(gl, 820, 800, Color.white , String.valueOf(fase));

            if (vidas!= 0) {
                bolinha(gl, glut, vermelhoBolinha, verdeBolinha, azulBolinha);
                movimentarBolinha();

                barra(gl, glut, vermelhoBarra, verdeBarra, azulBarra);
                movimentarBarra();

                if (fase >= 2) {
                    obstaculo(gl, glut);
                    colisaoObstaculo();
                }
            }
        } else if (menuPausaAtivado) {
                gerarTexto(gl, 450, 500, Color.white ,"PAUSE");
                gerarTexto(gl, 275, 250, Color.white ,"Aperte espaço para voltar ao jogo");
                gerarTexto(gl, 175, 190, Color.white ,"Aperte 'm' para voltar ao Menu");
                gerarTexto(gl, 580, 190, Color.red ,"(Perderá o Progresso!)");

        } else if (vidas == 0){
            if (pontuacao > record){
                record = pontuacao;
            }
            menuGameOver = true;
            gerarTexto(gl, 500, 500, Color.red ,"Game Over");
            gerarTexto(gl, 500, 400, Color.red, "Sua pontuação foi de: " + pontuacao);
            gerarTexto(gl, 500, 300, Color.red, "Seu Record foi de: " + record);
            gerarTexto(gl, 500, 250, Color.white ,"Aperte  espaço  para  reiniciar !!");
        }
        gl.glFlush();
    }

    public void bordas(GL2 gl,GLUT glut, float r, float g, float b){
        gl.glPushMatrix();
        gl.glColor3f(r, g, b);
        gl.glLineWidth(1f);
        gl.glBegin(GL.GL_LINE_LOOP);
            gl.glVertex2f(-1000,1000);
            gl.glVertex2f(1000,1000);
            gl.glVertex2f(1000,-1000);
            gl.glVertex2f(-1000,-1000);
            gl.glVertex2f(-1000,1000);
        gl.glEnd();
        gl.glPopMatrix();
    }
    public void planoDeFundo(GL2 gl){
        gl.glEnable(GL2.GL_DEPTH_TEST);

        textura.setAutomatica(false);

        textura.setFiltro(filtro);
        textura.setModo(modo);
        textura.setWrap(wrap);


        textura.gerarTextura(gl, texturaEspaco, 5);

        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(movimentoFundo, movimentoFundo);
        gl.glVertex3f(Renderer.screenWidth, Renderer.screenHeight, -100f);
        gl.glTexCoord2f(movimentoFundo, 0.0f);
        gl.glVertex3f(Renderer.screenWidth, -Renderer.screenHeight, -100f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-Renderer.screenWidth, -Renderer.screenHeight, -100f);
        gl.glTexCoord2f(0.0f, movimentoFundo);
        gl.glVertex3f(-Renderer.screenWidth, Renderer.screenHeight, -100f);
        gl.glEnd();

        textura.desabilitarTextura(gl, 5);
    }

    public void bolinha(GL2 gl,GLUT glut, float r, float g, float b){
        gl.glPushMatrix();
        gl.glTranslatef(translacaoXBolinha, translacaoYBolinha, 0);
        gl.glTranslatef(margemDeErroX,margemDeErroY,0);
        // Aplica os valores convertidos:
        gl.glColor3f(r, g, b);
        glut.glutSolidSphere(size,500,500);
        gl.glPopMatrix();
    }

    public void barra(GL2 gl, GLUT glut, float r, float g, float b){
        gl.glPushMatrix();
        gl.glTranslatef(movimentacaoBarra,0,0);

        textura.setAutomatica(false);
        textura.setFiltro(filtro);
        textura.setModo(modo);
        textura.setWrap(wrap);
        textura.gerarTextura(gl, texturaBarra, 6);

        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(1f, 1f);
        gl.glVertex3f(150, -800, 10f);
        gl.glTexCoord2f(1f, 0.0f);
        gl.glVertex3f(150, -850, -100f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-150, -850, -100f);
        gl.glTexCoord2f(0.0f, 1f);
        gl.glVertex3f(-150, -800, -100f);
        gl.glEnd();

        textura.desabilitarTextura(gl, 6);
        textura.setAutomatica(false);
        gl.glColor3f(0,0,0);
        textura.setFiltro(filtro);
        textura.setModo(modo);
        textura.setWrap(gl.GL_CLAMP_TO_BORDER);
        textura.gerarTextura(gl, texturaPropulsor, 7);

        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(1f, 1f);
        gl.glVertex3f(150, -850, 10f);
        gl.glTexCoord2f(movimentacaoLaser+1 , -movimentacaoLaser);
        gl.glVertex3f(150, -1000, -100f);
        gl.glTexCoord2f(-movimentacaoLaser-0.08f, -movimentacaoLaser);
        gl.glVertex3f(-150, -1000, -100f);
        gl.glTexCoord2f(0f, 1f);
        gl.glVertex3f(-150, -850, -100f);
        gl.glEnd();

        textura.desabilitarTextura(gl, 7);

        gl.glPopMatrix();
        movimentacaoLaser-=velocidadeLaser;
        if (movimentacaoLaser<=-0.2f){velocidadeLaser= -velocidadeLaser;}
        else if (movimentacaoLaser >= 0.1) {velocidadeLaser= -velocidadeLaser;}

    }

    public void obstaculo(GL2 gl, GLUT glut){
        gl.glPushMatrix();
        gl.glColor3f(1,1,1);
        tamanhoObstaculo = tamanhoInicialObstaculo + (20 * (fase-1));
        glut.glutSolidSphere(tamanhoObstaculo/2,(int)tamanhoObstaculo,(int)tamanhoObstaculo);
//        glut.glutSolidCube(tamanhoObstaculo);
        gl.glPopMatrix();
    }

    public void corações(GL2 gl, GLUT glut){
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glColor3f(0,0,0);
        if (vidas>=1) {
            // Textura para o primeiro coração:

            //não é geração de textura automática
            textura.setAutomatica(false);

            textura.setFiltro(filtro);
            textura.setModo(modo);
            textura.setWrap(wrap);

            //cria a textura indicando o local da imagem e o índice
//            gl.glEnable(GL2.GL_TEXTURE_2D);
            textura.gerarTextura(gl, texturaCoracao, 0);
            // Criando a face para o Primeiro Coração
            gl.glBegin(GL2.GL_QUADS);
            //coordenadas da Textura            //coordenadas do quads
            gl.glTexCoord2f(1f, 1f);
            gl.glVertex3f(-1100f, 1050f, 1000f);
            gl.glTexCoord2f(1f, 0.0f);
            gl.glVertex3f(-1100f, 950f, 1000f);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-1200f, 950f, 1000f);
            gl.glTexCoord2f(0.0f, 1f);
            gl.glVertex3f(-1200f, 1050f, 1000f);
            gl.glEnd();

            //desabilita a textura indicando o índice
            textura.desabilitarTextura(gl, 0);
//            gl.glDisable(GL2.GL_TEXTURE_2D);

//            gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        }
        if (vidas>=2) {

            //Textura para o segundo coração

            //não é geração de textura automática
//            gl.glEnable(GL2.GL_TEXTURE_2D);
            textura.setAutomatica(false);

            textura.setFiltro(filtro);
            textura.setModo(modo);
            textura.setWrap(wrap);

            //cria a textura indicando o local da imagem e o índice
            textura.gerarTextura(gl, texturaCoracao, 1);
            // Criando a face para o segundo Coração
            gl.glBegin(GL2.GL_QUADS);
            //coordenadas da Textura            //coordenadas do quads
            gl.glTexCoord2f(1f, 1f);
            gl.glVertex3f(-1100f, 950f, 1000f);
            gl.glTexCoord2f(1f, 0.0f);
            gl.glVertex3f(-1100f, 850f, 1000f);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-1200f, 850f, 1000f);
            gl.glTexCoord2f(0.0f, 1f);
            gl.glVertex3f(-1200f, 950f, 1000f);
            gl.glEnd();

            //desabilita a textura indicando o índice

            textura.desabilitarTextura(gl, 1);
//            gl.glDisable(GL2.GL_TEXTURE_2D);

//            gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        }
        if (vidas>=3) {
            //Textura para o terceiro coração

            //não é geração de textura automática
//            gl.glEnable(GL2.GL_TEXTURE_2D);
            textura.setAutomatica(false);

            textura.setFiltro(filtro);
            textura.setModo(modo);
            textura.setWrap(wrap);

            //cria a textura indicando o local da imagem e o índice
            textura.gerarTextura(gl, texturaCoracao, 2);
            gl.glBegin(GL2.GL_QUADS);
            //coordenadas da Textura            //coordenadas do quads
            gl.glTexCoord2f(1f, 1f);
            gl.glVertex3f(-1100f, 850f, 1000f);
            gl.glTexCoord2f(1f, 0.0f);
            gl.glVertex3f(-1100f, 750f, 1000f);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-1200f, 750f, 1000f);
            gl.glTexCoord2f(0.0f, 1f);
            gl.glVertex3f(-1200f, 850f, 1000f);
            gl.glEnd();
            // Criando a face para o terceiro Coração


            //desabilita a textura indicando o índice

            textura.desabilitarTextura(gl, 2);
//            gl.glDisable(GL2.GL_TEXTURE_2D);

//            gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        }
        if (vidas>=4) {


            //Textura para o quarto coração

            //não é geração de textura automática
//            gl.glEnable(GL2.GL_TEXTURE_2D);
            textura.setAutomatica(false);

            textura.setFiltro(filtro);
            textura.setModo(modo);
            textura.setWrap(wrap);

            //cria a textura indicando o local da imagem e o índice
            textura.gerarTextura(gl, texturaCoracao, 3);
            // Criando a face para o quarto Coração
            gl.glBegin(GL2.GL_QUADS);
            //coordenadas da Textura            //coordenadas do quads
            gl.glTexCoord2f(1f, 1f);
            gl.glVertex3f(-1100f, 750f, 1000f);
            gl.glTexCoord2f(1f, 0.0f);
            gl.glVertex3f(-1100f, 650f, 1000f);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-1200f, 650f, 1000f );
            gl.glTexCoord2f(0.0f, 1f);
            gl.glVertex3f(-1200f, 750f, 1000f);
            gl.glEnd();

            //desabilita a textura indicando o índice
            textura.desabilitarTextura(gl, 3);
//            gl.glDisable(GL2.GL_TEXTURE_2D);

//            gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        }
        if(vidas==5) {
            //Textura para o quinto coração

            //não é geração de textura automática
//            gl.glEnable(GL2.GL_TEXTURE_2D);
            textura.setAutomatica(false);

            textura.setFiltro(filtro);
            textura.setModo(modo);
            textura.setWrap(wrap);

            //cria a textura indicando o local da imagem e o índice
            textura.gerarTextura(gl, texturaCoracao, 4);
            // Criando a face para o quinto Coração
            gl.glBegin(GL2.GL_QUADS);
            //coordenadas da Textura            //coordenadas do quads
            gl.glTexCoord2f(1f, 1f);
            gl.glVertex3f(-1100f, 650f, 1000f);
            gl.glTexCoord2f(1f, 0.0f);
            gl.glVertex3f(-1100f, 550f, 1000f);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-1200f, 550f, 1000f);
            gl.glTexCoord2f(0.0f, 1f);
            gl.glVertex3f(-1200f, 650f, 1000f);
            gl.glEnd();

            //desabilita a textura indicando o índice
            textura.desabilitarTextura(gl, 4);
//            gl.glDisable(GL2.GL_TEXTURE_2D);
        }
    }

    public void gerarTexto(GL2 gl, int xPosicao, int yPosicao, Color cor, String texto){
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        //Retorna a largura e altura da janela
        textRenderer.beginRendering(1000, 1000);
        textRenderer.setColor(cor);
        textRenderer.draw(texto, xPosicao, yPosicao);
        textRenderer.endRendering();
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, mode);
    }

    public void iluminacaoDifusa(GL2 gl) {
        float luzDifusa[] = {1.0f, 1.0f, 1.0f, 1.0f};
        float posicaoLuz[] = {-50.0f, -5.0f, 100.0f, 0.0f};

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, luzDifusa, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posicaoLuz, 0);
    }

    public void ligarLuz(GL2 gl) {
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glShadeModel(GL2.GL_SMOOTH);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        //obtem o contexto grafico Opengl
        GL2 gl = drawable.getGL().getGL2();

        //ativa a matriz de projecao
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity(); //ler a matriz identidade

        //projecao ortogonal sem a correcao do aspecto
        gl.glOrtho(xMin, xMax, yMin, yMax, zMin, zMax);

        //ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); //ler a matriz identidade
        System.out.println("Reshape: " + width + ", " + height);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {}
}

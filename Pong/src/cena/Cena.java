package cena;


import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.awt.TextRenderer;
import java.util.Random;
import java.awt.Color;
import java.awt.Font;

public class Cena implements GLEventListener{
    private float xMin, xMax, yMin, yMax, zMin, zMax;
    public final float extremidadeJanela = 1000f;
    public float size = 50;
    private TextRenderer textRenderer;
    public float extremidadeDireitaXBolinha = size/2, extremidadeEsquerdaXBolinha = -size/2;
    public float extremidadeSuperiorYBolinha = size/2, extremidadeInferiorYBolinha = -size/2;
    public float translacaoXBolinha=0, translacaoYBolinha=0;
    public final float velocidadeInicialX = 20f, velocidadeInicialY = 15f;
    public float taxaAtualizacaoX =20f , taxaAtualizacaoY =15f;
    public float movimentacaoBarra=0;
    public float extremidadeDireitaBarra = size*3 , extremidadeEsquerdaBarra =extremidadeDireitaBarra -(size*6);
    public float posicaoYbarra = -900 ;
    public final float tamanhoInicialObstaculo = 100;
    public float tamanhoObstaculo =100;
    public int vidas = 5;
    public int pontuacao = 0;
    public int fase= 1;

    public boolean menuPrincipalAtivado = true;
    public boolean jogoIniciado = false;
    public boolean menuPausaAtivado = false;
    public boolean menuGameOver = false;

    public int mode;
    public void resetarPosicaoInicialBolinha(){
        translacaoYBolinha = 0;
        extremidadeSuperiorYBolinha = size / 2;
        extremidadeInferiorYBolinha = - size / 2;
        taxaAtualizacaoY = - taxaAtualizacaoY;

        translacaoXBolinha = 0;
        extremidadeDireitaXBolinha = size / 2;
    }
    public void resetarJogo(){
        resetarPosicaoInicialBolinha();
        taxaAtualizacaoY = velocidadeInicialY;

        movimentacaoBarra=0;
        extremidadeDireitaBarra = size*3;
        extremidadeEsquerdaBarra =extremidadeDireitaBarra -(size*6);

        menuGameOver=false;
        vidas = 5;
        pontuacao = 0;
        fase= 1;
    }
    public void colisaoObstaculo(){
        if(extremidadeDireitaXBolinha >= -tamanhoObstaculo/2 && extremidadeDireitaXBolinha <= tamanhoObstaculo/2){
            if (extremidadeInferiorYBolinha <= tamanhoObstaculo/2 && extremidadeInferiorYBolinha >= (tamanhoObstaculo/2) - 20)// parte superior + margem de erro
            {
                //taxa crescente, eixo y
                taxaAtualizacaoY = velocidadeInicialY + (5 * (fase-1));

                Random ran = new Random();
                int aleatorizaAcressimoX = ran.nextInt(11);
                if (taxaAtualizacaoX<0){
                    taxaAtualizacaoX = -velocidadeInicialX - (5 * (fase-1));//pode aumentar velocidade a depender da fase
                    taxaAtualizacaoX -= aleatorizaAcressimoX;
                } else {
                    taxaAtualizacaoX = velocidadeInicialX + (5 * (fase - 1));//pode aumentar velocidade a depender da fase
                    taxaAtualizacaoX += aleatorizaAcressimoX;
                }
            } else if (extremidadeSuperiorYBolinha <= -tamanhoObstaculo/2 && extremidadeSuperiorYBolinha >= -((tamanhoObstaculo/2) + 20))// parte inferior + margem de erro
            {
                //taxa decrescente, eixo y
                taxaAtualizacaoY = -velocidadeInicialY - (5 * (fase-1));

                Random ran = new Random();
                int aleatorizaAcressimoX = ran.nextInt(11);
                if (taxaAtualizacaoX<0){
                    taxaAtualizacaoX = -velocidadeInicialX - (5 * (fase-1));//pode aumentar velocidade a depender da fase
                    taxaAtualizacaoX -= aleatorizaAcressimoX;
                } else {
                    taxaAtualizacaoX = velocidadeInicialX + (5 * (fase - 1));//pode aumentar velocidade a depender da fase
                    taxaAtualizacaoX += aleatorizaAcressimoX;
                }
            } else if (extremidadeInferiorYBolinha <= tamanhoObstaculo/2 && extremidadeInferiorYBolinha >= -tamanhoObstaculo/2)
            {
                // eixo y
                if (taxaAtualizacaoY<= 0){
                    taxaAtualizacaoY = -velocidadeInicialY - (5 * (fase-1));
                } else {
                    taxaAtualizacaoY = velocidadeInicialY + (5 * (fase - 1));
                }
                Random ran = new Random();
                int aleatorizaAcressimoX = ran.nextInt(11);
                //se bater na lateral a bolinha vai para o lado oposto em relação ao eixo x
                if (taxaAtualizacaoX < 0){
                    taxaAtualizacaoX = velocidadeInicialX + (5 * (fase-1) );//pode aumentar velocidade a depender da fase
                    taxaAtualizacaoX += aleatorizaAcressimoX;
                } else {
                    taxaAtualizacaoX = -velocidadeInicialX - (5 * (fase - 1));//pode aumentar velocidade a depender da fase
                    taxaAtualizacaoX -= aleatorizaAcressimoX;
                }
            }
        }
    }

    public void movimentarBarra(){
        //verifica a colisão da barra com a parede
        System.out.println(taxaAtualizacaoY);


        if (movimentacaoBarra+ size*3 >= extremidadeJanela){
            movimentacaoBarra = extremidadeJanela - size*3;
            extremidadeDireitaBarra = extremidadeJanela;
        }else if(movimentacaoBarra- size*3 <= - extremidadeJanela){
            movimentacaoBarra = - extremidadeJanela + size*3;
            extremidadeDireitaBarra = - extremidadeJanela + (size*6);
        }
        //verifica colisao no eixo y
        if (extremidadeInferiorYBolinha <= posicaoYbarra+(size) && extremidadeInferiorYBolinha >= posicaoYbarra+(size/2))// parte superior + margem de erro
        {
            //verificar colisão com a parte superior da barrra
            if(extremidadeDireitaXBolinha >= extremidadeEsquerdaBarra && extremidadeDireitaXBolinha <= extremidadeDireitaBarra){
                pontuacao+=50;

                fase = (pontuacao/200)+1;

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
            }
        }else if (extremidadeInferiorYBolinha <= posicaoYbarra+(size) && extremidadeInferiorYBolinha >= posicaoYbarra-(size)){
            //verificar colisão com a parte lateral da barrra
            if(extremidadeDireitaXBolinha >= extremidadeEsquerdaBarra && extremidadeDireitaXBolinha <= extremidadeDireitaBarra)
             {
                pontuacao+=50;

                fase = (pontuacao/200)+1;

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
            extremidadeSuperiorYBolinha =translacaoYBolinha+(size/2);//armazena a extremidade Y com base na translacao e tamanho do objeto( /2 porque a bolinha é iniciada no centro da janela )
            extremidadeInferiorYBolinha =translacaoYBolinha-(size/2);

            translacaoXBolinha+= taxaAtualizacaoX;//inicia a movimentacao da bolinha no eixo X
            extremidadeDireitaXBolinha =translacaoXBolinha+(size/2);//armazena a extremidade X
            extremidadeEsquerdaXBolinha= translacaoXBolinha-(size/2);

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
//        xMin = yMin = zMin = -extremidadeJanela;
//        xMax = yMax = zMax = extremidadeJanela;
        xMin = -Renderer.screenWidth;
        xMax = Renderer.screenWidth;
        yMin = -Renderer.screenHeight;
        yMax = Renderer.screenHeight;
        zMin = -extremidadeJanela;
        zMax = extremidadeJanela;

        //configurações de texto
        textRenderer = new TextRenderer(new Font("Serif", Font.BOLD, 30));

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


        if (menuPrincipalAtivado){
            gerarTexto(gl, 450, 750, Color.white ,"PONG");
            gerarTexto(gl, 275, 250, Color.white ,"Aperte espaço para começar o jogo");
        } else if (jogoIniciado && vidas != 0) {//começar os desenhos
            bordas(gl,glut);
            corações(gl,glut);
            gerarTexto(gl, 800, 950, Color.white ,"Score");
            gerarTexto(gl, 800, 900, Color.white , String.valueOf(pontuacao));
            gerarTexto(gl, 800, 850, Color.white ,"Fase");
            gerarTexto(gl, 820, 800, Color.white , String.valueOf(fase));

            if (vidas!= 0){ bolinha(gl,glut); }
            movimentarBolinha();

            barra(gl,glut);
            movimentarBarra();

            if (fase >= 2){
                obstaculo(gl,glut);
                colisaoObstaculo();
            }
        } else if (menuPausaAtivado) {
            gerarTexto(gl, 450, 500, Color.white ,"PAUSE");
            gerarTexto(gl, 275, 250, Color.white ,"Aperte espaço para voltar ao jogo");
        } else if (vidas == 0){
            menuGameOver = true;
            gerarTexto(gl, 425, 500, Color.white ,"Game Over");
            gerarTexto(gl, 275, 250, Color.white ,"Aperte  espaço  para  reiniciar !!");
        }
        gl.glFlush();
    }

    public void bordas(GL2 gl, GLUT glut){
        gl.glPushMatrix();
        gl.glColor3f(1,1,1);
        gl.glLineWidth(100f);
        gl.glBegin(GL.GL_LINE_LOOP);
            gl.glVertex2f(-1050,1040);
            gl.glVertex2f(1050,1040);
            gl.glVertex2f(1050,-1050);
            gl.glVertex2f(-1050,-1050);
            gl.glVertex2f(-1050,1050);
        gl.glEnd();
        gl.glPopMatrix();
    }
    public void bolinha(GL2 gl,GLUT glut){
        gl.glPushMatrix();
        gl.glTranslatef(translacaoXBolinha, translacaoYBolinha, 0);
        gl.glTranslatef(-10, -10, 0);//mantem a boliha no ponto central na inicializacao
        gl.glColor3f(1,1,1);
        glut.glutSolidSphere(size,500,500);
        gl.glPopMatrix();
    }

    public void barra(GL2 gl, GLUT glut){
        gl.glPushMatrix();
        gl.glTranslatef(0,-900,0);
        gl.glTranslatef(movimentacaoBarra,0,0);
        float x = (float) -(size*2.5);
        for (int i = 0; i < 6 ; i++) {
            gl.glPushMatrix();
            gl.glTranslatef(x,0,0);
            gl.glColor3f(0,0,1);
            glut.glutSolidCube(size);
            gl.glPopMatrix();
            x+=size;
        }
        gl.glPopMatrix();
    }

    public void obstaculo(GL2 gl, GLUT glut){
        gl.glPushMatrix();
        gl.glColor3f(1,1,1);
        tamanhoObstaculo = tamanhoInicialObstaculo + (20 * (fase-1));
        glut.glutSolidSphere(tamanhoObstaculo/2,(int)tamanhoObstaculo,(int)tamanhoObstaculo);
        gl.glPopMatrix();
    }

    public void corações(GL2 gl, GLUT glut){
        gl.glPushMatrix();
        gl.glTranslatef(-1150,1000,0);
        for (int i = 0; i < vidas ; i++) {
            float y =- (100 * i) - (20f *i);
            System.out.println(y);
            gl.glPushMatrix();
            gl.glTranslatef(0,y,0);
            gl.glColor3f(1,0,0);
            glut.glutSolidCube(100);
            gl.glPopMatrix();
        }
        gl.glPopMatrix();
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
        float posicaoLuz[] = {-50.0f, 0.0f, 100.0f, 0.0f};

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
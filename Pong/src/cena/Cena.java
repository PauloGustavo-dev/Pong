package cena;


import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.gl2.GLUT;

public class Cena implements GLEventListener{    
    private float xMin, xMax, yMin, yMax, zMin, zMax;

    public boolean play = false;
    public float size = 50;
    public float extremidadeDireitaXBolinha = size/2, extremidadeEsquerdaXBolinha = -size/2;
    public float extremidadeSuperiorYBolinha = size/2, extremidadeInferiorYBolinha = -size/2;
    public float translacaoXBolinha=0, translacaoYBolinha=0;
    public float taxaAtualizacaoX =20f , taxaAtualizacaoY =15f;

    public float movimentacaoBarra=0;
    public float extremidadeDireitaBarra = size*3 , extremidadeEsquerdaBarra =extremidadeDireitaBarra -(size*6);
    public float posicaoYbarra = -900 ;

    public int vidas = 3;
    public int pontuacao = 0;


    public int mode;

    public void movimentarBarra(){
        //verifica a colisão da barra com a parede

        if (movimentacaoBarra+ size*3 >=1000){
            movimentacaoBarra = 1000 - size*3;
            extremidadeDireitaBarra = 1000;
        }else if(movimentacaoBarra- size*3 <=-1000){
            movimentacaoBarra = -1000 + size*3;
            extremidadeDireitaBarra = -1000 + (size*6);
        }
        //verifica colisao no eixo y
        if (extremidadeInferiorYBolinha <= posicaoYbarra+(size/2) && extremidadeInferiorYBolinha >= posicaoYbarra-(size/2)){
            if(extremidadeDireitaXBolinha >= extremidadeEsquerdaBarra && extremidadeDireitaXBolinha <= extremidadeDireitaBarra){
                taxaAtualizacaoY = - taxaAtualizacaoY;
                pontuacao+=100;
            }
        }
        //if extremidadexbolinha <= extremidadexbarra && extremidadexbolinha >= -extremidadexbarra
        //colisao realisada
        //pontuacao ++
        //taxaAtualizacaoX =20 taxaAtualizacaoY =15;

    }

    public void movimentarBolinha(){
        if (play && vidas!=0){
            translacaoYBolinha+= taxaAtualizacaoY;//inicia a movimentacao da bolinha no eixo y
            extremidadeSuperiorYBolinha =translacaoYBolinha+(size/2);//armazena a extremidade Y com base na translacao e tamanho do objeto( /2 porque a bolinha é iniciada no centro da janela )
            extremidadeInferiorYBolinha =translacaoYBolinha-(size/2);

            translacaoXBolinha+= taxaAtualizacaoX;//inicia a movimentacao da bolinha no eixo X
            extremidadeDireitaXBolinha =translacaoXBolinha+(size/2);//armazena a extremidade X
            extremidadeEsquerdaXBolinha= translacaoXBolinha-(size/2);

            //verificar colisoes paredes
            if(extremidadeDireitaXBolinha >=1000){
                taxaAtualizacaoX = - taxaAtualizacaoX;
            } else if(extremidadeEsquerdaXBolinha <=-1000){
                taxaAtualizacaoX = - taxaAtualizacaoX;
            }
            //verifica colisões teto/chão
            if(extremidadeSuperiorYBolinha >=1000){
                taxaAtualizacaoY = -15;
            }else if(extremidadeInferiorYBolinha <=-1000){
                vidas-=1;
                //resetando valores iniciaais
                translacaoYBolinha= 0;
                extremidadeSuperiorYBolinha = size/2;
                extremidadeInferiorYBolinha = -size/2;
                taxaAtualizacaoY=15;

                translacaoXBolinha= 0;
                extremidadeDireitaXBolinha = size/2;

            }
        }
    }

    
    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena        
        GL2 gl = drawable.getGL().getGL2();        
        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -1000;
        xMax = yMax = zMax = 1000;


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

        //começar os desenhos
        bolinha(gl,glut);
        barra(gl,glut);

        movimentarBolinha();
        movimentarBarra();

        gl.glFlush();      
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

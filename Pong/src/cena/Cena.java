package cena;


import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.gl2.GLUT;

public class Cena implements GLEventListener{    
    private float xMin, xMax, yMin, yMax, zMin, zMax;

    public boolean play = false;
    public float size = 50, extremidadeXBolinha = size/2, extremidadeYBolinha = size/2;
    public float translacaoXBolinha=0, translacaoYBolinha=0;
    public float taxaAtualizacaoX =20, taxaAtualizacaoY =15;

    public int vidas = 3;


    public int mode;

    public void movimentarBolinha(GL2 gl){
        if (play && vidas!=0){
            translacaoYBolinha+= taxaAtualizacaoY;//inicia a movimentacao da bolinha no eixo y
            extremidadeYBolinha=translacaoYBolinha+(size/2);//armazena a extremidade Y com base na translacao e tamanho do objeto( /2 porque a bolinha é iniciada no centro da janela )

            translacaoXBolinha+= taxaAtualizacaoX;//inicia a movimentacao da bolinha no eixo X
            extremidadeXBolinha=translacaoXBolinha+(size/2);//armazena a extremidade X
            System.out.println(extremidadeYBolinha);
            //verificar colisoes paredes
            if(extremidadeXBolinha>=1000){
                taxaAtualizacaoX = -15;
            } else if(extremidadeXBolinha<=-1000){
                taxaAtualizacaoX = 15;
            }
            //verifica colisões teto/chão
            if(extremidadeYBolinha>=1000){
                taxaAtualizacaoY = -15;
            }else if(extremidadeYBolinha<=-1000){
                vidas-=1;
                translacaoYBolinha= 0;
                extremidadeYBolinha= size/2;
                taxaAtualizacaoY=15;

                translacaoXBolinha= 0;
                extremidadeXBolinha= size/2;
                taxaAtualizacaoX=20;
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

        String m = mode == GL2.GL_LINE ? "LINE" : "FILL";

        //começar os desenhos
        bolinha(gl,glut);
        movimentarBolinha(gl);

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

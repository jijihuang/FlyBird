package game;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * 游戏界面
 */

public class MyBirdGame extends JPanel {

    BufferedImage background;//背景图片
    BufferedImage startImage;//开始图片
    BufferedImage gameOverImage;//结束图片

    Ground ground;
    Column column1,column2;
    Bird bird;

    int score;//得分

    public static final int START=0;
    public static final int RUNNING=1;
    public static final int GAMEOVER=2;
    int state;//游戏状态

    //初始化游戏
    public MyBirdGame()throws Exception{
        background = ImageIO.read(getClass().getResource("/resources/bg.png"));
        startImage=ImageIO.read(getClass().getResource("/resources/start.png"));
        gameOverImage=ImageIO.read(getClass().getResource("/resources/gameover.png"));
        ground=new Ground();
        column1=new Column(1);
        column2=new Column(2);
        bird=new Bird();
        score=0;
        state=START;
    }

    //绘制界面
    public void paint(Graphics g){
        //背景
        g.drawImage(background,0,0,null);

        //地面
        g.drawImage(ground.image,ground.x,ground.y,null);

        //柱子
        g.drawImage(column1.image,column1.x-column1.width/2,column1.y-column1.height/2,null);
        g.drawImage(column2.image,column2.x-column2.width/2,column2.y-column2.height/2,null);

        //小鸟（旋转坐标系）
        Graphics2D g2=(Graphics2D)g;
        g2.rotate(-bird.alpha,bird.x,bird.y);
        g.drawImage(bird.image,bird.x-bird.width/2,bird.y-bird.height/2,null);
        g2.rotate(bird.alpha,bird.x,bird.y);

        //分数
        Font f=new Font(Font.SANS_SERIF,Font.BOLD,40);
        g.setFont(f);
        g.drawString(""+score,40,60);
        g.setColor(Color.white);
        g.drawString(""+score,40-3,60-3);

        //开始与结束界面
        switch(state){
            case START:
                g.drawImage(startImage,0,0,null);
                break;
            case GAMEOVER:
                g.drawImage(gameOverImage,0,0,null);
                break;
        }
    }

    //开始游戏
    public void action()throws Exception{
        MouseListener l=new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try{
                    switch (state){
                        case START:
                            state=RUNNING;
                            break;
                        case RUNNING:
                            bird.flappy();
                            break;
                        case GAMEOVER:
                            column1=new Column(1);
                            column2=new Column(2);
                            bird=new Bird();
                            score=0;
                            state=START;
                            break;
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        };
        addMouseListener(l);
        while (true){
            switch (state){
                case START:
                    bird.fly();
                    ground.step();
                    break;
                case RUNNING:
                    ground.step();
                    column1.step();
                    column2.step();
                    bird.fly();
                    bird.step();
                    if (bird.x==column1.x||bird.x==column2.x){
                        score++;
                    }
                    if (bird.hit(ground)||bird.hit(column1)||bird.hit(column2)){
                        state=GAMEOVER;
                    }
                    break;
            }
            repaint();
            Thread.sleep(1000/60);
        }
    }

    //启动游戏
    public static void main(String[] args)throws Exception{
        JFrame frame = new JFrame();
        MyBirdGame game = new MyBirdGame();
        frame.add(game);
        frame.setSize(440,670);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);//显示JPanel
        game.action();
    }
}

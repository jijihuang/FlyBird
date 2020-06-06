package game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 柱子类
 */

public class Column {

    BufferedImage image;
    int x,y;
    int width,height;
    int gap;//上下柱子之间的间隙
    int distance;//左右柱子的距离
    Random random=new Random();

    //初始化柱子
    public Column(int n)throws Exception{
        image=ImageIO.read(getClass().getResource("/resources/column.png"));
        width=image.getWidth();
        height=image.getHeight();
        gap=144;
        distance=245;
        x=550+(n-1)*distance;
        y=random.nextInt(128)+132;
    }

    //向左移动一步
    public void step(){
        x--;
        if (x==-width/2){
            x=distance*2-width/2;
            y=random.nextInt(128)+132;
        }
    }
}

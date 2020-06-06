package game;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * 小鸟类
 */

public class Bird {

    BufferedImage image;
    int x,y;
    int width,height;
    int size;//大小，用于碰撞检测
    double g;//加速度
    double t;//位移时间间隔
    double v0;//最初向上抛的速度
    double speed;//当前向上抛速度
    double s;//经过时间t之后的位移
    double alpha;//小鸟的倾斜角度
    BufferedImage[] images;//小鸟的动画帧，一组图片
    int index;//动画帧下标

    public Bird()throws Exception{
        //初始化基本参数
        image=ImageIO.read(getClass().getResource("/resources/0.png"));
        width=image.getWidth();
        height=image.getHeight();
        x=132;
        y=280;
        size=40;

        //初始化位移参数
        g=4;
        v0=20;
        t=0.25;
        speed=v0;
        s=0;
        alpha=0;

        //初始化动画帧参数
        images=new BufferedImage[8];
        for (int i=0;i<8;i++){
            images[i]=ImageIO.read(getClass().getResource("/resources/"+i+".png"));
        }
        index=0;
    }

    //飞行动作，帧数+1
    public void fly(){
        index++;
        image=images[(index/12)%8];
    }

    //移动一步
    public void step(){
        double v0=speed;
        //上抛运动位移
        s=v0*t+g*t*t/2;
        //鸟的坐标
        y=y-(int)s;
        //计算下次移动速度
        double v=v0-g*t;
        speed=v;
        //计算倾角（反正切函数）
        alpha=Math.atan(s/8);
    }

    //向上飞行
    public void flappy(){
        //重置速度
        speed=v0;
    }

    //检测是否碰到地面
    public boolean hit(Ground ground){
        boolean hit=y+size/2>ground.y;
        if (hit){
            y=ground.y-size/2;
            alpha=-3.14159265358979323/2;
        }
        return hit;
    }

    //检测是否碰到柱子
    public boolean hit(Column column){
        if (x>column.x-column.width/2-size/2&&x<column.x+column.width/2+size/2){
            if (y>column.y-column.gap/2+size/2&&y<column.y+column.gap/2-size/2){
                return false;
            }
            return true;
        }
        return false;
    }
}

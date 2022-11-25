package com.thomas.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener,ActionListener {

    //创建二维数组存放图片顺序
    int[][] data =new int[4][4];

    //记录空白图片位置
    int x = 0;
    int y = 0;


    //记录步数
    int count = 0;




    String path = "image\\girl\\girl1\\";

    int[][] win = new int[][]{
        {1,2,3,4},
        {5,6,7,8},
        {9,10,11,12},
        {13,14,15,0}
    };



    //创建选项下面的条目对象
    // (功能条目)
    JMenuItem replayItem = new JMenuItem("重新游戏");
    JMenuItem reloginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");

    //(关于我们条目)
    JMenuItem accountItem = new JMenuItem("公众号");






    public GameJFrame() {
        //初始化界面
        initJFram();

        //初始化菜单
        initJMenuBar();

        //初始化数据(打乱图片)
        initData();

        //初始化图片
        initImage();


        //界面可视化
        this.setVisible(true);
    }











    //初始化图片顺序
    private void initData() {
        int[] tempArr={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        Random r = new Random();
        for (int i = 0; i < tempArr.length; i++) {
            int index = r.nextInt(tempArr.length);
            
            //交换数据
            int temp =tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }

        //将数据赋值到二维数组
        for (int i = 0,index=0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++,index++) {
                data[i][j]=tempArr[index];

                //确定空白图片位置
                if(data[i][j]==0){
                    x=i;
                    y=j;
                }
            }
        }
        
    }








    

    //初始化图片,添加图片
    private void initImage() {

        //清除已经出现的图片（格式化）
        this.getContentPane().removeAll();

        //胜利图片
        if(victory()){
            JLabel winJLabel = new JLabel(new ImageIcon("image\\win.png"));

            winJLabel.setBounds(203,283,197,73);

            this.getContentPane().add(winJLabel);
        }


        //统计步数
        JLabel countJLabel = new JLabel("步数："+count);

        countJLabel.setBounds(50,30,100,20);

        this.getContentPane().add(countJLabel);


        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                //图片顺序
                int number = data[i][j];

                //创建一个JLabel对象（管理容器）,存入照片
                JLabel jLabel = new JLabel(new ImageIcon(path+number+".jpg" ));

                //制定图片位置
                jLabel.setBounds(105*j+83,105*i+134,105,105);

                //给照片添加边框
                jLabel.setBorder(new BevelBorder(1));

                //把管理容器添加到界面中
                this.getContentPane().add(jLabel);


            }
        }

        //添加背景图片
        JLabel background = new JLabel(new ImageIcon("image\\background.png"));
        background.setBounds(40,40,508,560);
        this.getContentPane().add(background);


        //刷新界面
        this.getContentPane().repaint();


    }












    //初始化菜单
    private void initJMenuBar() {
        //创建菜单对象
        JMenuBar jMenuBar = new JMenuBar();

        //创建菜单的选项(功能  关于我们 )
        JMenu fouctionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于我们");




        //添加条目
        //(功能)
        fouctionJMenu.add(replayItem);
        fouctionJMenu.add(reloginItem);
        fouctionJMenu.add(closeItem);

        //(关于我们)
        aboutJMenu.add(accountItem);


        //绑定事件
        replayItem.addActionListener( this);
        reloginItem.addActionListener( this);
        closeItem.addActionListener( this);
        accountItem.addActionListener( this);

        //添加选项到菜单
        jMenuBar.add(fouctionJMenu);
        jMenuBar.add(aboutJMenu);

        //给界面设置菜单
        this.setJMenuBar(jMenuBar);
    }










    //初始化界面
    private void initJFram() {
        //设置宽高
        this.setSize(603,680);
        //设置标题
        this.setTitle("拼图单机版 v1.0");
        //设置界面置顶
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置游戏关闭模式
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //取消默认居中放置,否则组件位置只能在中间
        this.setLayout(null);
        //添加键盘监听事件
        this.addKeyListener(this);



    }


    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) {

        //查看完整图片
        int code = e.getKeyCode();
        if(code ==65){
            //清空
            this.getContentPane().removeAll();

            JLabel all = new JLabel(new ImageIcon(path+"all.jpg"));
            all.setBounds(83,134,420,420);
            this.getContentPane().add(all);

            //添加背景图片
            JLabel background = new JLabel(new ImageIcon("image\\background.png"));
            background.setBounds(40,40,508,560);
            this.getContentPane().add(background);

            //刷新
            this.getContentPane().repaint();


        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //对上下左右判断
        // 左37，上38，右39，下40
        int code = e.getKeyCode();

        //游戏结束时停止移动
        if(victory()){
            return;
        }


        //向左
        if(code ==37 && y+1<=3){
            data[x][y]=data[x][y+1];
            data[x][y+1]=0;
            y++;

            //步数
            count++;

            //从新加载图片
            initImage();
        }
        //向上
        else if(code ==38 && x+1<=3){

            data[x][y]=data[x+1][y];
            data[x+1][y] = 0;
            x++;

            //步数
            count++;

            //从新加载图片
            initImage();

        }
        //向右
        else if(code ==39 && y-1>=0){
            data[x][y]=data[x][y-1];
            data[x][y-1]=0;
            y--;

            //步数
            count++;

            //从新加载图片
            initImage();



        }
        //向下
        else if(code ==40 && x-1>=0){

            data[x][y]=data[x-1][y];
            data[x-1][y] = 0;
            x--;

            //步数
            count++;

            //从新加载图片
            initImage();

        }
        else if(code ==65){
            initImage();
        }
        //作弊码
        else if(code ==87){
            data = new int[][]{
                    {1,2,3,4},
                    {5,6,7,8},
                    {9,10,11,12},
                    {13,14,15,0}
            };

            initImage();

        }


    }


    private boolean victory() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {

                if (data[i][j] != win[i][j]) {
                    return false;
                }
            }
        }


        return true;
    }



    @Override
    public void actionPerformed(ActionEvent e) {

        Object obj = e.getSource();

        if (obj == replayItem) {

            //打乱数组数据
            initData();

            //计步器清零
            count =0;

            //重新加载图片
            initImage();



        }
        else if (obj == reloginItem) {

            //关闭当前界面
            this.setVisible(false);

            //打开登录界面
            new LoginJFrame();

        }
        else if (obj == closeItem) {

            System.exit(0);

        }
        else if (obj == accountItem) {

            //创建弹窗
            JDialog jDialog = new JDialog();

            JLabel jLabel = new JLabel(new ImageIcon("image\\about.jpg"));
            jLabel.setBounds(0,0,258,258);

            //添加图片到弹框
            jDialog.getContentPane().add(jLabel);
            jDialog.setSize(344,344);
            //置顶
            jDialog.setAlwaysOnTop(true);
            //居中
            jDialog.setLocationRelativeTo(null);
            //弹框不关则无法继续操作
            jDialog.setModal(true);

            jDialog.setVisible(true);

        }


    }
}

package recommend.recommender;

import NLP.ChnWordSeg;
import NLP.WordCloudTest;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by legotime
 */
public class recommendTest {
    public static void main(String[] args) throws SQLException {

        timer4();
//
    }
//         第四种方法：安排指定的任务task在指定的时间firstTime开始进行重复的固定速率period执行．
//         Timer.scheduleAtFixedRate(TimerTask task,Date firstTime,long period)

    public static void timer4() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 10); // 控制时
//        calendar.set(Calendar.MINUTE, 24);       // 控制分
//        calendar.set(Calendar.SECOND, 0);       // 控制秒
//        Date time = calendar.getTime();         // 得出执行任务的时间,此处为今天的12：00：00
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                try {
                    System.out.println("开始执行推荐系统");
                    maindaima();
                    System.out.println("开始执行NLP计算");
                    ChnWordSeg.main();
                    System.out.println("开始执行人物画像");
                    WordCloudTest.main();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }, 0, 1000 * 60 * 3);// 这里设定将延时每天固定执行

//        }, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
    }
    public static void maindaima() throws SQLException {
        //连接数据库，读取每个用户访问的类型名称
        ahistory();
        java.sql.Connection conn;
        conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/community?characterEncoding=utf8&useSSL=false", "root", "123456");
        java.sql.Statement stmt = conn.createStatement();
        //执行 SQL
        String sql = "select uid,tnamesum from tb_usertname";
        int uid;
        String s1;
        String[] products = new String[]{};
        int[] uids = new int[]{};
        ResultSet rs = stmt.executeQuery(sql);
        //将所有用户的uid和浏览过的类型名称都存入数组，products[]是所有用户浏览过的类型，uids[]是所有用户的id
        while (rs.next()) {
            //将其他用户浏览类型加入products数组
            uid = rs.getInt("uid");
            s1 = rs.getString("tnamesum");
            products = insert(products, s1);
            uids = insert1(uids, uid);
        }
        System.out.print(Arrays.toString(uids) + "\n");
        System.out.print(Arrays.toString(products) + "\n");
        //得到每个用户的推荐算法算出的数据
        //如：[0.2, 0.0, 0.0, 0.0, 0.0]
        //   [clothing, electronics, everyday, food, fresh]
        for (int i = 0; i < products.length; i++) {
            String s = products[i];
            int sid = uids[i];
            System.out.print(sid + "\n");
            recommend rec = new recommend();
            rec.fit(products);
            double[] doubles = rec.recommendFun(s);
            System.out.println(Arrays.toString(doubles));
            //  System.out.println(rec.getProduct());
            //将每个用户的浏览类型存入product[]
            //将集合转为数组
            String[] product = (String[]) rec.getProduct().toArray(new String[rec.getProduct().size()]);
            System.out.print(Arrays.toString(product) + "\n");

            int[] Index = new int[product.length];
            recommendTest oneall = new recommendTest();
            Index = oneall.Arraysort(doubles);
            String[] str = new String[]{};
            for (int j = 0; j < doubles.length; j++) {
                System.out.println("排序：" + doubles[j]);
                System.out.println("对应下标:" + Index[j]);
                for (int z = 0; z < doubles.length; z++) {
                    if (doubles[0] > 0) {
                        if (doubles[z] == doubles[0]) {
                            str = insert(str, product[Index[z]]);
                        } else break;
                    }
                }
                System.out.print(Arrays.toString(str) + "\n");

                String sql2="select count(*) num from tb_recomuid where uid = '" + sid + "'";
                try {
                    java.sql.Statement sta = conn.createStatement();
                    ResultSet rs2 = sta.executeQuery(sql2);
                    int count = 0;
                    while (rs2.next()) {
                        count = rs2.getInt("num");
                    }
                    if (count == 0) {
                        java.sql.Statement stmt1 = conn.createStatement();
                        //执行 SQL
                        String sql1 = "insert into tb_retype(uid,maxtype) values('" + sid + "','"+ Arrays.toString(str).replace("[","").replace("]","") + "')";
                        stmt1.executeUpdate(sql1);
                        System.out.println("数据库插入成功！！！");
                        break;
                    } else {
                        java.sql.Statement stmt2 = conn.createStatement();
                        String sql3="update tb_recomuid set maxtname ='"+Arrays.toString(str).replace("[","").replace("]","")+"' where uid = '" + sid + "'";
                        stmt2.executeUpdate(sql3);
                        System.out.println("数据库更新成功！！！");
                        break;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static int[] Arraysort(double[]arr)
    {
    //double[] arr = {5.5,2,66,3,7,5};
    double temp;
    int index;
    int k=arr.length;
    int[]Index= new int[k];
    for(int i=0;i<k;i++)
    {
        Index[i]=i;
    }

    for(int i=0;i<arr.length;i++)
    {
        for(int j=0;j<arr.length-i-1;j++)
        {
            if(arr[j]<arr[j+1])
            {
                temp = arr[j];
                arr[j] = arr[j+1];
                arr[j+1] = temp;

                index=Index[j];
                Index[j] = Index[j+1];
                Index[j+1] = index;
            }
        }
    }
    return Index;
}

        private static String[] insert (String[]arr, String str){
            int size = arr.length;  //获取数组长度
            String[] tmp = new String[size + 1];  //新建临时字符串数组，在原来基础上长度加一
            for (int i = 0; i < size; i++) {  //先遍历将原来的字符串数组数据添加到临时字符串数组
                tmp[i] = arr[i];
            }
            tmp[size] = str;  //在最后添加上需要追加的数据
            return tmp;  //返回拼接完成的字符串数组
        }
    private static int[] insert1 (int[]arr, int uid){
        int size = arr.length;  //获取数组长度
        int[] tmp = new int[size + 1];  //新建临时字符串数组，在原来基础上长度加一
        for (int i = 0; i < size; i++) {  //先遍历将原来的字符串数组数据添加到临时字符串数组
            tmp[i] = arr[i];
        }
        tmp[size] = uid;  //在最后添加上需要追加的数据
        return tmp;  //返回拼接完成的字符串数组
    }

    //连接tb_history与tb_usertname
    public static void ahistory() throws SQLException {
        System.out.println("++++++++++++++++++++++++++++++++++=");
        java.sql.Connection conn;
        conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/community?characterEncoding=utf8&useSSL=false", "root", "123456");
        java.sql.Statement stmt = conn.createStatement();
        //执行 SQL
        for (int i = 0; i < 100; i++) {
            String sql = "select tname from tb_history where uid='" + i + "'";
            int id;
            String tname;
            String tnamesum = "";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                tname = rs.getString("tname");
                tnamesum = tnamesum + tname + " ";
            }
            if (tnamesum == "") continue;
                //计算同一个uid的tname出现的次数
            else {
                System.out.print(i);
                System.out.print(tnamesum + "\n");
                String sql2="select count(*) num from tb_usertname where uid = '" + i + "'";
                try {
                    Statement sta = conn.createStatement();
                    ResultSet rs2 = sta.executeQuery(sql2);
                    int count = 0;
                    while (rs2.next()) {
                        count = rs2.getInt("num");
                    }
                    if (count == 0) {
                        java.sql.Statement stmt1 = conn.createStatement();
                        //执行 SQL
                        String sql1 = "insert into tb_usertname(uid,tnamesum) values('" + i + "','" +tnamesum + "')";
                        stmt1.executeUpdate(sql1);
                        System.out.println("数据库插入成功！！！");
                        break;
                    } else {
                        java.sql.Statement stmt2 = conn.createStatement();
                        String sql3="update tb_usertname set tnamesum ='"+tnamesum+"' where uid = '" + i+ "'";
                        stmt2.executeUpdate(sql3);
                        System.out.println("数据库更新成功！！！");
                        break;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }





    }


}


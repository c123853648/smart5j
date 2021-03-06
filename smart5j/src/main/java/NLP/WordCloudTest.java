package NLP;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizers.ChineseWordTokenizer;
import com.kennycason.kumo.palette.LinearGradientColorPalette;
import para.GlobalPara;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import java.awt.*;


public class WordCloudTest {




    public void test(int uid) throws IOException {
        //建立词频分析器，设置词频，以及词语最短长度，此处的参数配置视情况而定即可
        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        frequencyAnalyzer.setMinWordLength(2);

        //引入中文解析器
        frequencyAnalyzer.setWordTokenizer(new ChineseWordTokenizer());
        //指定文本文件路径，生成词频集合
        final List<WordFrequency> wordFrequencyList = frequencyAnalyzer.load(GlobalPara.NLP_uid +uid+".txt");
        //设置图片分辨率
        Dimension dimension = new Dimension(1920,1080);
        //此处的设置采用内置常量即可，生成词云对象
        com.kennycason.kumo.WordCloud wordCloud = new com.kennycason.kumo.WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        //设置边界及字体
        wordCloud.setPadding(2);
        java.awt.Font font = new java.awt.Font("STSong-Light", 2, 20);
        //设置词云显示的三种颜色，越靠前设置表示词频越高的词语的颜色
        wordCloud.setColorPalette(new LinearGradientColorPalette(Color.RED, Color.BLUE, Color.GREEN, 30, 30));
//        wordCloud.setKumoFont(new KumoFont(new File("E:\\smart5jRes\\msyh.ttc")));
        wordCloud.setKumoFont(new KumoFont(font));
        //设置背景色
        wordCloud.setBackgroundColor(new Color(255,255,255));
        //设置背景图片
        //wordCloud.setBackground(new PixelBoundryBackground("E:\\爬虫/google.jpg"));
        //设置背景图层为圆形
        wordCloud.setBackground(new CircleBackground(255));
        wordCloud.setFontScalar(new SqrtFontScalar(12, 45));
        //生成词云
        wordCloud.build(wordFrequencyList);

        wordCloud.writeToFile(GlobalPara.wordCloudPng+uid+".png");



    }
    public static void main() throws IOException, SQLException {

        java.sql.Connection conn;
        conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/community?characterEncoding=utf8&useSSL=false", "root", "123456");
        java.sql.Statement stmt = conn.createStatement();
        for(int i = 0; i<100; i++)
        {
            String sql = "select content from tb_moments_copy where uid='"+i+"' ";
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            String item="" ;
            String newString="";
            while(rs.next()){
                item=rs.getString("content")+item;
            }
            if(item !="" ){
                String item1="";
                int uid=i;
                String split_words = item;  //得到该列数据
                List<String> list = new ArrayList();
                // 将查询出的内容添加到list中，其中userName为数据库中的字段名称
                String[] words = split_words.split("\\|");
                for (int j=0;j<words.length;j++) {
                    System.out.print(j);
                    String key = words[j].split(":")[0];
                    if(!list.contains(key)){
                        list.add(key);
                    }
                }
                for(String word1:list)
                {
                    item1=item1+" "+word1;
                }


                File fp=new File(GlobalPara.NLP_uid+uid+".txt");
                PrintWriter pfp= new PrintWriter(fp);
                pfp.print(item1);
                pfp.close();
                WordCloudTest wordCloudTest=new WordCloudTest();
                wordCloudTest.test(uid);

            }
        }


    }
}

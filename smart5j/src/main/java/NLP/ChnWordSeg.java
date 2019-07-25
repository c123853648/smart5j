package NLP;


import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

//import scala.util.control.Exception;

public class ChnWordSeg {


    //中文分词
    public static String chnSeg(String content) {
        String strResult = "";
        try {
            //分词
            Result segWords = ToAnalysis.parse(content);//分词结果的一个封装，主要是一个List<Term>的terms
            //提取分词
            Iterator<Term> segTerms = segWords.iterator();
            StringBuffer strbuf = new StringBuffer();
            while (segTerms.hasNext()) {
                Term tm = segTerms.next();
                String strNs = tm.getNatureStr();//获取词性
                if (strNs == "null") continue;
                char cns = strNs.charAt(0);//取词性第一个字母
                //http://nlpchina.github.io/ansj_seg/content.html?name=词性说明
                if (cns == 'n' || cns == 't' || cns == 's' ||//名词、时间词、处所词
                        cns == 'f' || cns == 'v' || cns == 'a' || //方位词、动词、形容词
                        cns == 'b' || cns == 'z' || cns == 'r' ||//区别词、状态词、代词
                        strNs.equals("mq") || cns == 'q' || cns == 'd' ||//数词、数量词、副词
                        cns == 'y' || cns == 'x' || strNs.equals("en")) //语气词、字符串x、英文
                {
                    //介词p、连词c、助词u、叹词e、拟声词o、标点符号w、前缀h、后缀k不获取 ，数词m只获取其中mq数量词
                    String strNm = tm.getName(); //拿到词
                    strbuf.append(strNm + ":" + strNs + "|"); // 词：词性|
                }
                //strbuf.append("\r\n");//换行
            }
            strResult = strbuf.toString();
            strResult = strResult.substring(0, strResult.length() - 1);//截取最后一个字符|
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return strResult;
    }

    public static void chnSegSen(String content, ArrayList<String> currentList) {
        String strResult = "";
        try {
            //分词
            Result segWords = ToAnalysis.parse(content);
            //提取分词
            Iterator<Term> segTerms = segWords.iterator();
            StringBuffer strbuf = new StringBuffer();
            while (segTerms.hasNext()) {
                Term tm = segTerms.next();
                String strNs = tm.getNatureStr();//获取词性
                if (strNs == "null") continue;
                char cns = strNs.charAt(0);//取词性第一个字母
                //http://nlpchina.github.io/ansj_seg/content.html?name=词性说明
                if (cns == 'n' || cns == 't' || cns == 's' ||//名词、时间词、处所词
                        cns == 'f' || cns == 'v' || cns == 'a' || //方位词、动词、形容词
                        cns == 'b' || cns == 'z' || cns == 'r' ||//区别词、状态词、代词
                        strNs.equals("mq") || cns == 'q' || cns == 'd' ||//数词、数量词、副词
                        cns == 'y' || cns == 'x' || strNs.equals("en")) {//语气词、字符串x、英文
                    //介词p、连词c、助词u、叹词e、拟声词o、标点符号w、前缀h、后缀k不获取 ，数词m只获取其中mq数量词
                    String strNm = tm.getName();
                    strbuf.append(strNm + ":");
                    currentList.add(strNm);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main() throws SQLException, IOException {

        java.sql.Connection conn;
        conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/community?characterEncoding=utf8&useSSL=false", "root", "123456");
        java.sql.Statement stmt = conn.createStatement();

        String sql = "select * from tb_moments";
        java.sql.ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int id = rs.getInt("id");
            String content = ChnWordSeg.chnSeg(rs.getString("content"));
            String logo = rs.getString(("logo"));
            int uid = rs.getInt("uid");
            String createtime = rs.getString("createtime");

            System.out.print(content + "\n");

            String sql2 = "select count(*) num from tb_moments_copy where id = '" + id + "'";
            try {
                Statement sta = conn.createStatement();
                ResultSet rs2 = sta.executeQuery(sql2);
                int count = 0;
                while (rs2.next()) {
                    count = rs2.getInt("num");
                }
                if (count == 0) {
                    java.sql.Statement stmt1 = conn.createStatement();
                    String sql1 = "insert into tb_moments_copy(id,content,logo,uid,createtime) values('" + id + "','" + content + "','" + logo + "','" + uid + "','" + createtime + "')";
                    stmt1.executeUpdate(sql1);
                    System.out.println("数据库插入成功！！！");
                    continue;
                } else {
                    java.sql.Statement stmt2 = conn.createStatement();
                    String sql3 = "update tb_moments_copy set content='"+content+"',logo='"+logo+"',uid='"+uid+"',createtime='"+createtime+"' where id ='"+id+"'";
                    stmt2.executeUpdate(sql3);
                    System.out.println("数据库更新成功！！！");
                    continue;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
    }
}



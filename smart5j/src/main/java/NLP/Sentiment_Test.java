package NLP;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import para.GlobalPara;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
          
          
public class Sentiment_Test {  
          
            static private HashSet<String> Negative, Positive; //两种情感词典  
            static private Integer NegativeDoc, PositiveDoc, UnsureDoc;     //属于两种情感的文本数    - 所构建模型需要保存下的值  
            static private Hashtable<String, Integer> NegativeWeight, PositiveWeight, UnsureWeight;   //两种情感中所有词与他的权值 - 所构建模型需要保存下的值  
              
            public static void main(String[] args) throws Exception {  
                // TODO 自动生成的方法存根  
                Sentiment_Test Sentiment_Test = new Sentiment_Test();  
                  
                Sentiment_Test.Read_Model();                    //读取模型
                //E:\smart5j\smart5j\src\main\resources\500trainblogxml\negativeout
               Sentiment_Test.Classify_Directory("E:\\smart5j\\smart5j\\src\\main\\resources\\500trainblogxml\\negativeout\\");

//                Sentiment_Test.Classify_Directory("");
            }     


            @SuppressWarnings({ "resource", "unchecked" })  
            public void Read_Model() throws Exception {  
                  
                this.Read_Sentiment_Dictionary();  
                  
                ObjectInputStream OIS;      //对象流直接读入  
                File ModelPath = new File("Model");  
                File NegativeModel = new File(ModelPath, "NegativeModel.txt");  
                File PositiveModel = new File(ModelPath, "PositiveModel.txt");  
                File UnsureModel = new File(ModelPath, "UnsureModel.txt");  
                  
                System.out.println("Reading NegativeModel...");  
                OIS = new ObjectInputStream( new FileInputStream( NegativeModel ) );  
                NegativeDoc = (Integer) OIS.readObject();  
                NegativeWeight = (Hashtable<String, Integer>) OIS.readObject();  
                  
                System.out.println("Reading PositiveModel...");  
                OIS = new ObjectInputStream( new FileInputStream( PositiveModel ) );  
                PositiveDoc = (Integer) OIS.readObject();  
                PositiveWeight = (Hashtable<String, Integer>) OIS.readObject();  
                  
                System.out.println("Reading UnsureModel...");  
                OIS = new ObjectInputStream( new FileInputStream( UnsureModel ) );  
                UnsureDoc = (Integer) OIS.readObject();  
                UnsureWeight = (Hashtable<String, Integer>) OIS.readObject();  
                  
                System.out.println("Read Success.");  
            }  
              
            @SuppressWarnings("resource")  
            public void Read_Sentiment_Dictionary( ) throws Exception  {    //读入情感词典  
                BufferedReader buf;  
                String str;  
                  
                Negative = new HashSet<String>();  
                buf = new BufferedReader( new InputStreamReader(new FileInputStream(GlobalPara.ntusd_negative), "UTF-8") );
                while( (str = buf.readLine()) != null ) {  
                    Negative.add(str);  
                }  
                  
                Positive = new HashSet<String>();  
                buf = new BufferedReader( new InputStreamReader(new FileInputStream(GlobalPara.ntusd_positive), "UTF-8") );
                while( (str = buf.readLine()) != null ) {

                    Positive.add(str);  
                }  
            }  
              
            public void Classify_Directory( String DirectoryPath ) throws Exception {  
                  
                int PositiveNum = 0, NegativeNum = 0, UnsureNum = 0;  
                String[] Text_Path = new File( DirectoryPath ).list();  
                  
                for ( int i = 0; i < Text_Path.length; i ++ ) {  
                      
                    Classify( DirectoryPath+Text_Path[i] );  
                    double Ans = Classify( DirectoryPath+Text_Path[i] );    //对当前目录下的每一个文件进行测试  
                    if ( Ans < 0 ) {         //根据测试结果将测试文本进行分类
                        PositiveNum ++;  
                    }  
                    else if ( Ans > 0 ) {
                        NegativeNum ++;  
                    }  
                    else {
                        UnsureNum ++;  
                    }  
                    System.out.print( "No." + (i+1) + "  " + Text_Path[i] + ": " );  
                    if ( Ans < 0 ) { System.out.println("Positive"); }  
                    else if ( Ans > 0 ) {    System.out.println("Negative"); }  
                    else {  System.out.println("Unsure");   }  
                }  
                System.out.println("End.");  
                System.out.println("NegativeNum = " + NegativeNum + "  PositiveNum = " + PositiveNum + "  UnsureNum = " + UnsureNum);  
            }  

            public static Statement connectDB(){
                // JDBC 驱动名及数据库 URL
                String JDBC_DRIVER = "com.mysql.jdbc.Driver";
                String DB_URL = "jdbc:mysql://localhost:3306/community";

                // 数据库的用户名与密码，需要根据自己的设置
                String USER = "root";
                String PASS = "root";
                Connection conn = null;
                Statement stmt = null;
                try {
                    // 注册 JDBC 驱动
                    Class.forName("com.mysql.jdbc.Driver");
                    // 打开链接
                    System.out.println("连接数据库...");
                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    stmt = conn.createStatement();

                }catch (Exception e){
                    // 关闭资源
                    try{
                        if(stmt!=null) stmt.close();
                        if(conn!=null) conn.close();
                    }catch(SQLException se2){
                    }// 什么都不做
                }
                return stmt;
            }

            public static Hashtable<String, Integer> readDataFromDB(){
                Statement connectDB = Sentiment_Test.connectDB();

                return null;
            }

            public double Classify( String FilePath ) throws Exception {  
                  //获取情感词和情感词出现的次数
             Hashtable<String, Integer> FileHashTable = Read_TestFile( FilePath );
              //  添加输入源，从数据库中获取统计完毕的信息，信息格式{“a”：3，“b”：4}
               // Hashtable<String, Integer> FileHashTable = ChnWordSeg.GetDataFromDB( 1 );

                Enumeration<String> Keys;  
                double NegativeAns = 1, PositiveAns = 1;  
                  
                Keys = FileHashTable.keys();  
                while( Keys.hasMoreElements() ) {  
                    String Word = Keys.nextElement();
                    //PostProbability：后验概率  FileHashTable.get(Word)：测试语料中word的单词权重
                    NegativeAns *= ( Math.pow(this.PostProbability(Word, NegativeWeight), FileHashTable.get(Word)) );
                }  
                NegativeAns *= this.PriorProbability(NegativeDoc);  //PriorProbability：先验概率
                  
                Keys = FileHashTable.keys();  
                while( Keys.hasMoreElements() ) {  
                    String Word = Keys.nextElement();
                    PositiveAns *= ( Math.pow(this.PostProbability(Word, PositiveWeight), FileHashTable.get(Word)) );
                }
                PositiveAns *= this.PriorProbability(PositiveDoc);

                return ( NegativeAns-PositiveAns );//消极 NegativeAns减去积极PositiveAns
            }

            //得到测试集中的情感词以及情感词出现的次数
            public Hashtable <String, Integer> Read_TestFile( String FilePath ) throws Exception {
                  
                ArrayList<String> FileCurrentList = new ArrayList<String>();  
                ReadXML( FilePath, FileCurrentList );
                Hashtable<String, Integer> FileHashTable = HashTable( FileCurrentList );

                return FileHashTable;  
            }  
              
            public void ReadXML( String FilePath, ArrayList<String> currentList ) throws Exception {  //从指定路径读取XML文件并提取出其情感词返回
                //这儿改为从数据库导入数据
                  
                SAXReader SaxReader = new SAXReader();
                Document Doc = SaxReader.read(new File(FilePath));  
                Element root = Doc.getRootElement();
                  
                Element content = root.element("content");  
                List<?> sentenses = content.elements("sentence");  //每一句话作为一项
                  
                for ( Iterator<?> iter = sentenses.iterator(); iter.hasNext();  ) {  
                    Element sentense = (Element)iter.next();  
                      
                    List<?> toks = sentense.elements();  
                    for ( Iterator<?> iter1 = toks.iterator(); iter1.hasNext(); ) {  
                        Element tok = (Element)iter1.next();  
                        String Type = tok.attributeValue("type");  
                          
                        if ( Type.equals("group") ) {       //如果是"atom"一定不存在于情感词中  
                            GetWord( tok, currentList );    //从"group"中获取词  
                        }  
                    }  
                }  
            }  
              
            public void GetWord( Element root, ArrayList<String> currentList ) {      //获取XML中的情感词  
                  
                String Word = "";  
                List<?> elements = root.elements("tok");  
                for ( Iterator<?> iter = elements.iterator(); iter.hasNext(); ) {  
                    Element tok = (Element)iter.next();  
                    String Type = tok.attributeValue("type");  
                      
                    if ( Type.compareTo("atom") == 0 ) {  
                        Word += tok.getText().trim();  
                    }  
                    else {  
                        GetWord( tok, currentList );  
                    }  
                }  
                if ( Word.length() > 1 && (Positive.contains(Word) || Negative.contains(Word)) ) {  //筛选出情感词  
                    currentList.add(Word);  
                }  
            }  
              
            public Hashtable<String, Integer> HashTable( ArrayList<String> currentList ) {  //根据文本中的情感词构建哈希表  
                  
                Hashtable<String, Integer> HashTable = new Hashtable<String, Integer>();  
                  
                for ( Iterator<String> iter = currentList.iterator(); iter.hasNext();  ) {  
                    String Word = (String)iter.next();  
                    if ( HashTable.containsKey(Word) ) {  
                        Integer Weight = HashTable.get(Word);  
                        HashTable.put(Word, Weight+1);  
                    }  
                    else {  
                        HashTable.put(Word, 1);  
                    }  
                }  
                return HashTable;  
            }  
            //先验概率
            public double PriorProbability( Integer SentimentDoc ) {  
                  
                double Ans = 1;  
                  
                Ans = ( (double)SentimentDoc/( (double)NegativeDoc+(double)PositiveDoc+(double)UnsureDoc ) );  
                  
                return Ans;  
            }  
            //求后验概率
            public double PostProbability( String Word, Hashtable<String, Integer> SentimentWeight ) {  
                  
                double Ans, V, E;  
                double Weight = 0, Weights = 0;  
                  
                if ( SentimentWeight.containsKey(Word) )  
                    Weight = (double)SentimentWeight.get(Word);  
                  
                Weights = PostWeights( SentimentWeight );  
                  
                V = PostWeights( NegativeWeight ) + PostWeights( PositiveWeight ) + PostWeights( UnsureWeight );  
                E = 1/Math.abs(V);  //Math.abs(V):V的绝对值
                Ans = ( Weight + E )/( Weights + E*Math.abs(V) );  
                  
                return Ans;  
            }  
              
            public double PostWeights( Hashtable<String, Integer> SentimentWeight ) {  
                  
                double Weights = 0;  
                  
                Enumeration<String> Keys;  
                Keys = SentimentWeight.keys();  
                while( Keys.hasMoreElements() ) {  
                    String Key = Keys.nextElement();  
                    Weights += (double)SentimentWeight.get(Key);
                }  
                  
                return Weights;  
            }
        }
        

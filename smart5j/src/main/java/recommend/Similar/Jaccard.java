package recommend.Similar;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Jaccard 系数　 计算相关性
 */
public class Jaccard extends StringBaseCalculate implements Similarity {

    @Override
    public double distance(String s1, String s2) {
        if (s1 == null || s2 == null) {
            throw new NullPointerException("s1 must not be null");
        }
        if (s1.equals(s2)){
            return 1;
        }

        Map<String, Integer> h1 = getHashKey(s1);      //比较两个字符串的哈希
        Map<String, Integer> h2 = getHashKey(s2);

        Set<String> union = new HashSet<String>();

        union.addAll(h1.keySet());  //取出所有的键
        union.addAll(h2.keySet());

        int flag = 0;
        for (String key : union) {          //如果一个词语在两个字符串中都出现 flag+1
            if (h1.containsKey(key) && h2.containsKey(key)){   //在h1和h2集合中同时包含键名
                flag++;
            }            //flag越大相似度越高

        }

        return 1.0*flag / union.size();
    }

}

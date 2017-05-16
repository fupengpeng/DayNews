package edu.feicui.daynews.net;

import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/9/22.
 */
public class Utils {
    /**
     *
     * @param params  {"name":"zs","pwd":"123"}--->JSON
     * @return   ?name=zs&pwd=123
     */
    public static String getUrl(Map<String,String> params,int type){
      StringBuffer buffer=new StringBuffer();
      if(params!=null&&params.size()!=0){
          if(type==Constants.GET){
              buffer.append("?");
          }
          Set<String> keySet=params.keySet();
          for (String key:keySet) {
               buffer.append(key)
                       .append("=")
                       .append(params.get(key)).append("&");
          }
          buffer.deleteCharAt(buffer.length()-1);
      }

      return  buffer.toString();
    }
}

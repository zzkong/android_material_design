package lico.example.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import lico.example.Interface.JSONParserCompleteListener;
import lico.example.bean.HttpResponseEntity;
import lico.example.bean.ImageInfo;

/**
 * Created by Administrator on 2015/9/16.
 */
public class DataParser {

    public static void parserImageData(String string, final JSONParserCompleteListener listener,
                                       HttpResponseEntity response){
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            JSONObject jsonObject = new JSONObject(string);
            JSONArray ja = jsonObject.getJSONArray("newslist");
             List<ImageInfo> list = gson.fromJson(ja.toString(), new TypeToken<List<ImageInfo>>() {}.getType());
            listener.ParserCompleteListener(response, list);
        }catch (Exception e){
            e.printStackTrace();
            HttpResponseEntity responses = new HttpResponseEntity(101, "数据解析异常");
            listener.ParserCompleteListener(responses, null);
        }

    }
}

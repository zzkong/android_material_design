package lico.example.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import lico.example.Interface.JSONParserCompleteListener;
import lico.example.app.EApplication;
import lico.example.bean.HttpResponseEntity;
import lico.example.utils.ConstantURL;

/**
 * Created by zwl on 15/7/27.
 */
public class HttpManager {

    public static void getImages(String url, final JSONParserCompleteListener listener){
        VolleyClient.stringGet(url, new JSONParserCompleteListener() {
            @Override
            public void ParserCompleteListener(HttpResponseEntity response, Object object) {
                if(response.responseCode == 0){
                    DataParser.parserImageData(object.toString(), listener, response);
                }else{
                    listener.ParserCompleteListener(response, null);
                }
            }
        });
    }

    public static void doGet(Context context, String url, final JSONParserCompleteListener listener){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getAbsoluteUrl(url), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject json = new JSONObject(s);
                    int ret = json.getInt("ret");
                    String msg = json.getString("msg");
                    HttpResponseEntity response = new HttpResponseEntity(ret, msg);
                    listener.ParserCompleteListener(response, s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                HttpResponseEntity response = new HttpResponseEntity(101, "出现异常错误");
                listener.ParserCompleteListener(response, null);
                Log.e("", error.getMessage(), error);
            }
        });
        EApplication.addRequest(stringRequest, "");
    }

    /**
     * 得到正确的路径
     *
     * @return
     */
    private static String getAbsoluteUrl(String relativeUrl) {
        return ConstantURL.BASE_API_PATH + relativeUrl;
    }
}

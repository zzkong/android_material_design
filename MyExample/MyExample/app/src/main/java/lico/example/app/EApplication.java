package lico.example.app;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.okhttp.OkHttpClient;

import lico.example.http.OkHttpStack;
import lico.example.update.VolleyUtil;

/**
 * Created by zwl on 15/5/22.
 */
public class EApplication extends Application{

    private static EApplication instance = null;
    private RequestQueue mRequestQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Fresco.initialize(instance);
        VolleyUtil.initialize(instance);
    }

    public static EApplication getInstance(){
        if(instance == null){
            synchronized (EApplication.class){
                if(instance == null){
                    instance = new EApplication();
                }
            }
        }
        return instance;
    }

    public RequestQueue getVolleyRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(this, new OkHttpStack(new OkHttpClient()));
        }
        return mRequestQueue;
    }

    private static void addRequest(Request<?> request){
        getInstance().getVolleyRequestQueue().add(request);
    }

    public static void addRequest(Request<?> request, String tag){
        request.setTag(tag);
        addRequest(request);
    }

    public static void cancelAllRequests(String tag){
        if(getInstance().getVolleyRequestQueue() != null){
            getInstance().getVolleyRequestQueue().cancelAll(tag);
        }
    }
}

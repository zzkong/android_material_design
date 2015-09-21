package lico.example.update;

import com.android.volley.Response;

/**
 * Created by Administrator on 2015/8/24.
 */
public interface ResponseListener<T> extends Response.ErrorListener,Response.Listener<T> {

}

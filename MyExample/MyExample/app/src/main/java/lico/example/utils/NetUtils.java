package lico.example.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Locale;

/**
 * Created by Administrator on 2015/10/26.
 */
public class NetUtils {

    public static enum NetType{
        WIFI, CMNET, CMWAP, NONE
    }

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = mgr.getAllNetworkInfo();
        if(null != info){
            for (int i =0; i < info.length; i++){
                if(info[i].getState() == NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }
        return false;
    }

    public static NetType getAPNType(Context context){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(null == networkInfo){
            return NetType.NONE;
        }
        int nType = networkInfo.getType();

        if(nType == ConnectivityManager.TYPE_MOBILE){
            if(networkInfo.getExtraInfo().toLowerCase(Locale.getDefault()).equals("cmnet")){
                return NetType.CMNET;
            }else {
                return NetType.CMWAP;
            }
        }else if(nType == ConnectivityManager.TYPE_WIFI){
            return NetType.WIFI;
        }
        return NetType.NONE;
    }
}

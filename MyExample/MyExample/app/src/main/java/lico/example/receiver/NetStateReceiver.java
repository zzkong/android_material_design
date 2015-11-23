package lico.example.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.ArrayList;

import lico.example.http.NetChangeObserver;
import lico.example.utils.NetUtils;

/**
 * Created by Administrator on 2015/10/26.
 */
public class NetStateReceiver extends BroadcastReceiver{

    public final static String CUSTOM_ANDROID_NET_CHANGE_ACTION = "com.github.obsessive.library.net.conn.CONNECTIVITY_CHANGE";
    private final static String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private final static String TAG = NetStateReceiver.class.getSimpleName();

    private static boolean isNetAvailable = false;
    private static NetUtils.NetType mNetType;
    private static ArrayList<NetChangeObserver> mNetChangeObservers = new ArrayList<NetChangeObserver>();
    private static BroadcastReceiver mBroadcastReceiver;

    private static BroadcastReceiver getReceiver(){
        if(mBroadcastReceiver == null){
            mBroadcastReceiver = new NetStateReceiver();
        }
        return mBroadcastReceiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mBroadcastReceiver = NetStateReceiver.this;
        if(intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION) || intent.getAction().equalsIgnoreCase(CUSTOM_ANDROID_NET_CHANGE_ACTION)){
            if(!NetUtils.isNetworkAvailable(context)){

                isNetAvailable = false;
            }else {
                isNetAvailable = true;
                mNetType = NetUtils.getAPNType(context);
            }
            notifyObserver();
        }

    }

    public static void registerNetworkStateReceiver(Context mContext){
        IntentFilter filter = new IntentFilter();
        filter.addAction(CUSTOM_ANDROID_NET_CHANGE_ACTION);
        filter.addAction(ANDROID_NET_CHANGE_ACTION);
        mContext.getApplicationContext().registerReceiver(getReceiver(), filter);
    }

    public static void checkNetworkState(Context mContext){
        Intent intent = new Intent();
        intent.setAction(CUSTOM_ANDROID_NET_CHANGE_ACTION);
        mContext.sendBroadcast(intent);
    }

    public static boolean isNetworkAvailable() {
        return isNetAvailable;
    }

    public static NetUtils.NetType getAPNType(){
        return mNetType;
    }

    private void notifyObserver(){
        if(!mNetChangeObservers.isEmpty()){
           int size = mNetChangeObservers.size();
            for (int i= 0;i<size;i++){
                NetChangeObserver observer = mNetChangeObservers.get(i);
                if(observer != null){
                    if(isNetworkAvailable()){
                        observer.onNetConnected(mNetType);
                    }else {
                        observer.onNetDisConnect();
                    }
                }
            }
        }
    }

    public static void registerObserver(NetChangeObserver observer){
        if(mNetChangeObservers == null){
            mNetChangeObservers = new ArrayList<NetChangeObserver>();
        }
        mNetChangeObservers.add(observer);
    }

    public static void removeRegisterObserver(NetChangeObserver observer){
        if(mNetChangeObservers != null){
            if(mNetChangeObservers.contains(observer)){
                mNetChangeObservers.remove(observer);
            }
        }
    }
}

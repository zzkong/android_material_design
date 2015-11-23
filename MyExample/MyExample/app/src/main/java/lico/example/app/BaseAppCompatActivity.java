package lico.example.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.simple.eventbus.EventBus;

import butterknife.ButterKnife;
import lico.example.R;
import lico.example.http.NetChangeObserver;
import lico.example.loading.VaryViewHelperController;
import lico.example.receiver.NetStateReceiver;
import lico.example.utils.NetUtils;
import lico.example.utils.Utils;

/**
 * Created by Administrator on 2015/10/26.
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {

    protected static String TAG_LOG = null;

    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;

    protected Context mContext = null;

    protected NetChangeObserver mNetChangeObserver = null;

    private VaryViewHelperController mVaryViewHelperController = null;

    public enum TransitionMode{
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(toggleOverridePendingTransition()){
            switch (getOverridePendingTransitionMode()){
                case LEFT:
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in, R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in,R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    break;
            }
        }
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if(null != extras){
            getBundleExtras(extras);
        }

        if(isBindEventBusHere()){
            EventBus.getDefault().register(this);
        }

        mContext = this;
        TAG_LOG = this.getClass().getSimpleName();
        BaseAppManager.getInstance().addActivity(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;

        if(getContentViewLayoutID() != 0){
            setContentView(getContentViewLayoutID());
        }else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }

        mNetChangeObserver = new NetChangeObserver(){
            @Override
            public void onNetConnected(NetUtils.NetType type) {
                super.onNetConnected(type);
                onNetworkConnected(type);
            }

            @Override
            public void onNetDisConnect() {
                super.onNetDisConnect();
                onNetworkDisConnected();
            }
        };

        NetStateReceiver.registerObserver(mNetChangeObserver);

        initViewsAndEvents();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        if(null != getLoadingTargetView()){
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }
    }

    @Override
    public void finish() {
        super.finish();
        BaseAppManager.getInstance().removeActivity(this);
        if(toggleOverridePendingTransition()){
            switch (getOverridePendingTransitionMode()){
                case LEFT:
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in, R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
        if(isBindEventBusHere()){
            EventBus.getDefault().register(this);
        }
    }

    protected void readyGo(Class<?> clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void readyGo(Class<?> clazz, Bundle bundle){
        Intent intent = new Intent(this, clazz);
        if(null != bundle){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void readyGoThenKill(Class<?> clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    protected void readyGoThenKill(Class<?> clazz, Bundle bundle){
        Intent intent = new Intent(this, clazz);
        if(null != bundle){
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    protected void readyGoForResult(Class<?> clazz, int requestCode){
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle){
        Intent intent = new Intent(this, clazz);
        if(null != bundle){
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    protected void showToast(String msg){
        if(!Utils.isEmpty(msg)){
            Snackbar.make(getWindow().getDecorView(), msg, Snackbar.LENGTH_LONG).show();
        }
    }

    protected void toggleShowLoading(boolean toggle, String msg){
        if(null == mVaryViewHelperController){
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if(toggle){
            mVaryViewHelperController.showLoading(msg);
        }else {
            mVaryViewHelperController.restore();
        }
    }

    protected void toggleShowEmpty(boolean toggle, String msg, View.OnClickListener onClickListener){
        if(null == mVaryViewHelperController){
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if(toggle){
            mVaryViewHelperController.showEmpty(msg, onClickListener);
        }else {
            mVaryViewHelperController.restore();
        }
    }

    protected void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showError(msg, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    protected void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showNetworkError(onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * use SytemBarTintManager
     *
     * @param tintDrawable
     */
    protected void setSystemBarTintDrawable(Drawable tintDrawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            if (tintDrawable != null) {
                mTintManager.setStatusBarTintEnabled(true);
                mTintManager.setTintDrawable(tintDrawable);
            } else {
                mTintManager.setStatusBarTintEnabled(false);
                mTintManager.setTintDrawable(null);
            }
        }

    }

    /**
     * set status bar translucency
     *
     * @param on
     */
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    protected abstract void initViewsAndEvents();

    protected abstract View getLoadingTargetView();

    protected abstract int getContentViewLayoutID();

    protected abstract boolean isBindEventBusHere();

    protected abstract void onNetworkDisConnected();

    protected abstract void onNetworkConnected(NetUtils.NetType type);

    protected abstract boolean toggleOverridePendingTransition();

    protected abstract void getBundleExtras(Bundle extras);

    protected abstract TransitionMode getOverridePendingTransitionMode();
}

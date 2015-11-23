package lico.example.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import lico.example.InterfaceView.SplashView;
import lico.example.R;
import lico.example.app.BaseActivity;
import lico.example.presenter.Presenter;
import lico.example.presenter.impl.SplashPresenterImpl;
import lico.example.utils.NetUtils;

/**
 * Created by zwl on 2015/8/29.
 */
public class SplashActivity extends BaseActivity implements SplashView {


    @Bind(R.id.splash_image)
    ImageView splashImage;
    @Bind(R.id.splash_version_name)
    TextView splashVersionName;
    @Bind(R.id.splash_copyright)
    TextView splashCopyright;

    private Presenter mSplashPresenter = null;

    @Override
    protected void initViewsAndEvents() {
        mSplashPresenter = new SplashPresenterImpl(this, this);
        mSplashPresenter.initialized();
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    public void animateBackgroundImage(Animation animation) {
        splashImage.startAnimation(animation);
    }

    @Override
    public void initializwViews(String versionName, String copyright, int backgroundResId) {
        splashVersionName.setText(versionName);
        splashCopyright.setText(copyright);
        splashImage.setImageResource(backgroundResId);
    }

    @Override
    public void navigateToHomePage() {
        readyGoThenKill(HomeActivity.class);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}

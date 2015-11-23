package lico.example.presenter.impl;

import android.content.Context;
import android.view.animation.Animation;

import lico.example.interactor.SplashInteractor;
import lico.example.InterfaceView.SplashView;
import lico.example.interactor.impl.SplashinteractorImpl;
import lico.example.presenter.Presenter;

/**
 * Created by Administrator on 2015/10/27.
 */
public class SplashPresenterImpl implements Presenter{

    private Context mContext = null;
    private SplashView mSplashView = null;
    private SplashInteractor mSplashInteractor = null;

    public SplashPresenterImpl(Context context, SplashView splashView) {
        if(null == splashView){
            throw new IllegalArgumentException("Constructor's parameters must not be Null");
        }

        mContext = context;
        mSplashView = splashView;
        mSplashInteractor = new SplashinteractorImpl();
    }

    @Override
    public void initialized() {
        mSplashView.initializwViews(mSplashInteractor.getVersionName(mContext),
                mSplashInteractor.getCopyright(mContext),
                mSplashInteractor.getBackgroundImageResID());

        Animation animation = mSplashInteractor.getBackgroundImageAnimation(mContext);
        animation.setAnimationListener(new Animation.AnimationListener(){

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mSplashView.navigateToHomePage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mSplashView.animateBackgroundImage(animation);
    }
}

package lico.example.InterfaceView;

import android.view.animation.Animation;

/**
 * Created by Administrator on 2015/10/27.
 */
public interface SplashView {

    void animateBackgroundImage(Animation animation);

    void initializwViews(String versionName, String copyright, int backgroundResId);

    void navigateToHomePage();
}

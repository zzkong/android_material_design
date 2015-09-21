package lico.example.activity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.ImageView;

import butterknife.Bind;
import lico.example.R;
import lico.example.app.BaseActivity;
import lico.example.utils.Utils;

/**
 * Created by zwl on 2015/8/29.
 */
public class SplashActivity extends BaseActivity {


    @Bind(R.id.splash_image)
    ImageView splashImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Animation animation = Utils.getBackgroundImageAnimation(this);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                readyGoThenKill(HomeActivity.class);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        splashImage.setAnimation(animation);

    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initToolbar() {}
}

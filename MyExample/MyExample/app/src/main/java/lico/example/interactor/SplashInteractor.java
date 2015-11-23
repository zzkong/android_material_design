package lico.example.interactor;

import android.content.Context;
import android.view.animation.Animation;

/**
 * Created by Administrator on 2015/10/27.
 */
public interface SplashInteractor {

    int getBackgroundImageResID();

    String getCopyright(Context context);

    String getVersionName(Context context);

    Animation getBackgroundImageAnimation(Context context);
}

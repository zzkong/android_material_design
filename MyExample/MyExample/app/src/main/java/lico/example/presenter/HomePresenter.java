package lico.example.presenter;

import android.graphics.Bitmap;
import android.widget.ImageView;

import lico.example.fragment.BaseFragment;

/**
 * Created by Administrator on 2015/10/28.
 */
public interface HomePresenter {

    void initialized();

    void blurBitmap(ImageView imageview, Bitmap bitmap);

    void setNewRootFragment(BaseFragment baseFragment, int index);
}

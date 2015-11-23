package lico.example.loading;

import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2015/10/26.
 */
public interface IVaryViewHelper {

    public abstract View getCurrentLayout();

    public abstract void restoreView();

    public abstract void showLayout(View view);

    public abstract View inflate(int layoutId);

    public abstract Context getContext();

    public abstract View getView();
}

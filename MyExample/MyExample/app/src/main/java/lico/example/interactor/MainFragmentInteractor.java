package lico.example.interactor;

import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by Administrator on 2015/11/9.
 */
public interface MainFragmentInteractor {

    List<Fragment> getFragments();

    List<String> getTitles(Context context);
}

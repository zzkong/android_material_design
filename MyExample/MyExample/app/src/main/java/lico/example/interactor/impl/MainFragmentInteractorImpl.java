package lico.example.interactor.impl;

import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import lico.example.interactor.MainFragmentInteractor;
import lico.example.R;

/**
 * Created by Administrator on 2015/11/9.
 */
public class MainFragmentInteractorImpl implements MainFragmentInteractor {



    @Override
    public List<Fragment> getFragments() {
        return null;
    }

    @Override
    public List<String> getTitles(Context context) {
        List<String> titleList = new ArrayList<>();
        String[] titles = context.getResources().getStringArray(R.array.images_category_list);
        for(int i = 0; i < titles.length; i++){
            titleList.add(titles[i]);
        }
        return titleList;
    }
}

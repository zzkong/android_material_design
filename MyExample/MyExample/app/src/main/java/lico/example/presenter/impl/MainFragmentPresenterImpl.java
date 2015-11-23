package lico.example.presenter.impl;

import android.content.Context;

import lico.example.interactor.MainFragmentInteractor;
import lico.example.InterfaceView.MainFragmentView;
import lico.example.interactor.impl.MainFragmentInteractorImpl;
import lico.example.presenter.Presenter;

/**
 * Created by Administrator on 2015/11/9.
 */
public class MainFragmentPresenterImpl implements Presenter{

    private Context mContext;
    private MainFragmentView mMainFragmentView;
    private MainFragmentInteractor mMainFragmentInteractor;

    public MainFragmentPresenterImpl(Context mContext, MainFragmentView mainFragmentView) {
        this.mContext = mContext;
        this.mMainFragmentView = mainFragmentView;
        mMainFragmentInteractor = new MainFragmentInteractorImpl();
    }

    @Override
    public void initialized() {
        mMainFragmentView.initializePagerViews(mMainFragmentInteractor.getTitles(mContext));
    }



}

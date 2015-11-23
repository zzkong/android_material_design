package lico.example.presenter.impl;

import android.content.Context;

import lico.example.listener.AsyncListener;
import lico.example.interactor.CommonListInteractor;
import lico.example.InterfaceView.ImagesListView;
import lico.example.interactor.impl.CommonListInteractorImpl;
import lico.example.bean.ImagesListEntity;
import lico.example.bean.ResponseImagesListEntity;
import lico.example.presenter.ImagesListPresenter;
import lico.example.utils.Constants;

/**
 * Created by zwl on 2015/11/10.
 */
public class ImagesListPresenterImpl implements ImagesListPresenter, AsyncListener<ResponseImagesListEntity> {

    private Context mContext = null;
    private ImagesListView mImagesListView;
    private CommonListInteractor mCommonListInteractor;

    public ImagesListPresenterImpl(Context mContext, ImagesListView mImagesListView) {
        this.mContext = mContext;
        this.mImagesListView = mImagesListView;
        mCommonListInteractor = new CommonListInteractorImpl(this);
    }

    @Override
    public void loadData(int eventTag, String keyword, int page) {
        mCommonListInteractor.getCommonListData(eventTag, keyword, page);
    }

    @Override
    public void onItemClickListener(int position, ImagesListEntity imagesListEntity, int x, int y, int width, int height) {
        mImagesListView.navigateToDetail(position, imagesListEntity, x, y, width, height);
    }

    @Override
    public void onSuccess(int eventTag, ResponseImagesListEntity data) {
        if (eventTag == Constants.REFRESH_DATA) {
            mImagesListView.refreshListData(data);
        } else
            mImagesListView.addMoreListData(data);
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onException(String msg) {

    }
}

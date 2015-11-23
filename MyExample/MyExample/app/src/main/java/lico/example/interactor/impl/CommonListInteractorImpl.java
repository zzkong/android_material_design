package lico.example.interactor.impl;

import lico.example.listener.AsyncListener;
import lico.example.interactor.CommonListInteractor;
import lico.example.listener.JSONParserCompleteListener;
import lico.example.bean.HttpResponseEntity;
import lico.example.bean.ResponseImagesListEntity;
import lico.example.http.HttpManager;

/**
 * Created by Administrator on 2015/11/10.
 */
public class CommonListInteractorImpl implements CommonListInteractor {

    private AsyncListener<ResponseImagesListEntity> mAsyncListener = null;

    public CommonListInteractorImpl(AsyncListener<ResponseImagesListEntity> asyncListener) {
        this.mAsyncListener = asyncListener;
    }

    @Override
    public void getCommonListData(final int eventTag, String keyword, int page) {
        HttpManager.getImages(keyword, page, new JSONParserCompleteListener() {
            @Override
            public void ParserCompleteListener(HttpResponseEntity response, Object object) {
                if (response.responseCode == 0) {
                    ResponseImagesListEntity entity = (ResponseImagesListEntity) object;
                    mAsyncListener.onSuccess(eventTag, entity);
                } else {
                    mAsyncListener.onError(response.errorMsg);
                }
            }
        });
    }
}

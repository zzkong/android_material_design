package lico.example.presenter;

import lico.example.bean.ImagesListEntity;

/**
 * Created by Administrator on 2015/11/10.
 */
public interface ImagesListPresenter {

    void loadData(int eventTag, String keyword, int page);

    void onItemClickListener(int position, ImagesListEntity imagesListEntity, int x, int y, int width, int height);
}

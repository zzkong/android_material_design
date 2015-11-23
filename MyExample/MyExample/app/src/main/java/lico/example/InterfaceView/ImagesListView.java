package lico.example.InterfaceView;

import lico.example.app.BaseView;
import lico.example.bean.ImagesListEntity;
import lico.example.bean.ResponseImagesListEntity;

/**
 * Created by zwl on 2015/11/10.
 */
public interface ImagesListView extends BaseView{
    void refreshListData(ResponseImagesListEntity data);

    void addMoreListData(ResponseImagesListEntity data);

    void navigateToDetail(int position, ImagesListEntity imagesListEntity, int x, int y, int width, int height);
}

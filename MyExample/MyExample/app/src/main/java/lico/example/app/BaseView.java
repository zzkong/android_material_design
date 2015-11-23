package lico.example.app;

/**
 * Created by Administrator on 2015/10/26.
 */
public interface BaseView {

    void showLoading(String msg);

    void hideLoading();

    void showError(String msg);

    void showException(String msg);

    void showNetError();
}

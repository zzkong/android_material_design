package lico.example.listener;

/**
 * Created by zwl on 2015/10/28.
 * 弄了个异步操作的回调接口。由于以前有一个JSONParserCompleteListener是处理网络数据回调的。
 * 固这里写个异步回调的；如访问数据库、压缩图片、模糊图片等其他耗时操作
 */
public interface AsyncListener<T> {

    void onSuccess(int eventTag, T data);

    void onError(String msg);

    void onException(String msg);
}

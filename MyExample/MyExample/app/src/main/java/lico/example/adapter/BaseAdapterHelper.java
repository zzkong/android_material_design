package lico.example.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import lico.example.R;
import lico.example.app.EApplication;
import lico.example.views.RatioImageView;

/**
 * Created by zwl on 2015/8/25.
 */
public class BaseAdapterHelper extends RecyclerView.ViewHolder {

    private SparseArray<View> views;


    public BaseAdapterHelper(View itemView) {
        super(itemView);
        this.views = new SparseArray<View>();
    }

    public void setText(int viewId, String str){
        View view = retrieveView(viewId);
        if(view instanceof TextView){
            ((TextView) view).setText(str);
        }else if(view instanceof Button){
            ((Button) view).setText(str);
        }
    }

    public void setImageByUrl(int viewId, String url){
        View view = retrieveView(viewId);
        if(view instanceof RatioImageView){    //普通图片用glide加载
            Glide.with(EApplication.getInstance()).load(url)
            .placeholder(R.drawable.night)
            .crossFade().into((RatioImageView)view);
        }
    }

    public TextView getTextView(int viewId) {
        return retrieveView(viewId);
    }

    public Button getButton(int viewId) {
        return retrieveView(viewId);
    }

    public ImageView getImageView(int viewId) {
        return retrieveView(viewId);
    }

    public View getView(int viewId) {
        return retrieveView(viewId);
    }

    protected <T extends View> T retrieveView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }
}

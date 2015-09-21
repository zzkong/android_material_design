package lico.example.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lico.example.R;
import lico.example.bean.ImageInfo;

/**
 * Created by zwl on 15/7/10.
 */
public class ImageAdapter extends BaseRecyclerViewAdapter<ImageInfo>{

    private final List<ImageInfo> imageInfos;
    private int upDownFactor = 1;
    private boolean isShowScaleAnimate = true;


    public ImageAdapter(List<ImageInfo> list) {
        super(list);
        imageInfos = new ArrayList<ImageInfo>(list);
    }

    public ImageAdapter(List<ImageInfo> list, Context mContext) {
        super(list, mContext);
        imageInfos = new ArrayList<ImageInfo>(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        final View view = LayoutInflater.from(context).inflate(R.layout.view_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ImageViewHolder viewHolder = (ImageViewHolder)holder;
        ImageInfo info = list.get(position);
        if(null == info){
            return;
        }
        viewHolder.setTitle(info.title);
        animate(holder, position);
    }

    @Override
    protected Animator[] getAnimators(View view) {
        if (view.getMeasuredHeight() <=0 || isShowScaleAnimate){
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.1f, 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.1f, 1f);
            return new ObjectAnimator[]{scaleX, scaleY};
        }
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "scaleX", 1.1f, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 1.1f, 1f),
                ObjectAnimator.ofFloat(view, "translationY", upDownFactor * 2.0f * view.getMeasuredHeight(), 0)
        };
    }


}

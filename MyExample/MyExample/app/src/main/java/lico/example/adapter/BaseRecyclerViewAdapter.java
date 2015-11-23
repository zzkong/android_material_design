package lico.example.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lico.example.utils.ViewHelper;

/**
 * Created by zwl on 15/7/10.
 */
public abstract class BaseRecyclerViewAdapter<E> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    protected Context mContext;

    protected int mDuration = 300;

    private Interpolator mInterpolator = new LinearInterpolator();

    private int mLastPosition = -1;

    private boolean isFirstOnly = true;

    protected List<E> list;

    private Map<Integer, onInternalClickListener<E>> canClickItem;

    public BaseRecyclerViewAdapter(List<E> list) {
        this(list, null);
    }

    public BaseRecyclerViewAdapter(List<E> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    public void add(E e){
        this.list.add(0, e);
        notifyItemInserted(0);
    }
    public void remove(E e){
        this.list.remove(e);
        notifyDataSetChanged();
    }
    public void remove(int position){
        this.list.remove(position);
        notifyDataSetChanged();
    }
    public void setList(List<E> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    public List<E> getList(){
        return list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder != null){
                addInternalClickListener(holder.itemView, position, list.get(position));
            }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void addInternalClickListener(final View itemV, final Integer position, final E valuesMap){
        if(canClickItem != null){
            for(Integer key : canClickItem.keySet()){
                View inView = itemV.findViewById(key);
                final onInternalClickListener<E> listener = canClickItem.get(key);
                if(inView != null && listener != null){
                    inView.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            listener.OnClickListener(itemV, view, position, valuesMap);
                        }
                    });
                    inView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            listener.OnLongClickListener(itemV, view, position,
                                    valuesMap);
                            return true;
                        }
                    });
                }
            }
        }
    }

    public void setOnInViewClickListener(Integer key, onInternalClickListener<E> onClickListener){
        if(canClickItem == null)
            canClickItem = new HashMap<Integer, onInternalClickListener<E>>();
            canClickItem.put(key, onClickListener);

    }

    public interface onInternalClickListener<T>{
        void OnClickListener(View parentV, View v, Integer position, T values);

        void OnLongClickListener(View parentV, View v, Integer position, T values);
    }

    public static class onInternalClickListenerImpl<T> implements onInternalClickListener<T>{
        @Override
        public void OnClickListener(View parentV, View v, Integer position, T values) {

        }

        @Override
        public void OnLongClickListener(View parentV, View v, Integer position, T values) {

        }
    }
    public void setDuration(int duration) {
        mDuration = duration;
    }

    public void setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
    }

    public void setStartPosition(int start) {
        mLastPosition = start;
    }

    public void setFirstOnly(boolean firstOnly) {
        isFirstOnly = firstOnly;
    }

    protected void animate(RecyclerView.ViewHolder holder, int position){
        if (!isFirstOnly || position > mLastPosition) {
            for (Animator anim : getAnimators(holder.itemView)) {
                anim.setDuration(mDuration).start();
                anim.setInterpolator(mInterpolator);

            }
            mLastPosition = position;
        } else {
            ViewHelper.clear(holder.itemView);
        }
    }

    protected abstract Animator[] getAnimators(View view);
}

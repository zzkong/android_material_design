package lico.example.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by 赵万林 on 15/5/4.
 * 通用的ListView GridView的Adapter， 包括用ImageLoader设置网络图片
 */
public abstract class SimpleBaseAdapter<T> extends BaseAdapter {

    protected LayoutInflater mInflater;
    protected static Context context;
    protected List<T> data;
    protected final int mItemLayoutId;


    public SimpleBaseAdapter(Context context, List<T> data, int itemLayoutId) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data == null ? new ArrayList<T>() : data;
        this.mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView, parent);
        convert(viewHolder, getItem(position));
        return viewHolder.getConvertView();
    }


    public abstract void convert(ViewHolder helper, T item);

    private ViewHolder getViewHolder(int position, View convertView,
                                     ViewGroup parent) {
        return ViewHolder.get(context, convertView, parent, mItemLayoutId,
                position);
    }

    public static class ViewHolder {
        private SparseArray<View> views = new SparseArray<View>();
        private View convertView;
        private int position;

        public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
            this.position = position;
            this.views = new SparseArray<View>();
            convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            //setTag
            convertView.setTag(this);
        }

        public static ViewHolder get(Context context, View convertView,
                                     ViewGroup parent, int layoutId, int position) {
            if (null == convertView) {
                return new ViewHolder(context, parent, layoutId, position);
            }
            return (ViewHolder) convertView.getTag();

        }

        public View getConvertView() {
            return convertView;
        }


        public <T extends View> T getView(int resId) {
            View v = views.get(resId);
            if (null == v) {
                v = convertView.findViewById(resId);
                views.put(resId, v);
            }
            return (T) v;
        }

        /**
         * @param viewId
         * @param url
         * @return
         * 加载网络图片
         */
        public ViewHolder setImageByUrl(int viewId, String url) {
            View view = getView(viewId);

            return this;
        }

        /**
         * @param viewId
         * @param listener
         * @return
         * 控件点击事件
         */
        public ViewHolder buttonClick(final int viewId, View.OnClickListener listener) {
            Button button = getView(viewId);
            button.setOnClickListener(listener);
            return this;
        }

        public ViewHolder itemClick(int viewId, AdapterView.OnItemClickListener listener){
            View view = getView(viewId);
            if(view instanceof GridView){
                ((GridView) view).setOnItemClickListener(listener);
            }
            return this;
        }

        /**
         * @param viewId
         * @param text
         * @return
         * settext事件
         */
        public ViewHolder setText(int viewId, String text) {
            View view = getView(viewId);
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            } else if (view instanceof Button) {
                ((Button) view).setText(text);
            }
            return this;
        }

        public int getPosition() {
            return position;
        }
    }

    public void addAll(List<T> elem) {
        data.addAll(elem);
        notifyDataSetChanged();
    }

    public void remove(T elem) {
        data.remove(elem);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        data.remove(index);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
        data.clear();
        data.addAll(elem);
        notifyDataSetChanged();
    }
}

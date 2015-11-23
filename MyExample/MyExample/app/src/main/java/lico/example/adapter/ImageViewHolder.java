package lico.example.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import lico.example.R;

/**
 * Created by zwl on 15/7/10.
 */
public class ImageViewHolder extends RecyclerView.ViewHolder{

    private final TextView title;

    public ImageViewHolder(View parent) {
        super(parent);
        title = (TextView) parent.findViewById(R.id.title);
    }

    public void setTitle(CharSequence text){
        setTextView(title, text);
    }

    private void setTextView(TextView view, CharSequence text){
        if(view == null || TextUtils.isEmpty(text))
            return;
            view.setText(text);

    }
}

package lico.example.loading;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import lico.example.R;
import lico.example.utils.Utils;

/**
 * Created by Administrator on 2015/10/26.
 */
public class VaryViewHelperController {

    private IVaryViewHelper helper;

    public VaryViewHelperController(View view){
        this(new VaryViewHelper(view));
    }

    public VaryViewHelperController(IVaryViewHelper helper){
        super();
        this.helper = helper;
    }

    public void showNetworkError(View.OnClickListener onClickListener){
        View layout = helper.inflate(R.layout.message);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        textView.setText(helper.getContext().getResources().getString(R.string.common_no_network_msg));

        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        imageView.setImageResource(R.drawable.ic_exception);

        if(null != onClickListener){
            layout.setOnClickListener(onClickListener);
        }

        helper.showLayout(layout);
    }

    public void showError(String errorMsg, View.OnClickListener onClickListener){
        View layout = helper.inflate(R.layout.message);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        if(!Utils.isEmpty(errorMsg)){
            textView.setText(errorMsg);
        }else {
            textView.setText(helper.getContext().getResources().getString(R.string.common_error_msg));
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        imageView.setImageResource(R.drawable.ic_error);

        if(null != onClickListener){
            layout.setOnClickListener(onClickListener);
        }

        helper.showLayout(layout);
    }

    public void showEmpty(String emptyMsg, View.OnClickListener onClickListener){
        View layout = helper.inflate(R.layout.message);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        if(!Utils.isEmpty(emptyMsg)){
            textView.setText(emptyMsg);
        }else {
            textView.setText(helper.getContext().getResources().getString(R.string.common_empty_msg));
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        imageView.setImageResource(R.drawable.ic_exception);

        if(null != onClickListener){
            layout.setOnClickListener(onClickListener);
        }

        helper.showLayout(layout);
    }

    public void showLoading(String msg){
        View layout = helper.inflate(R.layout.view_loading);
        if(!Utils.isEmpty(msg)){
            TextView textView = (TextView) layout.findViewById(R.id.loading_msg);
            textView.setText(msg);
        }
        helper.showLayout(layout);
    }

    public void restore(){
        helper.restoreView();
    }
}

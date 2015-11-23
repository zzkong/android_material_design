package lico.example.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.ButterKnife;
import lico.example.R;
import lico.example.activity.HomeActivity;
import lico.example.app.BaseView;

/**
 * Created by Administrator on 2015/9/28.
 */
public abstract class BaseFragment extends BaseLazyFragment implements BaseView{
    Toolbar mToolbar;

    public HomeActivity getHomeActivity(){
        return (HomeActivity)super.getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbar(view);
    }

    protected void setToolbar(View view){
        if(!hasCustomToolbar())return;
        mToolbar = ButterKnife.findById(view, getToolbarId());
        mToolbar.setTitle(getTitle());
        mToolbar.setNavigationIcon(R.drawable.ic_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHomeActivity().openDrawer();
            }
        });
    }

    protected int getToolbarId(){
        return R.id.toolbar;
    }

    public boolean hasCustomToolbar(){
        return false;
    }

    protected int getTitle(){
        return R.string.string_no_title;
    }

    @Override
    public void showError(String msg) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showException(String msg) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showNetError() {
        toggleNetworkError(true, null);
    }

    @Override
    public void showLoading(String msg) {
        toggleShowLoading(true, null);
    }

    @Override
    public void hideLoading() {
        toggleShowLoading(false, null);
    }
}

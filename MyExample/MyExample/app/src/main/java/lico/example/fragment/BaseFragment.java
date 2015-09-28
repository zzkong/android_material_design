package lico.example.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import lico.example.R;
import lico.example.activity.HomeActivity;

/**
 * Created by Administrator on 2015/9/28.
 */
public abstract class BaseFragment extends Fragment{
    Toolbar mToolbar;

    public HomeActivity getHomeActivity(){
        return (HomeActivity)super.getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        return view;
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

    protected abstract int getLayout();
}

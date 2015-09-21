package lico.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lico.example.Interface.JSONParserCompleteListener;
import lico.example.R;
import lico.example.activity.SwipeListViewActivity;
import lico.example.adapter.BaseAdapterHelper;
import lico.example.adapter.BaseQuickAdapter;
import lico.example.adapter.SimpleRecyclerAdapter;
import lico.example.bean.HttpResponseEntity;
import lico.example.bean.ImageInfo;
import lico.example.http.HttpManager;
import lico.example.utils.ConstantURL;

/**
 * Created by Administrator on 2015/8/31.
 */
public class CommonListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.refresher)
    SwipeRefreshLayout refresher;
    @Bind(R.id.progress_wheel)
    ProgressWheel progressWheel;
    @Bind(R.id.content)
    FrameLayout content;

    private List<ImageInfo> imageInfos;
    private SimpleRecyclerAdapter mAdapter;
    int num = 10;
    boolean isLoadingMore;

    public static CommonListFragment newFragment(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        CommonListFragment fragment = new CommonListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showProgressWheel(true);
        initRecyclerView();
        initData();
        refresher.setOnRefreshListener(this);
    }

    private void initData() {

        String url = ConstantURL.imageurl + ConstantURL.apikey + "&num=" + num;
        HttpManager.getImages(url, new JSONParserCompleteListener() {
            @Override
            public void ParserCompleteListener(HttpResponseEntity response, Object object) {
                if (response.responseCode == 0) {
                    List<ImageInfo> list = (List<ImageInfo>) object;
                    imageInfos.addAll(list);
                    mAdapter.notifyDataSetChanged();
                    refresher.setRefreshing(false);
                } else {
                    Toast.makeText(getActivity(), response.errorMsg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initRecyclerView() {
        imageInfos = new ArrayList<ImageInfo>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setOnScrollListener(mRecyclerScrollListener);

        mAdapter = new SimpleRecyclerAdapter<ImageInfo>(getActivity(), imageInfos, R.layout.view_image) {
            @Override
            protected void convert(BaseAdapterHelper helper, ImageInfo item) {
                helper.setImageByUrl(R.id.image_id, item.picUrl);
            }
        };
        mAdapter.setOnInViewClickListener(R.id.root_layout,
                new BaseQuickAdapter.onInternalClickListenerImpl<ImageInfo>() {
                    @Override
                    public void OnClickListener(View parentV, View v, Integer position, ImageInfo values) {
                        Intent intent = new Intent(getActivity(), SwipeListViewActivity.class);
                        startActivity(intent);
                    }
                });

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        showProgressWheel(false);
        refresher.setOnRefreshListener(this);
    }

    RecyclerView.OnScrollListener mRecyclerScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            RecyclerView.LayoutManager layoutManager;
            layoutManager = recyclerView.getLayoutManager();
            int lastVisibleItem = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();
            int totalItemCount = mAdapter.getItemCount();
            int currentDataCount = imageInfos.size();
            if(lastVisibleItem >= totalItemCount -4 && dy > 0){
                    num = num + 10;
                    initData();
            }
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        num = 10;
        imageInfos.clear();
        initData();

    }

    private void showProgressWheel(boolean visible) {
        progressWheel.setBarColor(getColorPrimary());
        if (visible) {
            if (!progressWheel.isSpinning()) {
                progressWheel.spin();
            } else {
                progressWheel.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (progressWheel.isSpinning()) {
                            progressWheel.stopSpinning();
                        }
                    }
                }, 500);
            }
        }
    }

    public int getColorPrimary(){
        TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
}
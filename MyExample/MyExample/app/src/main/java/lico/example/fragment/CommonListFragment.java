package lico.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lico.example.InterfaceView.ImagesListView;
import lico.example.R;
import lico.example.activity.SpaceImageDetailActivity;
import lico.example.adapter.BaseAdapterHelper;
import lico.example.adapter.BaseQuickAdapter;
import lico.example.adapter.SimpleRecyclerAdapter;
import lico.example.bean.ImagesListEntity;
import lico.example.bean.ResponseImagesListEntity;
import lico.example.listener.OnPageSelectedListener;
import lico.example.loadmore.PullLoadMoreRecyclerView;
import lico.example.presenter.ImagesListPresenter;
import lico.example.presenter.impl.ImagesListPresenterImpl;
import lico.example.utils.Constants;
import lico.example.views.PLAImageView;

/**
 * Created by Administrator on 2015/8/31.
 */
public class CommonListFragment extends BaseFragment implements ImagesListView, OnPageSelectedListener {

    @Bind(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;

    private List<ImagesListEntity> imageInfos;
    private SimpleRecyclerAdapter mAdapter;
    private String keywords;
    private ImagesListPresenter mImagesListPresenter;
    private int pageIndex = 0;
    int lastVisibleItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keywords = getResources().getStringArray(R.array.images_category_list)[0];
    }

    @Override
    protected void onFirstUserVisible() {
        mImagesListPresenter = new ImagesListPresenterImpl(getActivity(), this);
        mImagesListPresenter.loadData(Constants.REFRESH_DATA, keywords, pageIndex);
    }

    @Override
    protected void onUserVisible() {
    }

    @Override
    protected void onUserInvisible() {
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        imageInfos = new ArrayList<ImagesListEntity>();
        mPullLoadMoreRecyclerView.setStaggeredGridLayout(2);
        mPullLoadMoreRecyclerView.setRefreshing(true);
        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreListener());

        mAdapter = new SimpleRecyclerAdapter<ImagesListEntity>(getActivity(), imageInfos, R.layout.view_image) {
            @Override
            protected void convert(BaseAdapterHelper helper, ImagesListEntity item) {
                int width = item.thumbnailWidth;
                int height = item.thumbnailHeight;
                helper.setImageByUrl(R.id.image_id, item.imageUrl);
                PLAImageView plaImageView = (PLAImageView) helper.getImageView(R.id.image_id);
                plaImageView.setImageHeight(height);
                plaImageView.setImageWidth(width);
            }
        };
        mAdapter.setOnInViewClickListener(R.id.root_layout,
                new BaseQuickAdapter.onInternalClickListenerImpl<ImagesListEntity>() {
                    @Override
                    public void OnClickListener(View parentV, View v, Integer position, ImagesListEntity values) {
                        int[] location = new int[2];
                        v.getLocationOnScreen(location);
                        int width = v.getWidth();
                        int height = v.getHeight();
                        mImagesListPresenter.onItemClickListener(position, imageInfos.get(position), location[0], location[1],
                                width, height);
                    }
                });
        mPullLoadMoreRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onPageSelected(int position, String keyword) {
        Log.e("xx", "--------------------:" + keyword);
        keywords = keyword;
    }

    class PullLoadMoreListener implements PullLoadMoreRecyclerView.PullLoadMoreListener {
        @Override
        public void onRefresh() {
            pageIndex = 0;
            mImagesListPresenter.loadData(Constants.REFRESH_DATA, keywords, pageIndex);
        }

        @Override
        public void onLoadMore() {
            pageIndex++;
            mImagesListPresenter.loadData(Constants.LOADMORE_DATA, keywords, pageIndex);
        }
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_common_list;
    }

    @Override
    public void refreshListData(ResponseImagesListEntity responseImagesListEntity) {
        if (null != responseImagesListEntity) {
            if (null != mAdapter) {
                mAdapter.getDataList().clear();
                mAdapter.getDataList().addAll(responseImagesListEntity.imgs);
                mAdapter.notifyDataSetChanged();
                mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
            }
        }
    }

    @Override
    public void addMoreListData(ResponseImagesListEntity responseImagesListEntity) {
        if (null != responseImagesListEntity) {
            mAdapter.getDataList().addAll(responseImagesListEntity.imgs);
            mAdapter.notifyDataSetChanged();
            mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        }
    }

    @Override
    public void navigateToDetail(int position, ImagesListEntity imagesListEntity, int x, int y, int width, int height) {
        Intent intent = new Intent(getActivity(), SpaceImageDetailActivity.class);
        Log.e("", "" + imagesListEntity.thumbnailUrl);
        intent.putExtra("images", imagesListEntity.imageUrl);
        intent.putExtra("locationX", x);
        intent.putExtra("locationY", y);
        intent.putExtra("width", width);
        intent.putExtra("height", height);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(0, 0);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

//RecyclerView.OnScrollListener mRecyclerScrollListener = new RecyclerView.OnScrollListener() {
//    @Override
//    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//        super.onScrollStateChanged(recyclerView, newState);
//        if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount()) {
//            pageIndex++;
//            mImagesListPresenter.loadData(LOADMORE_DATA, keywords, pageIndex);
//        }
//    }
//
//    @Override
//    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//        super.onScrolled(recyclerView, dx, dy);
//        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//        StaggeredGridLayoutManager staggeredGridLayoutManager = ((StaggeredGridLayoutManager) layoutManager);
//        //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
//        //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
//        int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
//        staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
//        lastVisibleItem = findMax(lastPositions);
//    }
//};
//
//    private int findMax(int[] lastPositions) {
//
//        int max = lastPositions[0];
//        for (int value : lastPositions) {
//            //       int max    = Math.max(lastPositions,value);
//            if (value > max) {
//                max = value;
//            }
//        }
//        return max;
//    }
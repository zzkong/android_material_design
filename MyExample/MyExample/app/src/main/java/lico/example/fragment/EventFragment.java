package lico.example.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;
import lico.example.listener.JSONParserCompleteListener;
import lico.example.R;
import lico.example.adapter.BaseAdapterHelper;
import lico.example.adapter.SimpleRecyclerAdapter;
import lico.example.bean.HttpResponseEntity;
import lico.example.bean.ImagesListEntity;
import lico.example.bean.ResponseImagesListEntity;
import lico.example.http.HttpManager;
import lico.example.utils.AnimUtils;

/**
 * Created by zwl  记事  on 2015/9/9.
 */
public class EventFragment extends BaseFragment {

    @Bind(R.id.appbar)
    AppBarLayout appbar;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;

    int pageIndex = 0;
    @Bind(R.id.ptr_frameLayout)
    PtrFrameLayout ptrFrameLayout;
    @Bind(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    private List<ImagesListEntity> imageInfos;
    private SimpleRecyclerAdapter mAdapter;

    public static EventFragment newInstance() {
        return new EventFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    protected int getTitle() {
        return R.string.string_floatingbutton;
    }

    @Override
    public boolean hasCustomToolbar() {
        return true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRecycler();
        initData();
        moveFab();
    }

    @Override
    protected void onFirstUserVisible() {

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

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_event;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void initRecycler() {
        imageInfos = new ArrayList<ImagesListEntity>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new SimpleRecyclerAdapter<ImagesListEntity>(getActivity(), imageInfos, R.layout.item_event) {

            @Override
            protected void convert(BaseAdapterHelper helper, ImagesListEntity item) {
                helper.setText(R.id.text, item.title);
                helper.setImageByUrl(R.id.item_image, item.imageUrl);
            }
        };
        recyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        HttpManager.getImages("美女", pageIndex, new JSONParserCompleteListener() {
            @Override
            public void ParserCompleteListener(HttpResponseEntity response, Object object) {
                if (response.responseCode == 0) {
                    ResponseImagesListEntity entity = (ResponseImagesListEntity) object;
                    imageInfos.addAll(entity.imgs);
                    mAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getActivity(), response.errorMsg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void moveFab() {
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                float deltaY = floatingActionButton.getHeight() * 1.5f;
                if (i < 0) animFab(deltaY);
                else animFab(-deltaY);
            }
        });
    }

    private void animFab(final float deltaY) {
        ViewCompat.animate(floatingActionButton)
                .translationYBy(deltaY)
                .setInterpolator(AnimUtils.FAST_OUT_SLOW_IN_INTERPOLATOR)
                .withLayer()
                .start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

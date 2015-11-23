package lico.example.activity;

import android.animation.Animator;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lico.example.R;
import lico.example.adapter.BaseAdapterHelper;
import lico.example.adapter.BaseQuickAdapter;
import lico.example.adapter.SimpleRecyclerAdapter;
import lico.example.bean.ImageInfo;

/**
 * Created by Administrator on 2015/9/12.
 */
public class CoordinatorTwoActivity extends AppCompatActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsingLayout)
    CollapsingToolbarLayout collapsingLayout;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.root_layout)
    CoordinatorLayout rootLayout;

    private List<ImageInfo> imageInfos;
    private SimpleRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_two);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        collapsingLayout.setTitle("来发折叠的");
        initRecyclerView();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {    //这里设置头文件返回事件
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initRecyclerView() {
        imageInfos = new ArrayList<ImageInfo>();


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new SimpleRecyclerAdapter<ImageInfo>(this, imageInfos, R.layout.view_image) {
            @Override
            protected void convert(BaseAdapterHelper helper, ImageInfo item) {
                helper.setText(R.id.title, item.title);
            }

            @Override
            protected Animator[] getAnimators(View view) {
                return new Animator[0];
            }
        };
        mAdapter.setOnInViewClickListener(R.id.root_layout,
                new BaseQuickAdapter.onInternalClickListenerImpl<ImageInfo>() {
                    @Override
                    public void OnClickListener(View parentV, View v, Integer position, ImageInfo values) {
                        Snackbar.make(rootLayout, "lico is coming !!", Snackbar.LENGTH_SHORT).show();
                    }
                });

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }
}

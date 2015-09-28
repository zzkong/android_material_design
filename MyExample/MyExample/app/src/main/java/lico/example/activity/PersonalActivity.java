package lico.example.activity;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

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
 * Created by zwl on 2015/9/9.
 */
public class PersonalActivity extends AppCompatActivity {
    @Bind(R.id.backgroud)
    ImageView backgroud;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<ImageInfo> imageInfos;
    private SimpleRecyclerAdapter mAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar.setTitle("Lico");
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        collapsingToolbar.setCollapsedTitleTextColor(Color.GREEN);//设置收缩后Toolbar上字体的颜色
        Glide.with(this).load(R.drawable.bg_banner_dialog).centerCrop().into(backgroud);
        initRecyclerView();
    }

    private void initRecyclerView() {
        imageInfos = new ArrayList<ImageInfo>();
        ImageInfo info = null;    //模拟数据


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new SimpleRecyclerAdapter<ImageInfo>(this,imageInfos, R.layout.view_image) {
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
                        Intent intent = new Intent(PersonalActivity.this, SwipeListViewActivity.class);
                        startActivity(intent);
                    }
                });

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }
}

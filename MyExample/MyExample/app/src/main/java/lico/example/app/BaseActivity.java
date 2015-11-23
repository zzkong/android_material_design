package lico.example.app;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import lico.example.R;

/**
 * Created by zwl on 15/5/27.
 */
public abstract class BaseActivity extends BaseAppCompatActivity implements BaseView{

    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isApplyKitKatTranslucency()){
            setSystemBarTintDrawable(getResources().getDrawable(R.drawable.sr_primary));
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mToolbar = ButterKnife.findById(this, R.id.toolbar);
        if(null != mToolbar){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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

    protected abstract boolean isApplyKitKatTranslucency();
//
//    protected void initToolbar(Toolbar toolbar){
//        if(toolbar == null)
//            return;
//        toolbar.setBackgroundColor(getColorPrimary());
//        toolbar.setTitle("万里行");
//        toolbar.setTitleTextColor(getColor(R.color.action_bar_title_color));
//        toolbar.collapseActionView();
//        setSupportActionBar(toolbar);
//        if(getSupportActionBar() != null){
//            getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//    }
//
//    protected int getColor(int res){
//        if(res <= 0)
//            throw new IllegalArgumentException("resource id can not be less 0");
//        return getResources().getColor(res);
//    }
//
//    public int getColorPrimary(){
//        TypedValue typedValue = new TypedValue();
//        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
//        return typedValue.data;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch (id) {
//            case android.R.id.home:
//                finish();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    protected abstract @LayoutRes int getLayoutView();
//
//    protected abstract void initToolbar();
//
//    protected void readyGoThenKill(Class<?> clazz) {
//        Intent intent = new Intent(this, clazz);
//        startActivity(intent);
//        finish();
//    }
}

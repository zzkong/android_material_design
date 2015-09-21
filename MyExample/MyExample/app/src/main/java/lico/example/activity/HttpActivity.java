package lico.example.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import lico.example.R;
import lico.example.app.BaseActivity;

/**
 * Created by zwl on 2015/8/27.
 */
public class HttpActivity extends BaseActivity implements View.OnClickListener{


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.get_one)
    Button getOne;
    @Bind(R.id.get_two)
    Button getTwo;
    @Bind(R.id.get_three)
    Button getThree;
    @Bind(R.id.get_four)
    Button getFour;
    @Bind(R.id.get_five)
    Button getFive;
    @Bind(R.id.post_one)
    Button postOne;
    @Bind(R.id.post_two)
    Button postTwo;
    @Bind(R.id.post_three)
    Button postThree;
    @Bind(R.id.post_four)
    Button postFour;
    @Bind(R.id.post_five)
    Button postFive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        ButterKnife.bind(this);

        setClickListener();
    }

    private void setClickListener(){
        getOne.setOnClickListener(this);
        getTwo.setOnClickListener(this);
        getThree.setOnClickListener(this);
        getFour.setOnClickListener(this);
        getFive.setOnClickListener(this);
        postOne.setOnClickListener(this);
        postTwo.setOnClickListener(this);
        postThree.setOnClickListener(this);
        postFour.setOnClickListener(this);
        postFive.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_one:
                break;
            case R.id.get_two:
                break;
            case R.id.get_three:
                break;
            case R.id.get_four:
                break;
            case R.id.get_five:
                break;
            case R.id.post_one:
                break;
            case R.id.post_two:
                break;
            case R.id.post_three:
                break;
            case R.id.post_four:
                break;
            case R.id.post_five:
                break;
        }
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_http;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar(toolbar);
    }


}

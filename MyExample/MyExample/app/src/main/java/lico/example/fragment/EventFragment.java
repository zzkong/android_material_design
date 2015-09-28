package lico.example.fragment;

import lico.example.R;

/**
 * Created by zwl  记事  on 2015/9/9.
 */
public class EventFragment extends BaseFragment{


    public static EventFragment newInstance(){
        return new EventFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_event;
    }
}

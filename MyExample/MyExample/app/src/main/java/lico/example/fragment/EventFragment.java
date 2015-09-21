package lico.example.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lico.example.R;

/**
 * Created by zwl  记事  on 2015/9/9.
 */
public class EventFragment extends Fragment{


    public static EventFragment newInstance(){
        EventFragment eventFragment = new EventFragment();
        return eventFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event, container, false);
    }
}

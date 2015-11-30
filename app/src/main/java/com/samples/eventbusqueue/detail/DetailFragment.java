package com.samples.eventbusqueue.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samples.eventbusqueue.R;

/**
 * Created by chrisbraunschweiler1 on 16/11/15.
 */
public class DetailFragment extends Fragment {

    public static DetailFragment getInstance(){
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle args){
        super.onCreate(args);
        //TODO fetch args

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }
}

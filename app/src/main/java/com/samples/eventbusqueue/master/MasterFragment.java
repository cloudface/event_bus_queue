package com.samples.eventbusqueue.master;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samples.eventbusqueue.R;
import com.samples.eventbusqueue.repository.DataRepository;
import com.samples.eventbusqueue.repository.Repository;
import com.samples.eventbusqueue.repository.events.DataFetchedEvent;

/**
 * Created by chrisbraunschweiler1 on 16/11/15.
 */
public class MasterFragment extends Fragment {

    private MasterFragmentListener mListener;
    private Repository mDataRepository;

    public static MasterFragment getInstance(){
        MasterFragment fragment = new MasterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle args){
        super.onCreate(args);
        //TODO fetch args
        mDataRepository = new DataRepository(getActivity());
    }

    @Override
    public void onResume(){
        super.onResume();
        DataRepository.EVENT_BUS.register(this);
        mDataRepository.resume();
    }

    @Override
    public void onPause(){
        super.onPause();
        DataRepository.EVENT_BUS.unregister(this);
        mDataRepository.pause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        view.findViewById(R.id.pressMeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataRepository.performRequest();
            }
        });
        return view;
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(final DataFetchedEvent event) {
        mListener.onShowDetailView();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mListener = (MasterFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + MasterFragmentListener.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface MasterFragmentListener {

        void onShowDetailView();
    }
}

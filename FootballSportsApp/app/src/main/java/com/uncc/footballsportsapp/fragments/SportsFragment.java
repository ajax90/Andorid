package com.uncc.footballsportsapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uncc.footballsportsapp.model.Leagues;
import com.uncc.footballsportsapp.R;
import com.uncc.footballsportsapp.model.Sports;
import com.uncc.footballsportsapp.adapters.SportsAdapter;

import java.util.ArrayList;

public class SportsFragment extends Fragment {

    private ArrayList<Sports> sportsList = new ArrayList<>();
    private ArrayList<Leagues> leaguesList = new ArrayList<>();
    private OnFragmentInteractionListener mListener;

    public SportsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sports, container, false);
        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Bundle bundle = getArguments();
        if (bundle != null) {
            sportsList = (ArrayList<Sports>) bundle.getSerializable("SPORTS_KEY");
            leaguesList = (ArrayList<Leagues>) bundle.getSerializable("LEAGUES_KEY");
        }

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.layoutRecyclerSports);
        RecyclerView.Adapter mAdapter = new SportsAdapter(sportsList, leaguesList);
        recyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }
}

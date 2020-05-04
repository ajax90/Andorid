package com.uncc.footballsportsapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.uncc.footballsportsapp.R;
import com.uncc.footballsportsapp.SharedPreferencesFavorite;

import java.util.ArrayList;
import java.util.List;

import com.uncc.footballsportsapp.model.Teams;


public class TeamInfoFragment extends Fragment {
    Bundle bundle;

    private OnFragmentInteractionListener mListener;
    String updatedURL;
    SharedPreferencesFavorite sharedPreference = new SharedPreferencesFavorite();
    private ArrayList<Teams> favTList;

    public TeamInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_team_info, container, false);
        bundle = getArguments();
        Teams team = (Teams) bundle.getSerializable("TEAMS_ITEM_KEY");
        ImageView logo = view.findViewById(R.id.imageViewTeamLogoTeamInfo);
        TextView teamName = view.findViewById(R.id.textViewTeamNameTeamInfo);
        TextView foundingDate = view.findViewById(R.id.textViewFoundersDate);
        TextView stadium = view.findViewById(R.id.textViewStadiumTeamInfo);
        TextView desciption = view.findViewById(R.id.textViewOverviewDescription);
        TextView url = view.findViewById(R.id.textViewOverviewURL);
        url.setPaintFlags(url.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        if (team.getBadge() == "null") {
            logo.setVisibility(View.INVISIBLE);
        } else {
            Picasso.get().load(team.getBadge()).into(logo);
            logo.setVisibility(View.VISIBLE);
        }

        if (team.getTeamName() == "null") {
            teamName.setVisibility(View.INVISIBLE);
        } else {
            teamName.setText(team.getTeamName());
            teamName.setVisibility(View.VISIBLE);
        }

        if (team.getFormedYear() <= 0) {
            foundingDate.setVisibility(View.INVISIBLE);
        } else {
            foundingDate.setText("" + team.getFormedYear());
            foundingDate.setVisibility(View.VISIBLE);
        }

        if (team.getStadium() == "null") {
            stadium.setVisibility(View.INVISIBLE);
        } else {
            stadium.setText(team.getStadium());
            stadium.setVisibility(View.VISIBLE);
        }

        if (team.getDescription() == "null") {
            desciption.setVisibility(View.INVISIBLE);
        } else {
            desciption.setText(team.getDescription());
            desciption.setMovementMethod(new ScrollingMovementMethod());
            desciption.setVisibility(View.VISIBLE);
        }

        if (team.getUrl() == "null") {
            desciption.setVisibility(View.INVISIBLE);
        } else {
            url.setText("Visit webiste: " + team.getUrl());
            url.setVisibility(View.VISIBLE);
        }

        if (!team.getUrl().startsWith("http://") && !team.getUrl().startsWith("https://"))
            updatedURL = "http://" + team.getUrl();

        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(updatedURL));
                startActivity(intent);
            }
        });

        view.findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkFavoriteItem(team)) {
                    Toast.makeText(getContext(), "Already in favorites", Toast.LENGTH_SHORT).show();
                } else {
                    sharedPreference.addFavorite(getContext(), team);
                }
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onTeamInfoFragmentInteraction();
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
        // TODO: Update argument type and name
        void onTeamInfoFragmentInteraction();
    }

    public boolean checkFavoriteItem(Teams checkTeam) {
        boolean check = false;
        List<Teams> favorites = sharedPreference.getFavorites(getContext());
        if (favorites != null) {
            for (Teams teams : favorites) {
                if (teams.getTeamName().equals(checkTeam.getTeamName())) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }
}

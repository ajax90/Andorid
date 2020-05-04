package com.uncc.footballsportsapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.uncc.footballsportsapp.model.Games;
import com.uncc.footballsportsapp.R;

import java.util.ArrayList;

import com.uncc.footballsportsapp.model.Teams;


public class ScoreBoardFragment extends Fragment {
    Bundle bundle;
    ArrayList<Teams> teamsList;

    private OnFragmentInteractionListener mListener;

    public ScoreBoardFragment() {
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
        View view = inflater.inflate(R.layout.fragment_score_board, container, false);
        //Log.d("teamsList", teamsList.toString());
        bundle = getArguments();
        Games game = (Games) bundle.getSerializable("GAMES_ITEM_KEY");
        teamsList = (ArrayList<Teams>) bundle.getSerializable("TEAMS_LIST");
        TextView dateTextView = view.findViewById(R.id.textViewDateScoreBoard);
        ImageView logoHome = view.findViewById(R.id.imageViewHomeTeamLogoScoreCard);
        ImageView logoAway = view.findViewById(R.id.imageViewAwayTeamLogoScoreCard);
        TextView teamNameHome = view.findViewById(R.id.textViewHomeTeamNameScoreBoard);
        TextView teamNameAway = view.findViewById(R.id.textViewAwayTeamNameScoreBoard);
        TextView leagueName = view.findViewById(R.id.textViewLeagueNameScoreBoard);
        TextView stadiumName = view.findViewById(R.id.textViewStadiumNameScoreBoard);
        TextView goalsHome = view.findViewById(R.id.textViewGoalsHomeScoreBoard);
        TextView goalsAway = view.findViewById(R.id.textViewGoalsAwayScoreBoard);
        TextView shotsHome = view.findViewById(R.id.textViewShotsHomeScoreBoard);
        TextView shotsAway = view.findViewById(R.id.textViewShotsAwayScoreBoard);
        TextView yellowCardHome = view.findViewById(R.id.textViewYellowCardHomeScoreBoard);
        TextView yellowCardAway = view.findViewById(R.id.textViewYellowCardAwayScoreBoard);
        TextView redCardHome = view.findViewById(R.id.textViewRedCardHomeScoreBoard);
        TextView redCardAway = view.findViewById(R.id.textViewRedCardAwayScoreBoard);
        TextView goalkeeperHome = view.findViewById(R.id.textViewGoalKeeperHomeTeamScoreBoard);
        TextView goalkeeperAway = view.findViewById(R.id.textViewGoalKeepetAwayTeamScoreBoard);
        TextView defenseHome = view.findViewById(R.id.textViewDefenseHomeTeamScoreBoard);
        TextView defenseAway = view.findViewById(R.id.textViewDefenseAwayTeamScoreBoard);
        TextView midfieldHome = view.findViewById(R.id.textViewMidFieldHomeTeamScoreBoard);
        TextView midfieldAway = view.findViewById(R.id.textViewMidFieldAwayTeamScoreBoard);
        TextView forwardHome = view.findViewById(R.id.textViewForwardHomeTeamScoreBoard);
        TextView forwardAway = view.findViewById(R.id.textViewForwardAwayTeamScoreBoard);
        TextView substituteHome = view.findViewById(R.id.textViewSubstitutesHomeTeamScoreBoard);
        TextView substituteAway = view.findViewById(R.id.textViewSubstituteAwayTeamScoreBoard);

        Teams homeTeam;
        Teams awayTeam;
        Log.d("Inside on create ScoreBoard", "" + teamsList.size());
        homeTeam = teamsList.stream().filter(obj -> obj.getTeamName().equals(game.getHomeTeam())).findFirst().orElse(null);
        awayTeam = teamsList.stream().filter(obj -> obj.getTeamName().equals(game.getAwayTeam())).findFirst().orElse(null);

        if (game.getEventDate() == "null") {
            dateTextView.setVisibility(View.INVISIBLE);
        } else {
            dateTextView.setText(game.getEventDate());
            dateTextView.setVisibility(View.VISIBLE);
        }

        if (homeTeam == null) {
            logoHome.setVisibility(View.INVISIBLE);
        } else {
            Picasso.get().load(homeTeam.getBadge()).into(logoHome);
            logoHome.setVisibility(View.VISIBLE);
        }

        if (awayTeam == null) {
            logoAway.setVisibility(View.INVISIBLE);
        } else {
            Picasso.get().load(awayTeam.getBadge()).into(logoAway);
            logoAway.setVisibility(View.VISIBLE);
        }

        if (game.getLeagueName() == "null") {
            leagueName.setVisibility(View.INVISIBLE);
        } else {
            leagueName.setText(game.getLeagueName());
            leagueName.setVisibility(View.VISIBLE);
        }

        if (homeTeam == null) {
            stadiumName.setVisibility(View.INVISIBLE);
        } else {
            stadiumName.setText(homeTeam.getStadium());
            stadiumName.setVisibility(View.VISIBLE);
        }

        if (game.getHomeTeam() == "null") {
            teamNameHome.setVisibility(View.INVISIBLE);
        } else {
            teamNameHome.setText(game.getHomeTeam());
            teamNameHome.setVisibility(View.VISIBLE);
        }

        if (game.getAwayTeam() == "null") {
            teamNameAway.setVisibility(View.INVISIBLE);
        } else {
            teamNameAway.setText(game.getAwayTeam());
            teamNameAway.setVisibility(View.VISIBLE);
        }

        if (game.getHomeScore() == "null") {
            goalsHome.setVisibility(View.INVISIBLE);
        } else {
            goalsHome.setText(game.getHomeScore());
            goalsHome.setVisibility(View.VISIBLE);
        }

        if (game.getAwayScore() == "null") {
            goalsAway.setVisibility(View.INVISIBLE);
        } else {
            goalsAway.setText(game.getAwayScore());
            goalsAway.setVisibility(View.VISIBLE);
        }

        if (game.getHomeShots() == "null") {
            shotsHome.setVisibility(View.INVISIBLE);
        } else {
            shotsHome.setText(game.getHomeShots());
            shotsHome.setVisibility(View.VISIBLE);
        }

        if (game.getAwayShots() == "null") {
            shotsAway.setVisibility(View.INVISIBLE);
        } else {
            shotsAway.setText(game.getAwayShots());
            shotsAway.setVisibility(View.VISIBLE);
        }

        if (game.getHomeYellowCards() == "null") {
            yellowCardHome.setVisibility(View.INVISIBLE);
        } else {
            yellowCardHome.setText("H : " + game.getHomeYellowCards());
            yellowCardHome.setVisibility(View.VISIBLE);
        }

        if (game.getAwayYellowCards() == "null") {
            yellowCardAway.setVisibility(View.INVISIBLE);
        } else {
            yellowCardAway.setText("A : " + game.getAwayYellowCards());
            yellowCardAway.setVisibility(View.VISIBLE);
        }

        if (game.getHomeRedCard() == "null") {
            redCardHome.setVisibility(View.INVISIBLE);
        } else {
            redCardHome.setText("H : " + game.getHomeRedCard());
            redCardHome.setVisibility(View.VISIBLE);
        }

        if (game.getAwayRedCard() == "null") {
            redCardAway.setVisibility(View.INVISIBLE);
        } else {
            redCardAway.setText("A : " + game.getAwayRedCard());
            redCardAway.setVisibility(View.VISIBLE);
        }

        if (game.getHomeGoalKeeper() == "null") {
            goalkeeperHome.setVisibility(View.INVISIBLE);
        } else {
            goalkeeperHome.setText("H : " + game.getHomeGoalKeeper());
            goalkeeperHome.setVisibility(View.VISIBLE);
        }

        if (game.getAwayGoalKeeper() == "null") {
            goalkeeperAway.setVisibility(View.INVISIBLE);
        } else {
            goalkeeperAway.setText("A : " + game.getAwayGoalKeeper());
            goalkeeperAway.setVisibility(View.VISIBLE);
        }

        if (game.getHomeDefense() == "null") {
            defenseHome.setVisibility(View.INVISIBLE);
        } else {
            defenseHome.setText("H : " + game.getHomeDefense());
            defenseHome.setVisibility(View.VISIBLE);
        }

        if (game.getAwayDefense() == "null") {
            defenseAway.setVisibility(View.INVISIBLE);
        } else {
            defenseAway.setText("A : " + game.getAwayDefense());
            defenseAway.setVisibility(View.VISIBLE);
        }

        if (game.getHomeMidfield() == "null") {
            midfieldHome.setVisibility(View.INVISIBLE);
        } else {
            midfieldHome.setText("H : " + game.getHomeMidfield());
            midfieldHome.setVisibility(View.VISIBLE);
        }

        if (game.getAwayMidfield() == "null") {
            midfieldAway.setVisibility(View.INVISIBLE);
        } else {
            midfieldAway.setText("A : " + game.getAwayMidfield());
            midfieldAway.setVisibility(View.VISIBLE);
        }

        if (game.getHomeForward() == "null") {
            forwardHome.setVisibility(View.INVISIBLE);
        } else {
            forwardHome.setText("H : " + game.getHomeForward());
            forwardHome.setVisibility(View.VISIBLE);
        }

        if (game.getAwayForward() == "null") {
            forwardAway.setVisibility(View.INVISIBLE);
        } else {
            forwardAway.setText("A : " + game.getAwayForward());
            forwardAway.setVisibility(View.VISIBLE);
        }

        if (game.getHomeSubstitute() == "null") {
            substituteHome.setVisibility(View.INVISIBLE);
        } else {
            substituteHome.setText("H : " + game.getHomeSubstitute());
            substituteHome.setVisibility(View.VISIBLE);
        }

        if (game.getAwaySubstitute() == "null") {
            substituteAway.setVisibility(View.INVISIBLE);
        } else {
            substituteAway.setText("A : " + game.getAwayForward());
            substituteAway.setVisibility(View.VISIBLE);
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onScoreBoardFragmentInteraction();
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
        void onScoreBoardFragmentInteraction();
    }

    public ScoreBoardFragment setTeamsList(ArrayList<Teams> teamsListl) {
        teamsList = teamsListl;
        Log.d("teamListNowHere", "" + teamsList.size());
        return this;
    }
}

package com.app.ecoquest.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.ecoquest.R;
import com.app.ecoquest.database.SQLiteHelper;
import com.app.ecoquest.adapters.HomeAdapter;
import com.app.ecoquest.models.Stats;
import com.app.ecoquest.utils.Methods;

public class HomeFragment extends Fragment {

    View view;
    TextView tvLevel, tvProgress;
    ProgressBar progressBar;
    RecyclerView rvHome;
    SQLiteHelper sqLiteHelper;
    HomeAdapter adapter;
    String TAG = "theS";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        tvLevel = view.findViewById(R.id.tvLevel);
        tvProgress = view.findViewById(R.id.tvProgress);
        progressBar = view.findViewById(R.id.progressBar);
        rvHome = view.findViewById(R.id.rvHome);

        sqLiteHelper = new SQLiteHelper(requireContext());

        setStats();

        adapter = new HomeAdapter(requireContext(), sqLiteHelper.readTasks(false), sqLiteHelper,this);
        rvHome.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        rvHome.setAdapter(adapter);

        return view;
    }

    public void setStats() {
        Stats stats = sqLiteHelper.readStats(1);
        int level = stats.getLevel();
        int exp = stats.getExp();
        int totalExp = Methods.getTotalExp(level);
        tvLevel.setText("Lvl" + level);
        tvProgress.setText(exp + "/" + totalExp);
        int percentage = (int) (((double)exp / totalExp) * 100);
        Log.d(TAG, "setStats: level=" + level + " exp=" + exp + " totalExp=" + totalExp + " (exp/totalExp)=" + (exp / totalExp) + " percentage=" + percentage);
        progressBar.setProgress(percentage);
    }
}

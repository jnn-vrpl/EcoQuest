package com.app.ecoquest.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.ecoquest.R;
import com.app.ecoquest.database.SQLiteHelper;
import com.app.ecoquest.adapters.TaskAdapter;
import com.app.ecoquest.models.Task;
import com.app.ecoquest.utils.Methods;

public class TodoFragment extends Fragment {

    View view;
    RadioGroup rgSelection;
    ImageView ivAdd;
    RecyclerView rvTasks;
    SQLiteHelper sqLiteHelper;
    TaskAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_todo, container, false);

        ivAdd = view.findViewById(R.id.ivAdd);
        rgSelection = view.findViewById(R.id.rgSelection);
        rvTasks = view.findViewById(R.id.rvTasks);

        sqLiteHelper = new SQLiteHelper(requireContext());

        setView(false);

        rgSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbActive) {
                    setView(false);
                } else if (checkedId == R.id.rbComplete) {
                    setView(true);
                }
            }
        });


        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        return view;
    }

    public void setView(boolean isComplete) {
        adapter = new TaskAdapter(requireContext(), sqLiteHelper.readTasks(isComplete), sqLiteHelper);
        rvTasks.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        rvTasks.setAdapter(adapter);
    }

    public void addTask() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add new task");
        builder.setCancelable(false);
        View viewInflated = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_task, null, false);
        EditText etName = viewInflated.findViewById(R.id.etName);

        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = etName.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Methods.showMessage(requireActivity(), "Please enter name");
                } else {
                    Task task = new Task(name, Methods.getCurrentDate(), Methods.getCurrentTime(), false);
                    long id = sqLiteHelper.createTask(task);
                    task.setId(id);
                    adapter.addTask(task);
                }
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}

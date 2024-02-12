package com.app.ecoquest.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.app.ecoquest.R;
import com.app.ecoquest.database.SQLiteHelper;
import com.app.ecoquest.models.Task;
import com.app.ecoquest.utils.Methods;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    Context context;
    List<Task> list;
    SQLiteHelper sqLiteHelper;

    public TaskAdapter(Context context, List<Task> list, SQLiteHelper sqLiteHelper) {
        this.context = context;
        this.list = list;
        this.sqLiteHelper = sqLiteHelper;
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {

        if (list.get(position).isComplete()) {
            holder.tvName.setPaintFlags(holder.tvName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.ivComplete.setImageResource(R.drawable.checked_box_green_64px);
        } else {
            holder.ivComplete.setImageResource(R.drawable.uncheck_box_64px);
        }

        holder.tvName.setText(list.get(position).getName());

        holder.ivComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!list.get(position).isComplete()) {
                    sqLiteHelper.addStats();
                    sqLiteHelper.completeTask(list.get(position).getId());
                    holder.ivComplete.setImageResource(R.drawable.checked_box_green_64px);
                    list.remove(position);
                    notifyDataSetChanged();
                }
            }
        });

        holder.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.menu_task_item, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.iEdit) {
                            if (!list.get(position).isComplete()) {
                                updateTask(list.get(position));
                            } else {
                                Methods.showMessage((Activity) context, "Task is completed");
                            }
                        } else if (menuItem.getItemId() == R.id.iDelete) {
                            sqLiteHelper.deleteTask(list.get(position).getId());
                            list.remove(position);
                            notifyDataSetChanged();
                            Methods.showMessage((Activity) context, "Task is deleted");
                        }
                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivComplete, ivMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            ivComplete = itemView.findViewById(R.id.ivComplete);
            ivMenu = itemView.findViewById(R.id.ivMenu);
        }
    }

    public void addTask(Task task){
        list.add(task);
        notifyDataSetChanged();
    }

    public void updateTask(Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Update task");
        builder.setCancelable(false);
        View viewInflated = LayoutInflater.from(context).inflate(R.layout.dialog_add_task, null, false);
        EditText etName = viewInflated.findViewById(R.id.etName);
        etName.setText(task.getName());
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = etName.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Methods.showMessage((Activity) context, "Please enter name");
                } else {
                    task.setName(name);
                    task.setDate(Methods.getCurrentDate());
                    task.setTime(Methods.getCurrentTime());
                    sqLiteHelper.updateTask(task);
                    notifyDataSetChanged();
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

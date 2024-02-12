package com.app.ecoquest.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.ecoquest.fragments.HomeFragment;
import com.app.ecoquest.R;
import com.app.ecoquest.database.SQLiteHelper;
import com.app.ecoquest.models.Task;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    Context context;
    List<Task> list;
    SQLiteHelper sqLiteHelper;
    HomeFragment homeFragment;

    public HomeAdapter(Context context, List<Task> list, SQLiteHelper sqLiteHelper, HomeFragment homeFragment) {
        this.context = context;
        this.list = list;
        this.sqLiteHelper = sqLiteHelper;
        this.homeFragment = homeFragment;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {

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
                    homeFragment.setStats();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivComplete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            ivComplete = itemView.findViewById(R.id.ivComplete);
        }
    }
}

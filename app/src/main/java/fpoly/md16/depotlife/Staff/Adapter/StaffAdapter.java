package fpoly.md16.depotlife.Staff.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.md16.depotlife.Helper.Interfaces.onClickListener.OnItemClickListener;
import fpoly.md16.depotlife.Staff.Model.UserResponse;
import fpoly.md16.depotlife.databinding.ItemStaffBinding;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<UserResponse.User> list;

    private OnItemClickListener itemClickListener;


    public StaffAdapter(Context context, ArrayList<UserResponse.User> list, OnItemClickListener itemClickListener) {
        this.context = context;
        this.list = list;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStaffBinding binding = ItemStaffBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvName.setText(list.get(position).getName());
        holder.binding.tvEmail.setText(list.get(position).getEmail());
        holder.binding.tvPhone.setText(list.get(position).getPhoneNumber());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener !=null){
                    itemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemStaffBinding binding;

        public ViewHolder(@NonNull ItemStaffBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

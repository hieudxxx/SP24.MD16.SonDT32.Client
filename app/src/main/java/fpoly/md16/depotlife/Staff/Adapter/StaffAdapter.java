package fpoly.md16.depotlife.Staff.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.md16.depotlife.Login.Model.UserResponse;
import fpoly.md16.depotlife.Staff.Model.StaffResponse;
import fpoly.md16.depotlife.databinding.ItemStaffBinding;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<StaffResponse.User> list;




    public StaffAdapter(Context context, ArrayList<StaffResponse.User> list) {
        this.context = context;
        this.list = list;
    }

    public void addList(ArrayList<StaffResponse.User> mlist){
        list.clear();
        list.addAll(mlist);
        notifyDataSetChanged();
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
                Toast.makeText(context,"OK STAFF",Toast.LENGTH_SHORT).show();
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

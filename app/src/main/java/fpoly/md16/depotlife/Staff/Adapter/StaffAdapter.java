package fpoly.md16.depotlife.Staff.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

import fpoly.md16.depotlife.Helper.Interfaces.onClickListener.onMenuClick;
import fpoly.md16.depotlife.Login.Model.UserResponse;
import fpoly.md16.depotlife.Product.Activity.ProductActivity;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Staff.Activity.StaffDetailActivity;
import fpoly.md16.depotlife.Staff.Model.StaffResponse;
import fpoly.md16.depotlife.databinding.ItemStaffBinding;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> implements Filterable {
    private final Context context;
    private ArrayList<StaffResponse.User> list;
    private final ArrayList<StaffResponse.User> mlist;

    private String token;

    public StaffAdapter(Context context, ArrayList<StaffResponse.User> list, String token) {
        this.context = context;
        this.list = list;
        this.mlist = list;
        this.token = token;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStaffBinding binding = ItemStaffBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        StaffResponse.User user = list.get(position);

        Picasso.get().load(user.getAvatar()).into(holder.binding.imgAvatar);
        holder.binding.tvName.setText(user.getName());
        holder.binding.tvEmail.setText(user.getEmail());
        holder.binding.tvPhone.setText(user.getPhoneNumber());
        holder.binding.switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Nếu SwitchCompat được bật
                    user.setStatus(1); // Cập nhật status là
                    holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.bgr_screen));
                    Toast.makeText(context, "Chặn thành công.", Toast.LENGTH_SHORT).show();
                } else {
                    // Nếu SwitchCompat được tắt
                    user.setStatus(0); // Cập nhật status là 0
                    holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.white));
                    Toast.makeText(context, "Bỏ chặn thành công.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("staff", list.get(holder.getAdapterPosition()));
                context.startActivity(new Intent(context, StaffDetailActivity.class).putExtras(bundle));
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    list = mlist;
                } else {
                    ArrayList<StaffResponse.User> listFilter = new ArrayList<>();
                    for (StaffResponse.User user : mlist) {
                        if (user.getName().toLowerCase().contains(strSearch.toLowerCase())) {
                            listFilter.add(user);
                        }
                    }
                    list = listFilter;
                }
                FilterResults results = new FilterResults();
                results.values = list;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (ArrayList<StaffResponse.User>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemStaffBinding binding;

        public ViewHolder(@NonNull ItemStaffBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

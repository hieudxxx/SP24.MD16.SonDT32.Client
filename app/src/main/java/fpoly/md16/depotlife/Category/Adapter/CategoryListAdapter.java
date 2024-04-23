package fpoly.md16.depotlife.Category.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.md16.depotlife.Category.Model.Category;
import fpoly.md16.depotlife.databinding.ItemCategoryListBinding;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Category> list;
    private ArrayList<Category> mlist;
    private int cat_id;

    public CategoryListAdapter(Context context, ArrayList<Category> list, int cat_id) {
        this.context = context;
        this.list = list;
        this.mlist = list;
        this.cat_id = cat_id;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()) {
                    list = mlist;
                } else {
                    ArrayList<Category> FilterList = new ArrayList<>();
                    for (Category category : mlist) {
                        if (category.getName().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(category);
                        }
                    }
                    list = FilterList;
                }
                FilterResults results = new FilterResults();
                results.values = list;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<Category>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryListBinding binding = ItemCategoryListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvName.setText(list.get(position).getName());
        for (Category cat : list) {
            if (cat.getId() == cat_id) {
                holder.binding.cbCategopry.setChecked(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryListBinding binding;

        public ViewHolder(@NonNull ItemCategoryListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

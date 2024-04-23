package fpoly.md16.depotlife.Category.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.md16.depotlife.Category.Model.Category;
import fpoly.md16.depotlife.databinding.ItemCategoryBinding;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> implements Filterable {
    Context context;
    List<Category> categoryList;
    List<Category> oldCategoryList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
        this.oldCategoryList = categoryList;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding categoryBinding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryViewHolder(categoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.categoryBinding.tvCategoryId.setText(category.getId());
        holder.categoryBinding.tvCategoryName.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        if (categoryList != null) {
            return categoryList.size();
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
                    categoryList = oldCategoryList;
                } else {
                    List<Category> list = new ArrayList<>();
                    for (Category category : oldCategoryList) {
                        if (category.getName().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(category);
                        }
                    }
                    categoryList = list;
                }

                FilterResults results = new FilterResults();
                results.values = categoryList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                categoryList = (List<Category>) results.values;
                // Thông báo thay đổi
                notifyDataSetChanged();
            }
        };
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryBinding categoryBinding;

        public CategoryViewHolder(@NonNull ItemCategoryBinding categoryBinding) {
            super(categoryBinding.getRoot());
            this.categoryBinding = categoryBinding;


        }
    }
}

package fpoly.md16.depotlife.Product.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fpoly.md16.depotlife.Category.Model.Category;
import fpoly.md16.depotlife.databinding.ItemCategoryProductFilterBinding;

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.CategoryViewHolder> {
    private Context context;
    private List<Category> categoryList;

    public ProductCategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryProductFilterBinding categoryBinding = ItemCategoryProductFilterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryViewHolder(categoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.categoryBinding.textView.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        if (categoryList != null) {
            return categoryList.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ItemCategoryProductFilterBinding categoryBinding;

        public CategoryViewHolder(@NonNull ItemCategoryProductFilterBinding categoryBinding) {
            super(categoryBinding.getRoot());
            this.categoryBinding = categoryBinding;
        }
    }
}

package fpoly.md16.depotlife.Category.Adapter;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.md16.depotlife.Category.CategoryActivity;
import fpoly.md16.depotlife.Category.Model.Category;
import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiCategory;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiSupplier;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Supplier.Fragment.SupplierFragment;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.databinding.DialogUpdateCategoryBinding;
import fpoly.md16.depotlife.databinding.ItemCategoryBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> implements Filterable {
    Context context;
    List<Category> categoryList;
    List<Category> oldCategoryList;

    DialogUpdateCategoryBinding binding_up;

    private String token;

    public CategoryAdapter(Context context, List<Category> categoryList,String token) {
        this.context = context;
        this.categoryList = categoryList;
        this.oldCategoryList = categoryList;
        this.token = token;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding categoryBinding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryViewHolder(categoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category  category = categoryList.get(position);
        holder.categoryBinding.tvCategoryName.setText(category.getName());
        holder.categoryBinding.tvStatus.setText(category.getStatus() == 1 ? "Đang sử dụng" : "Ngưng sử dụng");

        holder.categoryBinding.icDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.onCheckdeleteDialog(context, () -> {
                    ApiCategory.apiCategory.delete(token, category.getId()).enqueue(new Callback<Category>() {
                        @Override
                        public void onResponse(Call<Category> call, Response<Category> response) {
                            if (response.isSuccessful() || response.code() == 200) {
                                Toast.makeText(context, "Xóa thể loại thành công!", Toast.LENGTH_SHORT).show();
                                categoryList.remove(category);

                            }
                        }

                        @Override
                        public void onFailure(Call<Category> call, Throwable throwable) {
                            Log.d("onFailure", "onFailure: " + throwable.getMessage());
                            Toast.makeText(context, "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                        }
                    });

                });
            }
        });


        holder.categoryBinding.icEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(category);
            }
        });

    }


    private void showDialog(Category category) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context); // view.getRootView().getContext()
        builder.setCancelable(false);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_update_category, null);

        binding_up = DialogUpdateCategoryBinding.inflate(inflater, view, false);
        builder.setView(binding_up.getRoot());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        binding_up.edtAddCategory.setText(category.getName());

        binding_up.btnAddCateogry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String name = binding_up.edtAddCategory.getText().toString();
                if (name.isEmpty() ) {
                    Toast.makeText(context, "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                } else {
                    Helper.isContainSpace(name, binding_up.tvWarName);

                    if (binding_up.tvWarName.getText().toString().isEmpty()){
                        Category cate = new Category();
                        cate.setName(name);
                        ApiCategory.apiCategory.update(token, category.getId(), cate).enqueue(new Callback<Category>() {
                            @Override
                            public void onResponse(Call<Category> call, Response<Category> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, "Sửa thể loại thành công!", Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();

                                }
                            }

                            @Override
                            public void onFailure(Call<Category> call, Throwable throwable) {
                                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                                Toast.makeText(context, "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        binding_up.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
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

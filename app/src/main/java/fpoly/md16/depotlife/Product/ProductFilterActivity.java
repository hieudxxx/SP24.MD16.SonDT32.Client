package fpoly.md16.depotlife.Product;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import fpoly.md16.depotlife.Category.Model.Category;
import fpoly.md16.depotlife.Product.Adapter.ProductCategoryAdapter;
import fpoly.md16.depotlife.databinding.ActivityProductFilterBinding;

public class ProductFilterActivity extends AppCompatActivity {

    private ActivityProductFilterBinding binding;
    private ProductCategoryAdapter productCategoryAdapter;
    private List<Category> categoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductFilterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imgBack.setOnClickListener(v -> {
            finish();
        });
        setSupportActionBar(binding.tbProductFilter);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        productCategoryAdapter = new ProductCategoryAdapter(ProductFilterActivity.this, getCategoryList());

        binding.rcvProductCategory.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false));
        binding.rcvProductCategory.setAdapter(productCategoryAdapter);


        binding.linearLayout2.setOnClickListener(v -> {
        });
        binding.linearLayout3.setOnClickListener(v -> {
        });
        binding.linearLayout4.setOnClickListener(v -> {
        });
        binding.linearLayout5.setOnClickListener(v -> {
        });
        binding.linearLayout6.setOnClickListener(v -> {
        });


    }

    public List<Category> getCategoryList() {
        List<Category> list = new ArrayList<>();
//        list.add(new Category("id1", "Mỹ phẩm", true));
//        list.add(new Category("id2", "Gia dụng", true));
//        list.add(new Category("id3", "Thức ăn", true));
//        list.add(new Category("id4", "Mỹ phẩm", true));
//        list.add(new Category("id1", "Mỹ phẩm", true));
//        list.add(new Category("id2", "Gia dụng", true));
//        list.add(new Category("id3", "Thức ăn", true));
//        list.add(new Category("id4", "Mỹ phẩm", true));
//        list.add(new Category("id1", "Mỹ phẩm", true));
//        list.add(new Category("id2", "Gia dụng", true));
//        list.add(new Category("id3", "Thức ăn", true));
//        list.add(new Category("id4", "Mỹ phẩm", true));
        return list;
    }
}
package fpoly.md16.depotlife.Product.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;

import fpoly.md16.depotlife.Category.Fragment.CategoryListFragment;
import fpoly.md16.depotlife.Category.Model.Category;
import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.RealPathUtil;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Supplier.Fragment.SupplierListSelectFragment;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.ViewModel.ShareViewModel;
import fpoly.md16.depotlife.databinding.FragmentProductAddBinding;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class ProductAddFragment extends Fragment {
    private FragmentProductAddBinding binding;
    private String token;
    private Product product;
    private Category category;
    private Supplier supplier;
    private Bundle bundle;
    private int id_product;
    private ShareViewModel<Object> viewModel;
    private Uri uri;
    private MultipartBody.Part multipartBody;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductAddBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbProduct);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.imgBack.setOnClickListener(view1 -> {
            requireActivity().finish();
        });

        token = "Bearer " + (String) Helper.getSharedPre(getContext(), "token", String.class);

        viewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);

        binding.imgProduct.setOnClickListener(view14 -> {
            onRequestPermission();
        });

        binding.tvCategory.setOnClickListener(view13 -> {
            Helper.loadFragment(getParentFragmentManager(), new CategoryListFragment(), bundle, R.id.frag_container_product);
        });

        binding.tvSupplier.setOnClickListener(view13 -> {
            Helper.loadFragment(getParentFragmentManager(), new SupplierListSelectFragment(), bundle, R.id.frag_container_product);
        });

        binding.tvSave.setOnClickListener(view12 -> {
            String name = binding.edtName.getText().toString().trim();
            String tvcategory = binding.tvCategory.getText().toString().trim();
            String tvsupplier = binding.tvSupplier.getText().toString().trim();
            String export_price = binding.edtExportPrice.getText().toString().trim();
            String import_price = binding.edtImportPrice.getText().toString().trim();
            String inventory = binding.edtInventory.getText().toString().trim();
            String unit = binding.edtUnit.getText().toString().trim();

            if (name.isEmpty() || tvcategory.isEmpty() || tvcategory.isEmpty() || export_price.isEmpty() || import_price.isEmpty() || inventory.isEmpty() || unit.isEmpty()) {
                Toast.makeText(getContext(), "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
            } else {
                Helper.isContainSpace(name, binding.tvWarName);
                Helper.isContainSpace(tvcategory, binding.tvWarCat);
                Helper.isContainSpace(tvsupplier, binding.tvWarSup);
                Helper.isNumberValid(export_price, binding.tvWarExportPrice);
                Helper.isNumberValid(import_price, binding.tvWarImportPrice);
                Helper.isNumberValid(inventory, binding.tvWarInventory);
                Helper.isContainSpace(unit, binding.tvWarUnit);

                if (binding.tvWarName.getText().toString().isEmpty() &&
                        binding.tvWarCat.getText().toString().isEmpty() &&
                        binding.tvSupplier.getText().toString().isEmpty() &&
                        binding.tvWarExportPrice.getText().toString().isEmpty() &&
                        binding.tvWarImportPrice.getText().toString().isEmpty() &&
                        binding.tvWarInventory.getText().toString().isEmpty() &&
                        binding.tvWarUnit.getText().toString().isEmpty()
                ) {
                    if (category == null || supplier != null) {
                        Toast.makeText(getContext(), "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                    } else {
                        if (uri != null) {
//                            RequestBody requestBoy = RequestBody.create(MediaType.parse("multipart/form-data"), product);
                            String realPath = RealPathUtil.getRealPath(getContext(), uri);
                            File file = new File(realPath);
                            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                            multipartBody = MultipartBody.Part.createFormData("images", file.getName(), requestBody);
                            Log.d("tag_kiemTra", "onViewCreated: " + multipartBody);
                        }

//                        product = new Product(supplier.getId(), category.getId(), name, unit, Double.parseDouble(import_price), Double.parseDouble(export_price), Integer.parseInt(inventory));




                    }


                }
            }
        });
    }

    private void onRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (getActivity().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String permission = (Manifest.permission.READ_EXTERNAL_STORAGE);
            requestPermissions(new String[]{permission}, 10);
        }
    }

    private void openGallery() {
        ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uri = data.getData();
        Log.d("tag_kiemTra", "o/nActivityResult: " + uri);
        if (uri != null) {
            binding.imgProduct.setImageURI(uri);
            product.setImg(String.valueOf(uri));
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        viewModel.get().observe(getViewLifecycleOwner(), item -> {
            Log.d("tag_kiemTra", "onResume: "+item.toString());
            if (item instanceof Category) {
                category = (Category) item;
                binding.tvCategory.setText(category.getName());
//                product.setCategory_name(category.getName());
//                product.setCategory_id(category.getId());
            }
            if (item instanceof Supplier) {
                supplier = (Supplier) item;
                binding.tvSupplier.setText(supplier.getName());
//                product.setSupplier_name(supplier.getName());
//                product.setSupplier_id(supplier.getId());
            }

        });
    }

}
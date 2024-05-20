package fpoly.md16.depotlife.Product.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import fpoly.md16.depotlife.Category.Fragment.CategoryListFragment;
import fpoly.md16.depotlife.Category.Model.Category;
import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiLocation;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiProduct;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Supplier.Fragment.SupplierListSelectFragment;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.ViewModel.ShareViewModel;
import fpoly.md16.depotlife.databinding.FragmentProductAddBinding;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductAddFragment extends Fragment {
    private FragmentProductAddBinding binding;
    private String token;
    private Product product;
    private Category category = new Category();
    private Supplier supplier = new Supplier();
    private ShareViewModel<Object> viewModel;
    private Uri uri;
    private MultipartBody.Part[] listMultipartBody;

    private int zone;
    private String shelf;
    private int level;
    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    uri = result.getData().getData();
                    binding.imgProduct.setImageURI(uri);
                }
            }
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductAddBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.imgBack.setOnClickListener(view1 -> {
            requireActivity().finish();
        });

        token = "Bearer " + Helper.getSharedPre(getContext(), "token", String.class);
        viewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);

        if (category == null) binding.tvCategory.setText("");
        else binding.tvCategory.setText(category.getName());

        if (supplier == null) binding.tvSupplier.setText("");
        else binding.tvSupplier.setText(supplier.getName());

        getData();

        binding.tvCategory.setOnClickListener(view13 -> {
            Helper.loadFragment(getParentFragmentManager(), new CategoryListFragment(), null, R.id.frag_container_product);
        });

        binding.tvSupplier.setOnClickListener(view13 -> {
            Helper.loadFragment(getParentFragmentManager(), new SupplierListSelectFragment(), null, R.id.frag_container_product);
        });

        binding.spnZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                zone = (Integer) ((Spinner) adapterView).getAdapter().getItem(position);
//                        product.getLocation().setZone(zone);
//                        Toast.makeText(getContext(), ""+selectedItem, Toast.LENGTH_SHORT).show();
                ApiLocation.apiLocation.getShelf(token, zone).enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                        if (response.isSuccessful()) {
                            List<String> listShelf = response.body();
                            Helper.onSetStringSpn(getContext(), listShelf, binding.spnShelf, " ");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable throwable) {
                        Log.d("onFailure", "onFailure: " + throwable.getMessage());
                        Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spnShelf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                shelf = (String) ((Spinner) adapterView).getAdapter().getItem(position);
//                        product.getLocation().setShelf(shelf);
//                        Log.d("tag_kiemTra", "onItemSelected: "+product.getLocation().getShelf());
//                        Toast.makeText(getContext(), ""+selectedItem, Toast.LENGTH_SHORT).show();
                ApiLocation.apiLocation.getLevel(token, shelf).enqueue(new Callback<List<Integer>>() {
                    @Override
                    public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                        if (response.isSuccessful()) {
                            List<Integer> listLevel = response.body();
                            Helper.onSetIntSpn(getContext(), listLevel, binding.spnLevel, -1);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Integer>> call, Throwable throwable) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spnLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                level = (Integer) ((Spinner) adapterView).getAdapter().getItem(position);
//                        product.getLocation().setLevel(level);
//                        Log.d("tag_kiemTra", "level: "+product.getLocation().getLevel());
//                        Toast.makeText(getContext(), ""+selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        binding.imgProduct.setOnClickListener(view14 -> {
            onRequestPermission();
        });

        binding.tvSave.setOnClickListener(view12 -> {
            String name = binding.edtName.getText().toString().trim();
            String tvcategory = binding.tvCategory.getText().toString().trim();
            String tvsupplier = binding.tvSupplier.getText().toString().trim();
            String export_price = binding.edtExportPrice.getText().toString().trim();
            String import_price = binding.edtImportPrice.getText().toString().trim();
            String unit = binding.edtUnit.getText().toString().trim();

            if (name.isEmpty() || tvcategory.isEmpty() || tvsupplier.isEmpty() || export_price.isEmpty() || import_price.isEmpty() || unit.isEmpty()) {
                Toast.makeText(getContext(), "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
            } else {
                Helper.isContainSpace(name, binding.tvWarName);
                Helper.isContainSpace(tvcategory, binding.tvWarCat);
                Helper.isContainSpace(tvsupplier, binding.tvWarSup);
                Helper.isNumberValid(export_price, binding.tvWarExportPrice);
                Helper.isNumberValid(import_price, binding.tvWarImportPrice);
                Helper.isContainSpace(unit, binding.tvWarUnit);

                if (binding.tvWarName.getText().toString().isEmpty() &&
                        binding.tvWarCat.getText().toString().isEmpty() &&
                        binding.tvWarSup.getText().toString().isEmpty() &&
                        binding.tvWarExportPrice.getText().toString().isEmpty() &&
                        binding.tvWarImportPrice.getText().toString().isEmpty() &&
                        binding.tvWarUnit.getText().toString().isEmpty()
                ) {
                    if (uri != null) listMultipartBody = new MultipartBody.Part[]{Helper.getRealPathFile(getContext(), uri, "images[]")};

                    if (category == null || supplier == null)
                        Toast.makeText(getContext(), "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                    else {
                        product = new Product(supplier.getId(), category.getId(), name, unit, Integer.parseInt(import_price), Integer.parseInt(export_price));
                        onAddProduct(product);
                    }
                }
            }
        });
    }

    private void getData() {
        ApiLocation.apiLocation.getZone(token).enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                if (response.isSuccessful()) {
                    List<Integer> listZone = response.body();
                    Helper.onSetIntSpn(getContext(), listZone, binding.spnZone, -1);
                }
            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onAddProduct(Product product) {
        ApiProduct.apiProduct.add(token,
                Helper.createStringPart(product.getProduct_name()),
                Helper.createIntPart(product.getExport_price()),
                Helper.createIntPart(product.getImport_price()),
                Helper.createStringPart(product.getUnit()),
                Helper.createIntPart(product.getSupplier_id()),
                Helper.createIntPart(product.getCategory_id()),
                Helper.createIntPart(zone),
                Helper.createStringPart(shelf),
                Helper.createIntPart(level),
                listMultipartBody).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
                    ProductFragment.isLoadData = true;
                    requireActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Helper.openGallery(getActivity(), launcher);
            return;
        }
        if (getActivity().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Helper.openGallery(getActivity(), launcher);
        } else {
            String permission = (Manifest.permission.READ_EXTERNAL_STORAGE);
            requestPermissions(new String[]{permission}, 10);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Helper.openGallery(getActivity(), launcher);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        viewModel.get().observe(getViewLifecycleOwner(), item -> {
            if (item instanceof Category) {
                category = (Category) item;
                binding.tvCategory.setText(category.getName());
            }
            if (item instanceof Supplier) {
                supplier = (Supplier) item;
                binding.tvSupplier.setText(supplier.getName());
            }
        });
    }

}
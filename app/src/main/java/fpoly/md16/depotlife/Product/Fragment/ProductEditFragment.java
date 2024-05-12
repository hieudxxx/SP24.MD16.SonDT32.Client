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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fpoly.md16.depotlife.Category.Fragment.CategoryListFragment;
import fpoly.md16.depotlife.Category.Model.Category;
import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiProduct;
import fpoly.md16.depotlife.Helper.Interfaces.onClickListener.onItemRcvClick;
import fpoly.md16.depotlife.Product.Adapter.ProductImagesAdapter;
import fpoly.md16.depotlife.Product.Model.Image;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Supplier.Fragment.SupplierListSelectFragment;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.ViewModel.ShareViewModel;
import fpoly.md16.depotlife.databinding.FragmentProductEditBinding;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductEditFragment extends Fragment implements onItemRcvClick<Integer> {
    private FragmentProductEditBinding binding;
    private String token;
    private Product product;
    private Category category;
    private Supplier supplier;
    private Bundle bundle;
    private int id_product;
    private ShareViewModel<Object> viewModel;
    private Uri uri;
    private MultipartBody.Part[] listMultipartBody;
    private List<Image> listImages;
    private String pin_image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductEditBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.imgBack.setOnClickListener(view1 -> {requireActivity().getSupportFragmentManager().popBackStack();});

        token = "Bearer " + Helper.getSharedPre(getContext(), "token", String.class);
        viewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);

        bundle = getArguments();
        if (bundle != null) {
            product = (Product) bundle.getSerializable("product");
            if (product != null) {

                getData();


                listImages = new ArrayList<>();
                listNames = new ArrayList<>();
                ApiProduct.apiProduct.getProductImages(token, product.getId(), product.getImg()).enqueue(new Callback<ImagesResponse>() {
                    @Override
                    public void onResponse(Call<ImagesResponse> call, Response<ImagesResponse> response) {
                        if (response.isSuccessful()) {
                            ImagesResponse imagesResponse = response.body();
                            if (imagesResponse != null) {
                                String[] path = imagesResponse.getPaths();
                                String [] names = imagesResponse.getNames();
                                if (path != null){
                                    if (path.length > 0) {
                                        if (listImages != null) {
                                            listImages.addAll(Arrays.asList(path));
                                            listNames.addAll(Arrays.asList(names));
                                            if (imagesResponse.getImage() != null) {
                                                for (int i = 0; i < listImages.size(); i++) {
                                                    if (imagesResponse.getImage().equalsIgnoreCase(listImages.get(i))) {
                                                        index = i;
                                                    }
                                                }
                                            }
                                            ProductImagesAdapter imagesAdapter = new ProductImagesAdapter(getContext(), listImages, index, ProductEditFragment.this);
                                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                                            binding.rcvImages.setLayoutManager(layoutManager);
                                            binding.rcvImages.setAdapter(imagesAdapter);
                                        }
                                    }

                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ImagesResponse> call, Throwable throwable) {
                        Log.d("onFailure", "onFailure: " + throwable.getMessage());
                    }
                });

                binding.imgProduct.setOnClickListener(view14 -> { onRequestPermission();});

                binding.imgProduct.setOnClickListener(view14 -> {onRequestPermission();});


                binding.tvCategory.setOnClickListener(view13 -> {Helper.loadFragment(getParentFragmentManager(), new CategoryListFragment(), bundle, R.id.frag_container_product);});

                binding.tvSupplier.setOnClickListener(view13 -> {Helper.loadFragment(getParentFragmentManager(), new SupplierListSelectFragment(), bundle, R.id.frag_container_product);});

                binding.tvSave.setOnClickListener(view12 -> {
                    String name = binding.edtName.getText().toString().trim();
                    String export_price = binding.edtExportPrice.getText().toString().trim();
                    String import_price = binding.edtImportPrice.getText().toString().trim();
                    String unit = binding.edtUnit.getText().toString().trim();
                    if (name.isEmpty() || export_price.isEmpty() || import_price.isEmpty() || unit.isEmpty()) Toast.makeText(getContext(), "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                    else {
                        Helper.isContainSpace(name, binding.tvWarName);
                        Helper.isNumberValid(export_price, binding.tvWarExportPrice);
                        Helper.isNumberValid(import_price, binding.tvWarImportPrice);
                        Helper.isContainSpace(unit, binding.tvWarUnit);

                        if (binding.tvWarName.getText().toString().isEmpty() &&
                                binding.tvWarExportPrice.getText().toString().isEmpty() &&
                                binding.tvWarImportPrice.getText().toString().isEmpty() &&
                                binding.tvWarUnit.getText().toString().isEmpty()
                        ) {
                            if (category == null || category.getId() <= 0) {
                                category = new Category();
                                category.setId(product.getCategory_id());
                            }

                            if (supplier == null || supplier.getId() <= 0) {
                                supplier = new Supplier();
                                supplier.setId(product.getSupplier_id());
                            }

                            if (uri != null) listMultipartBody = new MultipartBody.Part[]{Helper.getRealPathFile(getContext(), uri)};

//                            if (img == null) img = product.getImg();
                            if (pin_image == null) pin_image = "";
                            product = new Product(supplier.getId(), category.getId(), name, unit, Integer.parseInt(import_price), Integer.parseInt(export_price));
                            onEditProduct(product);
                        } else {
                            Toast.makeText(getContext(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }

        }
    }

    private void onEditProduct(Product product) {
        ApiProduct.apiProduct.update(token, id_product,
                Helper.createStringPart(product.getProduct_name()),
                Helper.createIntPart(product.getExport_price()),
                Helper.createIntPart(product.getImport_price()),
                Helper.createIntPart(product.getInventory()),
                Helper.createStringPart(product.getUnit()),
                Helper.createIntPart(product.getSupplier_id()),
                Helper.createIntPart(product.getCategory_id()),
                Helper.createStringPart(pin_image),
                listMultipartBody).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
//                                        Helper.loadFragment(getParentFragmentManager(), new ProductFragment(), null, R.id.frag_container_product);
                    requireActivity().getSupportFragmentManager().popBackStack();

                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData() {
        id_product = product.getId();
        binding.edtName.setText(product.getProduct_name());
        binding.edtId.setText(product.getId() + "");
        binding.tvCategory.setText(product.getCategory().getName());
        binding.tvSupplier.setText(product.getSupplier().getName());
        binding.edtExportPrice.setText(product.getExport_price() + "");
        binding.edtImportPrice.setText(product.getImport_price() + "");
        binding.edtUnit.setText(product.getUnit());

        listImages = new ArrayList<>();
        listImages = Arrays.asList(product.getImg());

        Helper.setImgProduct(product.getImg(), binding.imgProduct);

        ProductImagesAdapter imagesAdapter = new ProductImagesAdapter(getContext(), listImages, ProductEditFragment.this);
        binding.rcvImages.setAdapter(imagesAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.rcvImages.setLayoutManager(layoutManager);

    }

    private void onRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Helper.openGallery(getContext());
            return;
        }
        if (getActivity().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) Helper.openGallery(getContext());
        else {
            String permission = (Manifest.permission.READ_EXTERNAL_STORAGE);
            requestPermissions(new String[]{permission}, 10);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        viewModel.get().observe(getViewLifecycleOwner(), item -> {
            if (item instanceof Category) {
                category = (Category) item;
                binding.tvCategory.setText(category.getName());
                product.setCategory(category);
                product.setCategory_id(category.getId());
            }
            if (item instanceof Supplier) {
                supplier = (Supplier) item;
                binding.tvSupplier.setText(supplier.getName());
                product.setSupplier(supplier);
                product.setSupplier_id(supplier.getId());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Helper.openGallery(getContext());
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uri = data.getData();
        binding.imgProduct.setImageURI(uri);
    }

    @Override
    public void onClick(Integer position) {
        if (position > -1) pin_image = listImages.get(position).getName();
    }
}
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fpoly.md16.depotlife.Category.Fragment.CategoryListFragment;
import fpoly.md16.depotlife.Category.Model.Category;
import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiProduct;
import fpoly.md16.depotlife.Product.Adapter.ProductImagesAdapter;
import fpoly.md16.depotlife.Product.Model.ImagesResponse;
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

public class ProductEditFragment extends Fragment {
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
    private List<String> listImages;


//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult o) {
//                Intent data = o.getData();
//                if (data != null) {
//                    return;
//                }
//                Uri uri = data.getData();
//                mUri = uri;
//                binding.imgProduct.setImageURI(uri);
//                Log.d("tag_kiemTra", "onActivityResult1: "+uri);
//                product.setImg(String.valueOf(uri));
//                if (o.getResultCode() == Activity.RESULT_OK) {
//
//                }
//            }
//        });
//        resultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
//            @Override
//            public void onActivityResult(Uri uri) {
//                if (uri != null) {
//                    binding.imgProduct.setImageURI(uri);
//                }
//            }
//        });
//}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductEditBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbProduct);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.imgBack.setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        token = "Bearer " + (String) Helper.getSharedPre(getContext(), "token", String.class);
        viewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);

        bundle = getArguments();
        if (bundle != null) {
            product = (Product) bundle.getSerializable("product");

//            category = (Category) bundle.getSerializable("category");

            if (product != null) {
                id_product = product.getId();
                binding.edtName.setText(product.getProduct_name());
                binding.edtId.setText(product.getId() + "");
                binding.tvCategory.setText(product.getCategory_name());
                binding.tvSupplier.setText(product.getSupplier_name());
                binding.edtExportPrice.setText(product.getExport_price() + "");
                binding.edtImportPrice.setText(product.getImport_price() + "");
                binding.edtInventory.setText(product.getInventory() + "");
                binding.edtUnit.setText(product.getUnit());

                if (product.getImg() == null) product.setImg("null");
                Helper.getImagesProduct(product, token, binding.imgProduct);

                listImages = new ArrayList<>();
                ApiProduct.apiProduct.getProductImages(token, product.getId(), product.getImg()).enqueue(new Callback<ImagesResponse>() {
                    @Override
                    public void onResponse(Call<ImagesResponse> call, Response<ImagesResponse> response) {
                        if (response.isSuccessful()) {
                            ImagesResponse imagesResponse = response.body();
                            if (imagesResponse != null) {
                                String[] path = imagesResponse.getPaths();
                                if (path.length > 0) {
                                    if (listImages != null) {
                                        listImages.addAll(Arrays.asList(path));
                                        Log.d("tag_kiemTra", "onResponse: "+listImages.toString());

                                        int index = -1;
                                        Log.d("tag_kiemTra", "imagesResponse: "+imagesResponse.getImage());
                                        if (imagesResponse.getImage() != null || !imagesResponse.getImage().isEmpty()) {
                                            for (int i = 0; i < listImages.size(); i++){
                                                if (imagesResponse.getImage().equalsIgnoreCase(listImages.get(i))){
                                                    index = i;
                                                    Log.d("tag_kiemTra", "onResponse: "+index);
                                                }
                                            }

                                        }
                                        ProductImagesAdapter imagesAdapter = new ProductImagesAdapter(getContext(), listImages, index);
                                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                                        binding.rcvImages.setLayoutManager(layoutManager);
                                        binding.rcvImages.setAdapter(imagesAdapter);
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
                    String export_price = binding.edtExportPrice.getText().toString().trim();
                    String import_price = binding.edtImportPrice.getText().toString().trim();
                    String inventory = binding.edtInventory.getText().toString().trim();
                    String unit = binding.edtUnit.getText().toString().trim();
                    if (name.isEmpty() || export_price.isEmpty() || import_price.isEmpty() || inventory.isEmpty() || unit.isEmpty()) {
                        Toast.makeText(getContext(), "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                    } else {
                        Helper.isContainSpace(name, binding.tvWarName);
                        Helper.isNumberValid(export_price, binding.tvWarExportPrice);
                        Helper.isNumberValid(import_price, binding.tvWarImportPrice);
                        Helper.isNumberValid(inventory, binding.tvWarInventory);
                        Helper.isContainSpace(unit, binding.tvWarUnit);

                        if (binding.tvWarName.getText().toString().isEmpty() &&
                                binding.tvWarExportPrice.getText().toString().isEmpty() &&
                                binding.tvWarImportPrice.getText().toString().isEmpty() &&
                                binding.tvWarInventory.getText().toString().isEmpty() &&
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

                            if (uri != null) {
                                listMultipartBody = new MultipartBody.Part[]{Helper.getRealPathFile(getContext(), uri)};
                            }

                            product = new Product(supplier.getId(), category.getId(), name, unit, Integer.parseInt(import_price), Integer.parseInt(export_price), Integer.parseInt(inventory));
                            ApiProduct.apiProduct.update(token, id_product,
                                    Helper.createStringPart(product.getProduct_name()),
                                    Helper.createIntPart(product.getExport_price()),
                                    Helper.createIntPart(product.getImport_price()),
                                    Helper.createIntPart(product.getInventory()),
                                    Helper.createStringPart(product.getUnit()),
                                    Helper.createIntPart(product.getSupplier_id()),
                                    Helper.createIntPart(product.getCategory_id()), listMultipartBody).enqueue(new Callback<Product>() {
                                @Override
                                public void onResponse(Call<Product> call, Response<Product> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
                                        requireActivity().getSupportFragmentManager().popBackStack();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Product> call, Throwable throwable) {
                                    Log.d("onFailure", "onFailure: " + throwable.getMessage());
                                    Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }

        }
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
//        Intent i = new Intent();
//        i.setType("image/*");
//        i.setAction(Intent.ACTION_GET_CONTENT);
//        resultLauncher.launch(Intent.createChooser(i, "Select Picture"));
    }

    @Override
    public void onResume() {
        super.onResume();

        viewModel.get().observe(getViewLifecycleOwner(), item -> {
            if (item instanceof Category) {
                category = (Category) item;
                binding.tvCategory.setText(category.getName());
                product.setCategory_name(category.getName());
                product.setCategory_id(category.getId());
            }
            if (item instanceof Supplier) {
                supplier = (Supplier) item;
                binding.tvSupplier.setText(supplier.getName());
                product.setSupplier_name(supplier.getName());
                product.setSupplier_id(supplier.getId());
            }

        });
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
        binding.imgProduct.setImageURI(uri);
        product.setImg(String.valueOf(uri));
    }
}
package fpoly.md16.depotlife.Product.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fpoly.md16.depotlife.Category.Fragment.CategoryListFragment;
import fpoly.md16.depotlife.Category.Model.Category;
import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.API;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiLocation;
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
    private String pin_image;
    private Product product;
    private Category category;
    private Supplier supplier;
    private Bundle bundle;
    private Context context;
    private ShareViewModel<Object> viewModel;
    private Uri uri;
    private MultipartBody.Part[] listMultipartBody;
    private List<Image> listImages;
    private int id_product;
    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    uri = result.getData().getData();
                    binding.imgProduct.setImageURI(uri);
                }
            }
    );
    private int zone;
    private String shelf;
    private int level;
    private int positonItem;
    private boolean isLocation = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductEditBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();
        binding.imgBack.setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        token = "Bearer " + Helper.getSharedPre(context, "token", String.class);
        viewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
        bundle = getArguments();
        if (bundle != null) {
            product = (Product) bundle.getSerializable("product");
            if (product != null) {
                listImages = new ArrayList<>();

                getData();

                binding.imgProduct.setOnClickListener(view14 -> {
                    onRequestPermission();

//                    Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    pickPhotoIntent.setType("image/*");
//                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    Intent chooserIntent = Intent.createChooser(pickPhotoIntent, "Chọn ảnh hoặc chụp ảnh mới");
//                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[]{takePictureIntent});
                });

                binding.tvCategory.setOnClickListener(view13 -> {
                    Helper.loadFragment(getParentFragmentManager(), new CategoryListFragment(), bundle, R.id.frag_container_product);
                });

                binding.tvSupplier.setOnClickListener(view13 -> {
                    Helper.loadFragment(getParentFragmentManager(), new SupplierListSelectFragment(), bundle, R.id.frag_container_product);
                });

                binding.spnZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                        zone = (Integer) ((Spinner) adapterView).getAdapter().getItem(position);
                        ApiLocation.apiLocation.getShelf(token, zone).enqueue(new Callback<List<String>>() {
                            @Override
                            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                                if (response.isSuccessful()) {
                                    List<String> listShelf = response.body();
                                    Helper.onSetStringSpn(getContext(), listShelf, binding.spnShelf, product.getLocation().getShelf());
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
                        ApiLocation.apiLocation.getLevel(token, shelf).enqueue(new Callback<List<Integer>>() {
                            @Override
                            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                                if (response.isSuccessful()) {
                                    List<Integer> listLevel = response.body();
                                    Helper.onSetIntSpn(getContext(), listLevel, binding.spnLevel, product.getLocation().getLevel());
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
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                binding.imgLocation.setOnClickListener(view15 -> {
                    if (isLocation) {
                        binding.imgLocation.setImageResource(R.drawable.edit_invoice);
                        binding.layoutLocation.setVisibility(View.GONE);
                        isLocation = !isLocation;
                    } else {
                        binding.imgLocation.setImageResource(R.drawable.delete_img_product);
                        binding.layoutLocation.setVisibility(View.VISIBLE);
                        isLocation = !isLocation;
                    }
                    Toast.makeText(context, ""+isLocation, Toast.LENGTH_SHORT).show();
                });

                binding.tvSave.setOnClickListener(view12 -> {
                    String name = binding.edtName.getText().toString().trim();
                    String export_price = binding.edtExportPrice.getText().toString().trim();
                    String import_price = binding.edtImportPrice.getText().toString().trim();
                    String unit = binding.edtUnit.getText().toString().trim();
                    if (name.isEmpty() || export_price.isEmpty() || import_price.isEmpty() || unit.isEmpty())
                        Toast.makeText(getContext(), "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
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

                            if (uri != null)
                                listMultipartBody = new MultipartBody.Part[]{Helper.getRealPathFile(getContext(), uri)};

                            if (pin_image == null)
                                pin_image = listImages.get(positonItem).getName();

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

    private void onEditProduct(Product product) {
        if (!isLocation) {
            zone = -1;
            shelf = " ";
            level = -1;
        }
        ApiProduct.apiProduct.update(token, id_product,
                Helper.createStringPart(product.getProduct_name()),
                Helper.createIntPart(product.getExport_price()),
                Helper.createIntPart(product.getImport_price()),
                Helper.createStringPart(product.getUnit()),
                Helper.createIntPart(product.getSupplier_id()),
                Helper.createIntPart(product.getCategory_id()),
                Helper.createIntPart(zone),
                Helper.createStringPart(shelf),
                Helper.createIntPart(level),
                Helper.createStringPart(pin_image),
                listMultipartBody).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
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
        binding.tvOldLocation.setText(product.getLocation().getCode());

        listImages = Arrays.asList(product.getImg());

        Helper.setImgProduct(product.getImg(), binding.imgProduct);

        ProductImagesAdapter imagesAdapter = new ProductImagesAdapter(getContext(), listImages, ProductEditFragment.this, token, true);
        binding.rcvImages.setAdapter(imagesAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.rcvImages.setLayoutManager(layoutManager);

        ApiLocation.apiLocation.getZone(token).enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                if (response.isSuccessful()) {
                    List<Integer> listZone = response.body();
                    Helper.onSetIntSpn(getContext(), listZone, binding.spnZone, product.getLocation().getZone());
                }
            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable throwable) {
                Log.d("onFailure1111", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
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
    public void onClick(Integer position) {
        if (position > -1) {
            pin_image = listImages.get(position).getName();
            positonItem = position;
            Picasso.get().load(API.URL_IMG + listImages.get(position).getPath().replace("public", "")).into(binding.imgProduct);
        }
    }
}
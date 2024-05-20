package fpoly.md16.depotlife.Invoice.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiProduct;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiSupplier;
import fpoly.md16.depotlife.Invoice.Adapter.ChooseProductAdapter;
import fpoly.md16.depotlife.Invoice.Adapter.DialogProductAdapter;
import fpoly.md16.depotlife.Invoice.Adapter.DialogSupplierAdapter;
import fpoly.md16.depotlife.Product.Model.Expiry;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.Product.Model.ProductResponse;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.Supplier.Model.SupplierResponse;
import fpoly.md16.depotlife.databinding.DialogLayoutBinding;
import fpoly.md16.depotlife.databinding.DialogProductLayoutBinding;
import fpoly.md16.depotlife.databinding.FragmentInvoiceAddBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InvoiceAddFragment extends Fragment {
    private FragmentInvoiceAddBinding binding;
    public String token;
    private Uri imageUri;
    private int invoiceType;
    public String invoiceCreator;
    private final Handler handler = new Handler();
    private Runnable runnable;
    private int pageIndex = 1;
    private int perPage = 0;
    private List<Supplier> list;
    private List<Product> listProduct;
    private List<Product> listChooseProduct;
    private DialogSupplierAdapter dialogSupplierAdapter;
    private DialogProductAdapter dialogProductAdapter;
    private ChooseProductAdapter chooseProductAdapter;
    public static boolean isLoadData = false;
    final int SEARCH_ID = R.id.action_search;

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    binding.imgSignature.setImageURI(imageUri);
                }
            }
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInvoiceAddBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        token = "Bearer " + Helper.getSharedPre(getContext(), "token", String.class);
        invoiceCreator = (String) Helper.getSharedPre(getContext(), "name", String.class);
        binding.tvInvoiceCreator.setText(invoiceCreator);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());

        binding.tvDateTime.setText(currentDate);

        binding.imgBack.setOnClickListener(view1 -> requireActivity().finish());
        list = new ArrayList<>();
        listProduct = new ArrayList<>();
        listChooseProduct = new ArrayList<>();

        if (invoiceType == 0) {
            binding.spnInvoiceType.setText("Hóa đơn nhập");
            binding.idCustomer.setVisibility(View.GONE);
            binding.idSupplier.setVisibility(View.VISIBLE);
        } else {
            binding.btnAddProduct.setVisibility(View.VISIBLE);
            binding.spnInvoiceType.setText("Hóa đơn xuất");
            binding.idCustomer.setVisibility(View.VISIBLE);
            binding.idSupplier.setVisibility(View.GONE);
        }

        String[] items_status_payment = new String[]{"Chưa thanh toán", "Đã thanh toán"};
        ArrayAdapter<String> adapter_status = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_dropdown_item_1line, items_status_payment);
        binding.spnPaymentStatus.setAdapter(adapter_status);

        binding.spnSupName.setOnClickListener(view12 -> {
            showDialog(view.getContext());
        });

        binding.spnSupName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.btnAddProduct.setVisibility(View.VISIBLE);
            }
        });

        binding.btnAddProduct.setOnClickListener(view13 -> {
            showDialogProduct(view.getContext(), binding);
        });

        binding.edDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view.getContext(), binding.edDueDate);
            }
        });

        binding.idSignature.setOnClickListener(view14 -> {
            onRequestPermission();
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

    private File convertUriToFile(Uri uri) {
        File file = null;
        try {
            ContentResolver contentResolver = getActivity().getContentResolver();
            InputStream inputStream = contentResolver.openInputStream(uri);
            file = new File(getActivity().getCacheDir(), "temp_image.jpg");
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            Log.e("File Conversion", "Error converting URI to file", e);
        }
        return file;
    }

    public void getChooseProdut(FragmentInvoiceAddBinding binding, Context context) {
        binding.rcvListChooseProduct.setVisibility(View.VISIBLE);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        binding.rcvListChooseProduct.setLayoutManager(manager);
        chooseProductAdapter = new ChooseProductAdapter(new ChooseProductAdapter.InterClickItemData() {
            @Override
            public void Expiry(Expiry expiry) {
                Log.d("dataaaaaaaaaa", "expiry: " + expiry);
            }
        }, invoiceType);

        binding.rcvListChooseProduct.setAdapter(chooseProductAdapter);
        chooseProductAdapter.setData(listChooseProduct);
    }

    public void showDatePickerDialog(Context context, final TextInputEditText editText) {
        // Lấy ngày hiện tại làm ngày mặc định trong bộ chọn
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Tạo và hiển thị DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        String formattedDate = String.format(Locale.getDefault(), "%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                        editText.setText(formattedDate);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    private void getData(DialogLayoutBinding dialogLayoutBinding) {
        ApiSupplier.apiSupplier.getData("Bearer " + token, pageIndex).enqueue(new Callback<SupplierResponse>() {
            @Override
            public void onResponse(Call<SupplierResponse> call, Response<SupplierResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        onCheckList(dialogLayoutBinding, response.body());
                        perPage = response.body().getLast_page();
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(), "Không thể lấy dữ liệu danh mục", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SupplierResponse> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataSearch(DialogLayoutBinding dialogLayoutBinding, String keyword) {
        ApiSupplier.apiSupplier.getDataSearch("Bearer " + token, keyword).enqueue(new Callback<List<Supplier>>() {
            @Override
            public void onResponse(Call<List<Supplier>> call, Response<List<Supplier>> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        onCheckListSearch(dialogLayoutBinding, response.body());
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(), "Không thể lấy dữ liệu danh mục", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Supplier>> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
            }
        });
    }

    private void onCheckList(DialogLayoutBinding dialogLayoutBinding, SupplierResponse supplierResponse) {
        if (supplierResponse.getData() != null) {
            List<Supplier> tempList = Arrays.asList(supplierResponse.getData()); // hoặc có thể dùng foreach để check từng item
            list.addAll(tempList);
            dialogLayoutBinding.rcvDialogSupplier.setVisibility(View.VISIBLE);
            dialogLayoutBinding.tvEmpty.setVisibility(View.GONE);
            dialogLayoutBinding.pbLoading.setVisibility(View.GONE);
            dialogLayoutBinding.pbLoadMore.setVisibility(View.GONE);
            setHasOptionsMenu(true);
            dialogLayoutBinding.rcvDialogSupplier.setAdapter(dialogSupplierAdapter);
            dialogSupplierAdapter.setData(list);
            pageIndex++;
        } else {
            setHasOptionsMenu(false);
            dialogLayoutBinding.rcvDialogSupplier.setVisibility(View.GONE);
            dialogLayoutBinding.pbLoading.setVisibility(View.GONE);
            dialogLayoutBinding.tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    public void onCheckListSearch(DialogLayoutBinding dialogLayoutBinding, List<Supplier> supplier) {
        if (supplier != null) {
            list.clear();
            list.addAll(supplier);
            dialogLayoutBinding.rcvDialogSupplier.setVisibility(View.VISIBLE);
            dialogLayoutBinding.tvEmpty.setVisibility(View.GONE);
            dialogLayoutBinding.pbLoading.setVisibility(View.GONE);
            dialogLayoutBinding.pbLoadMore.setVisibility(View.GONE);
            dialogLayoutBinding.rcvDialogSupplier.setAdapter(dialogSupplierAdapter);
            dialogSupplierAdapter.setData(list);
        } else {
            list.clear();
            getData(dialogLayoutBinding);
        }
    }

    private void showDialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_layout);

        DialogLayoutBinding dialogLayoutBinding = DialogLayoutBinding.bind(dialog.findViewById(R.id.layout_dialog));
        Toolbar toolbar = dialogLayoutBinding.toolbar;

        // Thiết lập menu cho Toolbar
        toolbar.inflateMenu(R.menu.search_bar); // Thay 'your_menu' bằng tên file menu của bạn
        // Lấy SearchManager từ hệ thống
        SearchManager searchManager = (SearchManager) dialog.getContext().getSystemService(Context.SEARCH_SERVICE);

        // Tìm SearchView và cấu hình nó
        MenuItem searchItem = toolbar.getMenu().findItem(R.id.action_search); // Đảm bảo rằng ID này khớp với ID trong file menu của bạn
        SearchView searchView = (SearchView) searchItem.getActionView();
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(((Activity) context).getComponentName()));
            searchView.setMaxWidth(Integer.MAX_VALUE);
        }

        LinearLayoutManager manager = new LinearLayoutManager(dialog.getContext());
        dialogLayoutBinding.rcvDialogSupplier.setLayoutManager(manager);
        dialogSupplierAdapter = new DialogSupplierAdapter(supplier -> {
            binding.spnSupName.setText(supplier.getName());
            dialog.dismiss();
        });

        getData(dialogLayoutBinding);

        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    //Log.d("TAGGGGGG", "onQueryTextSubmit: " + query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (!newText.isEmpty()) {
                        debounceSearch(newText, 500, dialogLayoutBinding);
                        return true;
                    } else {
                        list.clear();
                        getData(dialogLayoutBinding);
                    }
                    return false;
                }
            });
        }

        dialogLayoutBinding.nestScroll.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                dialogLayoutBinding.pbLoadMore.setVisibility(View.VISIBLE);
                if (pageIndex <= perPage) {
                    getData(dialogLayoutBinding);
                    dialogLayoutBinding.pbLoadMore.setVisibility(View.GONE);
                } else {
                    dialogLayoutBinding.pbLoadMore.setVisibility(View.GONE);
                }
            }
        });

        // Xử lý sự kiện khi một mục menu được chọn
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return item.getItemId() == SEARCH_ID;
            }
        });

        dialog.show();
    }

    private void showDialogProduct(Context context, FragmentInvoiceAddBinding binding) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_product_layout);

        DialogProductLayoutBinding dialogProductLayoutBinding = DialogProductLayoutBinding.bind(dialog.findViewById(R.id.layout_dialog_product));
        Toolbar toolbar = dialogProductLayoutBinding.toolbarDialogProduct;

        // Thiết lập menu cho Toolbar
        toolbar.inflateMenu(R.menu.search_bar); // Thay 'your_menu' bằng tên file menu của bạn
        // Lấy SearchManager từ hệ thống
        SearchManager searchManager = (SearchManager) dialog.getContext().getSystemService(Context.SEARCH_SERVICE);

        // Tìm SearchView và cấu hình nó
        MenuItem searchItem = toolbar.getMenu().findItem(R.id.action_search); // Đảm bảo rằng ID này khớp với ID trong file menu của bạn
        SearchView searchView = (SearchView) searchItem.getActionView();
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(((Activity) context).getComponentName()));
            searchView.setMaxWidth(Integer.MAX_VALUE);
        }

        LinearLayoutManager manager = new LinearLayoutManager(dialog.getContext());
        dialogProductLayoutBinding.rcvDialogProduct.setLayoutManager(manager);

        dialogProductAdapter = new DialogProductAdapter((product, aBoolean) -> {
            if (aBoolean) {
                listChooseProduct.add(product);
            } else {
                listChooseProduct.remove(product);
            }
        }, listChooseProduct);
        getDataProduct(dialogProductLayoutBinding);

        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    //Log.d("TAGGGGGG", "onQueryTextSubmit: " + query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (!newText.isEmpty()) {
                        debounceSearchProduct(newText, 500, dialogProductLayoutBinding);
                        return true;
                    } else {
                        listProduct.clear();
                        getDataProduct(dialogProductLayoutBinding);
                    }
                    return false;
                }
            });
        }

        dialogProductLayoutBinding.nestScrollDialogProduct.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                dialogProductLayoutBinding.pbLoadMoreProduct.setVisibility(View.VISIBLE);
                if (pageIndex <= perPage) {
                    getDataProduct(dialogProductLayoutBinding);
                    dialogProductLayoutBinding.pbLoadMoreProduct.setVisibility(View.GONE);
                } else {
                    dialogProductLayoutBinding.pbLoadMoreProduct.setVisibility(View.GONE);
                }
            }
        });

        // Xử lý sự kiện khi một mục menu được chọn
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return item.getItemId() == SEARCH_ID;
            }
        });

        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                getChooseProdut(binding, context);
            }
        });
    }

    private void debounceSearch(final String newText, long delayMillis, DialogLayoutBinding dialogLayoutBinding) {
        // Hủy bỏ Runnable trước đó
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }

        // Tạo Runnable mới để thực hiện tìm kiếm
        runnable = new Runnable() {
            @Override
            public void run() {
                getDataSearch(dialogLayoutBinding, newText);
            }
        };

        // Lên lịch thực hiện tìm kiếm sau một khoảng thời gian delay
        handler.postDelayed(runnable, delayMillis);
    }

    private void debounceSearchProduct(final String newText, long delayMillis, DialogProductLayoutBinding dialogProductLayoutBinding) {
        // Hủy bỏ Runnable trước đó
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }

        // Tạo Runnable mới để thực hiện tìm kiếm
        runnable = new Runnable() {
            @Override
            public void run() {
                getDataSearchProduct(dialogProductLayoutBinding, newText);
            }
        };

        // Lên lịch thực hiện tìm kiếm sau một khoảng thời gian delay
        handler.postDelayed(runnable, delayMillis);
    }

    private void getDataProduct(DialogProductLayoutBinding dialogProductLayoutBinding) {
        ApiProduct.apiProduct.getData("Bearer " + token, pageIndex).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        onCheckListProduct(dialogProductLayoutBinding, response.body());
                        perPage = response.body().getLast_page();
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(), "Không thể lấy dữ liệu hàng hóa", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable throwable) {
                Log.d("zzzzzzzzzz", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataSearchProduct(DialogProductLayoutBinding dialogProductLayoutBinding, String keyword) {
        ApiProduct.apiProduct.productSearch("Bearer " + token, keyword).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        onCheckListSearchProduct(dialogProductLayoutBinding, response.body());
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(), "Không thể lấy dữ liệu danh mục", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
            }
        });
    }

    public void onCheckListSearchProduct(DialogProductLayoutBinding dialogProductLayoutBinding, List<Product> products) {
        if (products != null) {
            listProduct.clear();
            listProduct.addAll(products);
            dialogProductLayoutBinding.rcvDialogProduct.setVisibility(View.VISIBLE);
            dialogProductLayoutBinding.tvEmptyProduct.setVisibility(View.GONE);
            dialogProductLayoutBinding.pbLoadingProduct.setVisibility(View.GONE);
            dialogProductLayoutBinding.pbLoadMoreProduct.setVisibility(View.GONE);
            dialogProductLayoutBinding.rcvDialogProduct.setAdapter(dialogProductAdapter);
            dialogProductAdapter.setData(listProduct);
        } else {
            listProduct.clear();
            getDataProduct(dialogProductLayoutBinding);
        }
    }

    private void onCheckListProduct(DialogProductLayoutBinding dialogProductLayoutBinding, ProductResponse productResponse) {
        if (productResponse.getData() != null) {
            List<Product> tempList = Arrays.asList(productResponse.getData()); // hoặc có thể dùng foreach để check từng item
            listProduct.addAll(tempList);
            dialogProductLayoutBinding.rcvDialogProduct.setVisibility(View.VISIBLE);
            dialogProductLayoutBinding.tvEmptyProduct.setVisibility(View.GONE);
            dialogProductLayoutBinding.pbLoadingProduct.setVisibility(View.GONE);
            dialogProductLayoutBinding.pbLoadMoreProduct.setVisibility(View.GONE);
            dialogProductLayoutBinding.rcvDialogProduct.setAdapter(dialogProductAdapter);
            dialogProductAdapter.setData(listProduct);
            pageIndex++;
        } else {
            dialogProductLayoutBinding.rcvDialogProduct.setVisibility(View.GONE);
            dialogProductLayoutBinding.pbLoadingProduct.setVisibility(View.GONE);
            dialogProductLayoutBinding.tvEmptyProduct.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
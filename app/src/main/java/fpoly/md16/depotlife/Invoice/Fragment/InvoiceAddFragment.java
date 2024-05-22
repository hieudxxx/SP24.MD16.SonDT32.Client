package fpoly.md16.depotlife.Invoice.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
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
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiProduct;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiSupplier;
import fpoly.md16.depotlife.Invoice.Adapter.ChooseProductAdapter;
import fpoly.md16.depotlife.Invoice.Adapter.DialogProductAdapter;
import fpoly.md16.depotlife.Invoice.Adapter.DialogSupplierAdapter;
import fpoly.md16.depotlife.Invoice.Model.Invoice;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.Product.Model.ProductResponse;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.Supplier.Model.SupplierResponse;
import fpoly.md16.depotlife.databinding.DialogSelectBinding;
import fpoly.md16.depotlife.databinding.FragmentInvoiceAddBinding;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceAddFragment extends Fragment {
    private FragmentInvoiceAddBinding binding;
    public String token;
    private String expiryDate;
    private String discount;
    private String note;
    private String term;
    private String sigMame;
    private String currentDate;
    private static final String ARG_PRODUCT_INVOICES = "product_invoices";
    private int invoiceType;
    private int invoiceCreatorId;
    private int paymentStatus = -1;
    private int supplierId = -1;
    private int pageIndexSupplier = 1;
    private int pageIndexProduct = 1;
    private int perPage = 0;
    final int SEARCH_ID = R.id.action_search;

    public static boolean isLoadData = false;

    private List<Supplier> listSupplier;
    private List<Product> listProduct;
    private List<Product> listSelectedProduct;

    private DialogSupplierAdapter dialogSupplierAdapter;
    private DialogProductAdapter dialogProductAdapter;
    private ChooseProductAdapter chooseProductAdapter;

    private final Handler handler = new Handler();
    private Runnable runnable;
    private List<Invoice.ProductInvoice> productInvoices;

    private Uri uri;
    private MultipartBody.Part multipartBody;

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    uri = result.getData().getData();
                    binding.iconSignature.setVisibility(View.GONE);
                    binding.tvSignature.setVisibility(View.GONE);
                    binding.imgSignature.setVisibility(View.VISIBLE);
                    binding.imgSignature.setImageURI(uri);
                }
            }
    );

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productInvoices = (List<Invoice.ProductInvoice>) getArguments().getSerializable(ARG_PRODUCT_INVOICES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInvoiceAddBinding.inflate(inflater, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            invoiceType = bundle.getInt("type_invoice");
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.imgBack.setOnClickListener(view1 -> requireActivity().finish());

        init();
        initSupplier();
        initProduct();

        binding.btnAddInvoice.setOnClickListener(view12 -> {
            expiryDate = binding.edDueDate.getText().toString().trim();
            note = binding.edNotes.getText().toString().trim();
            term = binding.edTerms.getText().toString().trim();
            sigMame = binding.edSigName.getText().toString().trim();

            if (supplierId < 1) binding.tvWarSup.setText(R.string.not_empty);
            else binding.tvWarSup.setText("");

            if (paymentStatus < 0) binding.tvWarPayment.setText(R.string.not_empty);
            else binding.tvWarPayment.setText("");

            if (!binding.edDueDate.getText().toString().isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                try {
                    Date selectedDate = dateFormat.parse(binding.edDueDate.getText().toString());
                    Date today = dateFormat.parse(currentDate);

                    if (selectedDate != null && selectedDate.compareTo(today) < 0)
                        binding.tvWarDate.setText("Không thể chọn ngày quá khứ");
                    else binding.tvWarDate.setText("");

                } catch (ParseException e) {
                    Toast.makeText(getContext(), "Định dạng ngày không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }

            if (listSelectedProduct.isEmpty()) binding.tvWarProduct.setText(R.string.not_empty);
            else binding.tvWarProduct.setText("");

            if (!binding.edtDiscount.getText().toString().isEmpty()) {
                discount = binding.edtDiscount.getText().toString().trim();
                Helper.isNumberValid(discount, binding.tvWarDiscount);
            } else binding.tvWarDiscount.setText("");

            if (!note.isEmpty()) Helper.isContainSpace(note, binding.tvWarNote);
            else binding.tvWarNote.setText("");

            if (!term.isEmpty()) Helper.isContainSpace(term, binding.tvWarTerm);
            else binding.tvWarTerm.setText("");

            if (sigMame.isEmpty()) binding.tvWarSigName.setText(R.string.not_empty);
            else {
                Helper.isContainSpace(sigMame, binding.tvWarSigName);
            }

            if (uri == null) binding.tvWarSigImg.setText(R.string.not_empty);
            else {
                multipartBody = Helper.getRealPathFile(getActivity(), uri, "signature");
                binding.tvWarSigImg.setText("");
            }

            if (binding.tvWarSup.getText().toString().isEmpty() &&
                    binding.tvWarDiscount.getText().toString().isEmpty() &&
                    binding.tvWarPayment.getText().toString().isEmpty() &&
                    binding.tvWarDate.getText().toString().isEmpty() &&
                    binding.tvWarProduct.getText().toString().isEmpty() &&
                    binding.tvWarNote.getText().toString().isEmpty() &&
                    binding.tvWarTerm.getText().toString().isEmpty() &&
                    binding.tvWarSigName.getText().toString().isEmpty() &&
                    binding.tvWarSigImg.getText().toString().isEmpty()
            ) {

            }

        });
    }


    private void init() {
        listSupplier = new ArrayList<>();
        listProduct = new ArrayList<>();
        listSelectedProduct = new ArrayList<>();
        token = "Bearer " + Helper.getSharedPre(getContext(), "token", String.class);
        invoiceCreatorId = (Integer) Helper.getSharedPre(getContext(), "id", Integer.class);
        binding.tvInvoiceCreator.setText((String) Helper.getSharedPre(getContext(), "name", String.class));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        currentDate = dateFormat.format(new Date());
        binding.tvDateTime.setText(currentDate);

        Intent intent = getActivity().getIntent();
        if (intent != null) invoiceType = intent.getIntExtra("type_invoice", -1);
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
        ArrayAdapter<String> adapter_status = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, items_status_payment);
        binding.spnPaymentStatus.setAdapter(adapter_status);

        binding.spnPaymentStatus.setOnItemClickListener((adapterView, view, position, l) -> {
            paymentStatus = position;
            if (position == 0) binding.borderDueDate.setVisibility(View.VISIBLE);
            else binding.borderDueDate.setVisibility(View.GONE);
        });

        binding.edDueDate.setOnClickListener(view12 -> Helper.onShowCaledar(binding.edDueDate, getContext(), "%d-%02d-%02d"));

        binding.idSignature.setOnClickListener(view14 -> {
            onRequestPermission();
        });

    }

    private void initSupplier() {
        binding.spnSupName.setOnClickListener(view12 -> {
            listSupplier.clear();
            pageIndexSupplier = 1;
            showDialogSelectSup(getContext());
        });

        binding.spnSupName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listProduct.clear();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.btnAddProduct.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initProduct() {
        binding.btnAddProduct.setOnClickListener(view13 -> {
            listProduct.clear();
            pageIndexProduct = 1;
            showDialogSelectProduct(getContext(), binding);
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        binding.rcvListProduct.addItemDecoration(dividerItemDecoration);
        chooseProductAdapter = new ChooseProductAdapter(new ChooseProductAdapter.InterClickItemData() {
            @Override
            public void onProductInvoiceUpdated(List<Invoice.ProductInvoice> productInvoices) {
                updateTotalTal(productInvoices);
                for (Invoice.ProductInvoice val : productInvoices) {
                    Log.d("zzzzzzzzzzzzzzzz", "onID: " + val.getProduct_id());
                    Log.d("zzzzzzzzzzzzzzzz", "onExpiry: " + val.getExpiry());
                    Log.d("zzzzzzzzzzzzzzzz", "onQuantity: " + val.getQuantity());
                    Log.d("zzzzzzzzzzzzzzzz", "onQuantity: " + listSelectedProduct);
                    Log.d("zzzzzzzzzzzzzzzz", "------------------------: ");

                }
            }

            @Override
            public void removeItem(Product product) {
                clickDeleteData(product);
            }

        }, invoiceType);
        binding.rcvListProduct.setAdapter(chooseProductAdapter);
    }

    private void updateTotalTal(List<Invoice.ProductInvoice> product_invoice_list) {
        int total = 0;
        for (int i = 0; i < listSelectedProduct.size(); i++) {
            if (invoiceType == 0) {
                total += listSelectedProduct.get(i).getImport_price() * product_invoice_list.get(i).getQuantity();

            }
        }

        discount = binding.edtDiscount.getText().toString().trim();
        int dis = 0;
        Helper.isNumberValid(discount, binding.tvWarDiscount);
        if (binding.tvWarDiscount.getText().toString().isEmpty()) {
            dis = Integer.parseInt(discount);
        }

        double totalPayment = total * (1 - ((double) dis / 100)) * (1 + 0.1);
        binding.tvTotalTaxable.setText(Helper.formatVND(total));
        binding.tvTotalAmount02.setText(Helper.formatVND((int) totalPayment));
        binding.tvTotalAmount01.setText(Helper.formatVND((int) totalPayment));
    }

    private void showDialogSelectSup(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_select);
        DialogSelectBinding dialogSelectBinding = DialogSelectBinding.bind(dialog.findViewById(R.id.dialog_select));

        Toolbar toolbar = dialogSelectBinding.toolbar;
        toolbar.setTitle("Nhà cung cấp");
        toolbar.inflateMenu(R.menu.search_bar);
        toolbar.setOnMenuItemClickListener(item -> item.getItemId() == SEARCH_ID);
        SearchManager searchManager = (SearchManager) dialog.getContext().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = toolbar.getMenu().findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(((Activity) context).getComponentName()));
            searchView.setMaxWidth(Integer.MAX_VALUE);
        }

        dialogSupplierAdapter = new DialogSupplierAdapter(supplier -> {
            binding.spnSupName.setText(supplier.getName());
            supplierId = supplier.getId();
            dialog.dismiss();
        });

        dialogSelectBinding.rcv.setAdapter(dialogSupplierAdapter);
        getDataSup(dialogSelectBinding);

        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (!newText.isEmpty()) {
                        if (runnable != null) handler.removeCallbacks(runnable);
                        runnable = () -> getDataSearchSup(dialogSelectBinding, newText);
                        handler.postDelayed(runnable, 500);
                        return true;
                    } else {
                        listSupplier.clear();
                        getDataSup(dialogSelectBinding);
                    }
                    return false;
                }
            });
        }

        dialogSelectBinding.nestScroll.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                dialogSelectBinding.pbLoadMore.setVisibility(View.VISIBLE);
                if (pageIndexSupplier < perPage) {
                    pageIndexSupplier++;
                    getDataSup(dialogSelectBinding);
                    dialogSelectBinding.pbLoadMore.setVisibility(View.GONE);
                } else {
                    dialogSelectBinding.pbLoadMore.setVisibility(View.GONE);
                }
            }
        });
        dialog.show();
    }

    private void getDataSearchSup(DialogSelectBinding dialogLayoutBinding, String keyword) {
        ApiSupplier.apiSupplier.getDataSearch("Bearer " + token, keyword).enqueue(new Callback<List<Supplier>>() {
            @Override
            public void onResponse(Call<List<Supplier>> call, Response<List<Supplier>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        onCheckListSearchSup(dialogLayoutBinding, response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Supplier>> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
            }
        });
    }

    public void onCheckListSearchSup(DialogSelectBinding dialogLayoutBinding, List<Supplier> supplier) {
        if (supplier != null) {
            listSupplier.clear();
            listSupplier.addAll(supplier);
            dialogLayoutBinding.rcv.setVisibility(View.VISIBLE);
            dialogLayoutBinding.tvEmpty.setVisibility(View.GONE);
            dialogLayoutBinding.pbLoading.setVisibility(View.GONE);
            dialogLayoutBinding.pbLoadMore.setVisibility(View.GONE);
            dialogLayoutBinding.rcv.setAdapter(dialogSupplierAdapter);
            dialogSupplierAdapter.setData(listSupplier);
        } else {
            listSupplier.clear();
            getDataSup(dialogLayoutBinding);
        }
    }

    private void getDataSup(DialogSelectBinding dialogSelectBinding) {
        ApiSupplier.apiSupplier.getData(token, pageIndexSupplier).enqueue(new Callback<SupplierResponse>() {
            @Override
            public void onResponse(Call<SupplierResponse> call, Response<SupplierResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        perPage = response.body().getLast_page();
                        SupplierResponse supplierResponse = response.body();
                        if (supplierResponse.getData() != null) {
                            List<Supplier> tempList = Arrays.asList(supplierResponse.getData());
                            onCheckList1(dialogSelectBinding, tempList);
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<SupplierResponse> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private <T> void onCheckList1(DialogSelectBinding dialogSelectBinding, List<T> list) {
        if (list != null && !list.isEmpty()) {
            if (list.get(0) instanceof Product) {
                List<Product> productList = (List<Product>) list;
                listProduct.addAll(productList);
                dialogProductAdapter.setData(listProduct);
            } else if (list.get(0) instanceof Supplier) {
                List<Supplier> supplierList = (List<Supplier>) list;
                listSupplier.addAll(supplierList);
                dialogSupplierAdapter.setData(listSupplier);
            }
            dialogSelectBinding.rcv.setVisibility(View.VISIBLE);
            dialogSelectBinding.tvEmpty.setVisibility(View.GONE);
            dialogSelectBinding.pbLoading.setVisibility(View.GONE);
            dialogSelectBinding.pbLoadMore.setVisibility(View.GONE);
        } else {
            dialogSelectBinding.rcv.setVisibility(View.GONE);
            dialogSelectBinding.pbLoading.setVisibility(View.GONE);
            dialogSelectBinding.pbLoadMore.setVisibility(View.VISIBLE);
        }
    }

    private void showDialogSelectProduct(Context context, FragmentInvoiceAddBinding binding) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_select);
        DialogSelectBinding dialogSelectBinding = DialogSelectBinding.bind(dialog.findViewById(R.id.dialog_select));

        Toolbar toolbar = dialogSelectBinding.toolbar;
        toolbar.setTitle("Hàng hóa");
        toolbar.inflateMenu(R.menu.search_bar);
        toolbar.setOnMenuItemClickListener(item -> item.getItemId() == SEARCH_ID);
        SearchManager searchManager = (SearchManager) dialog.getContext().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = toolbar.getMenu().findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(((Activity) context).getComponentName()));
            searchView.setMaxWidth(Integer.MAX_VALUE);
        }

        dialogProductAdapter = new DialogProductAdapter((product, aBoolean, id) -> {
            if (aBoolean) {
                listSelectedProduct.add(product);
            } else {
                listSelectedProduct.remove(product);
            }
        }, listSelectedProduct);

        dialogSelectBinding.rcv.setAdapter(dialogProductAdapter);
        getDataProduct(dialogSelectBinding);

        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (!newText.isEmpty()) {
                        if (runnable != null) handler.removeCallbacks(runnable);
                        runnable = () -> getDataSearchProduct(dialogSelectBinding, newText);
                        handler.postDelayed(runnable, 500);
                        return true;
                    } else {
                        listProduct.clear();
                        getDataProduct(dialogSelectBinding);
                    }
                    return false;
                }
            });
        }

        dialogSelectBinding.nestScroll.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                dialogSelectBinding.pbLoadMore.setVisibility(View.VISIBLE);
                if (pageIndexProduct < perPage) {
                    pageIndexProduct++;
                    getDataProduct(dialogSelectBinding);
                    dialogSelectBinding.pbLoadMore.setVisibility(View.GONE);
                } else {
                    dialogSelectBinding.pbLoadMore.setVisibility(View.GONE);
                }
            }
        });
        dialog.show();
        dialog.setOnDismissListener(dialogInterface -> getListProduct(binding, listSelectedProduct));
    }

    public void getListProduct(FragmentInvoiceAddBinding binding, List<Product> list) {
        binding.rcvListProduct.setVisibility(View.VISIBLE);
        chooseProductAdapter.setData(list);
    }

    private void getDataProduct(DialogSelectBinding dialogProductLayoutBinding) {
        ApiProduct.apiProduct.getProductBySupplier(token, pageIndexProduct, supplierId).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        perPage = response.body().getLast_page();
                        ProductResponse productResponse = response.body();
                        if (productResponse.getData() != null) {
                            List<Product> tempList = Arrays.asList(productResponse.getData());
                            onCheckList1(dialogProductLayoutBinding, tempList);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable throwable) {
                Log.d("zzzzzzzzzz", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataSearchProduct(DialogSelectBinding dialogProductLayoutBinding, String keyword) {
        ApiProduct.apiProduct.productSearch(token, keyword, supplierId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        onCheckListSearchProduct(dialogProductLayoutBinding, response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
            }
        });
    }

    public void onCheckListSearchProduct(DialogSelectBinding dialogProductLayoutBinding, List<Product> products) {
        if (products != null) {
            listProduct.clear();
            listProduct.addAll(products);
            dialogProductLayoutBinding.rcv.setVisibility(View.VISIBLE);
            dialogProductLayoutBinding.tvEmpty.setVisibility(View.GONE);
            dialogProductLayoutBinding.pbLoadMore.setVisibility(View.GONE);
            dialogProductLayoutBinding.pbLoadMore.setVisibility(View.GONE);
            dialogProductLayoutBinding.rcv.setAdapter(dialogProductAdapter);
            dialogProductAdapter.setData(listProduct);
        } else {
            listProduct.clear();
            getDataProduct(dialogProductLayoutBinding);
        }
    }

    private void clickDeleteData(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Xóa sản phẩm");
        builder.setMessage("Bạn có muốn xóa (" + product.getProduct_name() + ") không ?");
        builder.setPositiveButton("Có", (dialog, which) -> {
            listSelectedProduct.remove(product);
            chooseProductAdapter.setData(listSelectedProduct);
            Toast.makeText(getActivity(), "Xóa sản phẩm thành công!", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Không", null);
        builder.show();
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
    public void onResume() {
        super.onResume();
    }
}
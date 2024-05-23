package fpoly.md16.depotlife.Product.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiProduct;
import fpoly.md16.depotlife.Helper.Interfaces.onClickListener.onItemRcvClick;
import fpoly.md16.depotlife.Product.Adapter.ProductBatchAdapter;
import fpoly.md16.depotlife.Product.Model.BatchResponse;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.databinding.DialogEnterQuantityBinding;
import fpoly.md16.depotlife.databinding.FragmentProductDeleteBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDeleteFragment extends Fragment implements onItemRcvClick<Integer> {
    private FragmentProductDeleteBinding binding;
    private Bundle bundle;
    private String token;
    private List<BatchResponse> list;
    private Product product;
    private ProductBatchAdapter adapter;
    private int quantity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductDeleteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        token = "Bearer " + Helper.getSharedPre(getContext(), "token", String.class);
        binding.imgBack.setOnClickListener(view1 -> {
            requireActivity().finish();
            ProductFragment.isLoadData = true;
        });

        bundle = getArguments();
        list = new ArrayList<>();
        if (bundle != null) {
            product = (Product) bundle.getSerializable("product");
            if (product != null) {
                getData(product);
            }
        }
    }

    private void getData(Product product) {
        ApiProduct.apiProduct.getBatch(token, product.getId()).enqueue(new Callback<List<BatchResponse>>() {
            @Override
            public void onResponse(Call<List<BatchResponse>> call, Response<List<BatchResponse>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
//                    assert list != null;
                    if (list.size() > 0) {
                        binding.rcv.setVisibility(View.VISIBLE);
                        binding.tvEmpty.setVisibility(View.GONE);
                        adapter = new ProductBatchAdapter(getContext(), list, ProductDeleteFragment.this);
                        binding.rcv.setAdapter(adapter);
                    } else {
                        binding.rcv.setVisibility(View.GONE);
                        binding.tvEmpty.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BatchResponse>> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
            }
        });
    }

    @Override
    public void onClick(Integer item) {
        onShowDialog(product.getId(), list.get(item).getId());
    }

    private void onShowDialog(int product_id, int expoty_id) {
        DialogEnterQuantityBinding dialogBinding = DialogEnterQuantityBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogBinding.getRoot());
        AlertDialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        dialogBinding.btnDelete.setOnClickListener(view -> {
            String soLuong = dialogBinding.edtQuantity.getText().toString().trim();
            if (soLuong.isEmpty()) {
                Toast.makeText(getContext(), "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
            } else {
                Helper.isNumberValid(soLuong, dialogBinding.tvWar);
                if (dialogBinding.tvWar.getText().toString().isEmpty()) {
                    quantity = Integer.parseInt(soLuong);
                    ApiProduct.apiProduct.delete(token, product_id, expoty_id, quantity).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.isSuccessful()) {
                                JsonObject jsonResponse = response.body();
                                if (jsonResponse != null && jsonResponse.has("success")) {
                                    String successMessage = jsonResponse.get("success").getAsString();
                                    Toast.makeText(getContext(), ""+successMessage, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                try {
                                    System.out.println("Error: " + response.errorBody().string());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable throwable) {
                            Log.d("onFailure", "onFailure: " + throwable.getMessage());
                            Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        });

        dialogBinding.imgClose.setOnClickListener(view -> {
            dialog.dismiss();
        });

    }
}
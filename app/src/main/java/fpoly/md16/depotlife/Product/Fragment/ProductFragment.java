package fpoly.md16.depotlife.Product.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiProduct;
import fpoly.md16.depotlife.Helper.Pagination;
import fpoly.md16.depotlife.Product.Activity.ProductActivity;
import fpoly.md16.depotlife.Product.Adapter.ProductAdapter;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.Product.Model.ProductResponse;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.FragmentProductBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment {
    private FragmentProductBinding binding;
    private ProductAdapter adapter;
    private Context context;
    private ArrayList<Product> list;
    private List<Product> listSearch = new ArrayList<>();
    private ProductResponse productResponse;
    private int pageIndex = 1;
    private int perPage = 0;
    private String token;
    public static boolean isLoadData = false;
    private boolean isLoading;
    private boolean isLastPage;
    private Runnable runnable;
    private final Handler handler = new Handler();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbProduct);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.fab.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ProductActivity.class));
        });

        token = "Bearer " + Helper.getSharedPre(getContext(), "token", String.class);
        list = new ArrayList<>();
        adapter = new ProductAdapter(getContext(), token);
        binding.rcvProduct.setAdapter(adapter);

        callApi();
        binding.rcvProduct.addOnScrollListener(new Pagination((LinearLayoutManager) binding.rcvProduct.getLayoutManager()) {
            @Override
            public void loadMore() {
                isLoading = true;
                pageIndex += 1;
                loadNextPage();
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean isLaspage() {
                return isLastPage;
            }
        });
    }

    private void loadNextPage() {
        handler.postDelayed(() -> {
            adapter.removeFooterLoading();
            callApi();

            isLoading = false;
            if (pageIndex < perPage) {
                adapter.addFooterLoading();
            } else {
                isLastPage = true;
            }
        }, 500);


    }

    private void callApi() {
        ApiProduct.apiProduct.getData(token, pageIndex).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    productResponse = response.body();
                    if (productResponse != null) {
                        binding.tvTotalProduct.setText(productResponse.getTotal() + "");
                        perPage = productResponse.getLast_page();
                        onCheckList(productResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });

//        isLastPage = false;
//        if (pageIndex < perPage) {
//            adapter.addFooterLoading();
//        } else {
//            isLastPage = true;
//        }
//        return productList;
    }
    private void onCheckList(ProductResponse productResponse) {
        if (productResponse.getData() != null) {
            list.addAll(Arrays.asList(productResponse.getData()));
            if (!list.isEmpty()) {
                binding.layoutTotal.setVisibility(View.VISIBLE);
                binding.tvEmpty.setVisibility(View.GONE);
                binding.pbLoading.setVisibility(View.GONE);
                adapter.setData(list);
            } else {
                binding.layoutTotal.setVisibility(View.GONE);
                binding.pbLoading.setVisibility(View.GONE);
                binding.tvEmpty.setVisibility(View.VISIBLE);
            }
        }
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu, menu);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isLoadData) {
            pageIndex = 1;
            list.clear();
            adapter.notifyDataSetChanged();
            callApi();
            isLoadData = false;
        }
    }


// private void onSearch() {
//        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
//        MenuItem searchItem = binding.tbProduct.getMenu().findItem(R.id.item_search); // Đảm bảo rằng ID này khớp với ID trong file menu của bạn
//        SearchView searchView = (SearchView) searchItem.getActionView();
//        if (searchView != null) {
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(((Activity) getContext()).getComponentName()));
//            searchView.setMaxWidth(Integer.MAX_VALUE);
//        }

//        if (searchView != null) {
//            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String newText) {
////                    if (!newText.isEmpty()) {
////                        if (runnable != null) {
////                            handler.removeCallbacks(runnable);
////                        }
////
////                        runnable = () -> getDataSearch(newText);
////
////                        handler.postDelayed(runnable, 500);
////                        return true;
////                    } else {
////                        list.clear();
////                        getData();
////                    }
//                    return false;
//                }
//            });
//        }


    //   }

    //    private void getDataSearch(String keyword) {
//        ApiProduct.apiProduct.searchByName(token, keyword).enqueue(new Callback<List<Product>>() {
//            @Override
//            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
//                if (response.isSuccessful()) {
//                    if (response.body() != null) {
//                        listSearch = response.body();
//                        onCheckListSearch(listSearch);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Product>> call, Throwable throwable) {
//                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
//                Log.d("onFailure", "onFailure: " + throwable.getMessage());
//            }
//        });
//
//    }
//
//    private void onCheckListSearch(List<Product> listSearch) {
//        if (listSearch != null) {
//            list.clear();
//            list.addAll(listSearch);
//            binding.rcvProduct.setVisibility(View.VISIBLE);
//            binding.layoutTotal.setVisibility(View.VISIBLE);
//            binding.tvEmpty.setVisibility(View.GONE);
//            binding.pbLoading.setVisibility(View.GONE);
//            binding.pbLoadMore.setVisibility(View.GONE);
//            binding.rcvProduct.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//        } else {
//            list.clear();
//            getData();
//        }
//    }
//

}
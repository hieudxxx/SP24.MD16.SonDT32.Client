package fpoly.md16.depotlife.Product.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiProduct;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductViewModel extends ViewModel {
    private MutableLiveData<List<Product>> productsLiveData = new MutableLiveData<>();
    private ArrayList<Product> list = new ArrayList<>();
    private int pageIndex = 1;
    private boolean loadMore = true;

    public LiveData<List<Product>> getProductsLiveData() {
        return productsLiveData;
    }

    public void fetchProducts(String token) {
        if (!loadMore) return;
        ApiProduct.apiProduct.getData("Bearer " + token, pageIndex).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    list.addAll(Arrays.asList(response.body().getData()));
                    productsLiveData.postValue(list);
                    pageIndex++;
                    loadMore = pageIndex <= response.body().getLast_page();
                } else {
                    // Xử lý lỗi
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                // Xử lý lỗi
            }
        });
    }

    public boolean canLoadMore() {
        return loadMore;
    }
}

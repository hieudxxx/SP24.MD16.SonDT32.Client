package fpoly.md16.depotlife.Helper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class Pagination extends RecyclerView.OnScrollListener {
    private LinearLayoutManager linearLayoutManager;

    public Pagination(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItem = linearLayoutManager.getChildCount();
        int totalItem = linearLayoutManager.getItemCount();
        int firstItem = linearLayoutManager.findFirstVisibleItemPosition();
        if (isLoading() || isLaspage()) {
            return;
        }

        if (firstItem >= 0 && (visibleItem + firstItem) >= totalItem){
            loadMore();
        }

    }

    public abstract void loadMore();

    public abstract boolean isLoading();

    public abstract boolean isLaspage();
}

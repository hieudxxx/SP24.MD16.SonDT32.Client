package fpoly.md16.depotlife.Product.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fpoly.md16.depotlife.databinding.ItemImagesProductBinding;

public class ProductImagesAdapter extends RecyclerView.Adapter<ProductImagesAdapter.ViewHolder> {
    private Context context;
    private List<String> list;
    private int index;

    public ProductImagesAdapter(Context context, List<String> list, int index) {
        this.context = context;
        this.list = list;
        this.index = index;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImagesProductBinding binding = ItemImagesProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (index != -1) {
            if (index == position){
                holder.binding.imgProduct.setStrokeColor(ColorStateList.valueOf(Color.RED));
            } else {
                holder.binding.imgProduct.setStrokeColor(ColorStateList.valueOf(Color.TRANSPARENT));
            }
        }
        Picasso.get().load("https://warehouse.sinhvien.io.vn/public" + list.get(position)).into(holder.binding.imgProduct);

        holder.itemView.setOnClickListener(view -> {
            index = holder.getAdapterPosition();
//            holder.binding.imgProduct.setStrokeColor(ColorStateList.valueOf(Color.RED));
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemImagesProductBinding binding;

        public ViewHolder(@NonNull ItemImagesProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

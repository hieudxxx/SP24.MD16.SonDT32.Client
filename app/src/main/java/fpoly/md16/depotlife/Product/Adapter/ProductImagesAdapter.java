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

import fpoly.md16.depotlife.Helper.Interfaces.Api.API;
import fpoly.md16.depotlife.Helper.Interfaces.onClickListener.onItemRcvClick;
import fpoly.md16.depotlife.Product.Model.Image;
import fpoly.md16.depotlife.databinding.ItemImagesProductBinding;

public class ProductImagesAdapter extends RecyclerView.Adapter<ProductImagesAdapter.ViewHolder> {
    private Context context;
    private List<Image> list;
    private onItemRcvClick onItemRcvClick;

    public ProductImagesAdapter(Context context, List<Image> list, onItemRcvClick onItemRcvClick) {
        this.context = context;
        this.list = list;
        this.onItemRcvClick = onItemRcvClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImagesProductBinding binding = ItemImagesProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list.get(position).getIs_pined() == 1) holder.binding.imgProduct.setStrokeColor(ColorStateList.valueOf(Color.GRAY));
        else holder.binding.imgProduct.setStrokeColor(ColorStateList.valueOf(Color.TRANSPARENT));

        Picasso.get().load(API.URL_IMG + list.get(position).getPath().replace("public","")).into(holder.binding.imgProduct);

        holder.itemView.setOnClickListener(view -> {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setIs_pined(0);
            }
            list.get(position).setIs_pined(1);
            if (onItemRcvClick != null) {
                onItemRcvClick.onClick(holder.getAdapterPosition());
            }
            notifyDataSetChanged();
        });

        holder.binding.imgDelete.setOnClickListener(view -> {

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

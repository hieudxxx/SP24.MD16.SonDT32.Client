package fpoly.md16.depotlife.Invoice.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiInvoice;
import fpoly.md16.depotlife.Invoice.Adapter.InvoiceDetailAdapter;
import fpoly.md16.depotlife.Invoice.Model.Invoice;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.DialogCheckDeleteBinding;
import fpoly.md16.depotlife.databinding.FragmentInvoiceDetailBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceDetailFragment extends Fragment {
    private FragmentInvoiceDetailBinding binding;
    private Invoice invoice;
    private InvoiceDetailAdapter adapter;
    private ArrayList<Product> list;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInvoiceDetailBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbInvoice);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.imgBack.setOnClickListener(v -> {
            requireActivity().finish();
//
//            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//            fragmentManager.popBackStack();
        });



        bundle = getArguments();
        if (bundle != null) {
            invoice = (Invoice) bundle.getSerializable("invoice");
            if (invoice != null) {
                if (invoice.getStatus() == 0) {
                    binding.tvStatusInvoice.setText("Phiếu tạm");
                    binding.tvStatusInvoice.setTextColor(Color.YELLOW);
                    binding.imgEditDetail.setVisibility(View.VISIBLE);
                } else {
                    binding.tvStatusInvoice.setText("Hoàn thành");
                    binding.tvStatusInvoice.setTextColor(Color.BLACK);
                    binding.imgEditDetail.setVisibility(View.GONE);
                }

                binding.imgEditDetail.setOnClickListener(view12 -> {
                    bundle = new Bundle();
                    bundle.putSerializable("invoice", invoice);
                    Helper.loadFragment(getParentFragmentManager(), new InvoiceEditFragment(), bundle, R.id.frag_container_invoice);
                });

                binding.tvIdInvoiceDetail.setText(invoice.getId());
                ApiInvoice.apiInvoice.getOne(invoice.getId()).enqueue(new Callback<Invoice>() {
                    @Override
                    public void onResponse(Call<Invoice> call, Response<Invoice> response) {
                        if (response.isSuccessful()) {
                            invoice = response.body();

                            binding.tvTotal.setText(Helper.formatVND(invoice.getTotal()));

//                            binding.tvName.setText(comic.getName());
//                            binding.tvTime.setText("Năm xuất bản: " + comic.getDate());
//                            Glide.with(context).load(comic.getCover()).into(binding.imgCover);
//                            binding.tvAuthor.setText("Tác giả: " + comic.getAuthor());
//                            binding.tvChapter.setText("Chapter: " + comic.getChapter());
//                            binding.tvDes.setText(comic.getDes());
//                            _idComic = comic.get_id();
//                            list = new ArrayList<>(Arrays.asList(comic.getCmt()));
//
//                            adapter = new Cmt_Item_Adapter(context, list);
//                            binding.rcv.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<Invoice> call, Throwable t) {
                        Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
                list = new ArrayList<>();
//                ApiProduct.apiProduct.getProductList().enqueue(new Callback<ArrayList<Product>>() {
//                    @Override
//                    public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
////                Log.d("tag_kiemTra", "onResponse: " + response.code());
//
//                        if (response.isSuccessful()) {
//                            list = response.body();
////                    Log.d("tag_kiemTra", "onResponse: " + response);
//                        }
//
//                        adapter = new InvoiceDetailAdapter(getContext(), list);
//                        binding.rcv.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
//                        Log.d("tag_kiemTra", "onFailure: " + t.getMessage());
//                        Toast.makeText(getContext(), "thất bại", Toast.LENGTH_SHORT).show();
//                    }
//                });



            }

        }


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.invoice_detail_opt_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.print) {
            return true;
        } else if (id == R.id.share) {
            return true;
        } else if (id == R.id.delete) {
            DialogCheckDeleteBinding dialog_binding = DialogCheckDeleteBinding.inflate(LayoutInflater.from(getContext()));
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(dialog_binding.getRoot());
            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            dialog_binding.btnCancel.setOnClickListener(view1 -> {
                dialog.cancel();
            });
            dialog_binding.btnOk.setOnClickListener(view1 -> {
                dialog.cancel();
                Toast.makeText(getContext(), "OK", Toast.LENGTH_SHORT).show();
            });
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
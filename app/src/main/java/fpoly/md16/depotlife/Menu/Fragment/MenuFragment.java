package fpoly.md16.depotlife.Menu.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Invoice.Fragment.InvoiceFragment;
import fpoly.md16.depotlife.Login.LoginActivity;
import fpoly.md16.depotlife.MainActivity;
import fpoly.md16.depotlife.Menu.Account.Activity.AccountActivity;
import fpoly.md16.depotlife.Menu.Account.Fragment.AccountFragment;
import fpoly.md16.depotlife.Staff.Fragment.StaffFragment;
import fpoly.md16.depotlife.Menu.Activity.BaoLoi;
import fpoly.md16.depotlife.Menu.Activity.CaiDat;
import fpoly.md16.depotlife.Menu.Activity.DieuKhoan;
import fpoly.md16.depotlife.Menu.Activity.Goi_Hotline;
import fpoly.md16.depotlife.Menu.Activity.ThongTinGianHang;
import fpoly.md16.depotlife.Menu.Activity.TroGiupVaPhanHoi;
import fpoly.md16.depotlife.Menu.Activity.VeChungToi;
import fpoly.md16.depotlife.Category.CategoryActivity;
import fpoly.md16.depotlife.Supplier.Activity.SupplierActivity;
import fpoly.md16.depotlife.Product.Fragment.ProductFragment;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Statistic.StatisticFragment;
import fpoly.md16.depotlife.databinding.DialogCheckFeatureBinding;
import fpoly.md16.depotlife.databinding.FragmentMenuBinding;


public class MenuFragment extends Fragment {
    private FragmentMenuBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMenuBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbMenu);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.txtNameAccount.setText((String)Helper.getSharedPre(getContext(), "name", String.class));
        String avt = (String) Helper.getSharedPre(getContext(), "avatar", String.class);
        String ava = avt.replace("public","storage");
        if (avt.isEmpty()) {
            binding.imgAvt.setImageResource(R.drawable.unknow_avt);
        } else {
            Picasso.get().load("https://warehouse.sinhvien.io.vn/public/" +ava).into(binding.imgAvt);
        }

        binding.imgBtnEdit.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AccountActivity.class));
//            Helper.loadFragment(getActivity().getSupportFragmentManager(), new AccountFragment(), null, R.id.frag_container_main);
        });

        binding.btnInvoice.setOnClickListener(v -> {
            Helper.loadFragment(getActivity().getSupportFragmentManager(), new InvoiceFragment(), null, R.id.frag_container_main);
            MainActivity.binding.bottomNav.show(2,true);
        });

        binding.btnCensor.setOnClickListener(v -> {
            DialogCheckFeatureBinding feature_binding = DialogCheckFeatureBinding.inflate(LayoutInflater.from(getActivity()));
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(feature_binding.getRoot());
            AlertDialog dialog = builder.create();
            dialog.show();
            feature_binding.btnRetry.setOnClickListener(view1 -> {
                dialog.cancel();

            });

        });

        binding.btnSupplier.setOnClickListener(v -> startActivity(new Intent(getContext(), SupplierActivity.class)));

        binding.btnStaff.setOnClickListener(v -> Helper.loadFragment(getActivity().getSupportFragmentManager(), new StaffFragment(), null, R.id.frag_container_main));

        binding.btnCategories.setOnClickListener(v -> startActivity(new Intent(getContext(), CategoryActivity.class)));

        binding.btnProduct.setOnClickListener(v -> {
            Helper.loadFragment(getActivity().getSupportFragmentManager(), new ProductFragment(), null, R.id.frag_container_main);
            MainActivity.binding.bottomNav.show(4,true);
        });

        binding.btnStatistic.setOnClickListener(v -> {
            Helper.loadFragment(getActivity().getSupportFragmentManager(), new StatisticFragment(), null, R.id.frag_container_main);
            MainActivity.binding.bottomNav.show(1, true);
        });
        binding.btnTreasuryBook.setOnClickListener(v -> Toast.makeText(getContext(), "Đang phát triển", Toast.LENGTH_SHORT).show());

        binding.btnInfo.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ThongTinGianHang.class);
            startActivity(intent);
        });

        binding.btnPolicy.setOnClickListener(v -> {

        });

        binding.btnAbout.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), VeChungToi.class));
        });

        binding.btnReport.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), BaoLoi.class));
        });

        binding.btnFeedback.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), TroGiupVaPhanHoi.class));
        });

        binding.btnSetting.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), CaiDat.class));
        });

        binding.btnCall.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), Goi_Hotline.class));
        });

        binding.btnClause.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), DieuKhoan.class));
        });

        binding.btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });
    }

}
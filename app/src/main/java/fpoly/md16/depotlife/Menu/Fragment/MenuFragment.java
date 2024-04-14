package fpoly.md16.depotlife.Menu.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Invoice.Fragment.InvoiceImportFragment;
import fpoly.md16.depotlife.Login.LoginActivity;
import fpoly.md16.depotlife.Staff.StaffFragment;
import fpoly.md16.depotlife.Menu.Account.Activity.AccountActivity;
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

        binding.cvAccount.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AccountActivity.class));
        });

        binding.btnImport.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Đang phát triển", Toast.LENGTH_SHORT).show();
        });


        binding.btnExport.setOnClickListener(v -> {
            Helper.loadFragment(getParentFragmentManager(), new InvoiceImportFragment(), null, R.id.frag_container_main);
            Toast.makeText(getContext(), "Đang phát triển", Toast.LENGTH_SHORT).show();
        });

        binding.btnSupplier.setOnClickListener(v -> startActivity(new Intent(getContext(), SupplierActivity.class)));

        binding.btnStaff.setOnClickListener(v -> Helper.loadFragment(getActivity().getSupportFragmentManager(), new StaffFragment(), null, R.id.frag_container_main));

        binding.btnCategory.setOnClickListener(v -> startActivity(new Intent(getContext(), CategoryActivity.class)));

        binding.btnProduct.setOnClickListener(v -> {
            Helper.loadFragment(getActivity().getSupportFragmentManager(), new ProductFragment(), null, R.id.frag_container_main);
        });

        binding.btnStatistic.setOnClickListener(v -> Helper.loadFragment(getActivity().getSupportFragmentManager(), new StatisticFragment(), null, R.id.frag_container_main));
        binding.btnLedger.setOnClickListener(v -> Toast.makeText(getContext(), "Đang phát triển", Toast.LENGTH_SHORT).show());

        binding.in4Shop.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ThongTinGianHang.class);
            startActivity(intent);
        });

        binding.policy.setOnClickListener(v -> {

        });

        binding.aboutUs.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), VeChungToi.class));
        });

        binding.report.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), BaoLoi.class));
        });

        binding.help.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), TroGiupVaPhanHoi.class));
        });

        binding.settings.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), CaiDat.class));
        });

        binding.hotline.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), Goi_Hotline.class));
        });

        binding.rules.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), DieuKhoan.class));
        });

        binding.logout.setOnClickListener(v -> {
            requireActivity().finish();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
    }

}
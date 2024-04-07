package fpoly.md16.depotlife.Helper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import fpoly.md16.depotlife.Invoice.Activity.InvoiceActivity;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.BotSheetSortBinding;

public class Helper {

    public static void loadFragment(FragmentManager fragmentManager, Fragment fragment, Bundle bundle, int fragmentContainerView) {
        if (bundle != null) fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(fragmentContainerView, fragment)
                .addToBackStack(null) // Cho phép quay lại fragment trước đó nếu cần
                .commit();
    }

    public static void loadFragInvoice(Context context, FragmentManager fragmentManager, Fragment fragment, Bundle bundle, int fragmentContainerView) {
        context.startActivity(new Intent(context, InvoiceActivity.class));
        loadFragment(fragmentManager, fragment, bundle, fragmentContainerView);
    }

    public static void isContainSpace(String value, EditText edt, TextView tv) {
        if (value.contains(" ")) { //
            tv.setVisibility(View.VISIBLE);
            tv.setText("Không được chứa khoảng trắng");
        } else {
            tv.setVisibility(View.GONE);
            tv.setText("");
        }
    }

    public static void onShowPass(ImageView img, EditText edt) {
        if (edt.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            edt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            img.setImageResource(R.drawable.appear);
        } else {
            edt.setTransformationMethod(PasswordTransformationMethod.getInstance());
            img.setImageResource(R.drawable.hidden);
        }

    }

    public static void onShowCaledar(TextView tv, Context context) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = simpleDateFormat.format(calendar.getTime());
        tv.setText("Hôm nay, " + currentDate);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Tạo một Calendar từ ngày được chọn
                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(year, month, dayOfMonth);
                // So sánh ngày được chọn với ngày hiện tại
                if (selectedCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                        selectedCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                        selectedCalendar.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)) {
                    // Nếu ngày được chọn là ngày hiện tại, hiển thị "Hôm nay" cùng với ngày
                    tv.setText("Hôm nay, " + currentDate);
                } else {
                    // Nếu không, chỉ hiển thị ngày được chọn
                    String selectedDate = simpleDateFormat.format(selectedCalendar.getTime());
                    tv.setText(selectedDate);
                }
            }
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    public static Dialog onSettingsBotSheet(Context context, ViewBinding viewBinding) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(viewBinding.getRoot());

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.custom_anim_bot_sheet;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();
        return dialog;
    }

    public static String formatVND(Double sum) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat nf = NumberFormat.getInstance(locale);
        String money = nf.format(sum) + " đ";
        return money;
    }

    public static void onSearch(MenuItem menuItem, Filterable filterableAdapter) {
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Tìm kiếm");
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterableAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterableAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
    public static <T> void onSort(Context context, ArrayList<T> list, RecyclerView.Adapter<?> adapter, Comparator<T> sortByAsc,  Comparator<T> sortByAZ) {
        BotSheetSortBinding sortBinding = BotSheetSortBinding.inflate(LayoutInflater.from(context));
        sortBinding.rdGr.setOnCheckedChangeListener(((radioGroup, i) -> {

            if (i == R.id.rd_sort_asc && sortByAsc != null) {
                Collections.sort(list, sortByAsc);
            } else if (i == R.id.rd_sort_decs && sortByAsc != null) {
                Collections.sort(list, Collections.reverseOrder(sortByAsc));
            } else if (i == R.id.rd_sort_AZ && sortByAZ != null) {
                Collections.sort(list, sortByAZ);
            } else if (i == R.id.rd_sort_ZA && sortByAZ != null) {
                Collections.sort(list, Collections.reverseOrder(sortByAZ));
            }
            adapter.notifyDataSetChanged();
        }));
        Helper.onSettingsBotSheet(context, sortBinding);
    }



}

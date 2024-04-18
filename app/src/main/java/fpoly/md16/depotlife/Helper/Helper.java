package fpoly.md16.depotlife.Helper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;

import fpoly.md16.depotlife.Helper.Interfaces.onClickListener.CheckdeleteListener;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.BotSheetDetailStaffBinding;
import fpoly.md16.depotlife.databinding.BotSheetOptionStaffBinding;
import fpoly.md16.depotlife.databinding.BotSheetSortBinding;
import fpoly.md16.depotlife.databinding.BotSheetSortStaffBinding;
import fpoly.md16.depotlife.databinding.DialogCheckDeleteBinding;

public class Helper {
    public static final String SHARE_NAME = "ACC";

    public static void saveSharedPre(Context context, Map<String, Object> data) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value == null) {
                Log.d("Warning", "Giá trị " + key + " null");
                value = "";
            }

            if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (int) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (boolean) value);
            } else {
                throw new IllegalArgumentException("Unsupported data type: " + value.getClass());
            }
        }

        editor.apply();
    }

    public static Object getSharedPre(Context context, String key, Class<?> type) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);

        if (type == String.class) {
            return sharedPreferences.getString(key, "");
        } else if (type == Integer.class) {
            return sharedPreferences.getInt(key, 0);
        } else if (type == Boolean.class) {
            return sharedPreferences.getBoolean(key, false);
        } else {
            throw new IllegalArgumentException("Unsupported data type: " + type);
        }
    }

    public static void loadFragment(FragmentManager fragmentManager, Fragment fragment, Bundle bundle, int fragmentContainerView) {
        if (bundle != null) fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(fragmentContainerView, fragment)
                .addToBackStack(null) // Cho phép quay lại fragment trước đó nếu cần
                .commit();
    }

    public static void isContainSpace(String value, TextView tv) {
        if (value.trim().isEmpty()) { //
            tv.setVisibility(View.VISIBLE);
            tv.setText("Không được chứa khoảng trắng");
//            return true;
        } else if (value.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?~-].*")) {
            tv.setVisibility(View.VISIBLE);
            tv.setText("Không được chứa ký tự đặc biệt");
//            return false;
        } else {
            tv.setVisibility(View.GONE);
            tv.setText("");
        }
    }

    public static void isPhoneValid(String value, TextView tv) {
        if (value.length() < 10 || value.length() > 10) {
            tv.setVisibility(View.VISIBLE);
            tv.setText("Số điện thoại phải chứa 10 số");
        } else if (!value.matches("^0\\d{9}$")) {
            tv.setVisibility(View.VISIBLE);
            tv.setText("Số điện thoại không hợp lệ");
        } else if (!TextUtils.isDigitsOnly(value)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText("Không được nhập chữ");
        } else {
            tv.setVisibility(View.GONE);
            tv.setText("");
        }
    }

    public static void isEmailValid(String value, TextView tv) {
        if (value.trim().contains(" ") || !value.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            tv.setVisibility(View.VISIBLE);
            tv.setText("Hãy nhập đúng định dạng email");
        } else if (value.matches(".*[!#$%^&*()_+=|<>?{}\\[\\]~-].*")) {
            tv.setVisibility(View.VISIBLE);
            tv.setText("Không được chứa ký tự đặc biệt");
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

    public static Dialog onCheckdeleteDialog(Context context, CheckdeleteListener deleteListener) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DialogCheckDeleteBinding dialogBinding = DialogCheckDeleteBinding.inflate(LayoutInflater.from(context));
        dialogBinding.getRoot().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(dialogBinding.getRoot());

        dialogBinding.btnCancel.setOnClickListener(view -> {
            dialog.dismiss();

        });

        dialogBinding.btnOk.setOnClickListener(view -> {
            if (deleteListener != null) {
                deleteListener.isDeleteClicked();
            }
            dialog.dismiss();
        });

        dialog.show();
        return dialog;
    }

    public static String formatVND(Double sum) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

        Locale locale = new Locale("vi", "VN");
        NumberFormat nf = NumberFormat.getInstance(locale);
        String money = decimalFormat.format(sum) + " đ";
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

    public static <T> void onSort(Context context, ArrayList<T> list, RecyclerView.Adapter<?> adapter, Comparator<T> sortByAsc, Comparator<T> sortByAZ) {
        BotSheetSortBinding sortBinding = BotSheetSortBinding.inflate(LayoutInflater.from(context));
        sortBinding.rdGr.setOnCheckedChangeListener(((radioGroup, i) -> {

            if (i == R.id.rd_sort_asc && sortByAsc != null) {
                Collections.sort(list, sortByAsc);
            } else if (i == R.id.rd_sort_desc && sortByAsc != null) {
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
    public static <T> void onSortStaff(Context context) {
        BotSheetSortStaffBinding sortStaffBinding = BotSheetSortStaffBinding.inflate(LayoutInflater.from(context));
        sortStaffBinding.rdGr.setOnCheckedChangeListener(((radioGroup, i) -> {

//            if (i == R.id.rd_sort_asc && sortByAsc != null) {
//                Collections.sort(list, sortByAsc);
//            } else if (i == R.id.rd_sort_desc && sortByAsc != null) {
//                Collections.sort(list, Collections.reverseOrder(sortByAsc));
//            } else if (i == R.id.rd_sort_AZ && sortByAZ != null) {
//                Collections.sort(list, sortByAZ);
//            } else if (i == R.id.rd_sort_ZA && sortByAZ != null) {
//                Collections.sort(list, Collections.reverseOrder(sortByAZ));
//            }
//            adapter.notifyDataSetChanged();
        }));
        Helper.onSettingsBotSheet(context, sortStaffBinding);
    }


    public static <T> void onOptionStaff(Context context) {
        BotSheetOptionStaffBinding optionStaffBinding = BotSheetOptionStaffBinding.inflate(LayoutInflater.from(context));
        BotSheetDetailStaffBinding detailStaffBinding = BotSheetDetailStaffBinding.inflate(LayoutInflater.from(context));
//        optionStaffBinding.rdGr.setOnCheckedChangeListener(((radioGroup, i) -> {
//
////            if (i == R.id.rd_sort_asc && sortByAsc != null) {
////                Collections.sort(list, sortByAsc);
////            } else if (i == R.id.rd_sort_desc && sortByAsc != null) {
////                Collections.sort(list, Collections.reverseOrder(sortByAsc));
////            } else if (i == R.id.rd_sort_AZ && sortByAZ != null) {
////                Collections.sort(list, sortByAZ);
////            } else if (i == R.id.rd_sort_ZA && sortByAZ != null) {
////                Collections.sort(list, Collections.reverseOrder(sortByAZ));
////            }
////            adapter.notifyDataSetChanged();
//        }));


        optionStaffBinding.layoutCall.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Ok Call",Toast.LENGTH_SHORT).show();
        });
        optionStaffBinding.layoutEmail.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Ok Email",Toast.LENGTH_SHORT).show();
        });
        optionStaffBinding.layoutSendSms.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Ok SendSms",Toast.LENGTH_SHORT).show();
        });
        optionStaffBinding.layoutDetail.setOnClickListener(v -> {
            Helper.onSettingsBotSheet(context,detailStaffBinding);
            //Toast.makeText(v.getContext(), "Ok Detail",Toast.LENGTH_SHORT).show();
        });
        Helper.onSettingsBotSheet(context,optionStaffBinding );
    }






}

package fpoly.md16.depotlife.Helper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import fpoly.md16.depotlife.Helper.Interfaces.Api.API;
import fpoly.md16.depotlife.Helper.Interfaces.onClickListener.CheckdeleteListener;
import fpoly.md16.depotlife.Product.Model.Image;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Staff.Activity.StaffDetailActivity;
import fpoly.md16.depotlife.Staff.Model.StaffResponse;
import fpoly.md16.depotlife.databinding.BotSheetOptionStaffBinding;
import fpoly.md16.depotlife.databinding.BotSheetSortBinding;
import fpoly.md16.depotlife.databinding.BotSheetSortStaffBinding;
import fpoly.md16.depotlife.databinding.DialogCheckDeleteBinding;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Helper {
    public static final String SHARE_NAME = "ACC";

    public static void saveSharedPre(Context context, Map<String, Object> data) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value == null) {
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

    public static boolean isContainSpace(String value, TextView tv) {
        if (value.trim().isEmpty()) { //
            tv.setVisibility(View.VISIBLE);
            tv.setText("Không được chứa khoảng trắng");
            return false;
        } else if (value.matches(".*[&!@#$%^*_+\\-=\\[\\]{};':\"\\\\|,.<>/?~-].*")) {
            tv.setVisibility(View.VISIBLE);
            tv.setText("Không được chứa ký tự đặc biệt");
            return false;
        } else {
            tv.setVisibility(View.GONE);
            tv.setText("");
            return true;
        }
    }

//    public static boolean isSpecialChar(String value, TextView tv) {
//        if (value.matches(".*[!@#$%^&*_+\\-=\\[\\]{};':\"\\\\|,.<>/?~-].*")) {
//            tv.setVisibility(View.VISIBLE);
//            tv.setText("Không được chứa ký tự đặc biệt");
//            return false;
//        } else {
//            tv.setVisibility(View.GONE);
//            tv.setText(null);
//            return true;
//        }
//    }

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

    public static boolean isNumberValid(String value, TextView tv) {
        if (value.matches("[1-9][0-9]*")) {
            tv.setVisibility(View.GONE);
            tv.setText(null);
            return true;
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText("Giá trị không hợp lệ");
            return false;
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


    public static void onShowCaledar(TextView tv, Context context, String format) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = simpleDateFormat.format(calendar.getTime());
        tv.setText(currentDate);

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
                    tv.setText(currentDate);
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

    public static RequestBody createStringPart(String value) {
        RequestBody requestBody;
        if (value != null) {
            requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), value);
            return requestBody;
        } else {
            requestBody = null;
            return requestBody;
        }
    }

//    public static RequestBody[] createListPart(List<> value) {
//        return RequestBody.create(MediaType.parse("multipart/form-data"), value);
//    }

    public static RequestBody createIntPart(int value) {
        return createStringPart(String.valueOf(value));
    }

    public static String formatVND(int sum) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

        Locale locale = new Locale("vi", "VN");
        NumberFormat nf = NumberFormat.getInstance(locale);
        String money = decimalFormat.format(sum) + " đ";
        return money;
    }

    public static String formatVNDLong(long sum) {
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

    public static void isTaxValid(String value, TextView tv) {
        if (value.length() < 10 || value.length() > 13) {
            tv.setVisibility(View.VISIBLE);
            tv.setText("Mã số thuế phải từ 10-13 số");
        } else if (!TextUtils.isDigitsOnly(value)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText("Không được nhập chữ");
        } else {
            tv.setVisibility(View.GONE);
            tv.setText("");
        }
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


    public static <T> void onOptionStaff(Context context, StaffResponse.User user) {
        BotSheetOptionStaffBinding optionStaffBinding = BotSheetOptionStaffBinding.inflate(LayoutInflater.from(context));

        optionStaffBinding.layoutCall.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Ok Call", Toast.LENGTH_SHORT).show();
        });
        optionStaffBinding.layoutEmail.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Ok Email", Toast.LENGTH_SHORT).show();
        });
        optionStaffBinding.layoutSendSms.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Ok SendSms", Toast.LENGTH_SHORT).show();
        });
        optionStaffBinding.layoutDetail.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("staff", user);
            context.startActivity(new Intent(context, StaffDetailActivity.class).putExtras(bundle));


        });
        Helper.onSettingsBotSheet(context, optionStaffBinding);
    }

    public static void setImgProduct(Image[] url, ShapeableImageView img) {
        if (url == null) {
            img.setImageResource(R.drawable.img_add);
        } else {
            if (url.length == 0) {
                img.setImageResource(R.drawable.img_add);
            } else {
                for (int i = 0; i < url.length; i++) {
                    if (url[i].getIs_pined() == 1) {
                        Picasso.get().load(API.URL_IMG + url[i].getPath().replaceFirst("public", "")).into(img);
                    } else {
                        Picasso.get().load(API.URL_IMG + url[0].getPath().replaceFirst("public", "")).into(img);
                    }
                }
            }

        }
    }

    public static void setImgCustomer(String url, ShapeableImageView img) {
        if (url == null) {
            img.setImageResource(R.drawable.img_add);
        } else {
            Picasso.get().load(API.URL_IMG + url.replaceFirst("public", "")).into(img);
        }
    }

    private Uri saveImageToStorage(Bitmap bitmap, Context context) {
        String fileName = "camera_image_" + System.currentTimeMillis() + ".jpg";
        File storageDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "YourFolderName");

        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }

        File imageFile = new File(storageDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }

        if (success) {
            return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", imageFile);
        } else {
            return null;
        }
    }

    public static void openAlbum(ActivityResultLauncher<Intent> resultLauncher) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        resultLauncher.launch(intent);
    }

    public static void openGallery(Activity activity, ActivityResultLauncher<Intent> resultLauncher) {
        ImagePicker.with(activity)
                .crop()                    // Crop image (Optional), Check Customization for more option
                .compress(1024)            // Final image size will be less than 1 MB (Optional)
                .maxResultSize(1080, 1080) // Final image resolution will be less than 1080 x 1080 (Optional)
                .createIntent(intent -> {
                    resultLauncher.launch(intent);
                    return null;
                });
    }

    public static MultipartBody.Part getRealPathFile(Context context, Uri uri, String name) {
        MultipartBody.Part multipartBody;
        String realPath = RealPathUtil.getRealPath(context, uri);
        File file = new File(realPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        multipartBody = MultipartBody.Part.createFormData(name, file.getName(), requestBody);
        return multipartBody;
    }

    public static void onSetIntSpn(Context context, List<Integer> listInt, Spinner spinner, int index) {
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, listInt);
        spinner.setAdapter(adapter);
        int position = listInt.indexOf(index);
        if (position != -1) {
            // Nếu tìm thấy, thiết lập vị trí được chọn của Spinner
            spinner.setSelection(position);
        }
    }

    public static void onSetStringSpn(Context context, List<String> listString, Spinner spinner, String index) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, listString);
        spinner.setAdapter(adapter);

        int position = -1; // Khởi tạo vị trí không tìm thấy
        for (int i = 0; i < listString.size(); i++) {
            if (listString.get(i).equalsIgnoreCase(index)) {
                position = i;
                break; // Thoát vòng lặp khi tìm thấy
            }
        }

        if (position != -1) {
            // Nếu tìm thấy, thiết lập vị trí được chọn của Spinner
            spinner.setSelection(position);
        }
    }


}

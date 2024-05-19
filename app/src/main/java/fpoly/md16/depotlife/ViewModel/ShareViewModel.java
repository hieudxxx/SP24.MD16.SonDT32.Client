package fpoly.md16.depotlife.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareViewModel<T> extends ViewModel {
    private MutableLiveData<T> selected = new MutableLiveData<>();
    public void select(T item){
        selected.setValue(item);
    }
    public LiveData<T> get(){
        return selected;
    }
}

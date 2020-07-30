package com.example.proyectoandroid;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FragmentViewModel extends ViewModel {
    private final MutableLiveData<DatoFragment>datoFragment = new MutableLiveData<DatoFragment>();

    public FragmentViewModel(){
        datoFragment.postValue(new DatoFragment(""));
    }

    public LiveData<DatoFragment>obtenerDato(){
        return datoFragment;
    }

    public void actulizarDato(DatoFragment dato){
        datoFragment.setValue(dato);
    }
}

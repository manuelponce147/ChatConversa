package com.example.proyectoandroid;

import java.util.Objects;

public class DatoFragment {
    private String textoFragment1;
    private String textoFragment3;

    DatoFragment(){

    }

    public DatoFragment(String textoFragment1, String textoFragment3) {
        this.textoFragment1 = textoFragment1;
        this.textoFragment3 = textoFragment3;
    }

    public String getTextoFragment1() {
        return textoFragment1;
    }

    public void setTextoFragment1(String textoFragment1) {
        this.textoFragment1 = textoFragment1;
    }

    public String getTextoFragment3() {
        return textoFragment3;
    }

    public void setTextoFragment3(String textoFragment3) {
        this.textoFragment3 = textoFragment3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatoFragment that = (DatoFragment) o;
        return Objects.equals(textoFragment1, that.textoFragment1) &&
                Objects.equals(textoFragment3, that.textoFragment3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textoFragment1, textoFragment3);
    }

    @Override
    public String toString() {
        return "DatoFragment{" +
                "textoFragment1='" + textoFragment1 + '\'' +
                ", textoFragment3='" + textoFragment3 + '\'' +
                '}';
    }
}

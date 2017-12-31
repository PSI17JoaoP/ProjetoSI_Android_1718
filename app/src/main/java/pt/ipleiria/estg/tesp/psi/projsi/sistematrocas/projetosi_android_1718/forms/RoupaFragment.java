package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.R;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.Roupa;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.TipoRoupa;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons.SingletonTiposRoupa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by JAPorelo on 16-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.forms
 */


public class RoupaFragment extends Fragment {

    private TextInputEditText textInputEditTextMarcaRoupa;
    private TextInputEditText textInputEditTextTamanhoRoupa;
    private Spinner spinnerTipoRoupa;

    public RoupaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_roupa, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        textInputEditTextMarcaRoupa = view.findViewById(R.id.textInputEditTextMarcaRoupa);
        textInputEditTextTamanhoRoupa = view.findViewById(R.id.textInputEditTextTamanhoRoupa);

        ArrayList<TipoRoupa> tiposRoupa = SingletonTiposRoupa.getInstance(getContext()).getTiposRoupa();

        //Inserção manual para efeito de desenvolvimento.
        /*tiposRoupa.add(new TipoRoupa(1L, "Camisola"));
        tiposRoupa.add(new TipoRoupa(2L, "Calças"));
        tiposRoupa.add(new TipoRoupa(3L, "Casaco"));
        tiposRoupa.add(new TipoRoupa(4L, "Camisa"));
        tiposRoupa.add(new TipoRoupa(5L, "Boné"));
        tiposRoupa.add(new TipoRoupa(5L, "T-Shirt"));*/

        ArrayAdapter<TipoRoupa> spinnerTamanhos = new ArrayAdapter<>(getContext(),
                R.layout.custom_spinner_item,
                tiposRoupa);

        spinnerTipoRoupa = view.findViewById(R.id.spinnerTipoRoupa);

        spinnerTamanhos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoRoupa.setAdapter(spinnerTamanhos);
    }

    @Override
    public void onDestroyView() {
        textInputEditTextMarcaRoupa = null;
        textInputEditTextTamanhoRoupa = null;
        spinnerTipoRoupa = null;
        super.onDestroyView();
    }

    public Roupa getCategoria(String nome) {

        String marca = textInputEditTextMarcaRoupa.getText().toString().trim();
        String tamanho = textInputEditTextTamanhoRoupa.getText().toString().trim();
        TipoRoupa tipo = (TipoRoupa) spinnerTipoRoupa.getSelectedItem();

        if(!nome.isEmpty() && !marca.isEmpty() && !tamanho.isEmpty() && tipo != null) {
            return new Roupa(nome, marca, tamanho, tipo.getId());
        }

        return null;
    }
}

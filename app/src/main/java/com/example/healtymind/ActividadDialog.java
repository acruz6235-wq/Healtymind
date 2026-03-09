package com.example.healtymind;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ActividadDialog extends Dialog {

    TextView Cuestioanrios;
    TextView Desafios;
    TextView Racha;
    TextView Repiracion;

    public ActividadDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_actividad);

        Cuestioanrios = findViewById(R.id.Cuestioanrios);
        Desafios = findViewById(R.id.Desafios);
        Racha = findViewById(R.id.Racha);
        Repiracion = findViewById(R.id.Repiracion);

        Desafios.setOnClickListener(v -> {
            getContext().startActivity(new Intent(getContext(), Desafio_Activity.class));
        });

        Racha.setOnClickListener(v -> {
            getContext().startActivity(new Intent(getContext(), Racha_Activity.class));
        });

        Repiracion.setOnClickListener(v -> {
            getContext().startActivity(new Intent(getContext(), Respiracion_Activity.class));
        });

        if (getWindow() != null) {
            getWindow().setBackgroundDrawableResource(android.R.color.black);
            getWindow().getAttributes().windowAnimations = R.style.DialogAnimacion;

            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.BOTTOM | Gravity.END;
            getWindow().setAttributes(params);
        }
    }
}
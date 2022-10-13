package com.example.mobilelabs;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class EditProductFragment extends DialogFragment {
    public String resultProductName;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        String productName = bundle.getString("productName");

        getDialog().setTitle("Edit product!");
        View v = inflater.inflate(R.layout.edit_product_fragment, null);
        Button button = v.findViewById(R.id.buttonSuccess);
        TextView textViewName =  v.findViewById(R.id.editTextProductNameFragment);
        textViewName.setText(productName);

        button.setOnClickListener(u -> {
            resultProductName = textViewName.getText().toString();
            dismiss();
        });
        return v;
    }
    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}

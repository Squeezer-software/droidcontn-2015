package tn.droidcon.testprovider.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import tn.droidcon.testprovider.R;
import tn.droidcon.testprovider.wrapper.ListItemWrapper;


public class AddDialog extends DialogFragment {


    private static OnAddListener mListener;

    private EditText mLabel;
    private EditText mDescription;


    public static AddDialog newInstance(OnAddListener listener) {

        AddDialog dialog = new AddDialog();
        mListener = listener;
        return dialog;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_dialog_layout, null);

        initViews(view);

        Dialog dialog = builder
                .setTitle(R.string.add_dialog_title).setView(view)
                .setPositiveButton(
                        getResources().getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                mListener.onOkClicked(createListItem());
                            }
                        })
                .setNegativeButton(
                        getResources().getString(android.R.string.cancel),
                        null).create();

        return dialog;
    }

    private void initViews(View view) {
        mLabel = (EditText) view.findViewById(R.id.label);
        mDescription = (EditText) view.findViewById(R.id.description);
    }

    private ListItemWrapper createListItem() {


        ListItemWrapper listItemWrapper = new ListItemWrapper();
        listItemWrapper.setTitle(mLabel.getText().toString());
        listItemWrapper.setDescription(mDescription.getText().toString());

        return listItemWrapper;
    }

    public interface OnAddListener {

        public void onOkClicked(ListItemWrapper listItemWrapper);


    }

}

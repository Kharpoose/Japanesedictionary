package com.example.deneme;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.deneme.databinding.FragmentFirstBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class FirstFragment extends Fragment {
    private FragmentFirstBinding binding;
    EditText editTextSearch;
    Button btnSearch;
    TextView txtResults;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        editTextSearch = (EditText) v.findViewById(R.id.editTxtSearch);
        btnSearch = (Button) v.findViewById(R.id.button);
        txtResults = (TextView) v.findViewById(R.id.txtViewResults);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editTextSearch.getText().toString())) {
                    // Toast.makeText(MainActivity.this, "Arama yeri boş bırakılamaz", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Anlam1");
                    mRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String searchKeyword = editTextSearch.getText().toString();
                            if (snapshot.child(searchKeyword).exists()) {
                                txtResults.setText(Objects.requireNonNull(snapshot.child(searchKeyword).getValue()).toString());
                            }//else
                            {
                                //   Toast.makeText(MainActivity.this, "Arama sonucu bulunamadı", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        return v;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }

}
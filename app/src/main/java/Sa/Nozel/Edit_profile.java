package Sa.Nozel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Edit_profile extends AppCompatActivity {
    private static final String TAG = "Edit_profile";


    EditText fullname,mobile,age;
    Button save;
    ImageView backArrow;

    FirebaseAuth firebaseAuth;
    DatabaseReference dbRef;
    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_lt);
        setupUIViews();

       String  user = FirebaseAuth.getInstance().getCurrentUser().getUid();
         dbRef = FirebaseDatabase.getInstance().getReference(user);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                String getMobile = dataSnapshot.child("Mobile").getValue().toString();
                Log.d(TAG, "Value is: " + getMobile);
                mobile.setText(getMobile);
                String getFullname = dataSnapshot.child("Fullname").getValue().toString();
                Log.d(TAG, "Value is: " + getFullname);
                fullname.setText(getFullname);
                String getAge = dataSnapshot.child("Age").getValue().toString();
                Log.d(TAG, "Value is: " + getAge);
                age.setText(getAge);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



       /* if (dbRef.getMobile() != null) {
            mobile.setText(dbRef.getMobile());
        }else{
            Toast.makeText(this,"Mobile is null",Toast.LENGTH_SHORT).show();
        }
        if (user.getEmail() != null) {
            fullname.setText(user.getEmail());
        }else {
            Toast.makeText(this,"FullNAme is null",Toast.LENGTH_SHORT).show();
        } */

       backArrow.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(Edit_profile.this,search_result.class));
           }
       });

    }

    private void setupUIViews(){
        fullname =findViewById(R.id.edtFulltNameEdtProfile);
        mobile =findViewById(R.id.edtMobileEdtProfile);
        age =findViewById(R.id.edtAgeedtprofile);
        save = findViewById(R.id.btnSave);
        backArrow = findViewById(R.id.iv_backarrow);

    }
}

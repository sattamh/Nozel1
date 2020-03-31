package Sa.Nozel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
    EditText fullname, mobile,age, email, password;
    String userFullname,userMobile,userAge,userEmail,userPassword;
    Button register;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRef;

    ImageView backarrow;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_lt);
        setupUIViews();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (validate()) {
                    // Upload data to database
                    String usermail = email.getText().toString().trim();
                    String userPassword = password.getText().toString().trim(); // Add onCompleteListener to Know if the Upload sucessfull or not
                    progressDialog.setMessage("In Progress ");
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(usermail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                // when user want to creat a user direct him to sendEmailVerification method
                                progressDialog.dismiss();

                                sendEmailVerification();
                                //firebaseAuth.signOut();

                                //Toast.makeText(register.this,"Successfuly Registered", Toast.LENGTH_LONG).show();
                                // To pervent user from sign in tha app because the user alreay created up

                                // finish this Activity and direct user to login page
                                //finish();
                                //startActivity(new Intent(register.this,login.class));
                            } else {
                                Toast.makeText(register.this, "Registeration Failed", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
                }

            }
        });
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this,login.class));
            }
        });


    }

    private void setupUIViews() {
        fullname = findViewById(R.id.edtFulltName);
        mobile = findViewById(R.id.edtMobile);
        age = findViewById(R.id.edtAge);
        email = findViewById(R.id.edtEmail);
        password = findViewById(R.id.edtPassword);
        register = findViewById(R.id.btnRegister);
        backarrow = findViewById(R.id.iv_backarrow);
    }

    private Boolean validate() {
        Boolean result = false;

        userFullname = fullname.getText().toString();
        userMobile = mobile.getText().toString();
        userAge = age.getText().toString();
        userEmail = email.getText().toString();
        userPassword = password.getText().toString();

        if (userFullname.isEmpty() || userMobile.isEmpty() || userAge.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty()) {
            Toast.makeText(this,"Please enter all the details", Toast.LENGTH_SHORT).show();

        }else {
            result=true;
        }
        return result;
    }
    // Send email verification method
    private void sendEmailVerification(){
        FirebaseUser firebaseUser=  firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            // addOnCompleteListener to know task completed or not
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        sendUserData();

                        Toast.makeText(register.this,"Successfuly Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                        // To pervent user from sign in tha app because the user alreay created up
                        firebaseAuth.signOut();
                        // finish this Activity and direct user to login page
                        finish();
                        startActivity(new Intent(register.this,login.class));
                    }else {
                        Toast.makeText(register.this,"Verification mail hasn't sent!", Toast.LENGTH_SHORT).show();

                    }

                }
            });


        }

    }
    // create a method to send user registeration info from register activity to firebase database Users
    private void sendUserData() {

        // create a database object and the user authentication by ID because if we use the same user name there will be a lot of users with the same name So UID unique
        dbRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        userEmail = email.getText().toString().trim();
        userFullname = fullname.getText().toString().trim();
        userMobile = mobile.getText().toString().trim();
        userAge = age.getText().toString().trim();

        userProfile user = new userProfile(userEmail,userFullname,userMobile,userAge);
        dbRef.setValue(user);

    }
}

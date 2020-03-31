package Sa.Nozel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login extends AppCompatActivity {
    EditText email,password;
   private Button signin,register;
    private FirebaseAuth firebaseAuth;
        private ProgressDialog progressDialog;
    TextView forgotpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_lt);
        setupUIViews();
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        // To check with DB whether the user already login in or not
       // FirebaseUser user = firebaseAuth.getCurrentUser();
      // if (user != null) {
            //finish();
            //startActivity(new Intent(login.this,search_result.class));
       // }

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String userEmail= email.getText().toString();
               String userPassword= password.getText().toString();
               if (userEmail.isEmpty() || userPassword.isEmpty()){
                   Toast.makeText(login.this,"Please enter login Information",Toast.LENGTH_SHORT).show();
               }else
                validate(userEmail,userPassword);
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, register.class));
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this,forgot_password.class));
            }
        });
    }
    private void setupUIViews(){
        email = findViewById(R.id.edtemaillogin);
        password= findViewById(R.id.edtPasswordLogin);
        signin = findViewById(R.id.btnsignin);
        register= findViewById(R.id.btnRegister);
        forgotpassword = findViewById(R.id.txtforgotpassword);

    }
    private void validate(String userEmail, String userPassword){  //using addOnCompleteListener to check the task successful or not

        progressDialog.setMessage("Signing in");
       progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();

                   checkEmailVerification();
                }else {
                    Toast.makeText(login.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }


            }
        });


    }
    // To check whether user veify his email or not
    private void checkEmailVerification(){

        FirebaseUser firebaseUser= firebaseAuth.getInstance().getCurrentUser();
        Boolean emailVerify= firebaseUser.isEmailVerified();
        if (emailVerify){
            finish();
            Toast.makeText(login.this,"Login Successful",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, search_result.class));
        }else {
            Toast.makeText(login.this,"Please verify your email",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

}

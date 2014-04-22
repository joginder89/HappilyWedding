package com.example.happilywedding;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Emaill extends Activity {

	private EditText recipient;
	private EditText subject;
	private EditText body;
	String id = "F4U@gmail.com";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.emaill);
		recipient = (EditText) findViewById(R.id.recipient);
		subject = (EditText) findViewById(R.id.subject);
		body = (EditText) findViewById(R.id.body);
		recipient.setText(id);
		Button sendBtn = (Button) findViewById(R.id.sendEmail);
		sendBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				sendEmail();
				// after sending the email, clear the fields
				recipient.setText(id);
				subject.setText("");
				body.setText("");
			}
		});
	}

	protected void sendEmail() {

		String[] recipients = { recipient.getText().toString() };
		Intent email = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
		// prompts email clients only
		email.setType("message/rfc822");

		email.putExtra(Intent.EXTRA_EMAIL, recipients);
		email.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
		email.putExtra(Intent.EXTRA_TEXT, body.getText().toString());

		try {
			// the user can choose the email client
			startActivity(Intent.createChooser(email,
					"Choose an email client from..."));

		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(Emaill.this, "No email client installed.",
					Toast.LENGTH_LONG).show();
		}
	}

}

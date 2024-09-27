package com.example.neoapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale
import android.util.Log
import com.example.neoapp.models.Document

class AddDocumentActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_document)

        titleEditText = findViewById(R.id.title_edit_text)
        saveButton = findViewById(R.id.save_button)

        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            if (title.isNotEmpty()) {
                // Create new document with current date
                val currentDate = getCurrentDate()
                val newDocument = Document(
                    title = title,
                    createdAt = Date(),  // Set to current date
                    updatedAt = Date()   // Set to current date
                )
                saveDocument(newDocument)
            } else {
                showToast("Title cannot be empty")
            }
        }
    }

    private fun getCurrentDate(): Date {
        return Date() // Return current system time
    }

    private fun saveDocument(document: Document) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Make the API call, which will return a Response object
                val response = RetrofitInstance.api.createDocument(document).execute()

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        showToast("Document saved successfully")
                        finish() // Close the activity and return to MainActivity
                    } else {
                        showToast("Error saving document")
                    }
                }
            } catch (e: Exception) {
                Log.e("AddDocumentActivity", "Error saving document", e)
                withContext(Dispatchers.Main) {
                    showToast("Network error")
                }
            }
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(this@AddDocumentActivity, message, Toast.LENGTH_SHORT).show()
    }
}

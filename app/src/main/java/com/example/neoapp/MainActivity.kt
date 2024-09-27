package com.example.neoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neoapp.adapter.DocumentAdapter
import java.util.Locale
import android.util.Log
import com.example.neoapp.AddDocumentActivity
import com.example.neoapp.R
import com.example.neoapp.RetrofitInstance
import com.example.neoapp.models.Document
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.appcompat.widget.SearchView

class MainActivity : AppCompatActivity() {
    private lateinit var documentAdapter: DocumentAdapter
    private lateinit var documentList: List<Document>
    private lateinit var searchView: SearchView // Change to SearchView
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchView = findViewById(R.id.search_view) // Still using SearchView
        recyclerView = findViewById(R.id.recyclerView)

        // Initialize RecyclerView with LinearLayoutManager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize adapter with an empty MutableList
        documentAdapter = DocumentAdapter(mutableListOf())
        recyclerView.adapter = documentAdapter

        // Fetch documents from the backend
        fetchDocuments()

        // Set up search functionality
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchText = newText?.lowercase(Locale.ROOT) ?: ""
                val filteredList = documentList.filter { it.title.lowercase(Locale.ROOT).contains(searchText) }

                // Update the list in the existing adapter and notify the change
                documentAdapter.updateDocuments(filteredList)
                return true
            }
        })

        // Add button click listener
        findViewById<Button>(R.id.add_document_button).setOnClickListener {
            // Navigate to Add Document Activity
            startActivity(Intent(this, AddDocumentActivity::class.java))
        }
    }

    private fun fetchDocuments() {
        // Call the backend to get all documents
        RetrofitInstance.api.getAllDocuments().enqueue(object : Callback<List<Document>> {
            override fun onResponse(call: Call<List<Document>>, response: Response<List<Document>>) {
                if (response.isSuccessful && response.body() != null) {
                    documentList = response.body()!!

                    // Log document details for debugging
                    for (doc in documentList) {
                        Log.d("MainActivity", "Document title: ${doc.title}, CreatedAt: ${doc.createdAt}, UpdatedAt: ${doc.updatedAt}")
                    }

                    documentAdapter.updateDocuments(documentList)
                } else {
                    Log.e("MainActivity", "Failed to fetch documents: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<Document>>, t: Throwable) {
                Log.e("MainActivity", "Error fetching documents", t)
            }
        })
    }


}

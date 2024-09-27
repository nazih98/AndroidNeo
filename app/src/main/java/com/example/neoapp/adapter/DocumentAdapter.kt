package com.example.neoapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.neoapp.R
import com.example.neoapp.models.Document
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

class DocumentAdapter(private var documents: MutableList<Document>) : RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder>() {

    inner class DocumentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.document_title)
        val date: TextView = view.findViewById(R.id.document_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_document, parent, false)
        return DocumentViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        val document = documents[position]
        holder.title.text = document.title
        holder.date.text = "Created: ${formatDate(document.createdAt)}\nUpdated: ${formatDate(document.updatedAt)}"
    }

    override fun getItemCount(): Int {
        return documents.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDocuments(newDocuments: List<Document>) {
        documents.clear()
        documents.addAll(newDocuments)
        notifyDataSetChanged()
    }

    // Helper function to format Date objects
    private fun formatDate(date: Date?): String {
        return if (date != null) {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            formatter.format(date)
        } else {
            "No Date"
        }
    }
}

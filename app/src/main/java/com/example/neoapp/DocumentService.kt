import com.example.neoapp.models.Document
import retrofit2.Call
import retrofit2.http.*

interface DocumentService {

    // Fetch all documents
    @GET("api/document")
    fun getAllDocuments(): Call<List<Document>>

    // Fetch a single document by ID
    @GET("api/document/{id}")
    fun getDocumentById(@Path("id") id: String): Call<Document>

    // Create a new document
    @POST("api/document")
    fun createDocument(@Body newDocument: Document): Call<Document>

    // Update an existing document
    @PUT("api/document/{id}")
    fun updateDocument(@Path("id") id: String, @Body updatedDocument: Document): Call<Document>

    // Delete a document
    @DELETE("api/document/{id}")
    fun deleteDocument(@Path("id") id: String): Call<Void>
}

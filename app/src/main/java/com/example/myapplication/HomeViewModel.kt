package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentSnapshot

class HomeViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    
    private val _books = MutableLiveData<List<Livro>>()
    val books: LiveData<List<Livro>> get() = _books

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private var lastVisible: DocumentSnapshot? = null
    private var isLastPage = false
    private val PAGE_SIZE = 10L

    fun fetchBooks(isFirstPage: Boolean) {
        if (loading.value == true) return
        if (!isFirstPage && isLastPage) return

        _loading.value = true
        _error.value = null

        var query = db.collection("Livros")
            .limit(PAGE_SIZE)

        if (isFirstPage) {
            lastVisible = null
            isLastPage = false
        } else if (lastVisible != null) {
            query = query.startAfter(lastVisible!!)
        }

        query.get()
            .addOnSuccessListener { documents ->
                val newBooks = documents.toObjects(Livro::class.java)
                
                if (isFirstPage) {
                    _books.value = newBooks
                } else {
                    val currentList = _books.value?.toMutableList() ?: mutableListOf()
                    currentList.addAll(newBooks)
                    _books.value = currentList
                }

                if (!documents.isEmpty) {
                    lastVisible = documents.documents[documents.size() - 1]
                }
                
                isLastPage = documents.size() < PAGE_SIZE
                _loading.value = false
            }
            .addOnFailureListener { exception ->
                _error.value = exception.message
                _loading.value = false
            }
    }
}

package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration

class HomeViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private var favoritesListener: ListenerRegistration? = null
    
    private var booksListener: ListenerRegistration? = null
    
    private val _books = MutableLiveData<List<Livro>>()
    val books: LiveData<List<Livro>> get() = _books

    private val _favorites = MutableLiveData<List<Livro>>()
    val favorites: LiveData<List<Livro>> get() = _favorites

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private var lastVisible: DocumentSnapshot? = null
    private var isLastPage = false
    private val PAGE_SIZE = 50L // Aumentado para cobrir mais livros no real-time

    fun fetchFavorites(userId: String) {
        // Remove listener anterior se existir
        favoritesListener?.remove()

        favoritesListener = db.collection("Favoritos")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    _error.value = "Erro ao carregar favoritos: ${e.message}"
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val favBooks = snapshot.documents.mapNotNull { doc ->
                        val livro = doc.toObject(Livro::class.java)
                        livro?.id = doc.getString("livroId") ?: doc.id
                        livro
                    }
                    _favorites.value = favBooks
                }
            }
    }

    fun fetchBooks(isFirstPage: Boolean) {
        if (loading.value == true) return
        if (!isFirstPage && isLastPage) return

        _loading.value = true
        _error.value = null

        // Para a primeira página, usamos um listener em tempo real
        if (isFirstPage) {
            booksListener?.remove()
            booksListener = db.collection("Livros")
                .limit(PAGE_SIZE)
                .addSnapshotListener { snapshot, e ->
                    _loading.value = false
                    if (e != null) {
                        _error.value = e.message
                        return@addSnapshotListener
                    }

                    if (snapshot != null) {
                        val booksList = snapshot.documents.map { doc ->
                            val livro = doc.toObject(Livro::class.java)!!
                            livro.id = doc.id
                            livro
                        }
                        _books.value = booksList
                        if (!snapshot.isEmpty) {
                            lastVisible = snapshot.documents[snapshot.size() - 1]
                        }
                        isLastPage = snapshot.size() < PAGE_SIZE
                    }
                }
        } else {
            // Para páginas seguintes, mantemos o get() para evitar excesso de listeners ativos
            var query = db.collection("Livros")
                .limit(PAGE_SIZE)

            if (lastVisible != null) {
                query = query.startAfter(lastVisible!!)
            }

            query.get()
                .addOnSuccessListener { documents ->
                    val newBooks = documents.map { doc ->
                        val livro = doc.toObject(Livro::class.java)
                        livro.id = doc.id 
                        livro
                    }
                    
                    val currentList = _books.value?.toMutableList() ?: mutableListOf()
                    currentList.addAll(newBooks)
                    _books.value = currentList

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

    override fun onCleared() {
        super.onCleared()
        favoritesListener?.remove()
        booksListener?.remove()
    }
}

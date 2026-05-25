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
    private val PAGE_SIZE = 10L

    fun fetchFavorites(userId: String) {
        // Remove listener anterior se existir
        favoritesListener?.remove()

        // Usamos SnapshotListener para que a Home atualize em tempo real 
        // quando o usuário favoritar/desfavoritar em outra tela
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
                        // IMPORTANTE: Garantir que o ID do livro seja o ID original (livroId)
                        // e não o ID do documento do favorito (que é userId_livroId)
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
                val newBooks = documents.map { doc ->
                    val livro = doc.toObject(Livro::class.java)
                    // IMPORTANTE: Se o documento não tem o campo 'id' dentro dele,
                    // pegamos o ID do documento do Firestore.
                    livro.id = doc.id 
                    livro
                }
                
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

    override fun onCleared() {
        super.onCleared()
        favoritesListener?.remove()
    }
}

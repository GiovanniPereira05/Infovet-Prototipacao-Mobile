import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infovet.DatabaseHelper
import com.example.infovet.SessionManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoginViewModel(private val sessionManager: SessionManager, private val databaseHelper: DatabaseHelper) : ViewModel() {
    fun verificarSessaoValida(onSessaoValida: () -> Unit, onSessaoExpirada: () -> Unit) {
        viewModelScope.launch {
            val logadoNoDataStore = sessionManager.isLoggedIn.first()
            val token = sessionManager.userToken.first()
            if (logadoNoDataStore && token != null) {
                val tokenAindaValido = databaseHelper.checkSessao(token, System.currentTimeMillis())

                if (tokenAindaValido) {
                    onSessaoValida()
                } else {
                    sessionManager.clearSession()
                    onSessaoExpirada()
                }
            } else {
                onSessaoExpirada()
            }
        }
    }

    fun deslogar(onDeslogar: () -> Unit){
        viewModelScope.launch {
            sessionManager.clearSession()
            onDeslogar()
        }
    }



    fun realizarLogin(email: String, token: String, onSucesso: () -> Unit) {
        viewModelScope.launch {
            try {
                sessionManager.saveSession(token, email)
                onSucesso()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
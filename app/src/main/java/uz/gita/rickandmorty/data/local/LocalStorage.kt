package uz.gita.rickandmorty.data.local

import com.securepreferences.SecurePreferences
import uz.gita.rickandmorty.app.App
import javax.inject.Singleton

@Singleton
class LocalStorage {
    private val KEY = "SHDIJHEUNNSONAIEFIUBOMXOSMB4s5456sd4cv8d"
    private val securePref = SecurePreferences(App.instance, KEY, "local_storage.xml")

    var savedCharacters: String
        set(value) = securePref.edit().putString("SAVED_CHARACTERS", value).apply()
        get() = securePref.getString("SAVED_CHARACTERS", "")!!
}
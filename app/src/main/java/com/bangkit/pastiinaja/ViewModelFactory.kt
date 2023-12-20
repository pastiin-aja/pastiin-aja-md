package com.bangkit.pastiinaja

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.pastiinaja.daftar.SignupViewModel
import com.bangkit.pastiinaja.di.Injection
import com.bangkit.pastiinaja.login.LoginViewModel
import com.bangkit.pastiinaja.repository.UserRepository

class ViewModelFactory(private val repository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(repository) as T
            }

            modelClass.isAssignableFrom(StoryAddViewModel::class.java) -> {
                StoryAddViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        fun getInstance(context: Context): ViewModelFactory =
            synchronized(this) {
                ViewModelFactory(Injection.provideRepository(context))
            }


//        @Volatile
//        private var INSTANCE: ViewModelFactory? = null
//
//        @JvmStatic
//        fun getInstance(context: Context): ViewModelFactory {
//            if (INSTANCE == null) {
//                synchronized(ViewModelFactory::class.java) {
//                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
//                }
//            }
//            return INSTANCE as ViewModelFactory
//        }
    }
}
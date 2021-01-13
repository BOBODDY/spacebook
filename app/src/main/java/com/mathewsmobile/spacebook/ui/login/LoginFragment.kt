package com.mathewsmobile.spacebook.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mathewsmobile.spacebook.R
import com.mathewsmobile.spacebook.model.LoginResponse
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * [Fragment] for authenticating a user using an email and password
 */
class LoginFragment : Fragment() {

    lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        viewModel.loginStatus.observe(viewLifecycleOwner, Observer {
            handleLoginResponse(it)
        })

        loginButton.setOnClickListener {
            val email = username.text.toString()
            val password = password.text.toString()

            viewModel.login(email, password).observe(viewLifecycleOwner, Observer { 
                handleLoginResponse(it)
            })
        }
    }

    private fun handleLoginResponse(it: LoginResponse) {
        if (it.loginSuccessful()) {
            Log.d(TAG, "Login succeeded, going to feed")
            findNavController().navigate(R.id.actionLoginSuccessful)
        } else {
            Log.e(TAG, "Failed to login")
        }
    }
    
    companion object {
        private const val TAG = "LoginFragment"
    }
}
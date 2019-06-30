package com.igmob.android.marvelapi.application.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.igmob.android.marvelapi.R
import com.igmob.android.marvelapi.application.ui.characterslist.CharactersListFragment
import com.igmob.android.marvelapi.domain.core.CharactersListRepository
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val repository: CharactersListRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setCurrentFragment(CharactersListFragment.newInstance(repository))
    }

    private fun setCurrentFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

}

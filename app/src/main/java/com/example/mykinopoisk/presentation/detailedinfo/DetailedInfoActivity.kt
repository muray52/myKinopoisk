package com.example.mykinopoisk.presentation.detailedinfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mykinopoisk.R

class DetailedInfoActivity : AppCompatActivity() {

    private var filmId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_info)
        parseIntent()
        launchFragment()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(FILM_ID)) {
            throw RuntimeException("Param filmId is absent")
        }
        filmId = intent.getIntExtra(FILM_ID, 0)
    }

    private fun launchFragment() {
        val fragment = DetailedInfoFragment.newInstance(filmId)
        supportFragmentManager.beginTransaction()
            .add(R.id.detailed_film_container, fragment)
            .commit()
    }

    companion object {

        private const val FILM_ID = "filmId"

        fun newIntent(context: Context, filmId: Int): Intent {
            val intent = Intent(context, DetailedInfoActivity::class.java)
            intent.putExtra(FILM_ID, filmId)
            return intent
        }
    }
}
package xyz.odevgabriel.motivation.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import xyz.odevgabriel.motivation.util.Constants
import xyz.odevgabriel.motivation.R
import xyz.odevgabriel.motivation.data.Mock
import xyz.odevgabriel.motivation.util.SecurityPreferences
import xyz.odevgabriel.motivation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private var categoryId: Int = Constants.FILTER.ALL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        handleFilter(R.id.image_all)
        handleNewPhrase()

        binding.buttonNewPhrase.setOnClickListener(this)
        binding.imageAll.setOnClickListener(this)
        binding.imageHappy.setOnClickListener(this)
        binding.imageSunny.setOnClickListener(this)
        binding.textName.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            in listOf(R.id.image_all, R.id.image_happy, R.id.image_sunny) -> {
                handleFilter(view.id)
            }
            R.id.button_new_phrase -> {
                handleNewPhrase()
            }
            R.id.text_name -> {
                startActivity(Intent(this, UserActivity::class.java))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        handleUserName()
    }

    private fun handleNewPhrase() {
        val phrase = Mock().getPhrase(categoryId)
        binding.textPhrase.text = phrase.description
    }

    private fun handleFilter(id: Int) {
        binding.imageAll.setColorFilter(ContextCompat.getColor(this, R.color.dark_purple))
        binding.imageSunny.setColorFilter(ContextCompat.getColor(this, R.color.dark_purple))
        binding.imageHappy.setColorFilter(ContextCompat.getColor(this, R.color.dark_purple))

        when (id) {
            R.id.image_all -> {
                categoryId = Constants.FILTER.ALL
                binding.imageAll.setColorFilter(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
            }
            R.id.image_happy -> {
                categoryId = Constants.FILTER.HAPPY
                binding.imageHappy.setColorFilter(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
            }
            R.id.image_sunny -> {
                categoryId = Constants.FILTER.SUNNY
                binding.imageSunny.setColorFilter(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
            }
        }
    }

    private fun handleUserName() {
        val name = SecurityPreferences(this).getString(Constants.KEY.USER_NAME)
        binding.textName.text = "Olá, ${name.ifEmpty { "mundo" }}"
    }
}
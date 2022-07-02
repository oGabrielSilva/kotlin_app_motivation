package xyz.odevgabriel.motivation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import xyz.odevgabriel.motivation.util.Constants
import xyz.odevgabriel.motivation.R
import xyz.odevgabriel.motivation.util.SecurityPreferences
import xyz.odevgabriel.motivation.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.buttonSave.setOnClickListener(this)
        handleUserName()
    }

    override fun onClick(v: View) {
        if (v.id != R.id.button_save) return
        handleSave()
    }

    private fun handleUserName() {
        val name = SecurityPreferences(this).getString(Constants.KEY.USER_NAME)
        if(name.isEmpty()) return
        binding.editName.setText(name)
    }

    private fun handleSave() {
        val text = binding.editName.text.toString()
        if (text == "" || text.length < 3) return Toast.makeText(
            this,
            R.string.validation_mandatory_name,
            Toast.LENGTH_SHORT
        ).show()
        SecurityPreferences(this).store(Constants.KEY.USER_NAME, text)
        finish()
    }
}

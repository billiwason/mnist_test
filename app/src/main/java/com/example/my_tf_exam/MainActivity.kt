package com.example.my_tf_exam

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.divyanshu.draw.widget.DrawView
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var classifier: Classifier

    private val execute : Button by lazy{
        findViewById(R.id.inference)
    }
    private val drawView : DrawView by lazy{
        findViewById(R.id.draw_view)
    }

    private val clear : Button by lazy {
        findViewById(R.id.clear)
    }

    private val resultText : TextView by lazy {
        findViewById(R.id.result)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initClassifier()

        drawView.setStrokeWidth(50.0f)
        drawView.setColor(Color.WHITE)

        clear.setOnClickListener {
            drawView.clearCanvas()
        }

        execute.setOnClickListener {
            val image = drawView.getBitmap()
            val result = classifier.classify(image)
            val outString = String.format("%d, %.0f%%", result.first, result.second * 100.0f)
            resultText.text = outString
        }
    }

    private fun initClassifier() {
        classifier = Classifier(assets, Classifier.DIGIT_CLASSIFIER)
        try {
            classifier.init()
        } catch (exception: IOException) {
            Toast.makeText(this,"classifier IOException", Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(this,"SuccessToInitialize", Toast.LENGTH_SHORT).show()
    }


    override fun onDestroy() {
        if (::classifier.isInitialized) classifier.finish()
        super.onDestroy()
    }
}
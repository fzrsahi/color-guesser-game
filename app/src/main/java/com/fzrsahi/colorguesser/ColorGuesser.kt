package com.fzrsahi.colorguesser

import android.app.AlertDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


//cara bermain : drag kotak yang dibawah ke kotak yang warnanya sama

class ColorGuesser : AppCompatActivity(),View.OnDragListener {

    lateinit var draggableView: View
    lateinit var dragReceives: Array<View>
    var health : Int = 5
    lateinit var healthScore : TextView

    fun generateRandomColor(): Int {
        val alpha = 255
        val red = (0..255).random()
        val green = (0..255).random()
        val blue = (0..255).random()
        return (alpha shl 24) or (red shl 16) or (green shl 8) or blue
    }


    fun displayMessage() {
        val alertDialog = AlertDialog.Builder(this)
            .setMessage("Anda Kehabisan Nyawa")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .create()

        alertDialog.show()
        health = 5
    }

    fun displayHealth(){
        val scoreHealth = resources.getString(R.string.health_score_string, health.toString())
        healthScore.text = scoreHealth
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.color_guesser)
        Log.i("colorguesser","test")

        healthScore  = findViewById(R.id.health_score)

        draggableView = findViewById(R.id.draggable_view);
        dragReceives = arrayOf(
            findViewById(R.id.drag_receive_one),
            findViewById(R.id.drag_receive_two),
            findViewById(R.id.drag_receive_three),
            findViewById(R.id.drag_receive_four),
            findViewById(R.id.drag_receive_five),
            findViewById(R.id.drag_receive_six)
        )


        val draggableView: View = findViewById(R.id.draggable_view)
        draggableView.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                val shadowBuilder = View.DragShadowBuilder(view)
                view.startDragAndDrop(null, shadowBuilder, view, 0)
                true
            } else if (motionEvent.action == MotionEvent.ACTION_UP) {
                view.performClick() // menangani kejadian klik
                true
            } else {
                false
            }

        }
        for (dragReceive in dragReceives) {
            dragReceive.setOnDragListener(this)

        }
        randomColor()
    }


    override fun onDrag(view: View, event: DragEvent): Boolean {
        when (event.action) {
            DragEvent.ACTION_DROP -> {
                val receiveViewColor = (view.background as ColorDrawable).color
                val sendViewColor = (draggableView.background as ColorDrawable).color
                if (receiveViewColor != sendViewColor) {
                    health--
                }

                if (health == 0){
                    displayMessage()
                }
                displayHealth()
                randomColor()
                return true
            }
        }
        return true
    }



    fun randomColor() {
        val trueColor = generateRandomColor()
        draggableView.setBackgroundColor(trueColor)

        dragReceives.shuffle()

        for (i in 0 until dragReceives.size) {
            val dragReceive = dragReceives[i]
            if (i == 0) {
                dragReceive.setBackgroundColor(trueColor)
            } else {
                dragReceive.setBackgroundColor(generateRandomColor())
            }
        }
    }
}

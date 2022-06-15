package com.rossgramm.rossapp.base.common

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import java.io.IOException
import java.io.InputStream
import java.security.AccessController.getContext


// Класс нужен для тестирования случайно загруженных
// картинок в данно случае в ImageView  будет попадать
// случайно загруженная картинка

@Suppress("DEPRECATION")
class LoadRandomImageFromAssets(var imageView: ImageView, val context: Context) {

    fun loadImage(){
        // выбираем случайную картинку из assets
        val fileName = when ((0..4).random()){
            0-> "alberta-2297204_1920.jpg"
            1-> "river-219972_1280.jpg"
            2-> "tree-736885_1280.jpg"
            3-> "woman-570883_1920.jpg"
            else -> "tree-736885_1280.jpg"
        }

        try {
            // get input stream
            val ims: InputStream = context.assets.open(fileName)
            // load image as Drawable
            val d = Drawable.createFromStream(ims, null)
            // set image to ImageView
            imageView.setImageDrawable(d)
            ims.close()
        } catch (ex: IOException) {
            return
        }


    }

}

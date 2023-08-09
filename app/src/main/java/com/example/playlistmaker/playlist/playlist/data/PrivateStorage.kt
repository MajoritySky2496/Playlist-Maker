package com.example.playlistmaker.playlist.playlist.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import com.example.playlistmaker.playlist.playlist.data.storage.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class PrivateStorage(private val context: Context):Storage {
    override suspend fun saveImageToPrivateStorage(uri: Uri?) {
        val filePath = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "myalbum")
        //создаем каталог, если он не создан
        if (!filePath.exists()){
            filePath.mkdirs()
        }
        val idlist = uri.toString().split("/")
        val id = idlist.get(idlist.size-1)


        //создаём экземпляр класса File, который указывает на файл внутри каталога
        val file = File(filePath, id)
        // создаём входящий поток байтов из выбранной картинки
        val inputStream = uri?.let { context.contentResolver.openInputStream(it) }
        // создаём исходящий поток байтов в созданный выше файл
        val outputStream =
            FileOutputStream(file)

        // записываем картинку с помощью BitmapFactory
        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
    }

    override suspend fun getImage(uri:String): Uri {
        val id = uri.split("/")
        val filePath = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "myalbum")
        val file = File(filePath, id.last())
        return file.toUri()
    }

}
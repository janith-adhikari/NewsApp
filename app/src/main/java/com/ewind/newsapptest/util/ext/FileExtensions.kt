package com.ewind.newsapptest.util.ext

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.charset.Charset

fun Context.getAppFilePath(child: String = "bse"): String {
    val dir = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        File(this.externalCacheDir, child)
    } else {
        File(Environment.getDataDirectory(), child)
    }
    if (!dir.exists()) {
        dir.mkdirs()
    }
    return dir.absolutePath
}

fun Context.getRealPathFromURI(uri: Uri?): Uri? {

    if (uri == null)
        return null

    var cursor: Cursor? = null
    try {
        val proj = arrayOf(MediaStore.Files.FileColumns.DATA)
        cursor = contentResolver.query(uri, proj, null, null, null)
        if (cursor == null) {
            return null
        }
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return Uri.parse(cursor.getString(column_index))
    } finally {
        cursor?.close()
    }
}

@Throws(IOException::class)
fun Context.readFileFromUri(uri: Uri, child: String = "temp"): File {
    val fileDir = getAppFilePath()
    val file = File(fileDir, child)
    file.deleteOnExit()
    file.createNewFile()
    val fileOutputStream = FileOutputStream(file)
    contentResolver.openInputStream(uri)?.use { inputStream ->
        fileOutputStream.write(inputStream.readBytes())
    }
    return file
}

@Throws(IOException::class)
fun Context.loadJSONFromAsset(
    jsonFileName: String
): String {
    (assets).open(jsonFileName)
        .let {
            val buffer = ByteArray(it.available())
            it.read(buffer)
            it.close()
            return String(buffer, Charset.forName("UTF-8"))
        }
}
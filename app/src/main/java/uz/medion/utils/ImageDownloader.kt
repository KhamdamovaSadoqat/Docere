package uz.medion.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import android.util.Log




object ImageDownloader {
    @SuppressLint("CheckResult")
    fun loadImage(
        context: Context,
        url: String,
        imageView: ImageView,
        @DrawableRes
        placeHolderRes: Int? = null,
        @DrawableRes
        errorRes: Int? = null,
    ) {
        val uri = Uri.parse(url)
        val separated = url.split(".")
        if (separated.last() == "svg") {
            GlideToVectorYou
                .init()
                .apply {
                    with(context)
                    if (placeHolderRes != null && errorRes != null)
                        setPlaceHolder(placeHolderRes, errorRes)
                    else
                        setPlaceHolder(uz.medion.R.drawable.ic_image_placeholder, uz.medion.R.drawable.ic_loading_error)
                    load(uri, imageView)
                }


        } else {
            Glide.with(context)
                .load(uri)
                .apply {
                    if (placeHolderRes != null)
                        placeholder(placeHolderRes)
                    else
                        placeholder(uz.medion.R.drawable.ic_image_placeholder)
                    if (errorRes != null)
                        error(errorRes)
                    else
                        error(uz.medion.R.drawable.ic_loading_error)
                    into(imageView)
                }
        }
    }

    fun getImage(
        context: Context,
        url: String,
        onResponse: ((Bitmap) -> Unit),
    ) {
        val uri = Uri.parse(url)
        Glide.with(context)
            .asBitmap()
            .load(uri)
            .placeholder(uz.medion.R.drawable.ic_image_placeholder)
            .error(uz.medion.R.drawable.ic_loading_error)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    onResponse.invoke(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })

    }
}
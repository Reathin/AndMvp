package com.rairmmd.andmvp.loader;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;

public class MediaLoader implements AlbumLoader {

    @Override
    public void load(ImageView imageView, AlbumFile albumFile) {
        if (albumFile != null && albumFile.getPath() != null) {
            load(imageView, "file://".concat(albumFile.getPath()));
        }
    }

    @Override
    public void load(ImageView imageView, String url) {
        Picasso.get().load(url).into(imageView);
    }
}
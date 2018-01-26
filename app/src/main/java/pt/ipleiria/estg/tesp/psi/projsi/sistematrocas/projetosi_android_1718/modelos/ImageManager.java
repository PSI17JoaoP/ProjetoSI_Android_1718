package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by JAPorelo on 26-01-2018.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos
 */

public class ImageManager {

    public static byte[] toByteArray(Bitmap image) {

        byte[] imageBytes;

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, stream);

            imageBytes = stream.toByteArray();

            stream.flush();
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Ocorreu um erro no processamento da imagem devolvida pela câmara.");
        }

        return imageBytes;
    }

    public static String toBase64(byte[] imageBytes) {

        byte[] imageBase64Bytes = Base64.encode(imageBytes, Base64.DEFAULT);

        return new String(imageBase64Bytes);
    }

    public static byte[] fromBase64(String imageBase64) {

        return Base64.decode(imageBase64, Base64.DEFAULT);
    }

    /**
     *  Contém partes da solução da Google (https://developer.android.com/topic/performance/graphics/load-bitmap.html)
     */
    public static byte[] downscaleImage(byte[] imageBytes, int reqWidth, int reqHeight) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length, options);

        options.inSampleSize = calculateInSampleSize(options.outWidth, options.outHeight, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;

        Bitmap downsampledImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length, options);

        return toByteArray(downsampledImage);
    }

    /**
     *  Contém partes da solução da Google (https://developer.android.com/topic/performance/graphics/load-bitmap.html)
     */
    private static int calculateInSampleSize(final int imageWidth, final int imageHeight, int reqWidth, int reqHeight) {

        int inSampleSize = 1;

        if (imageHeight > reqWidth || imageWidth > reqHeight) {

            final int halfHeight = imageHeight / 2;
            final int halfWidth = imageWidth / 2;

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth)
                inSampleSize *= 2;
        }

        return inSampleSize;
    }
}

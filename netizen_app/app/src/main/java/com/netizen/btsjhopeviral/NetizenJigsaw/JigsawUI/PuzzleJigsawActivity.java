package com.netizen.btsjhopeviral.NetizenJigsaw.JigsawUI;

import static java.lang.Math.abs;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.aliendroid.alienads.AliendroidBanner;
import com.aliendroid.alienads.AliendroidIntertitial;
import com.aliendroid.alienads.AliendroidNative;
import com.netizen.btsjhopeviral.NetizenSettings;
import com.netizen.btsjhopeviral.R;
import com.netizen.btsjhopeviral.NetizenJigsaw.asset.PuzzlePiece;
import com.netizen.btsjhopeviral.NetizenJigsaw.config.TouchListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PuzzleJigsawActivity extends AppCompatActivity {
    ArrayList<PuzzlePiece> pieces;
    String mCurrentPhotoPath;
    String mCurrentPhotoUri;
    private FrameLayout adContainerView;
    public static MediaPlayer mpsound, finissound;
    public static ImageView imageViewx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle_activity_puzzle);
        final Animation fade_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        imageViewx = findViewById(R.id.imageViewx);

        RelativeLayout layAds = findViewById(R.id.layNative);
        switch (NetizenSettings.SELECT_MAIN_ADS) {
            case "ADMOB":
                AliendroidNative.SmallNativeAdmob(PuzzleJigsawActivity.this, layAds, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_NATIVES, NetizenSettings.BACKUP_ADS_NATIVES, NetizenSettings.HIGH_PAYING_KEYWORD1, NetizenSettings.HIGH_PAYING_KEYWORD2, NetizenSettings.HIGH_PAYING_KEYWORD3, NetizenSettings.HIGH_PAYING_KEYWORD4, NetizenSettings.HIGH_PAYING_KEYWORD5);
                break;
            case "ADMOB-B":
                AliendroidBanner.SmallBannerAdmob(PuzzleJigsawActivity.this, layAds, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_BANNER, NetizenSettings.BACKUP_ADS_BANNER, NetizenSettings.HIGH_PAYING_KEYWORD1, NetizenSettings.HIGH_PAYING_KEYWORD2, NetizenSettings.HIGH_PAYING_KEYWORD3, NetizenSettings.HIGH_PAYING_KEYWORD4, NetizenSettings.HIGH_PAYING_KEYWORD5);
                break;
            case "APPLOVIN-M":
                AliendroidNative.SmallNativeMax(PuzzleJigsawActivity.this, layAds, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_NATIVES, NetizenSettings.BACKUP_ADS_NATIVES);
                break;
            case "APPLOVIN-D":
                AliendroidBanner.SmallBannerApplovinDis(PuzzleJigsawActivity.this, layAds, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_NATIVES, NetizenSettings.BACKUP_ADS_NATIVES);
                break;
            case "IRON":
                AliendroidBanner.SmallBannerIron(PuzzleJigsawActivity.this, layAds, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_NATIVES, NetizenSettings.BACKUP_ADS_NATIVES);
                break;
            case "FACEBOOK":
                AliendroidNative.SmallNativeFan(PuzzleJigsawActivity.this, layAds, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_NATIVES, NetizenSettings.BACKUP_ADS_NATIVES);
                break;
            case "GOOGLE-ADS":
                AliendroidBanner.SmallBannerGoogleAds(PuzzleJigsawActivity.this, layAds, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_BANNER, NetizenSettings.BACKUP_ADS_BANNER);
                break;
        }


        new CountDownTimer(5000, 1000) {
            @Override
            public void onFinish() {
                imageViewx.setVisibility(View.VISIBLE);
                imageViewx.startAnimation(fade_out);
            }

            @Override
            public void onTick(long millisUntilFinished) {

            }
        }.start();
        mpsound = MediaPlayer.create(this, R.raw.backsound);
        finissound = MediaPlayer.create(this, R.raw.end);

        if (PuzzleMainActivity.numsound == 1) {
            mpsound.start();
            mpsound.setLooping(true);
        }


        final RelativeLayout layout = findViewById(R.id.layout);
        final ImageView imageView = findViewById(R.id.imageView);

        Intent intent = getIntent();
        final String assetName = intent.getStringExtra("assetName");
        mCurrentPhotoPath = intent.getStringExtra("mCurrentPhotoPath");
        mCurrentPhotoUri = intent.getStringExtra("mCurrentPhotoUri");
        imageView.post(new Runnable() {
            @Override
            public void run() {
                if (assetName != null) {
                    Picasso.get().load(assetName).into(imageView);
                } else if (mCurrentPhotoPath != null) {
                    setPicFromPath(mCurrentPhotoPath, imageView);
                } else if (mCurrentPhotoUri != null) {
                    imageView.setImageURI(Uri.parse(mCurrentPhotoUri));
                }
                pieces = splitImage();
                TouchListener touchListener = new TouchListener(PuzzleJigsawActivity.this);
                Collections.shuffle(pieces);
                for (PuzzlePiece piece : pieces) {
                    piece.setOnTouchListener(touchListener);
                    layout.addView(piece);
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) piece.getLayoutParams();
                    lParams.leftMargin = new Random().nextInt(layout.getWidth() - piece.pieceWidth);
                    lParams.topMargin = layout.getHeight() - piece.pieceHeight;
                    piece.setLayoutParams(lParams);
                }
            }
        });
    }

    private void setPicFromAsset(String assetName, ImageView imageView) {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        AssetManager am = getAssets();
        try {
            InputStream is = am.open("img/" + assetName);
            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, new Rect(-1, -1, -1, -1), bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

            is.reset();

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeStream(is, new Rect(-1, -1, -1, -1), bmOptions);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<PuzzlePiece> splitImage() {
        int piecesNumber = PuzzleMainActivity.sum;
        int rows = PuzzleMainActivity.row;
        int cols = PuzzleMainActivity.col;

        ImageView imageView = findViewById(R.id.imageView);
        ArrayList<PuzzlePiece> pieces = new ArrayList<>(piecesNumber);

        // Get the scaled bitmap of the source image
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        int[] dimensions = getBitmapPositionInsideImageView(imageView);
        int scaledBitmapLeft = dimensions[0];
        int scaledBitmapTop = dimensions[1];
        int scaledBitmapWidth = dimensions[2];
        int scaledBitmapHeight = dimensions[3];

        int croppedImageWidth = scaledBitmapWidth - 2 * abs(scaledBitmapLeft);
        int croppedImageHeight = scaledBitmapHeight - 2 * abs(scaledBitmapTop);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledBitmapWidth, scaledBitmapHeight, true);
        Bitmap croppedBitmap = Bitmap.createBitmap(scaledBitmap, abs(scaledBitmapLeft), abs(scaledBitmapTop), croppedImageWidth, croppedImageHeight);

        // Calculate the with and height of the pieces
        int pieceWidth = croppedImageWidth / cols;
        int pieceHeight = croppedImageHeight / rows;

        // Create each bitmap piece and add it to the resulting array
        int yCoord = 0;
        for (int row = 0; row < rows; row++) {
            int xCoord = 0;
            for (int col = 0; col < cols; col++) {
                // calculate offset for each piece
                int offsetX = 0;
                int offsetY = 0;
                if (col > 0) {
                    offsetX = pieceWidth / 3;
                }
                if (row > 0) {
                    offsetY = pieceHeight / 3;
                }

                // apply the offset to each piece
                Bitmap pieceBitmap = Bitmap.createBitmap(croppedBitmap, xCoord - offsetX, yCoord - offsetY, pieceWidth + offsetX, pieceHeight + offsetY);
                PuzzlePiece piece = new PuzzlePiece(getApplicationContext());
                piece.setImageBitmap(pieceBitmap);
                piece.xCoord = xCoord - offsetX + imageView.getLeft();
                piece.yCoord = yCoord - offsetY + imageView.getTop();
                piece.pieceWidth = pieceWidth + offsetX;
                piece.pieceHeight = pieceHeight + offsetY;

                // this bitmap will hold our final puzzle piece image
                Bitmap puzzlePiece = Bitmap.createBitmap(pieceWidth + offsetX, pieceHeight + offsetY, Bitmap.Config.ARGB_8888);

                // draw path
                int bumpSize = pieceHeight / 4;
                Canvas canvas = new Canvas(puzzlePiece);
                Path path = new Path();
                path.moveTo(offsetX, offsetY);
                if (row == 0) {
                    // top side piece
                    path.lineTo(pieceBitmap.getWidth(), offsetY);
                } else {

                    // top bump
                    path.lineTo(offsetX + (pieceBitmap.getWidth() - offsetX) / 3, offsetY);
                    path.cubicTo(offsetX + (pieceBitmap.getWidth() - offsetX) / 6, offsetY - bumpSize, offsetX + (pieceBitmap.getWidth() - offsetX) / 6 * 5, offsetY - bumpSize, offsetX + (pieceBitmap.getWidth() - offsetX) / 3 * 2, offsetY);
                    path.lineTo(pieceBitmap.getWidth(), offsetY);
                }

                if (col == cols - 1) {
                    // right side piece
                    path.lineTo(pieceBitmap.getWidth(), pieceBitmap.getHeight());
                } else {
                    // right bump
                    path.lineTo(pieceBitmap.getWidth(), offsetY + (pieceBitmap.getHeight() - offsetY) / 3);
                    path.cubicTo(pieceBitmap.getWidth() - bumpSize, offsetY + (pieceBitmap.getHeight() - offsetY) / 6, pieceBitmap.getWidth() - bumpSize, offsetY + (pieceBitmap.getHeight() - offsetY) / 6 * 5, pieceBitmap.getWidth(), offsetY + (pieceBitmap.getHeight() - offsetY) / 3 * 2);
                    path.lineTo(pieceBitmap.getWidth(), pieceBitmap.getHeight());
                }

                if (row == rows - 1) {
                    // bottom side piece
                    path.lineTo(offsetX, pieceBitmap.getHeight());
                } else {
                    // bottom bump
                    path.lineTo(offsetX + (pieceBitmap.getWidth() - offsetX) / 3 * 2, pieceBitmap.getHeight());
                    path.cubicTo(offsetX + (pieceBitmap.getWidth() - offsetX) / 6 * 5, pieceBitmap.getHeight() - bumpSize, offsetX + (pieceBitmap.getWidth() - offsetX) / 6, pieceBitmap.getHeight() - bumpSize, offsetX + (pieceBitmap.getWidth() - offsetX) / 3, pieceBitmap.getHeight());
                    path.lineTo(offsetX, pieceBitmap.getHeight());
                }

                if (col == 0) {
                    // left side piece
                    path.close();
                } else {
                    // left bump
                    path.lineTo(offsetX, offsetY + (pieceBitmap.getHeight() - offsetY) / 3 * 2);
                    path.cubicTo(offsetX - bumpSize, offsetY + (pieceBitmap.getHeight() - offsetY) / 6 * 5, offsetX - bumpSize, offsetY + (pieceBitmap.getHeight() - offsetY) / 6, offsetX, offsetY + (pieceBitmap.getHeight() - offsetY) / 3);
                    path.close();
                }

                // mask the piece
                Paint paint = new Paint();
                paint.setColor(0XFF000000);
                paint.setStyle(Paint.Style.FILL);

                canvas.drawPath(path, paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(pieceBitmap, 0, 0, paint);

                // draw a white border
                Paint border = new Paint();
                border.setColor(0XffFFFFFF);
                border.setStyle(Paint.Style.STROKE);
                border.setStrokeWidth(3.0f);
                canvas.drawPath(path, border);

                // draw a black border
                border = new Paint();
                border.setColor(0Xffffffff);
                border.setStyle(Paint.Style.STROKE);
                border.setStrokeWidth(3.0f);
                canvas.drawPath(path, border);

                // set the resulting bitmap to the piece
                piece.setImageBitmap(puzzlePiece);

                pieces.add(piece);
                xCoord += pieceWidth;
            }
            yCoord += pieceHeight;
        }


        return pieces;
    }

    private int[] getBitmapPositionInsideImageView(ImageView imageView) {

        int[] ret = new int[4];

        if (imageView == null || imageView.getDrawable() == null) return ret;

        // Get image dimensions
        // Get image matrix values and place them in an array
        float[] f = new float[9];
        imageView.getImageMatrix().getValues(f);

        // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
        final float scaleX = f[Matrix.MSCALE_X];
        final float scaleY = f[Matrix.MSCALE_Y];

        // Get the drawable (could also get the bitmap behind the drawable and getWidth/getHeight)
        final Drawable d = imageView.getDrawable();
        final int origW = d.getIntrinsicWidth();
        final int origH = d.getIntrinsicHeight();

        // Calculate the actual dimensions
        final int actW = Math.round(origW * scaleX);
        final int actH = Math.round(origH * scaleY);

        ret[2] = actW;
        ret[3] = actH;

        // Get image position
        // We assume that the image is centered into ImageView
        int imgViewW = imageView.getWidth();
        int imgViewH = imageView.getHeight();

        int top = (int) (imgViewH - actH) / 2;
        int left = (int) (imgViewW - actW) / 2;

        ret[0] = left;
        ret[1] = top;

        return ret;
    }

    public void checkGameOver() {
        if (isGameOver()) {
            exitapp();
            finissound.start();
            if (PuzzleMainActivity.numsound == 1) {
                mpsound.stop();
            }
        }
    }

    private boolean isGameOver() {
        for (PuzzlePiece piece : pieces) {
            if (piece.canMove) {
                return false;
            }
        }

        return true;
    }

    private void setPicFromPath(String mCurrentPhotoPath, ImageView imageView) {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        Bitmap rotatedBitmap = bitmap;

        // rotate bitmap if needed
        try {
            ExifInterface ei = new ExifInterface(mCurrentPhotoPath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(bitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(bitmap, 270);
                    break;
            }
        } catch (IOException e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

        imageView.setImageBitmap(rotatedBitmap);
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }


    public void onDestroy() {
        super.onDestroy();
        if (PuzzleMainActivity.numsound == 1) {
            mpsound.stop();
        }
    }

    public void onStop() {
        super.onStop();
        if (PuzzleMainActivity.numsound == 1) {
            mpsound.pause();
        }
    }

    public void onResume() {
        super.onResume();
        if (PuzzleMainActivity.numsound == 1) {
            mpsound.start();
        }
    }

    private void exitapp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PuzzleJigsawActivity.this);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_baseline_insert_emoticon_24);
        builder.setTitle(getString(R.string.end));
        builder.setMessage(getString(R.string.description_end));
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

                switch (NetizenSettings.SELECT_MAIN_ADS) {
                    case "ADMOB":
                    case "ADMOB-B":
                        AliendroidIntertitial.ShowIntertitialAdmob(PuzzleJigsawActivity.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0,
                                NetizenSettings.HIGH_PAYING_KEYWORD1, NetizenSettings.HIGH_PAYING_KEYWORD2, NetizenSettings.HIGH_PAYING_KEYWORD3, NetizenSettings.HIGH_PAYING_KEYWORD4, NetizenSettings.HIGH_PAYING_KEYWORD5);
                        break;
                    case "APPLOVIN-D":
                        AliendroidIntertitial.ShowIntertitialApplovinDis(PuzzleJigsawActivity.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "APPLOVIN-M":
                        AliendroidIntertitial.ShowIntertitialApplovinMax(PuzzleJigsawActivity.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "IRON":
                        AliendroidIntertitial.ShowIntertitialIron(PuzzleJigsawActivity.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "STARTAPP":
                        AliendroidIntertitial.ShowIntertitialSartApp(PuzzleJigsawActivity.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "FACEBOOK":
                        AliendroidIntertitial.ShowIntertitialFAN(PuzzleJigsawActivity.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                    case "GOOGLE-ADS":
                        AliendroidIntertitial.ShowIntertitialGoogleAds(PuzzleJigsawActivity.this, NetizenSettings.SELECT_BACKUP_ADS, NetizenSettings.MAIN_ADS_INTERTITIAL, NetizenSettings.BACKUP_ADS_INTERTITIAL, 0);
                        break;
                }

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

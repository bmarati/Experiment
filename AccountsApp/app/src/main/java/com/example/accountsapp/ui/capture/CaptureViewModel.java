package com.example.accountsapp.ui.capture;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CaptureViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<String> mFilePath;

    public CaptureViewModel() {
        mText = new MutableLiveData<>();
        mFilePath = new MutableLiveData<>();

    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<String> getFilePath() {
        return mFilePath;
    }

    void savePdf(Bitmap image) {
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(image.getWidth(), image.getHeight(), 1).create();
        PdfDocument.Page page = pdfDocument.startPage(myPageInfo);

        page.getCanvas().drawBitmap(image, 0, 0, null);
        pdfDocument.finishPage(page);

        File myPDFFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                , System.currentTimeMillis() + ".pdf");
        try {
            myPDFFile.createNewFile();
            mFilePath.setValue(myPDFFile.getAbsolutePath());
            pdfDocument.writeTo(new FileOutputStream(myPDFFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        pdfDocument.close();
    }
}
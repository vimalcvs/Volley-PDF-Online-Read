package com.vimalcvs.pdflist.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.vimalcvs.pdflist.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.material.appbar.MaterialToolbar;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;

public class PdfViewActivity extends AppCompatActivity implements OnLoadCompleteListener, OnPageErrorListener {

    private ProgressBar pdfViewProgressBar;
    private PDFView pdfView;
    private String pdf_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());


        pdfView = findViewById(R.id.pdfView);
        pdfViewProgressBar = findViewById(R.id.pdfViewProgressBar);

        Bundle bundle=getIntent().getExtras();
        String pdf_name = bundle.getString("pdf_name");
        pdf_url = bundle.getString("pdf_url");
        toolbar.setTitle(pdf_name);

        PdfView();
    }

    private void PdfView() {
        FileLoader.with(this)
                .load(pdf_url, false)
                .fromDirectory("FileSave", FileLoader.DIR_INTERNAL)
                .asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        File pdfFile = response.getBody();
                        try {
                            pdfView.fromFile(pdfFile)
                                    .defaultPage(0)
                                    .enableAnnotationRendering(true)
                                    .onLoad(PdfViewActivity.this)
                                    .scrollHandle(new DefaultScrollHandle(PdfViewActivity.this))
                                    .spacing(10)
                                    .onPageError(PdfViewActivity.this)
                                    .load();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {
                        pdfViewProgressBar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void loadComplete(int nbPages) {
        pdfViewProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onPageError(int page, Throwable t) {
        pdfViewProgressBar.setVisibility(View.GONE);
    }

}
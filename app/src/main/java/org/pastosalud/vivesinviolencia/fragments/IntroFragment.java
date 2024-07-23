package org.pastosalud.vivesinviolencia.fragments;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import org.pastosalud.vivesinviolencia.R;
import org.pastosalud.vivesinviolencia.ui.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IntroFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_intro, container, false);

        ImageView buttonGoToHome = view.findViewById(R.id.btnInformation);
        buttonGoToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment homeFragment = new HomeFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, homeFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        ImageView buttonOpenDocPopup = view.findViewById(R.id.btnRoutesAtention);
        buttonOpenDocPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDocOptionsPopup();
            }
        });

        return view;
    }

    private void showDocOptionsPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Ya conoces las rutas de atención?")
                .setPositiveButton("Si y quiero guardarlas!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Lógica para descargar el archivo PDF
                        downloadPdf();
                    }
                })
                .setNegativeButton("No, quiero conocerlas ahora!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Lógica para visualizar el archivo PDF
                        viewPdf();
                    }
                })
                .create()
                .show();
    }

    private void downloadPdf() {
        try {
            // Copiar archivo PDF desde assets al almacenamiento externo
            InputStream inputStream = getContext().getAssets().open("directory.pdf");
            File downloadsDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "directory.pdf");
            FileOutputStream outputStream = new FileOutputStream(downloadsDir);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.close();

            Toast.makeText(getContext(), "Revisa en tu carpeta Descargas.. ", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error al descargar PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewPdf() {
        // Nombre del archivo PDF en la carpeta assets
        String pdfFile = "directory.pdf";

        // Obtener la ruta completa del archivo PDF en la carpeta assets
        String assetPath = "file:///android_asset/" + pdfFile;

        // Obtener la URI del archivo PDF utilizando FileProvider
        Uri fileUri = Uri.parse(assetPath);

        // Llamar al método para abrir el archivo PDF utilizando la URI obtenida
        openPdfFile(fileUri);
    }

    private void openPdfFile(Uri fileUri) {
        // Verificar si el URI comienza con "file:///android_asset"
        if (fileUri.toString().startsWith("file:///android_asset")) {
            // Obtener el nombre del archivo PDF
            String pdfFileName = "directory.pdf";

            // Copiar el archivo PDF desde assets al almacenamiento interno
            File pdfFile = copyFileFromAssetsToStorage(pdfFileName);

            if (pdfFile != null) {
                // Obtener la URI del archivo temporal utilizando FileProvider
                Uri uri = FileProvider.getUriForFile(requireContext(),
                        "org.pastosalud.vivesinviolencia.fileprovider", pdfFile);

                // Crear un Intent para abrir el archivo PDF con una aplicación de visualización de PDF
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "application/pdf");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getContext(), "No se encontró un visor de PDF", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Error al abrir el archivo PDF", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Si el URI no comienza con "file:///android_asset", continuar con la apertura normal
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(fileUri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getContext(), "No se encontró un visor de PDF", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File copyFileFromAssetsToStorage(String fileName) {
        File file = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            // Abrir el archivo PDF desde assets
            AssetManager assetManager = requireContext().getAssets();
            inputStream = assetManager.open(fileName);

            // Crear un archivo temporal en el directorio de archivos de la aplicación
            file = new File(requireContext().getFilesDir(), fileName);
            outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            return file;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }




}
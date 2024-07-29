package org.pastosalud.vivesinviolencia.fragments;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import org.pastosalud.vivesinviolencia.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PhychoFragment extends Fragment {

    private ImageView iconChat;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_phycho, container, false);

        LottieAnimationView animationView = view.findViewById(R.id.lottie_animation);
        // Puedes controlar la animación aquí
        animationView.setAnimation("animation_justicia3.json");
        animationView.playAnimation();

        LottieAnimationView animationView2 = view.findViewById(R.id.lottie_animation2);
        // Puedes controlar la animación aquí
        animationView2.setAnimation("animation_salud.json");
        animationView2.playAnimation();

        LottieAnimationView animationView3 = view.findViewById(R.id.lottie_animation3);
        // Puedes controlar la animación aquí
        animationView3.setAnimation("animation_proteccion.json");
        animationView3.playAnimation();

        animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment directoryFragment = new DirectoryFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, directoryFragment);
                transaction.addToBackStack("Justicia");
                transaction.commit();
            }
        });
        animationView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment directoryFragment = new DirectoryFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, directoryFragment);
                transaction.addToBackStack("Salud");
                transaction.commit();
            }
        });
        animationView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment directoryFragment = new DirectoryFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, directoryFragment);
                transaction.addToBackStack("Proteccion");
                transaction.commit();
            }
        });
        TextView textView = view.findViewById(R.id.title);
        TextView textView2 = view.findViewById(R.id.second_title);


        iconChat = view.findViewById(R.id.chat_icon);
        iconChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = "+3216469661";

                // abrir WhatsApp
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber));
                startActivity(sendIntent);
            }
        });
        Typeface customFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/kabut_hitam.ttf");
        textView.setTypeface(customFont);

        Typeface customFont2 = Typeface.createFromAsset(getContext().getAssets(), "fonts/kabut_hitam.ttf");
        textView2.setTypeface(customFont2);

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
            InputStream inputStream = getContext().getAssets().open("routes.pdf");
            File downloadsDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "routes.pdf");
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
    private void openPdfFile(Uri fileUri) {
        // Verificar si el URI comienza con "file:///android_asset"
        if (fileUri.toString().startsWith("file:///android_asset")) {
            // Obtener el nombre del archivo PDF
            String pdfFileName = "routes.pdf";

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
    private void viewPdf() {
        // Nombre del archivo PDF en la carpeta assets
        String pdfFile = "routes.pdf";

        // Obtener la ruta completa del archivo PDF en la carpeta assets
        String assetPath = "file:///android_asset/" + pdfFile;

        // Obtener la URI del archivo PDF utilizando FileProvider
        Uri fileUri = Uri.parse(assetPath);

        // Llamar al método para abrir el archivo PDF utilizando la URI obtenida
        openPdfFile(fileUri);
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
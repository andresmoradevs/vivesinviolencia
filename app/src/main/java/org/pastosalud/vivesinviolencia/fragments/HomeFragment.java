package org.pastosalud.vivesinviolencia.fragments;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.pastosalud.vivesinviolencia.models.Item;
import org.pastosalud.vivesinviolencia.R;
import org.pastosalud.vivesinviolencia.adapters.IconAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private IconAdapter adapter;
    private List<Item> itemList;
    private ImageView iconChat;
    private ImageView fb, instagram;
    private static final String FACEBOOK_URL = "https://www.facebook.com/PastoSaludeSe?locale=es_LA";
    private static final String FACEBOOK_PAGE_ID = "377389259132031";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));


        itemList = new ArrayList<>();
        itemList.add(new Item(R.drawable.psychological_violence_icon));
        itemList.add(new Item(R.drawable.pshysical_violence_icon));
        itemList.add(new Item(R.drawable.sexual_violence_icon));
        itemList.add(new Item(R.drawable.behavior_suicide_icon));
        itemList.add(new Item(R.drawable.negligence_icon));
        itemList.add(new Item(R.drawable.patriomonial_violence_icon));

        fb = view.findViewById(R.id.fb_icon);
        instagram = view.findViewById(R.id.instagram_icon);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFacebookPage();
            }
        });
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFacebookPage();
            }
        });

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

        adapter = new IconAdapter(itemList, new IconAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = new PhychoFragment();
                        break;
                    case 1:
                        fragment = new PhysicalFragment();
                        break;
                    case 2:
                        fragment = new SexualFragment();
                        break;
                    case 3:
                        fragment = new SuicideFragment();
                        break;
                    case 4:
                        fragment = new NegligenceFragment();
                        break;
                    case 5:
                        fragment = new PatrimonialFragment();
                        break;
                }
                if (fragment != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        ImageView buttonOpenDocPopup = view.findViewById(R.id.btnRoutesAtention);
        buttonOpenDocPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDocOptionsPopup();
            }
        });

        recyclerView.setAdapter(adapter);

        return view;
    }
    private void openFacebookPage() {
        Intent intent;
        try {
            getActivity().getPackageManager().getPackageInfo("com.facebook.katana", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + FACEBOOK_PAGE_ID));
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_URL));
        }
        startActivity(intent);
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

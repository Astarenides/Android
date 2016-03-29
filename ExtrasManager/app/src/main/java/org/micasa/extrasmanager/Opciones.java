package org.micasa.extrasmanager;

import android.app.Dialog;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;

/**
 * Created by Astarenides on 05/10/2015.
 */
public class Opciones extends Fragment {

    private TextView txtResetBD;
    private TextView txtLimpiarHoras;
    private TextView txtExportarHoras;
    private Dialog customDialog;
    private ImageButton btnMenuOpciones;
    AdaptadorBD db;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public View onCreateView(LayoutInflater inflator,ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflator.inflate(R.layout.opciones, parent, false);

        txtResetBD = (TextView) rootView.findViewById(R.id.opcionesResetBD);
        txtLimpiarHoras = (TextView) rootView.findViewById((R.id.opcionesResetHoras));
        txtExportarHoras = (TextView) rootView.findViewById(R.id.opcionesExportarHoras);
        btnMenuOpciones = (ImageButton) rootView.findViewById(R.id.btnMenuOpciones);

        txtResetBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // con este tema personalizado evitamos los bordes por defecto
                customDialog = new Dialog(getActivity(), R.style.Theme_Dialog_Translucent);
                //deshabilitamos el t�tulo por defecto
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //obligamos al usuario a pulsar los botones para cerrarlo
                customDialog.setCancelable(false);
                //establecemos el contenido de nuestro dialog
                customDialog.setContentView(R.layout.dialog_resetbd);

                TextView textoReset = (TextView) customDialog.findViewById(R.id.textoReset);
                textoReset.setText("Reiniciar la base de datos borrara TODOS los datos introducidos por el usuario.");

                ((com.gc.materialdesign.views.ButtonFlat) customDialog.findViewById(R.id.btnAceptarResetBD)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ResetBD();
                        customDialog.dismiss();
                    }
                });

                ((com.gc.materialdesign.views.ButtonFlat) customDialog.findViewById(R.id.btnCancelarResetBD)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialog.dismiss();
                    }
                });

                customDialog.show();

            }
        });

        txtLimpiarHoras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // con este tema personalizado evitamos los bordes por defecto
                customDialog = new Dialog(getActivity(), R.style.Theme_Dialog_Translucent);
                //deshabilitamos el t�tulo por defecto
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //obligamos al usuario a pulsar los botones para cerrarlo
                customDialog.setCancelable(false);
                //establecemos el contenido de nuestro dialog
                customDialog.setContentView(R.layout.dialog_resetbd);

                TextView textoReset = (TextView) customDialog.findViewById(R.id.textoReset);
                textoReset.setText("Se van a borrar todas las horas extra introducidas por el usuario.");

                ((com.gc.materialdesign.views.ButtonFlat) customDialog.findViewById(R.id.btnAceptarResetBD)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ResetHoras();
                        customDialog.dismiss();
                    }
                });

                ((com.gc.materialdesign.views.ButtonFlat) customDialog.findViewById(R.id.btnCancelarResetBD)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialog.dismiss();
                    }
                });

                customDialog.show();
            }
        });

        txtExportarHoras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int dia = c.get(Calendar.DAY_OF_MONTH);
                int mes = c.get(Calendar.MONTH) + 1;
                int año = c.get(Calendar.YEAR);
                String diaAux = "";
                if (dia < 10) diaAux = '0' + Integer.toString(dia);
                else diaAux = Integer.toString(dia);
                String mesAux = "";
                if (mes < 10) mesAux = '0' + Integer.toString(mes);
                else mesAux = Integer.toString(mes);
                String fecha = diaAux + "_" + mesAux + "_" + año;
                try {
                    File ruta = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/data/org.micasa.extrasmanager/exportar");
                    ruta.mkdirs();
                    String file = ruta + "/Horas_Extra_" + fecha + ".txt";
                    FileOutputStream archivo = new FileOutputStream(new File(file));

                    archivo.write(("Horas Extra:\n").getBytes());
                    archivo.write(("------------------------------------------------------------\n").getBytes());
                    archivo.write(("EN CONTRA:\n").getBytes());
                    archivo.write(("------------------------------------------------------------\n").getBytes());
                    db = new AdaptadorBD(getActivity().getBaseContext());
                    db.abrir();
                    Cursor cF = db.consulta("SELECT fecha, horasExtra, notas FROM HORAS WHERE razon = 0;", null);
                    if (cF.moveToFirst()){
                        do {
                            String notas = cF.getString(2);
                            if (notas == null || notas.equalsIgnoreCase("")) notas = "Sin nota";
                            String linea = cF.getString(0) + ":  " + Double.toString(cF.getDouble(1)) + "  (" + notas + ")\n";
                            archivo.write(linea.getBytes());
                        } while (cF.moveToNext());
                    }
                    cF.close();
                    archivo.write(("------------------------------------------------------------\n").getBytes());
                    archivo.write(("A FAVOR:\n").getBytes());
                    archivo.write(("------------------------------------------------------------\n").getBytes());

                    Cursor cc = db.consulta("SELECT fecha, horasExtra, notas FROM HORAS WHERE razon = 1;", null);
                    if (cc.moveToFirst()){
                        do {
                            String notas = cc.getString(2);
                            if (notas == null || notas.equalsIgnoreCase("")) notas = "Sin nota";
                            String linea = cc.getString(0) + ":  " + Double.toString(cc.getDouble(1)) + "  (" + notas + ")\n";
                            archivo.write(linea.getBytes());
                        } while (cc.moveToNext());
                    }
                    cc.close();
                    archivo.write(("------------------------------------------------------------\n").getBytes());

                    double total = 0;
                    String cad = "";

                    Cursor c2 = db.consulta(
                            "SELECT (SELECT IFNULL((SELECT SUM(horasExtra) FROM HORAS WHERE razon = 1),0)) - (SELECT IFNULL((SELECT SUM(horasExtra) FROM HORAS WHERE razon = 0),0))",null);
                    if (c2.moveToFirst()){
                        do {
                            total = c2.getDouble(0);
                        } while (c2.moveToNext());
                    }
                    c2.close();

                    if (total > 0) {
                        cad = Double.toString(total) + " hora(s) a favor";
                    } else if (total < 0) {
                        total = -total;
                        cad = Double.toString(total) + " hora(s) en contra";
                    } else if (total == 0) {
                        cad = "Sin horas";
                    }
                    archivo.write(("Total: " + cad + "\n").getBytes());
                    db.cerrar();

                    archivo.write(("------------------------------------------------------------\n").getBytes());

                    archivo.flush();
                    archivo.close();
                    Toast.makeText(getActivity().getBaseContext(), "Horas extra exportadas a archivo " + file, Toast.LENGTH_SHORT).show();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnMenuOpciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).openDrawer();
            }
        });

        return rootView;
    }

    public void ResetHoras() {
        try {
            db = new AdaptadorBD(getActivity().getBaseContext());
            db.abrir();
            db.begin();
            db.insertar("DELETE FROM HORAS");
            db.insertar("DELETE FROM SQLITE_SEQUENCE WHERE name = 'HORAS'");
            db.commit();
            Toast.makeText(getActivity().getBaseContext(), "Horas extra eliminadas", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            db.rollback();
        } finally {
            db.rollback();
            db.cerrar();
        }
    }

    public void ResetBD() {
        try {
            File localFile1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/data/org.micasa.extrasmanager/databases");
            localFile1.mkdirs();
            File localFile2 = new File(localFile1, "extras");
            localFile2.delete();
            CopyDB(getActivity().getBaseContext().getAssets().open("extras"), new FileOutputStream(localFile2));
            Toast.makeText(getActivity().getBaseContext(), "Base de datos actualizada", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException localFileNotFoundException) {
            localFileNotFoundException.printStackTrace();
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        }
    }

    public void CopyDB(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException {
        // ---copy 1K bytes at a time---
        byte[] buffer = new byte[1024];
        int length;
        while ((length = paramInputStream.read(buffer)) > 0) {
            paramOutputStream.write(buffer, 0, length);
        }
        paramInputStream.close();
        paramOutputStream.close();
    }

}

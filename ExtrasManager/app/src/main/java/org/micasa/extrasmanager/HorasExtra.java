package org.micasa.extrasmanager;



import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HorasExtra extends Fragment {

	private ImageButton btnExtrasAñadir;
	private ImageButton btnBorrar;
	private ImageButton btnEditar;
	private ListView listaHoras;
	private TextView textoTotal;
	private Dialog customDialog = null;
	private ImageButton btnMenuExtras;
	AdaptadorBD db;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	public View onCreateView(LayoutInflater inflator, ViewGroup parent, Bundle savedInstanceState) {
		final View rootView = inflator.inflate(R.layout.horasextra, parent, false);

		btnExtrasAñadir = (ImageButton) rootView.findViewById(R.id.btnExtras);
		btnBorrar = (ImageButton) rootView.findViewById(R.id.btnBorrar);
		btnEditar = (ImageButton) rootView.findViewById(R.id.btnEditar);
		listaHoras = (ListView) rootView.findViewById(R.id.listaHoras);
		textoTotal = (TextView) rootView.findViewById((R.id.textoTotal));
		btnMenuExtras = (ImageButton) rootView.findViewById(R.id.btnMenuExtras);

		listaHoras.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				listaHoras.setItemChecked(position, true);
				btnBorrar.setVisibility(View.VISIBLE);
				btnEditar.setVisibility(View.VISIBLE);
				return true;
			}
		});

		listaHoras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				listaHoras.clearChoices();
				parent.requestLayout();
				btnBorrar.setVisibility(View.INVISIBLE);
				btnEditar.setVisibility(View.INVISIBLE);

			}
		});

		btnExtrasAñadir.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// con este tema personalizado evitamos los bordes por defecto
				customDialog = new Dialog(getActivity(), R.style.Theme_Dialog_Translucent);
				//deshabilitamos el t�tulo por defecto
				customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				//obligamos al usuario a pulsar los botones para cerrarlo
				customDialog.setCancelable(false);
				//establecemos el contenido de nuestro dialog
				customDialog.setContentView(R.layout.dialog_nuevo_registro);

				//final DatePicker dateExtras = (DatePicker) customDialog.findViewById(R.id.dateExtras);
				final TextView txtFecha = (TextView) customDialog.findViewById(R.id.dateExtras);
				final EditText txtHorasMas = (EditText) customDialog.findViewById(R.id.txtHorasExtraMas);
				final EditText txtNotas = (EditText) customDialog.findViewById(R.id.txtHorasExtraNotas);
				final RadioButton rbMas = (RadioButton) customDialog.findViewById(R.id.rbMas);
				final RadioButton rbMenos = (RadioButton) customDialog.findViewById(R.id.rbMenos);

				txtFecha.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						showDatePickerDialog(view);
					}
				});

				((com.gc.materialdesign.views.ButtonFlat) customDialog.findViewById(R.id.btnAceptarDialogMas)).setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View view) {
						if (txtHorasMas.getText() == null) return;
						if ("Seleccionar Fecha".equals(txtFecha.getText()) || (!rbMas.isChecked() && !rbMenos.isChecked())) {
							return;
						}
						String fecha = txtFecha.getText().toString();
						double horas = Double.parseDouble(txtHorasMas.getText().toString());
						String notas = "";
						if (txtNotas.getText() != null) notas = txtNotas.getText().toString();
						int razon;
						if (rbMas.isChecked()) razon = 1;
						else razon = 0;
						try {
							db = new AdaptadorBD(getActivity().getBaseContext());
							db.abrir();
							//Comprobar si ya hay horas extras puestas ese dia
							int id = 0;
							Cursor c = db.consulta("SELECT id FROM HORAS WHERE fecha = '" + fecha + "';", null);
							if (c.moveToFirst()) {
								do {
									id = c.getInt(0);
								} while (c.moveToNext());
							}
							c.close();
							db.begin();
							if (id == 0) {
								//El registro de fecha no existe, se crea
								db.insertar("INSERT INTO HORAS (fecha, horasExtra, razon, notas) " + "VALUES ('" + fecha + "', " + horas + ", " + razon + ",'" + notas + "');");
							} else {
								//El registro de fecha existe, se edita
								Cursor cHoras = db.consulta("SELECT horasExtra, razon FROM HORAS WHERE id = " + id + ";", null);
								if (cHoras.moveToFirst()) {
									do {
										double horasReg = cHoras.getDouble(0);
										double razonReg = cHoras.getDouble(1);
										if (razonReg == 0) {
											horasReg = -horasReg;
										}
										if (razon == 0) {
											horas = -horas;
										}
										horas = horas + horasReg;
										if (horas >= 0) razon = 1;
										else {
											razon = 0;
											horas = -horas;
										}
										db.insertar("UPDATE HORAS SET horasExtra = " + horas + ", razon = " + razon + ", notas = '" + notas + "' WHERE id = " + id + ";");
										if (horas == 0) {
											//El dia queda neutralizado, se borra el registro
											db.insertar("DELETE FROM HORAS WHERE fecha = '" + fecha + "';");
										}
									} while (cHoras.moveToNext());
								}
								cHoras.close();
							}
							db.commit();
						} catch (Exception e) {
							e.printStackTrace();
							db.rollback();
						} finally {
							db.rollback();
							db.cerrar();
						}
						customDialog.dismiss();
						Toast.makeText(getActivity().getBaseContext(), "Horas añadidas correctamente", Toast.LENGTH_SHORT).show();
						RellenarLista();
						listaHoras.clearChoices();
						listaHoras.requestLayout();
						btnBorrar.setVisibility(View.INVISIBLE);
						btnEditar.setVisibility(View.INVISIBLE);
					}
				});

				((com.gc.materialdesign.views.ButtonFlat) customDialog.findViewById(R.id.btnCancelarDialogMas)).setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View view) {
						customDialog.dismiss();
					}
				});

				customDialog.show();
			}
		});

		btnBorrar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// con este tema personalizado evitamos los bordes por defecto
				customDialog = new Dialog(getActivity(), R.style.Theme_Dialog_Translucent);
				//deshabilitamos el t�tulo por defecto
				customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				//obligamos al usuario a pulsar los botones para cerrarlo
				customDialog.setCancelable(false);
				//establecemos el contenido de nuestro dialog
				customDialog.setContentView(R.layout.dialog_eliminar);

				((com.gc.materialdesign.views.ButtonFlat) customDialog.findViewById(R.id.btnAceptarDialog)).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						ObjetoLista linea = (ObjetoLista) listaHoras.getItemAtPosition(listaHoras.getCheckedItemPosition());
						String fecha = linea.getFecha();
						try {
							db.abrir();
							db.begin();
							db.insertar("DELETE FROM HORAS WHERE fecha = '" + fecha + "';");
							db.commit();
						} catch (Exception e) {
							e.printStackTrace();
							db.rollback();
						} finally {
							db.rollback();
							db.cerrar();
						}
						customDialog.dismiss();
						Toast.makeText(getActivity().getBaseContext(), "Horas eliminadas correctamente", Toast.LENGTH_SHORT).show();

						RellenarLista();
						listaHoras.clearChoices();
						listaHoras.requestLayout();
						btnBorrar.setVisibility(View.INVISIBLE);
						btnEditar.setVisibility(View.INVISIBLE);
					}
				});

				((com.gc.materialdesign.views.ButtonFlat) customDialog.findViewById(R.id.btnCancelarDialog)).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						customDialog.dismiss();

					}
				});
				customDialog.show();

			}
		});

		btnEditar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ObjetoLista linea = (ObjetoLista) listaHoras.getItemAtPosition(listaHoras.getCheckedItemPosition());
				final String fecha = linea.getFecha();
				// con este tema personalizado evitamos los bordes por defecto
				customDialog = new Dialog(getActivity(), R.style.Theme_Dialog_Translucent);
				//deshabilitamos el t�tulo por defecto
				customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				//obligamos al usuario a pulsar los botones para cerrarlo
				customDialog.setCancelable(false);
				//establecemos el contenido de nuestro dialog
				customDialog.setContentView(R.layout.dialog_editar_registro);

				final TextView txtTituloEditar = (TextView) customDialog.findViewById(R.id.txtTituloEditar);
				final EditText txtHorasEditar = (EditText) customDialog.findViewById(R.id.txtHorasExtraEditar);
				final EditText txtNotasEditar = (EditText) customDialog.findViewById(R.id.txtHorasExtraNotasEditar);
				final RadioButton rbMasEditar = (RadioButton) customDialog.findViewById(R.id.rbMasEditar);
				final RadioButton rbMenosEditar = (RadioButton) customDialog.findViewById(R.id.rbMenosEditar);

				txtTituloEditar.setText("Editar Horas (" + fecha + ")");

				//Rellenar los cuadros de texto con los datos del registro seleccionado
				try {
					db.abrir();
					int id = 0;
					Cursor c = db.consulta("SELECT id FROM HORAS WHERE fecha = '" + fecha + "';", null);
					if (c.moveToFirst()){
						do {
							id = c.getInt(0);
						} while (c.moveToNext());
					}
					c.close();
					double horas = 0;
					int razon = -1;
					String notas = "";
					Cursor cc = db.consulta("SELECT horasExtra, razon, notas FROM HORAS WHERE id = " + id + ";", null);
					if (cc.moveToFirst()){
						do {
							horas = cc.getDouble(0);
							razon = cc.getInt(1);
							notas = cc.getString(2);
						} while (cc.moveToNext());
					}
					cc.close();
					txtHorasEditar.setText(Double.toString(horas));
					txtNotasEditar.setText(notas);
					if (razon == 0){
						rbMenosEditar.setChecked(true);
					} else {
						rbMasEditar.setChecked(true);
					}
					db.cerrar();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					db.cerrar();
				}

				((com.gc.materialdesign.views.ButtonFlat) customDialog.findViewById(R.id.btnAceptarDialogEditar)).setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View view) {
						if (txtHorasEditar.getText() == null) return;
						if (rbMasEditar.isChecked() == false && rbMenosEditar.isChecked() == false) return;
						try {
							db.abrir();
							int id = 0;
							Cursor c = db.consulta("SELECT id FROM HORAS WHERE fecha = '" + fecha + "';", null);
							if (c.moveToFirst()){
								do {
									id = c.getInt(0);
								} while (c.moveToNext());
							}
							c.close();
							double horas = Double.parseDouble(txtHorasEditar.getText().toString());
							String notas = "";
							if (txtNotasEditar.getText() != null) notas = txtNotasEditar.getText().toString();
							int razon = -1;
							if (rbMasEditar.isChecked()) razon = 1;
							else razon = 0;
							db.begin();
							db.insertar("UPDATE HORAS set horasExtra = " + horas + ", razon = " + razon + ", notas = '" + notas + "' WHERE id = " + id + ";");
							db.commit();
						} catch (Exception e) {
							e.printStackTrace();
							db.rollback();
						} finally {
							db.rollback();
							db.cerrar();
						}
						customDialog.dismiss();
						Toast.makeText(getActivity().getBaseContext(), "Horas añadidas correctamente", Toast.LENGTH_SHORT).show();

						RellenarLista();
						listaHoras.clearChoices();
						listaHoras.requestLayout();
						btnBorrar.setVisibility(View.INVISIBLE);
						btnEditar.setVisibility(View.INVISIBLE);
					}
				});

				((com.gc.materialdesign.views.ButtonFlat) customDialog.findViewById(R.id.btnCancelarDialogEditar)).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						customDialog.dismiss();

					}
				});

				customDialog.show();
			}
		});

		RellenarLista();

		btnMenuExtras.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity) getActivity()).openDrawer();
			}
		});
		return rootView;

	}

	public void RellenarLista(){
		db = new AdaptadorBD(getActivity().getBaseContext());
		//Rellenar listado de horas extra
		ArrayList<ObjetoLista> items = new ArrayList<>();
		try {
			db.abrir();
			Cursor cF = db.consulta("SELECT fecha, horasExtra, razon, notas FROM HORAS ORDER BY date(fecha);", null);
			if (cF.moveToFirst()){
				do {
					String status;
					if (cF.getInt(2) == 0) status = "(—)";
					else status = "(+)";
					String item = cF.getString(1) + " horas " + status;
					items.add(new ObjetoLista(cF.getString(0),item, cF.getString(3)));
				} while (cF.moveToNext());
			}
			cF.close();
			// Apply the adapter to the spinner
			AdapterLista adapter = new AdapterLista(getActivity(), items);
			listaHoras.setAdapter(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.cerrar();
		}
		consultarHorasExtra();

	}

	private void consultarHorasExtra(){
		db = new AdaptadorBD(getActivity().getBaseContext());
		try {
			double total = 0;
			String cad = "";
			db.abrir();
			Cursor c = db.consulta(
					"SELECT (SELECT IFNULL((SELECT SUM(horasExtra) FROM HORAS WHERE razon = 1),0)) - (SELECT IFNULL((SELECT SUM(horasExtra) FROM HORAS WHERE razon = 0),0))",null);
			if (c.moveToFirst()){
				do {
					total = c.getDouble(0);
				} while (c.moveToNext());
			}
			c.close();

			if (total > 0) {
				cad = Double.toString(total) + " hora(s) a favor";
			} else if (total < 0) {
				total = -total;
				cad = Double.toString(total) + " hora(s) en contra";
			} else if (total == 0) {
				cad = "Sin horas";
			}
			textoTotal.setText("Total: " + cad);
			db.cerrar();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.cerrar();
		}
	}

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment((TextView)v);
		newFragment.show(getFragmentManager(), "datePicker");
	}
}

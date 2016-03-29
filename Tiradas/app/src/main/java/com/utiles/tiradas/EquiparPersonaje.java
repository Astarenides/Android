package com.utiles.tiradas;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class EquiparPersonaje extends Activity {
	
	private Button btnGuardar;
	AdaptadorBD db;
	private Spinner desplegableArma;
	private Spinner desplegableArmadura;
	private Spinner desplegableBonos;
	private Spinner desplegableBonosEscudo;
	private Spinner desplegableOffhand;
	private Spinner desplegablePersonajes;
	private RadioGroup rgOff;

	public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.equiparpersonaje);
    
    desplegablePersonajes = ((Spinner)findViewById(R.id.sipnnerNombreEquiparPersonaje));
    desplegableArmadura = ((Spinner)findViewById(R.id.spinnerEquiparArmadura));
    desplegableBonos = ((Spinner)findViewById(R.id.spinnerBonos));
    desplegableArma = ((Spinner)findViewById(R.id.spinnerEquiparArma));
    desplegableOffhand = ((Spinner)findViewById(R.id.spinnerEquiparOffhand));
    desplegableBonosEscudo = ((Spinner)findViewById(R.id.spinnerBonosEscudo));
    rgOff = ((RadioGroup)findViewById(R.id.rgOff));
    btnGuardar = ((Button)findViewById(R.id.btnGuardarEquiparPersonaje));
    desplegableBonosEscudo.setEnabled(false);
    
    db = new AdaptadorBD(getBaseContext());
    ArrayList<String> listaPersonajes = new ArrayList<String>();
    listaPersonajes.add("Seleccionar");
    try {
    	db.abrir();
    	Cursor c = this.db.consulta("SELECT nombre FROM PERSONAJES ORDER BY nombre;", null);
    	if (c.moveToFirst()) {
    		do {
    			listaPersonajes.add(c.getString(0));
    		} while (c.moveToNext());
    	}
    	c.close();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.cerrar();
	}
    ArrayAdapter<String> adapterPersonajes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaPersonajes);
    adapterPersonajes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    desplegablePersonajes.setAdapter(adapterPersonajes);
    
    ArrayList<String> listaArmas = new ArrayList<String>();
    listaArmas.add("Seleccionar");
    try {
    	db.abrir();
    	Cursor c1 = this.db.consulta("SELECT nombre FROM armas ORDER BY nombre;", null);
    	if (c1.moveToFirst()) {
    		do {
    			listaArmas.add(c1.getString(0));
    		} while (c1.moveToNext());
    	}
    	c1.close();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.cerrar();
	}
    ArrayAdapter<String> adapterArmas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaArmas);
    adapterArmas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    desplegableArma.setAdapter(adapterArmas);
    
    ArrayList<String> listaArmaduras = new ArrayList<String>();
    listaArmaduras.add("Seleccionar");
    try {
    	db.abrir();
    	Cursor c2 = this.db.consulta("SELECT nombre FROM armaduras WHERE bonoAC > 0 ORDER BY nombre;", null);
    	if (c2.moveToFirst()) {
    		do {
    			listaArmaduras.add(c2.getString(0));
    		} while (c2.moveToNext());
    	}
    	c2.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      db.cerrar();
    }
    ArrayAdapter<String> adapterArmaduras = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaArmaduras);
    adapterArmaduras.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    desplegableArmadura.setAdapter(adapterArmaduras);
    
    ArrayList<String> listaBonos = new ArrayList<String>();
    listaBonos.add("Seleccionar");
    for (int i = 0; i < 6; i++) {
    	listaBonos.add(Integer.toString(i));
    }
    ArrayAdapter<String> adapterBonos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaBonos);
    adapterBonos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    desplegableBonos.setAdapter(adapterBonos);
    desplegableBonosEscudo.setAdapter(adapterBonos);
    
    ArrayList<String> listaOff = new ArrayList<String>();
    listaOff.add("Elegir");
    ArrayAdapter<String> adapterOff = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaOff);
    adapterOff.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    desplegableOffhand.setAdapter(adapterOff);
    
    rgOff.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        public void onCheckedChanged(RadioGroup radioGroup, int paramAnonymousInt) {
        	int i = rgOff.getCheckedRadioButtonId();
        	if (i == -1) {
        		ArrayList<String> listaOff = new ArrayList<String>();
        	    listaOff.add("Elegir");
        	    ArrayAdapter<String> adapterOff = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, listaOff);
        	    adapterOff.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        	    desplegableOffhand.setAdapter(adapterOff);
        	} else if (i == R.id.radioOffNinguno) {
        		ArrayList<String> listaOffNinguno = new ArrayList<String>();
        		listaOffNinguno.add("NINGUNO");
        		ArrayAdapter<String> adapterOff = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, listaOffNinguno);
        		adapterOff.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        		desplegableOffhand.setAdapter(adapterOff);
        		desplegableBonosEscudo.setEnabled(false);
        	} else if (i == R.id.radioOffArma) {
        		ArrayList<String> listaArmaOff = new ArrayList<String>();
        		listaArmaOff.add("Seleccionar");
        		try {
        			db = new AdaptadorBD(EquiparPersonaje.this.getBaseContext());
        			db.abrir();
        			Cursor c = EquiparPersonaje.this.db.consulta("SELECT nombre FROM armas ORDER BY nombre;", null);
        			if (c.moveToFirst()) {
        				do {
        					listaArmaOff.add(c.getString(0));
        				} while (c.moveToNext());
        			}
        			c.close();
        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        			db.cerrar();
        		}
        		
        		ArrayAdapter<String> adapterArmaOff = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, listaArmaOff);
        		adapterArmaOff.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        		desplegableOffhand.setAdapter(adapterArmaOff);
        		desplegableBonosEscudo.setEnabled(false);
        	} else if (i == R.id.radioOffEscudo) {
        		ArrayList<String> listaEscudoOff = new ArrayList<String>();
                listaEscudoOff.add("Seleccionar");
                try {
                	db = new AdaptadorBD(EquiparPersonaje.this.getBaseContext());
                	db.abrir();
                	Cursor c = db.consulta("SELECT nombre FROM armaduras WHERE bonoEscudo > 0 ORDER BY nombre;", null);
                	if (c.moveToFirst()) {
                		do {
                			listaEscudoOff.add(c.getString(0));
                		} while (c.moveToNext());
                	}
                	c.close();
                } catch (Exception e) {
                	e.printStackTrace();
                } finally {
                  db.cerrar();
                }
                ArrayAdapter<String> adapterEscudoOff = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, listaEscudoOff);
                adapterEscudoOff.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                desplegableOffhand.setAdapter(adapterEscudoOff);
                desplegableBonosEscudo.setEnabled(true);
        	}
        }
    });
    
    this.rgOff.clearCheck();
    this.desplegablePersonajes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    	@SuppressWarnings({ "unchecked", "rawtypes" })
		public void onItemSelected(AdapterView<?> spinner, View v, int position, long id) {
            
    		try {
    			db = new AdaptadorBD(EquiparPersonaje.this.getBaseContext());
                db.abrir();
                String personaje = spinner.getSelectedItem().toString();
                Cursor c = EquiparPersonaje.this.db.consulta("SELECT idPersonaje FROM PERSONAJES WHERE nombre = '" + personaje + "';", null);
                int idPersonaje = 0;
                if (c.moveToFirst()) {
                	do {
                		idPersonaje = c.getInt(0);
                	} while (c.moveToNext());
                }
                c.close();
                desplegableArma.setSelection(0);
                desplegableArmadura.setSelection(0);
                desplegableBonos.setSelection(0);
                desplegableOffhand.setSelection(0);
                desplegableBonosEscudo.setSelection(0);
                desplegableBonosEscudo.setEnabled(false);
                
                Cursor c1 = db.consulta("SELECT armadura, mainhand, offEscudo, offArma FROM PERSONAJES_EQUIPO WHERE idPersonaje = " + idPersonaje + ";", null);
                if (c1.moveToFirst()) {
                	do {
                		int idArmadura = c1.getInt(0);
                        int idArma = c1.getInt(1);
                        int idOffEscudo = c1.getInt(2);
                        int idOffArma = c1.getInt(3);
                        if (idArmadura == 0){
                        	desplegableArmadura.setSelection(0);
                        	desplegableBonos.setSelection(0);
                        } else {
                        	//Buscar nombre de armadura
							Cursor c11 = db.consulta("SELECT nombre, bonoAC FROM armaduras WHERE idArmadura = " + idArmadura + ";", null);
							c11.moveToFirst();
							String nombreArmadura = c11.getString(0);
							int bonoAC = c11.getInt(1);
							c11.close();
							int bono = 0;
							//Calcular el bonificador de la armadura restando la armadura normal a la que tiene el personaje
							//Obtener efecto de armadura del personaje
							Cursor ccc = db.consulta(
									"SELECT EFECTOS_STATS.valor FROM EFECTOS_STATS, EFECTOS " + 
									"WHERE EFECTOS_STATS.idEFecto = EFECTOS.idEfecto AND " + 
									"EFECTOS.nombre = 'Armadura " + personaje + "';", null);
							if (ccc.moveToFirst()){
								int bonoActual = ccc.getInt(0);
								bono = bonoActual - bonoAC;
							}
							ccc.close();
							//Seleccionar armadura
							int spinnerpositionArmadura = 0;
							ArrayAdapter<String> adapterArmadura;
							adapterArmadura = (ArrayAdapter<String>) desplegableArmadura.getAdapter(); //cast to an ArrayAdapter
							spinnerpositionArmadura = adapterArmadura.getPosition(nombreArmadura);
							desplegableArmadura.setSelection(spinnerpositionArmadura);
							//Seleccionar bonos
							int spinnerpositionBono = 0;
							ArrayAdapter<String> adapterBonos;
							adapterBonos = (ArrayAdapter<String>) desplegableBonos.getAdapter(); //cast to an ArrayAdapter
							spinnerpositionBono = adapterBonos.getPosition(Integer.toString(bono));
							desplegableBonos.setSelection(spinnerpositionBono);
                        }
                        if (idArma == 0) {
                        	desplegableArma.setSelection(0);
                        } else {
                        	Cursor c2 = db.consulta("SELECT nombre FROM armas WHERE idArma = " + idArma + ";", null);
                            c2.moveToFirst();
                            String arma = c2.getString(0);
                            c2.close();
                            int posArma = ((ArrayAdapter)EquiparPersonaje.this.desplegableArma.getAdapter()).getPosition(arma);
                            desplegableArma.setSelection(posArma);
                        }
                        if (idOffEscudo > 0){
                        	rgOff.check(R.id.radioOffEscudo);
                            Cursor c3 = EquiparPersonaje.this.db.consulta("SELECT nombre, bonoEscudo FROM armaduras WHERE idArmadura = " + idOffEscudo + ";", null);
                            c3.moveToFirst();
                            String escudo = c3.getString(0);
                            int acEscudo = c3.getInt(1);
                            c3.close();
                            int bonoEscudo = 0;
                            Cursor c4 = EquiparPersonaje.this.db.consulta(
                            		"SELECT EFECTOS_STATS.valor FROM EFECTOS_STATS, EFECTOS " + 
                            		"WHERE EFECTOS_STATS.idEFecto = EFECTOS.idEfecto AND " + 
                            		"EFECTOS.nombre = 'Escudo " + personaje + "';", null);
                            if(c4.moveToFirst()){
                            	int bonoActual = c4.getInt(0);
                            	bonoEscudo = bonoActual - acEscudo;
                            }
                            c4.close();
                            //Seleccionar escudo
							int spinnerpositionEscudo = 0;
							ArrayAdapter<String> adapterEscudo = (ArrayAdapter<String>) desplegableOffhand.getAdapter(); //cast to an ArrayAdapter
							spinnerpositionEscudo = adapterEscudo.getPosition(escudo);
							desplegableOffhand.setSelection(spinnerpositionEscudo);
							//Seleccionar bonos
							int spinnerpositionBonoEscudo = 0;
							ArrayAdapter<String> adapterBonosEscudo;
							adapterBonosEscudo = (ArrayAdapter<String>) desplegableBonosEscudo.getAdapter(); //cast to an ArrayAdapter
							spinnerpositionBonoEscudo = adapterBonosEscudo.getPosition(Integer.toString(bonoEscudo));
							desplegableBonosEscudo.setSelection(spinnerpositionBonoEscudo);
                        }
                        if (idOffArma > 0) {
                        	rgOff.check(R.id.radioOffArma);
							//Buscar nombre de arma
							Cursor c14 = db.consulta("SELECT nombre FROM armas WHERE idArma = " + idOffArma + ";", null);
							c14.moveToFirst();
							String nombreArmaOff = c14.getString(0);
							c14.close();
							//Seleccionar arma
							int spinnerpositionArmaOff = 0;
							ArrayAdapter<String> adapterArmaOff;
							adapterArmaOff = (ArrayAdapter<String>) desplegableOffhand.getAdapter(); //cast to an ArrayAdapter
							spinnerpositionArmaOff = adapterArmaOff.getPosition(nombreArmaOff);
							desplegableOffhand.setSelection(spinnerpositionArmaOff);
                        }
                        if (idOffEscudo == 0 && idOffArma == 0){
							rgOff.check(R.id.radioOffNinguno);
						}
                	} while (c1.moveToNext());
                }
                c1.close();
    		} catch (Exception e) {
    			e.printStackTrace();
    		} finally {
    			db.cerrar();
    		}
              
    	}
          
          
    	public void onNothingSelected(AdapterView<?> paramAnonymousAdapterView) {}
    	
    });
    
    btnGuardar.setOnClickListener(new View.OnClickListener() {
    	
    	public void onClick(View paramAnonymousView) {
    		if ((desplegablePersonajes.getSelectedItem() == null)  || (desplegablePersonajes.getSelectedItemId() == 0)) {
            	Toast.makeText(EquiparPersonaje.this.getBaseContext(), "Seleccione personaje", Toast.LENGTH_SHORT).show();
            	return;
            }
    		try {
    			//Guardar nombre del personaje
                String personaje = desplegablePersonajes.getSelectedItem().toString();
				db.abrir();
				db.begin();
				//ID del personaje
				int idPersonaje = 0;
				Cursor c = db.consulta("SELECT idPersonaje FROM PERSONAJES WHERE nombre = '" + personaje + "';", null);
				c.moveToFirst();
				idPersonaje = c.getInt(0);
				c.close();
				//Armadura
				int idArmadura = 0;
				int acArmadura = 0;
				int idEfectoArmadura = 0;
				String nombreEfectoArmadura = "Armadura " + personaje;
				Cursor arm = db.consulta("SELECT idEfecto FROM EFECTOS WHERE nombre = '" + nombreEfectoArmadura + "';", null);
				if (arm.moveToFirst()){
					do {
						idEfectoArmadura = arm.getInt(0);
					} while (arm.moveToNext());
				}
				arm.close();
				if (desplegableArmadura.getSelectedItemPosition() > 0){
					String armaduraSel = desplegableArmadura.getSelectedItem().toString();
					//Encontrar id de la armadura seleccionara
					Cursor c1 = db.consulta("SELECT idArmadura, bonoAC FROM ARMADURAS WHERE nombre = '" + armaduraSel + "';", null);
					c1.moveToFirst();
					idArmadura = c1.getInt(0);
					acArmadura = c1.getInt(1);
					c1.close();
					int bonoArmadura = 0;
					if (desplegableBonos.getSelectedItemId() > 0){
						bonoArmadura = Integer.parseInt(desplegableBonos.getSelectedItem().toString());
					}
					acArmadura += bonoArmadura;
					if (idEfectoArmadura > 0){
						//Actualizar el valor del efecto
						db.insertar("UPDATE EFECTOS_STATS SET valor = " + acArmadura + " WHERE idEfecto = " + idEfectoArmadura + ";");
						//Comprobar si el personaje ya tiene asignado ese efecto
						Cursor c11 = db.consulta(
								"SELECT COUNT(idPersonaje) FROM PERSONAJES_EFECTOS " + 
								"WHERE idPersonaje = " + idPersonaje + " AND idEfecto = " + idEfectoArmadura + ";", null);
						c11.moveToFirst();
						if (c11.getInt(0) == 0){
							//No lo tiene, se le asigna
							db.insertar(
									"INSERT INTO PERSONAJES_EFECTOS (idPersonaje, idEfecto, afectado) VALUES " + 
									"(" + idPersonaje + ", " + idEfectoArmadura + ", 1);");
						} else {
							//Si lo tiene, se actualiza el estado
							db.insertar(
									"UPDATE PERSONAJES_EFECTOS SET afectado = 1 " + 
									"WHERE idPersonaje = " + idPersonaje + " AND idEfecto = " + idEfectoArmadura + ";");
						}
						c11.close();
					} else {
						//Crear efecto "Armadura Nombrepersonaje"
						db.insertar("INSERT INTO EFECTOS (nombre) VALUES ('" + nombreEfectoArmadura + "');");
						//Conseguir el id del efecto recien creado
						Cursor c12 = db.consulta("SELECT idEfecto FROM EFECTOS WHERE rowid = last_insert_rowid();", null);
						c12.moveToFirst(); 
						int idEfecto = c12.getInt(0); 
						c12.close();
						//Crear asociacion de stats, tipo y valor del efecto recien creado
						db.insertar("INSERT INTO EFECTOS_STATS (idEfecto, idStat, idTipo, valor) VALUES "
								+ "(" + idEfecto + ", " + 7 + ", " + 2 + ", " + acArmadura + ");");
						//Asignar el efecto recien creado al personaje
						db.insertar(
								"INSERT INTO PERSONAJES_EFECTOS (idPersonaje, idEfecto, afectado) VALUES " + 
								"(" + idPersonaje + ", " + idEfecto + ", 1);");
					}
				} else {
					if (idEfectoArmadura > 0){
						//Desactivar el efecto de "Armadura nombrepersonaje" en caso de que exista
						db.insertar(
								"UPDATE PERSONAJES_EFECTOS SET afectado = 0 WHERE idEfecto = " + idEfectoArmadura + 
								" AND idPersonaje = " + idPersonaje + ";");
					}
				}
				//Arma
				int idArma = 0;
				if(desplegableArma.getSelectedItemPosition() > 0){
					String armaSel = desplegableArma.getSelectedItem().toString();
					//Encontrar id del arma seleccionada
					Cursor c2 = db.consulta("SELECT idArma FROM ARMAS WHERE nombre = '" + armaSel + "';", null);
					c2.moveToFirst();
					idArma = c2.getInt(0);
					c2.close();
				}
				//Offhand
				int idOffArma = 0;
				int idOffEscudo = 0;
				int acEscudo = 0;
				int idEfectoEscudo = 0;
				String nombreEfectoEscudo = "Escudo " + personaje;
				Cursor esc = db.consulta("SELECT idEfecto FROM EFECTOS WHERE nombre = '" + nombreEfectoEscudo + "';", null);
				if (esc.moveToFirst()){
					do {
						idEfectoEscudo = esc.getInt(0);
					} while (esc.moveToNext());
				}
				esc.close();
				int id = rgOff.getCheckedRadioButtonId();
				if (id == R.id.radioOffArma){
					if (desplegableOffhand.getSelectedItemPosition() == 0){
						Toast.makeText(getBaseContext(), "Seleccione un arma valida", Toast.LENGTH_SHORT).show();
					} else {
						String armaOffSel = desplegableOffhand.getSelectedItem().toString();
						//Encontrar id del arma seleccionada
						Cursor c3 = db.consulta("SELECT idArma FROM ARMAS WHERE nombre = '" + armaOffSel + "';", null);
						c3.moveToFirst();
						idOffArma = c3.getInt(0);
						c3.close();
						//Desactivar el efecto de "Escudo nombrepersonaje" en caso de que exista
						db.insertar(
								"UPDATE PERSONAJES_EFECTOS SET afectado = 0 WHERE idEfecto = " + idEfectoEscudo + 
								" AND idPersonaje = " + idPersonaje + ";");
					}
				} else if (id == R.id.radioOffEscudo) {
					if (desplegableOffhand.getSelectedItemPosition() == 0){
						Toast.makeText(getBaseContext(), "Seleccione un escudo valido", Toast.LENGTH_SHORT).show();
					} else {
						String escudoOffSel = desplegableOffhand.getSelectedItem().toString();
						//Encontrar id del arma seleccionada
						Cursor c4 = db.consulta("SELECT idArmadura, bonoEscudo FROM ARMADURAS WHERE nombre = '" + escudoOffSel + "';", null);
						c4.moveToFirst();
						idOffEscudo = c4.getInt(0);
						acEscudo = c4.getInt(1);
						c4.close();
						int bonoEscudo = 0;
						if (desplegableBonosEscudo.getSelectedItemId() > 0){
							bonoEscudo = Integer.parseInt(desplegableBonosEscudo.getSelectedItem().toString());
						}
						acEscudo += bonoEscudo;
						if (idEfectoEscudo > 0){
							//Actualizar el valor del efecto
							db.insertar("UPDATE EFECTOS_STATS SET valor = " + acEscudo + " WHERE idEfecto = " + idEfectoEscudo + ";");
							//Comprobar si el personaje ya tiene asignado ese efecto
							Cursor c73 = db.consulta(
									"SELECT COUNT(idPersonaje) FROM PERSONAJES_EFECTOS " + 
									"WHERE idPersonaje = " + idPersonaje + " AND idEfecto = " + idEfectoEscudo + ";", null);
							c73.moveToFirst();
							if (c73.getInt(0) == 0){
								//No lo tiene, se le asigna
								db.insertar(
										"INSERT INTO PERSONAJES_EFECTOS (idPersonaje, idEfecto, afectado) VALUES " + 
										"(" + idPersonaje + ", " + idEfectoEscudo + ", 1);");
							} else {
								//Si lo tiene, se actualiza el estado
								db.insertar(
										"UPDATE PERSONAJES_EFECTOS SET afectado = 1 " + 
										"WHERE idPersonaje = " + idPersonaje + " AND idEfecto = " + idEfectoEscudo + ";");
							}
							c73.close();
						} else {
							//Crear efecto "Escudo Nombrepersonaje"
							db.insertar("INSERT INTO EFECTOS (nombre) VALUES ('" + nombreEfectoEscudo + "');");
							//Conseguir el id del efecto recien creado
							Cursor c71 = db.consulta("SELECT idEfecto FROM EFECTOS WHERE rowid = last_insert_rowid();", null);
							c71.moveToFirst(); 
							int idEfecto = c71.getInt(0); 
							c71.close();
							//Crear asociacion de stats, tipo y valor del efecto recien creado
							db.insertar("INSERT INTO EFECTOS_STATS (idEfecto, idStat, idTipo, valor) VALUES "
									+ "(" + idEfecto + ", " + 7 + ", " + 16 + ", " + acEscudo + ");");
							//Asignar el efecto recien creado al personaje
							db.insertar(
									"INSERT INTO PERSONAJES_EFECTOS (idPersonaje, idEfecto, afectado) VALUES " + 
									"(" + idPersonaje + ", " + idEfecto + ", 1);");
						}
					}
				} else if (id == R.id.radioOffNinguno){
					if (idEfectoEscudo > 0){
						db.insertar(
							"UPDATE PERSONAJES_EFECTOS SET afectado = 0 WHERE idEfecto = " + idEfectoEscudo + 
							" AND idPersonaje = " + idPersonaje + ";");
					}
				} else {
					if (idEfectoEscudo > 0){
						db.insertar(
							"UPDATE PERSONAJES_EFECTOS SET afectado = 0 WHERE idEfecto = " + idEfectoEscudo + 
							" AND idPersonaje = " + idPersonaje + ";");
					}
				}
				//Encontrar si hay linea de registro de equipo para el personaje
				Cursor c5 = db.consulta("SELECT COUNT(idPersonaje) FROM PERSONAJES_EQUIPO WHERE idPersonaje = " + idPersonaje + ";", null);
				int existe = 0;
				if (c5.moveToFirst()){
					do {
						 existe = c5.getInt(0);
					} while (c5.moveToNext());
				}
				c5.close();
				String mensaje = "";
				if (existe == 0){
					//No existe, se crea
					db.insertar("INSERT INTO PERSONAJES_EQUIPO (idPersonaje, armadura, mainhand, offEscudo, offArma) VALUES " + 
								"(" + idPersonaje + ", " + idArmadura + ", " + idArma + ", " + idOffEscudo + ", " + idOffArma + ");");
					mensaje = "Equipacion creada con exito";
				} else {
					//Ya existe, se actualiza
					db.insertar(
							"UPDATE PERSONAJES_EQUIPO SET armadura = " + idArmadura + ", " +  
							"mainhand = " + idArma + ", offEscudo = " + idOffEscudo + ", offArma = " + idOffArma + " " +   
							"WHERE idPersonaje = " + idPersonaje + ";");
					mensaje = "Equipacion editada con exito";
				}
				db.commit();
				Toast.makeText(getBaseContext(), mensaje, Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				e.printStackTrace();
				db.rollback();
			} finally {
				db.rollback();
				db.cerrar();
				desplegablePersonajes.setSelection(0);
			}
    		
    	}
    });
    
  }
}
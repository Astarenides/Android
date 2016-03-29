package com.utiles.tiradas;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class EstadisticasPersonaje extends Activity{
	
	private Spinner spinnerPersonajesEstadisticas;
	private TextView txtArmaduraTotalEstadisticas;
	private TextView txtArmaduraToqueEstadisticas;
	private TextView txtArmaduraDesprevenidoEstadisticas;
	private TextView txtArmaduraSinEscudoEstadisticas;
	private TextView txtFortalezaEstadisticasPersonaje;
	private TextView txtReflejosEstadisticasPersonaje;
	private TextView txtVoluntadEstadisticasPersonaje;
	private TextView txtAtaqueCCEstadisticas;
	private TextView txtAtaqueDistanciaEstadisticas;
	private TextView txtBMCEstadisticas;
	private TextView txtDMCEstadisticas;
	private TableLayout cabeceraCaracteristicas;
	private TableLayout tablaCaracteristicas;
	private TableLayout cabeceraEstadisticas;
	private TableLayout tablaEstadisticas;
	private TableRow.LayoutParams layoutM;
	private TableRow.LayoutParams layoutL;
	private TableRow.LayoutParams layoutS;
	private static final float SIZE_G = 130f;
	private static final float SIZE_S = 50f;
	private static final float SIZE_M = 68f;
	AdaptadorBD db;
	
	public void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.estadisticaspersonaje);
		
		Resources res = getResources();
		 
		TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
		tabs.setup();
		 
		TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
		spec.setContent(R.id.tabStats);
		spec.setIndicator("RESUMEN", res.getDrawable(android.R.drawable.ic_btn_speak_now));
		tabs.addTab(spec);
		
		spec=tabs.newTabSpec("mitab2");
		spec.setContent(R.id.tabAtaque);
		spec.setIndicator("ATAQUE", res.getDrawable(android.R.drawable.ic_btn_speak_now));
		tabs.addTab(spec);
		 
		spec=tabs.newTabSpec("mitab3");
		spec.setContent(R.id.tabDefensas);
		spec.setIndicator("DEFENSA", res.getDrawable(android.R.drawable.ic_dialog_map));
		tabs.addTab(spec);
		 
		tabs.setCurrentTab(0);
		
		/* INICIALIZACION DE COMPONENTES DE LA ACTIVITY */
		
		spinnerPersonajesEstadisticas = (Spinner) findViewById(R.id.spinnerPersonajesEstadisticas);
		txtArmaduraTotalEstadisticas = (TextView) findViewById(R.id.txtArmaduraTotalEstadisticas);
		txtArmaduraToqueEstadisticas = (TextView) findViewById(R.id.txtArmaduraToqueEstadisticas);
		txtArmaduraDesprevenidoEstadisticas = (TextView) findViewById(R.id.txtArmaduraDesprevenidoEstadisticas);
		txtArmaduraSinEscudoEstadisticas = (TextView) findViewById(R.id.txtArmaduraSinEscudoEstadisticas);
		txtFortalezaEstadisticasPersonaje = (TextView) findViewById(R.id.txtFortalezaEstadisticasPersonaje);
		txtReflejosEstadisticasPersonaje = (TextView) findViewById(R.id.txtReflejosEstadisticasPersonaje);
		txtVoluntadEstadisticasPersonaje = (TextView) findViewById(R.id.txtVoluntadEstadisticasPersonaje);
		txtAtaqueCCEstadisticas = (TextView) findViewById(R.id.txtAtaqueCCEstadisticas);
		txtAtaqueDistanciaEstadisticas = (TextView) findViewById(R.id.txtAtaqueDistanciaEstadisticas);
		txtBMCEstadisticas = (TextView) findViewById(R.id.txtBMCEstadisticas);
		txtDMCEstadisticas = (TextView) findViewById(R.id.txtDMCEstadisticas);
		
		// Get the screen's density scale
		float scale = getResources().getDisplayMetrics().density;
		int escalaPixelL;
		int escalaPixelM;
		int escalaPixelS;
		// Convert the dps to pixels, based on density scale
		//Determine screen size
	    if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {     
	        //Pantalla grande
	    	escalaPixelL = (int) (SIZE_G * scale + 0.5f);
			escalaPixelS = (int) (SIZE_S * scale + 0.5f);
			escalaPixelM = (int) (SIZE_M * scale + 0.5f);
	    }
	    else {     
	        //Pantalla normal
	    	escalaPixelL = (int) (105f * scale + 0.5f);
			escalaPixelS = (int) (30f * scale + 0.5f);
			escalaPixelM = (int) (65f * scale + 0.5f);

	    } 
		
	    tablaCaracteristicas = (TableLayout) findViewById(R.id.tablaStats);
		cabeceraCaracteristicas = (TableLayout) findViewById(R.id.cabeceraStats);
		tablaEstadisticas = (TableLayout) findViewById(R.id.tablaGeneralEstadisticas);
		cabeceraEstadisticas = (TableLayout) findViewById(R.id.cabeceraEstadisticas);
		layoutL = new TableRow.LayoutParams(escalaPixelL, TableRow.LayoutParams.WRAP_CONTENT);
		layoutS = new TableRow.LayoutParams(escalaPixelS, TableRow.LayoutParams.WRAP_CONTENT);
		layoutM = new TableRow.LayoutParams(escalaPixelM, TableRow.LayoutParams.WRAP_CONTENT);
		
		agregarCabeceraCaracteristicas();
		agregarCabeceraEstadisticas();
		
		for (int i=0; i < 6; i++){
			String stat = "";
			switch (i){
			case 0:
				stat = "Fuerza";
				break;
			case 1:
				stat = "Destreza";
				break;
			case 2:
				stat = "Constitución";
				break;
			case 3:
				stat = "Inteligencia";
				break;
			case 4:
				stat = "Sabiduría";
				break;
			case 5:
				stat = "Carisma";
				break;
			}
			insertarFilaVacia(stat);
		}		
				
		db = new AdaptadorBD(getBaseContext());
		List<String> listaPersonajes = new ArrayList<String>();
		listaPersonajes.add("Seleccionar");
		// Obtener Personajes de la tabla
		try {
			db.abrir();
			Cursor c = db.consulta("SELECT nombre FROM PERSONAJES ORDER BY nombre;",null);
			if (c.moveToFirst()) {
				do {
					listaPersonajes.add(c.getString(0));
				} while (c.moveToNext());
			}
			db.cerrar();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.cerrar();
		}
		//Poner lista de personajes en desplegable de razas
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		        android.R.layout.simple_spinner_item, listaPersonajes);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerPersonajesEstadisticas.setAdapter(adapter);
		
		spinnerPersonajesEstadisticas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
				if (spinnerPersonajesEstadisticas.getSelectedItemId() == 0){
					return;
				}
				try {
					db.abrir();
					
		    		//Conseguir id, nombre y clase del personaje
					String nombrePersonaje = parent.getSelectedItem().toString();
					Cursor cId = db.consulta("SELECT idPersonaje FROM PERSONAJES WHERE nombre = '" + nombrePersonaje + "';", null);
					cId.moveToFirst();
					int idPersonaje = cId.getInt(0);
					cId.close();
					List<Integer> clases = new ArrayList<Integer>();
					Cursor clase = db.consulta("SELECT idClase FROM PERSONAJES_CLASES WHERE idPersonaje = " + idPersonaje + ";", null);
					if (clase.moveToFirst()){
						do {
							clases.add(clase.getInt(0));
						} while (clase.moveToNext());
					}
					clase.close();
					
					int armor = 10;
		    		int atkBase = 0;
		    		int saveF = 0;
		    		int saveR = 0;
		    		int saveV = 0;
		    		String dadosDmgMain = "";
		    		String dadosDmgOff= "";
		    		String tipoArma = "";
		    		int dmg = 0;
		    		int modStatDmg = 0;
		    		int idTamaño = 0;
		    		int fuerzaPersonaje = 0;
		    		int destrezaPersonaje = 0;
		    		int consPersonaje = 0;
		    		int intPersonaje = 0;
		    		int sabPersonaje = 0;
		    		int carPersonaje = 0;

		    		/* EFECTOS DEL PERSONAJE */
		    		int AtkBuffs = 0;
		    		int ACBuffs = 0;
		    		int DmgBuffs = 0;
		    		//Ver a que afecta el efecto y aplicarlo a los modificadores correspondientes
		    		List<EfectosPersonaje> lista = new ArrayList<EfectosPersonaje>();
					Cursor cc = db.consulta(
							"SELECT idStat, SUM(Valor) FROM ( " + 
								"SELECT DISTINCT b.valor, a.idStat, a.idTipo FROM " +  
								"(SELECT idPersonaje, a.idEfecto, a.idTipo, a.IdStat, a.valor " + 
								"FROM EFECTOS_STATS a " +  
								"INNER JOIN PERSONAJES_EFECTOS b ON b.idEfecto = a.idEfecto " + 
								"WHERE idPersonaje = " + idPersonaje + " AND afectado = 1 ) a " + 
								"INNER JOIN " +  
								"(SELECT MAX(a.valor) as valor, a.idTipo, a.idStat, a.idTipo " + 
								"FROM EFECTOS_STATS a INNER JOIN " +  
								"(SELECT idPersonaje, a.idEfecto, a.idTipo, a.IdStat, a.valor " + 
								"FROM EFECTOS_STATS a  INNER JOIN PERSONAJES_EFECTOS b ON b.idEfecto = a.idEfecto " + 
								"WHERE idPersonaje = " + idPersonaje + " AND afectado = 1 " +  
								"AND idtipo not in(6,3,19) ) b ON a.idEfecto= b.idEfecto " + 
								"GROUP BY a.idStat,a.idTipo )b ON a.idTipo = b.idTipo " + 
								"AND a.idStat = b.idStat AND a.valor = b.valor " + 
								"union all " + 
								"SELECT SUM(a.valor) as valor, a.IdStat, a.idTipo " + 
								"FROM EFECTOS_STATS a " +  
								"INNER JOIN PERSONAJES_EFECTOS b ON b.idEfecto = a.idEfecto " + 
								"WHERE idPersonaje = " + idPersonaje + " AND afectado = 1 AND idtipo in(6,13,19) " + 
								"GROUP BY idstat, a.idTipo) c GROUP BY idstat", null);
					if (cc.moveToFirst()){
						do {
							lista.add(new EfectosPersonaje(cc.getInt(0), cc.getInt(1)));									
						} while (cc.moveToNext());
					}
					cc.close();
					
					for (int i=0; i < lista.size(); i++){
						EfectosPersonaje ef = lista.get(i);
						int idStat = ef.getStat();
						int valor = ef.getValor();
						switch (idStat){
							case 1:
								//Fuerza
								fuerzaPersonaje += valor;
								break;
							case 2:
								//Destreza
								destrezaPersonaje += valor;
								break;
							case 3:
								//Constitucion
								consPersonaje += valor;
								break;
							case 4:
								//Sabiduria
								sabPersonaje += valor;
								break;
							case 5:
								//Inteligencia
								intPersonaje += valor;
								break;
							case 6:
								//Carisma
								carPersonaje += valor;
								break;
							case 7:
								//AC
								ACBuffs += valor;
								break;
							case 8:
								//meleeAtk
								AtkBuffs += valor;
								break;
							case 10:
								//saveF
								saveF += valor;
								break;
							case 11:
								//saveR
								saveR += valor;
								break;
							case 12:
								//saveV
								saveV += valor;
								break;
							case 22:
								//DMG
								DmgBuffs += valor;
								break;
							default:
							
						}
					}
					final int buffAC = ACBuffs;
		    		final int buffAtk = AtkBuffs;
		    		final int buffDmg = DmgBuffs;
					
					/* MODIFICADORES DE ESTADISTICAS */
		    		int fuerza = 0;
		    		int modBaseFuerza = 0;
		    		int destreza = 0;
		    		int modBaseDestreza = 0;
		    		int constitucion = 0;
		    		int modBaseConstitucion = 0;
		    		int inteligencia = 0;
		    		int modBaseInteligencia = 0;
		    		int sabiduria = 0;
		    		int modBaseSabiduria = 0;
		    		int carisma = 0;
		    		int modBaseCarisma = 0;
		    		
					//Buscar puntuacion de fuerza del personaje
					int StrMod = 0;
					Cursor c4 = db.consulta(
							"SELECT PERSONAJES_STATS.valor FROM PERSONAJES_STATS, STATS " + 
							"WHERE PERSONAJES_STATS.idStat = STATS.idStat " + 
							"AND STATS.nombreStat = 'Fuerza' " + 
							"AND PERSONAJES_STATS.idPersonaje = " + idPersonaje + ";"
							,null);
					c4.moveToFirst();
					fuerza = c4.getInt(0);
					c4.close();
					fuerzaPersonaje += fuerza;
					//Obtener modificador de fuerza base
					Cursor cFue = db.consulta("SELECT modificador FROM MODIF_STAT WHERE puntuacion = " + fuerza + ";", null);
					cFue.moveToFirst();
					modBaseFuerza = cFue.getInt(0);
					cFue.close();
					//Obtener modificador de fuerza modificada
					Cursor c5 = db.consulta("SELECT modificador FROM MODIF_STAT WHERE puntuacion = " + fuerzaPersonaje + ";", null);
					c5.moveToFirst();
					StrMod = c5.getInt(0);
					c5.close();
					final int modFuerza = StrMod;
					
					//Buscar puntuacion de destreza del personaje
		    		int DesMod = 0;
		    		Cursor c1 = db.consulta(
							"SELECT PERSONAJES_STATS.valor FROM PERSONAJES_STATS, STATS " + 
							"WHERE PERSONAJES_STATS.idStat = STATS.idStat " + 
							"AND STATS.nombreStat = 'Destreza' " + 
							"AND PERSONAJES_STATS.idPersonaje = " + idPersonaje + ";"
							,null);
					c1.moveToFirst();
					destreza = c1.getInt(0);
					c1.close();
					destrezaPersonaje += destreza;
					//Obtener modificador de destreza base
					Cursor cDes = db.consulta("SELECT modificador FROM MODIF_STAT WHERE puntuacion = " + destreza + ";", null);
					cDes.moveToFirst();
					modBaseDestreza = cDes.getInt(0);
					cDes.close();
					//Obtener modificador de destreza modificada
					Cursor c2 = db.consulta("SELECT modificador FROM MODIF_STAT WHERE puntuacion = " + destrezaPersonaje + ";", null);
					c2.moveToFirst();
					DesMod = c2.getInt(0);
					c2.close();
					final int modDestreza = DesMod;
					
					//Buscar puntuacion de constitucion del personaje
					int ConMod = 0;
					Cursor c11 = db.consulta(
							"SELECT PERSONAJES_STATS.valor FROM PERSONAJES_STATS, STATS " + 
							"WHERE PERSONAJES_STATS.idStat = STATS.idStat " + 
							"AND STATS.nombreStat = 'Constitucion' " + 
							"AND PERSONAJES_STATS.idPersonaje = " + idPersonaje + ";"
							,null);
					c11.moveToFirst();
					constitucion = c11.getInt(0);
					c11.close();
					consPersonaje += constitucion;
					//Obtener modificador de constitucion base
					Cursor cCon = db.consulta("SELECT modificador FROM MODIF_STAT WHERE puntuacion = " + constitucion + ";", null);
					cCon.moveToFirst();
					modBaseConstitucion = cCon.getInt(0);
					cCon.close();
					//Obtener modificador de constitucion modificada
					Cursor c12 = db.consulta("SELECT modificador FROM MODIF_STAT WHERE puntuacion = " + consPersonaje + ";", null);
					c12.moveToFirst();
					ConMod = c12.getInt(0);
					c12.close(); 
					final int modCon = ConMod;
					
					//Buscar puntuacion de inteligencia del personaje
					int IntMod = 0;
					Cursor cIntBase = db.consulta(
							"SELECT PERSONAJES_STATS.valor FROM PERSONAJES_STATS, STATS " + 
							"WHERE PERSONAJES_STATS.idStat = STATS.idStat " + 
							"AND STATS.nombreStat = 'Inteligencia' " + 
							"AND PERSONAJES_STATS.idPersonaje = " + idPersonaje + ";"
							,null);
					cIntBase.moveToFirst();
					inteligencia = cIntBase.getInt(0);
					cIntBase.close();
					intPersonaje += inteligencia;
					//Obtener modificador de inteligencia base
					Cursor cInt = db.consulta("SELECT modificador FROM MODIF_STAT WHERE puntuacion = " + inteligencia + ";", null);
					cInt.moveToFirst();
					modBaseInteligencia = cInt.getInt(0);
					cInt.close();
					//Obtener modificador de inteligencia modificada
					Cursor cModInt = db.consulta("SELECT modificador FROM MODIF_STAT WHERE puntuacion = " + intPersonaje + ";", null);
					cModInt.moveToFirst();
					IntMod = cModInt.getInt(0);
					cModInt.close();
					final int modInt = IntMod;
					
					//Buscar puntuacion de sabiduria del personaje
					int SabMod = 0;
					Cursor c6 = db.consulta(
							"SELECT PERSONAJES_STATS.valor FROM PERSONAJES_STATS, STATS " + 
							"WHERE PERSONAJES_STATS.idStat = STATS.idStat " + 
							"AND STATS.nombreStat = 'Sabiduria' " + 
							"AND PERSONAJES_STATS.idPersonaje = " + idPersonaje + ";"
							,null);
					c6.moveToFirst();
					sabiduria = c6.getInt(0);
					c6.close();
					sabPersonaje += sabiduria;
					//Obtener modificador de sabiduria base
					Cursor cSab = db.consulta("SELECT modificador FROM MODIF_STAT WHERE puntuacion = " + sabiduria + ";", null);
					cSab.moveToFirst();
					modBaseSabiduria = cSab.getInt(0);
					cSab.close();
					//Obtener modificador de sabiduria modificada
					Cursor c7 = db.consulta("SELECT modificador FROM MODIF_STAT WHERE puntuacion = " + sabPersonaje + ";", null);
					c7.moveToFirst();
					SabMod = c7.getInt(0);
					c7.close();
					final int modSab = SabMod;
					
					//Buscar puntuacion de carisma del personaje
					int CarMod = 0;
					Cursor cCarBase = db.consulta(
							"SELECT PERSONAJES_STATS.valor FROM PERSONAJES_STATS, STATS " + 
							"WHERE PERSONAJES_STATS.idStat = STATS.idStat " + 
							"AND STATS.nombreStat = 'Carisma' " + 
							"AND PERSONAJES_STATS.idPersonaje = " + idPersonaje + ";"
							,null);
					cCarBase.moveToFirst();
					carisma = cCarBase.getInt(0);
					cCarBase.close();
					carPersonaje += carisma;
					//Obtener modificador de carisma base
					Cursor cCar = db.consulta("SELECT modificador FROM MODIF_STAT WHERE puntuacion = " + carisma + ";", null);
					cCar.moveToFirst();
					modBaseCarisma = cCar.getInt(0);
					cCar.close();
					//Obtener modificador de carisma modificada
					Cursor cCarMod = db.consulta("SELECT modificador FROM MODIF_STAT WHERE puntuacion = " + carPersonaje + ";", null);
					cCarMod.moveToFirst();
					CarMod = cCarMod.getInt(0);
					cCarMod.close();
					final int modCar = CarMod;
					
					//Rellenar tabla de caracteristicas
					tablaCaracteristicas.removeAllViews();
					insertarFilaLlena("Fuerza", fuerza, modBaseFuerza, fuerzaPersonaje, modFuerza);
					insertarFilaLlena("Destreza", destreza, modBaseDestreza, destrezaPersonaje, modDestreza);
					insertarFilaLlena("Constitución", constitucion, modBaseConstitucion, consPersonaje, modCon);
					insertarFilaLlena("Inteligencia", inteligencia, modBaseInteligencia, intPersonaje, modInt);
					insertarFilaLlena("Sabiduría", sabiduria, modBaseSabiduria, sabPersonaje, modSab);
					insertarFilaLlena("Carisma", carisma, modBaseCarisma, carPersonaje, modCar);
					
					/* MODIFICADORES DE TAMAÑO */
					
					//Comprobar tama�o del personaje
		    		Cursor c10 = db.consulta("SELECT idTamano FROM PERSONAJES WHERE idPersonaje = " + idPersonaje + ";", null);
		    		c10.moveToFirst();
		    		idTamaño = c10.getInt(0);
		    		c10.close();
		    		String dados= "";
		    		switch (idTamaño){
			    		case 1:
			    			dados = "dmF";
			    			break;
			    		case 2:
			    			dados = "dmD";
			    			break;
			    		case 3:
			    			dados = "dmT";
			    			break;
			    		case 4:
			    			dados = "dmS";
			    			break;
			    		case 5:
			    			dados = "dmM";
			    			break;
			    		case 6:
			    			dados = "dmL";
			    			break;
			    		case 7:
			    			dados = "dmH";
			    			break;
			    		case 8:
			    			dados = "dmG";
			    			break;
			    		case 9:
			    			dados = "dmC";
			    			break;
		    		}
		    		Cursor cTamaño = db.consulta("SELECT acBonus, atkBonus FROM TAMANOS WHERE idTamano = " + idTamaño + ";", null);
		    		cTamaño.moveToFirst();
		    		final int modAcTamaño = cTamaño.getInt(0);
		    		int modAtkTamaño = cTamaño.getInt(1);
		    		cTamaño.close();
					
		    		/* ATAQUE BASE Y SAVES SEGUN CLASE */
		    		
		    		//Calcular el ataque (Ataque base + fuerza + efectos)
		    		
		    		//Buscar clase del personaje y obtener ataque base y saves en funcion del nivel/es del personaje
		    		//Primero obtener clases y niveles
		    		Cursor c3 = db.consulta("SELECT idClase, nivel FROM PERSONAJES_CLASES WHERE idPersonaje = " + idPersonaje + ";", null);
		    		List <Integer> listaAtk = new ArrayList<Integer>();
		    		List <Integer> listaSaveF = new ArrayList<Integer>();
		    		List <Integer> listaSaveR = new ArrayList<Integer>();
		    		List <Integer> listaSaveV = new ArrayList<Integer>();
		    		if (c3.moveToFirst()){
		    			do{
		    				int idClase = c3.getInt(0);
		    				int nivel = c3.getInt(1);
		    				//Buscar ataque base y saves de la clase y nivel obtenidos
		    				Cursor c33 = db.consulta(
		    						"SELECT saveFort, saveRef, saveWill, baseAtk " + 
		    						"FROM CLASES_ATK_SAVES WHERE idClase = " + idClase + " AND nivel = " + nivel + ";", null);
		    				//A�adir los datos obtenidos a las arraylists
		    				if (c33.moveToFirst()){
		    					do {
		    						listaAtk.add(c33.getInt(3));
						    		listaSaveF.add(c33.getInt(0));
						    		listaSaveR.add(c33.getInt(1));
						    		listaSaveV.add(c33.getInt(2));		    						
		    					} while (c33.moveToNext());
		    				}
		    				c33.close();
		    			} while (c3.moveToNext());
		    		}
		    		c3.close();
		    		//Calcular su armadura (10 + Destreza + escudo + Tamaño + efectos) Armadura = efecto (mas abajo)
					int idEscudo = 0;
					Cursor c8 = db.consulta("SELECT offEscudo FROM PERSONAJES_EQUIPO WHERE idPersonaje = " + idPersonaje + ";", null);
					if (c8.moveToFirst()){
						do {
							idEscudo = c8.getInt(0);
						} while (c8.moveToNext());
					}
					c8.close();
					
		    		/* DADOS DE DAÑO DEL ARMA DEL PERSONAJE */
		    		
		    		//Comprobar si el personaje lleva arma equipada en las dos manos
		    		int idArma = 0;
		    		int idArmaOff = 0;
		    		Cursor c9 = db.consulta("SELECT mainhand, offArma FROM PERSONAJES_EQUIPO WHERE idPersonaje = " + idPersonaje + ";", null);
					if (c9.moveToFirst()){
						do {
							idArma = c9.getInt(0);
							idArmaOff = c9.getInt(1);
							if (idArma > 0){
								Cursor c91 = db.consulta("SELECT " + dados + ", tipoArma from ARMAS WHERE idArma = " + idArma + ";", null);
								c91.moveToFirst();
								dadosDmgMain += c91.getString(0);
								tipoArma = c91.getString(1);
								c91.close();
							}
							if (idArmaOff > 0){
								Cursor c92 = db.consulta("SELECT " + dados + " from ARMAS WHERE idArma = " + idArmaOff + ";", null);
								c92.moveToFirst();
								dadosDmgOff += c92.getString(0);
								c92.close();
							}
							if (idArma == 0 && idArmaOff == 0){
								dadosDmgMain = "---";
							}
						} while (c9.moveToNext());
					}
					c9.close();
		    		
		    		//Sumar el total de ataque base de los datos metidos en las listas
		    		for (int i=0; i < listaAtk.size(); i++){
		    			atkBase += listaAtk.get(i);
		    		}
		    		
		    		//Sumar el total de saves
		    		for (int i=0; i < listaSaveF.size(); i++){
		    			saveF += listaSaveF.get(i);
		    		}
		    		for (int i=0; i < listaSaveR.size(); i++){
		    			saveR += listaSaveR.get(i);
		    		}
		    		for (int i=0; i < listaSaveV.size(); i++){
		    			saveV += listaSaveV.get(i);
		    		}
		    		
					boolean finesse = false;
					Cursor cDote1 = db.consulta(
		    				"SELECT a.nombre " + 
		    				"FROM EFECTOS a INNER JOIN PERSONAJES_EFECTOS b ON  a.idEfecto = b.idEfecto " + 
		    				"AND b.idPersonaje = " + idPersonaje + ";", null);
		    		if (cDote1.moveToFirst()){
		    			do {
		    				String efecto = cDote1.getString(0);
		    				if (efecto.equalsIgnoreCase("Sutileza con las armas")){
		    					finesse = true;
		    				}
		    			} while (cDote1.moveToNext());
		    		}
		    		boolean dervish = false;
					Cursor cDote2 = db.consulta(
		    				"SELECT a.nombre " + 
		    				"FROM EFECTOS a INNER JOIN PERSONAJES_EFECTOS b ON  a.idEfecto = b.idEfecto " + 
		    				"AND b.idPersonaje = " + idPersonaje + ";", null);
		    		if (cDote2.moveToFirst()){
		    			do {
		    				String efecto = cDote2.getString(0);
		    				if (efecto.equalsIgnoreCase("Dervish Dance")){
		    					//Hallar id del arma "Cimitarra"
		    					int idCimitarra = 0;
		    					Cursor cArma = db.consulta("SELECT idArma FROM ARMAS WHERE nombre = 'Cimitarra';", null);
		    					if (cArma.moveToFirst()){
		    						do {
		    							idCimitarra = cArma.getInt(0);
		    						} while (cArma.moveToNext());
		    					}
		    					if (idCimitarra != 0){
		    						if (idArma == idCimitarra && idEscudo == 0 && idArmaOff == 0){
		    							dervish = true;
		    						}
		    					}
		    				}
		    			} while (cDote2.moveToNext());
		    		}
		    		
		    		@SuppressWarnings("unused")
					int modStatAtk = modFuerza;
		    		
		    		int dexBonusMax = 0;
		    		int idArmadura = 0;
		    		Cursor getArmor = db.consulta(
		    				"SELECT a.maxDesBonus, b.armadura FROM ARMADURAS a, PERSONAJES_EQUIPO b " +  
		    				"WHERE a.idArmadura = b.armadura AND b.idpersonaje = " + idPersonaje + ";", null);
		    		if (getArmor.moveToFirst()){
		    			do {
		    				dexBonusMax = getArmor.getInt(0);
		    				idArmadura = getArmor.getInt(1);
		    			} while (getArmor.moveToNext());
		    		}
		    				    		
		    		saveF += modCon;
		    		saveR += modDestreza;
		    		saveV += modSab;
		    		
		    		//Calcular entrenamientos con arma y entrenamientos con armadura de la clase warrior
		    		for (int i=0; i < clases.size(); i++){
		    			int idClase = clases.get(i);
		    			String nombre = "";
		    			Cursor caca = db.consulta("SELECT nombre FROM CLASES WHERE idClase = " + idClase + ";", null);
		    			if (caca.moveToFirst()){
		    				do {
		    					nombre = caca.getString(0);
		    				} while (caca.moveToNext());
		    			}
		    			caca.close();
		    			if (nombre.equalsIgnoreCase("luchador") || nombre.equalsIgnoreCase("guerrero") || 
		    					nombre.equalsIgnoreCase("warrior") || nombre.equalsIgnoreCase("fighter")){
		    				int nivelClase = 0;
		    				Cursor nivel = db.consulta(
		    						"SELECT nivel FROM PERSONAJES_CLASES WHERE idClase = " + idClase + " " + 
		    						"AND idPersonaje = " + idPersonaje + ";", null);
		    				if (nivel.moveToFirst()){
		    					do {
		    						nivelClase = nivel.getInt(0);
		    					} while (nivel.moveToNext());
		    				}
		    				nivel.close();
		    				//Entrenamiento con armaduras
		    				if (nivelClase >= 15){
		    					dexBonusMax +=4;
		    				} else if (nivelClase >= 11){
		    					dexBonusMax +=3;
		    				} else if (nivelClase >= 7){
		    					dexBonusMax +=2;
		    				} else if(nivelClase >=3){
		    					dexBonusMax +=1;
		    				}
		    				//Entrenamiento con armas
		    				if (nivelClase >= 17){
		    					atkBase += 4;
		    					dmg += 4;
		    				} else if (nivelClase >= 13){
		    					atkBase +=3;
		    					dmg += 3;
		    				} else if (nivelClase >= 9){
		    					atkBase +=2;
		    					dmg += 2;
		    				} else if(nivelClase >= 5){
		    					atkBase +=1;
		    					dmg += 1;
		    				}
		    			} else if (nombre.equalsIgnoreCase("paladin")) {
		    				int nivelClase = 0;
		    				Cursor nivel = db.consulta(
		    						"SELECT nivel FROM PERSONAJES_CLASES WHERE idClase = " + idClase + " " + 
		    						"AND idPersonaje = " + idPersonaje + ";", null);
		    				if (nivel.moveToFirst()){
		    					do {
		    						nivelClase = nivel.getInt(0);
		    					} while (nivel.moveToNext());
		    				}
		    				nivel.close();
		    				//Suma de modificador de carisma a las salvaciones si es mayos de nivel 2 de paladin
		    				if (nivelClase >=2){
		    					saveF += modCar;
		    		    		saveR += modCar;
		    		    		saveV += modCar;
		    				}
		    			}
		    		}
		    		
		    		final int maxDesBonus = dexBonusMax;
		    		
		    		int bonoDesArmadura;
		    		if (modDestreza > maxDesBonus && idArmadura > 0){
		    			bonoDesArmadura = maxDesBonus;
		    		} else {
		    			bonoDesArmadura = modDestreza;
		    		}
		    		armor = armor + bonoDesArmadura + modAcTamaño + buffAC;
		    		
					//Calcular el daño (mod fuerza + efectos)
					modStatDmg += modFuerza;
					if (dervish == true){modStatDmg = modDestreza;}
					if (tipoArma.equalsIgnoreCase("2")){ 
		    			modStatDmg += (modFuerza/2); 
		    		} else if (tipoArma.equalsIgnoreCase("R")){
		    			modStatDmg = 0;
		    			modStatAtk = modDestreza;
		    		}
					if (finesse == true){modStatAtk = modDestreza;}
		    		int dmgMain =  dmg + modStatDmg + buffDmg;
		    		int dmgOff = dmg + (modStatDmg/2) + buffDmg;
		    		
		    		//Capturar efecto de "Maximo Armor buff (no se suman)" si es que existe
		    		int armorPersonaje = 0;
		    		Cursor carmorpj = db.consulta(
		    				"SELECT MAX(valor) FROM EFECTOS_STATS a, PERSONAJES_EFECTOS b " + 
		    				"WHERE a.idEfecto = b.idEfecto AND a.idStat = 7 AND a.idTipo = 2 AND idPersonaje = " + idPersonaje + 
		    				" AND afectado = 1;", null);
		    		if (carmorpj.moveToFirst()){
		    			do {
		    				armorPersonaje = carmorpj.getInt(0);
		    			} while (carmorpj.moveToNext());
		    		}
		    		carmorpj.close();
		    		
		    		//Capturar efecto de "Maximo shield buff" si es que existe
		    		int escudoPersonaje = 0;
		    		Cursor cescudopj = db.consulta(
		    				"SELECT MAX(valor) FROM EFECTOS_STATS a, PERSONAJES_EFECTOS b " + 
		    				"WHERE a.idEfecto = b.idEfecto AND a.idStat = 7 AND a.idTipo = 16 AND idPersonaje = " + idPersonaje + 
		    				" AND afectado = 1;", null);
		    		if (cescudopj.moveToFirst()){
		    			do {
		    				escudoPersonaje = cescudopj.getInt(0);
		    			} while (cescudopj.moveToNext());
		    		}
		    		cescudopj.close();
		    		
		    		//Capturar efecto de "Maximo natural armor" si es que existe
		    		int naturalArmor = 0;
		    		Cursor cnatural = db.consulta(
		    				"SELECT MAX(valor) FROM EFECTOS_STATS a, PERSONAJES_EFECTOS b " + 
		    				"WHERE a.idEfecto = b.idEfecto AND a.idStat = 7 AND a.idTipo = 12 AND idPersonaje = " + idPersonaje + 
		    				" AND afectado = 1;", null);
		    		if (cnatural.moveToFirst()){
		    			do {
		    				naturalArmor = cnatural.getInt(0);
		    			} while (cnatural.moveToNext());
		    		}
		    		cnatural.close();
		    		int dodgeBonus = 0;
		    		//Capturar efecto "Suma dodge" si es que existe
		    		Cursor dodge = db.consulta(
		    				"SELECT SUM(valor) FROM EFECTOS_STATS a, PERSONAJES_EFECTOS b " + 
		    				"WHERE a.idEfecto = b.idEfecto AND a.idStat = 7 AND a.idTipo = 6 AND idPersonaje = " + idPersonaje + ";", null);
		    		if (dodge.moveToFirst()){ do { dodgeBonus += dodge.getInt(0); } while (dodge.moveToNext()); }
		    		
		    		int armorTotal = 10 + bonoDesArmadura + modAcTamaño + buffAC;
		    		int armorToque = 10 + bonoDesArmadura + modAcTamaño + buffAC - armorPersonaje - escudoPersonaje - naturalArmor;
		    		int armorDesprevenido = 10 + buffAC + modAcTamaño - dodgeBonus;
		    		int armorSinEscudo = 10 + bonoDesArmadura + modAcTamaño + buffAC - escudoPersonaje;
		    		
		    		/* RELLENADO DATOS PESTAñA DEFENSAS */
		    		txtArmaduraTotalEstadisticas.setText(Integer.toString(armorTotal));
		    		txtArmaduraToqueEstadisticas.setText(Integer.toString(armorToque));
		    		txtArmaduraDesprevenidoEstadisticas.setText(Integer.toString(armorDesprevenido));
		    		txtArmaduraSinEscudoEstadisticas.setText(Integer.toString(armorSinEscudo));
		    		txtFortalezaEstadisticasPersonaje.setText(Integer.toString(saveF));
		    		txtReflejosEstadisticasPersonaje.setText(Integer.toString(saveR));
		    		txtVoluntadEstadisticasPersonaje.setText(Integer.toString(saveV));
		    		
		    		int modBMCTamaño = 0;
		    		int modDMCTamaño = 0;
		    		
		    		Cursor tamaño = db.consulta("SELECT atkBonus, cmdBonus, cmbBonus FROM TAMANOS WHERE idTamano = " + idTamaño + ";", null);
		    		if (tamaño.moveToFirst()){
		    			do {
		    				modAtkTamaño= tamaño.getInt(0);
		    				modDMCTamaño = tamaño.getInt(1);
		    				modBMCTamaño = tamaño.getInt(2);
		    			} while (tamaño.moveToNext());
		    		}
		    		tamaño.close();
		    		
		    		int modAtk = modFuerza;
		    		if (finesse == true) modAtk = modDestreza;
		    		
		    		int buffsDMC = 0;
		    		int modStatBMC = modFuerza;
		    		if (idTamaño <= 3){modStatBMC = modDestreza;}
		    		//Buffs circumstance, dodge y untyped (stackean)
		    		Cursor circDef = db.consulta(
		    				"SELECT SUM(valor) FROM EFECTOS_STATS a, PERSONAJES_EFECTOS b " + 
		    				"WHERE a.idEfecto = b.idEfecto AND a.idStat = 7 AND a.idTipo in (3,6,19) AND idPersonaje = " + idPersonaje + ";", null);
		    		if (circDef.moveToFirst()){ do { buffsDMC += circDef.getInt(0); } while (circDef.moveToNext()); }
		    		//Buffs deflection, insight, luck, morale, profane y sacred (no stackean)
		    		//Deflection
		    		Cursor deflection = db.consulta(
		    				"SELECT MAX(valor) FROM EFECTOS_STATS a, PERSONAJES_EFECTOS b " + 
		    				"WHERE a.idEfecto = b.idEfecto AND a.idStat = 7 AND a.idTipo = 5 AND idPersonaje = " + idPersonaje + ";", null);
		    		if (deflection.moveToFirst()){do{ buffsDMC += deflection.getInt(0); } while (deflection.moveToNext());}
		    		//Insight
		    		Cursor insight = db.consulta(
		    				"SELECT MAX(valor) FROM EFECTOS_STATS a, PERSONAJES_EFECTOS b " + 
		    				"WHERE a.idEfecto = b.idEfecto AND a.idStat = 7 AND a.idTipo = 9 AND idPersonaje = " + idPersonaje + ";", null);
		    		if (insight.moveToFirst()){do{ buffsDMC += insight.getInt(0); } while (insight.moveToNext());}
		    		//Luck
		    		Cursor luck = db.consulta(
		    				"SELECT MAX(valor) FROM EFECTOS_STATS a, PERSONAJES_EFECTOS b " + 
		    				"WHERE a.idEfecto = b.idEfecto AND a.idStat = 7 AND a.idTipo = 10 AND idPersonaje = " + idPersonaje + ";", null);
		    		if (luck.moveToFirst()){do{ buffsDMC += luck.getInt(0); } while (luck.moveToNext());}
		    		//Morale
		    		Cursor morale = db.consulta(
		    				"SELECT MAX(valor) FROM EFECTOS_STATS a, PERSONAJES_EFECTOS b " + 
		    				"WHERE a.idEfecto = b.idEfecto AND a.idStat = 7 AND a.idTipo = 11 AND idPersonaje = " + idPersonaje + ";", null);
		    		if (morale.moveToFirst()){do{ buffsDMC += morale.getInt(0); } while (morale.moveToNext());}
		    		//Profane
		    		Cursor profane = db.consulta(
		    				"SELECT MAX(valor) FROM EFECTOS_STATS a, PERSONAJES_EFECTOS b " + 
		    				"WHERE a.idEfecto = b.idEfecto AND a.idStat = 7 AND a.idTipo = 13 AND idPersonaje = " + idPersonaje + ";", null);
		    		if (profane.moveToFirst()){do{ buffsDMC += profane.getInt(0); } while (profane.moveToNext());}
		    		//Sacred
		    		Cursor sacred = db.consulta(
		    				"SELECT MAX(valor) FROM EFECTOS_STATS a, PERSONAJES_EFECTOS b " + 
		    				"WHERE a.idEfecto = b.idEfecto AND a.idStat = 7 AND a.idTipo = 15 AND idPersonaje = " + idPersonaje + ";", null);
		    		if (sacred.moveToFirst()){do{ buffsDMC += sacred.getInt(0); } while (sacred.moveToNext());}
		    		
		    		String ataque = "";
		    		String ataqueD = "";
		    		int ataque1 = 0;
		    		int ataque2 = 0;
		    		int ataque3 = 0;
		    		int ataque4 = 0;
		    		
		    		/* ATAQUE CC */
		    		if (atkBase > 15){
		    			ataque1 = atkBase + modAtk + modAtkTamaño + buffAtk;
		    			ataque2 = (atkBase-5) + modAtk + modAtkTamaño + buffAtk;
		    			ataque3 = (atkBase-10) + modAtk + modAtkTamaño + buffAtk;
		    			ataque4 = (atkBase-15) + modAtk + modAtkTamaño + buffAtk;
		    			ataque = Integer.toString(ataque1) + " / " + Integer.toString(ataque2) + " / " + Integer.toString(ataque3) + " / " + Integer.toString(ataque4);
		    		} else if (atkBase < 15 && atkBase > 10){
		    			ataque1 = atkBase + modAtk + modAtkTamaño + buffAtk;
		    			ataque2 = (atkBase-5) + modAtk + modAtkTamaño + buffAtk;
		    			ataque3 = (atkBase-10) + modAtk + modAtkTamaño + buffAtk;
		    			ataque = Integer.toString(ataque1) + " / " + Integer.toString(ataque2) + " / " + Integer.toString(ataque3);
		    		} else if (atkBase < 10 && atkBase > 5){
		    			ataque1 = atkBase + modAtk + modAtkTamaño + buffAtk;
		    			ataque2 = (atkBase-5) + modAtk + modAtkTamaño + buffAtk;
		    			ataque = Integer.toString(ataque1) + " / " + Integer.toString(ataque2);
		    		} else {
		    			ataque1 = atkBase + modAtk + modAtkTamaño + buffAtk;
		    			ataque = Integer.toString(ataque1);
		    		}
		    		
		    		/* ATAQUE DISTANCIA */
		    		if (atkBase > 15){
		    			ataque1 = atkBase + modDestreza + modAtkTamaño + buffAtk;
		    			ataque2 = (atkBase-5) + modDestreza + modAtkTamaño + buffAtk;
		    			ataque3 = (atkBase-10) + modDestreza + modAtkTamaño + buffAtk;
		    			ataque4 = (atkBase-15) + modDestreza + modAtkTamaño + buffAtk;
		    			ataqueD = Integer.toString(ataque1) + " / " + Integer.toString(ataque2) + " / " + Integer.toString(ataque3) + " / " + Integer.toString(ataque4);
		    		} else if (atkBase < 15 && atkBase > 10){
		    			ataque1 = atkBase + modDestreza + modAtkTamaño + buffAtk;
		    			ataque2 = (atkBase-5) + modDestreza + modAtkTamaño + buffAtk;
		    			ataque3 = (atkBase-10) + modDestreza + modAtkTamaño + buffAtk;
		    			ataqueD = Integer.toString(ataque1) + " / " + Integer.toString(ataque2) + " / " + Integer.toString(ataque3);
		    		} else if (atkBase < 10 && atkBase > 5){
		    			ataque1 = atkBase + modDestreza + modAtkTamaño + buffAtk;
		    			ataque2 = (atkBase-5) + modDestreza + modAtkTamaño + buffAtk;
		    			ataqueD = Integer.toString(ataque1) + " / " + Integer.toString(ataque2);
		    		} else {
		    			ataque1 = atkBase + modDestreza + modAtkTamaño + buffAtk;
		    			ataqueD = Integer.toString(ataque1);
		    		}
		    		
		    		/* BMC */
		    		int bmc = atkBase + modStatBMC + modBMCTamaño + buffAtk;
		    		/* DMC */
		    		int dmc = 10 + atkBase + modFuerza + modDestreza + modDMCTamaño + buffsDMC;
		    		
		    		/* RELLENADO DATOS PESTAÑA ATAQUE */
		    		txtAtaqueCCEstadisticas.setText(ataque);
		    		txtAtaqueDistanciaEstadisticas.setText(ataqueD);
		    		txtBMCEstadisticas.setText(Integer.toString(bmc));
		    		txtDMCEstadisticas.setText(Integer.toString(dmc));
		    		
		    		tablaEstadisticas.removeAllViews();
		    		
		    		if (idArma > 0){
		    			TableRow fila;
			    		TextView textoNombreArma;
			    		TextView textoDadoDmg;
			    		TextView textoCritico;
			    		TextView textoDmgPersonaje;
			    		
			    		String armaMainhand = "";
			    		String critMainhand = "";
			    		Cursor cMain = db.consulta("SELECT nombre, critico FROM ARMAS WHERE idArma = " + idArma + ";", null);
			    		cMain.moveToFirst();
			    		armaMainhand = cMain.getString(0);
			    		critMainhand = cMain.getString(1);
			    		cMain.close();
			    		
			    		fila = new TableRow(getBaseContext());
			    		
			    		textoNombreArma = new TextView(getBaseContext());
			    		textoDadoDmg = new TextView(getBaseContext());
			    		textoDmgPersonaje = new TextView(getBaseContext());
			    		textoCritico = new TextView(getBaseContext());
			    		
			    		textoNombreArma.setText(armaMainhand);
			    		textoNombreArma.setGravity(Gravity.CENTER_VERTICAL);
			    		textoNombreArma.setBackgroundResource(R.drawable.tabla_celda);
			    		textoNombreArma.setTextColor(0xFF000000);
			    		textoNombreArma.setLayoutParams(layoutL);
			    		
			    		textoDadoDmg.setText(dadosDmgMain);
			    		textoDadoDmg.setGravity(Gravity.CENTER);
			    		textoDadoDmg.setBackgroundResource(R.drawable.tabla_celda);
			    		textoDadoDmg.setTextColor(0xFF000000);
			    		textoDadoDmg.setLayoutParams(layoutM);
			    		
			    		textoCritico.setText(critMainhand);
			    		textoCritico.setGravity(Gravity.CENTER);
			    		textoCritico.setBackgroundResource(R.drawable.tabla_celda);
			    		textoCritico.setTextColor(0xFF000000);
			    		textoCritico.setLayoutParams(layoutL);
			    		
			    		textoDmgPersonaje.setText(Integer.toString(dmgMain));
			    		textoDmgPersonaje.setGravity(Gravity.CENTER);
			    		textoDmgPersonaje.setBackgroundResource(R.drawable.tabla_celda);
			    		textoDmgPersonaje.setTextColor(0xFF000000);
			    		textoDmgPersonaje.setLayoutParams(layoutM);
			    		
			    		fila.addView(textoNombreArma);
			    		fila.addView(textoDadoDmg);
			    		fila.addView(textoCritico);
			    		fila.addView(textoDmgPersonaje);

			    		tablaEstadisticas.addView(fila);
		    		}
		    		if (idArmaOff > 0){
		    			TableRow fila;
			    		TextView textoNombreArma;
			    		TextView textoDadoDmg;
			    		TextView textoCritico;
			    		TextView textoDmgPersonaje;
			    		
			    		String armaOff = "";
			    		String critOff = "";
			    		Cursor cOff = db.consulta("SELECT nombre, critico FROM ARMAS WHERE idArma = " + idArmaOff + ";", null);
			    		cOff.moveToFirst();
			    		armaOff = cOff.getString(0);
			    		critOff = cOff.getString(1);
			    		cOff.close();
			    		
			    		fila = new TableRow(getBaseContext());
			    		
			    		textoNombreArma = new TextView(getBaseContext());
			    		textoDadoDmg = new TextView(getBaseContext());
			    		textoDmgPersonaje = new TextView(getBaseContext());
			    		textoCritico = new TextView(getBaseContext());
			    		
			    		textoNombreArma.setText(armaOff);
			    		textoNombreArma.setGravity(Gravity.CENTER_VERTICAL);
			    		textoNombreArma.setBackgroundResource(R.drawable.tabla_celda);
			    		textoNombreArma.setTextColor(0xFF000000);
			    		textoNombreArma.setLayoutParams(layoutL);
			    		
			    		textoDadoDmg.setText(dadosDmgOff);
			    		textoDadoDmg.setGravity(Gravity.CENTER);
			    		textoDadoDmg.setBackgroundResource(R.drawable.tabla_celda);
			    		textoDadoDmg.setTextColor(0xFF000000);
			    		textoDadoDmg.setLayoutParams(layoutM);
			    		
			    		textoCritico.setText(critOff);
			    		textoCritico.setGravity(Gravity.CENTER);
			    		textoCritico.setBackgroundResource(R.drawable.tabla_celda);
			    		textoCritico.setTextColor(0xFF000000);
			    		textoCritico.setLayoutParams(layoutM);
			    		
			    		textoDmgPersonaje.setText(Integer.toString(dmgOff));
			    		textoDmgPersonaje.setGravity(Gravity.CENTER);
			    		textoDmgPersonaje.setBackgroundResource(R.drawable.tabla_celda);
			    		textoDmgPersonaje.setTextColor(0xFF000000);
			    		textoDmgPersonaje.setLayoutParams(layoutS);
			    		
			    		fila.addView(textoNombreArma);
			    		fila.addView(textoDadoDmg);
			    		fila.addView(textoCritico);
			    		fila.addView(textoDmgPersonaje);

			    		tablaEstadisticas.addView(fila);
		    		}
		    		
		    		
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					db.cerrar();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				return;
			}
			
		});
	}
	
	public void agregarCabeceraEstadisticas(){
		
		TableRow fila;
		TextView textoNombreArma;
		TextView textoDadoDmg;
		TextView textoCritico;
		TextView textoDmgPersonaje;
		
		fila = new TableRow(this);
		
		textoNombreArma = new TextView(this);
		textoDadoDmg = new TextView(this);
		textoDmgPersonaje = new TextView(this);
		textoCritico = new TextView(this);
		
		textoNombreArma.setText("ARMA");
		textoNombreArma.setGravity(Gravity.CENTER_VERTICAL);
		textoNombreArma.setBackgroundResource(R.drawable.tabla_celda_cabecera);
		textoNombreArma.setTypeface(null, Typeface.BOLD);
		textoNombreArma.setLayoutParams(layoutL);
		
		textoDadoDmg.setText("DAÑO");
		textoDadoDmg.setGravity(Gravity.CENTER);
		textoDadoDmg.setBackgroundResource(R.drawable.tabla_celda_cabecera);
		textoDadoDmg.setTypeface(null, Typeface.BOLD);
		textoDadoDmg.setLayoutParams(layoutM);
		
		textoCritico.setText("CRITICO");
		textoCritico.setGravity(Gravity.CENTER);
		textoCritico.setBackgroundResource(R.drawable.tabla_celda_cabecera);
		textoCritico.setTypeface(null, Typeface.BOLD);
		textoCritico.setLayoutParams(layoutL);
		
		textoDmgPersonaje.setText("+ DMG");
		textoDmgPersonaje.setGravity(Gravity.CENTER);
		textoDmgPersonaje.setBackgroundResource(R.drawable.tabla_celda_cabecera);
		textoDmgPersonaje.setTypeface(null, Typeface.BOLD);
		textoDmgPersonaje.setLayoutParams(layoutM);
		
		fila.addView(textoNombreArma);
		fila.addView(textoDadoDmg);
		fila.addView(textoCritico);
		fila.addView(textoDmgPersonaje);

		cabeceraEstadisticas.addView(fila);
		
	}
	
	public void agregarCabeceraCaracteristicas(){
		
		TableRow fila;
		TextView textoCaracteristica;
		TextView textoValor;
		TextView textoModificador;
		TextView textoTemporal;
		TextView textoModTemporal;
		
		fila = new TableRow(this);
		
		textoCaracteristica = new TextView(this);
		textoValor = new TextView(this);
		textoModificador = new TextView(this);
		textoTemporal = new TextView(this);
		textoModTemporal = new TextView(this);
		
		textoCaracteristica.setText("CARACT.");
		textoCaracteristica.setGravity(Gravity.CENTER_VERTICAL);
		textoCaracteristica.setBackgroundResource(R.drawable.tabla_celda_cabecera);
		textoCaracteristica.setTypeface(null, Typeface.BOLD);
		textoCaracteristica.setLayoutParams(layoutL);
		
		textoValor.setText("VALOR");
		textoValor.setGravity(Gravity.CENTER_VERTICAL);
		textoValor.setBackgroundResource(R.drawable.tabla_celda_cabecera);
		textoValor.setTypeface(null, Typeface.BOLD);
		textoValor.setLayoutParams(layoutM);
		
		textoModificador.setText("MOD.");
		textoModificador.setGravity(Gravity.CENTER);
		textoModificador.setBackgroundResource(R.drawable.tabla_celda_cabecera);
		textoModificador.setTypeface(null, Typeface.BOLD);
		textoModificador.setLayoutParams(layoutM);
		
		textoTemporal.setText("TEMP.");
		textoTemporal.setGravity(Gravity.CENTER);
		textoTemporal.setBackgroundResource(R.drawable.tabla_celda_cabecera);
		textoTemporal.setTypeface(null, Typeface.BOLD);
		textoTemporal.setLayoutParams(layoutM);
		
		textoModTemporal.setText("MOD.");
		textoModTemporal.setGravity(Gravity.CENTER);
		textoModTemporal.setBackgroundResource(R.drawable.tabla_celda_cabecera);
		textoModTemporal.setTypeface(null, Typeface.BOLD);
		textoModTemporal.setLayoutParams(layoutM);
		
		fila.addView(textoCaracteristica);
		fila.addView(textoValor);
		fila.addView(textoModificador);
		fila.addView(textoTemporal);
		fila.addView(textoModTemporal);

		cabeceraCaracteristicas.addView(fila);
		
	}
	
	public void insertarFilaVacia (String stat){
		
		TableRow fila;
		TextView textoCaracteristica;
		TextView textoValor;
		TextView textoModificador;
		TextView textoTemporal;
		TextView textoModTemporal;
		
		fila = new TableRow(this);
		
		textoCaracteristica = new TextView(this);
		textoValor = new TextView(this);
		textoModificador = new TextView(this);
		textoModTemporal = new TextView(this);
		textoTemporal = new TextView(this);
		
		textoCaracteristica.setText(stat);
		textoCaracteristica.setGravity(Gravity.CENTER_VERTICAL);
		textoCaracteristica.setBackgroundResource(R.drawable.tabla_celda);
		textoCaracteristica.setTextColor(0xFF000000);
		textoCaracteristica.setLayoutParams(layoutL);
		
		textoValor.setText("");
		textoValor.setGravity(Gravity.CENTER);
		textoValor.setBackgroundResource(R.drawable.tabla_celda);
		textoValor.setTextColor(0xFF000000);
		textoValor.setLayoutParams(layoutM);
		
		textoModificador.setText("");
		textoModificador.setGravity(Gravity.CENTER);
		textoModificador.setBackgroundResource(R.drawable.tabla_celda);
		textoModificador.setTextColor(0xFF000000);
		textoModificador.setLayoutParams(layoutM);
		
		textoTemporal.setText("");
		textoTemporal.setGravity(Gravity.CENTER);
		textoTemporal.setBackgroundResource(R.drawable.tabla_celda);
		textoTemporal.setTextColor(0xFF000000);
		textoTemporal.setLayoutParams(layoutM);
		
		textoModTemporal.setText("");
		textoModTemporal.setGravity(Gravity.CENTER);
		textoModTemporal.setBackgroundResource(R.drawable.tabla_celda);
		textoModTemporal.setTextColor(0xFF000000);
		textoModTemporal.setLayoutParams(layoutM);
		
		fila.addView(textoCaracteristica);
		fila.addView(textoValor);
		fila.addView(textoModificador);
		fila.addView(textoTemporal);
		fila.addView(textoModTemporal);

		tablaCaracteristicas.addView(fila);
	}
	
	public void insertarFilaLlena (String stat, int valor, int mod, int temporal, int modTemp){
		
		TableRow fila;
		TextView textoCaracteristica;
		TextView textoValor;
		TextView textoModificador;
		TextView textoTemporal;
		TextView textoModTemporal;
		
		fila = new TableRow(this);
		
		textoCaracteristica = new TextView(this);
		textoValor = new TextView(this);
		textoModificador = new TextView(this);
		textoModTemporal = new TextView(this);
		textoTemporal = new TextView(this);
		
		textoCaracteristica.setText(stat);
		textoCaracteristica.setGravity(Gravity.CENTER_VERTICAL);
		textoCaracteristica.setBackgroundResource(R.drawable.tabla_celda);
		textoCaracteristica.setTextColor(0xFF000000);
		textoCaracteristica.setLayoutParams(layoutL);
		
		textoValor.setText(Integer.toString(valor));
		textoValor.setGravity(Gravity.CENTER);
		textoValor.setBackgroundResource(R.drawable.tabla_celda);
		textoValor.setTextColor(0xFF000000);
		textoValor.setLayoutParams(layoutM);
		
		textoModificador.setText(Integer.toString(mod));
		textoModificador.setGravity(Gravity.CENTER);
		textoModificador.setBackgroundResource(R.drawable.tabla_celda);
		textoModificador.setTextColor(0xFF000000);
		textoModificador.setLayoutParams(layoutM);
		
		textoTemporal.setText(Integer.toString(temporal));
		textoTemporal.setGravity(Gravity.CENTER);
		textoTemporal.setBackgroundResource(R.drawable.tabla_celda);
		textoTemporal.setTextColor(0xFF000000);
		textoTemporal.setLayoutParams(layoutM);
		
		textoModTemporal.setText(Integer.toString(modTemp));
		textoModTemporal.setGravity(Gravity.CENTER);
		textoModTemporal.setBackgroundResource(R.drawable.tabla_celda);
		textoModTemporal.setTextColor(0xFF000000);
		textoModTemporal.setLayoutParams(layoutM);
		
		fila.addView(textoCaracteristica);
		fila.addView(textoValor);
		fila.addView(textoModificador);
		fila.addView(textoTemporal);
		fila.addView(textoModTemporal);

		tablaCaracteristicas.addView(fila);
	}
}
